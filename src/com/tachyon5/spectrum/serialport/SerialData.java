package com.tachyon5.spectrum.serialport;

import com.tachyon5.spectrum.activity.HomeActivity;
import com.tachyon5.spectrum.utils.Contents;

import android.util.Log;

/**
 * �������ݽ�����
 * @author Administrator
 *
 */
public class SerialData {

	public static final int MSG_STATE_IDLE = 0;
	public static final int MSG_STATE_RECV_HDR = 1;
	public static final int MSG_STATE_RECV_PAYLOAD = 2;
	public static final int MSG_STATE_PROCESSING = 3;

	private int mState = MSG_STATE_IDLE;

	public static final byte MSG_MAGISPEC_ID_LSB = (byte) 0xF4;
	public static final byte MSG_MAGISPEC_ID_MSB = 0x52;

	private byte mCmd;
	private byte mType;
	private int mPayloadLen;
	private byte[] mPayload;

	private int mPos = 0;

	// �������� ��װ
	public int assembleMsgByByte(byte[] data, int size) {
		
	//	Log.e("kk", byteToHex(data,size," "));
	
		if (mState == MSG_STATE_IDLE) {
			if (data[0] == MSG_MAGISPEC_ID_MSB && data[1] == MSG_MAGISPEC_ID_LSB) {
				mType = data[2];
				mCmd = data[3];
				mPayloadLen = data[4]& 0xff;
				mPayloadLen |= ((short) data[5] << 8) & 0xFF00;
				
				Log.e(">>data[0]", data[0]+"");
				Log.e(">>data[1]", data[1]+"");
				Log.e(">>mType", mType+"");
				Log.e(">>cmd", mCmd+"");
				Log.e(">>len", mPayloadLen+"");
				System.out.println(">>cmd"+mCmd+"//"+">>len"+mPayloadLen);
				mPayload = new byte[mPayloadLen];

				for (int i = 6; i < size; i++) {
					mPayload[mPos] = data[i];
					mPos++;
					System.out.println("mPos" + mPos);
				}

				if (mPos < mPayload.length) {
					mState = MSG_STATE_RECV_PAYLOAD;

					System.out.println("��ִ����MSG����MSG_STATE_RECV_PAYLOAD" + mState + "mpos" + mPos);
				} else {
					mState = MSG_STATE_PROCESSING;
					System.out.println("��ִ����MSG����MSG_STATE_PROCESSING" + ",pos" + mPos);
				}

			}
		} else if (mState == MSG_STATE_RECV_PAYLOAD) {

			for (int i = 0; i < size; i++) {
				if (mPos < mPayloadLen) {
					mPayload[mPos] = data[i];
					mPos++;
					if (mPos == mPayloadLen) {
						System.out.println("���ռ����˰�");
						mState = MSG_STATE_PROCESSING;
					}
				} else {
					System.out.println("�ռ�δ���");
				}
			}

		} else if (mState == MSG_STATE_PROCESSING) {
			return mState;
		} else {

		}

		return mState;
	}

	// ���� �������� ��������
	public void dataProcessing() {
		clear();
		
		// ��ӡ ���ڽ��յ� ����
		/*int[] reff = new int[mPayloadLen / 2];
		for (int i = 0; i < (mPayloadLen / 2); i++) {
			reff[i] = mPayload[i * 2] & 0xff;
			reff[i] |= (int) (mPayload[i * 2 + 1] & 0xff) << 8;
			Log.i("LOGLOG--", ""+reff[i]);
		}*/
		
		switch (mCmd) {

		case Contents.MSG_CMD_REF1_CALIB:  //ref����
			int[] ref1 = new int[mPayloadLen / 2];
			for (int i = 0; i < (mPayloadLen / 2); i++) {
				ref1[i] = mPayload[i * 2] & 0xff;
				ref1[i] |= (int) (mPayload[i * 2 + 1] & 0xff) << 8;
			}
			HomeActivity.refData = ref1;
			break;
		case Contents.MSG_CMD_DARK1_CALIB: //dark����
			int[] dark1 = new int[mPayloadLen / 2];
			for (int i = 0; i < (mPayloadLen / 2); i++) {
				dark1[i] = mPayload[i * 2] & 0xff;
				dark1[i] |= (int) (mPayload[i * 2 + 1] & 0xff) << 8;
			}
			HomeActivity.dardData = dark1;
			break;
		case Contents.MSG_CMD_SAMPLE_CALIB: //���� sample ����
			int[] sample = new int[mPayloadLen / 2];
			for (int i = 0; i < (mPayloadLen / 2); i++) {
				sample[i] = mPayload[i * 2] & 0xff;
				sample[i] |= (int) (mPayload[i * 2 + 1] & 0xff) << 8;
			}
			HomeActivity.sampleData = sample;
			break;
		case Contents.MSG_CMD_FW_VERSON_CALIB: // �̼��汾��
			String temp="";		
			for (int i = mPayload.length-1; i > 0; i--) {
				temp+=mPayload[i]+".";
			}
			temp+=mPayload[0];
			HomeActivity.fw_verson=temp;
			break;
		default:
			
			break;
		}

	}

	// ��װ������� ��ʼ��״̬
	public void clear() {
		mState = MSG_STATE_IDLE;
		mPos = 0;
		//mPayload=null;
	}

	// -------------------------------------------------------

	public byte getmCmd() {
		return mCmd;
	}

	public void setmCmd(byte mCmd) {
		this.mCmd = mCmd;
	}

	public byte getmType() {
		return mType;
	}

	public void setmType(byte mType) {
		this.mType = mType;
	}

	public int getmPayloadLen() {
		return mPayloadLen;
	}

	public void setmPayloadLen(int mPayloadLen) {
		this.mPayloadLen = mPayloadLen;
	}

	public byte[] getmPayload() {
		return mPayload;
	}

	public void setmPayload(byte[] mPayload) {
		this.mPayload = mPayload;
	}
	
	
	/**
	 *  ��byte ת�����ַ���
	 * @param sbyte byte����
	 * @param len  byte[]����ĳ���
	 * @param insterStr  �ַ��� �����
	 * @return
	 */
	public static String byteToHex(byte[] sbyte,int len,String insterStr){
		String str = "";
		for(int i=0;i<len;i++){
			str += (sbyte[i]&0xff)<16?"0"+Integer.toHexString(sbyte[i]&0xff).toUpperCase() + insterStr:Integer.toHexString(sbyte[i]&0xff).toUpperCase() + insterStr;
		}
		return str;
		
	}
}
