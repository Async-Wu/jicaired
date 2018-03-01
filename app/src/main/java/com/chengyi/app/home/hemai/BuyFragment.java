package com.chengyi.app.home.hemai;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.base.BaseFragment;
import com.chengyi.app.jingji.six.PopMode;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.model.HemaiListData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.pop.Title_pop;
import com.chengyi.app.user.info.FanganorderActivity;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.YOUMENG_EVENT;
import com.chengyi.app.util.net.NetUtil;

import com.chengyi.app.view.scoller.MyRefreshListView;

import java.util.*;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BuyFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, Title_pop.IGetDate {
    private LinearLayout llPop, llPopBuy;
    private ImageView ivAnim;


    private TextView caipiaoname, wanfaname;
    private RelativeLayout loaddata, noDataLayout;
    private LinearLayout leftbtnmenu, mainlayout;
    private ImageView buttonjindu, buttonzongjine, buttonshengyu, buttonredu;
    List<HemaiListData> list = new ArrayList<HemaiListData>();
    private TextView searchBtn;
    HemaiAdapter adapter;
    RelativeLayout failedLayout;
    MyRefreshListView listview;
    boolean isRefresh = false;//是否下拉刷新
    private TextView loadMoreButton;
    private boolean ismore = true;//是否可以加载更多
    private int begin = 0;
    private int flag = 0;//0代表默认乱序，1进度升序，2进度降序，3金额升序，4金额降序，5，剩余升序，6剩余降序，7，热度升序，8，热度降序
    private int lotteryId = 0;
    SearchView searchedit;
    boolean isSearch = false;

    private String selectDate = "全部";
    private PopupWindow popSearch;

    protected void setBegin(int begin) {
        this.begin = begin;
    }

    protected int getBegin() {
        return begin;
    }

    private View loadMoreView;
    Intent intent;
    HashMap<String, String> map = new HashMap<String, String>();

    public BuyFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buy, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        intent = new Intent();
        initView(view);
    }


    private void initView(View view) {
        view.findViewById(R.id.ll_back).setOnClickListener(this);
        view.findViewById(R.id.ffootballtopbarLayout).setOnClickListener(this);
        ivAnim = (ImageView) view.findViewById(R.id.iv_anim);
        initPop();
        llPop = (LinearLayout) view.findViewById(R.id.ll_pop);
        llPopBuy = (LinearLayout) view.findViewById(R.id.ll_pop_buy);
        searchBtn = (TextView) view.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);
        leftbtnmenu = (LinearLayout) view.findViewById(R.id.ll_back);
        wanfaname = (TextView) view.findViewById(R.id.wanfaname);

        leftbtnmenu.setOnClickListener(this);
        caipiaoname = (TextView) view.findViewById(R.id.caipiaoname);

        loaddata = (RelativeLayout) view.findViewById(R.id.loaddata);
        mainlayout = (LinearLayout) view.findViewById(R.id.mainlayout);
        buttonjindu = (ImageView) view.findViewById(R.id.buttonjindu);
        buttonjindu.setOnClickListener(this);
        buttonzongjine = (ImageView) view.findViewById(R.id.buttonzongjine);
        buttonzongjine.setOnClickListener(this);
        buttonshengyu = (ImageView) view.findViewById(R.id.buttonshengyu);
        buttonshengyu.setOnClickListener(this);
        buttonredu = (ImageView) view.findViewById(R.id.buttonredu);
        buttonredu.setOnClickListener(this);

        searchedit = ( SearchView) view.findViewById(R.id.searchedit);

        searchedit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER){
                    String s=searchedit.getQuery().toString().trim();
                    if (TextUtils.isEmpty(s)) {
                        Toast.makeText(getActivity(), "请输入用户名", Toast.LENGTH_SHORT).show();
                    } else {
                        llPopBuy.setVisibility(View.GONE);
                        searchData();
                    }
                    return true;
                }
                return false;
            }
        });











        buttonjindu.setOnClickListener(this);
        buttonzongjine = (ImageView) view.findViewById(R.id.buttonzongjine);
        buttonzongjine.setOnClickListener(this);
        buttonshengyu = (ImageView) view.findViewById(R.id.buttonshengyu);
        buttonshengyu.setOnClickListener(this);
        buttonredu = (ImageView) view.findViewById(R.id.buttonredu);
        buttonredu.setOnClickListener(this);        //获取listview
        listview = (MyRefreshListView) view.findViewById(R.id.pulllayout);
        listview.setCacheColorHint(Color.TRANSPARENT);
        listview.setFadingEdgeLength(0);
        listview.setDividerHeight(0);
        loadMoreView = getActivity().getLayoutInflater().inflate(R.layout.new_loadmore, null);
        loadMoreButton = (TextView) loadMoreView.findViewById(R.id.loadMoreButton);
        loadMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ismore) {
                    loadMoreButton.setText("正在加载中...");   //设置按钮文字
                    loadMoreData();
                    map.clear();
                    map.put("操作类型", "加载更多");
                    CaipiaoUtil.youmengEvent(getContext(), YOUMENG_EVENT.new_hemaicenter, map);
                }
            }
        });
        listview.addFooterView(loadMoreView);    //设置列表底部视图
        adapter = new HemaiAdapter(getActivity());
        adapter.setList(list);
        listview.setAdapter(adapter);
        listview.setonRefreshListener(new MyRefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                begin = 0;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        requestData();
                    }
                });


            }
        });
        listview.setOnItemClickListener(this);
        failedLayout = (RelativeLayout) view.findViewById(R.id.failed);
        noDataLayout = (RelativeLayout) view.findViewById(R.id.nodata);
        failedLayout.setOnClickListener(this);


        caipiaoname.setText("合买" + "-");

        if (TextUtils.isEmpty(selectDate))
            wanfaname.setText("全部");
        else
            wanfaname.setText(selectDate);


        llPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        loadData();
    }


    private void initPop() {
        pop_list = new ArrayList<>();

        PopMode pop = new PopMode();
        pop.setDate("全部");
        if (selectDate.equals(pop.getDate().replace("期", ""))) {
            pop.setSelect(true);
        } else {
            pop.setSelect(false);
        }
        pop_list.add(pop);

        for (int s = 0; s < CaipiaoFactory.getInstance(getContext()).getCaipiaoList().size(); s++) {


            PopMode popMode = new PopMode();
            popMode.setDate(CaipiaoFactory.getInstance(getContext()).getCaipiaoList().get(s).getName());
            popMode.setIssuId(s + 1);
            if (selectDate.equals(popMode.getDate())) {
                popMode.setSelect(true);
            } else {
                popMode.setSelect(false);
            }
            pop_list.add(popMode);
        }


    }


    private void loadMoreData() {
        setBegin(getBegin() + 10);
        requestData();
    }

    private void loadData() {
        handler.post(new Runnable() {
            @Override
            public void run() {

                failedLayout.setVisibility(View.GONE);
                loaddata.setVisibility(View.VISIBLE);
                mainlayout.setVisibility(View.GONE);

                if (!NetUtil.isNetworkAvailable(getActivity())) {
                    failedLayout.setVisibility(View.VISIBLE);
                    loaddata.setVisibility(View.GONE);
                    mainlayout.setVisibility(View.GONE);
                } else
                    requestData();
            }
        });

    }

    Comparator<HemaiListData> jinduUp = new Comparator<HemaiListData>() {
        @Override
        public int compare(HemaiListData lhs, HemaiListData rhs) {

            return rhs.getJindu() - lhs.getJindu();
        }
    };
    Comparator<HemaiListData> jinduDown = new Comparator<HemaiListData>() {
        @Override
        public int compare(HemaiListData lhs, HemaiListData rhs) {

            return lhs.getJindu() - rhs.getJindu();
        }
    };
    Comparator<HemaiListData> jineUp = new Comparator<HemaiListData>() {
        @Override
        public int compare(HemaiListData lhs, HemaiListData rhs) {

            return (int) rhs.getSchemeAmount() - (int) lhs.getSchemeAmount();
        }
    };
    Comparator<HemaiListData> jineDown = new Comparator<HemaiListData>() {
        @Override
        public int compare(HemaiListData lhs, HemaiListData rhs) {

            return (int) lhs.getSchemeAmount() - (int) rhs.getSchemeAmount();
        }
    };
    Comparator<HemaiListData> shengyuUp = new Comparator<HemaiListData>() {
        @Override
        public int compare(HemaiListData lhs, HemaiListData rhs) {

            return (int) (rhs.getRemainAmount() - lhs.getRemainAmount());
        }
    };
    Comparator<HemaiListData> shengyuDown = new Comparator<HemaiListData>() {
        @Override
        public int compare(HemaiListData lhs, HemaiListData rhs) {

            return (int) (lhs.getRemainAmount() - rhs.getRemainAmount());
        }
    };
    Comparator<HemaiListData> joinUp = new Comparator<HemaiListData>() {
        @Override
        public int compare(HemaiListData lhs, HemaiListData rhs) {

            return rhs.getPersonCount() - lhs.getPersonCount();
        }
    };
    Comparator<HemaiListData> joinDown = new Comparator<HemaiListData>() {
        @Override
        public int compare(HemaiListData lhs, HemaiListData rhs) {

            return lhs.getPersonCount() - rhs.getPersonCount();
        }
    };
    List<HemaiListData> listTemp = new ArrayList<HemaiListData>();

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


        int schemeid = 0;
        if (flag == 0)
            schemeid = list.get(position - 1).getSchemeId();
        else
            schemeid = listTemp.get(position - 1).getSchemeId();


        if (!TextUtils.isEmpty(getSession())) {
            int index = list.get(position - 1).getProgress().indexOf("+");
            if (index != -1) {
                String[] text = list.get(position - 1).getProgress().split("\\+");

                Integer integer = Integer.parseInt(text[0].replace("%", ""));
                intent.putExtra("first", integer + "");
                intent.putExtra("second", text[1]);
            } else {

                Integer integer = Integer.parseInt(list.get(position - 1).getProgress().replace("%", ""));

                intent.putExtra("first", integer + "");
//                intent.putExtra("second",listTemp.get(position-1));
            }
            intent.putExtra("lotteryId", list.get(position - 1).getLotteryId());


            intent.setClass(getContext(), FanganorderActivity.class);
        } else {
            intent.setClass(getContext(), LoginActivity.class);
        }
        intent.putExtra(URLSuffix.schemeId, schemeid);
        intent.putExtra("whereFrom", 0);
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        map.clear();
        if (v.getId() == R.id.failed) {
            failedLayout.setVisibility(View.GONE);
            loaddata.setVisibility(View.VISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadData();
                }
            }, 500);
            return;
        }


        if (v.getId() == R.id.searchBtn) {
            llPopBuy.setVisibility(View.GONE);
            searchedit.setFocusable(true);
            return;
        }


        if (v.getId() == R.id.ll_back) {
            llPopBuy.setVisibility(View.VISIBLE);
//            dismiss();
//            if (popSearch != null && popSearch.isShowing()) {
//                hideSearch();
//            } else {
//                getSearchPop().showAsDropDown(getActivity().findViewById(R.id.ffootballtopbarLayout));
//            }
            return;
        }

        if (v.getId() == R.id.ffootballtopbarLayout) {

            if (popPop != null && popPop.isShowing()) {
                dismiss();
            } else {
                AppUtil.startRotateAnim(ivAnim);

                showPop(new ImageView[]{ivAnim}).showAsDropDown(getActivity().findViewById(R.id.ffootballtopbarLayout));

            }

            return;
        }
        switch (v.getId()) {
            case R.id.buttonjindu:
                buttonzongjine.setImageResource((R.drawable.hmzongjine));
                buttonshengyu.setImageResource(R.drawable.hmshengyu);
                buttonredu.setImageResource(R.drawable.hmredu);
//			buttonjindu.setSelected(true);
                if (!list.isEmpty()) {
                    if (flag == 2) {
                        flag = 0;
                        buttonjindu.setImageResource(R.drawable.hmjindu);
                    } else if (flag != 1) {
                        Collections.sort(listTemp, jinduUp);
                        flag = 1;
                        buttonjindu.setImageResource(R.drawable.hmjindupress);
                    } else if (flag == 1) {
                        Collections.sort(listTemp, jinduDown);
                        flag = 2;
                        buttonjindu.setImageResource(R.drawable.hmjindupress1);
                    }
                }

                CaipiaoUtil.youmengEvent(getActivity(), YOUMENG_EVENT.new_hemaicenter, map);
                break;
            case R.id.buttonzongjine:
//			buttonzongjine.setSelected(true);
                buttonshengyu.setImageResource(R.drawable.hmshengyu);
                buttonredu.setImageResource(R.drawable.hmredu);
                buttonjindu.setImageResource(R.drawable.hmjindu);
                if (!list.isEmpty()) {
                    if (flag == 4) {
                        flag = 0;
                        buttonzongjine.setImageResource(R.drawable.hmzongjine);
                    } else if (flag != 3) {
                        Collections.sort(listTemp, jineUp);
                        flag = 3;
                        buttonzongjine.setImageResource(R.drawable.hmzongjinepress);
                    } else if (flag == 3) {
                        Collections.sort(listTemp, jineDown);
                        flag = 4;
                        buttonzongjine.setImageResource(R.drawable.hmzongjinepress1);
                    }
                }
                map.put("操作类型", "按总金额排序");
                CaipiaoUtil.youmengEvent(getContext(), YOUMENG_EVENT.new_hemaicenter, map);
                break;
            case R.id.buttonshengyu:
                buttonzongjine.setImageResource(R.drawable.hmzongjine);

                buttonredu.setImageResource(R.drawable.hmredu);
                buttonjindu.setImageResource(R.drawable.hmjindu);
                if (!list.isEmpty()) {
                    if (flag == 6) {
                        flag = 0;
                        buttonshengyu.setImageResource(R.drawable.hmshengyu);
                    } else if (flag != 5) {
                        Collections.sort(listTemp, shengyuUp);
                        flag = 5;
                        buttonshengyu.setImageResource(R.drawable.hmshengyupress);
                    } else if (flag == 5) {
                        Collections.sort(listTemp, shengyuDown);
                        flag = 6;
                        buttonshengyu.setImageResource(R.drawable.hmshengyupress1);
                    }
                }
                CaipiaoUtil.youmengEvent(getContext(), YOUMENG_EVENT.new_hemaicenter, map);
                break;
            case R.id.buttonredu:
                buttonzongjine.setImageResource(R.drawable.hmzongjine);
                buttonshengyu.setImageResource(R.drawable.hmshengyu);
//			buttonredu.setSelected(true);
                buttonjindu.setImageResource(R.drawable.hmjindu);
                if (!list.isEmpty()) {
                    if (flag == 8) {
                        flag = 0;
                        buttonredu.setImageResource(R.drawable.hmredu);
                    } else if (flag != 7) {
                        Collections.sort(listTemp, joinUp);
                        flag = 7;
                        buttonredu.setImageResource(R.drawable.hmredupress);
                    } else if (flag == 7) {
                        Collections.sort(listTemp, joinDown);
                        flag = 8;
                        buttonredu.setImageResource(R.drawable.hmredupress1);
                    }
                }
                break;
        }
        if (flag == 0)
            adapter.setList(list);
        else
            adapter.setList(listTemp);
        adapter.notifyDataSetChanged();
    }

    /**
     * 默认加载全部彩种的数据，默认排序是按照战绩
     */
    private void requestData() {

        RequestParams params = getRequestParams();
        params.put("lotteryId", lotteryId + "");
        params.put(URLSuffix.firstRow, getBegin() + "");
        params.put(URLSuffix.fetchSize, 11 + "");
        HttpBusinessAPI.post(URLSuffix.SCHEME_HM, params, ResponseHandler);
    }

    HttpRespHandler ResponseHandler = new HttpRespHandler() {
        @Override
        public void onFailure(Throwable error, String s) {

            super.onFailure(error);
            failedLayout.setVisibility(View.VISIBLE);
            loaddata.setVisibility(View.GONE);
            mainlayout.setVisibility(View.GONE);
            if (isRefresh)
                listview.onRefreshComplete();
            if (ismore)
                loadMoreButton.setText("加载失败,点击重试!");  //恢复按钮文字
        }

        @Override
        public void onSuccess(String r) {
            if (isRefresh)
                listview.onRefreshComplete();
            loaddata.setVisibility(View.GONE);
            mainlayout.setVisibility(View.VISIBLE);
            failedLayout.setVisibility(View.GONE);
            if (getBegin() == 0) {
                list.clear();
                listTemp.clear();

            }

            JSONArray response = JSON.parseArray(r);

            int showLenth = Math.min(response.size(), 10);
            for (int i = 0; i < showLenth; i++) {// 最后一个不显示
                JSONObject json = response.getJSONObject(i);
                HemaiListData data = HemaiListData.create(json);
                boolean exist = false;
                for (HemaiListData temp : list) {
                    if (data.getSchemeId() == temp.getSchemeId()) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {// 防止数据重复
                    list.add(data);
                    listTemp.add(data);
                }
            }
            if (flag != 0) {
                switch (flag) {
                    case 1:
                        Collections.sort(listTemp, jinduUp);
                        break;
                    case 2:
                        Collections.sort(listTemp, jinduDown);
                        break;
                    case 3:
                        Collections.sort(listTemp, jineUp);
                        break;
                    case 4:
                        Collections.sort(listTemp, jineDown);
                        break;
                    case 5:
                        Collections.sort(listTemp, shengyuUp);
                        break;
                    case 6:
                        Collections.sort(listTemp, shengyuDown);
                        break;
                    case 7:
                        Collections.sort(listTemp, joinUp);
                        break;
                    case 8:
                        Collections.sort(listTemp, joinDown);
                        break;
                }
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (flag != 0)
                        adapter.setList(listTemp);
                    else
                        adapter.setList(list);
                    adapter.notifyDataSetChanged();
                }
            }, CaipiaoConst.DELAY);
            noDataLayout.setVisibility(View.GONE);
            if (response.size() < 11) {
                ismore = false;
                if (list.size() == 0) {
                    noDataLayout.setVisibility(View.VISIBLE);
                }
                loadMoreButton.setText("");
            } else {
                ismore = true;
                loadMoreButton.setText("查看更多...");  //恢复按钮文字
            }
        }

    };


    View vPopSearch;


    private PopupWindow getSearchPop() {
        if (vPopSearch == null) {
            vPopSearch = getActivity().getLayoutInflater().inflate(R.layout.pop_search, null, false);
            vPopSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            popSearch = new PopupWindow(vPopSearch, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popSearch.setBackgroundDrawable(new BitmapDrawable());
            popSearch.setFocusable(true);
            popSearch.setOutsideTouchable(true);



        } else {

        }


        return popSearch;

    }


    /**
     * 搜索数据
     */
    private void searchData() {
        String s = searchedit.getQuery().toString();
        if (TextUtils.isEmpty(s)) {
            this.showToast("请输入用户名");
            return;
        }

        setBegin(0);
        isSearch = true;
        wanfaname.setText("搜索");
        failedLayout.setVisibility(View.GONE);
        loaddata.setVisibility(View.VISIBLE);
        mainlayout.setVisibility(View.GONE);
        RequestParams params = getRequestParams();
        params.put("lotteryId", "0");
        params.put(URLSuffix.firstRow, "0");
        params.put(URLSuffix.keyWord, searchedit.getQuery().toString().trim());
        HttpBusinessAPI.post(URLSuffix.SCHEME_HM, params, ResponseHandler);

    }


    private void dismiss() {
        if (popPop != null && popPop.isShowing()) {
            popPop.dismiss();
            AppUtil.startResRotateAnim(ivAnim);

        }
    }

    private void hideSearch() {
        if (popSearch != null && popSearch.isShowing())
            popSearch.dismiss();


    }


    @Override
    public void getDate(List<PopMode> pop) {
        dismiss();
        for (PopMode mode : pop) {

            if (mode.isSelect()) {
                selectDate = mode.getDate().replaceAll("期", "");
                wanfaname.setText(selectDate);
                if (selectDate.equals("排列3/5")) {
                    lotteryId = 10024;
                } else if (selectDate.equals("全部")) {
                    lotteryId = 0;
                } else if (selectDate.equals("幸运赛车")) {
                    lotteryId = 10067;
                } else {
                    Caipiao cp = CaipiaoFactory.getInstance(getActivity()).getCaipiaobyName(selectDate);
                    if (cp != null)
                        lotteryId = cp.getId();
                    else
                        lotteryId = 0;
                }
                mainlayout.setVisibility(View.GONE);
                loaddata.setVisibility(View.VISIBLE);
                begin = 0;
                loadData();
                isSearch = false;

            }
        }
    }


    private void reserImg(ImageView imageView, View v) {
//        ivProcess.setSelected(false);
//        ivJnee.setSelected(false);
//        ivSyu.setSelected(false);
//        ivHot.setSelected(false);
//        imageView.setSelected(true);
//
//        vProcess.setVisibility(View.INVISIBLE);
//        vJnee.setVisibility(View.INVISIBLE);
//        vSyu.setVisibility(View.INVISIBLE);
//        vHot.setVisibility(View.INVISIBLE);
//        v.setVisibility(View.VISIBLE);


    }

}
