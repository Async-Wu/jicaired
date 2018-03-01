package com.chengyi.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.user.money.Activity_ChongZhiActivity;
import com.chengyi.app.model.model.YouHuiData;
import com.chengyi.app.util.ImgUtil;

import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@Deprecated
public class  YouHuiAdapter extends BaseAdapter {
    
	List<YouHuiData> list;
	protected LayoutInflater mInflater;
	Context mContext;
	public YouHuiAdapter(Context activity){
		mInflater = LayoutInflater.from(activity);
		mContext = activity;
	}
	public List<YouHuiData> getList() {
		return list;
	}

	public void setList(List<YouHuiData> list) {
		this.list = list;
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

		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.new_item_chongzhiactivity, null);
			holder.loadViews(convertView);
			convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		final int p=position;
		holder.ljBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				((Activity_ChongZhiActivity)mContext).movetoChongzhi(p);
			}
		});
		holder.fillViews(list.get(position));
		return convertView;
	}
	 class ViewHolder{
		    ImageView image;
		    Button  ljBtn;
			TextView  text1,text2,text3;
			public void loadViews(View convertView){
				image=(ImageView) convertView.findViewById(R.id.imageView1);
				ljBtn= (Button) convertView.findViewById(R.id.button1);
				text1=(TextView) convertView.findViewById(R.id.yuanText);
				text2=(TextView) convertView.findViewById(R.id.xianText);
				text3=(TextView) convertView.findViewById(R.id.shengText);
			}
			public void fillViews(YouHuiData data){


				if (!TextUtils.isEmpty(data.getPicture())) {
					ImgUtil.SHOW(mContext,data.getPicture(),image);

				} else {
					image.setImageResource(R.drawable.list_quesheng);
				}
//
				text1.setText(data.getOldCost()+"元");
				text2.setText(data.getNewCost()+"元");
				text3.setText(data.getSaveCost()+"元");
			}	
	 }

}
