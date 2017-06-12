package com.tachyon5.spectrum.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings.SettingNotFoundException;
import android.widget.Toast;
/**
 * 
 * @author kang
 *
 */
public class CommonUtils {
	/**
	 *  ��������Ϊ�ܼ�
	 * @param dt  ����
	 * @return  ���� �����ܼ� 
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "������", "����һ", "���ڶ�", "������", "������", "������", "������" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		SimpleDateFormat myFmt = new SimpleDateFormat("MM��dd��");
		String str = myFmt.format(dt);
		return str + "  " + weekDays[w];
	}
	
	/**
	 *  ʮ�����Ƶ�byte[] ת��Ϊ�ַ��� 0x20 תΪ "20"
	 * @param bytes byte[] ����
	 * @return ת������ַ���
	 */
	public static String byte2HexString(byte[] bytes) {
		String hex = "";
		if (bytes != null) {
			for (Byte b : bytes) {
				hex += String.format("%02X", b.intValue() & 0xFF);
			}
		}
		return hex.substring(0, 2);
	}
	
	/**
	 *  ��byte ת�����ַ���  ʮ������
	 * @param sbyte byte����
	 * @param len  byte[]����ĳ���
	 * @param insterStr  �ַ��� �����
	 * @return
	 */
	public static String byteToHex(byte[] sbyte,int len,String insterStr){
		String str = "";
		for(int i=0;i<len;i++){
			str += (sbyte[i]&0xff)<16?"0"+Integer.toHexString(sbyte[i]&0xff).toUpperCase() + insterStr:Integer.toHexString(sbyte[i]&0xff).toUpperCase() + insterStr;
		}
		return str;
		
	}
	
	/**
	 * int[] ת���� String
	 * @param arr
	 * @return
	 */
	public static String getStringBySample(int[] arr) {
		String str = "";
		for (int i = 0; i < arr.length; i++) {
			str = str + arr[i] + " ";
		}
		return str;
	}

	/**
	 *   ��ȡ�ֻ�����״̬
	 * @param context
	 * @return
	 */
	public static boolean getMobileDataState(Context context){

        ConnectivityManager connMr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Class[] argsclass = null;
        try {
            Method method = connMr.getClass().getDeclaredMethod("getMobileDataEnabled",argsclass);
            return (boolean)method.invoke(connMr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
	
	/**
	 *  �����ֻ�����״̬
	 * @param context
	 * @param enable
	 */
    public static void setMobileDataState(Context context, boolean enable){
        Class[] argsClass = new Class[1];
        argsClass[0] = boolean.class;
        ConnectivityManager connMr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Method method = connMr.getClass().getMethod("setMobileDataEnabled",argsClass);

            method.invoke(connMr,enable);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context,"set failure + e = " + e.getCause(),Toast.LENGTH_LONG).show();
        }
    }
    
    /**
     *  �ж�ϵͳ��ʱ���Ƿ����Զ���ȡ��
     * @ram mContext mContext
     * @return ture
     */
	public static boolean isTimeZoneAuto(Context mContext){
	  try {
	      return  android.provider.Settings.Global.getInt(mContext.getContentResolver(),
	              android.provider.Settings.Global.AUTO_TIME_ZONE) > 0;
	  } catch (SettingNotFoundException e) {
	      e.printStackTrace();
	      return false;
	  }
	}
	
	/**
	 * �ж�ϵͳ��ʱ���Ƿ��Զ���ȡ��
	 * @param mContext
	 * @return
	 */
	public static boolean isDateTimeAuto(Context mContext){
		  try {
		      return android.provider.Settings.Global.getInt(mContext.getContentResolver(),
		              android.provider.Settings.Global.AUTO_TIME) > 0;
		  } catch (SettingNotFoundException e) {
		      e.printStackTrace();
		      return false;
		  }
		}
	
	/**
	 * ����ϵͳ��ʱ���Ƿ��Զ���ȡ 
	 * @param mContext
	 * @param checked 1-�� 0-��
	 */
	public static void setAutoTimeZone(Context mContext,int checked){
	  android.provider.Settings.Global.putInt(mContext.getContentResolver(),
	          android.provider.Settings.Global.AUTO_TIME_ZONE, checked);
	}
	
	/**
	 * ����ϵͳ��ʱ���Ƿ���Ҫ�Զ���ȡ  
	 * @param mContext
	 * @param checked 1-�� 0-��
	 */
	public static void setAutoDateTime(Context mContext,int checked){
	  android.provider.Settings.Global.putInt(mContext.getContentResolver(),
	          android.provider.Settings.Global.AUTO_TIME, checked);
	}
	
	// ���͹㲥
	public static void broadcastUpdate(Context mContext,String action) {
		Intent intent = new Intent(action);
		mContext.sendBroadcast(intent);
	}
    
}
