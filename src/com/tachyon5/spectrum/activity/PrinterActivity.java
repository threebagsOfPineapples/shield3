package com.tachyon5.spectrum.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.tachyon5.spectrum.R;

public class PrinterActivity extends BaseActivity{
	
	private ImageView iv_back;
	private ImageView iv_printer;
	private TextView tv_printer;	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_printer);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_back=(ImageView)findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		iv_printer=(ImageView)findViewById(R.id.iv_printer);
		tv_printer=(TextView)findViewById(R.id.tv_printer);
		iv_printer.setImageResource(R.drawable.printer1);
		tv_printer.setText("正在打印...");
		handler.sendEmptyMessageDelayed(1, 5000);
	}
	
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				iv_printer.setImageResource(R.drawable.printer2);
				tv_printer.setText("打印完成");
				handler.sendEmptyMessageDelayed(2, 1500);
				break;
			case 2:
				finish();
				break;
				
			default:
				
				break;
			}
		}
	};
	
}
