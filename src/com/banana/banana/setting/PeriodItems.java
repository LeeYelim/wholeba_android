package com.banana.banana.setting;

import android.os.Parcel;
import android.os.Parcelable;

public class PeriodItems implements Parcelable {
	public int period_no;
	public String period_start;
	public String period_end;
	public int period_cycle;
	
	public PeriodItems(int no, String start, String end , int cycle) {
		period_no = no;
		period_start = start;
		period_end = end;
		period_cycle = cycle; 
	}

	public PeriodItems() {
		// TODO Auto-generated constructor stub
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
		dest.writeInt(period_cycle); 
		dest.writeInt(period_no);
	}
	
	public static final Parcelable.Creator<PeriodItems> CREATOR 
	= new Parcelable.Creator<PeriodItems>() {
		
		@Override
		public PeriodItems[] newArray(int size) {
			// TODO Auto-generated method stub
			return new PeriodItems[size];
		}
		
		@Override
		public PeriodItems createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			return new PeriodItems(src);
		}
	};
	public PeriodItems(Parcel src) {
		period_start = src.readString();
		period_end = src.readString();
		period_cycle = src.readInt(); 
		period_no = src.readInt();
	}
}
