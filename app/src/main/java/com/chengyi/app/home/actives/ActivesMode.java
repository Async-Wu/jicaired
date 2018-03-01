package com.chengyi.app.home.actives;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by lishangfan on 2016/10/10.
 */
public class ActivesMode  extends BaseMode{
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ActivesMode() {
    }

    protected ActivesMode(Parcel in) {
    }

    public static final Creator<ActivesMode> CREATOR = new Creator<ActivesMode>() {
        @Override
        public ActivesMode createFromParcel(Parcel source) {
            return new ActivesMode(source);
        }

        @Override
        public ActivesMode[] newArray(int size) {
            return new ActivesMode[size];
        }
    };
}
