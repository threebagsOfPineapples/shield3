package com.tachyon5.spectrum.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.tachyon5.spectrum.R;
import com.tachyon5.spectrum.serialport.SerialPortUtil.OnDataReceiveListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.SystemClock;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * ����ʱ��ѡ��ؼ� ʹ�÷����� private EditText inputDate;//��Ҫ���õ�����ʱ���ı��༭�� private String
 * initDateTime="2012��9��3�� 14:44",//��ʼ����ʱ��ֵ �ڵ���¼���ʹ�ã�
 * inputDate.setOnClickListener(new OnClickListener() {
 * 
 * @Override public void onClick(View v) { DateTimePickDialogUtil
 *           dateTimePicKDialog=new
 *           DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
 *           dateTimePicKDialog.dateTimePicKDialog(inputDate);
 * 
 *           } });
 * 
 * @author
 */
public class DateTimePickDialogUtil implements OnDateChangedListener, OnTimeChangedListener {
	private DatePicker datePicker;
	private TimePicker timePicker;
	private AlertDialog ad;
	private String dateTime;  //���õ� ���� �� ʱ��
	private String initDateTime;
	private Activity activity;
	private boolean bool;

	private OnTimeSetListener onTimeSetListener = null;

	/**
	 * ����ʱ�䵯��ѡ����캯��
	 * 
	 * @param activity
	 *            �����õĸ�activity
	 * @param initDateTime
	 *            ��ʼ����ʱ��ֵ����Ϊ�������ڵı��������ʱ���ʼֵ
	 * @param bool
	 *            true Ϊ ��ʾ�������� false Ϊ��ʾ���� ʱ��
	 */
	public DateTimePickDialogUtil(Activity activity, String initDateTime, boolean bool) {
		this.activity = activity;
		this.initDateTime = initDateTime;
		this.bool = bool;

	}

	//�ص��ӿ�
	public interface OnTimeSetListener {
		public void onTiemSet(String str);
	}

	public void setOnTimeSetListener(OnTimeSetListener onTimeSetListener) {
		this.onTimeSetListener = onTimeSetListener;
	}

