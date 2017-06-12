package com.tachyon5.spectrum.activity;

import com.tachyon5.spectrum.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SetActivity extends BaseActivity implements OnClickListener {


	private LinearLayout ll_back;
	private LinearLayout ll_check;
	private LinearLayout ll_net;
	private LinearLayout ll_date;
	private LinearLayout ll_sleeptime;
	private LinearLayout ll_bright;
	private LinearLayout ll_help;
	private LinearLayout ll_about;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_check = (LinearLayout) findViewById(R.id.ll_check);
		ll_net = (LinearLayout) findViewById(R.id.ll_net);
		ll_date = (LinearLayout) findViewById(R.id.ll_date);
		ll_sleeptime = (LinearLayout) findViewById(R.id.ll_sleeptime);
		ll_bright = (LinearLayout) findViewById(R.id.ll_bright);
		ll_help = (LinearLayout) findViewById(R.id.ll_help);
		ll_about = (LinearLayout) findViewById(R.id.ll_about);

		ll_back.setOnClickListener(this);
		ll_check.setOnClickListener(this);
		ll_net.setOnClickListener(this);
		ll_date.setOnClickListener(this);
		ll_sleeptime.setOnClickListener(this);
		ll_bright.setOnClickListener(this);
		ll_help.setOnClickListener(this);
		ll_about.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_back:
			finish();
			break;
		case R.id.ll_check:
			Toast.makeText(mContext, "设备校准", 0).show();
			break;
		case R.id.ll_net:
			Intent intent_network = new Intent(mContext, NetworkSetActivity.class);
			startActivity(intent_network);
			// Toast.makeText(mContext, "网络设置", 0).show();
			break;
		case R.id.ll_date:
			Intent intent_datetime = new Intent(mContext, DateTimeActivity.class);
			startActivity(intent_datetime);
			break;
		case R.id.ll_sleeptime:
			Intent intent_dormanttime = new Intent(mContext, DormantTimeActivity.class);
			startActivity(intent_dormanttime);
			break;
		case R.id.ll_bright:
			Intent intent_bright = new Intent(mContext, BrightActivity.class);
			startActivity(intent_bright);
			break;
		case R.id.ll_help:
			Intent intent_help = new Intent(mContext,HelpActivity.class);
			startActivity(intent_help);
			break;
		case R.id.ll_about:
			Intent intent_about = new Intent(mContext,AboutActivity.class);
			startActivity(intent_about);
			break;

		}
	}

	// 重写 baseActivity 的方法 改变标题栏状态---------------------------
	@Override
	protected void changeSignal() {
		// TODO Auto-generated method stub

	}

}
