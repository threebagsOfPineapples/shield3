package com.tachyon5.spectrum.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * ����led ��
 * @author kang
 *
 */
public class LedUtils {
	 //����LED �Ŀ���
	 public static void ledSwitch( boolean bol) throws IOException {
			File file = new File("/proc/gpio_ctrl/rp_gpio_ctrl");
			FileOutputStream fos = new FileOutputStream(file);
			if(bol){
				fos.write("1".getBytes());
			}else{
				fos.write("2".getBytes());
			}
			
			fos.close();
		}
}
