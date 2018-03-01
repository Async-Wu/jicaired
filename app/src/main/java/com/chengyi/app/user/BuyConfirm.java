package com.chengyi.app.user;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.jingji.basket.BasketballOneGame;
import com.chengyi.app.listener.IBuyCallBack;
import com.chengyi.app.model.SchemeMode;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.model.GoumaiData;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.IP;
import com.chengyi.app.util.L;
import com.chengyi.app.web.WebViewActivity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */

/**
 * 转换为支付util
 */
public class BuyConfirm {

    int issueId = 0; // 奖期id
    String schemeNumber; // 方案号码


    private HashMap<String, String> params;

    HashSet<String> hashSet = new HashSet<String>();
    JingcaizuqiuOneGame tempGame;
    private String type = "";


    List<TouzhuquerenData> list;
    List<JingcaizuqiuOneGame> listTemp;
    List<BasketballOneGame> listBasketball;


    private void getJiangQI() {

        RequestParams m = new RequestParams();
        m.put(URLSuffix.lotteryId, CaipiaoUtil.getCpId(cp.getId()));

        HttpBusinessAPI.post(URLSuffix.LOTTERY_QUERY_CUR_ISSUE, m,  new HttpRespHandler() {
                    @Override
                    public void onSuccess(String result) {
                        if (JSON.parseObject(result).getIntValue(URLSuffix.flag) == 1) {
                            cp.setIssue(JSON.parseObject(result).getString("issue"));
                            cp.setIssueId(JSON.parseObject(result).getIntValue("issueId"));
                            cp.setRemainTime(JSON.parseObject(result).getIntValue("remainTime"));

                        } else {
                            cp.setIssue("当前没有销售奖期");
                            cp.setRemainTime(1000 * 60 * 10);//errorMessage

                        }
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        super.onFailure(error);

                    }
                });
    }


    private Caipiao cp;
    private boolean isLucky;
    private GoumaiData goumaiData;
    private int flag, wfIndex;
    private Context context;

    private IBuyCallBack buyCallBack;


    public BuyConfirm(Caipiao caipiao, boolean isLucky, GoumaiData goumaiData, int wfIndex, int flag, Context context, IBuyCallBack buyCallBack) {
        this.cp = caipiao;
        this.goumaiData = goumaiData;
        this.isLucky = isLucky;
        this.flag = flag;
        this.wfIndex = wfIndex;
        this.buyCallBack = buyCallBack;
        this.context = context;


        init();


    }

    private void init() {


        list = CaipiaoApplication.getInstance().getTouzhuHaomaList();
        if (cp.getIssue() != null) {
        } else {

            getJiangQI();
        }


        // 追号期数
        if (goumaiData.getZhuihaoqishu() > 1) {

            if (goumaiData.isDayutingzhiChecked()
                    && goumaiData.getDayutingzhi() > 0) {

            }
        }


        if (list != null && list.size() > 0 && wfIndex == -1) {


        } else if ((cp.getId() == CaipiaoConst.ID_JINGCAIZUQIU)) {
            listTemp = goumaiData.getListTemp();

            // 冠亚军竞猜
        } else if (cp.getId() == CaipiaoConst.ID_GUANYAJUN) {
            // 初始化投注号码
            schemeNumber = "content=";

            try {
                issueId = Integer.parseInt(goumaiData.getIssueIdStr());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                issueId = 0;
            }


            // 竞彩篮球
        } else if (cp.getId() == CaipiaoConst.ID_BASKETBALL) {

            listBasketball = goumaiData.getListBasketball();

        }


    }

