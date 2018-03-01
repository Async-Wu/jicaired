package com.chengyi.app.jingji.basket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.app.net.control.Control;

import java.util.ArrayList;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BasketballResponse {
    private ArrayList<BasketballOneDay> gameListAllDays;

    public ArrayList<BasketballOneDay> getBasketballGameList(String result) {
        gameListAllDays = new ArrayList<BasketballOneDay>();
        int localOrderId = 0;
        Control.getInstance().getBasketballManager().currentIssue = JSON.parseObject(result).getString("currentIssue");
        JSONArray array = JSON.parseObject(result).getJSONArray("dataList");
        if (array == null) {
            return new ArrayList<BasketballOneDay>();
        }
        int groupPosition = 0;
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            BasketballOneDay oneDay = new BasketballOneDay();
            oneDay.dayKey = object.getString("dayKey");
            int issueid = object.getInteger("issueId");
            oneDay.dayOfWeekStr = object.getString("dayOfWeekStr");
            oneDay.rowEnd = object.getIntValue("rowEnd");
            oneDay.totalMatch = object.getIntValue("totalMatch");
            JSONArray oneGameArray = object.getJSONArray("matches");
            groupPosition += 1;
            if (oneGameArray == null) {
                continue;
            }
            for (int j = 0; j < oneGameArray.size(); j++) {
                JSONObject oneGameObject = oneGameArray.getJSONObject(j);
                BasketballOneGame oneGameItem = new BasketballOneGame();
                oneGameItem.dayOfWeek = oneDay.dayOfWeekStr;
                oneGameItem.matchCode = oneGameObject.getString("matchCode");
                oneGameItem.id = oneGameObject.getIntValue("id");
                oneGameItem.issue = oneGameObject.getIntValue("issue");
                oneGameItem.issueId = issueid;

                oneGameItem.matchId = oneGameObject.getString("matchId");
                oneGameItem.adminId = oneGameObject.getString("adminId");
                oneGameItem.cls = oneGameObject.getString("cls");
                oneGameItem.color = oneGameObject.getString("color");
                oneGameItem.guestName = oneGameObject.getString("guestName");
                oneGameItem.hostName = oneGameObject.getString("hostName");
                String leagueName = oneGameObject.getString("leagueName");
                oneGameItem.leagueName[0] = shortLeaaueName(leagueName);
                oneGameItem.leagueName[1] = leagueName;
                oneGameItem.matchTime = oneGameObject.getLong("matchTime");
                oneGameItem.cp2yEndTime = oneGameObject.getLong("cp2yEndTime");
//				oneGameItem.end=oneGameObject.getBoolean("end");
//				oneGameItem.endTime=oneGameObject.getLong("sellEndTime");
                oneGameItem.oneScore = oneGameObject.getString("oneScore");
                oneGameItem.fourScore = oneGameObject.getString("fourScore");
                oneGameItem.lastScore = oneGameObject.getString("lastScore");
                oneGameItem.jcwId = oneGameObject.getIntValue("jcwId");
                oneGameItem.order = oneGameObject.getIntValue("order");
                oneGameItem.personLock = oneGameObject.getString("personLock");
                oneGameItem.dxfggResult = oneGameObject.getString("dxfggResult");
                oneGameItem.dxfPassStatus = oneGameObject.getIntValue("dxfPassStatus");
                oneGameItem.dxfResult = oneGameObject.getString("dxfResult");

                oneGameItem.dxfSingleStatus = oneGameObject.getIntValue("dxfSingleStatus");
                oneGameItem.dxfSp = oneGameObject.getString("dxfSp");
				oneGameItem.rate=oneGameObject.getDoubleValue("rate");
                oneGameItem.rfsfggResult = oneGameObject.getString("rfsfggResult");
                oneGameItem.rfsfPassStatus = oneGameObject.getIntValue("rfsfPassStatus");
                oneGameItem.rfsfSingleStatus = oneGameObject.getIntValue("rfsfSingleStatus");
                oneGameItem.rfsfSp = oneGameObject.getString("rfsfSp");
                oneGameItem.sfcggResult = oneGameObject.getString("sfcggResult");
                oneGameItem.sfcPassStatus = oneGameObject.getIntValue("sfcPassStatus");
                oneGameItem.sfcResult = oneGameObject.getString("sfcResult");
                oneGameItem.sfcSingleStatus = oneGameObject.getIntValue("sfcSingleStatus");
                oneGameItem.sfcSp = oneGameObject.getString("sfcSp");
                oneGameItem.sfggResult = oneGameObject.getString("sfggResult");
                oneGameItem.sfPassStatus = oneGameObject.getIntValue("sfPassStatus");
                oneGameItem.sfResult = oneGameObject.getString("sfResult");
                oneGameItem.sfSingleStatus = oneGameObject.getIntValue("sfSingleStatus");
                oneGameItem.sfSp = oneGameObject.getString("sfSp");
                JSONObject ggObject = oneGameObject.getJSONObject("sp");
				oneGameItem.basePoint=ggObject.getString("basePoint");
                oneGameItem.idGG = ggObject.getIntValue("id");
                oneGameItem.issueGG = ggObject.getIntValue("issue");
                oneGameItem.matchCodeGG = ggObject.getString("matchCode");
				oneGameItem.rateGG=ggObject.getDoubleValue("rate");
                oneGameItem.basketBallMatchId = ggObject.getString("basketBallMatchId");
                oneGameItem.createTime = ggObject.getString("createTime");

                try {
                    oneGameItem.sheng = ggObject.getDoubleValue("sheng");

                    oneGameItem.fu = ggObject.getDoubleValue("fu");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (oneGameItem.sheng > oneGameItem.fu) {
                    oneGameItem.shenfuMinSp[0] = oneGameItem.fu;
                    oneGameItem.shenfuMinSp[1] = 1;
                } else {
                    oneGameItem.shenfuMinSp[0] = oneGameItem.sheng;
                    oneGameItem.shenfuMinSp[1] = 0;
                }
                try {

                    oneGameItem.rffu = ggObject.getDoubleValue("rffu");
                    oneGameItem.rfsheng = ggObject.getDoubleValue("rfsheng");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (oneGameItem.rfsheng > oneGameItem.rffu) {
                    oneGameItem.rfshenfuMinSp[0] = oneGameItem.rffu;
                    oneGameItem.rfshenfuMinSp[1] = 1;
                } else {
                    oneGameItem.rfshenfuMinSp[0] = oneGameItem.rfsheng;
                    oneGameItem.rfshenfuMinSp[1] = 0;
                }
                try {
                    oneGameItem.d = ggObject.getDoubleValue("d");
                    oneGameItem.x = ggObject.getDoubleValue("x");
                    oneGameItem.sfcGuestWinSp[0] = ggObject.getDoubleValue("g15");
                    oneGameItem.sfcGuestWinSp[1] = ggObject.getDoubleValue("g610");
                    oneGameItem.sfcGuestWinSp[2] = ggObject.getDoubleValue("g1115");
                    oneGameItem.sfcGuestWinSp[3] = ggObject.getDoubleValue("g1620");
                    oneGameItem.sfcGuestWinSp[4] = ggObject.getDoubleValue("g2125");
                    oneGameItem.sfcGuestWinSp[5] = ggObject.getDoubleValue("g26");

                    oneGameItem.sfcHostWinSp[0] = ggObject.getDoubleValue("h15");
                    oneGameItem.sfcHostWinSp[1] = ggObject.getDoubleValue("h610");
                    oneGameItem.sfcHostWinSp[2] = ggObject.getDoubleValue("h1115");
                    oneGameItem.sfcHostWinSp[3] = ggObject.getDoubleValue("h1620");
                    oneGameItem.sfcHostWinSp[4] = ggObject.getDoubleValue("h2125");
                    oneGameItem.sfcHostWinSp[5] = ggObject.getDoubleValue("h26");


                    oneGameItem.spType = ggObject.getIntValue("spType");
                    oneGameItem.status = ggObject.getIntValue("status");
                    oneGameItem.weekDay = ggObject.getIntValue("weekDay");
                    oneGameItem.teamId = ggObject.getString("teamId");
                    oneGameItem.updateTime = ggObject.getString("updateTime");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                oneGameItem.orderIdLocal = localOrderId;
                oneGameItem.groupPosition = groupPosition;
                localOrderId++;

                oneDay.gameListOneDay.add(oneGameItem);

            }
            gameListAllDays.add(oneDay);
        }


        return gameListAllDays;
    }


    private String shortLeaaueName(String fullName) {
        String newLeaaueName = fullName;
        if (fullName != null && fullName.equals("美国女子篮球联盟")) {
            newLeaaueName = "WNBA";
        } else if (fullName != null && fullName.equals("美国职业篮球联盟")) {
            newLeaaueName = "NBA";
        } else if (fullName != null && (fullName.equals("欧洲篮球联赛") || fullName.equals("欧洲篮球冠军联赛"))) {
            newLeaaueName = "欧冠篮";
        }

        return newLeaaueName;
    }


}
