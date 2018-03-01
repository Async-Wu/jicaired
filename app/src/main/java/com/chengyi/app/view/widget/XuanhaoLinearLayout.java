package com.chengyi.app.view.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CurIssMode;
import com.chengyi.app.model.caipiao.HadMode;
import com.chengyi.app.model.model.CaipiaoLotteryData;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.model.wanfa.*;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.IP;
import com.chengyi.app.util.L;
import com.chengyi.app.util.net.NetUtil;
import com.chengyi.app.view.scoller.MyRativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@SuppressLint("StringFormatMatches")
public class XuanhaoLinearLayout extends LinearLayout implements
        TouzhuListener, View.OnClickListener {

    private ImageView iv;
    private TextView tvSelct;

    LayoutInflater mInflater;
    Context mContext;
    PopupWindow pressPreview;
    TextView previewTV, qianTishiSpecial;// 预览
    View previewBg;
    Caipiao caipiao;

    TextView[] totalZhushuAndMoney;// 共多少注，多少元j
    TextView[] jiangqiIsue, kaijiangResult;// 奖期，开奖公告


    private int danweiColor = Color.BLACK;
    TextView yaodongxuanhao, shengyushijiantext;// 快彩有剩余时间
    // 快乐10分红投数字
    TouzhuButton btn19, btn20;
    int screent_320 = 320;
    // Button jixuanqueding;// // 机选N注确定按钮
    RelativeLayout bottomlayout;
    LinearLayout linearLayoutjx, xuanhaolayoutbottom;//kjLayout
//    LinearLayoutForListView linearLayoutForListView;

    SharedPreferences preferences;
    Editor editor;
    Handler handler = new Handler();
    ScrollView qiuLayout;
    /**
     * 幸运赛车大小奇偶玩法控件
     */
    LinearLayout xjLyout, xoLyout, djLyout, doLyout;

    public PopupWindow getPressPreviewPop() {
        if (pressPreview == null) {

        }
        return pressPreview;
    }


    public View getPreviewBg() {
        return previewBg;
    }

    public TextView getPreviewTextView() {
        return previewTV;
    }

    public Caipiao getCurrentCaipiao() {
        return caipiao;
    }

    /**
     * 前区容器
     */

    private TextView issueOne;
    private TextView issueTwo;
    private TextView issueThree;
    private TextView issueFour;
    private TextView issueFive;
    private TextView contentOne;
    private TextView contentTwo;
    private TextView contentThree;
    private TextView contentFour;
    private TextView contentFive;

    ViewGroup qianquContainer;
    private LinearLayout llIsue;
    /**
     * 后区容器
     */
    ViewGroup houquContainer;
    // 红投容器
    ViewGroup hongtou_ball_layout;
    ViewGroup hongtou_ball_yilou_text;
    View qianqu_tip_layout;
    int btnTextSize = 12;
    TextView qianqutishi, dangqijiezhi, jiangqihaoma, jiangchitext, tishitext,
            hongtoutishitext, jiangqitextview, failedTx;
    RelativeLayout contenthistorydown;
    LinearLayout drawFailed;

    // 投注按钮列表的列表，如一组的按钮放到List<TouzhuButton>中，全部的List<TouzhuButton>放到listTouzuList中
    List<List<TouzhuButton>> listTouzuList = new ArrayList<List<TouzhuButton>>();
    // 快乐10分红投
    List<TouzhuButton> btnhtList = new ArrayList<TouzhuButton>();
    MyRativeLayout myRelativeLayout;
    Button wanchengxuanhao;// 完成选号
    Button clearBtn;// 清空
    LinearLayout randomBtn;// 机选
    Button jxbtn1, jxbtn2, jxbtn3, jxbtn4, jxbtn5;
    Button tryGetBtn;

    RequestParams params;

    HashMap<String, String> map = new HashMap<String, String>();
    private ArrayList<TextView> tvList = new ArrayList<TextView>();

    public XuanhaoLinearLayout(Context context) {
        super(context);
        preferences = CaipiaoUtil.getCpSharedPreferences(getContext());
        editor = preferences.edit();
        mContext = context;
        loadViews(context);
    }


    public void loadViews(Context context) {
        screent_320 = (int) mContext.getResources().getDimension(
                R.dimen.screent_320);
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.new_main_xuanhaolinearlayout, this, true);
        qianquContainer = (ViewGroup) findViewById(R.id.qianqu_container);
        houquContainer = (ViewGroup) findViewById(R.id.houqu_container);
        hongtou_ball_layout = (ViewGroup) findViewById(R.id.hongtou_ball_layout);
        hongtou_ball_yilou_text = (ViewGroup) findViewById(R.id.hongtou_ball_yilou_text);
        btnTextSize = (int) mContext.getResources().getDimension(
                R.dimen.touzhu_btn_textsize);
        qianqutishi = (TextView) findViewById(R.id.qianqutishi);
        totalZhushuAndMoney = new TextView[2];
        totalZhushuAndMoney[0] = (TextView) findViewById(R.id.xuanhaotishi1);
        totalZhushuAndMoney[1] = (TextView) findViewById(R.id.xuanhaotishi2);
        yaodongxuanhao = (TextView) findViewById(R.id.shoujiyaoyao);
        wanchengxuanhao = (Button) findViewById(R.id.wanchengxuanhao);
        qianqu_tip_layout = findViewById(R.id.qianqu_tip_layout);
        danweiColor = mContext.getResources().getColor(R.color.gray);

        tv_current = (TextView) findViewById(R.id.tv_current);
        tv_current_num = (TextView) findViewById(R.id.tv_current_num);


        tvSelct = (TextView) findViewById(R.id.tv_select);
        iv = (ImageView) findViewById(R.id.iv_select);


        // 剩余时间
        shengyushijiantext = (TextView) findViewById(R.id.shengyushijiantext);
        // 当前期
        dangqijiezhi = (TextView) findViewById(R.id.dangqijiezhi);

        llIsue = (LinearLayout) findViewById(R.id.ll_isue);
        llIsue.setVisibility(GONE);
        issueOne = (TextView) findViewById(R.id.issue_one);
        issueTwo = (TextView) findViewById(R.id.issue_two);
        issueThree = (TextView) findViewById(R.id.issue_three);
        issueFour = (TextView) findViewById(R.id.issue_four);
        issueFive = (TextView) findViewById(R.id.issue_five);


        contentOne = (TextView) findViewById(R.id.content_one);
        contentTwo = (TextView) findViewById(R.id.content_two);
        contentThree = (TextView) findViewById(R.id.content_three);
        contentFour = (TextView) findViewById(R.id.content_four);
        contentFive = (TextView) findViewById(R.id.content_five);


        // /上期开奖号码
        jiangqihaoma = (TextView) findViewById(R.id.jiangqihaoma);
        // /奖池
        jiangchitext = (TextView) findViewById(R.id.jiangchitext);
        // // 机选N注确定按钮
        // jixuanqueding = (Button) findViewById(R.id.jixuanbtn);

        tishitext = (TextView) findViewById(R.id.tishitext);
        // 奖期
        jiangqitextview = (TextView) findViewById(R.id.jiangqitextview);
        qianTishiSpecial = (TextView) findViewById(R.id.qianqutishispecial);
        hongtoutishitext = (TextView) findViewById(R.id.hongtoutishitext);
        bottomlayout = (RelativeLayout) findViewById(R.id.bottomlayout);
        layoutParams = (RelativeLayout.LayoutParams) bottomlayout
                .getLayoutParams();
        width = layoutParams.width;

        linearLayoutjx = (LinearLayout) findViewById(R.id.linearLayoutjx);
        xuanhaolayoutbottom = (LinearLayout) findViewById(R.id.xuanhaolayoutbottom);
        //奖期列表
//        kjLayout = (LinearLayout) findViewById(R.id.kaijianglistlayout);
        // 获取自定义布局文件
        myRelativeLayout = (MyRativeLayout) findViewById(R.id.xuanhaolayoutdown);
        clearBtn = (Button) findViewById(R.id.qingkong);// 清空
        randomBtn = (LinearLayout) findViewById(R.id.jixuanbtn);// 机选
        // linearLayoutForListView = (LinearLayoutForListView) findViewById(R.id.fanganhaomalistview);

        // /机选注数按钮
        jxbtn1 = (Button) findViewById(R.id.jxbtn1);
        jxbtn2 = (Button) findViewById(R.id.jxbtn2);
        jxbtn3 = (Button) findViewById(R.id.jxbtn3);
        jxbtn4 = (Button) findViewById(R.id.jxbtn4);
        jxbtn5 = (Button) findViewById(R.id.jxbtn5);
//
        params = getRequestParams();
//
        contenthistorydown = (RelativeLayout) findViewById(R.id.contenthistorydown);
        qiuLayout = (ScrollView) findViewById(R.id.qiulayout);
        handler.removeCallbacks(query_cur_issue);
        handler.post(query_cur_issue);
        setListener();
    }

    public XuanhaoLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        loadViews(context);
    }

    /**
     * 隐藏机选布局
     **/
    private void hideJiXuanLayout() {
        if (layoutParams == null)
            return;
        linearLayoutjx.setVisibility(View.INVISIBLE);
        qianqutishi.setVisibility(View.VISIBLE);
//
        jiangqihaoma.setTextColor(mContext.getResources().getColor(R.color.jianghaoma));
        jiangqitextview.setTextColor(mContext.getResources().getColor(R.color.qinghao));
//
//        }
        layoutParams.width = width;
        yaodongxuanhao.setVisibility(View.VISIBLE);
    }

    /***
     * 显示机选布局
     **/
    private void showJiXuanLayout() {
        if (layoutParams == null)
            return;
        linearLayoutjx.setVisibility(View.VISIBLE);
        if (caipiao.getId() == 10065) {
            isSelect(true);
        } else {
            isSelect(true);
        }

        layoutParams.width = LayoutParams.FILL_PARENT;
        yaodongxuanhao.setVisibility(View.GONE);
    }

    RelativeLayout.LayoutParams layoutParams;
    int width = 0;

    @Override
    public void onClick(View v) {

        map.clear();
        switch (v.getId()) {
            case R.id.jixuanbtn:
                if (canRandom()) {
                    // 机选布局隐藏起来
                    if (linearLayoutjx.getVisibility() == View.VISIBLE) {
                        hideJiXuanLayout();
                        isSelect(true);
                    }
                    // 机选布局展现起来
                    else {
                        showJiXuanLayout();
                        isSelect(false);
                    }
                    bottomlayout.setLayoutParams(layoutParams);
                } else {
                    if (getCurrentCaipiao().getCurrentWanfa().getType() >= 102)
                        showToast("胆拖玩法不能机选");
                    else
                        showToast(getCurrentCaipiao().getCurrentWanfa().getName()
                                + "玩法不能机选");
                }


                break;
            case R.id.wanchengxuanhao:
                finishChoose(v);
                break;
            case R.id.qingkong:
                setDefaultSelected(null);
                isSelect(true);

                break;
            case R.id.jxbtn1:
                jixuanNum = 1;
                isSelect(true);
                finishChoose(v);

                break;
            case R.id.jxbtn2:
                isSelect(true);
                jixuanNum = 2;
                finishChoose(v);

                break;
            case R.id.jxbtn3:
                isSelect(true);
                jixuanNum = 3;
                finishChoose(v);

                break;
            case R.id.jxbtn4:
                isSelect(true);
                jixuanNum = 5;
                finishChoose(v);

                break;
            case R.id.jxbtn5:
                isSelect(true);
                jixuanNum = 10;
                finishChoose(v);

                break;
            case R.id.contenthistorydown:
            case R.id.jiangqihaoma:
                handler.post(requestKaijiangContext);


                if (llIsue.getVisibility() == GONE) {
                    llIsue.setVisibility(VISIBLE);
                } else {
                    llIsue.setVisibility(GONE);
                }

                break;
            case R.id.layout1:
            case R.id.layout2:
            case R.id.layout3:
            case R.id.layout4:
                ((DaXiaoJQAbsWanFa) getCurrentWanfa()).checkView(v);
                break;
        }
    }

    private void setListener() {
        // 定义控件设置弹窗
        myRelativeLayout.setPressPreview(getPressPreviewPop());
        randomBtn.setOnClickListener(this);
        wanchengxuanhao.setOnClickListener(this);
        clearBtn.setOnClickListener(this);
        jxbtn1.setOnClickListener(this);
        jxbtn2.setOnClickListener(this);
        jxbtn3.setOnClickListener(this);
        jxbtn4.setOnClickListener(this);
        jxbtn5.setOnClickListener(this);
        contenthistorydown.setOnClickListener(this);

        handler.post(requestKaijiangContext);
    }

    private String checkBeforeRandom(boolean qianquRandom, int randomNum) {
        List<List<TouzhuButton>> list = caipiao.getListTouzuList();
        if (qianquRandom) {
            if (list.size() == 2) {
                int houquSelected = CaipiaoUtil
                        .getSelectedBtnCount(list.get(1));
                int total = CaipiaoUtil.getCombination(randomNum,
                        caipiao.getQianquMinCount())
                        * CaipiaoUtil.getCombination(houquSelected,
                        caipiao.getHouquMinCount());
                if (total > caipiao.getMaxTouzhuCount()) {
                    String s = String.format(
                            mContext.getString(R.string.bunengchaoguo),
                            caipiao.getMaxTouzhuCount() * CaipiaoConst.PRICE);
                    return s;
                }
            } else {
                int total = CaipiaoUtil.getCombination(randomNum,
                        caipiao.getQianquMinCount());
                if (total > caipiao.getMaxTouzhuCount()) {
                    String s = String.format(
                            mContext.getString(R.string.bunengchaoguo),
                            caipiao.getMaxTouzhuCount() * CaipiaoConst.PRICE);
                    return s;
                }
            }
        } else {
            int qianquSelected = CaipiaoUtil.getSelectedBtnCount(list.get(0));
            int total = CaipiaoUtil.getCombination(qianquSelected,
                    caipiao.getQianquMinCount())
                    * CaipiaoUtil.getCombination(randomNum,
                    caipiao.getHouquMinCount());
            if (total > caipiao.getMaxTouzhuCount()) {
                String s = String.format(
                        mContext.getString(R.string.bunengchaoguo),
                        caipiao.getMaxTouzhuCount() * CaipiaoConst.PRICE);
                return s;
            }
        }
        return null;
    }

    private void setBtnStateByArray(List<TouzhuButton> list, int[] randomArray) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < randomArray.length; j++) {
                if (randomArray[j] == i) {
                    list.get(i).setSelected(true);
                    break;
                } else {
                    list.get(i).setSelected(false);
                }
            }
        }
        try {
            getCurrentWanfa().getTouzhuListener().onTouzhuCountChange();
        } catch (Exception e) {

        }
    }

    /**
     * @param cp
     * @param focusResetAll 强制全部初始化，主要为了玩法设置。比如手动选号时跳到购彩界面，要把选中玩法移过来，而在切换玩法时不移动
     */
    public void changeView(Caipiao cp, boolean focusResetAll) {

        if (focusResetAll || null == caipiao || cp.getId() != caipiao.getId()) {
            if (myRelativeLayout.isShow())
                myRelativeLayout.close();
            caipiao = cp;
            CaipiaoApplication.getInstance().setCurrentCaipiao(cp);
//
        }
        if (preferences == null)
            preferences = CaipiaoUtil.getCpSharedPreferences(mContext);
        if (canRandom() && preferences.getBoolean("dakaiyaodongxuanhao", true)) {
            yaodongxuanhao.setText(mContext.getResources().getString(R.string.yaoyaoshoujijixuanyizhu));
            randomBtn.setVisibility(View.VISIBLE);
        } else {
            yaodongxuanhao.setText("已选0注,共0元");
            randomBtn.setVisibility(View.INVISIBLE);
            clearBtn.setVisibility(View.VISIBLE);
        }
        hideJiXuanLayout();
        qianquContainer.removeAllViews();
        houquContainer.removeAllViews();
        hongtou_ball_layout.removeAllViews();
        hongtou_ball_yilou_text.removeAllViews();
        // /前驱提示：数投 隐藏
        qianTishiSpecial.setVisibility(View.GONE);
        listTouzuList.clear();
        btnhtList.clear();
        caipiao.setListTouzuList(listTouzuList);
        if (caipiao.getCurrentWanfa() != null)
            caipiao.getCurrentWanfa().setTouzhuListener(this);
        // 由于快乐10分，前一数投只有18个球故需要重新设置球的个数
        int wanfaType = caipiao.getCurrentWanfa().getType();
        // 快三界面特殊处理
        if (CaipiaoUtil.isKySj(cp.getId())) {
            kuaiSanLayoutView(wanfaType);
            caipiao.setBtnList(kSanBtnList);
            caipiao.setkSanLastBtnList(kSanLastBtnList);
            caipiao.setkSanFiveBtnList(kSanFiveBtnList);
            //幸运赛车的大小奇偶玩法
        } else if (caipiao.getId() == CaipiaoConst.ID_LUCKYCAR && wanfaType == CaipiaoConst.WF_DAXIAOJQ) {
            caipiao.setBtnList(kSanBtnList);
        }
        if (caipiao.getId() == CaipiaoConst.ID_KUAILE10FEN) {
            if (CaipiaoUtil.isRenOne(wanfaType)) {
                findViewById(R.id.hongtou_tip_layout).setVisibility(View.VISIBLE);
                hongtou_ball_layout.setVisibility(View.VISIBLE);
                hongtoutishitext.setVisibility(View.VISIBLE);
                hongtoutishitext.setText(CaipiaoUtil.formatTextColorRed(mContext
                        .getString(R.string.shouweihaomwei19huo20)));
                caipiao.setQianquList(CaipiaoUtil.getHaomaList(1, 18));
                LayoutParams LP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                LP.gravity = Gravity.CENTER;
                LP.weight = 1;
                LP.leftMargin = 20;
                LP.rightMargin = 20;
                hongtou_ball_layout.addView(getOneBtn("19", R.drawable.new_qianqu_states, R.color.qianqubtnselector, redPreviewbg, btnhtList), LP);

                hongtou_ball_layout.addView(getOneBtn("20", R.drawable.new_qianqu_states, R.color.qianqubtnselector, redPreviewbg, btnhtList), LP);
                // /判断遗漏是否打开
                if (CaipiaoUtil.showYilou(mContext,
                        getCurrentCaipiao().getId(), getCurrentWanfa())) {
                    findViewById(R.id.hongtou_ball_yilou_layout).setVisibility(View.VISIBLE);
                    TextView[] tvArray = new TextView[2];
                    tvArray[0] = getYilouTextView();
                    tvArray[1] = getYilouTextView();
                    CaipiaoUtil.setYilouData(tvArray, getCurrentCaipiao()
                            .getId(), getCurrentWanfa().getType(), 0, 18);
                    hongtou_ball_yilou_text.addView(tvArray[0], LP);
                    LayoutParams LP3 = new LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                    LP3.leftMargin = 65;
                    hongtou_ball_yilou_text.addView(tvArray[1], LP3);
                    TextView liyou = (TextView) findViewById(R.id.yilouspecial);
                    liyou.setTextSize(12);
                } else {
                    findViewById(R.id.hongtou_ball_yilou_layout).setVisibility(
                            View.GONE);
                }
            } else {
                findViewById(R.id.hongtou_tip_layout).setVisibility(View.GONE);
                hongtou_ball_layout.setVisibility(View.GONE);
                findViewById(R.id.hongtoutishitext).setVisibility(View.GONE);
                caipiao.setQianquList(CaipiaoUtil.getHaomaList(1, 20));
                findViewById(R.id.hongtou_ball_yilou_layout).setVisibility(View.GONE);
            }
        } else {
            findViewById(R.id.hongtou_tip_layout).setVisibility(View.GONE);
            hongtou_ball_layout.setVisibility(View.GONE);
            findViewById(R.id.hongtoutishitext).setVisibility(View.GONE);
            findViewById(R.id.hongtou_ball_yilou_layout).setVisibility(
                    View.GONE);
        }
        switch (wanfaType) {
            case CaipiaoConst.WF_DALETOUSHENGXIAO:
                shengxiao();
                break;
            case CaipiaoConst.WF_NORMAL_LETOU:
                letou();
                break;
            case CaipiaoConst.WF_DANTUO:
            case CaipiaoConst.WF_DANTUO_ZU3:
            case CaipiaoConst.WF_DANTUO_ZU6:
                dantuo();
                break;
            case CaipiaoConst.WF_NORMAL_PAILIE:
            case CaipiaoConst.WF_WX_ZHIXUAN:// 五星直选
            case CaipiaoConst.WF_WX_TONGXUAN:// 五星通选
            case CaipiaoConst.WF_REN1:// 任1，按位相符
            case CaipiaoConst.WF_REN2:// 任2，按位相符
                pailie();
                break;
            case CaipiaoConst.WF_ZU3:
            case CaipiaoConst.WF_ZU6:
            case CaipiaoConst.WF_SX_ZU3:
            case CaipiaoConst.WF_SX_ZU6:
            case CaipiaoConst.WF_QIAN2_ZUXUAN:// 前2组选
            case CaipiaoConst.WF_QIAN3_ZUXUAN:// 前3组选
            case CaipiaoConst.WF_LIAN2ZX:
                zu3zu6();
                break;
            case CaipiaoConst.WF_QIAN1:
            case CaipiaoConst.WF_DANTUO_REN1:
            case CaipiaoConst.WF_HOU1:
            case CaipiaoConst.WF_YX_ZHIXUAN:// 一星直选，竞猜最后一位
                tvList.clear();
                qianN_houN(1);
                if (caipiao.getId() == 10067)
                    handler.post(getJiangJinRunnable);
                break;
            case CaipiaoConst.WF_QIAN2:
            case CaipiaoConst.WF_HOU2:
            case CaipiaoConst.WF_EX_ZHIXUAN:// 二星直选，竞猜后2位
            case CaipiaoConst.WF_LIAN2ZHX:// 连2直选
            case CaipiaoConst.WF_ZU3_SINGLE:
                qianN_houN(2);
                break;
            case CaipiaoConst.WF_QIAN3:
            case CaipiaoConst.WF_QIAN3ZHIX:
            case CaipiaoConst.WF_SX_ZHIXUAN:// 三星直选，竞猜后3位
                qianN_houN(3);
                break;
            case CaipiaoConst.WF_SIXING_ZHIXUAN:// 四星直选，竞猜后4位
                qianN_houN(4);
                break;
            case CaipiaoConst.WF_EX_ZUXUAN:// 二星组选，竞猜后2位，顺序不限
                // zu2();
                zu3zu6();
                break;
            case CaipiaoConst.WF_DXDS:
                daxiaodanshuang();
                break;
            case CaipiaoConst.WF_REN2_2:
            case CaipiaoConst.WF_REN3_3:
            case CaipiaoConst.WF_REN4_4:
            case CaipiaoConst.WF_REN5_5:
            case CaipiaoConst.WF_REN6_5:
            case CaipiaoConst.WF_REN7_5:
            case CaipiaoConst.WF_REN8_5:
                zu3zu6();
                break;
            // /快乐10分胆拖
            case CaipiaoConst.WF_DANTUO_LIAN2ZX:
            case CaipiaoConst.WF_DANTUO_LIAN2ZHX:
            case CaipiaoConst.WF_DANTUO_QIAN3ZHIX:
            case CaipiaoConst.WF_DANTUO_QIAN3ZX:
            case CaipiaoConst.WF_DANTUO_REN2:
            case CaipiaoConst.WF_DANTUO_REN3:
            case CaipiaoConst.WF_DANTUO_REN4:
            case CaipiaoConst.WF_DANTUO_REN5:
                // 新老11选5胆拖
            case CaipiaoConst.WF_DANTUO_REN6:
            case CaipiaoConst.WF_DANTUO_REN7:
            case CaipiaoConst.WF_DANTUO_REN8:
            case CaipiaoConst.WF_DANTUO_SX_ZHIXUAN:
            case CaipiaoConst.WF_DANTUO_EX_ZUXUAN:
                kuaiShiFenDantuo();
                break;
            // 幸运赛车的大小奇偶玩法
            case CaipiaoConst.WF_DAXIAOJQ:
                tvList.clear();
                luckyDaXiaoJO();
                handler.post(getJiangJinRunnable);
                break;
        }
        if (focusResetAll) {
            handler.removeCallbacks(changeData);
            handler.post(changeData);

            fillKaijiangjieguo();
        }
        // 快乐10分 前1玩法特殊处理
        if (getCurrentCaipiao().getId() == CaipiaoConst.ID_KUAILE10FEN
                && CaipiaoUtil.isRenOne(getCurrentWanfa().getType())) {
            List<TouzhuButton> temp = new ArrayList<>();
            temp = listTouzuList.get(0);
            if (!temp.contains(btnhtList.get(0)))
                temp.add(btnhtList.get(0));
            if (!temp.contains(btnhtList.get(1)))
                temp.add(btnhtList.get(1));
        }
        wanchengxuanhao.setEnabled(true);
        onTouzhuCountChange();
        qiuLayout.scrollTo(0, 0);
    }

    /**
     * 幸运赛车的大小奇偶界面布局
     */
    private void luckyDaXiaoJO() {
        houquContainer.setVisibility(View.GONE);
        houquContainer.setVisibility(View.GONE);
        qianqu_tip_layout.setVisibility(View.VISIBLE);
        qianqutishi.setVisibility(View.VISIBLE);
        qianqutishi.setText(mContext
                .getString(R.string.caizhongbisaiguanjunqian7));
        View layoutView = mInflater.inflate(R.layout.new_luckycar_view, null,
                false);
        qianquContainer.addView(layoutView, k3LP);
        xjLyout = (LinearLayout) layoutView.findViewById(R.id.layout1);
        xjLyout.setOnClickListener(this);
        xoLyout = (LinearLayout) layoutView.findViewById(R.id.layout2);
        xoLyout.setOnClickListener(this);
        djLyout = (LinearLayout) layoutView.findViewById(R.id.layout3);
        djLyout.setOnClickListener(this);
        doLyout = (LinearLayout) layoutView.findViewById(R.id.layout4);
        doLyout.setOnClickListener(this);
        kSanBtnList.clear();
        kSanBtnList.add(xjLyout);
        kSanBtnList.add(xoLyout);
        kSanBtnList.add(djLyout);
        kSanBtnList.add(doLyout);
        tvList.clear();
        tvList.add((TextView) layoutView.findViewById(R.id.textView2));
        tvList.add((TextView) layoutView.findViewById(R.id.textView3));
        tvList.add((TextView) layoutView.findViewById(R.id.textView4));
        tvList.add((TextView) layoutView.findViewById(R.id.textView5));
    }

    /**
     *
     */
    Runnable getJiangJinRunnable = new Runnable() {
        @Override
        public void run() {
            // 请求奖期前检查网络是否可用
            if (NetUtil.isNetworkAvailable(mContext)) {
                RequestParams params = getRequestParams();
                if (CaipiaoUtil.isRenOne(getCurrentWanfa().getType()))
                    params.put("betType", 0 + "");
                else if (getCurrentWanfa().getType() == CaipiaoConst.WF_YX_ZHIXUAN)
                    params.put("betType", 5 + "");
                else if (getCurrentWanfa().getType() == CaipiaoConst.WF_DAXIAOJQ)
                    params.put("betType", 8 + "");
                params.put("issue", getCurrentCaipiao().getIssue());


            }
        }
    };
    private HttpRespHandler luckyCarHandler = new HttpRespHandler() {

        @Override
        public void onSuccess(String response) {
            super.onSuccess(response);


            JSONArray list = JSON.parseObject(response).getJSONArray("list");
            String str = list.getJSONObject(0).getString("prize");
            try {
                String[] array = str.substring(1, str.length() - 1).split(",");
                for (int i = 0; i < array.length; i++) {
                    tvList.get(i).setText(array[i] + "元");
                    tvList.get(i).setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };
    LayoutParams k3LP = new LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    LayoutParams tempLp = getTempLayoutParams();
    View kuaiSanView;
    TextView kSanTishiText, kSanTishiTextDown;
    LinearLayout firstContainer, secondContainer, threeContainer,
            fourContainer, fiveContainer, sixContainer, shunZiLayout;
    ArrayList<View> kSanBtnList = new ArrayList<>();
    List<Button> kSanLastBtnList = new ArrayList<>();// /后区button列表
    List<Button> kSanFiveBtnList = new ArrayList<>();// /倒数第二列button列表

    // 快三各个玩法布局

    /**
     * @param type 玩法类型
     */
    private void kuaiSanLayoutView(int type) {
        kSanBtnList.clear();
        kSanLastBtnList.clear();
        kSanFiveBtnList.clear();
        if (type <= 37) {
            kuaiSanView = mInflater.inflate(R.layout.new_kuaisan_differentnum, this, false);
            kSanTishiText = (TextView) kuaiSanView.findViewById(R.id.k3tishitext);
            switch (type) {
                case CaipiaoConst.WF_NOSAME_THREE:
                    kSanTishiText.setText(mContext.getString(R.string.sanbudonghao));
                    qianqutishi.setText("至少选3个号码");
                    break;
                case CaipiaoConst.WF_NOSAME_TWO:
                    qianqutishi.setText("至少选2个号码");
                    kSanTishiText
                            .setText(mContext.getString(R.string.twobudonghao));
                    break;
                case CaipiaoConst.WF_RENYI:
                    qianqutishi.setText("可选1-3个号码");
                    kSanTishiText
                            .setText(mContext.getString(R.string.renyixuanhao));
                    break;
            }
            // 动态布局
            firstContainer = (LinearLayout) kuaiSanView.findViewById(R.id.firstContainer);
            secondContainer = (LinearLayout) kuaiSanView.findViewById(R.id.secondContainer);
            for (int i = 1; i <= 6; i++) {
                tempLp.height = (int) mContext.getResources().getDimension(R.dimen.kuaisanhight);
                final Button tempKsanBtn = getBtnForKuaiSan(R.style.ButtonKS, i + "");
                tempKsanBtn.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((KuaiSanAbsWanFa) getCurrentWanfa()).checkView(tempKsanBtn);
                    }
                });
                autoAddView(i, tempKsanBtn, tempLp);
            }
        }
        // 顺子玩法
        else if (type == CaipiaoConst.WF_SHUNZI) {
            kuaiSanView = mInflater.inflate(R.layout.new_kuaisan_shunzi, this,
                    false);
            shunZiLayout = (LinearLayout) kuaiSanView
                    .findViewById(R.id.shunzilayout);
            shunZiLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((KuaiSanAbsWanFa) getCurrentWanfa()).checkView(shunZiLayout);

                }
            });
            kSanBtnList.add(shunZiLayout);
        } else {
            LayoutParams lp = getTempLayoutParams();
            kuaiSanView = mInflater.inflate(R.layout.new_kuaisan_hezhi, this, false);
            kSanTishiText = (TextView) kuaiSanView.findViewById(R.id.k3tishitext);
            firstContainer = (LinearLayout) kuaiSanView
                    .findViewById(R.id.firstContainer);
            secondContainer = (LinearLayout) kuaiSanView
                    .findViewById(R.id.secondContainer);
            threeContainer = (LinearLayout) kuaiSanView
                    .findViewById(R.id.threeContainer);
            fourContainer = (LinearLayout) kuaiSanView
                    .findViewById(R.id.fourContainer);
            fiveContainer = (LinearLayout) kuaiSanView
                    .findViewById(R.id.fiveContainer);
            sixContainer = (LinearLayout) kuaiSanView
                    .findViewById(R.id.sixContainer);
            kSanTishiTextDown = (TextView) kuaiSanView
                    .findViewById(R.id.kuaisantishitwo);
            // 和值玩法
            if (type == CaipiaoConst.WF_HEZHI) {
                tempLp.height = (int) mContext.getResources().getDimension(
                        R.dimen.kuaisanhezhi);
                kSanTishiText.setText(mContext.getString(R.string.hezhitext));
                kSanTishiTextDown.setText("快速选号");
                for (int i = 3; i < 19; i++) {
                    final View ln = mInflater.inflate(R.layout.new_kuaisan_itemview, null, false);
                    ((TextView) ln.findViewById(R.id.numtext)).setText(i + "");
                    ((TextView) ln.findViewById(R.id.jiangjintext))
                            .setText(CaipiaoConst.kuanSanStrUtils[i - 3]);
                    ln.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((KuaiSanAbsWanFa) getCurrentWanfa()).checkView(ln);


                        }
                    });
