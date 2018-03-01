package com.chengyi.app.jingji.guanyajun;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.home.hemai.TogeterBuyActivity;
import com.chengyi.app.listener.IBuyCallBack;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.model.GoumaiData;
import com.chengyi.app.model.model.GuanYaJunData;
import com.chengyi.app.user.BuyConfirm;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.view.XuhaoExitDialog;

import java.util.ArrayList;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class GuanYaJunCart extends BaseActivity implements
        XuhaoExitDialog.ICallback {
    /**
     * 顶部左右两边按钮以及继续添加比赛按钮
     */
    LinearLayout addBtn;
    ImageView leftbtnmenu, rightbtnmenu;
    TextView  faQiHeMai, zhuShuText, moneyText;
    private ListView listView;
    Button enSureBtn;
    /**
     * 适配器
     */
    GuanYaJunAdapter adapter;
    /**
     * 投注页面传递过来的数据
     **/
    private String responseStr;
    private int wfIndex;
    private Intent intent;
    /**
     * 存储数据
     **/
    private ArrayList<GuanYaJunData> list = new ArrayList<GuanYaJunData>();
    private Caipiao cp;
    /**
     * 一共选择了多少注
     **/
    private int zhuShuNum;
    XuhaoExitDialog exitDailog;
    /**
     * 定义一个handler用来和适配器交互
     */
    private Handler myHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            zhuShuNum = selectedList.size();
            setZhuAndMoney();
        }
    };

    /**
     * 购买确认时所需要的数据
     */
    GoumaiData goucaiData = new GoumaiData();

    EditText beiShuEt;
    private int beiShuNum = 1;
    /**
     * 所选择的球队编号列表
     */
    ArrayList<String> selectedList = new ArrayList<String>();
    /**
     * 奖期id
     */
    private int issueId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery_football_cart);
        cp = getCurrentCaipiao();
        intent = getIntent();
        responseStr = intent.getStringExtra("listData");

        zhuShuNum = intent.getIntExtra("zhuShu", 0);
        wfIndex = intent.getIntExtra("wfindex", 0);
        selectedList = intent.getStringArrayListExtra("selectedData");
        // 初始化购买确认数据
        goucaiData.setZhuihaoqishu(1);
        goucaiData.setCaipiaoId(cp.getId());
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {


        findViewById(R.id.iv_anim).setVisibility(View.GONE);


        leftbtnmenu.setImageResource(R.drawable.new_football_btn_delete);
        leftbtnmenu.setOnClickListener(this);
        rightbtnmenu.setImageResource(R.drawable.nav_ic_close);
        rightbtnmenu.setOnClickListener(this);
        findViewById(R.id.guoguanlayout).setVisibility(View.GONE);
        findViewById(R.id.jiangjinlayout).setVisibility(View.GONE);
        addBtn = (LinearLayout) findViewById(R.id.btnlayout);
        addBtn.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview);

        // 发起合买
        faQiHeMai = (TextView) findViewById(R.id.faqihemai);
        faQiHeMai.setVisibility(View.INVISIBLE);
        // 确定按钮
        enSureBtn = (Button) findViewById(R.id.wanchengxuanhao);
        enSureBtn.setOnClickListener(this);
        // 注数和钱数
        zhuShuText = (TextView) findViewById(R.id.zhushu);
        moneyText = (TextView) findViewById(R.id.yuan);
        zhuShuText.setText(String.valueOf(zhuShuNum));
        moneyText.setText(String.valueOf(zhuShuNum * 2));
        // 设置适配器
        adapter = new GuanYaJunAdapter(this);
        if (wfIndex == 0) {
            adapter.setIsGuanYaJun(false);
        } else {
            adapter.setIsGuanYaJun(true);
        }
        // 解析数据串
        if (!TextUtils.isEmpty(responseStr))
            resolveStr();
        // 创建对话框
        exitDailog = new XuhaoExitDialog(this);
        exitDailog.setCallBack(this);
        beiShuEt = (EditText) findViewById(R.id.beishueditext);
        beiShuEt.setSelection(beiShuEt.getText().toString().length());
        beiShuEt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                try {
                    if (!TextUtils.isEmpty(s)) {
                        beiShuNum = Integer.parseInt(s + "");
                    } else {
                        beiShuNum = 1;
                    }
                    moneyText.setText(String.valueOf(zhuShuNum * 2 * beiShuNum));
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
        adapter.setList(list);
        adapter.setFromTouZhuQueRen(true);
        adapter.setSelectedList(selectedList);
        adapter.setmHandler(myHandler);
        listView.setAdapter(adapter);
    }

    /**
     * 设置注数和金额
     **/
    private void setZhuAndMoney() {
        zhuShuText.setText(String.valueOf(zhuShuNum));
        moneyText.setText(String.valueOf(zhuShuNum * 2 * beiShuNum));
    }

    /**
     * 数据字符串的解析 ,主要是解析投注页面传递过来的数据
     **/
    private void resolveStr() {
        try {
            JSONObject response = JSON.parseObject(responseStr);
            //清空数据列表
            list.clear();
            JSONObject issues = response.getJSONObject("issues");
            issueId = issues.getIntValue("id");
            JSONArray array = response.getJSONArray("datalist");
            for (int i = 0; i < array.size(); i++) {
                GuanYaJunData data = new GuanYaJunData(array.getJSONObject(i));
                if (selectedList.contains(data.getJcId())) {
                    data.setSelected(true);
                    list.add(data);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        super.onClick(v);
        // 当前处于编辑模式，点击其他的任何按钮都要恢复正常状态
        if (adapter.isDeleteMode()) {
            leftbtnmenu.setSelected(!leftbtnmenu.isSelected());
            adapter.setDeleteMode(!adapter.isDeleteMode());
            adapter.notifyDataSetChanged();
            return;
        }
        switch (v.getId()) {

            case R.id.btnlayout:
                intent.setClass(this, GuanYaJun.class);
                intent.putExtra("fromtouzhu", true);
                intent.putExtra("zhuShu", zhuShuNum);
                intent.putStringArrayListExtra("selectedData", selectedList);
                startActivityForResult(intent, 1);
                pullUpActivityAnim();
                break;
            case R.id.faqihemai:
                if (check())
                    return;
                if (TextUtils.isEmpty(beiShuEt.getText())) {
                    beiShuEt.setText("1");
                    beiShuNum = 1;
                }


                String username = getUserName();
                if (username != null && username.startsWith("m-") || username.startsWith("a-")) {
                    showToast(getString(R.string.buy_together_warning));
                    return;

                }
                //getTouZhuData();
                goucaiData.setBeishu(beiShuNum);
                goucaiData.setTotalZhushu(zhuShuNum);
                goucaiData.setList(getTouList());
                goucaiData.setIssueIdStr(String.valueOf(issueId));
                Intent inte = new Intent(this, TogeterBuyActivity.class);
                inte.putExtra("goumaidata", goucaiData);
                inte.putExtra("wfindex", wfIndex);
                inte.putExtra("isJingcai", true);
                startActivity(inte);
                pullUpActivityAnim();
                break;
            case R.id.wanchengxuanhao:
                if (check())
                    return;
                if (TextUtils.isEmpty(beiShuEt.getText())) {
                    beiShuEt.setText("1");
                    beiShuNum = 1;
                }
                goucaiData.setBeishu(beiShuNum);
                goucaiData.setTotalZhushu(zhuShuNum);
                goucaiData.setList(getTouList());
                goucaiData.setIssueIdStr(String.valueOf(issueId));

                if (!TextUtils.isEmpty(getSession())) {
                    showLoading("订单提交中...");
                    BuyConfirm buyConfirm = new BuyConfirm(getCurrentCaipiao(), false, goucaiData, -1, 1, this, new IBuyCallBack() {
                        @Override
                        public void buyComplete() {
                            hideLoading();
                        }

                        @Override
                        public void buyCancle() {
                            hideLoading();
                        }
                    });
                    buyConfirm.submitData();
                } else {
                    Intent in;
                    in = new Intent(this, LoginActivity.class);

                    startActivity(in);
                }
                pullUpActivityAnim();
                break;
        }
    }

    @Override
    public void reBack() {

        if (exitDailog.isShowing())
            exitDailog.dismiss();
        finish();
        pullDownActivityAnim();
    }

    @Override
    public void close() {

        if (exitDailog.isShowing())
            exitDailog.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 是否触发按键为back键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isShowDailog();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 是否显示对话框
     */
    private void isShowDailog() {
        if (zhuShuNum > 0) {
            exitDailog.show();
            exitDailog.getCenterTextView().setText("放弃已选取的比赛？");
        } else {
            finish();
            pullDownActivityAnim();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1) {
                responseStr = data.getStringExtra("listData");
                zhuShuNum = data.getIntExtra("zhuShu", 0);
                wfIndex = data.getIntExtra("wfindex", 0);
                selectedList = data.getStringArrayListExtra("selectedData");
                resolveStr();
                adapter.setSelectedList(selectedList);
                adapter.notifyDataSetChanged();
                myHandler.sendEmptyMessage(1);
            }
        }
    }

    /**
     * 发起合买和确认投注前检查数据是否够1注
     */
    private boolean check() {
        if (zhuShuNum == 0) {
            showToast("至少选择1注");
            return true;
        } else
            return false;
    }

    /**
     * 发起合买和确认投注前检查数据是否够1注
     */
    private ArrayList<GuanYaJunData> getTouList() {
        ArrayList<GuanYaJunData> mList = new ArrayList<GuanYaJunData>();
        for (GuanYaJunData data : list) {
            if (selectedList.contains(data.getJcId()))
                mList.add(data);
        }
        return mList;
    }

}
