package me.stijn.discordpackage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import me.stijn.discordpackage.controllers.ActionsChartController;
import me.stijn.discordpackage.controllers.ActivityStatsController;
import me.stijn.discordpackage.controllers.ConversationOverviewController;
import me.stijn.discordpackage.controllers.FileSelectionController;
import me.stijn.discordpackage.controllers.MessageStatsController;
import me.stijn.discordpackage.controllers.ParentView;

/**
 * ControllerManager stores all the controllers and creates them if they don't exist yet.
 * @author Stijn
 */
public class ControllerManager {

	public static Map<String, Pane> map = new HashMap<>(); //map which contains all the controllers

	/**
	 * Get the FileSelection page controller
	 * @return FileSelection page controller
	 */
	public static FileSelectionController getFileSelectionController() {
		if (map.containsKey("FileSelection"))
			return (FileSelectionController) map.get("FileSelection");
		return (FileSelectionController) addController("FileSelection");
	}

	/**
	 * Get the Parent controller
	 * @return Parent controller
	 */
	public static ParentView getParentController() {
		if (map.containsKey("ParentView"))
			return (ParentView) map.get("ParentView");
		return (ParentView) addController("ParentView");
	}
	
	/**
	 * Get the messagestats controller
	 * @return Messagestats controller
	 */
	public static MessageStatsController getMessageStatsController() {
		if (map.containsKey("MessageStats"))
			return (MessageStatsController) map.get("MessageStats");
		return (MessageStatsController) addController("MessageStats");
	}
	
	/**
	 * Get the activitystats controller
	 * @return activitystats controller
	 */
	public static ActivityStatsController getActivityStatsController() {
		if (map.containsKey("ActivityStats"))
			return (ActivityStatsController) map.get("ActivityStats");
		return (ActivityStatsController) addController("ActivityStats");
	}
	
	/**
	 * Get the Actioncharts page controller
	 * @return Actioncharts page controller
	 */
	public static ActionsChartController getActionsChartController() {
		if (map.containsKey("ActionsChart"))
			return (ActionsChartController) map.get("ActionsChart");
		return (ActionsChartController) addController("ActionsChart");
	}
	
	/**
	 * Get the Conversation overview page controller
	 * @return Conversation overview page controller
	 */
	public static ConversationOverviewController getConversationOverviewController() {
		if (map.containsKey("ConversationOverview"))
			return (ConversationOverviewController) map.get("ConversationOverview");
		return (ConversationOverviewController) addController("ConversationOverview");
	}
	
	
	private static Pane addController(String s) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("gui/" + s + ".fxml"));
		Node n = null;
		try {
			n = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put(s, loader.getController());
		GUIManager.map.put(s, n);
		return loader.getController();
	}

}
