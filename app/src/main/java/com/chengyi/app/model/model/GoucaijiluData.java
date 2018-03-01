package com.chengyi.app.model.model;


import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.param.URLSuffix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  GoucaijiluData {
    private int schemeId;// 方案ID
    private String lotteryName;// 彩票名称
    private String issue;// 奖期
    private double schemeAmount;// 方案金额
    private String statusDesc;// 状态
    private int percent;// 进度(百分比),我的关注中有
    private boolean isHemai;
    private int schemeNumberType;
    private double prize = 0;
    private boolean participant;

    public boolean isParticipant() {
        return participant;
    }

    public void setParticipant(boolean participant) {
        this.participant = participant;
    }

    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public int getSchemeNumberType() {
        return schemeNumberType;
    }

    public void setSchemeNumberType(int schemeNumberType) {
        this.schemeNumberType = schemeNumberType;
    }

    public boolean isHemai() {
        return isHemai;
    }

    public void setHemai(boolean isHemai) {
        this.isHemai = isHemai;
    }

    private String initiateTime;

    public String getInitiateTime() {
        if (!TextUtils.isEmpty(initiateTime)) {

//			 2016-08-15 17:18:10
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date;
            try {
                date = simpleDateFormat.parse(initiateTime);
            } catch (ParseException e) {
                e.printStackTrace();
                date = new Date();
            }

            return new SimpleDateFormat("M-dd HH:mm:ss").format(date);
        }
        return initiateTime;
    }

    public void setInitiateTime(String initiateTime) {
        this.initiateTime = initiateTime;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public void setSchemeId(int schemeId) {
        this.schemeId = schemeId;
    }

    public String getLotteryName() {
        if ("胜负彩".equals(lotteryName)) {
            lotteryName =  CaipiaoApplication.getInstance().getResources().getString(R.string.shengfu14chang);
        }
        if ("任选九场".equals(lotteryName)) {
            lotteryName =  CaipiaoApplication.getInstance().getResources().getString(R.string.renxuan9chang);
        }
        if ("排列3/5".equals(lotteryName)) {
            lotteryName = CaipiaoApplication.getInstance().getResources().getString(R.string.pailie3);
        }
        if ("时时彩".equals(lotteryName)) {
            lotteryName = CaipiaoApplication.getInstance().getResources().getString(R.string.laoshishicai);
        }
        if ("11选5".equals(lotteryName)) {
            lotteryName =  CaipiaoApplication.getInstance().getResources().getString(R.string.now11xuan5);
        }
        if ("快3".equals(lotteryName)) {
            lotteryName =  CaipiaoApplication.getInstance().getResources().getString(R.string.xinkuai3);
        }

        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        if (lotteryName.equals("超级大乐透"))
            this.lotteryName = "大乐透";
        else
            this.lotteryName = lotteryName;
    }


    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public double getSchemeAmount() {
        return schemeAmount;
    }

    public void setSchemeAmount(double schemeAmount) {
        this.schemeAmount = schemeAmount;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public static GoucaijiluData create(JSONObject json) {
        GoucaijiluData data = new GoucaijiluData();
        data.setIssue(json.getString(URLSuffix.issue));
        data.setLotteryName(json.getString(URLSuffix.lotteryName));
        data.setSchemeAmount(json.getDouble(URLSuffix.schemeAmount));
        data.setSchemeId(json.getIntValue(URLSuffix.schemeId));
        data.setStatusDesc(json.getString(URLSuffix.statusDesc));
        data.setPercent(json.getIntValue(URLSuffix.percent));
        data.setInitiateTime(json.getString(URLSuffix.initiateTime));
        data.setHemai(json.getBooleanValue("hemai" ));
        data.setParticipant(json.getBooleanValue("participant" ));
        data.setPrize(json.getDouble("prize"));
        return data;
    }
}
