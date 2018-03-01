package com.chengyi.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.chengyi.R;
import com.chengyi.app.model.caipiao.ImageEntity;

import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@Deprecated
public class  ScanimageAdapter extends BaseAdapter {
    private Context context;
    private List<ImageEntity.ListEntity> list;
    public ScanimageAdapter(Context context, List<ImageEntity.ListEntity> list){
        this.context=context;
        this.list=list;
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
        ImageView imageView=null;
        if (convertView==null){
            convertView=LayoutInflater.from(context).inflate(R.layout.scanimage_item,parent,false);
            imageView= (ImageView) convertView.findViewById(R.id.scanitem);
            convertView.setTag(imageView);

        }else {
            imageView= (ImageView) convertView.getTag();
        }
            ImageEntity.ListEntity listEntity = list.get(position);
            imageView.setImageURI(Uri.parse(listEntity.getImage()));
        return convertView;
    }
}
