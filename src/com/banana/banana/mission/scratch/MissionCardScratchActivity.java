package com.banana.banana.mission.scratch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.mission.BananaItemResponse;
import com.banana.banana.mission.MissionActivity;
import com.banana.banana.mission.MissionResult;
import com.winsontan520.WScratchView;

public class MissionCardScratchActivity extends ActionBarActivity {
	private WScratchView scratchView;
	private TextView percentageView;
	private float mPercentage;
	Button btn_ok,btn_chance,btn_ok2, btn_out, btn_cancel;
	LinearLayout sView, hView; 
	ImageView lottoView;
	int item_no,mlist_no,theme_no, chipCount, age;
	String themeName, mission_name, mlist_regdate;
	ImageView themeView;
	TextView text_ThemeView,text_missionName, chip_countView;  
	
	private static final int[] ITEM_COIN_COUNT = { 1, 1, 2, 2, 2, 2, 2, 3};
	private int resIds[] = {R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8};
	TextView[] items = new TextView[resIds.length];
	int selectedIndex = -1;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_card_scratch);
		
		for (int i = 0; i < resIds.length;i++) {
			items[i] = (TextView)findViewById(resIds[i]);
			items[i].setTag((Integer)i);
		}

		Intent intent=getIntent();
		mlist_no=intent.getIntExtra("mlist_no", 0);
		mission_name=intent.getStringExtra("mission_name");
		mlist_regdate=intent.getStringExtra("mlist_regdate");
		theme_no=intent.getIntExtra("theme_no", 0);
		item_no=-1; 
		
		themeView=(ImageView)findViewById(R.id.imageView1);
		chip_countView=(TextView)findViewById(R.id.chip_count);
		text_missionName=(TextView)findViewById(R.id.text_missionName);
		text_missionName.setText(mission_name);
		
		chipCount = PropertyManager.getInstance().getChipCount();
		chip_countView.setText(""+chipCount+"개로 교환할 수 있는 아이템");
		text_ThemeView=(TextView)findViewById(R.id.text_themeName);
		sView=(LinearLayout)findViewById(R.id.scratchView);
		btn_ok=(Button)findViewById(R.id.btn_ok);
		btn_chance=(Button)findViewById(R.id.btn_chance);
		btn_ok2=(Button)findViewById(R.id.btn_ok2);
		scratchView = (WScratchView) findViewById(R.id.scratch_view);
		hView=(LinearLayout)findViewById(R.id.itemView);
		btn_cancel = (Button)findViewById(R.id.btn_cancel);
		
		scratchView.setScratchable(true);
		scratchView.setRevealSize(50);
		scratchView.setAntiAlias(true); 
		scratchView.setScratchDrawable(getResources().getDrawable(R.drawable.mission_lotto_scratch));
		scratchView.setBackgroundClickable(true);
		lottoView=(ImageView)findViewById(R.id.imageView2);
		setTheme();
		item_no=2;
		// add callback for update scratch percentage
		scratchView.setOnScratchCallback(new WScratchView.OnScratchCallback() {

			public void onScratch(float percentage) {
				updatePercentage(percentage);
			}

			public void onDetach(boolean fingerDetach) {
				if(mPercentage > 10){
					scratchView.setScratchAll(true);
					updatePercentage(100);
					
						btn_ok.setVisibility(View.VISIBLE);
						btn_chance.setVisibility(View.VISIBLE);
					
				}
			}
		});
		
		scratchView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Toast.makeText(MissionCardScratchActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
				
			}
			
		});

		
		updatePercentage(0f);
		
		btn_chance.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				text_ThemeView.setVisibility(View.GONE);
				sView.setVisibility(View.GONE);//scratch view remove
				hView.setVisibility(View.VISIBLE);
			
				
			}
		});
		
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				NetworkManager.getInstnace().confirmMission(MissionCardScratchActivity.this, mlist_no, new OnResultListener<MissionResult>() {

					@Override
					public void onSuccess(MissionResult result) {
						if(result.success==1){
							Toast.makeText(MissionCardScratchActivity.this, "미션 확인 완료", Toast.LENGTH_SHORT).show();
							Intent intent=new Intent(MissionCardScratchActivity.this,MissionActivity.class);
							finish();
							startActivity(intent);
						}
						
					}

					@Override
					public void onFail(int code) {
						// TODO Auto-generated method stub
						
					}
				});
			} 
		});
		
		
		btn_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
			NetworkManager.getInstnace().confirmMission(MissionCardScratchActivity.this, mlist_no, new OnResultListener<MissionResult>() {

			@Override
			public void onSuccess(MissionResult result) {
				if(result.success==1){
					Toast.makeText(MissionCardScratchActivity.this, "미션 확인 완료", Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(MissionCardScratchActivity.this,MissionActivity.class);
					finish();
					startActivity(intent);
				}
						
			}

			@Override
			public void onFail(int code) {
			// TODO Auto-generated method stub
					
			}
		});
		}
			
	});
		
		btn_ok2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			if(item_no!=-1){
			NetworkManager.getInstnace().confirmMission(MissionCardScratchActivity.this, mlist_no, new OnResultListener<MissionResult>() {

			@Override
			public void onSuccess(MissionResult result) {
					
				if(result.success==1){
					if(item_no!=9){
					mission_name="";
					}
					Date today = new Date(); 
					Calendar cal = Calendar.getInstance();
					cal.setTime(today); 
					String item_usedate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(cal.getTime());
						
					NetworkManager.getInstnace().useItem(MissionCardScratchActivity.this, item_usedate, selectedIndex+1 , mlist_no,mission_name, new OnResultListener<BananaItemResponse>() {

					@Override
					public void onSuccess(BananaItemResponse result) {
						if(result.success==1){
							Toast.makeText(MissionCardScratchActivity.this, "아이템 사용 완료", Toast.LENGTH_SHORT).show();
							Intent intent=new Intent(MissionCardScratchActivity.this,MissionActivity.class);
							finish();
							startActivity(intent);
						}
					}

					@Override
					public void onFail(int code) {
						if(code == 0) {
							Toast.makeText(MissionCardScratchActivity.this, "아이템 사용 실패!", Toast.LENGTH_SHORT).show();
						} else if (code == 1) {
							Toast.makeText(MissionCardScratchActivity.this, "리워드 갯수 부족!", Toast.LENGTH_SHORT).show();
						}
												
					}
				});
				}
				}

					@Override
					public void onFail(int code) {
					
						
					}
				});
			}
			}
		});
		 
		btn_out = (Button)findViewById(R.id.btn_out);
		btn_out.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		//*-----칩 갯수에 따라 아이템 색 변화-----*//
		
		for (int i = 0; i < items.length; i++) {
			if (chipCount >= ITEM_COIN_COUNT[i] ) {
				items[i].setBackgroundResource(R.drawable.item_checked_selector);
			} else {
				items[i].setBackgroundResource(R.drawable.mission_item_select_bananachip_gray);
			}
		}
		
		for (int i = 0; i < items.length; i++) {
			items[i].setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int index = (Integer)v.getTag();
					if (chipCount >= ITEM_COIN_COUNT[index]) {
						if (selectedIndex != -1) {
							if (selectedIndex == index) {
								items[index].setSelected(false);
								selectedIndex = -1;
								return;
							} else {
								items[selectedIndex].setSelected(false);
								selectedIndex = -1;
							}
						}
						
						items[index].setSelected(true);
						selectedIndex = index;
					}
				}
			});
		}
		
		
	}
	
	public void setTheme()
	{
		if(theme_no==1)//악마
		{	themeView.setImageResource(R.drawable.mission_devil_icon);
			text_ThemeView.setText("악마미션");
		}else if(theme_no==2){//처음
			themeView.setImageResource(R.drawable.mission_fist_icon);
			text_ThemeView.setText("처음미션");
		}else if(theme_no==3){//섹시
			themeView.setImageResource(R.drawable.mission_sexy_icon);
			text_ThemeView.setText("섹시미션");
		}else if(theme_no==4){//애교
			themeView.setImageResource(R.drawable.mission_cute_icon);
			text_ThemeView.setText("애교미션");
		}else if(theme_no==5){//천사
			themeView.setImageResource(R.drawable.mission_angel_icon);
			text_ThemeView.setText("천사미션");
		}
		
	}

	
	protected void updatePercentage(float percentage) {
		mPercentage = percentage;
		String percentage2decimal = String.format("%.2f", percentage) + " %"; 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mission_card_scratch, menu);
		return true;
	}

	
}
