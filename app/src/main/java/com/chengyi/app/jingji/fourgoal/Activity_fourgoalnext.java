package com.chengyi.app.jingji.fourgoal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.home.hemai.TogeterBuyActivity;
import com.chengyi.app.model.SchemeMode;
import com.chengyi.app.model.model.GoumaiData;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.IP;
import com.chengyi.app.view.XuhaoExitDialog;
import com.chengyi.app.web.WebViewActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaqi on 2016/9/10.
 */
public class Activity_fourgoalnext extends BaseActivity {
    private TouzhuquerenData touzhudata;
    List<TouzhuquerenData> dataList = new ArrayList<TouzhuquerenData>();
    private TextView zhushu, yuan, danguan, faqihemai;
    private ListView listview;
    private FourgoalEntity fourgoalEntity;
    public FourgoalAdapter adapter;
    private TextView guoguanfangshi, moreguoguan;
    private LinearLayout jiangjinlayout;
    private List<FourgoalEntity.DataEntity> data;
    private EditText beishueditext;
    private Button wanchengxuanhao;

    private int beiShu = 1;
    private String issue;
    private String issueid;
    XuhaoExitDialog exitDailog;
    GoumaiData goucaiData = new GoumaiData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourgoalnext);
        Intent intent = getIntent();
        fourgoalEntity = (FourgoalEntity) intent.getSerializableExtra("fourgoal");
        issue = intent.getStringExtra("issue");
        issueid = intent.getStringExtra("issueid");
        data = fourgoalEntity.getData();
        initview();
        setlistner();
    }

    private void setlistner() {

        findViewById(R.id.btnlayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        faqihemai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(getSession())) {
                    startActivity(new Intent(Activity_fourgoalnext.this, LoginActivity.class));
                    return;
                }
                if (TextUtils.isEmpty(beishueditext.getText())) {
                    beishueditext.setText("1");
                    beiShu = 1;
                }
                getTouZhuData();
                goucaiData.setBeishu(beiShu);
                goucaiData.setCaipiaoId(CaipiaoConst.ID_FOURGOAL);
                goucaiData.setIssueIdStr(issueid);
                goucaiData.setIssueStr("1");
                goucaiData.setTotalZhushu(caculatemoney());
                goucaiData.setSubmitString(getTouZhuString());
                CaipiaoApplication.getInstance().setTouzhuHaomaList(dataList);
                Intent inte = new Intent(Activity_fourgoalnext.this, TogeterBuyActivity.class);
                inte.putExtra("goumaidata", goucaiData);
                startActivity(inte);
                pullUpActivityAnim();
            }
        });
        wanchengxuanhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(beishueditext.getText())) {
                    beishueditext.setText("1");
                    beiShu = 1;
                } else {
                    beiShu = Integer.parseInt(beishueditext.getText().toString());
                }
                getTouZhuData();
                goucaiData.setBeishu(beiShu);
                goucaiData.setTotalZhushu(caculatemoney());
                goucaiData.setIssueIdStr(issue);
                String content = "";
                int caculatemoney = caculatemoney();
                if (caculatemoney == 1) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).Hoststatusnew[0] == 1) {
                            content = content + "0";
                        }
                        if (data.get(i).Hoststatusnew[1] == 1) {
                            content = content + "1";
                        }
                        if (data.get(i).Hoststatusnew[2] == 1) {
                            content = content + "2";
                        }
                        if (data.get(i).Hoststatusnew[3] == 1) {
                            content = content + "3";
                        }
                        if (data.get(i).Guestatusnew[0] == 1) {
                            content = content + "0";
                        }
                        if (data.get(i).Guestatusnew[1] == 1) {
                            content = content + "1";
                        }
                        if (data.get(i).Guestatusnew[2] == 1) {
                            content = content + "2";
                        }
                        if (data.get(i).Guestatusnew[3] == 1) {
                            content = content + "3";
                        }
                    }
                    content = "single=" + content;
                } else if (caculatemoney > 1) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).Hoststatusnew[0] == 1) {
                            content = content + "0";
                        }
                        if (data.get(i).Hoststatusnew[1] == 1) {
                            content = content + "1";
                        }
                        if (data.get(i).Hoststatusnew[2] == 1) {
                            content = content + "2";
                        }
                        if (data.get(i).Hoststatusnew[3] == 1) {
                            content = content + "3";
                        }
                        content = content + "-";
                        if (data.get(i).Guestatusnew[0] == 1) {
                            content = content + "0";
                        }
                        if (data.get(i).Guestatusnew[1] == 1) {
                            content = content + "1";
                        }
                        if (data.get(i).Guestatusnew[2] == 1) {
                            content = content + "2";
                        }
                        if (data.get(i).Guestatusnew[3] == 1) {
                            content = content + "3";
                        }
                        content = content + "-";
                    }
                    content = "poly=" + content;
                }
                if (content.endsWith("-")) {
                    content = content.substring(0, content.length() - 1);
                }
                goucaiData.setCaipiaoId(CaipiaoConst.ID_FOURGOAL);
                goucaiData.setSubmitString(content);
                Intent in = null;
                CaipiaoApplication.getInstance().setTouzhuHaomaList(dataList);
                if (!TextUtils.isEmpty(getSession())) {
                    Map<String, String> parms = new HashMap<String, String>();
                    parms.put("clientUserSession", getSession());
                    parms.put("issueId", issueid);
                    parms.put("multiple", goucaiData.getBeishu() + "");
                    parms.put("schemeNumber", content);
                    parms.put("issueCount", "1");
                    parms.put("schemeNumberUnit", goucaiData.getTotalZhushu() + "");
                    parms.put("lotteryId", CaipiaoConst.ID_FOURGOAL + "");
                    parms.put("buyAmount", yuan.getText().toString());
                    parms.put("schemeAmount", yuan.getText().toString());
                    parms.put("buyType", "1");
                    OkHttpUtil.postSubmitForm(CaipiaoConst.BASE_URL + URLSuffix.BUY_LOTTERY, parms, new OkHttpUtil.OnDownDataListener() {
                        @Override
                        public void onResponse(String url, String json) {
                            JSONObject result = null;
                            try {
                                result = new JSONObject(json);
                                String errorMsg = result
                                        .optString(URLSuffix.errorMessage);

                                SchemeMode schemeMode = com.alibaba.fastjson.JSONObject.parseObject(result.toString(), SchemeMode.class);
                                if (schemeMode.getFlag() == 1 && !TextUtils.isEmpty(schemeMode.getSchemeId() + "")) {


                                    startActivity(new Intent(getBaseContext(), WebViewActivity.class)
                                            .putExtra("url", IP.IP + "lottery/scheme_confirm.htm?clientUserSession=" + getSession() + "&schemeId=" +
                                                    schemeMode.getSchemeId() + "&appVersion=" + CaipiaoConst.APPVERSION + "&version=" + CaipiaoConst.VERSION + "&requestType=" + CaipiaoConst.REQUESTTYPE));


                                } else if (schemeMode.getFlag() == 2) {
                                    exitDailog.show();
                                    exitDailog.getCenterTextView().setText(schemeMode.getErrorMessage());
                                } else {
                                    Toast.makeText(Activity_fourgoalnext.this, schemeMode.getErrorMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(String url, String error) {

                        }
                    });
                } else {
                    in = new Intent(Activity_fourgoalnext.this, LoginActivity.class);
                    startActivity(in);
                    pullDownActivityAnim();
                }
            }
        });


        beishueditext.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                int beiShuNum = 0;
                // TODO Auto-generated method stub
                try {
                    if (!TextUtils.isEmpty(s)) {
                        beiShuNum = Integer.parseInt(s + "");
                    } else {
                        beiShuNum = 1;
                    }
                    yuan.setText(String.valueOf(caculatemoney() * 2 * beiShuNum));
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        });
    }

    private int caculatemoney() {
        int zhu = 1;
        for (int i = 0; i < data.size(); i++) {
            zhu = data.get(i).caculGuest() * data.get(i).caculHost() * zhu;
        }
        return zhu;
    }

    private void initview() {
        findViewById(R.id.fl).setVisibility(View.GONE);
        faqihemai = (TextView) findViewById(R.id.faqihemai);
        exitDailog = new XuhaoExitDialog(this);
        wanchengxuanhao = (Button) findViewById(R.id.wanchengxuanhao);
        setBack();
        findViewById(R.id.ll_menu).setVisibility(View.INVISIBLE);

        findViewById(R.id.iv_anim).setVisibility(View.GONE);


        danguan = (TextView) findViewById(R.id.tv_danguan);
        beishueditext = (EditText) findViewById(R.id.beishueditext);
        zhushu = (TextView) findViewById(R.id.zhushu);
        yuan = (TextView) findViewById(R.id.yuan);
        zhushu.setText(String.valueOf(caculatemoney()));
        yuan.setText(String.valueOf(caculatemoney() * 2));
        jiangjinlayout = (LinearLayout) findViewById(R.id.jiangjinlayout);
        setCusTomeTitle("投注确认");
        listview = (ListView) findViewById(R.id.fourlistview);
        adapter = new FourgoalAdapter(Activity_fourgoalnext.this, fourgoalEntity.getData(),true);
        adapter.setModle(1);
        listview.setAdapter(adapter);
        guoguanfangshi = (TextView) findViewById(R.id.guoguanfangshi);
        moreguoguan = (TextView) findViewById(R.id.moreguoguan);
        guoguanfangshi.setText("四进球彩");
        moreguoguan.setText("");
        guoguanfangshi.setEnabled(false);
        moreguoguan.setEnabled(false);
        danguan.setVisibility(View.GONE);
        jiangjinlayout.setVisibility(View.GONE);
    }

    private void getTouZhuData() {
        dataList.clear();
        touzhudata = new TouzhuquerenData();
        touzhudata.setCaipiaoIdAndWanfaType(CaipiaoConst.ID_FOURGOAL, -1);
        touzhudata.setName("");
        String content = "";
        int caculatemoney = caculatemoney();
        if (caculatemoney == 1) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).Hoststatusnew[0] == 1) {
                    content = content + "0";
                }
                if (data.get(i).Hoststatusnew[1] == 1) {
                    content = content + "1";
                }
                if (data.get(i).Hoststatusnew[2] == 1) {
                    content = content + "2";
                }
                if (data.get(i).Hoststatusnew[3] == 1) {
                    content = content + "3";
                }
                if (data.get(i).Guestatusnew[0] == 1) {
                    content = content + "0";
                }
                if (data.get(i).Guestatusnew[1] == 1) {
                    content = content + "1";
                }
                if (data.get(i).Guestatusnew[2] == 1) {
                    content = content + "2";
                }
                if (data.get(i).Guestatusnew[3] == 1) {
                    content = content + "3";
                }
            }
        } else if (caculatemoney > 1) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).Hoststatusnew[0] == 1) {
                    content = content + "0";
                }
                if (data.get(i).Hoststatusnew[1] == 1) {
                    content = content + "1";
                }
                if (data.get(i).Hoststatusnew[2] == 1) {
                    content = content + "2";
                }
                if (data.get(i).Hoststatusnew[3] == 1) {
                    content = content + "3";
                }
                content = content + "-";
                if (data.get(i).Guestatusnew[0] == 1) {
                    content = content + "0";
                }
                if (data.get(i).Guestatusnew[1] == 1) {
                    content = content + "1";
                }
                if (data.get(i).Guestatusnew[2] == 1) {
                    content = content + "2";
                }
                if (data.get(i).Guestatusnew[3] == 1) {
                    content = content + "3";
                }
                content = content + "-";
            }

        }
        if (content.endsWith("-")) {
            content = content.substring(0, content.length() - 1);
        }
        touzhudata.setTouzhuhaoma(content);
        touzhudata.setZhushu(caculatemoney());
        dataList.add(touzhudata);
    }

    String getTouZhuString() {
        String content = "";
        int caculatemoney = caculatemoney();
        if (caculatemoney == 1) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).Hoststatusnew[0] == 1) {
                    content = content + "0";
                }
                if (data.get(i).Hoststatusnew[1] == 1) {
                    content = content + "1";
                }
                if (data.get(i).Hoststatusnew[2] == 1) {
                    content = content + "2";
                }
                if (data.get(i).Hoststatusnew[3] == 1) {
                    content = content + "3";
                }
                if (data.get(i).Guestatusnew[0] == 1) {
                    content = content + "0";
                }
                if (data.get(i).Guestatusnew[1] == 1) {
                    content = content + "1";
                }
                if (data.get(i).Guestatusnew[2] == 1) {
                    content = content + "2";
                }
                if (data.get(i).Guestatusnew[3] == 1) {
                    content = content + "3";
                }
            }
            content = "single=" + content;
        } else if (caculatemoney > 1) {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).Hoststatusnew[0] == 1) {
                    content = content + "0";
                }
                if (data.get(i).Hoststatusnew[1] == 1) {
                    content = content + "1";
                }
                if (data.get(i).Hoststatusnew[2] == 1) {
                    content = content + "2";
                }
                if (data.get(i).Hoststatusnew[3] == 1) {
                    content = content + "3";
                }
                content = content + "-";
                if (data.get(i).Guestatusnew[0] == 1) {
                    content = content + "0";
                }
                if (data.get(i).Guestatusnew[1] == 1) {
                    content = content + "1";
                }
                if (data.get(i).Guestatusnew[2] == 1) {
                    content = content + "2";
                }
                if (data.get(i).Guestatusnew[3] == 1) {
                    content = content + "3";
                }
                content = content + "-";
            }
            content = "poly=" + content;
        }
        if (content.endsWith("-")) {
            content = content.substring(0, content.length() - 1);
        }
        return content;
    }
}