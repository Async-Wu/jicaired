package com.chengyi.app.model.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.app.jingji.basket.BasketballData;
import com.chengyi.app.model.param.URLSuffix;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  HemaiXiangqingData {

	private int schemeId;// 方案ID
	private String schemeNumber;//方案号
	private String userName;// 发起人用户名
	private double schemeAmount;// 方案金额
	private String initiateTime;// 方案发起时间
	private String numberType;//玩法
	private int gameSize;
	private int beiShu;
	private String guoGuanStr;
	private int zhuShu;
	private int wfindex=0;
	private int canCancel;//是否可以撤单，发起人
	private int canRemove;//是否可以撤单，参与
	private String lotteryName;//彩种名称
	private int lotteryId;
	private boolean topStatus;
	private int   betType;
	private int  matchCount=0;
	private String pass="";   //过关方式
	private String drawNumber;
	private long  sellEndTime=0L;
	private double schemePrize=0;
	public double getSchemePrize() {
		return schemePrize;
	}
	public void setSchemePrize(double schemePrize) {
		this.schemePrize = schemePrize;
	}
	public long getSellEndTime() {
		return sellEndTime;
	}
	public void setSellEndTime(long sellEndTime) {
		this.sellEndTime = sellEndTime;
	}

	private int prizeStop=0;
	public int getPrizeStop() {
		return prizeStop;
	}
	public void setPrizeStop(int prizeStop) {
		this.prizeStop = prizeStop;
	}
	public String getSchemeNumber() {
		return schemeNumber;
	}
	public void setSchemeNumber(String schemeNumber) {
		this.schemeNumber = schemeNumber;
	}
	public String getDrawNumber() {
		return drawNumber;
	}
	public void setDrawNumber(String drawNumber) {
		this.drawNumber = drawNumber;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getMatchCount() {
		return matchCount;
	}
	public void setMatchCount(int matchCount) {
		this.matchCount = matchCount;
	}
	public int getBetType() {
		return betType;
	}
	public void setBetType(int betType) {
		this.betType = betType;
	}

	List<JingcaizuqiuOneGame> footballList;   ///竞彩足球
	ArrayList<BasketballData> BasketballList;///竞彩篮球
	ArrayList<FootBallSingle> fballSgList;
	public ArrayList<FootBallSingle> getFballSgList() {
		return fballSgList;
	}
	public void setFballSgList(ArrayList<FootBallSingle> fballSgList) {
		this.fballSgList = fballSgList;
	}
	public ArrayList<BasketballData> getBasketballList() {
		return BasketballList;
	}
	public void setBasketballList(ArrayList<BasketballData> basketballList) {
		BasketballList = basketballList;
	}
	public List<JingcaizuqiuOneGame> getFootballList() {
		return footballList;
	}
	public void setFootballList(List<JingcaizuqiuOneGame> footballList) {
		this.footballList = footballList;
	}

	private int[] level;// 战绩
	public int[] getLevel() {
		return level;
	}
	public void setLevel(int[] level) {
		this.level = level;
	}
	public boolean isTopStatus() {
		return topStatus;
	}
	public void setTopStatus(boolean topStatus) {
		this.topStatus = topStatus;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}

	private double totalPrize=0.0;//奖金
	private int  joinNum=0;//参入人数

	public int getJoinNum() {
		return joinNum;
	}
	public void setJoinNum(int joinNum) {
		this.joinNum = joinNum;
	}
	public double getTotalPrize() {
		return totalPrize;
	}
	public void setTotalPrize(double totalPrize) {
		this.totalPrize = totalPrize;
	}
	public String getLotteryName() {
		return lotteryName;
	}
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}
	public int getCanCancel() {
		return canCancel;
	}
	public void setCanCancel(int canCancel) {
		this.canCancel = canCancel;
	}
	public int getCanRemove() {
		return canRemove;
	}
	public void setCanRemove(int canRemove) {
		this.canRemove = canRemove;
	}

	private double joinAmount;
	public double getJoinAmount() {
		return joinAmount;
	}
	public void setJoinAmount(double joinAmount) {
		this.joinAmount = joinAmount;
	}
	public int getGameSize() {
		return gameSize;
	}
	public int getWfindex() {
		return wfindex;
	}
	public void setWfindex(int wfindex) {
		this.wfindex = wfindex;
	}

	public void setGameSize(int gameSize) {
		this.gameSize = gameSize;
	}

	public int getBeiShu() {
		return beiShu;
	}

	public void setBeiShu(int beiShu) {
		this.beiShu = beiShu;
	}

	public String getGuoGuanStr() {
		return guoGuanStr;
	}

	public void setGuoGuanStr(String guoGuanStr) {
		this.guoGuanStr = guoGuanStr;
	}

	public int getZhuShu() {
		return zhuShu;
	}

	public void setZhuShu(int zhuShu) {
		this.zhuShu = zhuShu;
	}

	public String getNumberType() {
		return numberType;
	}

	public void setNumberType(String numberType) {
		this.numberType = numberType;
	}

	private double buyAmount;// 购买金额
    private String statusDesc;
	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	private double remainAmount;// 剩余金额
	private String schemeType;// 方案类型 //0：合买；1：追号合买
	private int type;// 方案类型 //0：合买；1：追号合买
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	private int issueCount;// 期数
	private List<FanganHaomaData> fanganhaomaList;// 方案号码
	private int open;// 公开情况，0：未上传方案；1：公开；3：参与后公开；4：截止后公开
	private List<JingcaizuqiuOneGame> gameList;// 方案号码
	
	private List<SchemeParticipantData> joinPresionList;// 方案号码
 
    private	List<ZhuiHaoData> zhuiList;
    List<PriceDetailData> pList;
	public List<PriceDetailData> getpList() {
		return pList;
	}
	public void setpList(List<PriceDetailData> pList) {
		this.pList = pList;
	}
	public List<ZhuiHaoData> getZhuiList() {
		return zhuiList;
	}
	public void setZhuiList(List<ZhuiHaoData> zhuiList) {
		this.zhuiList = zhuiList;
	}
	public List<SchemeParticipantData> getJoinPresionList() {
		return joinPresionList;
	}
	public void setJoinPresionList(List<SchemeParticipantData> joinPresionList) {
		this.joinPresionList = joinPresionList;
	}
	public List<JingcaizuqiuOneGame> getGameList() {
		return gameList;
	}

	public void setGameList(List<JingcaizuqiuOneGame> gameList) {
		this.gameList = gameList;
	}

	private String progress;// 进展百分比
	private int yiBaodi;
	private int yiGenDan;
	public int getYiBaodi() {
		return yiBaodi;
	}

	public void setYiBaodi(int yiBaodi) {
		this.yiBaodi = yiBaodi;
	}

	public int getYiGenDan() {
		return yiGenDan;
	}

	public void setYiGenDan(int yiGenDan) {
		this.yiGenDan = yiGenDan;
	}

	private double moeyProgress;
	private double baodiProgress;
	public double getBaodiProgress() {
		return baodiProgress;
	}

	public void setBaodiProgress(double baodiProgress) {
		this.baodiProgress = baodiProgress;
	}

	public double getMoeyProgress() {
		return moeyProgress;
	}

	public void setMoeyProgress(double moeyProgress) {
		this.moeyProgress = moeyProgress;
	}

	private double safeguard;// 保底金额，可空
	private int remuneration;// 佣金 可空
	private String schemeDesc;// 方案描述 可空

	private int minParticipant;// 参与最低金额
	private int  isTrace=0;  //是否追号
	public int getIsTrace() {
		return isTrace;
	}
	public void setIsTrace(int isTrace) {
		this.isTrace = isTrace;
	}

	private int minSafeguard=0;
	public int getMinSafeguard() {
		return minSafeguard;
	}

	public void setMinSafeguard(int minSafeguard) {
		this.minSafeguard = minSafeguard;
	}

	//描述
    private String describe;
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public int getMinParticipant() {
		return minParticipant;
	}

	public void setMinParticipant(int minParticipant) {
		this.minParticipant = minParticipant;
	}

	private String issue;// 起始奖期

	public int getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
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

	public String getInitiateTime() {
		return initiateTime;
	}

	public void setInitiateTime(String initiateTime) {
		this.initiateTime = initiateTime;
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

	public String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	public int getIssueCount() {
		return issueCount;
	}

	public void setIssueCount(int issueCount) {
		this.issueCount = issueCount;
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

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}

	public double getSafeguard() {
		return safeguard;
	}

	public void setSafeguard(double safeguard) {
		this.safeguard = safeguard;
	}

	public int getRemuneration() {
		return remuneration;
	}

	public void setRemuneration(int remuneration) {
		this.remuneration = remuneration;
	}

	public String getSchemeDesc() {
		return schemeDesc;
	}

	public void setSchemeDesc(String schemeDesc) {
		this.schemeDesc = schemeDesc;
	}

	public static HemaiXiangqingData create(String json) {
		//System.out.println("json:"+json);
		HemaiXiangqingData data = new HemaiXiangqingData();
		data.setLotteryName(JSON.parseObject(json).getString("lotteryName"));
		data.setLotteryId(JSON.parseObject(json).getIntValue("schemeId"));
		JSONObject jobject=JSON.parseObject(json);
		data.setUserName(jobject.getString("userName"));//发起人
		//发起时间
	    data.setInitiateTime(jobject.getString("initiateTime"));





	    data.setSellEndTime(jobject.getLongValue("sellEndTime"));

//	    data.setSchemeNumber(jobject.getString("schemeNumber"));
		data.setProgress(jobject.getString("progress"));//进度
		data.setSchemeId(jobject.getIntValue("schemeId"));//方案id
		data.setIssue(jobject.getString("issue"));//追号期数
		data.setOpen(jobject.getIntValue("open"));//是否公开
		data.setSchemeAmount( jobject.getDoubleValue("schemeAmount"));//方案总金额
		data.setSchemePrize( jobject.getDoubleValue("schemePrize"));//方案总金额
		data.setIssueCount(jobject.getIntValue("issueCount"));//追号期数
		data.setBuyAmount(jobject.getDoubleValue("buyAmount"));///已经认购
		data.setRemainAmount(jobject.getDoubleValue("remainAmount"));//剩余金额
		data.setSafeguard(jobject.getDoubleValue("afeguard"));//保底金额
		data.setCanCancel(jobject.getIntValue("canCancel"));//是否可以撤单
		data.setSchemeDesc(jobject.getString("schemeDesc"));//方案描述
		data.setRemuneration(jobject.getIntValue("remuneration"));//佣金
		data.setStatusDesc(jobject.getString("statusDesc"));//方案状态
		data.setBaodiProgress(jobject.getDouble("safeguardProgress"));//保底进度
		data.setMinParticipant(jobject.getIntValue("minParticipant"));//最低认购金额
		data.setTopStatus(jobject.getBooleanValue("topStatus"));//是否置顶
		data.setNumberType(jobject.getString("numberType"));//
		//注数  
		data.setZhuShu(jobject.getIntValue(URLSuffix.schemeNumberUnit));
        data.setIsTrace(JSON.parseObject(json).getIntValue("isTrace"));
        data.setPrizeStop(JSON.parseObject(json).getIntValue("prizeStop"));
		data.setType(jobject.getIntValue("type"));
		int[] ls = new int[4];
		try {
			String[] levels = jobject.getString("userLevel").split(",");
			for (int i = 0; i < levels.length; i++) {
				ls[i] = Integer.valueOf(levels[i].trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		data.setLevel(ls);
		//方案内容
		JSONArray array = jobject.getJSONArray("schemeContent");
		int id=JSON.parseObject(json).getIntValue("lotteryId");
		if(id==10059){             //竞彩足球
			JSONObject object=array.getJSONObject(0);
			JSONArray  matches=object.getJSONArray("matches");
			data.setBetType(object.getInteger("betType" ));
			data.setMatchCount(object.getInteger("matchCount" ));
			data.setPass(object.getString("pass" ));
			List<JingcaizuqiuOneGame> footballList=new ArrayList<JingcaizuqiuOneGame>();
			for(int i = 0; i < matches.size(); i++){
				footballList.add(JingcaizuqiuOneGame.JoinCreate(matches.getString(i), data.getBetType()));
			}
			data.setFootballList(footballList);
		}else if(id==10057||id==10058){ //足球单场 ,///竞彩篮球
			String str[] = null;
			for(int t=0;t<array.size();t++){
				if(array.getJSONObject(t).getString("type" ).equals("过关")){
					data.setPass(array.getJSONObject(0).getString("number" ));
				}else if(array.getJSONObject(t).getString("type").equals(data.getNumberType())){
					str=array.getJSONObject(t).getString("number").split("\\$");
				}
			}
			if(str!=null){
			   data.setMatchCount(str.length);
			   if(id==10058){
				   ArrayList<BasketballData> basketballList=new ArrayList<BasketballData>();
				   for(int i=0;i<str.length;i++){
					   basketballList.add(new BasketballData(str[i],data.getNumberType()));
				   }
				   data.setBasketballList(basketballList);
			   }else{
				   ArrayList<FootBallSingle> fballSgList=new ArrayList<FootBallSingle>();
				   for(int i=0;i<str.length;i++){
					   fballSgList.add(new FootBallSingle(str[i],data.getNumberType()));
				   }
				   data.setFballSgList(fballSgList);
			   }
			}
		}else {
			List<FanganHaomaData> fanganhaomaList = new ArrayList<FanganHaomaData>();
			for (int i = 0; i < array.size(); i++) {
				fanganhaomaList.add(FanganHaomaData.create(array.getString(i)));
			}
			data.setFanganhaomaList(fanganhaomaList);
		}
		//参与人数列表
//		BaseJSONObject job=json.optBaseJSONObject("schemeParticipantData");
//		data.setJoinNum(job.optInt("totalCount", 0));
//		BaseJSONArray list = job.optBaseJSONArray("listData");
		List<SchemeParticipantData> joinList = new ArrayList<SchemeParticipantData>();
//		for (int i = 0; i < list.length(); i++) {
//			joinList.add(SchemeParticipantData.create(list.getJSONObject(i)));
//		}
		data.setJoinPresionList(joinList);
		///追号列表
		JSONArray zhuiHaolist = JSON.parseObject(json).getJSONArray("traceData");
		List<ZhuiHaoData> zhuiList = new ArrayList<ZhuiHaoData>();
		for (int i = 0; i < zhuiHaolist.size(); i++) {
			zhuiList.add(ZhuiHaoData.create(zhuiHaolist.getJSONObject(i)));
		}
		data.setZhuiList(zhuiList);
		//中奖明细
		JSONArray priceList = JSON.parseObject(json).getJSONArray("priceData");
		List<PriceDetailData> pList = new ArrayList<PriceDetailData>();
		for (int i = 0; i < priceList.size(); i++) {
			pList.add(PriceDetailData.create(priceList.getJSONObject(i)));
		}
		data.setpList(pList);
		return data;
	}
	/*竞彩足球数据解析
	 * */
	public static HemaiXiangqingData JingcaiZuqiuCreate(String json,boolean ishemai) {
//		System.out.println(json);
		HemaiXiangqingData data = new HemaiXiangqingData();
		//发起人
				data.setUserName(JSON.parseObject(json).getString(URLSuffix.userName));
		//是否能查看内容
				data.setOpen(JSON.parseObject(json).getIntValue(URLSuffix.open));
		//方案内容
				JSONArray array = JSON.parseObject(json).getJSONArray(URLSuffix.schemeContent);
				JSONObject jsonItem =array.getJSONObject(0);
				JSONArray arrayGame = jsonItem.getJSONArray(URLSuffix.matches);
				List<JingcaizuqiuOneGame> gameList=new ArrayList<JingcaizuqiuOneGame>();
				int type=jsonItem.getIntValue(URLSuffix.betType);
				data.setTotalPrize(jsonItem.getDouble(URLSuffix.totalPrize));
				if(type!=288)
					data.setWfindex(1);
				for(int i=0;i<arrayGame.size();i++)
				{   
					gameList.add(JingcaizuqiuOneGame.JoinCreate(arrayGame.getString(i),type));
				}
				data.setGameList(gameList);
		//描述
				data.setDescribe(JSON.parseObject(json).getString(URLSuffix.describe));
		//方案号
			    data.setSchemeId(JSON.parseObject(json).getIntValue(URLSuffix.schemeId));
		//发起时间
			    data.setInitiateTime(JSON.parseObject(json).getString(URLSuffix.initiateTime));
	    //方案状态
			    data.setStatusDesc(JSON.parseObject(json).getString(URLSuffix.statusDesc));
	    //玩法
			    data.setNumberType(JSON.parseObject(json).getString(URLSuffix.numberType));
			    
		//比赛场次
				data.setGameSize(jsonItem.getIntValue(URLSuffix.matchCount));
	    //过关方式
				data.setGuoGuanStr(jsonItem.getString(URLSuffix.pass));
		//注数  
				data.setZhuShu(JSON.parseObject(json).getIntValue(URLSuffix.schemeNumberUnit));
	    //方案金额
			    data.setSchemeAmount(JSON.parseObject(json).getDoubleValue(URLSuffix.schemeAmount));
	     //倍数
				//data.setBeiShu(json.getInt(CP_PARAS.multiple));
				data.setBeiShu((int)data.getSchemeAmount()/(data.getZhuShu()*2));
		//已经认购
			    data.setBuyAmount(JSON.parseObject(json).getByteValue(URLSuffix.buyAmount));
	    //剩余
			    data.setRemainAmount(JSON.parseObject(json).getByteValue(URLSuffix.remainAmount));
	    //进度
			    data.setMoeyProgress(JSON.parseObject(json).getByteValue(URLSuffix.moeyProgress));
	    //保底进度
			    data.setBaodiProgress(JSON.parseObject(json).getByteValue(URLSuffix.safeguardProgress));
	    //佣金
			    data.setRemuneration(JSON.parseObject(json).getIntValue(URLSuffix.remuneration));
	    //最低认购
			    data.setMinParticipant(JSON.parseObject(json).getIntValue(URLSuffix.minParticipant));
	    //最低保底
			    data.setMinSafeguard(((int)Math.ceil(data.getSchemeAmount()*0.1)));
		//已跟单
			    data.setYiGenDan(JSON.parseObject(json).getIntValue(URLSuffix.participantTimes));
		//已保底	    
			    data.setYiBaodi(JSON.parseObject(json).getIntValue(URLSuffix.safeguardTimes));
	    //购彩记录查看详情特殊字段
	    //参与金额
			    if(ishemai)
			    data.setJoinAmount(JSON.parseObject(json).getDoubleValue(URLSuffix.joinAmount));
			    data.setCanCancel(JSON.parseObject(json).getIntValue(URLSuffix.canCancel));
			    data.setCanRemove(JSON.parseObject(json).getIntValue(URLSuffix.canRemove));
			    data.setLotteryName(JSON.parseObject(json).getString(URLSuffix.lotteryName));
		return data;
	}
}
