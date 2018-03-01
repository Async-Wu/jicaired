package com.chengyi.app.home.discover;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.base.BaseFragment;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.IP;
import com.chengyi.app.web.WebViewActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class DisCoverFragment extends BaseFragment implements View.OnClickListener {


    private ListView listView;
    private SimpleAdapter adapter;
    String title[] = {"比 分", "指 数", "联 赛", "资 讯"};
    String url[] = {IP.ZST_M + "/saishi/jzscore?flag=1",
            IP.ZST_M + "saishi/jzindex?flag=1",
            IP.ZST_M + "/saishi/jzleague?flag=1",
            IP.ZST_M + "/zx/newslist?flag=1"};

    String endDate = "2014-07-15";
    private String DATE_TIME_FORMAT = "yyyy-MM-dd";
    SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
    private Calendar cal;
    private boolean isBannerVisiable = false;
    HashMap<String, String> map = new HashMap<String, String>();

    public DisCoverFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dis_cover, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCusTomeTitle(view,"发现");

        String newsUrl = CaipiaoFactory.getInstance(getActivity()).getDiscoveryUrl();
        if (newsUrl != null && !newsUrl.equals("null") && !newsUrl.equals("")) {
            url[3] = CaipiaoFactory.getInstance(getActivity()).getDiscoveryUrl();
        }

        view.findViewById(R.id.ll_back).setVisibility(View.INVISIBLE);

        cal = Calendar.getInstance();
        try {
            cal.setTime(df.parse(endDate));
            long timemiles = cal.getTimeInMillis();

            cal.setTime(new Date());
            long timemilesNow = cal.getTimeInMillis();
            if (timemilesNow < timemiles) {
                isBannerVisiable = true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }




        listView = (ListView) view.findViewById(R.id.discovery_list);
        listView.setCacheColorHint(Color.TRANSPARENT);

        String[] from = {"discovery_listview_text"};
        int[] to = {R.id.discovery_listview_text};

        List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
        for (int i = 0; i < 4; i++) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("discovery_listview_text", title[i]);

            list.add(m);
        }
        adapter = new SimpleAdapter(getContext(), list, R.layout.discovery_listview,
                from, to);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

//                if (position == 3) {
//                    startActivity(new Intent(getContext(), DiscoverActivity.class));
//                    return;
//                }

                Intent intent = new Intent();
                intent.setClass(getContext(), WebViewActivity.class);
                intent.putExtra("url", url[position] + "&appVersion=" + CaipiaoConst.APPVERSION + "&version=" + CaipiaoConst.VERSION
                        + "&requestType=" + CaipiaoConst.REQUESTTYPE);
                intent.putExtra("title", title[position]);
                map.put("操作类型", title[position]);

                if (position == 1 || position == 0 || position == 2) {
                    intent.putExtra("notop", true);
                }
                startActivity(intent);
            }

        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.discovery_banner:
                break;

        }
    }


}
