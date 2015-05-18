package com.banana.banana.main; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.dday.DdayActivity;
import com.banana.banana.love.LoveActivity;
import com.banana.banana.love.LovePopupActivity;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.main.MainAdapter.OnAdapterImageListener;
import com.banana.banana.main.MainDialog.OnChangeFeelingListener;
import com.banana.banana.mission.MissionActivity;
import com.banana.banana.mission.card.SimpleExample;
import com.banana.banana.setting.SettingActivity;

public class BananaMainActivity extends ActionBarActivity {

	ImageView coinView, ManChar, WomanChar, WcoinView;
	ListView contentsList;
	MainAdapter mAdapter; 
	TextView ddayView, TextMlevel, TextFlevel, titleView;
	HorizontalScrollView hView; 
	MainDialog dialog1; 
	String couple_birth,gender;
	int f_reward, m_reward, m_condition, f_condition;  
	ImageView settingImg;
	int couple_condom;
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_banana_main); ;  
			ActionBar actionBar = getSupportActionBar(); 
		      actionBar.setDisplayHomeAsUpEnabled(false);
		      actionBar.setDisplayShowTitleEnabled(false);
		      actionBar.setDisplayShowHomeEnabled(false);
		      actionBar.setDisplayShowCustomEnabled(true); // Custom메뉴 설정 true
		      actionBar.setCustomView(R.layout.custom_action_bar); 
		      titleView = (TextView)actionBar.getCustomView().findViewById(R.id.text_title);
		      titleView.setText("BANANA");
		      settingImg = (ImageView)actionBar.getCustomView().findViewById(R.id.img_setting);
		      settingImg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(BananaMainActivity.this, SettingActivity.class);
					startActivity(intent);
				}
			}); 
		      
		    gender = PropertyManager.getInstance().getUserGender();
		    
			init();
			//initMyInfo();
			TextMlevel = (TextView)findViewById(R.id.text_male_level);
			TextFlevel = (TextView)findViewById(R.id.text_female_level);
			dialog1 = new MainDialog(this);
			    
			hView = (HorizontalScrollView)findViewById(R.id.horizontalScroll_item);
			contentsList = (ListView)findViewById(R.id.listView_main);
			contentsList.setDivider(null);
			
			
			mAdapter = new MainAdapter();
			mAdapter.setOnAdapterImageListener(new OnAdapterImageListener() {
				
				@Override
				public void onAdapterImageAction(Adapter adapter, View view, MainItemData data) {
					// TODO Auto-generated method stub
					if(data.category == "Love") {
					Intent intent = new Intent(BananaMainActivity.this, LovePopupActivity.class);
					intent.addFlags(intent.FLAG_ACTIVITY_NO_HISTORY);
					intent.putExtra("couple_condom", couple_condom);
					startActivity(intent);
					} else if (data.category == "Mission") {
						Intent intent = new Intent(BananaMainActivity.this, SimpleExample.class);
						startActivity(intent);
					}
				}
			});
			
			contentsList.setAdapter(mAdapter); 
			 
			contentsList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if(id==0) {
						Intent intent = new Intent(BananaMainActivity.this, LoveActivity.class); 
						intent.putExtra("couple_condom", couple_condom);
						startActivity(intent);
					} else {
						Intent intent = new Intent(BananaMainActivity.this, MissionActivity.class);
						startActivity(intent);
					}
					
				}
				
			});
			
		
			coinView = (ImageView)findViewById(R.id.image_male_item);
			coinView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) { 
					if(gender.equals("M")) {
					setVisibileDetailView(!isVisibleDetailView());
					}
				}
			});
			
			WcoinView = (ImageView)findViewById(R.id.image_female_item);
			WcoinView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) { 
					if(gender.equals("F")) {
					setVisibileDetailView(!isVisibleDetailView());
					}
				}
			});
			
			ddayView = (TextView)findViewById(R.id.text_dday);
			
			ddayView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(BananaMainActivity.this, DdayActivity.class);
					intent.addFlags(intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
				}
			});
			
			initData();
			
			ManChar = (ImageView)findViewById(R.id.img_male); 
			WomanChar = (ImageView)findViewById(R.id.img_female);
			
			ManChar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(gender.equals("M")) {
					dialog1.showAsDropDown(v); 
					}  
				}
			});
			
			WomanChar.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) { 
					if(gender.equals("F")) {
						dialog1.showAsDropDown(v);
						}
					}
				
			});
			
			dialog1.setOnChangeFeelingListener(new OnChangeFeelingListener() {
				
				@Override
				public void OnFeelingClick(View view) { 
						ChangeFeel(view); //내 캐릭터 변경 기능들
						 
				}
			}); 
		}
		
		
		private void init() {
			// TODO Auto-generated method stub
			NetworkManager.getInstnace().getCoupleInfoList(BananaMainActivity.this, new OnResultListener<CoupleInfoResult>() {

				@Override
				public void onSuccess(CoupleInfoResult result) {
						couple_birth = result.items.couple_birth;
						m_condition = result.items.m_condition;
						f_condition = result.items.f_condition;
						m_reward = result.items.m_reward;
						f_reward = result.items.f_reward;
						TextMlevel.setText(""+m_reward);
						TextFlevel.setText(""+f_reward); 
						couple_condom = result.items.couple_condom;
						if(f_condition == 1) {
							//WomanChar.setImageResource(R.drawable.profile3);
						}
						if(m_condition == 1) {
							//ManChar.setImageResource(R.drawable.profile4);
						}
						
						subDdate(couple_birth);  
				}

				@Override
				public void onFail(int code) { 
					
				}
			
			});
		} 
		
		protected void subDdate(String d) { //사귄일수 구함
			// TODO Auto-generated method stub 
			long d1, d2;
			Date today = new Date(); 
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			
			
			Date day;
			Calendar cal2 = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try { 
				day = sdf.parse(d);   
				cal2.setTime(day);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			d1 = cal.getTime().getTime();
			d2 = cal2.getTime().getTime();
			
			int days = (int)((d1-d2)/(1000*60*60*24)); 
			ddayView.setText(""+days);
			
		}

		private void initData() {
			// TODO Auto-generated method stub
				MainItemData d1 = new MainItemData();
				d1.category = "Love";
				d1.popup = R.drawable.main_love_popup_button;
				mAdapter.add(d1);
				
				MainItemData d2 = new MainItemData();
				d2.category = "Mission";
				d2.popup = R.drawable.main_mission_popup_button;
				mAdapter.add(d2);
				
		}
		
	public void ChangeFeel(View v) {
		if(gender.equals("F")) {
			if(v.getId() == R.id.img_feel1) {
				myCondition(2);
				WomanChar.setImageResource(R.drawable.profile2);
			} else if (v.getId() == R.id.img_feel2) {
				myCondition(3);
				WomanChar.setImageResource(R.drawable.profile3);
			}
		} else if(gender.equals("M")) {
			if(v.getId() == R.id.img_feel1) {
				myCondition(2);
			ManChar.setImageResource(R.drawable.profile2);
			} else if (v.getId() == R.id.img_feel2) {
				myCondition(3);
			ManChar.setImageResource(R.drawable.profile3);
			}
		}
	} 
	
	private void myCondition(int condition_no) {
		NetworkManager.getInstnace().myCondition(BananaMainActivity.this, condition_no, new OnResultListener<UserInfoResult>() {

			@Override
			public void onSuccess(UserInfoResult result) { 
			}

			@Override
			public void onFail(int code) { 
			}
		});
	}
		
	public boolean isVisibleDetailView() {
		return hView.getVisibility() == View.VISIBLE;
	}
		
	public void setVisibileDetailView(boolean isVisible) {
		hView.setVisibility(isVisible?View.VISIBLE:View.GONE);
		}
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.menu_one, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//switch(item.getItemId()) {
		//case R.id.menu_m1 :
		//	Intent intent = new Intent(LoveActivity.this, SettingActivity.class);
		//	startActivity(intent);
		//	return true;
		//}
		return super.onOptionsItemSelected(item);
	}
}
