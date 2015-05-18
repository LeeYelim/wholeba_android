package com.banana.banana.main;

import java.util.ArrayList;
import java.util.List;

import android.R;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

public class MainAdapter extends BaseAdapter implements MainItemView.OnPopupImageClickListener {


	List<MainItemData> categorys = new ArrayList<MainItemData>();
	
	public MainAdapter() {
		// TODO Auto-generated constructor stub
	}

	public void add(MainItemData item) {
		categorys.add(item);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return categorys.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return categorys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MainItemView view;
		if(convertView == null) {
			view = new MainItemView(parent.getContext());
			view.setOnPopupImageClickListener(this);
		} else {
			view = (MainItemView)convertView;
		}
		view.setData(categorys.get(position));
		if (position %2 == 0) {
			view.setOddBackgroundDraw(true);
		} else {
			view.setOddBackgroundDraw(false);
		}
		return view;
	}
	
	public interface OnAdapterImageListener {
		public void onAdapterImageAction(Adapter adapter,View view, MainItemData data);
	}
	
	OnAdapterImageListener imageAdapterListener;
	public void setOnAdapterImageListener(OnAdapterImageListener listener) {
		imageAdapterListener = listener;
	}

	@Override
	public void OnPopupImageClick(View view, MainItemData data) {
		// TODO Auto-generated method stub
		if (imageAdapterListener != null) {
			imageAdapterListener.onAdapterImageAction(this, view, data);
		}
	}
	
	public void clear() {
		categorys.clear();
		notifyDataSetChanged();
	}
 

}
