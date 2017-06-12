package com.tachyon5.spectrum.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tachyon5.spectrum.R;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewSetUtils {
	
	//首页 显示时间设置
	public static void setTimeView(Context mContext,TextView tv) {
		// TODO Auto-generated method stub
		DateFormat format;
		if(android.text.format.DateFormat.is24HourFormat(mContext)){
			format = new SimpleDateFormat("HH:mm:ss");
		}else{
			format = new SimpleDateFormat("hh:mm:ss");
		}
		String time = format.format(new Date());
		tv.setText(time.substring(0, 5));
	}
	
	//标题栏 电量图标设置
	public static void setElectricityView(int TAG,ImageView iv){
		switch (TAG) {
		case 0:
			iv.setImageResource(R.drawable.status_icon_electricity0);
			break;
		case 1:
			iv.setImageResource(R.drawable.status_icon_electricity1);
			break;
		case 2:
			iv.setImageResource(R.drawable.status_icon_electricity2);
			break;
		case 3:
			iv.setImageResource(R.drawable.status_icon_electricity3);
			break;
		case 4:
			iv.setImageResource(R.drawable.status_icon_electricity4);
			break;
		default:
			break;

		}
	}
	
	//标题栏 wifi图标设置
	public static void setWifiView(int TAG,ImageView iv_wifi,ImageView iv_network){
		
		if (TAG == 1) {
			iv_wifi.setVisibility(View.VISIBLE);
			iv_network.setVisibility(View.GONE);
		} else {
			iv_wifi.setVisibility(View.GONE);
		}
	}
	
	//标题栏 网络图标设置
	public static void setNetworkView(int TAG,ImageView iv_network){
		iv_network.setVisibility(View.VISIBLE);
		switch (TAG) {
		case 0:
			iv_network.setVisibility(View.GONE);
			break;
		case 1:
			iv_network.setVisibility(View.GONE);
			break;
		case 2:
			iv_network.setVisibility(View.VISIBLE);
			iv_network.setImageResource(R.drawable.status_icon_2g);
			break;
		case 3:
			iv_network.setVisibility(View.VISIBLE);
			iv_network.setImageResource(R.drawable.status_icon_3g);
			break;
		case 4:
			iv_network.setVisibility(View.VISIBLE);
			iv_network.setImageResource(R.drawable.status_icon_4g);
			break;
		default:
			break;
		}
		
	}
	
	//标题栏 运营商设置 
	public static void setCarrieroperatorView(int TAG,TextView tv){
		switch (TAG) {
		case 1:
			tv.setText("中国移动");
			break;
		case 2:
			tv.setText("中国联通");
			break;
		case 3:
			tv.setText("中国电信");
			break;
		default:
			tv.setText("无服务");
			break;

		}
	}
	
	//标题栏 有无信号设置 
	public static void setSignalView(int TAG,ImageView iv){
		if (TAG == 1) {
			iv.setVisibility(View.VISIBLE);
		} else {
			iv.setVisibility(View.GONE);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
