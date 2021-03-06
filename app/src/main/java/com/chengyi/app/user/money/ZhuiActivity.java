package com.chengyi.app.user.money;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.adapter.GouCaiRecordAdapter;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.model.GoucaijiluData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.user.info.FanganorderActivity;
import com.chengyi.app.util.net.NetUtil;
import com.chengyi.app.view.scoller.MyRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  ZhuiActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    Button woFaQi,woCanYu,btnLeft;
    TextView daigouBtn,hemaiBtn,zhuiHaoBtn,zhongJiangBtn,allBtn;
    LinearLayout bottomTishi;

    private MyRefreshListView listview;
    private View loadMoreView;
    private TextView loadMoreButton;
    private RelativeLayout loaddata,failedLayout,noDataLayout;
    private int type=0;//5：代购
    private int status;// 0:全部, 1:中奖, 2:未中奖, 3:追号中, 4:撤单
    private int begin = 0;
    private int whereFrom=1;
    private boolean  ismore=false;//是否可以加载更多
    boolean  isRefresh=false;//是否下拉刷新
    private List<GoucaijiluData> listData=new ArrayList<GoucaijiluData>();
    private GouCaiRecordAdapter adapter;
    protected void setBegin(int begin) {
        this.begin = begin;
    }
    protected int getBegin() {
        return begin;
    }
    LinearLayout top;
    PopupWindow selectPop;  //区域选择性弹窗
    View vPopupWindow;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhui);
        type=1;
        whereFrom=4;
        status=0;
        begin=0;
        initView();
    }
    private  void initView(){

        //如果是代购需要隐藏
            findViewById(R.id.toplayoutselect).setVisibility(View.GONE);
        woFaQi=(Button) findViewById(R.id.wofaqidebtn);
        woFaQi.setOnClickListener(this);
        woCanYu=(Button) findViewById(R.id.wocanyudebtn);
        woCanYu.setOnClickListener(this);
        setBack();

        setCusTomeTitle("追号");

        //获取listview
        listview= (MyRefreshListView) findViewById(R.id.refreshListView);
        listview.setCacheColorHint(Color.TRANSPARENT);
        listview.setFadingEdgeLength(0);
        listview.setonRefreshListener(new MyRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh=true;
                begin=0;
                requestData();
                map.clear();
                map.put("操作类型","下拉刷新数据");
            }});
        listview.setOnItemClickListener(this);
        loadMoreView = getLayoutInflater().inflate(R.layout.new_loadmore, null);
        loadMoreButton = (TextView)loadMoreView.findViewById(R.id.loadMoreButton);
        bottomTishi=(LinearLayout) loadMoreView.findViewById(R.id.bottomtishi);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ismore){
                    loadMoreButton.setText("正在加载中...");   //设置按钮文字
                    loadMoreData();
                    map.clear();
                    map.put("操作类型","加载更多数据");

                }
            }
        });
        listview.addFooterView(loadMoreView);    //设置列表底部视图
        adapter=new GouCaiRecordAdapter(this);
        adapter.setListData(listData);
        listview.setAdapter(adapter);
        loaddata=(RelativeLayout) findViewById(R.id.loaddata);
        failedLayout=(RelativeLayout) findViewById(R.id.failed);
        noDataLayout=(RelativeLayout)findViewById(R.id.nodata);
        failedLayout.setOnClickListener(this);

        startLoadAnim();
        //加载数据...
        loadData();
    }
    private void loadMoreData(){
        setBegin(getBegin() + 10);
        requestData();
    }
    private void loadData(){
        loaddata.setVisibility(View.VISIBLE);
        failedLayout.setVisibility(View.GONE);
        noDataLayout.setVisibility(View.GONE);
        if(!NetUtil.isNetworkAvailable(this)){
            failedLayout.setVisibility(View.VISIBLE);
            loaddata.setVisibility(View.GONE);
        }else
            requestData();
    }
    private  void  requestData(){
        RequestParams params =getRequestParams();
        params.put(URLSuffix.type, type + "");
        params.put(URLSuffix.status, status + "");
        params.put(URLSuffix.firstRow, getBegin() + "");
        params.put(URLSuffix.fetchSize, 11 + "");

        HttpBusinessAPI.post(URLSuffix.MY_SCHEME, params, response);
        uMengTongji();
    }
    //友盟统计
    private  void  uMengTongji(){
        map.clear();
        switch(type){
            case 0:
                map.put("操作类型","全部");
                break;
            case 5:
                map.put("操作类型","代购");
                break;
            case 3:
                map.put("操作类型","我发起的合买");
                break;
            case 1:
                map.put("操作类型","代我发起的追号");
                break;
            case 6:
                map.put("操作类型","我发起的中奖");
                break;
            case 4:
                map.put("操作类型","我参与的合买");
                break;
            case 2:
                map.put("操作类型","我参与的追号");
                break;
            case 7:
                map.put("操作类型","我参与的中奖");
                break;
        }

    }
    HttpRespHandler response = new HttpRespHandler() {

        @Override
        public void onSuccess(String response) {
            super.onSuccess(response);
            //System.out.println("response:"+response);
            listview.setVisibility(View.VISIBLE);
            if(isRefresh)
                listview.onRefreshComplete();
            loaddata.setVisibility(View.GONE);
            if (getBegin() == 0) {
                listData.clear();
//				CacheFactory.getInstance().putJSONArrayWithTime("goucaiData_"+type,  ///缓存数据
//						response, System.currentTimeMillis());
            }
            int showLenth = Math.min(JSON.parseArray(response).size(), 10);
            for (int i = 0; i < showLenth; i++) {// 最后一个不显示

                JSONObject json = JSON.parseArray(response).getJSONObject(i);
                GoucaijiluData data = GoucaijiluData.create(json);

                listData.add(data);
            }
            adapter.notifyDataSetChanged();
            if (response.length() < 11) {
                ismore=false;
                //没有查询到数据
                loadMoreButton.setVisibility(View.GONE);
                if(listData.size()==0){
                    listview.setVisibility(View.GONE);
                    noDataLayout.setVisibility(View.VISIBLE);
                    bottomTishi.setVisibility(View.GONE);
                }else{
                    bottomTishi.setVisibility(View.VISIBLE);
                    noDataLayout.setVisibility(View.GONE);
                }
            } else {
                loadMoreButton.setVisibility(View.VISIBLE);
                noDataLayout.setVisibility(View.GONE);
                bottomTishi.setVisibility(View.GONE);
                ismore=true;
                loadMoreButton.setText("查看更多...");  //恢复按钮文字
            }
        }
//        @Override
//        public void onSuccess(BaseJSONObject response) {
//            super.onSuccess(response);
//            if(isRefresh)
//                listview.onRefreshComplete();
//            loaddata.setVisibility(View.GONE);
//            failedLayout.setVisibility(View.VISIBLE);
//            checkResult(response);
//        }
        @Override
        public void onFailure(Throwable error) {
            super.onFailure(error);
            if(getBegin() == 0&!isRefresh){
                failedLayout.setVisibility(View.VISIBLE);
                noDataLayout.setVisibility(View.GONE);
                loaddata.setVisibility(View.GONE);
            }
            if(isRefresh){
                showToast("刷新数据失败!");
                listview.onRefreshComplete();
            }
            if(ismore)
                loadMoreButton.setText("加载失败,点击重试!");  //恢复按钮文字
        }

    };
    Intent intent= new Intent();
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
        if(position-1>=listData.size())
            return;
        int schemeid=0;
        intent.setClass(this, FanganorderActivity.class);
        schemeid=listData.get(position-1).getSchemeId();
        intent.putExtra("zhongMoney", listData.get(position-1).getPrize());
        intent.putExtra(URLSuffix.schemeId,schemeid);
        intent.putExtra("detail",listData.get(position-1).getLotteryName());
        intent.putExtra("whereFrom", whereFrom);
        startActivity(intent);
        pullUpActivityAnimFromMain();
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
//		    case R.id.ffootballtopbarLayout:
//		    	if(selectPop!=null&&selectPop.isShowing()){
//					 dismiss();
//			     }
//			break;

            case R.id.failed:
                loaddata.setVisibility(View.VISIBLE);
                failedLayout.setVisibility(View.GONE);
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        loadData();
                    }
                }, 500);
                map.put("操作类型","数据加载失败重试");
                break;
            case R.id.wofaqidebtn:
                if(hemaiBtn.isSelected()){
                    whereFrom=2;
                    type=3;
                }else if(zhuiHaoBtn.isSelected()){
                    whereFrom=4;
                    type=1;
                }else if(zhongJiangBtn.isSelected()){
                    type=6;
                    whereFrom=6;
                }
                woFaQi.setSelected(true);
                woCanYu.setSelected(false);
                begin=0;
                loadData();
                break;
            case R.id.wocanyudebtn:
                if(hemaiBtn.isSelected()){
                    whereFrom=3;
                    type=4;
                }else if(zhuiHaoBtn.isSelected()){
                    whereFrom=5;
                    type=2;
                }else if(zhongJiangBtn.isSelected()){
                    whereFrom=7;
                    type=7;
                }
                woFaQi.setSelected(false);
                woCanYu.setSelected(true);
                begin=0;
                loadData();
                break;


            case R.id.button1:
                dismiss();
                if(!allBtn.isSelected()){

                    type=0;
                    whereFrom=8;
                    status=0;
                    begin=0;

                    setBtnStates();
                    loadData();
                }

                break;
            case R.id.button2:
                dismiss();
                if(!daigouBtn.isSelected()){
                    type=5;
                    whereFrom=1;
                    status=0;
                    begin=0;

                    setBtnStates();
                    loadData();
                }

                break;
            case R.id.button3:
                dismiss();
                if(!hemaiBtn.isSelected()){
                    type=3;
                    whereFrom=2;
                    status=0;
                    begin=0;
                    woFaQi.setSelected(true);
                    woCanYu.setSelected(false);
                    setBtnStates();
                    loadData();
                }
                break;
            case R.id.button4:
                dismiss();
                if(!zhuiHaoBtn.isSelected()){
                    type=1;
                    whereFrom=4;
                    status=0;
                    begin=0;
                    woFaQi.setSelected(true);
                    woCanYu.setSelected(false);
                    setBtnStates();
                    loadData();
                }
                break;
            case R.id.button5:
                dismiss();
                if(!zhongJiangBtn.isSelected()){
                    type=6;
                    status=5;
                    whereFrom=6;
                    begin=0;
                    woFaQi.setSelected(true);
                    woCanYu.setSelected(false);
                    setBtnStates();
                    loadData();
                }
                break;
        }
    }
    private PopupWindow getSelectPop(){
        if(vPopupWindow==null){
            vPopupWindow = getLayoutInflater().inflate(
                    R.layout.pop_selectlayout, null, false);
            selectPop = new PopupWindow(vPopupWindow, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            selectPop.setBackgroundDrawable(new BitmapDrawable());
            selectPop.setFocusable(true);
            selectPop.setOutsideTouchable(true);
            selectPop.setOnDismissListener(new PopupWindow.OnDismissListener(){
                @Override
                public void onDismiss() {
                    btnLeft.setSelected(false);

                    top.clearAnimation();
                    img.clearAnimation();
                }});
            top=(LinearLayout) vPopupWindow.findViewById(R.id.layouttopselect);
            top.startAnimation(mShowAction);
            top.setVisibility(View.VISIBLE);
            try {
                selectPop.setAnimationStyle(R.style.SelectPopWindowAnimation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            img=(ImageView) vPopupWindow.findViewById(R.id.imageView1);
            allBtn=(TextView) vPopupWindow.findViewById(R.id.button1);
            allBtn.setOnClickListener(this);
            allBtn.setSelected(true);
            daigouBtn=(TextView) vPopupWindow.findViewById(R.id.button2);
            daigouBtn.setText("代购");
            daigouBtn.setOnClickListener(this);
            hemaiBtn=(TextView) vPopupWindow.findViewById(R.id.button3);
            hemaiBtn.setText("合买");
            hemaiBtn.setOnClickListener(this);
            zhuiHaoBtn=(TextView) vPopupWindow.findViewById(R.id.button4);
            zhuiHaoBtn.setText("追号");
            zhuiHaoBtn.setOnClickListener(this);
            zhongJiangBtn=(TextView) vPopupWindow.findViewById(R.id.button5);
            zhongJiangBtn.setText("中奖");
            zhongJiangBtn.setOnClickListener(this);
            vPopupWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectPop != null&& selectPop.isShowing()) {
                        dismiss();

                    }
                }
            });
        }else{
            top.startAnimation(mShowAction);
            top.setVisibility(View.VISIBLE);
        }
        setBtnStates();
        return selectPop;
    }
    private void  setBtnStates(){
        if(type==0){
            allBtn.setSelected(true);
            daigouBtn.setSelected(false);
            hemaiBtn.setSelected(false);
            zhuiHaoBtn.setSelected(false);
            zhongJiangBtn.setSelected(false);
        }
        else if(type==5){
            daigouBtn.setSelected(true);
            hemaiBtn.setSelected(false);
            zhuiHaoBtn.setSelected(false);
            zhongJiangBtn.setSelected(false);
            allBtn.setSelected(false);
        }
        else if(type==1||type==2){
            daigouBtn.setSelected(false);
            hemaiBtn.setSelected(false);
            zhuiHaoBtn.setSelected(true);
            zhongJiangBtn.setSelected(false);
            allBtn.setSelected(false);
        }
        else if(type==3||type==4){
            daigouBtn.setSelected(false);
            hemaiBtn.setSelected(true);
            zhuiHaoBtn.setSelected(false);
            zhongJiangBtn.setSelected(false);
            allBtn.setSelected(false);
        }else if(type==6||type==7){
            daigouBtn.setSelected(false);
            hemaiBtn.setSelected(false);
            zhuiHaoBtn.setSelected(false);
            zhongJiangBtn.setSelected(true);
            allBtn.setSelected(false);
        }
    }
    private void  dismiss(){
        if(selectPop!=null&&selectPop.isShowing()){
            top.startAnimation(set);
            top.setVisibility(View.INVISIBLE);
            if(img!=null)
                img.startAnimation(am);
            handler.postDelayed(new Runnable(){
                @Override
                public void run() {
                    selectPop.dismiss();
                }
            }, 300);
        }
    }
}
