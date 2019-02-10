package me.stijn.discordpackage.controllers;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import me.stijn.discordpackage.DataAnalyser;
import me.stijn.discordpackage.Main;

public class FileSelectionController extends AnchorPane {
	@FXML
	private Label status;

	@FXML
	private TextField locationurl;

	@FXML
	public void initialize() {
		if (Main.PACKAGE_LOCATION != null && Main.PACKAGE_LOCATION != "") {
			locationurl.setText(Main.PACKAGE_LOCATION);
		}
	}

	public void onLoadButton() throws IOException, ParseException, InterruptedException {
		if (!DataAnalyser.analyseMessages())
			return;

	}

	public void onFileOpenButton() {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("Select Discord data package location");
		File selected = chooser.showDialog(null);
		if (selected != null) {
			Main.PACKAGE_LOCATION = selected.getAbsolutePath();
			locationurl.setText(Main.PACKAGE_LOCATION);
		}
	}

	public void setStatus(String s) {
		s = s.replace(" ", "   ");
		status.setText("Status: " + s);
	}

}
