package com.tachyon5.spectrum.activity;

import java.util.Date;

import com.tachyon5.spectrum.common.IntentFilterManger;
import com.tachyon5.spectrum.serialport.SerialPortUtil;
import com.tachyon5.spectrum.serialport.SerialPortUtil.OnDataReceiveListener;
import com.tachyon5.spectrum.utils.CommonUtils;
import com.tachyon5.spectrum.utils.Contents;
import com.tachyon5.spectrum.utils.SystemInformationUtils;
import com.tachyon5.spectrum.utils.ViewSetUtils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
/**
 * 
 * @author knag
 *
 */
public abstract class BaseActivity extends Activity {
	public Context mContext;

	/**
	 * �ź�״̬ 0: ���ź� 1: ���ź�
	 */
	public int Signal_TAG;
	
	/**
	 * ������״̬ 0:����Ӫ�̻�δ֪ 1���й��ƶ� 2���й���ͨ 3���й�����
	 */
	public int Carrieroperator_TAG;
	/**
	 * wifi״̬ 0:δ���� 1������
	 */
	public int Wifi_TAG;

	/**
	 * �ֻ�����״̬ 0:������  1 ��δ֪����  2��2G 3:3G 4:4G
	 */
	public int Network_TAG;

	/**
	 * ����״̬ 0:��� 1��һ�� 2:���� 3:���� 4:4��
	 */
	public int Electricity_TAG;
	
	public static String logTAG="SpectrumLog";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this;
		registerReceiver(baseReceiver, IntentFilterManger.baseIntentFilter()); // ע��㲥
		
		Signal_TAG=SystemInformationUtils.hasSimCard(mContext)? 1 : 0;//��������SIM���ж������ź�
		
	}
	

	private final BroadcastReceiver baseReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
				// ������Ϣ
				int current = intent.getExtras().getInt("level");// ��õ�ǰ����
				int total = intent.getExtras().getInt("scale");// ����ܵ���
				
				// ��ǰ�ֻ�ʹ�õ�������ĵ�Դ
				int pluged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
				switch (pluged) {
				case BatteryManager.BATTERY_PLUGGED_AC:
					// ��Դ��AC charger.[Ӧ����ָ�����]
					// Toast.makeText(mContext, "��Դ��AC charger", 0).show();
					Electricity_TAG=0;
					break;
				case BatteryManager.BATTERY_PLUGGED_USB:
					// ��Դ��USB port
					// Toast.makeText(mContext, "��Դ��USB port", 0).show();
					Electricity_TAG=0;
					break;
				default:
					
					if (!(current == 0 || total == 0)) {
						int percent=current*100/total; //�����ٷֱ�
						if(percent>0&&percent<=25){
							Electricity_TAG=1;
						}else if(percent>25&&percent<=50){
							Electricity_TAG=2;
						}else if(percent>50&&percent<=75){
							Electricity_TAG=3;
						}else if(percent>75&&percent<=100){
							Electricity_TAG=4;
						}else{
							
						}
					}
					break;
				}
				// ��س��״̬
				int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

				switch (status) {
				case BatteryManager.BATTERY_STATUS_CHARGING:
					// ���ڳ��
					// Toast.makeText(mContext, "���ڳ��", 0).show();
					Electricity_TAG=0;
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					// Toast.makeText(mContext, "�ŵ�״̬", 0).show();
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					// ����
					// Toast.makeText(mContext, "����", 0).show();
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					// û�г��
					// Toast.makeText(mContext, "û�г��", 0).show();
					break;
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					// δ֪״̬
					// Toast.makeText(mContext, "δ֪״̬", 0).show();
					break;
				default:
					break;
				}
				// ��ؽ���״̬
				int health = intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN);
				switch (health) {
				case BatteryManager.BATTERY_HEALTH_UNKNOWN:
					// "δ֪����";
					break;
				case BatteryManager.BATTERY_HEALTH_GOOD:
					// "״̬����";
					break;
				case BatteryManager.BATTERY_HEALTH_DEAD:
					// "���û�е�";
					break;
				case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
					// "��ص�ѹ����";
					break;
				case BatteryManager.BATTERY_HEALTH_OVERHEAT:
					// "��ع���";
					break;
				default:
					break;
				}
				changeElectricity();
				
				
			} else if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
				// ��ʾ��ǰ��ص�����
			} else if (intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)) {
				// ��ʾ��ǰ����Ѿ��ӵ����ͻָ�Ϊ����
			} else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
				// wifi���������
				NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
				if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
					// "wifi�������ӶϿ�");
				} else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
					// ������wifi
				}

			} else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				// ����״̬�����ı�
				ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (mobNetInfo.isConnected()) {
					// �ֻ���������
					Toast.makeText(mContext, "�ֻ���������", 0).show();
					Wifi_TAG=0;
					Signal_TAG=1; //�ֻ������� �ж�Ϊ���ź�
				} else if (wifiNetInfo.isConnected()) {
					// wifi��������
				//	Toast.makeText(mContext, "wifi��������", 0).show();
					Wifi_TAG=1;
				} else {
					// ����������
					Toast.makeText(mContext, "����������", 0).show();
					Wifi_TAG=0;
				}
				Network_TAG=SystemInformationUtils.getNetworkState(mContext);
				Carrieroperator_TAG=SystemInformationUtils.getCarrieroperator(mContext);
				changeSignal();
				changeNetWork();
				changeWifi();
				changeCarrieroperator();
			}
		}
	};

	//״̬�� ״̬����----------------------------------------------
	
	// �����ź�״̬
	protected void changeSignal(){};

	// ������Ӫ��״̬ �ƶ� ��ͨ ����
	protected  void changeCarrieroperator(){};

	// �����ֻ�����״̬ 2g 3g 4g
	protected  void changeNetWork(){};

	// ����wifi״̬
	protected  void changeWifi(){};

	// ���ĵ��״̬
	protected  void changeElectricity(){};
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		changeSignal();
		changeCarrieroperator();
	
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(baseReceiver);
	}
}
