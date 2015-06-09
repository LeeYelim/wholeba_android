package com.banana.banana.setting;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.banana.banana.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class NoticeChildView extends FrameLayout {

	NoticeItems mData;
	
	public NoticeChildView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}
	
	public NoticeChildView(Context context, AttributeSet attrs) {
		super(context);
	}

	TextView noticeContent, noticeTime;
	
	public void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.notice_child_item, this); 
		noticeContent = (TextView)findViewById(R.id.notice_content_txt);
		noticeTime = (TextView)findViewById(R.id.notice_content_time_txt);		
	}
	
	public void setItemData(NoticeItems data) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat kdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분"); 
		
		mData = data;
		try {
			noticeTime.setText(kdf.format(df.parse(mData.notice_regdate)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		noticeContent.setText(mData.notice_content);
		
	}
}
