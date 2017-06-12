package com.tachyon5.spectrum.view;

import com.tachyon5.spectrum.R;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

public class SwipeRefreshView extends SwipeRefreshLayout{
	private final int mScaledTouchSlop;
    private final View mFooterView;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;

    /**
     * ���ڼ���״̬
     */
    private boolean isLoading;

    public SwipeRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // ���ײ����ز���
        mFooterView = View.inflate(context, R.layout.swiperefreshview_footer, null);

        // ��ʾ�ؼ��ƶ�����С���룬���ƶ��ľ�����������������϶��ؼ�
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        System.out.println("====" + mScaledTouchSlop);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // ��ȡListView,����ListView�Ĳ���λ��
        if (mListView == null) {
            // �ж������ж��ٸ�����
            if (getChildCount() > 0) {
                // �жϵ�һ�������ǲ���ListView
                if (getChildAt(0) instanceof ListView) {
                    // ����ListView����
                    mListView = (ListView) getChildAt(0);

                    // ����ListView�Ļ�������
                    setListViewOnScroll();
                }
            }
        }
    }

    /**
     * �ڷַ��¼���ʱ�����ӿؼ��Ĵ����¼�
     *
     * @param ev
     * @return
     */
    private float mDownY, mUpY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // �ƶ������
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // �ƶ��������ж�ʱ�����������ظ���
                if (canLoadMore()) {
                    // ��������
                    loadData();
                }

                break;
            case MotionEvent.ACTION_UP:
                // �ƶ����յ�
                mUpY = getY();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * �ж��Ƿ�������ظ�������
     *
     * @return
     */
    private boolean canLoadMore() {
        // 1. ������״̬
        boolean condition1 = (mDownY - mUpY) >= mScaledTouchSlop;
        if (condition1) {
            System.out.println("������״̬");
        }

        // 2. ��ǰҳ��ɼ���item�����һ����Ŀ
        boolean condition2 = false;
        if (mListView != null && mListView.getAdapter() != null) {
            condition2 = mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }

        if (condition2) {
            System.out.println("�����һ����Ŀ");
        }
        // 3. ���ڼ���״̬
        boolean condition3 = !isLoading;
        if (condition3) {
            System.out.println("�������ڼ���״̬");
        }
        return condition1 && condition2 && condition3;
    }

    /**
     * ����������ݵ��߼�
     */
    private void loadData() {
        System.out.println("��������...");
        if (mOnLoadListener != null) {
            // ���ü���״̬���ò�����ʾ����
            setLoading(true);
            mOnLoadListener.onLoad();
        }

    }

    /**
     * ���ü���״̬���Ƿ���ش���booleanֵ�����ж�
     *
     * @param loading
     */
    public void setLoading(boolean loading) {
        // �޸ĵ�ǰ��״̬
        isLoading = loading;
        if (isLoading) {
            // ��ʾ����
            mListView.addFooterView(mFooterView);
        } else {
            // ���ز���
            mListView.removeFooterView(mFooterView);

            // ���û���������
            mDownY = 0;
            mUpY = 0;
        }
    }


    /**
     * ����ListView�Ļ�������
     */
    private void setListViewOnScroll() {

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // �ƶ��������ж�ʱ�����������ظ���
                if (canLoadMore()) {
                    // ��������
                    loadData();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }


    /**
     * �������صĽӿڻص�
     */

    public interface OnLoadListener {
        void onLoad();
    }

    public void setOnLoadListener(OnLoadListener listener) {
        this.mOnLoadListener = listener;
    }
}
