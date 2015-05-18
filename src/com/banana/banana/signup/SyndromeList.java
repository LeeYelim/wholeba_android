package com.banana.banana.signup;

import com.banana.banana.R;

import android.os.Parcel;
import android.os.Parcelable;

public class SyndromeList implements Parcelable {
	int syndrome_no;
	int syndrome_before;
	int syndrome_after;
	
	public static final int SYNDROME_NAME[] = {  
		R.string.symtom1,
		R.string.symtom2,
		R.string.symtom3,
		R.string.symtom4,
		R.string.symtom5,
		R.string.symtom6,
		R.string.symtom7,
		R.string.symtom8,
		R.string.symtom9,
		R.string.symtom10,
		R.string.symtom11,
		R.string.symtom12,
		R.string.symtom13,
		R.string.symtom14,
		R.string.symtom15,
		R.string.symtom16,
		R.string.symtom17  
		};
	
	public static final SyndromeList SYNDROME_DATA[] = {
		new SyndromeList(1,0,0),
		new SyndromeList(2,0,0),
		new SyndromeList(3,0,0),
		new SyndromeList(4,0,0),
		new SyndromeList(5,0,0),
		new SyndromeList(6,0,0),
		new SyndromeList(7,0,0),
		new SyndromeList(8,0,0),
		new SyndromeList(9,0,0),
		new SyndromeList(10,0,0),
		new SyndromeList(11,0,0),
		new SyndromeList(12,0,0),
		new SyndromeList(13,0,0),
		new SyndromeList(14,0,0),
		new SyndromeList(15,0,0),
		new SyndromeList(16,0,0),
		new SyndromeList(17,0,0)
	};

	public SyndromeList(int no, int before, int after) {
		syndrome_no = no;
		syndrome_before = before;
		syndrome_after = after;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(syndrome_no);
		dest.writeInt(syndrome_before);
		dest.writeInt(syndrome_after);
	}

	public static final Parcelable.Creator<SyndromeList> CREATOR = new Creator<SyndromeList>() {

		@Override
		public SyndromeList[] newArray(int size) {
			return new SyndromeList[size];
		}

		@Override
		public SyndromeList createFromParcel(Parcel source) {
			return new SyndromeList(source);
		}
	};

	public SyndromeList(Parcel src) {
		syndrome_no = src.readInt();
		syndrome_before = src.readInt();
		syndrome_after = src.readInt();
	}

	
	public SyndromeList() {
		// TODO Auto-generated constructor stub
	}
}
