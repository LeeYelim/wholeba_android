package com.banana.banana.signup;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class WomanInfoParcelData implements Parcelable{
	String period_start;
	String period_end;
	String period_cycle;
	List<SyndromeList> syndromes; 
	int couple_condom;
	int user_pills;
	String pills_date;
	String pills_time;
	
	
	public WomanInfoParcelData() { 
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(period_start);
		dest.writeString(period_end);
		dest.writeString(period_cycle);
		dest.writeList(syndromes);
		dest.writeInt(couple_condom);
		dest.writeInt(user_pills);
		dest.writeString(pills_date);
		dest.writeString(pills_time); 
	}
	public static final Parcelable.Creator<WomanInfoParcelData> CREATOR 
		= new Parcelable.Creator<WomanInfoParcelData>() {
			
			@Override
			public WomanInfoParcelData[] newArray(int size) {
				// TODO Auto-generated method stub
				return new WomanInfoParcelData[size];
			}
			
			@Override
			public WomanInfoParcelData createFromParcel(Parcel src) {
				// TODO Auto-generated method stub
				return new WomanInfoParcelData(src);
			}
		};
		public WomanInfoParcelData(Parcel src) {
			period_start = src.readString();
			period_end = src.readString();
			period_cycle = src.readString();
			syndromes = new ArrayList<SyndromeList>();
			src.readList(syndromes, SyndromeList.class.getClassLoader());
			couple_condom = src.readInt();
			user_pills = src.readInt();
			pills_date = src.readString();
			pills_time = src.readString();
		}
	
}
