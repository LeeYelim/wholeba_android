package com.banana.banana;

import com.banana.banana.mission.MissionActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MissionItemUsePushActivity extends ActionBarActivity {

	
	TextView themeNameView, itemNameView, useDateView, hintView, itemNameTwoView;
	Button btnok, btnCancel;
	String themeName, itemName, useDate, hint, gender;
	int themeNo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_item_use_push);
		themeNameView = (TextView)findViewById(R.id.text_theme_name);
		itemNameView = (TextView)findViewById(R.id.text_item_name);
		itemNameTwoView = (TextView)findViewById(R.id.text_item_name_2);
		useDateView = (TextView)findViewById(R.id.text_item_usedate);
		hintView = (TextView)findViewById(R.id.text_hint); 
		btnok = (Button)findViewById(R.id.btn_ok);
		btnok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(MissionItemUsePushActivity.this, MissionActivity.class);
				finish();
				startActivity(i);
			}
		});
		
		btnCancel = (Button)findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		}); 
		
		
		Intent i = getIntent();
		themeNo = i.getIntExtra("theme_no", -1);
		useDate = i.getStringExtra("item_usedate");
		itemName = i.getStringExtra("item_name");
		hint = i.getStringExtra("mission_hint");
		gender = PropertyManager.getInstance().getUserGender(); 
		
		if (themeNo == 1) {
			themeNameView.setText("악마미션에"); 
		} else if (themeNo == 2) {
			themeNameView.setText("처음미션에");
		} else if (themeNo == 3) {
			themeNameView.setText("섹시미션에");
		} else if (themeNo == 4) {
			themeNameView.setText("애교미션에");
		} else if (themeNo == 5) {
			themeNameView.setText("천사미션에");
		}  
		
		itemNameView.setText(itemName+"를");
		itemNameTwoView.setText(itemName);
		useDateView.setText(useDate);
		
//		if(!hint.equals("") && hint != null) {
//			hintView.setText("힌트 : "+hint); 
//		} else {
//			hintView.setText("");
//		}
//		
		if(hint == null || hint.equals("")) {
			hintView.setText("");
		} else {
			hintView.setText("힌트: "+hint);
		}
		
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mission_item_use_push, menu);
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
