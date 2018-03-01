package com.chengyi.app.model.home;

import android.os.Parcel;
import com.chengyi.app.util.CaipiaoUtil;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class UserMsgMode extends BaseMode {
    private String monthConsume;

    public String getMonthConsume() {
        return monthConsume;
    }

    public void setMonthConsume(String monthConsume) {
        this.monthConsume = monthConsume;
    }

    /**
     * balance : 35539.
     * flag : 1
     * score : 227540
     */
    private double capitalHandsel;
    private double balance;

    private int score;

    public String getCapitalHandsel() {
        return CaipiaoUtil.formatPrice(capitalHandsel) + "元";
    }

    public void setCapitalHandsel(double capitalHandsel) {
        this.capitalHandsel = capitalHandsel;
    }

    public String getBalance() {
        return CaipiaoUtil.formatPrice(balance) + "元";
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.balance);

        dest.writeInt(this.score);
    }

    public UserMsgMode() {
    }

    protected UserMsgMode(Parcel in) {
        this.balance = in.readDouble();

        this.score = in.readInt();
    }

    public static final Creator<UserMsgMode> CREATOR = new Creator<UserMsgMode>() {
        @Override
        public UserMsgMode createFromParcel(Parcel source) {
            return new UserMsgMode(source);
        }

        @Override
        public UserMsgMode[] newArray(int size) {
            return new UserMsgMode[size];
        }
    };
}
