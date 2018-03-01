package com.chengyi.app.model.model;

import java.io.Serializable;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  ActivityData implements Serializable{


	/**
	 * activityId : 10012
	 * activityName : 新闻公告
	 * joinFlag : 1
	 * head : 竞彩新特惠：中奖就有红包送
	 * content : 炎炎夏日，彩票2元网热上加热隆重推出“竞彩奖上奖”活动，点燃六月热情。只要竞彩方案中奖，就会返20-1280元不等的红包。还等什么？轻松购彩，好运赢大奖拿红包，心动不如行动！
	 * link :
	 * lotteryID : 10059
	 * lotteryName : 竞彩足球
	 * startTime : 2013-6-3 0:00:00
	 * endTime : 2013-6-30 23:59:59
	 * overFlag : 1
	 * ord : 1
	 * activityType : 2
	 */

	private int activityId;
	private String activityName;
	private int joinFlag;
	private String head;
	private String content;
	private String link;
	private int lotteryID;
	private String lotteryName;
	private String startTime;
	private String endTime;
	private int overFlag;
	private int ord;
	private int activityType;

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public int getJoinFlag() {
		return joinFlag;
	}

	public void setJoinFlag(int joinFlag) {
		this.joinFlag = joinFlag;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getLotteryID() {
		return lotteryID;
	}

	public void setLotteryID(int lotteryID) {
		this.lotteryID = lotteryID;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getOverFlag() {
		return overFlag;
	}

	public void setOverFlag(int overFlag) {
		this.overFlag = overFlag;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}
}
