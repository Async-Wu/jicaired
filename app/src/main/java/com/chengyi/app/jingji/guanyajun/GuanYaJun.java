package com.chengyi.app.jingji.guanyajun;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.model.GuanYaJunData;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.net.control.manage.GuanYaJunManager;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.view.scoller.MyRefreshListView;

import java.text.ParseException;
import java.util.ArrayList;

import static com.chengyi.app.util.net.NetUtil.isNetworkAvailable;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class GuanYaJun extends BaseActivity {

    private Button wfBtn, finishBtn;

    private RelativeLayout loadLayout, failedLayout, mainLayout, noDataLayout;
    LinearLayout ftcenterLayout;
    private TextView qiShuText, jieZhiText,  clearText,
            qiuDuiNumText, zhuText, moneyText;
    MyRefreshListView listView;
    Caipiao cp;
    int wfindex = 0;// 玩法类型标记0：世界杯，1：欧冠杯
    /**
     * 数据请求的url
     */
    private String URL;
    private RequestParams params;
    /**
     * 是否下拉刷新
     */
    boolean isReflesh = false;
    GuanYaJunAdapter adapter;

    /**
     * 是否从投注确认页面跳转过来的
     */
    boolean isFromTouZhuQueRen = false;
    private int zhuShuNum = 0;
    /**
     * 弹窗中的按钮
     */
    Button btnOne, btnTwo, currentBtn;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    zhuShuNum++;
                    break;
                case 2:
                    zhuShuNum--;
                    break;
            }
            setZhuAndMoney();
        }
    };
    /**
     * 定义一个intent用来跳转到投注确认页面
     */
    Intent intent;
    /**
     * 所选择的球队编号列表
     */
    ArrayList<String> selectedList = new ArrayList<String>();

    ArrayList<GuanYaJunData> datalist = new ArrayList<GuanYaJunData>();

    /**
     * 记录每次请求的数据
     */
    private String responseStr = "";
    /**
     * 是否清空所选择的方案
     */
    public boolean clearAllSelected = false;

    public int callbackId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery_shengfu14chang);
        cp = getCurrentCaipiao();
        params = getRequestParams();
        intent = new Intent(this, GuanYaJunCart.class);
        isFromTouZhuQueRen = getIntent().getBooleanExtra("fromtouzhu", false);
        wfindex = getIntent().getIntExtra("wfindex", 0);
        if (!isFromTouZhuQueRen) {
            switch (getCurrentCaipiao().getCurrentWanfa().getType()) {
                case CaipiaoConst.WF_SHIJIE_BEI:
                    wfindex = 0;
                    break;
                case CaipiaoConst.WF_SHIJIE_BEI_GUANYAJUN:
                    wfindex = 1;
                    break;
            }
        } else {
            selectedList = getIntent().getStringArrayListExtra("selectedData");
            zhuShuNum = getIntent().getIntExtra("zhuShu", 0);
            cp.setCurrentWanfa(cp.getWanfaList().get(wfindex));
        }
        initView();
        fillView();
        callbackId = Control.getInstance().getGYJManager().creatCallback(dataCallback);
        Control.getInstance().getGYJManager().currentCallback = callbackId;
        loadData();
    }

    @Override
    protected void onResume() {

        super.onResume();
        if (clearAllSelected) {
            clearData();
            clearAllSelected = false;
        }
        Control.getInstance().getGYJManager().currentCallback = callbackId;
    }

    /**
     * 初始化控件
     **/
    private void initView() {

        qiShuText = (TextView) findViewById(R.id.jiangqitextview);
        jieZhiText = (TextView) findViewById(R.id.jiangqihaoma);
        findViewById(R.id.shoujiyaoyao).setVisibility(View.GONE);
        findViewById(R.id.zhushulayout).setVisibility(View.VISIBLE);
        findViewById(R.id.touzhuqiudui).setVisibility(View.VISIBLE);
        qiuDuiNumText = (TextView) findViewById(R.id.qiuduinum);
        qiuDuiNumText.setVisibility(View.VISIBLE);
        // 清空按钮
        clearText = (TextView) findViewById(R.id.qingkong);
        clearText.setOnClickListener(this);
        // 完成按钮
        finishBtn = (Button) findViewById(R.id.ensurebtn);
        finishBtn.setOnClickListener(this);
        wfBtn = (Button) findViewById(R.id.wanfabutton);
        LayoutParams pm = (LayoutParams) wfBtn.getLayoutParams();
        pm.width = 0;
        wfBtn.setLayoutParams(pm);
        loadLayout = (RelativeLayout) findViewById(R.id.loaddata);
        failedLayout = (RelativeLayout) findViewById(R.id.failed);
        failedLayout.setOnClickListener(this);
        mainLayout = (RelativeLayout) findViewById(R.id.mainlayout);
        noDataLayout = (RelativeLayout) findViewById(R.id.nodata);
        listView = (MyRefreshListView) findViewById(R.id.pulllayout);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setFadingEdgeLength(0);
        listView.setDividerHeight(0);
        listView.setonRefreshListener(new MyRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                isReflesh = true;
                clearData();
                Control.getInstance().getGYJManager()
                        .requestLotteryData(wfindex, callbackId);
            }
        });
        // 左右两边的按钮
