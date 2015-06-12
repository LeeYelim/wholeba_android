package com.banana.banana.mission;
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.banana.banana.R;

public class MissionItemView extends FrameLayout {
	ImageView GenderView;
	TextView titleView,stateView,hintView,missionValidView,valid_view;
	MissionItemData mData;
	View detailView;
	View view;
	int MissionState, year, month,day, theme_no;
	int checked = 0;
	String Mission_hint,Mission_valid;
	String str_year,str_month,str_day; 
	
	public MissionItemView(Context context) {
		super(context);
		init();
	}

	public MissionItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public interface OnMissionListClickListener {
		public void OnMissionListClick(View view, MissionItemData data);
	}
	
	OnMissionListClickListener viewlistener;
	
	public void setOnMissionListClickListener(
			OnMissionListClickListener listener) {
		viewlistener = listener;
	}
	 
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.mission_itemdata, this);
		GenderView = (ImageView)findViewById(R.id.img_mission1);
		detailView=(View)findViewById(R.id.detailView);
		view=(View)findViewById(R.id.layoutMissionList);  
		view.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(viewlistener != null) {
					viewlistener.OnMissionListClick(v, mData);
				}
				
				setVisibileDetailView(!isVisibleDetailView());
				if(checked == 0) {
					view.setBackgroundResource(R.drawable.mission_contents_bar1_selected);
					checked++;
				} else {
					view.setBackgroundResource(R.drawable.mission_contents_bar1);
					checked--;
				}
			}
		});
		
		
		
		valid_view=(TextView)findViewById(R.id.text_date);
		titleView = (TextView)findViewById(R.id.text_missionName);
		stateView=(TextView)findViewById(R.id.missionState);
		hintView=(TextView)findViewById(R.id.mission_hint); 
		
	}
	
	public boolean isVisibleDetailView() {
		return detailView.getVisibility() == View.VISIBLE;
	}
	
	public void setVisibileDetailView(boolean isVisible) {
		detailView.setVisibility(isVisible?View.VISIBLE:View.GONE);
	}
	
	
	
	
	
	public void setItemData(MissionItemData data) {
		mData = data;
		theme_no = mData.theme_no;
		if(theme_no==1){
			titleView.setText("악마미션");
		}else if(theme_no==3){
			titleView.setText("섹시미션");
		}else if(theme_no==2){
			titleView.setText("처음미션");
		}else if(theme_no==4){
			titleView.setText("애교미션");
		}else if(theme_no==5){
			titleView.setText("천사미션");
		} 
		
		MissionState = mData.mlist_state;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat kdf = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
		Date d;
		
		try {
		if(MissionState==0){
			stateView.setText("실패");
			valid_view.setText("유효기간 " + kdf.format(df.parse(mData.mlist_expiredate)));
		}else if(MissionState==1){
			stateView.setText("성공");
			valid_view.setText("성공날짜 " + mData.mlist_successdate);
		}else if(MissionState==2){
			stateView.setText("확인안함");
			valid_view.setText("시작날짜 " + kdf.format(df.parse(mData.mlist_regdate)));
		}else if(MissionState==3){
			stateView.setBackgroundResource(R.drawable.mission_contents_yellow_icon);
			valid_view.setText("유효기간 " + kdf.format(df.parse(mData.mlist_expiredate)));
		}else if(MissionState==4){
			stateView.setText("패스");
			valid_view.setText("유효기간 " + kdf.format(df.parse(mData.mlist_expiredate)));
		}}catch (NullPointerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//성별
		setGenderView();
		//힌트처리 -------------------------
		Mission_hint=mData.mission_hint; 
		setMissionHint(); 
		
	}
	public void setMissionHint()
	{
		hintView.setText("힌트  " + "#" + mData.mission_hint);//힌트 설정
	}
	public void setGenderView(){
		
		if(mData.user_gender.equals("M"))//남자
		{
		    if(MissionState==3){//미션 진행중
				GenderView.setImageResource(R.drawable.mission_contents_man_ing_icon);
			}else{//그 이외상황(상대방이 확인안함,실패,패스,완료)
				GenderView.setImageResource(R.drawable.mission_contents_man_icon);
			}
		}
		else{//여자

			if(MissionState==3){//미션 진행중
				GenderView.setImageResource(R.drawable.mission_contents_woman_ing_icon);
			}else{//그 이외상황(상대방이 확인안함,실패,패스,완료)
				GenderView.setImageResource(R.drawable.mission_contents_woman_icon);
			}
		}
	}
	public String getTitle() {
		return mData.mlist_name;
	} 
}
