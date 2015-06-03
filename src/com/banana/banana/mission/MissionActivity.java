package com.banana.banana.mission;
import java.util.Calendar;

import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.R;
import com.banana.banana.love.CustomGallery;
import com.banana.banana.love.CustomGalleryImageAdapter;
import com.banana.banana.love.CustomGalleryImageAdapter2;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;
import com.banana.banana.setting.SettingActivity;

public class MissionActivity extends ActionBarActivity {
	int orderby=0;
	ListView MissionListView;
	MissionAdapter mAdapter;
	Button btn ;	
	Spinner mSpinner=null;
	String[] mData=null;
	ArrayAdapter<String> mArrayAdapter=null;
	boolean click=false;
	int year, month, manTotalScore,womanTotalScore, manCompleteScore,womanCompleteScore;
	TextView manTotalScoreView,womanTotalScoreView, manCompleteView,womanCompleteView, titleView, yearView, monthView;
	ImageView settingImg;
	private CustomGallery mCustomGallery, mCustomGallery2; 
	CustomGalleryImageAdapter cAdapter;
	CustomGalleryImageAdapter2 cAdapter2;
	View daySortLayout;
	View sortLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission);
		/*------액션바 등록-----*/
		ActionBar actionBar = getSupportActionBar(); 
	      actionBar.setDisplayHomeAsUpEnabled(false);
	      actionBar.setDisplayShowTitleEnabled(false);
	      actionBar.setDisplayShowHomeEnabled(false);
	      actionBar.setDisplayShowCustomEnabled(true); // Custom메뉴 설정 true
	      actionBar.setCustomView(R.layout.custom_action_bar); 
	      titleView = (TextView)actionBar.getCustomView().findViewById(R.id.text_title);
	      titleView.setText("MISSION");
	      settingImg = (ImageView)actionBar.getCustomView().findViewById(R.id.img_setting);
	      settingImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MissionActivity.this, SettingActivity.class);
				finish();
				startActivity(intent);
			}
		}); 
	      
	   
	    Calendar oCalendar = Calendar.getInstance();
		year = oCalendar.get(Calendar.YEAR);
		month = oCalendar.get(Calendar.MONTH)+1;  //오늘 날짜 가져옴
		yearView = (TextView)findViewById(R.id.todayYear);
		monthView = (TextView)findViewById(R.id.todayMonth);
		
		//toal score--------------------------------------------------------
		manTotalScoreView=(TextView)findViewById(R.id.man_receiveTotal);
		womanTotalScoreView=(TextView)findViewById(R.id.woman_receiveTotal); 
		manCompleteView=(TextView)findViewById(R.id.manScore);
		womanCompleteView=(TextView)findViewById(R.id.womanScore);
		MissionListView=(ListView)findViewById(R.id.listView1);
		mAdapter=new MissionAdapter(this);
		MissionListView.setAdapter(mAdapter);
		MissionListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mSpinner=(Spinner)findViewById(R.id.sort);//정렬 spinner설정
		mData=getResources().getStringArray(R.array.list_sort);//string배열 얻어오기
		mArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,mData);
		
		
		mSpinner.setAdapter(mArrayAdapter);
		
	    cAdapter = new CustomGalleryImageAdapter(this);
		cAdapter2 = new CustomGalleryImageAdapter2(this);
	    
		sortLayout = (View)findViewById(R.id.Sort_layout);  
		daySortLayout = (View)findViewById(R.id.daysortLayout);
		daySortLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setVisibileDetailView(!isVisibleDetailView());
			}
		});
		 
		orderby=0;
		//-----------------------------
		 
		yearView.setText(""+year);
		monthView.setText(""+month);
		
		
		MissionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				MissionItemView itemView = (MissionItemView)view; 
				if(click==false){
					itemView.view.setBackgroundResource(R.drawable.mission_contents_bar1_selected);
					click=true;
				} else {
					itemView.view.setBackgroundResource(R.drawable.list_selector);
					click=false;
				}
				
				itemView.requestFocus();
				itemView.setVisibileDetailView(!itemView.isVisibleDetailView());
			
			}
		});
		initData();
		
		btn=(Button)findViewById(R.id.btn_add);
		btn.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
			
				Intent intent=new Intent(MissionActivity.this,AddMissionActivity.class);
				finish();
				startActivity(intent);
			}
		}); 
		
		mCustomGallery = (CustomGallery) findViewById(R.id.gallery);
	    mCustomGallery.setAdapter(cAdapter);
	    mCustomGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
	
	    @Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {  
			 
			 yearView.setText(""+cAdapter.mImageID[position]);
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
	   
	  	mSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override  
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position == 1) {
					orderby = 2;
					initData();
				} else if(position == 0) {
					orderby = position;
					initData();
				} else if(position == 2) {
					orderby = 1;
					initData();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				orderby = 0;
			}
		});
	  
	  
	}
	
		public void initData()
		{ 
			NetworkManager.getInstnace().getMissionList(MissionActivity.this, year, month, orderby, new OnResultListener<MissionResult>() {
			
			@Override
			public void onSuccess(MissionResult result) {
				// TODO Auto-generated method stub
				mAdapter.clear();
				mAdapter.addAll(result.result.items.item);
				manTotalScore=result.result.items.m_total;
				manTotalScoreView.setText(""+manTotalScore); 
				womanTotalScore=result.result.items.f_total;
				womanTotalScoreView.setText(""+womanTotalScore); 
				manCompleteScore=result.result.items.m_copleted;
				manCompleteView.setText(""+womanCompleteScore);
				womanCompleteScore=result.result.items.f_completed;
				womanCompleteView.setText(""+womanCompleteScore);
				
			}
			
			@Override
			public void onFail(int code) {
				// TODO Auto-generated method stub
				Toast.makeText(MissionActivity.this,"실패", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
		

	public boolean isVisibleDetailView() {
		return sortLayout.getVisibility() == View.VISIBLE;
	}
		
	public void setVisibileDetailView(boolean isVisible) {
		sortLayout.setVisibility(isVisible?View.VISIBLE:View.GONE);
	} 	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.mission, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		//int id = item.getItemId();
		//if (id == R.id.action_settings) {
			//return true;
		//}
		return super.onOptionsItemSelected(item);
	}
	
}
