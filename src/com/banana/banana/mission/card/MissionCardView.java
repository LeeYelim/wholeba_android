package com.banana.banana.mission.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.banana.banana.R;
import com.banana.banana.mission.IngItemData;

public class MissionCardView extends FrameLayout {

	IngItemData mData;
	ImageView imgCardView;
	TextView textCardView; 
	public MissionCardView(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}
	
	public MissionCardView(Context context, AttributeSet attr) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.activity_mission_card_item, this);
		imgCardView = (ImageView)findViewById(R.id.img_mission_card);
		textCardView = (TextView)findViewById(R.id.text_mission_card); 
	}
	
	public void setData(IngItemData data) {
		mData = data;
		textCardView.setText(data.mlist_name);
		
		int theme = data.theme_no;
		if(theme == 1) { // 악마
			imgCardView.setImageResource(R.drawable.card1);
		} else if(theme==2){//처음
			imgCardView.setImageResource(R.drawable.card4);
			
		}else if(theme==3){//섹시
			imgCardView.setImageResource(R.drawable.card5);
			
		}else if(theme==4){//애교
			imgCardView.setImageResource(R.drawable.card3);
			
		}else if(theme==5){//천사
			imgCardView.setImageResource(R.drawable.card2);
			
		} 
		imgCardView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        
		 
	}
	
	

}
