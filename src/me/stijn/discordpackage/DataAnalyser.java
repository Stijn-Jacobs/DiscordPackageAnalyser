package me.stijn.discordpackage;

import java.io.File;

import me.stijn.discordpackage.controllers.FileSelectionController;
import me.stijn.discordpackage.controllers.ParentController;

public class DataAnalyser {
	


	public static boolean analyseMessages() {
		File map = new File(Main.PACKAGE_LOCATION + "\\messages\\");
		
		if (!map.isDirectory()) {
			ControllerManager.getFileSelectionController().setStatus("NO DISCORD PACKAGE FOUND");
			ControllerManager.getParentController().getMessageStatsButton().setDisable(true);
			return false;
		}
		//process
		for (File f : map.listFiles()) {
			System.out.println("Name: " + f.getName());
		}
		
		
		ControllerManager.getFileSelectionController().setStatus("LOADED MESSAGES");
		ControllerManager.getParentController().getMessageStatsButton().setDisable(false);
		return true;
	}
	
	
}
