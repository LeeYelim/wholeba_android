package com.banana.banana.mission;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.main.BananaMainActivity;
import com.banana.banana.mission.scratch.*;
import com.banana.banana.setting.SettingActivity;

public class AddMissionActivity extends ActionBarActivity {
	Button btnOK;
	ToggleButton btnTheme1, btnTheme2, btnTheme3, btnTheme4, btnTheme5, btnTheme6; 
	private static final int BUTTON_SIZE = 6;
	ToggleButton[] btnGroup = new ToggleButton[BUTTON_SIZE];
	ToggleButton selectedButton = null;
	private int[] toggleResIds = {R.id.ToggleTheme1,R.id.ToggleTheme2,R.id.ToggleTheme3,R.id.ToggleTheme4,R.id.ToggleTheme5,R.id.ToggleTheme6};
	int theme_no; //미션 테마 
	boolean selected = false;
	TextView titleView;
	ImageView settingImg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_mission); 
		
		ActionBar actionBar = getSupportActionBar(); 
	      actionBar.setDisplayHomeAsUpEnabled(false);
	      actionBar.setDisplayShowTitleEnabled(false);
	      actionBar.setDisplayShowHomeEnabled(false);
	      actionBar.setDisplayShowCustomEnabled(true); // Custom메뉴 설정 true
	      actionBar.setCustomView(R.layout.custom_action_bar); 
	      titleView = (TextView)actionBar.getCustomView().findViewById(R.id.text_title);
	      titleView.setText("MISSION");
	      settingImg = (ImageView)actionBar.getCustomView().findViewById(R.id.img_setting);
	      settingImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddMissionActivity.this, SettingActivity.class);
				finish();
				startActivity(intent);
			}
		}); 
	      
	      
		
				
			for (int i = 0; i < BUTTON_SIZE; i++) {
				btnGroup[i] = (ToggleButton)findViewById(toggleResIds[i]);
				btnGroup[i].setOnCheckedChangeListener(checkedListener);
			} 
			
		btnOK=(Button)findViewById(R.id.btn_ok);
		btnOK.setOnClickListener(new View.OnClickListener() {
		
			@Override
			public void onClick(View v) {  
				addMission();
				Toast.makeText(AddMissionActivity.this, "send", Toast.LENGTH_LONG).show();	 
			}
		}); 
	}
	
	
	CompoundButton.OnCheckedChangeListener checkedListener = new CompoundButton.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if (isChecked) {
				if (selectedButton != null) { //기존것 꺼지고
					selectedButton.setChecked(false);
				}
				if (selectedButton != buttonView) { //새로운거 켜지게
					selectedButton = (ToggleButton)buttonView;
					onToggleSelected(buttonView.getId(), isChecked);
				}
			} else {
				if (selectedButton == buttonView) { //같은거 두번 누르면 꺼지게
					onToggleSelected(buttonView.getId(), isChecked);
				}
			}
		}
	}; 
	
	private void onToggleSelected(int resId, boolean isChecked) {
		switch(resId) {
			case R.id.ToggleTheme1: 
				if(isChecked == true) {
					theme_no = 3;    
					break;
				}
			case R.id.ToggleTheme2 :
				if(isChecked == true) {
					theme_no = 1;  
					break;
				}
			case R.id.ToggleTheme3 :
				if(isChecked == true) {
					theme_no = 0;    
					break;
				}
			case R.id.ToggleTheme4 :
				if(isChecked == true) {
					theme_no = 5;   
					break;
				}
			case R.id.ToggleTheme5 :
				if(isChecked == true) {
					theme_no = 4;    
					break;
				}
			case R.id.ToggleTheme6 :
				if(isChecked == true) {
					theme_no = 2;    
					break;
				}
		} 
	}

	protected void addMission() {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().addMission(AddMissionActivity.this, theme_no, new OnResultListener<MissionResult>() {
			
			@Override
			public void onSuccess(MissionResult result) {
				if(result.success==1){
				
				Intent intent=new Intent(AddMissionActivity.this,MissionSendOkActivity.class); 
				finish();
				startActivity(intent); 
				}
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
		//getMenuInflater().inflate(R.menu.add_mission, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
			//return true;
		//}
		return super.onOptionsItemSelected(item);
	}
}
