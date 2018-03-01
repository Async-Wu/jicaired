package com.chengyi.app.user.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
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
public class  OrderliuyanActivity extends BaseActivity{
    private TextView centertextView;
    private int schemeId;
    private EditText msendeditext;
    private Button fanganliuyan_send;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderliuyan);
        Intent intent = getIntent();
        msendeditext= (EditText) findViewById(R.id.fanganliuynan_edit);
        fanganliuyan_send= (Button) findViewById(R.id.fanganliuyan_send);
        schemeId = intent.getIntExtra("schemeId", 1);

        final String url= IP.IP+"lottery/sendMessage.htm";

        fanganliuyan_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content= String.valueOf(msendeditext.getText());
                L.d("content", content);
                Map<String,String> map=new HashMap<>();
                map.put("clientUserSession",getSession());
                map.put("requestType",1+"");
                map.put("schemeId",schemeId+"");
                map.put("content",content);
                OkHttpUtil.postSubmitForm(url, map, new OkHttpUtil.OnDownDataListener() {
                    @Override
                    public void onResponse(String url, String json) {
                        try {
                            JSONObject jsonObject=new JSONObject(json);
                            Object flag = jsonObject.get("flag");
                            int id= (int) flag;
                            if (id==1){
                                Toast.makeText(OrderliuyanActivity.this,"留言成功", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(OrderliuyanActivity.this,"留言失败", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(OrderliuyanActivity.this,"留言失败", Toast.LENGTH_LONG).show();
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
