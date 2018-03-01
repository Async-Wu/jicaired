package com.chengyi.app.model.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@Deprecated
public class  ZhuiHaoData {
	
    String  status;
    String  issue;
    String  drawNumber;
    int multiple;
    int money=0;
    double  prize=0;
	public double getPrize() {
		return prize;
	}
	public void setPrize(double prize) {
		this.prize = prize;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getDrawNumber() {
		return drawNumber;
	}
	public void setDrawNumber(String drawNumber) {
		this.drawNumber = drawNumber;
	}
	public int getMultiple() {
		return multiple;
	}
	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}
    public  static  ZhuiHaoData  create(JSONObject json){
    	ZhuiHaoData  data=new ZhuiHaoData();
    	data.setDrawNumber(json.getString("drawNumber"));
        data.setIssue(json.getString("issue"));
        data.setStatus(json.getString("status"));
        data.setMultiple(json.getIntValue("multiple"));
        data.setMoney(json.getIntValue("money"));
        data.setPrize(json.getDouble("prize"));
    	return data;
    }
}
