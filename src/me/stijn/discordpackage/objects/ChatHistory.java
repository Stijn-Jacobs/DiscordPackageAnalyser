package me.stijn.discordpackage.objects;

import java.util.ArrayList;

public class ChatHistory {
	
	Channel channel;
	ArrayList<Message> list;
	
	public ChatHistory(Channel c, ArrayList<Message> list) {
		this.channel = c;
		this.list = list;
	}

	public Channel getChannel() {
		return channel;
	}

	public ArrayList<Message> getList() {
		return list;
	}

}
