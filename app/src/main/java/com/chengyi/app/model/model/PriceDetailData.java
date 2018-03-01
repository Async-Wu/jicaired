package com.chengyi.app.model.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  PriceDetailData {
        String issue;
        String prizeDetail;
		public String getIssue() {
			return issue;
		}
		public void setIssue(String issue) {
			this.issue = issue;
		}
		public String getPrizeDetail() {
			return prizeDetail;
		}
		public void setPrizeDetail(String prizeDetail) {
			this.prizeDetail = prizeDetail;
		}
        public static PriceDetailData create(JSONObject json){
        	PriceDetailData data=new PriceDetailData();
        	data.setIssue(json.getString("issue"));
        	data.setPrizeDetail(json.getString("prizeDetail"));
        	return data;
        }
}
