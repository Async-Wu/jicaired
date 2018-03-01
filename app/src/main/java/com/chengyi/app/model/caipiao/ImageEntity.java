package com.chengyi.app.model.caipiao;

import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  ImageEntity {

    /**
     * flag : 1
     * list : [{"imageId":137,"image":"http://res.jiyunkeji.com/images/upload/2016/05/10/201605102245169679946.jpg"}]
     */

    private int flag;
    /**
     * imageId : 137
     * image : http://res.jiyunkeji.com/images/upload/2016/05/10/201605102245169679946.jpg
     */

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
        private int imageId;
        private String image;

        public void setImageId(int imageId) {
            this.imageId = imageId;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getImageId() {
            return imageId;
        }

        public String getImage() {
            return image;
        }
    }
}
