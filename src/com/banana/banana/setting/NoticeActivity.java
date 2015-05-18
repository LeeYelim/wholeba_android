package com.banana.banana.setting;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
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

	TextView Textnotice; 
	ListView NoticeListView;
	
	NoticeAdapter nAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);
		init();
		NoticeListView=(ListView)findViewById(R.id.notice_contentsListView);
		nAdapter=new NoticeAdapter(this);
		NoticeListView.setAdapter(nAdapter);
		Textnotice = (TextView)findViewById(R.id.text_notice_contents); 
		
	}
	
	

	private void init() {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().getNotic(NoticeActivity.this, new OnResultListener<NoticeResponse>() {
			
			@Override
			public void onSuccess(NoticeResponse result) { 
				 nAdapter.clear();
				 nAdapter.addAll(result.result.items);
				/*int notice_no = result.result.items.get(0).notice_no;
				String notice_date = result.result.items.get(0).notice_date;
				String notice_title = result.result.items.get(0).notice_title;
				String notice_content = result.result.items.get(0).notice_content;
				Textnotice.setText("notice no : " + notice_no + " " + notice_date + " " + notice_title + notice_content);*/
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
		getMenuInflater().inflate(R.menu.notice, menu);
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
