package com.banana.banana.love;

import com.google.gson.annotations.SerializedName;


public class LoveSearchResult {
	@SerializedName("success")
	public int success;
	
	@SerializedName("result")
	public LoveList result;
}
