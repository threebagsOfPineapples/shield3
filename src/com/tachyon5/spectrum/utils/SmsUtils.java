package com.tachyon5.spectrum.utils;

import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.text.format.DateFormat;
/**
 * @author kang
 */
public class SmsUtils {
	
	private static  Uri uri = Uri.parse("content://sms/conversations");
	    
	private static String[] projection = new String[]{
				"sms.body AS snippet",
				"sms.thread_id AS _id",
				"groups.msg_count AS msg_count",
				"address as address",
				"date as date"
		};
	/*
	 * ����ÿһ�е��±�ֵ�������ڴ�����ʹ��
	 */
	private static int INDEX_BODY = 0;
	private static int INDEX_THREAD_ID = 1;
	private static int INDEX_MSG_COUNT = 2;
	private static int INDEX_ADDRESS = 3;
	private static int INDEX_DATE = 4;
	
	
	//������ 
	public static void sendSms(){
		SmsManager	smsManaget=SmsManager.getDefault();
		smsManaget.sendTextMessage("10086", null, "101", null, null);	
	}
	
	//��ѯ����
	public static void querySms(Context context){
		Cursor cursor=context.getContentResolver().query(uri, projection, null, null, null);
		while (cursor.moveToNext()) {
			long when = cursor.getLong(INDEX_DATE);
		    String	dateStr = DateFormat.getDateFormat(context).format(when);//���� ����  /ʱ��
			String str=cursor.getString(INDEX_ADDRESS)+"/"+cursor.getString(INDEX_BODY)+"/"+dateStr;
		 }
	   }
	

}
