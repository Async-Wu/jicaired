/**
 * Create on 2012-10-11
 */
package com.chengyi.app.jingji.football;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.jingji.guanyajun.CartGuanPopAdapter;
import com.chengyi.app.model.model.AbsJingcaizuqiuData;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.model.model.ShengpingfuData;
import com.chengyi.app.model.wanfa.AbsWanfa;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.L;
import com.chengyi.app.view.scoller.MyExpandableListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class FootBall extends BaseActivity implements OnClickListener,
        OnScrollListener {

    private ImageView ivAnim;
    Button ensureBtn, filterBtn, wanfaBtn;
    TextView sfpBtn, rqSfpBtn, zjqBtn, hunBtn, btn5, btn6, btn7;
    ImageView iv_wanfa, iv_select;
    MyExpandableListView listView;
    private RelativeLayout loaddata, failedLayout, mainlayout, noDataLayout;
    private TextView
            tishiText, yixuantishi, numbisai, bisaitishi, qingKong;
    private FootballAdapterShengpingfu adapterSfp;
    private FootballAdapterShengpingfu adapterRQSfp;
    private FootballAdapterTotalScore adapterZjq;
    private FootBallAdapter_mix_halfwhole_bifen adapterHunHe;
    private FootBallAdapter_mix_halfwhole_bifen adapterBanQuanChang;
    private FootBallAdapter_mix_halfwhole_bifen adapterBifen;

    private MixAdapter mixAdapter;

    private FrameLayout indicatorGroup;
    GridView shaiXuanGrid;
    private LinearLayout wanfalayout;

    private int indicatorGroupId = -1;
    private int indicatorGroupHeight;
    private LayoutInflater mInflater;

    int wfindex = 0;// 玩法类型标记0：胜平负，1：让球胜平负，2：总进球

    List<TextView> wanfaBtnList = new ArrayList<>(); // 玩法按钮列表
    private boolean isReflesh = false;

    List<AbsJingcaizuqiuData> footballList;
    private List<JingcaizuqiuOneGame> footballDetailList = new ArrayList<JingcaizuqiuOneGame>();
    private int callbackId;
    private FootballManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery_football);

        manager = Control.getInstance().getFootballballManager();
        initData();
        initView();
        callbackId = manager.createCallback(ResponseHandler);
        loadData();
    }

    private boolean isFirstTime = true;

    protected void onResume() {
        super.onResume();
        flag = CaipiaoApplication.getInstance().getFootBallType();
        Control.getInstance().getFootballballManager().currentCallback = callbackId;
        if (!isFirstTime) {
            try {
                updateAdapter();
                refreshBottom();
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            isFirstTime = false;
            clearData();
        }
    }

    private void initData() {
        switch (getCurrentCaipiao().getCurrentWanfa().getType()) {
            case CaipiaoConst.WF_ZUCAI_SPF:
                wfindex = 0;
                break;
            case CaipiaoConst.WF_ZUCAI_RQ:
                wfindex = 1;
                break;
            case CaipiaoConst.WF_ZUCAI_ZJQ:
                wfindex = 2;
                break;
            case CaipiaoConst.WF_ZUCAI_HUNHE:
                wfindex = 3;
                break;
            case CaipiaoConst.WF_ZUCAI_BIFEN:
                wfindex = 5;
                break;
            case CaipiaoConst.WF_ZUCAI_BANQUANCHANG:
                wfindex = 4;
                break;
            case CaipiaoConst.MIX:
                wfindex = 6;
                break;
        }
    }

    private void initView() {
        findViewById(R.id.footballffootballtopbarLayout).setOnClickListener(this);
        ivAnim = (ImageView) findViewById(R.id.iv_anim);
        tishiText = (TextView) findViewById(R.id.yixuantishi);

        iv_select = (ImageView) findViewById(R.id.iv_select);
        iv_select.setOnClickListener(this);

        iv_wanfa = (ImageView) findViewById(R.id.iv_wanfa);
        iv_wanfa.setOnClickListener(this);
        setBack();
        loaddata = (RelativeLayout) findViewById(R.id.loaddata);
        failedLayout = (RelativeLayout) findViewById(R.id.failed);
        failedLayout.setOnClickListener(this);

        noDataLayout = (RelativeLayout) findViewById(R.id.nodata);

        wanfalayout = (LinearLayout) findViewById(R.id.footballffootballtopbarLayout);
        wanfalayout.setOnClickListener(this);


        setCusTomeTitle(getCurrentCaipiao().getCurrentWanfa()
                .getName());


        mainlayout = (RelativeLayout) findViewById(R.id.mainlayout);

        // bottom bar start
        qingKong = (TextView) findViewById(R.id.qingkong);
        qingKong.setOnClickListener(this);

        ensureBtn = (Button) findViewById(R.id.ensurebtn);
        ensureBtn.setOnClickListener(this);
        // bottom bar end

        filterBtn = (Button) findViewById(R.id.shuaixuanBtn);
        filterBtn.setOnClickListener(this);
        wanfaBtn = (Button) findViewById(R.id.wanfabtn);
        wanfaBtn.setOnClickListener(this);

        // listview start
        listView = (MyExpandableListView) findViewById(R.id.expandableListView);

//        tishiText = (TextView) findViewById(R.id.xuanzebisaitishi);
        yixuantishi = (TextView) findViewById(R.id.yixuantishi);
        numbisai = (TextView) findViewById(R.id.numbisai);
        bisaitishi = (TextView) findViewById(R.id.bisaitishi);

        listView.setDivider(null);
        listView.setGroupIndicator(null);
        listView.setChildDivider(null);
        listView.setChildIndicator(null);
        listView.setCacheColorHint(0x000000);
        listView.setSelector(R.drawable.translucent_background);
        listView.setOnScrollListener(this);

        indicatorGroup = (FrameLayout) findViewById(R.id.topGroup);
        indicatorGroup.setVisibility(View.INVISIBLE);
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.fragment_lottery_football_listview_groupview, indicatorGroup, true);


        adapterSfp = new FootballAdapterShengpingfu(this,
                manager.footballSelectedArray.get(0), onGameTouchCallback);
        adapterRQSfp = new FootballAdapterShengpingfu(this,
                manager.footballSelectedArray.get(1), onGameTouchCallback);
        adapterZjq = new FootballAdapterTotalScore(this,
                manager.footballSelectedArray.get(2), onGameTouchCallback);

        adapterHunHe = new FootBallAdapter_mix_halfwhole_bifen(this,
                manager.footballSelectedArray.get(3), onGameTouchCallback);

        adapterBanQuanChang = new FootBallAdapter_mix_halfwhole_bifen(this,
                manager.footballSelectedArray.get(4), onGameTouchCallback);
        adapterBifen = new FootBallAdapter_mix_halfwhole_bifen(this,
                manager.footballSelectedArray.get(5), onGameTouchCallback);

        mixAdapter = new MixAdapter(this, manager.footballSelectedArray.get(6), onGameTouchCallback);
        listView.setonRefreshListener(new MyExpandableListView.OnRefreshListener() {
            public void onRefresh() {
                isReflesh = true;
                clearData();
                requestData();

            }
        });

    }

    private int flag = 0;

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.iv_wanfa: // /玩法
                moveToWanFa();
                break;
            case R.id.qingkong: // /清空
                clearData();
                break;
            case R.id.ensurebtn: // /确定
                if (flag == 0 && manager.footballSelectedArray.get(wfindex).size() < 2) {
                    Toast.makeText(FootBall.this, "最少选择2场比赛", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (flag == 1 && manager.footballSelectedArray.get(wfindex).size() < 1) {
                    Toast.makeText(FootBall.this, "最少选择1场比赛", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }


                Intent in = new Intent(FootBall.this, FootballCart.class);
                Bundle extras = new Bundle();

                extras.putInt("flag", flag);
                extras.putInt("wIndex", wfindex);
                in.putExtras(extras);

                // 进入到投注确认页面，但是不是比赛增加情况下，保存本次所有比赛数据
                in.putExtra("ListObject", (Serializable) footballList);
                in.putExtra("saiShiStr", saiShiStr);
                // if (!isFromTouZhuQueRen) {
                startActivity(in);


                break;
            case R.id.failed:
                loaddata.setVisibility(View.VISIBLE);
                failedLayout.setVisibility(View.GONE);
                mainlayout.setVisibility(View.GONE);
                loadData();

                break;
            case R.id.iv_select: // 筛选
                if (footballList == null || footballList.size() == 0)
                    Toast.makeText(this, ("没有比赛可以筛选"), Toast.LENGTH_SHORT).show();
                else
                    getShaiXuanPop().showAsDropDown(findViewById(R.id.footballffootballtopbarLayout));

                break;
            case R.id.footballffootballtopbarLayout:

            case R.id.tv_title: // 切换玩法
                if (wanfaChangePop != null && wanfaChangePop.isShowing()) {
                    dismiss();
                } else {
                    AppUtil.startRotateAnim(ivAnim);

                    getSelectPop().showAsDropDown(findViewById(R.id.footballffootballtopbarLayout));
                }
                break;
            case R.id.wanfa://过关
                vPopupWindow.findViewById(R.id.v_wanfa).setVisibility(View.VISIBLE);
                vPopupWindow.findViewById(R.id.v_dang).setVisibility(View.INVISIBLE);

                ((TextView) vPopupWindow.findViewById(R.id.football_pop_news_btn)).setTextColor(getResources().getColor(R.color.football_grey));
                ((TextView) vPopupWindow.findViewById(R.id.wanfa)).setTextColor(getResources().getColor(R.color.red));


                flag = 0;
                CaipiaoApplication.getInstance().setFootBallType(flag);


                if (currentBtn != null)
                    currentBtn.setSelected(false);
                currentBtn = sfpBtn;

                clearData();
                wfindex = wanfaBtnList.indexOf(sfpBtn);
                currentBtn.setSelected(true);
//
                AbsWanfa clickWf = getCurrentCaipiao().getWanfaList().get(wfindex);

                Control.getInstance().getFootballballManager().setCurrentWanfa(wfindex);
                getCurrentCaipiao().setCurrentWanfa(clickWf);
                setCusTomeTitle(clickWf.getName());
//                if (currentBtn != null)
//                    currentBtn.setSelected(false);
//
                isReflesh = false;
                requestData();


                break;

            case R.id.football_pop_news_btn:  //单关


                vPopupWindow.findViewById(R.id.v_wanfa).setVisibility(View.INVISIBLE);
                vPopupWindow.findViewById(R.id.v_dang).setVisibility(View.VISIBLE);
                ((TextView) vPopupWindow.findViewById(R.id.wanfa)).setTextColor(getResources().getColor(R.color.football_grey));
                ((TextView) vPopupWindow.findViewById(R.id.football_pop_news_btn)).setTextColor(getResources().getColor(R.color.red));
                flag = 1;
                CaipiaoApplication.getInstance().setFootBallType(flag);
                tishiText.setText("请至少选择1场比赛");
                if (currentBtn != null)
                    currentBtn.setSelected(false);
                currentBtn = sfpBtn;

                clearData();
                wfindex = wanfaBtnList.indexOf(sfpBtn);
                currentBtn.setSelected(true);
//
                clickWf = getCurrentCaipiao().getWanfaList().get(wfindex);

                Control.getInstance().getFootballballManager().setCurrentWanfa(wfindex);
                getCurrentCaipiao().setCurrentWanfa(clickWf);
                setCusTomeTitle(clickWf.getName());
//                if (currentBtn != null)
//                    currentBtn.setSelected(false);
//
                isReflesh = false;
                requestData();


                break;


            case R.id.buttonspf: // 胜平负
            case R.id.buttonrqspf: // 让球胜平负
            case R.id.buttonzjq: // 总进球
            case R.id.btnhunhe:// 混合投注
            case R.id.btn5:
            case R.id.btn6:
            case R.id.btn7:
                clearData();
                wfindex = wanfaBtnList.indexOf(v);
                dismiss();
//                AbsWanfa wf = getCurrentCaipiao().getCurrentWanfa();
                clickWf = getCurrentCaipiao().getWanfaList().get(wfindex);

                Control.getInstance().getFootballballManager().setCurrentWanfa(wfindex);
                getCurrentCaipiao().setCurrentWanfa(clickWf);
                setCusTomeTitle(clickWf.getName());
                if (currentBtn != null)
                    currentBtn.setSelected(false);
                v.setSelected(true);
                currentBtn = (TextView) v;
                isReflesh = false;
                requestData();
                break;

            case R.id.imgbg:
                if (shaiXuanPop != null && shaiXuanPop.isShowing())
                    shaiXuanPop.dismiss();
                break;
            case R.id.yesbtn: // 筛选布局确定
                if (shaiXuanPop != null && shaiXuanPop.isShowing())
                    shaiXuanPop.dismiss();
                // 首先清空已选择的比赛
                clearData();
                isFirstShow = false;
                flagBak = qujianFlag;
                selectedListBak.clear();
                for (int i = 0; i < selectedList.size(); i++)
                    selectedListBak.add(selectedList.get(i));
                totalBak = totalTemp;
                if (listTemp == null)
                    return;
                this.footballList = listTemp;
                setListToAdapter();
                if (listTemp.size() > 0)
                    listView.expandGroup(0);

                break;
            case R.id.cancel: // /筛选布局取消
                if (shaiXuanPop != null && shaiXuanPop.isShowing())
                    shaiXuanPop.dismiss();

                break;
            case R.id.buttonall: // 筛选 全选
                resetSelect(allBtn);
                for (int i = 0; i < saiShiList.size(); i++) {
                    selectedList.add(saiShiList.get(i));
                }
                adapter.notifyDataSetChanged();
                getListTempData();

                break;
            case R.id.buttonunall: // 筛选反选
                resetSelect(unAllBtn);
                for (int i = 0; i < saiShiList.size(); i++) {
                    if (selectedList.contains(saiShiList.get(i)))
                        selectedList.remove(saiShiList.get(i));
                    else
                        selectedList.add(saiShiList.get(i));
                }
                adapter.notifyDataSetChanged();
                getListTempData();

                break;
            case R.id.buttonclear: // 筛选清空
                resetSelect(clearBtn);
                selectedList.clear();
                adapter.notifyDataSetChanged();
                getListTempData();

                break;
            case R.id.buttonfive: // 筛选5大联赛
                resetSelect(fiveBtn);

                selectedList.clear();
                selectedList.add("意甲");
                selectedList.add("英超");
                selectedList.add("西甲");
                selectedList.add("德甲");
                selectedList.add("法甲");

                adapter.notifyDataSetChanged();

                getListTempData();

                break;


            case R.id.text2:
            case R.id.btn1:
                if (btn1.isSelected())
                    return;
                peiLvList[qujianFlag].setSelected(false);
                qujianFlag = 0;
                btn1.setSelected(true);
                getListTempData();

                break;

            case R.id.text4:
            case R.id.btn2:
                if (btn2.isSelected())
                    return;
                peiLvList[qujianFlag].setSelected(false);
                qujianFlag = 1;
                btn2.setSelected(true);
                getListTempData();

                break;

            case R.id.text6:
            case R.id.btn3:
                if (btn3.isSelected())
                    return;
                peiLvList[qujianFlag].setSelected(false);
                qujianFlag = 2;
                btn3.setSelected(true);
                getListTempData();

                break;
            case R.id.text7:

            case R.id.btn4:
                if (btn4.isSelected())
                    return;
                peiLvList[qujianFlag].setSelected(false);
                qujianFlag = 3;
                btn4.setSelected(true);
                getListTempData();

                break;
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++请求网络数据 start

    private void loadData() {


        requestData();

    }


    private void resetSelect(Button btn) {

        allBtn.setBackgroundResource(R.color.white);
        unAllBtn.setBackgroundResource(R.color.white);
        clearBtn.setBackgroundResource(R.color.white);
        fiveBtn.setBackgroundResource(R.color.white);
        allBtn.setBackgroundResource(R.color.white);
        allBtn.setTextColor(getResources().getColor(R.color.grey_txt));
        unAllBtn.setTextColor(getResources().getColor(R.color.grey_txt));
        clearBtn.setTextColor(getResources().getColor(R.color.grey_txt));
        fiveBtn.setTextColor(getResources().getColor(R.color.grey_txt));
        btn.setBackgroundResource(R.drawable.select_bg);
        btn.setTextColor(getResources().getColor(R.color.red));

    }


    protected void requestData() {
        noDataLayout.setVisibility(View.GONE);
        flagBak = 0;
        isFirstShow = true;
        selectedListBak.clear();
        if (saiShiStr != null) {
            for (int i = 0; i < saiShiStr.length; i++) {
                selectedListBak.add(saiShiStr[i]);
            }
            totalBak = totalSize;
        }

        getDataFromServer();

    }

    private void getDataFromServer() {
        if (!isReflesh)
            loaddata.setVisibility(View.VISIBLE);
        Control.getInstance().initial(this);
        Control.getInstance().getFootballballManager().requestLotteryData(wfindex, callbackId);
    }

    FootballManager.OnFootballDataCallback ResponseHandler = new FootballManager.OnFootballDataCallback() {
        @Override
        public void onFailure(Throwable error) {

            if (isReflesh) {
                Toast.makeText(FootBall.this, ("数据刷新失败!"), Toast.LENGTH_SHORT).show();
                listView.onRefreshComplete();
            } else {
                failedLayout.setVisibility(View.VISIBLE);
                loaddata.setVisibility(View.GONE);
                mainlayout.setVisibility(View.GONE);
            }

            if (error instanceof NullPointerException) {
                failedLayout.setVisibility(View.GONE);
            }
        }

        @Override
        public void onSuccess(ArrayList<AbsJingcaizuqiuData> footballList,
                              String[] saiShiStr) {
            loaddata.setVisibility(View.GONE);
            jieXiJsonData(footballList, saiShiStr);
            refreshBottom();
        }
    };

    // 对json进行解析
    String[] saiShiStr;

    private void jieXiJsonData(ArrayList<AbsJingcaizuqiuData> footballList,
                               String[] saiShiStr) {

        this.footballList = footballList;
        setListToAdapter();
        totalSize = 0;
        // 默认都展开，同时计算有多少场比赛
        for (int i = 0; i < footballList.size(); i++) {
            totalSize += footballList.get(i).getCount();
            listView.expandGroup(i);
        }
        totalBak = totalSize;
        // 如果是下拉刷新数据
        if (isReflesh) {
            listView.onRefreshComplete();
        }

        this.saiShiStr = saiShiStr;
        saiShiList.clear();
        selectedListBak.clear();
        for (int i = 0; i < saiShiStr.length; i++) {
            saiShiList.add(saiShiStr[i]);
            selectedListBak.add(saiShiStr[i]);
        }


    }

    // ================================================请求网络数据 end

    OnGameTouchCallback onGameTouchCallback = new OnGameTouchCallback() {

        @Override
        public void onGameTouched(boolean isTooMuchTeam) {

            if (isTooMuchTeam) {
                Toast.makeText(FootBall.this, ("最多选择15场比赛"), Toast.LENGTH_SHORT).show();

            } else {
                refreshBottom();
            }
        }

    };

    private void refreshBottom() {


        int size = manager.footballSelectedArray.get(wfindex).size();
        if (size == 0) {
            if (flag == 0)
                size = 2;
            else
                size = 1;
            yixuantishi.setText("请至少选择");

        } else {
            yixuantishi.setText("已选择");
        }
        numbisai.setText(String.valueOf(size));
        yixuantishi.setVisibility(View.VISIBLE);
        numbisai.setVisibility(View.VISIBLE);
        bisaitishi.setVisibility(View.VISIBLE);
        qingKong.setText("清空");

    }

    interface OnGameTouchCallback {
        void onGameTouched(boolean isTooMuchTeam);
    }

    // 点击清空按钮后将数据源数据重置，主要是重置isSelected[]
    public void clearData() {
        if (manager.footballSelectedArray.size() == 0) {
            return;
        }
        numbisai.setText("0");
        resetAllDataAndClearInArray();
        if (listTemp != null) {
            for (int i = 0; i < listTemp.size(); i++) {
                List<JingcaizuqiuOneGame> gl = listTemp.get(i).getGames();
                for (int j = 0; j < gl.size(); j++) {
                    gl.get(j).setSpfFlag(-1);
                    gl.get(j).reSetIsSelected();
                }
            }
        }
        int size = 0;
        switch (wfindex) {
            case 0:


                adapterSfp.notifyDataSetChanged();
                listView.setAdapter(adapterSfp);
                size = adapterSfp.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
            case 1:

                adapterRQSfp.notifyDataSetChanged();
                listView.setAdapter(adapterRQSfp);
                size = adapterRQSfp.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;

            case 2:

                adapterZjq.notifyDataSetChanged();
                listView.setAdapter(adapterZjq);
                size = adapterZjq.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
            case 3:
                adapterHunHe.setType(flag);
                adapterHunHe.notifyDataSetChanged();
                listView.setAdapter(adapterHunHe);
                size = adapterHunHe.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
            case 4:

                adapterBanQuanChang.notifyDataSetChanged();
                listView.setAdapter(adapterBanQuanChang);
                size = adapterBanQuanChang.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
            case 5:

                adapterBifen.notifyDataSetChanged();
                listView.setAdapter(adapterBifen);
                size = adapterBifen.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
            case 6:


                mixAdapter.notifyDataSetChanged();
                listView.setAdapter(mixAdapter);
                size = mixAdapter.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
        }

        refreshBottom();
    }

    // =====================================重置数据
    public void resetAllDataAndClearInArray() {
        SparseArray<JingcaizuqiuOneGame> array = manager.footballSelectedArray
                .get(wfindex);
        int size = array.size();
        for (int i = 0; i < size; i++) {
            JingcaizuqiuOneGame game = array.valueAt(i);
            game.reSetIsSelected();
            game.setSpfFlag(-1);
            // 半全场和比分需要设置的
            if (wfindex == 4 || wfindex == 5) {
                game.setSelectedStr("");
                game.setSelContentStr("");
            }
            game.reset();
        }
        array.clear();

    }

    private void setListToAdapter() {

        switch (wfindex) {
            case 0:
                footballList = resetList(footballList, flag, wfindex);
                adapterSfp.setWfIndex(wfindex);
                adapterSfp.setList(footballList);
                adapterSfp.notifyDataSetChanged();
                listView.setAdapter(adapterSfp);
                break;
            case 1:
                footballList = resetList(footballList, flag, wfindex);
                adapterRQSfp.setWfIndex(wfindex);
                adapterRQSfp.setList(footballList);
                listView.setAdapter(adapterRQSfp);
                break;

            case 2:
                footballList = resetList(footballList, flag, wfindex);
                adapterZjq.setList(footballList);
                listView.setAdapter(adapterZjq);
                break;
            case 3:
                footballList = resetList(footballList, flag, wfindex);
                adapterHunHe.setWfindex(wfindex);
                adapterHunHe.setList(footballList);
                adapterHunHe.setType(flag);
                listView.setAdapter(adapterHunHe);
                break;
            case 4:
                footballList = resetList(footballList, flag, wfindex);
                adapterBanQuanChang.setWfindex(wfindex);
                adapterBanQuanChang.setList(footballList);
                listView.setAdapter(adapterBanQuanChang);
                break;
            case 5:
                footballList = resetList(footballList, flag, wfindex);
                adapterBifen.setWfindex(wfindex);
                adapterBifen.setList(footballList);
                listView.setAdapter(adapterBifen);
                break;
            case 6:
                footballList = resetList(footballList, flag, wfindex);
                mixAdapter.setWfindex(wfindex);
                mixAdapter.setList(footballList);
                listView.setAdapter(mixAdapter);
                break;

        }

        if (footballList.size() == 0) {
            noDataLayout.setVisibility(View.VISIBLE);
        } else {
            noDataLayout.setVisibility(View.GONE);
        }
        failedLayout.setVisibility(View.GONE);

        mainlayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        try {
            listView.setFirstItemIndex(firstVisibleItem);
            final ExpandableListView listview = (ExpandableListView) view;
            int npos = view.pointToPosition(0, 0);// 其实就是firstVisibleItem
            if (npos == AdapterView.INVALID_POSITION)// 如果第一个位置值无效
                return;

            long pos = listview.getExpandableListPosition(npos);
            int childPos = ExpandableListView.getPackedPositionChild(pos);// 获取第一行child的id
            int groupPos = ExpandableListView.getPackedPositionGroup(pos);// 获取第一行group的id

            if (childPos == AdapterView.INVALID_POSITION
                    && firstVisibleItem == 0) {// 第一行不是显示child,就是group,此时没必要显示指示器
                indicatorGroup.setVisibility(View.GONE);// 隐藏指示器
            } else {
                indicatorGroup.setVisibility(View.VISIBLE);// 滚动到第一行是child，就显示指示器
                indicatorGroupHeight = indicatorGroup.getMeasuredHeight();// 获取group的高度
            }
            // update the data of indicator group view
            // if (groupPos != indicatorGroupId&&firstVisibleItem!=0) {//
            // 如果指示器显示的不是当前group
            if (firstVisibleItem != 0) {// 如果指示器显示的不是当前group

                switch (wfindex) {
                    case 0:
                        adapterSfp.getGroupView(groupPos,
                                listview.isGroupExpanded(groupPos),
                                indicatorGroup.getChildAt(0), null);
                        break;
                    case 1:
                        adapterRQSfp.getGroupView(groupPos,
                                listview.isGroupExpanded(groupPos),
                                indicatorGroup.getChildAt(0), null);
                        break;

                    case 2:
                        adapterZjq.getGroupView(groupPos,
                                listview.isGroupExpanded(groupPos),
                                indicatorGroup.getChildAt(0), null);
                        break;
                    case 3:
                        adapterHunHe.setType(flag);
                        adapterHunHe.getGroupView(groupPos,
                                listview.isGroupExpanded(groupPos),
                                indicatorGroup.getChildAt(0), null);
                        break;
                    case 4:
                        adapterBanQuanChang.getGroupView(groupPos,
                                listview.isGroupExpanded(groupPos),
                                indicatorGroup.getChildAt(0), null);
                        break;
                    case 5:
                        adapterBifen.getGroupView(groupPos,
                                listview.isGroupExpanded(groupPos),
                                indicatorGroup.getChildAt(0), null);
                        break;
                    case 6:
                        mixAdapter.getGroupView(groupPos,
                                listview.isGroupExpanded(groupPos),
                                indicatorGroup.getChildAt(0), null);
                        break;
                }

                indicatorGroupId = groupPos;
                indicatorGroup.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {

                        listview.collapseGroup(indicatorGroupId);
                    }
                });
            }
            if (indicatorGroupId == -1) // 如果此时grop的id无效，则返回
                return;
            int showHeight = indicatorGroupHeight;
            int nEndPos = listview.pointToPosition(0, indicatorGroupHeight);// 第二个item的位置
            if (nEndPos == AdapterView.INVALID_POSITION)// 如果无效直接返回
                return;
            long pos2 = listview.getExpandableListPosition(nEndPos);
            int groupPos2 = ExpandableListView.getPackedPositionGroup(pos2);// 获取第二个group的id

            if (groupPos2 != indicatorGroupId) {// 如果不等于指示器当前的group
                View viewNext = listview.getChildAt(nEndPos
                        - listview.getFirstVisiblePosition());
                showHeight = viewNext.getTop();
            }
            // update group position
            MarginLayoutParams layoutParams = (MarginLayoutParams) indicatorGroup
                    .getLayoutParams();
            layoutParams.topMargin = -(indicatorGroupHeight - showHeight);
            indicatorGroup.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {


    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++顶部彩种切换 start
    View vPopupWindow, sPopupWindow;
    PopupWindow wanfaChangePop, shaiXuanPop; // 区域选择性弹窗
    LinearLayout top;

    TextView currentBtn;

    private PopupWindow getSelectPop() {
        if (vPopupWindow == null) {
            wanfaBtnList.clear();
            vPopupWindow = getLayoutInflater().inflate(R.layout.pop_jincaiwanfa, null, true);
            wanfaChangePop = new PopupWindow(vPopupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            wanfaChangePop.setBackgroundDrawable(new BitmapDrawable());
            wanfaChangePop.setFocusable(true);
            wanfaChangePop.setOutsideTouchable(true);

            vPopupWindow.findViewById(R.id.football_pop_news_btn)
                    .setOnClickListener(this);

            sfpBtn = (TextView) vPopupWindow.findViewById(R.id.buttonspf);
            vPopupWindow.findViewById(R.id.wanfa).setOnClickListener(this);
            sfpBtn.setOnClickListener(this);
            wanfaBtnList.add(sfpBtn);
            rqSfpBtn = (TextView) vPopupWindow.findViewById(R.id.buttonrqspf);
            rqSfpBtn.setOnClickListener(this);
            wanfaBtnList.add(rqSfpBtn);
            zjqBtn = (TextView) vPopupWindow.findViewById(R.id.buttonzjq);
            zjqBtn.setOnClickListener(this);
            wanfaBtnList.add(zjqBtn);
            hunBtn = (TextView) vPopupWindow.findViewById(R.id.btnhunhe);
            hunBtn.setOnClickListener(this);
            wanfaBtnList.add(hunBtn);
            btn5 = (TextView) vPopupWindow.findViewById(R.id.btn5);
            btn5.setOnClickListener(this);
            wanfaBtnList.add(btn5);
            btn6 = (TextView) vPopupWindow.findViewById(R.id.btn6);

            btn6.setOnClickListener(this);
            btn7 = (TextView) vPopupWindow.findViewById(R.id.btn7);

            btn7.setOnClickListener(this);
            wanfaBtnList.add(btn6);
            wanfaBtnList.add(btn7);
            if (wfindex == 0) {
                sfpBtn.setSelected(true);
                currentBtn = sfpBtn;
            } else if (wfindex == 1) {
                rqSfpBtn.setSelected(true);
                currentBtn = rqSfpBtn;
            } else if (wfindex == 2) {
                zjqBtn.setSelected(true);
                currentBtn = zjqBtn;
            } else if (wfindex == 3) {
                hunBtn.setSelected(true);
                currentBtn = hunBtn;
            } else if (wfindex == 4) {
                btn5.setSelected(true);
                currentBtn = btn5;
            } else if (wfindex == 5) {
                btn6.setSelected(true);
                currentBtn = btn6;
            } else if (wfindex == 7) {
                btn6.setSelected(true);
                currentBtn = btn7;
            }

            wanfaChangePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    dismiss();
                }
            });
            vPopupWindow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wanfaChangePop != null && wanfaChangePop.isShowing()) {
                        dismiss();
                    }
                }
            });
        } else {

            if (currentBtn != null)
                currentBtn.setSelected(true);
        }
        return wanfaChangePop;
    }

    private void dismiss() {
        AppUtil.startResRotateAnim(ivAnim);
        wanfaChangePop.dismiss();


    }

    // =====================================================顶部彩种切换 end

    CartGuanPopAdapter adapter;
    ArrayList<String> saiShiList = new ArrayList<String>();
    ArrayList<String> selectedList = new ArrayList<String>();
    ArrayList<String> selectedListBak = new ArrayList<String>();

    Button yesBtn, cancelBtn, allBtn, unAllBtn, clearBtn, fiveBtn;
    int qujianFlag = 0;// 赔率区间0：全部，1:1.5以下，2:1.5-2区间，3:2.0以上
    Button btn1, btn2, btn3, btn4;
    Button[] peiLvList = new Button[4]; // 赔率列表
    int totalSize = 0;// 共有多少场比赛
    int flagBak = 0, totalBak = 0; // /备份数据
    TextView totalnum;
    boolean isFirstShow = true; // 筛选布局是否是第一次展示出来
    RelativeLayout peiLvLayout;
    TextView peiLvText;

    /**
     * 筛选弹框
     *
     * @return
     */
    ImageView imgbg;

    private PopupWindow getShaiXuanPop() {
        if (sPopupWindow == null) {
            sPopupWindow = getLayoutInflater().inflate(
                    R.layout.fragment_lottery_football_filter, null, false);
            shaiXuanPop = new PopupWindow(sPopupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

            shaiXuanPop.setFocusable(true);
            shaiXuanPop.setOutsideTouchable(true);
            try {
                shaiXuanPop.setAnimationStyle(R.style.PopWindowAnimation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            imgbg = (ImageView) sPopupWindow.findViewById(R.id.imgbg);
            shaiXuanGrid = (GridView) sPopupWindow.findViewById(R.id.gridView1);
            adapter = new CartGuanPopAdapter(this, false, saiShiList,
                    selectedList);
            yesBtn = (Button) sPopupWindow.findViewById(R.id.yesbtn);
            yesBtn.setOnClickListener(this);
            imgbg.setOnClickListener(this);
            cancelBtn = (Button) sPopupWindow.findViewById(R.id.cancel);
            cancelBtn.setOnClickListener(this);
            allBtn = (Button) sPopupWindow.findViewById(R.id.buttonall);
            allBtn.setOnClickListener(this);
            unAllBtn = (Button) sPopupWindow.findViewById(R.id.buttonunall);
            unAllBtn.setOnClickListener(this);
            clearBtn = (Button) sPopupWindow.findViewById(R.id.buttonclear);
            clearBtn.setOnClickListener(this);
            fiveBtn = (Button) sPopupWindow.findViewById(R.id.buttonfive);
            fiveBtn.setOnClickListener(this);
            // 赔率区间

            sPopupWindow.findViewById(R.id.btn1).setOnClickListener(this);
            sPopupWindow.findViewById(R.id.text2).setOnClickListener(this);

            sPopupWindow.findViewById(R.id.btn2).setOnClickListener(this);
            sPopupWindow.findViewById(R.id.text4).setOnClickListener(this);

            sPopupWindow.findViewById(R.id.btn3).setOnClickListener(this);
            sPopupWindow.findViewById(R.id.text6).setOnClickListener(this);
            sPopupWindow.findViewById(R.id.text7).setOnClickListener(this);
            sPopupWindow.findViewById(R.id.btn4).setOnClickListener(this);

            btn1 = (Button) sPopupWindow.findViewById(R.id.btn1);
            peiLvList[0] = btn1;
            btn2 = (Button) sPopupWindow.findViewById(R.id.btn2);
            peiLvList[1] = btn2;
            btn3 = (Button) sPopupWindow.findViewById(R.id.btn3);
            peiLvList[2] = btn3;
            btn4 = (Button) sPopupWindow.findViewById(R.id.btn4);
            peiLvList[3] = btn4;
            totalnum = (TextView) sPopupWindow.findViewById(R.id.totalnum);

            peiLvText = (TextView) sPopupWindow.findViewById(R.id.TextView01);
        }


        peiLvList[qujianFlag].setSelected(false);
        peiLvList[flagBak].setSelected(true);
        qujianFlag = flagBak;
        totalnum.setText("共" + totalBak + "场比赛");
        selectedList.clear();
        for (int i = 0; i < selectedListBak.size(); i++)
            selectedList.add(selectedListBak.get(i));
        shaiXuanGrid.setAdapter(adapter);
//        if (wfindex == 2 || wfindex > 3) {
//            peiLvLayout.setVisibility(View.GONE);
//            peiLvText.setVisibility(View.GONE);
//        } else {
//            peiLvLayout.setVisibility(View.VISIBLE);
//            peiLvText.setVisibility(View.VISIBLE);
//        }
        return shaiXuanPop;
    }

    private ArrayList<AbsJingcaizuqiuData> listTemp;
    int totalTemp = 0;

    public void getListTempData() {
        totalTemp = 0;
        listTemp = new ArrayList<>();
        // /当所选择的赛事列表为空时
        if (selectedList.size() == 0) {
            totalnum.setText("共0场比赛");
            return;
        }
        for (int j = 0; j < footballList.size(); j++) {
            List<JingcaizuqiuOneGame> gameList = filterGameList(footballList
                    .get(j).getGames(), qujianFlag, selectedList);
            if (gameList.size() > 0) {
                AbsJingcaizuqiuData listData = new ShengpingfuData();
                listData.setDate(footballList.get(j).getDate());
                listData.setIssueId(footballList.get(j).getIssueId());
                listData.setGames(gameList);
                listTemp.add(listData);
                totalTemp += gameList.size();
            }
        }
        totalnum.setText("共" + totalTemp + "场比赛");
    }

    // 筛选比赛
    private List<JingcaizuqiuOneGame> filterGameList(
            List<JingcaizuqiuOneGame> list, int flag, ArrayList<String> saishi) {
        List<JingcaizuqiuOneGame> listTemp = new ArrayList<JingcaizuqiuOneGame>();
        for (int i = 0; i < list.size(); i++) {
            JingcaizuqiuOneGame game = list.get(i);
            // 赛事符合同时赔率也要符合
            if (saishi.contains(game.getGameName())
                    && oddsJudgeGame(flag, game)) {
                listTemp.add(game);
            }
        }
        return listTemp;
    }

    // 判断比赛的赔率是否符合
    private boolean oddsJudgeGame(int flag, JingcaizuqiuOneGame game) {
        if (flag == 0)
            return true;
        double[] sp;
        if (wfindex == 2)
            sp = game.getZjqpeilv();
        else
            sp = game.getSpfpeilv();
        if (flag == 1) {
            for (int i = 0; i < sp.length; i++) {
                if (sp[i] < 1.5)
                    return true;
            }
        } else if (flag == 2) {
            for (int i = 0; i < sp.length; i++) {
                if (sp[i] >= 1.5 && sp[i] <= 2.0)
                    return true;
            }
        } else if (flag == 3) {
            for (int i = 0; i < sp.length; i++) {
                if (sp[i] > 4.0)
                    return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String lc = data.getStringExtra("location");

            // System.out.println("lc:"+lc);
            if (!TextUtils.isEmpty(lc)) {
                String[] s = lc.split("#");
                int groupPosition = Integer.parseInt(s[0]);
                int childPosition = Integer.parseInt(s[1]);
                if (requestCode == 1) {

                    // 修改数据源中IsSelected数组
                    try {
                        SparseArray<JingcaizuqiuOneGame> gameList = manager.footballSelectedArray.get(wfindex);

                        if (gameList.size() >= 15) {
                            onGameTouchCallback.onGameTouched(true);
                        } else {
                            JingcaizuqiuOneGame game = footballList
                                    .get(groupPosition).getGames()
                                    .get(childPosition);
                            game.setSelectedStr(data.getStringExtra("selectedStr"));
                            game.setSelContentStr(data.getStringExtra("selContentStr"));
                            // System.out.println(data.getStringExtra("selectedStr"));
                            // System.out.println(data.getStringExtra("selContentStr"));
                            if (TextUtils.isEmpty(game.getSelectedStr())) {
                                if (gameList.indexOfKey(game.orderIdLocal) >= 0)
                                    gameList.remove(game.orderIdLocal);
                            } else {
                                gameList.put(game.orderIdLocal, game);
                            }
                            onGameTouchCallback.onGameTouched(false);
                        }

                    } catch (Exception e) {

                        e.printStackTrace();
                    }

                } else if (requestCode == CaipiaoConst.MIX) {


                    // 修改数据源中IsSelected数组
                    try {
                        SparseArray<JingcaizuqiuOneGame> gameList = manager.footballSelectedArray.get(wfindex);

                        if (gameList.size() >= 15) {
                            onGameTouchCallback.onGameTouched(true);
                        } else {

                            String content = data.getStringExtra("content");
                            JingcaizuqiuOneGame jingcaizuqiuOneGame = (JingcaizuqiuOneGame) data.getSerializableExtra("gameData");
                            JingcaizuqiuOneGame game = footballList.get(groupPosition).getGames().get(childPosition);
                            game.setSelects(jingcaizuqiuOneGame.getSelects());
                            game.isSelected = jingcaizuqiuOneGame.isSelected;
                            if (TextUtils.isEmpty(content)) {
                                game.setMixHadSelect(0);
                            } else {
                                game.setMixHadSelect(content.split(",").length);
                            }
                            String spfStr = "";

                            if (!TextUtils.isEmpty(spfStr)) {
                                content = spfStr + content;
                            }

                            L.d(content);
                            game.setOtherSelect(content);
                            game.setSelectedStr(game.getMixContent());
                            game.setSelContentStr(data.getStringExtra("selContentStr"));
                            // System.out.println(data.getStringExtra("selectedStr"));
                            // System.out.println(data.getStringExtra("selContentStr"));
                            if (TextUtils.isEmpty(game.getMixContent())) {
                                if (gameList.indexOfKey(game.orderIdLocal) >= 0)
                                    gameList.remove(game.orderIdLocal);
                            } else {
                                gameList.put(game.orderIdLocal, game);
                            }
                            onGameTouchCallback.onGameTouched(false);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        }

        updateAdapter();
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void updateAdapter() {
        int size = 0;
        switch (wfindex) {
            case 0:
                adapterSfp.notifyDataSetChanged();
                listView.setAdapter(adapterSfp);
                size = adapterSfp.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
            case 1:

                adapterRQSfp.notifyDataSetChanged();
                listView.setAdapter(adapterRQSfp);
                size = adapterRQSfp.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;

            case 2:
                adapterZjq.notifyDataSetChanged();
                listView.setAdapter(adapterZjq);
                size = adapterZjq.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
            case 3:
                adapterHunHe.setType(flag);
                adapterHunHe.notifyDataSetChanged();
                listView.setAdapter(adapterHunHe);
                size = adapterHunHe.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
            case 4:

                adapterBanQuanChang.notifyDataSetChanged();
                listView.setAdapter(adapterBanQuanChang);
                size = adapterBanQuanChang.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
            case 5:

                adapterBifen.notifyDataSetChanged();
                listView.setAdapter(adapterBifen);
                size = adapterBifen.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
            case 6:

                mixAdapter.notifyDataSetChanged();
                listView.setAdapter(mixAdapter);
                size = mixAdapter.getGroupCount();
                for (int i = 0; i < size; i++) {
                    listView.collapseGroup(i);
                    listView.expandGroup(i);
                }
                break;
        }

    }


    private SparseArray<JingcaizuqiuOneGame> resetSparaArray(int flag, int wanfa) {


        SparseArray<JingcaizuqiuOneGame> dateTemp = new SparseArray<>();
        SparseArray<JingcaizuqiuOneGame> date = new SparseArray<>();
        switch (wanfa) {
            case 0:
                if (flag == 0) {
                    date = manager.footballSelectedArray.get(0);
                    int j = 0;
                    for (int i = 0; i < date.size(); i++) {

                        if (date.get(i).getSpfPassStatus() != 0) {
                            date.remove(i);
                            continue;
                        }

                        j++;
                        dateTemp.put(j, date.get(i));


                    }


                } else if (flag == 1) {
                    int j = 0;
                    date = manager.footballSelectedArray.get(0);
                    for (int i = 0; i < date.size(); i++) {
                        if (date.get(i).getSpfSingleStatus() != 0) {
                            date.remove(i);
                            j--;
                        } else {
                            j++;
                            dateTemp.put(j, date.get(i));
                        }

                    }
                }

                break;
            case 1:
                if (flag == 0) {


                } else if (flag == 1) {

                }

                break;
            case 2:
                if (flag == 0) {


                } else if (flag == 1) {

                }

                break;
            case 3:
                if (flag == 0) {


                } else if (flag == 1) {

                }

                break;
            case 4:
                if (flag == 0) {


                } else if (flag == 1) {

                }

                break;
            case 5:
                if (flag == 0) {


                } else if (flag == 1) {

                }

                break;


        }


        return dateTemp;
    }

    /**
     * todo  remove o
     *
     * @param date
     * @param flag
     * @param wanfa
     * @return
     */
    private List<AbsJingcaizuqiuData> resetList(List<AbsJingcaizuqiuData> date, int flag, int wanfa) {


        L.d("test", flag + ":" + wanfa);
        List<AbsJingcaizuqiuData> d = new ArrayList<>();

        switch (wanfa) {
            case 0:
                if (flag == 0) {

                    d = new ArrayList<>();
                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getSpfPassStatus() == 0) {
                                li.add(gam);
                            }
                        }


                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }

                } else if (flag == 1) {
                    d = new ArrayList<>();

                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getSpfSingleStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                            d.add(jingcaizuqiuData);
                    }


                }
                break;


            case 1:


                if (flag == 0) {

                    d = new ArrayList<>();


                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getRqspfPassStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }


                } else if (flag == 1) {
                    d = new ArrayList<>();


                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getRqspfSingleStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }


                }

                break;

            case 3:


                if (flag == 0) {

                    d = new ArrayList<>();


                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getSpfPassStatus() == 0 || gam.getRqspfPassStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }


                } else if (flag == 1) {
                    d = new ArrayList<>();


                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getSpfSingleStatus() == 0 || gam.getRqspfSingleStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }


                }

                break;

            case 4:


                if (flag == 0) {

                    d = new ArrayList<>();


                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getBqcPassStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }


                } else if (flag == 1) {
                    d = new ArrayList<>();


                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getBqcSingleStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }


                }
                break;

            case 5:


                if (flag == 0) {

                    d = new ArrayList<>();


                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getBfPassStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }


                } else if (flag == 1) {
                    d = new ArrayList<>();


                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getBfSingleStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }


                }
                break;

            case 2:

                if (flag == 0) {

                    d = new ArrayList<>();


                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getZjqPassStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }


                } else if (flag == 1) {
                    d = new ArrayList<>();
                    for (int j = 0; j < date.size(); j++) {
                        int m = 0;
                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            if (gam.getZjqSingleStatus() == 0) {
                                li.add(gam);
                            }
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }

                }
                break;

            default:
                if (flag == 0) {
                    d = new ArrayList<>();
                    for (int j = 0; j < date.size(); j++) {

                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        for (int i = 0; i < date.get(j).getGames().size(); i++) {
                            JingcaizuqiuOneGame gam = date.get(j).getGames().get(i);
                            li.add(gam);
                        }
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }


                } else if (flag == 1) {
                    for (int j = 0; j < date.size(); j++) {

                        AbsJingcaizuqiuData jingcaizuqiuData = new ShengpingfuData();
                        List<JingcaizuqiuOneGame> li = new ArrayList<>();
                        jingcaizuqiuData.setGames(li);
                        jingcaizuqiuData.setDate(date.get(j).getDate());
                        jingcaizuqiuData.setIssueId(date.get(j).getIssueId());
                        if (li != null && li.size() > 0)
                        d.add(jingcaizuqiuData);
                    }

                }


                break;

        }


        return d;


    }

}
