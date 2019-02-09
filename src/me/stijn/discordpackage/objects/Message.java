package me.stijn.discordpackage.objects;

import java.util.Date;

public class Message {

	String msg;
	Date timestamp;
	
	public Message(String msg, Date timestamp) {
		this.msg = msg;
		this.timestamp = timestamp;
	}

	public String getMsg() {
		return msg;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	
}
