package com.chengyi.app.model.model;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  LastWinPrizeData {
     String  userName;
     String  lotteryName;
     String  timeDesc;
     double  prize=0;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		if(userName.length()>6){
			this.userName=userName.substring(0, 3)+"***";
		}else if(userName.length()>0){
			for(int i=6-userName.length();i>=0;i--){
				userName+=" ";
			}
			this.userName = userName;
		}
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public String getTimeDesc() {
		return timeDesc;
	}
	public void setTimeDesc(String timeDesc) {
		this.timeDesc = timeDesc;
	}
	public double getPrize() {
		return prize;
	}
	public void setPrize(double prize) {
		this.prize = prize;
	}
     
}
