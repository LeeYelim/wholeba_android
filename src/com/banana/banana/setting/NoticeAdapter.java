package com.banana.banana.setting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.media.audiofx.NoiseSuppressor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

public class NoticeAdapter extends BaseExpandableListAdapter{
	ArrayList<NoticeItems> Noticeitems =new ArrayList<NoticeItems>();
	Context mcontext;
	NoticeGroupView noticeView;
	
	public NoticeAdapter(Context mcontext) {
		
		this.mcontext = mcontext;
		noticeView=new NoticeGroupView(mcontext);
	}

	public void add(int notice_no, NoticeItems data)
	{
		NoticeItems group = null;
		for(NoticeItems item : Noticeitems) {
			if(item.notice_no == notice_no) {
				group = item;
				break;
			}
		}
		
		if(group == null) {
			group = new NoticeItems();
			group.notice_no = data.notice_no;
			group.notice_title = data.notice_title;
			group.notice_regdate = data.notice_regdate;
			Noticeitems.add(group);
		}
		group.children.add(data); 
		notifyDataSetChanged();
		
	} 

	public void clear() {
		 for(int i = 0; i < Noticeitems.size(); i++){
			 Noticeitems.get(i).children.clear();
	      }
		 Noticeitems.clear();
	     notifyDataSetChanged(); 
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return Noticeitems.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return Noticeitems.get(groupPosition).children.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return Noticeitems.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return Noticeitems.get(groupPosition).children.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return (long)groupPosition << 32;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return ((long)groupPosition) << 32 | (long)childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		NoticeGroupView group;
		if(convertView == null) {
			group = new NoticeGroupView(mcontext);
		} else {
			group = (NoticeGroupView)convertView;
		}
		group.setItemData(Noticeitems.get(groupPosition));
		
		return group;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		NoticeChildView child;
		if(convertView == null) {
			child = new NoticeChildView(mcontext);
		} else {
			child = (NoticeChildView)convertView;
		}
		child.setItemData(Noticeitems.get(groupPosition).children.get(childPosition));
		return child;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
