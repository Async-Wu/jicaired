package com.chengyi.app.model.footOrder;

import android.os.Parcel;
import android.text.TextUtils;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by lishangfan on 2016/12/22.
 */
public class SchemeDetailEntity extends BaseMode {

    private String issue;
    private int multiple;
    private int money;
    private String statusDesc;
    private String drawNumber;

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public String getIssue() {
        return issue;
    }

    public int getMultiple() {
        return multiple;
    }

    public int getMoney() {
        return money;
    }

    public String getStatusDesc() {
        return statusDesc;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.issue);
        dest.writeInt(this.multiple);
        dest.writeInt(this.money);
        dest.writeString(this.statusDesc);
        dest.writeString(this.drawNumber);
    }

    public SchemeDetailEntity() {
    }

    @Override
    public String toString() {
        return "SchemeDetailEntity{" +
                "issue='" + issue + '\'' +
                ", multiple=" + multiple +
                ", money=" + money +
                ", statusDesc='" + statusDesc + '\'' +
                ", drawNumber='" + drawNumber + '\'' +
                '}';
    }

    protected SchemeDetailEntity(Parcel in) {
        this.issue = in.readString();
        this.multiple = in.readInt();
        this.money = in.readInt();
        this.statusDesc = in.readString();
        this.drawNumber = in.readString();
    }
    public String getDrawNumber() {
        if (TextUtils.isEmpty(drawNumber)) {
            return "暂无开奖号码";
        } else
            return drawNumber.replaceAll(",", "\t");
    }

    public static final Creator<SchemeDetailEntity> CREATOR = new Creator<SchemeDetailEntity>() {
        @Override
        public SchemeDetailEntity createFromParcel(Parcel source) {
            return new SchemeDetailEntity(source);
        }

        @Override
        public SchemeDetailEntity[] newArray(int size) {
            return new SchemeDetailEntity[size];
        }
    };
}
