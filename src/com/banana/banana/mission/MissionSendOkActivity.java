package com.banana.banana.mission;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.setting.SettingActivity;

public class MissionSendOkActivity extends ActionBarActivity {
	Button btn_ok; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_send_ok);
		  
		btn_ok=(Button)findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				Intent intent=new Intent(MissionSendOkActivity.this,MissionActivity.class); 
				finish();
				startActivity(intent);
				
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mission_send_ok, menu);
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
