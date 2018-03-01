package com.chengyi.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengyi.R;
import com.chengyi.app.model.model.ZijinmingxiData;

import java.text.DecimalFormat;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  ZiJinMingXiAdapter extends BaseAdapter {


	private  boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	protected LayoutInflater mInflater;
	private List<ZijinmingxiData> listData;
	Context mContext;
	public List<ZijinmingxiData> getListData() {
		return listData;
	}
	public void setListData(List<ZijinmingxiData> listData) {
		this.listData = listData;
	}
	public ZiJinMingXiAdapter(Context mContext ){

		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
	}
	@Override
	public int getCount() {

		return listData.size();
	}

	@Override
	public Object getItem(int position) {

		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder=null;
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.new_item_zijinmingxi, parent,false);
			holder=new ViewHolder();
			holder.create(convertView);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.fillView(listData.get(position));
 
		return convertView;
	}
	class  ViewHolder{
		DecimalFormat df = new DecimalFormat("0.00");
		ImageView  img;
		TextView mingchen,moneynum,zhanghuyue,time,textView1;
		public void create(View convertView){
			mingchen=(TextView) convertView.findViewById(R.id.mingchen);
			moneynum=(TextView) convertView.findViewById(R.id.moneynum);
			zhanghuyue=(TextView) convertView.findViewById(R.id.zhanghuyuetv);
			time=(TextView) convertView.findViewById(R.id.textView2);
			textView1= (TextView) convertView.findViewById(R.id.textView1);
		}
		public void  fillView(ZijinmingxiData data) {
			mingchen.setText(data.getType());
			time.setText(data.getCreateTime());
			if (data.getIn() > 0) {
				moneynum.setText("+" + df.format(data.getIn()) + "元");
				moneynum.setTextColor(mContext.getResources().getColor(R.color.red));
			} else if (data.getOut() > 0) {

				if ((data.getType().contains("充值")||data.getType().contains("存入"))&&!data.getType().contains("取出")) {
					moneynum.setText("+" + df.format(data.getOut()) + "元");
					moneynum.setTextColor(mContext.getResources().getColor(R.color.red));
				} else {
					moneynum.setText("-" + df.format(data.getOut()) + "元");
					moneynum.setTextColor(mContext.getResources().getColor(R.color.green));
				}

			} else {
				moneynum.setText(df.format(data.getOut()) + "元");
				moneynum.setTextColor(mContext.getResources().getColor(R.color.black));
			}



			if (flag){
				textView1.setText("手续费:");
				zhanghuyue.setText(df.format(data.getBalance()) + "元");
			}else {
				textView1.setText("余额:");
				zhanghuyue.setText(df.format(data.getBalance()) + "元");
			}


		}
	}
}
