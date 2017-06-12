package com.tachyon5.spectrum.utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
 * SharePreferences 保存类， 
 */
public class SharedPreferencesUtils {

	private SharedPreferences sp = null;
	//sp 数据键值对的  key 
	
	public static final String SP_GUIDEDHOW="guideshow";
	public static final String SP_STANDBYTIME="standbytime";

	/*
	 * 构造方法初始化 得到 sp 对象
	 *  context 上下文 
	 *  PhotonicShield为 sp的 名称
	 */
	public SharedPreferencesUtils(Context context) {
		sp = context.getSharedPreferences("MySpectrum", Context.MODE_PRIVATE);
	}

	/*
	 * 保存String 数据 key 、 value 需要保存的 String 数据
	 */
	public void spSaveData(String key, String value) {
		sp.edit().putString(key, value).commit();
	}

	/*
	 * 保存int 数据 key 、
	 *  value 需要保存的 int 数据
	 */
	public void spSaveData(String key, int value) {
		sp.edit().putInt(key, value).commit();
	}

	/*
	 *  保存boolean 数据
	 *  key 、
	 *  value 需要保存的 boolean 数据
	 */
	public void spSaveData(String key, boolean value) {
		sp.edit().putBoolean(key, value).commit();
	}

	/*
	 * 从sp中 取值 String 
	 * 取不到 默认为 "";
	 */
	public String spGetString(String key) {
		return sp.getString(key, "");
	}

	/*
	 * 从sp中 取值 Int 
	 * 取不到 默认为 =-1;
	 */
	public int spGetInt(String key) {
		return sp.getInt(key, -1);
	}

	/*
	 * 从sp中 取值 boolean 
	 * 取不到 默认为 false;
	 */
	public boolean spGetBoolean(String key) {
		return sp.getBoolean(key, false);
	}
	
	/*
	 * 从sp中 取值 boolean 
	 * 取不到 默认为 true;
	 */
	public boolean spGetBooleanTwo(String key) {
		return sp.getBoolean(key, true);
	}

	/*
	 * 删除 sp中对应 key的数据 key
	 */
	public void spRemove(String key) {
		sp.edit().remove(key).commit();
	}

	/*
	 * 删除 sp中对应 所有数据
	 */
	public void spClear() {
		sp.edit().clear().commit();
	}

}
