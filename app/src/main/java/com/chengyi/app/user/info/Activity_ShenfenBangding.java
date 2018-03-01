package com.chengyi.app.user.info;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.ClearEditText;
import com.chengyi.app.view.widget.WheelView;
import com.chengyi.app.view.widget.adapters.ArrayWheelAdapter;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_ShenfenBangding extends BaseActivity {

    ClearEditText shenfenzheng;
    ClearEditText xingming;
    TextView  zhengjianhaomatext;
    View vPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_shenfenbangding);

        setCusTomeTitle("身份绑定");
        shenfenzheng = (ClearEditText) findViewById(R.id.zhengjihaoma);
        xingming = (ClearEditText) findViewById(R.id.zhenshixingming);
        setBack();
        findViewById(R.id.querenbtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(xingming.getText().toString())) {
                            showToast("姓名不能为空");
                            return;
                        }
                        if (TextUtils.isEmpty(shenfenzheng.getText().toString())) {
                            showToast("证件号码不能为空");
                            return;
                        }
                        if (zhengjianhaomatext.getText().equals("身份证号:") && !CaipiaoUtil.isShenfenzheng(shenfenzheng.getText().toString())) {
                            showToast("身份证格式不正确");
                            return;
                        }
                        showLoading("提交中...");
                        RequestParams params = getRequestParams();
                        params.put(URLSuffix.identityNumber, shenfenzheng.getText().toString().trim());
                        params.put(URLSuffix.name, xingming.getText().toString().trim());
                        HttpBusinessAPI.post(URLSuffix.BIND_IDENTIFY_NAME_test,
                                params, new HttpRespHandler() {
                                    @Override
                                    public void onSuccess(String result) {

                                        hideLoading();
                                        if (checkResult(result)) {
                                            showToast("身份绑定成功!");
                                            Intent data = new Intent();
                                            data.putExtra("name", xingming.getText().toString().trim());
                                            data.putExtra("identityNumber", shenfenzheng.getText().toString().trim());
                                            //请求代码可以自己设置，这里设置成20
                                            setResult(1, data);
                                            //关闭掉这个Activity
                                            finish();
                                        }
                                    }
                                });
                    }
                });
        zhengjianhaomatext = (TextView) findViewById(R.id.zhengjianhaomatext);
        zhengjianhaomatext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getWheelViewPop().showAtLocation(Activity_ShenfenBangding.this.findViewById(R.id.mainlayout), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancel:
            case R.id.imagebg:
                if (wheelViewPop != null && wheelViewPop.isShowing())
                    wheelViewPop.dismiss();
                break;
            case R.id.ensure:
                if (wheelView.getCurrentItem() != str.length - 1)
                    zhengjianhaomatext.setText(str[wheelView.getCurrentItem()] + "号:");
                else
                    zhengjianhaomatext.setText(str[wheelView.getCurrentItem()] + "号码:");
                wheelViewPop.dismiss();
                break;
        }
    }

    PopupWindow wheelViewPop;  //区域选择性弹窗
    Button cancel, ensure;
    WheelView wheelView;
    String str[] = new String[]{"身份证", "军官证", "台胞证", "护照"};

    private PopupWindow getWheelViewPop() {
        if (vPopupWindow == null) {
            vPopupWindow = getLayoutInflater().inflate(R.layout.pop_wheelview, null, false);
            wheelViewPop = new PopupWindow(vPopupWindow, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            wheelViewPop.setBackgroundDrawable(new BitmapDrawable());
            wheelViewPop.setFocusable(true);
            wheelViewPop.setOutsideTouchable(true);
            try {
                wheelViewPop.setAnimationStyle(R.style.PopWindowAnimation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cancel = (Button) vPopupWindow.findViewById(R.id.cancel);
            cancel.setOnClickListener(this);
            ensure = (Button) vPopupWindow.findViewById(R.id.ensure);
            ensure.setOnClickListener(this);
            vPopupWindow.findViewById(R.id.wheelviewtwo).setVisibility(View.GONE);
            wheelView = (WheelView) vPopupWindow.findViewById(R.id.wheelviewone);
            wheelView.setVisibleItems(5);
            //wheelView.setCyclic(true);
            wheelView.setViewAdapter(new ArrayWheelAdapter<String>(this, str));
            wheelView.setCurrentItem(0);
            vPopupWindow.findViewById(R.id.imagebg).setOnClickListener(this);
        }
        return wheelViewPop;
    }

}
