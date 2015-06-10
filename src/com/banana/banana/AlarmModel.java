package com.banana.banana;


public class AlarmModel {
	public int id;
	public String NAME = "pillsAlarm";
	public int hour = PropertyManager.getInstance().getHour();
	public int minute = PropertyManager.getInstance().getMinute();
	public boolean isEnabled;
	
}
