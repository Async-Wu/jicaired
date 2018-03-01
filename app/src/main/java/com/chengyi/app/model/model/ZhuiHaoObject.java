package com.chengyi.app.model.model;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@Deprecated
public class  ZhuiHaoObject
{   
	    public ZhuiHaoObject(){
	    	
	    }
	    private int issueId=0;
	    public int getIssueId() {
			return issueId;
		}
		public void setIssueId(int issueId) {
			this.issueId = issueId;
		}
		boolean  isChecked=false;
	    int  beinum=0;
	    public boolean isChecked() {
		    return isChecked;
	    }
		public void setChecked(boolean isChecked) {
			this.isChecked = isChecked;
		}
		public int getBeinum() {
			return beinum;
		}
		public void setBeinum(int beinum) {
			this.beinum = beinum;
		}
		public int getMoney() {
			return money;
		}
		public void setMoney(int money) {
			this.money = money;
		}
		int  money=0;
	    String  issuenumber;
		public String getIssuenumber() {
			return issuenumber;
		}
		public void setIssuenumber(String issuenumber) {
			this.issuenumber = issuenumber;
		}
}
