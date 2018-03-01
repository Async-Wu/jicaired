package com.chengyi.app;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import com.chengyi.R;
import com.chengyi.app.model.bean.HadLotteryBean;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.model.AbsJingcaizuqiuData;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.push.DemoPushService;
import com.chengyi.app.receiver.GoucaiTixingReceiver;
import com.chengyi.app.user.info.UserInfo;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.lock.LockPatternUtils;
import com.igexin.sdk.PushManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class CaipiaoApplication extends Application {
    private static CaipiaoApplication instance;
    public static Context mContext;

    private int footBallType;

    public int getFootBallType() {
        return footBallType;
    }

    public void setFootBallType(int footBallType) {
        this.footBallType = footBallType;
    }

    Handler handler;

    private Handler updateIssue, getZhongJiang, userCenterHandler;
    private LockPatternUtils mLockPatternUtils;

    public boolean isOnRegisterProcess = false;

    public static SharedPreferences sharedPreferences;
    private int cptype;

    public Handler getGetZhongJiang() {
        return getZhongJiang;
    }

    public void setGetZhongJiang(Handler getZhongJiang) {
        this.getZhongJiang = getZhongJiang;
    }

    public Handler getUpdateIssue() {
        return updateIssue;
    }

    public void setUpdateIssue(Handler updateIssue) {
        this.updateIssue = updateIssue;
    }


    @Override
    public void onCreate() {
        OkHttpUtil.initOkHttp();

        super.onCreate();
        instance = this;
        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        mContext = this.getApplicationContext();


        mLockPatternUtils = new LockPatternUtils(this);
        handler = new Handler();

        sharedPreferences = CaipiaoUtil.getCpSharedPreferences(this);
//        CrashHandler  ca= CrashHandler.getInstance();
//        ca.init(this);

    }

    public LockPatternUtils getLockPatternUtils() {
        return mLockPatternUtils;
    }


    List<UserInfo> userList = new ArrayList<UserInfo>();


    private List<Integer> bjdcdelet = new ArrayList<>();


    public List<Integer> getBjdcdelet() {
        if (bjdcdelet == null) bjdcdelet = new ArrayList<>();
        return bjdcdelet;
    }

    public void clearDelete() {
        if (bjdcdelet != null)
            bjdcdelet.clear();
    }

    public void setBjdcdelet(int j) {
        if (bjdcdelet != null) {
            bjdcdelet.add(j);
        }
    }

    public static CaipiaoApplication getInstance() {
        return instance;
    }

    Caipiao caipiao;

    public Caipiao getCurrentCaipiao() {
        if (caipiao == null)
            return CaipiaoFactory.getInstance(this).getCaipiaoList().get(0);
        return caipiao;
    }

    public void setCurrentCaipiao(Caipiao caipiao) {
        this.caipiao = caipiao;
    }

    // 投注确认到购买确认界面 需要传递，用intent直接传比较麻烦
    private List<TouzhuquerenData> touzhuHaomaList;
    private HadLotteryBean data;

    public HadLotteryBean getData() {
        return data;
    }

    public void setData(HadLotteryBean data) {
        this.data = data;
    }

    public List<TouzhuquerenData> getTouzhuHaomaList() {
        return touzhuHaomaList;
    }


    public void setTouzhuHaomaList(List<TouzhuquerenData> touzhuHaomaList) {
        this.touzhuHaomaList = touzhuHaomaList;
    }

    String[] daojishi;

    public String[] getDaojishi() {
        if (daojishi == null) {
            daojishi = new String[4];
            daojishi[0] = getApplicationContext().getString(R.string.tian);
            daojishi[1] = getApplicationContext().getString(R.string.xiaoshi);
            daojishi[2] = getApplicationContext().getString(R.string.fenzhong);
            daojishi[3] = getApplicationContext().getString(R.string.miao);
        }
        return daojishi;
    }

    // 胜平负数据
    private List<AbsJingcaizuqiuData> spfList = new ArrayList<>();
    // 总进球数据
    private List<AbsJingcaizuqiuData> zjqList = new ArrayList<AbsJingcaizuqiuData>();

    public List<AbsJingcaizuqiuData> getSpfList() {
        return spfList;
    }

    public void setSpfList(List<AbsJingcaizuqiuData> spfList) {
        this.spfList = spfList;
    }

    public List<AbsJingcaizuqiuData> getZjqList() {
        return zjqList;
    }

    public void setZjqList(List<AbsJingcaizuqiuData> zjqList) {
        this.zjqList = zjqList;
    }

    // 竞彩足球筛选所用的数据
    private String[] saiShiChoice;
    private ArrayList<String> jiangQi = new ArrayList<String>();

    public String[] getSaiShiChoice() {
        return saiShiChoice;
    }

    public void setSaiShiChoice(String[] saiShiChoice) {
        this.saiShiChoice = saiShiChoice;
    }

    public ArrayList<String> getJiangQi() {
        return jiangQi;
    }

    public void setJiangQi(ArrayList<String> jiangQi) {
        this.jiangQi = jiangQi;
    }

    ArrayList<Integer> builder = new ArrayList<Integer>();
    ArrayList<Integer> builderSaishi = new ArrayList<Integer>();

    public void setBuilder(ArrayList<Integer> builder) {
        this.builder = builder;
    }

    public void setBuilderSaishi(ArrayList<Integer> builderSaishi) {
        this.builderSaishi = builderSaishi;
    }

    // 记录选择让球中用户的选择结果
    String xuanZeRangQiu;

    public ArrayList<Integer> getBuilder() {
        return builder;
    }

    public ArrayList<Integer> getBuilderSaishi() {
        return builderSaishi;
    }

    public String getXuanZeRangQiu() {
        return xuanZeRangQiu;
    }

    public void setXuanZeRangQiu(String xuanZeRangQiu) {
        this.xuanZeRangQiu = xuanZeRangQiu;
    }

    public void setBuilderSize(int size) {
        builder = new ArrayList<Integer>();
        for (int i = 0; i < size; i++)
            builder.add(i);
    }

    public void setBuilderSaishiDataSize(int t) {
        builderSaishi = new ArrayList<Integer>();
        for (int i = 0; i < t; i++)
            builderSaishi.add(i);
    }

    // 竞彩足球的销售奖期
    private String jingcaixiaoshouJiangqi;

    public String getJingcaixiaoshouJiangqi() {
        return jingcaixiaoshouJiangqi;
    }

    public void setJingcaixiaoshouJiangqi(String jingcaixiaoshouJiangqi) {
        this.jingcaixiaoshouJiangqi = jingcaixiaoshouJiangqi;
    }

    // 竞彩篮球的销售奖期

    private String basketballJiangqi;

    public String getBasketballJiangqi() {
        return basketballJiangqi;
    }

    public String setBasketballJiangqi(String basketballJiangqi) {
        return this.basketballJiangqi = basketballJiangqi;
    }


    private String phoneNum;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    PendingIntent pendingIntent;

    public PendingIntent getPendingIntent() {
        if (pendingIntent == null) {
            Intent in = new Intent(mContext, GoucaiTixingReceiver.class);
            in.setAction("AlarmReceiver");
            pendingIntent = PendingIntent.getBroadcast(mContext, 0, in,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }
        return pendingIntent;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    boolean isBackSetting = false; // 登录成功后需要返回到设置界面

    public boolean isBackSetting() {
        return isBackSetting;
    }

    public void setBackSetting(boolean isBackSetting) {
        this.isBackSetting = isBackSetting;
    }

    boolean isBackXinshow = false; // 登录成功后需要返回到设置新手

    public boolean isBackXinshow() {
        return isBackXinshow;
    }

    public void setBackXinshow(boolean isBackXinshow) {
        this.isBackXinshow = isBackXinshow;
    }

    boolean isBackHuoDong = false; // 登录成功后需要返回到设置新手

    public boolean isBackHuoDong() {
        return isBackHuoDong;
    }

    public void setBackHuoDong(boolean isBackHuoDong) {
        this.isBackHuoDong = isBackHuoDong;
    }

    Map<String, JingcaizuqiuOneGame> gameList;

    public Map<String, JingcaizuqiuOneGame> getGameList() {
        return gameList;
    }

    public void setGameList(Map<String, JingcaizuqiuOneGame> gameList) {
        this.gameList = gameList;
    }

    List<AbsJingcaizuqiuData> list;

    public List<AbsJingcaizuqiuData> getList() {
        return list;
    }

    public void setList(List<AbsJingcaizuqiuData> list) {
        this.list = list;
    }

    public String getRequstType() {
        return "4";
    }

    public void setUserCenterHandler(Handler userCenterHandler) {
        this.userCenterHandler = userCenterHandler;
    }

    public Handler getUserCenterHandler() {
        return this.userCenterHandler;
    }

    public void setCptype(int cptype) {
        this.cptype = cptype;
    }

    public String getSession() {
        if (sharedPreferences == null)
            sharedPreferences = CaipiaoUtil.getCpSharedPreferences(this);
        return sharedPreferences.getString("user_clientUserSession", "");
    }
}
