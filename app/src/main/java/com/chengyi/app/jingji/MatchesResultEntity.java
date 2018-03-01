package com.chengyi.app.jingji;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lishangfan on 2016/12/3.
 */
public class MatchesResultEntity extends BaseMode {

    private String hostName;
    private String guestName;
    private int rate;
    private String sellEndTime;
    private String matchCode;
    private String bingo;
    private boolean dan;
    private String teamId;
    private String createTime;
    private String initiateTime;

    public String getInitiateTime() {
        return initiateTime;
    }

    public void setInitiateTime(String initiateTime) {
        this.initiateTime = initiateTime;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getCreateTime() {

        /**
         * 2016-12-3 11:05:16
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        Date parse;
        try {
            parse = sdf.parse(createTime);
        } catch (Exception e) {


            try {
                parse = sdf.parse(sellEndTime);
            } catch (Exception e1) {
                try {
                    parse = sdf.parse(initiateTime);
                } catch (Exception e2) {
                    e2.printStackTrace();
                    parse = new Date();
                }


            }

        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        if (matchCode != null && matchCode.length() < 3) {
            return weekDays[w] + matchCode;
        } else  if (matchCode != null){

            return weekDays[w] + matchCode.substring(matchCode.length() - 3);
        }
        else {
            return weekDays[w];
        }

    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    private List<Integer> choose;
    private List<String> odds;

    public void setOdds(List<String> odds) {
        this.odds = odds;
    }

    public List<String> getOdds() {
        return odds;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setSellEndTime(String sellEndTime) {
        this.sellEndTime = sellEndTime;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public void setBingo(String bingo) {
        this.bingo = bingo;
    }

    public void setDan(boolean dan) {
        this.dan = dan;
    }

    public void setChoose(List<Integer> choose) {
        this.choose = choose;
    }

    public String getHostName() {
        return hostName;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRate() {
        return rate;
    }

    public String getSellEndTime() {
        return sellEndTime;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public String getBingo() {
        return bingo;
    }

    public boolean isDan() {
        return dan;
    }

    public List<Integer> getChoose() {
        return choose;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hostName);
        dest.writeString(this.guestName);
        dest.writeInt(this.rate);
        dest.writeString(this.sellEndTime);
        dest.writeString(this.matchCode);
        dest.writeString(this.bingo);
        dest.writeByte(this.dan ? (byte) 1 : (byte) 0);
        dest.writeString(this.teamId);
        dest.writeString(this.createTime);
        dest.writeString(this.initiateTime);
        dest.writeList(this.choose);
        dest.writeStringList(this.odds);
    }

    public MatchesResultEntity() {
    }

    protected MatchesResultEntity(Parcel in) {
        this.hostName = in.readString();
        this.guestName = in.readString();
        this.rate = in.readInt();
        this.sellEndTime = in.readString();
        this.matchCode = in.readString();
        this.bingo = in.readString();
        this.dan = in.readByte() != 0;
        this.teamId = in.readString();
        this.createTime = in.readString();
        this.initiateTime = in.readString();
        this.choose = new ArrayList<Integer>();
        in.readList(this.choose, Integer.class.getClassLoader());
        this.odds = in.createStringArrayList();
    }

    public static final Creator<MatchesResultEntity> CREATOR = new Creator<MatchesResultEntity>() {
        @Override
        public MatchesResultEntity createFromParcel(Parcel source) {
            return new MatchesResultEntity(source);
        }

        @Override
        public MatchesResultEntity[] newArray(int size) {
            return new MatchesResultEntity[size];
        }
    };

    @Override
    public String toString() {
        return "MatchesResultEntity{" +
                "hostName='" + hostName + '\'' +
                ", guestName='" + guestName + '\'' +
                ", rate=" + rate +
                ", sellEndTime='" + sellEndTime + '\'' +
                ", matchCode='" + matchCode + '\'' +
                ", bingo='" + bingo + '\'' +
                ", dan=" + dan +
                ", teamId='" + teamId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", initiateTime='" + initiateTime + '\'' +
                ", choose=" + choose +
                ", odds=" + odds +
                '}';
    }
}
