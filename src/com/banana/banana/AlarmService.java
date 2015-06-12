package com.banana.banana;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.banana.banana.main.BananaMainActivity;

public class AlarmService extends Service {
	NotificationManager nNM;
	public static String TAG = AlarmService.class.getSimpleName();
	int Snoti;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Snoti = intent.getIntExtra("alaR", -1);
		Log.i("alaR", ""+Snoti);
		
		if(PropertyManager.getInstance().getAlarmOnOff() == true && Snoti == 1) {
			noti();  
			Log.i("NOTINOTI", "NOTI");
			AlarmReceiver.setAlarms(this);
		}
		return Service.START_NOT_STICKY;
	}

	private void noti() {
		nNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE); 
		int mId = 1;
		Log.i("NOTINOTI2", "NOTI");
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
		
		nNM.notify(mId, builder.build());  
		Snoti = 0;
	} 

}
