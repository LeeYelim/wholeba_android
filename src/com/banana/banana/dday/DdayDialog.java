package com.banana.banana.dday;

import java.util.StringTokenizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class DdayDialog extends DialogFragment { 

	DdayAdapter mDAdpater;
	EditText ddayNameView, ddayYearView, ddayDayView, ddayMonthView;
	//ToggleButton repeatbtn;
	int dday_repeat, code, id;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.dialog_dday, container, false);
		mDAdpater = new DdayAdapter();
		ddayNameView = (EditText)view.findViewById(R.id.edit_dday_name);
		ddayYearView = (EditText)view.findViewById(R.id.edit_dday_year);
		ddayMonthView = (EditText)view.findViewById(R.id.edit_dday_month);
		ddayDayView = (EditText)view.findViewById(R.id.edit_dday_day);
		//repeatbtn = (ToggleButton)view.findViewById(R.id.btn_dday_repeat);
		
		
		Bundle b = getArguments();
		code = b.getInt("code");
		if(code == 1) { 
			ddayNameView.setText("");
		} else {
			initDialogData();
		}
		
		Button btn = (Button)view.findViewById(R.id.btn_dday_delete);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(code != 1) { 
					deleteData(id);
				} else {  
					Toast.makeText(getActivity(), "삭제할 디데이가 없습니다.", Toast.LENGTH_SHORT).show();
				} 
			}
		});
		
		btn = (Button)view.findViewById(R.id.btn_dday_ok);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				String dName = ddayNameView.getText().toString();
				String dYear = ddayYearView.getText().toString();
				String dMonth = ddayMonthView.getText().toString();
				String dDay = ddayDayView.getText().toString();
				String dDate = dYear+"+"+dMonth+"+"+dDay;
				/*if(repeatbtn.isChecked()) {
					dday_repeat = 1;
				} else {
					dday_repeat = 0;
				}*/
				if(code != 1) { 
					editDdayData(id, dName, dDate, dday_repeat);
				} else {  
					addDdayData(dName, dDate, dday_repeat);
				}
			}
		});
		
		return view;
	} 
	 
	private void editDdayData(int no, String dName, String dDate,
			int dday_repeat) {
		NetworkManager.getInstnace().editDday(getActivity(), no, dName, dDate, dday_repeat, new OnResultListener<DdayResponse>() {

			@Override
			public void onSuccess(DdayResponse result) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(getActivity(), DdayActivity.class);
				i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				Toast.makeText(getActivity(), "edit ok", Toast.LENGTH_SHORT).show();
				dismiss();
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		}); 
	}

	private void addDdayData(String ddayname, String ddaydate, int ddayrepeat) { 
		NetworkManager.getInstnace().addDday(getActivity(), ddayname, ddaydate, ddayrepeat, new OnResultListener<DdayResponse>() {

			@Override
			public void onSuccess(DdayResponse result) { 
				Intent i = new Intent(getActivity(), DdayActivity.class);
				i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				Toast.makeText(getActivity(), "ok", Toast.LENGTH_SHORT).show();
				dismiss();
			}

			@Override
			public void onFail(int code) { 
			}
		});
	}

	private void deleteData(int id) {
		// TODO Auto-generated method stub
		NetworkManager.getInstnace().deleteDday(getActivity(), id, new OnResultListener<DdayResponse>() {
			
			@Override
			public void onSuccess(DdayResponse result) {
				Intent i = new Intent(getActivity(), DdayActivity.class);  
				i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				Toast.makeText(getActivity(), "delete", Toast.LENGTH_SHORT).show();
				dismiss();
			}
			
			@Override
			public void onFail(int code) { 
				
			}
		});
	}
	
	private void initDialogData() { 
	  
			Bundle b = getArguments();  
			String ddayDate = b.getString("ddayDate");
			String ddayName = b.getString("ddayName");
			StringTokenizer tokens = new StringTokenizer(ddayDate, "-");
			String ddayYear = tokens.nextToken();
			String ddayMonth = tokens.nextToken();
			String ddayDay = tokens.nextToken(); 
			ddayNameView.setText(ddayName);
			ddayYearView.setText(ddayYear);
			ddayMonthView.setText(ddayMonth);
			ddayDayView.setText(ddayDay);
			id = b.getInt("ddayNo");
	}} 
	 
	 
