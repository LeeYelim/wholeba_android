package com.banana.banana.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.banana.banana.JoinCodeInfoParcel;
import com.banana.banana.PropertyManager;
import com.banana.banana.R;

public class SexInfoActivity extends ActionBarActivity {

	Button btn_before, btn_next;
	RadioButton radioMale, radioFemale; 
	RadioGroup radioGender;
	String gender=""; 
	Boolean isSelected = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sex_info);
		radioMale = (RadioButton)findViewById(R.id.radio_male);
		radioFemale = (RadioButton)findViewById(R.id.radio_female);
		radioMale.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setGender(true);
			}
		});
		
		radioFemale.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setGender(false);
			}
		});
		
		
		/*radioGender = (RadioGroup)findViewById(R.id.radioGroup1);
		radioGender.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_female:
					//gender = "F";
					//radioMale.setChecked(false);
					setGender(true);
					break;

				case R.id.radio_male:
					//gender = "M";
					//radioFemale.setChecked(false);
					setGender(false);
					break;
				} 
			}
		}); */
		 
		
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

				if(gender.equals("")) {
					
				} else { 
					Bundle bundle = getIntent().getExtras();  
					if (bundle == null) {
						bundle = new Bundle();
					}
					JoinCodeInfoParcel joinData = bundle.getParcelable("joinData");
					PropertyManager.getInstance().setUserGender(gender);
					Intent intent = new Intent(SexInfoActivity.this, CoupleRequestActivity.class);
					intent.putExtra("joinData", joinData);
					startActivity(intent);
				}
			}
		});
		
	}
	
	private void setGender(boolean isChecked) {
		isSelected = isChecked;
		
		if (isSelected == true) {
			radioMale.setChecked(true);	// image
			radioFemale.setChecked(false);	// clicked
			gender = "M";
																	
		} else {
			radioMale.setChecked(false);	// clicked 
			radioFemale.setChecked(true);  // image
			gender = "F";
		}
	} 


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sex_info, menu);
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
