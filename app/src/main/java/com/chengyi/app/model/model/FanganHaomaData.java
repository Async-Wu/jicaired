package com.chengyi.app.model.model;


import com.alibaba.fastjson.JSON;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.util.CaipiaoUtil;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  FanganHaomaData {
	private String type;// 中文玩法名称
	private String number;// 方案

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public String getTypeAndNumber() {
		return getType() + "  " + getNumber();
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public static FanganHaomaData create(String json) {
		FanganHaomaData data = new FanganHaomaData();
		data.setType(JSON.parseObject(json).getString(URLSuffix.type));
		data.setNumber(JSON.parseObject(json).getString(URLSuffix.number));
		return data;
	}

	public TouzhuquerenData getTouzhuquerenData(int cpId) {
		TouzhuquerenData data = new TouzhuquerenData();
		data.setCaipiaoIdAndWanfaType(cpId,
				CaipiaoUtil.getWanfaTypeByName(cpId, getType().trim()));
		
		return data;
	}

}
