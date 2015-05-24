package com.banana.banana.setting;

import java.util.List;
import java.util.StringTokenizer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class MyinfoActivity extends ActionBarActivity {
	TextView textperiodView, titleView;
	Button btn_update, btn_update2, btn_alarmYes, btn_alarmNo, btn_publicYes,
			btn_publicNo;
	View EditPeriodView;
	WomanLinearLayout menseListView;
	EditText editStartYear, editStartMonth, editStartDay, editEndYear,
			editEndMonth, editEndDay, editPeriod;
	// WomanInfoAdapter mAdapter;
	String user_gender;
	boolean isAlarm = false;
	boolean isPublic = false;
	View alarmView1, alarmView2;
	int user_public; 
	ImageView settingImg;
	
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
	      titleView.setText("SETTING");
	      settingImg = (ImageView)actionBar.getCustomView().findViewById(R.id.img_setting);
	      settingImg.setVisibility(View.INVISIBLE);
		
		
		
		// mAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_list_item_1, new ArrayList<String>());
		// mAdapter = new WomanInfoAdapter(this);
		// menseListView.setAdapter(mAdapter);
		btn_update2 = (Button) findViewById(R.id.btn_update2);
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

		user_gender = PropertyManager.getInstance().getUserGender();
		setPublic(true);
		
		textperiodView = (TextView) findViewById(R.id.text_period_input);
		textperiodView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setVisibileDetailView(!isVisibleDetailView());
			}
		});

		initData();

		btn_update2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updatePeriod(menseListView.list);

			}
		});

		btn_alarmYes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
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
					// TODO Auto-generated method stub
					setPublic(true);
					setPublic(1);
				}
			});

			btn_publicNo.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
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
		
		NetworkManager.getInstnace().editPeriod(MyinfoActivity.this, items,
				new OnResultListener<WomanInfoResponse>() {

					@Override
					public void onSuccess(WomanInfoResponse result) {
						// TODO Auto-generated method stub

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
						for (int i = 1; i < result.result.items.period.size(); i++) {
							menseListView.set(result.result.items.period.get(i));
						}
						// menseListView.set(result.result.items.period);

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
		return EditPeriodView.getVisibility() == View.VISIBLE;
	}

	public void setVisibileDetailView(boolean isVisible) {
		EditPeriodView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
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
