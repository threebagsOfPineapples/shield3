package com.tachyon5.spectrum.common;

import java.io.File;

import okhttp3.Call;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.tachyon5.spectrum.activity.HomeActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

public class AppUpdate {
	ProgressDialog progressDialog;
	Handler handler;
	Context context;
	/** ��ʱʱ�� */
	private static final int TIMEOUT = 100 * 1000;
	/** ���ص����� */
	private static String down_url ;
	/** ���سɹ� */
	private static final int DOWN_OK = 1;
	/** ����ʧ�� */
	private static final int DOWN_ERROR = 0;
	/** ��ǰ�ļ��Ĵ�С */
	private static long size;
				
	public AppUpdate(Context context) {
		this.context = context;
	}
	
	public boolean isNeedUpdate() {
		String new_version = HomeActivity.updateinfo.getVersionCode(); // ���°汾�İ汾��
	//	System.out.println("new_version:"+"��ǰ�汾��Ϊ��"+new_version);
		// ��ȡ��ǰ�汾��
		int old_version = 0;
		if(new_version==null){
			return false;
		}else{
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			old_version = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if (Integer.valueOf(new_version) >=old_version) {
			return true;
		} else {
			return false;
		}
		}
	}
	public void downLoadFile(final String url, final ProgressDialog pDialog, Handler h) {
		
		System.out.println("url--"+url);
		progressDialog = pDialog;
		handler = h;
		new Thread() {
			public void run() {
				
		OkHttpUtils
				.get()	
				.url(url)
				.build()   //Environment.getExternalStorageDirectory().getAbsolutePath()
				.execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(),"SpectrumScan.apk") 
				{	
					@Override
					public void onResponse(File file) {
						 Log.e("����", "onResponse :" + file.getAbsolutePath());
						 down();
					}					
					@Override
					public void onError(Call arg0, Exception e) {
						 Log.e("����Error", "onError :" + e.getMessage());						
					}
					
					@Override
					public void inProgress(float progress, long arg1) {
						progressDialog.setProgress((int) (100*progress));							
					}
				});
	
			}		
		}.start();
	}
	void down() {
		handler.post(new Runnable() {
			public void run() {
				progressDialog.cancel();
				update();
			} 
		});
	}
	void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "SpectrumScan.apk")),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
		
		android.os.Process.killProcess(android.os.Process.myPid());
		
	
		
	}
}
