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
import com.chengyi.app.user.jifen.Activity_JiFenShangCheng;
import com.chengyi.app.model.model.JiFenShopData;
import com.chengyi.app.util.ImgUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@Deprecated
public class  JiFenShopAdapter extends BaseAdapter {
	protected LayoutInflater mInflater;
	private List<JiFenShopData> list=new ArrayList<JiFenShopData>();
	Context mContext;
	public JiFenShopAdapter(Context activity) {
		mInflater = LayoutInflater.from(activity);
		mContext = activity;
	}
	public List<JiFenShopData> getList() {
		return list;
	}

	public void setList(List<JiFenShopData> list) {
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
			convertView=mInflater.inflate(R.layout.new_item_jifenshangcheng, null);
			holder.loadViews(convertView);
			convertView.setTag(holder);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		final int p = position;
		holder.caijinBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				((Activity_JiFenShangCheng)mContext).duiHuanShiWu(p, false);
			}
		});
		holder.shiwuBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				((Activity_JiFenShangCheng)mContext).duiHuanShiWu(p, true);
			}
		});
		holder.fillViews(list.get(position));
		return convertView;
	}
	 class ViewHolder{
		    ImageView image;
		    Button  shiwuBtn,caijinBtn;
			TextView  jifenText,name;
			public void loadViews(View convertView){
				image=(ImageView) convertView.findViewById(R.id.imageView1);
				shiwuBtn= (Button) convertView.findViewById(R.id.shiwubtn);
				caijinBtn=(Button) convertView.findViewById(R.id.caijinbtn);
				jifenText=(TextView) convertView.findViewById(R.id.jifentext);
				name=(TextView) convertView.findViewById(R.id.prizename);
			}
			public void fillViews(JiFenShopData data){

					if (!TextUtils.isEmpty(data.getPictureURL())) {
						ImgUtil.SHOW(mContext,data.getPictureURL(),image);
					} else {
						image.setImageResource(R.drawable.list_quesheng);
					}
				
				
//				SimpleImageLoader.display(image, data.getPictureURL(),
//				BitmapFactory.decodeResource(mContext.getResources(),R.drawable.jifen_wupindefault)); 
				name.setText(data.getPrizeName());
				jifenText.setText(data.getScore());
				caijinBtn.setText("兑换"+data.getCash());
			}	
	 }
}
