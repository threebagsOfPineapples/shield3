package com.tachyon5.spectrum.common;

import java.math.BigDecimal;

/**
 * 
 * @author kang
 *   
 */
public class Algorithm {
	

	/**
	 * 由 6个 color_sensor  6个beta limit  计算 rgb
	 * @param color_sensor_sum 
	 * @param beta_um
	 * @return rgb
	 */
	public static int[] getResult(double[][] color_sensor_sum,double[][] beta_sum ,double[] limit ){
		
		int[] result=new int[3];
		double[][] newRgbSum=new double[color_sensor_sum.length][3];
		
		for(int i=0;i<color_sensor_sum.length;i++){
			newRgbSum[i]=getNewRgb(color_sensor_sum[i],beta_sum);
		}
		
        
		if(getCompareLimit(newRgbSum[2],limit)){
			result[0]=getCompareLimit(newRgbSum[3],limit) ? 0 : 1;
		}else{
			result[0]=2;
		}
		
		if(getCompareLimit(newRgbSum[1],limit)){
			result[1]=getCompareLimit(newRgbSum[4],limit) ? 0 : 1;
		}else{
			result[1]=2;
		}
		
		if(getCompareLimit(newRgbSum[0],limit)){
			result[2]=getCompareLimit(newRgbSum[5],limit) ? 0 : 1;
		}else{
			result[2]=2;
		}
		
		return result;
	}
	/**
	 * 有6个colorSener[]  计算出新的6个 colorSener[]  
	 * @param colorSenerSum  采集的6个 colorSener[] 
	 * @param beta  
	 * @return
	 */
	public static double[][] getNewRgbArr(double[][] colorSenerSum,double[][] beta ){
		double[][] newRgbArr = new double[6][3];
		for(int i=0;i<newRgbArr.length;i++){
			double[] newRgb = getNewRgb(colorSenerSum[i],beta);
			newRgbArr[i]=newRgb;
		}
		return newRgbArr;
	}
	
	/**
	 * 由单个color_sensor beta 算出  newRgb
	 * @param color_sensor  
	 * @param beta
	 * @return newRGB
	 */
	private static double[] getNewRgb(double[] color_sensor, double[][] beta) {
		double[] newRGB = new double[color_sensor.length];
		for (int i = 0; i < color_sensor.length; i++) {
			newRGB[i] = color_sensor[i] * beta[1][i] + beta[0][i];
		  //BigDecimal   b   =   new   BigDecimal(color_sensor[i] * beta[1][i] + beta[0][i]);  
		  //newRGB[i]   =   b.setScale(0,   BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return newRGB;
	}

	/**
	 * 
	 * @param arg0 newRgb
	 * @param arg1 limit
	 * @return 对比单行的 newRgb和 limit的结果
	 */
	
	public static boolean getCompareLimit(double[] arg0,double[] arg1){
		boolean result=true;
		for(int i=0;i<arg0.length;i++){
			
			if(arg0[i] >= arg1[i]){
				result=false;
			}
		}
		return result;
	}
	
	/**
	 * 有 str 转换为 6组 colorsensor
	 * 
	 * @param str
	 *            数据字符串
	 * @return 6个ColorSensor
	 */
	public static double[][] getColorSensor(String str) {
		double[][] colorSensor = new double[6][3];
		String[] s = str.split(" ");
		int k = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				colorSensor[i][j] = Double.parseDouble(s[k]);
				k++;
			}
		}

		return colorSensor;
	}

	/**
	 * 截取光谱数据 前面 18个数字 为colorsener double[6][3] 的数组,
	 * 
	 * @param sample
	 *            采集光谱 字符串
	 */
	public static double[][] getColorSensorSum(String sample) {
		double[][] color_sensor_sum = new double[6][3];
		int[] colorSener = new int[18];
		String[] stringArr = sample.split(" ");
		for (int i = 0; i < 18; i++) {
			colorSener[i] = Integer.parseInt(stringArr[i]);
		}
		for (int j = 0; j < color_sensor_sum.length; j++) {
			for (int k = 0; k < 3; k++) {
				color_sensor_sum[j][k] = colorSener[j * 3 + k];
			}
		}
		return color_sensor_sum;
	}

	/**
	 * 得到 去掉 采集板数据的前 18 个colorsener, 得到sample
	 * 
	 * @param sample采集板sample数据
	 */
	public static String getSample(String sample) {
		String[] stringArr = sample.split(" ");
		String str = "";
		for (int i = 18; i < stringArr.length; i++) {
			if (i == stringArr.length - 1) {
				str += stringArr[i];
			} else {
				str += stringArr[i] + " ";
			}
		}
		return str;
	}

	/**
	 * 把字符串 转为 beta[][] 二维数组
	 * 
	 * @param betastr字符串
	 */
	public static double[][] getBeta(String betastr) {
		double[][] betaArr = new double[2][3];
		String[] stringArr = betastr.split(" ");
		betaArr[0][0] = Double.parseDouble(stringArr[0]);
		betaArr[0][1] = Double.parseDouble(stringArr[1]);
		betaArr[0][2] = Double.parseDouble(stringArr[2]);
		betaArr[1][0] = Double.parseDouble(stringArr[3]);
		betaArr[1][1] = Double.parseDouble(stringArr[4]);
		betaArr[1][2] = Double.parseDouble(stringArr[5]);

		return betaArr;
	}

	/**
	 * 把字符串 转为 rgblimit[] 二维数组
	 * 
	 * @param rgblimit字符串
	 */
	public static double[] getRgbLimit(String rgblimit) {
		String[] stringArr = rgblimit.split(" ");
		double[] RgbLimit = new double[stringArr.length];
		for (int i = 0; i < RgbLimit.length; i++) {
			RgbLimit[i] = Double.parseDouble(stringArr[i]);
		}
		return RgbLimit;
	}
	

}
