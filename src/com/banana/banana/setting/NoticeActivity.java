package com.banana.banana.setting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.R;
import com.banana.banana.R.id;
import com.banana.banana.R.layout;
import com.banana.banana.R.menu;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;


public class NoticeActivity extends ActionBarActivity { 
	
	ExpandableListView noticeListView;
	ImageView settingImg;
	NoticeAdapter nAdapter;
	TextView titleView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		
		ActionBar actionBar = getSupportActionBar(); 
	      actionBar.setDisplayHomeAsUpEnabled(false);
	      actionBar.setDisplayShowTitleEnabled(false);
	      actionBar.setDisplayShowHomeEnabled(false);
	      actionBar.setDisplayShowCustomEnabled(true); // Custom메뉴 설정 true
	      actionBar.setCustomView(R.layout.custom_action_bar); 
	      titleView = (TextView)actionBar.getCustomView().findViewById(R.id.text_title);
	      titleView.setText("NOTICE");
	      settingImg = (ImageView)actionBar.getCustomView().findViewById(R.id.img_setting);
	      settingImg.setVisibility(View.INVISIBLE);
		
		init();
		noticeListView=(ExpandableListView)findViewById(R.id.expandablelist_notice);
		nAdapter=new NoticeAdapter(this);
		noticeListView.setAdapter(nAdapter); 
		
	}
	
	

	private void init() {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().getNotic(NoticeActivity.this, new OnResultListener<NoticeResponse>() {
			
			@Override
			public void onSuccess(NoticeResponse result) { 
				 nAdapter.clear();
				 for(NoticeItems data : result.result.items) {
		               nAdapter.add(data.notice_no, data);
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
		//getMenuInflater().inflate(R.menu.notice, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
		//	return true;
		//}
		return super.onOptionsItemSelected(item);
	}
}
