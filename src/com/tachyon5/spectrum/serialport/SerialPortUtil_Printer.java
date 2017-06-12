package com.tachyon5.spectrum.serialport;

/**
 * 串口操作类  打印机串口
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
	 * 初始化串口信息
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
	 * 发送指令到串口(字符串)
	 * 
	 * @param cmd
	 * @return
	 */
	public boolean sendPrint(String cmd) {
		boolean result = true;

		String str = "光  盾  毒  品  检  测  仪 " + "\r\n";
		String str1 = "检  测  报  告" + "\r\n";
		String str2 = "编    号：291992020" + "\r\n";
		String str3 = "报    告：";
		String str4 = "吗  啡" + "  含量高"+"\r\n";
		String str4_1="          冰  毒" + "  微量"+"\r\n";
		String str4_2="          氯胺酮" + "  建议重测"+"\r\n";
		String str5 = "设    备：0023" + "\r\n";
		String str6 = "检测时间：2017/04/20 11:12:12" + "\r\n";
		String str7 = "请 妥 善 保 管 检 测 报 告 ！" + "\r\n";

		try {
			if (mOutputStream != null) {

				byte[] byt = { 0x1b, 0x76, 0x01 };// 查询状态 有纸 20 / 缺纸24
				mOutputStream.write(byt);

				mOutputStream.write("\r\n".getBytes("GB2312"));

				// ---------光 盾 毒 品 检 测 仪
				byte[] bb = { 0x1b, 0x61, 0x49 };// 居中对齐
				mOutputStream.write(bb);

				byte[] aa = { 0x1d, 0x21, 0x00 };// 字体
				mOutputStream.write(aa);

				mOutputStream.write(str.getBytes("GB2312"));

				// -----------检 测 报 告
				byte[] aa1 = { 0x1d, 0x21, 0x01 };// 字体
				mOutputStream.write(aa1);

				byte[] cc = { 0x1b, 0x33, 0x54 };// 行间距
				mOutputStream.write(cc);

				mOutputStream.write(str1.getBytes("GB2312"));

				// -------------编 号：91992020
				byte[] bb1 = { 0x1b, 0x61, 0x48 };// 左对齐
				mOutputStream.write(bb1);

				byte[] aa2 = { 0x1d, 0x21, 0x00 };// 字体
				mOutputStream.write(aa2);

				byte[] dd = { 0x1b, 0x42, 0x02 };// 左间距
				mOutputStream.write(dd);

				byte[] cc1 = { 0x1b, 0x33, 0x5e };// 行间距
				mOutputStream.write(cc1);

				mOutputStream.write(str2.getBytes("GB2312"));
				// -------检测结果
				byte[] cc2 = { 0x1b, 0x33, 0x46 };// 行间距
				mOutputStream.write(cc2);

				mOutputStream.write(str3.getBytes("GB2312"));

				byte[] ee = { 0x1b, 0x45, 0x01 };// 字体加粗
				mOutputStream.write(ee);

				mOutputStream.write(str4.getBytes("GB2312"));
				
				byte[] cc3 = { 0x1b, 0x33, 0x32 };// 行间距
				mOutputStream.write(cc3);
				
				mOutputStream.write(str4_1.getBytes("GB2312"));
				mOutputStream.write(str4_2.getBytes("GB2312"));

				byte[] ee1 = { 0x1b, 0x45, 0x00 };// 不加粗
				mOutputStream.write(ee1);

				// --------设备号
				byte[] cc4 = { 0x1b, 0x33, 0x46 };// 行间距
				mOutputStream.write(cc4);
				mOutputStream.write(str5.getBytes("GB2312"));
				// ---------检测时间
				byte[] cc5 = { 0x1b, 0x33, 0x3c };// 行间距
				mOutputStream.write(cc5);
				mOutputStream.write(str6.getBytes("GB2312"));
				// ---------请妥善保管检测报告
				byte[] cc6 = { 0x1b, 0x33, 0x78 };// 行间距
				mOutputStream.write(cc6);

				mOutputStream.write(bb); // 居中对齐

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
	 * 关闭串口
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
