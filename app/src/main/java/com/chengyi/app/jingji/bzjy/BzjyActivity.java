package com.chengyi.app.jingji.bzjy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.util.IP;
import com.chengyi.app.util.L;
import com.chengyi.app.view.scoller.MyExpandableListView;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */

public class BzjyActivity extends BaseActivity implements BzjyAdapter.IMode {
    private MyExpandableListView elvBzjy;
    private ImageView ivAnim;

    private TextView numbisai;
    private ImageView iv_wanfa, iv_select;
    private RelativeLayout loaddata;
    private LinearLayout ll_back_tv;
    private RelativeLayout rlPop;

    private BzjyAdapter adapter;
    private List<DataBean> data;
    private TextView qingkong;
    private View vPopupWindow;


    private TextView btn1, btn2, btn3, btn4, btn5;

    private int type;

    private TextView ensurebtn;
    private String num="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bzjy);
        init();
    }

    private void init() {
        initView();

        loaddate();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (data == null) return;
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).getMatches().size(); j++) {

                if (CaipiaoApplication.getInstance().getBjdcdelet().contains(data.get(i).getMatches().get(j).getId())) {
                    data.get(i).getMatches().get(j).clearselect();
                }

            }
        }
        if (adapter != null)
            adapter.notifyDataSetChanged();
        num = cacluBzjy(data);
        initBottom();


    }
    private  TextView tvNum;

    private void initBottom() {

        if (num.equals("0")){
            tvNum.setText("2");
            numbisai.setText("请选择");
        }else {
            tvNum.setText(num);
            numbisai.setText("已选择");
        }
    }

    private void loaddate() {
        if (loaddata != null) loaddata.setVisibility(View.VISIBLE);
        OkHttpUtil.postSubmitForm(IP.IP + "/lottery/bjdc.htm", null, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {

        L.i("test", json);
                try {
                    BzjyMode bzjyMode = JSONObject.parseObject(json, BzjyMode.class);

                    data = bzjyMode.getData();
                    loadaadapter(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(BzjyActivity.this, "暂无数据", Toast.LENGTH_SHORT).show();
                    if (loaddata != null) loaddata.setVisibility(View.GONE);
                }

            }


            @Override
            public void onFailure(String url, String error) {
                L.d("test", error);
                hideLoading();
            }
        });
    }
    private void loadaadapter(){
        loadaadapter(false);
    }
    private void loadaadapter(boolean flag) {
        if (loaddata != null) loaddata.setVisibility(View.GONE);
        if (!flag){
            dismiss();
        }

        elvBzjy.onRefreshComplete();
        data = resetDate(data);


        adapter = new BzjyAdapter(resetDate(data), BzjyActivity.this, type);
        elvBzjy.setAdapter(adapter);
        if ((data) == null) {
            return;
        }
        for (int i = 0; i < (data).size(); i++) {
            elvBzjy.expandGroup(i);
        }
    }

    private List<DataBean> resetDate(List<DataBean> data) {
        List<DataBean> d = new ArrayList<>();

        if (data == null) data = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {

            DataBean bean = new DataBean();
            bean.setIssueId(data.get(i).getIssueId());
            bean.setDayKey(data.get(i).getDayKey());
            bean.setDayOfWeekStr(data.get(i).getDayOfWeekStr());

            List<MatchBean> matchBeen = new ArrayList<>();
            for (int j = 0; j < data.get(i).getMatches().size(); j++) {


                if (type == 0) {
                    if (data.get(i).getMatches().get(j).getRqSpfStatus() == 0) {
                        matchBeen.add(data.get(i).getMatches().get(j));
                    }

                }
                if (type == 1) {
                    if (data.get(i).getMatches().get(j).getZjqStatus() == 0) {
                        matchBeen.add(data.get(i).getMatches().get(j));
                    }

                }
                if (type == 2) {
                    if (data.get(i).getMatches().get(j).getSxdsStatus() == 0) {
                        matchBeen.add(data.get(i).getMatches().get(j));
                    }

                }
                if (type == 3) {
                    if (data.get(i).getMatches().get(j).getBfStatus() == 0) {
                        matchBeen.add(data.get(i).getMatches().get(j));
                    }

                }
                if (type == 4) {
                    if (data.get(i).getMatches().get(j).getBqcStatus() == 0) {
                        matchBeen.add(data.get(i).getMatches().get(j));
                    }

                }


            }

            bean.setMatches(matchBeen);
            bean.setMatchCount(matchBeen.size());
            if (bean == null || bean.getMatches() == null || bean.getMatches().size() <= 0) continue;
            d.add(bean);

        }

//排序
        Collections.sort(d, new Comparator<DataBean>() {
            @Override
            public int compare(DataBean d1, DataBean d2) {

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date1;
                Date date2;
                try {
                    date1 = sdf.parse(d1.getDayKey());
                    date2 = sdf.parse(d2.getDayKey());
                } catch (Exception e) {
                    date1 = new Date();
                    date2 = new Date();
                }
                return (int) (date1.getTime() - date2.getTime());
            }
        });


        return d;


    }

    @Override
    public void hideSoftKeyboard() {
        super.hideSoftKeyboard();
    }

    private void initView() {
        ivAnim = (ImageView) findViewById(R.id.iv_anim);
        rlPop = (RelativeLayout) findViewById(R.id.rl_pop);
        findViewById(R.id.footballffootballtopbarLayout).setOnClickListener(this);
        rlPop.setOnClickListener(this);
        loaddata = (RelativeLayout) findViewById(R.id.loaddata);
        ll_back_tv = (LinearLayout) findViewById(R.id.footballffootballtopbarLayout);
        ll_back_tv.setOnClickListener(this);
        tvNum= (TextView) findViewById(R.id.numbisai);

        iv_select = (ImageView) findViewById(R.id.iv_select);
        iv_select.setVisibility(View.GONE);
        iv_wanfa = (ImageView) findViewById(R.id.iv_wanfa);
        iv_wanfa.setOnClickListener(this);
        setBack();


        ensurebtn = (TextView) findViewById(R.id.ensurebtn);
        ensurebtn.setOnClickListener(this);
        numbisai = (TextView) findViewById(R.id.yixuantishi);
        elvBzjy = (MyExpandableListView) findViewById(R.id.elv_bzjy);
        qingkong = (TextView) findViewById(R.id.qingkong);


        setCusTomeTitle("北京单场-让球胜平负");
        vPopupWindow = getLayoutInflater().inflate(
                R.layout.bd_pop, null, false);


        btn1 = (TextView) vPopupWindow.findViewById(R.id.btn1);
        btn2 = (TextView) vPopupWindow.findViewById(R.id.btn2);
        btn3 = (TextView) vPopupWindow.findViewById(R.id.btn3);
        btn4 = (TextView) vPopupWindow.findViewById(R.id.btn4);
        btn5 = (TextView) vPopupWindow.findViewById(R.id.btn5);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        wanfaBtnList.add(btn1);
        wanfaBtnList.add(btn2);
        wanfaBtnList.add(btn3);
        wanfaBtnList.add(btn4);
        wanfaBtnList.add(btn5);



        qingkong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = "0";
                L.i("test", "---------------------------------");
                if (adapter != null) {
                    adapter.clearhadget();
                }
            }
        });
        elvBzjy.setonRefreshListener(new MyExpandableListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddate();
            }
        });
    }

    private ArrayList<DataBean> bean;

    @Override
    public void setModel(List<DataBean> been) {
        this.bean = (ArrayList<DataBean>) been;
        L.i("test", been.toString());
        num = cacluBzjy(been);
     initBottom();
    }


    private List<MatchBean> getHasSelectMatch(ArrayList<DataBean> beans) {

        List<MatchBean> matchBeen = new ArrayList<>();


        for (DataBean dataBean : beans) {

            if (dataBean.getMatches() != null) {
                for (MatchBean mb : dataBean.getMatches()) {


                    if (mb.isSelect()){
                        matchBeen.add(mb);
                    }
                }
            }

        }

        return matchBeen;


    }


    /**
     * 计算 场次
     *
     * @param been
     * @return
     */

    int total;

    private ArrayList<DataBean> getSelect(List<DataBean> been) {
        ArrayList<DataBean> m = new ArrayList<>();

        if (cacluBzjy((bean)).equals("0")) {
            return null;
        }

        if (type == 0) {
            for (int i = 0; i < been.size(); i++) {

                for (int j = 0; j < been.get(i).getMatches().size(); j++) {
                    if (been.get(i).getMatches().get(j).getspf()) {
                        m.add(been.get(i));
                    }

                }
            }

        } else if (type == 1) {
            for (int i = 0; i < been.size(); i++) {

                for (int j = 0; j < been.get(i).getMatches().size(); j++) {
                    if (been.get(i).getMatches().get(j).getSp().zjq()) {
                        m.add(been.get(i));
                    }

                }
            }
        } else if (type == 2) {
            for (int i = 0; i < been.size(); i++) {

                for (int j = 0; j < been.get(i).getMatches().size(); j++) {
                    if (been.get(i).getMatches().get(j).getSp().ds()) {
                        m.add(been.get(i));
                    }

                }
            }
        } else if (type == 3) {
            for (int i = 0; i < been.size(); i++) {

                for (int j = 0; j < been.get(i).getMatches().size(); j++) {
                    if (been.get(i).getMatches().get(j).getSp().bifen()) {
                        m.add(been.get(i));
                    }

                }
            }
        } else if (type == 4) {
            for (int i = 0; i < been.size(); i++) {

                for (int j = 0; j < been.get(i).getMatches().size(); j++) {
                    if (been.get(i).getMatches().get(j).getSp().bqc()) {
                        m.add(been.get(i));
                    }

                }
            }
        }


        return m;

    }


    private String cacluBzjy(List<DataBean> been) {

        total = 0;


        for (int i = 0; i < been.size(); i++) {

            for (int j = 0; j < been.get(i).getMatches().size(); j++) {
                if (been.get(i).getMatches().get(j).ishad()) {
                    total++;
                }

            }
        }

        L.i("test", String.valueOf(total));

        return String.valueOf(total);
    }


    private void clearDate() {
initBottom();
        if (data != null) {

            for (int i = 0; i < data.size(); i++) {
                data.get(i).clearselect();
            }
            if (adapter != null)
                adapter.notifyDataSetChanged();

        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_pop:
                dismiss();
                break;


            case R.id.iv_wanfa:
                Toast.makeText(this, "暂无该玩法介绍", Toast.LENGTH_SHORT).show();
//                moveToWanFa();
                break;

            case R.id.qingkong:
                clearDate();
                break;
            case  R.id.footballffootballtopbarLayout:
            case R.id.tv_title:

                ll_back_tv.setSelected(!ll_back_tv.isSelected());
                if (rlPop.getVisibility() == View.VISIBLE) {
                    dismiss();
                } else {
                    AppUtil.startRotateAnim(ivAnim);
                    getSelectPop();
                }
                break;
            case R.id.btn1:
                selectBtn(btn1);
                clearDate();
                setCusTomeTitle("北京单场-" + "让球胜平负");
                dismiss();
                type = 0;
                loadaadapter();
                break;
            case R.id.btn2:
                selectBtn(btn2);
                clearDate();
                setCusTomeTitle("北京单场-" + "总进球");
                dismiss();
                type = 1;
                loadaadapter();
                break;
            case R.id.btn3:
                selectBtn(btn3);
                clearDate();
                setCusTomeTitle("北京单场-" + "上下单双");
                dismiss();
                type = 2;

                loadaadapter();
                break;
            case R.id.btn4:
                selectBtn(btn4);
                clearDate();
                setCusTomeTitle("北京单场-" + "比分");
                dismiss();
                type = 3;
                loadaadapter();
                break;
            case R.id.btn5:
                selectBtn(btn5);
                clearDate();
                setCusTomeTitle("北京单场-" + "半全场");
                dismiss();
                type = 4;
                loadaadapter();
                break;
            case R.id.ensurebtn:
                if (TextUtils.isEmpty(num) || Integer.parseInt(num) == 0) {
                    Toast.makeText(this, "至少选择2场", Toast.LENGTH_SHORT).show();
                } else {

                    if (Integer.parseInt(num) < 2) {
                        Toast.makeText(this, "至少选择2场", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    List<MatchBean>  hasbean=getHasSelectMatch(bean);
                    startActivity(new Intent(BzjyActivity.this, BjPreActivity.class).
                            putExtra("date", (bean)).putExtra("type", type).putExtra("issueId", bean.get(0).getIssueId() + ""));

                }
                break;
        }
    }


    List<TextView> wanfaBtnList = new ArrayList<>(); // 玩法按钮列表

    private void getSelectPop() {

        wanfaBtnList.clear();
        vPopupWindow = getLayoutInflater().inflate(
                R.layout.bd_pop, null, false);

        vPopupWindow.findViewById(R.id.football_pop_news_btn)
                .setOnClickListener(this);
//

        btn1 = (TextView) vPopupWindow.findViewById(R.id.btn1);
        btn2 = (TextView) vPopupWindow.findViewById(R.id.btn2);
        btn3 = (TextView) vPopupWindow.findViewById(R.id.btn3);
        btn4 = (TextView) vPopupWindow.findViewById(R.id.btn4);
        btn5 = (TextView) vPopupWindow.findViewById(R.id.btn5);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        wanfaBtnList.add(btn1);
        wanfaBtnList.add(btn2);
        wanfaBtnList.add(btn3);
        wanfaBtnList.add(btn4);
        wanfaBtnList.add(btn5);


        if (type == 0) {

            btn1.setSelected(true);


        } else if (type == 1) {


            btn2.setSelected(true);


        } else if (type == 2) {


            btn3.setSelected(true);


        } else if (type == 3) {


            btn4.setSelected(true);


        } else if (type == 4) {


            btn5.setSelected(true);


        } else {


            btn1.setSelected(true);


        }


        rlPop.removeAllViews();
        rlPop.addView(vPopupWindow);
        rlPop.setVisibility(View.VISIBLE);

    }

    private void dismiss() {
        if (rlPop != null && rlPop.getVisibility() == View.VISIBLE)
            rlPop.setVisibility(View.GONE);
        AppUtil.startResRotateAnim(ivAnim);

    }


    private void selectBtn(TextView btn) {

        for (TextView b : wanfaBtnList) {
            b.setSelected(false);
        }

        btn.setSelected(true);

    }

}
