package com.banana.banana.signup;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.banana.banana.R;

public class SymtomItemView extends FrameLayout {
	TextView symtom, before, after;

	public SymtomItemView(Context context) {
		super(context);
		init();
	}

	public SymtomItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}


	EditText editBefore, editAfter;

	ImageView iconView;
	TextView titleView;
	SyndromeList mData;
	View detailView;


	public interface OnViewFocusDownListener {
		public void OnViewFocusDown(View view, String data, String data2);
	}

	OnViewFocusDownListener viewlistener;

	public void setOnViewFocusDownListener(OnViewFocusDownListener listener) {
		viewlistener = listener;
	}

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.symtom_layout, this);
		symtom = (TextView) findViewById(R.id.textView4);
		// before=(TextView)findViewById(R.id.textView1);
		// after=(TextView)findViewById(R.id.textView3);

		editBefore = (EditText) findViewById(R.id.edit_before);
		editAfter = (EditText) findViewById(R.id.edit_after);
		iconView = (ImageView) findViewById(R.id.img_mission1);
		detailView = (View) findViewById(R.id.detailView);

		editAfter.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String afterString = s.toString();
				if (afterString != null && !afterString.equals("")) {
					mData.syndrome_after = Integer.parseInt(afterString);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		editBefore.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String beforeString = s.toString();
				if (beforeString != null && !beforeString.equals("")) {
					mData.syndrome_before = Integer.parseInt(beforeString);
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		// titleView = (TextView)findViewById(R.id.textView1);
	}

	public boolean isVisibleDetailView() {
		return detailView.getVisibility() == View.VISIBLE;
	}

	public void setVisibileDetailView(boolean isVisible) {
		detailView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
	}

	public void setItemData(SyndromeList data) {
		mData = data;
		symtom.setText(SyndromeList.SYNDROME_NAME[data.syndrome_no - 1]);
		// Symtom.setText(mData.syndrome_name);

		// before.setText(mData.syndrome_before);
		// after.setText(mData.syndrome_after);
		// iconView.setImageResource(data.iconId);
		// titleView.setText(data.Missiontitle);
	}

	/*
	 * public String getTitle() { return mData; }
	 */

}
