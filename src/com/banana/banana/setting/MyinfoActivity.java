package com.banana.banana.setting;

import java.util.List;
import java.util.StringTokenizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.banana.banana.AlarmService;
import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.main.BananaMainActivity;

public class MyinfoActivity extends ActionBarActivity {
	TextView textperiodView, titleView, text_latest_period;
	Button btn_update, btn_update2, btn_alarmYes, btn_alarmNo, btn_publicYes,
			btn_publicNo;
	View editPeriodView, scrollWomanInfoView, list_latest_period;
	WomanLinearLayout menseListView;
	EditText editStartYear, editStartMonth, editStartDay, editEndYear,
			editEndMonth, editEndDay, editPeriod, pills_yearView, pills_monthView, pills_dayView, pills_hourView, pills_minuteView;
	// WomanInfoAdapter mAdapter;
	String user_gender;
	boolean isAlarm = false;
	boolean isPublic = false;
	View alarmView1, alarmView2;
	int user_public, firstPeriodNum; 
	ImageView settingImg;
	ToggleButton toggleOn, toggleAMPM;
	
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
		btn_alarmYes = (Button) findViewById(R.id.btn_alarm_yes);
		btn_alarmNo = (Button) findViewById(R.id.btn_alarm_no);
		alarmView1 = (View) findViewById(R.id.alarm_layout_one);
		alarmView2 = (View) findViewById(R.id.alarm_layout_two);
		btn_publicYes = (Button) findViewById(R.id.btn_public_yes);
		btn_publicNo = (Button) findViewById(R.id.btn_public_no);
		pills_yearView = (EditText)findViewById(R.id.edit_medicine__year);
		pills_monthView = (EditText)findViewById(R.id.edit_medicine__month);
		pills_dayView = (EditText)findViewById(R.id.edit_medicine__day);
		pills_hourView = (EditText)findViewById(R.id.edit_medicine__Time1);
		pills_minuteView = (EditText)findViewById(R.id.edit_medicine__Time2);
		toggleAMPM = (ToggleButton)findViewById(R.id.toggleAMPM);
		toggleOn = (ToggleButton)findViewById(R.id.toggle_onoff);
		toggleOn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(isChecked==true) { 
				String hour = pills_hourView.getText().toString();
				String minute = pills_minuteView.getText().toString(); 
					
				if(!toggleAMPM.isChecked() && 
					Integer.parseInt(hour)<12 && 
					hour!= null) {
					int h = Integer.parseInt(hour);
					hour = Integer.toString((h+12)); 
				} 
						
				if(hour != null && minute != null && 
					Integer.parseInt(hour)>=0 && Integer.parseInt(hour)<=24 && 
					Integer.parseInt(hour)>=0 && Integer.parseInt(hour)<=60 ) {
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
					
				} else if(isChecked==false)  {
					PropertyManager.getInstance().setAlarmOnOff(false);
				}
			}
		});
		
		user_gender = PropertyManager.getInstance().getUserGender();
		setPublic(true);
		list_latest_period = (View)findViewById(R.id.list_latest_period);
		text_latest_period = (TextView) findViewById(R.id.text_latest_period);
		text_latest_period.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setVisibileDetailView(!isVisibleDetailView());
				setVisibleBtn(!isVisibleBtn());
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
			}
		});

		btn_alarmNo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setAlarm(false);
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
			
			/*PeriodItems p = new PeriodItems();
			for(int i = 0; i<items.size(); i++) {
				p.period_cycle = items.get(i).period_cycle;
				p.period_end = items.get(i).period_end;
				p.period_start = items.get(i).period_start;
				p.period_no = items.get(i).period_no;
			}*/
			
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
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().getWomanInfoList(MyinfoActivity.this,
				user_gender, new OnResultListener<WomanInfoResponse>() {

					@Override
					public void onSuccess(WomanInfoResponse result) {
						// TODO Auto-generated method stub
						// mAdapter.addAll(result.result.items.period);
						try {for (int i = 1; i < result.result.items.period.size(); i++) {
							menseListView.set(result.result.items.period.get(i));
						}
						// menseListView.set(result.result.items.period);
						
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
							toggleOn.setEnabled(false);
						}
						
						if(user_public == 0 && user_gender.equals("M")) {
							scrollWomanInfoView.setVisibility(View.GONE);
						}
						if(result.result.items.user_pills == 1) {
							setAlarm(true);
						} else {
							setAlarm(false);
						}
						
						if(!result.result.items.pills_date.equals("") && !result.result.items.pills_time.equals("")) {
						StringTokenizer st3 = new StringTokenizer(result.result.items.pills_date, "-");
						pills_yearView.setText(st3.nextToken());
						pills_monthView.setText(st3.nextToken());
						pills_dayView.setText(st3.nextToken());
						
						StringTokenizer st4 = new StringTokenizer(result.result.items.pills_time, ":");
						pills_hourView.setText(st4.nextToken());
						pills_minuteView.setText(st4.nextToken());  
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
				// TODO Auto-generated method stub
				Toast.makeText(MyinfoActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
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
																	// image
		} else {
			btn_publicYes.setBackgroundResource(R.drawable.set_on); // clicked
																	// image
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
		} else {
			btn_alarmYes.setBackgroundResource(R.drawable.set_on); // clicked
																	// image
			btn_alarmNo.setBackgroundResource(R.drawable.set_off);
			alarmView1.setVisibility(View.VISIBLE);
			alarmView2.setVisibility(View.VISIBLE);
		}
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
