package com.chengyi.app.start;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Update_model extends BaseMode{

     private  String AndroidnewVersion;
    private  String current_versionid;
    private  String IOSnewVersion;
    private  String update_desc;
    private  String android_update_url;
    private  String ios_update_url;
    private  int ios_forceUpdate;

    private  int android_forceUpdate;

    public String getAndroidnewVersion() {



        return AndroidnewVersion;
    }

    public void setAndroidnewVersion(String androidnewVersion) {
        AndroidnewVersion = androidnewVersion;
    }

    public String getCurrent_versionid() {
        return current_versionid;
    }

    public void setCurrent_versionid(String current_versionid) {
        this.current_versionid = current_versionid;
    }

    public String getIOSnewVersion() {
        return IOSnewVersion;
    }

    public void setIOSnewVersion(String IOSnewVersion) {
        this.IOSnewVersion = IOSnewVersion;
    }

    public String getUpdate_desc() {
        return update_desc;
    }

    public void setUpdate_desc(String update_desc) {
        this.update_desc = update_desc;
    }

    public String getAndroid_update_url() {
        return android_update_url;
    }

    public void setAndroid_update_url(String android_update_url) {
        this.android_update_url = android_update_url;
    }

    public String getIos_update_url() {
        return ios_update_url;
    }

    public void setIos_update_url(String ios_update_url) {
        this.ios_update_url = ios_update_url;
    }

    public int getIos_forceUpdate() {
        return ios_forceUpdate;
    }

    public void setIos_forceUpdate(int ios_forceUpdate) {
        this.ios_forceUpdate = ios_forceUpdate;
    }

    public int getAndroid_forceUpdate() {
        return android_forceUpdate;
    }

    public void setAndroid_forceUpdate(int android_forceUpdate) {
        this.android_forceUpdate = android_forceUpdate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.AndroidnewVersion);
        dest.writeString(this.current_versionid);
        dest.writeString(this.IOSnewVersion);
        dest.writeString(this.update_desc);
        dest.writeString(this.android_update_url);
        dest.writeString(this.ios_update_url);
        dest.writeInt(this.ios_forceUpdate);
        dest.writeInt(this.android_forceUpdate);
    }

    public Update_model() {
    }

    protected Update_model(Parcel in) {
        this.AndroidnewVersion = in.readString();
        this.current_versionid = in.readString();
        this.IOSnewVersion = in.readString();
        this.update_desc = in.readString();
        this.android_update_url = in.readString();
        this.ios_update_url = in.readString();
        this.ios_forceUpdate = in.readInt();
        this.android_forceUpdate = in.readInt();
    }

    public static final Creator<Update_model> CREATOR = new Creator<Update_model>() {
        @Override
        public Update_model createFromParcel(Parcel source) {
            return new Update_model(source);
        }

        @Override
        public Update_model[] newArray(int size) {
            return new Update_model[size];
        }
    };
}
