package me.stijn.discordpackage;

import java.util.HashMap;

import javafx.scene.layout.Pane;
import me.stijn.discordpackage.controllers.FileSelectionController;
import me.stijn.discordpackage.controllers.ParentController;

public class ControllerManager {
		
	public static HashMap<String, Pane> map = new HashMap<String, Pane>();

	public static FileSelectionController getFileSelectionController() {
		if (map.containsKey("FileSelectionController"))
			return (FileSelectionController) map.get("FileSelectionController");
		return null;
	}
	
	public static ParentController getParentController() {
		if (map.containsKey("ParentController"))
			return (ParentController) map.get("ParentController");
		return null;
	}
	

}
