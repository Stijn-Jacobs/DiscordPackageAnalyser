package me.stijn.discordpackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.binding.Bindings;
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
	public static List<PieChart.Data> hashmapToPieChartData(Map<String, Integer> map, int treshold, int max){
		List<PieChart.Data> list = new ArrayList<>();
		for (String d : map.keySet()) {
			if (map.get(d) > treshold) {
				list.add(new PieChart.Data(d, map.get(d)));
				if (list.size() - 1 >= max && max != 0) { //remove lowest item if more then max
					double minlist = Integer.MAX_VALUE;
					PieChart.Data mindata = null;
					for (PieChart.Data dat : list) {
						if (dat.getPieValue() < minlist) {
							minlist = dat.getPieValue();
							mindata = dat;
						}
					}
					list.remove(mindata);
				}
			}
		}
		return list;
	}
	
	/**
	 * Gets the size of a folder
	 * @param folder Folder
	 * @return size of the folder
	 */
	public static long getFolderSize(File folder) {
	    long length = 0;
	    File[] files = folder.listFiles();
	    int count = files.length;
	    for (int i = 0; i < count; i++) {
	        if (files[i].isFile()) {
	            length += files[i].length();
	        }
	        else {
	            length += getFolderSize(files[i]);
	        }
	    }
	    return length;
	}
	
	/**
	 * Sets the PieChart to given data.
	 * @param s Data to set
	 * @param c PieChart to set data to
	 */
	public static void setPieChart(List<PieChart.Data> s, PieChart c) {
		c.getData().setAll(s);
		c.getData().forEach(data -> data.nameProperty().bind(Bindings.concat(data.getName(), " - ", (int)data.pieValueProperty().get()))); //put the exact value behind the text
	}

}
