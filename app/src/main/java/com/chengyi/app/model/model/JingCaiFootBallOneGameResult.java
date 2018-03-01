package com.chengyi.app.model.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  JingCaiFootBallOneGameResult {
	
    String  hostName;      //主队
    String  issue;
    String  dayOfWeekStr;
    String  leagueName;
    String  matchTime;
    String  guestName;      //客队
    String  rate;           //让球
    String  lastScore ;     //赛果
    String  spfResult;      //胜平负结果
    String  rqSpfResult;    //让球胜平负结果
    String  zjqResult;      //总进球结果
    String  spfJiangJin;    //胜平负奖金
    String  rqSpfJiangJin;   //让球胜平负奖金
    String  zjqJiangJin;    //总进球奖金
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getDayOfWeekStr() {
		return dayOfWeekStr;
	}
	public void setDayOfWeekStr(String dayOfWeekStr) {
		this.dayOfWeekStr = dayOfWeekStr;
	}
	public String getLeagueName() {
		return leagueName;
	}
	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}
	public String getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getLastScore() {
		return lastScore;
	}
	public void setLastScore(String lastScore) {
		this.lastScore = lastScore;
	}
	public String getSpfResult() {
		return spfResult;
	}
	public void setSpfResult(String spfResult) {
		this.spfResult = spfResult;
	}
	public String getRqSpfResult() {
		return rqSpfResult;
	}
	public void setRqSpfResult(String rqSpfResult) {
		this.rqSpfResult = rqSpfResult;
	}
	public String getZjqResult() {
		return zjqResult;
	}
	public void setZjqResult(String zjqResult) {
		this.zjqResult = zjqResult;
	}
	public String getSpfJiangJin() {
		return spfJiangJin;
	}
	public void setSpfJiangJin(String spfJiangJin) {
		this.spfJiangJin = spfJiangJin;
	}
	public String getRqSpfJiangJin() {
		return rqSpfJiangJin;
	}
	public void setRqSpfJiangJin(String rqSpfJiangJin) {
		this.rqSpfJiangJin = rqSpfJiangJin;
	}
	public String getZjqJiangJin() {
		return zjqJiangJin;
	}
	public void setZjqJiangJin(String zjqJiangJin) {
		this.zjqJiangJin = zjqJiangJin;
	}
	public static JingCaiFootBallOneGameResult create(JSONObject json){
		JingCaiFootBallOneGameResult data=new JingCaiFootBallOneGameResult();
		data.setHostName(json.getString("hostName"));
		data.setIssue(json.getString("issue"));
		data.setDayOfWeekStr(json.getString("dayOfWeekStr"));
		data.setLeagueName(json.getString("leagueName"));
		data.setMatchTime(json.getString("matchTime"));
		data.setGuestName(json.getString("guestName"));
		data.setRate(json.getString("rate"));
		data.setLastScore(json.getString("lastScore"));
		data.setSpfResult(json.getString("spfResult"));
		data.setRqSpfResult(json.getString("rqSpfResult"));
		data.setZjqResult(json.getString("zjqResult"));
		if(json.getString("spfSp" ).equals("null"))
			data.setSpfJiangJin("0.00");
		else
		    data.setSpfJiangJin(json.getString("spfSp" ));
		if(json.getString("rqSpfSp").equals("null"))
			data.setRqSpfJiangJin("0.00");
		else
		    data.setRqSpfJiangJin(json.getString("rqSpfSp"));
		if(json.getString("zjqSp").equals("null"))
			data.setZjqJiangJin("0.00");
		else
		    data.setZjqJiangJin(json.getString("zjqSp"));
		return data;
	}
}
