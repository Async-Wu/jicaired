package com.chengyi.app.model.caipiao;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by lishangfan on 2016/12/22.
 */
public class SchemeJoinsEntity extends BaseMode {
    private int id;
    private int schemeId;
    private int lotteryId;
    private int userId;
    private int userSource;
    private int issueId;
    private int autoBuyId;
    private String pdescribe;
    private double money;
    private double proportion;
    private double prize;
    private String datetime;
    private boolean canRemove;
    private String dateWithoutYear;
    private String userName;
    private String userLevel;
    private String drawNumber = "暂无比赛结果";
    private String bingo = "暂无比赛结果";

    public String getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public String getBingo() {
        return bingo;
    }

    public void setBingo(String bingo) {
        this.bingo = bingo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSchemeId(int schemeId) {
        this.schemeId = schemeId;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserSource(int userSource) {
        this.userSource = userSource;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public void setAutoBuyId(int autoBuyId) {
        this.autoBuyId = autoBuyId;
    }

    public void setPdescribe(String pdescribe) {
        this.pdescribe = pdescribe;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public void setDateWithoutYear(String dateWithoutYear) {
        this.dateWithoutYear = dateWithoutYear;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public int getId() {
        return id;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public int getUserId() {
        return userId;
    }

    public int getUserSource() {
        return userSource;
    }

    public int getIssueId() {
        return issueId;
    }

    public int getAutoBuyId() {
        return autoBuyId;
    }

    public String getPdescribe() {
        return pdescribe;
    }

    public double getMoney() {
        return money;
    }

    public double getProportion() {
        return proportion;
    }

    public double getPrize() {
        return prize;
    }

    public String getDatetime() {
        return datetime;
    }

    public boolean isCanRemove() {
        return canRemove;
    }

    public String getDateWithoutYear() {
        return dateWithoutYear;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLevel() {
        return userLevel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.schemeId);
        dest.writeInt(this.lotteryId);
        dest.writeInt(this.userId);
        dest.writeInt(this.userSource);
        dest.writeInt(this.issueId);
        dest.writeInt(this.autoBuyId);
        dest.writeString(this.pdescribe);
        dest.writeDouble(this.money);
        dest.writeDouble(this.proportion);
        dest.writeDouble(this.prize);
        dest.writeString(this.datetime);
        dest.writeByte(this.canRemove ? (byte) 1 : (byte) 0);
        dest.writeString(this.dateWithoutYear);
        dest.writeString(this.userName);
        dest.writeString(this.userLevel);
        dest.writeString(this.drawNumber);
        dest.writeString(this.bingo);
    }

    public SchemeJoinsEntity() {
    }

    protected SchemeJoinsEntity(Parcel in) {
        this.id = in.readInt();
        this.schemeId = in.readInt();
        this.lotteryId = in.readInt();
        this.userId = in.readInt();
        this.userSource = in.readInt();
        this.issueId = in.readInt();
        this.autoBuyId = in.readInt();
        this.pdescribe = in.readString();
        this.money = in.readDouble();
        this.proportion = in.readDouble();
        this.prize = in.readDouble();
        this.datetime = in.readString();
        this.canRemove = in.readByte() != 0;
        this.dateWithoutYear = in.readString();
        this.userName = in.readString();
        this.userLevel = in.readString();
        this.drawNumber = in.readString();
        this.bingo = in.readString();
    }

    public static final Creator<SchemeJoinsEntity> CREATOR = new Creator<SchemeJoinsEntity>() {
        @Override
        public SchemeJoinsEntity createFromParcel(Parcel source) {
            return new SchemeJoinsEntity(source);
        }

        @Override
        public SchemeJoinsEntity[] newArray(int size) {
            return new SchemeJoinsEntity[size];
        }
    };
}
