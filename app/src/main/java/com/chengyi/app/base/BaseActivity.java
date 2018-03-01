package com.chengyi.app.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import android.view.*;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.jingji.guanyajun.CartGuanPopAdapter;
import com.chengyi.app.jingji.six.PopMode;
import com.chengyi.app.jingji.six.SixPopAdapter;
import com.chengyi.app.listener.AllMenuListener;
import com.chengyi.app.listener.BtnSelectedListener;
import com.chengyi.app.listener.IBottomList;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.model.param.SPKey;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.num.lottery.Activity_CaipiaoTouZhu;
import com.chengyi.app.num.lottery.Activity_Touzhuqueren;
import com.chengyi.app.num.lottery.LinearPopAdapter;
import com.chengyi.app.util.*;
import com.chengyi.app.view.widget.OnWheelChangedListener;
import com.chengyi.app.view.widget.OnWheelScrollListener;
import com.chengyi.app.view.widget.WheelView;
import com.chengyi.app.view.widget.XuanhaoLinearLayout;
import com.chengyi.app.view.widget.adapters.ArrayWheelAdapter;
import com.chengyi.app.view.widget.adapters.NumericWheelAdapter;
import com.chengyi.app.web.WebViewActivity;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BaseActivity extends FragmentActivity implements AllMenuListener,
        XuanhaoLinearLayout.FinishChoseListener, View.OnClickListener {
    protected int begin = 0;

    protected boolean isActive = true;
    public HashMap<String, String> map = new HashMap<>();
    private TextView tv_title;
    private List<String> guoGuan1 = new ArrayList<>();
    private List<String> guoGuan2 = new ArrayList<>();

    protected void setBegin(int begin) {
        this.begin = begin;
    }

    protected int getBegin() {
        return begin;
    }

    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    protected SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    protected SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
    protected SimpleDateFormat dateFormatInTime = new SimpleDateFormat("HH:mm");
    public String moneyReg = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";

    public String ZHANGHAO_RULE = "^([a-z|A-Z]+|[ \\u4e00-\\u9fa5]+|[0-9]+|[_|_]+)+$";
    public String cities[][];
    protected String[] provinceStr, bankStr;
    protected Animation mShowAction;
    protected Animation mHiddenAction;
    protected Animation am;
    protected Animation am1;
    private LinearLayout back;
    protected SharedPreferences basePreferences;
    protected AnimationSet set;
    protected PopupWindow wheelViewBankPop;
    protected DecimalFormat df = new DecimalFormat("0.00");



    private View ll_pop;


    protected List<PopMode> pop_list = new ArrayList<>();
    private SixPopAdapter sixPopAdapter;
    private RecyclerView rv_pop;


    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    public void setBack() {
        back = (LinearLayout) findViewById(R.id.ll_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setCusTomeTitle(String s) {
        s =
                s.replace(getCurrentCaipiao().getName(), "").replaceAll("-", "").replaceAll("_", "");

        s = s.replace("普通", "").replace("胆拖", "");
        if (TextUtils.isEmpty(s)) {
            s = getCurrentCaipiao().getName();
        }
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setOnClickListener(this);
        tv_title.setText(s);


    }


    protected void disPop(ImageView ivAnim) {
        if (popPop != null && popPop.isShowing()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            popPop.dismiss();
            AppUtil.startResRotateAnim(ivAnim);

        }
    }

    protected PopupWindow popPop;

    protected PopupWindow getPop(final ImageView ivAnim) {

        if (ll_pop == null) {
            ll_pop = LayoutInflater.from(this).inflate(getCurrentCaipiao().getResource(), null, false);

        }
        if (popPop == null) popPop = new PopupWindow(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        popPop.setFocusable(true);
        popPop.setOutsideTouchable(true);


        if (rv_pop == null) {

            rv_pop = (RecyclerView) ll_pop.findViewById(R.id.rv_pop_six);
        }
        if (pop_list != null && pop_list.size() < 3) {

            rv_pop.setLayoutManager(new GridLayoutManager(this, pop_list.size()));

        } else {

            rv_pop.setLayoutManager(new GridLayoutManager(this, 3));

        }

        Collections.sort(pop_list, new Comparator<PopMode>() {
            @Override
            public int compare(PopMode popMode, PopMode t1) {
                return popMode.getIssuId() - t1.getIssuId();
            }
        });
        sixPopAdapter = new SixPopAdapter(this, pop_list);
        rv_pop.setAdapter(sixPopAdapter);
        popPop.setContentView(ll_pop);
        popPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AppUtil.startResRotateAnim(ivAnim);

            }
        });
        return popPop;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



settinglanguage();

        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);
        mShowAction.setFillAfter(true);
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mHiddenAction.setDuration(300);
        mHiddenAction.setFillAfter(true);
        am = new AlphaAnimation(1.0f, 0.0f);
        am.setDuration(100);
        am.setFillAfter(true);
        am1 = new AlphaAnimation(0.0f, 1.0f);
        am1.setDuration(200);
        set = new AnimationSet(true);
        set.addAnimation(am);
        set.addAnimation(mHiddenAction);
        set.setFillAfter(true);
        provinceStr = this.getResources().getStringArray(R.array.province_item);
        bankStr = this.getResources().getStringArray(R.array.yinhang);
        cities = AppUtil.getCity(this);


    }

    protected void settinglanguage() {
        basePreferences = CaipiaoUtil.getCpSharedPreferences(this);
        String l= basePreferences.getString("local","");

        switch (l){
            case "zh":
                switchLanguage(Locale.CHINA);
                break;
            case "en":
                switchLanguage(Locale.ENGLISH);
                break;
            case "km":
                switchLanguage(new Locale("km","khmer"));
                break;
            default:
                switchLanguage(Locale.getDefault());
                break;
        }
    }

    protected void showToast(int msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }


    protected boolean isSave() {
        return basePreferences.getBoolean("save", false);
    }


    protected void setSave(boolean f) {
        Editor editor = basePreferences.edit();
        editor.putBoolean("save", f);
        editor.commit();



    }


    protected void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //

    // 必须紧挨在startActivity或finish之后调用
    public void pullDownActivityAnim() {
        try {
            Method method = this.getClass().getMethod(
                    "overridePendingTransition", int.class, int.class);
            if (method != null)
                method.invoke(this, R.anim.push_down_in, R.anim.push_down_out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected ProgressDialog pd;

    public void showLoading(String s) {
        try {
            if (pd == null) {

                pd = new ProgressDialog(this);

                pd.setCancelable(false);
                if (s.equals(""))
                    pd.setMessage(getString(R.string.loading));
                else
                    pd.setMessage(s);
            }
            if (!pd.isShowing()) {
                pd.show();
                handler.postDelayed(closeHandler, 5000);
            }
        } catch (Exception e) {
        }
    }

    private Runnable closeHandler = new Runnable() {
        @Override
        public void run() {
            pd.setCancelable(true);
        }
    };

    public void hideLoading() {
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
                handler.removeCallbacks(closeHandler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public RequestParams getRequestParams() {

        return new RequestParams();
    }


    public String getSession() {
        return CaipiaoApplication.getInstance().getSession();
    }

    public String getUserName() {
        if (TextUtils.isEmpty(getSession())) {
            return "";
        } else {
            return basePreferences.getString("nickname", "");
        }

    }

    public String getPwd() {
        if (TextUtils.isEmpty(getSession())) {
            return "";
        } else {
            String key = basePreferences.getString(SPKey.login_info_ecrpt_key, "");
            return Ecrpt.Decrypt(basePreferences.getString(SPKey.login_password, ""), key);
        }


    }


    /**
     * add for newframe
     */
    public Caipiao getCurrentCaipiao() {
        return CaipiaoApplication.getInstance().getCurrentCaipiao();
    }

    // 必须紧挨在startActivity或finish之后调用,使用反射机制加载切换activity的动画
    public void pullUpActivityAnimFromMain() {

    }

    public void pullUpActivityAnim() {

    }


    public boolean checkResult(String r) {
        JSONObject result = JSON.parseObject(r);
        if (result.getIntValue(URLSuffix.flag) == 0) {
            String errorMsg = result.getString(URLSuffix.errorMessage);
            if (TextUtils.isEmpty(errorMsg)) {
                showToast(R.string.error);
            } else {
                showToast(errorMsg);
            }
            return false;
        }
        return true;
    }

    @Override
    public void moveToTubiao() {

        String url;
        String title = "";
        if (getCurrentCaipiao() != null) {
            title = getCurrentCaipiao().getName() + "_" + getString(R.string.zst);
            url = IP.ZST_IP + getCurrentCaipiao().getId() + "#bt=" + getCurrentCaipiao().getId();
        } else {
            url = IP.ZST_IP + 0 + "#bt=" + 0;
        }

        startActivity(new Intent(getBaseContext(), WebViewActivity.class).putExtra("title", title)
                .putExtra("url", url /*+ "?" + "&appVersion=" + CaipiaoConst.APPVERSION + "&version=" + CaipiaoConst.VERSION + "&requestType=" + CaipiaoConst.REQUESTTYPE)*/));


    }

    @Override
    public void moveToWanFa() {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("fromFlag", 1);
        if (getCurrentCaipiao() != null) {
            intent.putExtra("url", IP.IP + "news/" + CaipiaoUtil.getCpId(getCurrentCaipiao().getId()) + ".htm" + "?requestType=4" + "&appVersion=" + CaipiaoConst.APPVERSION + "&version=" + CaipiaoConst.VERSION + "&requestType=" + CaipiaoConst.REQUESTTYPE);
        } else {
            intent.putExtra("url", IP.IP + "news/" + 0 + ".htm" + "?requestType=4" + "&appVersion=" + CaipiaoConst.APPVERSION + "&version=" + CaipiaoConst.VERSION + "&requestType=" + CaipiaoConst.REQUESTTYPE);
        }

        startActivity(intent);
    }


    @Override
    public void randomChose(int num, Context mContext) {
        if (mContext instanceof Activity_CaipiaoTouZhu) {
            Activity_CaipiaoTouZhu cpdt = (Activity_CaipiaoTouZhu) mContext;
            cpdt.setClearAllSelected(true);
            if (cpdt.isFromTouzhuqueren()) {// 判断是否来自投注确认
                cpdt.setRandomNum(num);
                cpdt.setFinishChoose(true);// 一定在cpdt.finish();前，因为cpdt.finish();中会用到
                cpdt.finish();
                return;
            } else {
                Intent intent = new Intent(this, Activity_Touzhuqueren.class);
                intent.putExtra("randomnumber", num);
                startActivity(intent);

            }
        }

    }

    @Override
    public void finishiChose(List<TouzhuquerenData> listData,
                             TouzhuquerenData data, Context mContext) {
        if (mContext instanceof Activity_CaipiaoTouZhu) {
            Activity_CaipiaoTouZhu cpdt = (Activity_CaipiaoTouZhu) mContext;
            cpdt.setClearAllSelected(true);
            if (cpdt.isFromTouzhuqueren()) {// 判断是否来自投注确认
                cpdt.setRandomNum(0);
                cpdt.setFinishChoose(true);// 一定在cpdt.finish();前，因为cpdt.finish();中会用到
                cpdt.finish();
                return;
            } else {
                Intent intent = new Intent(this, Activity_Touzhuqueren.class);
                if (data != null)
                    intent.putExtra("touzhudata", data);
                if (listData != null)
                    intent.putExtra("listTouzhuDataHT", (Serializable) listData);
                startActivity(intent);
                pullUpActivityAnimFromMain();
            }

        }

    }

    public void changeToCaipiaoDating() {
    }


    public boolean isHasZhuiHao(Caipiao cp) {
        if (cp.isKuaikai() || cp.getId() == CaipiaoConst.ID_PAILIE3
                || cp.getId() == CaipiaoConst.ID_FUCAI3D)
            return true;
        return false;
    }


    public void closeSoftKeybord() {
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // /本地缓存数据
    public void saveUserData(String zhanghu, String mima) {
        // 本地保存账号信息

        Editor editor = basePreferences.edit();
        String key = Ecrpt.randomString();
        String incrpUserName = Ecrpt.Encrypt(zhanghu, key);
        String incrpPWe = Ecrpt.Encrypt(mima, key);
        editor.putString("nickname", zhanghu);
        editor.putString(SPKey.login_password, incrpPWe);
        editor.putString(SPKey.login_info_ecrpt_key, key);

        editor.commit();
    }

    private boolean scrolling = false;
    protected View vPopupWindowCity, vPopupWindowBank;
    protected PopupWindow wheelViewCityPop;
    protected Button cancel, ensure;
    protected WheelView wheelView, wheelProvince, wheelCity;

    public PopupWindow getWheelViewCityPop() {
        if (vPopupWindowCity == null) {
            vPopupWindowCity = getLayoutInflater().inflate(
                    R.layout.pop_wheelview, null, false);
            wheelViewCityPop = new PopupWindow(vPopupWindowCity,
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            wheelViewCityPop.setBackgroundDrawable(new BitmapDrawable());
            wheelViewCityPop.setFocusable(true);
            wheelViewCityPop.setOutsideTouchable(true);
            try {
                wheelViewCityPop.setAnimationStyle(R.style.PopWindowAnimation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cancel = (Button) vPopupWindowCity.findViewById(R.id.cancel);
            cancel.setOnClickListener(this);
            ensure = (Button) vPopupWindowCity.findViewById(R.id.ensure);
            ensure.setOnClickListener(this);
            wheelProvince = (WheelView) vPopupWindowCity
                    .findViewById(R.id.wheelviewone);
            wheelProvince.setVisibleItems(7);
            ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(
                    this, provinceStr);
            adapter.setTextSize(15);
            wheelProvince.setViewAdapter(adapter);
            wheelCity = (WheelView) vPopupWindowCity
                    .findViewById(R.id.wheelviewtwo);
            wheelCity.setVisibleItems(7);
            wheelProvince.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue,
                                      int newValue) {
                    if (!scrolling) {
                        updateCities(wheelCity, cities, newValue);
                    }
                }
            });

            wheelProvince.addScrollingListener(new OnWheelScrollListener() {
                public void onScrollingStarted(WheelView wheel) {
                    scrolling = true;
                }

                public void onScrollingFinished(WheelView wheel) {
                    scrolling = false;
                    updateCities(wheelCity, cities,
                            wheelProvince.getCurrentItem());
                }
            });
            wheelProvince.setCurrentItem(10);
            vPopupWindowCity.findViewById(R.id.imagebg)
                    .setOnClickListener(this);
        }
        return wheelViewCityPop;
    }

    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
                cities[index]);
        adapter.setTextSize(15);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    public PopupWindow getWheelViewBankPop() {
        if (vPopupWindowBank == null) {
            vPopupWindowBank = getLayoutInflater().inflate(R.layout.pop_wheelview, null, false);
            wheelViewBankPop = new PopupWindow(vPopupWindowBank,
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            wheelViewBankPop.setBackgroundDrawable(new BitmapDrawable());
            wheelViewBankPop.setFocusable(true);
            wheelViewBankPop.setOutsideTouchable(true);
            try {
                wheelViewBankPop.setAnimationStyle(R.style.PopWindowAnimation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cancel = (Button) vPopupWindowBank.findViewById(R.id.cancel);
            cancel.setOnClickListener(this);
            ensure = (Button) vPopupWindowBank.findViewById(R.id.ensure);
            ensure.setOnClickListener(this);
            vPopupWindowBank.findViewById(R.id.wheelviewtwo).setVisibility(
                    View.GONE);
            wheelView = (WheelView) vPopupWindowBank
                    .findViewById(R.id.wheelviewone);
            wheelView.setVisibleItems(7);
            wheelView.setViewAdapter(new ArrayWheelAdapter<>(this,
                    bankStr));
            wheelView.setCurrentItem(4);
            vPopupWindowBank.findViewById(R.id.imagebg)
                    .setOnClickListener(this);
        }
        return wheelViewBankPop;
    }


    View vPopupWindowDate;
    protected PopupWindow wheelViewDatePop;
    protected WheelView wheelYear, wheelMonth, wheelDay;
    String months[] = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

    protected NumericWheelAdapter adapterYear;
    protected ArrayWheelAdapter<String> adapterMonth;
    protected ArrayWheelAdapter<String> adapterDay;
    String monthTemp[];
    Calendar calendar = Calendar.getInstance();
    int curMonth = calendar.get(Calendar.MONTH);
    int curYear = calendar.get(Calendar.YEAR);
    int curDay = calendar.get(Calendar.DAY_OF_MONTH);

    public PopupWindow getWheelViewDatePop(int y, int m, int d) {
        if (vPopupWindowDate == null) {
            vPopupWindowDate = getLayoutInflater().inflate(
                    R.layout.pop_wheelview, null, false);
            wheelViewDatePop = new PopupWindow(vPopupWindowDate,
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
            wheelViewDatePop.setBackgroundDrawable(new BitmapDrawable());
            wheelViewDatePop.setFocusable(true);
            wheelViewDatePop.setOutsideTouchable(true);
            try {
                wheelViewDatePop.setAnimationStyle(R.style.PopWindowAnimation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cancel = (Button) vPopupWindowDate.findViewById(R.id.cancel);
            cancel.setOnClickListener(this);
            ensure = (Button) vPopupWindowDate.findViewById(R.id.ensure);
            ensure.setOnClickListener(this);
            wheelYear = (WheelView) vPopupWindowDate
                    .findViewById(R.id.wheelviewone);
            wheelMonth = (WheelView) vPopupWindowDate
                    .findViewById(R.id.wheelviewtwo);
            wheelDay = (WheelView) vPopupWindowDate
                    .findViewById(R.id.wheelviewthree);
            wheelDay.setVisibility(View.VISIBLE);
            adapterYear = new NumericWheelAdapter(this, 2012, curYear);
            wheelYear.setViewAdapter(adapterYear);
            if (y < 0)
                y = adapterYear.getItemsCount() - 1;
            wheelYear.setCurrentItem(y);
            wheelYear.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue,
                                      int newValue) {
                    if (!scrolling) {
                        if (newValue == adapterYear.getItemsCount() - 1) {
                            updateMonth(wheelMonth, monthTemp, curMonth);
                        } else
                            updateMonth(wheelMonth, months, curMonth);
                        updateDays(wheelYear, wheelMonth, wheelDay);
                    }
                }
            });
            monthTemp = new String[curMonth + 1];
            for (int i = 1; i <= curMonth + 1; i++) {
                if (i < 10)
                    monthTemp[i - 1] = "0" + i;
                else
                    monthTemp[i - 1] = i + "";
            }
            updateMonth(wheelMonth, monthTemp, curMonth);
            wheelMonth.addChangingListener(new OnWheelChangedListener() {
                public void onChanged(WheelView wheel, int oldValue,
                                      int newValue) {
                    if (!scrolling) {
                        updateDays(wheelYear, wheelMonth, wheelDay);
                    }
                }
            });
            vPopupWindowDate.findViewById(R.id.imagebg)
                    .setOnClickListener(this);
            updateDays(wheelYear, wheelMonth, wheelDay);
            wheelDay.setCurrentItem(curDay - 1);
        }
        if (y < 0)
            y = adapterYear.getItemsCount() - 1;
        wheelYear.setCurrentItem(y);
        if (m < 0)
            m = curMonth;
        wheelMonth.setCurrentItem(m);
        if (d < 0)
            d = curDay - 1;
        wheelDay.setCurrentItem(d);
        return wheelViewDatePop;
    }

    private void updateDays(WheelView year, WheelView month, WheelView day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,
                calendar.get(Calendar.YEAR) + year.getCurrentItem());
        calendar.set(Calendar.MONTH, month.getCurrentItem());
        int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (month.getCurrentItem() == 1) {
            if (CaipiaoConst.judgeyear(Calendar.YEAR))
                maxDays = 29;
            else
                maxDays = 28;
        }
        if (year.getCurrentItem() == adapterYear.getItemsCount() - 1
                && month.getCurrentItem() == curMonth)
            maxDays = curDay;
        String str[] = new String[maxDays];

        for (int i = 1; i <= maxDays; i++) {
            if (i < 10)
                str[i - 1] = "0" + i;
            else
                str[i - 1] = i + "";
        }
        adapterDay = new ArrayWheelAdapter<String>(this, str);
        day.setViewAdapter(adapterDay);
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }

    private void updateMonth(WheelView month, String[] str, int index) {
        adapterMonth = new ArrayWheelAdapter<String>(this, str);
        month.setViewAdapter(adapterMonth);
        month.setCurrentItem(index);
    }

    public void openKeyboard(int time) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            }
        }, time);
    }

    // /开启加载数据动画
    protected RelativeLayout progressBarLayout;

    public void startLoadAnim() {
        progressBarLayout = (RelativeLayout) findViewById(R.id.loaddata);
        progressBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        settinglanguage();
    }


    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onStop() {
        super.onStop();

        if (!AppUtil.isAppOnForeground(this)) {
            // app 进入后台
            // 全局变量isActive = false 记录当前已经进入后台
            isActive = false;
        }
    }


    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new OnTouchListener() {
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

    @Override
    public void onClick(View v) {

    }


    private AlertDialog.Builder ad;
    private AlertDialog show;


    protected void setupBottomGuanPopData(List<String> selectedChuanList, final IBottomList bottomList, int maxChuanNum) {
        setupGuoGuanToList(maxChuanNum);
        ad = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.pop_guoguan, null, false);

        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(maxChuanNum + getString(R.string.matchype));


        GridView gridFreeView = (GridView) view.findViewById(R.id.gridView1);
        GridView gridCombinationView = (GridView) view.findViewById(R.id.gridView2);
        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show != null && show.isShowing()) {
                    show.dismiss();
                }
            }
        });

        TextView tv_ok = (TextView) view.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show != null && show.isShowing()) {
                    show.dismiss();
                }
            }
        });

        ad.setView(view);

        if (guoGuan1 == null) {
            guoGuan1 = new ArrayList<>();
        }
        if (guoGuan2 == null) {
            guoGuan2 = new ArrayList<>();
        }


        CartGuanPopAdapter adapterOne = new CartGuanPopAdapter(this, true, guoGuan1,
                selectedChuanList, new BtnSelectedListener() {
            @Override
            public void changBottomValue(boolean isSelected, String chuanName) {


                if (isSelected) {
                    bottomList.setBottomTextView(chuanName, false, isSelected);

                } else {
                    bottomList.setBottomTextView("", false, isSelected);
                }
            }
        });

        CartGuanPopAdapter adpaterTwo = new CartGuanPopAdapter(this, true, guoGuan2,
                selectedChuanList, new BtnSelectedListener() {
            @Override
            public void changBottomValue(boolean isSelected,
                                         String chuanName) {

                if (isSelected) {

                    bottomList.setBottomTextView(chuanName, false, isSelected);
                } else {

                    bottomList.setBottomTextView("", false, isSelected);
                }
            }
        });
        gridFreeView.setAdapter(adapterOne);
        gridCombinationView.setAdapter(adpaterTwo);

        show = ad.show();

    }


    // 获取可以选择的过关方式，底部POP用
    public void setupGuoGuanToList(int chuan) {
        guoGuan1 = new ArrayList<>();
        guoGuan2 = new ArrayList<>();
        switch (chuan) {
            case 2:
                guoGuan1.add(getString(R.string.twoinone));
                break;
            case 3:
                guoGuan1.add(getString(R.string.twoinone));
                guoGuan1.add(getString(R.string.threeinone));
                guoGuan2.add(getString(R.string.threeinthree));
                guoGuan2.add(getString(R.string.threeinfour));
                break;
            case 4:
                guoGuan1.add(getString(R.string.twoinone));
                guoGuan1.add(getString(R.string.threeinone));
                guoGuan1.add(getString(R.string.fourinone));
                guoGuan2.add(getString(R.string.threeinthree));
                guoGuan2.add(getString(R.string.threeinfour));
                guoGuan2.add(getString(R.string.fourinfour));
                guoGuan2.add(getString(R.string.fourinfive));
                guoGuan2.add(getString(R.string.fourinsix));
                guoGuan2.add(getString(R.string.fourin11));
                break;
            case 5:
                guoGuan1.add(getString(R.string.twoinone));
                guoGuan1.add(getString(R.string.threeinone));
                guoGuan1.add(getString(R.string.fourinone));
                guoGuan1.add(getString(R.string.fiveinone));
                guoGuan2.add(getString(R.string.threeinthree));
                guoGuan2.add(getString(R.string.threeinfour));
                guoGuan2.add(getString(R.string.fourinfour));
                guoGuan2.add(getString(R.string.fourinfive));
                guoGuan2.add(getString(R.string.fourinsix));
                guoGuan2.add(getString(R.string.fourin11));
                guoGuan2.add(getString(R.string.fiveinfive));
                guoGuan2.add(getString(R.string.fiveinsix));
                guoGuan2.add(getString(R.string.fiveinten));
                guoGuan2.add("5串16");
                guoGuan2.add("5串20");
                guoGuan2.add("5串26");
                break;
            case 6:
                guoGuan1.add(getString(R.string.twoinone));
                guoGuan1.add(getString(R.string.threeinone));
                guoGuan1.add(getString(R.string.fourinone));
                guoGuan1.add(getString(R.string.fiveinone));
                guoGuan1.add(getString(R.string.sixinone));
                guoGuan2.add(getString(R.string.threeinthree));
                guoGuan2.add(getString(R.string.threeinfour));
                guoGuan2.add(getString(R.string.fourinfour));
                guoGuan2.add(getString(R.string.fourinfive));
                guoGuan2.add(getString(R.string.fourinsix));
                guoGuan2.add(getString(R.string.fourin11));
                guoGuan2.add(getString(R.string.fiveinfive));
                guoGuan2.add(getString(R.string.fiveinsix));
                guoGuan2.add(getString(R.string.fiveinten));
                guoGuan2.add("5串16");
                guoGuan2.add("5串20");
                guoGuan2.add("5串26");
                guoGuan2.add("6串6");
                guoGuan2.add("6串7");
                guoGuan2.add("6串15");
                guoGuan2.add("6串20");
                guoGuan2.add("6串22");
                guoGuan2.add("6串35");
                guoGuan2.add("6串42");
                guoGuan2.add("6串50");
                guoGuan2.add("6串57");
                break;
            case 7:
                guoGuan1.add(getString(R.string.twoinone));
                guoGuan1.add(getString(R.string.threeinone));
                guoGuan1.add(getString(R.string.fourinone));
                guoGuan1.add(getString(R.string.fiveinone));
                guoGuan1.add(getString(R.string.sixinone));
                guoGuan1.add(getString(R.string.seveninone));
                guoGuan2.add(getString(R.string.threeinthree));
                guoGuan2.add(getString(R.string.threeinfour));
                guoGuan2.add(getString(R.string.fourinfour));
                guoGuan2.add(getString(R.string.fourinfive));
                guoGuan2.add(getString(R.string.fourinsix));
                guoGuan2.add(getString(R.string.fourin11));
                guoGuan2.add(getString(R.string.fiveinfive));
                guoGuan2.add(getString(R.string.fiveinsix));
                guoGuan2.add(getString(R.string.fiveinten));
                guoGuan2.add("5串16");
                guoGuan2.add("5串20");
                guoGuan2.add("5串26");
                guoGuan2.add("6串6");
                guoGuan2.add("6串7");
                guoGuan2.add("6串15");
                guoGuan2.add("6串20");
                guoGuan2.add("6串22");
                guoGuan2.add("6串35");
                guoGuan2.add("6串42");
                guoGuan2.add("6串50");
                guoGuan2.add("6串57");
                guoGuan2.add("7串7");
                guoGuan2.add("7串8");
                guoGuan2.add("7串21");
                guoGuan2.add("7串35");
                guoGuan2.add("7串120");
                break;
            default:
                guoGuan1.add(getString(R.string.twoinone));
                guoGuan1.add(getString(R.string.threeinone));
                guoGuan1.add(getString(R.string.fourinone));
                guoGuan1.add(getString(R.string.fiveinone));
                guoGuan1.add(getString(R.string.sixinone));
                guoGuan1.add(getString(R.string.seveninone));
                guoGuan1.add(getString(R.string.eightinone));
                
                
                
                
                guoGuan2.add(getString(R.string.threeinthree));
                guoGuan2.add(getString(R.string.threeinfour));
                guoGuan2.add(getString(R.string.fourinfour));
                guoGuan2.add(getString(R.string.fourinfive));
                guoGuan2.add(getString(R.string.fourinsix));
                guoGuan2.add(getString(R.string.fourin11));
                guoGuan2.add(getString(R.string.fiveinfive));
                guoGuan2.add(getString(R.string.fiveinsix));
                guoGuan2.add(getString(R.string.fiveinten));
                guoGuan2.add("5串16");
                guoGuan2.add("5串20");
                guoGuan2.add("5串26");
                guoGuan2.add("6串6");
                guoGuan2.add("6串7");
                guoGuan2.add("6串15");
                guoGuan2.add("6串20");
                guoGuan2.add("6串22");
                guoGuan2.add("6串35");
                guoGuan2.add("6串42");
                guoGuan2.add("6串50");
                guoGuan2.add("6串57");
                guoGuan2.add("7串7");
                guoGuan2.add("7串8");
                guoGuan2.add("7串21");
                guoGuan2.add("7串35");
                guoGuan2.add("7串120");
                guoGuan2.add("8串8");
                guoGuan2.add("8串9");
                guoGuan2.add("8串28");
                guoGuan2.add("8串56");
                guoGuan2.add("8串70");
                guoGuan2.add("8串247");
                break;
        }

    }

    protected void initBuyType() {
        if (Control.getInstance().getBasketballManager().getDanorguo() == 0) {
            findViewById(R.id.guoguanlayout).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_danguan).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.guoguanlayout).setVisibility(View.INVISIBLE);
            findViewById(R.id.tv_danguan).setVisibility(View.VISIBLE);
        }
    }


    private PopupWindow linearPop;
    private LinearPopAdapter linearPopAdapter;

    protected PopupWindow getLinearPop(final ImageView imageView) {

        if (linearPop == null) linearPop = new PopupWindow(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        linearPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AppUtil.startResRotateAnim(imageView);
            }
        });

        View linearView = getLayoutInflater().inflate(R.layout.pop_linear_wanfa, null, false);


        RecyclerView rvPopLinear = (RecyclerView) linearView.findViewById(R.id.rv_pop_linear);


        RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(this, 3);

        rvPopLinear.setLayoutManager(linearLayoutManager);
        if (linearPopAdapter == null) {
            linearPopAdapter = new LinearPopAdapter(this,getCurrentCaipiao().getWanfaList());
            rvPopLinear.setAdapter(linearPopAdapter);
        } else {
            linearPopAdapter.notifyDataSetChanged();
        }


        return linearPop;
    }
    protected long getBeisu(EditText beiShuEdit) {

        if (TextUtils.isEmpty(beiShuEdit.getText())){
            return Long.parseLong(beiShuEdit.getHint().toString());
        }else {
            return Long.parseLong(beiShuEdit.getText().toString());
        }

    }

    public void switchLanguage(Locale locale) {
        Configuration config = getResources().getConfiguration();// 获得设置对象
        Resources resources = getResources();//
        DisplayMetrics dm = resources.getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
        config.setLocale( locale); //
        resources.updateConfiguration(config, dm);
    }


}
