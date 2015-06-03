package com.banana.banana.main; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.AlarmService;
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

	ImageView coinView, ManChar, WomanChar, WcoinView, img_isperiod, img_headache, img_band, img_bust_band, img_pimple;
	ListView contentsList;
	MainAdapter mAdapter; 
	TextView ddayView, TextMlevel, TextFlevel, titleView;
	View dView;
	HorizontalScrollView hView; 
	MainDialog dialog1; 
	String couple_birth,gender;
	int f_reward, m_reward, m_condition, f_condition, ddays;  
	ImageView settingImg;
	int couple_condom, chipCount;
	ScrollView sv1;
	private static final int[] ITEM_COIN_COUNT = { 1, 1, 2, 2,2,2,2,3,5};
	private int resIds[] = {R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5, R.id.item6, R.id.item7, R.id.item8, R.id.item9};
	TextView[] items = new TextView[resIds.length];
	
	public static final int MESSAGE_BACK_PRESSED_TIMEOUT = 0;
	public static final int TIMEOUT_BACK_PRESSED = 2000;
	
	Handler mHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_BACK_PRESSED_TIMEOUT :
				isBackPressed = false;
				break;
			}
		}
	};
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_banana_main);
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
		     
		      
		      
		      
		      
		      
		      
		    sv1 = (ScrollView)findViewById(R.id.scrollView_main);  
		    sv1.smoothScrollTo(0, 0);
		    gender = PropertyManager.getInstance().getUserGender();
		    img_isperiod = (ImageView)findViewById(R.id.img_isperiod);
		    img_band = (ImageView)findViewById(R.id.img_band);
		    img_bust_band = (ImageView)findViewById(R.id.img_bust_band);
		    img_pimple = (ImageView)findViewById(R.id.img_pimple);
		    img_headache = (ImageView)findViewById(R.id.img_headache);
		    
			init();  
			 
			/*--------hView 아이템 코인에 따라 채우기--------*/
			chipCount = PropertyManager.getInstance().getChipCount();
			for (int i = 0; i < resIds.length;i++) {
				items[i] = (TextView)findViewById(resIds[i]); 
			}
			 
			for (int i = 0; i < items.length; i++) {
				if (chipCount >= ITEM_COIN_COUNT[i] ) {
					items[i].setBackgroundResource(R.drawable.mission_item_select_bananachip_unselected);
				} else {
					items[i].setBackgroundResource(R.drawable.mission_item_select_bananachip_gray);
				}
			}
			
			
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
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); 
					startActivity(intent); 
					} else if (data.category == "Mission") {
						Intent intent = new Intent(BananaMainActivity.this, SimpleExample.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
			dView = (View)findViewById(R.id.LayoutDday);
			dView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(BananaMainActivity.this, DdayActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); 
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
						PropertyManager.getInstance().setCoupleCondom(couple_condom);
						
						if(f_condition == 1) {
							WomanChar.setImageResource(R.drawable.woman_basic);
						} else if(f_condition == 2) {
							WomanChar.setImageResource(R.drawable.woman_angry_133_206);
						} else if(f_condition == 3) {
							WomanChar.setImageResource(R.drawable.woman_sick);
						} else if(f_condition == 4) {
							WomanChar.setImageResource(R.drawable.woman_sexy);
						} else if(f_condition == 5) {
							WomanChar.setImageResource(R.drawable.woman_happy);
						}
						if(m_condition == 1) {
							ManChar.setImageResource(R.drawable.man_basic);
						} else if(m_condition == 2) {
							ManChar.setImageResource(R.drawable.man_angry_133_206);
						} else if(m_condition == 3) {
							ManChar.setImageResource(R.drawable.man_sick);
						} else if(m_condition == 4) {
							ManChar.setImageResource(R.drawable.man_sexy);
						} else if(m_condition == 5) {
							ManChar.setImageResource(R.drawable.man_happy);
						}
						if(gender.equals("F")) {
							PropertyManager.getInstance().setChipCount(f_reward);
						} else if(gender.equals("M")) {
							PropertyManager.getInstance().setChipCount(m_reward);
						}
						try{
							subDdate(couple_birth);  
						} catch(NullPointerException e) {
							
						}
						
						
						
						if(result.items.f_isperiod == 1) {
							img_isperiod.setVisibility(View.VISIBLE);
							img_isperiod.setImageResource(R.drawable.magicday_icon);
						}
						if(result.items.f_isdanger == 1) {
							img_isperiod.setVisibility(View.VISIBLE);
							img_isperiod.setImageResource(R.drawable.warningday_icon);
						}
						
						int[] syndrom = result.items.f_syndno;
						
						if(syndrom.length != 0) {
							for(int i = 0; i < syndrom.length; i++) { 
								if(syndrom[i] == 14) {
									img_band.setVisibility(View.VISIBLE);
								} else if(syndrom[i] == 12) {
									img_headache.setVisibility(View.VISIBLE);
								} else if(syndrom[i] == 8) {
									img_pimple.setVisibility(View.VISIBLE);
								} else if(syndrom[i] == 10) {
									img_bust_band.setVisibility(View.VISIBLE);
								} else if(syndrom[i] == 5) {
									img_bust_band.setVisibility(View.VISIBLE);
								}
							}
						}
						
						if(gender.equals("F")) {
							setAlarm();
						}
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
			
			ddays = (int)((d1-d2)/(1000*60*60*24)); 
			ddayView.setText(""+ddays);
			PropertyManager.getInstance().setCoupleDays(ddays);
			
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
			if(v.getId() == R.id.img_feel5) {
				myCondition(1);
				WomanChar.setImageResource(R.drawable.woman_basic);
			} else if(v.getId() == R.id.img_feel1) {
				myCondition(2);
				WomanChar.setImageResource(R.drawable.woman_angry_133_206);
			} else if (v.getId() == R.id.img_feel2) {
				myCondition(3);
				WomanChar.setImageResource(R.drawable.woman_sick);
			}  else if (v.getId() == R.id.img_feel3) {
				myCondition(4);
				WomanChar.setImageResource(R.drawable.woman_sexy);
			}  else if (v.getId() == R.id.img_feel4) {
				myCondition(5);
				WomanChar.setImageResource(R.drawable.woman_happy);
			}
		} else if(gender.equals("M")) {
			if(v.getId() == R.id.img_feel5) {
				myCondition(1);
				ManChar.setImageResource(R.drawable.man_basic);
			} else if(v.getId() == R.id.img_feel1) {
				myCondition(2);
				ManChar.setImageResource(R.drawable.man_angry_133_206);
			} else if (v.getId() == R.id.img_feel2) {
				myCondition(3);
				ManChar.setImageResource(R.drawable.man_sick);
			} else if (v.getId() == R.id.img_feel3) {
				myCondition(4);
				ManChar.setImageResource(R.drawable.man_sexy);
			} else if (v.getId() == R.id.img_feel4) {
				myCondition(5);
				ManChar.setImageResource(R.drawable.man_happy);
			}
		}
	} 
	
		private void setAlarm() {
		int hh = 11; //알람 시간
		int mm = 00; //알람 분 설정  
		
		PropertyManager.getInstance().setHour(hh);
		PropertyManager.getInstance().setMinute(mm);  
		PropertyManager.getInstance().setAlarmCount(0);
		
		Intent intent = new Intent(BananaMainActivity.this, AlarmService.class); 
		startService(intent);
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
		
    boolean isBackPressed = false;
    @Override
    public void onBackPressed() {
    	if (!isBackPressed) {
    		Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
    		mHandler.sendEmptyMessageDelayed(MESSAGE_BACK_PRESSED_TIMEOUT, TIMEOUT_BACK_PRESSED);
    		isBackPressed = true;
    	} else {
    		mHandler.removeMessages(MESSAGE_BACK_PRESSED_TIMEOUT);
    		super.onBackPressed();
    	}
    }
    
    int count = 0;
    Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			// ....
			count++;
			mHandler.postDelayed(this, 1000);
		}
	};

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
