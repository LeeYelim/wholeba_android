package com.banana.banana;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlaramReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			Intent i = new Intent(context, AlarmService.class);  
			context.startService(i);
		}
	}

}
