package com.tachyon5.spectrum.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;

/**
 * �ߵ¶�λ
 * 
 * @author Administrator
 * 
 */
public class LocationUtils implements AMapLocationListener {
	public AMapLocationClientOption mLocationOption = null;
	public AMapLocationClient mLocationClient = null;

	public  void getPosition(Context mContext) {
		// TODO Auto-generated method stub
		// �ߵµ�ͼ ��λ
		mLocationClient = new AMapLocationClient(mContext);
		mLocationClient.setLocationListener(this);
		mLocationOption = new AMapLocationClientOption();
		mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
		mLocationOption.setNeedAddress(true);
		mLocationOption.setOnceLocation(false);
		mLocationOption.setWifiActiveScan(true);
		mLocationOption.setMockEnable(false);
		mLocationOption.setInterval(2000);
		mLocationClient.setLocationOption(mLocationOption);
		mLocationClient.startLocation();
	}

	// ��λ��Ϣ�ص�
	public void onLocationChanged(AMapLocation amapLocation) {
		if (amapLocation != null) {
			if (amapLocation.getErrorCode() == 0) {
				amapLocation.getLocationType();
				amapLocation.getLatitude(); // γ��
				amapLocation.getLongitude(); // ����
				amapLocation.getAccuracy();
				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				Date date = new Date(amapLocation.getTime());
				df.format(date);
				amapLocation.getAddress();
				amapLocation.getCountry();
				Contents.Province = amapLocation.getProvince()
						+ amapLocation.getLatitude();// ʡ
				Contents.City = amapLocation.getCity()
						+ amapLocation.getLongitude(); // ��
				Contents.District = amapLocation.getDistrict();// ��
				amapLocation.getStreet();
				amapLocation.getStreetNum();
				amapLocation.getCityCode();
				amapLocation.getAdCode();

				mLocationClient.stopLocation();

			} else {
				Log.e("AmapError",
						"location Error, ErrCode:"
								+ amapLocation.getErrorCode() + ", errInfo:"
								+ amapLocation.getErrorInfo());
			}
		}
	}
}
