package com.tachyon5.spectrum.utils;

import com.tachyon5.spectrum.common.Algorithm;

/*
 * 常来类，  保存多个类共同使用得常量
 * 
 */
public class Contents {

	// 通知消息 标记 ， 已读 未读
	public static final String NOTIFICATIONTAB_READ = "read";
	public static final String NOTIFICATIONTAB_NOREAD = "noread";

	// 联网 key值
	public static final String JSON_KEY_SESSIONID = "SESSIONID";
	public static final String JSON_KEY_OPENID = "OPENID";
	public static final String JSON_KEY_ACTION = "ACTION";
	public static final String JSON_KEY_TYPE = "TYPE";
	public static final String JSON_KEY_DATA = "DATA";
	public static final String JSON_KEY_RESULT = "RESULT";

	public static final String APPTYPE_ACTION = "APPTYPE_SHIELD3";
	public static final byte MSG_REQ = 0x00;
	public static final byte MSG_RES = 0x01;

	public static final short ACTION_CREATE_SESSION = 0x0001; // 创建会话 session
	public static final short ACTION_SHIELD_POLICE_RECOGNIZE_BLIND = 0x0503; // 毒品测试
	public static final short ACTION_SHIELD_POLICE_UPLOAD_DARKREF_DATE = 0x0604; // 上传darkref
	public static final short ACTION_SHIELD_POLICE_GET_RECORDS = 0x0501; // 获取记录
	public static final short ACTION_SHIELD_POLICE_DEL_RECORD = 0x0502; // 删除记录
	public static final short ACTION_SHIELD_POLICE_GET_MOLDER = 0x0507; // 获取模型
	public static final short ACTION_SHIELD_POLICE_RECOGNIZE_CHANGE_MODE = 0x0508; // 更换模型检测
	
	
	public static final short ACTION_START_GET_APP= 0x0607; //查询app版本号 
	public static final short ACTION_START_GET_FW_LATEST_VERSION= 0x0606; //查询固件版本号
	public static final short ACTION_START_GET_LOCALPARAM= 0x0603; //本地算法数据
	public static final short ACTION_START_GET_DETECTION= 0x0605; //检测
	public static final short ACTION_START_GET_RECORDS= 0x0601; //获取检测记录
	public static final short ACTION_START_GET_NOTIFY= 0x0602; //获取通知
	public static final short ACTION_START_DEL_RECORD=0x0608; //删除记录
	// 网络接口
	public static final String NET_CREATE_SESSON = "http://119.23.60.89/magispec-1.0/login.php"; // 创建会话地址
	public static final String NET_NET = "http://119.23.60.89/magispec-1.0/msg_central.php"; // 其他接口地址
	public static final String APP_VERSON="http://119.23.60.89/magispec-1.0/msg_central_nosession.php";   //获取app 版本信息
	public static final String url="http://119.23.60.89/download/application/shield3/Spectrum.apk";
	// 串口指令命令
	public static final byte MSG_CMD_DARK1_CALIB = (byte) 0x54; // 采集dark
	public static final byte MSG_CMD_REF1_CALIB = (byte) 0x55; // 采集 ref
	public static final byte MSG_CMD_SAMPLE_CALIB = (byte) 0xa0; // 光谱数据 sample
	public static final byte MSG_CMD_FW_VERSON_CALIB = (byte) 0x59; // 固件版本号
	// 和指令对应的 广播 action
	public static final String ACTION_DARK="action_dark" ;   //采集dark
	public static final String ACTION_REF="action_ref" ;    //采集ref
	public static final String ACTION_SAMPLE="action_sample" ;    //采集sample
	public static final String ACTION_FW_VERSON="action_fw_verson" ; //固件版本号
	//定位信息
	public static String Province="1";
	public static String City="2";
	public static String District="3";
	//创建会话信息
	public static String seesionID;
	public static String ifNewUser;
	public static String userID;
	public static String userNick;
	public static String notifyCount;
	// 全局变量
	//public static double[][] beta;  // 本地算法 beta
	//public static double[] rgblimit;  // 本地算法 limit
	public static double[][] beta =Algorithm.getBeta("-378.1111625 -336.6789994 -86.07526612 0.022662327 0.021676588 0.01210985");
	public static double[] rgblimit= Algorithm.getRgbLimit("172 166 187");
}
