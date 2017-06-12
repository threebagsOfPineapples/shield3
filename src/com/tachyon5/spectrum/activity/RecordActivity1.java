package com.tachyon5.spectrum.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;

import org.json.JSONException;
import org.json.JSONObject;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.adapter.HistoryListViewAdapter;
import com.tachyon5.spectrum.bean.HistoryListItemObject;
import com.tachyon5.spectrum.common.JsonManger;
import com.tachyon5.spectrum.utils.Contents;
import com.tachyon5.spectrum.utils.SystemInformationUtils;
import com.tachyon5.spectrum.view.SwipeRefreshView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

public class RecordActivity1 extends BaseActivity implements OnItemClickListener {
		
	private LinearLayout ll_back;
	private com.tachyon5.spectrum.view.CustomSwipeListView mListView;
	private List<HistoryListItemObject> mMessageItems = new ArrayList<HistoryListItemObject>();
	
	private int startindex; // ��ȡ��¼ ��ʼ index

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record1);
		initView();

		final SwipeRefreshView swipeRefreshView = (SwipeRefreshView) findViewById(R.id.srl);

		// ������onCreate�����ã������ʾ��ǰ��ˢ��״̬�����һ��������ˢ��״̬��SwipeRefreshLayout�����ε������¼�
		// swipeRefreshLayout.setRefreshing(true);

		// ������ɫ���Ե�ʱ��һ��Ҫע������������Դ�ļ�����ֱ������16���Ƶ���ɫ����Ϊ����intֵ���׸��
		// �����������ȵı�����ɫ��Ĭ�Ͼ��ǰ�ɫ��
		swipeRefreshView
				.setProgressBackgroundColorSchemeResource(android.R.color.white);
		// �����������ȵ�������ɫ
		swipeRefreshView.setColorSchemeResources(R.color.colorAccent,
				R.color.colorPrimary, R.color.colorPrimaryDark);

		// ����ʱ����SwipeRefreshLayout�������������������֮��ͻ�ص��������
		swipeRefreshView
				.setOnRefreshListener(new SwipeRefreshView.OnRefreshListener() {
					@Override
					public void onRefresh() {
						// ��ʼˢ�£����õ�ǰΪˢ��״̬
						// swipeRefreshLayout.setRefreshing(true);
						// ���������߳�
						// һЩ�ȽϺ�ʱ�Ĳ���������������ȡ���ݣ���Ҫ�ŵ����߳�ȥִ��
						// TODO ��ȡ����
						final Random random = new Random();
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								// mAdapter.notifyDataSetChanged();

								Toast.makeText(mContext, "ˢ����һ������",
										Toast.LENGTH_SHORT).show();

								// ��������������Ϊ��ˢ��״̬������������������
								swipeRefreshView.setRefreshing(false);
							}
						}, 1800);
					}
				});

		// �����������ظ���
		swipeRefreshView
				.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
					@Override
					public void onLoad() {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								// �������
							Toast.makeText(mContext, "������һ������" + 1 + "������",Toast.LENGTH_SHORT).show();
								// ��������������Ϊ������״̬�������ؽ���������
								swipeRefreshView.setLoading(false);
							}
						}, 1800);
					}
				});
	 // ��ȡ��¼����		
		startindex = 0;
		try {
			HttpRemoveRecord("2");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	public Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				// TODO ����ˢ�����ݲ���
			//	recordlist.clear();
				startindex = 0;
				try {
					HttpGetRecord();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	};
	

	private void initView() {
		ll_back = (LinearLayout) findViewById(R.id.ll_back);
		ll_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		mListView = (com.tachyon5.spectrum.view.CustomSwipeListView) findViewById(R.id.list);
		HistoryListItemObject item = null;
		for (int i = 0; i < 20; i++) {
			item = new HistoryListItemObject();
			if (i % 3 == 0) {
				item.setStr_result("ƥ��");
			}
			if (i % 3 == 1) {
				item.setStr_result("δ֪");
			}
			if (i % 3 == 2) {
				item.setStr_result("ƥ��");
			}
			item.setStr_type("���Խ����");
			item.setStr_time("17/04/11 17:00");
			mMessageItems.add(item);
		}

		mListView.setAdapter(new HistoryListViewAdapter(this, mMessageItems));
		mListView.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}


	// ��ȡ��¼
	private void HttpGetRecord() throws JSONException {

	   Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_6());
	   OkHttpUtils.post().url(Contents.NET_NET).addParams("json", JsonManger.getJsonStr_6())
	   .build().execute(new StringCallback() {
			@Override
			public void onError(Call arg0, Exception arg1) {
				Log.e(logTAG, "����ʧ�ܣ�" + arg1);
			}
			@Override
			public void onResponse(String string) {
				if (string == null) {
					Log.e(logTAG, "�������� null");
					return;	
				}
				JSONObject json_resp = null;
				JSONObject json_data = null;
				try {
					json_resp = new JSONObject(string);								
					System.out.println("string:" + string + "json:" + json_resp);
					if (json_resp != null && json_resp.get(Contents.JSON_KEY_RESULT) != null
						&& json_resp.get(Contents.JSON_KEY_RESULT).equals("OK")) {
						json_data = new JSONObject(json_resp.getString(Contents.JSON_KEY_DATA));
						int count = Integer.parseInt(json_data.getString("count"));// ��¼����
						startindex = startindex + count; // ��ȡ��¼ ��ʼindex
						for (int j = 0; j < count; j++) {
							Log.i(logTAG, "��¼��"+count);
							}
				   } else {
							Log.e(logTAG, "�������� fail");
							}
					} catch (JSONException e) {
						Log.e(logTAG, "���������쳣");
								}
							 }
					});
			}	
	
	// ɾ����¼
	private void HttpRemoveRecord(String recID) throws JSONException {
	   Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_8(recID));
	   OkHttpUtils.post().url(Contents.NET_NET).addParams("json", JsonManger.getJsonStr_8(recID))
	   .build().execute(new StringCallback() {
			@Override
			public void onError(Call arg0, Exception arg1) {
				Log.e(logTAG, "����ʧ�ܣ�" + arg1);
			}
			@Override
			public void onResponse(String string) {
				if (string == null) {
					Log.e(logTAG, "�������� null");
					return;	
				}
				JSONObject json_resp = null;
				JSONObject json_data = null;
				try {
					json_resp = new JSONObject(string);								
					System.out.println("string:" + string + "json:" + json_resp);
					if (json_resp != null && json_resp.get(Contents.JSON_KEY_RESULT) != null
						&& json_resp.get(Contents.JSON_KEY_RESULT).equals("OK")) {
						Log.i(logTAG, "ɾ����¼ ���� OK");
				   } else {
							Log.e(logTAG, "�������� fail");
							}
					} catch (JSONException e) {
						Log.e(logTAG, "���������쳣");
								}
							 }
					});
			}	
	
	
	
}
