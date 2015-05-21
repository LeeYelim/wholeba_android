package com.banana.banana.setting;

import java.util.List;
import java.util.StringTokenizer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class MyinfoActivity extends ActionBarActivity {
    TextView textperiodView;
	Button btn_update, btn_update2;
	View EditPeriodView;
	WomanLinearLayout menseListView; 
	EditText editStartYear, editStartMonth, editStartDay, editEndYear, editEndMonth, editEndDay, editPeriod;
	//WomanInfoAdapter mAdapter;
	String user_gender;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		//EditPeriodView = (View)findViewById(R.id.edit_period_layout);
		menseListView = (WomanLinearLayout)findViewById(R.id.list_menses);
		//mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		//mAdapter = new WomanInfoAdapter(this);
		//menseListView.setAdapter(mAdapter);
		btn_update2 = (Button)findViewById(R.id.btn_update2);
		editStartYear = (EditText)findViewById(R.id.edit_startYear);
		editStartMonth = (EditText)findViewById(R.id.edit_startMonth);
		editStartDay = (EditText)findViewById(R.id.edit_startDay);
		editEndYear = (EditText)findViewById(R.id.edit_endYear);
		editEndMonth = (EditText)findViewById(R.id.edit_endMonth);
		editEndDay = (EditText)findViewById(R.id.edit_endDay);
		editPeriod = (EditText)findViewById(R.id.edit_period);
		
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
		
		btn_update2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updatePeriod(menseListView.list);
				
			}
		});
		 
	
	}

	protected void updatePeriod(List<PeriodItems> items) {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().editPeriod(MyinfoActivity.this, items, new OnResultListener<WomanInfoResponse>() {

			@Override
			public void onSuccess(WomanInfoResponse result) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initData() {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().getWomanInfoList(MyinfoActivity.this, user_gender, new OnResultListener<WomanInfoResponse>() {

			@Override
			public void onSuccess(WomanInfoResponse result) {
				// TODO Auto-generated method stub
				//mAdapter.addAll(result.result.items.period);
				for(int i = 1; i<result.result.items.period.size(); i++) {
					menseListView.set(result.result.items.period.get(i));
				}
				//menseListView.set(result.result.items.period);
				
				String startPeriod = result.result.items.period.get(0).period_start;
				StringTokenizer st = new StringTokenizer(startPeriod, "-");				
				editStartYear.setText(st.nextToken());
				editStartMonth.setText(st.nextToken());
				editStartDay.setText(st.nextToken());
				
				String endPeriod = result.result.items.period.get(0).period_end;
				StringTokenizer st2 = new StringTokenizer(endPeriod, "-");				
				editEndYear.setText(st2.nextToken());
				editEndMonth.setText(st2.nextToken());
				editEndDay.setText(st2.nextToken());
				
				editPeriod.setText(""+result.result.items.period.get(0).period_cycle);
				
				
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
