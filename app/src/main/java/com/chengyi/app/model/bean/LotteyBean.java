package com.chengyi.app.model.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class LotteyBean implements Parcelable {

    private String message = "";

    private int lotteryId;
    private String issue;
    private int isStop;
    private String h5Url;
    private int issueId;
    private boolean linkH5;


    public Caipiao getCaipiao() {

        return CaipiaoFactory.getInstance(CaipiaoApplication.getInstance()).getCaipiaoById(lotteryId);
    }


    public int getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int getIsStop() {
        return isStop;
    }

    public void setIsStop(int isStop) {
        this.isStop = isStop;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public boolean isLinkH5() {
        return linkH5;
    }

    public void setLinkH5(boolean linkH5) {
        this.linkH5 = linkH5;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);

        dest.writeInt(this.lotteryId);
        dest.writeString(this.issue);
        dest.writeInt(this.isStop);
        dest.writeString(this.h5Url);
        dest.writeInt(this.issueId);
        dest.writeByte(this.linkH5 ? (byte) 1 : (byte) 0);
    }

    public LotteyBean() {
    }

    protected LotteyBean(Parcel in) {
        this.message = in.readString();

        this.lotteryId = in.readInt();
        this.issue = in.readString();
        this.isStop = in.readInt();
        this.h5Url = in.readString();
        this.issueId = in.readInt();
        this.linkH5 = in.readByte() != 0;
    }

    public static final Creator<LotteyBean> CREATOR = new Creator<LotteyBean>() {
        @Override
        public LotteyBean createFromParcel(Parcel source) {
            return new LotteyBean(source);
        }

        @Override
        public LotteyBean[] newArray(int size) {
            return new LotteyBean[size];
        }
    };


}
