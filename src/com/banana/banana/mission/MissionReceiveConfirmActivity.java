package com.banana.banana.mission;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



import com.banana.banana.R;
import com.banana.banana.R.id;
import com.banana.banana.R.layout;
import com.banana.banana.R.menu;

public class MissionReceiveConfirmActivity extends ActionBarActivity {
	Button btn_showHint;
	String hint;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_receive_confirm);
		Intent intent=getIntent();
		hint=intent.getStringExtra("mission_hint");
		btn_showHint=(Button)findViewById(R.id.btn_showHint);
		btn_showHint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(MissionReceiveConfirmActivity.this,MissionShowHint.class);
				intent.putExtra("mission_hint", hint);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mission_receive_confirm, menu);
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
