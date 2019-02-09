package me.stijn.discordpackage.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import me.stijn.discordpackage.objects.Conversation;

public class MessageStatsController extends AnchorPane {

	@FXML
	public TableView<Conversation> mostusedchats;

	@FXML
	public TableColumn<Conversation, String> sender;

	@FXML
	public TableColumn<Conversation, Integer> count;

	@FXML
	public void initialize() {
		sender.setCellValueFactory(new PropertyValueFactory<>("sender"));
		count.setCellValueFactory(new PropertyValueFactory<>("count"));
	}

	public TableView getMostUsedChats() {
		return mostusedchats;
	}

}
