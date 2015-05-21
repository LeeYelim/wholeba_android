package com.banana.banana.main;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;

public class MainDialog extends PopupWindow{
	Context mContext;
	ImageView feelOneView, feelTwoView, feelThreeView, feelFourView;

	public interface OnChangeFeelingListener{
		public void OnFeelingClick(View view);
	} 
	
	OnChangeFeelingListener changelistener;
	
	public void setOnChangeFeelingListener(OnChangeFeelingListener listener) {
		changelistener = listener;
	}
	
	public MainDialog(Context context) {
		super(context);
		mContext = context;
		
		//setWidth(LayoutParams.WRAP_CONTENT);
		//setHeight(LayoutParams.WRAP_CONTENT); 
		int width = context.getResources().getDimensionPixelSize(R.dimen.popup_feel_width);
		int height = context.getResources().getDimensionPixelSize(R.dimen.popup_feel_height);
		setWidth(width);
		setHeight(height);
		setOutsideTouchable(true);
		setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


		View view = LayoutInflater.from(context).inflate(R.layout.main_dialog, null);
		setContentView(view); 
		
		feelOneView = (ImageView)view.findViewById(R.id.img_feel1);
		feelTwoView = (ImageView)view.findViewById(R.id.img_feel2);
		feelThreeView = (ImageView)view.findViewById(R.id.img_feel3);
		feelFourView = (ImageView)view.findViewById(R.id.img_feel4);
		
		String gender = PropertyManager.getInstance().getUserGender();
		if(gender.equals("M")) {
			feelOneView.setImageResource(R.drawable.main_emotion_select_angry);
			feelTwoView.setImageResource(R.drawable.main_emotion_select_sick);
			feelThreeView.setImageResource(R.drawable.main_emotion_select_sexy);
			feelFourView.setImageResource(R.drawable.main_emotion_select_happy);
		} else if(gender.equals("F")) {
			feelOneView.setImageResource(R.drawable.woman_circle_angry);
			feelTwoView.setImageResource(R.drawable.woman_circle_sick);
			feelThreeView.setImageResource(R.drawable.woman_circle_sexy);
			feelFourView.setImageResource(R.drawable.woman_circle_happy);
		}
		
		
		
		
		feelOneView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(changelistener != null) {
					changelistener.OnFeelingClick(v);
				}
				dismiss();
			}
		});
		
		feelTwoView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(changelistener != null) {
					changelistener.OnFeelingClick(v);
				}
				dismiss();
			}
		});
		
		
		feelThreeView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(changelistener != null) {
							changelistener.OnFeelingClick(v);
						}
						dismiss();
					}
				});
		
		
		feelFourView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(changelistener != null) {
					changelistener.OnFeelingClick(v);
				}
				dismiss();
			}
		});
				
				
		
	}

	
	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, 0);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.main_dialog, container, false);
		return view;
	}*/
}
