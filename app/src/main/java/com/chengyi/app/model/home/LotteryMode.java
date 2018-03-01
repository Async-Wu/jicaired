package com.chengyi.app.model.home;

import android.os.Parcel;
import com.chengyi.app.model.bean.LotteyBean;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class LotteryMode extends BaseMode {


    @Override
    public String toString() {
        return "LotteryMode{" +
                "shuzilist=" + shuzilist +
                ", jingjilist=" + jingjilist +
                ", kuaikailist=" + kuaikailist +
                ", banners=" + banners +
                '}';
    }

    private CopyOnWriteArrayList<LotteyBean> shuzilist;


    private CopyOnWriteArrayList<LotteyBean> jingjilist;


    private CopyOnWriteArrayList<LotteyBean> kuaikailist;
    private List<String> banners;



    public CopyOnWriteArrayList<LotteyBean> LotteyBean() {
        return shuzilist;
    }

    public void setShuzilist(CopyOnWriteArrayList<LotteyBean> shuzilist) {
        this.shuzilist = shuzilist;
    }

    public CopyOnWriteArrayList<LotteyBean> getShuzilist() {

        return deleteIDontKonw(shuzilist);
    }

    public CopyOnWriteArrayList<LotteyBean> getJingjilist() {

        return deleteIDontKonw(jingjilist);
    }

    public void setJingjilist(CopyOnWriteArrayList<LotteyBean> jingjilist) {
        this.jingjilist = jingjilist;
    }

    public CopyOnWriteArrayList<LotteyBean> getKuaikailist() {


        return deleteIDontKonw(kuaikailist);
    }

    private CopyOnWriteArrayList<LotteyBean> deleteIDontKonw(CopyOnWriteArrayList<LotteyBean> l) {
        if (l != null) {
            for (LotteyBean b : l) {
                if (b == null || b.getCaipiao() == null) {
                    l.remove(b);
                }
//                if (b != null && b.getLotteryId() == CaipiaoConst.ID_PAILIE3) {
//                    LotteyBean bean = new LotteyBean();
//                    bean.setLotteryId(CaipiaoConst.ID_PAILIE5);
//
//                    l.add(bean);
//              }


            }
        } else {
            l = new CopyOnWriteArrayList<>();
        }


        return l;

    }

    public void setKuaikailist(CopyOnWriteArrayList<LotteyBean> kuaikailist) {
        this.kuaikailist = kuaikailist;
    }


    public LotteryMode() {
    }

    public List<String> getBanners() {
        return banners;
    }

    public void setBanners(List<String> banners) {
        this.banners = banners;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeTypedList(this.shuzilist);
        dest.writeTypedList(this.jingjilist);
        dest.writeTypedList(this.kuaikailist);
        dest.writeStringList(this.banners);
    }

    protected LotteryMode(Parcel in) {


        this.banners = in.createStringArrayList();
    }

    public static final Creator<LotteryMode> CREATOR = new Creator<LotteryMode>() {
        @Override
        public LotteryMode createFromParcel(Parcel source) {
            return new LotteryMode(source);
        }

        @Override
        public LotteryMode[] newArray(int size) {
            return new LotteryMode[size];
        }
    };


}
