package com.chengyi.app.jingji.guanyajun;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.jingji.football.FootBall;
import com.chengyi.app.listener.BtnSelectedListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  CartGuanPopAdapter extends BaseAdapter {

    BtnSelectedListener listener;
    Context activity;
    private List<String> list = new ArrayList<String>();

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    protected LayoutInflater mInflater;
    boolean isGuoGuan = false;         //是否是过关方式
    List<String> guoGuanKind;
    List<TextView> btnList = new ArrayList<>();    //用list来记录过关方式布局中所选择的button

    public CartGuanPopAdapter(Activity activity, boolean isGuoGuan, List<String> list,  List<String> guoGuanKind, BtnSelectedListener listener) {
        mInflater = LayoutInflater.from(activity);
        this.list = list;
        this.isGuoGuan = isGuoGuan;
        this.guoGuanKind = guoGuanKind;
        this.listener = listener;
        this.activity = activity;
    }

    public CartGuanPopAdapter(Activity activity, boolean isGuoGuan, List<String> list, ArrayList<String> guoGuanKind) {
        mInflater = LayoutInflater.from(activity);
        this.list = list;
        this.isGuoGuan = isGuoGuan;
        this.guoGuanKind = guoGuanKind;
        this.activity = activity;
    }

    public void clearBtnList(boolean self) {
        if (!self) {
            for (int i = 0; i < btnList.size(); i++)
                btnList.get(i).setSelected(false);
            btnList.clear();
        } else {
            if (btnList.size() > 1) {
                btnList.get(0).setSelected(false);
                btnList.remove(0);
                notifyDataSetChanged();
            }
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.new_item_jingcaigrid, parent,false);
            holder = new ViewHolder();
            holder.loadViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.btn.setText(list.get(position));
        if (isGuoGuan) {
            holder.btn.setSelected(false);
        } else {
            holder.btn.setSelected(true);
        }
        final TextView bt = holder.btn;
        holder.btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (bt.isSelected()) {
                    btnList.remove(bt);
                    guoGuanKind.remove(bt.getText().toString());
                    bt.setSelected(false);
                    //bt.setTextColor(activity.getResources().getColor(R.color.blue_text));
                } else {//当被选中了，需要清空另外一个过关中所选择的btn
                    //bt.setTextColor(activity.getResources().getColor(R.color.white));
                    if (!btnList.contains(bt))
                        btnList.add(bt);
                    bt.setSelected(true);
                    if (!guoGuanKind.contains(bt.getText().toString()))
                        guoGuanKind.add(bt.getText().toString());
                }
                if (isGuoGuan)
                    listener.changBottomValue(bt.isSelected(), bt.getText().toString());
                else {
                    if (activity instanceof FootBall) {
                        ((FootBall) activity).getListTempData();
                    }
                }
            }
        });
        if (guoGuanKind.contains(list.get(position))) {
            holder.btn.setSelected(true);
            if (!btnList.contains(bt))
                btnList.add(bt);
        } else {
            holder.btn.setSelected(false);
            if (btnList.contains(bt))
                btnList.remove(bt);
        }
        return convertView;
    }

    static class ViewHolder {

        private TextView btn;

        public void loadViews(View convertView) {
            btn = (TextView) convertView.findViewById(R.id.text);
        }
    }

}
