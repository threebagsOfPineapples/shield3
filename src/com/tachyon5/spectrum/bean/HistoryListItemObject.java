package com.tachyon5.spectrum.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryListItemObject implements Parcelable{
	
    public String str_result;
    public String str_type;
    public String str_time;

	public String getStr_result() {
		return str_result;
	}

	public void setStr_result(String str_result) {
		this.str_result = str_result;
	}

	public String getStr_type() {
		return str_type;
	}

	public void setStr_type(String str_type) {
		this.str_type = str_type;
	}

	public String getStr_time() {
		return str_time;
	}

	public void setStr_time(String str_time) {
		this.str_time = str_time;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
}
