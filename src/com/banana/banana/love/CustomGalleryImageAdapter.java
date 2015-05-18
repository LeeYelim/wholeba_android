package com.banana.banana.love;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.banana.banana.MyApplication;
import com.banana.banana.R;

public class CustomGalleryImageAdapter extends BaseAdapter {
   private Context mContext;
    private TextView image;
    private LayoutInflater mInflater;
    View mview;
	    private int count;
	
	    String[] mImageID = (MyApplication.getContext().getResources().getStringArray(R.array.list_year));
	       
	    
	    //private String[] mImageID = { "1", "2", "3", "4"};
	 
	     public CustomGalleryImageAdapter(Context c) {
	         mContext = c;
	      mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
	         count = mImageID.length;
	     }
	 
	     @Override
	     public int getCount() {
	         return count;
	     }
	 
	    @Override
	     public Object getItem(int position) {
	        return position;
	     }
	 
	     @Override
	     public long getItemId(int position) {
	         return position;
	    }
	 
	     @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
         mview = convertView;
	 
	         if (mview == null) {
	            mview = mInflater.inflate(R.layout.gallery, null);
	         }
	
	         image = (TextView) mview.findViewById(R.id.image);
	         
	       
	         int width = 100;
	         int height = 100;
	         RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,height);
	         image.setLayoutParams(parms);
	         image.setText(mImageID[position]);
	        
	       

	        
	         return mview;
	    }
	     
	     
	    
	 }