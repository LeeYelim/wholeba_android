package com.banana.banana.love;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.banana.banana.R;

public class LoveItemView extends FrameLayout {
	LoveItem mData;
	
	TextView LoveDateView;
	//ProgressBar LoveProgressBar;
	TextView PossibilityView;
	ImageView LoveProgressBar;
	ClipDrawable cd;
	
	//ImageView LoveImage;
	//ImageView LoveCondom;
	/*ImageView CondomImage1;
	ImageView CondomImage2;
	TextView EditView;
	EditText EditYear;
	EditText EditMonth;
	EditText EditDate;
	
	View MainView;
	View LoveEditView;
	*/
	
	public LoveItemView(Context context) {
		super(context);
		init();
	}

	public LoveItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public interface OnMainViewClickListener {
		public void OnMainViewClick(View view, LoveItemData data);
	}
	
	OnMainViewClickListener viewlistener;

	public void setOnMainViewClickListener(
			OnMainViewClickListener listener) {
		viewlistener = listener;
	}
	
	private void init() {
		// TODO Auto-generated method stub
		LayoutInflater.from(getContext()).inflate(R.layout.love_list_item_layout, this);
		LoveDateView = (TextView)findViewById(R.id.text_love_date);
		//LoveProgressBar = (ProgressBar)findViewById(R.id.love_progressBar);
		PossibilityView = (TextView)findViewById(R.id.num_love_possibility);
		//LoveProgressBar = (ProgressBar)findViewById(R.id.love_progressBar);
		LoveProgressBar = (ImageView)findViewById(R.id.love_progressBar);
		//LoveImage = (ImageView)findViewById(R.id.img_love);
		//LoveCondom = (ImageView)findViewById(R.id.img_condom);
		/*CondomImage1 = (ImageView)findViewById(R.id.img_condom1);
		CondomImage2 = (ImageView)findViewById(R.id.img_condom2);
		EditView = (TextView)findViewById(R.id.text_love_edit);
		EditYear = (EditText)findViewById(R.id.edit_love_year);
		EditMonth = (EditText)findViewById(R.id.edit_love_month);
		EditDate = (EditText)findViewById(R.id.edit_love_day);
		LoveEditView = (View)findViewById(R.id.Edit_love_layout);*/
	/*	MainView = (View)findViewById(R.id.Main_love_layout);
		MainView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(viewlistener != null) {
					viewlistener.OnMainViewClick(v, mData);
					
				}
			}
		});*/
		
	}
/*
	public boolean isVisibleDetailView() {
		return LoveEditView.getVisibility() == View.VISIBLE;
	}
	
	public void setVisibileDetailView(boolean isVisible) {
		LoveEditView.setVisibility(isVisible?View.VISIBLE:View.GONE);
	}*/

	public void setData(LoveItem data) {
		mData = data; 
		LoveDateView.setText(data.loves_date);
		//LoveProgressBar.setProgress((int)data.pregnancy_rate);
		if(data.loves_pregnancy>=70.0f){
			LoveProgressBar.setImageResource(R.drawable.love_clip2);
			cd=(ClipDrawable) LoveProgressBar.getDrawable();
			cd.setLevel((int)data.loves_pregnancy*100);
			
		}else{
			LoveProgressBar.setImageResource(R.drawable.love_clip);
			cd=(ClipDrawable) LoveProgressBar.getDrawable();
			cd.setLevel((int)data.loves_pregnancy*100);
		}
		
		PossibilityView.setText(""+data.loves_pregnancy); 
	}
}
