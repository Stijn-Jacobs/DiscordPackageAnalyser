package me.stijn.discordpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javafx.scene.chart.XYChart;
import me.stijn.discordpackage.objects.Channel;
import me.stijn.discordpackage.objects.ChatHistory;
import me.stijn.discordpackage.objects.Message;
import me.stijn.discordpackage.objects.tableview.Conversation;
import me.stijn.discordpackage.objects.tableview.MostUsedWordEntry;

public class DataAnalyser {

	public static ArrayList<ChatHistory> chats = new ArrayList<ChatHistory>(); // stores all chat messages //TODO MAYBE REMOVE

	public static boolean analyseMessages() throws InterruptedException {
		File map = new File(Main.PACKAGE_LOCATION + "\\messages\\");
		ControllerManager.getParentController().getMessageStatsButton().setDisable(true);
		if (!map.isDirectory()) { // if map does not exists
			ControllerManager.getFileSelectionController().setStatus("NO DISCORD PACKAGE FOUND");
			return false;
		}

		chats.clear();
		ControllerManager.getFileSelectionController().setStatus("LOADING MESSAGES");

		Thread thrd = new Thread() {
			public void run() {
				try {
					ArrayList<Date> dates = loadMostUsedChats(map);
					loadDayChart(dates);
					loadMostUsedWord();

				} catch (ParseException | IOException e) {
					e.printStackTrace();
				}
			}
		};
		thrd.start();
		thrd.join();

		ControllerManager.getFileSelectionController().setStatus("LOADED MESSAGES");
		ControllerManager.getParentController().getMessageStatsButton().setDisable(false);
		return true;
	}

	private static void loadMostUsedWord() throws IOException {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		File f = ControllerManager.getFileSelectionController().getExpletiveFile();

		List<String> words = null;
		if (f != null)
			words = Files.readAllLines(ControllerManager.getFileSelectionController().getExpletiveFile().toPath(), Charset.defaultCharset());

		for (ChatHistory c : chats) {
			for (Message m : c.getList()) {
				String[] split = m.getMsg().split(" ");
				for (String s : split) {
					if (f != null && words.contains(s) || s.equals(""))
						continue; // dont add if on expletive list or empty string

					if (map.containsKey(s))
						map.put(s, map.get(s) + 1);
					else
						map.put(s, 1);
				}
			}
		}
		ArrayList<MostUsedWordEntry> list = new ArrayList<MostUsedWordEntry>();

		for (String s : map.keySet()) {
			if (map.get(s) > 5) //only add words with at least 5 uses
				list.add(new MostUsedWordEntry(s, map.get(s)));
		}

		ControllerManager.getMessageStatsController().setMostUsedWords(list);
	}

	private static void loadDayChart(ArrayList<Date> dates) {
		HashMap<String, Integer> count = new HashMap<String, Integer>();
		for (String s : TimeUtils.getTimeInADay(10)) { // fill temp hashmap
			count.put(s, 0);
		}
		for (Date d : dates) { // floor dates to 10 minutes
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Date input = TimeUtils.toNearest10Minutes(d);
			count.put(format.format(input), count.get(format.format(input)) + 1);
		}
		dates.clear();

		XYChart.Series series = new XYChart.Series();
		for (String d : count.keySet()) {
			series.getData().add(new XYChart.Data(d, count.get(d)));
		}
		ControllerManager.getMessageStatsController().setDayChart(series);
	}

	private static ArrayList<Date> loadMostUsedChats(File map) throws ParseException, IOException {
		ArrayList<Conversation> list = new ArrayList<Conversation>(); // entries of the table view
		ArrayList<Date> dates = new ArrayList<Date>(); // all the dates of all the messages

		GsonBuilder gsonb = new GsonBuilder();
		Gson gson = gsonb.create();

		for (File f : map.listFiles()) {
			if (f.getName().contains("index")) // skip index.json file
				continue;

			ArrayList<Message> msges = new ArrayList<Message>();
			Reader csvreader = Files.newBufferedReader(Paths.get(f.getAbsolutePath() + "\\messages.csv"));
			CSVParser csvParser = new CSVParser(csvreader, CSVFormat.DEFAULT.withFirstRecordAsHeader());

			for (CSVRecord csvRecord : csvParser) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				format.setTimeZone(TimeZone.getTimeZone("GMT"));
				Date d = format.parse(csvRecord.get(1).replaceAll("000+00:00", "").replaceAll("\\+00:00", ".000")); // fix up some weird date errors in discord packages
				dates.add(d);
				msges.add(new Message(csvRecord.get(2), d));
			}
			csvParser.close();

			JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(new File(f.getAbsolutePath() + "\\channel.json")), "UTF-8"));
			Channel chan = gson.fromJson(reader, Channel.class);
			if (msges.size() > 3) { //only add to most used channels when having more than 3 messages
				if (chan.getGuild() != null && chan.getGuild().getName() != null && chan.getName() != null)
					list.add(new Conversation(chan.getGuild().getName() + " : " + chan.getName(), msges.size()));
				else
					list.add(new Conversation(chan.getId() + "", msges.size()));
			}
			chats.add(new ChatHistory(chan, msges)); // add all messages so they can be accessed again for other shit
		}
		ControllerManager.getMessageStatsController().setMostUsedChats(list);
		return dates;
	}

}
