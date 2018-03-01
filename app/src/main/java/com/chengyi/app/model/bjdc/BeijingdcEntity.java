package com.chengyi.app.model.bjdc;

import android.os.Parcel;
import com.chengyi.app.model.footOrder.SchemeContentEntity;
import com.chengyi.app.model.footOrder.SchemeDetailEntity;
import com.chengyi.app.model.home.BaseMode;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BeijingdcEntity  extends BaseMode{
    private int schemeId;
    private String lotteryName;
    private String userName;
    private double schemeAmount;
    private int schemeNumberUnit;
    private int issueCount;
    private int remuneration;
    private String numberType;
    private String initiateTime;
    private int open;
    private String statusDesc;
    private double buyAmount;
    private double remainAmount;
    private int moeyProgress;
    private int safeguardProgress;
    private int schemeType;
    private int flag;
    private int canCancel;
    private int canRemove;
    private String prizeDetail;
    private int multiple;
    private String describe;
    private int participantTimes;
    private int safeguardTimes;
    private List<SchemeContentEntity> schemeContent;
    private List<SchemeDetailEntity> schemeDetail;

    public void setSchemeId(int schemeId) {
        this.schemeId = schemeId;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSchemeAmount(double schemeAmount) {
        this.schemeAmount = schemeAmount;
    }

    public void setSchemeNumberUnit(int schemeNumberUnit) {
        this.schemeNumberUnit = schemeNumberUnit;
    }

    public void setIssueCount(int issueCount) {
        this.issueCount = issueCount;
    }

    public void setRemuneration(int remuneration) {
        this.remuneration = remuneration;
    }

    public void setNumberType(String numberType) {
        this.numberType = numberType;
    }

    public void setInitiateTime(String initiateTime) {
        this.initiateTime = initiateTime;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public void setBuyAmount(double buyAmount) {
        this.buyAmount = buyAmount;
    }

    public void setRemainAmount(double remainAmount) {
        this.remainAmount = remainAmount;
    }

    public void setMoeyProgress(int moeyProgress) {
        this.moeyProgress = moeyProgress;
    }

    public void setSafeguardProgress(int safeguardProgress) {
        this.safeguardProgress = safeguardProgress;
    }

    public void setSchemeType(int schemeType) {
        this.schemeType = schemeType;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setCanCancel(int canCancel) {
        this.canCancel = canCancel;
    }

    public void setCanRemove(int canRemove) {
        this.canRemove = canRemove;
    }

    public void setPrizeDetail(String prizeDetail) {
        this.prizeDetail = prizeDetail;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setParticipantTimes(int participantTimes) {
        this.participantTimes = participantTimes;
    }

    public void setSafeguardTimes(int safeguardTimes) {
        this.safeguardTimes = safeguardTimes;
    }

    public void setSchemeContent(List<SchemeContentEntity> schemeContent) {
        this.schemeContent = schemeContent;
    }

    public void setSchemeDetail(List<SchemeDetailEntity> schemeDetail) {
        this.schemeDetail = schemeDetail;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public String getUserName() {
        return userName;
    }

    public double getSchemeAmount() {
        return schemeAmount;
    }

    public int getSchemeNumberUnit() {
        return schemeNumberUnit;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public int getRemuneration() {
        return remuneration;
    }

    public String getNumberType() {
        return numberType;
    }

    public String getInitiateTime() {
        return initiateTime;
    }

    public int getOpen() {
        return open;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public double getBuyAmount() {
        return buyAmount;
    }

    public double getRemainAmount() {
        return remainAmount;
    }

    public int getMoeyProgress() {
        return moeyProgress;
    }

    public int getSafeguardProgress() {
        return safeguardProgress;
    }

    public int getSchemeType() {
        return schemeType;
    }

    public int getFlag() {
        return flag;
    }

    public int getCanCancel() {
        return canCancel;
    }

    public int getCanRemove() {
        return canRemove;
    }

    public String getPrizeDetail() {
        return prizeDetail;
    }

    public int getMultiple() {
        return multiple;
    }

    public String getDescribe() {
        return describe;
    }

    public int getParticipantTimes() {
        return participantTimes;
    }

    public int getSafeguardTimes() {
        return safeguardTimes;
    }

    public List<SchemeContentEntity> getSchemeContent() {
        return schemeContent;
    }

    public List<SchemeDetailEntity> getSchemeDetail() {
        return schemeDetail;
    }





    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.schemeId);
        dest.writeString(this.lotteryName);
        dest.writeString(this.userName);
        dest.writeDouble(this.schemeAmount);
        dest.writeInt(this.schemeNumberUnit);
        dest.writeInt(this.issueCount);
        dest.writeInt(this.remuneration);
        dest.writeString(this.numberType);
        dest.writeString(this.initiateTime);
        dest.writeInt(this.open);
        dest.writeString(this.statusDesc);
        dest.writeDouble(this.buyAmount);
        dest.writeDouble(this.remainAmount);
        dest.writeInt(this.moeyProgress);
        dest.writeInt(this.safeguardProgress);
        dest.writeInt(this.schemeType);
        dest.writeInt(this.flag);
        dest.writeInt(this.canCancel);
        dest.writeInt(this.canRemove);
        dest.writeString(this.prizeDetail);
        dest.writeInt(this.multiple);
        dest.writeString(this.describe);
        dest.writeInt(this.participantTimes);
        dest.writeInt(this.safeguardTimes);
        dest.writeList(this.schemeContent);
        dest.writeList(this.schemeDetail);
    }

    public BeijingdcEntity() {
    }

    protected BeijingdcEntity(Parcel in) {
        this.schemeId = in.readInt();
        this.lotteryName = in.readString();
        this.userName = in.readString();
        this.schemeAmount = in.readDouble();
        this.schemeNumberUnit = in.readInt();
        this.issueCount = in.readInt();
        this.remuneration = in.readInt();
        this.numberType = in.readString();
        this.initiateTime = in.readString();
        this.open = in.readInt();
        this.statusDesc = in.readString();
        this.buyAmount = in.readDouble();
        this.remainAmount = in.readDouble();
        this.moeyProgress = in.readInt();
        this.safeguardProgress = in.readInt();
        this.schemeType = in.readInt();
        this.flag = in.readInt();
        this.canCancel = in.readInt();
        this.canRemove = in.readInt();
        this.prizeDetail = in.readString();
        this.multiple = in.readInt();
        this.describe = in.readString();
        this.participantTimes = in.readInt();
        this.safeguardTimes = in.readInt();
        this.schemeContent = new ArrayList<SchemeContentEntity>();
        in.readList(this.schemeContent, SchemeContentEntity.class.getClassLoader());
        this.schemeDetail = new ArrayList<SchemeDetailEntity>();
        in.readList(this.schemeDetail, SchemeDetailEntity.class.getClassLoader());
    }

    public static final Creator<BeijingdcEntity> CREATOR = new Creator<BeijingdcEntity>() {
        @Override
        public BeijingdcEntity createFromParcel(Parcel source) {
            return new BeijingdcEntity(source);
        }

        @Override
        public BeijingdcEntity[] newArray(int size) {
            return new BeijingdcEntity[size];
        }
    };
}
