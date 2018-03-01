package com.chengyi.app.jingji.basket;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.net.control.Control;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by lishangfan on 2016/11/15.
 */
public abstract class BaseBasketApdater  extends BaseExpandableListAdapter {


    public  OnGamesTouchedCallback onGamesTouchedCallback ;
    DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat formatter2 = new SimpleDateFormat("MM月dd日");
    protected ArrayList<BasketballOneDay> gameList;
    protected int groupCount = 0;
    protected SparseArray<ArrayList<BasketballOneGame>> gameOneDayArray;
    protected SparseIntArray gameOneDayArrayCount;
    protected SparseArray<BasketballOneGame> selectedGames;// key:orderIdLocal
    protected LayoutInflater mInflater;
    protected int wanfaGuan = 200;
    protected int wanfa = 0;
    protected Context context;
    protected Fragment fragment;

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {


       GroupViewHolder holder;
        if (convertView == null) {
            convertView =   mInflater.inflate(
                    R.layout.fragment_lottery_football_listview_groupview, parent,false);
            holder = new BasketballSFCAdapter.GroupViewHolder();
            holder.date = (TextView) convertView
                    .findViewById(R.id.datetextview);
            holder.count = (TextView) convertView.findViewById(R.id.count);
            convertView.setTag(holder);
        } else {
            holder = (BasketballSFCAdapter.GroupViewHolder) convertView.getTag();
        }
        if (holder == null) {
            holder = new BasketballSFCAdapter.GroupViewHolder();
            holder.date = (TextView) convertView
                    .findViewById(R.id.datetextview);
            holder.count = (TextView) convertView.findViewById(R.id.count);
            convertView.setTag(holder);
        }

        String date = gameList.get(groupPosition).dayKey;
        String dayOfWeek = gameList.get(groupPosition).dayOfWeekStr;
        int matchNum = gameList.get(groupPosition).totalMatch;

        String newdate="";

        try {
            newdate=formatter2.format(formatter1.parse(date));
        } catch (Exception e) {

            newdate=date;
        }

        holder.date.setText(newdate +" "+ dayOfWeek);
        holder.count.setText(matchNum + "场比赛");

        return convertView;
    }




    @Override
    public int getGroupCount() {

        return groupCount;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return gameList.get(groupPosition).gameListOneDay.size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return gameOneDayArray.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return gameOneDayArray.get(groupPosition).get(childPosition);
    }


    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {

        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return false;
    }

    static class GroupViewHolder {
        public TextView date;
        public TextView count;
    }
    public void setData(ArrayList<BasketballOneDay> gameList, int wanfaGuan) {

        if (gameList == null) {
            return;
        }

        this.gameList = gameList;
        groupCount = gameList.size();
        for (int i = 0; i < groupCount; i++) {
            gameOneDayArray.put(i, gameList.get(i).gameListOneDay);
            gameOneDayArrayCount.put(i, gameList.get(i).gameListOneDay.size());

        }
        this.wanfaGuan = wanfaGuan;
        wanfa = Control.getInstance().getBasketballManager()
                .getWanfa(wanfaGuan);
        SparseArray<BasketballOneGame> selectedGames1 = Control.getInstance()
                .getBasketballManager().selectedLotteryGameArray.get(wanfaGuan);
        if (selectedGames1 != null) {
            selectedGames = selectedGames1;
        } else {
            selectedGames = new SparseArray<>();
            Control.getInstance().getBasketballManager().selectedLotteryGameArray
                    .put(wanfaGuan, selectedGames);
        }
    }
}
