package com.chengyi.app.jingji.basket;

import java.util.ArrayList;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BasketballOneDay {

	public String dayKey;
	public String dayOfWeekStr;
	public int rowEnd;
	public int totalMatch;
	public ArrayList<BasketballOneGame> gameListOneDay;
	
	public BasketballOneDay(){
		dayKey="";
		dayOfWeekStr="";
		rowEnd=0;
		totalMatch=0;
		gameListOneDay=new ArrayList<BasketballOneGame>();
	}
	
}
