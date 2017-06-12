package com.tachyon5.spectrum.serialport;

/**
 * ���ڲ�����  ��ӡ������
 *
 * @author kang
 *
 */
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.tachyon5.spectrum.serialport.SerialPortUtil_Printer.OnDataReceiveListener_Printer;

public class SerialPortUtil_Printer {
	private String TAG = SerialPortUtil.class.getSimpleName();
	private SerialPort mSerialPort;
	private OutputStream mOutputStream;
	private InputStream mInputStream;
	private ReadThread mReadThread;
	private String path = "/dev/ttySAC2";
	private int baudrate = 115200; //9600
	private static SerialPortUtil portUtil;
	private OnDataReceiveListener_Printer onDataReceiveListener = null;
	private boolean isStop = false;

	public interface OnDataReceiveListener_Printer {
		public void onDataReceive(byte[] buffer, int size);
	}

	public void setOnDataReceiveListener_Printer(
			OnDataReceiveListener_Printer dataReceiveListener) {
		onDataReceiveListener = dataReceiveListener;
	}

	public static SerialPortUtil getInstance() {
		if (null == portUtil) {
			portUtil = new SerialPortUtil();
			portUtil.onCreate();
		}
		return portUtil;
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	public void onCreate() {
		try {
			mSerialPort = new SerialPort(new File(path), baudrate, 0);
			mOutputStream = mSerialPort.getOutputStream();
			mInputStream = mSerialPort.getInputStream();

			mReadThread = new ReadThread();
			isStop = false;
			mReadThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ָ�����(�ַ���)
	 * 
	 * @param cmd
	 * @return
	 */
	public boolean sendPrint(String cmd) {
		boolean result = true;

		String str = "��  ��  ��  Ʒ  ��  ��  �� " + "\r\n";
		String str1 = "��  ��  ��  ��" + "\r\n";
		String str2 = "��    �ţ�291992020" + "\r\n";
		String str3 = "��    �棺";
		String str4 = "��  ��" + "  ������"+"\r\n";
		String str4_1="          ��  ��" + "  ΢��"+"\r\n";
		String str4_2="          �Ȱ�ͪ" + "  �����ز�"+"\r\n";
		String str5 = "��    ����0023" + "\r\n";
		String str6 = "���ʱ�䣺2017/04/20 11:12:12" + "\r\n";
		String str7 = "�� �� �� �� �� �� �� �� �� ��" + "\r\n";

		try {
			if (mOutputStream != null) {

				byte[] byt = { 0x1b, 0x76, 0x01 };// ��ѯ״̬ ��ֽ 20 / ȱֽ24
				mOutputStream.write(byt);

				mOutputStream.write("\r\n".getBytes("GB2312"));

				// ---------�� �� �� Ʒ �� �� ��
				byte[] bb = { 0x1b, 0x61, 0x49 };// ���ж���
				mOutputStream.write(bb);

				byte[] aa = { 0x1d, 0x21, 0x00 };// ����
				mOutputStream.write(aa);

				mOutputStream.write(str.getBytes("GB2312"));

				// -----------�� �� �� ��
				byte[] aa1 = { 0x1d, 0x21, 0x01 };// ����
				mOutputStream.write(aa1);

				byte[] cc = { 0x1b, 0x33, 0x54 };// �м��
				mOutputStream.write(cc);

				mOutputStream.write(str1.getBytes("GB2312"));

				// -------------�� �ţ�91992020
				byte[] bb1 = { 0x1b, 0x61, 0x48 };// �����
				mOutputStream.write(bb1);

				byte[] aa2 = { 0x1d, 0x21, 0x00 };// ����
				mOutputStream.write(aa2);

				byte[] dd = { 0x1b, 0x42, 0x02 };// ����
				mOutputStream.write(dd);

				byte[] cc1 = { 0x1b, 0x33, 0x5e };// �м��
				mOutputStream.write(cc1);

				mOutputStream.write(str2.getBytes("GB2312"));
				// -------�����
				byte[] cc2 = { 0x1b, 0x33, 0x46 };// �м��
				mOutputStream.write(cc2);

				mOutputStream.write(str3.getBytes("GB2312"));

				byte[] ee = { 0x1b, 0x45, 0x01 };// ����Ӵ�
				mOutputStream.write(ee);

				mOutputStream.write(str4.getBytes("GB2312"));
				
				byte[] cc3 = { 0x1b, 0x33, 0x32 };// �м��
				mOutputStream.write(cc3);
				
				mOutputStream.write(str4_1.getBytes("GB2312"));
				mOutputStream.write(str4_2.getBytes("GB2312"));

				byte[] ee1 = { 0x1b, 0x45, 0x00 };// ���Ӵ�
				mOutputStream.write(ee1);

				// --------�豸��
				byte[] cc4 = { 0x1b, 0x33, 0x46 };// �м��
				mOutputStream.write(cc4);
				mOutputStream.write(str5.getBytes("GB2312"));
				// ---------���ʱ��
				byte[] cc5 = { 0x1b, 0x33, 0x3c };// �м��
				mOutputStream.write(cc5);
				mOutputStream.write(str6.getBytes("GB2312"));
				// ---------�����Ʊ��ܼ�ⱨ��
				byte[] cc6 = { 0x1b, 0x33, 0x78 };// �м��
				mOutputStream.write(cc6);

				mOutputStream.write(bb); // ���ж���

				mOutputStream.write(str7.getBytes("GB2312"));
			} else {
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public boolean sendBuffer(byte[] mBuffer, int size) {
		boolean result = true;
		if (size > mBuffer.length)
			size = mBuffer.length;
		byte[] mBufferTemp = new byte[size];
		System.arraycopy(mBuffer, 0, mBufferTemp, 0, size);
		try {
			if (mOutputStream != null) {
				mOutputStream.write(mBufferTemp);
				// mOutputStream.write("\n".getBytes());
			} else {
				result = false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private class ReadThread extends Thread {

		@Override
		public void run() {
			super.run();
			while (!isStop && !isInterrupted()) {
				int size;
				try {
					if (mInputStream == null)
						return;
					byte[] buffer = new byte[512];
					size = mInputStream.read(buffer);
					if (size > 0) {
						if (null != onDataReceiveListener) {
							onDataReceiveListener.onDataReceive(buffer, size);
						}
					}
					Thread.sleep(20);
				} catch (Exception e) {
					e.printStackTrace();
					return;
				}
			}
		}
	}

	/**
	 * �رմ���
	 */
	public void closeSerialPort() {
		isStop = true;
		if (mReadThread != null) {
			mReadThread.interrupt();
		}
		if (mSerialPort != null) {
			mSerialPort.close();
		}
	}

}
