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

public class PeriodInfoActivity extends ActionBarActivity {

	Button btn_before, btn_next;
	EditText startYear, startMonth, startDay, endYear, endMonth, endDay, edit_period_cycle;
	String period_start, period_end, StartYear, StartMonth, StartDay, EndYear, EndMonth, EndDay, period_cycle;
	int yearS, monthS, dayS, yearD, monthD, dayD;
	Calendar cal;
	boolean isTrue1, isTrue2, sub;
	Date dateD1, dateD2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_period_info);

		long now = System.currentTimeMillis();
		Date date = new Date(now);
		cal = Calendar.getInstance();
		cal.setTime(date); 
		
		startYear = (EditText)findViewById(R.id.edit_period_start_year);
		startMonth = (EditText)findViewById(R.id.edit_period_start_month);
		startDay = (EditText)findViewById(R.id.edit_period_start_day);
		endYear = (EditText)findViewById(R.id.edit_period_end_year);
		endMonth = (EditText)findViewById(R.id.edit_period_end_month);
		endDay = (EditText)findViewById(R.id.edit_period_end_day);
		edit_period_cycle = (EditText)findViewById(R.id.edit_period);
		btn_before = (Button)findViewById(R.id.btn_before);
		btn_before.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_next = (Button)findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				StartYear = startYear.getText().toString();
				StartMonth = startMonth.getText().toString();
				StartDay = startDay.getText().toString();
				EndYear = endYear.getText().toString();
				EndMonth = endMonth.getText().toString();
				EndDay = endDay.getText().toString();
				
				
				period_start = StartYear + "-" + StartMonth + "-" + StartDay;
				period_end = EndYear + "-" + EndMonth + "-" + EndDay;
				period_cycle = edit_period_cycle.getText().toString();
				
				if(StartYear.equals("")||StartMonth.equals("")||StartDay.equals("")||
						EndYear.equals("")||EndMonth.equals("")||EndDay.equals("") ||period_cycle.equals("")) { 
					period_start = "";
					period_end = "";
					Toast.makeText(PeriodInfoActivity.this, "날짜를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
				} else {
						yearS = Integer.parseInt(StartYear);
						monthS = Integer.parseInt(StartMonth);
						dayS = Integer.parseInt(StartDay);
						yearD = Integer.parseInt(EndYear);
						monthD = Integer.parseInt(EndMonth);
						dayD = Integer.parseInt(EndDay);
				}
				
				StringBuffer sb = new StringBuffer();
				period_start  = sb.append(StartYear).append("-").append(StartMonth).append("-").append(StartDay).toString() 
						+" 00:00:00";
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				try {
					dateD1 = df.parse(period_start);
					isTrue1 = subDate(dateD1);  
				} catch (ParseException e) { 
					e.printStackTrace();
				}
				
				sb = new StringBuffer();
				period_end  = sb.append(EndYear).append("-").append(EndMonth).append("-").append(EndDay).toString() 
						+" 00:00:00"; 
				try {
					dateD2 = df.parse(period_end); 
				} catch (ParseException e) { 
					e.printStackTrace();
				}
				
				try {
					sub = subDate2(dateD1, dateD2);
				} catch(NullPointerException e) {
					Toast.makeText(PeriodInfoActivity.this, "날짜를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
				}
				
				 
				if(StartYear.length() < 4 || StartYear.equals("") || StartMonth.equals("") || StartDay.equals("") || 
						yearS>cal.get(Calendar.YEAR) || 
						monthS > 12 || monthS <= 0 || dayS > 31 || dayS <= 0 || isTrue1 == false || 
						EndYear.length() < 4 || EndYear.equals("") || EndMonth.equals("") || EndDay.equals("") || 
						yearD>cal.get(Calendar.YEAR) || 
						monthD > 12 || monthD <= 0 || dayD > 31 || dayD <= 0 || sub == false) {
					Toast.makeText(PeriodInfoActivity.this, "날짜를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
				} else {
					period_start = StartYear+"-"+StartMonth+"-"+StartDay;
					period_end = EndYear+"-"+EndMonth+"-"+EndDay; 

					WomanInfoParcelData wdata = new WomanInfoParcelData();
					wdata.period_start = period_start;
					wdata.period_end = period_end;
					wdata.period_cycle = period_cycle; 
					
					Intent intent = new Intent(PeriodInfoActivity.this, SymtomInfoActivity.class);
					intent.putExtra("wdata", wdata); 
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
	
	public boolean subDate2(Date d1, Date d2) { 
		long subday = d1.getTime() - d2.getTime(); 
		if(subday > 0) {
			return false; 
		} else {
			return true;
		}
	} 
	 
	 
	
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.period_info, menu);
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
