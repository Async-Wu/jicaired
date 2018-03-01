package com.chengyi.app.jingji.six;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishangfan on 2016/9/10.
 */
public class SixBean extends BaseMode {

    /**
     * remainTime : 13830381
     * issue : ["2016134","2016135"]
     * data : [{"hostName":"阿森纳","guestName":"南安普","matchTime":"2016-09-10 22:00:00","bf":"","odds_p":"","odds_f":"","odds_s":"","color":"#7E001C","in":""},{"hostName":"伯恩利","guestName":"赫尔城","matchTime":"2016-09-10 22:00:00","bf":"","odds_p":"","odds_f":"","odds_s":"","color":"#7E001C","in":""},{"hostName":"米德尔","guestName":"水晶宫","matchTime":"2016-09-10 22:00:00","bf":"","odds_p":"","odds_f":"","odds_s":"","color":"#7E001C","in":""},{"hostName":"斯托克","guestName":"热刺","matchTime":"2016-09-10 22:00:00","bf":"","odds_p":"","odds_f":"","odds_s":"","color":"#7E001C","in":""},{"hostName":"西汉姆","guestName":"沃特福","matchTime":"2016-09-10 22:00:00","bf":"","odds_p":"","odds_f":"","odds_s":"","color":"#7E001C","in":""},{"hostName":"利物浦","guestName":"莱切城","matchTime":"2016-09-11 00:30:00","bf":"","odds_p":"","odds_f":"","odds_s":"","color":"#7E001C","in":""}]
     * issueId : [7908783,7908784]
     * curIssue : 2016134
     * sellEndTime : 09-10 21:30
     * curIssueId : 7908783
     */

    private int remainTime;
    private String curIssue;
    private String sellEndTime;
    private int curIssueId;
    private List<String> issue;
    /**
     * hostName : 阿森纳
     * guestName : 南安普
     * matchTime : 2016-09-10 22:00:00
     * bf :
     * odds_p :
     * odds_f :
     * odds_s :
     * color : #7E001C
     * in :
     */

    private List<SixMode> data;
    private List<Integer> issueId;

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public String getCurIssue() {
        return curIssue+"期";
    }

    public void setCurIssue(String curIssue) {
        this.curIssue = curIssue;
    }

    public String getSellEndTime() {
        return sellEndTime;
    }

    public void setSellEndTime(String sellEndTime) {
        this.sellEndTime = sellEndTime;
    }

    public int getCurIssueId() {
        return curIssueId;
    }

    public void setCurIssueId(int curIssueId) {
        this.curIssueId = curIssueId;
    }

    public List<String> getIssue() {
        return issue;
    }

    public void setIssue(List<String> issue) {
        this.issue = issue;
    }

    public List<SixMode> getData() {
        return data;
    }

    public void setData(List<SixMode> data) {
        this.data = data;
    }

    public List<Integer> getIssueId() {
        return issueId;
    }

    public void setIssueId(List<Integer> issueId) {
        this.issueId = issueId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.remainTime);
        dest.writeString(this.curIssue);
        dest.writeString(this.sellEndTime);
        dest.writeInt(this.curIssueId);
        dest.writeStringList(this.issue);
        dest.writeTypedList(this.data);
        dest.writeList(this.issueId);
    }

    public SixBean() {
    }

    protected SixBean(Parcel in) {
        this.remainTime = in.readInt();
        this.curIssue = in.readString();
        this.sellEndTime = in.readString();
        this.curIssueId = in.readInt();
        this.issue = in.createStringArrayList();
        this.data = in.createTypedArrayList(SixMode.CREATOR);
        this.issueId = new ArrayList<Integer>();
        in.readList(this.issueId, Integer.class.getClassLoader());
    }

    public static final Creator<SixBean> CREATOR = new Creator<SixBean>() {
        @Override
        public SixBean createFromParcel(Parcel source) {
            return new SixBean(source);
        }

        @Override
        public SixBean[] newArray(int size) {
            return new SixBean[size];
        }
    };
}
