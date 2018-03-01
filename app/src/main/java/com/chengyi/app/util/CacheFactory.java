package com.chengyi.app.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  CacheFactory {
	Map<String, JSONObject> jsonMap;
	Map<String, JSONArray> jsonArrayMap;
	Map<String, Long> jsonArrayMapOfTime;

	private CacheFactory() {
		jsonMap = new HashMap<>();
		jsonArrayMap = new HashMap<>();
		jsonArrayMapOfTime = new HashMap<>();
	}

	private static CacheFactory instance;

	public static CacheFactory getInstance() {
		if (instance == null) {
			instance = new CacheFactory();
		}
		return instance;
	}

	public JSONObject getJSONObject(String key) {
		if (jsonMap != null) {
			return jsonMap.get(key);
		}
		return null;
	}

	public JSONArray getJSONArray(String key) {
		if (jsonArrayMap != null) {
			return jsonArrayMap.get(key);
		}
		return null;
	}

	public void putJSONObject(String key, JSONObject json) {
		if (jsonMap != null) {
			jsonMap.put(key, json);
		}
	}

	public void putJSONArray(String key, JSONArray json) {
		if (jsonArrayMap != null) {
			jsonArrayMap.put(key, json);
		}
	}

	public void putJSONArrayWithTime(String key, JSONArray json, long time) {
		if (jsonArrayMap != null && jsonArrayMapOfTime != null) {
			if (json.size() > 0) {
				jsonArrayMap.put(key, json);
				jsonArrayMapOfTime.put(key, time);
			}
		}
	}

	public long getTime(String key) {
		if (jsonArrayMapOfTime != null) {
			Long t = jsonArrayMapOfTime.get(key);
			if (t == null) {
				return 0;
			}
			return t;
		}
		return 0;
	}
	public void putTime(String key,long time){
		if (jsonArrayMapOfTime != null) {
			jsonArrayMapOfTime.put(key, time);
		}
	}
}
