package com.chengyi.app.user.setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.adapter.ScanimageAdapter;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.caipiao.ImageEntity;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.util.CaipiaoConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  ScanimageActivity extends BaseActivity {

    private String lotteryName;
    private int schemeId;
    private ImageEntity imageEntity;
    private ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanimage);
        init();
    }

    private void init() {

        listView= (ListView) findViewById(R.id.image_listview);

        Intent intent = getIntent();
        schemeId = intent.getIntExtra("schemeId", 1);

        lotteryName = intent.getStringExtra("lotteryName");
       setCusTomeTitle(lotteryName + "-图片浏览");

        Map<String,String> map=new HashMap<>();
        map.put("clientUserSession",getSession());
        map.put("requestType",1+"");
        map.put("schemeId", schemeId + "");
        map.put("page", 0 + "");
        String url= CaipiaoConst.BASE_URL+ "lottery/schemeImage.htm";
        OkHttpUtil.postSubmitForm(url, map, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                if (json!=null){
                    imageEntity = JSONObject.parseObject(json, ImageEntity.class);
                    List<ImageEntity.ListEntity> list = imageEntity.getList();
                    ScanimageAdapter adapter=new ScanimageAdapter(ScanimageActivity.this,list);
                    listView.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(String url, String error) {


                
            }
        });

    }
}
