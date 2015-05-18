package com.banana.banana.mission;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class MissionSendPushActivity extends ActionBarActivity {
	private int mlist_no;
	private Button btn_sendPush;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_send_push);
		Intent intent=getIntent();
		mlist_no=intent.getIntExtra("mlist_no", 0);
		btn_sendPush=(Button)findViewById(R.id.btn_sendPush);
		btn_sendPush.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				NetworkManager.getInstnace().askpopup(MissionSendPushActivity.this, mlist_no, new OnResultListener<MissionResult>() {
					
					@Override
					public void onSuccess(MissionResult result) {
						if(result.success==1){
							Intent intent=new Intent(MissionSendPushActivity.this,MissionPopupActivity.class);
							startActivity(intent);
						}
						
					}
					
					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mission_send_push, menu);
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
