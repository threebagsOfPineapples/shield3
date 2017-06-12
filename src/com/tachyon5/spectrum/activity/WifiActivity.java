package com.tachyon5.spectrum.activity;

import java.util.ArrayList;
import java.util.List;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.utils.WifiPswDialog;
import com.tachyon5.spectrum.utils.WifiPswDialog.OnCustomDialogListener;
import com.tachyon5.spectrum.utils.WifiUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SyncStatusObserver;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class WifiActivity extends BaseActivity implements OnClickListener {

	private LinearLayout ll_back;
	private ToggleButton tb_switch;
	private TextView tv_wifiname;
	private ListView listview_wifi;
	private LinearLayout ll_wificollector;
	private LinearLayout ll_wifilist;
	private LinearLayout ll_hint; // ��������WiFi��ʾ 

	private String wifiPassword = null;
	private List<ScanResult> wifiResultList;
	private List<String> wifiListString = new ArrayList<String>();
	private WifiUtils localWifiUtils;

	private MyListAdapter adapter;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wifi);
		registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
		initView();

	}

	@SuppressWarnings("unused")
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (localWifiUtils.WifiCheckState() == 3) {
			handler.sendEmptyMessageDelayed(1, 100);
			tb_switch.setChecked(true);
		} else {
			tb_switch.setChecked(false);
			ll_hint.setVisibility(View.GONE);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(this);
		tb_switch = (ToggleButton) findViewById(R.id.tb_switch);
		listview_wifi = (ListView) findViewById(R.id.listview_wifi);
		tv_wifiname = (TextView) findViewById(R.id.tv_wifiname);
		ll_wificollector = (LinearLayout) findViewById(R.id.ll_wificollector);
		ll_wifilist = (LinearLayout) findViewById(R.id.ll_wifilist);
		ll_hint = (LinearLayout) findViewById(R.id.ll_hint);

		tb_switch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
			//	Toast.makeText(mContext, "" + isChecked, 0).show();
				tb_switch.setClickable(false);
				handler.sendEmptyMessageDelayed(2, 2000);
				if (isChecked) {
					handler.sendEmptyMessageDelayed(1, 100);
					ll_hint.setVisibility(View.VISIBLE);
				} else {

					localWifiUtils.WifiClose();
				}
			}
		});

		adapter = new MyListAdapter();
		listview_wifi.setAdapter(adapter);
		ListOnItemClickListener wifiListListener = new ListOnItemClickListener();
		listview_wifi.setOnItemClickListener(wifiListListener);
		ListOnItemLongClickListener wifiListlongListener = new ListOnItemLongClickListener();
		listview_wifi.setOnItemLongClickListener(wifiListlongListener);
		localWifiUtils = new WifiUtils(mContext);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_back:
			finish();
			break;

		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1: // ��wifi ������ʾwifi�б�
				wifiListString.clear();
				localWifiUtils.WifiOpen();
				localWifiUtils.WifiStartScan();
				// 0���ڹر�,1WIFi������,2���ڴ�,3����,4״̬����zhi
				while (localWifiUtils.WifiCheckState() != WifiManager.WIFI_STATE_ENABLED) {// �ȴ�Wifi����
					Log.i("WifiState", String.valueOf(localWifiUtils.WifiCheckState()));
				}
				try {
					Thread.sleep(2000);// ����3s������������ڳ����״ο���WIFIʱ�򣬴���getScanResults�����wifiResultList.size()�����쳣
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				wifiResultList = localWifiUtils.getScanResults();
				localWifiUtils.getConfiguration();
				if (wifiListString != null) {
					Log.i("WIFIButtonListener", "dataChange");
					scanResultToString(wifiResultList, wifiListString);
				}
				break;
			case 2:
				tb_switch.setClickable(true);

				break;
			}
		}
	};

	private class MyListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return wifiListString.size();

		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.item_wifi, null);
				holder.tv_wifiname = (TextView) convertView.findViewById(R.id.tv_wifiname);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			String[] names = wifiListString.get(position).split("--");
			String weifiname = names[0];
			holder.tv_wifiname.setText(weifiname);
			ll_hint.setVisibility(View.GONE);
			return convertView;
		}
	}

	public static class ViewHolder {
		public TextView tv_wifiname;
	}

	// list item ���
	class ListOnItemClickListener implements OnItemClickListener {
		String wifiItemSSID = null; // wifi ����

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			Log.i("ListOnItemClickListener1", "start");
			String wifiItem = wifiListString.get(arg2);// ���ѡ�е��豸
			String[] ItemValue = wifiItem.split("--");
			wifiItemSSID = ItemValue[0];
			Log.i("ListOnItemClickListener2", wifiItemSSID);
			int wifiItemId = localWifiUtils.IsConfiguration("\"" + wifiItemSSID + "\"");
			Log.i("ListOnItemClickListener3", String.valueOf(wifiItemId));
			if (wifiItemId != -1) {
				if (localWifiUtils.ConnectWifi(wifiItemId)) {// ����ָ��WIFI

				}
			} else {// û�����ú���Ϣ������
				WifiPswDialog pswDialog = new WifiPswDialog(mContext, new OnCustomDialogListener() {
					@Override
					public void back(String str) {
						// TODO Auto-generated method stub
						wifiPassword = str;
						if (wifiPassword != null) {
							int netId = localWifiUtils.AddWifiConfig(wifiResultList, wifiItemSSID, wifiPassword);
							Log.i("WifiPswDialog", String.valueOf(netId));
							if (netId != -1) {
								localWifiUtils.getConfiguration();// �����������Ϣ��Ҫ���µõ�������Ϣ
								if (localWifiUtils.ConnectWifi(netId)) {

								}
							} else {
								Toast.makeText(mContext, "�������Ӵ���", Toast.LENGTH_SHORT).show();

							}
						} else {

						}
					}
				});
				pswDialog.show();
			}
		}
	}

	// list item ���� ������磬�������
	class ListOnItemLongClickListener implements OnItemLongClickListener {
		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			openNetworkSettings(position);
			return true;
		}

	}

	// ɾ������ ����
	private void openNetworkSettings(final int position) {
		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("�Ƿ�ɾ��������").setMessage("ɾ�����ٴ���������������")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String wifiItem = wifiListString.get(position);// ���ѡ�е��豸
						String[] ItemValue = wifiItem.split("--");
						String removeSSID = ItemValue[0];
						int wifiItemId = localWifiUtils.IsConfiguration("\"" + removeSSID + "\"");
						localWifiUtils.removeNet(wifiItemId);
						wifiListString.remove(position);
						adapter.notifyDataSetChanged();
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
	}

	// ScanResult����תΪString
	public void scanResultToString(List<ScanResult> listScan, List<String> listStr) {
		for (int i = 0; i < listScan.size(); i++) {
			ScanResult strScan = listScan.get(i);
			String str = strScan.SSID + "--" + strScan.BSSID;
			boolean bool = listStr.add(str);
			if (bool) {
				adapter.notifyDataSetChanged();// ���ݸ���,ֻ�ܵ���Item���£����ܹ�����List����
			} else {
				Log.i("scanResultToSting", "fail");
			}
			Log.i("scanResultToString", listStr.get(i));
		}
	}

	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION);
		intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);

		return intentFilter;
	}

	// wifi ״̬�����㲥
	private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
		@SuppressLint("ResourceAsColor")
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
				// signal strength changed
			} else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {// wifi���������
				Log.i(logTAG, "����״̬�ı�");
				NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
				if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
					Log.i(logTAG, "wifi�������ӶϿ�");
					ll_wificollector.setVisibility(View.GONE);
				} else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
					WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
					WifiInfo wifiInfo = wifiManager.getConnectionInfo();
					// ��ȡ��ǰwifi����
					Log.i(logTAG, "���ӵ����� " + wifiInfo.getSSID());
					ll_wificollector.setVisibility(View.VISIBLE);
					tv_wifiname.setText(wifiInfo.getSSID());
				}
			} else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {// wifi�����
				int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);

				if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
					Log.i(logTAG, "ϵͳ�ر�wifi");
					ll_wificollector.setVisibility(View.GONE);
					ll_wifilist.setVisibility(View.GONE);
				} else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
					Log.i(logTAG, "ϵͳ����wifi");
					
					ll_wifilist.setVisibility(View.VISIBLE);
				}
			}
		}
	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mGattUpdateReceiver);
	}

}
