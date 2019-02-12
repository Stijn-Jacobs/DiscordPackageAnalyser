package me.stijn.discordpackage.controllers;

import java.util.List;

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
import me.stijn.discordpackage.TimeUtils;
import me.stijn.discordpackage.objects.tableview.Conversation;
import me.stijn.discordpackage.objects.tableview.MostUsedWordEntry;

public class MessageStatsController extends AnchorPane {

	@FXML
	public TableView<Conversation> mostusedchats;

	@FXML
	public TableColumn<Conversation, String> mostSentChannelSender;

	@FXML
	public TableColumn<Conversation, Integer> mostSentChannelCount;
	 
	@FXML
	public LineChart daychart;
	
	@FXML
	public CategoryAxis xaxis;
	
	@FXML
	public TableView<MostUsedWordEntry> mostUsedWords;

	@FXML
	public TableColumn<MostUsedWordEntry, String> mostUsedWordsWord;

	@FXML
	public TableColumn<MostUsedWordEntry, Integer> mostUsedWordsCount;;
	
	@FXML
	public void initialize() {
		mostSentChannelSender.setCellValueFactory(new PropertyValueFactory<>("value"));
		mostSentChannelCount.setCellValueFactory(new PropertyValueFactory<>("count"));
		
		mostUsedWordsWord.setCellValueFactory(new PropertyValueFactory<>("value"));
		mostUsedWordsCount.setCellValueFactory(new PropertyValueFactory<>("count"));
		
		mostusedchats.setPlaceholder(new Label("No messages found"));
		mostUsedWords.setPlaceholder(new Label("No words found"));
		xaxis.setCategories(FXCollections.observableArrayList(TimeUtils.getTimeInADay(10)));  
		
	}
	
	public void setMostUsedChats(List<Conversation> l) {
		mostusedchats.getItems().setAll(FXCollections.observableArrayList(l));
		mostSentChannelCount.setSortType(TableColumn.SortType.DESCENDING);
		mostusedchats.getSortOrder().add(mostSentChannelCount);
	}
	
	public void setMostUsedWords(List<MostUsedWordEntry> l) {
		mostUsedWords.getItems().setAll(FXCollections.observableArrayList(l));
		mostUsedWordsCount.setSortType(TableColumn.SortType.DESCENDING);
		mostUsedWords.getSortOrder().add(mostUsedWordsCount);
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
