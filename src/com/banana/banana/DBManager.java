package com.banana.banana;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.content.Intent;

public class DBManager {
	static List<AlarmData> list = new ArrayList<AlarmData>(); 
	
	public static int add(AlarmData data) {
		if (data.id == -1) {
			data.id = list.size();
			list.add(data);
		}
		return data.id;
	}
	
	static AlarmData nearData() {
		AlarmData d = null;
		long currentTime = System.currentTimeMillis();
		for (int i = 0; i < list.size(); i++) {
			AlarmData data = list.get(i);
			if (data.time > currentTime) {
				if (d == null) {
					d = data;
				} else if (d.time > data.time) {
					d = data;
				}
			}
		}
		return d;
	}
	
	
	static void update() {
		AlarmData ad = new AlarmData();
		ad.message = "test alarm";
		
		int hh = 17;
		int mm = 6;
		long time;
		
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		int ch = c.get(Calendar.HOUR_OF_DAY);
		int cm = c.get(Calendar.MINUTE);
		c.set(Calendar.HOUR_OF_DAY, hh);
		c.set(Calendar.MINUTE, mm);
		if (ch < hh || (ch == hh && cm < mm)) {
			time = c.getTimeInMillis();
		} else {
			c.add(Calendar.DAY_OF_YEAR, 1);
			time = c.getTimeInMillis();
		}
		ad.time = time;
		add(ad); 
	}
	
	static List<AlarmData> processData() {
		List<AlarmData> processList = new ArrayList<AlarmData>();
		long currentTime = System.currentTimeMillis();
		for (int i = 0; i < list.size(); i++) {
			AlarmData data = list.get(i);
			if (data.time < currentTime) {
				processList.add(data);
			}
		}
		return processList;
	}
	
	static void setAlarmData(AlarmData data) {
		for (int i = 0; i < list.size(); i++) {
			AlarmData d = list.get(i);
			if (d.id == data.id) {
				d.message = data.message;
				d.time = data.time;
			}
		}
	}
}
