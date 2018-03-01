package com.chengyi.app.model.model;

import android.widget.TextView;

import java.util.ArrayList;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  FootBallSingle {
	private ArrayList<String> tzList=new ArrayList<String>();
	private String  tzStr="1:0,2:0,2:1,3:0,3:1,3:2,4:0,4:1,4:2,胜其他,0:0,1:1,2:2,3:3,平其他,0:1,0:2,1:2,0:3,1:3,2:3,0:4,1:4,2:4,负其他";
	public ArrayList<String> getTzList() {
		return tzList;
	}
	public void setTzList(ArrayList<String> tzList) {
		this.tzList = tzList;
	}
	String  zhuDui;
    String  keDui;
	public String getZhuDui() {
		return zhuDui;
	}
	public void setZhuDui(String zhuDui) {
		this.zhuDui = zhuDui;
	}
	public String getKeDui() {
		return keDui;
	}
	public void setKeDui(String keDui) {
		this.keDui = keDui;
	}
	public FootBallSingle(String  s,String t){
		String str[]=s.split("`");
		if(str.length>0){
			this.zhuDui=str[2];
			this.keDui=str[3];
			transferTouZhu(str[str.length-1],t);
		}
	}
	private void  transferTouZhu(String  s,String t){
		if(t.equals("上下单双复式")){
			s=s.replace("0", "上+单").replace("1", "上+双").replace("2", "下+单").replace("3", "下+双");
			String[] str=s.split(",");
			for(int i=0;i<str.length;i++){
				tzList.add(str[i]);
			}
		}else if(t.equals("让球胜平负复式")){
			s=s.replace("0", "负").replace("1", "平").replace("3", "胜");
			String[] str=s.split(",");
			for(int i=0;i<str.length;i++){
				tzList.add(str[i]);
			}
		}else if(t.equals("总进球数复式")){
			tzList.add(s.replace("7", "7+"));
		}else if(t.equals("比分复式")){
			String[] tz=tzStr.split(",");
			String[] temp=s.split(",");
			String str="";
			for(int i=1;i<=temp.length;i++){
				if(i%4==0)
					str="";
				str+=tz[Integer.parseInt(temp[i-1])]+",";
				if(i%3==0||i==temp.length){
					tzList.add(str.substring(0, str.length()-1));
				}
			}
		}else if(t.equals("半全场复式")){
			s=s.replace("0", "胜胜").replace("1", "胜平").replace("2", "胜负").replace("3", "平胜")
			  .replace("4", "平平").replace("5", "平负").replace("6", "负胜").replace("7", "负平").replace("8", "负负");
			String[] str=s.split(",");
			for(int i=0;i<str.length;i++){
				tzList.add(str[i]);
			}
		}
		
	}
	///填充投注确认和方案详情页面中的view
    public void fillXiangQingView(TextView dw,TextView tz,TextView cg){
    	String  dwStr=keDui+"\nVS\n"+zhuDui;
    	String  tzStr="";
    	for(int i=0;i<tzList.size();i++){
			if(i!=tzList.size()-1)
				tzStr+=tzList.get(i)+"\n";
			else
				tzStr+=tzList.get(i);
		}
		if(tzList.size()==1){
			tzStr+="\n\n";
		}else if(tzList.size()==2){
			tzStr+="\n";
		}else {
			//补换行符
			for(int i=0;i<tzList.size()-3;i++){
				dwStr+="\n";
			}
		}
		dw.setText(dwStr);
		tz.setText(tzStr);
    }
}
