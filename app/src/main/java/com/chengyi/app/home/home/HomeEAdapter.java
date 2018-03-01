package com.chengyi.app.home.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BasicAdapter;
import com.chengyi.app.jingji.basket.BasketBall;
import com.chengyi.app.jingji.bzjy.BzjyActivity;
import com.chengyi.app.jingji.football.FootBall;

import com.chengyi.app.jingji.fourgoal.Activity_fourgoal;
import com.chengyi.app.jingji.guanyajun.GuanYaJun;
import com.chengyi.app.jingji.renxuan.Activity_RenXuanQiuChang;
import com.chengyi.app.jingji.six.SixActivity;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.num.lottery.Activity_CaipiaoTouZhu;
import com.chengyi.app.num.lottery.Activity_Touzhuqueren;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class HomeEAdapter extends BasicAdapter<HomeMode> {


    ArrayList<TouzhuquerenData> touzhuDataList = new ArrayList<>();

    public HomeEAdapter(List<HomeMode> mMode, Context mCtx) {
        super(mMode, mCtx);
    }


    @Override
    protected View createView(final int position, View convertView, ViewGroup parent, LayoutInflater inflater) {

        Holder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_lottery, parent, false);
            holder = new Holder();
            holder.llLeft = (LinearLayout) convertView.findViewById(R.id.ll_left);
            holder.llRight = (LinearLayout) convertView.findViewById(R.id.ll_right);
            holder.tv_title_first = (TextView) convertView.findViewById(R.id.tv_title_first);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.ivLeft = (ImageView) convertView.findViewById(R.id.iv_left);
            holder.tvLeft = (TextView) convertView.findViewById(R.id.tv_left);
            holder.ivRight = (ImageView) convertView.findViewById(R.id.iv_right);
            holder.tvRight = (TextView) convertView.findViewById(R.id.tv_right);

            holder.v = convertView.findViewById(R.id.v);
            holder.vv = convertView.findViewById(R.id.vv);
            holder.ll = (LinearLayout) convertView.findViewById(R.id.ll);

            holder.tvLeftDesc = (TextView) convertView.findViewById(R.id.tv_left_desc);
            holder.tvRightDesc = (TextView) convertView.findViewById(R.id.tv_right_desc);


            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        if (mMode.get(position).getLeftNode() == null) {
            holder.ivLeft.setVisibility(View.GONE);
            holder.tvLeft.setVisibility(View.GONE);


        } else {
            holder.tvLeftDesc.setVisibility(View.VISIBLE);

            holder.ivLeft.setImageResource(mMode.get(position).getLeftNode().getIconResId());
            holder.tvLeft.setText(mMode.get(position).getLeftNode().getName());

            holder.tvLeftDesc.setText(mMode.get(position).getLeftNode().getMessage());
            holder.tvLeftDesc.setSelected(true);
        }

        if (mMode.get(position).getRightNode() != null) {

            holder.tvRightDesc.setVisibility(View.VISIBLE);
            holder.ivRight.setImageResource(mMode.get(position).getRightNode().getIconResId());
            holder.tvRight.setText(mMode.get(position).getRightNode().getName());

            holder.tvRightDesc.setText(mMode.get(position).getRightNode().getMessage());
            holder.tvRightDesc.setSelected(true);

        } else {
            holder.ivRight.setVisibility(View.GONE);
            holder.tvRight.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(mMode.get(position).getDesc())) {
            holder.ll.setVisibility(View.VISIBLE);
            if (mMode.get(position).getDesc().contains("开")) {
                holder.tv_title_first.setTextColor(Color.parseColor("#d03715"));
            } else if (mMode.get(position).getDesc().contains("竞技")) {
                holder.tv_title_first.setTextColor(Color.parseColor("#57b520"));
            } else if (mMode.get(position).getDesc().contains("数字")) {
                holder.tv_title_first.setTextColor(Color.parseColor("#278b17"));
            }
            holder.v.setVisibility(View.VISIBLE);
            holder.vv.setVisibility(View.VISIBLE);
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tv_title_first.setVisibility(View.VISIBLE);
            holder.tv_title_first.setText(mMode.get(position).getDesc());
            holder.tvTitle.setText(mMode.get(position).getTitle());
        } else {
            holder.tvTitle.setVisibility(View.GONE);
            holder.tv_title_first.setVisibility(View.GONE);
            holder.v.setVisibility(View.GONE);
            holder.ll.setVisibility(View.GONE);
            holder.vv.setVisibility(View.GONE);
        }


        holder.llLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMode.get(position).getLeftNode().getId() == CaipiaoConst.ID_JINGCAIZUQIU) {
                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getLeftNode());
                    mCtx.startActivity(new Intent(mCtx, FootBall.class));
                } else if (mMode.get(position).getLeftNode().getId() == CaipiaoConst.ID_BASKETBALL) {
                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getLeftNode());
                    mCtx.startActivity(new Intent(mCtx, BasketBall.class));
                } else if (mMode.get(position).getLeftNode().getId() == CaipiaoConst.ID_SHENGFU14CHANG) {
                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getLeftNode());
                    mCtx.startActivity(new Intent(mCtx, Activity_RenXuanQiuChang.class));

                } else if (mMode.get(position).getLeftNode().getId() == CaipiaoConst.ID_GUANYAJUN) {

                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getLeftNode());
                    mCtx.startActivity(new Intent(mCtx, GuanYaJun.class));
                } else if (mMode.get(position).getLeftNode().getId() == CaipiaoConst.ID_BALL) {

                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getLeftNode());
                    mCtx.startActivity(new Intent(mCtx, BzjyActivity.class));
                } else if (CaipiaoConst.ID_SIX == mMode.get(position).getLeftNode().getId()) {
                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getLeftNode());
                    mCtx.startActivity(new Intent(mCtx, SixActivity.class));
                } else if (mMode.get(position).getLeftNode().getId() == CaipiaoConst.ID_RENXUAN9CHANG || mMode.get(position).getLeftNode().getId() == CaipiaoConst.ID_SHENGFU14CHANG) {

                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getLeftNode());
                    mCtx.startActivity(new Intent(mCtx, Activity_RenXuanQiuChang.class));
                } else if (mMode.get(position).getLeftNode().getId() == CaipiaoConst.ID_FOURGOAL || mMode.get(position).getLeftNode().getId() == CaipiaoConst.ID_FOURGOAL) {

                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getLeftNode());
                    mCtx.startActivity(new Intent(mCtx, Activity_fourgoal.class));
                } else {


                    SharedPreferences sp = CaipiaoUtil.getCpSharedPreferences(mCtx);

                    String cashHaoMa = sp.getString(mMode.get(position).getLeftNode().getId() + "haoma", "");


                    if (TextUtils.isEmpty(cashHaoMa)) {

                        CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getLeftNode());
                        mCtx.startActivity(new Intent(mCtx, Activity_CaipiaoTouZhu.class));
                    } else {

                        String[] dList = cashHaoMa.split("#");
                        touzhuDataList.clear();
                        for (int i = 0; i < dList.length; i++) {

                            touzhuDataList.add(getTouzhuData(dList[i], mMode.get(position).getLeftNode()));
                        }
                        CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getLeftNode());

                        mCtx.startActivity(new Intent(mCtx, Activity_Touzhuqueren.class).putExtra("isfrombaocun", true).putExtra("touzhudataList", touzhuDataList));
                    }
                }

            }
        });
        holder.llRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (null == mMode.get(position).getRightNode() || null == mMode.get(position).getRightNode().getName() || "".equals(mMode.get(position).getRightNode().getName())) {
                    return;
                }

                if (mMode.get(position).getRightNode().getId() == CaipiaoConst.ID_JINGCAIZUQIU) {
                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getRightNode());
                    mCtx.startActivity(new Intent(mCtx, FootBall.class));
                } else if (mMode.get(position).getRightNode().getId() == CaipiaoConst.ID_BASKETBALL) {
                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getRightNode());
                    mCtx.startActivity(new Intent(mCtx, BasketBall.class));
                } else if (mMode.get(position).getRightNode().getId() == CaipiaoConst.ID_SHENGFU14CHANG) {
                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getRightNode());
                    mCtx.startActivity(new Intent(mCtx, Activity_RenXuanQiuChang.class));

                } else if (mMode.get(position).getRightNode().getId() == CaipiaoConst.ID_GUANYAJUN) {

                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getRightNode());
                    mCtx.startActivity(new Intent(mCtx, GuanYaJun.class));
                } else if (mMode.get(position).getRightNode().getId() == CaipiaoConst.ID_BALL) {

                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getRightNode());
                    mCtx.startActivity(new Intent(mCtx, BzjyActivity.class));
                } else if (mMode.get(position).getRightNode().getId() == CaipiaoConst.ID_RENXUAN9CHANG || mMode.get(position).getRightNode().getId() == CaipiaoConst.ID_SHENGFU14CHANG) {

                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getRightNode());
                    mCtx.startActivity(new Intent(mCtx, Activity_RenXuanQiuChang.class));
                } else if (CaipiaoConst.ID_SIX == mMode.get(position).getRightNode().getId()) {
                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getRightNode());
                    mCtx.startActivity(new Intent(mCtx, SixActivity.class));
                } else if (mMode.get(position).getRightNode().getId() == CaipiaoConst.ID_FOURGOAL || mMode.get(position).getRightNode().getId() == CaipiaoConst.ID_FOURGOAL) {

                    CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getRightNode());
                    mCtx.startActivity(new Intent(mCtx, com.chengyi.app.jingji.fourgoal.Activity_fourgoal.class));
                } else {
                    SharedPreferences sp = CaipiaoUtil.getCpSharedPreferences(mCtx);

                    String cashHaoMa = sp.getString(mMode.get(position).getRightNode().getId() + "haoma", "");


                    if (TextUtils.isEmpty(cashHaoMa)) {
                        CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getRightNode());
                        mCtx.startActivity(new Intent(mCtx, Activity_CaipiaoTouZhu.class));
                    } else {
                        String[] dList = cashHaoMa.split("#");
                        touzhuDataList.clear();
                        for (int i = 0; i < dList.length; i++) {

                            touzhuDataList.add(getTouzhuData(dList[i], mMode.get(position).getRightNode()));
                        }
                        CaipiaoApplication.getInstance().setCurrentCaipiao(mMode.get(position).getRightNode());

                        mCtx.startActivity(new Intent(mCtx, Activity_Touzhuqueren.class).putExtra("isfrombaocun", true).putExtra("touzhudataList", touzhuDataList));
                    }


                }


            }
        });


        return convertView;
    }

    class Holder {
        ImageView ivLeft, ivRight;
        TextView tvLeft, tvRight;
        TextView tvLeftDesc, tvRightDesc;
        LinearLayout llLeft, llRight, ll;
        TextView tv_title_first, tvTitle;
        View v, vv;


    }

    TouzhuquerenData data;

    private TouzhuquerenData getTouzhuData(String s, Caipiao caipiao) {
        data = new TouzhuquerenData();
        String[] ds = s.split(",");
        data.setHasHouqu(caipiao.getHouquMinCount() > 0);
        data.setName(ds[0]);
        data.setCaipiaoIdAndWanfaType(caipiao.getId(), Integer.valueOf(ds[1]));
        if (ds[0].contains("猜顺子") || ds[0].contains("三同号通选"))
            data.setTouzhuhaoma(ds[2].replace(" ", ","));
        else
            data.setTouzhuhaoma(ds[2]);
        data.setZhushu(Integer.valueOf(ds[3]));

        if (!CaipiaoUtil.isKySj(caipiao.getId()))
        {
            data.setListTouzuList2(getTouzuList(ds[4]));
        }
//        if (caipiao.getId() != CaipiaoConst.ID_NEWKUAI3)
//            data.setListTouzuList2(getTouzuList(ds[4]));
//        if (caipiao.getId() != CaipiaoConst.ID_GUANGXIKUAI3)
//            data.setListTouzuList2(getTouzuList(ds[4]));
//        if (caipiao.getId() != CaipiaoConst.ID_ANHUIKUAI3)
//            data.setListTouzuList2(getTouzuList(ds[4]));
        return data;
    }

    private List<List<Boolean>> getTouzuList(String s) {
        List<List<Boolean>> listTouzuList = new ArrayList<>();
        String[] str = s.split("\\+");
        for (int i = 0; i < str.length; i++) {
            List<Boolean> oneRow = new ArrayList<>();
            String[] row = str[i].split(" ");
            for (int t = 0; t < row.length; t++) {
                oneRow.add(Boolean.valueOf(row[t]));
            }
            listTouzuList.add(oneRow);
        }
        return listTouzuList;
    }

}
