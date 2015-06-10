package com.banana.banana;



import java.util.ArrayList;
import java.util.List;

import android.content.Context;
 
public class AlarmDBHelper {
	
	public static List<AlarmModel> alarms = new ArrayList<AlarmModel>();
	
	public AlarmDBHelper(Context context) {
		// TODO Auto-generated constructor stub
	}

	public static int createAlarm(AlarmModel data) {
		if (data.id == -1) {
			data.id = alarms.size();
			alarms.add(data);
		}
		return data.id;
	}
	
	public static int updateAlarm(AlarmModel data, String name) {
		for(int i = 0; i<=alarms.size(); i++) {
			if((data.NAME).equals(name)) {
				alarms.get(i).hour = data.hour;
				alarms.get(i).minute = data.minute;
			}
		}
		return data.id;
	}
}

