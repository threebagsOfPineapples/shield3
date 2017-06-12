package com.tachyon5.spectrum.sqlutils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.tachyon5.spectrum.bean.NotificationBean;
import com.tachyon5.spectrum.utils.Contents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManage {
	private MySQLiteOpenHelper helper;
	private SQLiteDatabase db;
	
	//֪ͨ   �� 
	private static final String TABLE_NAME="mynotification";

	public DBManage(Context context) {

		helper = new MySQLiteOpenHelper(context);		
	}
	/*
	 * �����ݱ���  �������� ��
	 * number ֪ͨ ���
	 * title ֪ͨ����
	 * time ʱ��
	 * dimension ���� 
	 * tab �Ѷ��� δ�����   
	 * return  void 
	 */
	public void addNotification(String number,String title,String time,String dimension,String tab) {		
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("number", number);
		values.put("title", title);
		values.put("time", time);
		values.put("dimension", dimension);
		values.put("tab", tab);
		db.insert(TABLE_NAME, null, values);
		db.close();
	}
	/*
	 *  ��ѯ ����֪ͨ ��Ϣ
	 *  return :  list(֪ͨ)
	 */
	public List<NotificationBean> queryNotification(){
		db = helper.getWritableDatabase();
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		List<NotificationBean>  listbean=new ArrayList<>();
		while (cursor.moveToNext()) {
			NotificationBean notificationBean=new NotificationBean();
			notificationBean.setNumber(cursor.getString(cursor.getColumnIndex("number")));
			notificationBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			notificationBean.setTime(cursor.getString(cursor.getColumnIndex("time")));
			notificationBean.setDimension(cursor.getString(cursor.getColumnIndex("dimension")));
			notificationBean.setTab(cursor.getString(cursor.getColumnIndex("tab")));
			listbean.add(notificationBean);					
		}		
		cursor.close();
		db.close();
		return listbean;
	}
	/*
	 * �޸�֪ͨ ���    
	 * number ֪ͨ ���  
	 * tab  Ҫ�޸ĵ� ���    
	 * return : void
	 */
	public void updateNotification(String number, String tab){
		db = helper.getWritableDatabase();		
		ContentValues valus=new ContentValues();
		valus.put("tab", tab);
		db.update(TABLE_NAME, valus, "number= ?", new String[]{number});
		db.close();
		
	}
	/*
	 * ��ѯ ֪ͨ״̬ �Ƿ���δ�� ��Ϣ 
	 * return�� true  ��δ����Ϣ   false  �� δ����Ϣ
	 */
	public boolean queryNotificationState(){
		db = helper.getWritableDatabase();
		boolean bool = false;
		Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String tab=cursor.getString(cursor.getColumnIndex("tab"));	
			if(tab.equals(Contents.NOTIFICATIONTAB_NOREAD)){
				bool=true;
			}				
		}		
		cursor.close();
		db.close();		
		return bool ;
	}
	
	
	
}
