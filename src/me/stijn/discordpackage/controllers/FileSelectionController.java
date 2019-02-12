package me.stijn.discordpackage.controllers;

import java.io.File;

import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import me.stijn.discordpackage.Main;
import me.stijn.discordpackage.analyse.DataAnalyser;

public class FileSelectionController extends AnchorPane {
	
	private File expletiveFile;
	
	@FXML
	private Label status;

	@FXML
	private TextField locationurl;
	
	@FXML
	private Button customExpletiveButton;

	@FXML
	private CheckBox useCustomExpletive;
	
	@FXML
	public void initialize() {
		if (Main.PACKAGE_LOCATION != null && Main.PACKAGE_LOCATION != "") {
			locationurl.setText(Main.PACKAGE_LOCATION);
		}
	}

	public void onLoadButton() throws InterruptedException {
		if (expletiveFile == null) { //disable custom expletive if no file has been set
			customExpletiveButton.setDisable(true);
			useCustomExpletive.setSelected(false);
		}
		if (!DataAnalyser.analyse())
			return;
	}
	
	public void onCustomExpletiveCheck() {
		customExpletiveButton.setDisable(!useCustomExpletive.isSelected());
	}
	
	public void onCustomExpletiveButton() { //select cusetom expletive file
		if (!useCustomExpletive.isSelected()) 
			return;
		
		FileChooser chooser = new FileChooser();
		chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		File selected = chooser.showOpenDialog(null);
		if (selected != null) {
			expletiveFile = selected;
		}
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
	
	public File getExpletiveFile() {
		return expletiveFile;
	}

}
