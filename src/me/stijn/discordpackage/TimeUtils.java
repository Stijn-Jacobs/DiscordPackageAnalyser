package me.stijn.discordpackage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtils {
	
	public static ArrayList<String> getTimeInADay(int interval){
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < 24; i++) {
			for (int j = 0; j < 60; j++) {
				if ((j%interval)==0) {
					String hours;
					if (i < 10)
						hours = "0" + i;
					else 
						hours = "" + i;
					
					String minutes;
					if (j == 0)
						minutes = j + "0";
					else 
						minutes = "" + j;
					list.add(hours + ":" + minutes);
				}
			}
		}
		return list;
	}

	public static Date toNearest10Minutes(Date d) {
        Calendar c = new GregorianCalendar();
        c.setTime(d);

        c.set(Calendar.MINUTE, ((c.get(Calendar.MINUTE)+5)/10)*10);
        
        return c.getTime();
    }
	
}
