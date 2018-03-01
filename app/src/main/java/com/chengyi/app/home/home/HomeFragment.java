package com.chengyi.app.home.home;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chengyi.R;
import com.chengyi.app.base.BaseFragment;
import com.chengyi.app.listener.INet;
import com.chengyi.app.model.bean.LotteyBean;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.home.LotteryMode;
import com.chengyi.app.net.api.HomeApi;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.IP;
import com.chengyi.app.util.L;
import com.chengyi.app.web.WebViewActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class HomeFragment extends BaseFragment implements INet {
    private SwipeRefreshLayout srfRe;
    private ListView elvhome;
    private View vHome;
    private HomeEAdapter adapter;
    private List<HomeMode> modes = new ArrayList<>();
    private LinearLayout llBuy, llGet, llAct;
    private CircleIndicator indicator;

    private ViewPager vpHomeTop;
    private ProgressBar pb;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (vpHomeTop.getCurrentItem() >= vpHomeTop.getAdapter().getCount() - 1) {
                vpHomeTop.setCurrentItem(0, false);
            } else {
                vpHomeTop.setCurrentItem(vpHomeTop.getCurrentItem() + 1, true);
            }
            handler.sendEmptyMessageDelayed(0, 3000);
        }
    };


    private int[] ivs = new int[]{R.drawable.banner, R.drawable.banner00, R.drawable.banner000};
    private List<String> urls = new ArrayList<>();


    public HomeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    private BannerAdapter bannerAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.ll_back).setVisibility(View.INVISIBLE);

        setCusTomeTitle(view, "购彩大厅");
        srfRe = (SwipeRefreshLayout) view.findViewById(R.id.srf_re);
        srfRe.setColorSchemeResources(R.color.red);
        srfRe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetch(FIRST);
            }
        });

        elvhome = (ListView) view.findViewById(R.id.elv_home);
        elvhome.setDividerHeight(0);
        vHome = LayoutInflater.from(getActivity()).inflate(R.layout.item_vp_title, null);
        pb = (ProgressBar) view.findViewById(R.id.pb);
        vpHomeTop = (ViewPager) vHome.findViewById(R.id.vp_home_top);


        llBuy = (LinearLayout) vHome.findViewById(R.id.ll_buy);
        llGet = (LinearLayout) vHome.findViewById(R.id.ll_get);
        llAct = (LinearLayout) vHome.findViewById(R.id.ll_act);
        indicator = (CircleIndicator) vHome.findViewById(R.id.indicator);
        llBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGet) getActivity()).get(2);
            }
        });
        llGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IGet) getActivity()).get(1);
            }
        });
        llAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CaipiaoConst.isHoDs(getActivity())) {
                    startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("url", IP.ZST_M + "/activity/list").putExtra("title", "活动列表").putExtra("hidden", true));
                } else {
                    Toast.makeText(getActivity(), "暂无活动", Toast.LENGTH_SHORT).show();
                }

            }
        });

        fetch(FIRST);


        initBanner();
        elvhome.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case SCROLL_STATE_IDLE:
                        if (absListView.getAdapter().getCount() == absListView.getLastVisiblePosition() + 1) {
                            L.d("over");
                        }
                        break;
                    case SCROLL_STATE_FLING:
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        elvhome.addHeaderView(vHome);
        loadAdapter();

    }

    private void initBanner() {
        bannerAdapter = new BannerAdapter(urls, ivs, getActivity());
        vpHomeTop.setAdapter(bannerAdapter);
//        } else {
//            bannerAdapter.notifyDataSetChanged();
//        }


        vpHomeTop.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {

            }
        });

        indicator.setViewPager(vpHomeTop);
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeMessages(0);

    }

    @Override
    public void onResume() {
        super.onResume();
        handler.removeMessages(0);
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (handler != null) handler.removeMessages(0);
    }

    private void loadAdapter() {

        if (modes == null) return;

        adapter = new HomeEAdapter(modes, getContext());
        elvhome.setAdapter(adapter);
        if (elvhome == null) return;


    }

    private List<HomeMode> uuzi = new ArrayList<>();
    private List<HomeMode> jyji = new ArrayList<>();
    private List<HomeMode> kykl = new ArrayList<>();


    @Override
    public void response(int tag, Object o) {

        pb.setVisibility(View.GONE);
        srfRe.setRefreshing(false);

        if (o == null) return;
        if (tag == FIRST) {
            uuzi.clear();
            jyji.clear();
            kykl.clear();
            modes.clear();
            LotteryMode m = (LotteryMode) o;
            L.d(m.toString());
            urls = m.getBanners();
            initBanner();
            if (m.getShuzilist() != null) {
                List<LotteyBean> shuzilist = new ArrayList<>();
                shuzilist.addAll(m.getShuzilist());
                L.d(urls.get(0).toString());
                if (CaipiaoConst.isSort(getActivity())) {
                    Collections.sort(shuzilist, new Comparator<LotteyBean>() {
                        @Override
                        public int compare(LotteyBean lotteyBean, LotteyBean t1) {
                            return CaipiaoFactory.getInstance(getContext()).getCaipiaoById(lotteyBean.getLotteryId()).getSort() - CaipiaoFactory.getInstance(getContext()).getCaipiaoById(t1.getLotteryId()).getSort();
                        }
                    });
                }

                for (int i = 0; i < shuzilist.size(); i++) {
                    HomeMode homeMode = new HomeMode();
                    if (i == 0) {
                        homeMode.setDesc("数字:");
                        homeMode.setTitle("以小博大  彩渲人生");
                    }
                    if (i % 2 == 0) {
                        Caipiao caipiao = shuzilist.get(i).getCaipiao();
//                        if (!TextUtils.isEmpty(m.getShuzilist().get(i).getMessage()))
//                            caipiao.setMessage(m.getShuzilist().get(i).getMessage());
                        homeMode.setLeftNode(caipiao);

                        if (i + 1 == shuzilist.size()) {
                            Caipiao c = new Caipiao(0);
                            c.setName("");
                            c.setIconResId(android.R.color.transparent);
                            homeMode.setRightNode(c);
                        } else {
                            Caipiao caipiao2 = shuzilist.get(i + 1).getCaipiao();
//                            if (!TextUtils.isEmpty(m.getShuzilist().get(i + 1).getMessage()))
//                                caipiao2.setMessage(m.getShuzilist().get(i + 1).getMessage());
                            homeMode.setRightNode(caipiao2);
                        }
                        uuzi.add(homeMode);
                    }

                }
            }
            if (m.getKuaikailist() != null) {


                List<LotteyBean> kuaikai = new ArrayList<>();
                kuaikai.addAll(m.getKuaikailist());
                if (CaipiaoConst.isSort(getActivity())) {
                    Collections.sort(kuaikai, new Comparator<LotteyBean>() {
                        @Override
                        public int compare(LotteyBean lotteyBean, LotteyBean t1) {
                            return CaipiaoFactory.getInstance(getContext()).getCaipiaoById(lotteyBean.getLotteryId()).getSort() - CaipiaoFactory.getInstance(getContext()).getCaipiaoById(t1.getLotteryId()).getSort();
                        }
                    });

                }


                for (int i = 0; i < kuaikai.size(); i++) {
                    HomeMode homeMode = new HomeMode();
                    if (i == 0) {
                        homeMode.setDesc("快开:");
                        homeMode.setTitle("十分钟开奖 简单易中");
                    }
                    if (i % 2 == 0) {
                        Caipiao caipiao = kuaikai.get(i).getCaipiao();
//                        if (!TextUtils.isEmpty(m.getKuaikailist().get(i).getMessage()))
//                            caipiao.setMessage(m.getKuaikailist().get(i).getMessage());
                        homeMode.setLeftNode(caipiao);
                        if (i + 1 == kuaikai.size()) {
                            Caipiao c = new Caipiao(0);
                            c.setName("");
                            c.setIconResId(android.R.color.transparent);
                            homeMode.setRightNode(c);
                        } else {
                            Caipiao caipiao2 = kuaikai.get(i + 1).getCaipiao();
//                            if (!TextUtils.isEmpty(m.getKuaikailist().get(i + 1).getMessage()))
//                                caipiao2.setMessage(m.getKuaikailist().get(i + 1).getMessage());
                            homeMode.setRightNode(caipiao2);
                        }
                        kykl.add(homeMode);
                    }
                }

            }


            if (m.getJingjilist() != null) {


                List<LotteyBean> jingjilist = new ArrayList<>();
                jingjilist.addAll(m.getJingjilist());
                if (CaipiaoConst.isSort(getActivity())) {
                    Collections.sort(jingjilist, new Comparator<LotteyBean>() {
                        @Override
                        public int compare(LotteyBean lotteyBean, LotteyBean t1) {
                            return CaipiaoFactory.getInstance(getContext()).getCaipiaoById(lotteyBean.getLotteryId()).getSort() - CaipiaoFactory.getInstance(getContext()).getCaipiaoById(t1.getLotteryId()).getSort();
                        }
                    });
                }


                for (int i = 0; i < m.getJingjilist().size(); i++) {
                    HomeMode homeMode = new HomeMode();

                    if (i == 0) {

                        homeMode.setDesc("竞技:");
                        homeMode.setTitle("返奖率最高73%");


                    }

                    if (i % 2 == 0) {
                        Caipiao caipiao = jingjilist.get(i).getCaipiao();
//                        if (!TextUtils.isEmpty(m.getJingjilist().get(i).getMessage()))
//                            caipiao.setMessage(m.getJingjilist().get(i).getMessage());
                        homeMode.setLeftNode(caipiao);
                        if (i + 1 == jingjilist.size()) {
                            Caipiao c = new Caipiao(0);
                            c.setName("");
                            c.setIconResId(android.R.color.transparent);
                            homeMode.setRightNode(c);
                        } else {
                            Caipiao caipiao2 = jingjilist.get(i + 1).getCaipiao();
//                            if (!TextUtils.isEmpty(m.getJingjilist().get(1 + i).getMessage()))
//                                caipiao2.setMessage(m.getJingjilist().get(i + 1).getMessage());
                            homeMode.setRightNode(caipiao2);
                        }
                        jyji.add(homeMode);
                    }
                }

            }


            modes.addAll(jyji);
            modes.addAll(kykl);
            modes.addAll(uuzi);
            loadAdapter();


        }


    }

    @Override
    public void fetch(int tag) {
        if (tag == FIRST) {
            pb.setVisibility(View.VISIBLE);
            Map<String, String> map = new HashMap<>();
            map.put("listType", "0");
            HomeApi.LOTTERY_LIST(getActivity(), this, tag, LotteryMode.class, map);
        }
    }


    public interface IGet {
        void get(int tag);
    }
}
