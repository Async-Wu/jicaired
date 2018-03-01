package com.chengyi.app.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.jingji.six.PopMode;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.param.SPKey;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.pop.Title_pop;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.Ecrpt;
import com.chengyi.app.view.widget.OnWheelChangedListener;
import com.chengyi.app.view.widget.OnWheelScrollListener;
import com.chengyi.app.view.widget.WheelView;
import com.chengyi.app.view.widget.adapters.ArrayWheelAdapter;
import com.chengyi.app.view.widget.adapters.NumericWheelAdapter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BaseFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = BaseFragment.class.getSimpleName();

    int begin = 0;

    boolean isActive = true;
    public HashMap<String, String> map = new HashMap<String, String>();


    protected void setBegin(int begin) {
        this.begin = begin;
    }

    protected int getBegin() {
        return begin;
    }

    protected SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public String moneyReg = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
    protected SimpleDateFormat dateFormatInTime = new SimpleDateFormat("HH:mm");
    String ZHANGHAO_RULE = "^([a-z|A-Z]+|[ \\u4e00-\\u9fa5]+|[0-9]+|[_|_]+)+$";
    public String cities[][];
    String[] provinceStr, bankStr;

    protected Animation mShowAction;
    Animation mHiddenAction;
    protected Animation am;
    protected Animation am1;
    SharedPreferences preferences;
    protected AnimationSet set;
    PopupWindow wheelViewBankPop;
    protected DecimalFormat df = new DecimalFormat("0.00");
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//
        }
    };
    RequestParams params;
    protected Activity parentActivity;

    private View ll_pop;


    private Button btnLeft, btnRight;
    protected List<PopMode> pop_list = new ArrayList<>();
    private Title_pop sixPopAdapter;
    private RecyclerView rv_pop;

    protected void disPop(LinearLayout ll) {


        if (ll != null && ll.getVisibility() == View.VISIBLE) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ll.setVisibility(View.GONE);

        }
    }

    ImageView[] btns;

    protected PopupWindow popPop;

    protected PopupWindow showPop(final ImageView... btn) {
        btns = btn;
        if (ll_pop == null) {
            ll_pop = getActivity().getLayoutInflater().from(getActivity()).inflate(R.layout.wanfa_six, null, false);

        }
        popPop = new PopupWindow(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

      popPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
          @Override
          public void onDismiss() {
              for (ImageView b : btn)
                  AppUtil.startResRotateAnim(b);

          }
      });


        if (rv_pop == null) {

            rv_pop = (RecyclerView) ll_pop.findViewById(R.id.rv_pop_six);
        }
        if (pop_list != null && pop_list.size() < 4 && pop_list.size() != 0) {

            rv_pop.setLayoutManager(new GridLayoutManager(getContext(), pop_list.size()));

        } else {

            rv_pop.setLayoutManager(new GridLayoutManager(getContext(), 4));

        }

        Collections.sort(pop_list, new Comparator<PopMode>() {
            @Override
            public int compare(PopMode popMode, PopMode t1) {
                return popMode.getIssuId() - t1.getIssuId();
            }
        });
        sixPopAdapter = new Title_pop(this, pop_list);
        rv_pop.setAdapter(sixPopAdapter);
        popPop.setFocusable(true);
        popPop.setOutsideTouchable(true);
        popPop.setContentView(ll_pop);
        return popPop;
    }

    private TextView tv_title;

    public void setCusTomeTitle(View view, String title) {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setOnClickListener(this);
        tv_title.setText(title);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        parentActivity = getActivity();

        preferences = CaipiaoUtil.getCpSharedPreferences(parentActivity);
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


        //数据上传参数
        params = getRequestParams();
    }

    protected void showToast(int msg) {
        Toast toast = Toast.makeText(parentActivity, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public String getUserName() {
        if (TextUtils.isEmpty(getSession())) {
            return "";
        } else {
            return preferences.getString("nickname", "");
        }

    }

    public String getUserBalance() {//user_balance
        if (TextUtils.isEmpty(getSession())) {
            return "";
        } else {

            return (preferences.getString("user_balance", ""));
        }
    }

    public String getScore() {//user_balance
        if (TextUtils.isEmpty(getSession())) {
            return "";
        } else {

            return (preferences.getString("score", ""));
        }
    }

    public String getPwd() {
        if (TextUtils.isEmpty(getSession())) {
            return "";
        } else {
            String key = preferences.getString(SPKey.login_info_ecrpt_key, "");
            return Ecrpt.Decrypt(preferences.getString(SPKey.login_password, ""), key);
        }


    }

    protected void showToast(String msg) {
        Toast toast = Toast.makeText(parentActivity, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

//016778
    ProgressDialog pd;

    public void showLoading(String s) {
        try {
            if (pd == null) {
                pd = new ProgressDialog(parentActivity);
                pd.setCancelable(false);
                if (s.equals(""))
                    pd.setMessage(getString(R.string.loading));
                else
                    pd.setMessage(s);
            }
            if (!pd.isShowing()) {
                pd.show();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void hideLoading() {
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        return params;
    }

    public String getSession() {
        return CaipiaoApplication.getInstance().getSession();
    }


    /**
     * add for newframe
     */
    public Caipiao getCurrentCaipiao() {
        return CaipiaoApplication.getInstance().getCurrentCaipiao();
    }


    public boolean checkResult(String result) {
        if (JSON.parseObject(result).getIntValue(URLSuffix.flag) == 0) {
            String errorMsg = JSON.parseObject(result).getString(URLSuffix.errorMessage);
            if (TextUtils.isEmpty(errorMsg)) {
                showToast(R.string.error);
            } else {
                showToast(errorMsg);
            }
            return false;
        }
        return true;
    }


    {
    }


    private boolean scrolling = false;
    View vPopupWindowCity, vPopupWindowBank;
    PopupWindow wheelViewCityPop;
    Button cancel, ensure;
    WheelView wheelView, wheelProvince, wheelCity;

    public PopupWindow getWheelViewCityPop() {
        if (vPopupWindowCity == null) {
            vPopupWindowCity = parentActivity.getLayoutInflater().inflate(
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
                    parentActivity, provinceStr);
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
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(parentActivity,
                cities[index]);
        adapter.setTextSize(15);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    public PopupWindow getWheelViewBankPop() {
        if (vPopupWindowBank == null) {
            vPopupWindowBank = parentActivity.getLayoutInflater().inflate(
                    R.layout.pop_wheelview, null, false);
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
            wheelView.setViewAdapter(new ArrayWheelAdapter<String>(parentActivity,
                    bankStr));
            wheelView.setCurrentItem(4);
            vPopupWindowBank.findViewById(R.id.imagebg)
                    .setOnClickListener(this);
        }
        return wheelViewBankPop;
    }

    // 保存文件内容
    public void writeFiles(String content, String name) {
        try {
            // 打开文件获取输出流，文件不存在则自动创建
            FileOutputStream fos = parentActivity.openFileOutput(name, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 读取文件内容
    public String readFiles(String name) {
        String content = null;
        try {
            FileInputStream fis = parentActivity.openFileInput(name);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            content = baos.toString();
            fis.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    View vPopupWindowDate;
    protected PopupWindow wheelViewDatePop;
    protected WheelView wheelYear, wheelMonth, wheelDay;
    String months[] = new String[]{"01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12"};

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
            vPopupWindowDate = parentActivity.getLayoutInflater().inflate(
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
            adapterYear = new NumericWheelAdapter(parentActivity, 2012, curYear);
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
        adapterDay = new ArrayWheelAdapter<String>(parentActivity, str);
        day.setViewAdapter(adapterDay);
        int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
        day.setCurrentItem(curDay - 1, true);
    }

    private void updateMonth(WheelView month, String[] str, int index) {
        adapterMonth = new ArrayWheelAdapter<>(parentActivity, str);
        month.setViewAdapter(adapterMonth);
        month.setCurrentItem(index);
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        if (!AppUtil.isAppOnForeground(parentActivity)) {
            // app 进入后台
            // 全局变量isActive = false 记录当前已经进入后台
            isActive = false;
        }
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */


}
