package com.tachyon5.spectrum.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.bean.NotificationBean;
import com.tachyon5.spectrum.common.JsonManger;
import com.tachyon5.spectrum.sqlutils.DBManage;
import com.tachyon5.spectrum.utils.Contents;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import okhttp3.Call;

public class NotifyActivity extends BaseActivity {

	private LinearLayout ll_back;
	private ListView lv_notificationlist;

	private MyListAdapter adapter;
	private List<NotificationBean> notificationlist;
	private DBManage dbm;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify);
		initView();
		notificationlist = new ArrayList<NotificationBean>();
		dbm = new DBManage(this);
		adapter = new MyListAdapter();
		
		try {
			getNotify();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		notificationlist = dbm.queryNotification();
		lv_notificationlist.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	private void initView() {
		// TODO Auto-generated method stub
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		lv_notificationlist = (ListView) findViewById(R.id.lv_notificationlist);
		lv_notificationlist.setDivider(new ColorDrawable(0x440000FF));// 0x363d6a
		lv_notificationlist.setDividerHeight(1);
		lv_notificationlist.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				// notification tab 改为 NOTIFICATIONTAB_READ 标记为已读
				dbm.updateNotification(notificationlist.get(position).getNumber(), Contents.NOTIFICATIONTAB_READ);
				Intent intent = new Intent(mContext, NotificationContentActivity.class);
				intent.putExtra("notificationContent", notificationlist.get(position).getDimension());
				intent.putExtra("notificationTitle", notificationlist.get(position).getTitle());
				intent.putExtra("notificationTime", notificationlist.get(position).getTime());
				startActivity(intent);
			}
		});
	}

	private class MyListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return notificationlist.size();
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
				convertView = getLayoutInflater().inflate(R.layout.item_notification, null);
				holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
				holder.iv_red_dot = (ImageView) convertView.findViewById(R.id.iv_red_dot);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_title.setText(notificationlist.get(position).getTitle());
			if (notificationlist.get(position).getTab().equals(Contents.NOTIFICATIONTAB_READ)) {
				holder.iv_red_dot.setVisibility(View.GONE);
			} else {
				holder.iv_red_dot.setVisibility(View.VISIBLE);
			}

			return convertView;
		}

	}

	public static class ViewHolder {
		public TextView tv_title;
		public ImageView iv_red_dot;

	}
	
	
	// 获取通知
	public void getNotify()throws JSONException {
			
		Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_7());
		OkHttpUtils.post().url(Contents.NET_NET)
				.addParams("json", JsonManger.getJsonStr_7()).build().execute(new StringCallback() {
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
									Log.i(logTAG,"获取通知"+ json_data.getString("count"));
									Toast.makeText(mContext, "获取通知"+ json_data.getString("count"), 0).show();
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

	}

}
