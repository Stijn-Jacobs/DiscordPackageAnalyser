package me.stijn.discordpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

public class Utils {
	/**
	 * Converts the given map to XYChart series, to put in a XYChart.
	 * @param map Map with String, Integers to convert.
	 * @return Series of XYChart Data.
	 */
	public static Series hashmapToSeries(Map<String, Integer> map) {
		XYChart.Series series = new XYChart.Series();
		for (String d : map.keySet()) {
			series.getData().add(new XYChart.Data(d, map.get(d)));
		}
		return series;
	}
	
	/**
	 * Converts Map to PieChart input.
	 * @param map Input map with String, Integers to convert.
	 * @param treshold The value of the map must be higher then the treshold to be put in the return list.
	 * @return list of PieChart Data.
	 */
	public static List<PieChart.Data> hashmapToPieChartData(Map<String, Integer> map, int treshold){
		List<PieChart.Data> list = new ArrayList<>();
		for (String d : map.keySet()) {
			if (map.get(d) > treshold)
				list.add(new PieChart.Data(d, map.get(d)));
		}
		return list;
	}

}
