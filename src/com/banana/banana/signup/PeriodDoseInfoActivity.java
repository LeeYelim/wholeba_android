package com.banana.banana.signup;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TimeZone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.banana.banana.AlarmModel;
import com.banana.banana.AlarmReceiver;
import com.banana.banana.AlarmService;
import com.banana.banana.AlarmDBHelper;
import com.banana.banana.MyApplication;
import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.main.BananaMainActivity;
import com.banana.banana.setting.MyinfoActivity;

public class PeriodDoseInfoActivity extends ActionBarActivity {

	RadioGroup doseInfo;
	View doseDateView;
	View doseTimeView;
	EditText yearView, monthView, dayView, hourView, minuteView;
	ToggleButton toggleTime;
	Button btn_next, btn_before;
	int user_pills = 0;
	AlarmModel alarmDetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_period_dose_info);
		alarmDetails = new AlarmModel();
		
		yearView = (EditText)findViewById(R.id.edit_period_start_year);
		monthView = (EditText)findViewById(R.id.edit_period_start_month);
		dayView = (EditText)findViewById(R.id.edit_period_start_day);
		hourView = (EditText)findViewById(R.id.EditText02);
		minuteView = (EditText)findViewById(R.id.EditText01);
		
		btn_before = (Button)findViewById(R.id.btn_before);
		btn_next = (Button)findViewById(R.id.btn_next);
		
		toggleTime = (ToggleButton)findViewById(R.id.toggleTime);
		doseInfo = (RadioGroup)findViewById(R.id.radioGroup1);
		doseDateView = (View)findViewById(R.id.linearLayout1);
		doseTimeView = (View)findViewById(R.id.LinearLayout01);
		
		
		doseInfo.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_yes: 
					doseDateView.setVisibility(View.VISIBLE);
					doseTimeView.setVisibility(View.VISIBLE);
					user_pills = 1;
					break;

				case R.id.radio_no:
					doseDateView.setVisibility(View.GONE);
					doseTimeView.setVisibility(View.GONE);
					user_pills = 0;
					break;
				} 
			}
		}); 
		
		btn_before.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				String year = yearView.getText().toString();
				String month = monthView.getText().toString();
				String day = dayView.getText().toString();
				String pills_date = year + "-" + month + "-" + day;
				String hour = hourView.getText().toString();
				String minute = minuteView.getText().toString();
				String pills_time = hour+":"+minute; 
				  
				if(!hour.equals("")) {
					if(!toggleTime.isChecked() && Integer.parseInt(hour)<=12 && hour!= null) {
						int h = Integer.parseInt(hour);
						if(h == 12) {
							h = 00;
						}
						hour = Integer.toString((h+12));
						pills_time = hour+":"+minute;   
					} 
			    
					if(hour != null && minute != null && 
							Integer.parseInt(hour)>=0 && Integer.parseInt(hour)<=24 && 
							Integer.parseInt(hour)>=0 && Integer.parseInt(hour)<=60 ) {
						
							AlarmReceiver.cancelAlarms(MyApplication.getContext());
							List<AlarmModel> list = AlarmDBHelper.alarms;
							alarmDetails.hour = Integer.parseInt(hour);
							alarmDetails.minute = Integer.parseInt(minute);
							
							PropertyManager.getInstance().setHour(Integer.parseInt(hour));
							PropertyManager.getInstance().setMinute(Integer.parseInt(minute));   
							PropertyManager.getInstance().setAlarmOnOff(true);	
							
							if (list.size()<=0) {
								AlarmDBHelper.createAlarm(alarmDetails);
							} else {
								AlarmDBHelper.updateAlarm(alarmDetails, "pillsAlarm");
							}
			 
							AlarmReceiver.setAlarms(MyApplication.getContext()); 
							setResult(RESULT_OK);  
							
							Toast.makeText(PeriodDoseInfoActivity.this, "알람등록 설정", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(PeriodDoseInfoActivity.this, "정확한 시간을 입력해주세요", Toast.LENGTH_SHORT).show();
					}
				}
				
				/*int h = Integer.parseInt(hour);
				
				if(toggleTime.getText().equals("PM") && h < 12) { 
					h += 12;
					hour = Integer.toString(h);
				}*/   
				
				if(year.equals("")||month.equals("")||day.equals("")) {
					pills_date = "";
				}
				
				if(hour.equals("")||minute.equals("")) {
					pills_time = "";
				} 
				   
			/*	if(!pills_time.equals("")) {
					setAlarm(pills_time);
				}*/

				Bundle bundle = getIntent().getExtras();
				WomanInfoParcelData wdata = bundle.getParcelable("wdata"); 
				wdata.user_pills = user_pills;
				wdata.pills_date = pills_date;
				wdata.pills_time = pills_time;
				
				addWomanInfo(wdata); 
				
			}
		});
	}
	
	protected void addWomanInfo(WomanInfoParcelData wdata) {
		NetworkManager.getInstnace().addWomanInfo(PeriodDoseInfoActivity.this, wdata, new OnResultListener<JoinResult>() {

			@Override
			public void onSuccess(JoinResult result) {
				
				Bundle bundle = getIntent().getExtras();
				WomanInfoParcelData wdata = bundle.getParcelable("wdata");  
				Intent intent = new Intent(PeriodDoseInfoActivity.this, BananaMainActivity.class);
				intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK); 
				intent.putExtra("wdata", wdata);
				startActivity(intent);
				Toast.makeText(PeriodDoseInfoActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}  
	
	 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.period_dose_info, menu);
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
