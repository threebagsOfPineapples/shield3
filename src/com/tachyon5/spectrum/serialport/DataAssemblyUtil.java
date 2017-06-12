package com.tachyon5.spectrum.serialport;
/*
 * 数据组装
 */

public class DataAssemblyUtil {
		
	public static final byte MSG_MAGISPEC_ID_LSB = (byte) 0xF4;
	public static final byte MSG_MAGISPEC_ID_MSB = 0x52;
	
	public static byte[] getRawData(byte Cmd, byte Type, byte[]payload) {
		int mlength = (payload == null) ? 0 : payload.length;
		byte[] rawData = new byte[6 + mlength];
		rawData[0] = MSG_MAGISPEC_ID_MSB;
		rawData[1] = MSG_MAGISPEC_ID_LSB;		
		rawData[2] = Type;
		rawData[3] = Cmd;
		
		
		
		rawData[4] = (byte) ((mlength) & 0x00FF);
		rawData[5] = (byte) ((mlength >>> 8) & 0x00FF);
		for (int i = 0; i < mlength; i++) {
			rawData[6 + i] = payload[i];
		}
		
		for (int i=0;i<rawData.length;i++){
			System.out.println("rawdata["+i+"]="+rawData[i]);
		}
		System.out.println("消息组装完毕"+rawData.length);
		return rawData;
	}
	
	
}
