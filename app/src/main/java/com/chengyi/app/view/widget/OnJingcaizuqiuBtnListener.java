package com.chengyi.app.view.widget;

import com.chengyi.app.model.model.JingcaizuqiuOneGame;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */

public interface OnJingcaizuqiuBtnListener {
	 /**
	  * 回调函数，用于在Dialog的监听事件触发后修改Activity的注数，钱数，奖金范围和过关方式，是否可以选择胆码
	  */    
     // public void refreshUI(long num,double min,double max,ArrayList<String> list);
	  /**
	   * 回调函数，用于在Dialog的监听事件触发后恢复Activity中的注数，钱数，奖金范围和过关方式，是否可以选择胆码
	   */    
        void reSetData();
	  /**
	   * 回调函数，用于在Dialog的监听事件触发后修改Activity中的过关方式列表
	   */  
        void changData(ArrayList<String> list, double min, double max, Map<String, Double> map);
      
        void danMaChangUI(boolean isSelected);
      
        void buttonChangeUI(JingcaizuqiuOneGame game);
      
      ///点击胜负平按钮，但是不改变比赛场次
        void changeZhuNumUI();
      
      //点击删除按钮后，list对应位置的比赛所选择的按钮都设置为非选中状态
        void qingKongBiSaiData(JingcaizuqiuOneGame game);
      //点击删除后，当list的size=0左则的按钮恢复状态
        void changLeftBtnStates();
}
