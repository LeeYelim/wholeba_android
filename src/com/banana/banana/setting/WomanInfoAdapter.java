package com.banana.banana.setting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class WomanInfoAdapter extends BaseAdapter {
	
	Context mcontext;
	WomanInfoView womanInfoView;
	List<PeriodItems> womaninfo = new ArrayList<PeriodItems>();
	
	public WomanInfoAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.mcontext=context;
		womanInfoView = new WomanInfoView(mcontext);
	}
	
	public void add(PeriodItems item) {
		womaninfo.add(item);
		notifyDataSetChanged();
	}
	
	public void addAll(List<PeriodItems> items)
	{
		womaninfo.addAll(items);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return womaninfo.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return womaninfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		WomanInfoView lv = new WomanInfoView(mcontext);
		 lv.setData(womaninfo.get(position)); 
		return lv;
	}
	
	public void clear() {
		womaninfo.clear();
		notifyDataSetChanged();
	}
}
