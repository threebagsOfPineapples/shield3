package com.tachyon5.spectrum.activity;

import com.tachyon5.spectrum.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class LoginActivity extends Activity{

	private static final int JUMP_HOMEACTIVITY = 1;
	private Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		mContext=this;
		
		handler.sendEmptyMessageDelayed(JUMP_HOMEACTIVITY, 2000);
	}
	
	
	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case JUMP_HOMEACTIVITY:
				Intent intent = new Intent(mContext, HomeActivity.class);
				startActivity(intent);
				finish();
				break;
			}
		}	
	};
	
}
