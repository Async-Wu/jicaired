package com.chengyi.app.num.lottery;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.adapter.TouzhuquerenAdapter;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.home.hemai.TogeterBuyActivity;
import com.chengyi.app.jingji.renxuan.Activity_RenXuanQiuChang;
import com.chengyi.app.listener.IBuyCallBack;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.model.GoumaiData;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.model.wanfa.AbsWanfa;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.user.BuyConfirm;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.L;
import com.chengyi.app.util.YOUMENG_EVENT;
import com.chengyi.app.view.XuhaoExitDialog;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ChengYi
 * * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class Activity_Touzhuqueren extends BaseActivity implements
        View.OnClickListener, XuhaoExitDialog.ICallback {
    ListView listView;
    ImageView imageViewbg, title_back_right;
    TouzhuquerenAdapter adapter;
    LinearLayout rightbtnmenu;
    private LinearLayout ll_menu;
    LinearLayout shouxuanBtn, jixuanBtn;
    Button faqihemai,
            gouMaiBtn;
    Button zhuiqione, zhuiqiTwo, zhuiqiThree;
    TextView zhuNum, totalMoney, qiShu, beiShu, qishutext,
            beishutext, beiNum, moneytext;
    GoumaiData goucaiData = new GoumaiData();
    List<TouzhuquerenData> listData;
    CheckBox zhuijiatouzhu, leijicheckBox;
    EditText beishueditext, qishueditext, leijizhongchutext;
    private int lastClickTouzhu = 0;
    //    SoftKeyboardLayout layout;
    XuhaoExitDialog exitDailog;
    LinearLayout kuaishuzhuihao, zhinengbeitou, zhuihaolayout, stoplayout;
    RelativeLayout zhinengzhuihaolayout, bottomlayout;
    int kuaiSuQiOne = 1, kuaiSuQiTwo = 0, kuaiSuQiThree = 0;
    Caipiao cp;
    boolean isCanZhiNengZhuihao = false;// /是否能进行智能追号
    boolean isToday = false;
    boolean dayutingzhiChecked = false;// 记录累计中出是否选中
    String issueStr = "";
    String multipleStr = "";
    String issuseIdStr = "";
    HashMap<String, String> map = new HashMap<String, String>();
    SharedPreferences preferences;
    Editor editor;
    boolean isFromBaoCun = false;
    Intent intent;
    /**
     * 每场比赛三个按钮代表的含义
     */
    private static char[] TOUZHUSTR = new char[]{'3', '1', '0'};

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_touzhulinearlayout);
        cp = CaipiaoApplication.getInstance().getCurrentCaipiao();
        preferences = CaipiaoUtil.getCpSharedPreferences(this);
        editor = preferences.edit();
        loadViews();
        setListeners();
        int randomNumber = getIntent().getIntExtra("randomnumber", 0);
        isFromBaoCun = getIntent().getBooleanExtra("isfrombaocun", false);
        if (getIntent().getBooleanExtra("isfromgoucaixiangqing", false)
                || isFromBaoCun) {
            dataList = (List<TouzhuquerenData>) getIntent()
                    .getSerializableExtra("touzhudataList");
            for (int i = 0; i < dataList.size(); i++) {
                initData(dataList.get(i));
            }
            adapter.setList(dataList);
            adapter.notifyDataSetChanged();
            if (isFromBaoCun)
                this.showToast("这是您上次保存的号码");
        } else {
            if (randomNumber == 0) {
                TouzhuquerenData data = (TouzhuquerenData) getIntent()
                        .getSerializableExtra("touzhudata");
                initData(data);
                // /处理下如果号码变成*要移出
                if (data != null && !data.getTouzhuhaoma().equals("*"))
                    dataList.add(data);
                getHongTouData(this.getIntent());
                adapter.setList(dataList);
                adapter.notifyDataSetChanged();
            } else {// 随机N注进来的
                goucaiData.setBeishu(1);
                goucaiData.setZhuihaoqishu(1);
                goucaiData.setCaipiaoId(cp.getId());
                random(randomNumber, 0);
            }
        }
        setSmoothSelection();
        setBottomValues();
        if (goucaiData.getCaipiaoId() != CaipiaoConst.ID_DALETOU) {
            findViewById(R.id.zhuijiatouzhulayout).setVisibility(View.GONE);
        } else {
            findViewById(R.id.zhuijiatouzhulayout).setVisibility(View.VISIBLE);
        }
        goucaiData.setZhuijiatouzhu(zhuijiatouzhu.isChecked());
    }

    // //快乐10分前一玩法中含有红投数据需要分离获取过来
    @SuppressWarnings("unchecked")
    private void getHongTouData(Intent data) {
        // 快乐10分前一数投和红投特殊处理
        listData = (List<TouzhuquerenData>) data
                .getSerializableExtra("listTouzhuDataHT");
        if (listData != null) {
            for (int i = 0; i < listData.size(); i++) {
                TouzhuquerenData dataSpecial = listData.get(i);
                initData(dataSpecial);
                dataList.add(0, dataSpecial);
            }
        }
    }

    private void initData(TouzhuquerenData data) {
        if (data == null)
            return;
        currentRandomCaipiao[0] = data.getCaipiaoId();
        currentRandomCaipiao[1] = data.getWfType();
        goucaiData.setBeishu(1);
        goucaiData.setZhuihaoqishu(1);
        goucaiData.setCaipiaoId(data.getCaipiaoId());
    }

    Handler handler = new Handler();
    Runnable run = new Runnable() {
        @Override
        public void run() {
            int position = listView.getCount() - 1;
            if (position >= 0) {
                try {
                    Method method = ListView.class.getMethod(
                            "smoothScrollToPosition", Integer.TYPE);
                    method.invoke(listView, 0);
                } catch (Exception e) {
                    listView.setSelection(0);
                }
            }
        }
    };

    private void setSmoothSelection() {
        handler.removeCallbacks(run);
        handler.postDelayed(run, 50);
    }

    private void loadViews() {
        zhuihaolayout = (LinearLayout) findViewById(R.id.zhuihaolayout);
        kuaishuzhuihao = (LinearLayout) findViewById(R.id.kuaishuzhuihao);
        zhinengbeitou = (LinearLayout) findViewById(R.id.zhinengbeitou);
        zhinengzhuihaolayout = (RelativeLayout) findViewById(R.id.zhinengzhuihaolayout);
        bottomlayout = (RelativeLayout) findViewById(R.id.bottomlayout);


        rightbtnmenu = (LinearLayout) findViewById(R.id.ll_back);

        shouxuanBtn = (LinearLayout) findViewById(R.id.shouxuan);// 手选
        jixuanBtn = (LinearLayout) findViewById(R.id.jixuan); // /机选一注
        faqihemai = (Button) findViewById(R.id.faqihemai);// /发起合买
        gouMaiBtn = (Button) findViewById(R.id.wanchengxuanhao);// 确定投注
        zhuNum = (TextView) findViewById(R.id.xuanhaotishi1);// /一共多少注
        totalMoney = (TextView) findViewById(R.id.xuanhaotishi2);// 一共多少钱
        qiShu = (TextView) findViewById(R.id.qishu);// 期數
        beiShu = (TextView) findViewById(R.id.beishu);// 倍數
        qishutext = (TextView) findViewById(R.id.qishutext);
        beishutext = (TextView) findViewById(R.id.beishutext);
        zhuijiatouzhu = (CheckBox) findViewById(R.id.zhuijiatouzhucheckBox); // /追加投注
        beishueditext = (EditText) findViewById(R.id.beishueditext);
        qishueditext = (EditText) findViewById(R.id.zhuiqishu);
        listView = (ListView) findViewById(R.id.listview);

        adapter = new TouzhuquerenAdapter(this);
        listView.setAdapter(adapter);
        ll_menu = (LinearLayout) findViewById(R.id.ll_menu);

        ll_menu.setVisibility(View.VISIBLE);
        ll_menu.setOnClickListener(this);

        title_back_right = (ImageView) findViewById(R.id.title_back_right);


//
        // 创建对话框
        exitDailog = new XuhaoExitDialog(this);
        setCusTomeTitle(cp.getName() + "投注确认");
        zhuiqione = (Button) findViewById(R.id.zhuiqione);
        zhuiqiTwo = (Button) findViewById(R.id.zhuiqitwo);
        zhuiqiThree = (Button) findViewById(R.id.zhuiqithree);
        try {
//            setKuaiSuZhuiHaoBtn();
        } catch (Exception e) {
        }
        beiNum = (TextView) findViewById(R.id.beinum);
        beiNum.setText(cp.getBeiShu() + "倍");
        moneytext = (TextView) findViewById(R.id.moneytext);
        if (cp.getMessage().split(":").length > 1)
            moneytext.setText(cp.getMessage().split(":")[1]);
        imageViewbg = (ImageView) findViewById(R.id.imageViewbg);
        stoplayout = (LinearLayout) findViewById(R.id.stoplayout);
        leijicheckBox = (CheckBox) findViewById(R.id.leijicheckBox);
        leijizhongchutext = (EditText) findViewById(R.id.leijizhongchutext);
        if (cp.getCaipiaoType() == 3) {
            findViewById(R.id.zhuihaoqishuly).setVisibility(View.GONE);
            findViewById(R.id.btnlayout).setVisibility(View.INVISIBLE);
        }
        setKuaiSuZhuiHaoBtn();
    }


    private void setKuaiSuZhuiHaoBtn() {
        // /非快开类
        if (!cp.isKuaikai()) {
            switch (cp.getId()) {
                case CaipiaoConst.ID_FUCAI15XUAN5:
                case CaipiaoConst.ID_TICAI20XUAN5:
                case CaipiaoConst.ID_FUCAI3D:
                case CaipiaoConst.ID_PAILIE5:
                case CaipiaoConst.ID_PAILIE3:

                    kuaiSuQiOne = 15;
                    kuaiSuQiTwo = 30;
                    kuaiSuQiThree = 50;
                    break;
                // 双色球特殊处理的
                case CaipiaoConst.ID_SHUANGSEQIU:

                    kuaiSuQiOne = 13;
                    kuaiSuQiTwo = 39;
                    kuaiSuQiThree = 50;
                    break;
                default:

                    kuaiSuQiOne = 13;
                    kuaiSuQiTwo = 39;
                    kuaiSuQiThree = 50;
                    break;
            }
            // 非快开彩种中福彩3d和排列3,5可以智能追号
            if (cp.getId() == CaipiaoConst.ID_FUCAI3D
                    || cp.getId() == CaipiaoConst.ID_PAILIE5
                    || cp.getId() == CaipiaoConst.ID_PAILIE3) {
                isCanZhiNengZhuihao = false;
            } else {
                isCanZhiNengZhuihao = false;
            }
        } else {
            isCanZhiNengZhuihao = false;
            if (cp.getIssue() != null) {
                if (cp.getId() == CaipiaoConst.ID_XINSHISHICAI
                        || cp.getId() == CaipiaoConst.ID_LAOSHISHICAI
                        || cp.getId() == CaipiaoConst.ID_KUAILE10FEN
                        || CaipiaoUtil.isKySj(cp.getId())
                        || cp.getId() == CaipiaoConst.ID_LUCKYCAR
                        ) {
                    try {
                        kuaiSuQiOne = cp.getIssueNum()
                                - Integer.parseInt(cp.getIssue().substring(
                                cp.getIssue().length() - 3)) + 1;
                    } catch (NumberFormatException e) {

                        e.printStackTrace();
                        kuaiSuQiOne = cp.getIssueNum();
                    }
                } else
                    try {
                        kuaiSuQiOne = cp.getIssueNum()
                                - Integer.parseInt(cp.getIssue().substring(
                                cp.getIssue().length() - 2)) + 1;
                    } catch (NumberFormatException e) {

                        e.printStackTrace();
                        kuaiSuQiOne = cp.getIssueNum();
                    }
            }

        }
    }


    private void setListeners() {
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                moveToEdit(arg2);
            }
        });
        zhuiqione.setOnClickListener(this);
        zhuiqiTwo.setOnClickListener(this);
        zhuiqiThree.setOnClickListener(this);
        exitDailog.setCallBack(this);
        rightbtnmenu.setOnClickListener(this);
        shouxuanBtn.setOnClickListener(this);
        faqihemai.setOnClickListener(this);
        jixuanBtn.setOnClickListener(this);
        gouMaiBtn.setOnClickListener(this);
        imageViewbg.setOnClickListener(this);
        zhuijiatouzhu.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (goucaiData.getCaipiaoId() == CaipiaoConst.ID_DALETOU) {
                    goucaiData.setZhuijiatouzhu(isChecked);
                    setBottomValues();
                    adapter.setPrice(isChecked ? 3 : CaipiaoConst.PRICE);
                    adapter.notifyDataSetChanged();
                }
            }
        });
