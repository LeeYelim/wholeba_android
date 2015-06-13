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

import com.banana.banana.R;

public class FirstMeetingActivity extends ActionBarActivity {
/*-----------요청자가 응답받으면 입력할 첫만남 페이지 */
	Button btn_next;
	EditText FirstDateYear, FirstDateMonth, FirstDateDay; 
	int year, month, day;
	String dateYear, dateMonth, dateDay;
	boolean isTrue;
	Calendar cal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_meeting);
		FirstDateYear = (EditText)findViewById(R.id.edit_first_date_year);
		FirstDateMonth = (EditText)findViewById(R.id.edit_first_date_month);
		FirstDateDay = (EditText)findViewById(R.id.edit_first_date_day); 
		 
		
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		cal = Calendar.getInstance();
		cal.setTime(date); 
		
		btn_next = (Button)findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				dateYear = FirstDateYear.getText().toString();
				dateMonth = FirstDateMonth.getText().toString();
				dateDay = FirstDateDay.getText().toString();
				
				if(!(dateYear.equals("") || dateMonth.equals("") || dateDay.equals(""))) {
					year = Integer.parseInt(dateYear);
					month = Integer.parseInt(dateMonth);
					day = Integer.parseInt(dateDay);
				}
				
				StringBuffer sb = new StringBuffer();
				String couple_birth = sb.append(dateYear).append("-").append(dateMonth).append("-").append(dateDay).toString() 
						+" 00:00:00";
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					Date dateD = df.parse(couple_birth);
					isTrue = subDate(dateD); 
					Log.i("dateD", ""+dateD);
				} catch (ParseException e) { 
					e.printStackTrace();
				}
				
				if(dateYear.length() < 4 || dateYear.equals("") || dateMonth.equals("") || dateDay.equals("") || 
						year>cal.get(Calendar.YEAR) || 
						month > 12 || month <= 0 || day > 31 || day <= 0 || isTrue == false) {
					Toast.makeText(FirstMeetingActivity.this, "날짜를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
				} else {
					couple_birth = dateYear+"-"+dateMonth+"-"+dateDay;
					Bundle bundle = new Bundle();  
					
					bundle.putString("couple_birth", couple_birth); 
					Intent intent = new Intent(FirstMeetingActivity.this, BirthDayInfoActivity.class);
					intent.putExtras(bundle);
					startActivity(intent); 
				} 
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
	 public void onBackPressed() {
	     // do nothing.
	 }
	 
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_meeting, menu);
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
