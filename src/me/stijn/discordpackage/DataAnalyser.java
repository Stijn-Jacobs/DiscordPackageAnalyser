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


	public static boolean analyseMessages() throws InterruptedException {
		File map = new File(Main.PACKAGE_LOCATION + "\\messages\\");
		ControllerManager.getParentController().getMessageStatsButton().setDisable(true);
		if (!map.isDirectory()) { // if map does not exists
			ControllerManager.getFileSelectionController().setStatus("NO DISCORD PACKAGE FOUND");
			return false;
		}

		ControllerManager.getFileSelectionController().setStatus("LOADING MESSAGES");

		Thread thrd = new Thread() {
			public void run() {
				try {
					//message analyse
					MessageAnalyser msganalyser = new MessageAnalyser();
					ArrayList<Date> dates = msganalyser.loadMostUsedChats(map);
					msganalyser.loadDayChart(dates);
					msganalyser.loadMostUsedWord();
					
					//activity analyse
					
					
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



}