//		rightbtnmenu.setOnClickListener(this);
//		leftbtnmenu.setOnClickListener(this);
        // 注数和金额
        zhuText = (TextView) findViewById(R.id.xuanhaotishi1);
        moneyText = (TextView) findViewById(R.id.xuanhaotishi2);
        // 顶部切换玩法
        ftcenterLayout = (LinearLayout) findViewById(R.id.footballffootballtopbarLayout);
        if (isFromTouZhuQueRen) {

        } else {
            ftcenterLayout.setOnClickListener(this);
        }
    }

    /**
     * 设置注数和金额
     **/
    private void setZhuAndMoney() {
        zhuText.setText(String.valueOf(zhuShuNum));
        moneyText.setText(String.valueOf(zhuShuNum * 2));
    }

    /**
     * 填充控件
     **/
    private void fillView() {
        // cpName.setVisibility(View.GONE);
       setCusTomeTitle("世界杯·"+cp.getCurrentWanfaName());


        adapter = new GuanYaJunAdapter(this);
        if (wfindex == 0) {
            adapter.setIsGuanYaJun(false);
        } else {
            adapter.setIsGuanYaJun(true);
        }

        adapter.setList(datalist);
        adapter.setmHandler(mHandler);
        adapter.setSelectedList(selectedList);
        listView.setAdapter(adapter);
        qiShuText.setText("截止:");
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
            if (!isFromTouZhuQueRen || isReflesh)
                Control.getInstance().getGYJManager()
                        .requestLotteryData(wfindex, callbackId);
            else {
                responseStr = getIntent().getStringExtra("listData");
                if (!TextUtils.isEmpty(responseStr)) {
                    try {

                        Control.getInstance().getGYJManager()
                                .jieXiJsonData(wfindex, responseStr, callbackId, false);

                    } catch ( Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        Control.getInstance().getGYJManager()
                                .requestLotteryData(wfindex, callbackId);
                    }
                }
            }
        }
    }


    GuanYaJunManager.OnGuanYaJunDataCallback dataCallback = new GuanYaJunManager.OnGuanYaJunDataCallback() {

        @Override
        public void onSuccess(String response) {


            responseStr = response.toString();
            //清空数据列表
            datalist.clear();
            JSONArray array = JSON.parseObject(response).getJSONArray("datalist");
            for (int i = 0; i < array.size(); i++) {
                GuanYaJunData data = new GuanYaJunData(array.getJSONObject(i));
                if (null != selectedList && selectedList.contains(data.getJcId()))
                    data.setSelected(true);
                datalist.add(data);
            }
            if (wfindex == 0) {
                qiuDuiNumText.setText("共" + datalist.size() + "支球队");
            } else {
                qiuDuiNumText.setText("共" + datalist.size() + "种组合");
            }

            String oldTime = JSON.parseObject(response).getString("sellEndTime");
            String newTime;
            try {
                newTime = sdf2.format(sdf1.parse(oldTime));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                newTime = oldTime;
            }
            jieZhiText.setText(newTime);

            if (wfindex == 0) {
                adapter.setIsGuanYaJun(false);
            } else {
                adapter.setIsGuanYaJun(true);
            }
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
            // System.out.println("response:"+response);
            if (isReflesh) {
                isReflesh = false;
                listView.onRefreshComplete();
            } else {
                loadLayout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.VISIBLE);
                noDataLayout.setVisibility(View.GONE);
            }
            if (isFromTouZhuQueRen && !isReflesh) {
                setZhuAndMoney();
            }
        }

        @Override
        public void onFailure(Throwable error) {


        }
    };

    @Override
    public void onClick(View v) {

        super.onClick(v);
        switch (v.getId()) {
            case R.id.failed:
                reLoadData();
                break;
//
            case R.id.qingkong:
                clearData();
                break;
            case R.id.footballffootballtopbarLayout:
                getSelectPop().showAsDropDown(  findViewById(R.id.footballffootballtopbarLayout));
                break;
            case R.id.btn1:
            case R.id.btn2:
                dismiss();
                currentBtn.setSelected(false);
                v.setSelected(true);
                currentBtn = (Button) v;
                if (v.getId() == R.id.btn1) {
                    wfindex = 0;
                    cp.setCurrentWanfa(cp.getWanfaList().get(0));
                } else {
                    cp.setCurrentWanfa(cp.getWanfaList().get(1));
                    wfindex = 1;
                }
                setCusTomeTitle("世界杯."+cp.getCurrentWanfaName());
                zhuShuNum = 0;
                if (null != selectedList)
                    selectedList.clear();
                setZhuAndMoney();
                reLoadData();
                break;
            case R.id.ensurebtn:
                if (zhuShuNum == 0)
                    showToast("至少选择1注");
                else {
                    // 重新获取所选择的的球队编号列表
                    if (selectedList.size() != zhuShuNum) {
                        selectedList.clear();
                        for (GuanYaJunData data : datalist) {
                            if (data.isSelected())
                                selectedList.add(data.getJcId());
                        }
                        zhuShuNum = selectedList.size();
                    }
                    // 传递数据
                    intent.putExtra("listData", responseStr);

                    intent.putExtra("zhuShu", zhuShuNum);
                    intent.putExtra("wfindex", wfindex);
                    intent.putStringArrayListExtra("selectedData", selectedList);
                    if (!isFromTouZhuQueRen) {
                        clearAllSelected = true;
                        startActivity(intent);
                        pullUpActivityAnimFromMain();
                    } else {
                        setResult(RESULT_OK, intent);
                        finish();
                        pullDownActivityAnim();
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

    /**
     * 清空数据
     */
    private void clearData() {
        zhuShuNum = 0;
        if (null != selectedList)
            selectedList.clear();
        setZhuAndMoney();
        for (GuanYaJunData data : datalist) {
            data.setSelected(false);
        }
        adapter.notifyDataSetChanged();
    }

    View vPopupWindow;
    PopupWindow wanfaChangePop;
    LinearLayout top;
    ImageView img;

    private PopupWindow getSelectPop() {
        if (vPopupWindow == null) {
            vPopupWindow = getLayoutInflater().inflate(
                    R.layout.pop_guanyajun, null, false);
            wanfaChangePop = new PopupWindow(vPopupWindow,
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            wanfaChangePop.setBackgroundDrawable(new BitmapDrawable());
            wanfaChangePop.setFocusable(true);
            wanfaChangePop.setOutsideTouchable(true);
            top = (LinearLayout) vPopupWindow
                    .findViewById(R.id.layouttopselect);
            top.setVisibility(View.VISIBLE);

            img = (ImageView) vPopupWindow.findViewById(R.id.imageView1);
            btnOne = (Button) vPopupWindow.findViewById(R.id.btn1);
            btnOne.setTag(R.id.tag, 0);
            btnTwo = (Button) vPopupWindow.findViewById(R.id.btn2);
            btnTwo.setTag(R.id.tag, 1);
            btnOne.setOnClickListener(this);
            btnTwo.setOnClickListener(this);
            if (wfindex == 0) {
                btnOne.setSelected(true);
                currentBtn = btnOne;
            } else {
                btnTwo.setSelected(true);
                currentBtn = btnTwo;
            }
            vPopupWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wanfaChangePop != null && wanfaChangePop.isShowing()) {
                        dismiss();
                    }
                }
            });
        } else {

            top.setVisibility(View.VISIBLE);

        }
        return wanfaChangePop;
    }

    private void dismiss() {
        wanfaChangePop.dismiss();

    }
}
