package com.chengyi.app.follow;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

/**
 * Created by lishangfan on 2016/9/18.
 */
public class FollowMeModel extends BaseMode {
    private  String user_name;
    private  String  object_user_name;
    private  String lottery_id;
    private  String lottery_name;
    private String user_id;
    private  String cur_user_id;



    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCur_user_id() {
        return cur_user_id;
    }

    public void setCur_user_id(String cur_user_id) {
        this.cur_user_id = cur_user_id;
    }

    public String getLottery_name() {
        return lottery_name;
    }

    public void setLottery_name(String lottery_name) {
        this.lottery_name = lottery_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getObject_user_name() {
        return object_user_name;
    }

    public void setObject_user_name(String object_user_name) {
        this.object_user_name = object_user_name;
    }

    public String getLottery_id() {
        return lottery_id;
    }

    public void setLottery_id(String lottery_id) {
        this.lottery_id = lottery_id;
    }

    public FollowMeModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_name);
        dest.writeString(this.object_user_name);
        dest.writeString(this.lottery_id);
        dest.writeString(this.lottery_name);
        dest.writeString(this.user_id);
        dest.writeString(this.cur_user_id);
    }

    protected FollowMeModel(Parcel in) {
        this.user_name = in.readString();
        this.object_user_name = in.readString();
        this.lottery_id = in.readString();
        this.lottery_name = in.readString();
        this.user_id = in.readString();
        this.cur_user_id = in.readString();
    }

    public static final Creator<FollowMeModel> CREATOR = new Creator<FollowMeModel>() {
        @Override
        public FollowMeModel createFromParcel(Parcel source) {
            return new FollowMeModel(source);
        }

        @Override
        public FollowMeModel[] newArray(int size) {
            return new FollowMeModel[size];
        }
    };
}
