package com.chengyi.app.util;

import android.text.TextUtils;

/**
 * Created by lishangfan on 2016/11/23.
 * <p>
 * <p>
 * 遍历  字符串的数字位  进行 转换
 */

public class Num2Str {


    private static String ONE = "一";
    private static String TWO = "二";
    private static String THREE = "三";
    private static String FOUR = "四";
    private static String FIVE = "五";
    private static String SIX = "六";
    private static String SEVEN = "七";
    private static String EIGHT = "八";
    private static String NINE = "九";

    private static String ONE_NUM = "1";
    private static String TWO_NUM = "2";
    private static String THREE_NUM = "3";
    private static String FOUR_NUM = "4";
    private static String FIVE_NUM = "5";
    private static String SIX_NUM = "6";
    private static String SEVEN_NUM = "7";
    private static String EIGHT_NUM = "8";
    private static String NINE_NUM = "9";

    public static String num2Str(String s) {
        if (TextUtils.isEmpty(s))
            return "";
        else
            return s.replaceAll(ONE_NUM, ONE).replaceAll(TWO_NUM, TWO).replaceAll(THREE_NUM, THREE).replaceAll(FOUR_NUM, FOUR).replaceAll(FIVE_NUM, FIVE).
                    replaceAll(SIX_NUM, SIX).replaceAll(SEVEN_NUM, SEVEN).replaceAll(EIGHT_NUM, EIGHT).replaceAll(NINE_NUM, NINE);
    }


}
