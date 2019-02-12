package me.stijn.discordpackage.controllers;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.AnchorPane;

public class ActionsChartController extends AnchorPane {

	@FXML
	public PieChart actionsPie;

	public void setActionPieChart(List<PieChart.Data> s) {
		actionsPie.getData().setAll(s);

		actionsPie.getData().forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " ", (int)data.pieValueProperty().get()))); //put the exact value behind the text
	}

}
