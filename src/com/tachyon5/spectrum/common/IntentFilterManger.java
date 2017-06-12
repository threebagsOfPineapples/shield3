package com.tachyon5.spectrum.common;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import com.tachyon5.spectrum.utils.Contents;

public class IntentFilterManger {
	
	/** 
	 * ע��㲥 intentfilter
	 * @return
	 */
	public static IntentFilter baseIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);// wifi����״̬�ı�
		intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);// wifi���������
		intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);// wifi�����
		intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);// ���������ı�ʱ
		intentFilter.addAction(Intent.ACTION_BATTERY_LOW);// ��ص����ﵽ����ʱ
		intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);// ��ص����ӵͻָ�����ʱ
		intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);// ����״̬
		return intentFilter;
	}
	
	/** 
	 * ע��㲥 intentfilter checkActivity
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
