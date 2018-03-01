/**
 *
 */
package com.chengyi.app.net.http;


import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.util.CaipiaoConst;

import java.util.Map;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class HttpBusinessAPI {
    public static void post(String url, Map params, final HttpRespHandler responseHandler) {
        OkHttpUtil.postSubmitForm(getAbsoluteUrl(url), params, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String response) {
                responseHandler.onSuccess(response);
            }

            @Override
            public void onFailure(String url, String error) {
                responseHandler.onFailure(new Throwable(error), error);
            }
        });
    }

    /**
     * 网络请求
     *
     * @param BC
     * @param url
     * @param params
     * @param action
     */
    public static void post(int BC, String url, RequestParams params,
                            HttpRespHandler action) {

        if (params == null) params = new RequestParams();
        Control.getInstance().initial(CaipiaoApplication.getInstance());
        Control.getInstance().request(BC, getAbsoluteUrl(url), params, action, true);

    }


    private static String getAbsoluteUrl(String relativeUrl) {
        if (relativeUrl.startsWith("http:")) {
            return relativeUrl;
        }
        return CaipiaoConst.BASE_URL + relativeUrl;
    }
}
