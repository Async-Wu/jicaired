package com.chengyi.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.bean.HadLotteryBean;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.Date_util;

import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  DrawNumberAdapter extends BaseAdapter {

    private List<HadLotteryBean> list;
    protected LayoutInflater mInflater;
    private Context mContext;
    LinearLayout.LayoutParams mLP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


    public List<HadLotteryBean> getList() {
        return list;
    }

    public void setList(List<HadLotteryBean> list) {
        this.list = list;
    }

    public DrawNumberAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
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
            convertView = mInflater.inflate(R.layout.new_item_querydrawnumber, parent,false);
            holder = new ViewHolder();
            holder.create(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.fillView(list.get(position));
        return convertView;
    }

    class ViewHolder {
        ImageView caipiaoIcon;
        TextView caipiaoname, issue, date;
        LinearLayout qiulayout;



        public void create(View convertView) {


            caipiaoIcon = (ImageView) convertView.findViewById(R.id.imageView2);
            caipiaoname = (TextView) convertView.findViewById(R.id.caipiaoname);
            issue = (TextView) convertView.findViewById(R.id.issue);
            date = (TextView) convertView.findViewById(R.id.time);
            qiulayout = (LinearLayout) convertView.findViewById(R.id.qiulayout);
        }

        public void fillView(HadLotteryBean data) {

            issue.setVisibility(View.VISIBLE);
         /*   if (data.getLotteryId() == 10065) {
                caipiaoname.setText("新快三");
                caipiaoIcon.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.list_cpiconxk3));
            } else */
         if (data.getLotteryId() == 10066) {
                caipiaoname.setText("上海11选5");
                caipiaoIcon.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.list_cpicon11x5));
            } else {
                Caipiao cp = CaipiaoFactory.getInstance(mContext).getCaipiaoById(data.getLotteryId());
			    if(data.getLotteryId()==10024)
			    	caipiaoname.setText("排列3/5");
			    else
                caipiaoname.setText(cp.getName());
                caipiaoIcon.setBackgroundDrawable(mContext.getResources().getDrawable(cp.getIconResId()));
            }
            if (data.getLotteryId() == CaipiaoConst.ID_LAOSHISHICAI)
                issue.setText(data.getIssue().substring(2) + "期");
            else
                issue.setText(data.getIssue() + "期");



            date.setText(Date_util.getDateString("yyyy-MM-dd hh:mm:ss",data.getDrawTime()));
            ///快三要特殊处理下
            if (CaipiaoUtil.isKySj(data.getLotteryId())) {
                qiulayout.removeAllViews();
                LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                LP.gravity = Gravity.CENTER;
                LP.leftMargin = 12;
                String[] haoma = data.getDrawNumber().split(",");
                int sum = 0;
                for (int i = 0; i < haoma.length; i++) {
                    qiulayout.addView(getImg(haoma[i]), LP);
                    sum += Integer.parseInt(haoma[i]);
                }
                LP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                LP.leftMargin = 15;
                qiulayout.addView(getBtn(sum + "", -1), LP);
                //竞彩足球特殊处理
            } else if (data.getLotteryId() == 10059) {
                issue.setVisibility(View.INVISIBLE);
                qiulayout.removeAllViews();
                TextView btn = new TextView(mContext);
                btn.setGravity(Gravity.CENTER);
                btn.setTextColor(Color.WHITE);

                if (data.getDrawNumber() != null && !TextUtils.isEmpty(data.getDrawNumber()) && data.getDrawNumber().trim().length() > 0) {
                    String[] str = data.getDrawNumber().split(",");
                    btn.setText(str[0] + "(" + str[1] + ") " + str[3] + " " + str[2]);
                } else
                    btn.setText("等待开奖中");
//                btn.setBackgroundResource(R.drawable.football_jc_bg);
                LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                LP.leftMargin = 0;
                qiulayout.addView(btn, LP);
            } else if (data.getLotteryId() == 10039 || data.getLotteryId() == 10040) {
                qiulayout.removeAllViews();
                TextView btn = new TextView(mContext);
                btn.setGravity(Gravity.CENTER);
                btn.setTextColor(Color.WHITE);
                String newStr = data.getDrawNumber();
                btn.setText(newStr);
                btn.setTextAppearance(mContext, R.style.text_style);
                LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
                btn.setBackgroundResource(R.drawable.redqiu_2);
                qiulayout.addView(btn, LP);


            } else {
                addHaoma(data.getDrawNumber());
            }
            //福彩3d的试机号
            if (data.getLotteryId() == 10025) {
                TextView btn = new TextView(mContext);
                btn.setTextSize(16);
                btn.setGravity(Gravity.CENTER_VERTICAL);

                mLP.leftMargin = 20;
                mLP.bottomMargin = 2;
                qiulayout.addView(btn, mLP);
            }
        }

        ///一般数字彩号码布局所用
        private void addHaoma(String number) {
            qiulayout.removeAllViews();
            int index = number.indexOf("#");
            String[] haoma = null;
            LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            LP.gravity = Gravity.CENTER;

            //有后驱情况
            if (index != -1) {

                haoma = number.substring(0, index).split(",");
                for (int i = 0; i < haoma.length; i++) {
                    qiulayout.addView(getBtn(haoma[i], R.drawable.redqiu), LP);
                }
                haoma = number.substring(index + 1).split(",");
                LinearLayout.LayoutParams LP2 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                LP2.gravity = Gravity.CENTER;
                LP2.leftMargin = 20;
                for (int i = 0; i < haoma.length; i++) {
                    if (i == 0)
                        qiulayout.addView(getBtn(haoma[i], R.drawable.blueqiu), LP2);
                    else
                        qiulayout.addView(getBtn(haoma[i], R.drawable.blueqiu), LP);
                }
            } else {

                haoma = number.split(",");
                for (int i = 0; i < haoma.length; i++) {
                    qiulayout.addView(getBtn(haoma[i], R.drawable.redqiu), LP);
                }
            }
        }

        private TextView getBtn(String s, int drawable) {
            TextView btn = new TextView(mContext);
            btn.setGravity(Gravity.CENTER);
            if (drawable != -1) {
                btn.setText(s);
                btn.setTextAppearance(mContext, R.style.text_style);
                btn.setBackgroundResource(drawable);
                btn.setTextColor(Color.WHITE);
            } else {
                btn.setText("和值:" + s);
                btn.setTextColor(Color.GRAY);
                btn.setTextSize(16);
            }
            return btn;
        }

        private ImageView getImg(String s) {
            ImageView img = new ImageView(mContext);
            switch (Integer.parseInt(s)) {
                case 1:
                    img.setBackgroundResource(R.drawable.dice_v1_small);
                    break;
                case 2:
                    img.setBackgroundResource(R.drawable.dice_v2_small);
                    break;
                case 3:
                    img.setBackgroundResource(R.drawable.dice_v3_small);
                    break;
                case 4:
                    img.setBackgroundResource(R.drawable.dice_v4_small);
                    break;
                case 5:
                    img.setBackgroundResource(R.drawable.dice_v5_small);
                    break;
                case 6:
                    img.setBackgroundResource(R.drawable.dice_v6_small);
                    break;
            }
            return img;
        }
    }
}
