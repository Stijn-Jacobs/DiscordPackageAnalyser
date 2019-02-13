package me.stijn.discordpackage.analyse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import me.stijn.discordpackage.ControllerManager;
import me.stijn.discordpackage.Main;
import me.stijn.discordpackage.Utils;
import me.stijn.discordpackage.controllers.ParentView;
import me.stijn.discordpackage.objects.activity.ReportingEntry;

public class DataAnalyser {

	public static boolean analyse() throws InterruptedException {
		
		File map = new File(Main.PACKAGE_LOCATION);
		
		if (!map.isDirectory())
			return false;
		
		ControllerManager.getFileSelectionController().getFileSizeLabel().setText("Discord keeps more than " + Utils.getFolderSize(map) / 1024 / 1024 + " mb of plain text data of you.");
		ControllerManager.getFileSelectionController().getFileSizeLabel().setVisible(true);
		
		ControllerManager.getFileSelectionController().setStatus("LOADING DATA");

		final Thread thrd = new Thread() {
			public void run() {
				try {
					// message analyse
					MessageAnalyser msganalyser = new MessageAnalyser();
					List<Date> dates = msganalyser.loadMostUsedChats();
					if (dates == null)
						return;
					msganalyser.loadDayChart(dates);
					msganalyser.loadMostUsedWord();
					msganalyser.loadConversationOverview();

					// activity analyse
					ActivityAnalyser activityanalyser = new ActivityAnalyser();
					activityanalyser.loadReportings();
					activityanalyser.loadDayChart();
					activityanalyser.loadPie(ReportingEntry.class.getMethod("getBrowser"), 10, ControllerManager.getActivityStatsController().clientPie); //client chart
					activityanalyser.loadPie(ReportingEntry.class.getMethod("getOs"), 0, ControllerManager.getActivityStatsController().osPie); //Os usage chart
					activityanalyser.loadPie(ReportingEntry.class.getMethod("getEvent_type"), 50, ControllerManager.getActionsChartController().actionsPie); //Action chart page
					
				} catch (IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ParseException e) {
					e.printStackTrace();
				}
			}
		};
		thrd.start();
		thrd.join();

		ControllerManager.getFileSelectionController().setStatus("LOADED DATA");
		
		ParentView contr = ControllerManager.getParentController();
		contr.getMessageStatsButton().setDisable(false);
		contr.getActivityStatsButton().setDisable(false);
		contr.getActionsChartButton().setDisable(false);
		contr.getConversationOverviewButton().setDisable(false);
		return true;
	}

}
