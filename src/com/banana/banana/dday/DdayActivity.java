package com.banana.banana.dday;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.setting.SettingActivity;
public class DdayActivity extends ActionBarActivity {

	ListView ddayList;
	DdayAdapter mDAdapter;
	//View DdayheaderView;
	Button btnAdd;
	TextView titleView, textToday;
	ImageView settingImg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dday);
		ActionBar actionBar = getSupportActionBar(); 
	      actionBar.setDisplayHomeAsUpEnabled(false);
	      actionBar.setDisplayShowTitleEnabled(false);
	      actionBar.setDisplayShowHomeEnabled(false);
	      actionBar.setDisplayShowCustomEnabled(true); // Custom메뉴 설정 true
	      actionBar.setCustomView(R.layout.custom_action_bar); 
	      titleView = (TextView)actionBar.getCustomView().findViewById(R.id.text_title);
	      titleView.setText("BANANA");
	      settingImg = (ImageView)actionBar.getCustomView().findViewById(R.id.img_setting);
	      settingImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(DdayActivity.this, SettingActivity.class);
				startActivity(intent);
			}
		}); 
	      
	      
	    textToday = (TextView)findViewById(R.id.today);
	    long now = System.currentTimeMillis();
	    Date date = new Date(now);
	    SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy.MM.dd");
	    String today = CurDateFormat.format(date);
	    textToday.setText(today);
	    
		btnAdd = (Button)findViewById(R.id.btn_add_dday);
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();
				b.putInt("code", 1);
				DdayDialog dialog = new DdayDialog();
				dialog.setArguments(b);
				dialog.show(getSupportFragmentManager(), "dialog");
			}
		});
		ddayList = (ListView)findViewById(R.id.listView_dday);
		mDAdapter = new DdayAdapter();
		//DdayheaderView = (View)getLayoutInflater().inflate(
               // R.layout.dday_header_layout, null);
		
		//ddayList.addHeaderView(DdayheaderView, null, false);
		ddayList.setAdapter(mDAdapter);
		
		initData();
		ddayList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub  
				Bundle b = new Bundle();
				String ddayDate = mDAdapter.ddaylist.get(position).dday_date;
				String ddayName = mDAdapter.ddaylist.get(position).dday_name;
				int ddayNo = mDAdapter.ddaylist.get(position).dday_no;
				b.putString("ddayDate", ddayDate);
				b.putString("ddayName", ddayName);
				b.putInt("ddayNo", ddayNo);
				b.putInt("code", 0); 
				DdayDialog dialog = new DdayDialog();
				dialog.setArguments(b);
				dialog.show(getSupportFragmentManager(), "dialog");  
			}
		});
	}  
	  
	private void initData() {
		
		// TODO Auto-generated method stub 
		NetworkManager.getInstnace().getDdayList(DdayActivity.this, new OnResultListener<DdayResult>() {

			@Override
			public void onSuccess(DdayResult result) {
				// TODO Auto-generated method stub
				mDAdapter.addAll(result.items);
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
		//getMenuInflater().inflate(R.menu.dday, menu);
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
