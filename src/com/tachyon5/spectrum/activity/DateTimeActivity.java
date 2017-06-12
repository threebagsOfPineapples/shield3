package com.tachyon5.spectrum.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.utils.CommonUtils;
import com.tachyon5.spectrum.utils.DateTimePickDialogUtil;
import com.tachyon5.spectrum.utils.DateTimePickDialogUtil.OnTimeSetListener;

import android.os.Bundle;
import android.provider.Settings.SettingNotFoundException;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class DateTimeActivity extends BaseActivity {

	private ImageView iv_back;

	private ToggleButton tb_switch1;
	private ToggleButton tb_switch2;

	private TextView tv_Date;
	private TextView tv_Time;
	private EditText et_Date;
	private EditText et_Time;
	private LinearLayout ll_changedate;
	private LinearLayout ll_changetime;

	private String str_Date; // 初始化开始时间

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datetime);

		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		str_Date = myFmt.format(new Date());

		initView();
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	setTime(7,50);
			}
		});

		tb_switch1 = (ToggleButton) findViewById(R.id.tb_switch1);
		tb_switch2 = (ToggleButton) findViewById(R.id.tb_switch2);

		tb_switch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					android.provider.Settings.System.putString(mContext.getContentResolver(),
			                android.provider.Settings.System.TIME_12_24, "24");
				} else {
					android.provider.Settings.System.putString(mContext.getContentResolver(),
			                android.provider.Settings.System.TIME_12_24, "12");
				}
			}
		});

		tb_switch2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					setTimeBlank();
					CommonUtils.setAutoTimeZone(mContext,1);
					CommonUtils.setAutoDateTime(mContext,1);
					
				} else {
					setTimeShow();
					CommonUtils.setAutoTimeZone(mContext,0);
					CommonUtils.setAutoDateTime(mContext,0);
				}
			}
		});

		tv_Date = (TextView) findViewById(R.id.tv_Date);
		tv_Time = (TextView) findViewById(R.id.tv_Time);
		et_Date = (EditText) findViewById(R.id.et_Date);
		et_Time = (EditText) findViewById(R.id.et_Time);
		et_Date.setText(str_Date.split(" ")[0]);
		et_Time.setText(str_Date.split(" ")[1]);

		ll_changedate = (LinearLayout) findViewById(R.id.ll_changedate);
		ll_changetime = (LinearLayout) findViewById(R.id.ll_changetime);

		ll_changedate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(DateTimeActivity.this, str_Date,
						true);
				
	             dateTimePicKDialog.setOnTimeSetListener(new OnTimeSetListener() {
					
					@Override
					public void onTiemSet(String str) {
						// TODO Auto-generated method stub
						Log.i(logTAG, "myTime"+str);
					}
				});
				dateTimePicKDialog.dateTimePicKDialog(et_Date);

			}
		});

		ll_changetime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(DateTimeActivity.this, str_Date,
						false);
				dateTimePicKDialog.setOnTimeSetListener(new OnTimeSetListener() {
					
					@Override
					public void onTiemSet(String str) {
						// TODO Auto-generated method stub
						Log.i(logTAG, "myTime"+str);
					}
				});
				dateTimePicKDialog.dateTimePicKDialog(et_Time);
			}
		});

		initStatus();
	}

	private void initStatus() {
		// TODO Auto-generated method stub
		boolean is24Hour =  DateFormat.is24HourFormat(mContext);
		if (is24Hour) {  
			tb_switch1.setChecked(true);
		} else {
			tb_switch1.setChecked(false);
		}

		if (CommonUtils.isDateTimeAuto(mContext)) {
			tb_switch2.setChecked(true);
			setTimeBlank();
		} else {
			tb_switch2.setChecked(false);
			setTimeShow();
		}

	}
	
	//显示 时间日期的设置 
	private void setTimeShow(){
		tv_Date.setTextColor(0xffffffff);
		tv_Time.setTextColor(0xffffffff);
		et_Date.setTextColor(0x88ffffff);
		et_Time.setTextColor(0x88ffffff);
		ll_changedate.setClickable(true);
		ll_changetime.setClickable(true);
	}
	//不 显示 时间日期的设置 
	private void setTimeBlank(){
		tv_Date.setTextColor(0x66ffffff);
		tv_Time.setTextColor(0x66ffffff);
		et_Date.setTextColor(0x33ffffff);
		et_Time.setTextColor(0x33ffffff);
		ll_changedate.setClickable(false);
		ll_changetime.setClickable(false);	
	}
	

	

}
