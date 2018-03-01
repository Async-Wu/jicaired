package com.chengyi.app.home.discover;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by lishangfan on 2016/10/11.
 */




public class PageMode extends BaseMode {
    private  int index;

    private int curpage;
    private  int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCurpage() {
        return curpage;
    }

    public void setCurpage(int curpage) {
        this.curpage = curpage;
    }

    public PageMode() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.index);
        dest.writeInt(this.curpage);
        dest.writeInt(this.count);
    }

    protected PageMode(Parcel in) {
        this.index = in.readInt();
        this.curpage = in.readInt();
        this.count = in.readInt();
    }

    public static final Creator<PageMode> CREATOR = new Creator<PageMode>() {
        @Override
        public PageMode createFromParcel(Parcel source) {
            return new PageMode(source);
        }

        @Override
        public PageMode[] newArray(int size) {
            return new PageMode[size];
        }
    };
}
