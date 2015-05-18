package com.banana.banana.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.banana.banana.LogoutResponse;
import com.banana.banana.R;
import com.banana.banana.WithDrawReponse;
import com.banana.banana.intro.IntroActivity;
import com.banana.banana.login.LoginActivity;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class SettingActivity extends ActionBarActivity {

	Button btn_out, btn_logout, btn_qna, btn_myInfo, btn_sequrity, btn_alarm, btn_notice;
	View view;
	boolean securitybutton_click=false;
	boolean alarmbutton_click=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		btn_myInfo = (Button)findViewById(R.id.btn_myinfo);
		btn_logout = (Button)findViewById(R.id.btn_logout);
		btn_qna = (Button)findViewById(R.id.btn_qna);
		btn_notice = (Button)findViewById(R.id.btn_notice);

		//btn_sequrity = (Button)findViewById(R.id.btn_sequrity);
		btn_alarm = (Button)findViewById(R.id.btn_alarm);
		btn_out = (Button)findViewById(R.id.btn_out);
		btn_out.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this); 
				builder.setTitle("회원탈퇴 요청");
				builder.setMessage("회원탈퇴 하시면 커플의 모든 데이터가 삭제 됩니다. 탈퇴 하시겠습니까?");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						NetworkManager.getInstnace().withDraw(SettingActivity.this, new OnResultListener<WithDrawReponse>() {
							
							@Override
							public void onSuccess(WithDrawReponse result) {
								Toast.makeText(SettingActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
								Intent intent = new Intent(SettingActivity.this, IntroActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); 
								startActivity(intent);
								finish();
							}
							
							@Override
							public void onFail(int code) {
								
							}
						});
						
						
					}
				}); 
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Toast.makeText(SettingActivity.this, "no", Toast.LENGTH_SHORT).show();
						
					}
					});
			 
				
				builder.create().show();
			}
		});
		
		btn_logout.setOnClickListener(new View.OnClickListener() { 
			
			@Override
			public void onClick(View v) { 
				NetworkManager.getInstnace().logout(SettingActivity.this, new OnResultListener<LogoutResponse>() {

					@Override
					public void onSuccess(LogoutResponse result) {
						// TODO Auto-generated method stub
						Toast.makeText(SettingActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK); 
						startActivity(intent);
					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub
						
					}
				});
			}
		});
		
		
		btn_qna.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingActivity.this, QnAActivity.class);
				startActivity(intent);
			}
		});
		
		btn_notice.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingActivity.this, NoticeActivity.class);
				startActivity(intent);
			}
		});
		
		btn_myInfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SettingActivity.this, MyinfoActivity.class);
				startActivity(intent);
			}
		});
		
		btn_alarm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				view=(LinearLayout)findViewById(R.id.LinearLayout1);
				LinearLayout setting_alarm=(LinearLayout)view.findViewById(R.id.alarm_layout);
				LinearLayout detail_alarm=(LinearLayout)setting_alarm.findViewById(R.id.detail_alarm_layout);
				if(alarmbutton_click==false){
					
					detail_alarm.setVisibility(View.VISIBLE);
					alarmbutton_click=true;
					}
					else
					{
						detail_alarm.setVisibility(View.GONE);
						alarmbutton_click=false;
					}
			}
		});
	}
	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
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
