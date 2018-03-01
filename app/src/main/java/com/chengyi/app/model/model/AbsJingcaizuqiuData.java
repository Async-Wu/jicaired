/**
 * Create on 2012-10-15
 */
package com.chengyi.app.model.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public abstract class AbsJingcaizuqiuData implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int issueId;
	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}
	private String date;// 日期

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
    //每天的所有比赛数据集合
	List<JingcaizuqiuOneGame> games = new ArrayList<JingcaizuqiuOneGame>();

	public List<JingcaizuqiuOneGame> getGames() {
		return games;
	}

	public void setGames(List<JingcaizuqiuOneGame> games) {
		this.games = games;
	}

	public int getCount() {
		return games.size();
	}

	static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	static DateFormat formatter1 = new SimpleDateFormat("MM月dd日");
	static int localid=0;
	public static ArrayList<AbsJingcaizuqiuData> parseFootballJson(String result){
		ArrayList<AbsJingcaizuqiuData> footballList = new ArrayList<AbsJingcaizuqiuData>();
		JSONArray dataList = parseObject(result).getJSONArray("data");
		localid=0;
		for (int i = 0; i < dataList.size(); i++) {
			AbsJingcaizuqiuData object = create(dataList
					.getString(i));
			if (!footballList.contains(object))
				footballList.add(object);
		}
		return footballList;
	}
	
	
	public static ShengpingfuData create(String object) {
	JSONObject js= JSON.parseObject(object);
		ShengpingfuData data = new ShengpingfuData();
		String date="";
		String olddate=js.getString("dayKey");
		try {
			date=formatter1.format(formatter.parse(olddate.trim()));
		} catch (Exception e) {
			date=olddate;
		}
		
        data.setDate(js.getString("dayOfWeekStr")+"\t"+date);
        
		JSONArray dataList=js.getJSONArray("matches");
		
		for(int i=0;i<dataList.size();i++)
		{
			JingcaizuqiuOneGame onegame=JingcaizuqiuOneGame.create(dataList.getJSONObject(i), js.getString("dayOfWeekStr"),js.getIntValue("issueId"));
			onegame.orderIdLocal=localid;
			data.games.add(onegame);
			localid+=1;
		}
		data.setIssueId( js.getIntValue("issueId"));
		return data;
	}
	public static ShengpingfuData createTest() {
		ShengpingfuData data = new ShengpingfuData();
		data.setDate("2013年09月01日");
		for(int i=0;i<10;i++)
		{
			data.games.add(JingcaizuqiuOneGame.create());
		}
		data.setIssueId(1000);
		return data;
	}
}
