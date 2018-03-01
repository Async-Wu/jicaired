package com.chengyi.app.model.home;

import android.os.Parcel;
import android.os.Parcelable;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  LotteryListMode implements Parcelable {

    /**
     * message : 奖池:元
     * lotteryId : 10026
     * isStop : 1
     * h5Url :
     * linkH5 : false
     */

    private String message;
    private int lotteryId;
    private int isStop;
    private String h5Url;
    private boolean linkH5;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public void setIsStop(int isStop) {
        this.isStop = isStop;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public void setLinkH5(boolean linkH5) {
        this.linkH5 = linkH5;
    }

    public String getMessage() {
        return message;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public int getIsStop() {
        return isStop;
    }

    public String getH5Url() {
        return h5Url;
    }

    public boolean getLinkH5() {
        return linkH5;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeInt(this.lotteryId);
        dest.writeInt(this.isStop);
        dest.writeString(this.h5Url);
        dest.writeByte(this.linkH5 ? (byte) 1 : (byte) 0);
    }

    public LotteryListMode() {
    }

    protected LotteryListMode(Parcel in) {
        this.message = in.readString();
        this.lotteryId = in.readInt();
        this.isStop = in.readInt();
        this.h5Url = in.readString();
        this.linkH5 = in.readByte() != 0;
    }

    public static final Creator<LotteryListMode> CREATOR = new Creator<LotteryListMode>() {
        @Override
        public LotteryListMode createFromParcel(Parcel source) {
            return new LotteryListMode(source);
        }

        @Override
        public LotteryListMode[] newArray(int size) {
            return new LotteryListMode[size];
        }
    };
}
