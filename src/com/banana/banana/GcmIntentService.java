package com.banana.banana;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.banana.banana.love.LovePopupActivity;
import com.banana.banana.love.PopupOk;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.mission.MissionPopupActivity;
import com.banana.banana.mission.MissionReceiveConfirmActivity;
import com.banana.banana.mission.scratch.MissionCardScratchActivity;
import com.banana.banana.signup.CoupleRequestActivity;
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
    int mlist_no,theme_no,item_no, loves_no;
    
    
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
            	if(push_type == null) {
            		
            	} else {
            		push_type_num=(int)Integer.parseInt(push_type);
            	}
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

 
    Handler mHandler = new Handler(Looper.getMainLooper());
    private void sendNotification(int push_type,Intent send_intent) {
    	
    	  
    	 Intent intent=null;
    	  
    	  if(push_type==1){ //다른 기기 로그인 
    		  intent =new Intent(this,SplashActivity.class);
    		  mHandler.post(new Runnable() {
				
				@Override
				public void run() {
		    		  NetworkManager.getInstnace().logout(GcmIntentService.this, new OnResultListener<LogoutResponse>() {
		  				
		  				@Override
		  				public void onSuccess(LogoutResponse result) {
		  					// TODO Auto-generated method stub
		  					PropertyManager.getInstance().setUserId("");
		  					PropertyManager.getInstance().setPassword("");
		  				}
		  				
		  				@Override
		  				public void onFail(int code) {
		  					// TODO Auto-generated method stub
		  					
		  				}
		  			});
				}
			});
    		  
    		  /*------gcm-----*/
    		  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                      intent, PendingIntent.FLAG_UPDATE_CURRENT);
              NotificationCompat.Builder mBuilder =
                      new NotificationCompat.Builder(this)
              .setSmallIcon(R.drawable.magicday_woman_character_)
              .setContentTitle("홀딱바나나") 
              .setAutoCancel(true)
              .setContentText("다른기기에서 같은 아이디로 로그인하였습니다.");
              mNotificationManager = (NotificationManager)
                      this.getSystemService(Context.NOTIFICATION_SERVICE);
              mBuilder.setContentIntent(contentIntent);
              mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    		  
    	  }else if(push_type==2){ //커플요청 - 상대방 번호, partner_phone 
    		  partner_phone=send_intent.getStringExtra("partner_phone");
    		  intent =new Intent(this,SplashActivity.class); 
    		  intent.putExtra("partner", partner_phone); 
    		  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    		  
    		  /*------gcm-----*/
    		  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                      intent, PendingIntent.FLAG_UPDATE_CURRENT);
              NotificationCompat.Builder mBuilder =
                      new NotificationCompat.Builder(this)
              .setSmallIcon(R.drawable.magicday_woman_character_)
              .setContentTitle("홀딱바나나") 
              .setAutoCancel(true)
              .setContentText("커플요청이 도착하였습니다.");
              mNotificationManager = (NotificationManager)
                      this.getSystemService(Context.NOTIFICATION_SERVICE);
              mBuilder.setContentIntent(contentIntent);
              mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    		  
    	  }else if(push_type==3){ //커플승인
    		  intent =new Intent(this,SplashActivity.class); 
    		  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		  
    		  /*------gcm-----*/
    		  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                      intent, PendingIntent.FLAG_UPDATE_CURRENT);
              NotificationCompat.Builder mBuilder =
                      new NotificationCompat.Builder(this)
              .setSmallIcon(R.drawable.magicday_woman_character_)
              .setContentTitle("홀딱바나나") 
              .setAutoCancel(true)
              .setContentText("커플이 승인되었습니다.");
              mNotificationManager = (NotificationManager)
                      this.getSystemService(Context.NOTIFICATION_SERVICE);
              mBuilder.setContentIntent(contentIntent);
              mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build()); 
    		  
    	  }else if(push_type==4){ // 뽀뽀팝업 요청하기  
    		  
    		  if(!send_intent.getStringExtra("mlist_no").equals("-1")) {
    			  	mlist_no = Integer.parseInt(send_intent.getStringExtra("mlist_no"));
    			  	intent=new Intent(this, PopupOk.class);
    			  	intent.putExtra("mlist_no", mlist_no);
      		    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
      		    	
      		      /*------gcm-----*/
          		  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.magicday_woman_character_)
                    .setContentTitle("홀딱바나나") 
                    .setAutoCancel(true)
                    .setContentText("상대방이 미션팝업을 요청하였습니다.");
                    mNotificationManager = (NotificationManager)
                            this.getSystemService(Context.NOTIFICATION_SERVICE);
                    mBuilder.setContentIntent(contentIntent);
                    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
      		    	
    		  } else if(!send_intent.getStringExtra("loves_no").equals("-1")) {
    			  loves_no=Integer.parseInt(send_intent.getStringExtra("loves_no")); 
    			  intent=new Intent(this,PopupOk.class);
    			  intent.putExtra("loves_no", loves_no);
    			  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    			  
    			  /*------gcm-----*/
        		  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                          intent, PendingIntent.FLAG_UPDATE_CURRENT);
                  NotificationCompat.Builder mBuilder =
                          new NotificationCompat.Builder(this)
                  .setSmallIcon(R.drawable.magicday_woman_character_)
                  .setContentTitle("홀딱바나나") 
                  .setAutoCancel(true)
                  .setContentText("상대방이 뽀뽀팝업을 요청하였습니다.");
                  mNotificationManager = (NotificationManager)
                          this.getSystemService(Context.NOTIFICATION_SERVICE);
                  mBuilder.setContentIntent(contentIntent);
                  mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    			  
    			  
    		  } 
    	}else if(push_type==5){ //미션 생성
    		  mlist_no=Integer.parseInt(send_intent.getStringExtra("mlist_no"));
    		  mission_name=send_intent.getStringExtra("mission_name");
    		  theme_no=Integer.parseInt(send_intent.getStringExtra("theme_no"));
    		  mlist_regdate=send_intent.getStringExtra("mlist_regdate");
    		  
    		  intent=new Intent(this,MissionCardScratchActivity.class);
    		  intent.putExtra("mlist_no",mlist_no);
    		  intent.putExtra("mission_name",mission_name);
    		  intent.putExtra("theme_no",theme_no);
    		  intent.putExtra("mlist_regdate",mlist_regdate);
    		  intent.putExtra("item_no", item_no);
    		  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    		  
    		  /*------gcm-----*/
    		  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                      intent, PendingIntent.FLAG_UPDATE_CURRENT);
              NotificationCompat.Builder mBuilder =
                      new NotificationCompat.Builder(this)
              .setSmallIcon(R.drawable.magicday_woman_character_)
              .setContentTitle("홀딱바나나") 
              .setAutoCancel(true)
              .setContentText("상대방으로 부터 미션이 도착하였습니다.");
              mNotificationManager = (NotificationManager)
                      this.getSystemService(Context.NOTIFICATION_SERVICE);
              mBuilder.setContentIntent(contentIntent);
              mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    		  
    		  
    	  } else if(push_type==6){ // 미션 확인
    		  mission_hint=send_intent.getStringExtra("mission_hint");
    		  Log.i("mission_hint",mission_hint);
    		  intent=new Intent(this,MissionReceiveConfirmActivity.class);
    		  intent.putExtra("mission_hint", mission_hint);
    		  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    		  
    		  /*------gcm-----*/
    		  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                      intent, PendingIntent.FLAG_UPDATE_CURRENT);
              NotificationCompat.Builder mBuilder =
                      new NotificationCompat.Builder(this)
              .setSmallIcon(R.drawable.magicday_woman_character_)
              .setContentTitle("홀딱바나나") 
              .setAutoCancel(true)
              .setContentText("상대방이 미션을 확인하였습니다.");
              mNotificationManager = (NotificationManager)
                      this.getSystemService(Context.NOTIFICATION_SERVICE);
              mBuilder.setContentIntent(contentIntent);
              mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    		
    	  }else if(push_type==7){ // 미션에 아이템 사용 
    		  item_no=Integer.parseInt(send_intent.getStringExtra("item_no"));
    		  item_usedate=send_intent.getStringExtra("item_usedate");
    		  theme_no=Integer.parseInt(send_intent.getStringExtra("theme_no"));
    		  item_name=send_intent.getStringExtra("item_name");
    		  
    		  if(item_no==1||item_no==3||item_no==4||item_no==5||item_no==6||item_no==7){
    			  mission_hint=send_intent.getStringExtra("mission_hint");
    		  }  
    		  
    		  intent=new Intent(this,MissionItemUsePushActivity.class);
    		  intent.putExtra("theme_no", theme_no);
    		  intent.putExtra("item_name", item_name);
    		  intent.putExtra("item_usedate", item_usedate);
    		  intent.putExtra("mission_hint", mission_hint);
    		  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    		  
    		  /*------gcm-----*/
    		  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                      intent, PendingIntent.FLAG_UPDATE_CURRENT);
              NotificationCompat.Builder mBuilder =
                      new NotificationCompat.Builder(this)
              .setSmallIcon(R.drawable.magicday_woman_character_)
              .setContentTitle("홀딱바나나") 
              .setAutoCancel(true)
              .setContentText("상대방이 아이템을 사용했습니다.");
              mNotificationManager = (NotificationManager)
                      this.getSystemService(Context.NOTIFICATION_SERVICE);
              mBuilder.setContentIntent(contentIntent);
              mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    		  
    		  
    		//  item_no=send_intent.g
    		  
    		  // 8번일땐 shared에 리워드 저장만
    	  }else if(push_type==9){ // 탈퇴 
    		  intent=new Intent(this,SplashActivity.class);
    			PropertyManager.getInstance().setUserId("");
					PropertyManager.getInstance().setPassword("");
    		  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    		  /*------gcm-----*/
    		  PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                      intent, PendingIntent.FLAG_UPDATE_CURRENT);
              NotificationCompat.Builder mBuilder =
                      new NotificationCompat.Builder(this)
              .setSmallIcon(R.drawable.magicday_woman_character_)
              .setContentTitle("홀딱바나나") 
              .setAutoCancel(true)
              .setContentText("상대방이 탈퇴했습니다. 모든 기록이 삭제 됩니다.");
              mNotificationManager = (NotificationManager)
                      this.getSystemService(Context.NOTIFICATION_SERVICE);
              mBuilder.setContentIntent(contentIntent);
              mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    	  }
    	  
    	  /*PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                  intent, PendingIntent.FLAG_UPDATE_CURRENT);
          NotificationCompat.Builder mBuilder =
                  new NotificationCompat.Builder(this)
          .setSmallIcon(R.drawable.magicday_woman_character_)
          .setContentTitle("GCM Notification") 
          .setAutoCancel(true)
          .setContentText("");
          mNotificationManager = (NotificationManager)
                  this.getSystemService(Context.NOTIFICATION_SERVICE);
          mBuilder.setContentIntent(contentIntent);
          mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build()); */
    }
      
    
}
 