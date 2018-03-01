package com.chengyi.app.model.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  SchemeParticipantData {
	   
		String name; //名称
		double amount;//参与金额
		double proportion;//占有股份
		double prize;//奖金
		String userLevel;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public double getProportion() {
			return proportion;
		}
		public void setProportion(double proportion) {
			this.proportion = proportion;
		}
		public double getPrize() {
			return prize;
		}
		public void setPrize(double prize) {
			this.prize = prize;
		}
		public String getUserLevel() {
			return userLevel;
		}
		public void setUserLevel(String userLevel) {
			this.userLevel = userLevel;
		}
		private int[] level;// 战绩
		public int[] getLevel() {
			return level;
		}
		public void setLevel(int[] level) {
			this.level = level;
		}
		public static SchemeParticipantData create(JSONObject json){
			SchemeParticipantData data=new SchemeParticipantData();
			data.setName(json.getString("userName"));
			data.setAmount(json.getDoubleValue("money"));
			data.setPrize(json.getDoubleValue("prize"));
			data.setProportion(json.getDoubleValue("proportion"));
			data.setUserLevel(json.getString("userLevel"));
			int[] ls = new int[4];
			try {
				String[] levels = json.getString("userLevel").split(",");
				for (int i = 0; i < levels.length; i++) {
					ls[i] = Integer.valueOf(levels[i].trim());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			data.setLevel(ls);
			return data;
		}
}
