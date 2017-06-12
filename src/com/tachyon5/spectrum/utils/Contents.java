package com.tachyon5.spectrum.utils;

import com.tachyon5.spectrum.common.Algorithm;

/*
 * �����࣬  �������๲ͬʹ�õó���
 * 
 */
public class Contents {

	// ֪ͨ��Ϣ ��� �� �Ѷ� δ��
	public static final String NOTIFICATIONTAB_READ = "read";
	public static final String NOTIFICATIONTAB_NOREAD = "noread";

	// ���� keyֵ
	public static final String JSON_KEY_SESSIONID = "SESSIONID";
	public static final String JSON_KEY_OPENID = "OPENID";
	public static final String JSON_KEY_ACTION = "ACTION";
	public static final String JSON_KEY_TYPE = "TYPE";
	public static final String JSON_KEY_DATA = "DATA";
	public static final String JSON_KEY_RESULT = "RESULT";

	public static final String APPTYPE_ACTION = "APPTYPE_SHIELD3";
	public static final byte MSG_REQ = 0x00;
	public static final byte MSG_RES = 0x01;

	public static final short ACTION_CREATE_SESSION = 0x0001; // �����Ự session
	public static final short ACTION_SHIELD_POLICE_RECOGNIZE_BLIND = 0x0503; // ��Ʒ����
	public static final short ACTION_SHIELD_POLICE_UPLOAD_DARKREF_DATE = 0x0604; // �ϴ�darkref
	public static final short ACTION_SHIELD_POLICE_GET_RECORDS = 0x0501; // ��ȡ��¼
	public static final short ACTION_SHIELD_POLICE_DEL_RECORD = 0x0502; // ɾ����¼
	public static final short ACTION_SHIELD_POLICE_GET_MOLDER = 0x0507; // ��ȡģ��
	public static final short ACTION_SHIELD_POLICE_RECOGNIZE_CHANGE_MODE = 0x0508; // ����ģ�ͼ��
	
	
	public static final short ACTION_START_GET_APP= 0x0607; //��ѯapp�汾�� 
	public static final short ACTION_START_GET_FW_LATEST_VERSION= 0x0606; //��ѯ�̼��汾��
	public static final short ACTION_START_GET_LOCALPARAM= 0x0603; //�����㷨����
	public static final short ACTION_START_GET_DETECTION= 0x0605; //���
	public static final short ACTION_START_GET_RECORDS= 0x0601; //��ȡ����¼
	public static final short ACTION_START_GET_NOTIFY= 0x0602; //��ȡ֪ͨ
	public static final short ACTION_START_DEL_RECORD=0x0608; //ɾ����¼
	// ����ӿ�
	public static final String NET_CREATE_SESSON = "http://119.23.60.89/magispec-1.0/login.php"; // �����Ự��ַ
	public static final String NET_NET = "http://119.23.60.89/magispec-1.0/msg_central.php"; // �����ӿڵ�ַ
	public static final String APP_VERSON="http://119.23.60.89/magispec-1.0/msg_central_nosession.php";   //��ȡapp �汾��Ϣ
	public static final String url="http://119.23.60.89/download/application/shield3/Spectrum.apk";
	// ����ָ������
	public static final byte MSG_CMD_DARK1_CALIB = (byte) 0x54; // �ɼ�dark
	public static final byte MSG_CMD_REF1_CALIB = (byte) 0x55; // �ɼ� ref
	public static final byte MSG_CMD_SAMPLE_CALIB = (byte) 0xa0; // �������� sample
	public static final byte MSG_CMD_FW_VERSON_CALIB = (byte) 0x59; // �̼��汾��
	// ��ָ���Ӧ�� �㲥 action
	public static final String ACTION_DARK="action_dark" ;   //�ɼ�dark
	public static final String ACTION_REF="action_ref" ;    //�ɼ�ref
	public static final String ACTION_SAMPLE="action_sample" ;    //�ɼ�sample
	public static final String ACTION_FW_VERSON="action_fw_verson" ; //�̼��汾��
	//��λ��Ϣ
	public static String Province="1";
	public static String City="2";
	public static String District="3";
	//�����Ự��Ϣ
	public static String seesionID;
	public static String ifNewUser;
	public static String userID;
	public static String userNick;
	public static String notifyCount;
	// ȫ�ֱ���
	//public static double[][] beta;  // �����㷨 beta
	//public static double[] rgblimit;  // �����㷨 limit
	public static double[][] beta =Algorithm.getBeta("-378.1111625 -336.6789994 -86.07526612 0.022662327 0.021676588 0.01210985");
	public static double[] rgblimit= Algorithm.getRgbLimit("172 166 187");
}
