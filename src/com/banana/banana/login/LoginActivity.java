package com.banana.banana.login;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.banana.banana.JoinCodeInfoParcel;
import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.main.BananaMainActivity;
import com.banana.banana.signup.BirthDayInfoActivity;
import com.banana.banana.signup.CoupleRequestActivity;
import com.banana.banana.signup.FirstMeetingActivity;
import com.banana.banana.signup.JoinResult;
import com.banana.banana.signup.SexInfoActivity;

public class LoginActivity extends ActionBarActivity {

	EditText idView;
	EditText pwdView;
	Button btn_login_ok;
	int join_code;  
	int user_req;
	int user_no;
	String gender, user_phone, userid, password, user_regid_old, user_phone_old;
	String reg_id ; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		reg_id=PropertyManager.getInstance().getRegistrationId(); 
		idView = (EditText)findViewById(R.id.edit_userid);
		pwdView = (EditText)findViewById(R.id.edit_password);
		btn_login_ok = (Button)findViewById(R.id.btn_login_ok);
		btn_login_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {  
				login(); 
				/*NetworkManager.getInstance().login(LoginActivity.this, userid, password, new OnResultListener<String>() {
					
					@Override
					public void onSuccess(String result) {
						PropertyManager.getInstance().setUserId(userid);
						PropertyManager.getInstance().setPassword(password);  
						Intent intent = new Intent(LoginActivity.this, BananaMainActivity.class);
		 				intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
		 				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);

					}
						@Override
						public void onError(int code) {
							
						}
					});
				}
			});*/ 
		 
		} 
	});
	}
		
		public void login() {
			// TODO Auto-generated method stub
			userid = idView.getText().toString();
			password = pwdView.getText().toString();
			TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
		 	
			try{ 
				user_phone = telManager.getLine1Number();
				if(user_phone.startsWith("+82")){
			 		user_phone = user_phone.replace("+82", "0");
			 	}
			} catch (NullPointerException e) {
				user_phone = "01012341234";
			} 
			
		 	String num1 = user_phone.substring(0, 3);
			String num2 = user_phone.substring(3, 7);
			String num3 = user_phone.substring(7, 11);
			user_phone = num1+"-"+num2+"-"+num3;
			
			NetworkManager.getInstnace().login(LoginActivity.this, userid, password, user_phone, reg_id, new OnResultListener<LoginResult>() {
				
				@Override
				public void onSuccess(LoginResult result) { 
					Toast.makeText(LoginActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
					if(result.success == 2) {  
						reg_id = result.result.items.user_regid;
						user_no = result.result.items.user_no;
						user_phone = result.result.items.user_phone;
						user_regid_old = result.result.items.user_regid_old;
						user_phone_old = result.result.items.user_phone_old;
						acceptLoginDialog();
						
					} else if(result.success == 1) { 
						PropertyManager.getInstance().setUserId(userid);
						PropertyManager.getInstance().setPassword(password);
						getJoinInfo();
					}
				}  
				@Override
				public void onFail(int code) { 
					
				}
			});
		} 
	public void getJoinInfo() {  
		NetworkManager.getInstnace().searchJoinInfo(LoginActivity.this, new OnResultListener<JoinResult>() {

			
			@Override
			public void onSuccess(JoinResult result) {
				// TODO Auto-generated method stub 
				int join_code = result.result.items.join_code;
				user_req= result.result.items.user_req;
				gender = result.result.items.user_gender; 
				
				//autoLogin();
				if(join_code == 0) {
					Intent intent = new Intent(LoginActivity.this, BananaMainActivity.class);  
					intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
					intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK); 
					PropertyManager.getInstance().setUserGender(gender);
					startActivity(intent);
				} else if (join_code == 1) { 
					JoinCodeInfoParcel joinData = new JoinCodeInfoParcel();
					joinData.join_code = join_code; 
					Intent intent = new Intent(LoginActivity.this, SexInfoActivity.class);
					intent.putExtra("joinData", joinData);  
					startActivity(intent);
					finish();
				} else if(join_code == 2) { 
					PropertyManager.getInstance().setUserGender(result.result.items.user_gender);
					JoinCodeInfoParcel joinData = new JoinCodeInfoParcel();
					joinData.join_code = join_code;
					Intent intent = new Intent(LoginActivity.this, CoupleRequestActivity.class);
 					intent.putExtra("joinData", joinData); 
					startActivity(intent);
					finish();
				}  
				else if(join_code == 3) { 
					JoinCodeInfoParcel joinData = new JoinCodeInfoParcel();
					joinData.join_code = join_code;
					Intent intent = new Intent(LoginActivity.this, CoupleRequestActivity.class); 
					intent.putExtra("joinData", joinData); 
					startActivity(intent); 
					finish();
				} else if(join_code == 4) {
					notInputJoinDetail();
				} else if(join_code == 5) {
					Dialog();
				}
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	} 
	protected void notInputJoinDetail() {
		// TODO Auto-generated method stub  
			if(user_req == 1) {
				Intent intent = new Intent(LoginActivity.this, FirstMeetingActivity.class);
				startActivity(intent); 
			} else if(user_req == 0) {
				Intent intent = new Intent(LoginActivity.this, BirthDayInfoActivity.class);
				startActivity(intent); 
			}  
	}


		protected void Dialog() {
	
			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this); 
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("Alert Dialog");
			builder.setMessage("상대방이 탈퇴하였습니다. 모든 정보가 삭제됩니다.");
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(LoginActivity.this, "Yes Click", Toast.LENGTH_SHORT).show();
				}
			}); 
			builder.create().show();
}
		
		public void acceptLoginDialog() { 
			
			AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
			builder.setIcon(R.drawable.ic_launcher);
			builder.setTitle("중복로그인");
			builder.setMessage("중복 로그인 시도 입니다 허용하시겠습니까.");
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {   
					NetworkManager.getInstnace().acceptLogin(LoginActivity.this, user_no, reg_id, user_phone, user_regid_old, user_phone_old, new OnResultListener<LoginResult>() {

						@Override
						public void onSuccess(LoginResult result) { 
							finish();
							login();
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
					Toast.makeText(LoginActivity.this, "No Click", Toast.LENGTH_SHORT).show();
				}
			}); 
//			builder.setCancelable(false);
			
			builder.create().show();
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
