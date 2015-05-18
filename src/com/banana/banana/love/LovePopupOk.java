package com.banana.banana.love;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class LovePopupOk extends ActionBarActivity implements SensorEventListener {

	Sensor mProximitySensor;
	SensorManager mSM;
	SensorListener mListener;
	int is_condom;
	Button btn_out;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_love_popup_ok);
		
		mSM = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		mProximitySensor = mSM.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		
		btn_out = (Button)findViewById(R.id.btn_delete);
		btn_out.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LovePopupOk.this, LoveActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(i);
			}
		});
		
		Intent i = getIntent();
		is_condom = i.getIntExtra("is_condom", -1);
		Toast.makeText(LovePopupOk.this, ""+is_condom, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		if(mProximitySensor != null) {
			mSM.registerListener(this, mProximitySensor, SensorManager.SENSOR_DELAY_GAME);
		} else {
			Toast.makeText(LovePopupOk.this, "device doesn't have PROXIMITY SENSOR", Toast.LENGTH_SHORT).show();
		}
 	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.love_popup_ok, menu);
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
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		mSM.unregisterListener(this);
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		switch (event.sensor.getType()) {
		case Sensor.TYPE_PROXIMITY:
			 float distance = event.values[0];
			 if(distance <= 1 && is_condom != -1) {
				 addLove();
			 }
		}
	}

	private void addLove() {
		// TODO Auto-generated method stub
		
		Date today = new Date(); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(today); 
		String loves_date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		NetworkManager.getInstnace().addLove(LovePopupOk.this, is_condom, loves_date, new OnResultListener<LoveSearchResult>() {
			
			@Override
			public void onSuccess(LoveSearchResult result) {
				// TODO Auto-generated method stub
				Intent i = new Intent(LovePopupOk.this, LoveActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(i);
			}
			
			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
}
