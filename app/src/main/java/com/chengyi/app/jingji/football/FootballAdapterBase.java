/**
 * Create on 2012-10-12
 */
package com.chengyi.app.jingji.football;

import android.app.Activity;
import android.database.DataSetObserver;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.jingji.football.FootBall.OnGameTouchCallback;
import com.chengyi.app.model.model.AbsJingcaizuqiuData;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class FootballAdapterBase extends BaseExpandableListAdapter {
    public Activity mActivity;
    protected LayoutInflater inflater;
    protected SparseArray<JingcaizuqiuOneGame> gameList = new SparseArray<JingcaizuqiuOneGame>();
    // 用来保存getchildview返回的view
//	protected Map<String, View> viewList1 = new HashMap<String, View>();
    protected OnGameTouchCallback callback;
//	protected ExpandableListView listview;

    public FootballAdapterBase(Activity mActivity,
                               SparseArray<JingcaizuqiuOneGame> gameList, OnGameTouchCallback handler) {
        this.mActivity = mActivity;
        inflater = mActivity.getLayoutInflater();
        this.gameList = gameList;
        this.callback = handler;
    }

    protected List<AbsJingcaizuqiuData> list = new ArrayList<AbsJingcaizuqiuData>();

    public JingcaizuqiuOneGame getOneGame(int groupPosition, int childPosition) {
        JingcaizuqiuOneGame game = list.get(groupPosition).getGames()
                .get(childPosition);

        return game;
    }

    public List<AbsJingcaizuqiuData> getList() {
        return list;
    }

    // 设置list
    public void setList(List<AbsJingcaizuqiuData> lis) {
        this.list = lis;
    }

    public boolean[] isOpen = new boolean[100];

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {


        int i = 0;
        try {
            i = list == null || list.size() < 1 || list.get(groupPosition) == null ? 0 : list.get(groupPosition).getCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list == null ? 0 : list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getGames().get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        if (convertView == null) {
            convertView = buildGroupView(groupPosition, parent);
            holder = new GroupViewHolder();
            holder.date = (TextView) convertView
                    .findViewById(R.id.datetextview);
            holder.count = (TextView) convertView.findViewById(R.id.count);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();
        }
        if (holder == null) {
            holder = new GroupViewHolder();
            holder.date = (TextView) convertView
                    .findViewById(R.id.datetextview);
            holder.count = (TextView) convertView.findViewById(R.id.count);
            convertView.setTag(holder);
        }
        holder.date.setText(list.get(groupPosition).getDate());
        holder.count.setText("共有" + list.get(groupPosition).getCount() + "场比赛可投");


        return convertView;
    }

    private LinearLayout buildGroupView(int groupPosition, ViewGroup parent) {
        return (LinearLayout) inflater.inflate(
                R.layout.fragment_lottery_football_listview_groupview, parent, false);

    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        isOpen[groupPosition] = true;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        isOpen[groupPosition] = false;
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public boolean hasStableIds() {
        return true;
    }

    static class GroupViewHolder {
        public TextView date;
        public TextView count;
    }

}
