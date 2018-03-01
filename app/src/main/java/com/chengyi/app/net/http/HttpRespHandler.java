/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.chengyi.app.net.http;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * Used to intercept and handle the responses from requests made using
 * {@link AsyncHttpClient}, with automatic parsing into a {@link JSONObject} or
 * {@link JSONArray}.
 * <p>
 * This class is designed to be passed to get, post, put and delete requests
 * with the {@link #onSuccess(JSONObject)} or {@link #onSuccess(JSONArray)}
 * methods anonymously overridden.
 * <p>
 * Additionally, you can override the other event methods from the parent class.
 */

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class HttpRespHandler {
    private static final int SUCCESS_MESSAGE = 0;
    private static final int FAILURE_MESSAGE = 1;
    private static final int START_MESSAGE = 2;
    private static final int FINISH_MESSAGE = 3;

    private Handler handler;

    /**
     * Creates a new AsyncHttpResponseHandler
     */
    public HttpRespHandler() {

        if (Looper.myLooper() != null) {
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    HttpRespHandler.this.handleMessage(msg);
                }
            };
        }
    }


    public void onStart() {
    }


    public void onFinish() {
    }


    public void onSuccess(String content) {
    }


    public void onFailure(Throwable error) {
    }


    public void onFailure(Throwable error, String content) {
        onFailure(error);
    }


    protected void handleSuccessMessage(String responseBody) {
        onSuccess(responseBody);
    }

    protected void handleFailureMessage(Throwable e, String responseBody) {
        onFailure(e, responseBody);
    }


    protected void handleMessage(Message msg) {
        switch (msg.what) {
            case SUCCESS_MESSAGE:
                handleSuccessMessage((String) msg.obj);
                break;
            case FAILURE_MESSAGE:
                Object[] repsonse = (Object[]) msg.obj;
                handleFailureMessage((Throwable) repsonse[0], (String) repsonse[1]);
                break;
            case START_MESSAGE:
                onStart();
                break;
            case FINISH_MESSAGE:
                onFinish();
                break;
        }
    }


    public void onSuccess(int bc, String response) {
    }


    public void onFailure(int bc, Throwable error) {


    }


    public void handleResponse(int bc, String responseBody) {
        try {
            onSuccess(bc, responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            onFailure(bc, e);
        }
    }

}