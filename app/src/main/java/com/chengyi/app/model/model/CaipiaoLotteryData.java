package com.chengyi.app.model.model;


import com.alibaba.fastjson.JSONObject;
import com.chengyi.app.util.CaipiaoConst;



/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  CaipiaoLotteryData {
	private String issue;// 奖期
	private String drawNumber;// 开奖号码
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
	
	public CaipiaoLotteryData(JSONObject json, int cpId){
		try {
			setDrawNumber(json.getString("drawNumber"));
			if (cpId == CaipiaoConst.ID_QIXINGCAI) {
				String number = getDrawNumber();
				if (number.contains("#")) {
					String[] temp = number.split("#");
					if (temp.length == 2) {
						setDrawNumber(temp[0].trim() + "," + temp[1].trim());
					}
				}
			}
			setIssue(json.getString("issue"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
