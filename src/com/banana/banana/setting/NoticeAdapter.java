package com.banana.banana.setting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;



public class NoticeAdapter extends BaseAdapter{
	ArrayList<NoticeItems> Noticeitems =new ArrayList<NoticeItems>();
	Context mcontext;
	NoticeView noticeView;
	public NoticeAdapter(Context mcontext) {
		
		this.mcontext = mcontext;
		noticeView=new NoticeView(mcontext);
	}

	public void add(NoticeItems item)
	{
		
		Noticeitems.add(item);
		notifyDataSetChanged();
		
	}
	public void addAll(List<NoticeItems> items)
	{
		this.Noticeitems.addAll(items);
		notifyDataSetChanged();
	
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return Noticeitems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return Noticeitems.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NoticeView v=new NoticeView(mcontext);
		v.setItemData(Noticeitems.get(position));
		return v;
	}

	public void clear() {
		Noticeitems.clear();
		notifyDataSetChanged();
	}

}
