package com.tachyon5.spectrum.activity;

import java.lang.reflect.Method;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.utils.CommonUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class NetworkSetActivity extends BaseActivity implements OnClickListener{

	
	private LinearLayout ll_back;
	private LinearLayout ll_net_wifi;
	private LinearLayout ll_net_phone;
	private ToggleButton tb_switch;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_networkset);
		initView();
		//tb_switch.setChecked(CommonUtils.getMobileDataState(mContext));
	}

	private void initView() {
		// TODO Auto-generated method stub
		ll_back=(LinearLayout)findViewById(R.id.ll_back);
		ll_net_wifi=(LinearLayout)findViewById(R.id.ll_net_wifi);
		ll_net_phone=(LinearLayout)findViewById(R.id.ll_net_phone);
		ll_back.setOnClickListener(this);
		ll_net_wifi.setOnClickListener(this);
		ll_net_phone.setOnClickListener(this);
		tb_switch = (ToggleButton) findViewById(R.id.tb_switch);
		tb_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			
				//CommonUtils.setMobileDataState(mContext, isChecked);	
						
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.ll_back:			
			finish();
			break;
		case R.id.ll_net_wifi:
			Intent intent_wifi=new Intent(mContext,WifiActivity.class);
			startActivity(intent_wifi);
			break;
		case R.id.ll_net_phone:
			/*Intent intent_phone=new Intent(mContext,PrinterActivity.class);
			startActivity(intent_phone);*/
			break;
		}
	}

	
	
}
