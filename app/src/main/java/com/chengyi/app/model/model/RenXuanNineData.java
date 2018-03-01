package com.chengyi.app.model.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  RenXuanNineData implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private  String  hostName;
	private  String  guestname;
	private  String  date;
	private  String  time;
	private  String  color;
	private  String  lianSai;
	private  String  sPeilv;
    private  String  pPeilv;
    private  String  fPeilv;
    private  int num=0;
    private  int selectedNum=0;///胜平负三个按钮中有几个被选中了
    public int getSelectedNum() {
		return selectedNum;
	}
	public void setSelectedNum(int selectedNum) {
		this.selectedNum = selectedNum;
	}

	StringBuffer buffer=new StringBuffer();
	public StringBuffer getBuffer() {
		return buffer;
	}
	public void setBuffer(StringBuffer buffer) {
		this.buffer = buffer;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getGuestname() {
		return guestname;
	}
	public void setGuestname(String guestname) {
		this.guestname = guestname;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getLianSai() {
		return lianSai;
	}
	public void setLianSai(String lianSai) {
		this.lianSai = lianSai;
	}
	public String getsPeilv() {
		return sPeilv;
	}
	public void setsPeilv(String sPeilv) {
		this.sPeilv = sPeilv;
	}
	public String getpPeilv() {
		return pPeilv;
	}
	public void setpPeilv(String pPeilv) {
		this.pPeilv = pPeilv;
	}
	public String getfPeilv() {
		return fPeilv;
	}
	public void setfPeilv(String fPeilv) {
		this.fPeilv = fPeilv;
	}

	public RenXuanNineData(JSONObject obj, int i){
		  this.hostName=obj.getString("hostName");
		  this.guestname=obj.getString("guestName");
		  Date dt;
		  try {
				 dt = sim.parse(obj.getString("matchTime"));
				  sim=new SimpleDateFormat("MM-dd");
				  this.date=sim.format(dt);
				  sim=new SimpleDateFormat("hh:mm");
				  this.time=sim.format(dt);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
		  this.color=obj.getString("color");
		  this.lianSai=obj.getString("ln");
		  this.sPeilv=obj.getString("odds_s");
		  this.pPeilv=obj.getString("odds_p");
		  this.fPeilv=obj.getString("odds_f");
		  this.num=i;
	}
}
