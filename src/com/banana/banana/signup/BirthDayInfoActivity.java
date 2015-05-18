package com.banana.banana.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.main.BananaMainActivity;

public class BirthDayInfoActivity extends ActionBarActivity {

	Button btn_before, btn_next;
	EditText BirthDayYear, BirthDayMonth, BirthDayDay;
	String user_gender;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_birth_day_info);
		BirthDayYear = (EditText)findViewById(R.id.edit_birth_year);
		BirthDayMonth = (EditText)findViewById(R.id.edit_birth_month);
		BirthDayDay = (EditText)findViewById(R.id.edit_birth_day);
		
		/*Intent i = getIntent();
		Bundle bundle = i.getExtras();  
		if (bundle == null) {
			bundle = new Bundle();
		} */ 
		user_gender = PropertyManager.getInstance().getUserGender(); 
		
		btn_before = (Button)findViewById(R.id.btn_before);
		btn_before.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				finish();
			}
		});
		
		btn_next = (Button)findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = getIntent();
				Bundle bundle = i.getExtras();  
				if (bundle == null) {
					bundle = new Bundle();
				}
				String couple_birth = bundle.getString("couple_birth"); 
				//user_gender = bundle.getString("gender");
				String birthYear = BirthDayYear.getText().toString();
				String birthMonth = BirthDayMonth.getText().toString();
				String birthDay = BirthDayDay.getText().toString();
				StringBuffer sb = new StringBuffer();
				String user_birth = sb.append(birthYear).append("-").append(birthMonth).append("-").append(birthDay).toString();
				 
				addCommonInfo(couple_birth, user_birth);   // 공통 정보 등록
			}
		});
	}
	 


	private void addCommonInfo(String couple_birth, String user_birth) { 
		NetworkManager.getInstnace().addCommonInfo(BirthDayInfoActivity.this, couple_birth, user_birth, new OnResultListener<JoinResult>() {

			@Override
			public void onSuccess(JoinResult result) {
				// TODO Auto-generated method stub

				Log.i("user_gender", user_gender);

				if(user_gender.equals("M")) {
					Intent intent = new Intent(BirthDayInfoActivity.this, BananaMainActivity.class);
					intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
	 				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} else if(user_gender.equals("F")) {
					Intent intent = new Intent(BirthDayInfoActivity.this, PeriodInfoActivity.class); 

					startActivity(intent);  
				}
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		}); 
		/*
		NetworkManager.getInstnace().addCommonInfo(BirthDayInfoActivity.this, couple_birth, user_birth, new OnResultListener<JoinResult>() {

			@Override
			public void onSuccess(JoinResult result) {
				if(user_gender.equals("남")) {
					Intent intent = new Intent(BirthDayInfoActivity.this, MainActivity.class);
					intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
	 				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					Toast.makeText(BirthDayInfoActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
				} else if(user_gender.equals("여")) {
					Intent intent = new Intent(BirthDayInfoActivity.this, PeriodInfoActivity.class);  
					startActivity(intent); 
					Toast.makeText(BirthDayInfoActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFail(int code) {
				 
			}
		});*/
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.birth_day_info, menu);
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
