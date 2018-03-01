package com.chengyi.app.jingji.football;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;
import com.alibaba.fastjson.JSON;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.adapter.JingCaiKaiJiangAdapter;
import com.chengyi.app.model.caipiao.CaipiaoFactory;

import com.chengyi.app.model.model.JingCaiFootBallKaiJiangData;
import com.chengyi.app.model.model.JingCaiFootBallOneGameResult;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.view.scoller.MyExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.chengyi.app.util.net.NetUtil.isNetworkAvailable;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@Deprecated
public class  FootBall_Previous_Record extends BaseActivity implements OnScrollListener,OnClickListener{



	Button riqibtn;
	MyExpandableListView listView;
	private FrameLayout indicatorGroup;
	private int indicatorGroupId = -1;
	private int indicatorGroupHeight;
	private LayoutInflater mInflater;
	JingCaiKaiJiangAdapter adapter;
	List<JingCaiFootBallKaiJiangData> list;
    RelativeLayout loaddata,failedLayout,mainlayout,noDataLayout;
    private boolean isReflesh=false;
    String  dateTime;
    Date dt;
    boolean  isFromJingCai=false;
    int curMonth=-1;
    int curYear=-1;
    int curDay=-1;
    private SharedPreferences prefence;
    Editor  editor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		prefence=getSharedPreferences("jingcaizuqiukaijiang", MODE_PRIVATE);
		editor=prefence.edit();
		dt = new Date();

		dateTime=sdf.format(dt);
		isFromJingCai=this.getIntent().getBooleanExtra("isFromJingCai", false);
		setContentView(R.layout.fragment_football_previous_record);

