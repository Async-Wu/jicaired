package com.chengyi.app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Date_util {


    private static String dateType = "yyyy-MM-dd";

    private static String curType = "yyyy-MM-dd hh:mm:ss";

    /**
     * @param currentType 当前格式
     * @param date        日期
     * @param destineType 目标格式
     * @return
     */
    public static String getDateString(String currentType, String date, String destineType) {

        SimpleDateFormat sdf = new SimpleDateFormat(currentType);
        Date d;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }

        sdf = new SimpleDateFormat(destineType);

        return sdf.format(d);
    }

    /**
     * @param currentType
     * @param date
     * @return
     * @link getDeteString()
     */
    public static String getDateString(String currentType, String date) {

        return getDateString(currentType, date, dateType);
    }

    /**
     * @param currentType
     * @param date
     * @param destineType
     * @return
     */
    public static String getDateString(String currentType, Date date, String destineType) {
        SimpleDateFormat sdf = new SimpleDateFormat(currentType);

        return getDateString(currentType, sdf.format(date), destineType);

    }

    /**
     * @param currentType
     * @param date
     * @return
     */
    public static String getDateString(String currentType, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(currentType);

        return getDateString(currentType, sdf.format(date), dateType);

    }

    /**
     *
     * @param date
     * @return
     */
    public static String getDateString(String date) {
        return getDateString(curType, date);
    }


}
