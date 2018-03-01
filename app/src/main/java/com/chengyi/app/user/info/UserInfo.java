package com.chengyi.app.user.info;

import android.os.Parcel;
import android.os.Parcelable;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.util.CacheFactory;
import com.chengyi.app.util.CaipiaoUtil;

import java.util.ArrayList;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  UserInfo implements Parcelable {

	private String   zhanghao;
	private String   password;
	private String   clientUserSession;	//用户登录session用于验证用户及是否登录
	private String   photoNumber;
	private String   bindingStatus="";//默认为手机没有绑定
	private String   bangDingPhone="";//手机绑定时回到个人信息页所用
	private boolean  isYanPhone=false;//是否验证手机
	private int   bindStatuskey;
	
	public ArrayList<UserInfoHisPurchase> purchaseList;

    private String verifyCode;

	public int getBindStatuskey() {
		return bindStatuskey;
	}
	public void setBindStatuskey(int bindStatuskey) {
		this.bindStatuskey = bindStatuskey;
	}
	public boolean isYanPhone() {
		return isYanPhone;
	}
	public void setYanPhone(boolean isYanPhone) {
		this.isYanPhone = isYanPhone;
	}
	public boolean isFirstChong() {
		return isFirstChong;
	}
	public void setFirstChong(boolean isFirstChong) {
		this.isFirstChong = isFirstChong;
	}
	public boolean isFirstZhong() {
		return isFirstZhong;
	}
	public void setFirstZhong(boolean isFirstZhong) {
		this.isFirstZhong = isFirstZhong;
	}
	private boolean  isFirstChong=false;//是否首次充值
	private boolean  isFirstZhong=false;//是否首次中奖
	public String getBangDingPhone() {
		return bangDingPhone;
	}
	public void setBangDingPhone(String bangDingPhone) {
		this.bangDingPhone = bangDingPhone;
	}
//	private String[]  buyHistorylist;
//	public String[] getBuyHistorylist() {
//		return buyHistorylist;
//	}
//	public void setBuyHistorylist(String[] buyHistorylist) {
//		this.buyHistorylist = buyHistorylist;
//	}
	public String getBindingStatus() {
		return bindingStatus;
	}
	public void setBindingStatus(String bindingStatus) {
		this.bindingStatus = bindingStatus;
	}
	public String getPhotoNumber() {
		return photoNumber;
	}
	public void setPhotoNumber(String photoNumber) {
		this.photoNumber = photoNumber;
	}
	private int givedType = -1;// 积分是否领取
	// 0表示未领取积分
	// 1:您已经领取了积分
	// 2:您的手机客户端已经领取了积分

	private double balance = -1;// 余额
	private double bouns=-1;
	private int score;	//积分
	public String getBounsString() {
		if (bouns < 0) {
			return "0";
		}
		return CaipiaoUtil.formatPrice(bouns);
	}

	public double getBouns() {
		
		return bouns;
	}
	
	public void setBouns(double bouns) {
		this.bouns = bouns;
	}
	public int getGivedType() {
		return givedType;
	}

	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public double getBalance() {
		return balance;
	}
	public void setGivedType(int givedType) {
		this.givedType = givedType;
	}

	private long jifen = -1;// 积分

	public String getBalanceString() {
		if (balance < 0) {
			return "--";
		}
		return CaipiaoUtil.formatPrice(balance);
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getJifenString() {
		if (jifen == -1) {
			return "--";
		}
		return jifen + "";
	}

	public void setJifen(long jifen) {
		this.jifen = jifen;
	}

	public String getClientUserSession() {
		return clientUserSession;
	}

	public void setClientUserSession(String clientUserSession) {
		this.clientUserSession = clientUserSession;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof String)) {
			return false;
		}
		return zhanghao.equals(o.toString());
	}

	@Override
	public int hashCode() {
		return zhanghao.hashCode();
	}

	public String getZhanghao() {
		return zhanghao;
	}

	public void setZhanghao(String zhanghao) {
		this.zhanghao = zhanghao;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static UserInfo getUser(String result){
 
		UserInfo user = new UserInfo();
		user.setClientUserSession(JSON.parseObject(result).getString(URLSuffix.clientUserSession));
		user.setBalance(JSON.parseObject(result).getDoubleValue(URLSuffix.balance));
		user.setScore(JSON.parseObject(result).getIntValue(URLSuffix.score));
		///缓存数据
	    CacheFactory.getInstance().putTime("goucailist",System.currentTimeMillis());
		return user;
		
	}
	public static UserInfo create(String result) {
		//System.out.println("result:"+result);
		UserInfo user = new UserInfo();
		user.setFirstChong(JSON.parseObject(result).getBooleanValue("isIncome"));
		user.setFirstZhong(JSON.parseObject(result).getBooleanValue("isPrize"));
        user.setYanPhone(JSON.parseObject(result).getBooleanValue("isMobileBinded"));
		user.setClientUserSession(JSON.parseObject(result).getString(URLSuffix.clientUserSession));
		user.setBalance(JSON.parseObject(result).getDoubleValue(URLSuffix.balance ));
		user.setBouns(JSON.parseObject(result).getDoubleValue("handsel" ));
		user.setJifen(JSON.parseObject(result).getLongValue(URLSuffix.score ));
		user.setBindingStatus(JSON.parseObject(result).getString("bindStatus" ));
		user.setZhanghao(JSON.parseObject(result).getString(URLSuffix.username ));
		user.setBindStatuskey(JSON.parseObject(result).getIntValue("bindStatuskey" ));
//
		user.purchaseList=new ArrayList<>();
		JSONArray hisArray=JSON.parseObject(result).getJSONArray("buyHistorySchemeData");
		int hisLength=hisArray.size()>5?5:hisArray.size();
		for(int i=0;i<hisLength;i++){
			JSONObject object=hisArray.getJSONObject(i);
			UserInfoHisPurchase hisObject=new UserInfoHisPurchase();
			hisObject.initiateTime=object.getLong("initiateTime");
			hisObject.issue=object.getString("issue");
			hisObject.lotteryId=object.getIntValue("lotteryId");
			hisObject.lotteryName=object.getString("lotteryName");
			hisObject.prize=object.getDouble("prize");
			hisObject.prizeSubjoin=object.getDouble("prizeSubjoin");
			hisObject.schemeAmount=object.getDouble("schemeAmount");
			hisObject.schemeId=object.getIntValue("schemeId");
			hisObject.schemeNumberType=object.getIntValue("schemeNumberType");
			hisObject.sort=object.getIntValue("sort");
			hisObject.status=object.getIntValue("status");
			hisObject.statusDesc=object.getString("statusDesc");
			hisObject.userId=object.getIntValue("userId");
			user.purchaseList.add(hisObject);
		}
		
		///缓存数据
	    CacheFactory.getInstance().putTime("goucailist",System.currentTimeMillis());
		return user;
	}

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.zhanghao);
		dest.writeString(this.password);
		dest.writeString(this.clientUserSession);
		dest.writeString(this.photoNumber);
		dest.writeString(this.bindingStatus);
		dest.writeString(this.bangDingPhone);
		dest.writeByte(this.isYanPhone ? (byte) 1 : (byte) 0);
		dest.writeInt(this.bindStatuskey);
		dest.writeList(this.purchaseList);
		dest.writeString(this.verifyCode);
		dest.writeByte(this.isFirstChong ? (byte) 1 : (byte) 0);
		dest.writeByte(this.isFirstZhong ? (byte) 1 : (byte) 0);
		dest.writeInt(this.givedType);
		dest.writeDouble(this.balance);
		dest.writeDouble(this.bouns);
		dest.writeInt(this.score);
		dest.writeLong(this.jifen);
	}

	public UserInfo() {
	}

	protected UserInfo(Parcel in) {
		this.zhanghao = in.readString();
		this.password = in.readString();
		this.clientUserSession = in.readString();
		this.photoNumber = in.readString();
		this.bindingStatus = in.readString();
		this.bangDingPhone = in.readString();
		this.isYanPhone = in.readByte() != 0;
		this.bindStatuskey = in.readInt();
		this.purchaseList = new ArrayList<UserInfoHisPurchase>();
		in.readList(this.purchaseList, UserInfoHisPurchase.class.getClassLoader());
		this.verifyCode = in.readString();
		this.isFirstChong = in.readByte() != 0;
		this.isFirstZhong = in.readByte() != 0;
		this.givedType = in.readInt();
		this.balance = in.readDouble();
		this.bouns = in.readDouble();
		this.score = in.readInt();
		this.jifen = in.readLong();
	}

	public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>() {
		@Override
		public UserInfo createFromParcel(Parcel source) {
			return new UserInfo(source);
		}

		@Override
		public UserInfo[] newArray(int size) {
			return new UserInfo[size];
		}
	};
}


