package com.banana.banana.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.banana.banana.JoinCodeInfoParcel;
import com.banana.banana.R;

public class CoupleRequestActivity extends ActionBarActivity {

	
	FragmentManager mFragmentManager;
	private static final String TAG_F1 = "f1";
	private static final String TAG_F2 = "f2";
	
	CoupleRequestFragment f1;
	CoupleResponseFragment f2;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main); 
		
		f1 = new CoupleRequestFragment();
		f2 = new CoupleResponseFragment();
		
		mFragmentManager = getSupportFragmentManager();
		
		//Intent i = getIntent();
		//Bundle bundle = i.getExtras();  
		//if (bundle == null) {
			//bundle = new Bundle();
		//}
		//String join_code = bundle.getString("join_code"); 
		

		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			bundle = new Bundle();
		}
		JoinCodeInfoParcel joinData = bundle.getParcelable("joinData");

		
		int join_code = joinData.join_code;

		if(join_code == 1) {
			changeTab1();
		} else if(join_code == 2) {
			changeTab2();
		} else if(join_code == 3) {
			changeTab1();
		}
	}

	private void changeTab1() { 
			FragmentTransaction ft = mFragmentManager.beginTransaction(); 		
			ft.replace(R.id.container, f1, TAG_F1);
			ft.commit(); 
 
	}
	
	private void changeTab2() { 
			FragmentTransaction ft = mFragmentManager.beginTransaction(); 
			ft.replace(R.id.container, f2, TAG_F2);
			ft.commit();  
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	Button btn_request;
	EditText edit_number; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_couple_request);
		btn_request = (Button)findViewById(R.id.btn_couple_request);
		edit_number = (EditText)findViewById(R.id.edit_number);
 
		
		
		btn_request.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String number = edit_number.getText().toString(); 
				String message = "hi";
				TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
			 	String myphoneNum = telManager.getLine1Number();
			 	if(myphoneNum.startsWith("+82")){
			 		myphoneNum = myphoneNum.replace("+82", "0");
			 	}
				Intent i = getIntent();
				Bundle b = i.getExtras(); 
				b.putString("partenerNum", number);
				b.putString("mynumber", myphoneNum);
				Intent intent = new Intent(CoupleRequestActivity.this, FirstMeetingActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				/*
					if(number.length()>0) { 
						sendSMS(number, message); 
					} else {
						Toast.makeText(CoupleRequestActivity.this, "please enter number", Toast.LENGTH_SHORT).show();
					}
				*/
		/*	}
		});
	}*/
	/*
	
	 private void sendSMS(String phoneNumber, String message)
	    {    
		 
		 	TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
		 	String myphoneNum = telManager.getLine1Number();
		 	if(myphoneNum.startsWith("+82")){
		 		myphoneNum = myphoneNum.replace("+82", "0");
		 	}
		 	Intent i = getIntent();
			Bundle b = i.getExtras(); 
			b.putString("partenerNum", phoneNumber);
			b.putString("mynumber", myphoneNum);
			Intent intent = new Intent(CoupleRequestActivity.this, FirstMeetingActivity.class);
			intent.putExtras(b);
		 	PendingIntent pi = PendingIntent.getActivity(this, 0, 
	        		intent, 0); 	        
	        SmsManager sms = SmsManager.getDefault();
	        sms.sendTextMessage(phoneNumber, null, message, pi, null);
	        
	    }    */
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.couple_request, menu);
		return true;
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
}
