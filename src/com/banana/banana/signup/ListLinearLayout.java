package com.banana.banana.signup;

import java.util.ArrayList;
import java.util.List;

import com.banana.banana.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ListLinearLayout extends LinearLayout {
 
	public ListLinearLayout(Context context) {
		super(context);
		init();
	}

	public ListLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init() {
		setOrientation(LinearLayout.VERTICAL);
	}
	
	List<SyndromeList> list = new ArrayList<SyndromeList>();
	public static final int ITEM_ADDED = 1;
	public static final int ITEM_REMOVED = 2;
	public static final int ITEM_NO_ACTION = 3;
	public static final int DEFAULT_MAX_SIZE = 3;
	int mMax = DEFAULT_MAX_SIZE;
	public void setMax(int max) {
		mMax = max;
	}
	
	public int set(SyndromeList data, View v) {
		int index = -1;
		for (int i = 0; i < list.size(); i++) {
			SyndromeList item = list.get(i);
			if (item.syndrome_no == data.syndrome_no) {
				index = i; 
				v.setSelected(false);
				break;
			}
		}
		if (index != -1) {
			list.remove(index);
			v.setSelected(false);
			notifyDataSetChanged();
			return ITEM_REMOVED;
		}
		
		if (list.size() < mMax) {
			SyndromeList item = new SyndromeList(data.syndrome_no, data.syndrome_before, data.syndrome_after);
			list.add(item);
			v.setSelected(true);
			notifyDataSetChanged(); 
			return ITEM_ADDED;
		}
		
		Toast.makeText(getContext(), "최대 3개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show();
		return ITEM_NO_ACTION;
	}
	
	public void notifyDataSetChanged() {
		int childCount = getChildCount();
		if (childCount < list.size()) {
			for (int i = childCount; i < list.size(); i++) {
				SymtomItemView v = new SymtomItemView(getContext());
				addView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));		
			}
		} else if (childCount > list.size()) {
			for (int i = childCount; i > list.size() ; i--) {
				removeViewAt(i - 1);
			}
		}
		
		for (int i = 0; i < list.size();i++) {
			SymtomItemView v = (SymtomItemView)getChildAt(i);
			v.setItemData(list.get(i));
		}
	}
	public List<SyndromeList> getSyndromeList() {
		return list;
	}

}
