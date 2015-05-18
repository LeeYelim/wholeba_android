package com.banana.banana.mission.receive;

import com.banana.banana.R;
import com.banana.banana.R.id;
import com.banana.banana.R.layout;
import com.banana.banana.R.menu;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.mission.AddMissionActivity;
import com.banana.banana.mission.MissionResult;
import com.banana.banana.mission.MissionSendOkActivity;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.banana.banana.mission.scratch.*;
public class MissionConfirmActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_confirm);
	}
/*	protected void confirmMission() {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().confirmMission(MissionConfirmActivity.this,M, new OnResultListener<MissionResult>() {
			
			@Override
			public void onSuccess(MissionResult result) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MissionConfirmActivity.this,MissionCardScratchActivity.class);
				//Toast.makeText(AddMissionActivity.this, mission_theme, Toast.LENGTH_SHORT).show();
				startActivity(intent); 
			}
			
			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mission_confirm, menu);
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
