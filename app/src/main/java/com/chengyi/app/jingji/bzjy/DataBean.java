package com.chengyi.app.jingji.bzjy;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  DataBean implements Serializable {

    /**
     * matches : []
     * dayKey : 60703
     * dayOfWeekStr : 2016-07-12
     * matchCount : 0
     * issueId : 7905190
     */



    public  void  clearselect(){

        if (matches!=null){

            for (int i=0;i<matches.size();i++){
                matches.get(i).clearselect();
            }

        }

    }

        private String dayKey;
        private String dayOfWeekStr;
        private int matchCount;
        private int issueId;
        private List<MatchBean> matches;

        public String getDayKey() {
            return dayKey;
        }

        public void setDayKey(String dayKey) {
            this.dayKey = dayKey;
        }

        public String getDayOfWeekStr() {
            return dayOfWeekStr;
        }

        public void setDayOfWeekStr(String dayOfWeekStr) {
            this.dayOfWeekStr = dayOfWeekStr;
        }

        public int getMatchCount() {
            return matchCount;
        }

        public void setMatchCount(int matchCount) {
            this.matchCount = matchCount;
        }

        public int getIssueId() {
            return issueId;
        }

        public void setIssueId(int issueId) {
            this.issueId = issueId;
        }

        public List<MatchBean> getMatches() {
            return matches;
        }

        public void setMatches(List<MatchBean> matches) {
            this.matches = matches;
        }

}
