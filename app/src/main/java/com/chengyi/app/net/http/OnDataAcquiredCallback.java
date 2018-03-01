package com.chengyi.app.net.http;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public interface OnDataAcquiredCallback {
	   void onData(String key, String result);
	   void onNetworkError(String key, Throwable error);
}
