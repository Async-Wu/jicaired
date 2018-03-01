package com.chengyi.app.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public abstract class BasicAdapter<T> extends BaseAdapter{
    protected List<T> mMode;
    protected Context mCtx;

    public BasicAdapter(List<T> mMode, Context mCtx) {
        this.mMode = mMode;
        this.mCtx = mCtx;
    }

    @Override
    public int getCount() {
        return mMode.size();
    }

    @Override
    public Object getItem(int position) {
        return mMode.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater  inflater=LayoutInflater.from(mCtx);
        return createView(position,convertView,parent,inflater);
    }

    protected abstract  View createView(int position, View convertView, ViewGroup parent, LayoutInflater inflater) ;
}
