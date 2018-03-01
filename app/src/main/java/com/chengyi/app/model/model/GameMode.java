package com.chengyi.app.model.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lishangfan on 2016/11/17.
 */
public class GameMode implements Serializable{

    protected String gameName;// 联赛名
    protected String time;// 比赛时间
    protected String team1;// 队伍1
    protected String team2;// 队伍2
    protected int rangNumber = 0;// 让球数




    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }




    // 比分投注按钮
    protected String[] biFenStr = "1:0,2:0,2:1,3:0,3:1,3:2,4:0,4:1,4:2,5:0,5:1,5:2,胜其他,0:0,1:1,2:2,3:3,平其他,0:1,0:2,1:2,0:3,1:3,2:3,0:4,1:4,2:4,0:5,1:5,2:5,负其他"
            .split(",");




    public String[] getBiFenStr() {
        return biFenStr;
    }

    public void setBiFenStr(String[] biFenStr) {
        this.biFenStr = biFenStr;
    }





    protected ArrayList<String> bqcList = new ArrayList<String>();// 半全场，比分投注的内容
    public ArrayList<String> getBqcList() {
        return bqcList;
    }

    public void setBqcList(ArrayList<String> bqcList) {
        this.bqcList = bqcList;
    }



    protected String selectedStr;    //半全场，比分投注选择的拼接成的串

    public String getSelectedStr() {
        return selectedStr;
    }

    public void setSelectedStr(String selectedStr) {
        this.selectedStr = selectedStr;
        spfFlag = selectedStr.split(",").length - 1;
    }
    protected int spfFlag = -1; // /投注列表中被选中的按钮的个数，主要用来计算注数所用

    public int getSpfFlag() {
        return spfFlag;
    }

    public void setSpfFlag(int spfFlag) {
        this.spfFlag = spfFlag;
    }

}
