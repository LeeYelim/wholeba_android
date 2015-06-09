package com.banana.banana.love;
 
import java.util.Calendar;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.PropertyManager;
import com.banana.banana.R;
import com.banana.banana.login.LoginActivity;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.setting.SettingActivity;

import de.passsy.holocircularprogressbar.HoloCircularProgressBar;

public class LoveActivity extends ActionBarActivity {
	private HoloCircularProgressBar mHoloCircularProgressBar;
	private ObjectAnimator mProgressBarAnimator;
	ListView loveList;
	LoveAdapter mLAdapter; 
	View headerView; 
	Button btn_LoveAdd; 
	TextView noCondomPercentView, isCondomPercentView, isCondomView, noCondomView, LoveDayView, YearView, monthView, titleView; 
	int orderby=0, yearCount = 0, monthCount = 0, year, month, couple_condom; 
	float isCondom, notCondom;
	Spinner lovesort = null;
	ArrayAdapter<String> sortAdapter = null;
	private CustomGallery mCustomGallery, mCustomGallery2; 
	View layoutSort, sortLayout, layoutTodayPercent;
	CustomGalleryImageAdapter cAdapter;
	CustomGalleryImageAdapter2 cAdapter2;
	LoveDialog dialog;   
	ImageView settingImg, img_posibility;
	String[] mData = null;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		initData();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_love); 
		ActionBar actionBar = getSupportActionBar(); 
	      actionBar.setDisplayHomeAsUpEnabled(false);
	      actionBar.setDisplayShowTitleEnabled(false);
	      actionBar.setDisplayShowHomeEnabled(false);
	      actionBar.setDisplayShowCustomEnabled(true); // Custom메뉴 설정 true
	      actionBar.setCustomView(R.layout.custom_action_bar); 
	      titleView = (TextView)actionBar.getCustomView().findViewById(R.id.text_title);
	      titleView.setText("LOVE");
	      settingImg = (ImageView)actionBar.getCustomView().findViewById(R.id.img_setting);
	      settingImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoveActivity.this, SettingActivity.class);
				finish();
				startActivity(intent);
			}
		});
	      
		loveList = (ListView)findViewById(R.id.listView1);  
		btn_LoveAdd = (Button)findViewById(R.id.btn_add_love);
		mLAdapter = new LoveAdapter(this); 
		headerView = (View)getLayoutInflater().inflate(
                R.layout.love_header_layout,null);  
		loveList.addHeaderView(headerView,null,false);
		loveList.setAdapter(mLAdapter);
		loveList.requestFocus();
		LoveDayView = (TextView)findViewById(R.id.text_love_date);
		isCondomPercentView = (TextView)headerView.findViewById(R.id.text_iscondom1);
		noCondomPercentView = (TextView)headerView.findViewById(R.id.text_nocondom1);
		isCondomView = (TextView)headerView.findViewById(R.id.text_iscondom);
		noCondomView = (TextView)headerView.findViewById(R.id.text_nocondom);
		
		layoutSort = (View)headerView.findViewById(R.id.layout_sort);
		sortLayout = (View)headerView.findViewById(R.id.Sort_layout);
		YearView = (TextView)headerView.findViewById(R.id.text_sort_year);
		monthView = (TextView)headerView.findViewById(R.id.text_sort_month);  
		mHoloCircularProgressBar = (HoloCircularProgressBar)headerView.findViewById(R.id.holoCircularProgressBar1);
		layoutTodayPercent = (View)headerView.findViewById(R.id.layout_love_today_percent);
		
		
		lovesort = (Spinner)headerView.findViewById(R.id.love_sort);
		mData = getResources().getStringArray(R.array.list_love_sort); 
		sortAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,mData);	
		lovesort.setAdapter(sortAdapter);
		
		cAdapter = new CustomGalleryImageAdapter(this);
		cAdapter2 = new CustomGalleryImageAdapter2(this);
		  
		couple_condom = PropertyManager.getInstance().getCoupleCondom();
		
		 Calendar oCalendar = Calendar.getInstance();
		 year = oCalendar.get(Calendar.YEAR);
		 month = oCalendar.get(Calendar.MONTH)+1;
		
		 YearView.setText(""+year);
		 monthView.setText(""+month); 
		 
		 img_posibility = (ImageView)findViewById(R.id.img_posibility); 
		
		layoutTodayPercent.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(couple_condom == 0) {
					animate(mHoloCircularProgressBar, null, (float)isCondom, 1000);
					mHoloCircularProgressBar.setProgress((float)isCondom); 
					img_posibility.setImageResource(R.drawable.popup_bt_condorm);
					couple_condom = 1;   
					isCondomView.setTextColor(getResources().getColor(R.color.font_red));
					isCondomPercentView.setTextColor(getResources().getColor(R.color.font_black));
					noCondomPercentView.setTextColor(getResources().getColor(R.color.color_white)); 
					noCondomView.setTextColor(getResources().getColor(R.color.color_white));
				} else if(couple_condom == 1) {
					animate(mHoloCircularProgressBar, null, (float)notCondom, 1000);
					mHoloCircularProgressBar.setProgress((float)notCondom);
					img_posibility.setImageResource(R.drawable.popup_bt_banana); 
					couple_condom = 0; 
					noCondomPercentView.setTextColor(getResources().getColor(R.color.font_black)); 
					noCondomView.setTextColor(getResources().getColor(R.color.font_red));
					isCondomView.setTextColor(getResources().getColor(R.color.color_white));
					isCondomPercentView.setTextColor(getResources().getColor(R.color.color_white));
				}
			}
		}); 
		
		lovesort.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override  
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position == 1) {
					orderby = position;
					initData();
				} else if(position == 0) {
					orderby = position;
					initData();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				orderby = 0;
			}
		});
		
		btn_LoveAdd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle b = new Bundle();
				b.putString("code", "1");
				dialog = new LoveDialog();
				dialog.setArguments(b);
				dialog.show(getSupportFragmentManager(), "dialog");
			}
		});
		
		loveList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle b = new Bundle();
				b.putString("code", "0");
				String loveDate = mLAdapter.loves.get(position-1).loves_date;
				int lovesCondom = mLAdapter.loves.get(position-1).loves_condom;
				int lovesNo = mLAdapter.loves.get(position-1).loves_no;
				b.putString("loveDate", loveDate);
				b.putInt("lovesCondom", lovesCondom);
				b.putInt("lovesNo", lovesNo);    
				dialog = new LoveDialog();
				dialog.setArguments(b);
				dialog.show(getSupportFragmentManager(), "dialog");  
			}
		}); 
	

		mCustomGallery = (CustomGallery) findViewById(R.id.gallery);
	    mCustomGallery.setAdapter(cAdapter); 
	     mCustomGallery.setOnItemSelectedListener(new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {  
			 
			 YearView.setText(""+cAdapter.mImageID[position]);
			 year = Integer.parseInt(cAdapter.mImageID[position]);
			 initData(); 
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub
			
		}
	});

			mCustomGallery2 = (CustomGallery) findViewById(R.id.gallery2);  
			mCustomGallery2.setAdapter(cAdapter2);  
	  mCustomGallery2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position,
					long id) { 
				 monthView.setText(""+cAdapter2.mImageID[position]);
				 month = Integer.parseInt(cAdapter2.mImageID[position]);
				 initData();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
	  
	  	layoutSort.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setVisibileDetailView(!isVisibleDetailView());
				monthCount = 0;
				yearCount = 0;
				
				for(int i=2009; i<=2021; i++) {
					if(i == year) {
						mCustomGallery.setSelection(yearCount); 
					} else {
						yearCount++;
					}
				}
				for(int i=1; i<=12; i++) {
					if(i == month) {
						mCustomGallery2.setSelection(monthCount); 
					} else {
						monthCount++;
					}
				}
			}
		}); 
	}
	
	
	private void initData() {  	 
		NetworkManager.getInstnace().getLoveList(LoveActivity.this, orderby, year, month, new OnResultListener<LoveSearchResult>() {

			@Override
			public void onSuccess(LoveSearchResult result) {  
						// TODO Auto-generated method stub 
				mLAdapter.clear();
				mLAdapter.addAll(result.result.items.item); 
				isCondom = result.result.items.today_condom;
				notCondom = result.result.items.today_notcondom; 
				isCondomPercentView.setText("가임률"+(int)(isCondom*100)+"%");
				noCondomPercentView.setText("가임률"+(int)(notCondom*100)+"%");     
				if(couple_condom == 0) {
					mHoloCircularProgressBar.setProgress((float)result.result.items.today_notcondom); 
					img_posibility.setImageResource(R.drawable.popup_bt_banana);
					noCondomPercentView.setTextColor(getResources().getColor(R.color.font_black));
					noCondomView.setTextColor(getResources().getColor(R.color.font_red));
					isCondomPercentView.setTextColor(getResources().getColor(R.color.color_white));
					isCondomView.setTextColor(getResources().getColor(R.color.color_white));
				} else if(couple_condom == 1){
					mHoloCircularProgressBar.setProgress((float)result.result.items.today_condom); 
					img_posibility.setImageResource(R.drawable.popup_bt_condorm);  
					isCondomPercentView.setTextColor(getResources().getColor(R.color.font_black));
					isCondomView.setTextColor(getResources().getColor(R.color.font_red));
					noCondomPercentView.setTextColor(getResources().getColor(R.color.color_white));
					noCondomView.setTextColor(getResources().getColor(R.color.color_white));			
				}
			} 
			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				
			}
		});
	}
 
 

	public boolean isVisibleDetailView() {
		return sortLayout.getVisibility() == View.VISIBLE;
	}
	
	public void setVisibileDetailView(boolean isVisible) {
		sortLayout.setVisibility(isVisible?View.VISIBLE:View.GONE); 
	} 

	private void animate(final HoloCircularProgressBar progressBar, final AnimatorListener listener) {
		final float progress = (float) (Math.random() * 2);
		int duration = 3000;
		animate(progressBar, listener, progress, duration);
	}

	private void animate(final HoloCircularProgressBar progressBar, final AnimatorListener listener,
			final float progress, final int duration) {

		mProgressBarAnimator = ObjectAnimator.ofFloat(progressBar, "progress", progress);
		mProgressBarAnimator.setDuration(duration);

		mProgressBarAnimator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationCancel(final Animator animation) {
			}

			@Override
			public void onAnimationEnd(final Animator animation) {
				progressBar.setProgress(progress);
			}

			@Override
			public void onAnimationRepeat(final Animator animation) {
			}

			@Override
			public void onAnimationStart(final Animator animation) {
			}
		});
		if (listener != null) {
			mProgressBarAnimator.addListener(listener);
		}
		mProgressBarAnimator.reverse();
		mProgressBarAnimator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(final ValueAnimator animation) {
				progressBar.setProgress((Float) animation.getAnimatedValue());
			}
		});
		progressBar.setMarkerProgress(progress);
		mProgressBarAnimator.start();
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