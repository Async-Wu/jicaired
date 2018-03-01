package com.chengyi.app.jingji.football;

import android.util.SparseArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.model.AbsJingcaizuqiuData;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.model.model.ShengpingfuData;
import com.chengyi.app.model.param.BC;
import com.chengyi.app.model.param.SPKey;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class FootballManager {
    private int currentWanfa = 0;

    //	private SparseArray<BaseJSONObject> lotteryGameArray;
    private SparseArray<Long> lotteryGameRequestTimeArray;
    SparseArray<String[]> saiShiStrArray;
    SparseArray<ArrayList<AbsJingcaizuqiuData>> footballListArray;
    public SparseArray<SparseArray<JingcaizuqiuOneGame>> footballSelectedArray;
    private SparseArray<OnFootballDataCallback> dataCallbackArray;//
    public SparseArray<SparseArray<String>> footballGamePositionArray;
    public int currentCallback = 0;
    private int ncallbackId = 0;

    public FootballManager() {
        currentWanfa = CaipiaoApplication.sharedPreferences.getInt(SPKey.footballball_hiswanfa, 0);
        saiShiStrArray = new SparseArray<>();
        footballListArray = new SparseArray<>();
        lotteryGameRequestTimeArray = new SparseArray<>();
        dataCallbackArray = new SparseArray<>();
        footballSelectedArray = new SparseArray<>();
        footballGamePositionArray = new SparseArray<>();
        for (int i = 0; i < 7; i++) {
            footballSelectedArray.put(i, new SparseArray<JingcaizuqiuOneGame>());
        }
    }

    public void setCurrentWanfa(int wanfa) {
        currentWanfa = wanfa;
    }

    public int createCallback(OnFootballDataCallback callback) {
        ncallbackId += 1;
        dataCallbackArray.put(ncallbackId, callback);
        return ncallbackId;
    }

    public int requestLotteryData(int wanfa, int callbackId) {
        if (!checkIfRequestDataNecessary(wanfa, callbackId)) {
            return 0;
        }
        RequestParams params = getRequestParams();


        String footballRequest = URLSuffix.JINGCAI_ZUQIU_SPF;
//
        HttpBusinessAPI.post(BC.footballball + wanfa + callbackId * 100, footballRequest, params, new HttpRespHandler() {
            @Override
            public void onSuccess(int bc, String response) {

                super.onSuccess(bc, response);
//

                int number = bc - BC.footballball;
                int wanfa = number % 10;
                int callId = (number - wanfa) / 100;

                if (currentCallback == callId) {
                    jieXiJsonData(wanfa, response);
                    dataCallbackArray.get(callId).onSuccess(footballListArray.get(wanfa), saiShiStrArray.get(wanfa));
                }

            }

            @Override
            public void onFailure(int bc, Throwable error) {

                super.onFailure(bc, error);
                int number = bc - BC.footballball;
                int wanfa = number % 10;
                int callId = (number - wanfa) / 100;
                if (currentCallback == callId) {
                    dataCallbackArray.get(callId).onFailure(error);
                }

            }
        });
        return 1;

    }

    private boolean checkIfRequestDataNecessary(int wanfa, int callbackId) {
        long currentTimeMiles = new Date().getTime();
        if (footballListArray.get(wanfa) == null || saiShiStrArray.get(wanfa) == null) {
            lotteryGameRequestTimeArray.put(wanfa, currentTimeMiles);
            return true;
        }
        boolean ifNecessary = true;
        if (lotteryGameRequestTimeArray.indexOfKey(wanfa) >= 0) {
            long time = lotteryGameRequestTimeArray.get(wanfa);

            long result = (currentTimeMiles - time) / 1000 / 60;
            if (result < 5) {
                if (dataCallbackArray.get(callbackId) != null) {

                    dataCallbackArray.get(callbackId).onSuccess(footballListArray.get(wanfa), saiShiStrArray.get(wanfa));
                    ifNecessary = false;
                }
            }

        } else {
            lotteryGameRequestTimeArray.put(wanfa, currentTimeMiles);
        }
        return ifNecessary;
    }


    private void jieXiJsonData(int wanfa, String result) {
        JSONObject js = JSON.parseObject(result);
        CaipiaoApplication.getInstance().setJingcaixiaoshouJiangqi(js.getString("sels"));
        ArrayList<AbsJingcaizuqiuData> footballList = ShengpingfuData.parseFootballJson(result);
        setPosition(wanfa, footballList);
        footballListArray.put(wanfa, footballList);

        String[] saiShiStr = js.getString("leagues").split(",");
        saiShiStrArray.put(wanfa, saiShiStr);

    }

    private void setPosition(int wanfa, ArrayList<AbsJingcaizuqiuData> footballList) {
        if (footballList == null) {
            return;
        }
        SparseArray<String> position = footballGamePositionArray.get(wanfa);
        if (position == null) {
            position = new SparseArray<>();
            footballGamePositionArray.put(wanfa, position);
        }

        int size = footballList.size();
        for (int i = 0; i < size; i++) {
            List<JingcaizuqiuOneGame> list = footballList.get(i).getGames();
            int size1 = list.size();
            for (int j = 0; j < size1; j++) {
                JingcaizuqiuOneGame oneGame = list.get(j);
                oneGame.setGroupPosition(i);
                oneGame.setChildPosition(j);
                position.put(oneGame.orderIdLocal, i + "#" + j);
            }
        }

    }





    public RequestParams getRequestParams() {

        return new RequestParams();
    }

    public interface OnFootballDataCallback {
        void onSuccess(ArrayList<AbsJingcaizuqiuData> footballList, String[] saiShiStr);

        void onFailure(Throwable error);
    }

}
