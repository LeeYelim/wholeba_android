/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.banana.banana.mission.card;

import java.util.ArrayList;

import com.banana.banana.R;
import com.banana.banana.mission.IngItemData;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

public class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {
	
	ArrayList<IngItemData> list = new ArrayList<IngItemData>();

//	public static int cardCount=0;
    // =============================================================================
    // Private members
    // =============================================================================

//    private int[] images = new int[10];
    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public IngItemData getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public void add(IngItemData data){
    	list.add(data);
    	notifyDataSetChanged();
    	
    }
    public void clear()
    {
    	list.clear();
    }
    @Override
    public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) { 
        
    	MissionCardView cardView;
    	if (reuseableView != null) {
        	cardView = (MissionCardView) reuseableView;
        } else {
        	cardView = new MissionCardView(viewGroup.getContext());
        	int width = viewGroup.getContext().getResources().getDimensionPixelSize(R.dimen.gallery_item_width);
        	int height = viewGroup.getContext().getResources().getDimensionPixelSize(R.dimen.gallery_item_height);
        	cardView.setLayoutParams(new FancyCoverFlow.LayoutParams(width, height));
        	//cardView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);           
           // imageView.setLayoutParams(new FancyCoverFlow.LayoutParams(400, 800));
        }
        cardView.setData(list.get(i));	
        //imageView.setImageResource(this.getItem(i));
        	
        return cardView;
    }
}
