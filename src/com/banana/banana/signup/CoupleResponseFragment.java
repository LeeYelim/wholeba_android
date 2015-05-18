package com.banana.banana.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class CoupleResponseFragment extends Fragment {


	EditText edit_response_number; 
	Button btnResponse;
	String requestPhone;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		View view = inflater.inflate(R.layout.fragment_two, container, false);
		init();
		
		btnResponse = (Button)view.findViewById(R.id.btn_couple_response);
		edit_response_number = (EditText)view.findViewById(R.id.edit_number);
		
		
		btnResponse.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {  
				CoupleAnswer();
			   
			}
		});
	 	 
		return view;
	}
	
	public void init() {

		//Intent i = getActivity().getIntent();
		//Bundle b = i.getExtras(); 
		//String jcode = b.getString("join_code");
		//int join_code = Integer.parseInt(jcode);
	

		 
		
		//-----원래는 여기서 그냥 요청만 searchJoininfo 하면됨 파라매터 없이  
	//다시 풀	
		NetworkManager.getInstnace().searchJoinInfo(getActivity(), new OnResultListener<JoinResult>() {

			@Override
			public void onSuccess(JoinResult result) { 
				requestPhone = result.result.items.partner_phone; 
			 	edit_response_number.setText(requestPhone);
			 	PropertyManager.getInstance().setUserGender(result.result.items.user_gender);

			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void CoupleAnswer() {

	 	NetworkManager.getInstnace().coupleAnswer(getActivity(), new OnResultListener<JoinResult>() {
			
			@Override
			public void onSuccess(JoinResult result) {
				Intent intent = new Intent(getActivity(), BirthDayInfoActivity.class);
				intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
				//intent.putExtras(b);
				startActivity(intent);
			}
			
			@Override
			public void onFail(int code) {
				 
			}
		});
	}
}
