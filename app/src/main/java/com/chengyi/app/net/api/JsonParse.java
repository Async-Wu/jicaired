package com.chengyi.app.net.api;

import android.content.Context;
import android.os.SystemClock;
import com.chengyi.app.listener.INet;
import com.chengyi.app.util.L;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 * <p>
 * A
 */
public class JsonParse {
    private static long ss;

    // A.class
    public static void parse(final INet iNet, final int tag, final Class clazz, String resp, boolean flag, Context context) {
        L.d("res", resp);
        if (resp == null) return;
        if (clazz == null) {
            iNet.response(tag, resp);
            return;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(resp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject == null)
            return;

        if (flag) {//list
            final String s = jsonObject.optString("list");
            ss = SystemClock.currentThreadTimeMillis();
            try {
                iNet.response(tag, com.alibaba.fastjson.JSON.parseArray(s, clazz));
            } catch (Exception e) {
                e.printStackTrace();
                iNet.response(tag, null);
            }
            L.d(SystemClock.currentThreadTimeMillis() - ss + "");

        } else {
            try {
                iNet.response(tag, com.alibaba.fastjson.JSON.parseObject(resp, clazz));


                L.d("2222222222222222");

            } catch (Exception e) {

                e.printStackTrace();
                iNet.response(tag, null);
            }
        }


    }
}
