package com.chengyi.app.user.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.view.ClearEditText;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_ModifyPassWord extends BaseActivity {

    private ClearEditText oldpd, newpd, ensurepd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_modifypassword);

        setCusTomeTitle("修改密码");
        setBack();
        oldpd = (ClearEditText) findViewById(R.id.EditText01);
        newpd = (ClearEditText) findViewById(R.id.newpassword);
        ensurepd = (ClearEditText) findViewById(R.id.ensurepassword);
        findViewById(R.id.querenbtn).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.querenbtn:
                String check = check();
                if (check != null) {
                    showToast(check);
                } else {
                    showLoading("正在处理....");

                }
                break;
        }
    }

    private String check() {
        if (TextUtils.isEmpty(oldpd.getText().toString())) {
            return getString(R.string.qingshurujiumima);
        }

        if (newpd.getText().toString().length() < 6
                || newpd.getText().toString().length() > 20) {
            return getString(R.string.mimachangdurule);
        }
        if (!newpd.getText().toString().equals(ensurepd.getText().toString())) {
            return getString(R.string.mimabutong);
        }
        return null;
    }
}
