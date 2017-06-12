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
	// ״̬��
	private TextView tv_carrieroperator; // ��Ӫ��
	private ImageView iv_signal; // �ź�iv_signal
	private ImageView iv_wifi; // wifi
	private ImageView iv_network_mode; // �ֻ�����ģʽ
	private ImageView iv_electricity; // ���� ��
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
	
	//�����������
	public static SerialPortUtil mSerialPortUtil;
	
	public SerialData mSerialData;
	
	public static int[] refData; // ref����
	public static int[] dardData; // dark����
	public static int[] sampleData; // sample����   
	
	

	//app����
	public static String fw_verson; // �汾��
	public static UpdateInfo updateinfo; // �汾��Ϣʵ����
	private AppUpdate appUpdate; //������
	private ProgressDialog progressDialog; //������ʾ��
		
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();

		dbm = new DBManage(this);
		mSerialData = new SerialData(); 
		serialPortUtil_CollectListener();
		// ���汾�Ƿ���Ҫ����
		if (SystemInformationUtils.checkNetworkAvailable(this)) {
		//	checkUpdate();
		//	appUpdate = new AppUpdate(mContext);
		//	handler.sendEmptyMessage(UPDATE); 
		}

	//	new LocationUtils().getPosition(mContext);// ��ȡ λ����Ϣ
		
		try {
			HttpCreateSession();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	// �������汾�Ƿ���Ҫ����
	private void checkUpdate() {
		new Thread() {
			public void run() {
				appUpdate = new AppUpdate(mContext);
				try {
					getAppLastestVersion(); // ��ȡ�汾��Ϣ
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
		// ʱ�� ������ ����
		ViewSetUtils.setTimeView(mContext,tv_time); // ��ʾ��ǰʱ��
		tv_date.setText(CommonUtils.getWeekOfDate(new Date()));// ��������
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
		// ʱ�� ������ ����
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
			      "����֪ͨ����", "����֪ͨʱ��", "������������", Contents.NOTIFICATIONTAB_NOREAD);
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

	// �ɼ��崮�ڼ���
	private void serialPortUtil_CollectListener() {
		// TODO Auto-generated method stub
		// �ɼ��崮�ڻص�
		mSerialPortUtil = new SerialPortUtil();
		mSerialPortUtil.onCreate();
		mSerialPortUtil.setOnDataReceiveListener(new OnDataReceiveListener() {
			@Override
			public void onDataReceive(byte[] buffer, int size) {
				
				Log.i(logTAG, "�ɼ���ص�������������������++��������������������������������������" + size);
				int status = mSerialData.assembleMsgByByte(buffer, size);
				if (status == SerialData.MSG_STATE_PROCESSING) {
					mSerialData.dataProcessing();
					switch (mSerialData.getmCmd()) {

					case Contents.MSG_CMD_FW_VERSON_CALIB:
						Log.i(logTAG, "=�汾��=" + fw_verson);
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
				if (true) { //�汾������ʾ	appUpdate.isNeedUpdate()			
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

	// ��д baseActivity �ķ��� �ı������״̬---------------------------

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

	// �����Ự ����`
	private void HttpCreateSession() throws JSONException {
		Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_1(SystemInformationUtils.getIMEI(mContext)));
		OkHttpUtils.post().url(Contents.NET_CREATE_SESSON)
				.addParams("json", JsonManger.getJsonStr_1(SystemInformationUtils.getIMEI(mContext))).build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call arg0, Exception arg1) {
						Log.e(logTAG, "����ʧ�ܣ�"+arg1);
					}
					@Override
					public void onResponse(String string) {
						if (string == null) {
							Log.e(logTAG,"�������� null");	
							return;
						  }else{
							  System.out.println("��������-"+ string);  
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
									Log.i(logTAG, "��¼�ɹ�������"+Contents.seesionID+"/"+Contents.ifNewUser
											+"/"+Contents.userID+"/"+Contents.notifyCount);
									Toast.makeText(mContext, "��¼�ɹ�������", 0).show();
									handler.sendEmptyMessageDelayed(LOCALPARAM, 100); //��¼�ɹ����ȡ�����㷨����
								} else {
									Log.e(logTAG,"�������� fail");
								}
							} catch (JSONException e) {
								e.printStackTrace();
								Log.e(logTAG,"���������쳣");
							}
						
					}
				});
	}

	// �������� ��ȡ�汾��Ϣ
	public void getAppLastestVersion()throws JSONException {
		Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_2());
		OkHttpUtils.post().url(Contents.APP_VERSON).addParams("json", JsonManger.getJsonStr_2()).build()
				.execute(new StringCallback() {
					@Override
					public void onError(Call arg0, Exception arg1) {
						Log.e(logTAG, "����ʧ�ܣ�"+arg1);
					}
					@Override
					public void onResponse(String string) {
						if (string == null) {
							Log.w(logTAG,"�������� null");	
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
									handler.sendEmptyMessage(UPDATE);*/ // ��ȡ�汾��Ϣ�ɹ� �Ƚϰ汾��ʾ�Ƿ����
									Log.e(logTAG,"��ȡ�汾�ųɹ�");		
									Toast.makeText(mContext, "��ȡ�汾�ųɹ�������", 0).show();
								} else {
									Log.e(logTAG,"�������� fail");
								}
							} catch (JSONException e) {
								e.printStackTrace();
								Log.e(logTAG,"���������쳣");
							}
					}
				});
	     }
	
	// �����㷨����
		public void getLocalParam()throws JSONException {
			Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_3());
			OkHttpUtils.post().url(Contents.NET_NET).addParams("json", JsonManger.getJsonStr_3()).build()
					.execute(new StringCallback() {
						@Override
						public void onError(Call arg0, Exception arg1) {
							Log.e(logTAG, "����ʧ�ܣ�"+arg1);
						}
						@Override
						public void onResponse(String string) {
							if (string == null) {
								Log.e(logTAG,"�������� null");
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
										Log.i(logTAG, "�����㷨"+json_data.getString("beta"));
										Toast.makeText(mContext, "�����㷨"+json_data.getString("beta"), 0).show();
										Contents.beta = Algorithm.getBeta(json_data.getString("beta"));
										Contents.rgblimit= Algorithm.getRgbLimit(json_data.getString("rgblimit"));
											
												
									} else {
										Log.e(logTAG,"��������fail");
									}
								} catch (JSONException e) {
									e.printStackTrace();
									Log.e(logTAG,"���������쳣");
								}
						}
					});
		}
		

	

	
	
	
	// �Ƿ�����°汾 app ����ɾ������ ����
		private void showVersonDialog() {
			AlertDialog dialog = new AlertDialog.Builder(this).setTitle("�Ƿ�����°汾").setMessage("�°汾������")
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {							
								downFile(Contents.url); //updateinfo.getDownloadPath()
							} else {
								Toast.makeText(mContext, "SD�������ã������SD��", Toast.LENGTH_SHORT).show();
							}
						}
					}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					}).show();
		}
		
		private void downFile(final String url) {
			progressDialog = new ProgressDialog(HomeActivity.this); // �������������ص�ʱ��ʵʱ���½��ȣ�����û��Ѻö�
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setTitle("��������");
			progressDialog.setMessage("���Ժ�...");
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
