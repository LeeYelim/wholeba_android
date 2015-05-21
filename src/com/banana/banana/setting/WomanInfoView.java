package com.banana.banana.setting;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.banana.banana.R;
import com.banana.banana.main.MainItemData;
import com.banana.banana.main.MainItemView.OnPopupImageClickListener;

public class WomanInfoView extends FrameLayout{

		EditText period_startView, period_endView, period_cycleView;

		PeriodItems mData;
		public WomanInfoView(Context context) {
			super(context);
			init();
				// TODO Auto-generated constructor stub
		}

		public WomanInfoView(Context context, AttributeSet attrs) {
			super(context, attrs);
			init();
		}
		
		private void init() {
			// TODO Auto-generated method stub
			LayoutInflater.from(getContext()).inflate(R.layout.woman_info_list_item, this);
			period_startView = (EditText)findViewById(R.id.period_start);
			period_endView = (EditText)findViewById(R.id.period_end);
			period_cycleView = (EditText)findViewById(R.id.period_cycle);
			
			period_startView.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					String startString = s.toString();
					if (startString != null && !startString.equals("")) {
						mData.period_start = startString;
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
			
			period_endView.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					String endString = s.toString();
					if (endString != null && !endString.equals("")) {
						mData.period_end = endString;
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

			period_cycleView.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					String cycleString = s.toString();
					if (cycleString != null && !cycleString.equals("")) {
						mData.period_cycle = Integer.parseInt(cycleString);
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
			
		
		
		}
		
		public void setData(PeriodItems data) {
			mData = data;
			period_startView.setText(data.period_start);
			period_endView.setText(data.period_end);
			period_cycleView.setText(""+data.period_cycle);
		} 
			
			
			
}
