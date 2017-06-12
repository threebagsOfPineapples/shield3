package com.tachyon5.spectrum.view;

import com.tachyon5.spectrum.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 
 * @author Administrator ��⶯�� view
 */
public class TestView extends View {

	private Context mContext;
	private TypedArray typedArray;
	// Բ���ı߾�
	private int pandding = 30;
	// �Զ�������
	private int viewColor; // Բ����ɫ
	private int lineColor; // ������ɫ
	private int circularWide; // Բ�����
	private int lineWide; // �м����߿��

	private Paint mPaint;

	private int lineMin;
	private int lineMax;
	private int line1;
	private int line2;
	private int line3;
	private int lineClearance; // ���ߵļ�϶

	private int circleX;
	private int circleY;

	private boolean isAnimal;

	private int rippleWidth; // �����ƿ��
	private int alpal; //������ɫ͸����
	private boolean isRipple;  //�Ƿ���ʾ����

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 1) {
				if (isAnimal) {
					if (line1 >= lineMax) {
						mHandler.sendEmptyMessageDelayed(2, 2);
					} else {
						line1 = line1 + 2;
						postInvalidate();
						mHandler.sendEmptyMessageDelayed(1, 2);
					}
				}
			} else if (msg.what == 2) {
				if (isAnimal) {
					if (line1 <= lineMin) {
						mHandler.sendEmptyMessageDelayed(1, 2);
					} else {
						line1 = line1 - 2;
						postInvalidate();
						mHandler.sendEmptyMessageDelayed(2, 2);
					}
				}
			} else if (msg.what == 3) {
				if (isAnimal) {
					if (line2 >= lineMax) {
						mHandler.sendEmptyMessageDelayed(4, 2);
					} else {
						line2 = line2 + 2;
						postInvalidate();
						mHandler.sendEmptyMessageDelayed(3, 2);
					}
				}
			} else if (msg.what == 4) {
				if (isAnimal) {
					if (line2 <= lineMin) {
						mHandler.sendEmptyMessageDelayed(3, 2);
					} else {
						line2 = line2 - 2;
						postInvalidate();
						mHandler.sendEmptyMessageDelayed(4, 2);
					}
				}
			} else if (msg.what == 5) {
				if (isAnimal) {
					if (line3 >= lineMax) {
						mHandler.sendEmptyMessageDelayed(6, 2);
					} else {
						line3 = line3 + 2;
						postInvalidate();
						mHandler.sendEmptyMessageDelayed(5, 2);
					}
				}
			} else if (msg.what == 6) {
				if (isAnimal) {
					if (line3 <= lineMin) {
						mHandler.sendEmptyMessageDelayed(5, 2);
					} else {
						line3 = line3 - 2;
						postInvalidate();
						mHandler.sendEmptyMessageDelayed(6, 2);
					}
				}
			} else if (msg.what == 7) {
				if (isAnimal) {
					if (rippleWidth >= dip2px(mContext, 16)) {
						rippleWidth = 0;
						alpal = 200;
						mHandler.sendEmptyMessageDelayed(7, 20);
					} else {
						rippleWidth++;
						alpal = alpal - 200 / dip2px(mContext, 16);
						mHandler.sendEmptyMessageDelayed(7, 20);
					}
				}
			} else {
				Log.i("handle3", lineMin + "/" + lineMax + "/" + line1);
			}

		}
	};

	public TestView(Context context) {
		super(context);
	}

	public TestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		typedArray = context.obtainStyledAttributes(attrs, R.styleable.myTestView);
		initAttrs();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);// �������
		mPaint.setStyle(Paint.Style.STROKE);

	}

	private void initAttrs() {
		viewColor = typedArray.getColor(R.styleable.myTestView_viewClolor, 0xFF4b536a);
		lineColor = typedArray.getColor(R.styleable.myTestView_lineClolor, Color.WHITE);
		circularWide = typedArray.getDimensionPixelOffset(R.styleable.myTestView_circularWide, 3);
		lineWide = typedArray.getDimensionPixelOffset(R.styleable.myTestView_lineWide, 5);
		typedArray.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));
		circleX = getWidth() / 2;
		circleY = getHeight() / 2;
		lineClearance = getWidth() / 16; // ���ߵļ��
		lineMin = getHeight() * 10 / 110; // line1 �ĳ��� ��ʼΪ���ֵ�1/10����
		lineMax = getHeight() * 10 / 30; // line3 �ĳ��� ��ʼΪ���ֵ�10/30����
		line1 = lineMin;
		line2 = getHeight() * 10 / 50;
		line3 = lineMax;
	}

	private int measure(int origin) {
		int result = 500;
		int specMode = MeasureSpec.getMode(origin);
		int specSize = MeasureSpec.getSize(origin);
		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isRipple){
			drawRipple(canvas);
		}
		drawCircular(canvas);
		drawLine(canvas);

	}

	/**
	 * �����ֻ��ķֱ��ʴ� dp �ĵ�λ ת��Ϊ px(����)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * ����Բ
	 */
	private void drawCircular(Canvas canvas) {
		mPaint.setColor(viewColor);
		mPaint.setStrokeWidth(circularWide);
		RectF oval1 = new RectF(dip2px(mContext, pandding), dip2px(mContext, pandding),
				getWidth() - dip2px(mContext, pandding),
				getHeight() - dip2px(mContext, pandding));
		canvas.drawArc(oval1, 0, 360, false, mPaint);
	}

	/**
	 * ���м�����
	 */
	private void drawLine(Canvas canvas) {

		mPaint.setColor(lineColor);
		mPaint.setStrokeWidth(lineWide);
		mPaint.setStrokeCap(Paint.Cap.ROUND);

		canvas.drawLine(circleX, circleY - line1 / 2, circleX, circleY + line1 / 2, mPaint);

		canvas.drawLine(circleX - lineClearance, circleY - line2 / 2, circleX - lineClearance, circleY + line2 / 2,
				mPaint);

		canvas.drawLine(circleX + lineClearance, circleY - line2 / 2, circleX + lineClearance, circleY + line2 / 2,
				mPaint);

		canvas.drawLine(circleX - lineClearance * 2, circleY - line3 / 2, circleX - lineClearance * 2,
				circleY + line3 / 2, mPaint);

		canvas.drawLine(circleX + lineClearance * 2, circleY - line3 / 2, circleX + lineClearance * 2,
				circleY + line3 / 2, mPaint);

		canvas.drawLine(circleX - lineClearance * 3, circleY - line2 / 2, circleX - lineClearance * 3,
				circleY + line2 / 2, mPaint);

		canvas.drawLine(circleX + lineClearance * 3, circleY - line2 / 2, circleX + lineClearance * 3,
				circleY + line2 / 2, mPaint);

		canvas.drawLine(circleX - lineClearance * 4, circleY - line1 / 2, circleX - lineClearance * 4,
				circleY + line1 / 2, mPaint);

		canvas.drawLine(circleX + lineClearance * 4, circleY - line1 / 2, circleX + lineClearance * 4,
				circleY + line1 / 2, mPaint);

	}

	/**
	 * ����Χ������
	 */
	private void drawRipple(Canvas canvas) {
		mPaint.setColor(0x330000FF);
		mPaint.setStrokeWidth(rippleWidth);
		mPaint.setAlpha(alpal);
		RectF oval2 = new RectF(dip2px(mContext, pandding) - rippleWidth / 2,
				dip2px(mContext, pandding) - rippleWidth / 2,
				getWidth() - (dip2px(mContext, pandding) - rippleWidth / 2),
				getHeight() - (dip2px(mContext, pandding) - rippleWidth / 2));
		canvas.drawArc(oval2, 0, 360, false, mPaint);

	}

	public void startAnimal() {
		if (!isAnimal){
		isAnimal = true;
		mHandler.sendEmptyMessageDelayed(1, 200);
		mHandler.sendEmptyMessageDelayed(3, 200);
		mHandler.sendEmptyMessageDelayed(5, 200);
		mHandler.sendEmptyMessageDelayed(7, 200);
		alpal = 200;
		}
	}

	public void stopAnimal() {
		isAnimal = false;
		line1 = lineMin;
		line2 = getHeight() * 10 / 50;
		line3 = lineMax;
		postInvalidate();
	}

}
