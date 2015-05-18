package com.banana.banana.mission.scratch;

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
	Button btn_ok,btn_chance,btn_ok2;
	LinearLayout sView;
	LinearLayout hView;
	ImageView lottoView;
	int item_no,mlist_no,theme_no;
	String themeName;
	ImageView themeView;
	TextView text_ThemeView,text_missionName;
	TextView chip_countView;//보유 바나나칩 갯수
	
	ImageView item1,item2,item3,item4;
	int age;
	String mission_name;
	String mlist_regdate;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission_card_scratch);
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
		
		chip_countView.setText(""+PropertyManager.getInstance().getChipCount());
		text_ThemeView=(TextView)findViewById(R.id.text_themeName);
		sView=(LinearLayout)findViewById(R.id.scratchView);
		btn_ok=(Button)findViewById(R.id.btn_ok);
		btn_chance=(Button)findViewById(R.id.btn_chance);
		btn_ok2=(Button)findViewById(R.id.btn_ok2);
		scratchView = (WScratchView) findViewById(R.id.scratch_view);
		hView=(LinearLayout)findViewById(R.id.itemView);
		
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
		
		btn_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
					NetworkManager.getInstnace().confirmMission(MissionCardScratchActivity.this, mlist_no, new OnResultListener<MissionResult>() {

						@Override
						public void onSuccess(MissionResult result) {
							if(result.success==1){
								Toast.makeText(MissionCardScratchActivity.this, "미션 확인 완료", Toast.LENGTH_SHORT).show();
								Intent intent=new Intent(MissionCardScratchActivity.this,MissionActivity.class);
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
							NetworkManager.getInstnace().useItem(MissionCardScratchActivity.this, item_no, mlist_no,mission_name, new OnResultListener<BananaItemResponse>() {

								@Override
								public void onSuccess(BananaItemResponse result) {
								if(result.success==1){
									Toast.makeText(MissionCardScratchActivity.this, "아이템 사용 완료", Toast.LENGTH_SHORT).show();
									Intent intent=new Intent(MissionCardScratchActivity.this,MissionActivity.class);
									startActivity(intent);
								}
							}

								@Override
								public void onFail(int code) {
									
									
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
		//아이템 클릭시 처리 시작  ---------------------------------------
		
		item1=(ImageView)findViewById(R.id.item1);
		item2=(ImageView)findViewById(R.id.item2);
		item3=(ImageView)findViewById(R.id.item3);
		item4=(ImageView)findViewById(R.id.item4);
		item1.setOnClickListener(new View.OnClickListener() {//1번 아이템 선택 
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MissionCardScratchActivity.this, "item1click", Toast.LENGTH_SHORT).show();
				item_no=1;
			}
		});
		item2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MissionCardScratchActivity.this, "item2click", Toast.LENGTH_SHORT).show();
			item_no=2;
				
			}
		});
		item3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MissionCardScratchActivity.this, "item3click", Toast.LENGTH_SHORT).show();
				item_no=3;
				
			}
		});
		item4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MissionCardScratchActivity.this, "item4click", Toast.LENGTH_SHORT).show();
				item_no=4;
				
			}
		});
		
	}
	public void setTheme()
	{
		if(theme_no==1)//악마
		{
			themeView.setImageResource(R.drawable.mission_devil_icon);
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
