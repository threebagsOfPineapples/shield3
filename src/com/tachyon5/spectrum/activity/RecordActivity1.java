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
	
	private int startindex; // 获取记录 开始 index

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record1);
		initView();

		final SwipeRefreshView swipeRefreshView = (SwipeRefreshView) findViewById(R.id.srl);

		// 不能在onCreate中设置，这个表示当前是刷新状态，如果一进来就是刷新状态，SwipeRefreshLayout会屏蔽掉下拉事件
		// swipeRefreshLayout.setRefreshing(true);

		// 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
		// 设置下拉进度的背景颜色，默认就是白色的
		swipeRefreshView
				.setProgressBackgroundColorSchemeResource(android.R.color.white);
		// 设置下拉进度的主题颜色
		swipeRefreshView.setColorSchemeResources(R.color.colorAccent,
				R.color.colorPrimary, R.color.colorPrimaryDark);

		// 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
		swipeRefreshView
				.setOnRefreshListener(new SwipeRefreshView.OnRefreshListener() {
					@Override
					public void onRefresh() {
						// 开始刷新，设置当前为刷新状态
						// swipeRefreshLayout.setRefreshing(true);
						// 这里是主线程
						// 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
						// TODO 获取数据
						final Random random = new Random();
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								// mAdapter.notifyDataSetChanged();

								Toast.makeText(mContext, "刷新了一条数据",
										Toast.LENGTH_SHORT).show();

								// 加载完数据设置为不刷新状态，将下拉进度收起来
								swipeRefreshView.setRefreshing(false);
							}
						}, 1800);
					}
				});

		// 设置下拉加载更多
		swipeRefreshView
				.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {
					@Override
					public void onLoad() {
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								// 添加数据
							Toast.makeText(mContext, "加载了一条数据" + 1 + "条数据",Toast.LENGTH_SHORT).show();
								// 加载完数据设置为不加载状态，将加载进度收起来
								swipeRefreshView.setLoading(false);
							}
						}, 1800);
					}
				});
	 // 获取记录数据		
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
				// TODO 进行刷新数据操作
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
				item.setStr_result("匹配");
			}
			if (i % 3 == 1) {
				item.setStr_result("未知");
			}
			if (i % 3 == 2) {
				item.setStr_result("匹配");
			}
			item.setStr_type("测试结果：");
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


	// 获取记录
	private void HttpGetRecord() throws JSONException {

	   Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_6());
	   OkHttpUtils.post().url(Contents.NET_NET).addParams("json", JsonManger.getJsonStr_6())
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
					json_resp = new JSONObject(string);								
					System.out.println("string:" + string + "json:" + json_resp);
					if (json_resp != null && json_resp.get(Contents.JSON_KEY_RESULT) != null
						&& json_resp.get(Contents.JSON_KEY_RESULT).equals("OK")) {
						json_data = new JSONObject(json_resp.getString(Contents.JSON_KEY_DATA));
						int count = Integer.parseInt(json_data.getString("count"));// 记录数量
						startindex = startindex + count; // 获取记录 开始index
						for (int j = 0; j < count; j++) {
							Log.i(logTAG, "记录数"+count);
							}
				   } else {
							Log.e(logTAG, "解析数据 fail");
							}
					} catch (JSONException e) {
						Log.e(logTAG, "解析数据异常");
								}
							 }
					});
			}	
	
	// 删除记录
	private void HttpRemoveRecord(String recID) throws JSONException {
	   Log.i(logTAG, "jsondata"+JsonManger.getJsonStr_8(recID));
	   OkHttpUtils.post().url(Contents.NET_NET).addParams("json", JsonManger.getJsonStr_8(recID))
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
					json_resp = new JSONObject(string);								
					System.out.println("string:" + string + "json:" + json_resp);
					if (json_resp != null && json_resp.get(Contents.JSON_KEY_RESULT) != null
						&& json_resp.get(Contents.JSON_KEY_RESULT).equals("OK")) {
						Log.i(logTAG, "删除记录 返回 OK");
				   } else {
							Log.e(logTAG, "解析数据 fail");
							}
					} catch (JSONException e) {
						Log.e(logTAG, "解析数据异常");
								}
							 }
					});
			}	
	
	
	
}
