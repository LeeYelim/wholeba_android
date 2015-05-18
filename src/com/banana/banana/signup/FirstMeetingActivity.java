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

public class FirstMeetingActivity extends ActionBarActivity {
/*-----------요청자가 응답받으면 입력할 첫만남 페이지 */
	Button btn_next;

	EditText FirstDateYear, FirstDateMonth, FirstDateDay; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first_meeting);
		FirstDateYear = (EditText)findViewById(R.id.edit_first_date_year);
		FirstDateMonth = (EditText)findViewById(R.id.edit_first_date_month);
		FirstDateDay = (EditText)findViewById(R.id.edit_first_date_day); 
		 
		btn_next = (Button)findViewById(R.id.btn_next);
		btn_next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				String dateYear = FirstDateYear.getText().toString();
				String dateMonth = FirstDateMonth.getText().toString();
				String dateDay = FirstDateDay.getText().toString();
				StringBuffer sb = new StringBuffer();
				String couple_birth = sb.append(dateYear).append("-").append(dateMonth).append("-").append(dateDay).toString();
				 
				Bundle bundle = new Bundle();  
				
				bundle.putString("couple_birth", couple_birth); 
				Intent intent = new Intent(FirstMeetingActivity.this, BirthDayInfoActivity.class);
				intent.putExtras(bundle);
				startActivity(intent); 
			}
		});
	}

	 @Override
	 public void onBackPressed() {
	     // do nothing.
	 }
	 
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.first_meeting, menu);
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
