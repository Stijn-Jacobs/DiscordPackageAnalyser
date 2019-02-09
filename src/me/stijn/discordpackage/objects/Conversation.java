package me.stijn.discordpackage.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Conversation {
	
	SimpleStringProperty sender;
	SimpleIntegerProperty count;
	
	public Conversation(String sender, Integer count) {
		this.sender= new SimpleStringProperty(sender);
		this.count = new SimpleIntegerProperty(count);
	}
	
	public String getSender() {
		return sender.get();
	}
	
	public void setSender(String sender) {
		this.sender.set(sender);;
	}

	public void setCount(int count) {
		this.count.set(count);
	}

	public int getCount() {
		return count.get();
	}

}
