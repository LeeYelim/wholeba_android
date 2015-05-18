package com.banana.banana.mission;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.mission.scratch.*;

public class AddMissionActivity extends ActionBarActivity {
	Button btnOK;
	ToggleButton btnTheme1, btnTheme2, btnTheme3, btnTheme4, btnTheme5, btnTheme6;
	
	int theme_no=3;//미션 테마 
	boolean selected = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_mission);
		
		btnTheme1 = (ToggleButton)findViewById(R.id.ToggleTheme1); 
		btnTheme2 = (ToggleButton)findViewById(R.id.ToggleTheme2); 
		btnTheme3 = (ToggleButton)findViewById(R.id.ToggleTheme3); 
		btnTheme4 = (ToggleButton)findViewById(R.id.ToggleTheme4); 
		btnTheme5 = (ToggleButton)findViewById(R.id.ToggleTheme5); 
		btnTheme6 = (ToggleButton)findViewById(R.id.ToggleTheme6); 
		//로직 다시 짜기 
		/*
		if(btnTheme1.isChecked()) {
			theme_no = 3;
			btnTheme2.setEnabled(false);
			btnTheme3.setEnabled(false);
			btnTheme4.setEnabled(false);
			btnTheme5.setEnabled(false);
			btnTheme6.setEnabled(false);
			selected = true;
		} else if (btnTheme2.isChecked()) {
			theme_no =1;
			btnTheme1.setEnabled(false);
			btnTheme3.setEnabled(false);
			btnTheme4.setEnabled(false);
			btnTheme5.setEnabled(false);
			btnTheme6.setEnabled(false);
			selected = true;
		} else if (btnTheme3.isChecked()) {
			theme_no = 0; 
			btnTheme1.setEnabled(false);
			btnTheme2.setEnabled(false);
			btnTheme4.setEnabled(false);
			btnTheme5.setEnabled(false);
			btnTheme6.setEnabled(false);
			selected = true;
		} else if (btnTheme4.isChecked()) {
			theme_no =5; 
			btnTheme2.setEnabled(false);
			btnTheme3.setEnabled(false);
			btnTheme1.setEnabled(false);
			btnTheme5.setEnabled(false);
			btnTheme6.setEnabled(false);
			selected = true;
		} else if (btnTheme5.isChecked()) {
			theme_no =4;
			btnTheme2.setEnabled(false);
			btnTheme3.setEnabled(false);
			btnTheme4.setEnabled(false);
			btnTheme1.setEnabled(false);
			btnTheme6.setEnabled(false);
			selected = true;
		} else if (btnTheme6.isChecked()) {
			theme_no = 2; 
			btnTheme2.setEnabled(false);
			btnTheme3.setEnabled(false);
			btnTheme4.setEnabled(false);
			btnTheme5.setEnabled(false);
			btnTheme1.setEnabled(false);
			selected = true;
		}*/
		
		 
		btnOK=(Button)findViewById(R.id.btn_ok);
		btnOK.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				//if(selected  == true) {
				
				addMission();
				Toast.makeText(AddMissionActivity.this, "send", Toast.LENGTH_LONG).show();	
				//}
			}
		});
	}

	protected void addMission() {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().addMission(AddMissionActivity.this, theme_no, new OnResultListener<MissionResult>() {
			
			@Override
			public void onSuccess(MissionResult result) {
				if(result.success==1){
				
				Intent intent=new Intent(AddMissionActivity.this,MissionSendOkActivity.class);
			
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
		getMenuInflater().inflate(R.menu.add_mission, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
