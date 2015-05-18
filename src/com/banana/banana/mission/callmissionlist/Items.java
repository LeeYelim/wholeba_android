package com.banana.banana.mission.callmissionlist;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class Items {
	public int m_total;
	public int m_copleted;
	public int f_total;
	public int f_completed;
	@SerializedName("item")
	public ArrayList<Item> item;

	 
}
