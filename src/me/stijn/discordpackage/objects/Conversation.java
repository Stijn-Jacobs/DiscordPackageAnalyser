package me.stijn.discordpackage.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Conversation {
	
	SimpleStringProperty sender;
	SimpleIntegerProperty count;
	
	public Conversation(String sender, Integer count) {
		this.sender = new SimpleStringProperty(sender);
		this.count = new SimpleIntegerProperty(count);
	}
	
	public String getSender() {
		return sender.get();
	}

	public void setSender(String sender) {
		this.sender.set(sender);
	}

	public Integer getCount() {
		return count.get();
	}

	public void setCount(Integer count) {
		this.count.set(count);
	}

	@Override
	public String toString() {
		return sender.get() + " : " + count.get();
	}
}
