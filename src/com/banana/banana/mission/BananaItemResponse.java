package com.banana.banana.mission;

import com.google.gson.annotations.SerializedName;

public class BananaItemResponse {
	public int success;

	@SerializedName("result")
	public BananaItemResponseResult result;
}
