package com.chengyi.app.util;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.Random;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class Ecrpt {
    private static Random randGen = null;
    private static char[] numbersAndLetters = null;
    // ����
    private static byte[] iv1 = {0x12, 0x34, 0x56, 0x78, (byte) 0x90,
            (byte) 0xAB, 0x12, 0x78};

    // ����
    public static String Encrypt(String json, String sKey) {

        // 3DESECB解密,key必须是长度大于等于 3*8 = 24 位
        if (sKey == null) {

            return null;
        }
        // 判断Key是否为24位
        if (sKey.length() != 24) {

            return null;
        }
        DESedeKeySpec dks;
        try {
            dks = new DESedeKeySpec(sKey.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
            IvParameterSpec iv = new IvParameterSpec(iv1);
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
            byte[] b = cipher.doFinal(json.getBytes());
            return Base64.encodeToString(b, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }


    }

    // ����
    public static String Decrypt(String json, String sKey) {

        try {

            // 判断Key是否正确
            if (sKey == null) {

                return null;
            }
            // 判断Key是否为24位
            if (sKey.length() != 24) {

                return null;
            }
            // --通过base64,将字符串转成byte数组
            byte[] bytesrc = Base64.decode(json, Base64.NO_WRAP);
            // --解密的key
            DESedeKeySpec dks = new DESedeKeySpec(sKey.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("DESede");
            SecretKey securekey = keyFactory.generateSecret(dks);
            IvParameterSpec iv = new IvParameterSpec(iv1);
            // --Chipher对象解密
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte, "utf-8");
        } catch (Exception ex) {

            return "";
        }


    }

    public static final String randomString() {
        int key_length = 24;// ������
        if (randGen == null) {
            randGen = new Random();
            numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
                    + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
        }
        char[] randBuffer = new char[key_length];
        for (int i = 0; i < randBuffer.length; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
        }
        return new String(randBuffer);
    }
}
