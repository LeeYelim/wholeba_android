package com.banana.banana.setting;

import java.sql.ResultSet;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class MyinfoActivity extends ActionBarActivity {
    TextView textperiodView;
	Button btn_edit;
	View EditPeriodView;
	ListView menseListView; 
	WomanInfoAdapter mAdapter;
	String user_gender;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		//EditPeriodView = (View)findViewById(R.id.edit_period_layout);
		menseListView = (ListView)findViewById(R.id.list_menses);
		//mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		mAdapter = new WomanInfoAdapter(this);
		menseListView.setAdapter(mAdapter);
		user_gender = PropertyManager.getInstance().getUserGender();
		textperiodView = (TextView)findViewById(R.id.text_period_input);
		textperiodView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setVisibileDetailView(!isVisibleDetailView());
			}
		});
		
		initData();
	}
	
	
	private void initData() {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().getWomanInfoList(MyinfoActivity.this, user_gender, new OnResultListener<WomanInfoResponse>() {

			@Override
			public void onSuccess(WomanInfoResponse result) {
				// TODO Auto-generated method stub
				mAdapter.addAll(result.result.items.period);
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
		 
	}


	public boolean isVisibleDetailView() {
		return EditPeriodView.getVisibility() == View.VISIBLE;
	}
	
	public void setVisibileDetailView(boolean isVisible) {
		EditPeriodView.setVisibility(isVisible?View.VISIBLE:View.GONE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.myinfo, menu);
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
