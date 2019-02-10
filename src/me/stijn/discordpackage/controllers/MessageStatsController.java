package me.stijn.discordpackage.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import me.stijn.discordpackage.DataAnalyser;
import me.stijn.discordpackage.TimeUtils;
import me.stijn.discordpackage.objects.Conversation;

public class MessageStatsController extends AnchorPane {

	@FXML
	public TableView<Conversation> mostusedchats;

	@FXML
	public TableColumn<Conversation, String> sender;

	@FXML
	public TableColumn<Conversation, Integer> count;
	 
	@FXML
	public LineChart daychart;
	
	@FXML
	public CategoryAxis xaxis;

	@FXML
	public void initialize() {
		System.out.println("initi");
		sender.setCellValueFactory(new PropertyValueFactory<>("sender"));
		count.setCellValueFactory(new PropertyValueFactory<>("count"));
		mostusedchats.setPlaceholder(new Label("No messages found"));
		
		xaxis.setCategories(FXCollections.observableArrayList(TimeUtils.getTimeInADay(10)));  
	}
	
	public void addMostUsedChats(ArrayList<Conversation> l) {
		mostusedchats.getItems().addAll(FXCollections.observableArrayList(l));
	}
	
	public void setDayChart(Series s) {
		daychart.getData().setAll(s);
	}

	public TableView getMostUsedChats() {
		return mostusedchats;
	}
	
	public LineChart getDayChart() {
		return daychart;
	}

}
