package com.tachyon5.spectrum.view;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

/*
 * ��� ��� view  ��ʾ  �����ƷͼƬ   ����  ����  ��  
 */
public class CheckResultView extends View {
	private int width;  //���
	private int radius; // �뾶
	
	private int currentValue=60;  //���Ȱٷֱ�	
	private String strName = "��Ʒ";
	private String strPurity = "���ȣ�60%";
	private boolean showText;
	
	private Paint mPaint = new Paint();

	public CheckResultView(Context context) {
		super(context);
	}

	public CheckResultView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CheckResultView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		width = measure(Math.min(widthMeasureSpec, heightMeasureSpec));
		
		setMeasuredDimension(width, width);
		
		radius = width/2;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// ����Բ
		drawCircle(canvas);
		
		//������ 
		drawProgress(canvas);			
		
		//�� ���� 	
		if (showText){
		       drawText(canvas);
		    }
	}
	
	private void drawCircle (Canvas canvas){
		mPaint.setAntiAlias(true);
		mPaint.setStrokeWidth(12);
		mPaint.setColor(0xff4b536c);
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.drawCircle(width / 2, width / 2, radius - 12, mPaint);
	}
	
	private void drawProgress(Canvas canvas){
		mPaint.setStrokeWidth(20);
		mPaint.setColor(0xff0ad4ff); 
		mPaint.setStrokeCap(Paint.Cap.ROUND); 
		RectF oval = new RectF(12, 12, 2*radius-12,2*radius-12);   	                 		 
		canvas.drawArc( oval, 270,(float) (3.6*currentValue), false, mPaint);
	}
	
	private void drawText(Canvas canvas){
		mPaint.setStrokeWidth(3);
		mPaint.setTextSize(radius/6);  //���ݰ뾶��С ��̬���� �����С
		mPaint.setColor(Color.WHITE);
		mPaint.setTextAlign(Align.LEFT);
		mPaint.setStyle(Paint.Style.FILL);
		Rect bounds = new Rect();
		mPaint.getTextBounds(strName, 0, strName.length(), bounds);
		canvas.drawText(strName, radius - bounds.width() / 2, radius+radius-bounds.height()/2-radius/3, mPaint);
		
		mPaint.setTextSize(radius/9); 
		mPaint.getTextBounds(strPurity, 0, strPurity.length(), bounds);
		canvas.drawText(strPurity, radius - bounds.width() / 2, radius+radius-bounds.height()/2-radius/5, mPaint);
	}
	
	   //���ö�Ʒ����  �� ���� 
    public void setResult(String name,String Purity){
    	strName=name;
    	if(Purity.equals("0")){
    		strPurity="";
    	}else{
    		strPurity="���ȣ�"+Purity+"%";
    	}
    	
    	currentValue=Integer.parseInt(Purity);
    	invalidate();
    	
    }

	private int measure(int origin) {
		int result = 300;
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

}
