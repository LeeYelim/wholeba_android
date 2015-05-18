package com.banana.banana.setting;

import com.banana.banana.R;
import com.banana.banana.mission.MissionItemData;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeView extends FrameLayout{
	NoticeItems mData;
	TextView notice_no,notice_date,notice_title,notice_contents;
	public NoticeView(Context context) {
		super(context);
		init();
	}

	public NoticeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.activity_notice_list, this);
		notice_no=(TextView)findViewById(R.id.text_noticeNo);
		notice_date=(TextView)findViewById(R.id.text_noticeDate);
		notice_title=(TextView)findViewById(R.id.text_noticeTitle);
		notice_contents=(TextView)findViewById(R.id.text_noticeContent);
		
	}
	public void setItemData(NoticeItems data) {
		mData = data;
		notice_no.setText(""+mData.notice_no);
		notice_date.setText(mData.notice_date);
		notice_title.setText(mData.notice_title);
		notice_contents.setText(mData.notice_content);
		
	}
}
