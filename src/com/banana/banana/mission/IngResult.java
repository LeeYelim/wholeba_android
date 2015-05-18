package com.banana.banana.mission;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class IngResult {

	public String message;
	public int item_cnt;
	@SerializedName("items")
	public ArrayList<IngItemData> items;
}
