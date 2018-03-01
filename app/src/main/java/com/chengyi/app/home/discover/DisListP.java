package com.chengyi.app.home.discover;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lishangfan on 2016/10/10.
 */
public class DisListP implements Parcelable {
    private  int page=1;
    private  int size=10;

    public int getPage() {
        return page;
    }
    public  void  loadMore(){
        page++;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.size);
    }

    public DisListP() {
    }

    protected DisListP(Parcel in) {
        this.page = in.readInt();
        this.size = in.readInt();
    }

    public static final Creator<DisListP> CREATOR = new Creator<DisListP>() {
        @Override
        public DisListP createFromParcel(Parcel source) {
            return new DisListP(source);
        }

        @Override
        public DisListP[] newArray(int size) {
            return new DisListP[size];
        }
    };

    public void refresh() {
        page=1;
    }
}
