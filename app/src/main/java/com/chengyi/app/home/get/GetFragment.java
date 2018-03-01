package com.chengyi.app.home.get;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.chengyi.R;
import com.chengyi.app.adapter.DrawNumberAdapter;
import com.chengyi.app.base.BaseFragment;
import com.chengyi.app.listener.IInit;
import com.chengyi.app.listener.INet;
import com.chengyi.app.model.bean.HadLotteryBean;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.caipiao.HadLotteryMode;
import com.chengyi.app.net.api.HomeApi;

import java.util.concurrent.CopyOnWriteArrayList;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  GetFragment extends BaseFragment implements OnItemClickListener, IInit, INet {
    private SwipeRefreshLayout srf_get;
    private ListView listview;
    private RelativeLayout failedLayout,loaddata;
    private CopyOnWriteArrayList<HadLotteryBean> list = new CopyOnWriteArrayList<>();
    private DrawNumberAdapter adapter;

    private Context context;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.new_activity_kaijianggonggao, container, false);

        context = getActivity().getApplicationContext();

        init();

        return view;
    }


    @Override
    public void init() {
        initView();
        loadDate();
    }

    @Override
    public void initView() {
        loaddata= (RelativeLayout) view.findViewById(R.id.loaddata);
        srf_get = (SwipeRefreshLayout) view.findViewById(R.id.srf_get);
        srf_get.setColorSchemeColors(getResources().getColor(R.color.red));
        srf_get.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetch(FIRST);
            }
        });

        view.findViewById(R.id.ll_back).setVisibility(View.INVISIBLE);

        setCusTomeTitle(view,"开奖大厅");


        listview = (ListView) view.findViewById(R.id.refreshListViewtt);
        listview.setCacheColorHint(Color.TRANSPARENT);
        failedLayout = (RelativeLayout) view.findViewById(R.id.failed);
        failedLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                failedLayout.setVisibility(View.GONE);
                fetch(FIRST);
            }
        });
        list = new CopyOnWriteArrayList<>();
        adapter = new DrawNumberAdapter(context);
        adapter.setList(list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void loadDate() {
        fetch(FIRST);
    }

    /**
     * 使用CopyOnWriteArrayList
     *
     * @param tag
     * @param o
     * @see CopyOnWriteArrayList  同步
     */
    @Override
    public void response(int tag, Object o) {
        if (loaddata!=null)loaddata.setVisibility(View.GONE);
        srf_get.setRefreshing(false);
        if (o == null) return;

        if (tag == FIRST) {
            list = ((HadLotteryMode) o).getDrawList();
            for (HadLotteryBean bean : list) {
                if (bean == null||(bean!=null&& CaipiaoFactory.getInstance(getContext()).getCaipiaoById(bean.getLotteryId())==null))
                    list.remove(bean);
            }
            adapter.setList(list);
            adapter.notifyDataSetChanged();

        }

    }

    @Override
    public void fetch(int tag) {
        if (tag == FIRST) {
            loaddata.setVisibility(View.VISIBLE);
            HomeApi.ISSUE_NOTIFY_ALL(getActivity(), this, tag, HadLotteryMode.class, null);
        }
    }




    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        int id = list.get(arg2).getLotteryId();
        Intent intent;
        if (id == 10059) {
            return;
        } else {
            intent = new Intent(context, Activity_HistoryDrawNumber.class);
            intent.putExtra("lotteryId", id);
        }
        startActivity(intent);


    }

}
