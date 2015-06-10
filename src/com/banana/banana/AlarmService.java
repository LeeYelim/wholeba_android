package com.banana.banana;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.banana.banana.main.BananaMainActivity;

public class AlarmService extends Service {
	AlarmManager mAM;
	NotificationManager mNM;  
	
	@Override
	public void onCreate() {
		super.onCreate();
		mAM = (AlarmManager)getSystemService(Context.ALARM_SERVICE);  
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	 

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) { //서비스가 시작되면
		 
		int count = PropertyManager.getInstance().getAlarmCount();
		int hh = PropertyManager.getInstance().getHour(); 
		int mm = PropertyManager.getInstance().getMinute(); 
		boolean isOn = PropertyManager.getInstance().getAlarmOnOff();
		long alarmtime;  
		Log.i("AlarmSer2", ""+intent.getIntExtra("isAlaram", -1));
		if(isOn == true) {
			if(count == 0) {
				Calendar c = Calendar.getInstance(TimeZone.getDefault());
				int ch = c.get(Calendar.HOUR_OF_DAY);
				int cm = c.get(Calendar.MINUTE);
				c.set(Calendar.HOUR_OF_DAY, hh);
				c.set(Calendar.MINUTE, mm);
				if (ch < hh || (ch == hh && cm < mm)) { // 시간이 내가 설정한 시간보다 작거나, 시간은 같은데 분이 작다면
					alarmtime = c.getTimeInMillis(); // alert time을 내가 셋팅한 시간으로하고 
				} else {
					c.add(Calendar.DAY_OF_YEAR, 1); // 다음날으로 설정
					alarmtime = c.getTimeInMillis();
				}  
				
				Intent i = new Intent(this, AlarmService.class);
				PendingIntent pi = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
				mAM.set(AlarmManager.RTC, alarmtime, pi);  
				PropertyManager.getInstance().setAlarmCount(1); 
			} else if(count != 0) {
				if(intent.getIntExtra("isAlaram", -1) == 1) {
					Log.i("AlarmSer", ""+intent.getIntExtra("isAlaram", -1));
					noti();
				}
				Calendar c = Calendar.getInstance(TimeZone.getDefault());
				int ch = c.get(Calendar.HOUR_OF_DAY);
				int cm = c.get(Calendar.MINUTE);
				c.set(Calendar.HOUR_OF_DAY, hh);
				c.set(Calendar.MINUTE, mm); 
				if (ch < hh || (ch == hh && cm < mm)) { // 시간이 내가 설정한 시간보다 작거나, 시간은 같은데 분이 작다면
					alarmtime = c.getTimeInMillis(); // alert time을 내가 셋팅한 시간으로하고 
				} else {
					c.add(Calendar.DAY_OF_YEAR, 1); // 다음날으로 설정
					alarmtime = c.getTimeInMillis();
				}  
	
				Intent i = new Intent(this, AlarmService.class);
				i.putExtra("isAlaram", 1);
				PendingIntent pi = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
				mAM.set(AlarmManager.RTC, alarmtime, pi);   
				
			}
		} 
		return Service.START_NOT_STICKY;
			
	}
	
	private void noti() {
		mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE); 
		int mId = 1;
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this); 
		builder.setSmallIcon(R.drawable.magicday_woman_character_);
		builder.setTicker("홀딱 바나나");
		builder.setContentTitle("홀딱 바나나");
		builder.setContentText("약먹을 시간입니다.");
		builder.setAutoCancel(true); 
		
		Intent intent = new Intent(this, BananaMainActivity.class);
		TaskStackBuilder tsb = TaskStackBuilder.create(this);
		tsb.addParentStack(BananaMainActivity.class);
		tsb.addNextIntent(intent);
		PendingIntent pi = tsb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pi);
		
		mNM.notify(mId, builder.build());   
	}
}
