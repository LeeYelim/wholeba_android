package com.banana.banana.signup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.main.BananaMainActivity;

public class BirthDayInfoActivity extends ActionBarActivity {

	Button btn_before, btn_next;
	EditText BirthDayYear, BirthDayMonth, BirthDayDay;
	String user_gender, birthYear, birthMonth, birthDay;
	Calendar cal;
	int year, month, day;
	boolean isTrue;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_birth_day_info);
		BirthDayYear = (EditText)findViewById(R.id.edit_birth_year);
		BirthDayMonth = (EditText)findViewById(R.id.edit_birth_month);
		BirthDayDay = (EditText)findViewById(R.id.edit_birth_day);
		
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		cal = Calendar.getInstance();
		cal.setTime(date); 
		
		
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
				
				
				birthYear = BirthDayYear.getText().toString();
				birthMonth = BirthDayMonth.getText().toString();
				birthDay = BirthDayDay.getText().toString();
				
				if(!(birthYear.equals("") || birthMonth.equals("") || birthDay.equals(""))) {
					year = Integer.parseInt(birthYear);
					month = Integer.parseInt(birthMonth);
					day = Integer.parseInt(birthDay);
				}
				
				StringBuffer sb = new StringBuffer();
				String user_birth = sb.append(birthYear).append("-").append(birthMonth).append("-").append(birthDay).toString()
						+" 00:00:00";
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					Date dateD = df.parse(user_birth);
					isTrue = subDate(dateD); 
					Log.i("dateD", ""+dateD);
				} catch (ParseException e) { 
					e.printStackTrace();
				}
				
				if(birthYear.length() < 4 || birthYear.equals("") || birthMonth.equals("") || birthDay.equals("") || 
						year>cal.get(Calendar.YEAR) || 
						month > 12 || month <= 0 || day > 31 || day <= 0 || isTrue == false) {
					Toast.makeText(BirthDayInfoActivity.this, "날짜를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
				} else {
					user_birth = birthYear+"-"+birthMonth+"-"+birthDay;
					addCommonInfo(couple_birth, user_birth);   // 공통 정보 등록
				}  
			}
		});
	}
	 


	private void addCommonInfo(String couple_birth, String user_birth) { 
		NetworkManager.getInstnace().addCommonInfo(BirthDayInfoActivity.this, couple_birth, user_birth, new OnResultListener<JoinResult>() {

			@Override
			public void onSuccess(JoinResult result) {
				// TODO Auto-generated method stub 
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

	}

	public boolean subDate(Date d1) {
		Calendar car = Calendar.getInstance() ;
		Date d = new Date();
		d = car.getTime();
		long subday = d1.getTime() - d.getTime(); 
		
		if(subday > 0) {
			return false; 
		} else {
			return true;
		}
	} 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.birth_day_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
			//return true;
		//}
		return super.onOptionsItemSelected(item);
	}
}
