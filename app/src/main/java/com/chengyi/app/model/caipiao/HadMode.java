package com.chengyi.app.model.caipiao;

import android.os.Parcel;
import com.chengyi.app.model.home.BaseMode;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  HadMode extends BaseMode {


    /**
     * issue : 2016076
     * issueId : 7926577
     * drawNumber : 06,08,10,13,20,29,30#07
     * drawTime : 2016-07-01 21:56:01
     * saleTotal : 0
     * remainTotal : 0
     * items : [{"prizeItem":"一等奖","number":"0","prizeAmount":"1"},{"prizeItem":"二等奖","number":"0","prizeAmount":"1"},{"prizeItem":"三等奖","number":"0","prizeAmount":"1"},{"prizeItem":"四等奖","number":"0","prizeAmount":"200"},{"prizeItem":"五等奖","number":"0","prizeAmount":"50"},{"prizeItem":"六等奖","number":"0","prizeAmount":"10"},{"prizeItem":"七等奖","number":"0","prizeAmount":"5"}]
     */

    private String issue;
    private int issueId;
    private String drawNumber;
    private String drawTime;
    private String saleTotal;
    private String remainTotal;
    /**
     * prizeItem : 一等奖
     * number : 0
     * prizeAmount : 1
     */

    private List<ItemsBean> items;

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public String getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(String drawTime) {
        this.drawTime = drawTime;
    }

    public String getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(String saleTotal) {
        this.saleTotal = saleTotal;
    }

    public String getRemainTotal() {
        return remainTotal;
    }

    public void setRemainTotal(String remainTotal) {
        this.remainTotal = remainTotal;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private String prizeItem;
        private String number;
        private String prizeAmount;

        public String getPrizeItem() {
            return prizeItem;
        }

        public void setPrizeItem(String prizeItem) {
            this.prizeItem = prizeItem;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPrizeAmount() {
            return prizeAmount;
        }

        public void setPrizeAmount(String prizeAmount) {
            this.prizeAmount = prizeAmount;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.issue);
        dest.writeInt(this.issueId);
        dest.writeString(this.drawNumber);
        dest.writeString(this.drawTime);
        dest.writeString(this.saleTotal);
        dest.writeString(this.remainTotal);
        dest.writeList(this.items);
    }

    public HadMode() {
    }

    protected HadMode(Parcel in) {
        this.issue = in.readString();
        this.issueId = in.readInt();
        this.drawNumber = in.readString();
        this.drawTime = in.readString();
        this.saleTotal = in.readString();
        this.remainTotal = in.readString();
        this.items = new ArrayList<ItemsBean>();
        in.readList(this.items, ItemsBean.class.getClassLoader());
    }

    public static final Creator<HadMode> CREATOR = new Creator<HadMode>() {
        @Override
        public HadMode createFromParcel(Parcel source) {
            return new HadMode(source);
        }

        @Override
        public HadMode[] newArray(int size) {
            return new HadMode[size];
        }
    };
}
