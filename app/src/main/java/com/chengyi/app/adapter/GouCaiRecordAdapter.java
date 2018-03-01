package com.chengyi.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.model.GoucaijiluData;

import java.text.DecimalFormat;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class GouCaiRecordAdapter extends BaseAdapter {

    DecimalFormat df = new DecimalFormat("0");
    DecimalFormat df2 = new DecimalFormat("0.00");
    protected LayoutInflater mInflater;
    private List<GoucaijiluData> listData;
    private Context mContext;

    public List<GoucaijiluData> getListData() {
        return listData;
    }

    public void setListData(List<GoucaijiluData> listData) {
        this.listData = listData;
    }

    public GouCaiRecordAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.new_item_goucaijilu, parent, false);
            holder = new ViewHolder();
            holder.create(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fillView(listData.get(position));
        return convertView;
    }

    class ViewHolder {
        ImageView caipiaoIcon;
        TextView caipiaoname, issue, money, statusdesc, time;

        public void create(View convertView) {
            caipiaoIcon = (ImageView) convertView.findViewById(R.id.imageView2);
            caipiaoname = (TextView) convertView.findViewById(R.id.caipiaoname);
            issue = (TextView) convertView.findViewById(R.id.issueText);
            money = (TextView) convertView.findViewById(R.id.money);
            statusdesc = (TextView) convertView.findViewById(R.id.statusdesc);
            time = (TextView) convertView.findViewById(R.id.time);
        }

        public void fillView(GoucaijiluData data) {
            time.setText(data.getInitiateTime());
            caipiaoname.setText(data.getLotteryName());
            issue.setText(data.getIssue() + "期");
            if (data.isParticipant()) {
                money.setText("参与" + df.format(data.getSchemeAmount()) + "元");
            } else
                money.setText("金额" + df.format(data.getSchemeAmount()) + "元");
            statusdesc.setText(data.getStatusDesc());
            if (data.getStatusDesc().equals("中奖")) {
                statusdesc.setText("中" + df2.format(data.getPrize()) + "元");
//                statusdesc.setTextColor( (R.color.red));
            } else {
                statusdesc.setTextColor(Color.BLACK);
            }
            Caipiao cp = CaipiaoFactory.getInstance(mContext).getCaipiaobyName(data.getLotteryName());

            if (cp != null) {
                caipiaoIcon.setImageResource(cp.getIconResId());
            } else {
                caipiaoIcon.setImageResource(R.drawable.caizhong_icon1);
            }
        }
    }
}
