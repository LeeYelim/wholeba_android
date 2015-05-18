package com.banana.banana;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;




import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.banana.banana.love.LovePopupActivity;
import com.banana.banana.main.BananaMainActivity;
import com.banana.banana.mission.MissionActivity;
import com.banana.banana.mission.MissionReceiveConfirmActivity;
import com.banana.banana.mission.scratch.MissionCardScratchActivity;
import com.banana.banana.signup.CoupleResponseFragment;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmIntentService extends IntentService {
	private static final String TAG="GcmIntengService";
	public static final int NOTIFICATION_ID = 1;
	public static final String PUSH_TYPE="type";
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    int theme;
    String themeName;
    int chip_count=0;
    String push_type;
    int push_type_num;
    int phone_number;
    String mlist_regdate,item_usedate;
    String partner_phone,item_name;
    int mlist_no,theme_no,item_no;
    
    
    String mission_name;
   
    String mission_hint;
    public GcmIntentService(){
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        Intent send_intent=intent;
        
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) { 
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {

            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
            
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            	push_type=intent.getStringExtra(PUSH_TYPE);
            	push_type_num=(int) Integer.parseInt(push_type);
            	if(push_type_num!=8){
            		sendNotification(push_type_num,send_intent);
            	}else {
            		chip_count=Integer.parseInt(intent.getStringExtra("reward_cnt"));
            		Log.i("reward_cnt",""+ chip_count);
            		PropertyManager.getInstance().setChipCount(chip_count);
            	}
            }
        }
      
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

 
    private void sendNotification(int push_type,Intent send_intent) {
    	
    	  
    	 Intent intent=null;
    	  
    	  if(push_type==1){
    		  intent =new Intent(this,LogoutActivity.class);
    		  	
    		  
    	  }else if(push_type==2){
    		  
    		  partner_phone=send_intent.getStringExtra("partner_phone");
    		  intent =new Intent(this,CoupleResponseFragment.class);
    		  intent.putExtra("partner", partner_phone);
    		
    	  }else if(push_type==3){
    		  
    	  }else if(push_type==4){
    		 
    		  intent=new Intent(this,LovePopupActivity.class);
    		  
    		  //intent=new Intent(this,MissionPopupActivity.class);
    	  }else if(push_type==5){
    		  mlist_no=Integer.parseInt(send_intent.getStringExtra("mlist_no"));
    		  mission_name=send_intent.getStringExtra("mission_name");
    		  theme_no=Integer.parseInt(send_intent.getStringExtra("theme_no"));
    		  mlist_regdate=send_intent.getStringExtra("mlist_regdate");
    		 
    		  Log.i("mlist_no",""+mlist_no);
    		  Log.i("mission_name",mission_name);
    		  Log.i("theme_no",""+theme_no);
    		  Log.i("mlist_regdate",mlist_regdate);
    		  intent=new Intent(this,MissionCardScratchActivity.class);
    		  intent.putExtra("mlist_no",mlist_no);
    		  intent.putExtra("mission_name",mission_name);
    		  intent.putExtra("theme_no",theme_no);
    		  intent.putExtra("mlist_regdate",mlist_regdate);
    		  intent.putExtra("item_no", item_no);
    		  
    		  
    	  }else if(push_type==6){
    		  mission_hint=send_intent.getStringExtra("mission_hint");
    		  Log.i("mission_hint",mission_hint);
    		  intent=new Intent(this,MissionReceiveConfirmActivity.class);
    		  intent.putExtra("mission_hint", mission_hint);
    		
    	  }else if(push_type==7){
    		  item_no=Integer.parseInt(send_intent.getStringExtra("item_no"));
    		  item_usedate=send_intent.getStringExtra("item_usedate");
    		  theme_no=Integer.parseInt(send_intent.getStringExtra("theme_no"));
    		  item_name=send_intent.getStringExtra("item_name");
    		  
    		  if(item_no==1||item_no==3||item_no==4||item_no==5||item_no==6||item_no==7){
    			  mission_hint=send_intent.getStringExtra("mission_hint");
    		  }
    		//  item_no=send_intent.g
    	  }else if(push_type==9){
    		  intent=new Intent(this,WithDrawActivity.class);
    	  }
    	  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                  intent, PendingIntent.FLAG_UPDATE_CURRENT);
          NotificationCompat.Builder mBuilder =
                  new NotificationCompat.Builder(this)
          .setSmallIcon(R.drawable.ic_launcher)
          .setContentTitle("GCM Notification")
          .setStyle(new NotificationCompat.BigTextStyle()
          .bigText(""))
          .setContentText("");
          mNotificationManager = (NotificationManager)
                  this.getSystemService(Context.NOTIFICATION_SERVICE);
          mBuilder.setContentIntent(contentIntent);
          mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
