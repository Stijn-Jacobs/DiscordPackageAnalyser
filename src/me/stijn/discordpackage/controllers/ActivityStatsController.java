package me.stijn.discordpackage.controllers;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.AnchorPane;
import me.stijn.discordpackage.TimeUtils;

public class ActivityStatsController extends AnchorPane {
	 
	@FXML
	public LineChart dayChart;
	
	@FXML
	public CategoryAxis xaxis;
	
	@FXML
	public PieChart clientPie;
	
	@FXML
	public PieChart osPie;
	
	@FXML
	public void initialize() {
		xaxis.setCategories(FXCollections.observableArrayList(TimeUtils.getTimeInADay(10)));  
	}

	public void setDayChart(Series s) {
		dayChart.getData().setAll(s);
	}
	
//	public void setClientPieChart(List<PieChart.Data> s) {
//		clientPie.getData().setAll(s);
//		clientPie.getData().forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " ", (int)data.pieValueProperty().get()))); //put the exact value behind the text
//		
//	}
//	
//	public void setOsPieChart(List<PieChart.Data> s) {
//		osPie.getData().setAll(s);
//		osPie.getData().forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " ", (int)data.pieValueProperty().get()))); //put the exact value behind the text
//	}
	
	
}
