package com.chengyi.app.user.money;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.adapter.YouHuiAdapter;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.model.model.YouHuiData;
import com.chengyi.app.view.scoller.MyRefreshListView;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;

import java.util.ArrayList;
import java.util.List;

import static com.chengyi.app.util.net.NetUtil.isNetworkAvailable;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_ChongZhiActivity extends BaseActivity {


    private MyRefreshListView listview;
    RelativeLayout failedLayout, loaddata;
    YouHuiAdapter adapter;
    List<YouHuiData> list = new ArrayList<YouHuiData>();
    Intent in = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_chongzhiactivity);

       setCusTomeTitle("充值优惠");
        setBack();
        in.setClass(this, Activity_ZhangHuChongZhi.class);
        in.putExtra("isfromgoumaiqueren", true);
        in.putExtra("youhui", true);
        listview = (MyRefreshListView) findViewById(R.id.refreshListView);
        listview.setDividerHeight(0);
        listview.setCacheColorHint(Color.TRANSPARENT);
        listview.setFadingEdgeLength(0);
        TextView tv = new TextView(this);
        tv.setText("注：优惠充值金额只能消费，不可提现，中奖不受影响");
        tv.setPadding(20, 10, 10, 10);
        tv.setTextColor(this.getResources().getColor(R.color.gray));
        tv.setTextSize(15);
        listview.addFooterView(tv);
        adapter = new YouHuiAdapter(this);
        adapter.setList(list);
        listview.setAdapter(adapter);
        loaddata = (RelativeLayout) findViewById(R.id.loaddata);
        failedLayout = (RelativeLayout) findViewById(R.id.failed);
        failedLayout.setOnClickListener(this);
        startLoadAnim();
        loadData();
    }

    private void loadData() {
        if (!isNetworkAvailable(this)) {
            failedLayout.setVisibility(View.VISIBLE);
            loaddata.setVisibility(View.GONE);
        } else
            request();
    }

    private void request() {
        RequestParams params = getRequestParams();
        HttpBusinessAPI.post(URLSuffix.YOUHUI, params, new HttpRespHandler() {
            @Override
            public void onSuccess(String response) {

                loaddata.setVisibility(View.GONE);
                list.clear();
                for (int i = 0; i < response.length(); i++) {
                    YouHuiData data = new YouHuiData();
                    JSONObject json = JSON.parseArray(response).getJSONObject(i);
                    data.setNewCost(json.getString("newCost" ));
                    data.setOldCost(json.getString("oldCost" ));
                    data.setSaveCost(json.getString("saveCost" ));
                    data.setPicture(json.getString("picture" ));
                    list.add(data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error) {

                super.onFailure(error);
                failedLayout.setVisibility(View.VISIBLE);
                loaddata.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.failed:
                failedLayout.setVisibility(View.GONE);
                loaddata.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        loadData();
                    }
                }, 500);
                break;
        }
    }

    public void movetoChongzhi(int p) {
        YouHuiData data = list.get(p);
        in.putExtra("money", data.getNewCost());
        in.putExtra("oldCost", data.getOldCost());
        in.putExtra("saveCost", data.getSaveCost());
        this.startActivity(in);
        this.pullUpActivityAnim();
    }
}
