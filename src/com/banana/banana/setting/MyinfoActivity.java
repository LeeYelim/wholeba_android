package com.banana.banana.setting;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.banana.banana.AlarmService;
import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class MyinfoActivity extends ActionBarActivity {
	TextView textperiodView, titleView, text_latest_period;
	Button btn_update, btn_update2, btn_alarmYes, btn_alarmNo, btn_publicYes,
			btn_publicNo, btn_setAlarm;
	View editPeriodView, scrollWomanInfoView, list_latest_period;
	WomanLinearLayout menseListView;
	EditText editStartYear, editStartMonth, editStartDay, editEndYear,
			editEndMonth, editEndDay, editPeriod;
	// WomanInfoAdapter mAdapter;
	String user_gender, pills_date, pills_time;
	boolean isAlarm = false;
	boolean isPublic = false;
	View alarmView1, alarmView2;
	int user_public, firstPeriodNum; 
	ImageView settingImg;
	ToggleButton toggleOn;
	DatePicker alarmDatePicker;
	TimePicker alarmTimePicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		// EditPeriodView = (View)findViewById(R.id.edit_period_layout);
		menseListView = (WomanLinearLayout) findViewById(R.id.list_menses);
		ActionBar actionBar = getSupportActionBar(); 
	      actionBar.setDisplayHomeAsUpEnabled(false);
	      actionBar.setDisplayShowTitleEnabled(false);
	      actionBar.setDisplayShowHomeEnabled(false);
	      actionBar.setDisplayShowCustomEnabled(true); // Custom메뉴 설정 true
	      actionBar.setCustomView(R.layout.custom_action_bar); 
	      titleView = (TextView)actionBar.getCustomView().findViewById(R.id.text_title);
	      titleView.setText("내 정보");
	      settingImg = (ImageView)actionBar.getCustomView().findViewById(R.id.img_setting);
	      settingImg.setVisibility(View.INVISIBLE);
		
	    scrollWomanInfoView = (View)findViewById(R.id.scrollWomanInfo);
		btn_update = (Button)findViewById(R.id.btn_update);
	    btn_update2 = (Button)findViewById(R.id.btn_update2);
		editStartYear = (EditText) findViewById(R.id.edit_startYear);
		editStartMonth = (EditText) findViewById(R.id.edit_startMonth);
		editStartDay = (EditText) findViewById(R.id.edit_startDay);
		editEndYear = (EditText) findViewById(R.id.edit_endYear);
		editEndMonth = (EditText) findViewById(R.id.edit_endMonth);
		editEndDay = (EditText) findViewById(R.id.edit_endDay);
		editPeriod = (EditText) findViewById(R.id.edit_period);
		list_latest_period = (View)findViewById(R.id.list_latest_period);
		text_latest_period = (TextView) findViewById(R.id.text_latest_period);
		btn_alarmYes = (Button) findViewById(R.id.btn_alarm_yes);
		btn_alarmNo = (Button) findViewById(R.id.btn_alarm_no);
		alarmView1 = (View) findViewById(R.id.alarm_layout_one);
		alarmView2 = (View) findViewById(R.id.alarm_layout_two);
		btn_publicYes = (Button) findViewById(R.id.btn_public_yes);
		btn_publicNo = (Button) findViewById(R.id.btn_public_no); 
		alarmDatePicker = (DatePicker)findViewById(R.id.alarmDatePicker);
		alarmTimePicker = (TimePicker)findViewById(R.id.alarmTimePicker); 
		btn_setAlarm = (Button)findViewById(R.id.btn_set_alarm); 
		toggleOn = (ToggleButton)findViewById(R.id.toggle_onoff);
		toggleOn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(isChecked==true) {  
				setPillsAlarm(1); 
			}else if(isChecked==false)  {
				setPillsAlarm(0); 
				PropertyManager.getInstance().setAlarmOnOff(false);
			}
		}});
		
		user_gender = PropertyManager.getInstance().getUserGender();
		setPublic(true);
	
		text_latest_period.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {  
					setVisibileDetailView(!isVisibleDetailView());
					if(user_gender.equals("F")) {
						setVisibleBtn(!isVisibleBtn());
					}
			}
		});

		initData();

		btn_update.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				updatePeriod(menseListView.list);
			}
		});
		
		btn_update2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				updatePeriod(menseListView.list);

			}
		});

		btn_alarmYes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) { 
				setAlarm(true);
				setPills(1);
			}
		});

		btn_alarmNo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setAlarm(false);
				setPills(0);
			}
		});
		
		
		if (user_gender.equals("F")) {
			btn_publicYes.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) { 
					setPublic(true);
					setPublic(1);
				}
			});

			btn_publicNo.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) { 
					setPublic(false);
					setPublic(0);
				}
			});
		}
		
		else if(user_gender.equals("M")) {
			btn_publicNo.setEnabled(false);
			btn_publicYes.setEnabled(false);  
		}
		
		btn_setAlarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String hour = alarmTimePicker.getCurrentHour().toString();
				String minute = alarmTimePicker.getCurrentMinute().toString();  
				int year = alarmDatePicker.getYear();
				int month = alarmDatePicker.getMonth();
				int day = alarmDatePicker.getDayOfMonth();
				
				pills_date = year + "-" + month + "-" + day;
				pills_time = hour + ":" + minute;
				
				setPillsAlarmTime(pills_date, pills_time);
				
				if(hour != null && minute != null && toggleOn.isChecked()) {
					PropertyManager.getInstance().setAlarmOnOff(true);	
					PropertyManager.getInstance().setHour(Integer.parseInt(hour));
					PropertyManager.getInstance().setMinute(Integer.parseInt(minute));  
					PropertyManager.getInstance().setAlarmCount(0);								
					Toast.makeText(MyinfoActivity.this, "알람등록 설정", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(MyinfoActivity.this, AlarmService.class); 
					startService(intent);	
				} else {
					Toast.makeText(MyinfoActivity.this, "정확한 시간을 입력해주세요", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	protected void updatePeriod(List<PeriodItems> items) {	 
			
			PeriodItems periods = new PeriodItems();
			periods.period_cycle = Integer.parseInt(editPeriod.getText().toString());
			periods.period_no = firstPeriodNum; 
			String eStartY = editStartYear.getText().toString();
			String eStartM = editStartMonth.getText().toString();
			String eStartD = editStartDay.getText().toString();
			periods.period_start = eStartY+"-"+eStartM+"-"+eStartD;
			String eEndY = editEndYear.getText().toString();
			String eEndM = editEndMonth.getText().toString();
			String eEndD = editEndDay.getText().toString();
			periods.period_end = eEndY+"-"+eEndM+"-"+eEndD;
			items.add(periods);
			 
			NetworkManager.getInstnace().editPeriod(MyinfoActivity.this, items,
				new OnResultListener<WomanInfoResponse>() {

					@Override
					public void onSuccess(WomanInfoResponse result) {
						// TODO Auto-generated method stub
						Toast.makeText(MyinfoActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});

	}

	private void initData() { 
		NetworkManager.getInstnace().getWomanInfoList(MyinfoActivity.this,
				user_gender, new OnResultListener<WomanInfoResponse>() {

					@Override
					public void onSuccess(WomanInfoResponse result) { 
						try {
							for (int i = 1; i < result.result.items.period.size(); i++) {
							menseListView.set(result.result.items.period.get(i));
						} 
						
						firstPeriodNum = result.result.items.period.get(0).period_no;
						String startPeriod = result.result.items.period.get(0).period_start;
						StringTokenizer st = new StringTokenizer(startPeriod,
								"-");
						editStartYear.setText(st.nextToken());
						editStartMonth.setText(st.nextToken());
						editStartDay.setText(st.nextToken());

						String endPeriod = result.result.items.period.get(0).period_end;
						StringTokenizer st2 = new StringTokenizer(endPeriod,
								"-");
						editEndYear.setText(st2.nextToken());
						editEndMonth.setText(st2.nextToken());
						editEndDay.setText(st2.nextToken());

						editPeriod.setText(""
								+ result.result.items.period.get(0).period_cycle);
						user_public = result.result.items.user_public;
						} catch(IndexOutOfBoundsException i) {
							
						}
						if(user_gender.equals("M")) {
							btn_update.setVisibility(View.INVISIBLE);
							btn_update2.setVisibility(View.INVISIBLE); 
							toggleOn.setVisibility(View.INVISIBLE);
							btn_setAlarm.setVisibility(View.GONE);
							btn_alarmYes.setEnabled(false);
							btn_alarmNo.setEnabled(false);
						}
						
						if(user_public == 0 && user_gender.equals("M")) {
							scrollWomanInfoView.setVisibility(View.GONE);
						}
						if(result.result.items.user_pills == 1) {
							setAlarm(true);
						} else {
							setAlarm(false); 
						} 
						try{
						pills_date = result.result.items.pills_date;
						StringTokenizer st3 = new StringTokenizer(pills_date, "-");
						alarmDatePicker.updateDate(Integer.parseInt(st3.nextToken()), 
								Integer.parseInt(st3.nextToken()), Integer.parseInt(st3.nextToken())); 
						pills_time = result.result.items.pills_time;
						st3 = new StringTokenizer(pills_time, ":");
						alarmTimePicker.setCurrentHour(Integer.parseInt(st3.nextToken()));
						alarmTimePicker.setCurrentMinute(Integer.parseInt(st3.nextToken()));
						}catch(NoSuchElementException e) {
							
						}
					} 

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub

					}
				});

	}
	
	private void setPublic(int user_public) {
		NetworkManager.getInstnace().setPublic(MyinfoActivity.this, user_public, new OnResultListener<WomanInfoResponse>() {

			@Override
			public void onSuccess(WomanInfoResponse result) { 
				Toast.makeText(MyinfoActivity.this, ""+result.result.message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFail(int code) { 
				
			}
		});
	}
	
	
	public boolean isVisibleDetailView() {
		return list_latest_period.getVisibility() == View.VISIBLE;
	}

	public void setVisibileDetailView(boolean isVisible) {
		list_latest_period.setVisibility(isVisible ? View.VISIBLE : View.GONE);
	}
	
	public boolean isVisibleBtn() {
		return btn_update2.getVisibility() == View.VISIBLE;
	}

	public void setVisibleBtn(boolean isVisible) {
		btn_update2.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
	}


	private void setPublic(boolean isChecked) {
		isPublic = isChecked;
		if (isPublic == false) {
			btn_publicYes.setBackgroundResource(R.drawable.set_off);
			btn_publicNo.setBackgroundResource(R.drawable.set_on);// clicked
		} else {
			btn_publicYes.setBackgroundResource(R.drawable.set_on); // clicked																// image
			btn_publicNo.setBackgroundResource(R.drawable.set_off);
		}
	}

	private void setAlarm(boolean isChecked) {
		isAlarm = isChecked;
		if (isAlarm == false) {
			btn_alarmYes.setBackgroundResource(R.drawable.set_off);
			btn_alarmNo.setBackgroundResource(R.drawable.set_on);// clicked
																	// image
			alarmView1.setVisibility(View.GONE);
			alarmView2.setVisibility(View.GONE);
			btn_setAlarm.setVisibility(View.GONE);
		} else {
			btn_alarmYes.setBackgroundResource(R.drawable.set_on); // clicked
																// image
			btn_alarmNo.setBackgroundResource(R.drawable.set_off);
			alarmView1.setVisibility(View.VISIBLE);
			alarmView2.setVisibility(View.VISIBLE);
			btn_setAlarm.setVisibility(View.VISIBLE);
		}
	}
	
	private void setPills(int user_pills) {
		NetworkManager.getInstnace().setPills(MyinfoActivity.this, user_pills, new OnResultListener<PillsResponse>() {

			@Override
			public void onSuccess(PillsResponse result) { 
			}

			@Override
			public void onFail(int code) {  
			}
		});
	}
	
 
	private void setPillsAlarmTime(String pills_date, String pills_time) {
		NetworkManager.getInstnace().setPillsTime(MyinfoActivity.this, pills_date, pills_time, new OnResultListener<PillsResponse>() {

			@Override
			public void onSuccess(PillsResponse result) { 
				Toast.makeText(MyinfoActivity.this, ""+result.result.message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFail(int code) { 
			}
		});
	}
	
	private void setPillsAlarm(int pills_alarm) {
		NetworkManager.getInstnace().setPillsAlarm(MyinfoActivity.this, pills_alarm, new OnResultListener<PillsResponse>() {

			@Override
			public void onSuccess(PillsResponse result) { 
				Toast.makeText(MyinfoActivity.this, ""+result.result.message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFail(int code) { 
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.myinfo, menu);
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
