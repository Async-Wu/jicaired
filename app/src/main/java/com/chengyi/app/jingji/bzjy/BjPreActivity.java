package com.chengyi.app.jingji.bzjy;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.home.hemai.TogeterBuyActivity;
import com.chengyi.app.listener.IBottomList;
import com.chengyi.app.model.model.GoumaiData;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoZhushuAndJiangjin;
import com.chengyi.app.util.IP;
import com.chengyi.app.util.L;
import com.chengyi.app.web.WebViewActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BjPreActivity extends BaseActivity implements BjAdapter.IDelete ,IBottomList {


    private ArrayList<DataBean> dataBeen = new ArrayList<>();
    private int type;
    private ListView listview;
    private TextView zhushu, yuan;
    private LinearLayout guoguanlayout;

    private TextView guoguanfangshi;

    private EditText beishueditext;
    private LinearLayout ll_menu;
    ArrayList<String> guoGuanKind = new ArrayList<String>();

    private BjAdapter adapter;
    private List<MatchBean> m = new ArrayList<>();



    private Button wanchengxuanhao;
    private List<JingcaizuqiuOneGame> listTemp = new ArrayList<>();
    ArrayList<String> guoGuan2 = new ArrayList<String>();


    private TextView faqihemai;
    private TextView moreGuoGuan;
    private long zhuNum;
    private double minJiangjin;
    private double maxJiangjin;
    private int gameNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bj_pre);
        beishueditext = (EditText) findViewById(R.id.beishueditext);
        guoGuan1 = new ArrayList<>();
        guoGuan2 = new ArrayList<>();
        CaipiaoApplication.getInstance().clearDelete();

        findViewById(R.id.tv_danguan).setVisibility(View.GONE);
        findViewById(R.id.iv_select).setVisibility(View.INVISIBLE);
        findViewById(R.id.iv_anim).setVisibility(View.GONE);
        ((ImageView) findViewById(R.id.iv_wanfa)).setImageResource(R.drawable.laiji1);


        findViewById(R.id.iv_anim).setVisibility(View.GONE);

        issueId = getIntent().getStringExtra("issueId");


        findViewById(R.id.jiangjinlayout).setVisibility(View.GONE);

        guoguanlayout = (LinearLayout) findViewById(R.id.guoguanlayout);
        guoguanlayout.setOnClickListener(this);
        findViewById(R.id.btnlayout).setOnClickListener(this);
        guoguanfangshi = (TextView) findViewById(R.id.guoguanfangshi);
        findViewById(R.id.ll_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        faqihemai = (TextView) findViewById(R.id.faqihemai);
        faqihemai.setOnClickListener(this);

        setCusTomeTitle("北京单场投注确认");
        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
        ll_menu.setOnClickListener(this);
        ll_menu.setVisibility(View.VISIBLE);
        title_back_right = (ImageView) findViewById(R.id.iv_wanfa);


        moreGuoGuan = (TextView) findViewById(R.id.moreguoguan);




        beishueditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int beiuu = 1;
                try {
                    beiuu = Integer.parseInt(beishueditext.getText().toString());
                } catch (Exception e) {
                    beiuu = 1;
                }


                yuan.setText(String.valueOf(2 * beiuu * Integer.parseInt(zhushu.getText().toString())));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        findViewById(R.id.btnlayout).setOnClickListener(this);
        listview = (ListView) findViewById(R.id.listview);
        wanchengxuanhao = (Button) findViewById(R.id.wanchengxuanhao);
        wanchengxuanhao.setOnClickListener(this);
        zhushu = (TextView) findViewById(R.id.zhushu);
        yuan = (TextView) findViewById(R.id.yuan);

    }


    // 点击过关方式中的按钮，修改过关方式，注数，奖金，是否胆拖等
    public void setBottomTextView(String s, boolean zuhe, boolean isSelected) {
        if (isSelected) {
            guoGuanKind = removeHad(guoGuanKind);
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


    @Override
    protected void onResume() {
        super.onResume();
        type = getIntent().getIntExtra("type", 0);
        dataBeen = (ArrayList<DataBean>) getIntent().getSerializableExtra("date");
        resetM();
        adapter = new BjAdapter(m, BjPreActivity.this, type);

        listview.setAdapter(adapter);

        if (m.size() < 8) {
            guoGuanKind.add(m.size() + "串" + 1);
        } else {
            guoGuanKind.add(8 + "串" + 1);
        }

        getZhuNum();
        minJiangjin = CaipiaoZhushuAndJiangjin.getMinValue();
        maxJiangjin = CaipiaoZhushuAndJiangjin.getMaxValue();
        getStrFromGuoGuanKind(guoGuanKind);
        setViewText(guoGuanStr, zhuNum, minJiangjin, maxJiangjin);
        if (m.size() < 8) {
            guoguanfangshi.setText(m.size() + "串" + 1);
        } else {
            guoguanfangshi.setText(8 + "串" + 1);
        }

    }

    private void resetM() {
        m.clear();
        for (int i = 0; i < dataBeen.size(); i++) {

            for (int j = 0; j < dataBeen.get(i).getMatches().size(); j++) {

                if (type == 0) {
                    if (dataBeen.get(i).getMatches().get(j).getspf()) {
                        m.add(dataBeen.get(i).getMatches().get(j));
                    }
                } else if (type == 1) {
                    if (dataBeen.get(i).getMatches().get(j).getSp().zjq()) {
                        m.add(dataBeen.get(i).getMatches().get(j));
                    }
                } else if (type == 2) {
                    if (dataBeen.get(i).getMatches().get(j).getSp().ds()) {
                        m.add(dataBeen.get(i).getMatches().get(j));
                    }
                } else if (type == 3) {
                    if (dataBeen.get(i).getMatches().get(j).getSp().bifen()) {
                        m.add(dataBeen.get(i).getMatches().get(j));
                    }
                } else if (type == 4) {
                    if (dataBeen.get(i).getMatches().get(j).getSp().bqc()) {
                        m.add(dataBeen.get(i).getMatches().get(j));
                    }
                }

            }


        }


    }


    public String getNumber() {
        number = "";
        for (MatchBean matchBean : m) {

            if (type == 0) {
                number = number + matchBean.getSpfContent() + "$";
            } else if (type == 1) {

                number = number + matchBean.getZjqNum() + "$";
            } else if (type == 2) {

                number = number + matchBean.getSxdNum() + "$";
            } else if (type == 3) {

                number = number + matchBean.getBfNum() + "$";
            } else if (type == 4) {

                number = number + matchBean.getbqcNum() + "$";
            }

        }
        if (number.endsWith("$"))
            number = number.substring(0, number.length() - 1);

        return number;

    }


    private String number = "";
    ImageView/* arrowimage, imageViewpopbg, */title_back_right;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.wanchengxuanhao:
                if (m.size() < 2) {
                    Toast.makeText(this, "少于两场", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(zhushu.getText().toString()) || Long.parseLong(zhushu.getText().toString()) < 1) {
                    Toast.makeText(this, "不能发起0注购买", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(getSession())) {
                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    return;
                }

                buy();
                break;

            case R.id.btnlayout:
                finish();
                break;
            case R.id.ll_menu:
                title_back_right.setSelected(!adapter.isDelete());
                adapter.setDelete(!adapter.isDelete());
                adapter.notifyDataSetChanged();
                break;

            case R.id.faqihemai:
                if (TextUtils.isEmpty(zhushu.getText().toString()) || Long.parseLong(zhushu.getText().toString()) < 1) {
                    Toast.makeText(this, "不能发起0注购买", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isEmpty(getSession())) {
                    Intent intent = new Intent(this, TogeterBuyActivity.class);
                    GoumaiData goucaiData = new GoumaiData();
                    goucaiData.setCaipiaoId(CaipiaoConst.ID_BALL);
                    try {
                        goucaiData.setBeishu(Integer.parseInt(beishueditext.getText().toString().trim()));
                    } catch (Exception e) {
                        goucaiData.setBeishu(1);
                    }
                    resetM2G();
                    goucaiData.setShow("content=" + getNumber());
                    goucaiData.setTotalZhushu(Long.parseLong(zhushu.getText().toString()));

                    goucaiData.setGameSize(m.size());
                    goucaiData.setGuoGuanStr(guoGuanStr);
                    goucaiData.setListTemp(listTemp);
                    goucaiData.setIssue(issueId);
                    goucaiData.setIssueIdStr(issueId);
                   L.i("test", issueId);
                    goucaiData.setBeishu(TextUtils.isEmpty(beishueditext.getText().toString()) ? 1 : Integer.parseInt(beishueditext.getText().toString()));

                    intent.putExtra("goumaidata", goucaiData);
                    intent.putExtra("isJingcai", true);
                    intent.putExtra("wfindex", type);

                    startActivity(intent);
                    pullUpActivityAnim();
                } else {

                    Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getBaseContext(), LoginActivity.class));

                }
                break;


            case R.id.guoguanlayout:
                if (m.size() < 2) {
                    Toast.makeText(getBaseContext(), "至少选择2场比赛",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                // 胜平负
                if (type == 2) {
                    if (m.size() >= 6)
                        gameNum=6;

                    else
                    gameNum=(m.size());
                } else if (type == 4 || type == 5) {
                    if (m.size() >= 4)
                        gameNum=4;
                    else
                        gameNum=(m.size());
                } else {
                    gameNum=(m.size());
                }

                setupBottomGuanPopData(guoGuanKind,this,gameNum);

        }
    }


    private String betType = "";

    private void buy() {
        showLoading("提交中...");
//t=2&=content=205`205`庆南FC`江原
        Map<String, String> date = new HashMap<>();


        date.put("multiple", TextUtils.isEmpty(beishueditext.getText().toString()) ? "1" : beishueditext.getText().toString());
        date.put("schemeNumberUnit", zhushu.getText().toString());
        date.put("lotteryId", CaipiaoConst.ID_BALL + "");
        date.put("clientUserSession", getSession());
        date.put("schemeAmount", yuan.getText().toString());
        date.put("buyAmount", yuan.getText().toString());
        date.put("issueCount", "1");

        if (type == 1) {
            date.put("betType", "276");
            betType = "276";
        } else if (type == 0) {
            date.put("betType", "274");
            betType = "274";
        } else if (type == 3) {
            date.put("betType", "280");
            betType = "280";
        } else if (type == 4) {
            date.put("betType", "282");
            betType = "282";
        } else if (type == 2) {
            date.put("betType", "278");
            betType = "278";
        }

        date.put("pass", l2s());
        date.put("buyType", "1");
        date.put("cutRepeat", "false");
        date.put("issueId", String.valueOf(dataBeen.get(0).getIssueId()));
        date.put("issue", String.valueOf(dataBeen.get(0).getIssueId()));
        date.put("schemeNumber", "content=" + getNumber());


        String s = "";

        for (Map.Entry<String, String> entry : date.entrySet()) {
            s = s + "&" + entry.getKey() + "=" + entry.getValue();
        }


        OkHttpUtil.postSubmitForm(IP.IP + URLSuffix.BUY_LOTTERY, date, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                L.d("test", url + json);
                hideLoading();

                try {
                    JSONObject jsonObject = new JSONObject(json);


                    if (jsonObject.optInt("flag") != 1) {
                        Toast.makeText(BjPreActivity.this, jsonObject.optString("errorMessage"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (jsonObject.optInt("flag") == 1) {
                        if (TextUtils.isEmpty(getSession())) {
                            startActivity(new Intent(getBaseContext(), LoginActivity.class));
                        } else {

                            startActivity(new Intent(BjPreActivity.this, WebViewActivity.class)
                                    .putExtra("url", IP.IP + "lottery/scheme_confirm.htm?clientUserSession=" + getSession() + "&schemeId=" +
                                            jsonObject.optInt("schemeId") + "&betType=" + betType + "&appVersion=" + CaipiaoConst.APPVERSION + "&version=" + CaipiaoConst.VERSION
                                            + "&requestType=" + CaipiaoConst.REQUESTTYPE));

                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(String url, String error) {
                hideLoading();
            }
        });

    }


    ArrayList<String> guoGuan1;


    private Map<String, Double> minValueMap = new HashMap<>();

    public void modifyGuoGuanKind() {
        guoGuanKind = removeHad(guoGuanKind);

        for (int i = 0; i < guoGuanKind.size(); i++) {
            if (Integer.parseInt(guoGuanKind.get(i).substring(0, 1)) > m.size()) {
                minValueMap.remove(guoGuanKind.get(i));
                guoGuanKind.remove(i);
                i--;
            }
        }
    }

    public void publicCode() {
        // 修改过关方式
        modifyGuoGuanKind();

        for (int i = 0; i < listTemp.size(); i++) {
            listTemp.get(i).setDanTuo(false);
        }
        adapter.notifyDataSetChanged();
        // 计算注数
        guoGuanKind = removeHad(guoGuanKind);

        getZhuNum();
        minJiangjin = CaipiaoZhushuAndJiangjin.getMinValue();
        maxJiangjin = CaipiaoZhushuAndJiangjin.getMaxValue();
        getStrFromGuoGuanKind(guoGuanKind);
        setViewText(guoGuanStr, zhuNum, minJiangjin, maxJiangjin);
    }


    // 设置过关方式奖金，注数，共需多少钱控件的值
    public void setViewText(String guoGuanStr, long zhuNum, double min,
                            double max) {


        min = 0;
        max = 1000;
        min = (double) ((int) (min * 100 + 0.5)) / 100;
        max = (double) ((int) (max * 100 + 0.5)) / 100;
        guoGuanKind = removeHad(guoGuanKind);

        if (guoGuanKind.size() > 1) {
            moreGuoGuan.setText("(" + guoGuanKind.size() + "个过关方式)");
            guoguanfangshi.setText(guoGuanKind.get(0));
        } else {
            moreGuoGuan.setText("(更多过关)");
            guoguanfangshi.setText(guoGuanStr);
        }
        zhushu.setText(String.valueOf(zhuNum));
        try {
            yuan.setText(String.valueOf(zhuNum * 2
                    * getBeisu(beishueditext)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    // 根据过关方式列表来计算注数
    public void getZhuNum() {
        zhuNum = 0;
        minJiangjin = 1000000000;
        maxJiangjin = 0;
        guoGuanKind = removeHad(guoGuanKind);

        for (int i = 0; i < guoGuanKind.size(); i++) {
            String type = guoGuanKind.get(i);
            resetM2G();
            zhuNum += CaipiaoZhushuAndJiangjin.getZhuNum(listTemp, type);



            double minTemp = CaipiaoZhushuAndJiangjin.getMinValue();
            // /对minValueMap中的数据进行修改
            if (minValueMap.size() > 0 && minValueMap.containsKey(type)) {
                minValueMap.remove(type);
                minValueMap.put(type, minTemp);
            }
            minJiangjin = minJiangjin > minTemp ? (int) minTemp : minJiangjin;
            maxJiangjin += CaipiaoZhushuAndJiangjin.getMaxValue();
        }
        // 当比赛场次发生改变后，过关方式列表会发生修改，此时minValueMap要做出修改
        if (guoGuanKind.size() == 1) {
            if (!minValueMap.containsKey(guoGuanStr))
                minValueMap.put(guoGuanStr, (double) minJiangjin);
        }
        if (guoGuanKind.size() == 0) {
            minJiangjin = 0;
        }
    }


    String guoGuanStr = ""; // 过关方式

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

    String issueId;


    public void resetM2G() {

        listTemp.clear();


        for (MatchBean match : m) {
            JingcaizuqiuOneGame one = new JingcaizuqiuOneGame();
            one.setId(match.getId());
            one.setIssueId(match.getIssue());
            one.setDayOfWeekStr(match.getWeek());
            one.setMatchCode(match.getMatchCode());
            one.setGameName("北京单场");

            one.setShow("content=" + getNumber());
            one.setTeam1(match.getHostName());
            one.setTeam2(match.getGuestName());


            one.setTime(match.getMatchTime());

            if (type == 0) {
                one.setSpfFlag(match.getspfCount() - 1);
                one.setSpfShow(match.getSpfShow());
            } else if (type == 1) {
                one.setSpfFlag(match.getZjqCount() - 1);
                one.setSpfShow(match.getZjqShow());
            } else if (type == 2) {
                one.setSpfFlag(match.getDsCount() - 1);
                one.setSpfShow(match.getDsShow());
            } else if (type == 3) {
                one.setSpfFlag(match.getBfCount() - 1);
                one.setSpfShow(match.getBifenShow());
            } else if (type == 4) {
                one.setSpfFlag(match.getbqcCount() - 1);
                one.setSpfShow(match.getBqcShow());
            }


            listTemp.add(one);


        }


    }


    @Override
    public void delete(List<MatchBean> modes, int j) {

        this.m = modes;
        CaipiaoApplication.getInstance().setBjdcdelet(j);
        getZhuNum();
        minJiangjin = CaipiaoZhushuAndJiangjin.getMinValue();
        maxJiangjin = CaipiaoZhushuAndJiangjin.getMaxValue();
        getStrFromGuoGuanKind(guoGuanKind);
        setViewText(guoGuanStr, zhuNum, minJiangjin, maxJiangjin);
    }


    public String l2s() {
        String ss = "";
        guoGuanKind = removeHad(guoGuanKind);
        for (String s : guoGuanKind) {
            ss = ss + s + ",";
        }

        if (TextUtils.isEmpty(ss)) {
            return m.size() + "串1";
        } else {
            return ss.substring(0, ss.length() - 1);
        }
    }

    /**
     * @param ss
     */
    public ArrayList<String> removeHad(List<String> ss) {

        for (int i = 0; i < ss.size(); i++) {

            for (int j = i + 1; j < ss.size(); j++) {
                if (ss.get(i).equals(ss.get(j))) {
                    ss.remove(i);
                }
            }


        }
        return (ArrayList<String>) ss;

    }


}
