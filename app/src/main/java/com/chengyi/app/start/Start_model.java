package com.chengyi.app.start;

import android.os.Parcel;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.app.model.home.BaseMode;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Start_model extends BaseMode {


private  String version_content;


    private  Update_model update_model;

    public String getVersion_content() {
        return version_content;
    }

    public void setVersion_content(String version_content) {
        this.version_content = version_content;
    }

    public Update_model getUpdate_model() {

        return  JSONObject.parseObject(getVersion_content(),Update_model.class);
    }

    public void setUpdate_model(Update_model update_model) {
        this.update_model = update_model;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.version_content);
    }

    public Start_model() {
    }

    protected Start_model(Parcel in) {
        this.version_content = in.readString();
    }

    public static final Creator<Start_model> CREATOR = new Creator<Start_model>() {
        @Override
        public Start_model createFromParcel(Parcel source) {
            return new Start_model(source);
        }

        @Override
        public Start_model[] newArray(int size) {
            return new Start_model[size];
        }
    };
}
