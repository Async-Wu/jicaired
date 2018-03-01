package com.chengyi.app.home.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.listener.IInit;
import com.chengyi.app.listener.INet;
import com.chengyi.app.net.api.HomeApi;
import com.chengyi.app.start.Start_model;
import com.chengyi.app.start.Update_model;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.UpdateManager;
import com.chengyi.app.view.XuhaoExitDialog;
import com.chengyi.app.view.scoller.NoScrollerViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class HomeActivity extends BaseActivity implements IInit, HomeFragment.IGet, INet, XuhaoExitDialog.ICallback {

    private LinearLayout rgBottom;
    private ImageView rbHome;
    private ImageView rbBuy;
    private ImageView rbGet;
    private ImageView rbDicover;
    private ImageView rbMe;
    UpdateManager up = new UpdateManager(this);


    private NoScrollerViewPager vphome;
    private HomeVpAdapter adapter;

    private String url = "";
    private XuhaoExitDialog xuhaoExitDialog;
    private Update_model update_model;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        init();

    }

    @Override
    public void init() {
        initView();
        loadDate();
    }

    @Override
    public void initView() {
        this.vphome = (NoScrollerViewPager) findViewById(R.id.vp_home);
        rgBottom = (LinearLayout) findViewById(R.id.rg_bottom);
        rbHome = (ImageView) findViewById(R.id.rb_home);
        rbBuy = (ImageView) findViewById(R.id.rb_buy);
        rbGet = (ImageView) findViewById(R.id.rb_get);
        rbDicover = (ImageView) findViewById(R.id.rb_dicover);
        rbMe = (ImageView) findViewById(R.id.rb_me);
        rgBottom.setOnClickListener(this);
        rbHome.setOnClickListener(this);
        rbBuy.setOnClickListener(this);
        rbGet.setOnClickListener(this);
        rbDicover.setOnClickListener(this);
        rbMe.setOnClickListener(this);
        rbHome.setSelected(true);
        if (CaipiaoConst.isZixp(this)) {
            rbDicover.setVisibility(View.VISIBLE);
        } else {
            rbDicover.setVisibility(View.GONE);
        }


        xuhaoExitDialog = new XuhaoExitDialog(this);
        xuhaoExitDialog.setCallBack(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(getSession()) && vphome.getCurrentItem() == 4) {
            vphome.setCurrentItem(0);
        }

        initRb(vphome.getCurrentItem());


    }

    private void initRb(int pos) {
        switch (pos) {

            case 0:
                resetImg(rbHome);
                break;
            case 1:
                resetImg(rbBuy);
                break;
            case 2:
                resetImg(rbGet);
                break;
            case 3:
                resetImg(rbDicover);
                break;
            case 4:
                if (TextUtils.isEmpty(getSession())) {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                } else {
                    resetImg(rbMe);
                }
                break;
        }
    }

    @Override
    public void loadDate() {

        fetch(FIRST);
        if (adapter == null) {
            adapter = new HomeVpAdapter(getSupportFragmentManager());
            vphome.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        vphome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initRb(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void get(int tag) {
        if (tag == 2) {
            vphome.setCurrentItem(1, false);
        } else if (tag == 1) {
            vphome.setCurrentItem(2, false);
        }


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {

            case R.id.rb_home:
                vphome.setCurrentItem(0, false);
                break;
            case R.id.rb_buy:
                vphome.setCurrentItem(1, false);
                break;
            case R.id.rb_get:
                vphome.setCurrentItem(2, false);
                break;
            case R.id.rb_dicover:
                vphome.setCurrentItem(3, false);
                break;
            case R.id.rb_me:
                if (TextUtils.isEmpty(getSession())) {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                } else {
                    vphome.setCurrentItem(4, false);
                }
                break;


        }


    }

    private void resetImg(ImageView iv) {

        rbHome.setSelected(false);
        rbBuy.setSelected(false);
        rbGet.setSelected(false);
        rbDicover.setSelected(false);
        rbMe.setSelected(false);
        iv.setSelected(true);

    }


    @Override
    public void response(int tag, Object o) {
        if (o == null) return;

        if (tag == FIRST) {

            String s = (String) o;
            Start_model start_model = JSONObject.parseObject(s, Start_model.class);

            if (start_model.getFlag() != 1) {

            } else {
                String content = replace_zryi(start_model.getVersion_content());

                update_model = JSONObject.parseObject(content, Update_model.class);

                if (Integer.parseInt(update_model.getCurrent_versionid()) > (CaipiaoConst.VERSION_ID)) {
                    xuhaoExitDialog.show();
                    xuhaoExitDialog.getCenterTextView().setText(update_model.getUpdate_desc());
                    if (update_model.getAndroid_forceUpdate() == 1) {
                        xuhaoExitDialog.setCancelable(false);
                        xuhaoExitDialog.getNoBtn().setVisibility(View.GONE);
                        xuhaoExitDialog.getvIn().setVisibility(View.GONE);
                    }


                }

            }


        }
    }

    private void gotodown(String url) {


        showLoading("下载中...");
        if (pd != null) {
            pd.setCanceledOnTouchOutside(false);
        }
        if (AppUtil.getPerssion(this, Manifest.permission.READ_EXTERNAL_STORAGE) && AppUtil.getPerssion(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            up.getFile(url);
//            up.downLoad(update_model);

        } else {
            Toast.makeText(this, "未获取权限", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 递归 去除转义
     *
     * @param s
     * @return
     */
    private String replace_zryi(String s) {
        if (s.indexOf("\\") == -1) {
            return s;
        } else {
            s = s.replace("\\", "");
            return replace_zryi(s);
        }

    }


    @Override
    public void fetch(int tag) {
        if (tag == FIRST) {
            HomeApi.APP_NEWVERSION(this, this, tag, String.class, null);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            gotodown(url);

        } else {
            Toast.makeText(this, "未获取权限", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void reBack() {
        try {
            url = update_model.getAndroid_update_url();

            gotodown(update_model.getAndroid_update_url());
        } catch (Exception e) {

            e.printStackTrace();
            Toast.makeText(HomeActivity.this, "更新失败", Toast.LENGTH_SHORT).show();
        }
        close();
    }

    @Override
    public void close() {

        if (xuhaoExitDialog != null && xuhaoExitDialog.isShowing()) {
            xuhaoExitDialog.dismiss();
        }
    }


    @Override
    protected void onPause() {

        super.onPause();

    }
    public boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        Log.d("packagename",pName.toString());
        return pName.contains(packageName);
    }


}
