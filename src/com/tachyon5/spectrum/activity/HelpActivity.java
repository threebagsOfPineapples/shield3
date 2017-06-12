package com.tachyon5.spectrum.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.utils.SharedPreferencesUtils;

public class HelpActivity extends BaseActivity{
	private LinearLayout ll_back;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		ll_back=(LinearLayout)findViewById(R.id.ll_back);
		ll_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
