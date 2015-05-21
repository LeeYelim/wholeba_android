package com.banana.banana;

import java.util.List;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.banana.banana.signup.MainActivity;

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
	public int onStartCommand(Intent intent, int flags, int startId) {
		List<AlarmData> list = DBManager.processData();
		
		for (AlarmData d : list) { 
			DBManager.update(); 
			sendNoti();
			/*d.time = d.time + 30 * 1000;
			DBManager.setAlarmData(d);*/
			
		}

		AlarmData near = DBManager.nearData();
		
		Intent i = new Intent(this, AlarmService.class);
		PendingIntent pi = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		mAM.set(AlarmManager.RTC_WAKEUP, near.time, pi);
		
		return Service.START_NOT_STICKY;
	}
	public void sendNoti() {
		// TODO Auto-generated method stub
		mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		int mId = 1;
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.drawable.magicday_woman_character_); 
    	builder.setTicker("my notification...");
		builder.setContentTitle("약 먹을 시간임");
		builder.setContentText("약먹어");
		builder.setAutoCancel(true);
		
		
		
		Intent intent = new Intent(this, MainActivity.class);
		TaskStackBuilder tsb = TaskStackBuilder.create(this);
		tsb.addParentStack(MainActivity.class);
		tsb.addNextIntent(intent);
		PendingIntent pi = tsb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pi);
		
		mNM.notify(mId, builder.build());
		
	
	} 
}
