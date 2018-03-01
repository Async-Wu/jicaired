package com.chengyi.app.jingji.six;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by lishangfan on 2016/9/10.
 */
public class PopMode extends BaseMode {
    private  String date;
    private  boolean select;
    private  int issuId;

    public int getIssuId() {
        return issuId;
    }

    public void setIssuId(int issuId) {
        this.issuId = issuId;
    }

    public String getDate() {
        return date+"期";
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }

    public PopMode() {
    }

    protected PopMode(Parcel in) {
        this.date = in.readString();
        this.select = in.readByte() != 0;
    }

    public static final Creator<PopMode> CREATOR = new Creator<PopMode>() {
        @Override
        public PopMode createFromParcel(Parcel source) {
            return new PopMode(source);
        }

        @Override
        public PopMode[] newArray(int size) {
            return new PopMode[size];
        }
    };
}
