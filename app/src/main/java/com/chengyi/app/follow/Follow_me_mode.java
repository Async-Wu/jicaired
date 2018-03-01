package com.chengyi.app.follow;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by lishangfan on 2016/9/19.
 */
public class Follow_me_mode extends BaseMode {

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Follow_me_mode() {
    }

    protected Follow_me_mode(Parcel in) {
    }

    public static final Creator<Follow_me_mode> CREATOR = new Creator<Follow_me_mode>() {
        @Override
        public Follow_me_mode createFromParcel(Parcel source) {
            return new Follow_me_mode(source);
        }

        @Override
        public Follow_me_mode[] newArray(int size) {
            return new Follow_me_mode[size];
        }
    };
}
