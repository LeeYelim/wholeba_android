package com.banana.banana;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PropertyManager {
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	private PropertyManager() {
		mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
		mEditor = mPrefs.edit();
	}
	 
	private static final String KEY_IS_FIRST = "isfirst";
	public void setIsFirst(Boolean isFirst) {
		mEditor.putBoolean(KEY_IS_FIRST, isFirst);
		mEditor.commit();
	}
	public Boolean getIsFirst() {
		return mPrefs.getBoolean(KEY_IS_FIRST, true);
	}
	private static final String FIELD_REG_ID = "regid";
	public void setRegistrationId(String regid) {
		mEditor.putString(FIELD_REG_ID, regid);
		mEditor.commit();
	} 
	public String getRegistrationId() {
		return mPrefs.getString(FIELD_REG_ID, "");
	} 
	private static final String KEY_USER_ID = "userid";
	public void setUserId(String userid) {
		mEditor.putString(KEY_USER_ID, userid);
		mEditor.commit();
	}
	public String getUserId() {
		return mPrefs.getString(KEY_USER_ID, "");
	}
	
	private static final String KEY_PASSWORD = "password";
	public void setPassword(String password) {
		mEditor.putString(KEY_PASSWORD, password);
		mEditor.commit();
	}
	public String getPassword() {
		return mPrefs.getString(KEY_PASSWORD, "");
	} 

	private static final String KEY_GENDER = "gender";
	public void setUserGender(String gender) {
		mEditor.putString(KEY_GENDER, gender);
		mEditor.commit();
	}
	public String getUserGender() {
		return mPrefs.getString(KEY_GENDER, "");
	}


	private static final String CHIP_COUNT = "chip_count";
	public void setChipCount(int chipCount){
		mEditor.putInt(CHIP_COUNT, chipCount);
		mEditor.commit();
	}
	public int getChipCount()
	{
		return mPrefs.getInt(CHIP_COUNT, -1);
	}
	
	private static final String COUPLE_CONDOM = "couple_condom";
	public void setCoupleCondom(int coupleCondom) {
		mEditor.putInt(COUPLE_CONDOM, coupleCondom);
		mEditor.commit();
	}
	public int getCoupleCondom() {
		
		return mPrefs.getInt(COUPLE_CONDOM, -1);
	}
	
	private static final String COUPLE_DAYS = "coupleDays";
	public void setCoupleDays(int coupledays) {
		mEditor.putInt(COUPLE_DAYS, coupledays);
		mEditor.commit();
	}
	public int getCoupleDays() {
		
		return mPrefs.getInt(COUPLE_DAYS, 0);
	}
	
	private static final String ALARM_ONOFF = "alarmOnoff";
	public void setAlarmOnOff(boolean isOn) {
		mEditor.putBoolean(ALARM_ONOFF, isOn); 
		mEditor.commit();
	}
	
	public boolean getAlarmOnOff() {
		return mPrefs.getBoolean(ALARM_ONOFF, false);
	}
	 
	private static final String ALARM_HOUR = "hour";
	public void setHour(int hour) {
		mEditor.putInt(ALARM_HOUR, hour); 
		mEditor.commit();
	}
	
	public int getHour() {
		return mPrefs.getInt(ALARM_HOUR, -1);
	}
	
	private static final String ALARM_MINUTE = "minute";
	public void setMinute(int minute) {
		mEditor.putInt(ALARM_MINUTE, minute); 
		mEditor.commit();
	}
	
	public int getMinute() {
		return mPrefs.getInt(ALARM_MINUTE, -1);
	} 
	private static final String ALARM_COUNT = "alarm_count";
	public void setAlarmCount(int count) {
		mEditor.putInt(ALARM_COUNT, count); 
		mEditor.commit();
	}
	
	public int getAlarmCount() {
		return mPrefs.getInt(ALARM_COUNT, -1);
	} 
	
	private static final String USE_PUSH = "use_push";
	public void setUsePush(int use) {
		mEditor.putInt(USE_PUSH, use);
		mEditor.commit();
	}
	public int getUsePush() {
		return mPrefs.getInt(USE_PUSH, 1);
	}
}
