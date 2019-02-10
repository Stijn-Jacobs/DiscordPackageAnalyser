package me.stijn.discordpackage.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import me.stijn.discordpackage.GUIManager;

public class ParentView extends BorderPane {

	@FXML
	private Button loadview;

	@FXML
	private Button messagestats;

	@FXML
	public void initialize() {
		messagestats.setDisable(true);
	}

	public void onLoadFileViewButton() throws IOException {
		GUIManager.switchView("FileSelection");
	}

	public void onMessageStatsButton() throws IOException {
		GUIManager.switchView("MessageStats");
	}

	public Button getMessageStatsButton() {
		return messagestats;
	}
}
