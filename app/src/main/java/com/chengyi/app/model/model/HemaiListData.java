package com.chengyi.app.model.model;


import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.param.URLSuffix;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class HemaiListData {
    private int lotteryId;
    private String lotteryName;
    private int schemeId;// 方案ID
    private String userName;// 用户名称
    private int[] level;// 战绩
    private double schemeAmount;// 方案金额
    private double remainAmount;// 剩余金额
    private String progress;// 进度
    private int personCount;// 参与人数
    private int jindu;// 实际进度
    private int baodijindu;// 保底百分比（进度）
    private boolean istop = false;//是否置顶

    public boolean isIstop() {
        return istop;
    }

    public void setIstop(boolean istop) {
        this.istop = istop;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public int getJindu() {
        return jindu;
    }

    public void setJindu(int jindu) {
        this.jindu = jindu;
    }

    public int getBaodijindu() {
        return baodijindu;
    }

    public void setBaodijindu(int baodijindu) {
        this.baodijindu = baodijindu;
    }

    private int iconId;

    public int getIcon() {
        if (iconId == 0) {
            Caipiao cp = CaipiaoFactory.getInstance(
                    CaipiaoApplication.getInstance().getApplicationContext())
                    .getCaipiaoById(lotteryId);
            if (cp != null) {
                iconId = cp.getIconResId();
            } else {
                iconId = R.drawable.list_quesheng;
            }
        }
        return iconId;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(int schemeId) {
        this.schemeId = schemeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int[] getLevel() {
        return level;
    }

    public void setLevel(int[] level) {
        this.level = level;
    }

    public double getSchemeAmount() {
        return schemeAmount;
    }

    public void setSchemeAmount(double schemeAmount) {
        this.schemeAmount = schemeAmount;
    }

    public double getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(double remainAmount) {
        this.remainAmount = remainAmount;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;

    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public static HemaiListData create(JSONObject json) {
        HemaiListData data = new HemaiListData();
        int[] ls = new int[4];
        try {
            String[] levels = json.getString(URLSuffix.level).split(",");
            for (int i = 0; i < levels.length; i++) {
                ls[i] = Integer.valueOf(levels[i].trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        data.setLevel(ls);
        data.setLotteryId(json.getIntValue(URLSuffix.lotteryId));
        data.setLotteryName(json.getString(URLSuffix.lotteryName));
        data.setPersonCount(json.getIntValue(URLSuffix.personCount));
        data.setProgress(json.getString(URLSuffix.progress));
        data.setRemainAmount(json.getDouble(URLSuffix.remainAmount));
        data.setSchemeAmount(json.getDouble(URLSuffix.schemeAmount));
        data.setSchemeId(json.getIntValue(URLSuffix.schemeId));
        data.setUserName(json.getString(URLSuffix.userName));
        data.setJindu(json.getIntValue(URLSuffix.moeyProgress));
        data.setBaodijindu(json.getIntValue(URLSuffix.safeguardProgress));
        try {
            data.setIstop(json.getBooleanValue("schemeTop"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
