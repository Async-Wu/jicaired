package com.chengyi.app.model.caipiao;

import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  FanganliuyanEntity {



    private int flag;


    private List<ListEntity> list;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setList(List<ListEntity> list) {
        this.list = list;
    }

    public int getFlag() {
        return flag;
    }

    public List<ListEntity> getList() {
        return list;
    }

    public static class ListEntity {
        private int id;
        private int orderId;
        private int userId;
        private int shopId;
        private String content;
        private int status;
        private String createTime;
        private String userName;
        private String orderNO;

        public void setId(int id) {
            this.id = id;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setOrderNO(String orderNO) {
            this.orderNO = orderNO;
        }

        public int getId() {
            return id;
        }

        public int getOrderId() {
            return orderId;
        }

        public int getUserId() {
            return userId;
        }

        public int getShopId() {
            return shopId;
        }

        public String getContent() {
            return content;
        }

        public int getStatus() {
            return status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getUserName() {
            return userName;
        }

        public String getOrderNO() {
            return orderNO;
        }
    }
}
