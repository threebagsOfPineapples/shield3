package com.tachyon5.spectrum.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;
/**
 * 
 * @author kang on 2017/3/23
 *
 */
public class WifiUtils {
	private WifiManager localWifiManager;//�ṩWifi����ĸ�����ҪAPI����Ҫ����wifi��ɨ�衢�������ӡ�������Ϣ��
	//private List<ScanResult> wifiScanList;//ScanResult���������Ѿ������Ľ���㣬��������ĵ�ַ�����ơ������֤��Ƶ�ʡ��ź�ǿ�ȵ�
	private List<WifiConfiguration> wifiConfigList;//WIFIConfiguration����WIFI��������Ϣ������SSID��SSID���ء�password�ȵ�����
	private WifiInfo wifiConnectedInfo;//�Ѿ��������������ӵ���Ϣ
	private WifiLock wifiLock;//�ֻ���������ֹWIFIҲ����˯��״̬��WIFI�Ĺر�
	
	public WifiUtils( Context context){
		localWifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
	}
	
    //���WIFI״̬
	/*WIFI_STATE_DISABLING  WIFI�������ڹر�  0
	WIFI_STATE_DISABLED   WIFI����������  1
	WIFI_STATE_ENABLING    WIFI�������ڴ�  2
	WIFI_STATE_ENABLED     WIFI��������  3
	WIFI_STATE_UNKNOWN    WIFI����״̬����֪ 4*/
	public int WifiCheckState(){
		return localWifiManager.getWifiState();
	}
	
	//����WIFI
	public void WifiOpen(){
		if(!localWifiManager.isWifiEnabled()){
			localWifiManager.setWifiEnabled(true);
		}
	}
	
	//�ر�WIFI
	public void WifiClose(){
		if(localWifiManager.isWifiEnabled()){
			localWifiManager.setWifiEnabled(false);
		}
	}
	
	//ɨ��wifi
	public void WifiStartScan(){
		localWifiManager.startScan();
	}
	
	//�õ�Scan���
	public List<ScanResult> getScanResults(){
		return localWifiManager.getScanResults();//�õ�ɨ����
	}

	//Scan���תΪSting
	public List<String> scanResultToString(List<ScanResult> list){
		List<String> strReturnList = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++){
			ScanResult strScan = list.get(i);
			String str = strScan.toString();
			boolean bool = strReturnList.add(str);
			if(!bool){
				Log.i("scanResultToSting","Addfail");
			}
		}
		return strReturnList;
	}
	
	//�õ�Wifi���úõ���Ϣ
	public void getConfiguration(){
		wifiConfigList = localWifiManager.getConfiguredNetworks();//�õ����úõ�������Ϣ
		for(int i =0;i<wifiConfigList.size();i++){
			Log.i("getConfiguration1",wifiConfigList.get(i).SSID);
			Log.i("getConfiguration2",String.valueOf(wifiConfigList.get(i).networkId));
		}
	}
	//�ж�ָ��WIFI�Ƿ��Ѿ����ú�,����WIFI�ĵ�ַBSSID,����NetId
	public int IsConfiguration(String SSID){
		Log.i("IsConfiguration",String.valueOf(wifiConfigList.size()));
		for(int i = 0; i < wifiConfigList.size(); i++){
			Log.i(wifiConfigList.get(i).SSID,String.valueOf( wifiConfigList.get(i).networkId));
			if(wifiConfigList.get(i).SSID.equals(SSID)){//��ַ��ͬ
				return wifiConfigList.get(i).networkId;
			}
		}
		return -1;
	}

	//���ָ��WIFI��������Ϣ,ԭ�б����ڴ�SSID
	public int AddWifiConfig(List<ScanResult> wifiList,String ssid,String pwd){
		int wifiId = -1;
		for(int i = 0;i < wifiList.size(); i++){
			ScanResult wifi = wifiList.get(i);
			if(wifi.SSID.equals(ssid)){
				Log.i("AddWifiConfig","equals");
				WifiConfiguration wifiCong = new WifiConfiguration();
				wifiCong.SSID = "\""+wifi.SSID+"\"";//\"ת���ַ�������"
				wifiCong.preSharedKey = "\""+pwd+"\"";//WPA-PSK����
				wifiCong.hiddenSSID = false;
				wifiCong.status = WifiConfiguration.Status.ENABLED;
				wifiId = localWifiManager.addNetwork(wifiCong);//�����úõ��ض�WIFI������Ϣ���,�����ɺ�Ĭ���ǲ�����״̬���ɹ�����ID������Ϊ-1
				if(wifiId != -1){
					return wifiId;
				}
			}
		}
		return wifiId;
	}
	
	//����ָ��Id��WIFI
	public boolean ConnectWifi(int wifiId){
		for(int i = 0; i < wifiConfigList.size(); i++){
			WifiConfiguration wifi = wifiConfigList.get(i);
			if(wifi.networkId == wifiId){
				while(!(localWifiManager.enableNetwork(wifiId, true))){//�����Id����������
					Log.i("ConnectWifi",String.valueOf(wifiConfigList.get(wifiId).status));//status:0--�Ѿ����ӣ�1--�������ӣ�2--��������
				}
				return true;
			}
		}
		return false;
	}
	
	//����һ��WIFILock
	public void createWifiLock(String lockName){
		wifiLock = localWifiManager.createWifiLock(lockName);
	}
	
	//����wifilock
	public void acquireWifiLock(){
		wifiLock.acquire();
	}
	
	//����WIFI
	public void releaseWifiLock(){
		if(wifiLock.isHeld()){//�ж��Ƿ�����
			wifiLock.release();
		}
	}
	
	//�õ��������ӵ���Ϣ
	public void getConnectedInfo(){
		wifiConnectedInfo = localWifiManager.getConnectionInfo();
	}
	//�õ����ӵ�MAC��ַ
	public String getConnectedMacAddr(){
		return (wifiConnectedInfo == null)? "NULL":wifiConnectedInfo.getMacAddress();
	}
	
	//�õ����ӵ�����SSID
	public String getConnectedSSID(){
		return (wifiConnectedInfo == null)? "NULL":wifiConnectedInfo.getSSID();
	}
	
	//�õ����ӵ�IP��ַ
	public int getConnectedIPAddr(){
		return (wifiConnectedInfo == null)? 0:wifiConnectedInfo.getIpAddress();
	}
	
	//�õ����ӵ�ID
	public int getConnectedID(){
		return (wifiConnectedInfo == null)? 0:wifiConnectedInfo.getNetworkId();
	}
	
	//-----------------------------------------------------------kang
    //��������  �������
	public void removeNet(int netid){
		localWifiManager.removeNetwork(netid);
	}
	//���ص�ǰ���ӵ� wifi ���֡�
	public String getConnectWifiSsid(){  
		WifiInfo wifiInfo = localWifiManager.getConnectionInfo();  
		Log.d("wifiInfo", wifiInfo.toString());  
		Log.d("SSID",wifiInfo.getSSID());  
		return wifiInfo.getSSID();  
		} 
	
    	
}
