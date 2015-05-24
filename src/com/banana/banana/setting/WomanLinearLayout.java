package com.banana.banana.setting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.banana.banana.signup.SymtomItemView;

public class WomanLinearLayout extends LinearLayout{

	public WomanLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public WomanLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		setOrientation(LinearLayout.VERTICAL);
	}
	

	List<PeriodItems> list = new ArrayList<PeriodItems>();
	public static final int ITEM_ADDED = 1;
	public static final int ITEM_REMOVED = 2;
	public static final int ITEM_NO_ACTION = 3; 
	
	
	public void clear() {
		list.clear();
		notifyDataSetChanged();
	}
	
	public int set(ArrayList<PeriodItems> data) { 
//			PeriodItems item = new PeriodItems(data.period_no, data.period_start, data.period_end, data.period_cycle);
//			list.add(item);
		list.addAll(data);
		notifyDataSetChanged();
		return ITEM_ADDED;  
	}
	
	public int set(PeriodItems data) { 
//		PeriodItems item = new PeriodItems(data.period_no, data.period_start, data.period_end, data.period_cycle);
//		list.add(item);
		list.add(data);
		notifyDataSetChanged();
		return ITEM_ADDED;  
}
	
	
	
	public void notifyDataSetChanged() {
		int childCount = getChildCount();
		if (childCount < list.size()) {
			for (int i = childCount; i < list.size(); i++) {
				WomanInfoView v = new WomanInfoView(getContext());
				addView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));		
			}
		} else if (childCount > list.size()) {
			for (int i = childCount; i > list.size() ; i--) {
				removeViewAt(i - 1);
			}
		}
		
		for (int i = 0; i < list.size();i++) {
			WomanInfoView v = (WomanInfoView)getChildAt(i);
			v.setData(list.get(i));
		}
	}
	public List<PeriodItems> getSyndromeList() {
		return list;
	}
 
	
}
