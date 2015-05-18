package com.banana.banana.signup;
 

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.banana.banana.JoinCodeInfoParcel;
import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class CoupleRequestFragment extends Fragment {

	EditText edit_request_number; 
	Button btnRequest;
	String user_gender, auth_phone;
	int join_code;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		btnRequest = (Button)view.findViewById(R.id.btn_couple_request);
		edit_request_number = (EditText)view.findViewById(R.id.edit_number); 
		edit_request_number.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
		edit_request_number.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		
		user_gender = PropertyManager.getInstance().getUserGender();  
		
		Bundle bundle = getActivity().getIntent().getExtras();
		if (bundle == null) {
			bundle = new Bundle();
		}
		JoinCodeInfoParcel joinData = bundle.getParcelable("joinData");
		join_code = joinData.join_code;  
		
		if(join_code == 3) {
			btnRequest.setEnabled(false);
		}
		btnRequest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				auth_phone = edit_request_number.getText().toString(); 
				if(auth_phone.length()!=13) {
					//String num1 = number.substring(0, 3);
					//String num2 = number.substring(3, 7);
					//String num3 = number.substring(7, 11); 
					//auth_phone = num1+"-"+num2+"-"+num3; 
					Toast.makeText(getActivity(), "번호를 다시 입력", Toast.LENGTH_SHORT).show();
				} else {
					NetworkManager.getInstnace().coupleAsk(getActivity(), auth_phone, user_gender, new OnResultListener<JoinResult>() {
					
					@Override
					public void onSuccess(JoinResult result) {   
						//버튼 비활성화
						sendSMS(auth_phone, "커플요청"); 
						btnRequest.setEnabled(false);
					}
					
					@Override
					public void onFail(int code) { 
						
					}
				});  
			}  
			}  
		});
	 	 
		return view;
	}
	

	 private void sendSMS(String phoneNumber, String message)
	    {        
	        SmsManager sms = SmsManager.getDefault();
	        sms.sendTextMessage(phoneNumber, null, message, null, null);        
	    }    

}
