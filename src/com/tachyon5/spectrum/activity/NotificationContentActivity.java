package com.tachyon5.spectrum.activity;

import com.tachyon5.spectrum.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotificationContentActivity extends BaseActivity implements OnClickListener {
	private LinearLayout ll_back;

	private TextView tv_notificationtitle;
	private TextView tv_notificationtime;
	private TextView tv_notificationcontent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notificationcontent);
		initView();

		Intent inten = getIntent();
		tv_notificationtitle.setText(inten.getStringExtra("notificationTitle"));
		tv_notificationtime.setText(inten.getStringExtra("notificationTime"));
		tv_notificationcontent.setText("        " + inten.getStringExtra("notificationContent"));

	}

	private void initView() {
		// TODO Auto-generated method stub
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(this);
		tv_notificationcontent = (TextView) findViewById(R.id.tv_notificationcontent);
		tv_notificationtitle = (TextView) findViewById(R.id.tv_notificationtitle);
		tv_notificationtime = (TextView) findViewById(R.id.tv_notificationtime);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	}

}
