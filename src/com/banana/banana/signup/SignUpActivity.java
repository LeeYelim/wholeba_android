package com.banana.banana.signup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.banana.banana.JoinCodeInfoParcel;
import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.login.LoginResult;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class SignUpActivity extends ActionBarActivity {

	Button btn_next;
	EditText idView, pwdView, pwdOkView; 
	CheckBox chk_agree;
	String pwd, pwdOk;  
	TextView TextpwdOK;
	int joincode = 2;   
	int user_req = 0;  
 	String reg_id = PropertyManager.getInstance().getRegistrationId(); 
	String user_id, user_pass, user_phone; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		chk_agree = (CheckBox)findViewById(R.id.chk_agree1);
		btn_next = (Button)findViewById(R.id.btn_next);
		idView = (EditText)findViewById(R.id.edit_id);
		pwdView = (EditText)findViewById(R.id.edit_pwd);
		pwdOkView = (EditText)findViewById(R.id.edit_pwd_ok);
		TextpwdOK = (TextView)findViewById(R.id.text_pwd_ok);
		
		pwdOkView.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus == false) {
					pwdOk();
				}
			}
		});
		
		btn_next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(chk_agree.isChecked() && pwdOk() == true) {
					user_id= idView.getText().toString();
				    user_pass = pwdView.getText().toString();				
				    TelephonyManager telManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); 
				    user_phone = telManager.getLine1Number();
				    if(user_phone.startsWith("+82")){
			 			user_phone = user_phone.replace("+82", "0");
			 		}
				    String num1 = user_phone.substring(0, 3);
					String num2 = user_phone.substring(3, 7);
					String num3 = user_phone.substring(7, 11);
					user_phone = num1+"-"+num2+"-"+num3;
			 	
				    //user_phone = "010-0000-0000"; for 강사님
					
					Date today = new Date(); 
					Calendar cal = Calendar.getInstance();
					cal.setTime(today); 
					String user_regdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
					
					NetworkManager.getInstnace().addjoin(SignUpActivity.this, user_id, user_pass, user_phone, reg_id, user_regdate, new OnResultListener<JoinResult>() {
					
					@Override
					public void onSuccess(JoinResult result) {
						// TODO Auto-generated method stub
						PropertyManager.getInstance().setUserId(user_id);
						PropertyManager.getInstance().setPassword(user_pass);
						login();
						
						//Login();
					} 
					@Override
					public void onFail(int code) { 
					}
				}); 
			}  
			}
		});
	}

	
	private void login() {
		NetworkManager.getInstnace().login(SignUpActivity.this, user_id, user_pass, user_phone, reg_id, new OnResultListener<LoginResult>() {

			@Override
			public void onSuccess(LoginResult result) {
				// TODO Auto-generated method stub
				searchJoinInfo();
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void searchJoinInfo() { 
		NetworkManager.getInstnace().searchJoinInfo(SignUpActivity.this, new OnResultListener<JoinResult>() {
			@Override
			public void onSuccess(JoinResult result) {
					int jcode = result.result.items.join_code;  
				if (jcode == 1) {
					//Bundle bundle = new Bundle();
					//bundle.putInt("join_code", jcode);
					JoinCodeInfoParcel joinData = new JoinCodeInfoParcel();
					joinData.join_code = jcode; 
					Intent intent = new Intent(SignUpActivity.this, SexInfoActivity.class);
					intent.putExtra("joinData", joinData); 
					//intent.putExtras(bundle);
					startActivity(intent);
					finish();
				} else if(jcode == 2) {
					//Bundle bundle = new Bundle();
					//bundle.putInt("join_code", jcode); 
					PropertyManager.getInstance().setUserGender(result.result.items.user_gender);
					JoinCodeInfoParcel joinData = new JoinCodeInfoParcel();
					joinData.join_code = jcode;
					Intent intent = new Intent(SignUpActivity.this, CoupleRequestActivity.class);
					//intent.putExtras(bundle); 
					intent.putExtra("joinData", joinData); 
					startActivity(intent);
					finish();
				}  
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/*private void Login() {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().login(SignUpActivity.this, user_id, user_pass, user_phone, reg_id, new OnResultListener<LoginResult>() {

			@Override
			public void onSuccess(LoginResult result) {
				autoLogin();		
				
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	protected void autoLogin() {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().autoLogin(SignUpActivity.this, user_no, user_phone, new OnResultListener<AutoLoginResponse>() {

			@Override
			public void onSuccess(AutoLoginResponse result) {
				// TODO Auto-generated method stub
				//searchJoinInfo();
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}*/

	
		
		/*
		NetworkManager.getInstnace().searchJoinInfo(SignUpActivity.this, user_no, new OnResultListener<JoinResult>() {

			@Override
			public void onSuccess(JoinResult result) {
				// TODO Auto-generated method stub
				int join_code = result.result.items.join_code;
				if(join_code == 0) { 
					Bundle b = new Bundle();
					b.putInt("join_code", join_code);
					Intent intent = new Intent(SignUpActivity.this, SexInfoActivity.class); 
					intent.putExtras(b);
					intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
	 				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
	 				startActivity(intent); 
					finish();
					Toast.makeText(SignUpActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
				} else if(join_code == 1){
					Bundle b = new Bundle();
					b.putInt("join_code", join_code);
					Intent intent = new Intent(SignUpActivity.this, CoupleRequestActivity.class); 
					intent.putExtras(b);
					intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
	 				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
	 				startActivity(intent); 
					finish();
					Toast.makeText(SignUpActivity.this, result.result.message, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}*/

	public boolean pwdOk() {
		// TODO Auto-generated method stub
		pwd = pwdView.getText().toString();
		pwdOk = pwdOkView.getText().toString();
		if(!pwd.equals("") && !pwdOk.equals("")) {
			if(pwd.equals(pwdOk)) {
				TextpwdOK.setText("암호일치");
				return true;
			} else {
				TextpwdOK.setText("암호불일치");
				return false;
			}
		} else {
			TextpwdOK.setText("");
			return false;
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
