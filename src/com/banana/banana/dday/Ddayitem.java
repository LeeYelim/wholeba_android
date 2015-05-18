package com.banana.banana.dday;

import com.google.gson.annotations.SerializedName;


public class Ddayitem { 
	@SerializedName("dday_no")
	public int dday_no;
	
	@SerializedName("dday_name")
	public String dday_name;
	
	@SerializedName("dday_date")
	public String dday_date;
	
	@Override
	public String toString() {
		return dday_name;
	}
}
