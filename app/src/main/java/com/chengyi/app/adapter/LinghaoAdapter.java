package com.chengyi.app.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.model.LinghaoData;

import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@Deprecated
public class  LinghaoAdapter extends BaseAdapter {
    
	private  List<LinghaoData> dataList;
	protected LayoutInflater mInflater;
    public  LinghaoAdapter(Activity activity){
		mInflater = LayoutInflater.from(activity);
	}
	public void setData(List<LinghaoData> list){
		this.dataList=list;
	}
	@Override
	public int getCount() {

		return dataList.size();
	}
	@Override
	public Object getItem(int position) {

		return dataList.get(position);
	}
	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ActivityHolder holder=null;
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.item_linghao, null);
			holder=new ActivityHolder();
			holder.create(convertView);
			convertView.setTag(holder);
		}else{
			holder=(ActivityHolder) convertView.getTag();
		}
		holder.fill(dataList.get(position));
		return convertView;
	}
   class ActivityHolder{
	   TextView num;
	   TextView time;
	   TextView draw;
	   public void create(View convertView){
		   num=(TextView) convertView.findViewById(R.id.linghaonumtextView);
		   time=(TextView) convertView.findViewById(R.id.timetextView);
		   draw=(TextView) convertView.findViewById(R.id.kaijianghaoma);
	   }
	   public void fill(LinghaoData data){
		   if(!data.getStatus().equals("0")){
			   num.setText(data.getReceiveNo());
			   num.setTextColor(Color.RED);
		   }else{
			   num.setText(data.getReceiveNo());
			   num.setTextColor(Color.BLACK);
		   }
		   draw.setText(data.getDrawNumber());
		   time.setText(data.getCreateTime());
	   }
   }
}
