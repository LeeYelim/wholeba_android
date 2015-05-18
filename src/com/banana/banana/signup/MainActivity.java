package com.banana.banana.signup;
 

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.banana.banana.R;

public class MainActivity extends ActionBarActivity {
	FragmentManager mFragmentManager;
	private static final String TAG_F1 = "f1";
	private static final String TAG_F2 = "f2";
	CoupleRequestFragment f1;
	CoupleResponseFragment f2;
	
	int request = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		f1 = new CoupleRequestFragment();
		f2 = new CoupleResponseFragment();
		
		mFragmentManager = getSupportFragmentManager();
		if(request == 1) {
			changeTab1();
		} else if(request == 0) {
			changeTab2();
		}
	}

	private void changeTab1() { 
			FragmentTransaction ft = mFragmentManager.beginTransaction(); 		
			ft.replace(R.id.container, f1, TAG_F1);
			ft.commit(); 
 
	}
	
	private void changeTab2() { 
			FragmentTransaction ft = mFragmentManager.beginTransaction(); 
			ft.replace(R.id.container, f2, TAG_F2);
			ft.commit(); 
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
