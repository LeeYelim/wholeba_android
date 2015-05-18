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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.mission.IngItemData;
import com.banana.banana.mission.MissionIngResult;
import com.banana.banana.mission.MissionItemData;
import com.banana.banana.mission.MissionResult;
import com.banana.banana.mission.MissionSendPushActivity;
import com.banana.banana.mission.scratch.MissionCardScratchActivity;

public class SimpleExample extends Activity {
	HorizontalScrollView hView;
	Button btn_ok;
	private int item_cnt;
	private int theme_no;//미션 테마 
	private int mlist_no;
	private String mlist_name;//미션명
	
    // =============================================================================
    // Child views
    // =============================================================================

	FancyCoverFlow fancyCoverFlow;
    FancyCoverFlowSampleAdapter fAdapter;
    // =============================================================================
    // Supertype overrides
    // =============================================================================

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);
        btn_ok=(Button)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			Intent intent=new Intent(SimpleExample.this,MissionSendPushActivity.class);
			intent.putExtra("mlist_no",mlist_no);
			startActivity(intent);
			}
		});
        this.fancyCoverFlow = (FancyCoverFlow) this.findViewById(R.id.fancyCoverFlow);
        fAdapter=new FancyCoverFlowSampleAdapter();
        this.fancyCoverFlow.setAdapter(fAdapter);
       
        this.fancyCoverFlow.setUnselectedAlpha(100.0f);
        this.fancyCoverFlow.setUnselectedSaturation(0.0f);
        this.fancyCoverFlow.setUnselectedScale(0.5f);
        this.fancyCoverFlow.setSpacing(-150);
        this.fancyCoverFlow.setMaxRotation(0);
        this.fancyCoverFlow.setScaleDownGravity(0.2f);
        this.fancyCoverFlow.setActionDistance(FancyCoverFlow.ACTION_DISTANCE_AUTO);
       this.fancyCoverFlow.setSelection(0);
        
        this.fancyCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			
				
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
        
       NetworkManager.getInstnace().getMissionIngList(SimpleExample.this,new OnResultListener<MissionIngResult>() {
			
			@Override
			public void onSuccess(MissionIngResult result) {
				
				if(result.success==1){
					fAdapter.clear();
					item_cnt=result.result.item_cnt;//카드 총 갯수 
					
					//fAdapter.clear();
					for(int i=0;i<item_cnt;i++){
						IngItemData md;
						md=result.result.items.get(i);
						
						theme_no=md.theme_no;
						mlist_no=md.mlist_no;
						
						if(theme_no==1){//악마
							fAdapter.add(R.drawable.card1);
						}else if(theme_no==2){//처음
							fAdapter.add(R.drawable.card2);
							
						}else if(theme_no==3){//섹시
							fAdapter.add(R.drawable.card3);
							
						}else if(theme_no==4){//애교
							fAdapter.add(R.drawable.card4);
							
						}else if(theme_no==5){//천사
							fAdapter.add(R.drawable.card5);
							
						}
						
					}
					
				}
				
			}
			
			@Override
			public void onFail(int code) {
				
				
			}
		});
        
       
        
       
    }
  

}
