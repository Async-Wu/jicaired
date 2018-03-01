package com.chengyi.app.model.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  LinghaoData {
		public String getCreateTime() {
			return createTime;
		}
		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}
		public String getReceiveNo() {
			return receiveNo;
		}
		public void setReceiveNo(String receiveNo) {
			this.receiveNo = receiveNo;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		private String createTime;
		private String receiveNo;
		private String status;
		private String drawNumber;
		public String getDrawNumber() {
			return drawNumber;
		}
		public void setDrawNumber(String drawNumber) {
			this.drawNumber = drawNumber;
		}
		public void create(JSONObject obj){
			createTime=obj.getString("time");
			receiveNo=obj.getString("receiveNo");
			status=obj.getString("status");
			drawNumber=obj.getString("drawNumber");
		}
}
