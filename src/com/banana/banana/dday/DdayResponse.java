package com.banana.banana.dday;

import com.google.gson.annotations.SerializedName;


public class DdayResponse {
	@SerializedName("success")
	public int success;
	
	@SerializedName("result")
	public DdayResult result;
}
