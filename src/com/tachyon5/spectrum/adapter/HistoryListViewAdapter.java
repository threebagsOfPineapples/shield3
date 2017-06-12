package com.tachyon5.spectrum.adapter;

import java.util.List;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.bean.HistoryListItemObject;
import com.tachyon5.spectrum.view.CustomSwipeListView;
import com.tachyon5.spectrum.view.SwipeItemView;
import com.tachyon5.spectrum.view.SwipeItemView.OnSlideListener;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HistoryListViewAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<HistoryListItemObject> mMessageItems;
	private Context context;
	private SwipeItemView mLastSlideViewWithStatusOn;
	private ViewHolder holder;
	
	private int click=-1;
	private int showitem=-2;
	private boolean isDetalilShow;

	public HistoryListViewAdapter(Context context,
			List<HistoryListItemObject> mMessageItems) {
		mInflater = LayoutInflater.from(context);
		this.mMessageItems = mMessageItems;
		this.context = context;
	}

	@Override
	public int getCount() {
		return mMessageItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mMessageItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		SwipeItemView slideView = (SwipeItemView) convertView;
		if (slideView == null) {
			View itemView = mInflater.inflate(R.layout.item_history,null);
					
			slideView = new SwipeItemView(context);
			slideView.setContentView(itemView);

			holder = new ViewHolder(slideView);
			slideView.setOnSlideListener(new OnSlideListener() {

				@Override
				public void onSlide(View view, int status) {
					// TODO Auto-generated method stub
					if (mLastSlideViewWithStatusOn != null
							&& mLastSlideViewWithStatusOn != view) {
						mLastSlideViewWithStatusOn.shrink();
					}

					if (status == SLIDE_STATUS_ON) {
						mLastSlideViewWithStatusOn = (SwipeItemView) view;
					}
				}
			});
			slideView.setTag(holder);
		} else {
			holder = (ViewHolder) slideView.getTag();
		}
		HistoryListItemObject item = mMessageItems.get(position);
		// item.slideView = slideView;
		if (CustomSwipeListView.mFocusedItemView != null) {
			CustomSwipeListView.mFocusedItemView.shrink();
		}
		if(position<9){
			holder.tv_number.setText("0"+(position+1));
		}else{
			holder.tv_number.setText(""+(position+1));
		}
		if(item.getStr_result().equals("Æ¥Åä")){
			holder.tv_result.setTextColor(0xff03f241);
		}else{
			holder.tv_result.setTextColor(0xfffff607);
		}
		holder.tv_result.setText(item.getStr_result());
		holder.tv_type.setText(item.getStr_type());
		holder.tv_time.setText(item.getStr_time());
		/*holder.tv_time.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "Ê±¼ä",Toast.LENGTH_SHORT).show();
			}
		});*/
		holder.deleteHolder.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mMessageItems.remove(position);
				Toast.makeText(context, String.valueOf(position),Toast.LENGTH_SHORT).show();
						
				notifyDataSetChanged();
			}
		});
		if(isDetalilShow){
			if(click==position ){
				holder.ll_details.setVisibility(View.VISIBLE);
				showitem=click;
			}else{
				holder.ll_details.setVisibility(View.GONE);
			}
		}else{
			holder.ll_details.setVisibility(View.GONE);
		}
				
		holder.ll_ll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				    click=position;
					isDetalilShow=(click==position) ? !isDetalilShow : isDetalilShow;
				    notifyDataSetChanged();
			}
		});
		
		return slideView;
	}
	private static class ViewHolder {

		public TextView tv_number;
		public TextView tv_result;
		public TextView tv_type;
		public TextView tv_time;
		public ViewGroup deleteHolder;
		public LinearLayout ll_details;
		public LinearLayout ll_ll;

		ViewHolder(View view) {
			tv_number = (TextView) view.findViewById(R.id.tv_number);
			tv_result = (TextView) view.findViewById(R.id.tv_result);
			tv_type = (TextView) view.findViewById(R.id.tv_type);
			tv_time = (TextView) view.findViewById(R.id.tv_time);
			deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
			ll_details = (LinearLayout) view.findViewById(R.id.ll_details);
			ll_ll = (LinearLayout) view.findViewById(R.id.ll_ll);
		}
	}
}