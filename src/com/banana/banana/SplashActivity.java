package com.banana.banana;
 

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.banana.banana.intro.IntroActivity;
import com.banana.banana.login.LoginResult;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.main.BananaMainActivity;
import com.banana.banana.signup.BirthDayInfoActivity;
import com.banana.banana.signup.CoupleRequestActivity;
import com.banana.banana.signup.FirstMeetingActivity;
import com.banana.banana.signup.JoinResult;
import com.banana.banana.signup.SexInfoActivity;
import com.banana.banana.tutorial.TutorialActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class SplashActivity extends ActionBarActivity {
	  
	
	int join_code;   //join_code로 분기
	String gender;  
	int user_req;  //요청자인지 승인자인지
	int user_no; 
	Boolean isFirst;
	String user_id, user_pass;
	//String g;
	 //----gcm
		
		public static final String EXTRA_MESSAGE = "message";
	    public static final String PROPERTY_REG_ID = "registration_id";
	    private static final String PROPERTY_APP_VERSION = "appVersion";
	    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	    /**
	     * Substitute you own sender ID here. This is the project number you got
	     * from the API Console, as described in "Getting Started."
	     */
	    String SENDER_ID = "492958073196";

	    /**
	     * Tag used on log messages.
	     */
	    static final String TAG = "GCM Demo";
	    String reg_id ;  //실제 gcm id로 바꿔야함
	    //TextView mDisplay;
	    GoogleCloudMessaging gcm;
	    AtomicInteger msgId = new AtomicInteger();
	    Context context;
//----------------------------------------------------------------------------

 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spalsh);
		checkAndRegister();
		user_id = PropertyManager.getInstance().getUserId();   //shared의 user_id확인
		user_pass = PropertyManager.getInstance().getPassword();
		if(!user_id.equals("") && !user_pass.equals("")) { 
			Login();
		} else {
			Isfirst();
		}
	}
	 
