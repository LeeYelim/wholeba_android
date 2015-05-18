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

import com.banana.banana.R;

public class MainDialog extends PopupWindow{
	Context mContext;
	ImageView feelOneView, feelTwoView, feelThreeView;

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
