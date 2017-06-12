package com.tachyon5.spectrum.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.common.Algorithm;
import com.tachyon5.spectrum.common.IntentFilterManger;
import com.tachyon5.spectrum.common.JsonManger;
import com.tachyon5.spectrum.serialport.DataAssemblyUtil;
import com.tachyon5.spectrum.serialport.SerialPortUtil_Printer;
import com.tachyon5.spectrum.serialport.SerialPortUtil_Printer.OnDataReceiveListener_Printer;
import com.tachyon5.spectrum.utils.CommonUtils;
import com.tachyon5.spectrum.utils.Contents;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import okhttp3.Call;

public class CheckActivity1 extends BaseActivity implements OnClickListener{
	private ImageView iv_back;	
	private com.tachyon5.spectrum.view.TestView testView;
	private LinearLayout ll_check;
	private LinearLayout ll_result;
	private ImageView iv_start;
	private ImageView iv_restart;
	private ImageView iv_print;
	
	public static  String darkrefID="23";
	
	public static SerialPortUtil_Printer mSerialPortUtil_printer;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check1);
		initView();
		registerReceiver(CheckActivityReceiver, IntentFilterManger.CheckActivityIntentFilter()); // 注册广播
		serialPortUtil_PrintListener();

	}
		
	private void initView() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		testView = (com.tachyon5.spectrum.view.TestView )findViewById(R.id.testView);
		ll_check=(LinearLayout)findViewById(R.id.ll_check);
		ll_result=(LinearLayout)findViewById(R.id.ll_result);
		iv_start=(ImageView)findViewById(R.id.iv_start);
		iv_start.setImageResource(R.drawable.start_check);
		iv_start.setOnClickListener(this);
		iv_restart=(ImageView)findViewById(R.id.iv_restart);
		iv_restart.setOnClickListener(this);
		iv_print=(ImageView)findViewById(R.id.iv_print);
		iv_print.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
		//	finish();
			byte[] payload22 = null;
			byte[] buff22 = DataAssemblyUtil.getRawData((byte) 0x54, (byte) 0x00, payload22);
			HomeActivity.mSerialPortUtil.sendBuffer(buff22, buff22.length);
			Toast.makeText(mContext, "开始校验", 0).show();
		/*	try {
				getCheckResult(null,null,null,null);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			break;
		case R.id.iv_start:
			byte[] payload2 = null;
			byte[] buff2 = DataAssemblyUtil.getRawData((byte) 0x09, (byte) 0x00, payload2);
			HomeActivity.mSerialPortUtil.sendBuffer(buff2, buff2.length);
			testView.startAnimal();
			iv_start.setImageResource(R.drawable.starting_check);
			iv_start.setClickable(false);
		/*	try {
				getCheckResult(null,null,null,null);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			Log.i(logTAG, "开始检测");
			break;
		case R.id.iv_restart:
			ll_result.setVisibility(View.GONE);
			ll_check.setVisibility(View.VISIBLE);
			break;
		case R.id.iv_print:
			mSerialPortUtil_printer.sendPrint("打印内容");
			Intent intent_printer = new Intent(mContext, PrinterActivity.class);
			startActivity(intent_printer);
			break;
		default:
				
			break;
		}
	}
		
	// 广播 监听
		private final BroadcastReceiver CheckActivityReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(Contents.ACTION_FW_VERSON)) {
					Log.i(logTAG, "广播版本" + HomeActivity.fw_verson);
					handler.sendEmptyMessageDelayed(88, 10);
				} else if (intent.getAction().equals(Contents.ACTION_SAMPLE)) {
				
					String strSample=CommonUtils.getStringBySample(HomeActivity.sampleData);
					Log.i(logTAG, "sample>>"+strSample);
					String newStrSample =Algorithm.getSample(strSample);
						
					double[][] colorSensorSum = Algorithm.getColorSensorSum(strSample);
					
					double[][] newColorSensorSum = Algorithm.getNewRgbArr(colorSensorSum, Contents.beta);

					int [] rgbResult =Algorithm.getResult(colorSensorSum,Contents.beta,Contents.rgblimit);
					
					try {
						getCheckResult(darkrefID,newStrSample,rgbResult,newColorSensorSum);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
					handler.sendEmptyMessageDelayed(1, 1000);
					
				}else if (intent.getAction().equals(Contents.ACTION_DARK)) {
					String strDark=CommonUtils.getStringBySample(HomeActivity.dardData);
					Log.i(logTAG, "dark>>"+strDark);				
					byte[] payload3 = null;
					byte[] buff3 = DataAssemblyUtil.getRawData((byte) 0x55, (byte) 0x00, payload3);
					HomeActivity.mSerialPortUtil.sendBuffer(buff3, buff3.length);
					
					Toast.makeText(mContext, "dark"+strDark.length(), 0).show();
				}else if (intent.getAction().equals(Contents.ACTION_REF)) {
					String strDark=CommonUtils.getStringBySample(HomeActivity.dardData);
					String strRef=CommonUtils.getStringBySample(HomeActivity.refData);
					Log.i(logTAG, "erf>>"+strRef);	
					try {
						getDarkRefId(strDark,strRef);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Toast.makeText(mContext, "ref"+strRef.length(), 0).show();
				}
			}
		};
		private Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 1:
					testView.stopAnimal();
					iv_start.setImageResource(R.drawable.start_check);
					iv_start.setClickable(true);
					ll_check.setVisibility(View.GONE);
					ll_result.setVisibility(View.VISIBLE);
					break;
				case 88:
					Toast.makeText(mContext, "版本号是--" + HomeActivity.fw_verson, 0).show();
					break;
				}
			}
		};
			
		// 打印机串口监听
		private void serialPortUtil_PrintListener() {
			// TODO Auto-generated method stub
			// 打印机串口回调
			mSerialPortUtil_printer = new SerialPortUtil_Printer();
			mSerialPortUtil_printer.onCreate();
			mSerialPortUtil_printer
					.setOnDataReceiveListener_Printer(new OnDataReceiveListener_Printer() {
						@Override
						public void onDataReceive(byte[] buffer, int size) {
							// TODO Auto-generated method stub
							if(CommonUtils.byte2HexString(buffer).equals("20")){
				                 Log.i(logTAG, "--有纸");
							}else{
								 Log.i(logTAG, "--缺纸");
							}
						}
				});
		}
		
		// dark ref id
		public void getDarkRefId(String strDark,String strRef)throws JSONException {
			Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_4(strDark,strRef));
			OkHttpUtils.post().url(Contents.NET_NET)
					.addParams("json", JsonManger.getJsonStr_4(strDark,strRef)).build()
					.execute(new StringCallback() {
						@Override
						public void onError(Call arg0, Exception arg1) {
							Log.e(logTAG, "请求失败：" + arg1);
						}
						@Override
						public void onResponse(String string) {
							
							if (string == null) {
								Log.e(logTAG, "返回数据 null");
								return;
							}
							JSONObject json_resp = null;
							JSONObject json_data = null;
								try {
									json_resp = new JSONObject(string);
									System.out.println("--string:" + string + "json:"+ json_resp);
									if (json_resp != null&& json_resp.get(Contents.JSON_KEY_RESULT) != null
											&& json_resp.get(Contents.JSON_KEY_RESULT).equals("OK")) {
										json_data = new JSONObject(json_resp.getString(Contents.JSON_KEY_DATA));
										Log.i(logTAG,"daekrefID"+ json_data.getString("darkrefid"));
										darkrefID=json_data.getString("darkrefid");
										Toast.makeText(mContext, "daekrefID"+ json_data.getString("darkrefid"), 0).show();
									} else {
										Log.e(logTAG, "解析数据 fail");
									}
								} catch (JSONException e) {
									e.printStackTrace();
									Log.e(logTAG, "解析数据异常");
								}
							
						}
					});
		}
		
		//检测
		public void getCheckResult(String darkrefID,String sample,int[] ret,double[][] rgb)throws JSONException {
			System.out.println(logTAG+ "jsondata"+JsonManger.getJsonStr_5(darkrefID,sample,ret,rgb));
			OkHttpUtils.post().url(Contents.NET_NET).addParams("json", JsonManger.getJsonStr_5(darkrefID,sample,ret,rgb))
			.build().execute(new StringCallback() {
						@Override
						public void onError(Call arg0, Exception arg1) {
							Log.e(logTAG, "请求失败：" + arg1);
						}
						@Override
						public void onResponse(String string) {
							if (string == null) {
								Log.e(logTAG, "返回数据 null");
							    return;	
							}
							JSONObject json_resp = null;
							JSONObject json_data = null;
								try {
									System.out.println("--string-:" + string );
									json_resp = new JSONObject(string);
									
									if (json_resp != null&& json_resp.get(Contents.JSON_KEY_RESULT) != null
											&& json_resp.get(Contents.JSON_KEY_RESULT)	.equals("OK")) {
										json_data = new JSONObject(json_resp.getString(Contents.JSON_KEY_DATA));
										Log.i(logTAG, "检测成功"+json_data.getString("ret1"));
										Log.i(logTAG, "检测成功"+json_data.getString("ret2"));
										Log.i(logTAG, "检测成功"+json_data.getString("ret3"));
										Toast.makeText(mContext, "检测成功"+json_data.getString("ret1")+"/"+"检测成功"
										+json_data.getString("ret2")+"检测成功"+json_data.getString("ret3"), 0).show();
									} else {
										Log.e(logTAG, "解析数据 fail");
									}
								} catch (JSONException e) {
									e.printStackTrace();
									Log.e(logTAG, "解析数据异常");
								}
						}
					});
		}
		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			unregisterReceiver(CheckActivityReceiver);
		}
}
