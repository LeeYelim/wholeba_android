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
	int MissionState,year,month,day;
	String Mission_hint,Mission_valid;
	String str_year,str_month,str_day;
	int theme_no;
	public MissionItemView(Context context) {
		super(context);
		init();
	}

	public MissionItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.mission_itemdata, this);
		GenderView = (ImageView)findViewById(R.id.img_mission1);
		detailView=(View)findViewById(R.id.detailView);
		view=(View)findViewById(R.id.RelativeLayout1);
			
		valid_view=(TextView)findViewById(R.id.text_date);
		titleView = (TextView)findViewById(R.id.text_missionName);
		stateView=(TextView)findViewById(R.id.missionState);
		hintView=(TextView)findViewById(R.id.mission_hint);
		missionValidView=(TextView)findViewById(R.id.Mission_valid);
		
	}
	
	public boolean isVisibleDetailView() {
		return detailView.getVisibility() == View.VISIBLE;
	}
	
	public void setVisibileDetailView(boolean isVisible) {
		detailView.setVisibility(isVisible?View.VISIBLE:View.GONE);
	}
	
	public void setItemData(MissionItemData data) throws ParseException {
		mData = data;
		theme_no=mData.theme_no;
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
		
		
		MissionState=mData.mlist_state;
		if(MissionState==0){
			stateView.setText("실패");
			valid_view.setText(mData.mlist_expiredate);
		}else if(MissionState==1){
			stateView.setText("성공");
			valid_view.setText(mData.mlist_successdate);
		}else if(MissionState==2){
			stateView.setText("확인안함");
			valid_view.setText(mData.mlist_regdate);
			
		}else if(MissionState==3){
			stateView.setText("진행중");
			valid_view.setText(mData.mlist_regdate);
		}else if(MissionState==4){
			stateView.setText("패스");
			valid_view.setText(mData.mlist_regdate);
		}
		//성별
		setGenderView();
		//힌트처리 -------------------------
		Mission_hint=mData.mission_hint;
		//valid_view.setText("Zz");
		setMissionHint();
		
		//hintView.setText(Mission_hint);
		//미션 유효기간-------------------------------
		//setValidView();
		
	}
	public void setMissionHint()
	{
		hintView.setText(mData.mission_hint);//힌트 설정
	}
	/*public void setValidView() throws ParseException
	{
		if(mData.mlist_state==0){//실패
			
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		    
		     Date dateexpiredate=formatter.parse(mData.mlist_expiredate);
		    
		    
		    String Mission_expiredate = formatter.format(dateexpiredate);
		    valid_view.setText(Mission_expiredate);//미션 유효기간 
		}else if(mData.mlist_state==1){//성공
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		    
		     Date datesuccessdate=formatter.parse(mData.mlist_successdate);
		    
		    
		    String Mission_success = formatter.format(datesuccessdate);
		    valid_view.setText(Mission_success);//미션 유효기간 
			Date today = mData.mlist_successdate;//유효기간
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		    String Mission_success= formatter.format(today);
		    valid_view.setText(Mission_success); //미션 성공 날짜
		}else if(mData.mlist_state==2){//미션 확인 안함 
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    
    Date dateregdate=formatter.parse(mData.mlist_regdate);
   
   
   String Mission_regdate = formatter.format(dateregdate);
   valid_view.setText(Mission_regdate);//미션 유효기간 
			Date today = mData.mlist_regdate;//미션 생성 날짜
		    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		    String Mission_regdate= formatter.format(today);
		    valid_view.setText(Mission_regdate); //미션 생성 날짜 
		}else if(mData.mlist_state==3){//진행중
	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
	    
     Date dateexpiredate=formatter.parse(mData.mlist_expiredate);
    
    
    String Mission_expiredate = formatter.format(dateexpiredate);
    valid_view.setText(mData.mlist_expiredate);//미션 유효기간 
		}else if(mData.mlist_state==4){//패스
			
		}
	
	}*/
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
