package me.stijn.discordpackage;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GUIManager {
	
	public static void updateGUI() {
		
	}
	
	
	
	public static void switchView(Pane p) {
		Main.getMainPane().setCenter(p);
	}
	
	public static void switchView(String s) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("gui/" + s + ".fxml"));
		Main.getMainPane().setCenter(loader.load());
		ControllerManager.map.put(loader.getController().getClass().getSimpleName(), loader.getController());
	}

}
