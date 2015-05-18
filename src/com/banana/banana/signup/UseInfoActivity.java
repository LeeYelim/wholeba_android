package com.banana.banana.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.main.BananaMainActivity;

public class UseInfoActivity extends ActionBarActivity {

	TextView iv; 
	Button btn_before, btn_next;
	RadioGroup groupView; 
	int use = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_use_info);
		
		
		
		groupView = (RadioGroup)findViewById(R.id.radioGroup1);
		groupView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.radio_yes:
					use = 1;
					break;

				case R.id.radio_no:
					use = 0;
					break;
				} 
			}
		});
		 
		
		btn_before = (Button)findViewById(R.id.btn_before);
		btn_before.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				finish();
			}
		});
		
		btn_next = (Button)findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = getIntent().getExtras();
				WomanInfoParcelData wdata = bundle.getParcelable("wdata"); 
				wdata.couple_condom = use;
				Intent intent = new Intent(UseInfoActivity.this, PeriodDoseInfoActivity.class);
				intent.putExtra("wdata", wdata);
				startActivity(intent);
				
			}
		});
	}

	
	/*
	protected void addWomanInfo(WomanInfoParcelData wdata) {
		NetworkManager.getInstnace().addWomanInfo(UseInfoActivity.this, wdata, new OnResultListener<JoinResult>() {

			@Override
			public void onSuccess(JoinResult result) {
				Intent intent = new Intent(UseInfoActivity.this, BananaMainActivity.class);
				intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK); 
				startActivity(intent);
				Toast.makeText(UseInfoActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}  
*/


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.use_info, menu);
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
