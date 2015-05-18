package com.banana.banana.mission.receive;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.R;
import com.winsontan520.WScratchView;


public class PullMissionActivity extends ActionBarActivity {
	private TextView percentageView;
	private WScratchView scratchView;
	private float mPercentage;
	Button btn_chance;
	String s;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pull_mission);
		
		scratchView = (WScratchView) findViewById(R.id.scratch_view);
		scratchView.setScratchable(true);
		scratchView.setRevealSize(50);
		scratchView.setAntiAlias(true);
		scratchView.setOverlayColor(Color.RED);
		scratchView.setBackgroundClickable(true);

		// add callback for update scratch percentage
		scratchView.setOnScratchCallback(new WScratchView.OnScratchCallback() {

			@Override
			public void onScratch(float percentage) {
				updatePercentage(percentage);
			}

			@Override
			public void onDetach(boolean fingerDetach) {
				if(mPercentage > 8){
					scratchView.setScratchAll(true);
					updatePercentage(100);
				}
			}
		});
		
		scratchView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Toast.makeText(PullMissionActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
			}
			
		});

		updatePercentage(0f);
		
	}
	protected void updatePercentage(float percentage) {
		mPercentage = percentage;
		String percentage2decimal = String.format("%.2f", percentage) + " %";
		//percentageView.setText(percentage2decimal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pull_mission, menu);
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
	public void onClickHandler(View view) {
		//switch (view.getId()) {
		//case R.id.reset_button:
			//scratchView.resetView();
			//scratchView.setScratchAll(false); // todo: should include to resetView?
			//updatePercentage(0f);
			//Intent receive=getIntent();
			//s=receive.getStringExtra("pushMission");
			//Intent intent=new Intent(PullMissionActivity.this,MissionActivity.class);
			//intent.putExtra("pushMission",s);
			//startActivity(intent);
			//break;
		//}
	}
}
