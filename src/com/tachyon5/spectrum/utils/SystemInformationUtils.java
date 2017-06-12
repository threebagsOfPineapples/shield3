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
	// ��ȡ��ǰ�汾��
	public static String getAPPVerson(Context mcontext) {
		String versionName = "";
		try {
			// ��ȡ����汾�ţ�
			versionName = mcontext.getPackageManager().getPackageInfo(mcontext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
	/**
	 * ��ȡ�ֻ� IMEi��
	 * @param mcontext
	 * @return
	 */ 
	public static String getIMEI(Context mcontext) {
		TelephonyManager tm = (TelephonyManager) mcontext.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	/**
	 * �ж�wifi�Ƿ�����
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
	 * �ж��Ƿ�������
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
	 * ��ȡ��ǰ������������
	 * @param context
	 * @return 0 ������ 1δ֪�������� 2 2g 3 3g 4 4g 5 wifi����
	 */
	public static int getNetworkState(Context context) {
		// ��ȡϵͳ���������
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		// �����ǰû������
		if (null == connManager)
			return 0;

		// ��ȡ��ǰ�������ͣ����Ϊ�գ�����������
		NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
		if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
			return 0;
		}

		// �ж��ǲ������ӵ��ǲ���wifi
		NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (null != wifiInfo) {
			NetworkInfo.State state = wifiInfo.getState();
			if (null != state)
				if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
					return 5;
				}
		}

		// �������wifi�����жϵ�ǰ���ӵ�����Ӫ�̵���������2g��3g��4g��
		NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (null != networkInfo) {
			NetworkInfo.State state = networkInfo.getState();
			String strSubTypeName = networkInfo.getSubtypeName();
			if (null != state)
				if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
					switch (activeNetInfo.getSubtype()) {
					// �����2g����
					case TelephonyManager.NETWORK_TYPE_GPRS: // ��ͨ2g
					case TelephonyManager.NETWORK_TYPE_CDMA: // ����2g
					case TelephonyManager.NETWORK_TYPE_EDGE: // �ƶ�2g
					case TelephonyManager.NETWORK_TYPE_1xRTT:
					case TelephonyManager.NETWORK_TYPE_IDEN:
						return 2;
					// �����3g����
					case TelephonyManager.NETWORK_TYPE_EVDO_A: // ����3g
					case TelephonyManager.NETWORK_TYPE_UMTS:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_HSDPA:
					case TelephonyManager.NETWORK_TYPE_HSUPA:
					case TelephonyManager.NETWORK_TYPE_HSPA:
					case TelephonyManager.NETWORK_TYPE_EVDO_B:
					case TelephonyManager.NETWORK_TYPE_EHRPD:
					case TelephonyManager.NETWORK_TYPE_HSPAP:
						return 3;
					// �����4g����
					case TelephonyManager.NETWORK_TYPE_LTE:
						return 4;
					default:
						// �й��ƶ� ��ͨ ���� ����3G��ʽ
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
	 * ��ȡ��ǰ������Ӫ��
	 * @param context
	 * @return 0:����Ӫ�̻�δ֪ 1���й��ƶ� 2���й���ͨ 3���й�����
	 */
	public static int getCarrieroperator(Context context) {
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = telManager.getSimOperator();
		Log.i("��Ӫ��", operator);

		if (operator != null) {
			if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
				// �й��ƶ�
				return 1;
			} else if (operator.equals("46001")) {
				// �й���ͨ
				return 2;
			} else if (operator.equals("46003")) {
				// �й�����
				return 3;
			}else{
				return 0;	
			}
		} else {
			return 0;
		}
		
	}

	
	 /** 
	  * �ж��Ƿ����SIM��  
	  * @return ״̬ 
	  */
    public static boolean hasSimCard(Context mContext) {
        TelephonyManager telMgr = (TelephonyManager)
        		mContext.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                result = false; // û��SIM��
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false;
                break;
        }
        return result;
    }
	
}
