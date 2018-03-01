package com.chengyi.app.jingji.six;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lishangfan on 2016/9/9.
 */
public class SixMode extends BaseMode {
    private boolean bzusg, bzupy, bzufu, qzusg, qzupy, qzufu;

    private String hostName;
    private String guestName;
    private String matchTime;
    private String bf;
    private String odds_p;
    private String odds_f;
    private String odds_s;
    private String color;
    private String in;
    private String data;
    private String time;

    public String getData() {

        Date date = getRealDate();

        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        return sdf2.format(date) + "开赛";
    }

    public String getTime() {
//        2016-09-10 22:00:00
        Date date = getRealDate();

        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");

        return sdf2.format(date);
    }

    private Date getRealDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(matchTime);
        } catch (Exception e) {
            e.printStackTrace();
            date = new Date();
        }
        return date;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getBf() {
        return bf;
    }

    public void setBf(String bf) {
        this.bf = bf;
    }

    public String getOdds_p() {
        return odds_p;
    }

    public void setOdds_p(String odds_p) {
        this.odds_p = odds_p;
    }

    public String getOdds_f() {
        return odds_f;
    }

    public void setOdds_f(String odds_f) {
        this.odds_f = odds_f;
    }

    public String getOdds_s() {
        return odds_s;
    }

    public void setOdds_s(String odds_s) {
        this.odds_s = odds_s;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSelect() {
        if ((bzusg || bzupy || bzufu) && (qzusg || qzupy || qzufu)) {
            return 2;
        } else if ((bzusg || bzupy || bzufu) || (qzusg || qzupy || qzufu)) {
            return 1;
        } else {
            return 0;
        }
    }

    public void clear() {
        clearB();
        clearQ();
    }


    public void clearB() {
        bzusg = false;
        bzupy = false;
        bzufu = false;
    }

    public void clearQ() {
        qzusg = false;
        qzupy = false;
        qzufu = false;
    }


    public int getBCount() {

        return getCount(bzusg, bzupy, bzufu);
    }

    public int getQCount() {

        return getCount(qzusg, qzupy, qzufu);
    }

    private int getCount(boolean... flag) {
        int count = 0;

        for (int i = 0; i < flag.length; i++) {
            if (flag[i]) {
                count++;
            }

        }
        return count;
    }


    public String getBNum() {

        return getParam(bzusg, bzupy, bzufu);
    }


    public String getQNum() {

        return getParam(qzusg, qzupy, qzufu);
    }

    public String getParam(boolean... q) {
        String s = "";
        if (q[0]) {
            s += 3;
        }
        if (q[1]) {
            s += 1;
        }
        if (q[2]) {
            s += 0;
        }
        return s;
    }


    public boolean isBzusg() {
        return bzusg;
    }

    public void setBzusg(boolean bzusg) {
        this.bzusg = bzusg;
    }

    public boolean isBzupy() {
        return bzupy;
    }

    public void setBzupy(boolean bzupy) {
        this.bzupy = bzupy;
    }

    public boolean isBzufu() {
        return bzufu;
    }

    public void setBzufu(boolean bzufu) {
        this.bzufu = bzufu;
    }

    public boolean isQzusg() {
        return qzusg;
    }

    public void setQzusg(boolean qzusg) {
        this.qzusg = qzusg;
    }

    public boolean isQzupy() {
        return qzupy;
    }

    public void setQzupy(boolean qzupy) {
        this.qzupy = qzupy;
    }

    public boolean isQzufu() {
        return qzufu;
    }

    public void setQzufu(boolean qzufu) {
        this.qzufu = qzufu;
    }

    public SixMode() {
    }

    public void rand() {


        int i = (int) (Math.random() * 3);

        if (i == 0) {
            setBzusg(true);
        } else if (i == 1) {
            setBzupy(true);
        } else {
            setBzufu(true);
        }


        int j = (int) (Math.random() * 3);
        if (j == 0) {
            setQzusg(true);
        } else if (j == 1) {
            setQzupy(true);
        } else {
            setQzufu(true);
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.bzusg ? (byte) 1 : (byte) 0);
        dest.writeByte(this.bzupy ? (byte) 1 : (byte) 0);
        dest.writeByte(this.bzufu ? (byte) 1 : (byte) 0);
        dest.writeByte(this.qzusg ? (byte) 1 : (byte) 0);
        dest.writeByte(this.qzupy ? (byte) 1 : (byte) 0);
        dest.writeByte(this.qzufu ? (byte) 1 : (byte) 0);
        dest.writeString(this.hostName);
        dest.writeString(this.guestName);
        dest.writeString(this.matchTime);
        dest.writeString(this.bf);
        dest.writeString(this.odds_p);
        dest.writeString(this.odds_f);
        dest.writeString(this.odds_s);
        dest.writeString(this.color);
        dest.writeString(this.in);
        dest.writeString(this.data);
        dest.writeString(this.time);
    }

    protected SixMode(Parcel in) {
        this.bzusg = in.readByte() != 0;
        this.bzupy = in.readByte() != 0;
        this.bzufu = in.readByte() != 0;
        this.qzusg = in.readByte() != 0;
        this.qzupy = in.readByte() != 0;
        this.qzufu = in.readByte() != 0;
        this.hostName = in.readString();
        this.guestName = in.readString();
        this.matchTime = in.readString();
        this.bf = in.readString();
        this.odds_p = in.readString();
        this.odds_f = in.readString();
        this.odds_s = in.readString();
        this.color = in.readString();
        this.in = in.readString();
        this.data = in.readString();
        this.time = in.readString();
    }

    public static final Creator<SixMode> CREATOR = new Creator<SixMode>() {
        @Override
        public SixMode createFromParcel(Parcel source) {
            return new SixMode(source);
        }

        @Override
        public SixMode[] newArray(int size) {
            return new SixMode[size];
        }
    };
}
