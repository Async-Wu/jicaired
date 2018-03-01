package com.chengyi.app.user.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.L;
import com.chengyi.app.view.ClearEditText;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  LoginActivity extends BaseActivity implements OnClickListener {



    private ClearEditText userName, passWord;
    private Button loginBtn;
    private CheckBox cb_login;

    private TextView forgetTx;
    private Button zhuceBtn;

    boolean loginFrom;
    String name;
    String password;
    Activity activity;
    Editor editor;
    SharedPreferences preferences;
    /**
     * 该标记用来区别采用哪种第三方登录方式。
     */
    int currentLoginFlag = -1;

    /**
     * 获取新浪用户信息的对象
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        preferences = CaipiaoUtil.getCpSharedPreferences(this);
        editor = preferences.edit();
        setContentView(R.layout.fragment_login);
        setCusTomeTitle("账户登录");
        setBack();
        initView();
        setListener();

    }

    private void initView() {
        setupUI(findViewById(R.id.login_main_layout));
        cb_login = (CheckBox) findViewById(R.id.cb_login);
        cb_login.setChecked(isSave());

        cb_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setSave(isChecked);
            }
        });


        userName = (ClearEditText) findViewById(R.id.username);
        userName.requestFocus();


        if (isSave()) {
            userName.setText(getUserName());
        }


        passWord = (ClearEditText) findViewById(R.id.password);


        if (isSave()) {
            userName.setText(getUserName());
            passWord.setText(getPwd());
        }


        zhuceBtn = (Button) findViewById(R.id.login_register);
        loginBtn = (Button) findViewById(R.id.loginbtn);


        forgetTx = (TextView) findViewById(R.id.forgetpasswordtext);

    }

    private void setListener() {
        zhuceBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        forgetTx.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        map.clear();
        closeSoftKeybord();
        switch (v.getId()) {
            case R.id.login_register:// 注册

                startActivity(new Intent(getBaseContext(), RegeditActivity.class));

                break;
            case R.id.loginbtn:// 登录
                name = userName.getText().toString();
                password = passWord.getText().toString();
                login();
//
                break;
            case R.id.forgetpasswordtext:// 忘记密码


                Intent intt = new Intent(getApplicationContext(), Login_FindPW.class);
                intt.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intt);

                break;
        }
    }

    // 系统登录
    private void login() {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            showToast(R.string.zhanghaohuomimabunengweikong);
            return;
        } else {
            showLoading("正在登录中....");
            RequestParams params = new RequestParams();
            params.put(URLSuffix.username, name);
            params.put(URLSuffix.password, password);


//            Net.fetch(params,new HttpRespHandler());
            HttpBusinessAPI.post(URLSuffix.LOGIN, params,new HttpRespHandler(){
                @Override
                public void onSuccess(String  response) {
                    super.onSuccess(response);
                    handlResult( response);
                }

                @Override
                public void onFailure(Throwable error) {
                    super.onFailure(error);
                    hideLoading();
                }
            });
//
        }
    }

    /**
     * 处理返回的结果
     */
    private void handlResult(String result) {

        hideLoading();
        LoginModel loginModel = null;
        try {
            L.d(result+"------------");
            loginModel = JSON.parseObject(result, LoginModel.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (loginModel == null) {
            Toast.makeText(this, "用户信息错误", Toast.LENGTH_SHORT).show();
            return;
        }

        if (loginModel.getFlag() != 1) {
            Toast.makeText(this, loginModel.getErrorMessage(), Toast.LENGTH_SHORT).show();
        } else {
            editor.putString("user_balance",String.valueOf(loginModel.getBalance()));
            editor.putString("score", String.valueOf(loginModel.getScore()) );
            editor.putString("user_clientUserSession",loginModel.getClientUserSession());
            Log.d("userSession",loginModel.getClientUserSession());
            editor.putString("nickname",userName.getText().toString().trim());
            saveUserData(name, password);
            editor.commit();
            finish();
        }


    }


}
