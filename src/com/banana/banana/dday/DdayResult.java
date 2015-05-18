package com.banana.banana.dday;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;
  
public class DdayResult {
	@SerializedName("message")
	public String message;
	
	@SerializedName("item_cnt")
	public int item_cnt;
	
	@SerializedName("items")
	public ArrayList<Ddayitem> items;
}
