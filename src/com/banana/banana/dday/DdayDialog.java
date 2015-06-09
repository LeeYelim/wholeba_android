package com.banana.banana.dday;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.w3c.dom.Text;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.LinearLayout.LayoutParams;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class DdayDialog extends DialogFragment { 

	DdayAdapter mDAdpater;
	EditText ddayNameView, ddayYearView, ddayDayView, ddayMonthView;
	//ToggleButton repeatbtn;
	int dday_repeat, code, id;
	String dYear, dMonth, dDay;
	int year, month, day;
	Calendar cal;
	Button btnOk, btnDelete;
	TextView text_dday_title;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.dialog_dday, container, false);
		mDAdpater = new DdayAdapter();
		text_dday_title = (TextView)view.findViewById(R.id.text_dday_title);
		ddayNameView = (EditText)view.findViewById(R.id.edit_dday_name);
		ddayYearView = (EditText)view.findViewById(R.id.edit_dday_year);
		ddayMonthView = (EditText)view.findViewById(R.id.edit_dday_month);
		ddayDayView = (EditText)view.findViewById(R.id.edit_dday_day);
		//repeatbtn = (ToggleButton)view.findViewById(R.id.btn_dday_repeat);
		btnDelete = (Button)view.findViewById(R.id.btn_dday_delete);
		btnOk = (Button)view.findViewById(R.id.btn_dday_ok);
		
		Bundle b = getArguments();
		code = b.getInt("code");
		if(code == 1) { 
			ddayNameView.setText("");
			btnDelete.setVisibility(View.GONE);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			btnOk.setLayoutParams(params);			
		} else {
			text_dday_title.setText("디데이 수정");
			initDialogData();
		}
		
		btnDelete.setOnClickListener(new View.OnClickListener() {
			
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
		
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		cal = Calendar.getInstance();
		cal.setTime(date); 
			
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				String dName = ddayNameView.getText().toString();
				dYear = ddayYearView.getText().toString();
				dMonth = ddayMonthView.getText().toString();
				dDay = ddayDayView.getText().toString();
				if(!(dYear.equals("") || dMonth.equals("") || dDay.equals(""))) {
					year = Integer.parseInt(dYear);
					month = Integer.parseInt(dMonth);
					day = Integer.parseInt(dDay);
				}
				
				if(dYear.length() < 4 || dYear.equals("") || dMonth.equals("") || dDay.equals("") || month > 12 || month <= 0 || day > 31 || day <= 0) {
					Toast.makeText(getActivity(), "날짜를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
				} else {
					String dDate = dYear+"-"+dMonth+"-"+dDay; 
				
					if(code != 1) { 
						editDdayData(id, dName, dDate, dday_repeat);
					} else {  
						addDdayData(dName, dDate, dday_repeat);
					}
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
				if(code == 1) {
					Toast.makeText(getActivity(), "날짜를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "디데이 생성 실패", Toast.LENGTH_SHORT).show();
				}
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
				dismiss();
			}

			@Override
			public void onFail(int code) { 
				if(code == 1) {
					Toast.makeText(getActivity(), "날짜를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "디데이 생성 실패", Toast.LENGTH_SHORT).show();
				}
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
	 
	 
