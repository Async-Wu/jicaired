package com.chengyi.app.home.hemai;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.model.HemaiListData;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.progress.RoundProgressBarWidthNumber;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  HemaiAdapter extends BaseAdapter {
	List<HemaiListData> list = new ArrayList<HemaiListData>();
	protected LayoutInflater mInflater;
	Context mContext;

	public void setList(List<HemaiListData> list) {
		this.list = list;
	}

	public HemaiAdapter(Activity activity) {
		mInflater = LayoutInflater.from(activity);
		mContext = activity;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.new_item_hemai, parent,false);
			holder = new ViewHolder();
			holder.loadViews(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HemaiListData data = list.get(position);
		fillItem(data, holder);
		return convertView;
	}

	private void fillItem(HemaiListData data, ViewHolder holder) {
		String yuan = mContext.getString(R.string.yuan);
		holder.username.setText(data.getUserName());


		if(data.getLotteryId()==10026)
			holder.caipiaoName.setText("大乐透");
		else
		    holder.caipiaoName.setText(data.getLotteryName());
		holder.fanganjine.setText(CaipiaoUtil.formatZhengshuPrice(data
				.getSchemeAmount()) + yuan);

		int index=data.getProgress().indexOf("+");
		if(index!=-1){
			holder.baoditext.setVisibility(View.VISIBLE);
			String[] text=data.getProgress().split("\\+");

			Integer integer= Integer.parseInt(text[0].replace("%",""));

			holder.jinduText.setProgress(integer);
//			holder.jinduText.setSecondTxt(text[1]);
			holder.baoditext.setText(text[1]);


		}
		else{
			Integer integer= Integer.parseInt(data.getProgress().replace("%",""));

			holder.jinduText.setProgress(integer);
			holder.jinduText.setSecondTxt("");
			holder.baoditext.setText("");
			holder.baoditext.setVisibility(View.GONE);
		}

		holder.shengyujine.setText(CaipiaoUtil.formatZhengshuPrice(data
				.getRemainAmount()) + yuan);
		holder.yigendan.setText(data.getPersonCount()
				+ mContext.getString(R.string.ren));



	}



	class ViewHolder {

		TextView baoditext;
		RoundProgressBarWidthNumber jinduText;
		TextView caipiaoName;
		TextView username;
		LinearLayout dengjiicons;
		TextView fanganjine;
		TextView shengyujine;
		TextView yigendan;

		public void loadViews(View convertView) {

			baoditext = (TextView) convertView.findViewById(R.id.baoditext);
			jinduText = (RoundProgressBarWidthNumber) convertView.findViewById(R.id.textViewjindu);
			caipiaoName=(TextView) convertView.findViewById(R.id.textViewname);
			username = (TextView) convertView.findViewById(R.id.username);
			dengjiicons = (LinearLayout) convertView.findViewById(R.id.dengjiicons);
			fanganjine = (TextView) convertView.findViewById(R.id.zongjine);
			shengyujine = (TextView) convertView.findViewById(R.id.shengyu);
			yigendan = (TextView) convertView.findViewById(R.id.canyu);

		}
	}

}