	public void init(DatePicker datePicker, TimePicker timePicker) {
		Calendar calendar = Calendar.getInstance();
		if (!(null == initDateTime || "".equals(initDateTime))) {
			calendar = this.getCalendarByInintData(initDateTime);
		} else {
			initDateTime = calendar.get(Calendar.YEAR) + "��" + calendar.get(Calendar.MONTH) + "��"
					+ calendar.get(Calendar.DAY_OF_MONTH) + "�� " + calendar.get(Calendar.HOUR_OF_DAY) + ":"
					+ calendar.get(Calendar.MINUTE);
		}

		datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
				this);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
	}

	/**
	 * ��������ʱ��ѡ��򷽷�
	 * 
	 * @param inputDate
	 *            :Ϊ��Ҫ���õ�����ʱ���ı��༭��
	 * @return
	 */
	public AlertDialog dateTimePicKDialog(final EditText inputDate) {
		LinearLayout dateTimeLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.datetime_dialog,
				null);

		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
		timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
		if (bool) {
			timePicker.setVisibility(View.GONE);
			datePicker.setVisibility(View.VISIBLE);
		} else {
			timePicker.setVisibility(View.VISIBLE);
			datePicker.setVisibility(View.GONE);
		}
		init(datePicker, timePicker);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(this);

		ad = new AlertDialog.Builder(activity).setTitle(initDateTime).setView(dateTimeLayout)
				.setPositiveButton("����", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						if (bool) {
							String strDate=dateTime.split(" ")[0];// ����
							inputDate.setText(strDate); 
							setDate(Integer.parseInt(strDate.substring(0, 4)),
									Integer.parseInt(strDate.substring(5, 7)),
									Integer.parseInt(strDate.substring(8, 10)));
							
						} else {
							String strTime=dateTime.split(" ")[1];// ʱ��
							inputDate.setText(strTime); 
							setTime(Integer.parseInt(strTime.split(":")[0]),
									Integer.parseInt(strTime.split(":")[1]));
						}
						
						onTimeSetListener.onTiemSet(dateTime);
					}
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// inputDate.setText("");
					}
				}).show();

		onDateChanged(null, 0, 0, 0);
		return ad;
	}

	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		onDateChanged(null, 0, 0, 0);
	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		// �������ʵ��
		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
				timePicker.getCurrentHour(), timePicker.getCurrentMinute());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd�� HH:mm");

		dateTime = sdf.format(calendar.getTime());
		ad.setTitle(dateTime);
	}

	/**
	 * ʵ�ֽ���ʼ����ʱ��2012��07��02�� 16:45 ��ֳ��� �� �� ʱ �� ��,����ֵ��calendar
	 * 
	 * @param initDateTime
	 *            ��ʼ����ʱ��ֵ �ַ�����
	 * @return Calendar
	 */
	private Calendar getCalendarByInintData(String initDateTime) {
		Calendar calendar = Calendar.getInstance();

		// ����ʼ����ʱ��2012��07��02�� 16:45 ��ֳ��� �� �� ʱ �� ��
		String date = spliteString(initDateTime, "��", "index", "front"); // ����
		String time = spliteString(initDateTime, "��", "index", "back"); // ʱ��

		String yearStr = spliteString(date, "��", "index", "front"); // ���
		String monthAndDay = spliteString(date, "��", "index", "back"); // ����

		String monthStr = spliteString(monthAndDay, "��", "index", "front"); // ��
		String dayStr = spliteString(monthAndDay, "��", "index", "back"); // ��

		String hourStr = spliteString(time, ":", "index", "front"); // ʱ
		String minuteStr = spliteString(time, ":", "index", "back"); // ��

		int currentYear = Integer.valueOf(yearStr.trim()).intValue();
		int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
		int currentDay = Integer.valueOf(dayStr.trim()).intValue();
		int currentHour = Integer.valueOf(hourStr.trim()).intValue();
		int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();

		calendar.set(currentYear, currentMonth, currentDay, currentHour, currentMinute);
		return calendar;
	}

	/**
	 * ��ȡ�Ӵ�
	 * 
	 * @param srcStr
	 *            Դ��
	 * @param pattern
	 *            ƥ��ģʽ
	 * @param indexOrLast
	 * @param frontOrBack
	 * @return
	 */
	public static String spliteString(String srcStr, String pattern, String indexOrLast, String frontOrBack) {
		String result = "";
		int loc = -1;
		if (indexOrLast.equalsIgnoreCase("index")) {
			loc = srcStr.indexOf(pattern); // ȡ���ַ�����һ�γ��ֵ�λ��
		} else {
			loc = srcStr.lastIndexOf(pattern); // ���һ��ƥ�䴮��λ��
		}
		if (frontOrBack.equalsIgnoreCase("front")) {
			if (loc != -1)
				result = srcStr.substring(0, loc); // ��ȡ�Ӵ�
		} else {
			if (loc != -1)
				result = srcStr.substring(loc + 1, srcStr.length()); // ��ȡ�Ӵ�
		}
		return result;
	}

	
	    //����ϵͳ���� 
		private static void setDate(int year, int month, int day) {
	       Calendar c = Calendar.getInstance();
	       c.set(Calendar.YEAR, year);
	       c.set(Calendar.MONTH, month - 1<=0 ? 0:month-1);
	       c.set(Calendar.DAY_OF_MONTH, day);
	       long when = c.getTimeInMillis();
	       if (when / 1000 < Integer.MAX_VALUE) {
	           SystemClock.setCurrentTimeMillis(when);
	       }
	    }
	
	 //����ϵͳʱ��
	  private static void setTime(int hourOfDay, int minute) {
	        Calendar c = Calendar.getInstance();

	        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
	        c.set(Calendar.MINUTE, minute);
	        c.set(Calendar.SECOND, 0);
	        c.set(Calendar.MILLISECOND, 0);
	        long when = c.getTimeInMillis();

	        if (when / 1000 < Integer.MAX_VALUE) {
	            SystemClock.setCurrentTimeMillis(when);
	        }
	    }

	
	
}
