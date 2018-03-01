package com.chengyi.app.model.home;

import android.os.Parcelable;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public abstract class BaseMode implements Parcelable {
    private int flag;
    private String errorMessage = "";

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // 获取奖期的数字字符串
    protected String getStrjiangqi(String str) {
        return str;

    }


}
