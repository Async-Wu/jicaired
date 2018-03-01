package com.chengyi.app.model.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.util.CaipiaoUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  GoucaijiluxiangqingData {
	private int schemeId;// 方案ID
	private String lotteryName;// 股票名称
	private String userName;// 发起人
	private double schemeAmount;// 方案金额
	private int schemeNumberUnit;// 注数

	private int issueCount;// 期数
	private String statusDesc;// 方案状态
	private double buyAmount;// 购买金额
	private double remainAmount;// 剩余金额
	private double safeguard;// 保底金额，可空

	private double joinAmount;// 参与金额
	private int schemeType;// 方案类型，101:代购；102：代购追号；201：合买方案；202：合买追号方案
	private int moeyProgress;// 购买进展(百分比)
	private int safeguardProgress;// 保底进展(百分比)

	List<FanganHaomaData> fanganhaomaList;
	private int open;// 合买方案公开情况,0 ：未上传；1：公开；2：不允许查看该方案
	private String numberType;// 玩法
	private String initiateTime;// 发起时间
	private int remuneration;// 佣金比例，合买有
	private String burstIntoStop;// 开出停止追号,可空
	private String prizeStop;// 中奖停止追号 ，可空

	private int canCancel;// 是否能够撤单,0：不能撤； 1：能撤

	private String zhongjiangjine;// 中奖金额

	List<SchemeDetailData> zhongjiangXiangxiList;

	public int getSchemeId() {
		return schemeId;
	}

	public List<SchemeDetailData> getZhongjiangXiangxiList() {
		return zhongjiangXiangxiList;
	}

	public int getCanCancel() {
		return canCancel;
	}

	public void setCanCancel(int canCancel) {
		this.canCancel = canCancel;
	}

	public void setZhongjiangXiangxiList(
			List<SchemeDetailData> zhongjiangXiangxiList) {
		this.zhongjiangXiangxiList = zhongjiangXiangxiList;
	}

	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}

	public String getLotteryName() {
		return lotteryName;
	}

	public String getZhongjiangjine() {
		return zhongjiangjine;
	}

	public void setZhongjiangjine(String zhongjiangjine) {
		//格式处理下
		this.zhongjiangjine = zhongjiangjine.replace("<br>", "\n").replace(
				"<br/>", "\n");
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getSchemeAmount() {
		return schemeAmount;
	}

	public void setSchemeAmount(double schemeAmount) {
		this.schemeAmount = schemeAmount;
	}

	public int getSchemeNumberUnit() {
		return schemeNumberUnit;
	}

	public void setSchemeNumberUnit(int schemeNumberUnit) {
		this.schemeNumberUnit = schemeNumberUnit;
	}

	public int getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public double getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(double buyAmount) {
		this.buyAmount = buyAmount;
	}

	public double getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(double remainAmount) {
		this.remainAmount = remainAmount;
	}

	public double getSafeguard() {
		return safeguard;
	}

	public void setSafeguard(double safeguard) {
		this.safeguard = safeguard;
	}

	public double getJoinAmount() {
		return joinAmount;
	}

	public void setJoinAmount(double joinAmount) {
		this.joinAmount = joinAmount;
	}

	public int getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(int schemeType) {
		this.schemeType = schemeType;
	}

	public int getMoeyProgress() {
		return moeyProgress;
	}

	public void setMoeyProgress(int moeyProgress) {
		this.moeyProgress = moeyProgress;
	}

	public int getSafeguardProgress() {
		return safeguardProgress;
	}

	public void setSafeguardProgress(int safeguardProgress) {
		this.safeguardProgress = safeguardProgress;
	}

	public List<FanganHaomaData> getFanganhaomaList() {
		return fanganhaomaList;
	}

	public void setFanganhaomaList(List<FanganHaomaData> fanganhaomaList) {
		this.fanganhaomaList = fanganhaomaList;
	}

	public int getOpen() {
		return open;
	}

	public void setOpen(int open) {
		this.open = open;
	}

	public String getNumberType() {
		return numberType;
	}

	public void setNumberType(String numberType) {
		this.numberType = numberType;
	}

	public String getInitiateTime() {
		return initiateTime;
	}

	public void setInitiateTime(String initiateTime) {
		this.initiateTime = initiateTime;
	}

	public int getRemuneration() {
		return remuneration;
	}

	public void setRemuneration(int remuneration) {
		this.remuneration = remuneration;
	}

	public String getBurstIntoStop() {
		return burstIntoStop;
	}

	public void setBurstIntoStop(String burstIntoStop) {
		this.burstIntoStop = burstIntoStop;
	}

	public String getPrizeStop() {
		return prizeStop;
	}

	public void setPrizeStop(String prizeStop) {
		this.prizeStop = prizeStop;
	}

	public static GoucaijiluxiangqingData create(String json) {
		GoucaijiluxiangqingData data = new GoucaijiluxiangqingData();
		data.setBurstIntoStop(JSON.parseObject(json).getString(URLSuffix.burstIntoStop ));
		data.setBuyAmount(JSON.parseObject(json).getDouble(URLSuffix.buyAmount));
		data.setInitiateTime(JSON.parseObject(json).getString(URLSuffix.initiateTime));
		data.setIssueCount(JSON.parseObject(json).getIntValue(URLSuffix.issueCount));
		data.setJoinAmount(JSON.parseObject(json).getDoubleValue(URLSuffix.joinAmount ));
		data.setLotteryName(JSON.parseObject(json).getString(URLSuffix.lotteryName));
		data.setMoeyProgress(JSON.parseObject(json).getIntValue(URLSuffix.moeyProgress));
		data.setNumberType(JSON.parseObject(json).getString(URLSuffix.numberType));
		data.setOpen(JSON.parseObject(json).getIntValue(URLSuffix.open));
		data.setPrizeStop(JSON.parseObject(json).getString(URLSuffix.prizeStop ));
		data.setRemainAmount(JSON.parseObject(json).getDouble(URLSuffix.remainAmount));
		data.setRemuneration(JSON.parseObject(json).getIntValue(URLSuffix.remuneration ));
		data.setSafeguard(JSON.parseObject(json).getDoubleValue(URLSuffix.safeguard ));
		data.setSafeguardProgress(JSON.parseObject(json).getIntValue(URLSuffix.safeguardProgress));
		data.setSchemeAmount(JSON.parseObject(json).getDouble(URLSuffix.schemeAmount));
		data.setSchemeId(JSON.parseObject(json).getIntValue(URLSuffix.schemeId));
		data.setSchemeNumberUnit(JSON.parseObject(json).getIntValue(URLSuffix.schemeNumberUnit));
		data.setSchemeType(JSON.parseObject(json).getIntValue(URLSuffix.schemeType));
		data.setStatusDesc(JSON.parseObject(json).getString(URLSuffix.statusDesc));
		data.setUserName(JSON.parseObject(json).getString(URLSuffix.userName));
		data.setCanCancel(JSON.parseObject(json).getIntValue(URLSuffix.canCancel));
		JSONArray array = JSON.parseObject(json).getJSONArray(URLSuffix.schemeContent);
		List<FanganHaomaData> tempList = new ArrayList<FanganHaomaData>();
		for (int i = 0; i < array.size(); i++) {
			tempList.add(FanganHaomaData.create(array.getString(i)));
		}
		data.setFanganhaomaList(tempList);
		array = JSON.parseObject(json).getJSONArray(URLSuffix.schemeDetail);
		List<SchemeDetailData> tempList2 = new ArrayList<SchemeDetailData>();
		for (int i = 0; i < array.size(); i++) {
			tempList2.add(SchemeDetailData.create(array.getString(i)));
		}
		data.setZhongjiangXiangxiList(tempList2);

		if (data.getSchemeType() == 101 || data.getSchemeType() == 102) {
			data.setZhongjiangjine(JSON.parseObject(json).getString(URLSuffix.prizeDetail));
		} else {
			double d = JSON.parseObject(json).getDoubleValue(URLSuffix.ownPrize);
			if (d != 0)
				data.setZhongjiangjine(CaipiaoUtil.formatPrice(d));
		}
		return data;
	}

}
