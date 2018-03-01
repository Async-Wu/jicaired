package com.chengyi.app.model.home;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  ActivityMode extends  BaseMode {

    /**
     * lotteryName : 1
     * lotteryID : 1
     * activityName : 充值优惠
     * link : 1
     * ord : 1
     * overFlag : 1
     * activityId : 10027
     * activityType : 3
     * picture :
     * joinFlag : 1
     * endTime : 4095714320000
     * head : 充值优惠
     * startTime : 1381803920000
     * content : 充值优惠
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String lotteryName;
        private int lotteryID;
        private String activityName;
        private String link;
        private int ord;
        private int overFlag;
        private int activityId;
        private int activityType;
        private String picture;
        private int joinFlag;
        private long endTime;
        private String head;
        private long startTime;
        private String content;

        public String getLotteryName() {
            return lotteryName;
        }

        public void setLotteryName(String lotteryName) {
            this.lotteryName = lotteryName;
        }

        public int getLotteryID() {
            return lotteryID;
        }

        public void setLotteryID(int lotteryID) {
            this.lotteryID = lotteryID;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getOrd() {
            return ord;
        }

        public void setOrd(int ord) {
            this.ord = ord;
        }

        public int getOverFlag() {
            return overFlag;
        }

        public void setOverFlag(int overFlag) {
            this.overFlag = overFlag;
        }

        public int getActivityId() {
            return activityId;
        }

        public void setActivityId(int activityId) {
            this.activityId = activityId;
        }

        public int getActivityType() {
            return activityType;
        }

        public void setActivityType(int activityType) {
            this.activityType = activityType;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getJoinFlag() {
            return joinFlag;
        }

        public void setJoinFlag(int joinFlag) {
            this.joinFlag = joinFlag;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.list);
    }

    public ActivityMode() {
    }

    protected ActivityMode(Parcel in) {
        this.list = new ArrayList<ListBean>();
        in.readList(this.list, ListBean.class.getClassLoader());
    }

    public static final Creator<ActivityMode> CREATOR = new Creator<ActivityMode>() {
        @Override
        public ActivityMode createFromParcel(Parcel source) {
            return new ActivityMode(source);
        }

        @Override
        public ActivityMode[] newArray(int size) {
            return new ActivityMode[size];
        }
    };
}
