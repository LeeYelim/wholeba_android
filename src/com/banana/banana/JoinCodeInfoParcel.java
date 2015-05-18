package com.banana.banana;

import android.os.Parcel;
import android.os.Parcelable;

public class JoinCodeInfoParcel implements Parcelable{
	public int join_code;
	  
	public JoinCodeInfoParcel() {
		// TODO Auto-generated constructor stub
	}
	 
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(join_code);
	}

	public static final Parcelable.Creator<JoinCodeInfoParcel> CREATOR 
	= new Parcelable.Creator<JoinCodeInfoParcel>() {
		
		@Override
		public JoinCodeInfoParcel[] newArray(int size) {
			// TODO Auto-generated method stub
			return new JoinCodeInfoParcel[size];
		}
		
		@Override
		public JoinCodeInfoParcel createFromParcel(Parcel src) {
			// TODO Auto-generated method stub
			return new JoinCodeInfoParcel(src);
		}
	};
	
	public JoinCodeInfoParcel(Parcel src) {
		join_code = src.readInt();
	}
}
