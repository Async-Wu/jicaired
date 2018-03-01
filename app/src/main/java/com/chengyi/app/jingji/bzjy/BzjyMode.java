package com.chengyi.app.jingji.bzjy;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BzjyMode extends BaseMode {

    /**
     * matches : []
     * dayKey : 60703
     * dayOfWeekStr : 2016-07-12
     * matchCount : 0
     * issueId : 7905190
     */

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.data);
    }

    public BzjyMode() {
    }

    protected BzjyMode(Parcel in) {
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Creator<BzjyMode> CREATOR = new Creator<BzjyMode>() {
        @Override
        public BzjyMode createFromParcel(Parcel source) {
            return new BzjyMode(source);
        }

        @Override
        public BzjyMode[] newArray(int size) {
            return new BzjyMode[size];
        }
    };
}
