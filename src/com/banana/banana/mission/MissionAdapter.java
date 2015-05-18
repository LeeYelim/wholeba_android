package com.banana.banana.mission;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MissionAdapter extends BaseAdapter{
	ArrayList<MissionItemData> Missionitems =new ArrayList<MissionItemData>();
		Context mcontext;
		MissionItemView itemView;
		public MissionAdapter(Context mcontext) {
			
			this.mcontext = mcontext;
			itemView=new MissionItemView(mcontext);
		}

		public void add(MissionItemData item)
		{
			
			Missionitems.add(item);
			notifyDataSetChanged();
			
		}
		public void addAll(List<MissionItemData> items)
		{
			this.Missionitems.addAll(items);
			notifyDataSetChanged();
		
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Missionitems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return Missionitems.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			MissionItemView v=new MissionItemView(mcontext);
			try {
				v.setItemData(Missionitems.get(position));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return v;
		}

		public void clear() {
			Missionitems.clear();
			notifyDataSetChanged();
		}

}
