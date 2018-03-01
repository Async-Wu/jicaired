package com.chengyi.app.jingji.six;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.home.hemai.TogeterBuyActivity;
import com.chengyi.app.listener.INet;
import com.chengyi.app.model.SchemeMode;
import com.chengyi.app.model.model.GoumaiData;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.net.api.HomeApi;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.IP;
import com.chengyi.app.util.L;
import com.chengyi.app.view.XuhaoExitDialog;
import com.chengyi.app.web.WebViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SixCartActivity extends BaseActivity implements SixAdapter.ISixGet, INet {
    private ArrayList<SixMode> mode;
    private SixAdapter adapter;
    private EditText beishueditext;
    private Button wanchengxuanhao;
    private long zhunum = 1;
    private TextView faqihemai;
    private RecyclerView rv_cart_six;
    private TextView zhushu, yuan;
    private LinearLayout ll_menu,btnlayout;
    private ImageView title_back_right;
    private String issue;
    private int issueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six_cart);
        beishueditext = (EditText) findViewById(R.id.beishueditext);
        faqihemai = (TextView) findViewById(R.id.faqihemai);
        faqihemai.setOnClickListener(this);
        findViewById(R.id.fl).setVisibility(View.GONE);
        rv_cart_six = (RecyclerView) findViewById(R.id.rv_cart_six);
        wanchengxuanhao = (Button) findViewById(R.id.wanchengxuanhao);
        zhushu = (TextView) findViewById(R.id.zhushu);
        findViewById(R.id.ll_back).setOnClickListener(this);
        yuan = (TextView) findViewById(R.id.yuan);
        findViewById(R.id.jiangjinlayout).setVisibility(View.GONE);
        wanchengxuanhao.setOnClickListener(this);
        btnlayout = (LinearLayout) findViewById(R.id.btnlayout);
        btnlayout.setOnClickListener(this);
        findViewById(R.id.iv_anim).setVisibility(View.GONE);

        setCusTomeTitle(getString(R.string.siz_chang) + "投注确认");
        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);
        title_back_right = (ImageView) findViewById(R.id.iv_wanfa);
        title_back_right.setImageResource(R.drawable.laiji1);
        findViewById(R.id.iv_select).setVisibility(View.GONE);
        ll_menu.setOnClickListener(this);
        ll_menu.setVisibility(View.VISIBLE);

        mode = getIntent().getParcelableArrayListExtra("six");

        issue = getIntent().getStringExtra("currIsuu");
        issueId = getIntent().getIntExtra("currIsuId", 0);
        if (mode == null) return;
        adapter = new SixAdapter(this, mode);
        adapter.setType(1);


        rv_cart_six.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv_cart_six.setAdapter(adapter);
        initNum();


        beishueditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(beishueditext.getText().toString())) {

                    yuan.setText(String.valueOf(zhunum * 2));

                } else {
                    yuan.setText(String.valueOf(zhunum * 2 * Integer.parseInt(beishueditext.getText().toString())));

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void initNum() {
        zhunum = getzhunum(mode);
        zhushu.setText(String.valueOf(zhunum));
        yuan.setText(String.valueOf(zhunum * 2 * 1));
    }


    public long getzhunum(ArrayList<SixMode> mode) {
        int i = 1;
        for (SixMode m : mode) {
            i *= m.getBCount() * m.getQCount();
        }

        return i;

    }


    @Override
    public void notifyDate(List<SixMode> sixModes, int pos, boolean flag) {
        if (!flag) {
            SixController.setSixdelet(pos * 2);
        } else {
            SixController.setSixdelet(pos * 2 + 1);
        }
        mode = (ArrayList<SixMode>) sixModes;
        initNum();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_back:
                clear();
                finish();
                break;
            case R.id.btnlayout:
                finish();
                break;
            case R.id.ll_menu:
                title_back_right.setSelected(!title_back_right.isSelected());
                if (title_back_right.isSelected()) {
                    adapter.setDelete(1);
                } else {
                    adapter.setDelete(0);
                }
                break;

            case R.id.wanchengxuanhao:
                if (zhunum > 0) {
                    gotoBuy();
                } else {
                    Toast.makeText(this, "不能发起0注购买", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.faqihemai:

                if (zhunum == 0) {
                    Toast.makeText(this, "不能发起0注购买", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(SixCartActivity.this, TogeterBuyActivity.class);
                GoumaiData goucaiData = new GoumaiData();
                goucaiData.setCaipiaoId(CaipiaoConst.ID_BALL);
                try {
                    goucaiData.setBeishu(Integer.parseInt(beishueditext.getText()
                            .toString().trim()));
                } catch (Exception e) {
                    goucaiData.setBeishu(1);
                }

                List<TouzhuquerenData> l = new ArrayList<>();
                TouzhuquerenData data = new TouzhuquerenData();
                data.setCaipiaoIdAndWanfaType(getCurrentCaipiao().getId(), -1);
                data.setName("");
                data.setTouzhuhaoma(getNumber(mode));
                data.setZhushu(((int) zhunum));

                l.clear();
                l.add(data);
                CaipiaoApplication.getInstance().setTouzhuHaomaList(l);
                if (zhunum > 1) {

                    goucaiData.setShow("poly=" + getNumber(mode));
                    goucaiData.setSubmitString("poly=" + getNumber(mode));


                } else {
                    goucaiData.setSubmitString("single=" + getNumber(mode));
                    goucaiData.setShow("single=" + getNumber(mode));
                }

//                goucaiData.setShow("content=" + getNumber(mode));
                goucaiData.setTotalZhushu(zhunum);
                goucaiData.setGameSize(12);
                goucaiData.setCaipiaoId(getCurrentCaipiao().getId());

                goucaiData.setBeishu( TextUtils.isEmpty(beishueditext.getText().toString())?1:Integer.parseInt(beishueditext.getText().toString()));

                goucaiData.setIssue(String.valueOf(issueId));
                goucaiData.setIssueIdStr(String.valueOf(issueId));
        L.i("test", String.valueOf(issueId));

                intent.putExtra("goumaidata", goucaiData);
                intent.putExtra("isJingcai", true);


                startActivity(intent);
                break;
        }

    }

    private String getNumber(ArrayList<SixMode> sixModes) {
        String s = "";

        for (SixMode m : sixModes) {

            if (zhunum>1){
                s += m.getBNum() + "-" + m.getQNum() + "-";
            }else{
                s += m.getBNum() + "" + m.getQNum() + "";
            }

        }
        if (s.endsWith("-")) {
            s = s.substring(0, s.length() - 1);
        }


        return s;
    }

    private void gotoBuy() {
        if (TextUtils.isEmpty(getSession())) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SixCartActivity.this, LoginActivity.class));
        } else {
            fetch(FIRST);

        }


    }

    XuhaoExitDialog exitDailog;

    @Override
    public void response(int tag, Object o) {
        hideLoading();
        if (o == null) return;
        if (tag == FIRST) {
            SchemeMode schemeMode = (SchemeMode) o;
            if (schemeMode.getFlag() == 1 && !TextUtils.isEmpty(schemeMode.getSchemeId() + "")) {

                startActivity(new Intent(SixCartActivity.this, WebViewActivity.class)
                        .putExtra("url", IP.IP + "lottery/scheme_confirm.htm?clientUserSession=" + getSession() + "&schemeId=" +
                                schemeMode.getSchemeId() + "&appVersion=" + CaipiaoConst.APPVERSION + "&version=" + CaipiaoConst.VERSION + "&requestType=" + CaipiaoConst.REQUESTTYPE));

            } else {
                Toast.makeText(SixCartActivity.this, schemeMode.getErrorMessage(), Toast.LENGTH_SHORT).show();

            }
        }

    }

    @Override
    public void fetch(int tag) {
        if (tag == FIRST) {
            showLoading("加载中...");

            Map<String, String> m = new HashMap<>();


            if (zhunum > 1) {
                m.put("schemeNumber", "poly=" + getNumber(mode));
            } else {
                m.put("schemeNumber", "single=" + getNumber(mode));
            }


            m.put("issue", issue);
            m.put("issueId", issueId + "");
            m.put("multiple",  TextUtils.isEmpty(beishueditext.getText().toString())?"1":beishueditext.getText().toString());
            m.put("schemeNumberUnit", zhushu.getText().toString());
            m.put("lotteryId", CaipiaoConst.ID_SIX + "");
            m.put("clientUserSession", getSession());
            m.put("schemeAmount", yuan.getText().toString());
            m.put("buyAmount", yuan.getText().toString());
            m.put("issueCount", "1");
            m.put("buyType","1");

            HomeApi.SUBMIT(this, this, tag, SchemeMode.class, m);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clear();
        }
        return super.onKeyDown(keyCode, event);
    }


    private void clear() {
        SixController.setIsClear(true);
    }


}
