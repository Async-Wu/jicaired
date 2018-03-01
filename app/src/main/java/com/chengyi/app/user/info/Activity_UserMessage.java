package com.chengyi.app.user.info;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.alibaba.fastjson.JSON;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.user.login.Activity_RegisterSuccess;
import com.chengyi.app.user.money.Activity_YinhangkaBangding;
import com.chengyi.app.user.setting.Activity_ModifyPassWord;
import com.chengyi.app.user.setting.Activity_SetAccount;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.XuhaoExitDialog;

import static com.chengyi.app.util.net.NetUtil.isNetworkAvailable;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_UserMessage extends BaseActivity implements XuhaoExitDialog.ICallback {




    LinearLayout linearLayout0, linearLayout1, linearLayout2, linearLayout3, linearLayout4,
            linearLayout5;
    private RelativeLayout loaddata, failedLayout, mainlayout;
    int bindingIdentify, bindingBanked, mobileBinded;
    private boolean[] bangdings;
    EditText accountEditText, username, zhengJian, phoneCode, bankNum;
    String account, name, identify, mobile, bankAccount;
    XuhaoExitDialog exitDailog;

    private SharedPreferences pre;
    private Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_usermessage);
        bangdings = new boolean[4];
        initView();

    }


    private void loadData() {
        loaddata.setVisibility(View.VISIBLE);
        if (! isNetworkAvailable(this)) {
            failedLayout.setVisibility(View.VISIBLE);
            loaddata.setVisibility(View.GONE);
            mainlayout.setVisibility(View.GONE);
        } else
            getGerenxinxi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        if (!TextUtils.isEmpty(getUserName())) {
            accountEditText.setText(getUserName());
        } else {
            accountEditText.setText("");

        }

    }

    private void initView() {

      setBack();
        pre = CaipiaoUtil.getCpSharedPreferences(getApplicationContext());
        editor = pre.edit();

        setCusTomeTitle("个人信息");
        linearLayout0 = (LinearLayout) findViewById(R.id.linearLayout0);
        linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
        linearLayout4 = (LinearLayout) findViewById(R.id.linearLayout4);
        linearLayout5 = (LinearLayout) findViewById(R.id.linearLayout5);
        linearLayout0.setOnClickListener(this);
        linearLayout1.setOnClickListener(this);
        linearLayout2.setOnClickListener(this);
        linearLayout3.setOnClickListener(this);
        linearLayout4.setOnClickListener(this);
        linearLayout5.setOnClickListener(this);
        loaddata = (RelativeLayout) findViewById(R.id.loaddata);
        failedLayout = (RelativeLayout) findViewById(R.id.failed);
        failedLayout.setOnClickListener(this);
        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);
        accountEditText = (EditText) findViewById(R.id.accountEditText);
        accountEditText.setOnClickListener(this);
        username = (EditText) findViewById(R.id.xingming);
        username.setOnClickListener(this);
        zhengJian = (EditText) findViewById(R.id.zhengjianhaoma);
        zhengJian.setOnClickListener(this);
        phoneCode = (EditText) findViewById(R.id.shoujihaoma);
        phoneCode.setOnClickListener(this);
        bankNum = (EditText) findViewById(R.id.yinhangkahao);
        bankNum.setOnClickListener(this);
        // 创建对话框
        exitDailog = new XuhaoExitDialog(this);
        exitDailog.setCallBack(this);
        findViewById(R.id.tuichuibtn).setOnClickListener(this);
        startLoadAnim();
    }

    Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//
            case R.id.linearLayout0://用户名
            case R.id.accountEditText:
                if (bangdings == null) {
                    return;
                } else if (bangdings[3]) {
                    showToast("手机注册的账号只能设置一次用户名");
                } else {
                    intent = new Intent(this, Activity_SetAccount.class);
                    startActivityForResult(intent, 3);
                    pullUpActivityAnimFromMain();
                }
                break;
            case R.id.linearLayout1:
            case R.id.linearLayout2:
            case R.id.xingming:
            case R.id.zhengjianhaoma://证件号码
                if (bangdings == null) {
                    return;
                }
                if (bangdings[0]) {
                    showToast("已绑定");
                } else {
                    intent = new Intent(this, Activity_ShenfenBangding.class);
                    startActivityForResult(intent, 1);
                    pullUpActivityAnimFromMain();
                }
                break;
            case R.id.linearLayout3:
            case R.id.shoujihaoma: // /手机验证
                if (bangdings == null) {
                    return;
                }
                Intent in = new Intent(this, Activity_RegisterSuccess.class);
                in.putExtra("fromflag", "Activity_UserMessage");
                if (bangdings[1]) {
                    in.putExtra("isChongBang", true);
                }
                startActivity(in);
                pullUpActivityAnimFromMain();
                break;
            case R.id.linearLayout4:
            case R.id.yinhangkahao: // 银行卡绑定
                if (bangdings == null) {
                    return;
                }
                if (bangdings[2]) {
                    showToast("已绑定");
                } else {
                    intent = new Intent(this, Activity_YinhangkaBangding.class);
                    if (bangdings[0]) {
                        intent.putExtra("isbangding", bangdings[0]);
                        intent.putExtra("username", username.getText().toString());
                        intent.putExtra("zhengjianhaoma", zhengJian.getText()
                                .toString());
                    }
                    startActivityForResult(intent, 2);
                    pullUpActivityAnimFromMain();
                }
                break;
            case R.id.linearLayout5:// 修改密码
                intent = new Intent(this, Activity_ModifyPassWord.class);
                startActivity(intent);
                pullUpActivityAnimFromMain();
                break;
            case R.id.failed:
                loaddata.setVisibility(View.VISIBLE);
                failedLayout.setVisibility(View.GONE);
                mainlayout.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                }, 500);
                break;
            case R.id.tuichuibtn:
                if (!exitDailog.isShowing()) {
                    exitDailog.show();
                    exitDailog.getCenterTextView().setText("确定退出登录吗?");
                }
                break;
        }
    }

    /**
     * 请求个人信息
     */
    private void getGerenxinxi() {
        RequestParams params = getRequestParams();
        HttpBusinessAPI.post(URLSuffix.MY_INFO, params,
                new HttpRespHandler() {
                    @Override
                    public void onSuccess(String response) {
                        super.onSuccess(response);

                        if (JSON.parseObject(response).getIntValue(URLSuffix.flag) == 1) {


                            failedLayout.setVisibility(View.GONE);
                            loaddata.setVisibility(View.GONE);
                            mainlayout.setVisibility(View.VISIBLE);
                            // System.out.println(response);
                            // 身份证是否绑定
                            bangdings[0] =JSON.parseObject(response).getIntValue(
                                    URLSuffix.bindingIdentify ) == 1;
                            // 手机是否绑定
                            bangdings[1] = JSON.parseObject(response).getIntValue(
                                    URLSuffix.mobileBinded ) == 1;
                            // 银行卡是否绑定
                            bangdings[2] =JSON.parseObject(response).getIntValue(
                                    URLSuffix.bindingBanked ) == 1;
                            account =JSON.parseObject(response).getString(URLSuffix.username);
                            name = JSON.parseObject(response).getString(URLSuffix.name);
                            identify = JSON.parseObject(response).getString(URLSuffix.identify);
                            mobile = JSON.parseObject(response).getString(URLSuffix.mobile);
                            bankAccount = JSON.parseObject(response).getString(URLSuffix.bankAccount);
                            editor.putString("user_identify", identify);
                            editor.commit();
                            if (!TextUtils.isEmpty(account)) {
                                boolean isSetAccount = account.contains("-");
                                bangdings[3] = !isSetAccount;
                                String tmpAccountStr = account + (isSetAccount ? "(设置账号)" : "(已设置)");
                                if (isSetAccount) {
                                    SpannableStringBuilder fontStyleBuilder = new SpannableStringBuilder(tmpAccountStr);
                                    fontStyleBuilder.setSpan(new ForegroundColorSpan(Color.BLUE),
                                            account.length(), tmpAccountStr.length(),
                                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    accountEditText.setText(fontStyleBuilder);
                                } else {
                                    accountEditText.setText(tmpAccountStr);
                                }
                            }
                            username.setText(name
                                    + (bangdings[0] ? "(已绑定)" : "(未绑定)"));
                            zhengJian.setText(identify
                                    + (bangdings[0] ? "(已绑定)" : "(未绑定)"));
                            String phstr = mobile
                                    + (bangdings[1] ? "(重新绑定)" : "(未绑定)");
                            phoneCode.setText(phstr);
                            if (bangdings[1]) {
                                SpannableStringBuilder fontStyleBuilder = new SpannableStringBuilder(
                                        phstr);
                                fontStyleBuilder.setSpan(
                                        new ForegroundColorSpan(Color.BLUE),
                                        11, phstr.length(),
                                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                phoneCode.setText(fontStyleBuilder);
                            }
                            bankNum.setText(bankAccount
                                    + (bangdings[2] ? "(已绑定)" : "(未绑定)"));
                        } else {

                            failedLayout.setVisibility(View.VISIBLE);
                            loaddata.setVisibility(View.GONE);
                            mainlayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        super.onFailure(error);
                        failedLayout.setVisibility(View.VISIBLE);
                        loaddata.setVisibility(View.GONE);
                        mainlayout.setVisibility(View.GONE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (1 == resultCode) {
            setNameAndIdentity(data.getExtras().getString("name"), data
                    .getExtras().getString("identityNumber"));
        } else if (2 == resultCode) {
            String bankAccount = data.getExtras().getString("banknum");
            bankNum.setText(bankAccount.substring(0, bankAccount.length() - 4)
                    + "****" + "(已绑定)");
            if (!bangdings[0]) {
                setNameAndIdentity(data.getExtras().getString("username"), data
                        .getExtras().getString("identityNumber"));
            }
        } else if (3 == resultCode) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String username = bundle.getString("username");
                if (!TextUtils.isEmpty(username) && !username.contains("-")) {
                    accountEditText.setText(username + "(已设置)");
                    bangdings[3] = true;
                    CaipiaoApplication instance = CaipiaoApplication.getInstance();
                    Handler zhongJiangHandler = instance.getGetZhongJiang();
                    Message msg = new Message();
                    msg.what = 4;
                    Bundle d = new Bundle();
                    d.putString("username", username);
                    msg.setData(d);
                    zhongJiangHandler.handleMessage(msg);
                    Handler userCenterHandler = instance.getUserCenterHandler();
                    userCenterHandler.handleMessage(msg);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setNameAndIdentity(String name, String number) {
        bangdings[0] = true;
        String temp = name.substring(0, 1);
        for (int i = 0; i <= name.length() - 2; i++)
            temp += "*";
        username.setText(temp + (bangdings[0] ? "(已绑定)" : "(未绑定)"));
        zhengJian.setText(number.substring(0, number.length() - 4) + "****"
                + (bangdings[0] ? "(已绑定)" : "(未绑定)"));
    }

    @Override
    public void reBack() {
        if (exitDailog.isShowing())
            exitDailog.dismiss();
        // /修改中心区域
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                editor.putInt("login_flag", 1);
                editor.putInt("fragment_select", 0);
                editor.putString("user_clientUserSession", "");
                editor.commit();
                saveUserData("", "");
                finish();


            }
        }, 50);

    }

    @Override
    public void close() {
        if (exitDailog.isShowing())
            exitDailog.dismiss();
    }

}