//                    // 设置背景图片
//
                    // 选择容器
                    if (i < 7)
                        firstContainer.addView(ln, tempLp);
                    else if (i > 6 && i < 11)
                        secondContainer.addView(ln, tempLp);
                    else if (i > 10 && i < 15)
                        threeContainer.addView(ln, tempLp);
                    else
                        fourContainer.addView(ln, tempLp);
                    kSanBtnList.add(ln);
                }
                // 后区布局(大小奇偶全布局)
                lp.height = (int) mContext.getResources().getDimension(
                        R.dimen.kuaisanjiou);
                for (int i = 0; i < CaipiaoConst.kuanSanHeZhiUtils.length; i++) {
                    final Button tempKsanBtn = getBtnForKuaiSan(
                            R.style.ButtonKS2, CaipiaoConst.kuanSanHeZhiUtils[i]);
                    tempKsanBtn.setTag(CaipiaoConst.kuanSanHeZhiUtils[i]);
                    tempKsanBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ((KuaiSanAbsWanFa) getCurrentWanfa()).checkView(tempKsanBtn);


                        }
                    });

                    fiveContainer.addView(tempKsanBtn, lp);
                    kSanLastBtnList.add(tempKsanBtn);
                }
            }// 对子玩法,//豹子玩法
            else {
                qianqutishi.setText("至少选2个号码");
                if (type == CaipiaoConst.WF_DUIZI) {
                    tempLp.height = (int) mContext.getResources().getDimension(
                            R.dimen.kuaisanduizi);
                    kSanTishiText.setText(mContext
                            .getString(R.string.duizitext1));
                    kSanTishiTextDown.setText(mContext
                            .getString(R.string.duizitext2));
                } else {
                    tempLp.height = (int) mContext.getResources().getDimension(
                            R.dimen.kuaisanhight);
                    lp.height = (int) mContext.getResources().getDimension(
                            R.dimen.kuaisanduizi);
                    kSanTishiText.setText(mContext
                            .getString(R.string.baozitext1));
                    kSanTishiTextDown.setText(mContext
                            .getString(R.string.baozitext2));
                    final Button tBtn = getBtnForKuaiSan(R.style.ButtonKS3, "111 222 333 444 555 666");
                    tBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((KuaiSanAbsWanFa) getCurrentWanfa()).checkView(tBtn);

                        }
                    });
                    fiveContainer.addView(tBtn, lp);
                    kSanLastBtnList.add(tBtn);
                }
                for (int i = 1; i <= 6; i++) {
                    final Button tempKsanBtn;
                    if (type == CaipiaoConst.WF_DUIZI)
                        tempKsanBtn = getBtnForKuaiSan(R.style.ButtonKS, i + ""
                                + i + "*");
                    else
                        tempKsanBtn = getBtnForKuaiSan(R.style.ButtonKS, i + ""
                                + i + "" + i);
                    tempKsanBtn.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((KuaiSanAbsWanFa) getCurrentWanfa()).checkView(tempKsanBtn);
                        }
                    });
                    autoAddView(i, tempKsanBtn, tempLp);
                    if (type == CaipiaoConst.WF_DUIZI) {
                        final Button houQuBtn = getBtnForKuaiSan(
                                R.style.ButtonKS3, i + "" + i);
                        // 添加tag来定位
                        houQuBtn.setTag("f" + i);
                        houQuBtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ((KuaiSanAbsWanFa) getCurrentWanfa()).checkView(houQuBtn);

                            }
                        });

                        fiveContainer.addView(houQuBtn, tempLp);
                        kSanFiveBtnList.add(houQuBtn);
                        final Button lastBtn = getBtnForKuaiSan(
                                R.style.ButtonKS3, i + "");
                        // 添加tag来定位
                        lastBtn.setTag("s" + i);
                        lastBtn.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ((KuaiSanAbsWanFa) getCurrentWanfa())
                                        .checkView(lastBtn);

                            }
                        });

                        sixContainer.addView(lastBtn, tempLp);
                        kSanLastBtnList.add(lastBtn);
                    }
                }
            }
        }
        qianquContainer.addView(kuaiSanView, k3LP);
    }

    // 动态添加布局
    private void autoAddView(int i, Button tempKsanBtn, LayoutParams tempLp) {
        if (i < 4) {
//
            firstContainer.addView(tempKsanBtn, tempLp);
        } else {
//
            secondContainer.addView(tempKsanBtn, tempLp);
        }
        kSanBtnList.add(tempKsanBtn);
    }

    // 创建一个btn来填充快三布局
    private Button getBtnForKuaiSan(int style, String s) {
        Button tempKsanBtn = new Button(mContext);
        tempKsanBtn.setTextAppearance(mContext, style);
        tempKsanBtn.setBackgroundResource(R.drawable.select_half_line);
        tempKsanBtn.setText(s);
        return tempKsanBtn;
    }

    // 创建布局参数
    private LayoutParams getTempLayoutParams() {
        LayoutParams tempLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        tempLp.weight = 1;
        tempLp.width = 0;
        return tempLp;
    }

    /**
     * 获取5期的开奖结果
     */
    private void fillKaijiangjieguo() {
        if (getCurrentCaipiao().getIssue() != null
                && preferences.getString(
                getCurrentCaipiao().getName() + "issue", "").equals(
                getCurrentCaipiao().getIssue())) {
            String kaijiangListStr = preferences.getString(getCurrentCaipiao()
                    .getName() + "kaijianglist", "");
            if (kaijiangListStr.length() > 0) {
                try {

                    responseHandler.onSuccess((kaijiangListStr));
                } catch (Exception e) {
                    e.printStackTrace();
                    handler.postDelayed(requestKaijiangContext, 500);
                }
            } else
                handler.postDelayed(requestKaijiangContext, 500);
        } else {// 请求开奖结果


        }
    }

    private boolean isDown;

    private TextView tv_current, tv_current_num;
    Runnable query_cur_issue = new Runnable() {
        @Override
        public void run() {
            Map<String, String> m = new HashMap<>();
            m.put("lotteryId", CaipiaoUtil.getCpId(getCurrentCaipiao().getId()));
            OkHttpUtil.postSubmitForm(IP.IP + "/lottery/query_cur_issue.htm", m, new OkHttpUtil.OnDownDataListener() {
                @Override
                public void onResponse(String url, String json) {

                    CurIssMode curIssMode = JSONObject.parseObject(json, CurIssMode.class);
                   L.i("test", curIssMode.toString());
                    if (curIssMode.getFlag() == 1) {
                        getCurrentCaipiao().setIssue(curIssMode.getIssue());
                        getCurrentCaipiao().setIssueId(curIssMode.getIssueId());
                        timeDown(curIssMode);

                        tv_current.setText("第" + curIssMode.getUpIssue() + "期:");
                        tv_current_num.setText(curIssMode.getUpDrawNumber());
                    } else {
                        getCurrentCaipiao().setIssue(0 + "");
                        getCurrentCaipiao().setIssueId(0);
                        jiangqitextview.setText(curIssMode.getErrorMessage());

                    }

                }

                @Override
                public void onFailure(String url, String error) {
                }
            });
            handler.removeCallbacks(query_cur_issue);
            handler.postDelayed(query_cur_issue, 10000);

        }

    };


    private void timeDown(final CurIssMode curIssMode) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (curIssMode.getRemainTime().equals("已截至"))
                    curIssMode.setRemainTime(0);
                else
                    curIssMode.setRemainTime(Integer.parseInt(curIssMode.getRemainTime()) - 1000);

                if (curIssMode.getRemainTime().equals("已截至")) {
                    if (!isDown) {
                        isDown = true;
                        L.d(isDown + "");
                        handler.removeCallbacks(query_cur_issue);
                        handler.postDelayed(query_cur_issue, 1000);

                    }
                    jiangqitextview.setText(curIssMode.getIssue() + "-已截至");
                } else {

                    isDown = false;
                    handler.removeCallbacks(this);
                    handler.postDelayed(this, 1000);
                    jiangqitextview.setText(curIssMode.getIssue() + "-" + CaipiaoUtil.formatDuring(Long.parseLong(curIssMode.getRemainTime())));
                }
            }
        });


    }


    Runnable requestKaijiangContext = new Runnable() {
        @Override
        public void run() {
//
            if (!NetUtil.isNetworkAvailable(mContext)) {
//
                handler.removeCallbacks(requestKaijiangContext);
                kaijiangResult[1].setVisibility(View.GONE);
                drawFailed.setVisibility(View.VISIBLE);
                failedTx.setText("获取开奖结果失败,点击重试");
                tryGetBtn.setVisibility(View.VISIBLE);
                return;
            }


            Map<String, String> m = new HashMap<>();
            m.put("firstRow", "0");
            m.put("fetchSize", "5");
            m.put("lotteryId", CaipiaoUtil.getCpId(getCurrentCaipiao().getId()));
            OkHttpUtil.postSubmitForm(IP.IP + "/lottery/issue_notify.htm", m, new OkHttpUtil.OnDownDataListener() {
                @Override
                public void onResponse(String url, String json) {

                    final List<HadMode> hadModes = JSONObject.parseArray(json, HadMode.class);
                    initIssue(hadModes);
                }

                @Override
                public void onFailure(String url, String error) {

                }
            });


        }
    };

    public String getSession() {
        return "";
    }


    public RequestParams getRequestParams() {

        return new RequestParams();
    }

    List<CaipiaoLotteryData> list;
    String firstIssue = "";// /存储开奖号码的第一个奖期

    private HttpRespHandler responseHandler = new HttpRespHandler() {
        @Override
        public void onSuccess(String response) {
            super.onSuccess(response);

            if (JSON.parseObject(response).getIntValue(URLSuffix.flag) != 0) {
                list = new ArrayList<>();
                JSONArray drawNumList = JSON.parseObject(response).getJSONArray("list");
                for (int i = 0; i < drawNumList.size(); i++) {
                    JSONObject json = drawNumList.getJSONObject(i);
                    CaipiaoLotteryData data = new CaipiaoLotteryData(json,
                            getCurrentCaipiao().getId());

                    data.setIssue(getStrjiangqi(data.getIssue()));
                    if (i == 0) {
                        firstIssue = data.getIssue();
                    }
                    list.add(data);
                    jiangqiIsue[i].setText(data.getIssue() + "期:");
                    if (CaipiaoUtil.getSpanFromFuwuqi(data.getDrawNumber()) != null) {
                        if (CaipiaoUtil.isKySj(caipiao.getId())) {
                            kaijiangResult[i].setText(data.getDrawNumber()
                                    .replace(",", "  "));
                        } else
                            kaijiangResult[i].setText(CaipiaoUtil
                                    .getSpanFromFuwuqi(data.getDrawNumber()));
                    } else
                        kaijiangResult[i].setText("");
                }

                handler.removeCallbacks(requestKaijiangContext);
                jiangqiIsue[1].setVisibility(View.VISIBLE);
                kaijiangResult[1].setVisibility(View.VISIBLE);
                drawFailed.setVisibility(View.GONE);
                tryGetBtn.setVisibility(View.GONE);
                // 缓存数据
                if (editor == null)
                    editor = preferences.edit();
                editor.putString(
                        getCurrentCaipiao().getName() + "kaijianglist",
                        response.toString());
                editor.putString(getCurrentCaipiao().getName() + "issue",
                        getCurrentCaipiao().getIssue());
                editor.commit();
                try {
                    // 存在5分钟后新的开奖号码还没有出去，这时就过2分钟再去请求下
                    int length = firstIssue.length();
                    if (length > 0 && (Integer.parseInt(getStrjiangqi(getCurrentCaipiao()
                            .getIssue())) - Integer.parseInt(firstIssue
                            .substring(0, length - 1))) > 1) {
                        handler.postDelayed(requestKaijiangContext,
                                2 * 60 * 1000);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    handler.postDelayed(requestKaijiangContext, 2 * 60 * 1000);
                }
            }
        }

        @Override
        public void onFailure(Throwable error) {
            super.onFailure(error);

        }
    };

    // 获取奖期的数字字符串
    private String getStrjiangqi(String str) {
        if (getCurrentCaipiao().isKuaikai()
                && getCurrentCaipiao().getId() != CaipiaoConst.ID_LAOSHISHICAI) {
            return str.substring(str.length() - 2);
        } else
            return str.substring(str.length() - 3);
    }

    // /修改倒计时和当1奖期的线程
    Runnable changeData = new Runnable() {
        @Override
        public void run() {
            if (getCurrentCaipiao().getRemainTime() < 0) {
                jiangqihaoma.setText("");
                // 5分钟后再请求下一期的开奖公告
                handler.removeCallbacks(requestKaijiangContext);
                handler.postDelayed(requestKaijiangContext, 4 * 60 * 1000);
                // 更新遗漏
                if (preferences.getBoolean("dakaiyilou", true)
                        && CaipiaoUtil.hasYilou(caipiao.getId()))
                    updateYilou();
                handler.post(getJiangJinRunnable);
            } else {
                if (getCurrentCaipiao().getIssue() != null) {
                    if (getCurrentCaipiao().getIssue().equals("当前没有销售奖期")) {
                        jiangqitextview.setText("当前没有销售奖期");
                        jiangqihaoma.setText("");
                    } else {

                    }
                }
            }
            jiangqihaoma.invalidate();
            jiangqitextview.invalidate();
            handler.removeCallbacks(changeData);
            handler.postDelayed(changeData, 1000);
        }
    };

    // 更新遗漏
    private void updateYilou() {
        handler.removeCallbacks(updataYilouRun);
        // 4 * 60 * 1000 + 500
        handler.postDelayed(updataYilouRun, 5 * 60 * 1000 + 500);
    }

    Runnable updataYilouRun = new Runnable() {
        @Override
        public void run() {
            // showToast("更新遗漏");
            // Toast.makeText(mContext,"遗漏", Toast.LENGTH_SHORT).show();
            saveState();
            changeView(getCurrentCaipiao(), false);
            reloadState();
        }
    };

    public void removeRunnable() {
        handler.removeCallbacks(changeData);
        handler.removeCallbacks(requestKaijiangContext);
        handler.removeCallbacks(updataYilouRun);
    }

    public void resetCaipiaoBtns() {
        // 要重新设置一下，否则在彩票大厅中通过xuanhaoView.changeView(getCurrentCaipiao(), true);
        // 当前彩票的ListOfTouzhuButtonList变了。然后点击返回到彩票大厅，当前彩票和当前的ListOfTouzhuButtonList脱节了
        getCurrentCaipiao().setListTouzuList(getListOfTouzhuButtonList());
        getCurrentCaipiao().getCurrentWanfa().setTouzhuListener(this);// 监听也要重新弄
        if (CaipiaoUtil.isKySj(getCurrentCaipiao().getId())) {
            getCurrentCaipiao().setBtnList(kSanBtnList);
            getCurrentCaipiao().setkSanLastBtnList(kSanLastBtnList);
            getCurrentCaipiao().setkSanFiveBtnList(kSanFiveBtnList);
        }
        wanchengxuanhao.setEnabled(true);
    }

    private void daxiaodanshuang() {
        List<String> dxdsList = CaipiaoUtil.getDaxiaodanshuang();
        setPailieTips();
        int colNum = 2;
        LayoutParams LP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        List<TouzhuButton> list;

        for (int i = 0; i < colNum; i++) {
            list = new ArrayList<>();

            int oneGroupColNum = CaipiaoUtil
                    .getColNum(dxdsList, getOneRowMax());

            for (int j = 0; j < oneGroupColNum; j++) {
                qianquContainer.addView(
                        getOneRowBtns(CaipiaoUtil.getTextsByRow(dxdsList, j, getOneRowMax()),
                                getBgStates(true, getOneRowMax()),
                                R.color.qianqubtnselector, redPreviewbg, list,
                                j, i), LP);
            }
            if (i == 0) {
                TextView tv = new TextView(mContext);
                tv.setText("zhanwei");
                tv.setVisibility(View.INVISIBLE);
                qianquContainer.addView(tv);
            }
            addOneGroupBtnList(list);
        }
    }

    private int getBgStates(boolean isQianqu, int oneRowMax) {

        if (isQianqu) {

            return R.drawable.new_qianqu_states;
        } else {

            return R.drawable.new_houqu_states;
        }
    }

    public static final int redPreviewbg = R.drawable.ball_red_big;

    public static final int bluePreviewbg = R.drawable.ball_blue_big;

    private void zu3zu6() {
        setZu3Zu6Tips();
        setOnlyOneGroup();
    }

    private void kuaiShiFenDantuo() {
        int min = ((Dantuo) caipiao.getCurrentWanfa()).getMinDantu();
        int max = ((Dantuo) caipiao.getCurrentWanfa()).getMaxDantu();
        String s = "";
        if (max == min)
            s = "胆码(选" + min + "个)";
        else
            s = "胆码(选" + min + "-" + max + "个)";
        qianqutishi.setText(s);
        setPaileGroup(2);
    }

    private void setZu3Zu6Tips() {
        houquContainer.setVisibility(View.GONE);
        houquContainer.setVisibility(View.GONE);
        qianqu_tip_layout.setVisibility(View.GONE);
        String num = "";
        // qianqutishi.setVisibility(View.GONE);
        if (caipiao.getCurrentWanfa() instanceof Zuxuan) {
            num = ((Zuxuan) caipiao.getCurrentWanfa()).getMinNum() + "";
        } else if (caipiao.getCurrentWanfa() instanceof RenNzhongM) {
            num = ((RenNzhongM) caipiao.getCurrentWanfa()).getM() + "";
        }
        String s = String.format(mContext.getString(R.string.chooseatleast),
                num, mContext.getString(R.string.haoma));

        qianqu_tip_layout.setVisibility(View.VISIBLE);
        if (getCurrentCaipiao().getId() == CaipiaoConst.ID_LUCKYCAR) {
            if (getCurrentWanfa().getType() == CaipiaoConst.WF_QIAN2_ZUXUAN)
                s = mContext.getString(R.string.caizhongbisaiguanjunqian5);
            else if (getCurrentWanfa().getType() == CaipiaoConst.WF_QIAN3_ZUXUAN)
                s = mContext.getString(R.string.caizhongbisaiguanjunqian6);
        }
        qianqutishi.setText(s);
    }

    List<Button> wanfaBtnList = new ArrayList<Button>();

    public boolean diffWanfa() {// 界面选择的玩法，和当前玩法不同,如在兑奖期中改变了玩法 回到购彩大厅时玩法可能会不正确
        int selectedIndex = 0;
        for (int i = 0; i < wanfaBtnList.size(); i++) {
            if (wanfaBtnList.get(i).isSelected()) {
                selectedIndex = i;
                break;
            }
        }
        return selectedIndex != getCurrentCaipiao().getWanfaList().indexOf(
                getCurrentWanfa());
    }


    private void setPaileGroup(int n) {
        LayoutParams LP = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        List<TouzhuButton> list = null;
        if (qianqutishi.getVisibility() == View.GONE) {
            qianquContainer.setPadding(5, 10, 0, 0);
        } else if ((caipiao.getId() == 10064 || caipiao.getId() == 10025
                || caipiao.getId() == 10024 || caipiao.getId() == 10061
                || caipiao.getId() == 10038 || caipiao.getId() == 10067
                || CaipiaoUtil.is11xr5(caipiao.getId()))
                && qianqutishi.getVisibility() == View.VISIBLE) {
            qianquContainer.setPadding(5, 0, 0, 0);
            qianqu_tip_layout.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < n; i++) {
            list = new ArrayList<>();

            int oneGroupColNum = CaipiaoUtil.getColNum(caipiao.getQianquList(),
                    getOneRowMax());

            for (int j = 0; j < oneGroupColNum; j++) {
                qianquContainer.addView(
                        getOneRowBtns(CaipiaoUtil.getTextsByRow(caipiao.getQianquList(), j, getOneRowMax()),
                                getBgStates(true, getOneRowMax()),
                                R.color.qianqubtnselector, redPreviewbg, list,
                                j, i), LP);
            }
            if (i == 0 && this.getCurrentWanfa().getType() >= 102) {
                TextView fenGeXian = new TextView(mContext);
                LayoutParams params = new LayoutParams(
                        LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10;
                fenGeXian.setLayoutParams(params);
                fenGeXian.setTextSize(15);
                fenGeXian.setTextColor(mContext.getResources().getColor(
                        R.color.tip_color));
                int max = ((Dantuo) caipiao.getCurrentWanfa()).getMaxDantu();
                fenGeXian.setText(String.format(
                        mContext.getString(R.string.qianhouqutuomatishi), "",
                        max + 2));
                qianquContainer.addView(fenGeXian, params);
            } else if (i < n - 1 && oneGroupColNum > 1
                    && this.getCurrentWanfa().getType() < 102) { // /添加分隔线
                View fenGeXian = new View(mContext);
                LayoutParams params = new LayoutParams(
                        LayoutParams.MATCH_PARENT, 4);
                params.setMargins(0, 15, 0, 15);
                fenGeXian.setLayoutParams(params);
                fenGeXian.setBackgroundResource(R.drawable.group_buy_list_separator);
                qianquContainer.addView(fenGeXian, params);
            }
            addOneGroupBtnList(list);
        }
    }

    private void qianN_houN(int n) {
        setPailieTips();
        setPaileGroup(n);
    }

    private void pailie() {
        setPailieTips();
        int colNum = caipiao.getQianquMinCount();
        LayoutParams LP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        List<TouzhuButton> list = null;
        qianquContainer.setPadding(5, 0, 0, 0);
        for (int i = 0; i < colNum; i++) {
            list = new ArrayList<>();

            int oneGroupColNum = CaipiaoUtil.getColNum(caipiao.getQianquList(),
                    getOneRowMax());

            for (int j = 0; j < oneGroupColNum; j++) {
                qianquContainer.addView(
                        getOneRowBtns(CaipiaoUtil.getTextsByRow(
                                caipiao.getQianquList(), j, getOneRowMax()),
                                getBgStates(true, getOneRowMax()),
                                R.color.qianqubtnselector, redPreviewbg, list,
                                j, i), LP);
            }
            // /添加分隔线
            if (i < colNum - 1 && oneGroupColNum > 1) {
                View fenGeXian = new View(mContext);
                LayoutParams params = new LayoutParams(
                        LayoutParams.MATCH_PARENT, 4);
                params.setMargins(0, 15, 0, 15);
                fenGeXian.setLayoutParams(params);
                fenGeXian
                        .setBackgroundResource(R.drawable.group_buy_list_separator);
                qianquContainer.addView(fenGeXian, params);
            }
            addOneGroupBtnList(list);
        }
        colNum = caipiao.getHouquMinCount();
        if (colNum > 0) {
            for (int i = 0; i < colNum; i++) {
                list = new ArrayList<TouzhuButton>();

                int oneGroupColNum = CaipiaoUtil.getColNum(
                        caipiao.getHouquList(), getOneRowMax());

                for (int j = 0; j < oneGroupColNum; j++) {
                    qianquContainer.addView(
                            getOneRowBtns(CaipiaoUtil.getTextsByRow(
                                    caipiao.getHouquList(), j, getOneRowMax()),
                                    getBgStates(false, getOneRowMax()),
                                    R.color.houqubtnselector, bluePreviewbg,
                                    list, j, i + caipiao.getQianquMinCount()),
                            LP);
                }
                addOneGroupBtnList(list);
            }
        }
    }

    private void letou() {
        setLetouTips();
        setOnlyOneGroup();
    }

    // 大乐透生肖玩法，可以更一般化
    private void shengxiao() {
        setShengxiaoTips();
        setShengxiao();
    }

    private void setShengxiao() {
        Shengxiao sxwf = (Shengxiao) getCurrentWanfa();
        int colNum = CaipiaoUtil.getColNum(sxwf.getShengxiaoList(),
                getOneRowMax());
        LayoutParams LP = new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        List<TouzhuButton> list = new ArrayList<TouzhuButton>();

        for (int i = 0; i < colNum; i++) {
            qianquContainer
                    .addView(
                            getOneRowBtns(
                                    CaipiaoUtil.getTextsByRow(
                                            sxwf.getShengxiaoList(), i,
                                            getOneRowMax()),
                                    getBgStates(true, getOneRowMax()),
                                    R.color.qianqubtnselector, redPreviewbg,
                                    list, i, 0), LP);
        }
        addOneGroupBtnList(list);
    }

    private void dantuo() {
        setDantuoTips();
        setDantuo();
    }

    private void addOneGroupBtnList(List<TouzhuButton> list) {
        if (list != null && list.size() > 0) {
            listTouzuList.add(list);
        }
    }

    public List<List<TouzhuButton>> getListOfTouzhuButtonList() {
        return listTouzuList;
    }

    /**
     * 仅设置一遍（一组）顺序，如组选，大乐透等，如从1-33设置一遍，如果有后区，后区也设置一遍，不像直选0-9 0-9.。
     */
    private void setOnlyOneGroup() {
        int colNum = CaipiaoUtil.getColNum(caipiao.getQianquList(),
                getOneRowMax());

        LayoutParams LP = new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
        List<TouzhuButton> list = new ArrayList<TouzhuButton>();
        qianquContainer.setPadding(5, 0, 10, 0);
        for (int i = 0; i < colNum; i++) {
            qianquContainer.addView(getOneRowBtns(CaipiaoUtil.getTextsByRow(caipiao.getQianquList(), i, getOneRowMax()), getBgStates(true, getOneRowMax()), R.color.qianqubtnselector, redPreviewbg,
                    list, i, 0), LP);

        }

        addOneGroupBtnList(list);
        if (caipiao.getHouquList() == null
                || caipiao.getHouquList().size() == 0) {
            houquContainer.setVisibility(View.GONE);
        } else {
            houquContainer.setPadding(10, 5, 10, 0);
            list = new ArrayList<>();
            houquContainer.setVisibility(View.VISIBLE);
            houquContainer.removeAllViews();
            houquContainer.setVisibility(View.VISIBLE);
            colNum = CaipiaoUtil.getColNum(caipiao.getHouquList(),
                    getOneRowMax());
            for (int i = 0; i < colNum; i++) {
                houquContainer.addView(
                        getOneRowBtns(CaipiaoUtil.getTextsByRow(
                                caipiao.getHouquList(), i, getOneRowMax()),
                                getBgStates(false, getOneRowMax()),
                                R.color.houqubtnselector, bluePreviewbg, list,
                                0, i), LP);
            }
            // ///添加分隔线
            // View fenGeXian=new View(mContext);
            // LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            // LayoutParams.FILL_PARENT,4);
            // params.setMargins(0, 15, 0, 15);
            // fenGeXian.setLayoutParams(params);
            // fenGeXian.setBackgroundResource(R.drawable.group_buy_list_separator);
            // qianquContainer.addView(fenGeXian,params);
            addOneGroupBtnList(list);
        }
    }

    /**
     * 胆拖。
     */
    @SuppressLint("StringFormatMatches")
    private void setDantuo() {
        int qqColNum = CaipiaoUtil.getColNum(caipiao.getQianquList(),
                getOneRowMax());
        int hqColNum = CaipiaoUtil.getColNum(caipiao.getHouquList(),
                getOneRowMax());
        LayoutParams LP = new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);

        List<TouzhuButton> list = new ArrayList<TouzhuButton>();
        Dantuo wanfa = (Dantuo) getCurrentWanfa();
        // 前区胆码
        View tv;
        String temp = "";
        String str = mContext.getString(R.string.qianqu);
        if (wanfa.getM() == 2) {
            temp = mContext.getString(R.string.danmatishi2);
        } else {
            if (caipiao.getId() == 10032)
                str = "红球";
            temp = String.format(
                    mContext.getString(R.string.qianhouqudanmatishi),
                    (wanfa.getN() == 0 ? "" : str), "1", (wanfa.getM() - 1)
                            + "");
        }
        tv = getDantuoTishiLayout(temp);
        qianquContainer.addView(tv);
        for (int i = 0; i < qqColNum; i++) {
            qianquContainer
                    .addView(
                            getOneRowBtns(
                                    CaipiaoUtil.getTextsByRow(
                                            caipiao.getQianquList(), i,
                                            getOneRowMax()),
                                    getBgStates(true, getOneRowMax()),
                                    R.color.qianqubtnselector, redPreviewbg,
                                    list, i, 0), LP);
        }
        addOneGroupBtnList(list);

        // 前区拖码
        list = new ArrayList<TouzhuButton>();
        tv = getDantuoTishiLayout(String
                .format(mContext.getString(R.string.qianhouqutuomatishi),
                        getCurrentCaipiao().getId() == CaipiaoConst.ID_SHUANGSEQIU ? mContext
                                .getString(R.string.hongqiu)
                                : (wanfa.getN() == 0 ? "" : mContext
                                .getString(R.string.qianqu)),
                        wanfa.getId() == 32 ? caipiao.getId() == 10038 ? 4
                                : (caipiao.getQianquMinCount() + 1) : caipiao
                                .getId() == 10038 ? 3 : caipiao
                                .getQianquMinCount()));
        qianquContainer.addView(tv);
        for (int i = 0; i < qqColNum; i++) {
            qianquContainer
                    .addView(
                            getOneRowBtns(
                                    CaipiaoUtil.getTextsByRow(
                                            caipiao.getQianquList(), i,
                                            getOneRowMax()),
                                    getBgStates(true, getOneRowMax()),
                                    R.color.qianqubtnselector, redPreviewbg,
                                    list, i, 0), LP);
        }
        addOneGroupBtnList(list);
        list = new ArrayList<TouzhuButton>();
        // 后区胆码，如果有的话
        if (wanfa.getN() > 1) {// 大乐透之类，后区需要选2个或2个以上号码，才会又后区胆码区
            // 后区胆码可以为0个
            temp = String.format(
                    mContext.getString(R.string.qianhouqudanmatishi),
                    mContext.getString(R.string.houqu), "0",
                    (getCurrentCaipiao().getHouquMinCount() - 1) + "");

            tv = getDantuoTishiLayout(temp);
            qianquContainer.addView(tv);

            for (int i = 0; i < hqColNum; i++) {
                qianquContainer.addView(
                        getOneRowBtns(CaipiaoUtil.getTextsByRow(
                                caipiao.getHouquList(), i, getOneRowMax()),
                                getBgStates(false, getOneRowMax()),
                                R.color.houqubtnselector, bluePreviewbg, list,
                                0, i), LP);
            }
            addOneGroupBtnList(list);
        }
        if (wanfa.getN() > 0) {
            // 后区拖码，如果是双色球则为蓝色选择区(概念上和拖码是一样的)
            list = new ArrayList<TouzhuButton>();
            if (getCurrentCaipiao().getId() == CaipiaoConst.ID_SHUANGSEQIU)
                tv = getDantuoTishiLayout("蓝球(至少选1个)");
            else
                tv = getDantuoTishiLayout(getCurrentCaipiao().getId() == CaipiaoConst.ID_SHUANGSEQIU ? "蓝球选择区"
                        : String.format(mContext
                                .getString(R.string.qianhouqutuomatishi), "后区",
                        caipiao.getHouquMinCount()));
            qianquContainer.addView(tv);
            for (int i = 0; i < hqColNum; i++) {
                qianquContainer.addView(
                        getOneRowBtns(CaipiaoUtil.getTextsByRow(
                                caipiao.getHouquList(), i, getOneRowMax()),
                                getBgStates(false, getOneRowMax()),
                                R.color.houqubtnselector, bluePreviewbg, list,
                                i, 0), LP);
            }
            addOneGroupBtnList(list);
        }
    }

    private void setPailieTips() {
        houquContainer.setVisibility(View.GONE);
        houquContainer.setVisibility(View.GONE);
        qianqu_tip_layout.setVisibility(View.VISIBLE);
        int type = getCurrentWanfa().getType();
        // /前驱提示隐藏起来
        qianqutishi.setVisibility(View.VISIBLE);
        if (type == CaipiaoConst.WF_DXDS) {
            qianqutishi
                    .setText(mContext.getString(R.string.daxiaodanshuangtip));
        } else if (type == CaipiaoConst.WF_ZU3_SINGLE) {
            qianqutishi.setText(mContext.getString(R.string.zusandanshitip));
        } else if (getCurrentWanfa() instanceof Renxuan) {
            Renxuan rx = (Renxuan) getCurrentWanfa();
            qianqutishi.setText(String.format(
                    mContext.getString(R.string.renxuantip), rx.getN() + ""));
            // 快乐10分前1玩法
        } else if (getCurrentCaipiao().getId() == CaipiaoConst.ID_KUAILE10FEN
                && CaipiaoUtil.isRenOne(type)) {
            qianTishiSpecial.setVisibility(View.VISIBLE);
            qianqutishi.setVisibility(View.VISIBLE);
            qianqutishi.setText(mContext
                    .getString(R.string.zhishaoxuanzeyiweihaoma));
            // 幸运赛车的前一玩法
        } else if (getCurrentCaipiao().getId() == CaipiaoConst.ID_LUCKYCAR) {
            if (CaipiaoUtil.isRenOne(type))
                qianqutishi.setText(mContext
                        .getString(R.string.caizhongbisaiguanjun));
            else if (type == CaipiaoConst.WF_QIAN2)
                qianqutishi.setText(mContext
                        .getString(R.string.caizhongbisaiguanjunqian2));
            else if (type == CaipiaoConst.WF_QIAN3)
                qianqutishi.setText(mContext
                        .getString(R.string.caizhongbisaiguanjunqian3));
            else if (type == CaipiaoConst.WF_YX_ZHIXUAN)
                qianqutishi.setText(mContext
                        .getString(R.string.caizhongbisaiguanjunqian4));
        } else {
            if (getCurrentWanfa().getType() == 13
                    || getCurrentWanfa().getType() == 5)
                qianqutishi.setText("至少选择1个号码");
            else
                qianqutishi.setText(mContext
                        .getString(R.string.meiweizhishaoyige));
        }

    }

    private void setDantuoTips() {
        qianquContainer.setVisibility(View.VISIBLE);
        houquContainer.setVisibility(View.GONE);
        qianqu_tip_layout.setVisibility(View.GONE);
    }

    private void setLetouTips() {
        houquContainer.setVisibility(View.VISIBLE);
        houquContainer.setVisibility(View.VISIBLE);
        qianqu_tip_layout.setVisibility(View.VISIBLE);
        String str1;
        String str2;
        qianqutishi.setVisibility(View.VISIBLE);
        if (caipiao.getHouquMinCount() > 0) {
            if (caipiao.getName().equals(
                    mContext.getString(R.string.shuangseqiu))) {
                str1 = mContext.getString(R.string.hongqiu);
                str2 = mContext.getString(R.string.lanqiu);
            } else {
                str1 = mContext.getString(R.string.qianqu);
                str2 = mContext.getString(R.string.houqu);
            }
            qianqutishi.setText(String.format(mContext.getString(R.string.chooseatleast3), str1, caipiao.getQianquMinCount()));
            hongtoutishitext.setVisibility(View.VISIBLE);
            hongtoutishitext.setText(String.format(mContext.getString(R.string.chooseatleast3), str2, caipiao.getHouquMinCount()));
        } else {
            str1 = mContext.getString(R.string.haoma);
            str2 = mContext.getString(R.string.haoma);
            qianqutishi.setText(String.format(
                    mContext.getString(R.string.chooseatleast),
                    caipiao.getQianquMinCount(), str1));
        }
    }

    private void setShengxiaoTips() {
        houquContainer.setVisibility(View.GONE);
        houquContainer.setVisibility(View.GONE);
        qianqu_tip_layout.setVisibility(View.VISIBLE);

        String s = String.format(mContext.getString(R.string.chooseatleast),
                ((Shengxiao) getCurrentWanfa()).getN(),
                mContext.getString(R.string.shengxiao));

        qianqutishi.setText(s);
    }

    private String xuanHaoStr = "选号";

    private String getDanwei(int indexOfGroup) {

        int type = caipiao.getCurrentWanfa().getType();
        if (caipiao.getId() == CaipiaoConst.ID_KUAILE10FEN
                || CaipiaoUtil.is11xr5(caipiao.getId())) {
            if (type == CaipiaoConst.WF_LIAN2ZHX) {
                return CaipiaoUtil.getDanwei3(indexOfGroup);
            } else if (type == CaipiaoConst.WF_QIAN3ZHIX) {
                return CaipiaoUtil.getDanwei3(2 + indexOfGroup);
            } else if (type > 101) {
                return CaipiaoUtil.getDanwei4(indexOfGroup);
            } else
                return xuanHaoStr;
        }
        String s = null;
        switch (type) {
            case CaipiaoConst.WF_NORMAL_PAILIE:
                if (getCurrentCaipiao().getQianquMinCount() > 5) {
                    s = CaipiaoUtil.getDanwei(indexOfGroup);
                } else {
                    s = CaipiaoUtil.getDanwei2(/*caipiao.getQianquMinCount()
                            -*/ indexOfGroup /*- 1*/);
                }
                if (caipiao.getHouquMinCount() > 0
                        && indexOfGroup == caipiao.getQianquMinCount()) {
                    s = "\t特\t";// 特別号码
                }
                if (caipiao.getId() == CaipiaoConst.ID_TICAI6JIA1
                        && indexOfGroup == 6) {
                    s = "\t特\t";// 特別号码
                }
                break;

            case CaipiaoConst.WF_QIAN1:
            case CaipiaoConst.WF_DANTUO_REN1:
                s = xuanHaoStr;
                // if(caipiao.getId() == CaipiaoConst.ID_LUCKYCAR)
                // s="猜冠军";
                break;
            case CaipiaoConst.WF_REN2_2:
            case CaipiaoConst.WF_REN3_3:
            case CaipiaoConst.WF_QIAN2_ZUXUAN:
            case CaipiaoConst.WF_REN4_4:
            case CaipiaoConst.WF_REN5_5:
            case CaipiaoConst.WF_REN6_5:
            case CaipiaoConst.WF_REN7_5:
            case CaipiaoConst.WF_REN8_5:
            case CaipiaoConst.WF_QIAN3_ZUXUAN:
            case CaipiaoConst.WF_ZU3:
            case CaipiaoConst.WF_ZU6:
            case CaipiaoConst.WF_SX_ZU3:
            case CaipiaoConst.WF_SX_ZU6:
            case CaipiaoConst.WF_DANTUO_ZU3:
            case CaipiaoConst.WF_DANTUO_ZU6:
            case CaipiaoConst.WF_EX_ZUXUAN:
                s = xuanHaoStr;
                break;
            case CaipiaoConst.WF_QIAN2:
            case CaipiaoConst.WF_QIAN3:
                if (caipiao.getId() == CaipiaoConst.ID_LUCKYCAR) {
                    if (indexOfGroup == 0)
                        s = "冠军";
                    else if (indexOfGroup == 1)
                        s = "亚军";
                    else if (indexOfGroup == 2)
                        s = "季军";
                } else {
                    s = CaipiaoUtil.getDanwei2(/*caipiao.getQianquMinCount()
                            -*/ indexOfGroup /*- 1*/);
                }
                break;
            case CaipiaoConst.WF_WX_TONGXUAN:
            case CaipiaoConst.WF_WX_ZHIXUAN:
            case CaipiaoConst.WF_REN1:
            case CaipiaoConst.WF_REN2:
                s = CaipiaoUtil.getDanwei2(/*caipiao.getQianquMinCount()
                        - */indexOfGroup/* - 1*/);
                break;
            case CaipiaoConst.WF_HOU1:
                s = CaipiaoUtil.getDanwei2(0);
                break;
            case CaipiaoConst.WF_HOU2:
            case CaipiaoConst.WF_EX_ZHIXUAN:
            case CaipiaoConst.WF_DXDS:// 大小单双，后2位

            case CaipiaoConst.WF_SX_ZHIXUAN:

            case CaipiaoConst.WF_SIXING_ZHIXUAN:

                s = CaipiaoUtil.getDanwei2(indexOfGroup);

                break;
            case CaipiaoConst.WF_YX_ZHIXUAN:
            case CaipiaoConst.WF_LIAN2ZX:
                s = CaipiaoUtil.getDanwei2(0);
                if (caipiao.getId() == CaipiaoConst.ID_LUCKYCAR)
                    s = "选号";
                break;
            case CaipiaoConst.WF_ZU3_SINGLE:
                s = CaipiaoUtil.getDanwei5(indexOfGroup);
                break;
        }
        return s;
    }

    /**
     * @param texts
     * @param bgs
     * @param colors
     * @param previewBg
     * @param btnList
     * @param indexColOfOneGroup 一组中，第几行索引 从0开始，如全排列时，如果为12个数字，假如分为两排，则分别为0，1
     * @param indexOfGroup       第几组
     * @return
     */
    private LinearLayout getOneRowBtns(List<String> texts, int bgs, int colors,
                                       int previewBg, List<TouzhuButton> btnList, int indexColOfOneGroup,
                                       int indexOfGroup) {

        LinearLayout linearlayout = new LinearLayout(mContext);
        LayoutParams LP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LP.gravity = Gravity.CENTER;
        LP.weight = 1;
        LinearLayout linearlayout1 = new LinearLayout(mContext);
        if (getCurrentCaipiao().getId() == CaipiaoConst.ID_LUCKYCAR
                && (CaipiaoUtil.isRenOne(getCurrentWanfa().getType()) || getCurrentWanfa()
                .getType() == CaipiaoConst.WF_YX_ZHIXUAN)) {
            LayoutParams temp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            temp.gravity = Gravity.CENTER;
            TextView tv = new TextView(mContext);
            tv.setTextColor(danweiColor);
            tv.setText("奖金");
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(13);
            tv.setBackgroundResource(R.drawable.label_bg);
            if (indexColOfOneGroup > 0) {
                tv.setVisibility(View.INVISIBLE);
            }
            linearlayout1.addView(tv, temp);
            linearlayout1.setPadding(1, linearlayout1.getPaddingTop(), 3,
                    linearlayout1.getPaddingBottom());
            TextView[] tvArray = new TextView[texts.size()];
            for (int i = 0; i < texts.size(); i++) {
                tvArray[i] = getYilouTextView();
                tvArray[i].setTextSize(13);
                tvArray[i].setVisibility(View.INVISIBLE);
                tvArray[i].setTextColor(mContext.getResources().getColor(R.color.ywcolor));
                tvList.add(tvArray[i]);
                linearlayout1.addView(tvArray[i], LP);
            }
        }

        linearlayout.setPadding(1, 0, 0, 0);
        String danwei = getDanwei(indexOfGroup);
        TextView danweiTv = null;
        int type = getCurrentWanfa().getType();
        if (danwei != null) {
            LayoutParams temp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            temp.gravity = Gravity.CENTER;
            danweiTv = getTextView(danwei);

            danweiTv.setTextSize(13);
            danweiTv.setBackgroundResource(R.drawable.label_bg);
            if (indexColOfOneGroup > 0) {
                danweiTv.setVisibility(View.INVISIBLE);
            }
            linearlayout.addView(danweiTv, temp);
        }
        for (int i = 0; i < texts.size(); i++) {
            linearlayout.addView(
                    getOneBtn(texts.get(i), bgs, colors, previewBg, btnList),
                    LP);
        }
        yilouShowing = false;
        if (type > 101 && danwei != null && danwei.equals("胆码")) {
            return linearlayout;
        }
        if (CaipiaoUtil.showYilou(mContext, getCurrentCaipiao().getId(),
                getCurrentWanfa())) {
            LinearLayout container = new LinearLayout(mContext);
            container.setOrientation(LinearLayout.VERTICAL);
            LinearLayout linearlayout2 = new LinearLayout(mContext);
            LayoutParams temp = new LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            temp.gravity = Gravity.CENTER;
            if (danwei != null) {
                TextView tv = getYilouTextView();
                tv.setText("遗漏");
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(13);
                tv.setBackgroundResource(R.drawable.label_bg);
                if (indexColOfOneGroup > 0) {
                    tv.setVisibility(View.INVISIBLE);
                }
                linearlayout2.addView(tv, temp);
            }
            linearlayout2.setPadding(1, linearlayout2.getPaddingTop(), 3,
                    linearlayout2.getPaddingBottom());
            if (getCurrentCaipiao().getId() == CaipiaoConst.ID_LUCKYCAR
                    && (getCurrentWanfa().getType() == CaipiaoConst.WF_QIAN1 || getCurrentWanfa()
                    .getType() == CaipiaoConst.WF_YX_ZHIXUAN))
                container.addView(linearlayout1);
            container.addView(linearlayout);
            TextView[] tvArray = new TextView[texts.size()];
            for (int i = 0; i < texts.size(); i++) {
                tvArray[i] = getYilouTextView();
                if (TextUtils.isEmpty(texts.get(i))) {
                    tvArray[i].setVisibility(View.INVISIBLE);// 占位
                }
                linearlayout2.addView(tvArray[i], LP);
            }
            yilouShowing = true;
            CaipiaoUtil.setYilouData(tvArray, getCurrentCaipiao().getId(),
                    getCurrentWanfa().getType(), indexOfGroup, getOneRowMax()
                            * indexColOfOneGroup);
            container.addView(linearlayout2);
            return container;
        }
        return linearlayout;
    }

    private boolean yilouShowing = false;

    public boolean diffYilou() {// 当前遗漏是否显示和配置中不同，
        boolean shouldShowYilou = CaipiaoUtil.showYilou(mContext,
                getCurrentCaipiao().getId(), getCurrentWanfa());
        return shouldShowYilou != yilouShowing;
    }

    private TextView getTextView(String s) {
        TextView tv = new TextView(mContext);
        tv.setText(s);
        tv.setTextColor(danweiColor);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        return tv;
    }

    private TextView getYilouTextView() {
        TextView tv = new TextView(mContext);
        tv.setTextColor(danweiColor);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        return tv;
    }

    private View getDantuoTishiLayout(String s) {
        View view = mInflater.inflate(R.layout.new_dantuotiplayout, null);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        tv.setText(s);
        return view;
    }

    private int LETOU_ONE_ROW_MAX = 7;

    private int PAILIE_ONE_ROW_MAX = 5;

    private int PAILIE_ONE_ROW_MAX2 = 6;

    private int ZUXUAN_ONE_ROW_MAX = 5;

    private int QIANNHOUN_ONE_ROW_MAX = 6;

    private int LETUO_ONE_ROW_MAX = 5;

    private LinearLayout getOneBtn(String text, int bgs, int colors,
                                   int previewBg, List<TouzhuButton> btnList) {

        int btnLayout = colors == R.color.qianqubtnselector ? R.layout.new_touzhubutton
                : R.layout.new_touzhubutton2;
        // colors在代码里设置没用，要在布局里写，
        LinearLayout linearlayout = (LinearLayout) mInflater.inflate(btnLayout, null, false);
        TouzhuButton btn = (TouzhuButton) linearlayout.findViewById(R.id.btn);
        if (text == null) {
            btn.setText(" ");
            btn.setVisibility(View.INVISIBLE);
        } else {
            if (text.length() == 1) {
                text = text;
            }
            btn.setText(text);
            btn.setPreviewBg(previewBg);
        }

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View btn) {
                getCurrentWanfa().check((TouzhuButton) btn);
            }
        });
        btn.setGravity(Gravity.CENTER);
        btn.setSelected(false);

        btn.setBackgroundResource(CaipiaoUtil.getBallBg(bgs, getOneRowMax()));
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        if (text != null) {// text == null时，几个按钮是为了占位
            btnList.add(btn);
        }
        return linearlayout;
    }

    private int getOneRowMax() {
        // 显示遗漏时，一排显示10个。
        int id = getCurrentCaipiao().getId();
        switch (id) {
            case CaipiaoConst.ID_LAO11XUAN5:
            case CaipiaoConst.ID_XIN11XUAN5:
            case CaipiaoConst.ID_11XUAN5:
            case CaipiaoConst.ID_FUJM11XUAN5:
            case CaipiaoConst.ID_QILECAI:
                return QIANNHOUN_ONE_ROW_MAX;
            case CaipiaoConst.ID_TICAI20XUAN5:
            case CaipiaoConst.ID_FUCAI15XUAN5:
            case CaipiaoConst.ID_FUCAI3D:
            case CaipiaoConst.ID_PAILIE3:
                return LETUO_ONE_ROW_MAX;

        }

        int type = getCurrentWanfa().getType();
        if (getCurrentCaipiao().getId() == CaipiaoConst.ID_KUAILE10FEN) {
            if (type == CaipiaoConst.WF_QIAN1)
                return PAILIE_ONE_ROW_MAX2;
            else
                return PAILIE_ONE_ROW_MAX;
        }

        int n = 0;
        switch (type) {

            case CaipiaoConst.WF_NORMAL_PAILIE:
                // if(id==CaipiaoConst.ID_PAILIE3||id==CaipiaoConst.ID_FUCAI3D)
                // n=ZUXUAN_ONE_ROW_MAX;
                // else
                n = PAILIE_ONE_ROW_MAX;
                break;
            case CaipiaoConst.WF_WX_TONGXUAN:
            case CaipiaoConst.WF_WX_ZHIXUAN:
            case CaipiaoConst.WF_SIXING_ZHIXUAN:
            case CaipiaoConst.WF_REN1:
            case CaipiaoConst.WF_REN2:
                n = PAILIE_ONE_ROW_MAX;
                break;
            case CaipiaoConst.WF_QIAN2_ZUXUAN:
            case CaipiaoConst.WF_QIAN3_ZUXUAN:
            case CaipiaoConst.WF_ZU3:
            case CaipiaoConst.WF_ZU6:
            case CaipiaoConst.WF_SX_ZU3:// 三星组3
            case CaipiaoConst.WF_SX_ZU6:// 三星组6
            case CaipiaoConst.WF_EX_ZUXUAN:// 二星组选
            case CaipiaoConst.WF_EX_ZHIXUAN:
            case CaipiaoConst.WF_LIAN2ZX:// 连2组选
            case CaipiaoConst.WF_SX_ZHIXUAN:
                n = ZUXUAN_ONE_ROW_MAX;
                break;
            case CaipiaoConst.WF_YX_ZHIXUAN:
                n = ZUXUAN_ONE_ROW_MAX;
                if (caipiao.getId() == CaipiaoConst.ID_LUCKYCAR)
                    n = PAILIE_ONE_ROW_MAX2;
                break;
            // case CaipiaoConst.WF_HOU1:
            // case CaipiaoConst.WF_HOU2:
            // case CaipiaoConst.WF_QIAN1:
            // case CaipiaoConst.WF_QIAN2:
            // case CaipiaoConst.WF_QIAN3:
            // case CaipiaoConst.WF_LIAN2ZHX:
            // n = QIANNHOUN_ONE_ROW_MAX;
            // break;
            case CaipiaoConst.WF_DANTUO:
            case CaipiaoConst.WF_DALETOUSHENGXIAO:
            case CaipiaoConst.WF_NORMAL_LETOU:
                n = LETOU_ONE_ROW_MAX;
                break;
            default:
                if (getDanwei(0) == null) {
                    n = PAILIE_ONE_ROW_MAX2;
                } else {
                    n = PAILIE_ONE_ROW_MAX2;
                }
                break;
        }
        return n;
    }

    private AbsWanfa getCurrentWanfa() {
        return getCurrentCaipiao().getCurrentWanfa();
    }

    public void onCheck(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            showToast(msg);
        }
    }

    @Override
    public void onSubmit() {
        // getCurrentWanfa().getSubmitString();
    }

    private int touzhuCount;

    @Override
    public void onTouzhuCountChange() {
        if (hasBtnIsSelected()) {
            clearBtn.setVisibility(View.VISIBLE);
            randomBtn.setVisibility(View.INVISIBLE);
            linearLayoutjx.setVisibility(View.INVISIBLE);
        } else {
            if (canRandom()) {
                clearBtn.setVisibility(View.INVISIBLE);
                randomBtn.setVisibility(View.VISIBLE);
            }
        }
        int c = getCurrentWanfa().getTouzhuCount();
        touzhuCount = c;
        if (c > 0) {
            xuanhaolayoutbottom.setVisibility(View.VISIBLE);
            yaodongxuanhao.setVisibility(View.GONE);
            totalZhushuAndMoney[0].setText(c + "");
            totalZhushuAndMoney[1].setText(c * CaipiaoConst.PRICE + "");
            hideJiXuanLayout();
            yaodongxuanhao.setVisibility(View.GONE);
        } else {
            xuanhaolayoutbottom.setVisibility(View.GONE);
            yaodongxuanhao.setVisibility(View.VISIBLE);
            // 当清空按钮隐藏时，那就得显示摇摇手机，否则现在提示信息，至少选择几个球啊
            if (clearBtn.getVisibility() == View.INVISIBLE && canRandom()
                    && preferences.getBoolean("dakaiyaodongxuanhao", true)) {
                yaodongxuanhao.setText(mContext.getResources().getString(
                        R.string.yaoyaoshoujijixuanyizhu));
            } else {

                wanchengxuanhao.setVisibility(VISIBLE);
                if (getCurrentWanfa() instanceof Dantuo)
                    yaodongxuanhao.setText("");
                else if (caipiao.getId() == 10032)
                    yaodongxuanhao.setText("至少选择6个红球和1个蓝球");
                else if (caipiao.getId() == 10026)
                    yaodongxuanhao.setText("至少选择5个前区和2个后区");
                else
                    yaodongxuanhao.setText(qianqutishi.getText());
            }
        }
    }

    private int tempCount = 0;
    private String tempStr = "";

    private String kSanHasSelected(int type) {
        tempStr = "";
        tempCount = 0;
        for (int i = 1; i <= kSanBtnList.size(); i++) {
            if (kSanBtnList.get(i - 1).isSelected()) {
                if (type == CaipiaoConst.WF_DUIZI)
                    tempStr = tempStr + i + "" + i + "*" + " ";
                else if (type == CaipiaoConst.WF_BAOZI)
                    tempStr = tempStr + i + "" + i + "" + i + " ";
                tempCount++;
            }
        }
        return tempStr;
    }

    // /判断选号页面是否存在球被选中
    private boolean hasBtnIsSelected() {
        if (caipiao.getId() == 10065 || getCurrentWanfa().getType() == CaipiaoConst.WF_DAXIAOJQ || caipiao.getId() == 10073 || caipiao.getId() == 10074) {
            for (int i = 0; i < kSanBtnList.size(); i++) {
                if (kSanBtnList.get(i).isSelected())
                    return true;
            }
            for (int i = 0; i < kSanLastBtnList.size(); i++) {
                if (kSanLastBtnList.get(i).isSelected())
                    return true;
            }
            for (int i = 0; i < kSanFiveBtnList.size(); i++) {
                if (kSanFiveBtnList.get(i).isSelected())
                    return true;
            }
        } else {
            List<List<TouzhuButton>> list = caipiao.getListTouzuList();
            for (int i = 0; i < list.size(); i++) {
                if (CaipiaoUtil.getSelectedBtnCount(list.get(i)) > 0)
                    return true;
            }
        }
        return false;
    }

    private void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    private int jixuanNum = 1;

    private void finishChoose(View v) {
        // 非机选
        if (v.getId() == R.id.wanchengxuanhao) {
            if (touzhuCount == 0) {
                if (canRandom() && !hasBtnIsSelected()) {
                    random();
                } else
                    showToast(mContext
                            .getString(R.string.qingzhishaoxuanze1zhu));
                return;
            }
//            wanchengxuanhao.setEnabled(false);
            TouzhuquerenData d = getTouzhuData();
            finishChoseListener.finishiChose(listData, d, mContext);
        }
        // /机选
        else {
            linearLayoutjx.setVisibility(View.GONE);
            finishChoseListener.randomChose(jixuanNum, mContext);
            hideJiXuanLayout();
            // }
        }
    }


    private List<TouzhuquerenData> listData;

    public List<TouzhuquerenData> getListData() {
        return listData;
    }

    List<List<Boolean>> listTemp;

    private TouzhuquerenData getTouzhuDataNew() {
        TouzhuquerenData data = new TouzhuquerenData();
        data.setHasHouqu(getCurrentCaipiao().getHouquMinCount() > 0);
        data.setCaipiaoIdAndWanfaType(caipiao.getId(), getCurrentWanfa()
                .getType());
        data.setName(CaipiaoConst.QIANYIREDTOU);
        data.setZhushu(1);
        return data;
    }

    private int length;

    public TouzhuquerenData getTouzhuData() {

        listData = null;
        TouzhuquerenData data = new TouzhuquerenData();
        data.setHasHouqu(getCurrentCaipiao().getHouquMinCount() > 0);
        data.setCaipiaoIdAndWanfaType(caipiao.getId(), getCurrentWanfa()
                .getType());
        // ///快乐10分胆拖玩法特殊处理下
        data.setName(getCurrentCaipiao().getCurrentWanfaName());
        data.setTouzhuhaoma(getCurrentCaipiao().getTouzhuString());
        data.setZhushu(touzhuCount);
        if (getCurrentWanfa().getType() == CaipiaoConst.WF_DAXIAOJQ)
            data.setDXJOList(kSanBtnList);
        else
            data.setListTouzuList(getListOfTouzhuButtonList());
        if (caipiao.getId() == CaipiaoConst.ID_KUAILE10FEN
                && getCurrentWanfa().getType() == CaipiaoConst.WF_QIAN1) {
            if (isExistHongTou()) {
                // /临时保存下ListTouzuList
                listTemp = data.getListTouzuList();
                TouzhuquerenData dataTemp;
                listData = new ArrayList<TouzhuquerenData>();
                if (btnhtList.get(0).isSelected()) {
                    dataTemp = getTouzhuDataNew();
                    dataTemp.setTouzhuhaoma("19");
                    dataTemp.setListTouzuList2(getListBoolean(18));
                    listData.add(dataTemp);
                    listTemp.get(0).set(18, false);
                }
                if (btnhtList.get(1).isSelected()) {
                    dataTemp = getTouzhuDataNew();
                    dataTemp.setTouzhuhaoma("20");
                    dataTemp.setListTouzuList2(getListBoolean(19));
                    listData.add(dataTemp);
                    listTemp.get(0).set(19, false);
                }
                // 数投的要重新设置数据
                data.setName(CaipiaoConst.QIANYISHUTOU);
                data.setZhushu(touzhuCount - listData.size());
                data.setListTouzuList2(listTemp);
            } else {
                data.setName(CaipiaoConst.QIANYISHUTOU);
            }
        } else if (getCurrentWanfa().getType() == CaipiaoConst.WF_DUIZI
                || getCurrentWanfa().getType() == CaipiaoConst.WF_BAOZI) {
            listData = new ArrayList<>();
            // 先判断前驱是否有选中没
            if (!TextUtils
                    .isEmpty(kSanHasSelected(getCurrentWanfa().getType()))) {
                TouzhuquerenData dataTemp;
                dataTemp = getTouzhuDataNew();
                if (getCurrentWanfa().getType() == CaipiaoConst.WF_DUIZI)
                    dataTemp.setName("二同号复选");
                else
                    dataTemp.setName("三同号单选");
                dataTemp.setZhushu(tempCount);
                length = tempStr.length();
                if (length > 1)
                    dataTemp.setTouzhuhaoma(tempStr.substring(0, length - 1));
                listData.add(dataTemp);
            }
            if (touzhuCount > tempCount) {
                TouzhuquerenData dataTemp;
                dataTemp = getTouzhuDataNew();
                if (getCurrentWanfa().getType() == CaipiaoConst.WF_DUIZI) {
                    dataTemp.setName("二同号单选");
                    tempStr = "";
                    for (int i = 1; i <= caipiao.getkSanFiveBtnList().size(); i++) {
                        if (caipiao.getkSanFiveBtnList().get(i - 1)
                                .isSelected())
                            tempStr = tempStr + i + "" + i + " ";
                    }
                    length = tempStr.length();
                    if (length > 1)
                        tempStr = tempStr.substring(0, length - 1) + "-";
                    for (int i = 1; i <= caipiao.getkSanLastBtnList().size(); i++) {
                        if (caipiao.getkSanLastBtnList().get(i - 1)
                                .isSelected()) {
                            tempStr = tempStr + i + " ";
                        }
                    }
                } else {
                    dataTemp.setName("三同号通选");
                    tempStr = "111,222,333,444,555,666 ";
                }
                length = tempStr.length();
                if (length > 1)
                    dataTemp.setTouzhuhaoma(tempStr.substring(0, length - 1));
                dataTemp.setZhushu(touzhuCount - tempCount);
                listData.add(dataTemp);
            }
            return null;
        }
        return data;
    }

    private List<List<Boolean>> getListBoolean(int i) {
        List<List<Boolean>> list = new ArrayList<List<Boolean>>();
        List<Boolean> oneRow = new ArrayList<Boolean>();
        for (int j = 0; j < 20; j++) {
            if (j == i)
                oneRow.add(true);
            else {
                oneRow.add(false);
            }
        }
        list.add(oneRow);
        return list;
    }

    // /判断是否存在前一红投
    public boolean isExistHongTou() {
        for (int i = 0; i < btnhtList.size(); i++) {
            if (btnhtList.get(i).isSelected()) {
                return true;
            }
        }
        return false;
    }

    public boolean canRandom() {
        if (getCurrentWanfa() instanceof Dantuo) {
            return false;// 胆拖没有随机
        }
        return true;
    }

    private int getSecondRandom(int first) {
        int second = 0;
        second = CaipiaoUtil.getRandomArray(
                caipiao.getkSanLastBtnList().size(), 1)[0];
        if (second == first)
            return getSecondRandom(first);
        else
            return second;
    }

    public boolean random() {
        if (!canRandom()) {
            return false;
        }
        if (CaipiaoUtil.isKySj(caipiao.getId())) {
            setDefaultSelected(null);
            int n = 0, m = 0;
            m = caipiao.getBtnList().size();
            switch (getCurrentWanfa().getType()) {
                case CaipiaoConst.WF_NOSAME_THREE:
                case CaipiaoConst.WF_RENYI:
                    n = 3;
                    break;
                case CaipiaoConst.WF_NOSAME_TWO:
                    n = 2;
                    break;
                case CaipiaoConst.WF_HEZHI:
                case CaipiaoConst.WF_SHUNZI:
                    n = 1;
                    break;
                case CaipiaoConst.WF_BAOZI:
                    n = 1;
                    m = m + 1;
                    break;
                case CaipiaoConst.WF_DUIZI:
                    n = 1;
                    m = 2 * m;
                    break;
            }
            int[] randomArray = CaipiaoUtil.getRandomArray(m, n);
            if (getCurrentWanfa().getType() == CaipiaoConst.WF_BAOZI
                    && randomArray[0] > 5) {
                caipiao.getkSanLastBtnList().get(0).setSelected(true);

            } else if (getCurrentWanfa().getType() == CaipiaoConst.WF_DUIZI
                    && randomArray[0] > 5) {
                int first = CaipiaoUtil.getRandomArray(caipiao
                        .getkSanFiveBtnList().size(), 1)[0];
                caipiao.getkSanFiveBtnList().get(first).setSelected(true);

                int second = getSecondRandom(first);
                caipiao.getkSanLastBtnList().get(second).setSelected(true);

            } else {
                for (int i = 0; i < randomArray.length; i++) {
                    caipiao.getBtnList().get(randomArray[i]).setSelected(true);


                }
            }
            getCurrentWanfa().getTouzhuListener().onTouzhuCountChange();
            return true;
        } else if (caipiao.getId() == CaipiaoConst.ID_LUCKYCAR && getCurrentWanfa().getType() == CaipiaoConst.WF_DAXIAOJQ) {
            setDefaultSelected(null);
            int[] randomArray = CaipiaoUtil.getRandomArray(4, 1);
            caipiao.getBtnList().get(randomArray[0]).setSelected(true);

            getCurrentWanfa().getTouzhuListener().onTouzhuCountChange();
            return true;
        }
        if (getCurrentWanfa() instanceof NormalPailie) {// 一般的排列，以及前N后N等
            int qianquRows = getCurrentCaipiao().getQianquMinCount();
            int number = 1;
            int[] cache = new int[listTouzuList.size()];
            for (int i = 0; i < qianquRows; i++) {
                if (i > listTouzuList.size() - 1) {// 在前N或后N时，有此情况
                    if (((NormalPailie) getCurrentWanfa()).cannotchongfu()) {
                        // 每位不能有重复
                        String[] sArray = new String[cache.length];
                        for (int j = 0; j < cache.length; j++) {
                            sArray[j] = cache[j] + "";
                        }
                        if (CaipiaoUtil.isChongfu(sArray)) {
                            random();
                        }
                    }
                    return true;
                }
                List<TouzhuButton> temp = listTouzuList.get(i);
                int[] randomArray = CaipiaoUtil.getRandomArray(temp.size(),
                        number);
                cache[i] = randomArray[0];
                String checkStr = checkBeforeRandom(true, number);
                if (TextUtils.isEmpty(checkStr)) {
                    setBtnStateByArray(temp, randomArray);
                } else {
                    showToast(checkStr);
                }
            }
            // 最后一位选生肖，比较特殊的排列
            number = 1;
            for (int i = qianquRows; i < listTouzuList.size(); i++) {
                List<TouzhuButton> temp = listTouzuList.get(i);
                int[] randomArray = CaipiaoUtil.getRandomArray(temp.size(),
                        number);
                cache[i] = randomArray[0];
                // 检查下是否有重复情况
                if (((NormalPailie) getCurrentWanfa()).cannotchongfu()) {
                    // 每位不能有重复
                    String[] sArray = new String[i + 1];
                    for (int j = 0; j < i + 1; j++) {
                        sArray[j] = cache[j] + "-";
                    }
                    if (CaipiaoUtil.isChongfu(sArray)) {
                        random();
                        return true;
                    }
                }
                String checkStr = checkBeforeRandom(true, number);
                if (TextUtils.isEmpty(checkStr)) {
                    setBtnStateByArray(temp, randomArray);
                } else {
                    showToast(checkStr);
                }
            }

        } else if (getCurrentWanfa() instanceof Renxuan) {
            int qianquRows = getCurrentCaipiao().getQianquMinCount();

            int number = 1;
            int[] array = CaipiaoUtil.getRandomArray(
                    caipiao.getQianquMinCount(),
                    ((Renxuan) getCurrentWanfa()).getN());
            for (int i = 0; i < qianquRows; i++) {
                boolean isReady = false;
                for (int j = 0; j < array.length; j++) {
                    if (i == array[j]) {
                        isReady = true;
                        break;
                    }
                }
                List<TouzhuButton> temp = listTouzuList.get(i);
                if (isReady) {
                    int[] randomArray = CaipiaoUtil.getRandomArray(temp.size(),
                            number);
                    String checkStr = checkBeforeRandom(true, number);
                    if (TextUtils.isEmpty(checkStr)) {
                        setBtnStateByArray(temp, randomArray);
                    } else {
                        showToast(checkStr);
                    }
                } else {
                    for (TouzhuButton btn : temp) {
                        btn.setSelected(false);

                    }
                    getCurrentWanfa().getTouzhuListener().onTouzhuCountChange();
                }

            }
        } else {// if (getCurrentWanfa().getType() ==
            // CaipiaoConst.WF_NORMAL_LETOU )
            try {
                List<TouzhuButton> temp = listTouzuList.get(0);
                int number = CaipiaoUtil.getRandomNumber(getCurrentCaipiao());

                int[] randomArray = CaipiaoUtil.getRandomArray(temp.size(),
                        number);
                String checkStr = checkBeforeRandom(true, number);
                if (TextUtils.isEmpty(checkStr)) {
                    setBtnStateByArray(temp, randomArray);
                } else {
                    showToast(checkStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (listTouzuList.size() > 1) {
                try {
                    List<TouzhuButton> temp = listTouzuList.get(1);
                    int number = getCurrentCaipiao().getHouquMinCount();
                    int[] randomArray = CaipiaoUtil.getRandomArray(temp.size(),
                            number);
                    String checkStr = checkBeforeRandom(false, number);
                    if (TextUtils.isEmpty(checkStr)) {
                        setBtnStateByArray(temp, randomArray);
                    } else {
                        showToast(checkStr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    public void setDefaultSelected(TouzhuquerenData data) {

        try {
            String[] str;
            if (data == null || data.allNotSelected) {
                for (int i = 0; i < listTouzuList.size(); i++) {
                    for (int j = 0; j < listTouzuList.get(i).size(); j++) {
                        listTouzuList.get(i).get(j).setSelected(false);

                    }
                }
                for (int i = 0; i < kSanBtnList.size(); i++) {
                    kSanBtnList.get(i).setSelected(false);

                }
                for (int i = 0; i < kSanLastBtnList.size(); i++) {
                    kSanLastBtnList.get(i).setSelected(false);

                }
                for (int i = 0; i < kSanFiveBtnList.size(); i++) {
                    kSanFiveBtnList.get(i).setSelected(false);

                }

            } else {
                // 快三特殊处理下
                if (CaipiaoUtil.isKySj(caipiao.getId())) {
                    switch (data.getWfType()) {
                        case CaipiaoConst.WF_NOSAME_THREE:
                        case CaipiaoConst.WF_NOSAME_TWO:
                        case CaipiaoConst.WF_RENYI:
                        case CaipiaoConst.WF_HEZHI:
                            str = data.getTouzhuhaoma().split(" ");
                            for (int i = 0; i < str.length; i++) {
                                if (data.getWfType() == CaipiaoConst.WF_HEZHI) {
                                    kSanBtnList.get(Integer.parseInt(str[i]) - 3).setSelected(true);
                                } else {
                                    kSanBtnList.get(Integer.parseInt(str[i]) - 1).setSelected(true);
                                }
                            }
                            break;
                        case CaipiaoConst.WF_SHUNZI:
                            kSanBtnList.get(0).setSelected(true);


                            break;
                        case CaipiaoConst.WF_DUIZI:
                            if (data.getName().indexOf("二同号复选") != -1) {
                                str = data.getTouzhuhaoma().split(" ");
                                for (int i = 0; i < str.length; i++) {
                                    kSanBtnList.get(Integer.parseInt(str[i].substring(0, 1)) - 1).setSelected(true);

                                }
                            } else if (data.getName().indexOf("二同号单选") != -1) {
                                str = data.getTouzhuhaoma().split("-");
                                String[] tStr = str[0].split("-");
                                for (int i = 0; i < tStr.length; i++) {
                                    kSanFiveBtnList.get(
                                            Integer.parseInt(tStr[i]
                                                    .substring(0, 1)) - 1)
                                            .setSelected(true);

                                }
                                tStr = str[1].split(" ");
                                for (int i = 0; i < tStr.length; i++) {
                                    kSanLastBtnList.get(Integer.parseInt(tStr[i]) - 1).setSelected(true);
                                }
                            }
                            break;
                        case CaipiaoConst.WF_BAOZI:
                            if (data.getName().indexOf("三同号通选") != -1) {
                                kSanLastBtnList.get(0).setSelected(true);

                            } else if (data.getName().indexOf("三同号单选") != -1) {
                                str = data.getTouzhuhaoma().split("-");
                                for (int i = 0; i < str.length; i++) {
                                    kSanBtnList
                                            .get(Integer.parseInt(str[i].substring(
                                                    0, 1)) - 1).setSelected(true);

                                }
                            }
                            break;

                    }
                    if (data.getWfType() == CaipiaoConst.WF_HEZHI)
                        ((KuaiSanAbsWanFa) getCurrentWanfa())
                                .changeHouQuBtnState();
                } else if (caipiao.getId() == CaipiaoConst.ID_LUCKYCAR && getCurrentWanfa().getType() == CaipiaoConst.WF_DAXIAOJQ) {
                    try {
                        str = data.getTouzhuhaoma().split("-");
                        for (int i = 0; i < str.length; i++) {

                            kSanBtnList.get(getIndex(str[i])).setSelected(true);

                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                } else {
                    List<List<Boolean>> list = data.getListTouzuList();
                    if (data.getCaipiaoId() == CaipiaoConst.ID_KUAILE10FEN
                            && data.getWfType() == CaipiaoConst.WF_QIAN1) {
                        if (list.get(0).size() == 18) {
                            list.get(0).add(false);
                            list.get(0).add(false);
                        }
                        if (data.getTouzhuhaoma().equals("19")) {
                            list.get(0).set(18, true);
                        }
                        if (data.getTouzhuhaoma().equals("20")) {
                            list.get(0).set(19, true);
                        }
                    }
                    for (int i = 0; i < listTouzuList.size(); i++) {
                        for (int j = 0; j < listTouzuList.get(i).size(); j++) {
                            listTouzuList.get(i).get(j)
                                    .setSelected(list.get(i).get(j));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        onTouzhuCountChange();
    }

    /**
     * 获取大小奇偶的位置
     */
    private int getIndex(String s) {
        for (int i = 0; i < CaipiaoConst.strArray.length; i++) {
            if (CaipiaoConst.strArray[i].equals(s))
                return i;
        }
        return -1;
    }

    public static TouzhuquerenData currentState;

    public void saveState() {
        currentState = getTouzhuData();
    }

    public void reloadState() {
        setDefaultSelected(currentState);
        currentState = null;
    }

    // 设置为兑奖器状态，需要隐藏一些东西
    public void setDuijiangqiMode() {
        findViewById(R.id.wanchengxuanhao).setVisibility(View.GONE);
        findViewById(R.id.bottomlayout).setVisibility(View.GONE);
    }

    private FinishChoseListener finishChoseListener;

    public FinishChoseListener getFinishChoseListener() {
        return finishChoseListener;
    }

    public void setFinishChoseListener(FinishChoseListener finishChoseListener) {
        this.finishChoseListener = finishChoseListener;
    }

    public interface FinishChoseListener {
        /**
         * @param listData //快乐十分红投专用
         * @param data     //其他彩种的其他玩法专用
         * @param mContext
         */
        void finishiChose(List<TouzhuquerenData> listData,
                          TouzhuquerenData data, Context mContext);

        void randomChose(int num, Context mContext);
    }


    /**
     * 外部设置issue
     *
     * @param hadModes
     */
    public void setIssue(final List<HadMode> hadModes) {
        initIssue(hadModes);
    }


    private void initIssue(final List<HadMode> hadModes) {

        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {

                    issueOne.setText("第" + hadModes.get(0).getIssue() + "期:");
                    contentOne.setText(hadModes.get(0).getDrawNumber());
                    issueOne.setVisibility(GONE);
                    contentOne.setVisibility(GONE);
                    issueTwo.setText("第" + hadModes.get(1).getIssue() + "期:");
                    contentTwo.setText(hadModes.get(1).getDrawNumber());

                    issueThree.setText("第" + hadModes.get(2).getIssue() + "期:");
                    contentThree.setText(hadModes.get(2).getDrawNumber());
                    issueFour.setText("第" + hadModes.get(3).getIssue() + "期:");

                    contentFour.setText(hadModes.get(3).getDrawNumber());
                    issueFive.setText("第" + hadModes.get(4).getIssue() + "期:");


                    contentFive.setText(hadModes.get(4).getDrawNumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void isSelect(boolean flag) {

        if (flag) {
//            iv.setVisibility(VISIBLE);
            iv.setImageResource(R.drawable.new_ksjx_btn_states);
            tvSelct.setText("机选");
            wanchengxuanhao.setVisibility(VISIBLE);

        } else {
            iv.setVisibility(GONE);
            tvSelct.setText("取消");
            wanchengxuanhao.setVisibility(GONE);
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        if (handler != null && query_cur_issue != null)
            handler.removeCallbacks(query_cur_issue);
        super.onDetachedFromWindow();

    }

    public void onpause() {
        if (handler != null && query_cur_issue != null)
            handler.removeCallbacks(query_cur_issue);


    }

    public void onresume() {
        if (handler != null && query_cur_issue != null){
            handler.removeCallbacks(query_cur_issue);
        handler.post(query_cur_issue);}

    }


}