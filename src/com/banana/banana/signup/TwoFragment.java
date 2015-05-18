package com.banana.banana.signup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.main.BananaMainActivity;

public class TwoFragment extends Fragment {


	EditText edit_response_number; 
	Button btnResponse;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		View view = inflater.inflate(R.layout.fragment_two, container, false);
		btnResponse = (Button)view.findViewById(R.id.btn_couple_response);
		edit_response_number = (EditText)view.findViewById(R.id.edit_number);
		
		btnResponse.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
				TelephonyManager telManager = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE); 
			 	String myphoneNum = telManager.getLine1Number();
			 	if(myphoneNum.startsWith("+82")){
			 		myphoneNum = myphoneNum.replace("+82", "0");
			 	}
			 	edit_response_number.setText(myphoneNum);
			 	NetworkManager.getInstnace().coupleAnswer(getActivity(), new OnResultListener<JoinResult>() {
					
					@Override
					public void onSuccess(JoinResult result) {
						Intent i = getActivity().getIntent();
						Bundle b = i.getExtras(); 
						Intent intent = new Intent(getActivity(), BirthDayInfoActivity.class);
						intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
						intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
						intent.putExtras(b);
						startActivity(intent);
					}
					
					@Override
					public void onFail(int code) {
						 
					}
				});
			   
			}
		});
	 	 
		return view;
	}
	
}
