package com.tachyon5.spectrum.serialport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * ���ڲ�����  �ɼ��崮��
 *
 * @author kang
 *
 */
public class SerialPortUtil {
	 private String TAG = SerialPortUtil.class.getSimpleName();
	    private SerialPort mSerialPort;
	    private OutputStream mOutputStream;
	    private InputStream mInputStream;
	    private ReadThread mReadThread;
	    private String path = "/dev/ttySAC4";
	    private int baudrate = 115200;
	    private static SerialPortUtil portUtil;
	    private OnDataReceiveListener onDataReceiveListener = null;
	    private boolean isStop = false;

	    public interface OnDataReceiveListener {
	        public void onDataReceive(byte[] buffer, int size);
	    }

	    public void setOnDataReceiveListener(
	            OnDataReceiveListener dataReceiveListener) {
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
	            mSerialPort = new SerialPort(new File(path), baudrate,0);
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
	     * ����ָ�����
	     *
	     * @param cmd
	     * @return
	     */
	    public boolean sendCmds(String cmd) {
	        boolean result = true;
	        byte[] mBuffer = cmd.getBytes();
	      //ע�⣺�ҵ���Ŀ����Ҫ��ÿ�η��ͺ����\r\n����Ҹ�����Ŀ��Ŀ���޸ģ�Ҳ����ȥ����ֱ�ӷ���mBuffer
	        try {
	            if (mOutputStream != null) {
	                mOutputStream.write(mBuffer);
	            } else {
	                result = false;
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	            result = false;
	        }
	        return result;
	    }

	    public boolean sendBuffer(byte[] mBuffer,int size) {
	        boolean result = true;
	        //String tail = "\r\n";
	        if(size > mBuffer.length)size=mBuffer.length;
	        //byte[] tailBuffer = tail.getBytes();
	        byte[] mBufferTemp = new byte[size];
	        System.arraycopy(mBuffer, 0, mBufferTemp, 0, size);
	      //ע�⣺�ҵ���Ŀ����Ҫ��ÿ�η��ͺ����\r\n����Ҹ�����Ŀ��Ŀ���޸ģ�Ҳ����ȥ����ֱ�ӷ���mBuffer
	        try {
	            if (mOutputStream != null) {
	                mOutputStream.write(mBufferTemp);
	            //    mOutputStream.write("\n".getBytes());
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
