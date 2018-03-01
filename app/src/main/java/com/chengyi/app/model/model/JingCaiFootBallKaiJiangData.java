package com.chengyi.app.model.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  JingCaiFootBallKaiJiangData {
	
	private String date;// 日期

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	 //每天的所有比赛数据集合
	List<JingCaiFootBallOneGameResult> games = new ArrayList<JingCaiFootBallOneGameResult>();

	public List<JingCaiFootBallOneGameResult> getGames() {
			return games;
	}

	public void setGames(List<JingCaiFootBallOneGameResult> games) {
			this.games = games;
	}

	public int getCount() {
			return games.size();
	}
	
}
