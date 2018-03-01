package com.chengyi.app.jingji.basket;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BasketballData {
	int type=-1;
	private ArrayList<String> tzList=new ArrayList<String>();
	String  tzStr="客胜1-5,客胜6-10,客胜11-15,客胜16-20,客胜21-25,客胜26+,主胜1-5,主胜6-10,主胜11-15,主胜16-20,主胜21-25,主胜26+";
	HashMap<String,String> map=new HashMap<String,String>();
    public ArrayList<String> getTzList() {
		return tzList;
	}
	public void setTzList(ArrayList<String> tzList) {
		this.tzList = tzList;
	}
	String  zhuDui;
    String  keDui;
    String changci="";
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
	
	
	public String getChangci() {
		return changci;
	}
	public void setChangci(String changci) {
		this.changci = changci;
	}
	public BasketballData(String  s,String t){
		String str[]=s.split("`");
		if(str.length>0){
			this.changci=str[1];
			this.zhuDui=str[2];
			this.keDui=str[3];
			transferTouZhu(str[str.length-1],t);
		}
	}
	private void initMap(){
		map.clear();
		map.put("311", "客胜1-5");
		map.put("312", "客胜6-10");
		map.put("313", "客胜11-15");
		map.put("314", "客胜16-20");
		map.put("315", "客胜21-25");
		map.put("316", "客胜26+");
		map.put("301", "主胜1-5");
		map.put("302", "主胜6-10");
		map.put("303", "主胜11-15");
		map.put("304", "主胜16-20");
		map.put("305", "主胜21-25");
		map.put("306", "主胜26+");
		map.put("g15", "客胜1-5");
		map.put("g610", "客胜6-10");
		map.put("g1115", "客胜11-15");
		map.put("g1620", "客胜16-20");
		map.put("g2125", "客胜21-25");
		map.put("g26", "客胜26+");
		map.put("h15", "主胜1-5");
		map.put("h610", "主胜6-10");
		map.put("h1115", "主胜11-15");
		map.put("h1620", "主胜16-20");
		map.put("h2125", "主胜21-25");
		map.put("h26", "主胜26+");
		map.put("0", "主负");
		map.put("3", "主胜");
		map.put("100", "让分主负");
		map.put("103", "让分主胜");
		map.put("201", "大");
		map.put("202", "小");

	}
	private void  transferTouZhu(String  s,String t){
		if(t.equals("分数差复式")){
			type=0;
			
			String[] temp=s.split(",");
			String[] getStr=tzStr.split(",");
			for(int i=1;i<=temp.length;i++){
				try{
				tzList.add(getStr[Integer.parseInt(temp[i-1])]);
				}catch(Exception e){
					
					tzList.add(map.get(s));
					
				}
			}
			return;
		}else if(t.equals("竞彩篮球混合投注")){
			type=1;
			initMap();
			String[] temp=s.split(",");
			for(int i=0;i<temp.length;i++){
				tzList.add(map.get(temp[i]));
			}
			return;
		}else if(t.equals("大小分复式")){
			type=2;
			s=s.replace("0", "大分").replace("1", "小分");
		}else {
			type=3;
			s=s.replace("0", "主负").replace("3", "主胜");
		}
		String[] str=s.split(",");
		for(int i=0;i<str.length;i++){
			tzList.add(str[i]);
		}
	}
	///填充投注确认和方案详情页面中的view
    public void fillXiangQingView(TextView dw,TextView tz,TextView cg){
    	String  dwStr=changci+"\n"+keDui+"\nVS\n"+zhuDui+"（主）";
    	String  tzStr="\n";
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
    
	///填充投注确认和方案详情页面中的view
    public void fillBasketballXiangQingView(TextView dw,TextView tz,TextView cg){
    	String  dwStr=zhuDui+"\nVS\n"+keDui;
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
