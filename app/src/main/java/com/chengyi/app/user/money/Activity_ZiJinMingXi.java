package com.chengyi.app.user.money;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.adapter.ZiJinMingXiAdapter;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.model.ZijinmingxiData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.view.scoller.MyRefreshListView;

import java.util.ArrayList;
import java.util.List;

import static com.chengyi.app.util.net.NetUtil.isNetworkAvailable;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class Activity_ZiJinMingXi extends BaseActivity implements OnItemClickListener {


    TextView quanbuBtn, goucaiBtn, zhongJiangBtn, chongZhiBtn, tikuanBtn;
    TextView currentBtn;
    LinearLayout bottomTishi;
    private RelativeLayout loaddata, failedLayout, noDataLayout;


    ZiJinMingXiAdapter adapter;
    LinearLayout top;
    private List<ZijinmingxiData> list = new ArrayList<ZijinmingxiData>();
    private int begin = 0;
    Intent intent = new Intent();

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    private MyRefreshListView listview;
    private View loadMoreView;
    private TextView loadMoreButton;
    boolean isRefresh = false;//是否下拉刷新
    private boolean ismore = false;//是否可以加载更多
    PopupWindow selectPop;  //区域选择性弹窗
    View vPopupWindow;

    private int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_gcandmxlist);
        initView();

    }

    private void initView() {
        findViewById(R.id.toplayoutselect).setVisibility(View.GONE);
        findViewById(R.id.ll_menu).setVisibility(View.INVISIBLE);
        setBack();
        findViewById(R.id.footballffootballtopbarLayout).setOnClickListener(this);
        ivAnim = (ImageView) findViewById(R.id.iv_anim);
        setCusTomeTitle("资金明细" + "-全部");
        loaddata = (RelativeLayout) findViewById(R.id.loaddata);
        failedLayout = (RelativeLayout) findViewById(R.id.failed);
        noDataLayout = (RelativeLayout) findViewById(R.id.nodata);
        failedLayout.setOnClickListener(this);
        //获取listview
        listview = (MyRefreshListView) findViewById(R.id.refreshListView);
        listview.setCacheColorHint(Color.TRANSPARENT);
        listview.setFadingEdgeLength(0);
        listview.setDividerHeight(0);
        listview.setonRefreshListener(new MyRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                isRefresh = true;
                begin = 0;
                requestData();
            }
        });
        listview.setOnItemClickListener(this);
        loadMoreView = getLayoutInflater().inflate(R.layout.new_loadmore, null);
        loadMoreButton = (TextView) loadMoreView.findViewById(R.id.loadMoreButton);
        bottomTishi = (LinearLayout) loadMoreView.findViewById(R.id.bottomtishi);
        bottomTishi.setClickable(false);
        loadMoreButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ismore) {
                    loadMoreButton.setText("正在加载中...");   //设置按钮文字
                    loadMoreData();
                }
            }
        });
        listview.addFooterView(loadMoreView);    //设置列表底部视图
        adapter = new ZiJinMingXiAdapter(this);
        adapter.setListData(list);
        listview.setAdapter(adapter);


        startLoadAnim();
        loadData();
    }

    private void loadMoreData() {
        setBegin(getBegin() + 10);
        requestData();
    }

    private void loadData() {
        loaddata.setVisibility(View.VISIBLE);
        failedLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        if (!isNetworkAvailable(this)) {
            failedLayout.setVisibility(View.VISIBLE);
            loaddata.setVisibility(View.GONE);
        } else
            requestData();
    }

    ///请求数据
    private void requestData() {

        RequestParams params = new RequestParams();
        params.put(URLSuffix.firstRow, getBegin() + "");
        params.put(URLSuffix.fetchSize, 11 + "");
        params.put("optionType", type + "");
        String mingxi = CaipiaoConst.BASE_URL + "user/capital_detail.htm";
        HttpBusinessAPI.post(mingxi, params, ResponseHandler);
    }

    HttpRespHandler ResponseHandler = new HttpRespHandler() {

        @Override
        public void onSuccess(String response) {
            listview.setVisibility(View.VISIBLE);
            if (isRefresh)
                listview.onRefreshComplete();
            loaddata.setVisibility(View.GONE);
            if (getBegin() == 0) {
                list.clear();
//
            }
            int showLenth;
            try {
                showLenth = Math.min(JSON.parseArray(response).size(), 10);
            } catch (Exception e) {
                showLenth = 10;
            }
            for (int i = 0; i < showLenth; i++) {// 最后一个不显示
                JSONObject json = JSON.parseArray(response).getJSONObject(i);
                ZijinmingxiData data = ZijinmingxiData.create(json);
                list.add(data);
            }

            adapter.notifyDataSetChanged();
            if (response.length() < 11) {
                ismore = false;
                //没有查询到数据
                loadMoreButton.setVisibility(View.GONE);
                if (list.size() == 0) {
                    listview.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                } else {
                    bottomTishi.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                }
            } else {
                loadMoreButton.setVisibility(View.VISIBLE);
                bottomTishi.setVisibility(View.GONE);
                noDataLayout.setVisibility(View.GONE);
                ismore = true;
                loadMoreButton.setText("查看更多...");  //恢复按钮文字
            }
        }

        @Override
        public void onFailure(Throwable error) {
            super.onFailure(error);
            if (getBegin() == 0 & !isRefresh) {
                failedLayout.setVisibility(View.VISIBLE);
                noDataLayout.setVisibility(View.GONE);
                loaddata.setVisibility(View.GONE);
            }
            if (isRefresh) {
                showToast("刷新数据失败!");
                listview.onRefreshComplete();
            }
            if (ismore)
                loadMoreButton.setText("加载失败,点击重试!");  //恢复按钮文字
        }

    };

    private ImageView ivAnim;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.failed:
                loaddata.setVisibility(View.VISIBLE);
                failedLayout.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        loadData();
                    }
                }, 500);
                break;
            case R.id.footballffootballtopbarLayout:
            case R.id.tv_title:
              if (selectPop != null && selectPop.isShowing()) {
                    dismiss();
                } else {
                    AppUtil.startRotateAnim(ivAnim);
                    getSelectPop().showAsDropDown(findViewById(R.id.footballffootballtopbarLayout));
                    top.startAnimation(mShowAction);
                    top.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.button1:
                dismiss();
                if (!quanbuBtn.isSelected()) {
                    setCusTomeTitle("资金明细-" + "全部");
                    currentBtn.setSelected(false);
                    quanbuBtn.setSelected(true);
                    currentBtn = quanbuBtn;
                    begin = 0;
                    type = 0;
                    loadData();
                }
                break;
            case R.id.button2:
                dismiss();
                if (!goucaiBtn.isSelected()) {
                    setCusTomeTitle("资金明细-" + "提款");
                    currentBtn.setSelected(false);
                    goucaiBtn.setSelected(true);
                    currentBtn = goucaiBtn;
                    begin = 0;
                    type = 2;
                    loadData();
                }
                break;
            case R.id.button3:
                dismiss();
                if (!zhongJiangBtn.isSelected()) {
                    setCusTomeTitle("资金明细-" + "中奖");
                    currentBtn.setSelected(false);
                    zhongJiangBtn.setSelected(true);
                    currentBtn = zhongJiangBtn;
                    begin = 0;
                    type = 3;
                    loadData();
                }
                break;
            case R.id.button4:
                dismiss();
                if (!chongZhiBtn.isSelected()) {
                    setCusTomeTitle("资金明细" + "-充值");
                    currentBtn.setSelected(false);
                    chongZhiBtn.setSelected(true);
                    currentBtn = chongZhiBtn;
                    begin = 0;
                    type = 1;
                    loadData();
                }
                break;
            case R.id.button5:
                dismiss();
                if (!tikuanBtn.isSelected()) {
                    setCusTomeTitle("资金明细" + "-提款");
                    currentBtn.setSelected(false);
                    tikuanBtn.setSelected(true);
                    currentBtn = tikuanBtn;
                    begin = 0;
                    type = 4;
                    loadData();
                }
                break;
        }
    }

    private PopupWindow getSelectPop() {
        if (vPopupWindow == null) {
            vPopupWindow = getLayoutInflater().inflate(
                    R.layout.pop_selectlayout, null, false);
            selectPop = new PopupWindow(vPopupWindow,
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);


            selectPop.setFocusable(true);
            selectPop.setOutsideTouchable(true);
            selectPop.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    AppUtil.startResRotateAnim(ivAnim);
                    top.clearAnimation();

                }
            });
            top = (LinearLayout) vPopupWindow.findViewById(R.id.layouttopselect);
            top.startAnimation(mShowAction);
            top.setVisibility(View.VISIBLE);
            try {
                selectPop.setAnimationStyle(R.style.SelectPopWindowAnimation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            quanbuBtn = (TextView) vPopupWindow.findViewById(R.id.button1);
            quanbuBtn.setSelected(true);
            currentBtn = quanbuBtn;
            quanbuBtn.setOnClickListener(this);
            goucaiBtn = (TextView) vPopupWindow.findViewById(R.id.button2);
            goucaiBtn.setOnClickListener(this);
            zhongJiangBtn = (TextView) vPopupWindow.findViewById(R.id.button3);
            zhongJiangBtn.setOnClickListener(this);
            chongZhiBtn = (TextView) vPopupWindow.findViewById(R.id.button4);
            chongZhiBtn.setOnClickListener(this);
            tikuanBtn = (TextView) vPopupWindow.findViewById(R.id.button5);
            tikuanBtn.setVisibility(View.INVISIBLE);
            tikuanBtn.setOnClickListener(this);


        }

        if (currentBtn != null)
            currentBtn.setSelected(true);

        return selectPop;
    }

    private void dismiss() {
        top.startAnimation(set);
        top.setVisibility(View.INVISIBLE);

        selectPop.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        if (arg2 - 1 >= list.size())
            return;
        ZijinmingxiData data = list.get(arg2 - 1);
//		if(data.getIsBuy().equals("1")){
//		    intent.setClass(this, Activity_HemaiXiangQing.class);
//			intent.putExtra(URLSuffix.schemeId,data.getSchemeId());
//			intent.putExtra("whereFrom", 9);
//
//		}else{
        intent.setClass(this, Activity_ZiJinXiangQing.class);
        intent.putExtra("time", data.getCreateTime());
        intent.putExtra("type", data.getType());
        intent.putExtra("detail", data.getDetail());
//		}
        startActivity(intent);
    }
}
