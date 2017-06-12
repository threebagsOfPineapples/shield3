package com.tachyon5.spectrum.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
 * SharePreferences �����࣬ 
 */
public class SharedPreferencesUtils {

	private SharedPreferences sp = null;
	//sp ���ݼ�ֵ�Ե�  key 
	
	public static final String SP_GUIDEDHOW="guideshow";
	public static final String SP_STANDBYTIME="standbytime";

	/*
	 * ���췽����ʼ�� �õ� sp ����
	 *  context ������ 
	 *  PhotonicShieldΪ sp�� ����
	 */
	public SharedPreferencesUtils(Context context) {
		sp = context.getSharedPreferences("MySpectrum", Context.MODE_PRIVATE);
	}

	/*
	 * ����String ���� key �� value ��Ҫ����� String ����
	 */
	public void spSaveData(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	/*
	 * ����int ���� key ��
	 *  value ��Ҫ����� int ����
	 */
	public void spSaveData(String key, int value) {
		sp.edit().putInt(key, value).commit();
	}

	/*
	 *  ����boolean ����
	 *  key ��
	 *  value ��Ҫ����� boolean ����
	 */
	public void spSaveData(String key, boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	/*
	 * ��sp�� ȡֵ String 
	 * ȡ���� Ĭ��Ϊ "";
	 */
	public String spGetString(String key) {
		return sp.getString(key, "");
	}

	/*
	 * ��sp�� ȡֵ Int 
	 * ȡ���� Ĭ��Ϊ =-1;
	 */
	public int spGetInt(String key) {
		return sp.getInt(key, -1);
	}

	/*
	 * ��sp�� ȡֵ boolean 
	 * ȡ���� Ĭ��Ϊ false;
	 */
	public boolean spGetBoolean(String key) {
		return sp.getBoolean(key, false);
	}
	
	/*
	 * ��sp�� ȡֵ boolean 
	 * ȡ���� Ĭ��Ϊ true;
	 */
	public boolean spGetBooleanTwo(String key) {
		return sp.getBoolean(key, true);
	}

	/*
	 * ɾ�� sp�ж�Ӧ key������ key
	 */
	public void spRemove(String key) {
		sp.edit().remove(key).commit();
	}

	/*
	 * ɾ�� sp�ж�Ӧ ��������
	 */
	public void spClear() {
		sp.edit().clear().commit();
	}

}
