package com.banana.banana.dday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.banana.banana.R;

public class DdayItemView extends FrameLayout{

	TextView ddayNumView;
	TextView ddayNameView;
	TextView ddayDateView;  
	
	public DdayItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.dday_list_item_layout, this);
		ddayNumView = (TextView)findViewById(R.id.text_dday_num);
		ddayNameView = (TextView)findViewById(R.id.text_dday_name);
		ddayDateView = (TextView)findViewById(R.id.text_dday_date);   
	} 

	public void setData(Ddayitem data) {  
			subDdate(data.dday_date);
			ddayNameView.setText(data.dday_name);
			//ddayDateView.setText(data.dday_date);
			StringTokenizer st = new StringTokenizer(data.dday_date, "-");
			String year = st.nextToken();
			String month = st.nextToken();
			String date = st.nextToken();
			getDayOfWeek(year, month, date);
	}
	
	protected void getDayOfWeek(String year, String month, String date) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month));
		cal.set(Calendar.DATE, Integer.parseInt(date));
		
		switch (cal.get(Calendar.DAY_OF_WEEK)){
	    case 1:
			ddayDateView.setText(year+"."+month+". "+"일요일");
	        break;
	    case 2:
	    	ddayDateView.setText(year+"."+month+". "+"월요일");
	        break;
	    case 3:
	    	ddayDateView.setText(year+"."+month+". "+"화요일");
	        break;
	    case 4:
	    	ddayDateView.setText(year+"."+month+". "+"수요일");
	        break;
	    case 5:
	    	ddayDateView.setText(year+"."+month+". "+"목요일");
	        break;
	    case 6:
	    	ddayDateView.setText(year+"."+month+". "+"금요일");
	        break;
	    case 7:
	    	ddayDateView.setText(year+"."+month+". "+"토요일");
	        break;
	    }
	     
	}
	
	protected void subDdate(String d) { //사귄일수 구함
		// TODO Auto-generated method stub 
		long d1, d2;
		Date today = new Date(); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		
		
		Date day;
		Calendar cal2 = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try { 
			day = sdf.parse(d);   
			cal2.setTime(day);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		d1 = cal.getTime().getTime();
		d2 = cal2.getTime().getTime();
		
		int days = (int)((d1-d2)/(1000*60*60*24)); 
		if(days<0) {
			ddayNumView.setText("D"+days);
		} else if(days>0) {
			ddayNumView.setText("D+"+Math.abs(days));
		} else if(days==0) {
			ddayNumView.setText("today");
		}
	}
}
