package com.banana.banana.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.banana.banana.R;

public class SymtomInfoActivity extends ActionBarActivity {

 
	Button btn_before, btn_next;
	ListLinearLayout symtomListView;
	TextView textSymtomGuide;
	
	//SymtomAdapter mAdapter;
	int[] textViewList = { 
			R.id.textSymtom1, 
			R.id.textSymtom2, 
			R.id.textSymtom3,
			R.id.textSymtom4, 
			R.id.textSymtom5, 
			R.id.textSymtom6,
			R.id.textSymtom7, 
			R.id.textSymtom8, 
			R.id.textSymtom9,
			R.id.textSymtom10, 
			R.id.textSymtom11, 
			R.id.textSymtom12,
			R.id.textSymtom13,
			R.id.textSymtom14, 
			R.id.textSymtom15, 
			R.id.textSymtom16,
			R.id.textSymtom17 
			};
	SyndromeList[] dataList = SyndromeList.SYNDROME_DATA;
	View.OnClickListener textViewClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int index = (Integer)v.getTag();
			symtomListView.set(dataList[index], v); 
			textSymtomGuide.setVisibility(View.INVISIBLE);
		}
	};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_symtom_info);

		textSymtomGuide = (TextView)findViewById(R.id.text_guide);
		for (int i = 0; i < textViewList.length; i++) {
			TextView tv = (TextView)findViewById(textViewList[i]);
			tv.setTag((Integer)i);
			tv.setOnClickListener(textViewClickListener);
		}
		symtomListView = (ListLinearLayout)findViewById(R.id.list_Symtom);

			btn_before = (Button)findViewById(R.id.btn_before); 
			btn_before.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			
			btn_next = (Button)findViewById(R.id.btn_next);
			btn_next.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) { 

//					ArrayList<SyndromeList> items = mAdapter.items;

					/*Intent i = getIntent();
					Bundle bundle = i.getExtras();   
					Intent intent = new Intent(SymtomInfoActivity.this, UseInfoActivity.class);
					intent.putExtra("items", items);
					intent.putExtra("bundle", bundle);
					startActivity(intent);*/

					Bundle bundle = getIntent().getExtras();
					WomanInfoParcelData wdata = bundle.getParcelable("wdata"); 	 
					boolean isEmpty = false;
					
					if(symtomListView.getSyndromeList().size()==0) {
						Toast.makeText(SymtomInfoActivity.this, "최소 1개 이상 입력 선택 가능합니다.", Toast.LENGTH_SHORT).show();
					} else {
						for (int i = 0; i < symtomListView.getSyndromeList().size(); i++) {
							if(symtomListView.getSyndromeList().get(i).syndrome_after == 0 || symtomListView.getSyndromeList().get(i).syndrome_before == 0) { 
								isEmpty = true;
							}  
						}
							if(isEmpty == false) {
								wdata.syndromes = symtomListView.getSyndromeList();
								Intent intent = new Intent(SymtomInfoActivity.this, UseInfoActivity.class);
								intent.putExtra("wdata", wdata);
								startActivity(intent);	
							} else {
								Toast.makeText(SymtomInfoActivity.this, "최소 1개 이상 입력 선택 가능합니다.", Toast.LENGTH_SHORT).show();
							}
					}
					
					
					//wdata.syndromes = items;
					
				}
			});	
			
			
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.symtom_info, menu);
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
}
