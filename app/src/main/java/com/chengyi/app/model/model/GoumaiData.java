package com.chengyi.app.model.model;

import android.content.Context;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.jingji.basket.BasketballOneGame;
import com.chengyi.app.util.CaipiaoConst;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class GoumaiData implements Serializable {
    /**
     *
     */
    private String issue;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    private String show;

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    private static final long serialVersionUID = 1L;
    private int beishu = 1;
    private int zhuihaoqishu = 1;
    private long totalZhushu;
    private String submitString;
    private long allMoney = 0;
    String issueIdStr = "";  //追号追的期数id
    String issueStr = ""; //追号追的期数

    public String getIssueStr() {
        return issueStr;
    }

    public void setIssueStr(String issueStr) {
        this.issueStr = issueStr;
    }

    public String getIssueIdStr() {
        return issueIdStr;
    }

    public void setIssueIdStr(String issueIdStr) {
        this.issueIdStr = issueIdStr;
    }

    public String getMultipleStr() {
        return multipleStr;
    }

    public void setMultipleStr(String multipleStr) {
        this.multipleStr = multipleStr;
    }

    String multipleStr = "";//追号追的倍数

    public long getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(int allMoney) {
        this.allMoney = allMoney;
    }

    private int caipiaoId;

    private boolean zhuijiatouzhu = false;//大乐透才有

    public int getCaipiaoId() {
        return caipiaoId;
    }

    public void setCaipiaoId(int caipiaoId) {
        this.caipiaoId = caipiaoId;
    }

    public boolean isZhuijiatouzhu() {
        return zhuijiatouzhu;
    }

    public void setZhuijiatouzhu(boolean zhuijiatouzhu) {
        this.zhuijiatouzhu = zhuijiatouzhu;
    }

    // 以下为合买特有
    private String woyaorengou;
    private String zuidirengou;
    private int woyaobaodi = 0;
    private int yingliyongjin = 0;
    private int shifougongkai;
    private String miaoshu;

    private int dayutingzhi = 0;// 计中出≥ 元，停止追号。-1代码没选中
    private boolean kaichutingzhi = false;// 快开，追号开始前，号码开出停止追号。
    private boolean dayutingzhiChecked = false;

    public int getDayutingzhi() {
        return dayutingzhi;
    }

    public boolean isDayutingzhiChecked() {
        return dayutingzhiChecked;
    }

    public void setDayutingzhiChecked(boolean dayutingzhiChecked) {
        this.dayutingzhiChecked = dayutingzhiChecked;
    }

    public void setDayutingzhi(int dayutingzhi) {
        this.dayutingzhi = dayutingzhi;
    }

    public boolean isKaichutingzhi() {
        return kaichutingzhi;
    }

    public void setKaichutingzhi(boolean kaichutingzhi) {
        this.kaichutingzhi = kaichutingzhi;
    }

//	public int getIssueId() {
//		return issueId;
//	}
//
////	public void setIssueId(int issueId) {
////		this.issueId = issueId;
////	}

    public int getBeishu() {
        if (beishu < 1) return 1;
        return beishu;
    }

    public void setBeishu(int beishu) {
        this.beishu = beishu;
    }

    public int getZhuihaoqishu() {
        return zhuihaoqishu;
    }

    public void setZhuihaoqishu(int zhuihaoqishu) {
        this.zhuihaoqishu = zhuihaoqishu;
    }

    public long getTotalZhushu() {
        return totalZhushu;
    }

    public void setTotalZhushu(long totalZhushu) {
        this.totalZhushu = totalZhushu;
    }

    public long getTotalMoney() {
        int price = CaipiaoConst.PRICE;
        if (getCaipiaoId() == CaipiaoConst.ID_DALETOU && isZhuijiatouzhu()) {
            price = 3;
        }
        return (long) getTotalZhushu() * (long) price * (long) beishu * (long) zhuihaoqishu;
    }

    public String getSubmitString() {
        return submitString;
    }

    public void setSubmitString(String submitString) {
        this.submitString = submitString;
    }

    public String getWoyaorengou() {
        return woyaorengou;
    }

    public void setWoyaorengou(String woyaorengou) {
        this.woyaorengou = woyaorengou;
    }

    public String getZuidirengou() {
        return zuidirengou;
    }

    public void setZuidirengou(String zuidirengou) {
        this.zuidirengou = zuidirengou;
    }

    public int getWoyaobaodi() {
        return woyaobaodi;
    }

    public void setWoyaobaodi(int woyaobaodi) {
        this.woyaobaodi = woyaobaodi;
    }

    public int getYingliyongjin() {
        return yingliyongjin;
    }

    public void setYingliyongjin(int yingliyongjin) {
        this.yingliyongjin = yingliyongjin;
    }

    public int getShifougongkai() {
        return shifougongkai;
    }

    public String getShifougongkaiString() {
        Context context = CaipiaoApplication.getInstance()
                .getApplicationContext();
        switch (shifougongkai) {
            case 1:
                return context.getString(R.string.gongkai);
            case 2:
                return context.getString(R.string.baomi);
            case 3:
                return context.getString(R.string.gendanrenkekan);
            case 4:
                return context.getString(R.string.jiezhihougongkai);
        }
        return context.getString(R.string.gongkai);
    }

    public void setShifougongkai(int shifougongkai) {
        this.shifougongkai = shifougongkai;
    }

    public String getMiaoshu() {
        return miaoshu;
    }

    public void setMiaoshu(String miaoshu) {
        this.miaoshu = miaoshu;
    }

    public boolean isHemai() {
        return woyaorengou != null;
    }

    //竞彩足球特有
    private int gameSize = 0;
    private String guoGuanStr;


    public int getGameSize() {
        return gameSize;
    }

    public void setGameSize(int gameSize) {
        this.gameSize = gameSize;
    }

    public String getGuoGuanStr() {
        return guoGuanStr;
    }

    public void setGuoGuanStr(String guoGuanStr) {
        this.guoGuanStr = guoGuanStr;
    }

    private List<JingcaizuqiuOneGame> listTemp;

    public List<JingcaizuqiuOneGame> getListTemp() {
        return listTemp;
    }

    public void setListTemp(List<JingcaizuqiuOneGame> listTemp) {
        this.listTemp = listTemp;
    }

    //竞彩篮球特有

    private List<BasketballOneGame> listBasketball;

    public List<BasketballOneGame> getListBasketball() {
        return listBasketball;
    }

    public void setListBasketball(List<BasketballOneGame> listBasketball) {
        this.listBasketball = listBasketball;
    }

    //存储已选择的投注球队
    private ArrayList<GuanYaJunData> list = new ArrayList<GuanYaJunData>();

    public ArrayList<GuanYaJunData> getList() {
        return list;
    }

    public void setList(ArrayList<GuanYaJunData> list) {
        this.list = list;
    }

}
