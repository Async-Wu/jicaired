/**
 * Create on 2012-10-15
 */
package com.chengyi.app.model.model;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.util.IP;
import com.chengyi.app.web.WebViewActivity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class JingcaizuqiuOneGame extends GameMode {// 一场比赛
    /**
     *
     */

    public String[] bqcArr = {"胜胜", "胜平", "胜负", "平胜", "平负", "负胜", "平胜", "平胜", "平胜",};


    private List<Boolean> selects = new ArrayList<>();

    public List<Boolean> getSelects() {
        return selects;
    }

    public void setSelects(List<Boolean> selects) {
        this.selects = selects;
    }

    private String otherSelect;

    public String getOtherSelect() {
        return otherSelect;
    }

    public void setOtherSelect(String otherSelect) {
        this.otherSelect = otherSelect;
    }

    public String getMixContent() {


        String spfStr = "";
        for (int i = 0; i < isSelected.length; i++) {
            if (isSelected[i]) {
                switch (i) {
                    case 0:
                        spfStr += 3 + ",";
                        break;
                    case 1:
                        spfStr += 1 + ",";
                        break;
                    case 2:
                        spfStr += 0 + ",";
                        break;
                    case 3:
                        spfStr += 403 + ",";
                        break;
                    case 4:
                        spfStr += 401 + ",";
                        break;
                    case 5:
                        spfStr += 400 + ",";
                        break;

                }
            }
        }
        if (getOtherSelect() == null) {
            if (spfStr.endsWith(","))
                spfStr = spfStr.substring(0, spfStr.length() - 1);
            return spfStr;
        } else {
            return spfStr + getOtherSelect();
        }

    }

    private int mixHadSelect;


    public int getMixHadSelect() {
        int i = 0;
        for (Boolean b : selects) {
            if (b) i++;
        }

        for (int j = 0; j < 6; j++) {
            if (isSelected[j]) i++;
        }


        return i;
    }

    public void setMixHadSelect(int mixHadSelect) {
        this.mixHadSelect = mixHadSelect;
    }


    private String show;

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    private int spfSingleStatus;
    private int zjqSingleStatus;
    private int bfSingleStatus;
    private int bqcSingleStatus;


    private int spfPassStatus;
    private int zjqPassStatus;
    private int bfPassStatus;
    private int bqcPassStatus;
    private int rqspfPassStatus, rqspfSingleStatus;

    public int getRqspfPassStatus() {
        return rqspfPassStatus;
    }

    public void setRqspfPassStatus(int rqspfPassStatus) {
        this.rqspfPassStatus = rqspfPassStatus;
    }

    public int getRqspfSingleStatus() {
        return rqspfSingleStatus;
    }

    public void setRqspfSingleStatus(int rqspfSingleStatus) {
        this.rqspfSingleStatus = rqspfSingleStatus;
    }

    public int getSpfSingleStatus() {
        return spfSingleStatus;
    }

    public void setSpfSingleStatus(int spfSingleStatus) {
        this.spfSingleStatus = spfSingleStatus;
    }

    public int getZjqSingleStatus() {
        return zjqSingleStatus;
    }

    public void setZjqSingleStatus(int zjqSingleStatus) {
        this.zjqSingleStatus = zjqSingleStatus;
    }

    public int getBfSingleStatus() {
        return bfSingleStatus;
    }

    public void setBfSingleStatus(int bfSingleStatus) {
        this.bfSingleStatus = bfSingleStatus;
    }

    public int getBqcSingleStatus() {
        return bqcSingleStatus;
    }

    public void setBqcSingleStatus(int bqcSingleStatus) {
        this.bqcSingleStatus = bqcSingleStatus;
    }

    public int getSpfPassStatus() {
        return spfPassStatus;
    }

    public void setSpfPassStatus(int spfPassStatus) {
        this.spfPassStatus = spfPassStatus;
    }

    public int getZjqPassStatus() {
        return zjqPassStatus;
    }

    public void setZjqPassStatus(int zjqPassStatus) {
        this.zjqPassStatus = zjqPassStatus;
    }

    public int getBfPassStatus() {
        return bfPassStatus;
    }

    public void setBfPassStatus(int bfPassStatus) {
        this.bfPassStatus = bfPassStatus;
    }

    public int getBqcPassStatus() {
        return bqcPassStatus;
    }

    public void setBqcPassStatus(int bqcPassStatus) {
        this.bqcPassStatus = bqcPassStatus;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isshow() {
        return isshow;
    }

    public void setHostRank(int hostRank) {
        this.hostRank = hostRank;
    }

    public void setVisitRank(int visitRank) {
        this.visitRank = visitRank;
    }

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private static final long serialVersionUID = 1L;
    private Date sellEndTime;// 销售截止时间
    private String matchCode;
    private String dayOfWeekStr;// 星期几
    private int issueId;
    private int caiguo;
    String[] betCounter;
    private String lastScore;
    private boolean isshow = false; // 是否打开 投注比例，平均赔率等信息
    private double rate = 0.0;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    // 主队排名
    private int hostRank;

    public int getHostRank() {
        return hostRank;
    }

    public void setHostRank(Integer hostRank) {
        this.hostRank = hostRank;
    }

    // 客队排名
    private int visitRank;

    public String getLastScore() {
        return lastScore;
    }

    public void setLastScore(String lastScore) {
        this.lastScore = lastScore;
    }

    public int getVisitRank() {
        return visitRank;
    }

    public void setVisitRank(Integer visitRank) {
        this.visitRank = visitRank;
    }

    public boolean isIsshow() {
        return isshow;
    }

    public void setIsshow(boolean isshow) {
        this.isshow = isshow;
    }

    private boolean isZjqShow = false; // 是否打开 投注比例，平均赔率等信息(总进球)

    public boolean isZjqShow() {
        return isZjqShow;
    }

    public void setZjqShow(boolean isZjqShow) {
        this.isZjqShow = isZjqShow;
    }

    public String[] getBetCounter() {
        return betCounter;
    }

    public void setBetCounter(String[] betCounter) {
        this.betCounter = betCounter;
    }

    public double getMixSP() {
        return mixSP;
    }

    public void setMixSP(double mixSP) {
        this.mixSP = mixSP;
    }

    public double getMaxSP() {
        return maxSP;
    }

    public void setMaxSP(double maxSP) {
        this.maxSP = maxSP;
    }

    private double mixSP = 1000000000;
    private double maxSP = 0;

    public int getCaiguo() {
        return caiguo;
    }

    public void setCaiguo(int caiguo) {
        this.caiguo = caiguo;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getDayOfWeekStr() {
        return dayOfWeekStr;
    }

    public void setDayOfWeekStr(String dayOfWeekStr) {
        this.dayOfWeekStr = dayOfWeekStr;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public Date getSellEndTime() {
        return sellEndTime;
    }

    public void setSellEndTime(Date sellEndTime) {
        this.sellEndTime = sellEndTime;
    }

    // 胆码是否可见
    private int danVisible = View.INVISIBLE;

    public int getDanVisible() {
        return danVisible;
    }

    public void setDanVisible(int danVisible) {
        this.danVisible = danVisible;
    }

    // 胆码是否选择的状态值
    private boolean isDanTuo = false;

    public boolean isDanTuo() {
        return isDanTuo;
    }

    public void setDanTuo(boolean isDanTuo) {
        this.isDanTuo = isDanTuo;
    }


    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // 所在组的位置
    private int groupPosition = -1;
    private int childPosition = -1;

    public int getGroupPosition() {
        return groupPosition;
    }

    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }

    public int getChildPosition() {
        return childPosition;
    }

    public void setChildPosition(int childPosition) {
        this.childPosition = childPosition;
    }


    public boolean[] isSelected = new boolean[8];// 是否选中，如果是胜平负只用前三个

    private double[] spfpeilv = new double[3];// 胜平负的赔率

    private double[] rqSpfPeilv = new double[3];// 让球胜平负的赔率

    private double[] odds = new double[3];// 平均赔率


    public int orderIdLocal = 0;// 用于记录这场比赛在整个记录中的位置，方便投注确认页面正确排序


    // 重置所选的数据项
    public void reSetIsSelected() {
        isSelected = new boolean[8];

        spfFlag = -1;
    }

    public void reset() {
        setOtherSelect(null);
        isSelected = new boolean[8];
        spfFlag = -1;
        isDanTuo = false;
        selectedStr = "";
        selContentStr = "";
        selects = new ArrayList<>();

    }


    private String selContentStr;    //半全场，比分投注具体选择的投注按钮文字拼接成的串

    public String getSelContentStr() {
        return selContentStr;
    }

    public void setSelContentStr(String selContentStr) {
        this.selContentStr = selContentStr;
    }

    private String matchResult;

    public String getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(String matchResult) {
        this.matchResult = matchResult;
    }


    private String[] historyScore;// 历史排名

    public String[] getHistoryScore() {
        return historyScore;
    }

    public void setHistoryScore(String[] historyScore) {
        this.historyScore = historyScore;
    }

    public double[] getOdds() {
        return odds;
    }

    public void setOdds(double[] odds) {
        this.odds = odds;
    }

    public double[] getRqSpfPeilv() {
        return rqSpfPeilv;
    }

    public void setRqSpfPeilv(double[] rqSpfPeilv) {
        this.rqSpfPeilv = rqSpfPeilv;
    }

    private double[] zjqpeilv = new double[8];// 总进球的赔率，与胜平负的赔率不能同时存在


    public int getRangNumber() {

        return rangNumber;
    }

    public void setRangNumber(int rangNumber) {
        this.rangNumber = rangNumber;
    }

    public double[] getSpfpeilv() {
        return spfpeilv;
    }

    public void setSpfpeilv(double[] spfpeilv) {
        this.spfpeilv = spfpeilv;
    }

    public double[] getZjqpeilv() {
        return zjqpeilv;
    }

    public void setZjqpeilv(double[] zjqpeilv) {
        this.zjqpeilv = zjqpeilv;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    // 彩票大厅中竞彩足球比赛数据
    @SuppressLint("SimpleDateFormat")
    public static JingcaizuqiuOneGame create(JSONObject json,
                                             String dayOfWeekStr, int issueId) {
        JingcaizuqiuOneGame data = new JingcaizuqiuOneGame();
        data.setDayOfWeekStr(dayOfWeekStr);
        data.setIssueId(issueId);
        /**
         *过关单关 筛选的变量

         * spfSingleStatus;
         zjqSingleStatus;
         bfSingleStatus;
         bqcSingleStatus;
         spfPassStatus;
         zjqPassStatus;
         bfPassStatus;
         bqcPassStatus;
         zard
         */

        data.setSpfSingleStatus(json.getIntValue("spfSingleStatus"));
        data.setZjqSingleStatus(json.getIntValue("zjqSingleStatus"));
        data.setBfSingleStatus(json.getIntValue("bfSingleStatus"));
        data.setBqcSingleStatus(json.getIntValue("bqcSingleStatus"));


        data.setSpfPassStatus(json.getIntValue("spfPassStatus"));
        data.setZjqPassStatus(json.getIntValue("zjqPassStatus"));
        data.setBfPassStatus(json.getIntValue("bfPassStatus"));
        data.setBqcPassStatus(json.getIntValue("bqcPassStatus"));


        data.setRqspfSingleStatus(json.getIntValue("rqspfSingleStatus"));
        data.setRqspfPassStatus(json.getIntValue("rqspfPassStatus"));//rqspfPassStatus , rqspfSingleStatus

        data.setStatus(json.getIntValue("status"));
        data.setGameName(json.getString("leagueName"));
        data.setTeam1(json.getString(URLSuffix.hostName));
        data.setTeam2(json.getString(URLSuffix.guestName));
        data.setRangNumber((int) json.getDoubleValue("rate"));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt = df.parse(json.getString("sellEndTime"));
            data.setSellEndTime(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String time[] = json.getString("sellEndTime").split(" ");
        data.setTime(time[1].substring(0, time[1].lastIndexOf(":")));
        data.setMatchCode(json.getString("matchCode"));
        double[] spfpeilv = new double[3];
        spfpeilv[0] = json.getDouble(URLSuffix.sheng);
        spfpeilv[1] = json.getDouble(URLSuffix.ping);
        spfpeilv[2] = json.getDouble(URLSuffix.fu);
        data.setSpfpeilv(spfpeilv);
        // 让球胜平负赔率
        double[] rqSpfPeilv = new double[3];
        rqSpfPeilv[0] = json.getDouble("rqSheng");
        rqSpfPeilv[1] = json.getDouble("rqPing");
        rqSpfPeilv[2] = json.getDouble("rqFu");
        data.setRqSpfPeilv(rqSpfPeilv);
        // 只能有一个赔率
        double[] zjqpeilv = new double[8];
        zjqpeilv[0] = json.getDouble(URLSuffix.t0);
        zjqpeilv[1] = json.getDouble(URLSuffix.t1);
        zjqpeilv[2] = json.getDouble(URLSuffix.t2);
        zjqpeilv[3] = json.getDouble(URLSuffix.t3);
        zjqpeilv[4] = json.getDouble(URLSuffix.t4);
        zjqpeilv[5] = json.getDouble(URLSuffix.t5);
        zjqpeilv[6] = json.getDouble(URLSuffix.t6);
        zjqpeilv[7] = json.getDouble(URLSuffix.t7);
        data.setZjqpeilv(zjqpeilv);
        // /平均赔率
        double[] odds = new double[3];
        odds[0] = json.getDoubleValue("odds0");
        odds[1] = json.getDoubleValue("odds1");
        odds[2] = json.getDoubleValue("odds3");
        data.setOdds(odds);
        // 投注比例
//        data.setBetCounter(json.getString("betCounter").split(","));
        // //主队排名
//        data.setHostRank(json.getIntValue("hostRank"));
        // //客队排名
//        data.setVisitRank(json.getIntValue("visitRank"));
        // //历史排名
//        data.setHistoryScore(json.getString("historyScore").split(","));
        ///比分，半全场赔率
        data.getBqcList().add(json.getDouble("ss") + "");
        data.getBqcList().add(json.getDouble("sp") + "");
        data.getBqcList().add(json.getDouble("sf") + "");
        data.getBqcList().add(json.getDouble("ps") + "");
        data.getBqcList().add(json.getDouble("pp") + "");
        data.getBqcList().add(json.getDouble("pf") + "");
        data.getBqcList().add(json.getDouble("fs") + "");
        data.getBqcList().add(json.getDouble("fp") + "");
        data.getBqcList().add(json.getDouble("ff") + "");

        data.getBqcList().add(json.getDouble("s10") + "");
        data.getBqcList().add(json.getDouble("s20") + "");
        data.getBqcList().add(json.getDouble("s21") + "");
        data.getBqcList().add(json.getDouble("s30") + "");
        data.getBqcList().add(json.getDouble("s31") + "");
        data.getBqcList().add(json.getDouble("s32") + "");
        data.getBqcList().add(json.getDouble("s40") + "");
        data.getBqcList().add(json.getDouble("s41") + "");
        data.getBqcList().add(json.getDouble("s42") + "");
        data.getBqcList().add(json.getDouble("s50") + "");
        data.getBqcList().add(json.getDouble("s51") + "");
        data.getBqcList().add(json.getDouble("s52") + "");
        data.getBqcList().add(json.getDouble("sother") + "");

        data.getBqcList().add(json.getDouble("p00") + "");
        data.getBqcList().add(json.getDouble("p11") + "");
        data.getBqcList().add(json.getDouble("p22") + "");
        data.getBqcList().add(json.getDouble("p33") + "");
        data.getBqcList().add(json.getDouble("pother") + "");

        data.getBqcList().add(json.getDouble("f01") + "");
        data.getBqcList().add(json.getDouble("f02") + "");
        data.getBqcList().add(json.getDouble("f12") + "");
        data.getBqcList().add(json.getDouble("f03") + "");
        data.getBqcList().add(json.getDouble("f13") + "");
        data.getBqcList().add(json.getDouble("f23") + "");
        data.getBqcList().add(json.getDouble("f04") + "");
        data.getBqcList().add(json.getDouble("f14") + "");
        data.getBqcList().add(json.getDouble("f24") + "");
        data.getBqcList().add(json.getDouble("f05") + "");
        data.getBqcList().add(json.getDouble("f15") + "");
        data.getBqcList().add(json.getDouble("f25") + "");
        data.getBqcList().add(json.getDouble("fother") + "");
        return data;
    }

    // 方案详情
    public static JingcaizuqiuOneGame JoinCreate(String json, int type) {
        //System.out.println(json.toString());
        JingcaizuqiuOneGame data = new JingcaizuqiuOneGame();
        data.setTeam1(JSON.parseObject(json).getString(URLSuffix.hostName));
        data.setTeam2(JSON.parseObject(json).getString(URLSuffix.guestName));
        // 胆码
        if (!"null".equals(String.valueOf(JSON.parseObject(json).getString(URLSuffix.dan))))
            data.setDanTuo(JSON.parseObject(json).getBooleanValue(URLSuffix.dan));
        data.setCaiguo(JSON.parseObject(json).getIntValue(URLSuffix.bingo));
        data.setMatchResult(JSON.parseObject(json).getString("matchResult"));
        data.setLastScore(JSON.parseObject(json).getString("lastScore"));
        data.setMatchCode(JSON.parseObject(json).getString("matchCode"));
        data.setDayOfWeekStr(JSON.parseObject(json).getString("dayOfWeekStr"));
        JSONArray numberList = JSON.parseObject(json).getJSONArray(URLSuffix.choose);
        if (type == 288 || type == 426) { // 让球胜平负，胜平负
            data.setRangNumber((int) JSON.parseObject(json).getIntValue(URLSuffix.rate));
            // 赔率
            double[] spfpeilv = new double[3];
            if (type == 288) {
                spfpeilv[0] = JSON.parseObject(json).getDoubleValue(URLSuffix.sheng);
                spfpeilv[1] = JSON.parseObject(json).getDoubleValue(URLSuffix.ping);
                spfpeilv[2] = JSON.parseObject(json).getDoubleValue(URLSuffix.fu);
                data.setSpfpeilv(spfpeilv);
            } else {
                spfpeilv[0] = JSON.parseObject(json).getDoubleValue("rqSheng");
                spfpeilv[1] = JSON.parseObject(json).getDoubleValue("rqPing");
                spfpeilv[2] = JSON.parseObject(json).getDoubleValue("rqFu");
                data.setRqSpfPeilv(spfpeilv);
            }

            for (int i = 0; i < numberList.size(); i++) {
                if (numberList.getIntValue(i) == 3) {
                    data.isSelected[0] = true;
                } else if (numberList.getIntValue(i) == 1) {
                    data.isSelected[1] = true;
                } else if (numberList.getIntValue(i) == 0) {
                    data.isSelected[2] = true;
                }
            }
        } else if (type == 289) {// 总进球
            String time[] = JSON.parseObject(json).getString("sellEndTime").split(" ");
            data.setTime(time[1].substring(0, time[1].lastIndexOf(":")));
            ;
            // 赔率
            double[] zjqpeilv = new double[8];
            zjqpeilv[0] = JSON.parseObject(json).getDoubleValue(URLSuffix.t0);
            zjqpeilv[1] = JSON.parseObject(json).getDoubleValue(URLSuffix.t1);
            zjqpeilv[2] = JSON.parseObject(json).getDoubleValue(URLSuffix.t2);
            zjqpeilv[3] = JSON.parseObject(json).getDoubleValue(URLSuffix.t3);
            zjqpeilv[4] = JSON.parseObject(json).getDoubleValue(URLSuffix.t4);
            zjqpeilv[5] = JSON.parseObject(json).getDoubleValue(URLSuffix.t5);
            zjqpeilv[6] = JSON.parseObject(json).getDoubleValue(URLSuffix.t6);
            zjqpeilv[7] = JSON.parseObject(json).getDoubleValue(URLSuffix.t7);
            data.setZjqpeilv(zjqpeilv);
            for (int i = 0; i < numberList.size(); i++) {
                data.isSelected[numberList.getIntValue(i)] = true;
            }
        } else if (type == 291) { // /半全场
            data.setMatchResult(JSON.parseObject(json).getString("matchResult")
                    .replace('0', '负').replace('1', '平').replace('3', '胜'));
            for (int i = 0; i < numberList.size(); i++) {
                switch (numberList.getIntValue(i)) {
                    case 0:
                        data.getBqcList().add("胜胜");
                        break;
                    case 1:
                        data.getBqcList().add("胜平");
                        break;
                    case 2:
                        data.getBqcList().add("胜负");
                        break;
                    case 3:
                        data.getBqcList().add("平胜");
                        break;
                    case 4:
                        data.getBqcList().add("平平");
                        break;
                    case 5:
                        data.getBqcList().add("平负");
                        break;
                    case 6:
                        data.getBqcList().add("负胜");
                        break;
                    case 7:
                        data.getBqcList().add("负平");
                        break;
                    case 8:
                        data.getBqcList().add("负负");
                        break;
                }
            }
        } else if (type == 290) { // /比分
            String str = "";
            for (int i = 1; i <= numberList.size(); i++) {
                //System.out.println(i+":"+data.getBiFenStr()[numberList.getInt(i - 1)]);
//				if (i % 5 == 0)
//					str = "";
                str += data.getBiFenStr()[numberList.getIntValue(i - 1)] + ",";
                if (i % 4 == 0 || i == numberList.size()) {
//					System.out.println("stgggr:"+str.substring(0, str.length() - 1));
                    if (str.substring(0, str.length() - 1).contains("胜其他")
                            || str.substring(0, str.length() - 1).contains("平其他")
                            || str.substring(0, str.length() - 1).contains("负其他")) {
                        String[] temp = str.substring(0, str.length() - 1).split(",");
                        if (temp.length > 2) {
                            data.getBqcList().add(temp[0] + "," + temp[1]);
                            if (temp.length > 3)
                                data.getBqcList().add(temp[2] + "," + temp[3]);
                            else
                                data.getBqcList().add(temp[2]);
                        } else
                            data.getBqcList().add(str.substring(0, str.length() - 1));
                    } else
                        data.getBqcList().add(str.substring(0, str.length() - 1));
                    str = "";
                }
            }
            //冠亚军
        } else if (type == 427 || type == 422 || type == 423) {
            data.setRate(JSON.parseObject(json).getDoubleValue("rate"));
        } else { // 混合投注
            String str = "";
            String[] strList = JSON.parseObject(json).getString("msg").split(",");
            for (int i = 1; i <= strList.length; i++) {
                if (i % 2 == 1)
                    str = "";
                str += strList[i - 1] + ",";
                if (i % 2 == 0 || i == numberList.size()) {
                    data.getBqcList().add(str.substring(0, str.length() - 1));
                }
            }
        }
        return data;
    }


    public static JingcaizuqiuOneGame create() {
        JingcaizuqiuOneGame data = new JingcaizuqiuOneGame();
        data.setGameName("欧冠联赛");
        data.setSpfpeilv(new double[]{1.2, 2.5, 4.5});
        // 只能有一个赔率
        data.setZjqpeilv(new double[]{1.2, 2.5, 4.5, 2, 2.5, 4.5, 4.5});
        data.setRangNumber(1);
        data.setTeam1("莫斯科");
        data.setTeam2("费内巴切");
        data.setTime("01:20");
        data.setMatchCode("20130910001");
        return data;
    }

    //混合投注投注类型工具
    private String getTouZhuType(int i) {
        switch (i) {
            case 0:
                return "胜" + spfpeilv[0];
            case 1:
                return "平" + spfpeilv[1];
            case 2:
                return "负" + spfpeilv[2];
            case 3:
                return "让球胜" + rqSpfPeilv[0];
            case 4:
                return "让球平" + rqSpfPeilv[1];
            case 5:
                return "让球负" + rqSpfPeilv[2];
            default:
                return "";
        }
    }

    // /填充投注确认和方案详情页面中的view
    public void fillXiangQingView(int wf, TextView dw, TextView tz,
                                  TextView cg, TextView changci, final Activity activity) {
        DecimalFormat dft = new DecimalFormat("0.00");
        double[] spf = this.getSpfpeilv();
        String rqStr = "";
        if (activity != null) {
            dw.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.putExtra("title", "比分详情");
                    intent.putExtra("url", IP.ZST_M + "/saishi/matchdetail?flag=1&matchCode=" + JingcaizuqiuOneGame.this.getMatchCode());
                    intent.setClass(activity, WebViewActivity.class);
                    activity.startActivity(intent);
                }
            });
        }
        if (wf == 1) {
            spf = this.getRqSpfPeilv();
            if (this.getRangNumber() > 0)
                rqStr = "(+" + String.valueOf(getRangNumber()) + ")";
            else
                rqStr = "(" + String.valueOf(getRangNumber()) + ")";
            //冠亚军
        } else if (wf == 7) {
            changci.setText(this.getMatchCode());
            if (this.getTeam2() != null && !this.getTeam2().equals("null")) {
                dw.setText(this.getTeam1() + "\n--\n" + this.getTeam2());
            } else {
                dw.setText(this.getTeam1());
            }
            tz.setText(this.getRate() + "");
            if (this.getMatchResult() != null
                    && this.getMatchResult().equals("null")
                    || TextUtils.isEmpty(this.getMatchResult()))
                cg.setText("");
            else
                cg.setText(this.getMatchResult());
            return;
        }


        String changciStr = "";
        if (changci != null) {
            changciStr = this.getDayOfWeekStr() + "\n" + this.getMatchCode().substring(8);
        }

        String bfStr = "\nVS\n";
        if (this.getLastScore() != null && !this.getLastScore().equals("null")) {
            bfStr = "\n" + this.getLastScore() + "\n";
        }
        String dwStr = getTeam1() + rqStr + bfStr + getTeam2();

        String cgStr;
        if (this.getMatchResult() != null
                && this.getLastScore().trim().equals("null")) {
            cgStr = "\n--:--\n";
        } else {
            cgStr = "\n" + this.getMatchResult() + "\n";
        }


        String tzStr = "";
        String isDan = "";
        if (isDanTuo())
            isDan = "(胆)";
        int count = 0;
        switch (wf) {
            case 0: // /胜平负
            case 1: // 让球胜平负
                if (isSelected[0]) {
                    if (count == 0)
                        tzStr += "胜 " + dft.format(spf[0]) + isDan + "\n";
                    else
                        tzStr += "胜 " + dft.format(spf[0]) + "\n";
                    count++;
                }
                if (isSelected[1]) {
                    if (count == 0)
                        tzStr += "平 " + dft.format(spf[1]) + isDan + "\n";
                    else
                        tzStr += "平 " + dft.format(spf[1]) + "\n";
                    count++;
                }
                if (isSelected[2]) {
                    if (count == 2)
                        tzStr += "负 " + dft.format(spf[2]);
                    else {
                        if (count == 0)
                            tzStr += "负 " + dft.format(spf[2]) + isDan + "\n";
                        else
                            tzStr += "负 " + dft.format(spf[2]) + "\n";
                        count++;
                    }
                }
                // 补换行
                if (count == 1) {
                    tzStr = "\n" + tzStr;
                } else if (count == 2) {
                    tzStr = "\n" + tzStr;
                }
                // /赛果
                cgStr += "\n";
                // 比分
                bfStr += "\n";
                break;
            case 2:// 总进球
                dwStr += "\n";
                if (this.getMatchResult() != null
                        && this.getMatchResult().equals("null"))
                    cgStr = "\n--:--";
                else
                    cgStr = "\n" + this.getMatchResult() + "";
                bfStr = "\n--:--\n\n";
                if (this.getLastScore() != null
                        && !this.getLastScore().equals("null")) {
                    bfStr = "\n" + this.getLastScore() + "\n\n";
                }
                cgStr += "\n\n";
                for (int i = 0; i < isSelected.length; i++) {
                    if (isSelected[i]) {
                        if (i != 7) {
                            tzStr += i + "球" + dft.format(zjqpeilv[i]);
                        } else
                            tzStr += "7+球" + dft.format(zjqpeilv[i]);
                        if (count == 0)
                            tzStr += isDan;
                        tzStr += "\n";
                        count++;
                    }
                }
                if (count == 1) {
                    tzStr += "\n\n";
                } else if (count == 2)
                    tzStr += "\n";
                else if (count > 3) {
                    tzStr = tzStr.substring(0, tzStr.lastIndexOf("\n"));
                    count--;
                    // /补换行
                    for (int i = 0; i < count - 3; i++) {
                        dwStr += "\n";
                        cgStr += "\n";
                        bfStr += "\n";
                    }
                }
                break;
            case 3: // /半全场
            case 4: // /比分
                dwStr += "\n";
                bfStr = "\n--:--\n\n";
                if (null != this.getLastScore() && !this.getLastScore().equals("null")) {
                    bfStr = "\n" + this.getLastScore() + "\n\n";
                }
                tzStr = bqcList.get(0) + isDan + "\n";
                if (TextUtils.isEmpty(getMatchResult())
                        || getMatchResult().equals("null")) {
                    cgStr = "\n--:--" + "\n\n";
                } else
                    cgStr = "\n" + getMatchResult() + "\n\n";
                if (bqcList.size() == 1) {
                    tzStr += "\n\n";
                } else if (bqcList.size() == 2) {
                    tzStr += bqcList.get(1) + "\n\n";
                } else if (bqcList.size() == 3) {
                    tzStr += bqcList.get(1) + "\n" + bqcList.get(1) + "\n";
                } else if (bqcList.size() > 3) {
                    for (int i = 1; i < bqcList.size(); i++) {
                        if (i != bqcList.size() - 1) {
                            tzStr += bqcList.get(i) + "\n";
                            count++;
                            if (count > 2) {
                                dwStr += "\n";
                                cgStr += "\n";
                                bfStr += "\n";
                            }
                        } else
                            tzStr += bqcList.get(i);
                    }
                }
                break;
            case 5: // /混合投注
                dwStr += "\n";
                bfStr = "\n--:--" + "\n\n\n";
                if (this.getLastScore() != null
                        && !this.getLastScore().equals("null")) {
                    bfStr = this.getLastScore() + "\n\n\n\n";
                }
                if (TextUtils.isEmpty(getMatchResult())
                        || getMatchResult().equals("null")) {
                    cgStr = "\n--:--" + "\n\n\n";
                } else {
                    String[] result = getMatchResult().split("/");
                    cgStr = getMatchResult().replace("/", "\n");
                    for (int i = 0; i < 5 - result.length; i++)
                        cgStr += "\n";
                }
                dwStr += "\n";
                for (int i = 0; i < bqcList.size(); i++) {
                    if (i != bqcList.size() - 1)
                        tzStr += bqcList.get(i) + "\n";
                    else
                        tzStr += bqcList.get(i);
                }
                if (bqcList.size() < 5) {
                    for (int i = 0; i < 5 - bqcList.size(); i++) {
                        tzStr += "\n";
                    }
                } else {
                    // 补换行符
                    for (int i = 0; i < bqcList.size() - 5; i++) {
                        dwStr += "\n";
                        cgStr += "\n";
                        bfStr += "\n";
                    }
                }
                break;
            case 6: // /混合投注投注确认
                dwStr += "\n";
                for (int i = 0; i < 6; i++) {
                    if (isSelected[i]) {
                        tzStr += getTouZhuType(i);
                        if (count == 0)
                            tzStr += isDan;
                        tzStr += "\n";
                        count++;
                    }
                }
                if (count == 1) {
                    tzStr += "\n\n";
                } else if (count == 2)
                    tzStr += "\n";
                else if (count > 3) {
                    tzStr = tzStr.substring(0, tzStr.lastIndexOf("\n"));
                    count--;
                    // /补换行
                    for (int i = 0; i < count - 3; i++) {
                        dwStr += "\n";
                    }
                }
                break;
            case 8: // /比分，半全场投注投注确认
                dwStr += "\n";
                String[] str = this.getSelContentStr().split(",");
                for (int i = 0; i < str.length; i++) {
                    if (i % 2 != 1)
                        tzStr += str[i] + " ";
                    else {
                        tzStr += str[i] + "\n";
                        count++;
                    }
                }
                if (count < 3) {
                    for (int i = 0; i < 3 - count; i++) {
                        tzStr += "\n";
                    }
                } else if (count > 3) {
                    for (int i = 0; i < count - 3; i++) {
                        dwStr += "\n";
                    }
                }
                break;
        }
        dw.setText(dwStr);
        tz.setText(tzStr);
        if (cg != null) {
            cg.setText(cgStr);
        }
        if (changci != null) {
            changci.setText(changciStr);
        }


    }

    @Override
    public String toString() {
        return "JingcaizuqiuOneGame [sellEndTime=" + sellEndTime
                + ", matchCode=" + matchCode + ", dayOfWeekStr=" + dayOfWeekStr
                + ", issueId=" + issueId + ", caiguo=" + caiguo
                + ", betCounter=" + Arrays.toString(betCounter)
                + ", lastScore=" + lastScore + ", isshow=" + isshow
                + ", hostRank=" + hostRank + ", visitRank=" + visitRank
                + ", isZjqShow=" + isZjqShow + ", mixSP=" + mixSP + ", maxSP="
                + maxSP + ", danVisible=" + danVisible + ", isDanTuo="
                + isDanTuo + ", spfFlag=" + spfFlag + ", id=" + id
                + ", groupPosition=" + groupPosition + ", childPosition="
                + childPosition + ", gameName=" + gameName + ", time=" + time
                + ", team1=" + team1 + ", team2=" + team2 + ", rangNumber="
                + rangNumber + ", isSelected=" + Arrays.toString(isSelected)
                + ", spfpeilv=" + Arrays.toString(spfpeilv) + ", rqSpfPeilv="
                + Arrays.toString(rqSpfPeilv) + ", odds="
                + Arrays.toString(odds) + ", bqcList=" + bqcList
                + ", biFenStr=" + Arrays.toString(biFenStr) + ", matchResult="
                + matchResult + ", historyScore="
                + Arrays.toString(historyScore) + ", zjqpeilv="
                + Arrays.toString(zjqpeilv) + "]";
    }

    private String spfShow;

    public String getSpfShow() {
        return spfShow;
    }

    public void setSpfShow(String spfShow) {
        this.spfShow = spfShow;
    }
}