		setCusTomeTitle("竞彩足球往期开奖");
		setBack();
		riqibtn=(Button) findViewById(R.id.riqibtn);
		riqibtn.setOnClickListener(this);
		listView=(MyExpandableListView) findViewById(R.id.expandableListView);
		listView.setDivider(null);
		listView.setGroupIndicator(null);
		listView.setChildDivider(null);
		listView.setChildIndicator(null);
		listView.setCacheColorHint(0x000000);
		listView.setSelector(R.drawable.translucent_background);
		listView.setOnScrollListener(this);
		listView.setonRefreshListener(new MyExpandableListView.OnRefreshListener(){
			public void onRefresh() {
				isReflesh=true;
				requestData();
			}
		});
		mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		indicatorGroup = (FrameLayout) findViewById(R.id.topGroup);
		indicatorGroup.setVisibility(View.INVISIBLE);
		mInflater.inflate(R.layout.fragment_lottery_football_listview_groupview, indicatorGroup, true);
		adapter=new JingCaiKaiJiangAdapter(this);
		list=new ArrayList<>();
		loaddata=(RelativeLayout) findViewById(R.id.loaddata);
		failedLayout=(RelativeLayout) findViewById(R.id.failed);
		failedLayout.setOnClickListener(this);
		mainlayout=(RelativeLayout) findViewById(R.id.mainlayout);
		if(isFromJingCai)
			findViewById(R.id.bottomlayoutbuy).setVisibility(View.GONE);
		findViewById(R.id.bottomlayoutbuy).setOnClickListener(this);
//		findViewById(R.id.ffootballtopbarLayout).setBackgroundColor(getResources().getColor(R.color.default_green_actionbar_bg));
		noDataLayout=(RelativeLayout)findViewById(R.id.nodata);
		loadData();
	}
	private void  loadData(){
		startLoadAnim();
		if(! isNetworkAvailable(this)){
			failedLayout.setVisibility(View.VISIBLE);
			loaddata.setVisibility(View.GONE);
			mainlayout.setVisibility(View.GONE);
		}else if(dateTime.equals(sdf.format(dt))&&!isReflesh&&prefence.getString("footballDate", "").equals(dateTime)){
			//从缓存中取数据条件:当前日期是今天且不是刷新操作
			try {
				ResponseHandler.onSuccess((prefence.getString("footballData", "")));
			} catch ( Exception e) {
				e.printStackTrace();
				requestData();
			}
		}else 
			 requestData();
	}
	/****
	 * 请求数据
	 * */
	private void  requestData(){
	    	RequestParams params = getRequestParams();
	    	params.put("matchTime", dateTime);
	        HttpBusinessAPI.post(URLSuffix.FOOTBALL_HISTORY, params, ResponseHandler);
	}
	 HttpRespHandler ResponseHandler = new HttpRespHandler() {
			@SuppressLint("SimpleDateFormat")
			@Override
			public void onSuccess(String response) {

				list.clear();
				//初始化3天的数据
				for(int i=0;i<3;i++){
					list.add(new JingCaiFootBallKaiJiangData());
				}
				String date="";
				int t=-1;
				super.onSuccess(response);
				for(int i=0;i<response.length();i++){
					JingCaiFootBallOneGameResult data=JingCaiFootBallOneGameResult.create(JSON.parseArray(response).getJSONObject(i));
                    if(!date.equals(data.getMatchTime().split(" ")[0])){
                    	date=data.getMatchTime().split(" ")[0];
                    	t++;
                    	if(t<=2){
                    		Date d = null;
									try {
										d = sdf.parse(date);
										String now = new SimpleDateFormat("yyyy年MM月dd日").format(d);
			                    	     list.get(t).setDate(now);
									} catch (java.text.ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
                    	}
                    }
                    if(t<=2)
                    list.get(t).getGames().add(data);
				}
				///移出多余的list数据
				for(int i=2;i>t;i--)
					list.remove(i);
				adapter.setList(list);
				listView.setAdapter(adapter);
				for (int i = 0; i < list.size(); i++) {
					listView.expandGroup(i);
				}
				if(list.size()==0){
					noDataLayout.setVisibility(View.VISIBLE);
				}else
					noDataLayout.setVisibility(View.GONE);
				if(isReflesh)
					listView.onRefreshComplete();
				else{
					failedLayout.setVisibility(View.GONE);
					loaddata.setVisibility(View.GONE);
					mainlayout.setVisibility(View.VISIBLE); 
				}
				///缓存数据，条件是，只保存当天的数据
				if(dateTime.equals(sdf.format(dt))){
					editor.putString("footballDate", dateTime);
					editor.putString("footballData", response.toString());
					editor.commit();
				}
				isReflesh=false;
			}
			@Override
			public void onFailure(Throwable error) {

				super.onFailure(error);
				if(isReflesh){
					showToast("数据刷新失败!");
					listView.onRefreshComplete();
				}
				else{
					failedLayout.setVisibility(View.VISIBLE);
					loaddata.setVisibility(View.GONE);
					mainlayout.setVisibility(View.GONE);
				}
				isReflesh=false;
			}
			
		};
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {

		listView.setFirstItemIndex(firstVisibleItem);	
		final ExpandableListView listview = (ExpandableListView) view;
		int npos = view.pointToPosition(0, 0);// 其实就是firstVisibleItem
		if (npos == AdapterView.INVALID_POSITION)// 如果第一个位置值无效
			return;
		
		long pos = listview.getExpandableListPosition(npos);
		int childPos = ExpandableListView.getPackedPositionChild(pos);// 获取第一行child的id
		int groupPos = ExpandableListView.getPackedPositionGroup(pos);// 获取第一行group的id
		
		if (childPos == AdapterView.INVALID_POSITION&&firstVisibleItem==0) {// 第一行不是显示child,就是group,此时没必要显示指示器
			indicatorGroup.setVisibility(View.GONE);// 隐藏指示器
		} else{
			indicatorGroup.setVisibility(View.VISIBLE);// 滚动到第一行是child，就显示指示器
			indicatorGroupHeight = indicatorGroup.getMeasuredHeight();// 获取group的高度
		}
		// update the data of indicator group view
		if (groupPos != indicatorGroupId&&firstVisibleItem!=0) {// 如果指示器显示的不是当前group
			adapter.getGroupView(groupPos, listview.isGroupExpanded(groupPos),indicatorGroup.getChildAt(0), null);// 将指示器更新为当前group
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
		if (nEndPos == AdapterView.INVALID_POSITION)//如果无效直接返回
			return;
		long pos2 = listview.getExpandableListPosition(nEndPos);
		int groupPos2 = ExpandableListView.getPackedPositionGroup(pos2);//获取第二个group的id
		
		if (groupPos2 != indicatorGroupId) {//如果不等于指示器当前的group
			View viewNext = listview.getChildAt(nEndPos- listview.getFirstVisiblePosition());
			showHeight = viewNext.getTop();
		}
		// update group position
		MarginLayoutParams layoutParams = (MarginLayoutParams) indicatorGroup.getLayoutParams();
		layoutParams.topMargin =  -(indicatorGroupHeight - showHeight);
		indicatorGroup.setLayoutParams(layoutParams);
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		
	}
	@Override
	public void onClick(View v) {

		switch(v.getId()){

			case R.id.failed:
				loadDataAgain();
			break;
			case R.id.riqibtn:
				getWheelViewDatePop(curYear,curMonth,curDay).showAtLocation(this.findViewById(R.id.jingcaimainlayout), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
			break;
			case R.id.cancel:
		    case R.id.imagebg:
				if(wheelViewDatePop!=null&&wheelViewDatePop.isShowing())
					wheelViewDatePop.dismiss();
				break;
		    case R.id.ensure:
		    	if(wheelViewDatePop!=null&&wheelViewDatePop.isShowing()){
		    		curMonth=wheelMonth.getCurrentItem();
		    		curYear=wheelYear.getCurrentItem();
		    		curDay=wheelDay.getCurrentItem();
		    		dateTime=adapterYear.getItemText(curYear)+"-";
		    		dateTime+=adapterMonth.getItemText(curMonth)+"-";
		    		dateTime+=adapterDay.getItemText(curDay)+"";
                    wheelViewDatePop.dismiss();
                    loadDataAgain();
				}
		    break;
		    case R.id.bottomlayoutbuy:
		    	CaipiaoApplication.getInstance().setCurrentCaipiao(CaipiaoFactory.getInstance(this).getCaipiaoById(10059));
				Intent intent=new Intent(this,FootBall.class);
				startActivity(intent);
				finish();
		    break;
		}
		
	}
	private void  loadDataAgain(){
		loaddata.setVisibility(View.VISIBLE);
		failedLayout.setVisibility(View.GONE);
		mainlayout.setVisibility(View.GONE);
		handler.postDelayed(new Runnable(){
			@Override
			public void run() {

				loadData();
			}
		}, 500);
	}
}
