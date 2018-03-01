package com.chengyi.app.user.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.util.IP;
import com.chengyi.app.util.L;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_lianxiliuyan extends BaseActivity {

    private Button mlianxi_send;
    private EditText fankuiyijian_edit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lianxiliuyan);
        init();
    }

    private void init() {
        mlianxi_send= (Button) findViewById(R.id.lianxi_send);
        fankuiyijian_edit= (EditText) findViewById(R.id.fankuiyijian_edit);

        setCusTomeTitle("意见反馈");
        setBack();

        mlianxi_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content= String.valueOf(fankuiyijian_edit.getText());

                final String url= IP.IP+"/user/leave_word.htm";
                Map<String,String> map=new HashMap<>();



                map.put("title","意见反馈");
                map.put("content",content);
                OkHttpUtil.postSubmitForm(url, map, new OkHttpUtil.OnDownDataListener() {
                    @Override
                    public void onResponse(String url, String json) {
                        L.d("liuansjfidsj", json.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            Object flag = jsonObject.get("flag");
                            int id = (int) flag;
                            if (id == 1) {
                                Toast.makeText(Activity_lianxiliuyan.this, "留言成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Activity_lianxiliuyan.this, "留言失败", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(String url, String error) {

                    }
                });
            }
        });

    }
}
