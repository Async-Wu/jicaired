package com.chengyi.app.user.info;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.adapter.LinghaoAdapter;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.model.LinghaoData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.view.scoller.MyRefreshListView;

import java.util.ArrayList;
import java.util.List;

import static com.chengyi.app.util.net.NetUtil.isNetworkAvailable;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_LingHaoHistory extends BaseActivity {
    

	private MyRefreshListView listview;
	RelativeLayout  failedLayout,loaddata,noDataLayout;
	private LinghaoAdapter adapter;
	private  List<LinghaoData> dataList=new ArrayList<LinghaoData>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_chongzhiactivity);

    	setCusTomeTitle("领号记录");
    	listview=(MyRefreshListView) findViewById(R.id.refreshListView);
		listview.setDividerHeight(0);
		listview.setCacheColorHint(Color.TRANSPARENT);
		listview.setFadingEdgeLength(0);
		adapter=new LinghaoAdapter(this);
		adapter.setData(dataList);
		listview.setAdapter(adapter);
		loaddata=(RelativeLayout) findViewById(R.id.loaddata);
		noDataLayout=(RelativeLayout)findViewById(R.id.nodata);
 		failedLayout=(RelativeLayout) findViewById(R.id.failed);
 		failedLayout.setOnClickListener(this);

		startLoadAnim();
		loadData();
	}
    private void  loadData(){
    	    noDataLayout.setVisibility(View.GONE);
	    	if(! isNetworkAvailable(this)){
				failedLayout.setVisibility(View.VISIBLE);
				loaddata.setVisibility(View.GONE);
			}else
				request();
	}
    private void  request(){
		RequestParams params = getRequestParams();
		HttpBusinessAPI.post(URLSuffix.LINGHAOQUERY, params, new HttpRespHandler() {

			@Override
			public void onSuccess(String response) {

				super.onSuccess(response);
				loaddata.setVisibility(View.GONE);
				dataList.clear();
				JSONArray array = JSON.parseObject(response).getJSONArray("userReceiveNoList");
				for (int i = 0; i < array.size(); i++) {
					JSONObject obj = array.getJSONObject(i);
					LinghaoData lingData = new LinghaoData();
					lingData.create(obj);
					dataList.add(lingData);
				}
				if (dataList.size() == 0) {
//					 listview.addFooterView(loadMoreView);    //设置列表底部视图  
//					 loadMoreButton.setText("没有查询到相关数据!");
					noDataLayout.setVisibility(View.VISIBLE);
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

		switch(v.getId()){

			case R.id.failed:
				failedLayout.setVisibility(View.GONE);
				loaddata.setVisibility(View.VISIBLE);
				handler.postDelayed(new Runnable(){
					@Override
					public void run() {
						loadData();
					}
				}, 500);
			    break;
        }
	}
}
