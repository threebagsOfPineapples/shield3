package com.tachyon5.spectrum.common;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.tachyon5.spectrum.utils.Contents;

public class IntentFilterManger {
	
	/** 
	 * 注册广播 intentfilter
	 * @return
	 */
	public static IntentFilter baseIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);// wifi网络状态改变
		intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);// wifi连接上与否
		intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);// wifi打开与否
		intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);// 电量发生改变时
		intentFilter.addAction(Intent.ACTION_BATTERY_LOW);// 电池电量达到下限时
		intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);// 电池电量从低恢复到高时
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);// 网络状态
		return intentFilter;
	}
	
	/** 
	 * 注册广播 intentfilter checkActivity
	 * @return
	 */
	public static IntentFilter CheckActivityIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(Contents.ACTION_FW_VERSON);
		intentFilter.addAction(Contents.ACTION_SAMPLE);
		intentFilter.addAction(Contents.ACTION_DARK);
		intentFilter.addAction(Contents.ACTION_REF);
		return intentFilter;
	}
	
	
	
}
