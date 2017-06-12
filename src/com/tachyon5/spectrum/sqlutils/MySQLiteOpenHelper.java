package com.tachyon5.spectrum.sqlutils;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class MySQLiteOpenHelper extends SQLiteOpenHelper{
	
	private static final String SQL_NAME="Spectrum";
	
	public MySQLiteOpenHelper(Context context) {
		super(context, SQL_NAME, null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table mynotification("
				+ "id integer primary key autoincrement,"
				+ "number varchar(50),"
				+ "title varchar(50),"
				+ "time varchar(50),"
				+ "dimension varchar(1000),"
				+ "tab varchar(20))");
		
		Log.e("create", "数据库创建成功");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}
