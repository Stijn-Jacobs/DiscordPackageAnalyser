package me.stijn.discordpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import javafx.collections.FXCollections;
import me.stijn.discordpackage.objects.Channel;
import me.stijn.discordpackage.objects.ChatHistory;
import me.stijn.discordpackage.objects.Conversation;
import me.stijn.discordpackage.objects.Message;

public class DataAnalyser {
	
	public static ArrayList<ChatHistory> chats = new ArrayList<ChatHistory>();

	public static boolean analyseMessages() throws IOException, ParseException {
		File map = new File(Main.PACKAGE_LOCATION + "\\messages\\");

		if (!map.isDirectory()) {
			ControllerManager.getFileSelectionController().setStatus("NO DISCORD PACKAGE FOUND");
			ControllerManager.getParentController().getMessageStatsButton().setDisable(true);
			return false;
		}
		// process

		ArrayList<Conversation> list = new ArrayList<Conversation>();
		
		GsonBuilder gsonb = new GsonBuilder();
		Gson gson = gsonb.create();
		
		for (File f : map.listFiles()) {
			if (f.getName().contains("index"))
				continue;
			
			ArrayList<Message> msges = new ArrayList<Message>();
			//try (BufferedReader reader = Files.newBufferedReader(new File(f.getAbsolutePath() + "\\messages.csv").toPath())) {
			 //   String line;
			System.out.println(f.getName());
			    	int i = 0;
			    	String[] buff = new String[4];
			       	Scanner scanner = new Scanner(new File(f.getAbsolutePath() + "\\messages.csv"));
			        scanner.useDelimiter(",");
			        scanner.nextLine();
			        while (scanner.hasNext()){
			        	i++;
			        	buff[i] = scanner.next().replaceAll("\\n", "");
			        	//buff[i] = scanner.findInLine(rx);
			        	System.out.println(i + " : " + buff[i]);
			           // System.out.print(scanner.next() + "| " + i + " |");
			        	if (i == 3) {
			        		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			        		format.setTimeZone(TimeZone.getDefault());
			        		Date d = format.parse(buff[2].replaceAll("000+00:00", ""));    
			        		msges.add(new Message(buff[3],d));
				            i %= 3;
			        	}
			        }
			        
			        
			    
//			    while((line = reader.readLine()) != null ){
//			    	if (i == 0){
//				        i++;
//				        continue;
//			    	}
//			        String[] row = line.split(",");
//			        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//			        format.setTimeZone(TimeZone.getDefault());
//			        Date d = format.parse(row[1].replaceAll("000+00:00", ""));    
//			        System.out.println(f.getName() + " : " + line);
//			        msges.add(new Message(row[2],d));
//			    }        
			//}
			        
			        scanner.close();
			
			JsonReader reader = new JsonReader(new InputStreamReader(new FileInputStream(new File(f.getAbsolutePath() + "\\channel.json")), "UTF-8"));
			Channel chan = gson.fromJson(reader, Channel.class);
			if (chan.getGuild() != null && chan.getGuild().getName() != null && chan.getName() != null) 
				list.add(new Conversation(chan.getGuild().getName() + " : " + chan.getName(), 500));
			else 
				list.add(new Conversation(chan.getId() + "", 500));
			chats.add(new ChatHistory(chan, msges));
		}
		System.out.println(chats);
		
		ControllerManager.getMessageStatsController().mostusedchats.setItems(FXCollections.observableArrayList(list));

		ControllerManager.getFileSelectionController().setStatus("LOADED MESSAGES");
		ControllerManager.getParentController().getMessageStatsButton().setDisable(false);
		return true;
	}

}
