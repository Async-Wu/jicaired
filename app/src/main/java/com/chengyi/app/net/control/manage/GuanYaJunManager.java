package com.chengyi.app.net.control.manage;

import android.util.SparseArray;
import com.chengyi.app.model.param.BC;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;

import java.util.Date;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  GuanYaJunManager {


	private SparseArray<Long> lotteryGameRequestTimeArray;
	SparseArray<String> jsonArray;

	private SparseArray<OnGuanYaJunDataCallback> dataCallbackArray;// key:wanfaguan
	private int ncallbackId=0;
	
	public int currentCallback=0;
	
	public GuanYaJunManager() {

		jsonArray = new SparseArray<>();
		dataCallbackArray=new SparseArray<>();
		lotteryGameRequestTimeArray = new SparseArray<>();

	}
	
	public int creatCallback(OnGuanYaJunDataCallback callback){
		ncallbackId+=1;
		dataCallbackArray.put(ncallbackId, callback);
		return ncallbackId;
	}

	public int requestLotteryData(int wanfa,int callbackId) {
		if (!checkIfRequestDataNecessary(wanfa,callbackId)) {
			return 0;
		}
		RequestParams params = getRequestParams();

		String footballRequest = "";
		if (wanfa == 0) {
			footballRequest = URLSuffix.SHIJIEBEI;
		} else {
			footballRequest = URLSuffix.SHIJIEBEIGYJ;
		}

		HttpBusinessAPI.post(BC.guanyajun + wanfa + callbackId * 100, footballRequest, params,
				new HttpRespHandler() {
					@Override
					public void onSuccess(int bc, String response) {

						super.onSuccess(bc, response);
						int number = bc - BC.guanyajun;
						int wanfa = number % 10;
						int callId = (number - wanfa) / 100;
						if (currentCallback == callId) {
							jieXiJsonData(wanfa, response, callId, true);
						}

					}

					@Override
					public void onFailure(int bc, Throwable error) {

						super.onFailure(bc, error);
						int number = bc - BC.guanyajun;
						int wanfa = number % 10;
						int callId = (number - wanfa) / 100;
						if (currentCallback == callId) {
							dataCallbackArray.get(callId).onFailure(error);
						}

					}
				});
		return 1;

	}

	private boolean checkIfRequestDataNecessary(int wanfa,int callbackId) {
		long currentTimeMiles = new Date().getTime();
		if (jsonArray.get(wanfa) == null) {
			lotteryGameRequestTimeArray.put(wanfa, currentTimeMiles);
			return true;
		}
		boolean ifNecessary = true;
		if (lotteryGameRequestTimeArray.indexOfKey(wanfa) >= 0) {
			long time = lotteryGameRequestTimeArray.get(wanfa);

			long result = (currentTimeMiles - time) / 1000 / 60;
			if (result < 5) {
				if (dataCallbackArray.get(callbackId) != null) {

					dataCallbackArray.get(callbackId).onSuccess(jsonArray.get(wanfa));
					ifNecessary = false;
				}
			}

		} else {
			lotteryGameRequestTimeArray.put(wanfa, currentTimeMiles);
		}
		return ifNecessary;
	}

	public void jieXiJsonData(int wanfa, String result, int callbackId, boolean ifSave) {


		if (ifSave) {
			jsonArray.put(wanfa, result);
		}
		dataCallbackArray.get(callbackId).onSuccess(result);

	}



	public RequestParams getRequestParams() {
		return new RequestParams();

	}

	public interface OnGuanYaJunDataCallback {
		  void onSuccess(String response);

		  void onFailure(Throwable error);
	}

}
