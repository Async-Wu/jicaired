package com.chengyi.app.util.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  NetUtil {


    private static boolean isWifiEnable(Context ctx) {
        WifiManager wifi = (WifiManager) ctx
                .getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }
    /**
     * 检测网络连接是否可用
     *
     * @param ctx
     * @return true 可用; false 不可用
     */
    public static boolean isNetworkAvailable(Context ctx) {
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (isWifiEnable(ctx)) {
                return true;
            }
            if (cm == null) {
                return false;
            }
            // 取当前默认的连接信息
            NetworkInfo netinfo = cm.getActiveNetworkInfo();
            if (netinfo != null && netinfo.isConnected()) {
                return true;
            }
            NetworkInfo.State mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState();

            if (mobile == NetworkInfo.State.CONNECTED) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }



}
