package com.chengyi.app.jingji.football;


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
import com.chengyi.app.model.model.AbsJingcaizuqiuData;
import com.chengyi.app.model.model.GoumaiData;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.user.BuyConfirm;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.CaipiaoZhushuAndJiangjin;
import com.chengyi.app.util.L;
import com.chengyi.app.view.widget.OnJingcaizuqiuBtnListener;

import java.util.*;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class FootballCart extends BaseActivity implements
        View.OnClickListener ,IBottomList {

    // 原始所选择的gameList列表A
    private SparseArray<JingcaizuqiuOneGame> gameSelectedArray = new SparseArray<JingcaizuqiuOneGame>();

    private List<JingcaizuqiuOneGame> list = new ArrayList<JingcaizuqiuOneGame>();
    List<AbsJingcaizuqiuData> listObject;
    // 计算注数所用的list
    List<JingcaizuqiuOneGame> listTemp = new ArrayList<JingcaizuqiuOneGame>();

    int wfIndex = 0, danNumVisible = 0, danNumSelected = 0;
    // 过关方式
    ImageView arrowimage;

    // 所选择的比赛场次
    int gameSize = 0;
    ArrayList<String> guoGuan1;
    ArrayList<String> guoGuan2;
    // 注数
    long zhuNum = 0;
    // /所选择的过关方式列表
    ArrayList<String> guoGuanKind = new ArrayList<String>();
    double minJiangjin = 0, maxJiangjin = 0;


    // 胆码是否变成可见状态
    boolean isDanVisible = false;
    ArrayList<Integer> danMaList = new ArrayList<Integer>();
    String dateTime;// 记录所选择的赛事中最小的日期

    private Map<String, Double> minValueMap = new HashMap<String, Double>();


    ImageView rightbtnmenu;
    LinearLayout addBtn;
    LinearLayout guoGuanLayout;
    String guoGuanStr = ""; // 过关方式
    Button gouMaiBtn;
    TextView guoGuanText, sumMoney, fanWei,  zhuSu, moreGuoGuan,
            faQiHeMaiBtn;
    // 倍数编辑框
    EditText beiShuEdit;



    private ListView listView;
    JingcaiZuqiuTZquerenAdapter adapter;
    String[] saiShiStr;
    Intent in;

    private FootballManager manager;
    private int flag = 0;
    private TextView tv_danguan;


    private int gameNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        manager = Control.getInstance().getFootballballManager();
        guoGuan1 = new ArrayList<>();
        guoGuan2 = new ArrayList<>();
        setContentView(R.layout.fragment_lottery_football_cart);


        findViewById(R.id.iv_select).setVisibility(View.INVISIBLE);
        findViewById(R.id.iv_anim).setVisibility(View.GONE);
        in = getIntent();
        Bundle bundle = getIntent().getExtras();
        wfIndex = bundle.getInt("wIndex");
        flag = bundle.getInt("flag");
        gameSelectedArray = manager.footballSelectedArray.get(wfIndex);

        listObject = (List<AbsJingcaizuqiuData>) in
                .getSerializableExtra("ListObject");
        saiShiStr = in.getStringArrayExtra("saiShiStr");
        initTopView();
        initData();
        initView();

        setupUI(findViewById(R.id.mainlayout));
        publicCode();

    }




    private void initDilog() {

        setupBottomGuanPopData(guoGuanKind,this,gameNum);


    }

    private void initData() {
        int size = gameSelectedArray.size();
        for (int i = 0; i < size; i++) {

            JingcaizuqiuOneGame game = gameSelectedArray.valueAt(i);
            modifyGameSP(game);
            list.add(game);
            listTemp.add(game);
        }
        gameSize = list.size();
    }

    // 初始化顶部按钮等一些控件
    public void initTopView() {
        tv_danguan = (TextView) findViewById(R.id.tv_danguan);
        rightbtnmenu = (ImageView) findViewById(R.id.iv_wanfa);


        findViewById(R.id.iv_anim).setVisibility(View.GONE);


        setCusTomeTitle( "投注确认");
        setBack();

        rightbtnmenu.setOnClickListener(this);
        rightbtnmenu.setImageResource(R.drawable.laiji1);
//
        // 创建对话框
        addBtn = (LinearLayout) findViewById(R.id.btnlayout);
        addBtn.setOnClickListener(this);
    }

    // 初始化listview和底部等一些控件
    public void initView() {
        listView = (ListView) findViewById(R.id.listview);
        if (wfIndex == 2)
            adapter = new ZongjinqiuTZAdapter(this, list, listener);
        else if (wfIndex == 3 || wfIndex == 4 || wfIndex == 5) {
            adapter = new HunHeJingCaiEnsureAdapter(this, list, listener);
            adapter.setType(flag);
            adapter.setWfIndex(wfIndex);
        } else if (wfIndex == 6) {
            adapter = new MixCarAdapter(this, list, listener);
            adapter.setType(flag);
            adapter.setWfIndex(wfIndex);
        } else {
            adapter = new ShengpingfuTZAdapter(this, list, listener);
            adapter.setType(flag);
            adapter.setWfIndex(wfIndex);
        }
        listView.setAdapter(adapter);
        // 选择过关方式
        guoGuanLayout = (LinearLayout) findViewById(R.id.guoguanlayout);
        guoGuanLayout.setOnClickListener(this);
        guoGuanText = (TextView) findViewById(R.id.guoguanfangshi);
        arrowimage = (ImageView) findViewById(R.id.arrowimage);
        // 更多过关方式
        moreGuoGuan = (TextView) findViewById(R.id.moreguoguan);
        // 设置默认过关方式
        if (gameSize <= 4) {
            guoGuanStr = gameSize + "串1";
        } else if (gameSize > 4 && gameSize <= 6) {
            if (wfIndex == 4 || wfIndex == 5) {
                guoGuanStr = "4串1";
                setDanmaVisible();
                // 设置胆码可用个数为5
                danNumVisible = 3;
                isDanVisible = true;
            } else
                guoGuanStr = gameSize + "串1";
        } else {
            if (wfIndex == 4 || wfIndex == 5) {
                guoGuanStr = "4串1";
                setDanmaVisible();
                // 设置胆码可用个数为5
                danNumVisible = 3;
                isDanVisible = true;
            }
            // 总进球
            else if (wfIndex == 2) {
                guoGuanStr = "6串1";
                setDanmaVisible();
                // 设置胆码可用个数为5
                danNumVisible = 5;
                isDanVisible = true;
            } else {
                if (gameSize > 8) {
                    guoGuanStr = "8串1";
                    setDanmaVisible();
                    // 设置胆码可用个数为7
                    danNumVisible = 7;
                    isDanVisible = true;
                } else {
                    guoGuanStr = gameSize + "串1";
                }
            }
        }
        // 购买，发起合买
        gouMaiBtn = (Button) findViewById(R.id.wanchengxuanhao);
        faQiHeMaiBtn = (TextView) findViewById(R.id.faqihemai);
        gouMaiBtn.setOnClickListener(this);
        faQiHeMaiBtn.setOnClickListener(this);
        // 共多少钱
        sumMoney = (TextView) findViewById(R.id.yuan);
        // 奖金范围
        fanWei = (TextView) findViewById(R.id.dangqianjiangjin);
        // 注数
        zhuSu = (TextView) findViewById(R.id.zhushu);
        // 倍数
        beiShuEdit = (EditText) findViewById(R.id.beishueditext);
        beiShuEdit.setOnClickListener(this);


        if (flag == 1) {
            tv_danguan.setVisibility(View.VISIBLE);
            guoGuanLayout.setVisibility(View.GONE);
        } else if (flag == 0) {
            tv_danguan.setVisibility(View.GONE);
            guoGuanLayout.setVisibility(View.VISIBLE);
        }


        beiShuEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable v) {

                if (TextUtils.isEmpty(beiShuEdit.getText().toString()) || v.toString().trim() == "0") {

                    sumMoney.setText(String.valueOf(2 * zhuNum));
                    fanWei.setText(CaipiaoUtil.format_money(2 * minJiangjin) + "~" + CaipiaoUtil.format_money(2 * maxJiangjin)
                            + "元");
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                String sr = TextUtils.isEmpty(beiShuEdit.getText().toString())?beiShuEdit.getHint().toString():beiShuEdit.getText().toString();

                if (sr.trim().length() == 0 || sr.equals("0")) {
                    sr = "1";
                    sumMoney.setText(String.valueOf(2 * zhuNum * Integer.parseInt(sr)));
                    fanWei.setText(CaipiaoUtil.format_money(2 * minJiangjin) + "~"
                            + CaipiaoUtil.format_money(2 * maxJiangjin) + "元");
                } else {
                    int sss = Integer.parseInt(zhuSu.getText().toString()) * Integer.parseInt(sr) * 2;
                    sumMoney.setText("" + sss);
                    fanWei.setText(CaipiaoUtil.format_money(2 * minJiangjin * Integer.parseInt(sr)) + "~"
                            + CaipiaoUtil.format_money(2 * maxJiangjin * Integer.parseInt(sr)) + "元");


                }
            }
        });

        guoGuanKind.add(guoGuanStr);
        // 计算注数
        if (flag == 0) {
            zhuNum = CaipiaoZhushuAndJiangjin.getZhuNum(listTemp, guoGuanStr);
        } else {
            zhuNum = CaipiaoZhushuAndJiangjin.getZhuNum(listTemp, "1串1");
        }


        minJiangjin = CaipiaoZhushuAndJiangjin.getMinValue();
        maxJiangjin = CaipiaoZhushuAndJiangjin.getMaxValue();
        setViewText(guoGuanStr, zhuNum, minJiangjin, maxJiangjin);
        minValueMap.put(guoGuanStr, minJiangjin);
        // 过关方式的弹窗

        // 混合投注的理论奖金先隐藏起来
        if (wfIndex == 3 || wfIndex == 4 || wfIndex == 5) {
            findViewById(R.id.jiangjinlayout).setVisibility(View.GONE);
        }
    }

    // 点击过关方式中的按钮，修改过关方式，注数，奖金，是否胆拖等
     public void setBottomTextView(String s, boolean zuhe, boolean isSelected) {
        if (isSelected) {
            if (zuhe) {
                if (!guoGuanKind.contains(s))
                    guoGuanKind.add(s);
            } else {
                if (!guoGuanKind.contains(s))
                    guoGuanKind.add(s);
                Collections.sort(guoGuanKind, new Comparator<String>() {
                    @Override
                    public int compare(String lhs, String rhs) {

                        return lhs.compareTo(rhs);
                    }
                });
            }
        } else {
            guoGuanKind.remove(s);
        }
        publicCode();
    }


    GoumaiData goucaiData;
    Intent intent;

    @Override
    public void onClick(View v) {

        map.clear();
        // 当前处于编辑模式，点击其他的任何按钮都要恢复正常状态
        if (adapter.isDeleteMode()) {
            rightbtnmenu.setSelected(!rightbtnmenu.isSelected());
            adapter.setDeleteMode(!adapter.isDeleteMode());
            adapter.setType(flag);
            adapter.notifyDataSetChanged();
            return;
        }
        switch (v.getId()) {
            case R.id.iv_wanfa:
                rightbtnmenu.setSelected(!rightbtnmenu.isSelected());
                adapter.setDeleteMode(!adapter.isDeleteMode());
                adapter.setType(flag);
                adapter.notifyDataSetChanged();
                break;
            // 过关方式选择对话框按钮
            case R.id.guoguanlayout:

                if (gameSize < 2 && flag == 0) {

                    Toast.makeText(FootballCart.this, "至少选择2场比赛", Toast.LENGTH_LONG).show();

                    return;
                }
                if (gameSize < 1 && flag == 1) {

                    Toast.makeText(FootballCart.this, "至少选择1场比赛", Toast.LENGTH_LONG).show();

                    return;
                }


                // 胜平负
                if (wfIndex == 2) {
                    if (gameSize >= 6)
                        gameNum=6;
//                        getGuoGuanFangshiStr(6);
                    else
                        gameNum=gameSize;
//                        getGuoGuanFangshiStr(gameSize);
                } else if (wfIndex == 4 || wfIndex == 5) {
                    if (gameSize >= 4)
                        gameNum=4;
//                        getGuoGuanFangshiStr(4);
                    else
                        gameNum=gameSize;
//
                } else {
                    gameNum=gameSize;
//
                }

                initDilog();

                break;
            // 倍数文本编辑框
            case R.id.beishueditext:
                beiShuEdit.setSelection(beiShuEdit.getText().toString().length());
                break;

            // 增加
            case R.id.btnlayout:
                finish();

                break;
            // 购买
            case R.id.wanchengxuanhao:


                // 需要先判断是否过期
                listTemp.clear();
                int min = 100000;
                for (int i = 0; i < list.size(); i++) {
                    JingcaizuqiuOneGame game = list.get(i);
                    if (game.getSpfFlag() >= 0) {
                        listTemp.add(game);
                        if (min > game.getGroupPosition()) {
                            min = game.getGroupPosition();
                            dateTime = game.getMatchCode().substring(0, 8);
                        }
                    }
                }
                if (!check())
                    return;

                if (!TextUtils.isEmpty(getSession())) {
                    goucaiData = new GoumaiData();
                    goucaiData.setCaipiaoId(CaipiaoConst.ID_JINGCAIZUQIU);
                    goucaiData.setTotalZhushu(zhuNum);
                    goucaiData.setGameSize(listTemp.size());
                    goucaiData.setGuoGuanStr(guoGuanStr);
                    goucaiData.setListTemp(listTemp);

                    if (TextUtils.isEmpty(beiShuEdit.getText().toString().trim())) {
                        goucaiData.setBeishu(1);
                        beiShuEdit.setText("1");
                    } else {
                        goucaiData.setBeishu(Integer.parseInt(beiShuEdit.getText()
                                .toString().trim()));
                    }
                    goucaiData.setCaipiaoId(10059);
                    showLoading("订单提交中...");
                    BuyConfirm buyConfirm = new BuyConfirm(getCurrentCaipiao(), false, goucaiData, wfIndex, flag, this, new IBuyCallBack() {
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
                    Intent in = new Intent(this, LoginActivity.class);


                    startActivity(in);
                }

                break;
            // 发起合买
            case R.id.faqihemai:

                // 需要先判断是否过期
                listTemp.clear();
                int min2 = 100000;
                for (int t = 0; t < list.size(); t++) {
                    JingcaizuqiuOneGame game = list.get(t);
                    if (game.getSpfFlag() >= 0) {
                        listTemp.add(game);
                        if (min2 > game.getGroupPosition()) {
                            min2 = game.getGroupPosition();
                            dateTime = game.getMatchCode().substring(0, 8);
                        }
                    }
                }
                if (!check())
                    return;

                if (TextUtils.isEmpty(getUserName())) {
                    startActivity(new Intent(FootballCart.this, LoginActivity.class));
                    return;

                }
                intent = new Intent(this, TogeterBuyActivity.class);
                goucaiData = new GoumaiData();
                goucaiData.setCaipiaoId(10059);
                goucaiData.setBeishu((int) getBeisu(beiShuEdit));
                goucaiData.setTotalZhushu(zhuNum);
                goucaiData.setGameSize(listTemp.size());
                goucaiData.setGuoGuanStr(guoGuanStr);
                goucaiData.setListTemp(listTemp);
                intent.putExtra("goumaidata", goucaiData);
                intent.putExtra("isJingcai", true);
                intent.putExtra("wfindex", wfIndex);
                intent.putExtra("flag", flag);
                startActivity(intent);
                pullUpActivityAnim();

                break;

        }
    }

    public boolean check() {

        if (flag == 1) {
            if (guoGuanKind.size() == 0 || gameSize < 1) {
                Toast.makeText(FootballCart.this, "请选择过关方式", Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        }


        if (flag == 0) {
            if (guoGuanKind.size() == 0 || gameSize < 2) {
                Toast.makeText(FootballCart.this, "请选择过关方式", Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        }
        return true;
    }

    // 设置过关方式奖金，注数，共需多少钱控件的值
    public void setViewText(String guoGuanStr, long zhuNum, double min,
                            double max) {


        min = (double) ((int) (min * 100 + 0.5)) / 100;
        max = (double) ((int) (max * 100 + 0.5)) / 100;
        if (guoGuanKind.size() > 1) {
            moreGuoGuan.setText("(" + guoGuanKind.size() + "个过关方式)");
            guoGuanText.setText(guoGuanKind.get(0));
        } else {
            moreGuoGuan.setText("(更多过关)");
            guoGuanText.setText(guoGuanStr);
        }
        zhuSu.setText(String.valueOf(zhuNum));
        try {
            sumMoney.setText(String.valueOf(zhuNum * 2*getBeisu(beiShuEdit))

                    );
            fanWei.setText(CaipiaoUtil.format_money(2
                    * min
                    *  (getBeisu(beiShuEdit)))
                    + "~"
                    + CaipiaoUtil.format_money(2
                    * max
                    * getBeisu(beiShuEdit
                 )) + "元");
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
    }



    //
    public int getDanNum() {
        if (guoGuanKind.size() > 1) {
            return getMinChuan() - 1;
        } else if (guoGuanKind.size() == 1 && !(guoGuanKind.get(0).toString().equals(gameSize + "串1"))) {
            return getMinChuan() - 1;
        } else {
            return 0;
        }
    }

    private int getMinChuan() {
        int chuan = 0;
        for (int i = 0; i < guoGuanKind.size(); i++) {
            int oldmin = CaipiaoZhushuAndJiangjin.getDanNumFromGuan(guoGuanKind.get(i));
            if (chuan == 0) {
                chuan = oldmin;
            } else if (oldmin < chuan) {
                chuan = oldmin;
            }
        }
        return chuan;
    }

    // 修改过关方式列表里面的数据
    public void modifyGuoGuanKind() {
        for (int i = 0; i < guoGuanKind.size(); i++) {
            if (Integer.parseInt(guoGuanKind.get(i).substring(0, 1)) > gameSize) {
                minValueMap.remove(guoGuanKind.get(i));
                guoGuanKind.remove(i);
                i--;
            }
        }
    }

    // 重新获取可用的比赛列表
    public void getListTemp() {
        listTemp.clear();
        danNumSelected = 0;
        for (int i = 0; i < list.size(); i++) {
            JingcaizuqiuOneGame game = list.get(i);
            if (game.getSpfFlag() >= 0) {
                modifyGameSP(game);
                listTemp.add(game);
                if (game.isDanTuo())
                    danNumSelected++;
            } else {
                game.setDanTuo(false);
            }
        }
    }

    // 根据过关方式列表来计算注数
    public void getZhuNum() {
        zhuNum = 0;
        minJiangjin = 1000000000;
        maxJiangjin = 0;

        if (flag == 1) {
            guoGuanKind.clear();
            guoGuanKind.add("1串1");
        }

        for (int i = 0; i < guoGuanKind.size(); i++) {
            String type = guoGuanKind.get(i);
            L.e(type);
            zhuNum += CaipiaoZhushuAndJiangjin.getZhuNum(listTemp, type);
            double minTemp = CaipiaoZhushuAndJiangjin.getMinValue();
            // /对minValueMap中的数据进行修改
            if (minValueMap.size() > 0 && minValueMap.containsKey(type)) {
                minValueMap.remove(type);
                minValueMap.put(type, minTemp);
            }
            minJiangjin = minJiangjin > minTemp ? minTemp : minJiangjin;
            maxJiangjin += CaipiaoZhushuAndJiangjin.getMaxValue();
        }
        // 当比赛场次发生改变后，过关方式列表会发生修改，此时minValueMap要做出修改
        if (guoGuanKind.size() == 1) {
            if (!minValueMap.containsKey(guoGuanStr))
                minValueMap.put(guoGuanStr, minJiangjin);
        }
        if (guoGuanKind.size() == 0) {
            minJiangjin = 0;
        }
    }

    // 将过关方式列表转换成字符串
    public void getStrFromGuoGuanKind(ArrayList<String> lt) {
        guoGuanStr = "";
        for (int i = 0; i < lt.size(); i++) {
            if (i > 0) {
                guoGuanStr += "/";
            }
            guoGuanStr += lt.get(i);
        }
    }

    // 不同的过关方式胆码可见性是不一样的
    public void setDanmaVisible() {
        for (JingcaizuqiuOneGame game : list) {
            if (game.getSpfFlag() > -1) {
                game.setDanVisible(View.VISIBLE);
            } else {
                game.setDanVisible(View.INVISIBLE);
            }
        }

        adapter.setDanNumSelected(danNumVisible);
        adapter.setType(flag);
        adapter.notifyDataSetChanged();
    }

    public void clearData() {
        setViewText(" ", 0, 0.0, 0.0);
        guoGuanKind.clear();
        guoGuanStr = "";

        if (flag == 0) {

            Toast.makeText(FootballCart.this, "至少选择2场比赛", Toast.LENGTH_LONG).show();


        }
        if (flag == 1) {

            Toast.makeText(FootballCart.this, "至少选择1场比赛", Toast.LENGTH_LONG).show();

        }
    }

    OnJingcaizuqiuBtnListener listener = new OnJingcaizuqiuBtnListener() {

        // 点击胜平负些按钮时，如果处在删除模式，需要先回到不是删除模式
        @Override
        public void reSetData() {

            rightbtnmenu.setSelected(!rightbtnmenu.isSelected());
        }

        // 过关方式修改后点击确认按钮
        @Override
        public void changData(ArrayList<String> lt, double min, double max,
                              Map<String, Double> map) {

        }

        // 点击胆码
        @Override
        public void danMaChangUI(boolean isSelected) {

            if (guoGuanKind.size() == 0) {
                Toast.makeText(FootballCart.this, "请选择过关方式",
                        Toast.LENGTH_SHORT).show();
                return;
            } else {

                // 当胆码选中时
                if (isSelected) {
                    danNumSelected++;
                    L.e("danNumSelected" + danNumSelected + ";" + "danNumVisible" + danNumVisible);
                    if (danNumVisible == danNumSelected) {
                        for (JingcaizuqiuOneGame game : list) {
                            if (!game.isDanTuo()) {
                                game.setDanVisible(View.INVISIBLE);
                            }
                        }
                        adapter.setDanNumSelected(danNumSelected);
                        adapter.setType(flag);
                        adapter.notifyDataSetChanged();
                    }
                }
                // 当胆码没有被选中时
                else {
                    danNumSelected--;
                    if (danNumSelected == danNumVisible - 1) {
                        for (JingcaizuqiuOneGame game : list) {
                            if (!game.isDanTuo() && game.getSpfFlag() > -1)
                                game.setDanVisible(View.VISIBLE);
                        }
                        adapter.setDanNumSelected(danNumSelected);
                        adapter.setType(flag);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            getListTemp();
            getZhuNum();
            setViewText(guoGuanStr, zhuNum, minJiangjin, maxJiangjin);
        }

        // 点击胜平负按钮---原始列表数据条数发生改变
        @Override
        public void buttonChangeUI(JingcaizuqiuOneGame game) {

            // 可能需要修改过关方式
            getListTemp();
            gameSize = listTemp.size();
            // 判断所选比赛场次是否少于2
            if (gameSize < 2 && flag == 0) {
                clearData();
                if (gameSize == 0)
                    rightbtnmenu.setSelected(false);
                return;
            }

            if (game != null) {
                JingcaizuqiuOneGame oneGame = gameSelectedArray
                        .get(game.orderIdLocal);
                if (oneGame != null) {
                    oneGame.reset();
                    gameSelectedArray.remove(game.orderIdLocal);
                } else {
                    gameSelectedArray.put(game.orderIdLocal, game);
                }
            }

            publicCode();
        }

        // 重新计算注数，点击胜平负按钮---原始列表数据条数不发生改变
        @Override
        public void changeZhuNumUI() {

            if (guoGuanKind.size() == 0) {
                Toast.makeText(FootballCart.this, "请选择过关方式",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            getListTemp();
            getZhuNum();
            setViewText(guoGuanStr, zhuNum, minJiangjin, maxJiangjin);
        }

        // 点击删除按钮，彩票大厅中对应的比赛数据对应位置的比赛数据全部设置为未选中
        @Override
        public void qingKongBiSaiData(JingcaizuqiuOneGame game) {

            int parent = game.getGroupPosition();
            int child = game.getChildPosition();
            listObject.get(parent).getGames().get(child).reSetIsSelected();
            listObject.get(parent).getGames().get(child).setSelContentStr("");
            listObject.get(parent).getGames().get(child).setSelectedStr("");
            gameSelectedArray.remove(game.orderIdLocal);
        }

        @Override
        public void changLeftBtnStates() {
            rightbtnmenu.setSelected(false);
        }

    };

    public void publicCode() {
        // 修改过关方式
        modifyGuoGuanKind();
        danNumVisible = getDanNum();
        L.e(danNumVisible + "");
        for (int i = 0; i < list.size(); i++) {

            list.get(i).setDanTuo(false);
        }
        danNumSelected = 0;
        for (int i = 0; i < listTemp.size(); i++) {
            listTemp.get(i).setDanTuo(false);
        }
        // 当不允许选择胆码时
        if (danNumVisible == 0) {
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setDanVisible(View.INVISIBLE);
                list.get(i).setDanTuo(false);
            }
            for (int i = 0; i < listTemp.size(); i++) {
                listTemp.get(i).setDanTuo(false);
            }
            danNumSelected = 0;
            isDanVisible = false;
        } else {
            if (danNumVisible >= danNumSelected) {
                // 显示胆码
                setDanmaVisible();
                isDanVisible = true;
            } else {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setDanVisible(View.VISIBLE);
                    list.get(i).setDanTuo(false);
                }
                for (int i = 0; i < listTemp.size(); i++) {
                    listTemp.get(i).setDanTuo(false);
                }
                danNumSelected = 0;
            }
        }
        adapter.setDanNumSelected(danNumVisible);
        adapter.notifyDataSetChanged();
        // 计算注数
        getZhuNum();
        getStrFromGuoGuanKind(guoGuanKind);
        setViewText(guoGuanStr, zhuNum, minJiangjin, maxJiangjin);
    }

    // /设置每个比赛对象所选择的赔率中最大和最小的
    public void modifyGameSP(JingcaizuqiuOneGame game) {
        double min = 1000000000, max = 0;
        if (wfIndex == 2) {
            double[] zjq = game.getZjqpeilv();
            for (int i = 0; i < 8; i++) {
                if (game.isSelected[i]) {
                    min = min > zjq[i] ? zjq[i] : min;
                    max = max < zjq[i] ? zjq[i] : max;
                }
            }
        } else {
            double[] spf = game.getSpfpeilv();
            for (int i = 0; i < 3; i++) {
                if (game.isSelected[i]) {
                    min = min > spf[i] ? spf[i] : min;
                    max = max < spf[i] ? spf[i] : max;
                }
            }
        }
        game.setMaxSP(max);
        game.setMixSP(min);
    }

}
