package com.chengyi.app.model.model;


import com.alibaba.fastjson.JSON;
import com.chengyi.app.model.param.URLSuffix;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class SchemeDetailData {
    private String issue;// 奖期
    private int multiple;// 倍数
    private double money;// 金额
    private String statueDesc;// 状态
    private String drawNumber;// 开奖号码  可空

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getStatueDesc() {
        return statueDesc;
    }

    public void setStatueDesc(String statueDesc) {
        this.statueDesc = statueDesc;
    }

    public String getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public static SchemeDetailData create(String json) {
        SchemeDetailData data = new SchemeDetailData();
        data.setDrawNumber(JSON.parseObject(json).getString(URLSuffix.drawNumber));
        data.setIssue(JSON.parseObject(json).getString(URLSuffix.issue));
        data.setMoney(JSON.parseObject(json).getDoubleValue(URLSuffix.money));
        data.setMultiple(JSON.parseObject(json).getIntValue(URLSuffix.multiple));
        data.setStatueDesc(JSON.parseObject(json).getString(URLSuffix.statusDesc));
        return data;
    }

}
