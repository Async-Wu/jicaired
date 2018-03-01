package com.chengyi.app.jingji.renxuan;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.model.RenXuanNineData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.YOUMENG_EVENT;
import com.chengyi.app.view.scoller.MyRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.chengyi.app.util.net.NetUtil.isNetworkAvailable;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class Activity_RenXuanQiuChang extends BaseActivity implements
        SensorEventListener {
    private ImageView ivAnim;

    private RelativeLayout loadLayout, failedLayout, mainLayout, noDataLayout, rlPop;
    LinearLayout ftcenterLayout;
    private Caipiao cp;
    private RequestParams params;
    private String issueStr = "";
    private Button wfBtn, finishBtn;

    private TextView qiShuText, jieZhiText,
            qingKong, zhuText, moneyText
//            , yaoText
            ;
    private ArrayList<RenXuanNineData> list = new ArrayList<RenXuanNineData>();
    MyRefreshListView listView;
    RenXuanNineAdapter adapter;
    boolean isReflesh = false;
    private String sellEndTime = "";
    /**
     * 是否从投注确认页面跳转过来的
     */
    boolean isFromTouZhuQueRen = false;
    Button btnOne, btnTwo, btnThree, btnFour;
    JSONArray arrayIssue;
    Button currentBtn;
    private Vibrator vibrator;
    private SensorManager sensorMgr;
    private ArrayList<String> selectedList = new ArrayList<String>();
    /**
     * 需要选择的比赛场次，任选9场需选择9场
     */
    private static int GAME_NUM = 9;
    /**
     * 每场比赛三个按钮代表的含义
     */
    private static String[] TOUZHUSTR = new String[]{"3", "1", "0"};
    /**
     * 所选择的的比赛一共有多少注
     */
    private Integer zhuShu = 0;
    /**
     * 定义一个handler用来和适配器交互
     */
    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            if (msg.what == 0) {
                showToast("最多选择9场比赛");
            } else if (msg.what == 1) {
                zhuShu = getTouZhuNum();
                zhuText.setText(zhuShu + "");
                moneyText.setText(zhuShu * 2 + "");
                if (selectedList.size() > 0) {
//                    yaoText.setVisibility(View.GONE);
//                    zhuShuLayout.setVisibility(View.VISIBLE);
                } else {
//                    yaoText.setVisibility(View.VISIBLE);
//                    zhuShuLayout.setVisibility(View.GONE);
                }

            } else if (msg.what == 2) {
                showToast("至少选择1注");
            }
        }
    };

    /**
     * 定义一个intent用来跳转到投注确认页面
     */
    Intent intent;
    /**
     * 是否清空所选择的方案
     */
    public boolean clearAllSelected = false;
    /**
     * 记录每次请求的数据
     */
    private String responseStr = "";

    /**
     * 投注确认返回来的编辑数据
     */
    String editData;
    /**
     * 数据请求的URL
     */
    private String URL;
    SharedPreferences preferences;
    Editor editor;
    /**
     * 最后一次缓存数据的时间
     */
    long lastCacheTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery_shengfu14chang);

        findViewById(R.id.iv_wanfa).setOnClickListener(this);
        findViewById(R.id.iv_select).setVisibility(View.INVISIBLE);
        ivAnim = (ImageView) findViewById(R.id.iv_anim);
        cp = CaipiaoApplication.getInstance().getCurrentCaipiao();
        GAME_NUM = cp.getQianquMinCount();
        if (cp.getId() == CaipiaoConst.ID_SHENGFU14CHANG)
            URL = URLSuffix.SHENGFU14CHANG;
        else
            URL = URLSuffix.SHENGFU9CHANG;
        params = getRequestParams();

        preferences = CaipiaoUtil.getCpSharedPreferences(this);
        editor = preferences.edit();
        sensorMgr = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        intent = new Intent(this, Activity_RenXuanQiuChang_EnSure.class);
        isFromTouZhuQueRen = getIntent().getBooleanExtra("fromtouzhu", false);
        if (isFromTouZhuQueRen) {
            responseStr = getIntent().getStringExtra("listData");
            editData = getIntent().getStringExtra("touzhudata");
        } else
            lastCacheTime = preferences.getLong(cp.getId() + "lastcachetime", 0);
        initView();

        ftcenterLayout.setVisibility(View.GONE);
        if (!isFromTouZhuQueRen)
            loadData();
        else {
            startLoadAnim();
            handler.post(requestData);
        }
    }

    @Override
    protected void onPause() {

        super.onPause();
        sensorMgr.unregisterListener(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
        sensorMgr.registerListener(this,
                sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        if (clearAllSelected) {
            clearData();
            clearAllSelected = false;
        }
    }

    /**
     * 控件的初始化
     */
    private void initView() {
        ftcenterLayout = (LinearLayout) findViewById(R.id.footballffootballtopbarLayout);
        if (!isFromTouZhuQueRen)
            ftcenterLayout.setOnClickListener(this);

        rlPop = (RelativeLayout) findViewById(R.id.rl_pop);
        rlPop.setOnClickListener(this);
        loadLayout = (RelativeLayout) findViewById(R.id.loaddata);
        failedLayout = (RelativeLayout) findViewById(R.id.failed);
        failedLayout.setOnClickListener(this);
        mainLayout = (RelativeLayout) findViewById(R.id.mainlayout);
        noDataLayout = (RelativeLayout) findViewById(R.id.nodata);
        wfBtn = (Button) findViewById(R.id.wanfabutton);
        wfBtn.setOnClickListener(this);
        setBack();

        qiShuText = (TextView) findViewById(R.id.jiangqitextview);
        jieZhiText = (TextView) findViewById(R.id.jiangqihaoma);
        listView = (MyRefreshListView) findViewById(R.id.pulllayout);
        qingKong = (TextView) findViewById(R.id.qingkong);
        qingKong.setOnClickListener(this);
        zhuText = (TextView) findViewById(R.id.xuanhaotishi1);
        moneyText = (TextView) findViewById(R.id.xuanhaotishi2);
        finishBtn = (Button) findViewById(R.id.ensurebtn);
        finishBtn.setOnClickListener(this);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setFadingEdgeLength(0);
        findViewById(R.id.footballffootballtopbarLayout).setOnClickListener(this);
//		listView.setDividerHeight(0);
        listView.setonRefreshListener(new MyRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                isReflesh = true;
                clearData();
                handler.post(requestData);
            }
        });
        adapter = new RenXuanNineAdapter(this);
        adapter.setList(list);
        adapter.setHandler(myHandler);
        adapter.setSelectedList(selectedList);
        adapter.setCpId(cp.getId());
        listView.setAdapter(adapter);
        if (isFromTouZhuQueRen) {

        }
//        yaoText = (TextView) findViewById(R.id.shoujiyaoyao);
        zhuShuLayout = (LinearLayout) findViewById(R.id.zhushulayout);
    }

    /*
     * 请求网络数据
     */
    private void loadData() {
        startLoadAnim();
        if (!isNetworkAvailable(this)) {
            failedLayout.setVisibility(View.VISIBLE);
            loadLayout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.GONE);
        } else {
            handler.post(requestData);
        }
    }

    Runnable requestData = new Runnable() {
        @Override
        public void run() {

            if (!isFromTouZhuQueRen || isReflesh)
                getData();
            else
                try {
                    if (!TextUtils.isEmpty(responseStr))
                        ResponseHandler.onSuccess(
                                responseStr);
                    else
                        getData();
                } catch (Exception e) {

                    e.printStackTrace();
                    getData();
                }
        }
    };

    // 网络请求数据
    private void getData() {
        /**
         * 从缓存中取数据
         */
        responseStr = preferences.getString(cp.getId() + issueStr, "");
        if (!TextUtils.isEmpty(responseStr) && !isReflesh) {
            try {
                //cSystem.out.println("缓存数据了...........");
                ResponseHandler.onSuccess(
                        responseStr);
            } catch (Exception e) {

                e.printStackTrace();
                params.put("issue", issueStr);
                HttpBusinessAPI.post(URL, params, ResponseHandler);
            }
        } else {
            /**
             * 网络请求数据
             */
            params.put("issue", issueStr);
            HttpBusinessAPI.post(URL, params, ResponseHandler);
            //System.out.println("网络请求数据...........");
        }
    }

    HttpRespHandler ResponseHandler = new HttpRespHandler() {

        @Override
        public void onSuccess(String response) {

            super.onSuccess(response);

            if (JSON.parseObject(response).getIntValue(URLSuffix.flag) != 0) {
                responseStr = response.toString();
                list.clear();
                selectedList.clear();
                JSONArray array = JSON.parseObject(response).getJSONArray("data");
                String[] hmStr = null;
                if (null != editData && !TextUtils.isEmpty(editData))
                    hmStr = editData.split("-");
                for (int i = 0; i < array.size(); i++) {
                    RenXuanNineData dt = new RenXuanNineData(
                            array.getJSONObject(i), i + 1);
                    if (isFromTouZhuQueRen && null != editData && !TextUtils.isEmpty(editData)) {
                        try {
                            if (!hmStr[i].equals("*")) {
                                dt.getBuffer().append(hmStr[i]);
                                dt.setSelectedNum(hmStr[i].length());
                                selectedList.add(String.valueOf(i));
                            }
                        } catch (Exception e) {


                            e.printStackTrace();
                        }
                    }
                    list.add(dt);
                }
                issueStr = JSON.parseObject(response).getString("curIssue");
                sellEndTime = JSON.parseObject(response).getString("sellEndTime");
                if (!isFromTouZhuQueRen
                        && (TextUtils.isEmpty(cp.getIssue()) || issueStr.equals(cp
                        .getIssue()))) {
                    // 设置当前彩种的奖期数据
                    cp.setIssue(JSON.parseObject(response).getString("curIssue"));
                    cp.setIssueId(JSON.parseObject(response).getIntValue("curIssueId"));
                    cp.setRemainTime(JSON.parseObject(response).getIntValue("remainTime"));
                }
                setJiangQiData();
                arrayIssue = JSON.parseObject(response).getJSONArray("issue");
                //System.out.println("arrayIssue:"+arrayIssue);
                if (isReflesh) {
                    isReflesh = false;
                    listView.onRefreshComplete();
                } else {
                    loadLayout.setVisibility(View.GONE);
                    mainLayout.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();
                // 刷新后或者重新加载数据后
                myHandler.sendEmptyMessage(1);
                if (!isFromTouZhuQueRen) {
                    //清空缓存数据，防止缓存的数据不断的增长，5天时间为限
                    if (lastCacheTime != 0 && lastCacheTime > 5 * 24 * 60 * 60 * 1000) {
                        editor.clear();
                        lastCacheTime = 0;
                    }
                    if (lastCacheTime == 0) {
                        editor.putLong(cp.getId() + "lastcachetime", System.currentTimeMillis());
                    }
                    //缓存数据
                    editor.putString(cp.getId() + issueStr, responseStr);
                    editor.commit();
                }
            } else if (JSON.parseObject(response).getIntValue(URLSuffix.flag) == 0) {
                loadLayout.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.VISIBLE);
                failedLayout.setVisibility(View.GONE);
            } else if (JSON.parseObject(response).getIntValue(URLSuffix.flag) == 0) {
                loadLayout.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.GONE);
                failedLayout.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onFailure(Throwable error) {

            super.onFailure(error);
            loadLayout.setVisibility(View.GONE);
            noDataLayout.setVisibility(View.GONE);
            failedLayout.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onClick(View v) {

        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_pop:
                dismiss();
                break;

            case R.id.failed:
                reLoadData();
                break;
            case R.id.iv_wanfa:
                Toast.makeText(this, "暂无该玩法介绍", Toast.LENGTH_SHORT).show();
                break;


            case R.id.tv_title:
            case  R.id.footballffootballtopbarLayout:

                if (rlPop != null && rlPop.getVisibility() == View.VISIBLE) {
                    dismiss();
                } else {
                    AppUtil.startRotateAnim(ivAnim);
                    getSelectPop();

                }
//                        .showAsDropDown(findViewById(R.id.footballffootballtopbarLayout));
                break;
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
                dismiss();
                currentBtn.setSelected(false);
                v.setSelected(true);
                currentBtn = (Button) v;
                issueStr = String.valueOf(v.getTag());
                setCusTomeTitle(cp.getName() + "-" + issueStr + "期");
                clearData();
                reLoadData();
                break;
            case R.id.qingkong:
                clearData();
                break;
            case R.id.ensurebtn:
                if (selectedList.size() == 0)
                    random();
                else if (selectedList.size() > 0 && zhuShu == 0)
                    myHandler.sendEmptyMessage(2);
                else {
                    intent.putExtra("touzhudata", getTouZhuString());
                    intent.putExtra("listData", responseStr);
                    intent.putExtra("zhuShu", zhuShu);
                    if (!isFromTouZhuQueRen) {
                        clearAllSelected = true;
                        startActivity(intent);

                    } else {
                        setResult(RESULT_OK, intent);
                        finish();

                    }
                }
                break;
        }
    }

    /**
     * 重新请求数据
     */
    private void reLoadData() {
        loadLayout.setVisibility(View.VISIBLE);
        failedLayout.setVisibility(View.GONE);
        mainLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                loadData();
            }
        }, 500);
    }

    // 设置奖期和倒计时
    private void setJiangQiData() {
        qiShuText.setText("截止时间:");
        if (!TextUtils.isEmpty(sellEndTime)) {
            jieZhiText.setText(sellEndTime);
        }

        ftcenterLayout.setVisibility(View.VISIBLE);

        setCusTomeTitle(cp.getName() + "-" + issueStr + "期");

    }

    View vPopupWindow;
    // PopupWindow wanfaChangePop;
    LinearLayout top, zhuShuLayout;
    ImageView img;

    private void getSelectPop() {
        if (vPopupWindow == null) {
            vPopupWindow = getLayoutInflater().inflate(
                    R.layout.pop_shengfu14changwanfa, null, false);
//            wanfaChangePop = new PopupWindow(vPopupWindow,
//                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
//            wanfaChangePop.setBackgroundDrawable(new BitmapDrawable());
//            wanfaChangePop.setFocusable(true);
//            wanfaChangePop.setOutsideTouchable(true);
            top = (LinearLayout) vPopupWindow
                    .findViewById(R.id.layouttopselect);
//			top.startAnimation(mShowAction);
            top.setVisibility(View.VISIBLE);
//			try {
//				wanfaChangePop
//						.setAnimationStyle(R.style.SelectPopWindowAnimation);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
            img = (ImageView) vPopupWindow.findViewById(R.id.imageView1);
            btnOne = (Button) vPopupWindow.findViewById(R.id.button1);
            btnOne.setOnClickListener(this);
            btnTwo = (Button) vPopupWindow.findViewById(R.id.button2);
            btnTwo.setOnClickListener(this);
            btnThree = (Button) vPopupWindow.findViewById(R.id.button3);
            btnThree.setOnClickListener(this);
            btnFour = (Button) vPopupWindow.findViewById(R.id.button4);
            btnFour.setOnClickListener(this);
//            vPopupWindow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (wanfaChangePop != null && wanfaChangePop.isShowing()) {
//                        dismiss();
//                    }
//                }
//            });
        } else {
//			top.startAnimation(mShowAction);
            top.setVisibility(View.VISIBLE);
//			img.startAnimation(am1);
        }
        if (null != arrayIssue) {
            try {
                btnOne.setText(arrayIssue.getString(0) + "期");
                btnOne.setTag(arrayIssue.getString(0));
                if (arrayIssue.size() > 1) {
                    btnTwo.setVisibility(View.VISIBLE);
                    btnTwo.setText(arrayIssue.getString(1) + "期");
                    btnTwo.setTag(arrayIssue.getString(1));
                } else
                    btnTwo.setVisibility(View.GONE);
                if (arrayIssue.size() > 2) {
                    btnThree.setVisibility(View.VISIBLE);
                    btnThree.setText(arrayIssue.getString(2) + "期");
                    btnThree.setTag(arrayIssue.getString(2));
                } else
                    btnThree.setVisibility(View.GONE);
                if (issueStr.equals(arrayIssue.getString(0))) {
                    btnOne.setSelected(true);
                    currentBtn = btnOne;
                } else if (arrayIssue.size() > 1 && issueStr.equals(arrayIssue.getString(1))) {
                    btnTwo.setSelected(true);
                    currentBtn = btnTwo;
                } else if (arrayIssue.size() > 2 && issueStr.equals(arrayIssue.getString(2))) {
                    btnThree.setSelected(true);
                    currentBtn = btnThree;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (arrayIssue.size() > 3) {
                btnFour.setText(arrayIssue.getString(3) + "期");
                btnFour.setTag(arrayIssue.getString(3));
                if (issueStr.equals(arrayIssue.getString(3))) {
                    btnFour.setSelected(true);
                    currentBtn = btnFour;
                }
            } else
                btnFour.setVisibility(View.GONE);
        }

        rlPop.removeAllViews();
        rlPop.addView(vPopupWindow);
        rlPop.setVisibility(View.VISIBLE);
//        return wanfaChangePop;
    }

    private void dismiss() {
//		top.startAnimation(set);
//		top.setVisibility(View.INVISIBLE);
//		if (img != null)
//			img.startAnimation(am);
//		handler.postDelayed(new Runnable() {
//			@Override
//			public void run() {


        if (rlPop != null && rlPop.getVisibility() == View.VISIBLE) {
            rlPop.setVisibility(View.GONE);
            AppUtil.startResRotateAnim(ivAnim);

        }
//        wanfaChangePop.dismiss();
//			}
//		}, 100);
    }

    /**
     * 计算注数
     */
    private int getTouZhuNum() {
        if (selectedList.size() < cp.getQianquMinCount())
            return 0;
        int count = 1;
        for (String s : selectedList) {
            count = count * list.get(Integer.parseInt(s)).getSelectedNum();
        }
        return count;
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

    private void sharkRandomSelect() {


        random();
        vibrator.vibrate(150);
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("彩种", getCurrentCaipiao().getName());
            CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_yaodongxuanhao,
                    map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 机选一注
     */
    public void random() {
        clearData();
        int[] randomArray = CaipiaoUtil.getRandomArray(14, GAME_NUM);
        for (int i = 0; i < randomArray.length; i++) {
            selectedList.add(String.valueOf(randomArray[i]));
            RenXuanNineData data = list.get(randomArray[i]);
            data.setSelectedNum(1);
            data.getBuffer().append(
                    TOUZHUSTR[CaipiaoUtil.getRandomArray(3, 1)[0]]);
        }
        adapter.notifyDataSetChanged();
        zhuShu = 1;
        zhuText.setText(1 + "");
        moneyText.setText(2 + "");
//        yaoText.setVisibility(View.GONE);
        zhuShuLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onNewIntent(Intent intent) {

        super.onNewIntent(intent);
        if (cp.getId() != getCurrentCaipiao().getId()) {
            vPopupWindow = null;
            cp = CaipiaoApplication.getInstance().getCurrentCaipiao();
            GAME_NUM = cp.getQianquMinCount();


            adapter.setCpId(cp.getId());
            if (cp.getId() == CaipiaoConst.ID_SHENGFU14CHANG)
                URL = URLSuffix.SHENGFU14CHANG;
            else
                URL = URLSuffix.SHENGFU9CHANG;
            reLoadData();
        }
    }

    /**
     * 清空已选的数据
     */
    private void clearData() {
        zhuShu = 0;


        if (selectedList.size() > 0) {
            for (String s : selectedList) {
                RenXuanNineData data;
                try {
                    data = list.get(Integer.parseInt(s));
                    StringBuffer bf = data.getBuffer();
                    bf.delete(0, bf.length());
                    data.setSelectedNum(0);
                } catch (NumberFormatException e) {

                    e.printStackTrace();
                }
            }
            selectedList.clear();
            zhuText.setText("0");
            moneyText.setText("0");
//            yaoText.setVisibility(View.VISIBLE);
//            zhuShuLayout.setVisibility(View.GONE);

            adapter.notifyDataSetChanged();
        }
    }


    /**
     * 已选的比赛拼接成字符串
     */
    private String getTouZhuString() {
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            RenXuanNineData data = list.get(i);
            if (data.getSelectedNum() > 0)
                s += data.getBuffer().toString() + "-";
            else
                s += "*-";
        }
        return s;
    }
}