Handler mHandler = new Handler(Looper.getMainLooper());
 
	
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PLAY_SERVICES_RESOLUTION_REQUEST) {
			if (resultCode == Activity.RESULT_OK) {
				checkAndRegister();
			} else {
				finish();
			}
		}
	}

	private void checkAndRegister() {
		if (checkPlayServices()) {
			String regid = PropertyManager.getInstance().getRegistrationId();
			if (regid.equals("")) {
				registerInBackground();
			} else {
				doRealStart();
			}
		}
	}

	private void doRealStart() {
		
		reg_id=PropertyManager.getInstance().getRegistrationId();
		Log.i("regid", reg_id);
		
		
	}
	
	

	private boolean checkPlayServices() {
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);
		if (resultCode != ConnectionResult.SUCCESS) {
			if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
						resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST);
				dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						finish();
					}
				});
				dialog.show();
			} else {
				finish();
			}
			return false;
		}
		return true;
	}
	

	private void registerInBackground() {
		new AsyncTask<Void, Integer, String>() {
			@Override
			protected String doInBackground(Void... params) {
				try {
					GoogleCloudMessaging gcm = GoogleCloudMessaging
							.getInstance(SplashActivity.this);
					String regid = gcm.register(SENDER_ID);
					PropertyManager.getInstance().setRegistrationId(regid);
					return regid;
				} catch (IOException ex) {
				}
				return null;
			}

			@Override
			protected void onPostExecute(String msg) {
				doRealStart();
			}
		}.execute();
	}
	
	private void Login() {
		// TODO Auto-generated method stub
		TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
	 	String user_phone = telManager.getLine1Number();
	 	if(user_phone.startsWith("+82")){
	 		user_phone = user_phone.replace("+82", "0");
	 	}
	 	String num1 = user_phone.substring(0, 3);
		String num2 = user_phone.substring(3, 7);
		String num3 = user_phone.substring(7, 11);
		user_phone = num1+"-"+num2+"-"+num3;
		NetworkManager.getInstnace().login(SplashActivity.this, user_id, user_pass, user_phone, reg_id, new OnResultListener<LoginResult>() {

			@Override
			public void onSuccess(LoginResult result) {
				// TODO Auto-generated method stub
				if(result.success == 1) {
				searchJoinInfo(); 
				} else {
					PropertyManager.getInstance().setIsFirst(true);
					PropertyManager.getInstance().setPassword("");
					PropertyManager.getInstance().setUserId(""); 
					Isfirst();
				}
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void Isfirst() {
		// TODO Auto-generated method stub
		isFirst = PropertyManager.getInstance().getIsFirst();
		if(isFirst == true) {
			PropertyManager.getInstance().setIsFirst(false);
			Intent intent = new Intent(SplashActivity.this, TutorialActivity.class);
			startActivity(intent); 
			finish(); 
		} else if (isFirst == false) {
			Intent intent = new Intent(SplashActivity.this, IntroActivity.class); 
			startActivity(intent); 
			finish(); 
		} 
	}

	public void searchJoinInfo() { //가입정보 조회
		NetworkManager.getInstnace().searchJoinInfo(SplashActivity.this, new OnResultListener<JoinResult>() {

			@Override
			public void onSuccess(JoinResult result) {
				// TODO Auto-generated method stub
				join_code = result.result.items.join_code;
				user_req= result.result.items.user_req;
				gender = result.result.items.user_gender;
				if(join_code == 0) { //메인페이지로
					Intent intent = new Intent(SplashActivity.this, BananaMainActivity.class);  
					startActivity(intent);
					finish(); 
				}  else if (join_code == 1) {
					//Bundle bundle = new Bundle();
					//bundle.putInt("join_code", join_code);
					JoinCodeInfoParcel joinData = new JoinCodeInfoParcel();
					joinData.join_code = join_code; 
					Intent intent = new Intent(SplashActivity.this, SexInfoActivity.class);
					intent.putExtra("joinData", joinData); 
					//intent.putExtras(bundle);
					startActivity(intent);
					finish();
				} else if(join_code == 2) {
					//Bundle bundle = new Bundle();
					//bundle.putInt("join_code", join_code); 
					PropertyManager.getInstance().setUserGender(result.result.items.user_gender);
					JoinCodeInfoParcel joinData = new JoinCodeInfoParcel();
					joinData.join_code = join_code;
					Intent intent = new Intent(SplashActivity.this, CoupleRequestActivity.class);
					//intent.putExtras(bundle); 
					intent.putExtra("joinData", joinData); 
					startActivity(intent);
					finish();
				}  
				else if(join_code == 3) { //요청자의 커플 승인대기 페이지(버튼 비활성화)  
					JoinCodeInfoParcel joinData = new JoinCodeInfoParcel();
					joinData.join_code = join_code;
					Intent intent = new Intent(SplashActivity.this, CoupleRequestActivity.class); 
					intent.putExtra("joinData", joinData);
					startActivity(intent); 
					finish();
				} else if(join_code == 4) { //추가정보 미입력 페이지(기념일, 생일)
					notInputJoinDetail(); 
				} else if(join_code == 5) { //한명이 탈퇴했을 경우
					Dialog();
				}
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
} 
	protected void notInputJoinDetail() { //추가정보 미입력
		// TODO Auto-generated method stub
		
		if(user_req == 1) {
				Intent intent = new Intent(SplashActivity.this, FirstMeetingActivity.class);
				startActivity(intent);
				finish();
			} else if(user_req == 0) {
				Intent intent = new Intent(SplashActivity.this, BirthDayInfoActivity.class);
				startActivity(intent); 
				finish();
			}
	}
	
	/*	protected void notInputJoinDetail() { //추가정보 미입력
		// TODO Auto-generated method stub
			if(user_req == 0 && gender.equals("F")) {
				Intent intent = new Intent(SplashActivity.this, FirstMeetingActivity.class);
				startActivity(intent); 
			} else if(user_req == 1 && gender.equals("F")) {
				Intent intent = new Intent(SplashActivity.this, BirthDayInfoActivity.class);
				startActivity(intent); 
			} else if(user_req == 0 && gender.equals("M")) {
				Intent intent = new Intent(SplashActivity.this, FirstMeetingActivity.class);
				startActivity(intent); 
			} else if(user_req == 1 && gender.equals("M")) {
				Intent intent = new Intent(SplashActivity.this, BirthDayInfoActivity.class);
				startActivity(intent); 
			}
	}*/


		protected void Dialog() {
	
			AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("Alert Dialog");
			builder.setMessage("상대방이 탈퇴하였습니다. 모든 정보가 삭제됩니다.");
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(SplashActivity.this, "Yes Click", Toast.LENGTH_SHORT).show();
				}
			});
			
//			builder.setCancelable(false);
			
			builder.create().show();
}
 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.spalsh, menu);
		return true;
	}
 
    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = PropertyManager.getInstance().mPrefs;
        //int appVersion = getAppVersion(context);
       // Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        //editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	    private void sendRegistrationIdToBackend() {
	      // Your implementation here.
	    }


}
