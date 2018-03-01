package com.chengyi.app.model.footOrder;

import android.os.Parcel;
import com.chengyi.app.jingji.MatchesResultEntity;
import com.chengyi.app.model.home.BaseMode;

import java.util.List;

/**
 * Created by lishangfan on 2016/12/22.
 */
public   class SchemeContentEntity  extends  BaseMode{


    private int betType;
    private int lotteryId;
    private double totalPrize;
    private int matchCount;
    private boolean ticketOut;
    private String pass;
    private String number;
    private String type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * hostName : 德国
     * guestName : 意大利
     * rate : -1
     * sellEndTime : 2016-07-03 02:50:00
     * matchCode : 20160702047
     * bingo : 胜平负：平 / 比分：1:1 / 总进球：2球 / 半全场：平平
     * choose : [0]
     * dan : false
     */

    private List<MatchesResultEntity> matches;

    public void setBetType(int betType) {
        this.betType = betType;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public void setTotalPrize(double totalPrize) {
        this.totalPrize = totalPrize;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }

    public void setTicketOut(boolean ticketOut) {
        this.ticketOut = ticketOut;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setMatches(List<MatchesResultEntity> matches) {
        this.matches = matches;
    }

    public int getBetType() {
        return betType;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public double getTotalPrize() {
        return totalPrize;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public boolean isTicketOut() {
        return ticketOut;
    }

    public String getPass() {
        return pass;
    }

    public List<MatchesResultEntity> getMatches() {
        return matches;
    }

    public SchemeContentEntity() {
    }

    @Override
    public String toString() {
        return "SchemeContentEntity{" +
                "betType=" + betType +
                ", lotteryId=" + lotteryId +
                ", totalPrize=" + totalPrize +
                ", matchCount=" + matchCount +
                ", ticketOut=" + ticketOut +
                ", pass='" + pass + '\'' +
                ", matches=" + matches +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.betType);
        dest.writeInt(this.lotteryId);
        dest.writeDouble(this.totalPrize);
        dest.writeInt(this.matchCount);
        dest.writeByte(this.ticketOut ? (byte) 1 : (byte) 0);
        dest.writeString(this.pass);
        dest.writeString(this.number);
        dest.writeString(this.type);
        dest.writeTypedList(this.matches);
    }

    protected SchemeContentEntity(Parcel in) {
        this.betType = in.readInt();
        this.lotteryId = in.readInt();
        this.totalPrize = in.readDouble();
        this.matchCount = in.readInt();
        this.ticketOut = in.readByte() != 0;
        this.pass = in.readString();
        this.number = in.readString();
        this.type = in.readString();
        this.matches = in.createTypedArrayList(MatchesResultEntity.CREATOR);
    }

    public static final Creator<SchemeContentEntity> CREATOR = new Creator<SchemeContentEntity>() {
        @Override
        public SchemeContentEntity createFromParcel(Parcel source) {
            return new SchemeContentEntity(source);
        }

        @Override
        public SchemeContentEntity[] newArray(int size) {
            return new SchemeContentEntity[size];
        }
    };
}