package com.chengyi.app.home.discover;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by lishangfan on 2016/10/10.
 */
public class DisMode  extends BaseMode{
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public DisMode() {
    }

    protected DisMode(Parcel in) {
    }

    public static final Creator<DisMode> CREATOR = new Creator<DisMode>() {
        @Override
        public DisMode createFromParcel(Parcel source) {
            return new DisMode(source);
        }

        @Override
        public DisMode[] newArray(int size) {
            return new DisMode[size];
        }
    };
}
