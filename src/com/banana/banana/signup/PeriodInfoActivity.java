package com.banana.banana.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.banana.banana.R;

public class PeriodInfoActivity extends ActionBarActivity {

	Button btn_before, btn_next;
	EditText startYear, startMonth, startDay, endYear, endMonth, endDay, edit_period_cycle;

	String period_start, period_end;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_period_info);

		startYear = (EditText)findViewById(R.id.edit_period_start_year);
		startMonth = (EditText)findViewById(R.id.edit_period_start_month);
		startDay = (EditText)findViewById(R.id.edit_period_start_day);
		endYear = (EditText)findViewById(R.id.edit_period_end_year);
		endMonth = (EditText)findViewById(R.id.edit_period_end_month);
		endDay = (EditText)findViewById(R.id.edit_period_end_day);
		edit_period_cycle = (EditText)findViewById(R.id.edit_period);
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
				// TODO Auto-generated method stub
				String StartYear = startYear.getText().toString();
				String StartMonth = startMonth.getText().toString();
				String StartDay = startDay.getText().toString();
				String EndYear = endYear.getText().toString();
				String EndMonth = endMonth.getText().toString();
				String EndDay = endDay.getText().toString();
	
				period_start = StartYear + "-" + StartMonth + "-" + StartDay;
				period_end = EndYear + "-" + EndMonth + "-" + EndDay;
				
				if(StartYear.equals("")||StartMonth.equals("")||StartDay.equals("")||EndYear.equals("")||EndMonth.equals("")||EndDay.equals("")) { 
					period_start = "";
					period_end = "";
				} 

				 
				String period_cycle = edit_period_cycle.getText().toString();
				
				/*Intent i = getIntent();
				Bundle bundle = i.getExtras();  
				if (bundle == null) {
					bundle = new Bundle();
				}*/
				
				WomanInfoParcelData wdata = new WomanInfoParcelData();
				wdata.period_start = period_start;
				wdata.period_end = period_end;
				wdata.period_cycle = period_cycle; 
				
				Intent intent = new Intent(PeriodInfoActivity.this, SymtomInfoActivity.class);
				intent.putExtra("wdata", wdata); 
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.period_info, menu);
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
