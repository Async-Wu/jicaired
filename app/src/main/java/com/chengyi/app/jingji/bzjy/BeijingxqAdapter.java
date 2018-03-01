package com.chengyi.app.jingji.bzjy;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.jingji.MatchesResultEntity;
import com.chengyi.app.user.info.OrderSelectAdapter;
import com.chengyi.app.user.info.OrderSelectMode;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.view.MeGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BeijingxqAdapter extends BaseAdapter {
    Context context;
    private String[] biFenStr = "1:0,2:0,2:1,3:0,3:1,3:2,4:0,4:1,4:2,胜其他,0:0,1:1,2:2,3:3,平其他,0:1,0:2,1:2,0:3,1:3,2:3,0:4,1:4,2:4,负其他"
            .split(",");
    private String[] banquanchang = "胜胜,胜平,胜负,平胜,平平,平负,负胜,负平,负负".split(",");
    List<MatchesResultEntity> BJmatches;
    int flag;

    public BeijingxqAdapter(Context context, List<MatchesResultEntity> BJmatches, int flag) {
        this.context = context;

        this.BJmatches = BJmatches;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return BJmatches.size();
    }

    @Override
    public Object getItem(int position) {
        return BJmatches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fananfootball, parent, false);
            viewholder = new Viewholder();
            viewholder.matchcode = (TextView) convertView.findViewById(R.id.item_footmatchcode);
            viewholder.hostnam = (TextView) convertView.findViewById(R.id.item_foothostname);
            viewholder.guestname = (TextView) convertView.findViewById(R.id.item_footguestname);
            viewholder.choose = (MeGridView) convertView.findViewById(R.id.item_footchoose);
            viewholder.result = (MeGridView) convertView.findViewById(R.id.item_result);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }

        /*char[] chars = matchCode.toCharArray();
        String mouth="";
        for (int i=4;i<chars.length-5;i++){
            mouth+=String.valueOf(chars[i]);
        }
        mouth=mouth+"月";
        String day="";
        for (int i=chars.length-5;i<chars.length-3;i++){
            day+=String.valueOf(chars[i]);
        }
        day=day+"号";
        String code="";
        for (int i=chars.length-3;i<chars.length;i++){
            code+=String.valueOf(chars[i]);
        }
        L.d("code", code);*/
        viewholder.matchcode.setText(BJmatches.get(position).getCreateTime() + "");
        viewholder.hostnam.setText(BJmatches.get(position).getHostName() + "");
        viewholder.guestname.setText(BJmatches.get(position).getGuestName() + "");
        List<Integer> choose = BJmatches.get(position).getChoose();
        String choosetype = "";
        List<String> chooses = new ArrayList<>();
        switch (flag) {
            case 30:
                //被单让球胜平负；
                for (int i = 0; i < choose.size(); i++) {
                    String content = "";
                    Integer integer = choose.get(i);
                    if (integer == 0) {
                        content = "让球负,";
                    } else if (integer == 3) {
                        content = "让球胜,";
                    } else if (integer == 1) {
                        content = "让球平,";
                    }
                    chooses.add(content);
                    choosetype = choosetype + content;
                }
                break;
            case 31:
                //总进球
                for (int i = 0; i < choose.size(); i++) {
                    String content = "";
                    if (choose.get(i) == 7) {
                        content = "7球+";
                    } else {
                        content = choose.get(i) + "球,";
                    }
                    chooses.add(content);
                    choosetype = choosetype + content;
                }
                break;
            case 32:
                //上下单双
                for (int i = 0; i < choose.size(); i++) {
                    String content = "";
                    if (choose.get(i) == 0) {
                        content = "上单,";
                    }
                    if (choose.get(i) == 1) {
                        content = "上双,";
                    }
                    if (choose.get(i) == 2) {
                        content = "下单,";
                    }
                    if (choose.get(i) == 3) {
                        content = "下双,";
                    }
                    chooses.add(content);
                    choosetype = choosetype + content;
                }
                break;
            case 33:
                //比分
                for (int i = 0; i < choose.size(); i++) {
                    String content = "";
                    Integer integer = choose.get(i);

                    content = biFenStr[integer] + ",";
                    chooses.add(content);
                    choosetype = choosetype + content;
                }
                break;
            case 34:
                //半全场
                for (int i = 0; i < choose.size(); i++) {
                    String content = "";
                    Integer integer = choose.get(i);
                    content = banquanchang[integer] + ",";
                    chooses.add(content);
                    choosetype = choosetype + content;
                }
                break;
        }
        if (choosetype.endsWith(",")) {
            choosetype = choosetype.substring(0, choosetype.length() - 1);
        }
        List<String> result = new ArrayList<>();
        if (!TextUtils.isEmpty(BJmatches.get(position).getBingo())) {
            String[] split = BJmatches.get(position).getBingo().split("/");
            for (String ss : split) {
                String[] split1 = ss.split("：");

                switch (flag) {

                    case 30:
                    case 31:
                    case 32:
                    case 33:
                    case 34:
                        result.add(ss.trim());
                        break;
                }
            }


        }


        if (result == null || result.size() == 0) {
            result.add("暂无比赛结果");
        }

        List<OrderSelectMode> bgs = AppUtil.resove(context,result, result, true);

        OrderSelectAdapter resultAdapter = new OrderSelectAdapter(bgs, context);
        viewholder.result.setAdapter(resultAdapter);
        List<OrderSelectMode> resove = AppUtil.resove(context,chooses, result, false);
        OrderSelectAdapter orderSelectAdapter = new OrderSelectAdapter(resove, context);
        viewholder.choose.setAdapter(orderSelectAdapter);


        return convertView;
    }

    class Viewholder {
        TextView matchcode, hostnam, guestname;
        MeGridView choose, result;
    }
}
