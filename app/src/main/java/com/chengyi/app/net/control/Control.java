package com.chengyi.app.net.control;

import android.content.Context;
import com.chengyi.app.jingji.basket.BasketballManager;
import com.chengyi.app.jingji.football.FootballManager;
import com.chengyi.app.net.control.manage.GuanYaJunManager;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.net.http.MainResponseAnalyzer;

import java.lang.ref.WeakReference;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Control {

	private static final String TAG = "GlobalModule";
	private static Control mControl;

	public static Control getInstance() {
		if (mControl == null) {
			mControl = new Control();
		}
		return mControl;
	}

	private MainResponseAnalyzer mainResponseAnalyzer;
	private WeakReference<Context> mContextRef;

	public void initial(Context context) {
		this.mContextRef = new WeakReference<>(context);

		if (mainResponseAnalyzer == null) {
			mainResponseAnalyzer = new MainResponseAnalyzer(context);
		}

	}





	/**
	 * 
	 * 请求
	 * @param bc
	 * @param absoluteUrl
	 * @param params
	 * @param action
	 * @param isMustRefreshAction 是否要刷新回调
	 */
	public void request(final int bc, String absoluteUrl, RequestParams params,
						HttpRespHandler action, boolean isMustRefreshAction) {


		if (bc != -1) {
			if (isMustRefreshAction) {
				mainResponseAnalyzer.putBC(bc, action);

			} else {
				mainResponseAnalyzer.putBCIfNecessary(bc, action);
			}
		}

		OkHttpUtil.postSubmitForm(absoluteUrl, params, new OkHttpUtil.OnDownDataListener() {
			@Override
			public void onResponse(String url, String response) {
				mainResponseAnalyzer.onData(String.valueOf(bc), response);
			}

			@Override
			public void onFailure(String url, String error) {
				mainResponseAnalyzer.onNetworkError(String.valueOf(bc), new Throwable(error));
			}
		});

	}

	private BasketballManager mBasketballManager;
	
	public BasketballManager getBasketballManager(){
		if(mBasketballManager==null){
			mBasketballManager=new BasketballManager();
		}
		return mBasketballManager;
	}
	
	private FootballManager mFootballManager;
	
	public FootballManager getFootballballManager(){
		if(mFootballManager==null){
			mFootballManager=new FootballManager();
		}
		return mFootballManager;
	}
	
	
	private GuanYaJunManager mGYJManager;
	
	public GuanYaJunManager getGYJManager(){
		if(mGYJManager==null){
			mGYJManager=new GuanYaJunManager();
		}
		return mGYJManager;
	}

}
