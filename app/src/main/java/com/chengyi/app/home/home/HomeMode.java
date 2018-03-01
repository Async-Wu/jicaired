package com.chengyi.app.home.home;

import android.os.Parcel;
import android.os.Parcelable;
import com.chengyi.app.model.caipiao.Caipiao;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  HomeMode implements Parcelable {

    private  String title;
    private  String desc;
    private  String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private  Caipiao leftNode;//leftNode
    private  Caipiao rightNode;//rightNode

    public Caipiao getRightNode() {
        return rightNode;
    }

    public void setRightNode(Caipiao rightNode) {
        this.rightNode = rightNode;
    }

    public Caipiao getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Caipiao leftNode) {
        this.leftNode = leftNode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeParcelable(this.leftNode, flags);
        dest.writeParcelable(this.rightNode, flags);
    }

    public HomeMode() {
    }

    protected HomeMode(Parcel in) {
        this.title = in.readString();
        this.leftNode = in.readParcelable(Caipiao.class.getClassLoader());
        this.rightNode = in.readParcelable(Caipiao.class.getClassLoader());
    }

    public static final Creator<HomeMode> CREATOR = new Creator<HomeMode>() {
        @Override
        public HomeMode createFromParcel(Parcel source) {
            return new HomeMode(source);
        }

        @Override
        public HomeMode[] newArray(int size) {
            return new HomeMode[size];
        }
    };
}
