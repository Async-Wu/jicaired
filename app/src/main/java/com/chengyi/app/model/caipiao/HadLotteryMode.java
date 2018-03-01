package com.chengyi.app.model.caipiao;

import android.os.Parcel;
import com.chengyi.app.model.bean.HadLotteryBean;
import com.chengyi.app.model.home.BaseMode;

import java.util.concurrent.CopyOnWriteArrayList;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  HadLotteryMode extends BaseMode {


    /**
     * drawList : [{"issue":"2016077","issueId":7928470,"drawNumber":"01,09,17,19,20,29#10","drawTime":"2016-07-06 02:35:48","lotteryId":"10032"},{"issue":"2016181","issueId":7926550,"drawNumber":"8,6,9","drawTime":"2016-07-07 01:39:41","lotteryId":"10025"},{"issue":"2016078","issueId":7926579,"drawNumber":"06,11,16,21,24,25,30#23","drawTime":"2016-07-07 01:37:32","lotteryId":"10033"},{"issue":"2016181","issueId":7926664,"drawNumber":"02,09,10,11,15","drawTime":"2016-07-07 01:38:18","lotteryId":"10035"},{"issue":"16078","issueId":7928483,"drawNumber":"07,18,29,31,35#05,11","drawTime":"2016-07-07 01:36:42","lotteryId":"10026"},{"issue":"16181","issueId":7926629,"drawNumber":"4,3,7,1,3","drawTime":"2016-07-07 01:40:26","lotteryId":"10024"},{"issue":"2016078","issueId":7926694,"drawNumber":"3,3,5,5,3,7#1","drawTime":"2016-07-06 02:41:32","lotteryId":"10030"},{"issue":"16078","issueId":7926710,"drawNumber":"1,9,4,9,5,3#9","drawTime":"2016-07-06 02:38:47","lotteryId":"10027"},{"issue":"16181","issueId":7928499,"drawNumber":"04,06,11,15,17","drawTime":"2016-07-07 01:39:04","lotteryId":"10028"}]
     */

    private CopyOnWriteArrayList<HadLotteryBean> drawList;

    public void setDrawList(CopyOnWriteArrayList<HadLotteryBean> drawList) {
        this.drawList = drawList;
    }

    public CopyOnWriteArrayList<HadLotteryBean> getDrawList() {
        return drawList;
    }


    public HadLotteryMode() {
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.drawList);
    }

    protected HadLotteryMode(Parcel in) {
    }

    public static final Creator<HadLotteryMode> CREATOR = new Creator<HadLotteryMode>() {
        @Override
        public HadLotteryMode createFromParcel(Parcel source) {
            return new HadLotteryMode(source);
        }

        @Override
        public HadLotteryMode[] newArray(int size) {
            return new HadLotteryMode[size];
        }
    };
}
