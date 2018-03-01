package com.chengyi.app.jingji.basket;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.home.hemai.TogeterBuyActivity;
import com.chengyi.app.listener.IBottomList;
import com.chengyi.app.listener.IBuyCallBack;
import com.chengyi.app.model.model.GoumaiData;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.user.BuyConfirm;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.YOUMENG_EVENT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BasketballCartSFC extends BaseActivity implements IBottomList{

    LinearLayout leftbtnmenu;
    ImageView rightbtnmenu;
    TextView   guoguanfangshi, guoguanfangshiTip, bottomSumMoney,
            bottomFanWei, bottomZhuSu, buyTogetherBtn;
    EditText bottomBeiEditText;
    // XuhaoExitDialog exitDailog;
    Button buyBtn;
    LinearLayout btnlayout;

    LinearLayout guoGuanLayout;


    ListView listView;
    private BasketballCartSFCAdapter adapterSF;



    private int currentWanfaGuan = 0;
    private int currentWanfa = 0;

    long zhuNum = 0;
    double minJiangjin = 1000000000;
    double maxJiangjin = 0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery_basketball_cart);
        currentWanfaGuan = Control.getInstance().getBasketballManager()
                .getCurrentWanfaGuan();
        currentWanfa = Control.getInstance().getBasketballManager()
                .getWanfa(currentWanfaGuan);
        initChuanNum();
        setupView();
        setupListener();
    }

    private boolean isFirstTime = true;

    protected void onResume() {
        super.onResume();
        if (adapterSF != null) {
            int minChuan = getMinChuan();

            SparseArray<BasketballOneGame> selectedArray = Control
                    .getInstance().getBasketballManager().selectedLotteryGameArray
                    .get(currentWanfaGuan);
            int selectedSize = selectedArray.size();
            String defaultGuanStr = selectedSize + "串1";
            maxChuanNum = selectedSize;

            for (int i = 0; i < selectedChuanList.size(); i++) {
                if (Integer.parseInt(selectedChuanList.get(i).substring(0, 1)) > selectedSize) {
                    selectedChuanList.remove(i);
                    i--;
                }
            }
            adapterSF.resetDanData(minChuan);
            adapterSF.notifyDataSetChanged();
            setBottomTextView(defaultGuanStr,false,false);

        }
    }

    int maxChuanNum = 2;

    ArrayList<String> selectedChuanList = new ArrayList<String>();// x串已选列表


    private void initChuanNum() {
        SparseArray<BasketballOneGame> selectedArray = Control.getInstance()
                .getBasketballManager().selectedLotteryGameArray
                .get(currentWanfaGuan);

        if (selectedArray != null) {
            int selectedSize = selectedArray.size();
            for (int i = 0; i < selectedSize; i++) {
                filterGameSP(selectedArray.valueAt(i));
                selectedArray.valueAt(i).isDanPressed = false;
            }
            String defaultGuanStr = "";
            if (selectedSize >= 2) {
                if (selectedSize <= 4) {
                    defaultGuanStr = selectedSize + "串1";
                    if (adapterSF != null) {
                        adapterSF.resetDanData(selectedSize);
                    }
                    maxChuanNum = selectedSize;
                    selectedChuanList.add(defaultGuanStr);

                } else {
                    defaultGuanStr = "4串1";
                    if (adapterSF != null) {
                        adapterSF.resetDanData(4);
                    }
                    maxChuanNum = 4;
                    selectedChuanList.add(defaultGuanStr);

                }

                zhuNum = BasketballZhuCalculator.getZhuNum(selectedArray,
                        defaultGuanStr);
                minJiangjin = BasketballZhuCalculator.getMinValue();
                maxJiangjin = BasketballZhuCalculator.getMaxValue();

                minValueMap.put(defaultGuanStr, minJiangjin);
            }

        }

    }

    private void setupView() {

        initBuyType();
        setCusTomeTitle(Control.getInstance().getBasketballManager()
                .getCurrentWanfaGuanName()
                + "投注确认");


        findViewById(R.id.iv_anim).setVisibility(View.GONE);

        leftbtnmenu = (LinearLayout) findViewById(R.id.ll_back);


        findViewById(R.id.iv_select).setVisibility(View.INVISIBLE);
        rightbtnmenu = (ImageView) findViewById(R.id.iv_wanfa);
        rightbtnmenu.setImageResource(R.drawable.laiji1);

        btnlayout = (LinearLayout) findViewById(R.id.btnlayout);

        buyBtn = (Button) findViewById(R.id.wanchengxuanhao);
        buyTogetherBtn = (TextView) findViewById(R.id.faqihemai);

        listView = (ListView) findViewById(R.id.listview);


        adapterSF = new BasketballCartSFCAdapter(BasketballCartSFC.this, onCartTouchedCallback);
        adapterSF.setData(currentWanfaGuan);
        listView.setAdapter(adapterSF);

        // 共多少钱
        bottomSumMoney = (TextView) findViewById(R.id.yuan);
        // 奖金范围
        bottomFanWei = (TextView) findViewById(R.id.dangqianjiangjin);
        // 注数
        bottomZhuSu = (TextView) findViewById(R.id.zhushu);
        bottomBeiEditText = (EditText) findViewById(R.id.beishueditext);

        // 底部过关选择弹框
        guoGuanLayout = (LinearLayout) findViewById(R.id.guoguanlayout);

        guoguanfangshi = (TextView) findViewById(R.id.guoguanfangshi);
        guoguanfangshiTip = (TextView) findViewById(R.id.moreguoguan);
        setBottomTextView("",false,false);
        setupUI(findViewById(R.id.mainlayout));

    }

    // 设置过关方式奖金，注数，共需多少钱控件的值
    public void setBottomTextView(String defaultGuanStr,boolean f,boolean flag) {
        // min = (double) ((int) (min * 100 + 0.5)) / 100;
        // max = (double) ((int) (max * 100 + 0.5)) / 100;

        SparseArray<BasketballOneGame> selectedArray = Control.getInstance()
                .getBasketballManager().selectedLotteryGameArray
                .get(currentWanfaGuan);
        int selectedSize = selectedArray.size();
        for (int i = 0; i < selectedSize; i++) {
            filterGameSP(selectedArray.valueAt(i));
        }

        int minChuan = getMinChuan();
        adapterSF.resetDanData(minChuan);

        if (selectedSize >= 2) {
            if (Control.getInstance().getBasketballManager().getDanorguo() == 1) {
                guoguanfangshi.setText("单关");
                guoguanfangshiTip.setVisibility(View.GONE);
                zhuNum = BasketballZhuCalculator.getZhuNum(
                        selectedArray, "1串1");
            } else {
                guoguanfangshi.setVisibility(View.VISIBLE);
                int chuanSize = selectedChuanList.size();
                if (chuanSize > 0) {
                    guoguanfangshi.setText(selectedChuanList.get(0));
                    if (chuanSize > 1) {
                        guoguanfangshiTip.setText("(" + chuanSize + "个过关方式)");
                        zhuNum = 0;
                        for (int i = 0; i < chuanSize; i++) {

                            zhuNum += BasketballZhuCalculator.getZhuNum(
                                    selectedArray, selectedChuanList.get(i));
                        }
                    } else {
                        if (Control.getInstance().getBasketballManager().getDanorguo() == 0) {
                            guoguanfangshiTip.setText("(更多过关方式)");
                            zhuNum = BasketballZhuCalculator.getZhuNum(selectedArray,
                                    selectedChuanList.get(0));
                        }
                    }
                } else {
                    if (Control.getInstance().getBasketballManager().getDanorguo() == 0) {
                        if (!defaultGuanStr.equals("")) {
                            if (!selectedChuanList.contains(defaultGuanStr)) {
                                selectedChuanList.add(defaultGuanStr);
                                guoguanfangshi.setText(defaultGuanStr);
                                guoguanfangshiTip.setText("(更多过关方式)");
                                zhuNum = BasketballZhuCalculator.getZhuNum(
                                        selectedArray, selectedChuanList.get(0));
                            }
                        } else {
                            if (Control.getInstance().getBasketballManager().getDanorguo() == 0) {
                                guoguanfangshi.setVisibility(View.GONE);
                                guoguanfangshiTip.setText("(更多过关方式)");
                                zhuNum = 0;
                            }
                        }
                    }
                }
            }
        } else {
            if (Control.getInstance().getBasketballManager().getDanorguo() == 0) {
                guoguanfangshi.setVisibility(View.GONE);
                guoguanfangshiTip.setText("至少选2场比赛");
                leftbtnmenu.setSelected(false);
                adapterSF.setMode(false);
            } else {
                guoguanfangshi.setText("单关");
                guoguanfangshiTip.setVisibility(View.GONE);
                zhuNum = BasketballZhuCalculator.getZhuNum(
                        selectedArray, "1串1");

            }

        }


        bottomZhuSu.setText(String.valueOf(zhuNum));
        try {
            bottomSumMoney.setText(String.valueOf(zhuNum
                    * 2
                    * getBeisu(bottomBeiEditText )));
            // bottomFanWei.setText(df.format(2
            // * min
            // * Double.parseDouble(bottomBeiEditText.getText().toString()
            // .trim()))
            // + "~"
            // + df.format(2
            // * max
            // * Double.parseDouble(bottomBeiEditText.getText()
            // .toString().trim())) + "元");
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    OnCartTouchedCallback onCartTouchedCallback = new OnCartTouchedCallback() {


        @Override
        public void onDanPressed() {

            SparseArray<BasketballOneGame> selectedArray = Control
                    .getInstance().getBasketballManager().selectedLotteryGameArray
                    .get(currentWanfaGuan);
            zhuNum = 0;
            int chuanSize = selectedChuanList.size();
            for (int i = 0; i < chuanSize; i++) {
                zhuNum += BasketballZhuCalculator.getZhuNum(selectedArray,
                        selectedChuanList.get(i));
            }
            bottomZhuSu.setText(String.valueOf(zhuNum));
            bottomSumMoney.setText(String.valueOf(zhuNum
                    * 2
                    * getBeisu(bottomBeiEditText )));
        }

        @Override
        public void onGameDeleted() {


            SparseArray<BasketballOneGame> selectedArray = Control
                    .getInstance().getBasketballManager().selectedLotteryGameArray
                    .get(currentWanfaGuan);
            int selectedSize = selectedArray.size();
            String defaultGuanStr = selectedSize + "串1";
            maxChuanNum = selectedSize;
            for (int i = 0; i < selectedChuanList.size(); i++) {
                if (Integer.parseInt(selectedChuanList.get(i).substring(0, 1)) > selectedSize) {
                    selectedChuanList.remove(i);
                    i--;
                }
            }

            setBottomTextView(defaultGuanStr,false,false);

        }

    };

    private int getMinChuan() {
        int chuan = 0;
        for (int i = 0; i < selectedChuanList.size(); i++) {
//			int oldmin = Integer.parseInt(selectedChuanList.get(i).substring(0,
//					1));
            int oldmin = BasketballZhuCalculator.getDanNumFromGuan(selectedChuanList.get(i));
            if (chuan == 0) {
                chuan = oldmin;
            } else if (oldmin < chuan) {
                chuan = oldmin;
            }
        }
        return chuan;
    }

    private Map<String, Double> minValueMap = new HashMap<String, Double>();

    // /设置每个比赛对象所选择的赔率中最大和最小的
    public void filterGameSP(BasketballOneGame game) {
        double min = 1000000000, max = 0;
        if (currentWanfa == BasketballManager.sf) {
            if (game.isSFSelected[0] && !game.isSFSelected[1]) {
                max = game.sheng;
                min = game.sheng;
            } else if (!game.isSFSelected[1] && !game.isSFSelected[0]) {
                max = game.fu;
                min = game.fu;
            } else {
                if (game.sheng >= game.fu) {
                    max = game.sheng;
                    min = game.fu;
                } else {
                    max = game.fu;
                    min = game.sheng;
                }
            }
        } else if (currentWanfa == BasketballManager.rfsf) {
            if (game.isSFSelected[0] && !game.isSFSelected[1]) {
                max = game.rfsheng;
                min = game.rfsheng;
            } else if (!game.isSFSelected[1] && !game.isSFSelected[0]) {
                max = game.rffu;
                min = game.rffu;
            } else {
                if (game.rfsheng >= game.rffu) {
                    max = game.rfsheng;
                    min = game.rffu;
                } else {
                    max = game.rffu;
                    min = game.rfsheng;
                }
            }
        }
        game.maxSP = max;
        game.minSP = min;
    }

    private void setupListener() {
        rightbtnmenu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!rightbtnmenu.isSelected()) {
                    rightbtnmenu.setSelected(true);
                    adapterSF.setMode(true);

                } else {
                    rightbtnmenu.setSelected(false);
                    adapterSF.setMode(false);
                }

            }

        });

        leftbtnmenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseArray<BasketballOneGame> item = Control.getInstance()
                        .getBasketballManager().selectedLotteryGameArray
                        .get(currentWanfaGuan);

                Control.getInstance().getBasketballManager()
                        .clearSelected(currentWanfaGuan);
                adapterSF.notifyDataSetChanged();
                setResult(10);
                finish();
                pullDownActivityAnim();
                // }
            }
        });
        btnlayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                setResult(10);
                finish();
            }

        });


        guoGuanLayout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                SparseArray<BasketballOneGame> item = Control.getInstance()
                        .getBasketballManager().selectedLotteryGameArray
                        .get(currentWanfaGuan);

                if (Control.getInstance().getBasketballManager().getDanorguo() == 0) {
                    if (item == null || item.size() < 2) {
                        Toast.makeText(BasketballCartSFC.this, "至少选择2场比赛",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                setupBottomGuanPopData(selectedChuanList,BasketballCartSFC.this,maxChuanNum);


            }

        });

        bottomBeiEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {


                String sr = bottomBeiEditText.getText().toString();
                if (sr.trim().length() == 0 || sr.equals("0") || Integer.parseInt(sr) < 1) {
                    sr = "1";

                }


                bottomSumMoney.setText(String.valueOf(2 * zhuNum * Integer.parseInt(sr)));
                bottomFanWei.setText(df.format(2 * minJiangjin * Integer.parseInt(sr)) + "~"
                        + df.format(2 * maxJiangjin * Integer.parseInt(sr)) + "元");

                int sss = Integer.parseInt(bottomZhuSu.getText().toString()) * Integer.parseInt(sr) * 2;
                bottomSumMoney.setText("" + sss);

                bottomFanWei.setText(df.format(2 * minJiangjin * Integer.parseInt(sr)) + "~"
                        + df.format(2 * maxJiangjin * Integer.parseInt(sr)) + "元");


            }

            @Override
            public void afterTextChanged(Editable s) {


                try {
                    if (TextUtils.isEmpty(bottomBeiEditText.getText().toString()) || Integer.parseInt(s.toString().trim()) < 1) {

                        bottomSumMoney.setText(String.valueOf(2 * zhuNum));
                        bottomFanWei.setText(2 * minJiangjin + "~" + 2 * maxJiangjin
                                + "元");
                    }
                } catch (Exception e) {
                }
            }

        });

        buyBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Control.getInstance().getBasketballManager().getDanorguo() == 0) {
                    if (selectedChuanList.size() == 0) {
                        Toast.makeText(BasketballCartSFC.this, "请选择过关方式",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    if (Control.getInstance()
                            .getBasketballManager().selectedLotteryGameArray
                            .get(currentWanfaGuan) == null || Control.getInstance()
                            .getBasketballManager().selectedLotteryGameArray
                            .get(currentWanfaGuan).size() == 0) {
                        Toast.makeText(BasketballCartSFC.this, "至少选择一场比赛",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                SparseArray<BasketballOneGame> item = Control.getInstance()
                        .getBasketballManager().selectedLotteryGameArray
                        .get(currentWanfaGuan);
                if (Control.getInstance().getBasketballManager().getDanorguo() == 0) {
                    if (item == null || item.size() < 2) {
                        Toast.makeText(BasketballCartSFC.this, "请至少选择2场比赛",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                if (TextUtils.isEmpty(getSession())) {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                } else {
                    showLoading("订单提交中...");


                    GoumaiData goucaiData = new GoumaiData();
                    if (TextUtils.isEmpty(bottomBeiEditText.getText().toString()
                            .trim())) {
                        goucaiData.setBeishu(1);

                    } else {
                        goucaiData.setBeishu(Integer.parseInt(bottomBeiEditText
                                .getText().toString().trim()));
                    }
                    goucaiData.setTotalZhushu(zhuNum);
                    goucaiData.setGameSize(item.size());
                    goucaiData
                            .setGuoGuanStr(getStrFromGuoGuanKind(selectedChuanList));
                    List<BasketballOneGame> basketlist = new ArrayList<BasketballOneGame>();
                    for (int i = 0; i < item.size(); i++) {
                        basketlist.add(item.valueAt(i));
                    }
                    goucaiData.setListBasketball(basketlist);
                    goucaiData.setCaipiaoId(10058);
                    BuyConfirm buyConfirm = new BuyConfirm(getCurrentCaipiao(), false, goucaiData, currentWanfa, Control.getInstance().getBasketballManager().getDanorguo(), BasketballCartSFC.this, new IBuyCallBack() {
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





            }

        });

        buyTogetherBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                if (Control.getInstance().getBasketballManager().getDanorguo() == 0) {
                    if (selectedChuanList.size() == 0) {
                        Toast.makeText(BasketballCartSFC.this, "请选择过关方式",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                SparseArray<BasketballOneGame> item = Control.getInstance()
                        .getBasketballManager().selectedLotteryGameArray
                        .get(currentWanfaGuan);
                if (Control.getInstance().getBasketballManager().getDanorguo() == 0) {
                    if (item == null || item.size() < 2) {
                        Toast.makeText(BasketballCartSFC.this, "请至少选择2场比赛",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Intent intent = new Intent(BasketballCartSFC.this,
                        TogeterBuyActivity.class);
                intent.putExtra("flag", Control.getInstance().getBasketballManager().getDanorguo());

                GoumaiData goucaiData = new GoumaiData();

                if (TextUtils.isEmpty(bottomBeiEditText.getText().toString()
                        .trim())) {
                    goucaiData.setBeishu(1);
                    bottomBeiEditText.setText("1");
                } else {
                    goucaiData.setBeishu(Integer.parseInt(bottomBeiEditText
                            .getText().toString().trim()));
                }
                goucaiData.setTotalZhushu(zhuNum);
                goucaiData.setGameSize(item.size());
                goucaiData
                        .setGuoGuanStr(getStrFromGuoGuanKind(selectedChuanList));
                List<BasketballOneGame> basketlist = new ArrayList<BasketballOneGame>();
                for (int i = 0; i < item.size(); i++) {
                    basketlist.add(item.valueAt(i));
                }
                goucaiData.setListBasketball(basketlist);
                goucaiData.setCaipiaoId(10058);
                intent.putExtra("goumaidata", goucaiData);
                intent.putExtra("isJingcai", true);
                intent.putExtra("wfindex", currentWanfa);
                startActivity(intent);
                pullUpActivityAnim();
                map.put("操作类型", "发起合买");
                map.put("过关方式", getStrFromGuoGuanKind(selectedChuanList));
                CaipiaoUtil.youmengEvent(BasketballCartSFC.this,
                        YOUMENG_EVENT.new_basketball_cart, map);
            }

        });

    }

    // 将过关方式列表转换成字符串
    public String getStrFromGuoGuanKind(ArrayList<String> lt) {
        String guoGuanStr = "";
        for (int i = 0; i < lt.size(); i++) {
            if (i > 0) {
                guoGuanStr += "/";
            }
            guoGuanStr += lt.get(i);
        }
        return guoGuanStr;
    }





    interface OnCartTouchedCallback {

          void onDanPressed();

          void onGameDeleted();
    }


}
