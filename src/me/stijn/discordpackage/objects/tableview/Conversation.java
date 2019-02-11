package me.stijn.discordpackage.objects.tableview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Conversation extends TableViewEntry{
	
	public Conversation(String sender, Integer count) {
		super(sender, count);
	}

}
