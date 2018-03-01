package com.chengyi.app.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.model.FeedBackData;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  FeedBackAdapter extends BaseAdapter {
    
	List<FeedBackData>  list=new ArrayList<FeedBackData>();
	public List<FeedBackData> getList() {
		return list;
	}

	public void setList(List<FeedBackData> list) {
		this.list = list;
	}
	protected LayoutInflater mInflater;
	public FeedBackAdapter(Activity activity){
		mInflater = LayoutInflater.from(activity);
	}
	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {

		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.new_item_feedback, null);
			holder.loadViews(convertView);
			convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.fillViews(list.get(position));
		return convertView;
	}
	class ViewHolder{
		TextView  wenText,replyText,time;
		public void loadViews(View convertView){
			wenText=(TextView) convertView.findViewById(R.id.wenText);
			replyText=(TextView) convertView.findViewById(R.id.replyText);
			time=(TextView) convertView.findViewById(R.id.time);
		}
	   public void fillViews(FeedBackData data){
		   wenText.setText(data.getContent());
		   if(TextUtils.isEmpty(data.getReply()))
			   replyText.setText("暂未回复!");
		   else
			   replyText.setText(data.getReply());
		   time.setText(data.getReplyTime());
	   }
	}

}
