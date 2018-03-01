package com.chengyi.app.user.setting;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.adapter.FeedBackAdapter;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.model.FeedBackData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.view.scoller.MyRefreshListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.chengyi.app.util.net.NetUtil.isNetworkAvailable;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_FeedBack extends BaseActivity {


	RelativeLayout  failedLayout;
	private RelativeLayout loaddata,mainlayout;
	EditText   yijianEdite;
	Button  sendbtn;
	List<FeedBackData>  list=new ArrayList<FeedBackData>();
	FeedBackAdapter adapter;
	private MyRefreshListView listview;
	boolean isReflesh=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_feedback);

	 setBack();
		loaddata=(RelativeLayout) findViewById(R.id.loaddata);
		failedLayout=(RelativeLayout) findViewById(R.id.failed);
		failedLayout.setOnClickListener(this);
		mainlayout=(RelativeLayout) findViewById(R.id.mainlayout);
		yijianEdite=(EditText) findViewById(R.id.yijianedite);
		sendbtn=(Button) findViewById(R.id.sendbtn);
		sendbtn.setOnClickListener(this);
		adapter=new FeedBackAdapter(this);
		listview=(MyRefreshListView) findViewById(R.id.listView);
		listview.setDividerHeight(0);
		listview.setCacheColorHint(Color.TRANSPARENT);
		listview.setFadingEdgeLength(0);
	    listview.setonRefreshListener(new MyRefreshListView.OnRefreshListener() {
				@Override
		     public void onRefresh() {
					isReflesh=true;
					request();
				}
	     });
	    adapter.setList(list);
		listview.setAdapter(adapter);
		startLoadAnim();
		///加载数据
		loadData();
	}
	@Override
	public void onClick(View v) {

		switch(v.getId()){

			case R.id.failed:
				failedLayout.setVisibility(View.GONE);
				mainlayout.setVisibility(View.GONE);
				loaddata.setVisibility(View.VISIBLE);
				handler.postDelayed(new Runnable(){
					@Override
					public void run() {

						loadData();
					}
				}, 500);
			break;
			case R.id.sendbtn:
				if(TextUtils.isEmpty(yijianEdite.getText().toString())){
					showToast("留言内容不能为空!");
				}else{
					sendLiuYanContent();
				}
			break;
		}
	}
	private void  sendLiuYanContent(){
		showLoading("正在处理....");
		RequestParams params = getRequestParams();
		params.put("content", yijianEdite.getText().toString());
		params.put("title", "client");
		HttpBusinessAPI.post(URLSuffix.FEEDBACK_SEND, params, new HttpRespHandler() {
			@Override
			public void onSuccess(String result) {

				super.onSuccess(result);
				hideLoading();
				if (checkResult(result)) {
					showToast("留言成功!");
					FeedBackData data = new FeedBackData();
					data.setContent(yijianEdite.getText().toString());
					data.setReply("");
					data.setReplyTime(sdf.format(new Date()));
					list.add(0, data);
					adapter.notifyDataSetChanged();
					closeSoftKeybord();
					yijianEdite.setText("");
				}
			}

			@Override
			public void onFailure(Throwable error) {

				super.onFailure(error);
				hideLoading();
				showToast("数据提交失败!");
			}

		});
	}
	private void loadData(){
		if(! isNetworkAvailable(this)){
			failedLayout.setVisibility(View.VISIBLE);
			loaddata.setVisibility(View.GONE);
			mainlayout.setVisibility(View.GONE);
		}else
			request();
    }
	private void  request(){
		RequestParams params = getRequestParams();
		params.put("firstRow", 0+ "");
		params.put("fetchSize",10 + "");
		HttpBusinessAPI.post(URLSuffix.FEEDBACK_QUERY, params, new HttpRespHandler(){
			@Override
			public void onSuccess(String response) {

				super.onSuccess(response);
				/**
				 * 如果用户提交过意见，那么就展示意见列表，隐藏发送按钮，
				 * 如果用户没有提交过意见，那么就弹出键盘
				 * */
				int length=response.length();
				if(length==0){
					openKeyboard(100);
					yijianEdite.requestFocus();
				}else{
					sendbtn.setVisibility(View.GONE);
					listview.setVisibility(View.VISIBLE);
					list.clear();
					for(int i=0;i<length;i++){
						FeedBackData data=new FeedBackData();
						JSONObject json= JSON.parseArray(response).getJSONObject(i);
						data.setContent(json.getString("content"  ));
						data.setReply(json.getString("reply" ));
						data.setLwTime(sdf.format(new Date(json.getLongValue("lwTime"))));
						data.setReplyTime(sdf.format(new Date(json.getLongValue("replyTime" ))));
//						data.setContent("什么是彩票合买？");
//						data.setReply("彩票代购指的是由个人出资委托本站购买指定的彩票，由本站代理出票，兑奖和派奖。彩票代购无需您外出排队买彩票，不受自然因素困扰，既快捷方便，又高效安全。");
//						data.setReplyTime("2013-09-23");
						list.add(data);
					}
					 adapter.notifyDataSetChanged();

				}
				//System.out.println(response);
				if(!isReflesh){
					failedLayout.setVisibility(View.GONE);
					loaddata.setVisibility(View.GONE);
					mainlayout.setVisibility(View.VISIBLE);
				}else
				    listview.onRefreshComplete();
			}
			@Override
			public void onFailure(Throwable error) {

				super.onFailure(error);
				if(!isReflesh){
					failedLayout.setVisibility(View.VISIBLE);
					loaddata.setVisibility(View.GONE);
					mainlayout.setVisibility(View.GONE);
				}else{
					 listview.onRefreshComplete();
					 showToast("刷新数据失败!");
				}
			}
		});
	}

	Handler  myhandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			switch(msg.what){
				case 0:
					yijianEdite.requestFocus();
					listview.setVisibility(View.GONE);
					sendbtn.setVisibility(View.VISIBLE);
				break;
				case 1:
					if(list.size()>0){
						listview.setVisibility(View.VISIBLE);
						sendbtn.setVisibility(View.GONE);
					}	
				break;
			}
		}
		
	};
}
