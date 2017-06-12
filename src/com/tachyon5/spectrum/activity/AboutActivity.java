package com.tachyon5.spectrum.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.utils.SystemInformationUtils;

public class AboutActivity extends BaseActivity{
	private LinearLayout ll_back;
	
	private View parentView;
	private LinearLayout ll_tel;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private TextView tv_verson;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_about, null);
		setContentView(parentView);
		initView();
		popwindow();
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_verson = (TextView) findViewById(R.id.tv_verson);
		tv_verson.setText(SystemInformationUtils.getAPPVerson(this));
		ll_back=(LinearLayout)findViewById(R.id.ll_back);
		ll_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	@SuppressLint("InflateParams")
	private void popwindow() {
		pop = new PopupWindow(this);

		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);

		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_time);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_call);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		bt1.setEnabled(false);
		bt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "4008650825"));

				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();

			}
		});

		bt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pop.dismiss();

			}
		});

	}
}
