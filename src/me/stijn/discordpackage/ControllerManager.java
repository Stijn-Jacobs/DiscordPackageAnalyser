package me.stijn.discordpackage;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import me.stijn.discordpackage.controllers.FileSelectionController;
import me.stijn.discordpackage.controllers.MessageStatsController;
import me.stijn.discordpackage.controllers.ParentView;

public class ControllerManager {

	public static HashMap<String, Pane> map = new HashMap<String, Pane>();

	public static FileSelectionController getFileSelectionController() {
		if (map.containsKey("FileSelection"))
			return (FileSelectionController) map.get("FileSelection");
		return (FileSelectionController) addController("FileSelection");
	}

	public static ParentView getParentController() {
		if (map.containsKey("ParentView"))
			return (ParentView) map.get("ParentView");
		return (ParentView) addController("ParentView");
	}
	
	public static MessageStatsController getMessageStatsController() {
		if (map.containsKey("MessageStats"))
			return (MessageStatsController) map.get("MessageStats");
		return (MessageStatsController) addController("MessageStats");
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
