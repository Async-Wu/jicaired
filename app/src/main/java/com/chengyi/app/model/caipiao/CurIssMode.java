package com.chengyi.app.model.caipiao;

import android.os.Parcel;
import android.text.TextUtils;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  CurIssMode extends BaseMode {

    /**
     * drawTime : 2016-07-03 02:06:43
     * remainTime : 511592
     * upDrawNumber : 04,06,08,09,10
     * issue : 16070430
     * sellStartTime : 2016-07-04 13:46:30
     * upIssue : 16070428
     * issueId : 7932566
     * sellEndTime : 2016-07-04 13:53:30
     */

    private String drawTime;
    private int remainTime;
    private String upDrawNumber;
    private String issue = "";
    private String sellStartTime;
    private String upIssue;
    private int issueId;
    private String sellEndTime;

    public void setDrawTime(String drawTime) {
        this.drawTime = drawTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public void setUpDrawNumber(String upDrawNumber) {
        this.upDrawNumber = upDrawNumber;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setSellStartTime(String sellStartTime) {
        this.sellStartTime = sellStartTime;
    }

    public void setUpIssue(String upIssue) {
        this.upIssue = upIssue;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public void setSellEndTime(String sellEndTime) {
        this.sellEndTime = sellEndTime;
    }

    public String getDrawTime() {
        return drawTime;
    }



    public String getRemainTime() {





        if (remainTime<=0){
            return "已截至";
        }else

        return ""+(remainTime);
    }

    public String getUpDrawNumber() {
        return upDrawNumber;
    }

    public String getIssue() {

        if (TextUtils.isEmpty(issue)) {
            return "获取奖期中";
        }



            return "第" + getStrjiangqi(issue) + "期" ;



    }







    public String getSellStartTime() {
        return sellStartTime;
    }

    public String getUpIssue() {
        return upIssue;
    }

    public int getIssueId() {
        return issueId;
    }

    public String getSellEndTime() {
        return sellEndTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.drawTime);
        dest.writeInt(this.remainTime);
        dest.writeString(this.upDrawNumber);
        dest.writeString(this.issue);
        dest.writeString(this.sellStartTime);
        dest.writeString(this.upIssue);
        dest.writeInt(this.issueId);
        dest.writeString(this.sellEndTime);
    }

    public CurIssMode() {
    }

    protected CurIssMode(Parcel in) {
        this.drawTime = in.readString();
        this.remainTime = in.readInt();
        this.upDrawNumber = in.readString();
        this.issue = in.readString();
        this.sellStartTime = in.readString();
        this.upIssue = in.readString();
        this.issueId = in.readInt();
        this.sellEndTime = in.readString();
    }

    public static final Creator<CurIssMode> CREATOR = new Creator<CurIssMode>() {
        @Override
        public CurIssMode createFromParcel(Parcel source) {
            return new CurIssMode(source);
        }

        @Override
        public CurIssMode[] newArray(int size) {
            return new CurIssMode[size];
        }
    };

    @Override
    public String toString() {
        return "CurIssMode{" +
                "drawTime='" + drawTime + '\'' +
                ", remainTime=" + remainTime +
                ", upDrawNumber='" + upDrawNumber + '\'' +
                ", issue='" + issue + '\'' +
                ", sellStartTime='" + sellStartTime + '\'' +
                ", upIssue='" + upIssue + '\'' +
                ", issueId=" + issueId +
                ", sellEndTime='" + sellEndTime + '\'' +
                '}';
    }
}
