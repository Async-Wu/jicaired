package com.chengyi.app.user.setting;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.home.home.HomeActivity;
import com.chengyi.app.user.info.LanguageAdapter;
import com.chengyi.app.user.info.LanguageMode;
import com.chengyi.app.util.*;
import com.chengyi.app.web.WebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class Activity_Setting extends BaseActivity implements LanguageAdapter.ISetting {


    Button updateBtn;
    TextView banBenText;

    CheckBox yaoHao, yiLou, luckyCheckBox;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private LinearLayout llWord;
    private TextView tvlocalname;
    private AlertDialog show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_setting);
        preferences = CaipiaoUtil.getCpSharedPreferences(this);
        editor = preferences.edit();
        initView();
    }

    private void initView() {

        setCusTomeTitle("设置");
        setBack();
        llWord = (LinearLayout) findViewById(R.id.ll_word);
        tvlocalname = (TextView) findViewById(R.id.localname);
        tvlocalname.setText(Locale.getDefault().getLanguage());
        llWord.setOnClickListener(this);

        updateBtn = (Button) findViewById(R.id.updateBtn);
//

        findViewById(R.id.tuichuibtn).setOnClickListener(this);
        findViewById(R.id.modifypassword).setOnClickListener(this);
        findViewById(R.id.kefuid).setOnClickListener(this);
        findViewById(R.id.yijianfankui).setOnClickListener(this);
        findViewById(R.id.helpcenter).setOnClickListener(this);
        findViewById(R.id.tongzhi).setOnClickListener(this);
        findViewById(R.id.tuijian).setOnClickListener(this);
        yaoHao = (CheckBox) findViewById(R.id.checkbox1);
        yaoHao.setChecked(preferences.getBoolean("dakaiyaodongxuanhao", true));
        yaoHao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("dakaiyaodongxuanhao", true);
                } else {
                    editor.putBoolean("dakaiyaodongxuanhao", false);
                }
                CaipiaoUtil.youmengEvent(Activity_Setting.this, YOUMENG_EVENT.new_setting_center, map);
                editor.commit();
            }
        });
        yiLou = (CheckBox) findViewById(R.id.checkbox2);
        yiLou.setChecked(preferences.getBoolean("dakaiyilou", true));
        yiLou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("dakaiyilou", true);
                } else {
                    editor.putBoolean("dakaiyilou", false);
                }
                CaipiaoUtil.youmengEvent(Activity_Setting.this, YOUMENG_EVENT.new_setting_center, map);
                editor.commit();
            }
        });
        luckyCheckBox = (CheckBox) findViewById(R.id.checkbox0);
        luckyCheckBox.setChecked(preferences.getBoolean("openlucky", true));
        luckyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("openlucky", true);
                    map.put("操作类型", "幸运一注打开");
                } else {
                    editor.putBoolean("openlucky", false);
                    map.put("操作类型", "幸运一注关闭");
                }
                CaipiaoUtil.youmengEvent(Activity_Setting.this, YOUMENG_EVENT.new_setting_center, map);
                editor.commit();
                //((MainActivity)Activity_Setting.this.getParent()).changIndexLucky();
            }
        });
        banBenText = (TextView) findViewById(R.id.banbencontext);
        banBenText.setText("当前版本" + CaipiaoConst.VERSION);
        if (preferences.getInt("current_versionid", 0) > CaipiaoConst.VERSION_ID) {
            banBenText.setVisibility(View.GONE);
            updateBtn.setVisibility(View.VISIBLE);
            updateBtn.setOnClickListener(this);
        } else {
            updateBtn.setVisibility(View.INVISIBLE);
        }
        ///当用户没有登录时，修改密码和退出账户不显示
        if (TextUtils.isEmpty(getSession())) {
            findViewById(R.id.modifypassword).setVisibility(View.GONE);
            findViewById(R.id.tuichuibtn).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        map.clear();
        Intent intent;
        switch (v.getId()) {
//		 
            case R.id.modifypassword://修改密码
                intent = new Intent(this, Activity_ModifyPassWord.class);
                startActivity(intent);
                pullUpActivityAnimFromMain();
                map.put("操作类型", "修改密码");
                CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_setting_center, map);
                break;
            case R.id.tuichuibtn:

                break;
            case R.id.kefuid:          //客服呼叫
                bodadianhua(CaipiaoConst.PHONE);
                map.put("操作类型", "客服呼叫");
                CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_setting_center, map);
                break;
            case R.id.yijianfankui:    //意见反馈
                if (TextUtils.isEmpty(getSession())) {
                    CaipiaoApplication.getInstance().setBackSetting(true);
                    //((MainActivity)this.getParent()).moveWindow(2);
                    map.put("操作类型", "意见反馈(未登录)");
                    CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_setting_center, map);
                } else {

                    intent = new Intent(this, Activity_FeedBack.class);
                    startActivity(intent);
                    pullUpActivityAnimFromMain();

                }
                break;
            case R.id.helpcenter:      //帮助中心

                intent = new Intent(this, WebViewActivity.class);
                intent.putExtra("url", IP.IP + "/news/help.htm" + "?requestType=4" + "&appVersion=" + CaipiaoConst.APPVERSION + "&version=" + CaipiaoConst.VERSION + "&requestType=" + CaipiaoConst.REQUESTTYPE);
                intent.putExtra("title", "帮助中心");
                startActivity(intent);
                pullUpActivityAnimFromMain();

                break;
            case R.id.tongzhi:         //通知中心
                intent = new Intent(this, Activity_Notification.class);
                startActivity(intent);
                pullUpActivityAnimFromMain();

                break;
            case R.id.tuijian:         //好友推荐
                String temp = "";
                if (TextUtils.isEmpty(temp)) {
                    temp = "我刚用极彩网手机客户端买了彩票，你也去试试吧。可在网站下载软件买彩也能直接在网上买\nhttp://m.jicai500.com/index";
                }
                tuijianhaoyou(temp);
                map.put("操作类型", "好友推荐");
                CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_setting_center, map);
                break;
            case R.id.updateBtn:
                String url = preferences.getString("update_url", "");
                if (CaipiaoUtil.isFileApk(url)) {
                    showLoading(getString(R.string.xiazaizhong2));
                    new UpdateManager(this).getFile(url);
                }
                map.put("操作类型", "版本更新");
                CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_setting_center, map);
                break;

            case R.id.ll_word:
               /* if (TextUtils.isEmpty(preferences.getString("local", ""))) {
                    editor.putString("local", "china");
                } else if ((preferences.getString("local", "")).equals("china")) {
                    editor.putString("local", "en");
                } else if ((preferences.getString("local", "")).equals("en")) {
                    editor.putString("local", "china");
                }

                editor.commit();*/

                selectLanguage();
                break;
        }

    }

    private AlertDialog.Builder alertDialog;

    private void selectLanguage() {
        alertDialog = new AlertDialog.Builder(Activity_Setting.this);
        View v = getLayoutInflater().inflate(R.layout.language, null, false);
        final AlertDialog.Builder builder = alertDialog.setView(v);
        ListView lvLanguage = (ListView) v.findViewById(R.id.lv_language);
        String[] ls = getResources().getStringArray(R.array.language);
        final List<LanguageMode> l = new ArrayList<>();
        for (String s : ls) {
            LanguageMode mode = new LanguageMode();
//            if ((mode.getLanguage().equals(Locale.getDefault().getLanguage()))) {
//                mode.setSelect(true);
//            } else {
//                mode.setSelect(false);
//            }
            mode.setLaungename(s);
            l.add(mode);
        }

        final LanguageAdapter adapter = new LanguageAdapter(l, Activity_Setting.this);
        lvLanguage.setAdapter(adapter);
        show = alertDialog.show();
        lvLanguage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                show.dismiss();
                L.d("---------------------");
            }
        });


    }


    protected void tuijianhaoyou(String msg) {
        try {
            Uri smsToUri = Uri.parse("smsto:");// 联系人地址
            Intent mIntent = new Intent(Intent.ACTION_SENDTO,
                    smsToUri);
            mIntent.putExtra("sms_body", msg);// 短信内容
            startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bodadianhua(String inputStr) {
        try {// 异常捕获一下，比如xiaomi系统，拨打电话 手机先会弹出提示框，如禁止，则会报错
            Intent myIntentDial = new Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:" + inputStr));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            startActivity(myIntentDial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seting() {

        settinglanguage();
        tvlocalname.setText(Locale.getDefault().getLanguage());
        if (show != null) show.dismiss();
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);


    }
}
