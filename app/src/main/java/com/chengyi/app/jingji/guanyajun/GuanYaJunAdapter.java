package com.chengyi.app.jingji.guanyajun;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.model.model.GuanYaJunData;
import com.chengyi.app.util.ImgUtil;

import java.util.ArrayList;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  GuanYaJunAdapter extends BaseAdapter {

    ArrayList<GuanYaJunData> list;
    protected LayoutInflater mInflater;
    Context mContext;
    Handler mHandler;
    ArrayList<String> selectedList;
    private boolean isGuanYaJun = false;
    private boolean isDeleteMode = false;
    private boolean isFromTouZhuQueRen = false;

    public boolean isFromTouZhuQueRen() {
        return isFromTouZhuQueRen;
    }

    public void setFromTouZhuQueRen(boolean isFromTouZhuQueRen) {
        this.isFromTouZhuQueRen = isFromTouZhuQueRen;
    }

    public boolean isDeleteMode() {
        return isDeleteMode;
    }

    public void setDeleteMode(boolean isDeleteMode) {
        this.isDeleteMode = isDeleteMode;
    }

    public ArrayList<String> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(ArrayList<String> selectedList) {
        this.selectedList = selectedList;
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }

    public ArrayList<GuanYaJunData> getList() {
        return list;
    }

    public void setList(ArrayList<GuanYaJunData> list) {
        this.list = list;
    }

    public GuanYaJunAdapter(Activity activity) {
        mInflater = LayoutInflater.from(activity);
        mContext = activity;

    }

    public void setIsGuanYaJun(boolean isGuanYaJun) {
        this.isGuanYaJun = isGuanYaJun;
    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            if (!isFromTouZhuQueRen) {
                convertView = mInflater.inflate(
                        R.layout.new_item_guanjunjingcai, null);
            } else {
                convertView = mInflater.inflate(
                        R.layout.fragment_lottery_football_cart_item_gunyajun,
                        null);
            }
            holder = new ViewHolder();
            holder.loadViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GuanYaJunData data = list.get(position);
        final TextView hostName = holder.hostName;
        final TextView peiLvText = holder.peiLvText;
        final TextView guestName = holder.guestName;
        final TextView link = holder.link;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setSelected(!v.isSelected());
                data.setSelected(v.isSelected());
                if (v.isSelected()) {
                    if (!selectedList.contains(data.getJcId()))
                        selectedList.add(data.getJcId());
                    hostName.setTextColor(mContext.getResources().getColor(
                            R.color.white));
                    peiLvText.setTextColor(mContext.getResources().getColor(
                            R.color.white));
                    link.setTextColor(mContext.getResources().getColor(
                            R.color.white));
                    guestName.setTextColor(mContext.getResources().getColor(
                            R.color.white));

                    mHandler.sendEmptyMessage(1);
                } else {
                    try {
                        int index = selectedList.indexOf(data.getJcId());
                        if (index != -1)
                            selectedList.remove(index);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    hostName.setTextColor(mContext.getResources().getColor(
                            R.color.pvcolor1));
                    link.setTextColor(mContext.getResources().getColor(
                            R.color.pvcolor1));
                    guestName.setTextColor(mContext.getResources().getColor(
                            R.color.pvcolor1));
                    peiLvText.setTextColor(mContext.getResources().getColor(
                            R.color.mgray));
                    mHandler.sendEmptyMessage(2);
                }
            }
        });
        if (isFromTouZhuQueRen) {
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    try {
                        int index = selectedList.indexOf(data.getJcId());
                        if (index != -1)
                            selectedList.remove(index);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    list.remove(position);
                    GuanYaJunAdapter.this.notifyDataSetChanged();
                    mHandler.sendEmptyMessage(0);
                }
            });
        }
        holder.fillView(data);
        return convertView;
    }

    class ViewHolder {
        TextView numText, hostName, peiLvText, guestName, link;
        ImageView hostImg, guestImage;
        LinearLayout layout;
        Button deleteBtn;

        public void loadViews(View convertView) {
            numText = (TextView) convertView.findViewById(R.id.num);
            hostName = (TextView) convertView.findViewById(R.id.duiwu1);
            guestName = (TextView) convertView.findViewById(R.id.duiwu2);
            link = (TextView) convertView.findViewById(R.id.duiwu_line);
            peiLvText = (TextView) convertView.findViewById(R.id.speilv);
            layout = (LinearLayout) convertView.findViewById(R.id.layout);
            if (isFromTouZhuQueRen) {
                deleteBtn = (Button) convertView.findViewById(R.id.danbtn);
            } else {
                hostImg = (ImageView) convertView
                        .findViewById(R.id.duiwu1_flag);
                guestImage = (ImageView) convertView
                        .findViewById(R.id.duiwu2_flag);
            }
        }

        public void fillView(GuanYaJunData data) {
            numText.setText(data.getJcId());
            hostName.setText(data.getTeamNameG());

            if (!isFromTouZhuQueRen) {
                ImgUtil.SHOW(mContext, data.getImgG(), hostImg);
            }
            if (isGuanYaJun) {
                if (!data.getTeamNameG().equals("其它")) {
                    guestName.setVisibility(View.VISIBLE);
                    link.setVisibility(View.VISIBLE);
                    guestName.setText(data.getTeamNameY());
                    if (!isFromTouZhuQueRen) {
                        guestImage.setVisibility(View.VISIBLE);
                        hostImg.setVisibility(View.VISIBLE);
//

                        ImgUtil.SHOW(mContext, data.getImgY(), guestImage);

                    }
                } else {
                    guestName.setVisibility(View.GONE);
                    if (!isFromTouZhuQueRen) {
                        guestImage.setVisibility(View.GONE);
                        hostImg.setVisibility(View.GONE);
                    }
                    link.setVisibility(View.GONE);
                }
            } else {
                guestName.setVisibility(View.GONE);
                if (!isFromTouZhuQueRen) {
                    guestImage.setVisibility(View.GONE);
                }
                link.setVisibility(View.GONE);
            }

            if (data.getSellStatus() == 0 && data.getStatus() == 0) {
                peiLvText.setText(String.valueOf(data.getSp()));
                layout.setEnabled(true);
                layout.setSelected(data.isSelected());
                if (data.isSelected()) {
                    hostName.setTextColor(mContext.getResources().getColor(
                            R.color.white));
                    guestName.setTextColor(Color.WHITE);
                    link.setTextColor(Color.WHITE);
                    peiLvText.setTextColor(mContext.getResources().getColor(
                            R.color.white));
                } else {
                    hostName.setTextColor(mContext.getResources().getColor(
                            R.color.pvcolor1));
                    guestName.setTextColor(mContext.getResources().getColor(
                            R.color.pvcolor1));
                    link.setTextColor(mContext.getResources().getColor(
                            R.color.pvcolor1));
                    peiLvText.setTextColor(mContext.getResources().getColor(
                            R.color.mgray));
                }
            } else {
                layout.setEnabled(false);
                layout.setSelected(false);
                switch (data.getSellStatus()) {
                    case 1:
                        peiLvText.setText("[停售]");
                        break;
                    case 2:
                        peiLvText.setText("[已出局]");
                        break;
                    case 3:
                        peiLvText.setText("[冠军]");
                        break;
                }
                hostName.setTextColor(mContext.getResources().getColor(
                        R.color.gray));
                guestName.setTextColor(mContext.getResources().getColor(
                        R.color.gray));
                link.setTextColor(mContext.getResources().getColor(
                        R.color.gray));
                peiLvText.setTextColor(mContext.getResources().getColor(
                        R.color.gray));
            }
            if (isFromTouZhuQueRen) {
                if (isDeleteMode)
                    deleteBtn.setVisibility(View.VISIBLE);
                else
                    deleteBtn.setVisibility(View.GONE);
            }
        }
    }
}
