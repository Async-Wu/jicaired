package com.chengyi.app.jingji.renxuan;

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
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.home.hemai.TogeterBuyActivity;
import com.chengyi.app.listener.IBuyCallBack;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.model.*;
import com.chengyi.app.user.BuyConfirm;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.view.XuhaoExitDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class Activity_RenXuanQiuChang_EnSure extends BaseActivity implements
        XuhaoExitDialog.ICallback {
    /**
     * 顶部左右两边按钮以及继续添加比赛按钮
     */
    LinearLayout btnlayout;
    TextView faQiHeMai, zhuShuText, moneyText;
    private ListView listView;
    Button enSureBtn;
    ImageView leftbtnmenu;
    /**
     * 适配器
     */
    RenXuanNineAdapter adapter;
    /**
     * 投注页面传递过来的数据
     **/
    private String responseStr, touzhudata, issueStr;
    private int curIssueId;
    private Intent intent;
    /**
     * 存储数据
     **/
    private ArrayList<RenXuanNineData> list = new ArrayList<RenXuanNineData>();
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
            if (msg.what == 0) {
                zhuShuNum = 0;
                zhuShuText.setText("0");
                moneyText.setText("0");
            } else if (msg.what == 1) {
                zhuShuText.setText(String.valueOf(zhuShuNum));
                moneyText.setText(String.valueOf(zhuShuNum * 2 * beiShuNum));
            } else if (msg.what == 2) {
                zhuShuNum = getTouZhuNum();
                zhuShuText.setText(String.valueOf(zhuShuNum));
                moneyText.setText(String.valueOf(zhuShuNum * 2 * beiShuNum));
            }
        }
    };
    /**
     * 投注内容的字符串数组
     */
    private static String[] numStr = new String[]{"*-", "*-", "*-", "*-",
            "*-", "*-", "*-", "*-", "*-", "*-", "*-", "*-", "*-", "*"};
    /**
     * 购买确认时所需要的数据
     */
    GoumaiData goucaiData = new GoumaiData();
    TouzhuquerenData data;
    List<TouzhuquerenData> dataList = new ArrayList<TouzhuquerenData>();
    EditText beiShuEt;
    private int beiShuNum = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery_football_cart);

        findViewById(R.id.iv_select).setVisibility(View.GONE);


        findViewById(R.id.tv_danguan).setVisibility(View.GONE);
        findViewById(R.id.iv_anim).setVisibility(View.GONE);
        cp = getCurrentCaipiao();
        intent = getIntent();
        responseStr = intent.getStringExtra("listData");
        // System.out.println("responseStr:"+responseStr);
        // 已选的比赛拼接的串
        touzhudata = intent.getStringExtra("touzhudata");
        zhuShuNum = intent.getIntExtra("zhuShu", 0);
        // 初始化购买确认数据
        goucaiData.setZhuihaoqishu(1);
        goucaiData.setCaipiaoId(cp.getId());
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        leftbtnmenu = (ImageView) findViewById(R.id.iv_wanfa);
        leftbtnmenu.setImageResource(R.drawable.laiji1);

        leftbtnmenu.setOnClickListener(this);

        findViewById(R.id.iv_anim).setVisibility(View.GONE);

        setCusTomeTitle(cp.getName() + "_投注确认");
        setBack();
        findViewById(R.id.fl).setVisibility(View.GONE);
        findViewById(R.id.jiangjinlayout).setVisibility(View.GONE);
        btnlayout = (LinearLayout) findViewById(R.id.btnlayout);
        btnlayout.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview);

        // 发起合买
        faQiHeMai = (TextView) findViewById(R.id.faqihemai);
        faQiHeMai.setOnClickListener(this);
        // 确定按钮
        enSureBtn = (Button) findViewById(R.id.wanchengxuanhao);
        enSureBtn.setOnClickListener(this);
        // 注数和钱数
        zhuShuText = (TextView) findViewById(R.id.zhushu);
        moneyText = (TextView) findViewById(R.id.yuan);
        zhuShuText.setText(String.valueOf(zhuShuNum));
        moneyText.setText(String.valueOf(zhuShuNum * 2));
        // 设置适配器
        adapter = new RenXuanNineAdapter(this);
        // 解析数据串
        if (!TextUtils.isEmpty(responseStr))
            resolveStr();
        adapter.setList(list);
        adapter.setCpId(cp.getId());
        adapter.setTouZhuEnsue(true);
        adapter.setHandler(myHandler);
        listView.setAdapter(adapter);
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
    }

    /**
     * 数据字符串的解析 ,主要是解析投注页面传递过来的数据
     **/
    private void resolveStr() {
        try {
            list.clear();
            JSONObject response = JSON.parseObject(responseStr);
            issueStr = response.getString("curIssue");
            curIssueId = response.getIntValue("curIssueId");
            JSONArray array = response.getJSONArray("data");
            String[] hmStr = null;
            if (null != touzhudata && !TextUtils.isEmpty(touzhudata))
                hmStr = touzhudata.split("-");
            for (int i = 0; i < array.size(); i++) {
                RenXuanNineData dt = new RenXuanNineData(
                        array.getJSONObject(i), i + 1);
                try {
                    if (!hmStr[i].equals("*")) {
                        dt.getBuffer().append(hmStr[i]);
                        dt.setSelectedNum(hmStr[i].length());
                        list.add(dt);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

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
            case R.id.iv_wanfa:
                leftbtnmenu.setSelected(!leftbtnmenu.isSelected());
                adapter.setDeleteMode(!adapter.isDeleteMode());
                adapter.notifyDataSetChanged();
                break;

            case R.id.btnlayout:
                intent.setClass(this, Activity_RenXuanQiuChang.class);
                intent.putExtra("touzhudata", getTouZhuString());
                intent.putExtra("fromtouzhu", true);
                startActivityForResult(intent, 1);
                pullUpActivityAnim();
                break;
            case R.id.faqihemai:
                if (check())
                    return;
                if (TextUtils.isEmpty(getSession())) {

                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    return;
                }
                if (TextUtils.isEmpty(beiShuEt.getText())) {
                    beiShuEt.setText("1");
                    beiShuNum = 1;
                }
                getTouZhuData();
                goucaiData.setBeishu(beiShuNum);
                goucaiData.setIssueIdStr(String.valueOf(curIssueId));
                goucaiData.setIssueStr(issueStr);
                goucaiData.setTotalZhushu(zhuShuNum);
                if (null != data)
                    goucaiData.setSubmitString(zhuShuNum == 1 ? data
                            .getSubmitString().replace("-", "") : data
                            .getSubmitString());


                goucaiData.setSubmitString(goucaiData.getSubmitString().replace("oneSingle","single").replace("onePoly","poly"));
                CaipiaoApplication.getInstance().setTouzhuHaomaList(dataList);
                Intent inte = new Intent(this, TogeterBuyActivity.class);
                inte.putExtra("goumaidata", goucaiData);
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
                getTouZhuData();
                goucaiData.setBeishu(beiShuNum);
                goucaiData.setIssueIdStr(String.valueOf(curIssueId));
                goucaiData.setIssueStr(issueStr);
                goucaiData.setTotalZhushu(zhuShuNum);
                if (null != data) {
                    goucaiData.setSubmitString(zhuShuNum == 1 ? data
                            .getSubmitString().replace("-", "") : data
                            .getSubmitString());
                }
                goucaiData.setSubmitString(goucaiData.getSubmitString().replace("oneSingle","single").replace("onePoly","poly"));

                if (TextUtils.isEmpty(getSession())) {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                } else {
                    showLoading("订单提交中...");
                    CaipiaoApplication.getInstance().setTouzhuHaomaList(dataList);
                    BuyConfirm buyConfirm = new BuyConfirm(getCurrentCaipiao(), false, goucaiData, -1, -1, Activity_RenXuanQiuChang_EnSure.this, new IBuyCallBack() {
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
                }


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

    /**
     * 已选的比赛拼接成字符串
     */
    private String getTouZhuString() {
        if (list.size() == 0)
            return "";
        numStr = new String[]{"*-", "*-", "*-", "*-", "*-", "*-", "*-", "*-",
                "*-", "*-", "*-", "*-", "*-", "*"};
        String s = "-";
        for (int i = 0; i < list.size(); i++) {
            RenXuanNineData data = list.get(i);
            if (data.getSelectedNum() > 0) {
                if (data.getNum() == 14)
                    s = "";
                numStr[data.getNum() - 1] = data.getBuffer().toString() + s;
            }
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < numStr.length; i++) {
            sb.append(numStr[i]);
        }
        // System.out.println("str:"+sb.toString());
        return sb.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1) {
                responseStr = data.getStringExtra("listData");
                // 已选的比赛拼接的串
                touzhudata = data.getStringExtra("touzhudata");
                // System.out.println("touzhudata:"+touzhudata);
                zhuShuNum = data.getIntExtra("zhuShu", 0);
                resolveStr();
                adapter.notifyDataSetChanged();
                myHandler.sendEmptyMessage(1);
            }
        }

    }

    /**
     * 计算注数
     */
    private int getTouZhuNum() {
        if (list.size() < cp.getQianquMinCount())
            return 0;
        int count = 1;
        for (RenXuanNineData data : list) {
            count = count * data.getSelectedNum();
        }
        return count;
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
     * 获取投注数据内容
     */
    private void getTouZhuData() {
        dataList.clear();
        data = new TouzhuquerenData();
        data.setCaipiaoIdAndWanfaType(cp.getId(), -1);
        data.setName("");
        data.setTouzhuhaoma(getTouZhuString());
        data.setZhushu(zhuShuNum);
        dataList.add(data);
    }
}
