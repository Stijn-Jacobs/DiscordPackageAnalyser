package me.stijn.discordpackage.analyse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import me.stijn.discordpackage.ControllerManager;
import me.stijn.discordpackage.controllers.ActionsChartController;
import me.stijn.discordpackage.controllers.ActivityStatsController;
import me.stijn.discordpackage.objects.activity.ReportingEntry;

public class DataAnalyser {

	public static boolean analyse() throws InterruptedException {

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

					// activity analyse
					ActivityAnalyser activityanalyser = new ActivityAnalyser();
					activityanalyser.loadReportings();
					activityanalyser.loadDayChart();
					activityanalyser.loadPie(ControllerManager.getActivityStatsController(), ReportingEntry.class.getMethod("getBrowser"), //Type of client chart
							ActivityStatsController.class.getMethod("setClientPieChart", List.class), 10);
					activityanalyser.loadPie(ControllerManager.getActionsChartController(), ReportingEntry.class.getMethod("getEvent_type"), //Action chart page
							ActionsChartController.class.getMethod("setActionPieChart", List.class), 50);

				} catch (IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ParseException e) {
					e.printStackTrace();
				}
			}
		};
		thrd.start();
		thrd.join();

		ControllerManager.getFileSelectionController().setStatus("LOADED DATA");
		ControllerManager.getParentController().getMessageStatsButton().setDisable(false);
		ControllerManager.getParentController().getActivityStatsButton().setDisable(false);
		ControllerManager.getParentController().getActionsChartButton().setDisable(false);
		return true;
	}

}
