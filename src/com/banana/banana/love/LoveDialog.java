package com.banana.banana.love;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class LoveDialog extends DialogFragment {
	
	EditText LoveDayView, LoveYearView, LoveMonthView;
	Button btnOk, btnDelete; 
	int iscondom, relation_no;
	String code;
	String loveday, loves_date;
	RadioGroup isCondomView; 
	RadioButton condomView, notCondomView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, 0);
		 
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.love_dialog, container, false);
		

		Bundle b = getArguments();
		code = b.getString("code");
		
		btnOk = (Button)view.findViewById(R.id.btn_love_ok);
		btnDelete = (Button)view.findViewById(R.id.btn_love_delete); 
		LoveDayView = (EditText)view.findViewById(R.id.edit_love_day);
		LoveMonthView = (EditText)view.findViewById(R.id.edit_love_month);
		LoveYearView = (EditText)view.findViewById(R.id.edit_love_year);

		condomView = (RadioButton)view.findViewById(R.id.radio_condom);
		notCondomView = (RadioButton)view.findViewById(R.id.radio_nocondom);
		isCondomView = (RadioGroup)view.findViewById(R.id.RadioGroup1);
		
		isCondomView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.radio_condom:
					iscondom = 1;
					break;

				case R.id.radio_nocondom:
					iscondom = 0;
					break;
				} 
			}
		});  
		
			if(code.equals("1")) { 
				LoveDayView.setText("");
			} else { 
				initLoveDialogData();
			} 
		
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub 
				if(!code.equals("1")) {  
					String year = LoveYearView.getText().toString();
					String month = LoveMonthView.getText().toString();
					String day = LoveDayView.getText().toString();
					loveday = year+"-"+month+"-"+day;
					 modifyLove(relation_no, iscondom, loveday);
				} else if(code.equals("1")){
					addLove(iscondom);
				}
			}
		});
		
		btnDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!code.equals("1")) { 
					deleteLove(relation_no);
				} else {  
					Toast.makeText(getActivity(), "삭제할 성관계가 없습니다.", Toast.LENGTH_SHORT).show();
				}  
			}
		}); 
		return view;
	}
	
	private void deleteLove(int relation_no) {
		NetworkManager.getInstnace().deleteLove(getActivity(), relation_no, new OnResultListener<LoveSearchResult>() {

			@Override
			public void onSuccess(LoveSearchResult result) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), LoveActivity.class);  
				i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				Toast.makeText(getActivity(), result.result.message, Toast.LENGTH_SHORT).show();
				dismiss();  
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void initLoveDialogData() { 
				Bundle b = getArguments();  
				String loveDate = b.getString("loveDate");
				iscondom = b.getInt("lovesCondom"); 
				relation_no = b.getInt("lovesNo");  
				
				StringTokenizer tokens = new StringTokenizer(loveDate);
				String loveYear = tokens.nextToken("-");
				String loveMonth = tokens.nextToken("-");
				String loveDay = tokens.nextToken("-");
				LoveYearView.setText(loveYear);
				LoveMonthView.setText(loveMonth);
				LoveDayView.setText(loveDay); 
				if(iscondom == 1) {
					condomView.setChecked(true);
				} else {
					notCondomView.setChecked(true);
				} 
			}
 
		
	protected void addLove(int iscondom) {
		// TODO Auto-generated method stub
		 
		String year = LoveYearView.getText().toString();
		String month = LoveMonthView.getText().toString();
		String day = LoveDayView.getText().toString();
		loves_date = year+"-"+month+"-"+day;
		
		NetworkManager.getInstnace().addLove(getActivity(), iscondom, loves_date, new OnResultListener<LoveSearchResult>() {

			@Override
			public void onSuccess(LoveSearchResult result) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), LoveActivity.class);
				i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				Toast.makeText(getActivity(), result.result.message, Toast.LENGTH_SHORT).show();
				dismiss();
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	private void modifyLove(int relation_no, int is_condom, String date) {
		NetworkManager.getInstnace().modifyLove(getActivity(), relation_no, is_condom, date, new OnResultListener<LoveSearchResult>() {

			@Override
			public void onSuccess(LoveSearchResult result) {
				Intent i = new Intent(getActivity(), LoveActivity.class);
				i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				Toast.makeText(getActivity(), result.result.message, Toast.LENGTH_SHORT).show();
				dismiss();
				
			}

			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}
 
		
	}
	 
	
