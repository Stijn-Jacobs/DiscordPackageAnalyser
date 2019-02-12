package me.stijn.discordpackage.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import me.stijn.discordpackage.ControllerManager;
import me.stijn.discordpackage.GUIManager;

public class ParentView extends BorderPane {

	@FXML
	private Button loadView; //load file tab

	@FXML
	private Button messageStats; //message stats tab
	
	@FXML
	private Button activityStats; //activity stats tab
	
	@FXML
	private Button actions; //action chart tab

	@FXML
	public void initialize() {
		messageStats.setDisable(true);
		activityStats.setDisable(true);
		actions.setDisable(true);
	}

	public void onLoadFileViewButton() throws IOException {
		GUIManager.switchView("FileSelection");
	}

	public void onMessageStatsButton() throws IOException {
		GUIManager.switchView("MessageStats");
	}
	
	public void onActivityStatsButton() throws IOException {
		GUIManager.switchView("ActivityStats");
	}
	
	public void onActionsChartButton() throws IOException {
		GUIManager.switchView("ActionsChart");
	}

	public Button getMessageStatsButton() {
		return messageStats;
	}
	
	public Button getActivityStatsButton() {
		return activityStats;
	}
	
	public Button getActionsChartButton() {
		return actions;
	}
}
