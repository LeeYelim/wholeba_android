package com.banana.banana;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    setAlarms(context);
	} 
	
	public static void setAlarms(Context context) {
		int hh = PropertyManager.getInstance().getHour();
		int mm = PropertyManager.getInstance().getMinute();
		long alarmtime;
		boolean alarmSet = false;
		
		
		cancelAlarms(context);
		PendingIntent pIntent = createPendingIntent(context);
		
		Calendar c = Calendar.getInstance(TimeZone.getDefault());
		int ch = c.get(Calendar.HOUR_OF_DAY);
		int cm = c.get(Calendar.MINUTE);
		c.set(Calendar.HOUR_OF_DAY, hh);
		c.set(Calendar.MINUTE, mm); 
		c.set(Calendar.SECOND, 00); 
		
		
		if (ch < hh || (ch == hh && cm < mm)) { // 시간이 내가 설정한 시간보다 작거나, 시간은 같은데 분이 작다면
			alarmtime = c.getTimeInMillis(); // alert time을 내가 셋팅한 시간으로하고 
			setAlarm(context, alarmtime, pIntent);
			alarmSet = true;
		} else {
			c.add(Calendar.DAY_OF_YEAR, 1); // 다음날으로 설정
			alarmtime = c.getTimeInMillis();
			setAlarm(context, alarmtime, pIntent);
			alarmSet = true;
		}  
	}
	
	
	@SuppressLint("NewApi")
	private static void setAlarm(Context context, long time, PendingIntent pIntent) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			alarmManager.setExact(AlarmManager.RTC_WAKEUP, time, pIntent);
		} else {
			alarmManager.set(AlarmManager.RTC_WAKEUP, time, pIntent);
		}
	}
	
	
	public static void cancelAlarms(Context context) {  
		
		AlarmDBHelper dbHelper = new AlarmDBHelper(context);
		List<AlarmModel> alarms = dbHelper.alarms; 
		
		if (alarms != null) {
			for (AlarmModel alarm : alarms) {
				if (alarm.isEnabled) {
					PendingIntent pIntent = createPendingIntent(context);
					AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
					alarmManager.cancel(pIntent);
				}
			}
		}
	}

	private static PendingIntent createPendingIntent(Context context) {
		
		Intent intent = new Intent(context, AlarmService.class); 
		
		if(PropertyManager.getInstance().getUserGender().equals("F")) {
			intent.putExtra("TIME_HOUR", PropertyManager.getInstance().getHour());
			intent.putExtra("TIME_MINUTE", PropertyManager.getInstance().getMinute());
			intent.putExtra("alaR", 1);
		} else {
			
		}
		return PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

}
