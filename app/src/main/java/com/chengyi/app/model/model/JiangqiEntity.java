package com.chengyi.app.model.model;

import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  JiangqiEntity {


    /**
     * threeDNumber : null
     * saleTotal : 326,490,562
     * remainTotal : 925,399,830
     * items : [{"prizeItem":"一等奖","prizeAmount":"8206646","number":"6"},{"prizeItem":"二等奖","prizeAmount":"133610","number":"180"},{"prizeItem":"三等奖","prizeAmount":"3000","number":"848"},{"prizeItem":"四等奖","prizeAmount":"200","number":"55554"},{"prizeItem":"五等奖","prizeAmount":"10","number":"1218600"},{"prizeItem":"六等奖","prizeAmount":"5","number":"7588035"}]
     * luckBlue : null
     * lotteryId : 10032
     * issue : 2016056
     * issueId : 1238923
     * drawNumber : 03,04,08,11,16,18#14
     * drawTime : 2016-05-17
     */

    private Object threeDNumber;
    private String saleTotal;
    private String remainTotal;
    private Object luckBlue;
    private int lotteryId;
    private String issue;
    private int issueId;
    private String drawNumber;
    private String drawTime;
    /**
     * prizeItem : 一等奖
     * prizeAmount : 8206646
     * number : 6
     */

    private List<ItemsEntity> items;

    public void setThreeDNumber(Object threeDNumber) {
        this.threeDNumber = threeDNumber;
    }

    public void setSaleTotal(String saleTotal) {
        this.saleTotal = saleTotal;
    }

    public void setRemainTotal(String remainTotal) {
        this.remainTotal = remainTotal;
    }

    public void setLuckBlue(Object luckBlue) {
        this.luckBlue = luckBlue;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public void setDrawNumber(String drawNumber) {
        this.drawNumber = drawNumber;
    }

    public void setDrawTime(String drawTime) {
        this.drawTime = drawTime;
    }

    public void setItems(List<ItemsEntity> items) {
        this.items = items;
    }

    public Object getThreeDNumber() {
        return threeDNumber;
    }

    public String getSaleTotal() {
        return saleTotal;
    }

    public String getRemainTotal() {
        return remainTotal;
    }

    public Object getLuckBlue() {
        return luckBlue;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public String getIssue() {
        return issue;
    }

    public int getIssueId() {
        return issueId;
    }

    public String getDrawNumber() {
        return drawNumber;
    }

    public String getDrawTime() {
        return drawTime;
    }

    public List<ItemsEntity> getItems() {
        return items;
    }

    public static class ItemsEntity {
        private String prizeItem;
        private String prizeAmount;
        private String number;

        public void setPrizeItem(String prizeItem) {
            this.prizeItem = prizeItem;
        }

        public void setPrizeAmount(String prizeAmount) {
            this.prizeAmount = prizeAmount;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getPrizeItem() {
            return prizeItem;
        }

        public String getPrizeAmount() {
            return prizeAmount;
        }

        public String getNumber() {
            return number;
        }
    }
}
