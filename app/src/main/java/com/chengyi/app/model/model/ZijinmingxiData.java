package com.chengyi.app.model.model;


import com.alibaba.fastjson.JSONObject;
import com.chengyi.app.model.param.URLSuffix;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@Deprecated
public class  ZijinmingxiData {
	private String type;// 类型 不可空
	private double in;// 收入 可空
	private double out;// 支出 可空
	private double balance;// 余额 不 可空
    private String  isBuy;
    private int schemeId=-1;
    public int getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}

	public String getIsBuy() {
		return isBuy;
	}

	public void setIsBuy(String isBuy) {
		this.isBuy = isBuy;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	private String detail;
	private String createTime;//
	public String getType() {
		return type;
	}

	public String getCreateTime() {
		return   createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getIn() {
		return in;
	}

	public void setIn(double in) {
		this.in = in;
	}

	public double getOut() {
		return out;
	}

	public void setOut(double out) {
		this.out = out;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public static ZijinmingxiData create(JSONObject json) {
		ZijinmingxiData data = new ZijinmingxiData();
		data.setBalance(json.getDoubleValue(URLSuffix.balance));
		data.setIn(json.getDoubleValue(URLSuffix.in   ));
		data.setOut(json.getDoubleValue(URLSuffix.out ));
		data.setType(json.getString(URLSuffix.type));
		data.setCreateTime(json.getString(URLSuffix.createTime)); 
		data.setIsBuy(json.getString("isbuy" ));
		data.setDetail(json.getString("detail" ));
		data.setSchemeId(json.getIntValue("schemeId"));
		return data;
	}
}
