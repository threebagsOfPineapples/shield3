package com.tachyon5.spectrum.serialport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * 串口操作类  采集板串口
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
	     * 初始化串口信息
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
	     * 发送指令到串口
	     *
	     * @param cmd
	     * @return
	     */
	    public boolean sendCmds(String cmd) {
	        boolean result = true;
	        byte[] mBuffer = cmd.getBytes();
	      //注意：我得项目中需要在每次发送后面加\r\n，大家根据项目项目做修改，也可以去掉，直接发送mBuffer
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
	      //注意：我得项目中需要在每次发送后面加\r\n，大家根据项目项目做修改，也可以去掉，直接发送mBuffer
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
