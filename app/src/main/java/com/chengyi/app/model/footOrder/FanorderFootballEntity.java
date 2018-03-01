package com.chengyi.app.model.footOrder;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaqi on 2016/6/24.
 */
public class FanorderFootballEntity  extends BaseMode{


    /**
     * schemeId : 23655814
     * lotteryName : 竞彩足球
     * userName : 面包1
     * schemeAmount : 288
     * schemeNumberUnit : 144
     * issueCount : 1
     * schemeContent : [{"matches":[{"hostName":"德国","guestName":"意大利","rate":-1,"sellEndTime":"2016-07-03 02:50:00","matchCode":"20160702047","bingo":"胜平负：平 / 比分：1:1 / 总进球：2球 / 半全场：平平","choose":[0],"dan":false},{"hostName":"甲府风林","guestName":"神户胜利船","matchCode":"20160702101","choose":[1,401],"dan":false},{"hostName":"鹿岛鹿角","guestName":"大阪钢巴","matchCode":"20160702102","choose":[1,401],"dan":false},{"hostName":"福冈黄蜂","guestName":"浦和红钻","matchCode":"20160702103","choose":[1,401],"dan":false},{"hostName":"柏太阳神","guestName":"新泻天鹅","matchCode":"20160702104","choose":[1,401],"dan":false},{"hostName":"大宫松鼠","guestName":"名古屋鲸八","matchCode":"20160702105","choose":[1,401],"dan":false}],"betType":424,"lotteryId":10059,"totalPrize":0,"matchCount":6,"ticketOut":true,"pass":"6串1,5串1"}]
     * remuneration : 0
     * numberType : 竞彩足球混合投注
     * initiateTime : 2016-07-02 16:35:59
     * open : 1
     * statusDesc : 未中奖
     * buyAmount : 288
     * remainAmount : 0
     * moeyProgress : 100
     * safeguardProgress : 0
     * schemeType : 101
     * flag : 1
     * canCancel : 0
     * canRemove : 0
     * prizeDetail :
     * multiple : 144
     * describe :
     * participantTimes : 0
     * safeguardTimes : 0
     * schemeDetail : [{"issue":"20160706","multiple":1,"money":288,"statusDesc":"未中奖","drawNumber":"  "}]
     */

    private int schemeId;
    private String lotteryName;
    private String userName;
    private int schemeAmount;
    private int schemeNumberUnit;
    private int issueCount;
    private int remuneration;
    private String numberType;
    private String initiateTime;
    private int open;
    private String statusDesc;
    private int buyAmount;
    private int remainAmount;
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
    /**
     * matches : [{"hostName":"德国","guestName":"意大利","rate":-1,"sellEndTime":"2016-07-03 02:50:00","matchCode":"20160702047","bingo":"胜平负：平 / 比分：1:1 / 总进球：2球 / 半全场：平平","choose":[0],"dan":false},{"hostName":"甲府风林","guestName":"神户胜利船","matchCode":"20160702101","choose":[1,401],"dan":false},{"hostName":"鹿岛鹿角","guestName":"大阪钢巴","matchCode":"20160702102","choose":[1,401],"dan":false},{"hostName":"福冈黄蜂","guestName":"浦和红钻","matchCode":"20160702103","choose":[1,401],"dan":false},{"hostName":"柏太阳神","guestName":"新泻天鹅","matchCode":"20160702104","choose":[1,401],"dan":false},{"hostName":"大宫松鼠","guestName":"名古屋鲸八","matchCode":"20160702105","choose":[1,401],"dan":false}]
     * betType : 424
     * lotteryId : 10059
     * totalPrize : 0
     * matchCount : 6
     * ticketOut : true
     * pass : 6串1,5串1
     */

    private List<SchemeContentEntity> schemeContent;
    /**
     * issue : 20160706
     * multiple : 1
     * money : 288
     * statusDesc : 未中奖
     * drawNumber :
     */

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

    public void setSchemeAmount(int schemeAmount) {
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

    public void setBuyAmount(int buyAmount) {
        this.buyAmount = buyAmount;
    }

    public void setRemainAmount(int remainAmount) {
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

    public int getSchemeAmount() {
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

    public int getBuyAmount() {
        return buyAmount;
    }

    public int getRemainAmount() {
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
        dest.writeInt(this.schemeAmount);
        dest.writeInt(this.schemeNumberUnit);
        dest.writeInt(this.issueCount);
        dest.writeInt(this.remuneration);
        dest.writeString(this.numberType);
        dest.writeString(this.initiateTime);
        dest.writeInt(this.open);
        dest.writeString(this.statusDesc);
        dest.writeInt(this.buyAmount);
        dest.writeInt(this.remainAmount);
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

    public FanorderFootballEntity() {
    }

    protected FanorderFootballEntity(Parcel in) {
        this.schemeId = in.readInt();
        this.lotteryName = in.readString();
        this.userName = in.readString();
        this.schemeAmount = in.readInt();
        this.schemeNumberUnit = in.readInt();
        this.issueCount = in.readInt();
        this.remuneration = in.readInt();
        this.numberType = in.readString();
        this.initiateTime = in.readString();
        this.open = in.readInt();
        this.statusDesc = in.readString();
        this.buyAmount = in.readInt();
        this.remainAmount = in.readInt();
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

    public static final Creator<FanorderFootballEntity> CREATOR = new Creator<FanorderFootballEntity>() {
        @Override
        public FanorderFootballEntity createFromParcel(Parcel source) {
            return new FanorderFootballEntity(source);
        }

        @Override
        public FanorderFootballEntity[] newArray(int size) {
            return new FanorderFootballEntity[size];
        }
    };

    @Override
    public String toString() {
        return "FanorderFootballEntity{" +
                "schemeId=" + schemeId +
                ", lotteryName='" + lotteryName + '\'' +
                ", userName='" + userName + '\'' +
                ", schemeAmount=" + schemeAmount +
                ", schemeNumberUnit=" + schemeNumberUnit +
                ", issueCount=" + issueCount +
                ", remuneration=" + remuneration +
                ", numberType='" + numberType + '\'' +
                ", initiateTime='" + initiateTime + '\'' +
                ", open=" + open +
                ", statusDesc='" + statusDesc + '\'' +
                ", buyAmount=" + buyAmount +
                ", remainAmount=" + remainAmount +
                ", moeyProgress=" + moeyProgress +
                ", safeguardProgress=" + safeguardProgress +
                ", schemeType=" + schemeType +
                ", flag=" + flag +
                ", canCancel=" + canCancel +
                ", canRemove=" + canRemove +
                ", prizeDetail='" + prizeDetail + '\'' +
                ", multiple=" + multiple +
                ", describe='" + describe + '\'' +
                ", participantTimes=" + participantTimes +
                ", safeguardTimes=" + safeguardTimes +
                ", schemeContent=" + schemeContent +
                ", schemeDetail=" + schemeDetail +
                '}';
    }
}
