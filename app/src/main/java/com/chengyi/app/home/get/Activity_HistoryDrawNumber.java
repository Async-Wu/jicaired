package com.chengyi.app.home.get;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.bean.HadLotteryBean;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.CacheFactory;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.YOUMENG_EVENT;
import com.chengyi.app.util.net.NetUtil;
import com.chengyi.app.view.scoller.MyRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class Activity_HistoryDrawNumber extends BaseActivity {
    private MyRefreshListView listview;
    private RelativeLayout failedLayout;
    private RelativeLayout loaddata;
    private List<HadLotteryBean> list;
    private HistoryDrawNumberAdapter adapter;
    private View loadMoreView;
    private int lotteryId;
    private TextView loadMoreButton;

    private boolean ismore = true;
 
    private Caipiao cp;
    private TextView issue, date;
    private LinearLayout qiulayout;

    private boolean isReflesh = false;
    private HadLotteryBean firstData;
    boolean isCache = false;

    private JSONArray cache;//缓存所用

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        lotteryId = this.getIntent().getIntExtra("lotteryId", -1);
        setContentView(R.layout.new_history_kaijianggonggao);
        setBack();

        
        if (lotteryId == 10065)
           setCusTomeTitle("新快三-往期开奖");
        else if (lotteryId == 10066)
           setCusTomeTitle("11选5-往期开奖");
//		else if(lotteryId==10024)
//			centertextView.setText("排列3/5-往期开奖");
        else {
            cp = CaipiaoFactory.getInstance(this).getCaipiaoById(lotteryId);
           setCusTomeTitle(cp.getName() + "-往期开奖");
        }
        loaddata = (RelativeLayout) findViewById(R.id.loaddata);
        failedLayout = (RelativeLayout) findViewById(R.id.failed);
        failedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startLoadAnim();
                loadData();
                map.clear();


            }
        });
        listview = (MyRefreshListView) findViewById(R.id.listView);
        listview.setDividerHeight(0);
        listview.setCacheColorHint(Color.TRANSPARENT);
        listview.setFadingEdgeLength(0);
        listview.setonRefreshListener(new MyRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                begin = 0;
                isCache = false;       //刷新时候缓存最新的数据
                isReflesh = true;
                loadData();
                map.clear();

                CaipiaoUtil.youmengEvent(Activity_HistoryDrawNumber.this, YOUMENG_EVENT.new_kaijianggonggao_wangqi, map);
            }
        });
        View convertView = getLayoutInflater().inflate(R.layout.new_item_historydrawnumber, null);
        issue = (TextView) convertView.findViewById(R.id.issue);
        date = (TextView) convertView.findViewById(R.id.date);
        qiulayout = (LinearLayout) convertView.findViewById(R.id.qiulayout);
        listview.addHeaderView(convertView);
        loadMoreView = getLayoutInflater().inflate(R.layout.new_loadmore, null);
        loadMoreButton = (TextView) loadMoreView.findViewById(R.id.loadMoreButton);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ismore) {
                    loadMoreButton.setText("正在加载中...");   //设置按钮文字
                    isReflesh = false;
                    loadMoreData();
                    map.clear();

                    CaipiaoUtil.youmengEvent(Activity_HistoryDrawNumber.this, YOUMENG_EVENT.new_kaijianggonggao_wangqi, map);
                }
            }
        });
        listview.addFooterView(loadMoreView);    //设置列表底部视图
        adapter = new HistoryDrawNumberAdapter(this);
        list = new ArrayList<>();
        adapter.setList(list);
        listview.setAdapter(adapter);
        startLoadAnim();
        loadData();
    }

    private void loadMoreData() {
        setBegin(getBegin() + 10);
        getKuaikaiGonggao();
    }

    private void loadData() {
        if (!NetUtil.isNetworkAvailable(this)) {
            failedLayout.setVisibility(View.VISIBLE);
            loaddata.setVisibility(View.GONE);
        } else {
            ///从缓存中读取数据的条件是页面初次加载的时候
            long time = 0;
            if (lotteryId == 10065 || lotteryId == 10066 || (cp != null && cp.isKuaikai()))
                time = 1000 * 60 * 5;
            else
                time = 1000 * 60 * 60 * 5;
            cache = CacheFactory.getInstance().getJSONArray(getKey());
            if (!isReflesh && getBegin() == 0 && cache != null && System.currentTimeMillis() - CacheFactory.getInstance().getTime(getKey()) < time) {
                ResponseHandler.onSuccess(cache.toString());
                isCache = true;
            } else
                getKuaikaiGonggao();
        }
    }

    private String getKey() {
        return "kaijianggonggao" + lotteryId;
    }

    // 获取快开开奖公告
    private void getKuaikaiGonggao() {
        RequestParams params = getRequestParams();
        params.put("firstRow", getBegin() + "");
        params.put("fetchSize", 11 + "");
        if (lotteryId == 10024 * 10)
            params.put("lotteryId", 10024 + "");
        else
            params.put("lotteryId", lotteryId + "");
        HttpBusinessAPI.post(URLSuffix.ISSUE_NOTIFY, params, ResponseHandler);
    }

    HttpRespHandler ResponseHandler = new HttpRespHandler() {

        @Override
        public void onSuccess(String response) {

            listview.onRefreshComplete();
            int t = 0;
            if (getBegin() == 0) {
                loaddata.setVisibility(View.GONE);
                firstData = HadLotteryBean.createHistoryData(JSON.parseArray(response).getJSONObject(0), lotteryId);
                issue.setText(firstData.getIssue() + "期");
                date.setText(firstData.getDrawTime());
                ///快三要特殊处理下
                if (CaipiaoUtil.isKySj(lotteryId)) {
                    qiulayout.removeAllViews();
                    LayoutParams LP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    LP.gravity = Gravity.CENTER;
                    LP.leftMargin = 2;
                    String[] haoma = firstData.getDrawNumber().split(",");
                    int sum = 0;
                    for (int i = 0; i < haoma.length; i++) {
                        qiulayout.addView(getImg(haoma[i]), LP);
                        sum += Integer.parseInt(haoma[i]);
                    }

//					   LP.leftMargin=15;
                    qiulayout.addView(getBtn(sum + "", -1), LP);
                } else if (lotteryId == 10039 || lotteryId == 10040) {
                    qiulayout.removeAllViews();
                    LayoutParams LP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    LP.gravity = Gravity.CENTER;
                    LP.leftMargin = 5;

                    qiulayout.addView(getBtn(firstData.getDrawNumber(), R.drawable.redqiu_2), LP);

                } else {
                    addHaoma(firstData.getDrawNumber());
                }
                t = 1;
                ///非缓存状态，缓存数据
                if (!isCache)
                    CacheFactory.getInstance().putJSONArrayWithTime(getKey(), JSON.parseArray(response), System.currentTimeMillis());
            }
            super.onSuccess(response);
            if (isReflesh)
                list.clear();
            int showLenth = Math.min(JSON.parseArray(response).size(), 10);
            for (int i = t; i < showLenth; i++) {
                list.add(HadLotteryBean.createHistoryData(JSON.parseArray(response).getJSONObject(i), lotteryId));
            }
            adapter.notifyDataSetChanged();
            if (response.length() < 10 + 1) {
                ismore = false;
                loadMoreButton.setText("数据加载完毕!");
            } else
                loadMoreButton.setText("查看更多...");  //恢复按钮文字
        }

        @Override
        public void onFailure(Throwable error) {

            super.onFailure(error);
            if (isReflesh)
                showToast("刷新数据失败");
            if (getBegin() == 0) {
                failedLayout.setVisibility(View.VISIBLE);
                loaddata.setVisibility(View.GONE);
            }
            if (ismore)
                loadMoreButton.setText("加载失败,点击重试!");  //恢复按钮文字
            listview.onRefreshComplete();
        }
    };

    ///一般数字彩号码布局所用
    private void addHaoma(String number) {
        qiulayout.removeAllViews();
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) qiulayout.getLayoutParams();
        params.bottomMargin = 15;
        int index = number.indexOf("#");
        String[] haoma = null;
        LayoutParams LP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LP.gravity = Gravity.CENTER;
        LP.bottomMargin = 2;
        LP.leftMargin = 1;
        //有后驱情况
        if (index != -1) {
            haoma = number.substring(0, index).split(",");
            for (int i = 0; i < haoma.length; i++) {
                qiulayout.addView(getBtn(haoma[i], R.drawable.redqiu), LP);
            }
            haoma = number.substring(index + 1).split(",");
            LayoutParams LP2 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            LP2.gravity = Gravity.CENTER;
            LP2.bottomMargin = 2;
            LP2.leftMargin = 15;
            for (int i = 0; i < haoma.length; i++) {
                qiulayout.addView(getBtn(haoma[i], R.drawable.blueqiu), LP2);
            }
        } else {
            haoma = number.split(",");
            for (int i = 0; i < haoma.length; i++) {
                qiulayout.addView(getBtn(haoma[i], R.drawable.redqiu), LP);
            }
        }
        qiulayout.setLayoutParams(params);
    }

    private TextView getBtn(String s, int drawable) {
        TextView btn = new TextView(this);
        btn.setGravity(Gravity.CENTER);
        if (drawable != -1) {
            btn.setText(s);
            btn.setTextAppearance(this, R.style.text_style);
            btn.setBackgroundResource(drawable);
            btn.setTextColor(Color.WHITE);
        } else {
            btn.setText("和值:" + s);
            btn.setTextColor(Color.GRAY);
            btn.setTextSize(15);
        }
        return btn;
    }

    private ImageView getImg(String s) {
        ImageView img = new ImageView(this);
        switch (Integer.parseInt(s)) {
            case 1:
                img.setBackgroundResource(R.drawable.dice_v1_small);
                break;
            case 2:
                img.setBackgroundResource(R.drawable.dice_v2_small);
                break;
            case 3:
                img.setBackgroundResource(R.drawable.dice_v3_small);
                break;
            case 4:
                img.setBackgroundResource(R.drawable.dice_v4_small);
                break;
            case 5:
                img.setBackgroundResource(R.drawable.dice_v5_small);
                break;
            case 6:
                img.setBackgroundResource(R.drawable.dice_v6_small);
                break;
        }
        return img;
    }


}