//        layout.setOnResizeListener(this);
        qishueditext.setOnClickListener(this);
//        qishueditext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//
//                if (hasFocus) {
//                    kuaishuzhuihao.setVisibility(View.GONE);
//                    System.out.println("eeeeee1-----------");
//                    zhinengbeitou.setVisibility(View.GONE);
//                    changStopLayoutVisible();
//                } else {
//                    if (isCanZhiNengZhuihao) {
//                        zhinengbeitou.setVisibility(View.GONE);
//                        kuaishuzhuihao.setVisibility(View.GONE);
//                    }
//                }
//            }
//        });
        // qishueditext.setOnEditorActionListener(new
        // TextView.OnEditorActionListener(){
        // @Override
        // public boolean onEditorAction(TextView v, int actionId,
        // KeyEvent event) {
        //
        // if (actionId == EditorInfo.IME_ACTION_DONE) {
        // // 在这里编写自己想要实现的功能
        // isToday=false;
        // String text=qishueditext.getText().toString();
        // if(text!=null&&!TextUtils.isEmpty(text)&&Integer.valueOf(text)>1&&isCanZhiNengZhuihao){
        // String check = check();
        // if (check != null) {
        // showToast(check);
        // return false;
        // }
        // moveToZhiNengZhuiHao(Integer.valueOf(text));
        // }
        // }
        // return false;
        // }
        // });
        qishueditext.setSelection(0);
        qishueditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable v) {

                if (v.toString().trim().length() == 0 || v.toString() == "0") {
                    // qishueditext.setText("1");
                    changLayoutVisible();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (TextUtils.isEmpty(qishueditext.getText()) || TextUtils.isEmpty(qishueditext.getText().toString())) {

                    Toast.makeText(Activity_Touzhuqueren.this, "最小输入1", Toast.LENGTH_SHORT).show();
                }

                int max;
                if (!cp.isKuaikai()) {
                    max = kuaiSuQiThree;
                } else {
                    max = kuaiSuQiOne + cp.getIssueNum() * 2;
                }
                if (!TextUtils.isEmpty(qishueditext.getText()) && Integer.parseInt(qishueditext.getText().toString()) > max) {
                    Toast.makeText(Activity_Touzhuqueren.this, "最大输入" + max, Toast.LENGTH_SHORT).show();
                    qishueditext.setText(max + "");
                }

                changLayoutVisible();
                changStopLayoutVisible();
            }
        });

        beishueditext.setOnClickListener(this);
        beishueditext
                .setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if (hasFocus) {
                            if (cp.getId() == 10032 || cp.getId() == 10026) {
                                zhinengbeitou.setVisibility(View.GONE);
                                zhinengzhuihaolayout
                                        .setVisibility(View.VISIBLE);
                            } else {
                                zhinengbeitou.setVisibility(View.GONE);
                            }
                            kuaishuzhuihao.setVisibility(View.GONE);
                            stoplayout.setVisibility(View.GONE);

                        } else {
                            kuaishuzhuihao.setVisibility(View.GONE);

                            zhinengbeitou.setVisibility(View.GONE);
                            changStopLayoutVisible();
                        }
                    }
                });
        beishueditext.setSelection(0);
        beishueditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable v) {

                if (v.toString().trim() == "0"
                        || v.toString().indexOf("-") != -1) {
                    changLayoutVisible();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
//                if (TextUtils.isEmpty(beishueditext.getText()) || TextUtils.isEmpty(beishueditext.getText().toString())) {
//                    beishueditext.setHint("1");
//                }
//                if (Integer.parseInt(beishueditext.getText().toString()) <= 0) {
//
//                    beishueditext.setHint("1");
//                }

                if (!TextUtils.isEmpty(beishueditext.getText()) && Integer.parseInt(beishueditext.getText().toString()) > 9999) {

                    beishueditext.setText("9999");
                }


//                String sr = s.toString();
//                if (sr.equals("不同")) {
//                    return;
//                }
//                if (beishueditext.getSelectionStart() == 1) {
//                    String t = sr.substring(0, 1);
//                    if (t.equals("0")) {
//                        Toast.makeText(Activity_Touzhuqueren.this, "最小输入1",
//                                Toast.LENGTH_SHORT).show();
//                        beishueditext.setText("1");
//                        beishueditext.setSelection(1);
//                    } else {
//                        beishueditext.setText(t);
//                        beishueditext.setSelection(1);
//                    }
//                } else if (beishueditext.getSelectionStart() == 4
//                        || beishueditext.getSelectionStart() == 5) {
//                    if (Integer.parseInt(sr) > 9999) {
//                        Toast.makeText(Activity_Touzhuqueren.this, "最大输入9999",
//                                Toast.LENGTH_SHORT).show();
//                        beishueditext.setText("9999");
//                    } else
//                        beishueditext.setText(sr);
//                    beishueditext.setSelection(4);
//                }
                changLayoutVisible();
            }
        });
    }


    // /控制停止追号条件和快速追期的显示
    private void changStopLayoutVisible() {
        if (!isCanZhiNengZhuihao) {
            String qi = qishueditext.getText().toString();
            if (!TextUtils.isEmpty(qi) && Integer.parseInt(qi) > 1) {
                zhinengzhuihaolayout.setVisibility(View.GONE);
                zhinengzhuihaolayout.setVisibility(zhinengbeitou
                        .getVisibility());
                if (zhinengzhuihaolayout.getVisibility() == View.GONE)
                    stoplayout.setVisibility(View.GONE);
            } else {
                zhinengzhuihaolayout.setVisibility(View.VISIBLE);
                stoplayout.setVisibility(View.GONE);
            }
        }
    }

    private void changLayoutVisible() {
        if (beishueditext.getText().toString().equals("不同"))
            return;
        int b = 1;
        if (!TextUtils.isEmpty(beishueditext.getText().toString()))
            b = Integer.valueOf(beishueditext.getText().toString());
        int q = 1;
        if (!TextUtils.isEmpty(qishueditext.getText().toString()))
            q = Integer.valueOf(qishueditext.getText().toString());
        if (b > 1 || q > 1) {
            qiShu.setVisibility(View.VISIBLE);
            beiShu.setVisibility(View.VISIBLE);
            qishutext.setVisibility(View.VISIBLE);
            beishutext.setVisibility(View.VISIBLE);
        } else {
            qiShu.setVisibility(View.GONE);
            beiShu.setVisibility(View.GONE);
            qishutext.setVisibility(View.GONE);
            beishutext.setVisibility(View.GONE);
        }
        goucaiData.setZhuihaoqishu(q);
        goucaiData.setBeishu(b);

        setBottomValues();
    }

    // /跳到编辑模式
    public void moveToEdit(int position) {
        if (position >= 0 && position < dataList.size()) {
            TouzhuquerenData data = dataList.get(position);
            Intent intent;
            if (cp.getCaipiaoType() != 3) {
                intent = new Intent(Activity_Touzhuqueren.this, Activity_CaipiaoTouZhu.class);
                intent.putExtra("touzhudata", data);
            } else {
                intent = getIntent();
                intent.setClass(this, Activity_RenXuanQiuChang.class);
                intent.putExtra("gametouzhudata", data);
            }
            intent.putExtra("fromtouzhu", true);
            startActivityForResult(intent, 2);

            lastClickTouzhu = position;

        }
    }

    // 点击删除后，左边按钮的选中状态切换到非选中
    public void LeftMenuReselected() {
        issueStr = "";
        multipleStr = "";
        qishueditext.setText("");
    }

    public void setBottomValues() {
        // 当能够智能追号的彩种且追的期数不是默认的1,这些操作只是改变了注数
        if (isCanZhiNengZhuihao
                && !TextUtils.isEmpty(qishueditext.getText().toString())
                && Integer.parseInt(qishueditext.getText().toString()) > 1
                && multipleStr.length() > 0) {
            int total = 0;
            String[] beiNum = multipleStr.split(",");
            if (isSameBei) {
                beiShu.setText(goucaiData.getBeishu() + "");
                multipleStr = multipleStr.replace(beiNum[0],
                        goucaiData.getBeishu() + "");
                goucaiData.setMultipleStr(multipleStr);
            }
            beiNum = multipleStr.split(",");
            for (int i = 0; i < beiNum.length; i++) {
                total += Integer.parseInt(beiNum[i]);
            }
            zhuNum.setText(CaipiaoUtil.getTotalZhushu(dataList) + "");
            goucaiData.setAllMoney(total * 2
                    * CaipiaoUtil.getTotalZhushu(dataList));
            totalMoney.setText(goucaiData.getAllMoney() + "");
        } else {
            zhuNum.setText(CaipiaoUtil.getTotalZhushu(dataList) + "");
            // 这里不能用getTotalMoney()，
            long l1 = (long) CaipiaoUtil.getTotalZhushu(dataList);
            long l2 = (long) CaipiaoConst.PRICE;
            if (goucaiData.getCaipiaoId() == CaipiaoConst.ID_DALETOU
                    && goucaiData.isZhuijiatouzhu()) {
                l2 = (long) 3;
            }
            long l3 = (long) (goucaiData.getBeishu() * goucaiData
                    .getZhuihaoqishu());
            totalMoney.setText(l1 * l2 * l3 + "");
            beiShu.setText(goucaiData.getBeishu() + "");
            qiShu.setText(goucaiData.getZhuihaoqishu() + "");
        }
    }

    List<TouzhuquerenData> dataList = new ArrayList<TouzhuquerenData>();

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//            if (event.getAction() == KeyEvent.ACTION_DOWN
//                    && event.getRepeatCount() == 0) {
//                if (dataList != null && dataList.size() > 0) {
//                    exitDailog.show();
//                    if (cp.getCaipiaoType() != 3) {
//                        exitDailog.getCenterTextView().setText("是否保存已选号码?");
//                        exitDailog.getYesBtn().setText("保存");
//                        exitDailog.getNoBtn().setText("清除");
//                    } else {
//                        exitDailog.getCenterTextView().setText("是否放弃已选号码?");
//                        exitDailog.getYesBtn().setText("是");
//                        exitDailog.getNoBtn().setText("否");
//                    }
//                } else {
//                    finish();
//
//                    return super.onKeyDown(keyCode, event);
//                }
//            }
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    private int[] currentRandomCaipiao = new int[2];// 当前彩票，用来随机用的。

    private void setCurrentRandomCaipiao(int cpId, int wfType) {
        currentRandomCaipiao[0] = cpId;
        currentRandomCaipiao[1] = wfType;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (data != null) {
                if (requestCode == 1) {// 手选添加
                    int randomNumber = data.getIntExtra("randomnumber", 0);
                    if (randomNumber == 0) {
                        TouzhuquerenData touzhuquerendata = (TouzhuquerenData) data
                                .getSerializableExtra("touzhudata");
                        if (touzhuquerendata != null
                                && !touzhuquerendata.getTouzhuhaoma().equals(
                                "*"))
                            dataList.add(0, touzhuquerendata);
                        getHongTouData(data);
                        adapter.setList(dataList);
                        adapter.notifyDataSetChanged();
                        if (touzhuquerendata != null)
                            setCurrentRandomCaipiao(
                                    touzhuquerendata.getCaipiaoId(),
                                    touzhuquerendata.getWfType());
                        else if (listData != null)
                            setCurrentRandomCaipiao(listData.get(0)
                                    .getCaipiaoId(), listData.get(0)
                                    .getWfType());
                        setSmoothSelection();
                        // /手选操作了同时是通过机选几注来完成的
                    } else {
                        setCurrentRandomCaipiao(data.getIntExtra("intent_cpid",
                                cp.getId()), data.getIntExtra("intent_wftype",
                                cp.getCurrentWanfa().getType()));
                        random(randomNumber, 0);
                    }
                } else if (requestCode == 2) {// 修改
                    int randomNumber = data.getIntExtra("randomnumber", 0);
                    if (randomNumber == 0) {
                        TouzhuquerenData touzhuquerendata = (TouzhuquerenData) data
                                .getSerializableExtra("touzhudata");
                        if (lastClickTouzhu < dataList.size()) {
                            if (touzhuquerendata != null
                                    && !touzhuquerendata.getTouzhuhaoma()
                                    .equals("*"))
                                dataList.set(lastClickTouzhu, touzhuquerendata);
                            else
                                dataList.remove(lastClickTouzhu);
                            getHongTouData(data);
                            adapter.setList(dataList);
                            adapter.notifyDataSetChanged();
                            setCurrentRandomCaipiao(
                                    touzhuquerendata.getCaipiaoId(),
                                    touzhuquerendata.getWfType());
                        }
                    } else {
                        if (lastClickTouzhu < dataList.size()) {
                            setCurrentRandomCaipiao(data.getIntExtra(
                                    CaipiaoConst.KEY_INTENT_CAIPIAOID,
                                    cp.getId()), data.getIntExtra(
                                    CaipiaoConst.KEY_INTENT_WF_TYPE, cp
                                            .getCurrentWanfa().getType()));
                            dataList.remove(lastClickTouzhu);// 将修改的那个删除
                            random(randomNumber, lastClickTouzhu);
                        }
                    }
                } else if (requestCode == 3) {// 智能追号返回回来的处理
                    map.clear();
                    issueStr = data.getStringExtra("issueStr");
                    multipleStr = data.getStringExtra("multipleStr");
                    issuseIdStr = data.getStringExtra("issuseIdStr");

                    int totalSize = data.getIntExtra("totalSize", 0);
                    int totalmoney = data.getIntExtra("totalmoney", 0);
                    int leiJiNum = data.getIntExtra("leijinum", 0);
                    if (leiJiNum > 0) {
                        goucaiData.setDayutingzhiChecked(true);
                        goucaiData.setDayutingzhi(leiJiNum);
                        dayutingzhiChecked = true;
                        map.put("操作类型", "智能追号设置了停止条件");
                        CaipiaoUtil.youmengEvent(this,
                                YOUMENG_EVENT.new_touzhuqueren, map);
                    } else {
                        goucaiData.setDayutingzhiChecked(false);
                        goucaiData.setDayutingzhi(0);
                        dayutingzhiChecked = false;
                        map.put("操作类型", "智能追号没有设置停止条件");
                        CaipiaoUtil.youmengEvent(this,
                                YOUMENG_EVENT.new_touzhuqueren, map);
                    }
                    if (totalSize > 1) {
                        chargeBeiShu(multipleStr);
                        qiShu.setVisibility(View.VISIBLE);
                        beiShu.setVisibility(View.GONE);
                        qishutext.setVisibility(View.VISIBLE);
                        beishutext.setVisibility(View.GONE);
                        qiShu.setText(totalSize + "");
                        isEditMode = true;
                        totalMoney.setText(totalmoney + "");
                        qishueditext.setText(totalSize + "");
                        qishueditext.setSelection(qishueditext.getText()
                                .toString().length());
                        goucaiData.setZhuihaoqishu(totalSize);
                        goucaiData.setAllMoney(totalmoney);
                        goucaiData.setKaichutingzhi(data.getBooleanExtra(
                                "isStop", true));
                        map.put("操作类型", "有效地智能追号");
                        CaipiaoUtil.youmengEvent(this,
                                YOUMENG_EVENT.new_touzhuqueren, map);
                    } else {
                        beishueditext.setFocusable(true);
                        beishueditext.setText(multipleStr.substring(0,
                                multipleStr.length() - 1));
                        qiShu.setVisibility(View.GONE);
                        qishutext.setVisibility(View.GONE);
                        isEditMode = false;
                        qishueditext.setText("");
                        qishueditext.setInputType(InputType.TYPE_CLASS_NUMBER);
                        setBottomValues();
                        map.put("操作类型", "放弃了智能追号");
                        CaipiaoUtil.youmengEvent(this,
                                YOUMENG_EVENT.new_touzhuqueren, map);
                    }
                    goucaiData.setIssueIdStr(issuseIdStr);
                    goucaiData.setMultipleStr(multipleStr);
                    goucaiData.setIssueStr(issueStr);
                    return;
                }
                setBottomValues();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 判断追号期数每期所选择的倍数是否都一样
    private boolean isSameBei = true;

    private void chargeBeiShu(String multipleStr) {
        isSameBei = true;
        String str[] = multipleStr.split(",");
        beishueditext.setText(str[0]);
        goucaiData.setBeishu(Integer.parseInt(str[0]));
        beishueditext.setFocusable(true);
        for (int i = 1; i < str.length; i++) {
            if (!str[0].equals(str[i])) {
                beishueditext.setText("不同");
                goucaiData.setBeishu(0);
                isSameBei = false;
                beishueditext.setFocusable(false);
                return;
            }
        }
    }

    private boolean click = false;

    @Override
    public void onClick(View v) {
        map.clear();
//        // 当前处于删除模式，点击其他的任何按钮都要恢复正常状态
//        if (adapter.isDeleteMode()) {
//            title_back_right.setSelected(true);
//            adapter.setDeleteMode(!adapter.isDeleteMode());
//            adapter.notifyDataSetChanged();
//            return;
//        }
        switch (v.getId()) {
            case R.id.ll_menu:

                adapter.setDeleteMode(!adapter.isDeleteMode());
                adapter.notifyDataSetChanged();
                title_back_right.setSelected(!title_back_right.isSelected());

                L.d(title_back_right.isSelected() + "");
                break;
            case R.id.ll_back:
//                if (dataList != null && dataList.size() > 0) {
//                    exitDailog.show();
//                    if (cp.getCaipiaoType() != 3) {
//                        exitDailog.getCenterTextView().setText("是否保存已选号码?");
//                        exitDailog.getYesBtn().setText("保存");
//                        exitDailog.getNoBtn().setText("清除");
//                    } else {
//                        exitDailog.getCenterTextView().setText("是否放弃已选号码?");
//                        exitDailog.getYesBtn().setText("是");
//                        exitDailog.getNoBtn().setText("否");
//                    }
//                } else {
//                    if (exitDailog.isShowing())
//                        exitDailog.dismiss();
//                    finish();
//                    pullDownActivityAnim();
//                }
                // 统计
                finish();
                map.put("操作类型", "关闭");
                CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_touzhuqueren, map);
                break;
            case R.id.shouxuan:// //继续投注
                if (cp.getCaipiaoType() != 3) {
                    intent = new Intent(Activity_Touzhuqueren.this,
                            Activity_CaipiaoTouZhu.class);
                    TouzhuquerenData data = new TouzhuquerenData();
                    data.setCaipiaoIdAndWanfaType(currentRandomCaipiao[0],
                            currentRandomCaipiao[1]);
                    data.allNotSelected = true;
                    intent.putExtra("touzhudata", data);
                    intent.putExtra("fromtouzhu", true);
                    startActivityForResult(intent, 1);
                    //胜负14场和任选9场
                } else {
                    intent = this.getIntent();
                    intent.setClass(this, Activity_RenXuanQiuChang.class);
                    intent.putExtra("fromtouzhu", true);
                    startActivityForResult(intent, 1);
                }
                pullUpActivityAnim();

                break;
            case R.id.jixuan:
                random(1, 0);

                break;
            case R.id.faqihemai:
                String check = check();
                if (check != null) {
                    showToast(check);
                    return;
                }


                goucaiData.setCaipiaoId(dataList.get(0).getCaipiaoId());
                goucaiData.setTotalZhushu(CaipiaoUtil.getTotalZhushu(dataList));
                goucaiData.setSubmitString(adapter.getSubmitString());
                // setDayutingzhi();
                Intent inte = new Intent(this, TogeterBuyActivity.class);
                CaipiaoApplication.getInstance().setTouzhuHaomaList(dataList);
                //胜负14场 ，任九场奖期和奖期id字段设置下
                if (cp.getCaipiaoType() == 3) {
                    goucaiData.setIssueIdStr(getIntent().getStringExtra("curIssueId"));
                    goucaiData.setIssueStr(getIntent().getStringExtra("issueStr"));
                }
                inte.putExtra("goumaidata", goucaiData);
                startActivity(inte);
                pullUpActivityAnim();

                break;
            case R.id.wanchengxuanhao:
                issueStr = qishueditext.getText().toString();
                check = check();
                if (check != null) {
                    showToast(check);
                    return;
                }

                goucaiData.setCaipiaoId(dataList.get(0).getCaipiaoId());
                goucaiData.setTotalZhushu(CaipiaoUtil.getTotalZhushu(dataList));
                goucaiData.setSubmitString(adapter.getSubmitString());
                // setDayutingzhi();
                CaipiaoApplication.getInstance().setTouzhuHaomaList(dataList);
                //胜负14场 ，任九场奖期和奖期id字段设置下
                if (cp.getCaipiaoType() == 3) {
                    goucaiData.setIssueIdStr(getIntent().getStringExtra("curIssueId"));
                    goucaiData.setIssueStr(getIntent().getStringExtra("issueStr"));
                }

                if (TextUtils.isEmpty(getSession())) {
                    startActivity(new Intent(Activity_Touzhuqueren.this, LoginActivity.class));
                } else {

                    showLoading("订单提交中");
                    BuyConfirm buyConfirm = new BuyConfirm(cp, false, goucaiData, -1, Control.getInstance().getBasketballManager().getDanorguo(), Activity_Touzhuqueren.this, new IBuyCallBack() {
                        @Override
                        public void buyComplete() {
                            hideLoading();
                        }

                        @Override
                        public void buyCancle() {
                            hideLoading();
                        }
                    });
                    buyConfirm.submitData();

                }


                break;
            case R.id.zhuiqishu:
                if (!isEditMode) {
                    qishueditext.requestFocus();
                    if (!TextUtils.isEmpty(qishueditext.getText().toString())) {
                        qishueditext.setSelection(qishueditext.length());
                    }
                } else
                    closeSoftKeybord();
                if (!TextUtils.isEmpty(qishueditext.getText().toString())
                        && Integer.parseInt(qishueditext.getText().toString()) > 1
                        && isCanZhiNengZhuihao) {
                    check = check();
                    if (check != null) {
                        showToast(check);
                        return;
                    }
                }
                break;
            case R.id.beishueditext:
                beishueditext.requestFocus();
                if (!beishueditext.getText().equals("1"))
                    beishueditext.setSelection(beishueditext.length());
                break;
            case R.id.zhuiqione:

                if (isCanZhiNengZhuihao) {
                    check = check();
                    if (check != null) {
                        showToast(check);
                        return;
                    }

                    if (cp.isKuaikai())
                        isToday = true;
                    else
                        isToday = false;

                } else {
                    qishueditext.setText(kuaiSuQiOne + "");
                    qishueditext.setSelection(2);
                }

                break;
            case R.id.zhuiqitwo:
                if (isCanZhiNengZhuihao) {
                    check = check();
                    if (check != null) {
                        showToast(check);
                        return;
                    }

                    isToday = false;

                } else {
                    qishueditext.setText(kuaiSuQiTwo + "");
                    qishueditext.setSelection(2);
                }

                break;
            case R.id.zhuiqithree:
                if (isCanZhiNengZhuihao) {
                    check = check();
                    if (check != null) {
                        showToast(check);
                        return;
                    }

                    isToday = false;

                } else {
                    qishueditext.setText(kuaiSuQiThree + "");
                    qishueditext.setSelection(qishueditext.length());
                }

                break;
            case R.id.imageViewbg:
                closeSoftKeybord();
                break;
            default:
                break;
        }
    }

    private boolean isEditMode = false;


    AbsWanfa tempWf;// 临时玩法记录

    /**
     * 机选N注号码
     */
    private void random(int number, int position) {
        TouzhuquerenData data = new TouzhuquerenData();
        if (cp.getCaipiaoType() != 3)
            setCurrentRandomCaipiao(cp.getId(), cp.getCurrentWanfa().getType());
        for (int i = 0; i < number; i++) {
            if (cp.getCaipiaoType() != 3) {
                data = cp.getCurrentWanfa().randomTouzhu();
                // 胆拖机选用默认玩法的机选来代替
                if (data == null
                        && cp.getCurrentWanfa().getName().indexOf("胆拖") != -1) {
                    tempWf = cp.getCurrentWanfa();// 备份下当前玩法
                    cp.setCurrentWanfa(cp.getWanfaList().get(0));
                    data = cp.getCurrentWanfa().randomTouzhu();
                    cp.setCurrentWanfa(tempWf);// 恢复当前玩法
                    data.setName(cp.getWanfaList().get(0).getName());
                }
                //胜负14场，任选9场
            } else {
                data.setCaipiaoIdAndWanfaType(cp.getId(), -1);
                data.setName("");
                data.setTouzhuhaoma(getRandomStr());
                data.setZhushu(1);
            }
            if (data != null) {
                dataList.add(position, data);
            }
        }
        adapter.setList(dataList);
        adapter.notifyDataSetChanged();
        setSmoothSelection();
        setBottomValues();
        // 统计

    }

    /**
     * 胜负14场，任选9场机选号码
     */
    private String getRandomStr() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 14; i++)
            buf.append("*");
        int[] randomArray = CaipiaoUtil.getRandomArray(14, cp.getQianquMinCount());
        for (int i = 0; i < randomArray.length; i++) {
            buf.setCharAt(randomArray[i], TOUZHUSTR[CaipiaoUtil.getRandomArray(3, 1)[0]]);
        }
        return buf.toString();
    }

    @SuppressLint("StringFormatMatches")
    private String check() {
        String text = leijizhongchutext.getText().toString();
        goucaiData.setDayutingzhiChecked(leijicheckBox.isChecked());
        if (leijicheckBox.isChecked() && !isCanZhiNengZhuihao) {
            if (text.trim().length() == 0 || Integer.parseInt(text) < 1) {
                return "累计中出金额不能小于1";
            } else
                goucaiData.setDayutingzhi(Integer.parseInt(text));
        }
        String s = null;
        // if(cp.getIssue()==null||cp.getIssue().equals(""))
        // return "当前奖期不能为空";
        if (CaipiaoUtil.getTotalZhushu(dataList) == 0) {
            return getString(R.string.qingxuanzetouzhuhanma);
        }
        Caipiao cp = CaipiaoFactory.getInstance(this).getCaipiaoById(
                dataList.get(0).getCaipiaoId());
        if (Integer.parseInt(totalMoney.getText().toString()) > cp
                .getMaxTouzhuCount() * CaipiaoConst.PRICE) {
            return String.format(getString(R.string.bunengchaoguo),
                    cp.getMaxTouzhuCount() * CaipiaoConst.PRICE);
        }
        if (cp.getId() == CaipiaoConst.ID_DALETOU && zhuijiatouzhu.isChecked()) {
            if (adapter.getSubmitString().contains(URLSuffix.sxPoly)) {
                s = getString(R.string.shengxiaobunengzhuijia);
            }
        }

        return s;
    }

    @Override
    public void reBack() {

        map.clear();
        String str = exitDailog.getCenterTextView().getText().toString();
        if (exitDailog.isShowing())
            exitDailog.dismiss();
        if (str.equals("所追期数中存在过期期数,是否修改?")) {


        } else if (cp.getCaipiaoType() == 3) {
            finish();
            pullDownActivityAnim();
        } else {
            // 保存所选的号码
            String vs = "";
            TouzhuquerenData d;
            if (CaipiaoUtil.isKySj(cp.getId())) {
                for (int i = 0; i < dataList.size(); i++) {
                    d = dataList.get(i);
                    if (d.getOldName().contains("猜顺子")
                            || d.getOldName().contains("三同号通选"))
                        vs += d.getOldName() + "," + d.getWfType() + ","
                                + d.getTouzhuhaoma().replace(",", " ") + ","
                                + d.getZhushu() + "#";
                    else
                        vs += d.getOldName() + "," + d.getWfType() + ","
                                + d.getTouzhuhaoma() + "," + d.getZhushu()
                                + "#";
                }
            } else {
                for (int i = 0; i < dataList.size(); i++) {
                    d = dataList.get(i);
                    vs += d.getOldName() + "," + d.getWfType() + ","
                            + d.getTouzhuhaoma() + "," + d.getZhushu() + ","
                            + getBtnListStr(d.getListTouzuList()) + "#";
                }
            }
            editor.putString(cp.getId() + "haoma", vs);

            editor.commit();
            finish();
            pullDownActivityAnim();

        }

    }

    private String getBtnListStr(List<List<Boolean>> listTouzuList) {
        String booleanStr = "";
        for (int i = 0; i < listTouzuList.size(); i++) {
            String str = "";
            for (int t = 0; t < listTouzuList.get(i).size(); t++) {
                str += listTouzuList.get(i).get(t) + " ";
            }
            booleanStr += str + "+";
        }
        return booleanStr;
    }

    @Override
    public void close() {

        String str = exitDailog.getCenterTextView().getText().toString();
        if (exitDailog.isShowing())
            exitDailog.dismiss();
        if (isFromBaoCun) {
            editor.putString(cp.getId() + "haoma", "");
            editor.commit();
        }
        if (cp.getCaipiaoType() == 3)
            return;
        if (!str.equals("所追期数中存在过期期数,是否修改?")) {
            finish();
            pullDownActivityAnim();
        }

    }

}
