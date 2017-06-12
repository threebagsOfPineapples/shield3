package com.tachyon5.spectrum.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.utils.SharedPreferencesUtils;

public class DormantTimeActivity extends BaseActivity implements OnClickListener{
	
	private LinearLayout ll_back;
	
	private LinearLayout ll_standbytime1;
	private LinearLayout ll_standbytime2;
	private LinearLayout ll_standbytime3;
	private LinearLayout ll_standbytime4;
	private LinearLayout ll_standbytime5;
	private ImageView iv_standbytime1;
	private ImageView iv_standbytime2;
	private ImageView iv_standbytime3;
	private ImageView iv_standbytime4;
	private ImageView iv_standbytime5;
	
	public static final String STANDBYTIME1="20";
	public static final String STANDBYTIME2="30";
	public static final String STANDBYTIME3="45";
	public static final String STANDBYTIME4="1Сʱ";
	public static final String STANDBYTIME5="2Сʱ";
	
	private SharedPreferencesUtils sp;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dormanttime);
		sp = new SharedPreferencesUtils(this);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		ll_back=(LinearLayout)findViewById(R.id.ll_back);
		ll_standbytime1 = (LinearLayout) findViewById(R.id.ll_standbytime1);
		ll_standbytime2 = (LinearLayout) findViewById(R.id.ll_standbytime2);
		ll_standbytime3 = (LinearLayout) findViewById(R.id.ll_standbytime3);
		ll_standbytime4 = (LinearLayout) findViewById(R.id.ll_standbytime4);
		ll_standbytime5 = (LinearLayout) findViewById(R.id.ll_standbytime5);
		iv_standbytime1 = (ImageView) findViewById(R.id.iv_standbytime1);
		iv_standbytime2 = (ImageView) findViewById(R.id.iv_standbytime2);
		iv_standbytime3 = (ImageView) findViewById(R.id.iv_standbytime3);
		iv_standbytime4 = (ImageView) findViewById(R.id.iv_standbytime4);
		iv_standbytime5 = (ImageView) findViewById(R.id.iv_standbytime5);
		
		ll_back.setOnClickListener(this);
		ll_standbytime1.setOnClickListener(this);
		ll_standbytime2.setOnClickListener(this);
		ll_standbytime3.setOnClickListener(this);
		ll_standbytime4.setOnClickListener(this);
		ll_standbytime5.setOnClickListener(this);
	}@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		resetImageview();
		String standbytime = sp.spGetString(SharedPreferencesUtils.SP_STANDBYTIME);
		switch (standbytime) {
		case STANDBYTIME1:
			iv_standbytime1.setVisibility(View.VISIBLE);
		
			break;
		case STANDBYTIME2:
			iv_standbytime2.setVisibility(View.VISIBLE);
			
			break;
		case STANDBYTIME3:
			iv_standbytime3.setVisibility(View.VISIBLE);
			
			break;
		case STANDBYTIME4:
			iv_standbytime4.setVisibility(View.VISIBLE);
			
			break;
		case STANDBYTIME5:
			iv_standbytime5.setVisibility(View.VISIBLE);
			
			break;

		default:
			iv_standbytime1.setVisibility(View.VISIBLE);
			
			break;
		}
	}
	
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		resetImageview();

		switch (v.getId()) {
		case R.id.ll_back:
			finish();
			
			break;
		case R.id.ll_standbytime1:
			iv_standbytime1.setVisibility(View.VISIBLE);
			sp.spSaveData(SharedPreferencesUtils.SP_STANDBYTIME, STANDBYTIME1);
		
			break;
		case R.id.ll_standbytime2:
			iv_standbytime2.setVisibility(View.VISIBLE);
			sp.spSaveData(SharedPreferencesUtils.SP_STANDBYTIME, STANDBYTIME2);
			
			break;
		case R.id.ll_standbytime3:
			iv_standbytime3.setVisibility(View.VISIBLE);
			sp.spSaveData(SharedPreferencesUtils.SP_STANDBYTIME, STANDBYTIME3);
			
			break;
		case R.id.ll_standbytime4:
			iv_standbytime4.setVisibility(View.VISIBLE);
			sp.spSaveData(SharedPreferencesUtils.SP_STANDBYTIME, STANDBYTIME4);
			
			break;
		case R.id.ll_standbytime5:
			iv_standbytime5.setVisibility(View.VISIBLE);
			sp.spSaveData(SharedPreferencesUtils.SP_STANDBYTIME, STANDBYTIME5);
			
			break;
		default:
			break;
		}
	}
	
	private void resetImageview() {
		iv_standbytime1.setVisibility(View.GONE);
		iv_standbytime2.setVisibility(View.GONE);
		iv_standbytime3.setVisibility(View.GONE);
		iv_standbytime4.setVisibility(View.GONE);
		iv_standbytime5.setVisibility(View.GONE);
	}
}
