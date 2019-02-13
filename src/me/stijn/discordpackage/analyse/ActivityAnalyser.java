package me.stijn.discordpackage.analyse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import me.stijn.discordpackage.ControllerManager;
import me.stijn.discordpackage.Main;
import me.stijn.discordpackage.TimeUtils;
import me.stijn.discordpackage.Utils;
import me.stijn.discordpackage.objects.activity.ReportingEntry;

public class ActivityAnalyser {
	
	private static List<ReportingEntry> entries = new ArrayList<ReportingEntry>(); //all the entry objects imported from the json files
	
	public static void loadReportings() throws FileNotFoundException, IOException {
		entries.clear();
		ControllerManager.getParentController().getActivityStatsButton().setDisable(true);
		ControllerManager.getParentController().getActionsChartButton().setDisable(true);
		
		File map = new File(Main.PACKAGE_LOCATION + File.separator + "activity" + File.separator + "reporting" + File.separator);
		if (!map.isDirectory()) { // if map does not exists
			ControllerManager.getFileSelectionController().setStatus("NO DISCORD PACKAGE FOUND");
			return;
		}
		
		GsonBuilder gsonb = new GsonBuilder();
		gsonb.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
		Gson gson = gsonb.create();
		
		for (File f : map.listFiles()) {
			try (BufferedReader br = Files.newBufferedReader(f.toPath())) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	ReportingEntry message = gson.fromJson(line, ReportingEntry.class);
			    	entries.add(message);
			    }
			}
		}
	}
	
	
	public static void loadDayChart() {
		if (entries == null) 
			return;
		
		Map<String, Integer> count = new HashMap<>();
		for (String s : TimeUtils.getTimeInADay(10))  // fill temp hashmap
			count.put(s, 0);
		
		for (ReportingEntry d : entries) { // floor dates to 10 minutes
			SimpleDateFormat format = new SimpleDateFormat("HH:mm");
			Date input = TimeUtils.toNearest10Minutes(d.getTimestamp());
			count.put(format.format(input), count.get(format.format(input)) + 1);
		}

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ControllerManager.getActivityStatsController().setDayChart(Utils.hashmapToSeries(count));
			}
		});
	}

	/**
	 * Loads pie 
	 * @param controller Controller on which to insert the pie data into
	 * @param datatype Method which will get the desired data out of the ReportingEntry class instance.
	 * @param chart Method that sets the data to the desired Pie.
	 * @param threshold The value must be higher then this, to be included in the Pie.
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static void loadPie(Pane controller, Method datatype, Method chart, int threshold) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<String, Integer> map = new HashMap<>();
		for (ReportingEntry entry : entries) {
			if (datatype.invoke(entry) == null)
				continue;
			if (map.containsKey(datatype.invoke(entry)))
				map.put((String) datatype.invoke(entry), map.get(datatype.invoke(entry)) + 1);
			else 
				map.put((String) datatype.invoke(entry), 1);
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					chart.invoke(controller, Utils.hashmapToPieChartData(map, threshold));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
}
