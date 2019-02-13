package me.stijn.discordpackage.analyse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javafx.application.Platform;
import me.stijn.discordpackage.ControllerManager;
import me.stijn.discordpackage.Main;
import me.stijn.discordpackage.Utils;
import me.stijn.discordpackage.controllers.FileSelectionController;
import me.stijn.discordpackage.controllers.ParentView;
import me.stijn.discordpackage.objects.activity.ReportingEntry;

public class DataAnalyser {

	public static boolean analyse() throws InterruptedException {

		File map = new File(Main.PACKAGE_LOCATION);

		if (!map.isDirectory())
			return false;

		beforeAnalyse();

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
					msganalyser.clear();

					// activity analyse
					ActivityAnalyser activityanalyser = new ActivityAnalyser();
					activityanalyser.loadReportings();
					activityanalyser.loadDayChart();
					activityanalyser.loadPie(ReportingEntry.class.getMethod("getBrowser"), 10, ControllerManager.getActivityStatsController().clientPie); // client chart
					activityanalyser.loadPie(ReportingEntry.class.getMethod("getOs"), 0, ControllerManager.getActivityStatsController().osPie); // Os usage chart
					activityanalyser.loadPie(ReportingEntry.class.getMethod("getEvent_type"), 50, ControllerManager.getActionsChartController().actionsPie); // Action chart page
					activityanalyser.clear();

					afterAnalyse();
				} catch (IOException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ParseException e) {
					e.printStackTrace();
				}
			}
		};
		thrd.start();
		return true;
	}

	private static void afterAnalyse() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ControllerManager.getFileSelectionController().setStatus("LOADED DATA");

				ParentView contr = ControllerManager.getParentController();
				contr.getMessageStatsButton().setDisable(false);
				contr.getActivityStatsButton().setDisable(false);
				contr.getActionsChartButton().setDisable(false);
				contr.getConversationOverviewButton().setDisable(false);
			}
		});
	}
	
	private static void beforeAnalyse() {
		FileSelectionController filectrl = ControllerManager.getFileSelectionController();
		filectrl.getFileSizeLabel().setText("Discord keeps more than " + Utils.getFolderSize(new File(Main.PACKAGE_LOCATION)) / 1024 / 1024 + " mb of plain text data of you.");
		filectrl.getFileSizeLabel().setVisible(true);
		filectrl.setStatus("LOADING DATA");
		
		ParentView parentctrl = ControllerManager.getParentController();
		parentctrl.getActivityStatsButton().setDisable(true);
		parentctrl.getActionsChartButton().setDisable(true);
		parentctrl.getMessageStatsButton().setDisable(true);
		parentctrl.getConversationOverviewButton().setDisable(true);
		
	}

}
