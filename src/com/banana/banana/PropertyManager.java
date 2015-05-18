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

	private static final String CHIP_COUNT="chip_count";
	public void setChipCount(int chipCount){
		mEditor.putInt(CHIP_COUNT,chipCount);
	}
	public int getChipCount()
	{
		return mPrefs.getInt(CHIP_COUNT, 0);
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
}
