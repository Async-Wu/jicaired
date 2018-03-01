package com.chengyi.app.net.api;

import android.content.Context;
import com.chengyi.app.listener.INet;
import com.chengyi.app.model.param.URLSuffix;

import java.util.Map;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 * <p>
 * api       retrofit2
 */

public class HomeApi extends BaseApi {
    //lottery/lottery_list.htm
    public static void LOTTERY_LIST(Context context, INet iNet, int tag, Class clazz, Map<String, String> map) {
        fetch("lottery/lottery_list.htm", context, iNet, tag, clazz, map, false);

    }///

    public static void QUERY_BALANCE(Context context, INet iNet, int tag, Class clazz, Map<String, String> map) {
        fetch("user/query_balance.htm", context, iNet, tag, clazz, map, false);

    }

    public static void REGISTER(Context context, INet iNet, int tag, Class clazz, Map<String, String> map) {
        fetch("/user/register.htm", context, iNet, tag, clazz, map, false);
    }// /user/bind_mobile.htm

    public static void BIND_MOBILE(Context context, INet iNet, int tag, Class clazz, Map<String, String> map) {
        fetch("/user/bind_mobile.htm", context, iNet, tag, clazz, map, false);
    }//

    public static void BUY_2_BUy(Context context, INet iNet, int tag, Class clazz, Map<String, String> map) {
        fetch("/lottery/scheme_detail.htm", context, iNet, tag, clazz, map, false);
    }//


    public static void ISSUE_NOTIFY_ALL(Context context, INet iNet, int tag, Class clazz, Map<String, String> map) {
        fetch("/lottery/issue_notify_all.htm", context, iNet, tag, clazz, map, false);
    }//

    public static void APP_NEWVERSION(Context context, INet iNet, int tag, Class clazz, Map<String, String> map) {
        fetch("/user/app_newVersion.htm", context, iNet, tag, clazz, map, false);
    }

    public static void SIX_CHANG(Context context, INet iNet, int tag, Class clazz, Map<String, String> map) {
        fetch("/lottery/toto6.htm", context, iNet, tag, clazz, map, false);
    }

    public static void SUBMIT(Context context, INet iNet, int tag, Class clazz, Map<String, String> map) {
        fetch(URLSuffix.BUY_LOTTERY, context, iNet, tag, clazz, map, false);
    }

    public static void FOLLOW_ME(Context context, INet iNet, int tag, Class clazz, Map<String, String> map) {
        fetch(URLSuffix.FOLLOW_ME, context, iNet, tag, clazz, map, false);
    }
}
