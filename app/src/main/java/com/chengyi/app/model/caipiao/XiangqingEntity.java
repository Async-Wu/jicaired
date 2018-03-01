package com.chengyi.app.model.caipiao;

import android.os.Parcel;
import android.text.TextUtils;
import com.chengyi.app.model.footOrder.SchemeContentEntity;
import com.chengyi.app.model.footOrder.SchemeDetailEntity;
import com.chengyi.app.model.home.BaseMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaqi on 2016/5/11.
 */
public class XiangqingEntity extends BaseMode {


    /**
     * schemeId : 23649180
     * lotteryName : 刮刮乐
     * userName : ceshi123
     * schemeAmount : 5.0
     * schemeNumberUnit : 5
     * issueCount : 1
     * schemeContent : [{"number":"2","type":"刮刮乐"}]
     * remuneration : 0
     * numberType : 直选单式
     * initiateTime : 2016-05-10 22:43:01
     * open : 1
     * statusDesc : 成功
     * buyAmount : 5.0
     * remainAmount : 0.0
     * moeyProgress : 100
     * safeguardProgress : 0
     * schemeType : 101
     * flag : 1
     * canCancel : 0
     * canRemove : 0
     * prizeDetail :
     * multiple : 5
     * describe :
     * participantTimes : 0
     * safeguardTimes : 0
     * schemeDetail : [{"issue":"001","multiple":1,"money":5,"statusDesc":"成功","drawNumber":""}]
     */

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
    private String prizeDetail = "";
    private int multiple;
    private String describe;
    private int participantTimes;
    private int safeguardTimes;
    /**
     * number : 2
     * type : 刮刮乐
     */

    private List<SchemeContentEntity> schemeContent;
    /**
     * issue : 001
     * multiple : 1
     * money : 5.0
     * statusDesc : 成功
     * drawNumber :
     */
    private List<SchemeJoinsEntity> schemeJoins;
    /**
     * currentUserId : 1402857
     * userId : 1402857
     * lotteryId : 10058
     * joinAmount : 1.0
     * ownPrize : 0.0
     */

    private int currentUserId;
    private int userId;
    private int lotteryId;
    private double joinAmount;
    private double ownPrize;

    public void setSchemeJoins(List<SchemeJoinsEntity> schemeJoins) {
        this.schemeJoins = schemeJoins;
    }

    public List<SchemeJoinsEntity> getSchemeJoins() {
        return schemeJoins;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public double getJoinAmount() {
        return joinAmount;
    }

    public void setJoinAmount(double joinAmount) {
        this.joinAmount = joinAmount;
    }

    public double getOwnPrize() {
        return ownPrize;
    }

    public void setOwnPrize(double ownPrize) {
        this.ownPrize = ownPrize;
    }

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
        return "时间:" + initiateTime;
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
        //任选二 3注，奖金18.00元，税后18.00元\u003cbr\u003e总奖金18.00元，税后18.00元。

        prizeDetail = prizeDetail.substring(prizeDetail.indexOf("奖金"), prizeDetail.indexOf("元"));
        if (TextUtils.isEmpty(prizeDetail)) {
            return "";
        } else
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




    public XiangqingEntity() {
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
        dest.writeList(this.schemeJoins);
        dest.writeInt(this.currentUserId);
        dest.writeInt(this.userId);
        dest.writeInt(this.lotteryId);
        dest.writeDouble(this.joinAmount);
        dest.writeDouble(this.ownPrize);
        dest.writeList(this.schemeDetail);
    }

    protected XiangqingEntity(Parcel in) {
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
        this.schemeJoins = new ArrayList<SchemeJoinsEntity>();
        in.readList(this.schemeJoins, SchemeJoinsEntity.class.getClassLoader());
        this.currentUserId = in.readInt();
        this.userId = in.readInt();
        this.lotteryId = in.readInt();
        this.joinAmount = in.readDouble();
        this.ownPrize = in.readDouble();
        this.schemeDetail = new ArrayList<SchemeDetailEntity>();
        in.readList(this.schemeDetail, SchemeDetailEntity.class.getClassLoader());
    }

    public static final Creator<XiangqingEntity> CREATOR = new Creator<XiangqingEntity>() {
        @Override
        public XiangqingEntity createFromParcel(Parcel source) {
            return new XiangqingEntity(source);
        }

        @Override
        public XiangqingEntity[] newArray(int size) {
            return new XiangqingEntity[size];
        }
    };
}
