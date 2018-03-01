package com.chengyi.app.user.login;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  RegeditMode extends BaseMode {


    /**
     * clientUserSession : 1401217_219fa660f5ccef969b7635be44a0e02d
     */

    private String clientUserSession;

    public String getClientUserSession() {
        return clientUserSession;
    }

    public void setClientUserSession(String clientUserSession) {
        this.clientUserSession = clientUserSession;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.clientUserSession);
    }

    public RegeditMode() {
    }

    protected RegeditMode(Parcel in) {
        this.clientUserSession = in.readString();
    }

    public static final Creator<RegeditMode> CREATOR = new Creator<RegeditMode>() {
        @Override
        public RegeditMode createFromParcel(Parcel source) {
            return new RegeditMode(source);
        }

        @Override
        public RegeditMode[] newArray(int size) {
            return new RegeditMode[size];
        }
    };
}
