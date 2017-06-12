package com.tachyon5.spectrum.activity;


import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.bean.UpdateInfo;
import com.tachyon5.spectrum.common.Algorithm;
import com.tachyon5.spectrum.common.AppUpdate;
import com.tachyon5.spectrum.common.JsonManger;
import com.tachyon5.spectrum.serialport.SerialData;
import com.tachyon5.spectrum.serialport.SerialPortUtil;
import com.tachyon5.spectrum.serialport.SerialPortUtil.OnDataReceiveListener;
import com.tachyon5.spectrum.sqlutils.DBManage;
import com.tachyon5.spectrum.utils.CommonUtils;
import com.tachyon5.spectrum.utils.Contents;
import com.tachyon5.spectrum.utils.LocationUtils;
import com.tachyon5.spectrum.utils.SystemInformationUtils;
import com.tachyon5.spectrum.utils.ViewSetUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import okhttp3.Call;

public class HomeActivity extends BaseActivity implements OnClickListener {
	// 状态栏
	private TextView tv_carrieroperator; // 运营商
	private ImageView iv_signal; // 信号iv_signal
	private ImageView iv_wifi; // wifi
	private ImageView iv_network_mode; // 手机网络模式
	private ImageView iv_electricity; // 电量 ·
	//handle
	private static final int UPDATE = 0;
	private static final int SET_Time = 1;
	private static final int LOCALPARAM = 2;


	private TextView tv_time;
	private TextView tv_date;
	private TextView tv_temperature;
	private ImageView iv_check;
	private ImageView iv_record;
	private ImageView iv_notify;
	private ImageView iv_set;

	private DBManage dbm;
	
	//串口类和数据
	public static SerialPortUtil mSerialPortUtil;
	
	public SerialData mSerialData;
	
	public static int[] refData; // ref数据
	public static int[] dardData; // dark数据
	public static int[] sampleData; // sample数据   
	
	

	//app更新
	public static String fw_verson; // 版本号
	public static UpdateInfo updateinfo; // 版本信息实体类
	private AppUpdate appUpdate; //更新类
	private ProgressDialog progressDialog; //更新提示框
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();

		dbm = new DBManage(this);
		mSerialData = new SerialData(); 
		serialPortUtil_CollectListener();
		// 检测版本是否需要更新
		if (SystemInformationUtils.checkNetworkAvailable(this)) {
		//	checkUpdate();
		//	appUpdate = new AppUpdate(mContext);
		//	handler.sendEmptyMessage(UPDATE); 
		}

	//	new LocationUtils().getPosition(mContext);// 获取 位置信息
		
		try {
			HttpCreateSession();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	// 联网检测版本是否需要更新
	private void checkUpdate() {
		new Thread() {
			public void run() {
				appUpdate = new AppUpdate(mContext);
				try {
					getAppLastestVersion(); // 获取版本信息
				} catch (JSONException e) {
					e.printStackTrace();
				}
			};
		}.start();
	  }

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 时间 和日期 设置
		ViewSetUtils.setTimeView(mContext,tv_time); // 显示当前时间
		tv_date.setText(CommonUtils.getWeekOfDate(new Date()));// 设置日期
	//	Toast.makeText(mContext, Contents.Province+Contents.City+Contents.District, 0).show();
	//	Toast.makeText(mContext, CommonUtils.getMobileDataState(mContext)+"", 0).show();
	}

