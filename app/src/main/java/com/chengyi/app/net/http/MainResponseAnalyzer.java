package com.chengyi.app.net.http;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.SparseArray;
import com.chengyi.app.model.param.BroadcastMsg;

import java.lang.ref.WeakReference;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  MainResponseAnalyzer implements OnDataAcquiredCallback {

	private WeakReference<Context> m_context;

	private ResponseManagerReceiver mResponseManagerReceiver;

	private SparseArray<HttpRespHandler> mResponseMap;
	



	// ============================================================PARSE

	public MainResponseAnalyzer(Context context) {
		this.m_context = new WeakReference<Context>(context);

		
		mResponseMap = new SparseArray<>();
		
		
		mResponseManagerReceiver = new ResponseManagerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(BroadcastMsg.BROADCAST_ACTION);
		m_context.get().getApplicationContext()
				.registerReceiver(mResponseManagerReceiver, filter);
	}

	public void setContext(Context context) {
		m_context = new WeakReference<Context>(context);
	}

//	public void onDestory() {
//		clear();
//		m_context.get().getApplicationContext()
//				.unregisterReceiver(mResponseManagerReceiver);
//		m_context.clear();
//	}

	public void clear() {

		mResponseMap.clear();
		

		
	}
	
	public void putBCIfNecessary(int BC, HttpRespHandler action){
		if(mResponseMap.indexOfKey(BC)<0){
			mResponseMap.put(BC, action);
		}
	}

	public void putBC(int BC, HttpRespHandler action){
		
			mResponseMap.put(BC, action);
		
	}
	
	@Override
	public void onNetworkError(String bc, Throwable error) {

		int BC=Integer.valueOf(bc);
		if(mResponseMap.indexOfKey(BC)<0){
			mResponseMap.get(BC).onFailure(BC,error);;
		}
		
	}

	
	/**
	 * 实现接口，获得解析字符串（key目前无用）
	 */
	@Override
	public void onData(String bc, String result) {

		if(bc==null){
			return;
		}
		parseJson(Integer.valueOf(bc), result);
	}

	public void parseJson(int bc, String json) {
		if (mResponseMap.indexOfKey(bc) != -1) {
			mResponseMap.get(bc).handleResponse(bc, json);
		}
	}
	private class ResponseManagerReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String broadcaString = intent
					.getStringExtra(BroadcastMsg.MSG_KEY);
			if (broadcaString == null) {
				return;
			}

			if (broadcaString.equals(BroadcastMsg.HTTP_REQUEST_FAILED)) {
				String requestTypeId = intent
						.getStringExtra(BroadcastMsg.HTTP_REQUEST_FAILED_ID);


//				handleNetWorkFail(requestId);
			} 

		}

	}




	
	
	
	

}
