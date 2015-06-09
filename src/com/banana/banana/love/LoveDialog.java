package com.banana.banana.love;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class LoveDialog extends DialogFragment {
	
	EditText LoveDayView, LoveYearView, LoveMonthView;
	Button btnOk, btnDelete; 
	int iscondom, relation_no, year, month, day;
	String code;
	String lovedate, yearS, monthS, dayS;
	RadioGroup isCondomView; 
	RadioButton condomView, notCondomView;
	Calendar cal;
	boolean isTrue;
	TextView text_love_title;
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
		text_love_title = (TextView)view.findViewById(R.id.text_love_title);
		btnOk = (Button)view.findViewById(R.id.btn_love_ok);
		btnDelete = (Button)view.findViewById(R.id.btn_love_delete); 
		LoveDayView = (EditText)view.findViewById(R.id.edit_love_day);
		LoveMonthView = (EditText)view.findViewById(R.id.edit_love_month);
		LoveYearView = (EditText)view.findViewById(R.id.edit_love_year);

		condomView = (RadioButton)view.findViewById(R.id.radio_condom);
		notCondomView = (RadioButton)view.findViewById(R.id.radio_nocondom);
		isCondomView = (RadioGroup)view.findViewById(R.id.RadioGroup1);
		
		int couple_condom = PropertyManager.getInstance().getCoupleCondom();
		if(couple_condom == 0) {
			notCondomView.setChecked(true);
			iscondom = 0;
		} else {
			condomView.setChecked(true);
			iscondom = 1;
		}
		
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
		
		
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		cal = Calendar.getInstance();
		cal.setTime(date); 
		
		
			if(code.equals("1")) { 
				LoveDayView.setText("");
				LoveYearView.setText(""+cal.get(Calendar.YEAR));
				btnDelete.setVisibility(View.GONE);
				RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				param.addRule(Gravity.CENTER_HORIZONTAL);
				btnOk.setLayoutParams(param);
			} else { 
				text_love_title.setText("러브 수정");
				initLoveDialogData();
			} 
		
		
		
		btnOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) { 
					yearS = LoveYearView.getText().toString();
					monthS = LoveMonthView.getText().toString();
					dayS = LoveDayView.getText().toString();
					if(!(yearS.equals("") || monthS.equals("") || dayS.equals(""))) {
						year = Integer.parseInt(yearS);
						month = Integer.parseInt(monthS);
						day = Integer.parseInt(dayS);
					}
					
					String d = yearS+"-"+monthS+"-"+dayS+" 00:00:00";
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					try {
						Date dateD = df.parse(d);
						isTrue = subDate(dateD);
						Log.i("dateD", ""+dateD);
					} catch (ParseException e) { 
						e.printStackTrace();
					}
					 
					if(yearS.length() < 4 || yearS.equals("") || monthS.equals("") || dayS.equals("") || 
							year>cal.get(Calendar.YEAR) || 
							month > 12 || month <= 0 || day > 31 || day <= 0 || isTrue == false) {
						Toast.makeText(getActivity(), "날짜를 다시 입력해 주세요", Toast.LENGTH_SHORT).show();
					} else {
						lovedate = yearS+"-"+monthS+"-"+dayS;
						if(!code.equals("1")) {   
							 modifyLove(relation_no, iscondom, lovedate);
						} else if(code.equals("1")){
							addLove(iscondom, lovedate);
						}
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
 
		
	protected void addLove(int iscondom, String love_date) { 
		int loves_ispopup = 0; 
		NetworkManager.getInstnace().addLove(getActivity(), iscondom, love_date, loves_ispopup, new OnResultListener<LoveSearchResult>() {

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
 
	public boolean subDate(Date d1) {
		Calendar car = Calendar.getInstance() ;
		Date d = new Date();
		d = car.getTime();
		long subday = d1.getTime() - d.getTime(); 
		
		if(subday > 0) {
			return false; 
		} else {
			return true;
		}
	} 
		
}
	 
	