	@SuppressLint("SimpleDateFormat")
	private void initView() {
		// TODO Auto-generated method stub
		tv_carrieroperator = (TextView) findViewById(R.id.tv_carrieroperator);
		iv_signal = (ImageView) findViewById(R.id.iv_signal);
		iv_wifi = (ImageView) findViewById(R.id.iv_wifi);
		iv_network_mode = (ImageView) findViewById(R.id.iv_network_mode);
		iv_electricity = (ImageView) findViewById(R.id.iv_electricity);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_date = (TextView) findViewById(R.id.tv_date);
		tv_temperature = (TextView) findViewById(R.id.tv_temperature);
		iv_check = (ImageView) findViewById(R.id.iv_check);
		iv_record = (ImageView) findViewById(R.id.iv_record);
		iv_notify = (ImageView) findViewById(R.id.iv_notify);
		iv_set = (ImageView) findViewById(R.id.iv_set);
		iv_check.setOnClickListener(this);
		iv_record.setOnClickListener(this);
		iv_notify.setOnClickListener(this);
		iv_set.setOnClickListener(this);
		// 时间 和日期 设置
		handler.sendEmptyMessageDelayed(SET_Time, 1000);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_check:
			Intent intent_Check = new Intent(mContext, CheckActivity1.class);
			startActivity(intent_Check);
			break;
		case R.id.iv_record:
			Intent intent_record = new Intent(mContext, RecordActivity1.class);
			startActivity(intent_record);
			break;
		case R.id.iv_notify:
			/*  for (int i = 0; i < 10; i++) { 
				  dbm.addNotification((i + 1) + "",
			      "这是通知标题", "这是通知时间", "内容这是内容", Contents.NOTIFICATIONTAB_NOREAD);
				  }*/
			  			
			  Intent intent_Notify = new Intent(mContext,  NotifyActivity.class); 
			  startActivity(intent_Notify); 
			break;
		case R.id.iv_set:
			Intent intent_set = new Intent(mContext, SetActivity.class);
			startActivity(intent_set);
	
			break;
		default:
			
			break;
		}
	}

	// 采集板串口监听
	private void serialPortUtil_CollectListener() {
		// TODO Auto-generated method stub
		// 采集板串口回调
		mSerialPortUtil = new SerialPortUtil();
		mSerialPortUtil.onCreate();
		mSerialPortUtil.setOnDataReceiveListener(new OnDataReceiveListener() {
			@Override
			public void onDataReceive(byte[] buffer, int size) {
				
				Log.i(logTAG, "采集板回调—————————++———————————————————" + size);
				int status = mSerialData.assembleMsgByByte(buffer, size);
				if (status == SerialData.MSG_STATE_PROCESSING) {
					mSerialData.dataProcessing();
					switch (mSerialData.getmCmd()) {

					case Contents.MSG_CMD_FW_VERSON_CALIB:
						Log.i(logTAG, "=版本号=" + fw_verson);
						CommonUtils.broadcastUpdate(mContext,Contents.ACTION_FW_VERSON);
						break;
					case Contents.MSG_CMD_DARK1_CALIB:
						Log.i(logTAG, "=dark=" + dardData.length);
						CommonUtils.broadcastUpdate(mContext,Contents.ACTION_DARK);
						break;
					case Contents.MSG_CMD_REF1_CALIB:
						Log.i(logTAG, "=ref=" + refData.length);
						CommonUtils.broadcastUpdate(mContext,Contents.ACTION_REF);
						break;
					case Contents.MSG_CMD_SAMPLE_CALIB:
						CommonUtils.broadcastUpdate(mContext,Contents.ACTION_SAMPLE);
						break;
					default:
						
						break;
					}
				}
			}
		});
	}


	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SET_Time:
				ViewSetUtils.setTimeView(mContext,tv_time);
				handler.sendEmptyMessageDelayed(SET_Time, 500);
				break;
				
			case UPDATE:
				if (true) { //版本更新提示	appUpdate.isNeedUpdate()			
					showVersonDialog();
				}				
				break;
			case LOCALPARAM:
				try {
					getLocalParam();
				} catch (JSONException e) {
					e.printStackTrace();
				}				
				break;
			default:
				break;
			}
		}
	};

	// 重写 baseActivity 的方法 改变标题栏状态---------------------------

	@Override
	protected void changeSignal() {
		// TODO Auto-generated method stub
		ViewSetUtils.setSignalView(Signal_TAG, iv_signal);
	}

	@Override
	protected void changeCarrieroperator() {
		// TODO Auto-generated method stub
		ViewSetUtils.setCarrieroperatorView(Carrieroperator_TAG, tv_carrieroperator);
	}

	@Override
	protected void changeNetWork() {
		// TODO Auto-generated method stub
		ViewSetUtils.setNetworkView(Network_TAG, iv_network_mode);
	}

	@Override
	protected void changeWifi() {
		// TODO Auto-generated method stub
		ViewSetUtils.setWifiView(Wifi_TAG, iv_wifi,iv_network_mode);
	}

	@Override
	protected void changeElectricity() {
		// TODO Auto-generated method stub
		ViewSetUtils.setElectricityView(Electricity_TAG, iv_electricity);
	}

	// 创建会话 请求`
	private void HttpCreateSession() throws JSONException {
		Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_1(SystemInformationUtils.getIMEI(mContext)));
		OkHttpUtils.post().url(Contents.NET_CREATE_SESSON)
				.addParams("json", JsonManger.getJsonStr_1(SystemInformationUtils.getIMEI(mContext))).build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call arg0, Exception arg1) {
						Log.e(logTAG, "请求失败："+arg1);
					}
					@Override
					public void onResponse(String string) {
						if (string == null) {
							Log.e(logTAG,"返回数据 null");	
							return;
						  }else{
							  System.out.println("返回数据-"+ string);  
						  }
						JSONObject json_data = null;
						JSONObject json_resp = null;
							try {
								 System.out.println("--string:" + string + "json:"+ json_resp);
								json_resp = new JSONObject(string);
							   
								if (json_resp != null&& json_resp.get(Contents.JSON_KEY_RESULT) != null
									&& json_resp.get(Contents.JSON_KEY_RESULT)	.equals("OK")) {
									json_data = new JSONObject(json_resp.getString(Contents.JSON_KEY_DATA));
									Contents.seesionID = json_data.getString("sessionID");
									Contents.ifNewUser = json_data.getString("ifNewUser");
									Contents.userID= json_data.getString("userid");
									Contents.userNick = json_data.getString("nickname");
									Contents.notifyCount = json_data.getString("notifycount");
									Log.i(logTAG, "登录成功！！！"+Contents.seesionID+"/"+Contents.ifNewUser
											+"/"+Contents.userID+"/"+Contents.notifyCount);
									Toast.makeText(mContext, "登录成功！！！", 0).show();
									handler.sendEmptyMessageDelayed(LOCALPARAM, 100); //登录成功后获取本地算法数据
								} else {
									Log.e(logTAG,"解析数据 fail");
								}
							} catch (JSONException e) {
								e.printStackTrace();
								Log.e(logTAG,"解析数据异常");
							}
						
					}
				});
	}

	// 连网请求 获取版本信息
	public void getAppLastestVersion()throws JSONException {
		Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_2());
		OkHttpUtils.post().url(Contents.APP_VERSON).addParams("json", JsonManger.getJsonStr_2()).build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call arg0, Exception arg1) {
						Log.e(logTAG, "请求失败："+arg1);
					}
					@Override
					public void onResponse(String string) {
						if (string == null) {
							Log.w(logTAG,"返回数据 null");	
							return;
						}
						JSONObject json_resp = null;
						    try {
						    	System.out.println("string:" + string + "json:"+ json_resp);
								json_resp = new JSONObject(string);
							
								
								if (json_resp != null&& json_resp.get(Contents.JSON_KEY_RESULT) != null
										&& json_resp.get(Contents.JSON_KEY_RESULT).equals("OK")) {
									/*Gson gson = new Gson();
									updateinfo = gson.fromJson(json_resp.get(Contents.JSON_KEY_DATA).toString(),UpdateInfo.class);
									System.out.println(updateinfo.getDownloadPath()+ updateinfo.getVersionCode()+ updateinfo.getVersionName());
									handler.sendEmptyMessage(UPDATE);*/ // 获取版本信息成功 比较版本提示是否更新
									Log.e(logTAG,"获取版本号成功");		
									Toast.makeText(mContext, "获取版本号成功！！！", 0).show();
								} else {
									Log.e(logTAG,"解析数据 fail");
								}
							} catch (JSONException e) {
								e.printStackTrace();
								Log.e(logTAG,"解析数据异常");
							}
					}
				});
	     }
	
	// 本地算法数据
		public void getLocalParam()throws JSONException {
			Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_3());
			OkHttpUtils.post().url(Contents.NET_NET).addParams("json", JsonManger.getJsonStr_3()).build()
					.execute(new StringCallback() {
						@Override
						public void onError(Call arg0, Exception arg1) {
							Log.e(logTAG, "请求失败："+arg1);
						}
						@Override
						public void onResponse(String string) {
							if (string == null) {
								Log.e(logTAG,"返回数据 null");
								return;
							}
							JSONObject json_resp = null;
							JSONObject json_data = null;
								try {
									json_resp = new JSONObject(string);
									System.out.println("string:" + string + "json:"+ json_resp);
									if (json_resp != null&& json_resp.get(Contents.JSON_KEY_RESULT) != null
											&& json_resp.get(Contents.JSON_KEY_RESULT)	.equals("OK")) {
										json_data = new JSONObject(json_resp.getString(Contents.JSON_KEY_DATA));
										Log.i(logTAG, "本地算法"+json_data.getString("beta"));
										Toast.makeText(mContext, "本地算法"+json_data.getString("beta"), 0).show();
										Contents.beta = Algorithm.getBeta(json_data.getString("beta"));
										Contents.rgblimit= Algorithm.getRgbLimit(json_data.getString("rgblimit"));
											
												
									} else {
										Log.e(logTAG,"解析数据fail");
									}
								} catch (JSONException e) {
									e.printStackTrace();
									Log.e(logTAG,"解析数据异常");
								}
						}
					});
		}
		

	

	
	
	
	// 是否更新新版本 app 弹框删除网络 弹框
		private void showVersonDialog() {
			AlertDialog dialog = new AlertDialog.Builder(this).setTitle("是否更新新版本").setMessage("新版本。。。")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {							
								downFile(Contents.url); //updateinfo.getDownloadPath()
							} else {
								Toast.makeText(mContext, "SD卡不可用，请插入SD卡", Toast.LENGTH_SHORT).show();
							}
						}
					}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					}).show();
		}
		
		private void downFile(final String url) {
			progressDialog = new ProgressDialog(HomeActivity.this); // 进度条，在下载的时候实时更新进度，提高用户友好度
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setTitle("正在下载");
			progressDialog.setMessage("请稍候...");
			progressDialog.setProgress(0);
			progressDialog.show();
			appUpdate.downLoadFile(url, progressDialog, handler);
		}
		
		
		 public boolean onKeyDown(int keyCode, KeyEvent event) {
				
			    if (keyCode==KeyEvent.KEYCODE_F1){
			        Log.i(logTAG, "777777777777777777777777777777777777777");
			    }
			    Log.i(logTAG, "----------------"+keyCode);
			    return super.onKeyDown(keyCode, event);
			}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// sp.closeSerialPort();
	}
   
	
	
}
