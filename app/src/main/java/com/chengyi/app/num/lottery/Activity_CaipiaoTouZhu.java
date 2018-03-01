package com.chengyi.app.num.lottery;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.model.wanfa.AbsWanfa;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.Num2Str;
import com.chengyi.app.view.widget.XuanhaoLinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class Activity_CaipiaoTouZhu extends BaseActivity implements SensorEventListener {


    private Vibrator vibrator;
    private SensorManager sensorMgr;
    private TouzhuquerenData touzhuData;
    private XuanhaoLinearLayout xuanhaoView;
    private LinearLayout llMenu;

    private RelativeLayout contenthistorydown;
    private Caipiao cp;
    private LinearLayout tubiaoBtn;// 图表按钮
    //	Button wanfaBtn;// 玩法按钮

    private TextView btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11,
            btn12, btn13, btn14, btn15, btn16, btn17, btn18, btn19, btn20,
            btn21, btn22, btn23,btn24, currentBtn;
    private List<TextView> btnList = new ArrayList<>();
    // 从投注确认界面过来增加手选或修改的
    private boolean fromTouzhuqueren = false;


    public boolean isFromTouzhuqueren() {
        return fromTouzhuqueren;
    }

    private AbsWanfa cachCurrentWanFa;// 用来缓存当前页面所选择的玩法，防止继续投注时切换了玩法时使得大厅布局不一致了
    public boolean clearAllSelected = false;// 是否清空所选择的方案

    public boolean isClearAllSelected() {
        return clearAllSelected;
    }

    public void setClearAllSelected(boolean clearAllSelected) {
        this.clearAllSelected = clearAllSelected;
    }

    HashMap<String, String> map = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setFinishChoose(false);// 点击完成选号，会为true

        setContentView(R.layout.new_caipiaotz_frame);
        setBack();
        cp = getCurrentCaipiao();
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

        findViewById(R.id.footballffootballtopbarLayout).setOnClickListener(this);
        findViewById(R.id.tv_title).setOnClickListener(this);
        touzhuData = (TouzhuquerenData) getIntent().getSerializableExtra(
                "touzhudata");

        setCusTomeTitle(getCurrentCaipiao().getCurrentWanfaName());
        // 切换到当前彩种的当前玩法
        if (touzhuData != null
                && touzhuData.getWfType() != cp.getCurrentWanfa().getType()) {
            getCurrentCaipiao().setCurrentWanfa(
                    getCurrentCaipiao().getWanfaByType(touzhuData.getWfType()));
        }
        fromTouzhuqueren = getIntent().getBooleanExtra("fromtouzhu", false);
        initView();
    }

    public void initView() {

        if (AppUtil.isHideWanfa(getCurrentCaipiao())) {
            findViewById(R.id.iv_wanfa).setVisibility(View.INVISIBLE);

        }
        if (AppUtil.isHideZs(getCurrentCaipiao())) {
            findViewById(R.id.btn_chart).setVisibility(View.INVISIBLE);
        }
        ivAnim = (ImageView) findViewById(R.id.iv_anim);
        llMenu = (LinearLayout) findViewById(R.id.ll_menu);
        llMenu.setVisibility(View.VISIBLE);
        llMenu.setOnClickListener(this);
        xuanhaoView = (XuanhaoLinearLayout) findViewById(R.id.xuanhaoView);
        contenthistorydown = (RelativeLayout) findViewById(R.id.contenthistorydown);
        if (getCurrentCaipiao() != null && getCurrentCaipiao().getWanfaList() != null && getCurrentCaipiao().getWanfaList().size() > 1) {
            ivAnim.setVisibility(View.VISIBLE);
        } else {
            ivAnim.setVisibility(View.GONE);

        }
        findViewById(R.id.iv_select).setVisibility(View.GONE);


        xuanhaoView.setFinishChoseListener(this);
        tubiaoBtn = (LinearLayout) findViewById(R.id.btn_chart);
        tubiaoBtn.setOnClickListener(this);

        changeToCaipiaoDating();
    }

    private void sharkRandomSelect() {
        if (xuanhaoView.random()) {// 有的玩法没有机选 则不震动
            vibrator.vibrate(150);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorMgr.unregisterListener(this);


    }

    @Override
    protected void onStop() {
        if (xuanhaoView != null) {
            xuanhaoView.onpause();
        }
        super.onStop();

    }

    @Override
    protected void onResume() {

        super.onResume();
        if (xuanhaoView != null) {
            xuanhaoView.onresume();
        }
        sensorMgr.registerListener(this, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        if (!fromTouzhuqueren)
            getCurrentCaipiao().setCurrentWanfa(cachCurrentWanFa);
        if (clearAllSelected) {
            xuanhaoView.resetCaipiaoBtns();
            xuanhaoView.setDefaultSelected(null);
            xuanhaoView.onTouzhuCountChange();
            clearAllSelected = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        SharedPreferences preferences = CaipiaoUtil.getCpSharedPreferences(this);
        // 打开摇动选号设置才行
        if (preferences.getBoolean("dakaiyaodongxuanhao", true)) {
            int sensorType = event.sensor.getType();
            // values[0]:X轴，values[1]：Y轴，values[2]：Z轴
            float[] values = event.values;
            if (sensorType == Sensor.TYPE_ACCELEROMETER) {
                isShark(values);
            }
        }
    }

    private long lastStepTime = 0;
    private static final int SUB = 19;

    private void isShark(float[] values) {
        double d = Math.sqrt(values[0] * values[0] + values[1] * values[1]
                + values[2] * values[2]);
        if (d > SUB) {
            // 判定为一步后要等一下，不然读数会猛跳。
            long curTime = System.currentTimeMillis();
            if ((curTime - lastStepTime) > 500) {
                sharkRandomSelect();
                lastStepTime = curTime;
            }
        }
    }

    @Override
    public void onClick(View v) {

        map.clear();
        switch (v.getId()) {
            case R.id.tv_title:
            case R.id.footballffootballtopbarLayout: // 切换玩法
                if (wanfaPop != null && wanfaPop.isShowing()) {
                    dismiss();
                } else {

                    if (getCurrentCaipiao() != null && getCurrentCaipiao().getWanfaList() != null && getCurrentCaipiao().getWanfaList().size() > 1) {

                        AppUtil.startRotateAnim(ivAnim);
                        getWanFaPop().showAsDropDown(findViewById(R.id.footballffootballtopbarLayout));

                    }
                }
                break;
            case R.id.btn_chart:
                moveToTubiao();

                break;
            case R.id.ll_menu:
                moveToWanFa();

                break;
        }
    }

    @Override
    public void finish() {

        if (fromTouzhuqueren) {
            if (isFinishChoose()) {
                Intent intent = new Intent();
                TouzhuquerenData data = xuanhaoView.getTouzhuData();
                if (getRandomNum() == 0) {// isFinishChoose()为true时，data.getZhushu()肯定也大于0，因为在finishChoose时，先判断了
                    // 把返回数据存入Intent
                    intent.putExtra("touzhudata", data);
                    intent.putExtra("randomnumber", 0);
                    if (xuanhaoView.getListData() != null) {
                        intent.putExtra("listTouzhuDataHT", (Serializable) xuanhaoView.getListData());
                    }
                    // 设置返回数据
                    setResult(Activity.RESULT_OK, intent);
                    // 关闭Activity
                } else if (getRandomNum() > 0) {
                    intent.putExtra("randomnumber", getRandomNum());
                    intent.putExtra("intent_cpid", getCurrentCaipiao().getId());
                    intent.putExtra("intent_wftype", getCurrentCaipiao().getCurrentWanfa().getType());
                    // 设置返回数据
                    setResult(Activity.RESULT_OK, intent);
                }
            }
            super.finish();
            pullDownActivityAnim();
        } else
            super.finish();
    }

    // 机选N注 选号
    private int randomNum = 0;

    public void setRandomNum(int c) {
        randomNum = c;
    }

    public int getRandomNum() {
        return randomNum;
    }

    @Override
    public void changeToCaipiaoDating() {

        cp = getCurrentCaipiao();

        xuanhaoView.changeView(getCurrentCaipiao(), true);
        setDefalutSeleted();
        cachCurrentWanFa = cp.getCurrentWanfa();
    }

    private boolean isFinishChoose = false;// 点击完成选号或者随机选号,导致的finish，而不是点击关闭或者返回键（确认投注界面过来的），

    public boolean isFinishChoose() {
        return isFinishChoose;
    }

    public void setFinishChoose(boolean isFinishChoose) {
        this.isFinishChoose = isFinishChoose;
    }

    public void setDefalutSeleted() {
        if (touzhuData != null)
            xuanhaoView.setDefaultSelected(touzhuData);
    }


    /*
     * 玩法弹窗
     */
    PopupWindow wanfaPop;
    View vPopupWindow = null;
    LinearLayout top;

    boolean isFind;
    String[] str;

    public PopupWindow getWanFaPop() {
        if (vPopupWindow == null) {
            btnList.clear();
            vPopupWindow = getLayoutInflater().inflate(cp.getResource(), null,
                    false);
            wanfaPop = new PopupWindow(vPopupWindow, LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT);
            wanfaPop.setBackgroundDrawable(new BitmapDrawable());
            wanfaPop.setFocusable(true);
            wanfaPop.setOutsideTouchable(true);
            wanfaPop.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    AppUtil.startResRotateAnim(ivAnim);
                    top.clearAnimation();

                }
            });
            top = (LinearLayout) vPopupWindow
                    .findViewById(R.id.layouttopselect);
            startAnimation();
            try {
                wanfaPop.setAnimationStyle(R.style.SelectPopWindowAnimation);
            } catch (Exception e) {
                e.printStackTrace();
            }

            vPopupWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wanfaPop != null && wanfaPop.isShowing()) {
                        dismiss();
                    }
                }
            });
            btn1 = (TextView) vPopupWindow.findViewById(R.id.btn1);
            btnList.add(btn1);
            btn2 = (TextView) vPopupWindow.findViewById(R.id.btn2);
            btnList.add(btn2);
            if (vPopupWindow.findViewById(R.id.btn3) != null) {
                btn3 = (TextView) vPopupWindow.findViewById(R.id.btn3);
                btnList.add(btn3);
            }
            if (vPopupWindow.findViewById(R.id.btn4) != null) {
                btn4 = (TextView) vPopupWindow.findViewById(R.id.btn4);
                btnList.add(btn4);
            }
            if (vPopupWindow.findViewById(R.id.btn5) != null) {
                btn5 = (TextView) vPopupWindow.findViewById(R.id.btn5);
                btnList.add(btn5);
            }
            if (vPopupWindow.findViewById(R.id.btn6) != null) {
                btn6 = (TextView) vPopupWindow.findViewById(R.id.btn6);
                btnList.add(btn6);
            }
            if (vPopupWindow.findViewById(R.id.btn7) != null) {
                btn7 = (TextView) vPopupWindow.findViewById(R.id.btn7);
                btnList.add(btn7);
            }
            if (vPopupWindow.findViewById(R.id.btn8) != null) {
                btn8 = (TextView) vPopupWindow.findViewById(R.id.btn8);
                btnList.add(btn8);
            }
            if (vPopupWindow.findViewById(R.id.btn9) != null) {
                btn9 = (TextView) vPopupWindow.findViewById(R.id.btn9);
                btnList.add(btn9);
            }
            if (vPopupWindow.findViewById(R.id.btn10) != null) {
                btn10 = (TextView) vPopupWindow.findViewById(R.id.btn10);
                btnList.add(btn10);
            }
            if (vPopupWindow.findViewById(R.id.btn11) != null) {
                btn11 = (TextView) vPopupWindow.findViewById(R.id.btn11);
                btnList.add(btn11);
            }
            if (vPopupWindow.findViewById(R.id.btn12) != null) {
                btn12 = (TextView) vPopupWindow.findViewById(R.id.btn12);
                btnList.add(btn12);
            }
            if (vPopupWindow.findViewById(R.id.btn13) != null) {
                btn13 = (TextView) vPopupWindow.findViewById(R.id.btn13);
                btnList.add(btn13);
            }
            if (vPopupWindow.findViewById(R.id.btn14) != null) {
                btn14 = (TextView) vPopupWindow.findViewById(R.id.btn14);
                btnList.add(btn14);
            }
            if (vPopupWindow.findViewById(R.id.btn15) != null) {
                btn15 = (TextView) vPopupWindow.findViewById(R.id.btn15);
                btnList.add(btn15);
            }
            if (vPopupWindow.findViewById(R.id.btn16) != null) {
                btn16 = (TextView) vPopupWindow.findViewById(R.id.btn16);
                btnList.add(btn16);
            }
            if (vPopupWindow.findViewById(R.id.btn17) != null) {
                btn17 = (TextView) vPopupWindow.findViewById(R.id.btn17);
                btnList.add(btn17);
            }
            //  11选5玩法一共有23个
            if (CaipiaoUtil.is11xr5(cp.getId())) {
                btn18 = (TextView) vPopupWindow.findViewById(R.id.btn18);
                btnList.add(btn18);
                btn19 = (TextView) vPopupWindow.findViewById(R.id.btn19);
                btnList.add(btn19);
                btn20 = (TextView) vPopupWindow.findViewById(R.id.btn20);
                btnList.add(btn20);
                btn21 = (TextView) vPopupWindow.findViewById(R.id.btn21);
                btnList.add(btn21);
                btn22 = (TextView) vPopupWindow.findViewById(R.id.btn22);
                btnList.add(btn22);
                btn23 = (TextView) vPopupWindow.findViewById(R.id.btn23);
                btnList.add(btn23);
                btn24= (TextView) vPopupWindow.findViewById(R.id.btn24);
                btnList.add(btn24);
            }


            if (btnList != null) {
                for (TextView tv : btnList  ) {
                    tv.setText(Num2Str.num2Str(tv.getText().toString()));

                }

            }

            if (cp.getId() == CaipiaoConst.ID_FUCAI3D
                    || cp.getId() == CaipiaoConst.ID_PAILIE3
                    || cp.getId() == CaipiaoConst.ID_KUAILE10FEN
                    || CaipiaoUtil.is11xr5(cp.getId())
                    || cp.getId() == CaipiaoConst.ID_LAOSHISHICAI
                    || cp.getId() == CaipiaoConst.ID_XINSHISHICAI) {
                str = cp.getCurrentWanfa().getName().split("-");
                isFind = false;
                int flag = 0;
                if (str.length > 1 && str[0].equals("普通")) {
                    if (cp.getId() == CaipiaoConst.ID_KUAILE10FEN
                            || cp.getId() == CaipiaoConst.ID_LAOSHISHICAI)
                        flag = 8;
                    else if (CaipiaoUtil.is11xr5(cp.getId()) || cp.getId() == CaipiaoConst.ID_XINSHISHICAI)
                        flag = 11;
                    else
                        flag = 3;
                    for (int i = 0; i < btnList.size(); i++) {
                        TextView b = btnList.get(i);
                        if (i <= flag)
                            b.setTag("普通");
                        else {
                            b.setTag("胆拖");
                        }
                        b.setOnClickListener(listener);
                        if (!isFind && Num2Str.num2Str(b.getText().toString()).equals(str[1])) {
                            currentBtn = b;
                            isFind = true;
                        }
                    }
                } else if (str.length > 1 && str[0].equals("胆拖")) {
                    if (cp.getId() == CaipiaoConst.ID_KUAILE10FEN)
                        flag = 9;
                    else if (CaipiaoUtil.is11xr5(cp.getId()))
                        flag = 12;
                    else
                        flag = 4;
                    for (int i = btnList.size() - 1; i >= 0; i--) {
                        TextView b = btnList.get(i);
                        if (i >= flag)
                            b.setTag("胆拖");
                        else
                            b.setTag("普通");
                        b.setOnClickListener(listener);
                        if (!isFind &&(b.getText().toString()).equals(str[1])) {
                            currentBtn = b;
                            isFind = true;
                        }
                    }
                }
            } else {
                for (TextView b : btnList) {
                    b.setOnClickListener(listener);
                    if ((b.getText().toString()).equals(cp.getCurrentWanfa().getName())) {
                        currentBtn = b;
                    }
                }
            }
            if (currentBtn!=null)
                currentBtn.setSelected(true);
        } else {
            startAnimation();
        }
        return wanfaPop;
    }

    private void startAnimation() {
        top.startAnimation(mShowAction);
        mShowAction.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {

                top.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }
        });
    }

    private ImageView ivAnim;

    private void dismiss() {
        if (wanfaPop != null && wanfaPop.isShowing()) {
            top.startAnimation(set);
            wanfaPop.dismiss();
            top.setVisibility(View.INVISIBLE);

        }
    }

    private String getBtnStr(TextView b) {
        if (b.getTag() == null)
            return b.getText().toString();
        else if (b.getTag().toString().equals("普通"))

            return "普通-" + b.getText().toString();
        else if (b.getTag().toString().equals("胆拖"))
            return "胆拖-" + b.getText().toString();
        else
            return "";
    }

    View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            map.clear();
            final TextView b = (TextView) v;
            String bStr = getBtnStr(b);
            String cStr = getBtnStr(currentBtn);
            if (bStr.equals(cStr)) {
                dismiss();
                return;
            }
            currentBtn.setSelected(false);
            b.setSelected(true);

            cp.setCurrentWanfa(cp.getWanfaByName(bStr));
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                    currentBtn = b;
                }
            }, 100);
            xuanhaoView.changeView(getCurrentCaipiao(), false);
            // 切换彩种的时候来自投注确认页面
            if (fromTouzhuqueren && touzhuData != null
                    && touzhuData.getWfType() == cp.getCurrentWanfa().getType()) {
                setDefalutSeleted();
            }

            cachCurrentWanFa = cp.getCurrentWanfa();
            setCusTomeTitle(getCurrentCaipiao().getCurrentWanfaName());
        }

    };

    public XuanhaoLinearLayout getXuanhaoView() {
        return xuanhaoView;
    }

    public void setXuanhaoView(XuanhaoLinearLayout xuanhaoView) {
        this.xuanhaoView = xuanhaoView;
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        if (cp.getId() != getCurrentCaipiao().getId()) {
            vPopupWindow = null;
            clearAllSelected = false;
            changeToCaipiaoDating();
        }
    }


}
