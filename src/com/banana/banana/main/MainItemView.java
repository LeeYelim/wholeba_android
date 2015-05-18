package com.banana.banana.main;

import android.R.bool;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.banana.banana.R;

public class MainItemView extends FrameLayout{

	TextView categoryView;
	ImageView popupView;
	MainItemData mData;
 
	public MainItemView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	
	public MainItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public interface OnPopupImageClickListener {
		public void OnPopupImageClick(View view, MainItemData data);
	}
	
	OnPopupImageClickListener imageListener;

	public void setOnPopupImageClickListener(
			OnPopupImageClickListener listener) {
		imageListener = listener;
	}
	
	private void init() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.main_list_item_layout, this);
		 
		categoryView = (TextView)findViewById(R.id.text_category);
		popupView = (ImageView)findViewById(R.id.img_popup);
		
		popupView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(imageListener != null) {
					imageListener.OnPopupImageClick(v, mData);
				}
			}
		});
		 
		
	}

	public void setOddBackgroundDraw(boolean bOdd) {
		if(bOdd == true) {
			setBackgroundResource(R.drawable.main_love_button);
			popupView.setBackgroundResource(R.drawable.main_love_popup_button);
		} else if(bOdd == false) {
			setBackgroundResource(R.drawable.main_mission_button);
			popupView.setBackgroundResource(R.drawable.main_mission_popup_button);
		}
	}
	
	public void setData(MainItemData data) {
		mData = data;
		categoryView.setText(data.category);
		popupView.setImageResource(data.popup);
	}
 
}
