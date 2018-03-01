package com.chengyi.app.jingji.six;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.listener.INet;
import com.chengyi.app.net.api.HomeApi;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.L;

import java.util.*;

public class SixActivity extends BaseActivity implements INet, SixAdapter.ISixGet, SixPopAdapter.IGetDate {
    private TextView qingkong, numbisai, ensurebtn, tv_end_time, tv_is_end;
    private RelativeLayout loaddata;
    private SwipeRefreshLayout srl_six;
    private RecyclerView rv_six;
    private ArrayList<SixMode> modes;
    private int count;
    private SixAdapter adapter;

    private long rend_time;
    private ImageView ivAnim;
    private TextView yixuantishi;


    @Override
    protected void onResume() {
        super.onResume();
        if (SixController.getSixdelet() != null && modes != null) {
            for (Integer pos : SixController.getSixdelet()) {
                if (pos % 2 == 0) {
                    modes.get(pos / 2).clearB();
                } else {
                    modes.get(pos / 2).clearQ();
                }
            }
            caculNum(modes);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }


        if (modes != null && SixController.isClear()) {
            SixController.setIsClear(false);
            clearDate();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
        ivAnim = (ImageView) findViewById(R.id.iv_anim);
        setCusTomeTitle(getCurrentCaipiao().getName());
        tv_is_end = (TextView) findViewById(R.id.tv_is_end);
        findViewById(R.id.iv_wanfa).setOnClickListener(this);
        findViewById(R.id.iv_select).setVisibility(View.GONE);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        qingkong = (TextView) findViewById(R.id.qingkong);
        ensurebtn = (TextView) findViewById(R.id.ensurebtn);
        ensurebtn.setOnClickListener(this);
        qingkong.setOnClickListener(this);
        yixuantishi = (TextView) findViewById(R.id.yixuantishi);
        numbisai = (TextView) findViewById(R.id.numbisai);
        numbisai.setOnClickListener(this);
        loaddata = (RelativeLayout) findViewById(R.id.loaddata);
        srl_six = (SwipeRefreshLayout) findViewById(R.id.srl_six);
        srl_six.setColorSchemeResources(R.color.red);
        findViewById(R.id.footballffootballtopbarLayout).setOnClickListener(this);

        rv_six = (RecyclerView) findViewById(R.id.rv_six);
        srl_six.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                clearDate();
                fetch(FIRST);
            }
        });
        setBack();
        fetch(FIRST);
    }

    @Override
    public void response(int tag, Object o) {
        if (loaddata != null) loaddata.setVisibility(View.GONE);
        if (srl_six != null) srl_six.setRefreshing(false);
        if (o == null) return;

        if (tag == FIRST) {

            SixBean bean = ((SixBean) o);
            if (bean != null && bean.getFlag() == 1 && bean.getData() != null) {
                modes = (ArrayList<SixMode>) bean.getData();
                rend_time = bean.getRemainTime();

                selectDate = bean.getCurIssue();
                curIsuuId = bean.getCurIssueId();

                if (isEnd()) {
                    tv_is_end.setVisibility(View.VISIBLE);
                    tv_is_end.setText(" 已截至");
                } else {
                    tv_is_end.setVisibility(View.VISIBLE);
                    tv_is_end.setText("" + CaipiaoUtil.formatDuring(bean.getRemainTime()));
                }


                tv_end_time.setText("截至时间:" + bean.getSellEndTime());
                setCusTomeTitle(getCurrentCaipiao().getName() + "-" + bean.getCurIssue());
                issue = bean.getIssue();
                issueId = bean.getIssueId();


            } else {
                tv_end_time.setText("截至时间:");
                tv_is_end.setText(" 已截至");
                modes = new ArrayList<>();
                rend_time = 0;
                Toast.makeText(this, bean.getErrorMessage(), Toast.LENGTH_SHORT).show();

            }
            caculNum(modes);
            initAdapter();
            initTitlePop();


        }


    }

    private boolean isEnd() {
        return rend_time <= 0;
//    return  false;
    }

    private int curIsuuId;
    private List<Integer> issueId = new ArrayList<>();
    private List<String> issue = new ArrayList<>();

    private void initTitlePop() {
        pop_list = new ArrayList<>();
        for (int s = 0; s < issue.size(); s++) {
            PopMode popMode = new PopMode();
            popMode.setDate(issue.get(s));
            popMode.setIssuId(issueId.get(s));
            if (selectDate.equals(popMode.getDate())) {
                popMode.setSelect(true);
            } else {
                popMode.setSelect(false);
            }
            pop_list.add(popMode);
        }


    }

    @Override
    public void fetch(int tag) {
        if (tag == FIRST) {
            loaddata.setVisibility(View.VISIBLE);
            Map<String, String> m = new HashMap<>();
            m.put("issue", selectDate);
            HomeApi.SIX_CHANG(this, this, tag, SixBean.class, m);
        }


    }

    private void initAdapter() {
        loaddata.setVisibility(View.GONE);
        if (modes == null || modes.isEmpty()) {
            modes = new ArrayList<>();
        }

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_six.setLayoutManager(manager);
        adapter = new SixAdapter(this, modes);
        if (isEnd()) {
            adapter.setType(2);

        } else {
            adapter.setType(0);
        }

        rv_six.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.qingkong:
                clearDate();
                break;


            case R.id.ensurebtn:

                if (isEnd()) {
                    Toast.makeText(this, "比赛已截至", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (count < 12 && count != 0) {
                    Toast.makeText(this, "选择足够场次", Toast.LENGTH_SHORT).show();
                } else if (count == 0) {

                    for (int i = 0; i < modes.size(); i++) {
                        modes.get(i).rand();
                    }
                    caculNum(modes);
                    SixController.clearDelete();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }

                    return;
                } else if (count == 12) {
                    SixController.clearDelete();
                    startActivity(new Intent(SixActivity.this, SixCartActivity.class).putExtra("six", modes).putExtra("currIsuu", selectDate).putExtra("currIsuId", curIsuuId));
                }
                break;
            case R.id.tv_title:
            case R.id.footballffootballtopbarLayout:
                if (popPop != null && popPop.isShowing()) {
                    disPop(ivAnim);
                } else {
                    if (pop_list != null && !pop_list.isEmpty() && pop_list.size() > 0)
                        showPop();
                }
                break;

            case R.id.iv_wanfa:
                Toast.makeText(this, "暂无该玩法介绍", Toast.LENGTH_SHORT).show();
//                moveToWanFa();
                break;
            default:
                break;

        }
    }

    private void clearDate() {
        if (modes != null) {
            for (int i = 0; i < modes.size(); i++) {
                modes.get(i).clear();
            }
            adapter.notifyDataSetChanged();
        }
        caculNum(modes);
    }

    @Override
    public void notifyDate(List<SixMode> sixModes, int pos, boolean flag) {

        caculNum(sixModes);
    }

    private void caculNum(List<SixMode> sixModes) {
        count = getsetCount(sixModes);
        L.d(count + sixModes.toString());
        if (count > 0) {
            yixuantishi.setText("已选择");
            numbisai.setText("" + count + "");
        } else {
            yixuantishi.setText("至少选择");
            numbisai.setText("" + 12 + "");
        }
    }


    public int getsetCount(List<SixMode> sixModes) {

        if (sixModes == null) {
            return 0;
        } else {
            int count = 0;
            for (SixMode mode : sixModes) {
                count += (mode.getSelect());
            }

            return count;
        }
    }


    private void showPop() {
        AppUtil.startRotateAnim(ivAnim);
        getPop(ivAnim).showAsDropDown(findViewById(R.id.footballffootballtopbarLayout));

    }


    private String selectDate = "";

    @Override
    public void getDate(List<PopMode> pop) {
        disPop(ivAnim);
        for (PopMode mode : pop) {
            if (mode.isSelect()) {
                selectDate = mode.getDate().replace("期","");
                curIsuuId = mode.getIssuId();

                setCusTomeTitle(getCurrentCaipiao().getName() + "-" + selectDate+"期");

                fetch(FIRST);
            }
        }
    }
}
