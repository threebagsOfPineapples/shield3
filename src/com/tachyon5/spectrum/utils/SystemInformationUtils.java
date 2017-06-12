package com.tachyon5.spectrum.utils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
/**
 * 
 * @author kang
 *
 */
public class SystemInformationUtils {
	// 获取当前版本号
	public static String getAPPVerson(Context mcontext) {
		String versionName = "";
		try {
			// 获取软件版本号，
			versionName = mcontext.getPackageManager().getPackageInfo(mcontext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
	/**
	 * 获取手机 IMEi号
	 * @param mcontext
	 * @return
	 */ 
	public static String getIMEI(Context mcontext) {
		TelephonyManager tm = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * 判断wifi是否连接
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断是否有网络
	 * @param context
	 * @return
	 */
	public static boolean checkNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						NetworkInfo netWorkInfo = info[i];
						if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
							return true;
						} else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	/**
	 * 获取当前网络连接类型
	 * @param context
	 * @return 0 无网络 1未知网络连接 2 2g 3 3g 4 4g 5 wifi连接
	 */
	public static int getNetworkState(Context context) {
		// 获取系统的网络服务
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		// 如果当前没有网络
		if (null == connManager)
			return 0;

		// 获取当前网络类型，如果为空，返回无网络
		NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
		if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
			return 0;
		}

		// 判断是不是连接的是不是wifi
		NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (null != wifiInfo) {
			NetworkInfo.State state = wifiInfo.getState();
			if (null != state)
				if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
					return 5;
				}
		}

		// 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
		NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (null != networkInfo) {
			NetworkInfo.State state = networkInfo.getState();
			String strSubTypeName = networkInfo.getSubtypeName();
			if (null != state)
				if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
					switch (activeNetInfo.getSubtype()) {
					// 如果是2g类型
					case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
					case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
					case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
					case TelephonyManager.NETWORK_TYPE_1xRTT:
					case TelephonyManager.NETWORK_TYPE_IDEN:
						return 2;
					// 如果是3g类型
					case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
					case TelephonyManager.NETWORK_TYPE_UMTS:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_HSDPA:
					case TelephonyManager.NETWORK_TYPE_HSUPA:
					case TelephonyManager.NETWORK_TYPE_HSPA:
					case TelephonyManager.NETWORK_TYPE_EVDO_B:
					case TelephonyManager.NETWORK_TYPE_EHRPD:
					case TelephonyManager.NETWORK_TYPE_HSPAP:
						return 3;
					// 如果是4g类型
					case TelephonyManager.NETWORK_TYPE_LTE:
						return 4;
					default:
						// 中国移动 联通 电信 三种3G制式
						if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA")
								|| strSubTypeName.equalsIgnoreCase("CDMA2000")) {
							return 3;
						} else {
							return 1;
						}
					}
				}
		}
		return 0;
	}

	/**
	 * 获取当前网络运营商
	 * @param context
	 * @return 0:无运营商或未知 1：中国移动 2：中国联通 3：中国电信
	 */
	public static int getCarrieroperator(Context context) {
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = telManager.getSimOperator();
		Log.i("运营商", operator);

		if (operator != null) {
			if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
				// 中国移动
				return 1;
			} else if (operator.equals("46001")) {
				// 中国联通
				return 2;
			} else if (operator.equals("46003")) {
				// 中国电信
				return 3;
			}else{
				return 0;	
			}
		} else {
			return 0;
		}
		
	}

	
	 /** 
	  * 判断是否包含SIM卡  
	  * @return 状态 
	  */
    public static boolean hasSimCard(Context mContext) {
        TelephonyManager telMgr = (TelephonyManager)
        		mContext.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // 没有SIM卡
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        return result;
    }
	
}
