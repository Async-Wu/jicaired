package com.chengyi.app.model.bean;

import android.os.Parcel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.app.model.home.BaseMode;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  HadLotteryBean extends BaseMode{

	private int issueId;

	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	private String title;
	private  String desc;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private String drawNumber;
    private String issue;
    private int lotteryId;
    private String drawTime;
    private String saleTotal;
    private List<PrizeData>  items;
    private String threeDNumber;
    public String getThreeDNumber() {
		return threeDNumber;
	}
	public void setThreeDNumber(String threeDNumber) {
		this.threeDNumber = threeDNumber;
	}
	private int flag;
    public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public List<PrizeData> getItems() {
		return items;
	}
	public void setItems(List<PrizeData> items) {
		this.items = items;
	}
	public String getSaleTotal() {
		return saleTotal;
	}
	public void setSaleTotal(String saleTotal) {
		this.saleTotal = saleTotal;
	}
	public String getRemainTotal() {
		return remainTotal;
	}
	public void setRemainTotal(String remainTotal) {
		this.remainTotal = remainTotal;
	}
	private String remainTotal;
	public String getDrawNumber() {
		return drawNumber;
	}
	public void setDrawNumber(String drawNumber) {
		this.drawNumber = drawNumber;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public int getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(int lotteryId) {
		this.lotteryId = lotteryId;
	}
	public String getDrawTime() {
		return drawTime;
	}
	public void setDrawTime(String drawTime) {
		this.drawTime = drawTime;
	}
	public HadLotteryBean(){
		
	}

    public  int getDefaulFlag(int id){
    	int flag=0;
    	switch(id){
	    	case 10032:
	    		flag=1;
	    	break;
	    	case 10025:
	    		flag=2;
	    	break;
	    	case 10026:
	    		flag=3;
	    	break;
	    	case 10046:
	    		flag=4;
	    	break;
	    	case 10059:
	    		flag=5;
	    	break;
	    	case 10060:
	    		flag=6;
	    	break;
	    	case 10038:
	    		flag=7;
	    	break;
	    	case 10061:
	    		flag=8;
	    	break;
	    	case 10024:
	    		flag=9;
	    	break;
	    	case 10024*10:
	    		flag=9;
	    	break;
	    	case 10065:
	    		flag=10;
	    	break;
	    	case 10033:
	    		flag=11;
	    	break;
	    	case 10035:
	    		flag=12;
	    	break;
	    	case 10064:
	    		flag=13;
	    	break;
	    	case 10030:
	    		flag=14;
	    	break;
	    	case 10027:
	    		flag=15;
	    	break;
	    	case 10028:
	    		flag=16;
	    	break;
	    	case 10066:
	    		flag=17;
	    	break;
	    	case 10029:
	    		flag=18;
	    	break;
    	}
    	return flag;
    }
    public static HadLotteryBean createHistoryData(JSONObject json, int id){
    	HadLotteryBean data =new HadLotteryBean();
    	data.setDrawNumber(json.getString("drawNumber" ));
    	data.setDrawTime(json.getString("drawTime" ));
    	data.setIssue(json.getString("issue" ));
    	data.setSaleTotal(json.getString("saleTotal" ));
    	data.setRemainTotal(json.getString("remainTotal" ));
    	List<PrizeData> list=new ArrayList<PrizeData>();
    	JSONArray array=json.getJSONArray("items");
    	for(int i=0;i<array.size();i++){
    		list.add(PrizeData.create(array.getJSONObject(i)));
    	}
    	//data.setLotteryId(id);
    	if(id==10024){
    		data.setDrawNumber(data.getDrawNumber().substring(0,5));
            list.remove(0);
    	}else if(id==10024*10){
    		list.clear();
    		list.add(PrizeData.create(array.getJSONObject(0)));
    	} 
    	data.setItems(list);
    	return data;
    }
    public static class  PrizeData{
    	String prizeItem;
    	String number;
    	String prizeAmount;
		public String getPrizeItem() {
			return prizeItem;
		}
		public void setPrizeItem(String prizeItem) {
			this.prizeItem = prizeItem;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public String getPrizeAmount() {
			return prizeAmount;
		}
		public void setPrizeAmount(String prizeAmount) {
			this.prizeAmount = prizeAmount;
		}
		public static  PrizeData create(JSONObject json){
			PrizeData data =new PrizeData();
			data.setNumber(json.getString("number"));
			data.setPrizeAmount(json.getString("prizeAmount"));
			data.setPrizeItem(json.getString("prizeItem"));
			return data;
		}
    }



	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.issueId);
		dest.writeString(this.title);
		dest.writeString(this.desc);
		dest.writeString(this.drawNumber);
		dest.writeString(this.issue);
		dest.writeInt(this.lotteryId);
		dest.writeString(this.drawTime);
		dest.writeString(this.saleTotal);
		dest.writeList(this.items);
		dest.writeString(this.threeDNumber);
		dest.writeInt(this.flag);
		dest.writeString(this.remainTotal);
	}

	protected HadLotteryBean(Parcel in) {
		this.issueId = in.readInt();
		this.title = in.readString();
		this.desc = in.readString();
		this.drawNumber = in.readString();
		this.issue = in.readString();
		this.lotteryId = in.readInt();
		this.drawTime = in.readString();
		this.saleTotal = in.readString();
		this.items = new ArrayList<PrizeData>();
		in.readList(this.items, PrizeData.class.getClassLoader());
		this.threeDNumber = in.readString();
		this.flag = in.readInt();
		this.remainTotal = in.readString();
	}

	public static final Creator<HadLotteryBean> CREATOR = new Creator<HadLotteryBean>() {
		@Override
		public HadLotteryBean createFromParcel(Parcel source) {
			return new HadLotteryBean(source);
		}

		@Override
		public HadLotteryBean[] newArray(int size) {
			return new HadLotteryBean[size];
		}
	};
}
