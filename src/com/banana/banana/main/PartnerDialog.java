package com.banana.banana.main;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.banana.banana.R;

public class PartnerDialog extends PopupWindow{
	Context mContext;
	ImageView feelOneView, feelTwoView, feelThreeView;

	public interface OnChangePartnerFeelingListener{
		public void OnFeelingClick(View view);
	} 
	
	OnChangePartnerFeelingListener changelistener;
	
	public void setOnChangePartnerFeelingListener(OnChangePartnerFeelingListener listener) {
		changelistener = listener;
	}
	
	public PartnerDialog(Context context) {
		super(context);
		mContext = context;
		
		int width = context.getResources().getDimensionPixelSize(R.dimen.popup_main_item_width);
		int height = context.getResources().getDimensionPixelSize(R.dimen.popup_main_item_height);
		setWidth(width);
		setHeight(height);
		setOutsideTouchable(true);
		setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

		View view = LayoutInflater.from(context).inflate(R.layout.partner_dialog, null);
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
