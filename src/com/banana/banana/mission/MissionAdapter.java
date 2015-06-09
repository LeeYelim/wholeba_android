package com.banana.banana.mission;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;

public class MissionAdapter extends BaseAdapter implements MissionItemView.OnMissionListClickListener{
	ArrayList<MissionItemData> missionitems =new ArrayList<MissionItemData>();
		Context mcontext;
		MissionItemView itemView;
		public MissionAdapter(Context mcontext) {
			
			this.mcontext = mcontext;
			itemView=new MissionItemView(mcontext);
		}

		public void add(MissionItemData item)
		{
			
			missionitems.add(item);
			notifyDataSetChanged();
			
		}
		public void addAll(List<MissionItemData> items)
		{
			this.missionitems.addAll(items);
			notifyDataSetChanged();
		
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return missionitems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return missionitems.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		 
		

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MissionItemView v;
			if(convertView == null) {
				v = new MissionItemView(mcontext); 
			} else {
				//v = (MissionItemView)convertView;
				v = new MissionItemView(mcontext); 
			} 
			v.setItemData(missionitems.get(position));
			v.setOnMissionListClickListener(this);
			
			return v;
			/*
			MissionItemView v;
			if(convertView == null) {
				v = new MissionItemView(mcontext); 
			} else {
				v = new MissionItemView(mcontext); 
			}
			try {
				v.setItemData(Missionitems.get(position));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return v;
		/*	MissionItemView v=new MissionItemView(mcontext);
			try {
				v.setItemData(Missionitems.get(position));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return v;*/
		}
		
		public void clear() {
			missionitems.clear();
			notifyDataSetChanged();
		}


		public interface OnAdapterMissionListener {
			public void onAdapterMissionAction(Adapter adapter,View view, MissionItemData data);
		}
		
		OnAdapterMissionListener missionAdapterListener;
		public void setOnAdapterMissionListener(OnAdapterMissionListener listener) {
			missionAdapterListener = listener;
		} 
		
		@Override
		public void OnMissionListClick(View view, MissionItemData data) {
			// TODO Auto-generated method stub
			if (missionAdapterListener != null) {
				missionAdapterListener.onAdapterMissionAction(this, view, data);
			}
		}
}