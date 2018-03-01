package com.chengyi.app.net.api;

import android.content.Context;
import com.chengyi.app.listener.INet;
import com.chengyi.app.net.http.OkHttpUtil;

import java.util.Map;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BaseApi {
    /**
     * net
     *
     * @param context
     * @param iNet
     * @param tag
     * @param clazz
     */
    public  static  void fetch(String url, Context context, INet iNet, int tag, Class clazz, Map<String,String> map,boolean flag){
        OkHttpUtil.postSubmitForm(url,context,iNet,tag,clazz,map,flag);
    }

}
