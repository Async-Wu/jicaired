package com.chengyi.app.net.http;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.chengyi.app.listener.INet;
import com.chengyi.app.net.api.JsonParse;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.util.IP;
import com.chengyi.app.util.L;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class OkHttpUtil {


    private static OkHttpClient okHttpClient;
    private static Handler handler = new Handler();


    public static void initOkHttp() {

//     setCertificates(new InputStream().writeUtf8("")
//                        .inputStream());

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
    }



    public static void downJSON(final String url, final OnDownDataListener onDownDataListener){
        final String newurl=url;
        Log.d("downokhttp",newurl);
        Request request = new Request.Builder()
                .url(newurl)
                .build();
        Log.d("print", "下载3");

        Call call = okHttpClient.newCall(request);
        Log.d("print", "下载4");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(onDownDataListener != null){
                    onDownDataListener.onFailure(newurl, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String str = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(onDownDataListener != null){
                            onDownDataListener.onResponse(newurl, str);
                        }
                    }
                });
            }
        });
    }
    /**
     * post提交表单
     */


    public static void postSubmitForm(final String url, Map<String, String> params, final OnDownDataListener onDownDataListener) {
        String s = "";
        params = addParam(params);
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()) {
            String value = params.get(key);
            s += key + "=" + value + "&";
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                continue;
            }
            builder.add(key, value);
        }
        L.d("api", url + "?" + s);

        FormBody formBody = builder.build();
        final Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onDownDataListener.onFailure("", e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final String str = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        L.d(str);
                        if (onDownDataListener != null) {
                         try {
                             onDownDataListener.onResponse(url, str);
                         }catch (Exception e){
                             onFailure(call,new IOException(e.getMessage()));
                         }
                        }
                    }
                });
            }
        });

    }

    @NonNull
    private static Map<String, String> addParam(Map<String, String> params) {

        return AppUtil.addParam(params);
    }



    public interface OnDownDataListener {
        void onResponse(String url, String response);

        void onFailure(String url, String error);

    }


    public static void postSubmitForm(String url, final Context context, final INet iNet, final int tag, final Class clazz, Map<String, String> params, final boolean flag) {


        String s = "";

        params = addParam(params);

        if (params.size() > 0) {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                s += key + "=" + value + "&";
                builder.add(key, value);
            }
            FormBody formBody = builder.build();

            L.d("apiUrl", IP.IP + url + "?" + s);

            final Request request = new Request.Builder()
                    .url(IP.IP + url)
                    .post(formBody)
                    .build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iNet.response(tag, null);
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) {
                    try {
                        final String str;

                        str = response.body().string();
                        L.d(str);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {

                                JsonParse.parse(iNet, tag, clazz, (str), flag, context);

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        iNet.response(tag, null);
                        Toast.makeText(context, "网络连接错误", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }




    public void setCertificates(InputStream... certificates)
    {
        try
        {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates)
            {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try
                {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e)
                {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init
                    (
                            null,
                            trustManagerFactory.getTrustManagers(),
                            new SecureRandom()
                    );




        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