    public void submitData() {
        // 智能追号的方案发起时，判断下所追的期数中是否存在过期的期数
        if (goumaiData.getZhuihaoqishu() > 1
                && !TextUtils.isEmpty(goumaiData.getIssueIdStr())) {
            try {
                // /数据过期
                if (Long.parseLong(goumaiData.getIssueStr().split(",")[0]) < Long
                        .parseLong(cp.getIssue())) {

                    return;
                } else {
                    buy();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                buy();
            }
        } else {
            // 判断奖期是否过期等
            if (checkJiangqi()) {
                buy();// 代购
            }
        }
    }


    private boolean checkJiangqi() {
        if (cp.getCaipiaoType() == 3)
            return true;
        if (cp.getIssue() == null || cp.getIssue().equals("null")
                || TextUtils.isEmpty(cp.getIssue()) || cp.getIssue().contains("没有销售奖期")) {

            return false;
        }
        boolean isguoqi = true;

//        if (!isguoqi) {
//            if (!exitDailog.isShowing()) {
//                exitDailog.show();
//                exitDailog.getCenterTextView().setText("奖期已过期是否更新到最新奖期?");
//            }
//        }
        return isguoqi;
    }


    private void buy() {

        if (cp.getId() == CaipiaoConst.ID_BALL) {
            cp.setIssue(goumaiData.getIssue());
            cp.setIssueId(Integer.parseInt(goumaiData.getIssue()));
        }
        if (wfIndex == -1 && cp.getIssue() == null) {

            return;
        }


        params = new HashMap<>();


        params.put("lotteryId", CaipiaoUtil.getCpId(cp.getId()));

        if (wfIndex == -1) {
            if (cp.getCaipiaoType() != 3)
                issueId = cp.getIssueId();
            else {
                try {
                    issueId = Integer.parseInt(goumaiData.getIssueIdStr());
                } catch (Exception e) {
                }
            }
            schemeNumber = goumaiData.getSubmitString();
        } else if (cp.getId() == CaipiaoConst.ID_JINGCAIZUQIU) {
            // 竞彩足球特殊参数
            if (wfIndex == 0) {
                params.put(URLSuffix.betType, 288 + "");
                type = 288 + "";
            } else if (wfIndex == 1) {
                params.put(URLSuffix.betType, 426 + "");
                type = 426 + "";
            } else if (wfIndex == 2) {
                params.put(URLSuffix.betType, 289 + "");
                type = 289 + "";
            } else if (wfIndex == 4) {
                params.put(URLSuffix.betType, 291 + "");
                type = 291 + "";
            } else if (wfIndex == 5) {
                params.put(URLSuffix.betType, 290 + "");
                type = 290 + "";
            } else {
                /*
                 * caoql 足球混合过关投注，betType=42	 */
                params.put(URLSuffix.betType, 424 + "");
                type = 424 + "";
            }
            try {
                getSchemeNumberAndIssueId();
            } catch (Exception e) {
                e.printStackTrace();
            }

            params.put("issue", cp.getIssue());


            if (flag == 1) {
                params.put("pass", "单关");
            } else {
                params.put("pass", goumaiData.getGuoGuanStr().replace("/", ","));
            }
            params.put(URLSuffix.cutRepeat, "false");

            params.put(URLSuffix.sels, CaipiaoApplication.getInstance()
                    .getJingcaixiaoshouJiangqi());

            // 冠亚军竞猜
        } else if (cp.getId() == CaipiaoConst.ID_GUANYAJUN) {
            if (wfIndex == 0) {
                params.put(URLSuffix.betType, 427 + "");
                type = "427";
                params.put(URLSuffix.pass, "冠军");
            } else if (wfIndex == 1) {
                params.put(URLSuffix.betType, 423 + "");
                type = "423";
                // System.out.println("betType:422");
                params.put(URLSuffix.pass, "冠亚军");
            }
            try {

                schemeNumber = schemeNumber.substring(0,
                        schemeNumber.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
                schemeNumber = "";
            }
            // System.out.println("schemeNumber:"+schemeNumber);
        } else if (cp.getId() == CaipiaoConst.ID_BASKETBALL) {
            getBasketballSchemeNumberAndIssueId();
            // 竞彩篮球特殊参数
            if (wfIndex == 0) {
                params.put(URLSuffix.betType, 284 + "");
                type = "284";
            } else if (wfIndex == 1) {
                params.put(URLSuffix.betType, 285 + "");
                type = "285";
            } else if (wfIndex == 2) {
                params.put(URLSuffix.betType, 286 + "");
                type = "286";
            } else if (wfIndex == 3) {
                type = "287";
                params.put(URLSuffix.betType, 287 + "");
            } else if (wfIndex == 4) {
                type = "425";
                params.put(URLSuffix.betType, 425 + "");
            }

            if (flag == 0) {
                params.put(URLSuffix.pass,
                        goumaiData.getGuoGuanStr().replace("/", ","));
            } else {
                params.put(URLSuffix.pass, "单关");
            }
            params.put(URLSuffix.cutRepeat, "false");

            params.put(URLSuffix.sels, basketSelId.substring(0,
                    basketSelId.lastIndexOf(",")));

//
            String issue = basketissueId;
            if (issue.contains(",")) {
                params.put("issueIds", issue);

            } else {
                params.put("issueId", issue);
            }
//

        } else if (cp.getId() == CaipiaoConst.ID_BALL) {
            if (flag == 1) {
                params.put("pass",
                        "单关");
            } else {
                params.put("pass",
                        goumaiData.getGuoGuanStr().replace("/", ","));
            }
            params.put(URLSuffix.cutRepeat, "false");

            if (wfIndex == 1) {
                params.put("betType", "276");
                type = "276";
            } else if (wfIndex == 0) {
                params.put("betType", "274");
                type = "274";
            } else if (wfIndex == 3) {
                params.put("betType", "280");
                type = "280";
            } else if (wfIndex == 4) {
                params.put("betType", "282");
                type = "282";
            } else if (wfIndex == 2) {
                params.put("betType", "278");
                type = "278";
            }
            params.put("issue", goumaiData.getIssue());
            params.put("issueId", goumaiData.getIssue());


        }


        if (cp.getId() != CaipiaoConst.ID_BASKETBALL) {
            if (cp.getId() == CaipiaoConst.ID_BALL) {
                params.put("issueId", goumaiData.getIssue());
            } else {
                params.put("issueId", issueId + "");
            }
        }

        params.put("issueCount", goumaiData.getZhuihaoqishu() + "");
        params.put("multiple", goumaiData.getBeishu() + "");
        if (TextUtils.isEmpty(goumaiData.getShow())) {
            params.put("schemeNumber", schemeNumber);
        } else {
            params.put("schemeNumber", goumaiData.getShow());
        }
        params.put("schemeAmount", goumaiData.getTotalMoney() + "");


        if (goumaiData.getCaipiaoId() == CaipiaoConst.ID_DALETOU) {
            params.put("additional", goumaiData.isZhuijiatouzhu() ? "1" : "0");
        }
        if (goumaiData.isHemai()) {// 如果是大乐透还需要加additional字段，以后做的时候注意
            params.put("buyAmount", goumaiData.getWoyaorengou());
            params.put("buyType", "0");// 合买
            params.put("minParticipant", goumaiData.getZuidirengou());// 最低
            params.put("remuneration", goumaiData.getYingliyongjin() + "");// 佣金比例
            params.put("open", goumaiData.getShifougongkai() + "");// 合买方案公开情况
            params.put("schemeDesc", goumaiData.getMiaoshu());// 描述
            if (goumaiData.getWoyaobaodi() > 0) {
                params.put("safeguardMoney", goumaiData.getWoyaobaodi() + "");// 保底
            }

        } else {
            params.put("buyAmount", goumaiData.getTotalMoney() + "");
            params.put("buyType", "1");// 代购
        }
        if (goumaiData.getZhuihaoqishu() > 1) {// 是追号才要设置追号停止条件
            if (goumaiData.isDayutingzhiChecked()
                    && goumaiData.getDayutingzhi() >= 0) {// 选中并大于等于0
                params.put("prizeStop", goumaiData.getDayutingzhi() + "");
            }
            if (cp.isKuaikai()) {
                if (goumaiData.isKaichutingzhi()) {
                    // 0：开出停止追号; 1：不限。快开类型彩种才有此设置
                    params.put("burstIntoStop", "0");
                } else {
                    params.put("burstIntoStop", "1");
                }
            }


            // 智能倍投追号
            params.put("issueIds", goumaiData.getIssueIdStr());
            params.put("multiples", goumaiData.getMultipleStr());
        }
        params.put("schemeNumberUnit", goumaiData.getTotalZhushu() + "");
        if (flag == 1) {
            params.put("pass", "单关");
        }

        L.i("buy_json", params.toString());

        HttpBusinessAPI.post(URLSuffix.BUY_LOTTERY, params, new HttpRespHandler() {
            @Override
            public void onFailure(Throwable error) {

                super.onFailure(error);
                buyCallBack.buyCancle();

            }

            @Override
            public void onSuccess(String result) {
                buyCallBack.buyComplete();
                SchemeMode schemeMode = JSONObject.parseObject(result, SchemeMode.class);
                if (schemeMode.getFlag() == 1 && !TextUtils.isEmpty(schemeMode.getSchemeId() + "")) {

                    context.startActivity(new Intent(context, WebViewActivity.class)
                            .putExtra("url", IP.IP + "lottery/scheme_confirm.htm?clientUserSession=" + CaipiaoApplication.getInstance().getSession() + "&schemeId=" +
                                    schemeMode.getSchemeId() + "&betType=" + type + "&appVersion=" + CaipiaoConst.APPVERSION + "&version=" + CaipiaoConst.VERSION + "&requestType=" + CaipiaoConst.REQUESTTYPE));

                } else if (schemeMode.getFlag() == 2) {
                    showToast(schemeMode.getErrorMessage());

                } else {
                    Toast.makeText(context, schemeMode.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    if (schemeMode.getErrorMessage().contains("奖期已截止销售")) {


//                        getJiangQI();
                    }
                }
            }
        });


    }

    private void showToast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    private void getSchemeNumberAndIssueId() {
        StringBuilder builder = new StringBuilder();
        builder.append("content=");
        int max = -1;
        for (JingcaizuqiuOneGame game : listTemp) {
            if (game.getGroupPosition() > max) {
                max = game.getGroupPosition();
                issueId = game.getIssueId();
            }
            builder.append(game.getMatchCode() + "`");
            builder.append(game.getDayOfWeekStr() + " "
                    + game.getMatchCode().substring(8) + "`");
            builder.append(game.getTeam1() + "`");
            builder.append(game.getTeam2() + "`");
            builder.append(game.getMatchCode() + "_0" + "`");
            if (game.isDanTuo()) {
                builder.append(true + "`");
            } else {
                builder.append(false + "`");
            }
            if (wfIndex == 0 || wfIndex == 1) {
                if (game.isSelected[0]) {
                    builder.append(3 + ",");
                }
                if (game.isSelected[1]) {
                    builder.append(1 + ",");
                }
                if (game.isSelected[2]) {
                    builder.append(0 + ",");
                }
            } else if (wfIndex == 3) {
                if (game.isSelected[0]) {
                    builder.append(3 + ",");
                }
                if (game.isSelected[1]) {
                    builder.append(1 + ",");
                }
                if (game.isSelected[2]) {
                    builder.append(0 + ",");
                }
                if (game.isSelected[3]) {
                    builder.append(403 + ",");
                }
                if (game.isSelected[4]) {
                    builder.append(401 + ",");
                }
                if (game.isSelected[5]) {
                    builder.append(400 + ",");
                }


            } else if (wfIndex == 4 || wfIndex == 5) {
                builder.append(game.getSelectedStr() + ",");
            } else if (wfIndex == 6) {
                builder.append(game.getMixContent()+",");
            } else {
                for (int j = 0; j < game.isSelected.length; j++) {
                    if (game.isSelected[j]) {
                        builder.append(j + ",");
                    }
                }
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append("$");
        }
        builder.deleteCharAt(builder.length() - 1);
        schemeNumber = builder.toString();

    }

    String basketissueId = "";
    String basketSelId = "";
//	int basketCount=0;
//	String basketBeiShu="";

    private void getBasketballSchemeNumberAndIssueId() {
        StringBuilder builder = new StringBuilder();
        builder.append("content=");
        int max = -1;

        int id = 0;
        for (BasketballOneGame game : listBasketball) {

            if (game.groupPosition > max) {
                max = game.groupPosition;
//				issueId += game.issueGG;
                basketissueId = game.issueId + "";
                basketSelId = basketSelId + game.issue + ",";
//				basketCount+=1;
//				basketBeiShu=basketBeiShu+goumaiData.getBeishu()+",";
//				L.e("groupPosition basketCount", ""+basketCount);
            }

            builder.append(game.matchCodeGG + "`");
            builder.append(game.dayOfWeek + game.matchCodeGG.substring(8) + "`");
            builder.append(game.hostName + "`");
            builder.append(game.guestName + "`");
            builder.append("B" + game.matchCodeGG + "_" + id + "`");
            id++;
            // builder.append("B"+game.matchCodeGG+"_0`false`");

            if (game.isDanPressed) {
                builder.append(true + "`");
            } else {
                builder.append(false + "`");
            }
            if (wfIndex == 0 || wfIndex == 1) {
                if (game.isSFSelected[0]) {
                    builder.append(3 + ",");
                }
                if (game.isSFSelected[1]) {
                    builder.append(0 + ",");
                }

            } else if (wfIndex == 2) {
                for (int i = 0; i < 6; i++) {
                    if (game.isSFCHostSelected[i]) {
                        builder.append(game.sfcHostRequestId[i] + ",");
                    }
                    if (game.isSFCGuestSelected[i]) {
                        builder.append(game.sfcGuestRequestId[i] + ",");
                    }
                }
            } else if (wfIndex == 3) {
                if (game.isSFSelected[0]) {
                    builder.append(1 + ",");
                }
                if (game.isSFSelected[1]) {
                    builder.append(0 + ",");
                }
            } else if (wfIndex == 4) {
                if (game.isSFSelected[0]) {
                    builder.append(3 + ",");
                }
                if (game.isSFSelected[1]) {
                    builder.append(0 + ",");
                }
                if (game.isRFSFSelecter[0]) {
                    builder.append(103 + ",");
                }
                if (game.isRFSFSelecter[1]) {
                    builder.append(100 + ",");
                }
                if (game.isBigSmallSelect[0]) {
                    builder.append(202 + ",");
                }
                if (game.isBigSmallSelect[1]) {
                    builder.append(201 + ",");
                }
                for (int i = 0; i < 6; i++) {
                    if (game.isSFCHostSelected[i]) {
                        builder.append("30" + game.mixHostRequestId[i] + ",");
                    }
                    if (game.isSFCGuestSelected[i]) {
                        builder.append("31" + game.mixHostRequestId[i] + ",");
                    }
                }
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append("$");
        }
        builder.deleteCharAt(builder.length() - 1);
        schemeNumber = builder.toString();
        // System.out.println("schemeNumber:"+schemeNumber);
    }


}
