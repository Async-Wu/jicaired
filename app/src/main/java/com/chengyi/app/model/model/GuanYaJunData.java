package com.chengyi.app.model.model;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  GuanYaJunData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sellStatus;
	private double sp;
	private String teamNameG;
	private String teamNameY;
	private String imgG;
	private String imgY;
	private String jcId;
	private int status;
	/**
	 * 是否被选中
	 * */
	
	public GuanYaJunData(){
		sellStatus=0;
		sp=0;
		teamNameG="";
		teamNameY="";
		imgG="";
		imgY="";
		jcId="";
		status=0;
	}
	
	private boolean  isSelected=false;
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
	}

	public double getSp() {
		return sp;
	}

	public void setSp(double sp) {
		this.sp = sp;
	}

	public String getTeamNameG() {
		return teamNameG;
	}

	public void setTeamNameG(String teamNameG) {
		this.teamNameG = teamNameG;
	}

	public String getJcId() {
		return jcId;
	}

	public void setJcId(String jcId) {
		this.jcId = jcId;
	}

	public int getSellStatus() {
		return sellStatus;
	}

	public void setSellStatus(int sellStatus) {
		this.sellStatus = sellStatus;
	}
	
	
	
	
	public String getTeamNameY() {
		return teamNameY;
	}

	public void setTeamNameY(String teamNameY) {
		this.teamNameY = teamNameY;
	}

	public String getImgG() {
		return imgG;
	}

	public void setImgG(String imgG) {
		this.imgG = imgG;
	}

	public String getImgY() {
		return imgY;
	}

	public void setImgY(String imgY) {
		this.imgY = imgY;
	}

	public GuanYaJunData(JSONObject obj){
		this.sellStatus=obj.getIntValue("sellStatus" );
		this.status=obj.getIntValue("status" );
		this.sp=obj.getDoubleValue("sp" );
		this.teamNameG=obj.getString("teamNameG" );
		this.teamNameY=obj.getString("teamNameY" );
		this.imgG=obj.getString("imgG" );
		this.imgY=obj.getString("imgY" );
		this.jcId=obj.getString("jcId" );
	}
	
}
