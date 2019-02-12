package me.stijn.discordpackage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class GUIManager {

	public static Map<String, Node> map = new HashMap<String, Node>();

	public static void updateGUI() {

	}

	public static void switchView(Pane p) {
		Main.getMainPane().setCenter(p);
	}

	public static Node switchView(String s) throws IOException {
		if (map.containsKey(s)) {
			Main.getMainPane().setCenter(map.get(s));
			return map.get(s);
		}
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("gui/" + s + ".fxml"));
		Node n = loader.load();
		Main.getMainPane().setCenter(n);
		ControllerManager.map.put(s, loader.getController());
		map.put(s, n);
		return n;
	}

}
