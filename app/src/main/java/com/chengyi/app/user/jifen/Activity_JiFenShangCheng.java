package com.chengyi.app.user.jifen;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.adapter.JiFenShopAdapter;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.model.JiFenShopData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.view.TiShiDialog;
import com.chengyi.app.view.XuhaoExitDialog;
import com.chengyi.app.view.scoller.MyRefreshListView;

import java.util.ArrayList;
import java.util.List;

import static com.chengyi.app.util.net.NetUtil.isNetworkAvailable;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_JiFenShangCheng extends BaseActivity implements XuhaoExitDialog.ICallback {

	TextView centertextView,jifentext;

	RelativeLayout  failedLayout,loaddata;
	private MyRefreshListView listview;
	private List<JiFenShopData> list=new ArrayList<JiFenShopData>();
	JiFenShopAdapter adapter;
	XuhaoExitDialog exitDailog;
	TiShiDialog dailog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_jifenshangcheng);
 		initView();
 		loadData();
	}
    private void initView(){

    	centertextView.setText("积分商城");
//


		setBack();

 		loaddata=(RelativeLayout) findViewById(R.id.loaddata);
 		failedLayout=(RelativeLayout) findViewById(R.id.failed);
 		failedLayout.setOnClickListener(this);
 		listview=(MyRefreshListView) findViewById(R.id.refreshListView);
		listview.setCacheColorHint(Color.TRANSPARENT);
		listview.setFadingEdgeLength(0);
		adapter=new JiFenShopAdapter(this);
		adapter.setList(list);
		listview.setAdapter(adapter);
		jifentext=(TextView) findViewById(R.id.jifentext);
//		jifentext.setText(this.getUser().getJifenString()+"分");
		View convertView=getLayoutInflater().inflate(R.layout.new_jifenshop_bottom, null);  
		listview.addFooterView(convertView); 
		//创建对话框
		exitDailog=new XuhaoExitDialog(this);
		exitDailog.setCallBack(this);
		dailog=new TiShiDialog(this);
		startLoadAnim();
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
	private void loadData(){
		if(!isNetworkAvailable(this)){
			failedLayout.setVisibility(View.VISIBLE);
			loaddata.setVisibility(View.GONE);
		}else
			request();
    }
	private void  request(){
		RequestParams params = getRequestParams();
		HttpBusinessAPI.post(URLSuffix.JIFENSHANGCHENG, params, new HttpRespHandler() {
			@Override
			public void onSuccess(String response) {

				loaddata.setVisibility(View.GONE);
				list.clear();
				super.onSuccess(response);

				for (int i = 0; i < response.length(); i++) {
					JSONObject json = JSON.parseArray( response).getJSONObject(i);
					JiFenShopData data = new JiFenShopData();
					data.setCash(json.getString("cash" ));
					data.setPictureURL(json.getString("picture" ));
					data.setPrizeName(json.getString("prizeName" ));
					data.setScore(json.getString("score" ));
					if (data.getScore().equals("5万分") || data.getCash().equals("彩金50元")) {
						continue;
					}
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
	boolean  shiWu=true;
	JiFenShopData data;
	//积分兑换商品
	public void  duiHuanShiWu(int p,boolean shiwu){
		shiWu=shiwu;
		data=list.get(p);
//		if(Long.parseLong(getUser().getJifenString())<Long.parseLong(data.getScore().substring(0, data.getScore().length()-2))*10000L){
//			showToast("当前积分不足兑换此物!");
//		}else
		if(!exitDailog.isShowing()){
			exitDailog.show();
			if(shiwu){
				exitDailog.getCenterTextView().setText("确认兑换"+data.getPrizeName()+"?");
			}else{
				exitDailog.getCenterTextView().setText("确认兑换"+data.getCash()+"?");
			}
		}
	}
	@Override
	public void reBack() {

		RequestParams params = getRequestParams();
		if(shiWu)
			params.put("giftName", data.getPrizeName());
		else
			params.put("giftName", data.getCash());
		if(exitDailog.isShowing())
			exitDailog.dismiss();
		showLoading("正在处理....");
		HttpBusinessAPI.post(URLSuffix.JIFEN_DUIHUAN, params, new HttpRespHandler(){

			@Override
			public void onSuccess(String response) {

				super.onSuccess(response);
				hideLoading();
				if(JSON.parseObject(response).getIntValue("flag")==1){
					dailog.show();
					dailog.getTitle().setText("恭喜，兑换成功！");
					if(shiWu){
						dailog.getText1().setText("我们客服稍后联系你");
						dailog.getText2().setText("请保持电话畅通！");
					}else{
						dailog.getText1().setText("我们客服将稍后处理");
						dailog.getText2().setText("请注意查看您的账户！");
					}
				}else{
					dailog.show();
					dailog.getTitle().setText("抱歉，兑换失败");
					dailog.getText1().setText("请联系客服");
					dailog.getText2().setText("400-666-7575");
				}
			}

			@Override
			public void onFailure(Throwable error) {

				super.onFailure(error);
				hideLoading();
				dailog.show();
				dailog.getTitle().setText("抱歉，兑换失败");
				dailog.getText1().setText("请联系客服");
				dailog.getText2().setText("400-666-7575!");
			}
			
		});
	}
	@Override
	public void close() {

		if(exitDailog.isShowing())
			exitDailog.dismiss();
	}
}
