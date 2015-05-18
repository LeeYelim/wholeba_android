package com.banana.banana.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.banana.banana.R;

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

				
			}
			
			public void setData(PeriodItems data) {
				mData = data;
				period_startView.setText(data.period_start);
				period_endView.setText(data.period_end);
				period_cycleView.setText("("+data.period_cycle+")");
			}
}
