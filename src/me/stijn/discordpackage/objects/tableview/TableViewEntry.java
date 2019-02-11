package me.stijn.discordpackage.objects.tableview;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class TableViewEntry {
	
	SimpleStringProperty value;
	SimpleIntegerProperty count;
	
	public TableViewEntry(String value, Integer count) {
		this.value = new SimpleStringProperty(value);
		this.count = new SimpleIntegerProperty(count);
	}
	
	public String getValue() {
		return value.get();
	}

	public void setValue(String sender) {
		this.value.set(sender);
	}

	public Integer getCount() {
		return count.get();
	}

	public void setCount(Integer count) {
		this.count.set(count);
	}

}
