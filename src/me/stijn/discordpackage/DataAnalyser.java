package me.stijn.discordpackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javafx.collections.FXCollections;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import me.stijn.discordpackage.objects.Channel;
import me.stijn.discordpackage.objects.ChatHistory;
import me.stijn.discordpackage.objects.Conversation;
import me.stijn.discordpackage.objects.Message;

public class DataAnalyser {

	public static ArrayList<ChatHistory> chats = new ArrayList<ChatHistory>(); // stores all chat messages //TODO MAYBE REMOVE

	public static boolean analyseMessages() throws IOException, ParseException, InterruptedException {
		File map = new File(Main.PACKAGE_LOCATION + "\\messages\\");
		ControllerManager.getParentController().getMessageStatsButton().setDisable(true);
		if (!map.isDirectory()) { //if map does not exists
			ControllerManager.getFileSelectionController().setStatus("NO DISCORD PACKAGE FOUND");
			return false;
		}
		
		chats.clear();
		ControllerManager.getFileSelectionController().setStatus("LOADING MESSAGES");

		ArrayList<Date> dates = loadMostUsedChats(map); //dates includes all message dates
		loadDayChart(dates);
			
		ControllerManager.getFileSelectionController().setStatus("LOADED MESSAGES");
		ControllerManager.getParentController().getMessageStatsButton().setDisable(false);
		System.out.println(ControllerManager.getParentController().getChildren());
		return true;
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
		//ControllerManager.getMessageStatsController().getDayChart().getData().add(series);
		ControllerManager.getMessageStatsController().setDayChart(series);
	}
	
	private static ArrayList<Date> loadMostUsedChats(File map) throws ParseException, IOException{
		ArrayList<Conversation> list = new ArrayList<Conversation>(); //entries of the table view
		ArrayList<Date> dates = new ArrayList<Date>(); //all the dates of all the messages

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
			if (chan.getGuild() != null && chan.getGuild().getName() != null && chan.getName() != null)
				list.add(new Conversation(chan.getGuild().getName() + " : " + chan.getName(), msges.size()));
			else
				list.add(new Conversation(chan.getId() + "", msges.size()));
			chats.add(new ChatHistory(chan, msges)); //add all messages so they can be accessed again for other shit
		}
		//ControllerManager.getMessageStatsController().getMostUsedChats().getItems().add(new Conversation("aasdfasdf", 50));
		ControllerManager.getMessageStatsController().addMostUsedChats(list);
		//System.out.println(ControllerManager.getMessageStatsController().getMostUsedChats().getItems());
		//ControllerManager.getMessageStatsController().getMostUsedChats().getItems().add(FXCollections.observableArrayList(list));

		//ControllerManager.getMessageStatsController().getMostUsedChats().setItems(FXCollections.observableArrayList(list)); //set listview
		return dates;
	}

}
