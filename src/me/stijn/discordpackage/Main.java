package me.stijn.discordpackage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
		  Font.loadFont(Main.class.getResource("font/BEBAS.ttf").toExternalForm(), 10);
		  stage.setScene(sc);
	      GUIManager.switchView("FileSelection");
	      stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch();
	}
	
	public static BorderPane getMainPane() {
		return main;
	}
	
}
