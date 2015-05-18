package com.banana.banana.dday;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class DdayAdapter extends BaseAdapter {


	List<Ddayitem> ddaylist = new ArrayList<Ddayitem>();
	
	public DdayAdapter() { 
		// TODO Auto-generated constructor stub
	}
	
	public void addAll(List<Ddayitem> ddays) {
		ddaylist.addAll(ddays);
		notifyDataSetChanged();
	}

	public void add(Ddayitem item) {
		ddaylist.add(item);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ddaylist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ddaylist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		DdayItemView view;
		if(convertView == null) {
			view = new DdayItemView(parent.getContext());
		} else {
			view = (DdayItemView)convertView;
		}
		view.setData(ddaylist.get(position));	
		return view;
	}
 
}
