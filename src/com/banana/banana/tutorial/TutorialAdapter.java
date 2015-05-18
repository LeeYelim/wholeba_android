package com.banana.banana.tutorial;
 

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TutorialAdapter extends FragmentStatePagerAdapter {

	int FRAGMENT_COUNT = 4;

	public TutorialAdapter(FragmentManager fm) {
		super(fm);
	}
	
	@Override
	public Fragment getItem(int position) {
		Fragment f = null;
		switch (position) {
		case 0:
			f = new TutorialOneFragment();
			break;
		case 1:
			f = new TutorialTwoFragment();
			break;
		case 2:
			f = new TutorialThreeFragment();
			break;
		case 3:
			f = new TutorialFourFragment();
			break;
		}
		return f;
	}

	@Override
	public int getCount() {
		return FRAGMENT_COUNT;
	}

}
