package com.banana.banana.tutorial;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.banana.banana.R;
import com.banana.banana.intro.IntroActivity;

public class TutorialFourFragment extends Fragment {
	
	Button gointroButton;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tutorial_four_layout, container, false);
		
		gointroButton = (Button)view.findViewById(R.id.btn_go_intro);
		gointroButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), IntroActivity.class);
				startActivity(intent);

				getActivity().finish(); 
			}
		});
		return view;
	}
}
