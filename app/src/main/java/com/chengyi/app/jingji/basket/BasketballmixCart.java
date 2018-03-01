package com.chengyi.app.jingji.basket;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
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
import com.chengyi.app.util.GetNumModel;
import com.chengyi.app.util.GetNumUtil;
import com.chengyi.app.util.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BasketballmixCart extends BaseActivity implements IBottomList {

    LinearLayout leftbtnmenu;
    ImageView rightbtnmenu;
    TextView bottomGuan, bottomGuanTip, bottomSumMoney,
            bottomFanWei, bottomZhuSu, buyTogetherBtn;
    EditText bottomBeiEditText;

    Button buyBtn;


    LinearLayout guoGuanLayout, btnlayout;
//    ImageView/* bottomArrow, */guoguanPopbg;

    ListView listView;
    private BasketballmixcartAdapter adapterSF;


    private int currentWanfaGuan = 0;
    private int currentWanfa = 0;

    long zhuNum = 0;
    double minJiangjin = 1000000000;
    double maxJiangjin = 0;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery_basketball_cart);
        currentWanfaGuan = Control.getInstance().getBasketballManager()   .getCurrentWanfaGuan();
        currentWanfa = Control.getInstance().getBasketballManager()
                .getWanfa(currentWanfaGuan);
        initChuanNum();
        setupView();
        setupListener();
    }


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

                List<GetNumModel> caculetchoose = caculetchoose(selectedArray);

                zhuNum = GetNumUtil.getZhuNum(caculetchoose, defaultGuanStr);
                minJiangjin = BasketballZhuCalculator.getMinValue();
                maxJiangjin = BasketballZhuCalculator.getMaxValue();

                minValueMap.put(defaultGuanStr, minJiangjin);
            }

        }

    }

    private void setupView() {
        initBuyType();
        setCusTomeTitle(Control.getInstance().getBasketballManager()    .getCurrentWanfaGuanName()    + "投注确认");
        leftbtnmenu = (LinearLayout) findViewById(R.id.ll_back);
        setBack();
        rightbtnmenu = (ImageView) findViewById(R.id.iv_wanfa);
        rightbtnmenu.setImageResource(R.drawable.laiji1);


        findViewById(R.id.iv_anim).setVisibility(View.GONE);

        findViewById(R.id.iv_select).setVisibility(View.INVISIBLE);

        btnlayout = (LinearLayout) findViewById(R.id.btnlayout);

        buyBtn = (Button) findViewById(R.id.wanchengxuanhao);
        buyTogetherBtn = (TextView) findViewById(R.id.faqihemai);

        listView = (ListView) findViewById(R.id.listview);


        adapterSF = new BasketballmixcartAdapter(BasketballmixCart.this, onCartTouchedCallback);
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
        bottomGuan = (TextView) findViewById(R.id.guoguanfangshi);
        bottomGuanTip = (TextView) findViewById(R.id.moreguoguan);
//        bottomArrow = (ImageView) findViewById(R.id.basket_cart_image_arrow);

        bottomGuan.setText(selectedChuanList.get(0));
        bottomGuanTip.setText("(更多过关方式)");

        setBottomTextView("",false,false);
        setupUI(findViewById(R.id.mainlayout));

    }



    // 设置过关方式奖金，注数，共需多少钱控件的值
    public void setBottomTextView(String defaultGuanStr,boolean f,boolean flag) {


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
            bottomGuan.setVisibility(View.VISIBLE);
            int chuanSize = selectedChuanList.size();
            if (chuanSize > 0) {
                bottomGuan.setText(selectedChuanList.get(0));
                if (chuanSize > 1) {
                    bottomGuanTip.setText("(" + chuanSize + "个过关方式)");
                    zhuNum = 0;
                    for (int i = 0; i < chuanSize; i++) {
                        zhuNum += GetNumUtil.getZhuNum(caculetchoose(selectedArray), selectedChuanList.get(i));
    /*                    zhuNum += BasketballZhuCalculator.getZhuNum(
                                selectedArray, selectedChuanList.get(i));*/
                    }
                } else {
                    bottomGuanTip.setText("(更多过关方式)");
/*                    zhuNum = BasketballZhuCalculator.getZhuNum(selectedArray,
                            selectedChuanList.get(0));*/
                    zhuNum = GetNumUtil.getZhuNum(caculetchoose(selectedArray), selectedChuanList.get(0));
                }
            } else {
                if (!defaultGuanStr.equals("")) {
                    if (!selectedChuanList.contains(defaultGuanStr)) {
                        selectedChuanList.add(defaultGuanStr);
                        bottomGuan.setText(defaultGuanStr);
                        bottomGuanTip.setText("(更多过关方式)");
                        /*zhuNum = BasketballZhuCalculator.getZhuNum(
                                selectedArray, selectedChuanList.get(0));*/
                        zhuNum = GetNumUtil.getZhuNum(caculetchoose(selectedArray), selectedChuanList.get(0));
                    }
                } else {
                    bottomGuan.setVisibility(View.GONE);
                    bottomGuanTip.setText("(更多过关方式)");
                    zhuNum = 0;
                }
            }
        } else {
            bottomGuan.setVisibility(View.GONE);
            bottomGuanTip.setText("至少选2场比赛");
            rightbtnmenu.setSelected(false);
            adapterSF.setMode(false);
        }


        bottomZhuSu.setText(String.valueOf(zhuNum));
        try {
            bottomSumMoney.setText(String.valueOf(zhuNum * 2 *getBeisu(bottomBeiEditText)));

        } catch (NumberFormatException e) {

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
                zhuNum += GetNumUtil.getZhuNum(caculetchoose(selectedArray), selectedChuanList.get(i));

            }
            bottomZhuSu.setText(String.valueOf(zhuNum));
            bottomSumMoney.setText(String.valueOf(zhuNum   * 2   * getBeisu(bottomBeiEditText )));
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


        rightbtnmenu.setOnClickListener(new View.OnClickListener() {

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

        leftbtnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Control.getInstance()
                        .getBasketballManager().selectedLotteryGameArray
                        .get(currentWanfaGuan);
                Control.getInstance().getBasketballManager()
                        .clearSelected(currentWanfaGuan);
                adapterSF.notifyDataSetChanged();
                finish();

            }
        });
        btnlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                onBackPressed();

            }

        });


        guoGuanLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SparseArray<BasketballOneGame> item = Control.getInstance()
                        .getBasketballManager().selectedLotteryGameArray
                        .get(currentWanfaGuan);

                if (item == null || item.size() < 2) {
                    Toast.makeText(BasketballmixCart.this, "至少选择2场比赛",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                initDilog();



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

        buyBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (selectedChuanList.size() == 0) {
                    Toast.makeText(BasketballmixCart.this, "请选择过关方式",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                SparseArray<BasketballOneGame> item = Control.getInstance()
                        .getBasketballManager().selectedLotteryGameArray
                        .get(currentWanfaGuan);
                if (item == null || item.size() < 2) {
                    Toast.makeText(BasketballmixCart.this, "请至少选择2场比赛",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

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


                if (TextUtils.isEmpty(getSession())) {
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                } else {
                    showLoading("订单提交中...");// boolean isLucky, GoumaiData goumaiData, int wfIndex, int flag, Context context, IBuyCallBack buyCallBack
                    BuyConfirm buyConfirm = new BuyConfirm(getCurrentCaipiao(), false, goucaiData, currentWanfa, Control.getInstance().getBasketballManager().getDanorguo(), BasketballmixCart.this, new IBuyCallBack() {
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

        buyTogetherBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//

                if (selectedChuanList.size() == 0) {
                    Toast.makeText(BasketballmixCart.this, "请选择过关方式",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                SparseArray<BasketballOneGame> item = Control.getInstance()
                        .getBasketballManager().selectedLotteryGameArray
                        .get(currentWanfaGuan);
                if (item == null || item.size() < 2) {
                    Toast.makeText(BasketballmixCart.this, "请至少选择2场比赛",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(BasketballmixCart.this,
                        TogeterBuyActivity.class);

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
                intent.putExtra("goumaidata", goucaiData);
                intent.putExtra("isJingcai", true);
                intent.putExtra("wfindex", currentWanfa);
                startActivity(intent);
                pullUpActivityAnim();

            }

        });

    }


    private void initDilog() {
        setupBottomGuanPopData(selectedChuanList, BasketballmixCart.this, maxChuanNum);

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


    List<GetNumModel> caculetchoose(SparseArray<BasketballOneGame> selectedArray) {
        List<GetNumModel> list = new ArrayList<>();
        for (int i = 0; i < selectedArray.size(); i++) {
            int choosezhu = 0;
            GetNumModel getNumModel = new GetNumModel();
            getNumModel.setDan(false);
            getNumModel.setMaxSP(10000);
            getNumModel.setMixSP(0);
            BasketballOneGame basketballOneGame = selectedArray.get(selectedArray.keyAt(i));
            if (basketballOneGame.isSFSelected[0] == true) {
                choosezhu++;
            }
            if (basketballOneGame.isSFSelected[1] == true) {
                choosezhu++;
            }
            if (basketballOneGame.isRFSFSelecter[0] == true) {
                choosezhu++;
            }
            if (basketballOneGame.isRFSFSelecter[1] == true) {
                choosezhu++;
            }
            if (basketballOneGame.isBigSmallSelect[0] == true) {
                choosezhu++;
            }
            if (basketballOneGame.isBigSmallSelect[1] == true) {
                choosezhu++;
            }
            choosezhu += basketballOneGame.SFCGuestFlag;
            choosezhu += basketballOneGame.SFCHostFlag;
            getNumModel.setCount(choosezhu);
            L.d("zhushu", choosezhu + "");
            list.add(getNumModel);
        }
        return list;
    }
}
