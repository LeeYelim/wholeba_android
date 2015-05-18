package com.banana.banana.mission;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.R;
import com.banana.banana.love.NetworkManager;
import com.banana.banana.love.NetworkManager.OnResultListener;

public class MissionActivity extends ActionBarActivity {
	int orderby=0;
	ListView MissionListView;
	MissionAdapter mAdapter;
	Button btn ;	
	Spinner mSpinner=null;
	String[] mData=null;
	ArrayAdapter<String> mArrayAdapter=null;
	boolean click=false;
	int year, month;
	int manTotalScore,womanTotalScore;
	TextView manTotalScoreView,womanTotalScoreView;
	int manCompleteScore,womanCompleteScore;
	TextView manCompleteView,womanCompleteView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mission);
		MissionListView=(ListView)findViewById(R.id.listView1);
		mAdapter=new MissionAdapter(this);
		MissionListView.setAdapter(mAdapter);
		mSpinner=(Spinner)findViewById(R.id.sort);//정렬 spinner설정
		mData=getResources().getStringArray(R.array.list_sort);//string배열 얻어오기
		mArrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,mData);
		mSpinner.setAdapter(mArrayAdapter);
		//임시로
		year=2015;
		month=5;
		orderby=0;
		//-----------------------------
		
		//toal score--------------------------------------------------------
		manTotalScoreView=(TextView)findViewById(R.id.man_receiveTotal);
		womanTotalScoreView=(TextView)findViewById(R.id.woman_receiveTotal);
		
		manCompleteView=(TextView)findViewById(R.id.manScore);
		womanCompleteView=(TextView)findViewById(R.id.womanScore);
		
		
		MissionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				MissionItemView itemView = (MissionItemView)view; 
				if(click==false){
				itemView.view.setBackgroundResource(R.drawable.mission_contents_bar1_selected);
				click=true;
				}else{
					itemView.view.setBackgroundResource(R.drawable.mission_contents_bar1);
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
				
				startActivity(intent);
			}
		});



	
}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mission, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
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
				Log.i("manTotal", ""+manTotalScore);
				womanTotalScore=result.result.items.f_total;
				womanTotalScoreView.setText(""+womanTotalScore);
				Log.i("womanTotal", ""+womanTotalScore);
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
	
	
}
