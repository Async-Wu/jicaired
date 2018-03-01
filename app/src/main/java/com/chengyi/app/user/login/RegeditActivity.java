package com.chengyi.app.user.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.listener.INet;
import com.chengyi.app.net.api.HomeApi;
import com.chengyi.app.view.ClearEditText;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class RegeditActivity extends BaseActivity implements INet, View.OnClickListener {

    private ClearEditText username;
    private ClearEditText password, user_name;
    private EditText et_verifyCode;
    private ClearEditText superiorUserID;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regedit);
        username = (ClearEditText) findViewById(R.id.username);
        password = (ClearEditText) findViewById(R.id.password);
        findViewById(R.id.btn_get).setOnClickListener(this);
        findViewById(R.id.loginbtn).setOnClickListener(this);
        et_verifyCode = (EditText) findViewById(R.id.et_verifyCode);
        setCusTomeTitle("注册");
        superiorUserID = (ClearEditText) findViewById(R.id.superiorUserID);
        user_name = (ClearEditText) findViewById(R.id.user_name);
        setBack();
    }

    private EditText getEtVerifyCode() {
        return (EditText) findViewById(R.id.et_verifyCode);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get:
                getCode();
                break;
            case R.id.loginbtn:
                regedit();
                break;
        }
    }

    private void regedit() {
        if (TextUtils.isEmpty(username.getText().toString())) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }


        if (TextUtils.isEmpty(user_name.getText().toString())) {
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (TextUtils.isEmpty(et_verifyCode.getText().toString())) {
//            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
//            return;
//        }


        fetch(FIRST);


    }

    @Override
    public void response(int tag, Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoading();
            }
        });
        if (o == null) return;
        if (tag == FIRST) {
            final RegeditMode regeditMode = (RegeditMode) o;
            Log.d("newsession",regeditMode.getClientUserSession());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (regeditMode.getFlag() == 1) {
                        Toast.makeText(RegeditActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegeditActivity.this, regeditMode.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else if (tag == SECOND) {
        }


    }

    /**
     * username
     * password
     * mobile
     * requestType
     *
     * @param tag
     */
    @Override
    public void fetch(int tag) {


        if (tag == FIRST) {// /user/register.htm
            showLoading("注册中...");
            Map<String, String> map = new HashMap<>();
            map.put("username", user_name.getText().toString());
            map.put("password", password.getText().toString());
            if (superiorUserID.getText() != null && !TextUtils.isEmpty(superiorUserID.getText().toString()))
                map.put("superiorUserID", superiorUserID.getText().toString());
            map.put("mobile", username.getText().toString());

            HomeApi.REGISTER(this, this, tag, RegeditMode.class, map);


        } else if (tag == SECOND) {

            Map<String, String> map = new HashMap<>();
            map.put("username", username.getText().toString());
            map.put("password", password.getText().toString());

            map.put("mobile", username.getText().toString());

            HomeApi.BIND_MOBILE(this, this, tag, null, map);

        }
    }

    public void getCode() {
        if (TextUtils.isEmpty(username.getText().toString())) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        time = 60;  //可点击则可进行倒计时  进行时间重置
        findViewById(R.id.btn_get).setClickable(false);
        handler.removeMessages(0);
        handler.sendEmptyMessage(0);

        fetch(SECOND);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(0);
    }

    private int time = 60;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (time > 0) {
                ((Button) findViewById(R.id.btn_get)).setTextColor(Color.GRAY);
                ((Button) findViewById(R.id.btn_get)).setText(time + "秒后重新获取");
                time--;
                handler.sendEmptyMessageDelayed(0, 1000);
            } else {
                findViewById(R.id.btn_get).setClickable(true);
                ((Button) findViewById(R.id.btn_get)).setTextColor(Color.parseColor("#CD372C"));
                ((Button) findViewById(R.id.btn_get)).setText(R.string.get);
            }

        }
    };
}
