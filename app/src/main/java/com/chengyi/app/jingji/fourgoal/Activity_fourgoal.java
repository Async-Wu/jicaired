package com.chengyi.app.jingji.fourgoal;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.util.CaipiaoConst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by xiaqi on 2016/9/9.
 */
public class Activity_fourgoal extends BaseActivity {
    private TextView zhushutext, qingkong, qiShuText, jiangqihaoma;

    private SwipeRefreshLayout refreshlayout;
    private Caipiao cp;
    private Button ensurebtn;
    private Map<String, String> params = new HashMap<>();
    private ListView fourgoal_listview;
    private FourgoalAdapter adapter;
    private List<FourgoalEntity.DataEntity> data;
    private int caculatezhushu = 0;
    private FourgoalEntity fourgoalEntity;

    List<String> arrayIssue;
    private Button btnOne, btnTwo, btnThree, btnFour;
    private String issuestr = "";
    private String issueid = "";
    private Button currentBtn;
    private LinearLayout ftcenterLayout;
    private  TextView yixuantishi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourgoal);
        cp = CaipiaoApplication.getInstance().getCurrentCaipiao();
        initview();
        setlistner();
        requestdata();
    }

    /**
     * 计算投注数的handler
     */
    Handler caculhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int code = msg.what;
            if (code == 0) {
                int shu = caculatezhushu();
                yixuantishi.setText("已选择");
                zhushutext.setText( shu + "");
            }
        }
    };

    public int caculatezhushu() {
        int changnum = 0;
        for (int i = 0; i < data.size(); i++) {

            if (data.get(i).judgezhu()) {
                changnum = changnum + 1;
            }
        }
        return changnum;
    }



    private void setlistner() {
findViewById(R.id.footballffootballtopbarLayout).setOnClickListener(this);
        setBack();
        setCusTomeTitle(getCurrentCaipiao().getName());

        ensurebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int caculatezhushu = caculatezhushu();
                Random random = new Random();
                if (caculatezhushu == 0) {
                    for (int i = 0; i < 4; i++) {
                        int ranshu = random.nextInt(4);
                        data.get(i).Guestatusnew[ranshu] = 1;
                    }
                    for (int i = 0; i < 4; i++) {
                        int ranshu1 = random.nextInt(4);
                        data.get(i).Hoststatusnew[ranshu1] = 1;
                    }
                    adapter.notifyDataSetChanged();
                } else if (caculatezhushu < 4) {
                    Toast.makeText(Activity_fourgoal.this, "请选齐4场比赛", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Activity_fourgoal.this, Activity_fourgoalnext.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("fourgoal", fourgoalEntity);
                    intent.putExtras(bundle);
                    intent.putExtra("issue", issuestr);
                    intent.putExtra("issueid", issueid);
                    startActivityForResult(intent, CaipiaoConst.FOURREQUEST);
                }
            }
        });
        refreshlayout.setColorSchemeResources(R.color.red);
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestdata();
                refreshlayout.setRefreshing(false);
            }
        });
        qingkong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).cleardata();
                }
                yixuantishi.setText("请至少选择");
                zhushutext.setText("4");
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initview() {
      findViewById(R.id.iv_select).setVisibility(View.INVISIBLE);
        findViewById(R.id.iv_wanfa).setOnClickListener(this);
        ftcenterLayout = (LinearLayout) findViewById(R.id.footballffootballtopbarLayout);
        jiangqihaoma = (TextView) findViewById(R.id.jiangqihaoma);
        qiShuText = (TextView) findViewById(R.id.jiangqitextview);

        yixuantishi= (TextView) findViewById(R.id.yixuantishi);
        ensurebtn = (Button) findViewById(R.id.ensurebtn);
        refreshlayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        fourgoal_listview = (ListView) findViewById(R.id.fourgoal_listview);
        qingkong = (TextView) findViewById(R.id.qingkong);
        zhushutext = (TextView) findViewById(R.id.numbisai);

    }

    private void requestdata() {
        params.put("issue", issuestr);
        OkHttpUtil.postSubmitForm(CaipiaoConst.BASE_URL + URLSuffix.SICHANGJINQIU, params, new OkHttpUtil.OnDownDataListener() {
            @Override
            public void onResponse(String url, String json) {
                if (json != null) {

                    fourgoalEntity = JSONObject.parseObject(json, FourgoalEntity.class);
                    if (fourgoalEntity.getFlag() == 1) {
                        data = fourgoalEntity.getData();
                        adapter = new FourgoalAdapter(Activity_fourgoal.this, data);
                        adapter.setHandler(caculhandler);
                        fourgoal_listview.setAdapter(adapter);
                        getCurrentCaipiao().setIssue(fourgoalEntity.getCurIssue());
                        getCurrentCaipiao().setIssueId(fourgoalEntity.getCurIssueId());
                        setCusTomeTitle(cp.getName() + "-" + cp.getIssue()+"期");

                        qiShuText.setText("截止时间：");
                        if (!TextUtils.isEmpty(fourgoalEntity.getSellEndTime())) {
                            jiangqihaoma.setText(fourgoalEntity.getSellEndTime());
                        }

                        arrayIssue = fourgoalEntity.getIssue();
                        issuestr = fourgoalEntity.getCurIssue();
                        issueid = String.valueOf(fourgoalEntity.getCurIssueId());
                    }
                }
            }

            @Override
            public void onFailure(String url, String error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }

    View vPopupWindow;
    PopupWindow wanfaChangePop;
    LinearLayout top, zhuShuLayout;
    ImageView img;


    private PopupWindow getSelectPop() {
        if (vPopupWindow == null) {
            vPopupWindow = getLayoutInflater().inflate(
                    R.layout.new_pop_shengfu14changwanfa, null, false);
            wanfaChangePop = new PopupWindow(vPopupWindow,
                    ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            wanfaChangePop.setBackgroundDrawable(new BitmapDrawable());
            wanfaChangePop.setFocusable(true);
            wanfaChangePop.setOutsideTouchable(true);
            top = (LinearLayout) vPopupWindow
                    .findViewById(R.id.layouttopselect);

            top.setVisibility(View.VISIBLE);

            img = (ImageView) vPopupWindow.findViewById(R.id.imageView1);
            btnOne = (Button) vPopupWindow.findViewById(R.id.button1);
            btnOne.setOnClickListener(Activity_fourgoal.this);
            btnTwo = (Button) vPopupWindow.findViewById(R.id.button2);
            btnTwo.setOnClickListener(Activity_fourgoal.this);
            btnThree = (Button) vPopupWindow.findViewById(R.id.button3);
            btnThree.setOnClickListener(Activity_fourgoal.this);
            btnFour = (Button) vPopupWindow.findViewById(R.id.button4);
            btnFour.setOnClickListener(Activity_fourgoal.this);
            vPopupWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wanfaChangePop != null && wanfaChangePop.isShowing()) {
                        dismiss();
                    }
                }
            });
        } else {

            top.setVisibility(View.VISIBLE);

        }
        if (null != arrayIssue) {
            try {
                btnOne.setText(arrayIssue.get(0) + "期");
                btnOne.setTag(arrayIssue.get(0));
                if (arrayIssue.size() > 1) {
                    btnTwo.setVisibility(View.VISIBLE);
                    btnTwo.setText(arrayIssue.get(1) + "期");
                    btnTwo.setTag(arrayIssue.get(1));
                } else
                    btnTwo.setVisibility(View.GONE);
                if (arrayIssue.size() > 2) {
                    btnThree.setVisibility(View.VISIBLE);
                    btnThree.setText(arrayIssue.get(2) + "期");
                    btnThree.setTag(arrayIssue.get(2));
                } else
                    btnThree.setVisibility(View.GONE);
                if (issuestr.equals(arrayIssue.get(0))) {
                    btnOne.setSelected(true);
                    currentBtn = btnOne;
                } else if (arrayIssue.size() > 1 && issuestr.equals(arrayIssue.get(1))) {
                    btnTwo.setSelected(true);
                    currentBtn = btnTwo;
                } else if (arrayIssue.size() > 2 && issuestr.equals(arrayIssue.get(2))) {
                    btnThree.setSelected(true);
                    currentBtn = btnThree;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (arrayIssue.size() > 3) {
                btnFour.setText(arrayIssue.get(3) + "期");
                btnFour.setTag(arrayIssue.get(3));
                if (issuestr.equals(arrayIssue.get(3))) {
                    btnFour.setSelected(true);
                    currentBtn = btnFour;
                }
            } else
                btnFour.setVisibility(View.GONE);
        }
        return wanfaChangePop;
    }

    private void dismiss() {

        wanfaChangePop.dismiss();

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_wanfa:
                Toast.makeText(Activity_fourgoal.this, "暂无此玩法介绍", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
                dismiss();
                try {
                    currentBtn.setSelected(false);
                } catch (Exception e) {
                }

                v.setSelected(true);
                currentBtn = (Button) v;
                issuestr = String.valueOf(v.getTag());
                setCusTomeTitle(cp.getName() + "-" + issuestr+"期");
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).cleardata();
                }
                yixuantishi.setText("请至少选择");
                zhushutext.setText("4");
                adapter.notifyDataSetChanged();
                requestdata();
                break;

            case  R.id.footballffootballtopbarLayout:
            case  R.id.tv_title:
                getSelectPop().showAsDropDown(   findViewById(R.id.footballffootballtopbarLayout));
                break;
        }
    }
}
