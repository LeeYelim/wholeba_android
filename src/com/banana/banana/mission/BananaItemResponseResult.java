package com.banana.banana.mission;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class BananaItemResponseResult {

	String message;
	int item_cnt;
	@SerializedName("items")
	public ArrayList<BananaItemData> item;
}
