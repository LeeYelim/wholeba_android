package com.banana.banana.mission;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.banana.banana.R;
public class MissionShowHint extends ActionBarActivity {
	String hint;
	TextView hintView;
	Button btn_ok;
	TextView titleView;
	ImageView settingImg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_show_hint);
		ActionBar actionBar = getSupportActionBar(); 
	      actionBar.setDisplayHomeAsUpEnabled(false);
	      actionBar.setDisplayShowTitleEnabled(false);
	      actionBar.setDisplayShowHomeEnabled(false);
	      actionBar.setDisplayShowCustomEnabled(true); // Custom메뉴 설정 true
	      actionBar.setCustomView(R.layout.custom_action_bar); 
	      titleView = (TextView)actionBar.getCustomView().findViewById(R.id.text_title);
	      titleView.setText("MISSION");
	      settingImg = (ImageView)actionBar.getCustomView().findViewById(R.id.img_setting);
	      settingImg.setVisibility(View.GONE);
		Intent intent=getIntent();
		hint=intent.getStringExtra("mission_hint");
		hintView=(TextView)findViewById(R.id.text_hint);
		hintView.setText(hint);
		btn_ok=(Button)findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Intent intent=new Intent(MissionShowHint.this,MissionActivity.class); 
			finish();
			startActivity(intent);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mission_show_hint, menu);
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
