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
	 *  当天日期为周几
	 * @param dt  日期
	 * @return  日期 和是周几 
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		SimpleDateFormat myFmt = new SimpleDateFormat("MM月dd日");
		String str = myFmt.format(dt);
		return str + "  " + weekDays[w];
	}
	
	/**
	 *  十六进制的byte[] 转换为字符串 0x20 转为 "20"
	 * @param bytes byte[] 数组
	 * @return 转换后的字符串
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
	 *  将byte 转换成字符串  十六进制
	 * @param sbyte byte数组
	 * @param len  byte[]数组的长度
	 * @param insterStr  字符串 间隔符
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
	 * int[] 转换成 String
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
	 *   获取手机网络状态
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
	 *  设置手机网络状态
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
     *  判断系统的时区是否是自动获取的
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
	 * 判断系统的时间是否自动获取的
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
	 * 设置系统的时区是否自动获取 
	 * @param mContext
	 * @param checked 1-是 0-否
	 */
	public static void setAutoTimeZone(Context mContext,int checked){
	  android.provider.Settings.Global.putInt(mContext.getContentResolver(),
	          android.provider.Settings.Global.AUTO_TIME_ZONE, checked);
	}
	
	/**
	 * 设置系统的时间是否需要自动获取  
	 * @param mContext
	 * @param checked 1-是 0-否
	 */
	public static void setAutoDateTime(Context mContext,int checked){
	  android.provider.Settings.Global.putInt(mContext.getContentResolver(),
	          android.provider.Settings.Global.AUTO_TIME, checked);
	}
	
	// 发送广播
	public static void broadcastUpdate(Context mContext,String action) {
		Intent intent = new Intent(action);
		mContext.sendBroadcast(intent);
	}
    
}
