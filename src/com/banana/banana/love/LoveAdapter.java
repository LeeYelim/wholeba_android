package com.banana.banana.love;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
 
public class LoveAdapter  extends BaseAdapter implements LoveItemView.OnMainViewClickListener {

	Context mcontext;
	LoveItemView loveView;
	List<LoveItem> loves = new ArrayList<LoveItem>();
	
	public LoveAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.mcontext=context;
		loveView=new LoveItemView(mcontext);
		
	}

	public void add(LoveItem item) {
		loves.add(item);
		notifyDataSetChanged();
	}
	 
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return loves.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return loves.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LoveItemView lv=new LoveItemView(mcontext);
		lv.setData(loves.get(position));
		return lv;
		 
	}


	public interface OnAdapterViewListener {
		public void onAdapterViewAction(Adapter adapter,View view, LoveItemData data);
	}
	
	OnAdapterViewListener viewAdapterListener;
	public void setOnAdapterViewListener(OnAdapterViewListener listener) {
		viewAdapterListener = listener;
	}
	
   

	public void clear() {
		loves.clear();
		notifyDataSetChanged();
	}

	@Override
	public void OnMainViewClick(View view, LoveItemData data) {
		// TODO Auto-generated method stub
		if (viewAdapterListener != null) {
			viewAdapterListener.onAdapterViewAction(this, view, data);
		}
	}

	public void addAll(ArrayList<LoveItem> items) {
		// TODO Auto-generated method stu 
		loves.addAll(items);
		notifyDataSetChanged();
		
	}
}
