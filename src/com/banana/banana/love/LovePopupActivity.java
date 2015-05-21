package com.banana.banana.love;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class LovePopupActivity extends ActionBarActivity {

	Button btn_popUpOk, btn_close;
	ToggleButton toggleCondom;
	int is_condom;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_love_popup); 
		toggleCondom = (ToggleButton)findViewById(R.id.toggle_love_condom);
		
		Intent i = getIntent();
		is_condom = i.getIntExtra("couple_condom", -1);
		Toast.makeText(LovePopupActivity.this, ""+is_condom, Toast.LENGTH_SHORT).show();
		
		if(is_condom == 0) {
			toggleCondom.setChecked(false);
		} else if(is_condom == 1) {
			toggleCondom.setChecked(true);
		}
		
		
		
		btn_popUpOk = (Button)findViewById(R.id.btn_popup_ok);
		btn_popUpOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(toggleCondom.isChecked()) {
					is_condom = 1;
				} else {
					is_condom = 0;
				}				
				/*Intent intent = new Intent(LovePopupActivity.this, LovePopupOk.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				intent.putExtra("is_condom", is_condom);
				startActivity(intent);*/
				Date today = new Date(); 
				Calendar cal = Calendar.getInstance();
				cal.setTime(today); 
				String loves_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(cal.getTime());
				int loves_ispopup = 1;
				
				NetworkManager.getInstnace().addLove(LovePopupActivity.this, is_condom, loves_date, loves_ispopup, new OnResultListener<LoveSearchResult>() {
					
					@Override
					public void onSuccess(LoveSearchResult result) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(LovePopupActivity.this, PopupOk.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); 
						intent.putExtra("loves_no", ""+result.result.items.loves_no);
						startActivity(intent);
					}
					
					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub
						
					}
				});
				
			}
		});
		
		btn_close = (Button)findViewById(R.id.btn_close);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.love_popup, menu);
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
