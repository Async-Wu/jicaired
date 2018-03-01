package com.chengyi.app.user.info;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by lishangfan on 2016/12/3.
 */
public class OrderSelectMode extends BaseMode {

    private  String name;
    private  boolean select;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        dest.writeString(this.name);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }

    public OrderSelectMode() {
    }

    protected OrderSelectMode(Parcel in) {
        this.name = in.readString();
        this.select = in.readByte() != 0;
    }

    public static final Creator<OrderSelectMode> CREATOR = new Creator<OrderSelectMode>() {
        @Override
        public OrderSelectMode createFromParcel(Parcel source) {
            return new OrderSelectMode(source);
        }

        @Override
        public OrderSelectMode[] newArray(int size) {
            return new OrderSelectMode[size];
        }
    };
}
