package com.banana.banana.setting;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.banana.banana.R;
import com.banana.banana.mission.MissionItemData;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class NoticeGroupView extends FrameLayout{
	NoticeItems mData;
	TextView notice_title, notice_time;
	public NoticeGroupView(Context context) {
		super(context);
		init();
	}

	public NoticeGroupView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.notice_group_item, this); 
		notice_time = (TextView)findViewById(R.id.notice_time_txt);
		notice_title = (TextView)findViewById(R.id.notice_title_txt);
				
		
	}
	public void setItemData(NoticeItems data) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat kdf = new SimpleDateFormat("MM/dd"); 
		
		mData = data;
		try {
			notice_time.setText(kdf.format(df.parse(mData.notice_regdate)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		notice_title.setText(mData.notice_title);
		
	}
}
