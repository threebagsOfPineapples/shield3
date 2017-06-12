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
	 * 信号状态 0: 无信号 1: 有信号
	 */
	public int Signal_TAG;
	
	/**
	 * 运行商状态 0:无运营商或未知 1：中国移动 2：中国联通 3：中国电信
	 */
	public int Carrieroperator_TAG;
	/**
	 * wifi状态 0:未连接 1已连接
	 */
	public int Wifi_TAG;

	/**
	 * 手机网络状态 0:无网络  1 ：未知网络  2：2G 3:3G 4:4G
	 */
	public int Network_TAG;

	/**
	 * 电量状态 0:充电 1：一格 2:二格 3:三格 4:4格
	 */
	public int Electricity_TAG;
	
	public static String logTAG="SpectrumLog";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext = this;
		registerReceiver(baseReceiver, IntentFilterManger.baseIntentFilter()); // 注册广播
		
		Signal_TAG=SystemInformationUtils.hasSimCard(mContext)? 1 : 0;//根据有我SIM卡判断有无信号
		
	}
	

	private final BroadcastReceiver baseReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
				// 电量信息
				int current = intent.getExtras().getInt("level");// 获得当前电量
				int total = intent.getExtras().getInt("scale");// 获得总电量
				
				// 当前手机使用的是哪里的电源
				int pluged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
				switch (pluged) {
				case BatteryManager.BATTERY_PLUGGED_AC:
					// 电源是AC charger.[应该是指充电器]
					// Toast.makeText(mContext, "电源是AC charger", 0).show();
					Electricity_TAG=0;
					break;
				case BatteryManager.BATTERY_PLUGGED_USB:
					// 电源是USB port
					// Toast.makeText(mContext, "电源是USB port", 0).show();
					Electricity_TAG=0;
					break;
				default:
					
					if (!(current == 0 || total == 0)) {
						int percent=current*100/total; //电量百分比
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
				// 电池充电状态
				int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

				switch (status) {
				case BatteryManager.BATTERY_STATUS_CHARGING:
					// 正在充电
					// Toast.makeText(mContext, "正在充电", 0).show();
					Electricity_TAG=0;
					break;
				case BatteryManager.BATTERY_STATUS_DISCHARGING:
					// Toast.makeText(mContext, "放电状态", 0).show();
					break;
				case BatteryManager.BATTERY_STATUS_FULL:
					// 充满
					// Toast.makeText(mContext, "充满", 0).show();
					break;
				case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
					// 没有充电
					// Toast.makeText(mContext, "没有充电", 0).show();
					break;
				case BatteryManager.BATTERY_STATUS_UNKNOWN:
					// 未知状态
					// Toast.makeText(mContext, "未知状态", 0).show();
					break;
				default:
					break;
				}
				// 电池健康状态
				int health = intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN);
				switch (health) {
				case BatteryManager.BATTERY_HEALTH_UNKNOWN:
					// "未知错误";
					break;
				case BatteryManager.BATTERY_HEALTH_GOOD:
					// "状态良好";
					break;
				case BatteryManager.BATTERY_HEALTH_DEAD:
					// "电池没有电";
					break;
				case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
					// "电池电压过高";
					break;
				case BatteryManager.BATTERY_HEALTH_OVERHEAT:
					// "电池过热";
					break;
				default:
					break;
				}
				changeElectricity();
				
				
			} else if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
				// 表示当前电池电量低
			} else if (intent.getAction().equals(Intent.ACTION_BATTERY_OKAY)) {
				// 表示当前电池已经从电量低恢复为正常
			} else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
				// wifi连接上与否
				NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
				if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
					// "wifi网络连接断开");
				} else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
					// 连接上wifi
				}

			} else if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				// 网络状态发生改变
				ConnectivityManager connectMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
				NetworkInfo mobNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
				if (mobNetInfo.isConnected()) {
					// 手机网络连接
					Toast.makeText(mContext, "手机网络连接", 0).show();
					Wifi_TAG=0;
					Signal_TAG=1; //手机有网络 判断为有信号
				} else if (wifiNetInfo.isConnected()) {
					// wifi网络连接
				//	Toast.makeText(mContext, "wifi网络连接", 0).show();
					Wifi_TAG=1;
				} else {
					// 无网络连接
					Toast.makeText(mContext, "无网络连接", 0).show();
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

	//状态栏 状态控制----------------------------------------------
	
	// 更改信号状态
	protected void changeSignal(){};

	// 更改运营商状态 移动 联通 电信
	protected  void changeCarrieroperator(){};

	// 更改手机网络状态 2g 3g 4g
	protected  void changeNetWork(){};

	// 更改wifi状态
	protected  void changeWifi(){};

	// 更改电池状态
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
