package com.chengyi.app.util;

import java.security.MessageDigest;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  StrMD5Encryption {

	public static String getMD5(String instr) {
		String s = null;
		// 用来将字节转换成 16 进制表示的字�?
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest md = MessageDigest
					.getInstance("MD5");
			md.update(instr.getBytes());
			byte tmp[] = md.digest(); // MD5 的计算结果是�?�� 128 位的长整数，
										// 用字节表示就�?16 个字�?
			char str[] = new char[16 * 2]; // 每个字节�?16 进制表示的话，使用两个字符，
											// �?��表示�?16 进制�?�� 32 个字�?
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第�?��字节�?��，对 MD5 的每�?��字节
											// 转换�?16 进制字符的转�?
				byte byte0 = tmp[i]; // 取第 i 个字�?
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中�?4 位的数字转换,
															// >>>
															// 为�?辑右移，将符号位�?��右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中�?4 位的数字转换
			}
			s = new String(str).toUpperCase(); // 换后的结果转换为字符�?

		} catch (Exception e) {

		}
		return s;
	}
	
	public static String getDeviceId(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  

            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString().substring(8, 24);  
  
    }  
	 public static String convertMD5(String inStr){  
		  
	        char[] a = inStr.toCharArray();  
	        for (int i = 0; i < a.length; i++){  
	            a[i] = (char) (a[i] ^ '@');  
	        }  
	        String s = new String(a);  
	        return s;  
	  
	    }  
}
