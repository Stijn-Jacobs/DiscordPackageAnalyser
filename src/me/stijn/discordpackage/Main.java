package me.stijn.discordpackage;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import me.stijn.discordpackage.controllers.ParentView;

public class Main extends Application{
	
	public static String PACKAGE_LOCATION = "C:\\Users\\Stijn\\Desktop\\package";
	
	private static BorderPane main;
	
	@Override
	public void start(Stage stage) throws Exception {
		  stage.setTitle("Discord Data Package Analyser - By Deflopper");
		  stage.setResizable(false);
		  FXMLLoader loader = new FXMLLoader();
		  loader.setLocation(Main.class.getResource("gui/ParentView.fxml"));
		  main = loader.load();
		  ControllerManager.map.put(loader.getController().getClass().getSimpleName(), loader.getController());
		  Scene sc = new Scene(main);
		  sc.getStylesheets().add("me/stijn/discordpackage/gui/css/main.css");
		  stage.setScene(sc);
	      GUIManager.switchView("FileSelection");
	      stage.show();

	}

	public static void main(String[] args) {
		Application.launch();
		//DataAnalyser.analyseMessages();
	}
	
//	public static void debug(String msg) {
//		System.out.println(msg);
//	}

	public static BorderPane getMainPane() {
		return main;
	}
	
}
