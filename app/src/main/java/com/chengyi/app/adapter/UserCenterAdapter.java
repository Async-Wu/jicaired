package com.chengyi.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.user.info.UserInfoHisPurchase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@Deprecated
public class  UserCenterAdapter extends BaseAdapter{

	private LayoutInflater mInflater;
	private int nHeight;
	private int maxSize=0;
	private int size=0;
	private ArrayList<UserInfoHisPurchase> recordList;
	 DateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
	
	public UserCenterAdapter(Context context,int height,int maxSize){
		mInflater = LayoutInflater.from(context);
		nHeight=height;
		this.recordList=new ArrayList<UserInfoHisPurchase>();
		this.maxSize=maxSize;
	}
	public void updateData(ArrayList<UserInfoHisPurchase> record){
		this.recordList=record;
		size=recordList.size()>maxSize?maxSize:recordList.size();
	}
	@Override
	public int getCount() {

		return size;
	}

	@Override
	public Object getItem(int position) {

		return recordList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView==null){
			convertView = mInflater.inflate(R.layout.fragment_usercenter_drawer_listview,null);
		}
		TextView lotteryName=(TextView) convertView.findViewById(R.id.usercenter_list_lottery_name);
		TextView money=(TextView) convertView.findViewById(R.id.usercenter_list_lottery_money);
		TextView peroidNum=(TextView) convertView.findViewById(R.id.usercenter_list_lottery_period_num);
		TextView buyTime=(TextView) convertView.findViewById(R.id.usercenter_list_lottery_buy_time);
		TextView result=(TextView) convertView.findViewById(R.id.usercenter_list_lottery_result);
		
		UserInfoHisPurchase item=recordList.get(position);
		lotteryName.setText(item.lotteryName);
		money.setText(String.valueOf((int)item.schemeAmount)+"元");
		peroidNum.setText(item.issue+"期");
		buyTime.setText(formatter.format(item.initiateTime));
		
		if(item.status==501){
			
			
			result.setText("中奖"+item.prize+"元");
			result.setTextColor(Color.RED);
		}else{
			result.setText(getStatus(item.status));
			result.setTextColor(Color.BLACK);
		}
		
//		result.setText(getStatus(item.status));

		
		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,nHeight);
		
		convertView.setLayoutParams(params);
		
		convertView.setTag(R.id.tag,item);
		
		return convertView;
	}
	
	
	
	private String getStatus(int status){
		String statusStr="";
		switch (status) {
        case 101:
            statusStr="认购中";
            break;
        case 201:
            statusStr="委托中";
            break;
        case 301:
            statusStr="成功";
            break;
        case 401:
            statusStr="追号中";
            break;
        case 501:
            statusStr="中奖";
            break;
        case 601:
            statusStr="未中奖";
            break;
        case 701:
            statusStr="撤单";
            break;
        default:
            statusStr="认购中";
            break;
    }
		return statusStr;
	}

}
