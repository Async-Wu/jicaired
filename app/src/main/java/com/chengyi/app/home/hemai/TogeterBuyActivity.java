package com.chengyi.app.home.hemai;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.listener.IBuyCallBack;
import com.chengyi.app.user.BuyConfirm;
import com.chengyi.app.model.model.GoumaiData;
import com.chengyi.app.user.login.LoginActivity;
import com.chengyi.app.util.CaipiaoUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 * <div> <span style="color: wheat;">1111</span><span style="color: red;">111</span></div>
 */
public class TogeterBuyActivity extends BaseActivity implements View.OnClickListener {

    private int flag = 0;
    EditText woyaorengou;
    EditText zuidirengou;
    EditText woyaobaodi;
    EditText fanganmiaoshu;
    EditText yongJin;//盈利佣金
    Button querentouzhu;
    LinearLayout btnclose;
    Button gongKai, genDan, jieZhi;
    GoumaiData goumaiData;
    boolean isJingcai = false;
    boolean isBasketball = false;
    TextView content;
    int wfindex = -1;
    long totalMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_faqihemai);
        loadViews();
        goumaiData = (GoumaiData) getIntent().getSerializableExtra(
                "goumaidata");
        //是否是竞彩足球
        isJingcai = getIntent().getBooleanExtra("isJingcai", false);

        isBasketball = getIntent().getBooleanExtra("isBasketball", false);
        wfindex = getIntent().getIntExtra("wfindex", -1);
        flag = getIntent().getIntExtra("flag", 0);
        setTips();
    }

    int minwoyao = 0;///最少认购的钱数

    private void setTips() {
        if (goumaiData.getBeishu() > 0)
            totalMoney = goumaiData.getTotalMoney();
        else
            totalMoney = goumaiData.getAllMoney();
        setCusTomeTitle(getCurrentCaipiao().getName() + "_发起合买");
        String contextStr = String.format(getString(R.string.nzhunbeinqi), goumaiData.getTotalZhushu() + "", goumaiData.getBeishu() + "",
                goumaiData.getZhuihaoqishu() + "")
                + String.format(getString(R.string.gongnyuan),
                totalMoney + "");
        if (goumaiData.getBeishu() == 0)
            contextStr = contextStr.replace("0倍", "");
        content.setText(contextStr);
        int temp = (int) totalMoney * 5;
        int extra = temp % 100 == 0 ? 0 : 1;
        minwoyao = temp / 100 + extra;// 余数进位
        if (minwoyao < 1) {
            minwoyao = 1;
        }
        int minBaodiJine = 0;
        minBaodiJine = (int) Math.ceil(totalMoney * 0.1);
        woyaorengou.setHint("最少认购" + minwoyao + "元");
        woyaobaodi.setHint("若保底,最少保底" + minBaodiJine + "元");
    }

    private void loadViews() {
        setupUI(findViewById(R.id.faqihemai_main_layout));

        btnclose = (LinearLayout) findViewById(R.id.ll_back);//关闭
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        querentouzhu = (Button) findViewById(R.id.querentouzhu);//确定
//		zhanYouLv=(TextView) findViewById(R.id.zhanyoulv);//占有率
//		minRenGou=(TextView) findViewById(R.id.minrengouyuan);//最少认购
//		minBaodi=(TextView) findViewById(R.id.baodiyuan);//最少保底
        content = (TextView) findViewById(R.id.content);//方案内容
        woyaorengou = (EditText) findViewById(R.id.woyaorengouedit);//我要认购
        woyaorengou.requestFocus();
        zuidirengou = (EditText) findViewById(R.id.zuidiexite);//最低认购
        woyaobaodi = (EditText) findViewById(R.id.baodijine);//我要保底
        yongJin = (EditText) findViewById(R.id.yongjinedit);
        fanganmiaoshu = (EditText) findViewById(R.id.fanganmiaoshuEdit);///方案描述
        fanganmiaoshu.setHint(Html.fromHtml("<span style=\"color: #4b4a4b;\">方案描述:</span><span style=\"color: #b0b0b0;\">众人拾柴火焰高</span>"));

        gongKai = (Button) findViewById(R.id.gongkaiBtn);
        gongKai.setSelected(true);
        genDan = (Button) findViewById(R.id.gendanbtn);
        jieZhi = (Button) findViewById(R.id.jiezhibtn);
        setListeners();
    }

    private void setListeners() {
        querentouzhu.setOnClickListener(this);
        btnclose.setOnClickListener(this);
        gongKai.setOnClickListener(this);
        genDan.setOnClickListener(this);
        jieZhi.setOnClickListener(this);
        woyaorengou.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String sr = s.toString();
                if (!TextUtils.isEmpty(sr) && sr.indexOf("-") == -1) {
                    if (Integer.valueOf(sr) > totalMoney) {
                        showToast(getString(R.string.rengoujinebunengchaoguozongjine));
                    }
                }
            }
        });
    }

    public String myPercent(int y, long z) {
        String baifenbi = "";//接受百分比的值
        double baiy = y * 1.0;
        double baiz = z * 1.0;
        double fen = baiy / baiz;
        DecimalFormat df1 = new DecimalFormat("##.00%");    //##.00%   百分比格式，后面不足2位的用0补齐
        baifenbi = df1.format(fen);
        return baifenbi;
    }

    private int getGongkai() {
        if (gongKai.isSelected()) {
            return 1;
        } else if (genDan.isSelected()) {
            return 3;
        } else {
            return 4;
        }
    }

    private String check() {

        if (TextUtils.isEmpty(woyaorengou.getText().toString())) {
            return getString(R.string.rengoubunengweikong);
        }
        if (!CaipiaoUtil.isZhengZhengshu(woyaorengou.getText().toString())) {
            return getString(R.string.rengoujinegeshibuzhengque);
        }
        try {
            if (Integer.valueOf(woyaorengou.getText().toString()) > totalMoney) {
                return getString(R.string.rengoujinebunengchaoguozongjine);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getString(R.string.rengoujinegeshibuzhengque);
        }
        try {
            if (Integer.valueOf(woyaorengou.getText().toString()) < minwoyao) {
                return String.format(
                        getString(R.string.rengoujinebunengshaoyunyuan), minwoyao);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getString(R.string.rengoujinegeshibuzhengque);
        }

        if (TextUtils.isEmpty(zuidirengou.getText().toString().trim())) {
            return getString(R.string.zuidirengoubunengweikong);
        }
        if (!CaipiaoUtil.isZhengZhengshu(zuidirengou.getText().toString())) {
            return getString(R.string.zuidirengougeshibuzhengque);
        }
        long maxValue = totalMoney - Long.parseLong(woyaorengou.getText().toString());
        try {
            if (maxValue != 0 && Integer.valueOf(zuidirengou.getText().toString()) > maxValue) {
                return "最低认购金额不能大于" + maxValue;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getString(R.string.zuidirengougeshibuzhengque);
        }
        if (zuidirengou.getText().toString().equals("0"))
            return "最低认购金额不能小于0";

        ///检查保底数据的格式
        if (!TextUtils.isEmpty(woyaobaodi.getText().toString()) && !woyaobaodi.getText().toString().equals("0")) {
            ///检查格式
            if (!CaipiaoUtil.isZhengZhengshu(woyaobaodi.getText().toString())) {
                return "保底金额格式不正确";
            } else if (Integer.valueOf(woyaobaodi.getText().toString().trim()) > maxValue) {
                return "如果要保底,金额不能大于" + maxValue + "元";
            }
            int minValue = (int) Math.ceil(totalMoney * 0.1);
            if (Integer.valueOf(woyaobaodi.getText().toString().trim()) < minValue) {
                return "如果要保底,金额不能小于" + minValue + "元";
            }
        }
//		if (!TextUtils.isEmpty(woyaobaodi.getText().toString())
//				&& CaipiaoUtil.isZhengZhengshu(woyaobaodi.getText().toString())) {
//			try {
//
//				if (Integer.valueOf(woyaobaodi.getText().toString().trim()) > maxValue) {
//					return "如果要保底,金额不能大于"+maxValue+"元";
//				}
//
//				if (Integer.valueOf(woyaobaodi.getText().toString().trim()) <minValue) {
//					return "如果要保底,金额不能小于"+minValue+"元";
//				}
//
//			} catch (Exception e) {
//				e.printStackTrace();
//				return getString(R.string.baodijinegeshibuzhengque)
//						+ getString(R.string.huobunengwei0);
//			}
//		}
        if (!TextUtils.isEmpty(yongJin.getText().toString())) {
            //判断盈利佣金
            if (yongJin.getText().toString().substring(0, 1).equals("0") && yongJin.getText().toString().length() > 1) {
                return getString(R.string.yingliyongjinbuzhengque);
            }
            if (Integer.valueOf(yongJin.getText().toString()) > 10) {
                return "盈利佣金百分比不能大于10%";
            }
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        closeSoftKeybord();
        switch (v.getId()) {
            case R.id.querentouzhu:
                String check = check();
                if (check != null) {
                    showToast(check);
                    return;
                }


                if (!TextUtils.isEmpty(woyaobaodi.getText().toString()))
                    goumaiData.setWoyaobaodi(Integer.valueOf(woyaobaodi.getText().toString()));
                if (!TextUtils.isEmpty(yongJin.getText().toString()))
                    goumaiData.setYingliyongjin(Integer.valueOf(yongJin.getText().toString()));
                goumaiData.setMiaoshu(fanganmiaoshu.getText().toString());
                goumaiData.setShifougongkai(getGongkai());
                goumaiData.setWoyaorengou(woyaorengou.getText().toString());
                goumaiData.setZuidirengou(zuidirengou.getText().toString());


                if (TextUtils.isEmpty(getSession())) {
                    startActivity(new Intent(TogeterBuyActivity.this, LoginActivity.class));
                } else {
                    CaipiaoApplication.getInstance().setTouzhuHaomaList(CaipiaoApplication.getInstance().getTouzhuHaomaList());
                    //Caipiao caipiao, boolean isLucky, GoumaiData goumaiData, int wfIndex, int flag, Context context
                    showLoading("订单提交中");
                    BuyConfirm buyConfirm = new BuyConfirm(getCurrentCaipiao(), false, goumaiData, wfindex, flag, TogeterBuyActivity.this, new IBuyCallBack() {
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
                    hideLoading();
                }

                String lotteryName = getCurrentCaipiao().getName();
                if (!TextUtils.isEmpty(lotteryName)) {
                    List<String> tags = new ArrayList<String>();
                    tags.add(lotteryName);
                }
                // end
                break;
            case R.id.ll_back:
                finish();
                pullDownActivityAnim();
                break;
            case R.id.gongkaiBtn:
                genDan.setSelected(false);
                jieZhi.setSelected(false);
                gongKai.setSelected(true);
                break;
            case R.id.gendanbtn:
                genDan.setSelected(true);
                jieZhi.setSelected(false);
                gongKai.setSelected(false);
                break;
            case R.id.jiezhibtn:
                genDan.setSelected(false);
                jieZhi.setSelected(true);
                gongKai.setSelected(false);
                break;
        }
    }

    public void setupUI(View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }

            });
        }
        // If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


}
