package com.banana.banana.signup;
 

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class OneFragment extends Fragment {

	EditText edit_request_number; 
	Button btnRequest;
	String user_gender, auth_phone;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		View view = inflater.inflate(R.layout.fragment_one, container, false);
		btnRequest = (Button)view.findViewById(R.id.btn_couple_request);
		edit_request_number = (EditText)view.findViewById(R.id.edit_number);
		Intent i = getActivity().getIntent();
		Bundle bundle = i.getExtras();  
		if (bundle == null) {
			bundle = new Bundle();
		}
		user_gender = bundle.getString("gender");  
		//edit_request_number.setText(user_gender);
		btnRequest.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String number = edit_request_number.getText().toString(); 
				if(number.length()>=10) {
					String num1 = number.substring(0, 3);
					String num2 = number.substring(3, 7);
					String num3 = number.substring(7, 11);
					auth_phone = num1+"-"+num2+"-"+num3;
				
				NetworkManager.getInstnace().coupleAsk(getActivity(), auth_phone, user_gender, new OnResultListener<JoinResult>() {
					
					@Override
					public void onSuccess(JoinResult result) {   
						Intent i = getActivity().getIntent();
						Bundle bundle = i.getExtras();  
						if (bundle == null) {
							bundle = new Bundle();
						}  
						Intent intent = new Intent(getActivity(), FirstMeetingActivity.class);
						intent.putExtras(bundle); 
						Toast.makeText(getActivity(), result.result.message, Toast.LENGTH_SHORT).show();
						startActivity(intent);
					}
					
					@Override
					public void onFail(int code) { 
						
					}
				});  
			} else {
				Toast.makeText(getActivity(), "번호를 다시 입력", Toast.LENGTH_SHORT).show();
			}
			}  
		});
	 	 
		return view;
	}
	
}
