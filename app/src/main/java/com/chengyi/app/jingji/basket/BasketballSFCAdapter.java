package com.chengyi.app.jingji.basket;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.chengyi.R;
import com.chengyi.app.net.control.Control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BasketballSFCAdapter extends BaseBasketApdater {

	public BasketballSFCAdapter(Context context,Fragment fragment){
		mInflater = LayoutInflater.from(context);
		this.context=context;
		this.fragment=fragment;
		gameList = new ArrayList<>();
		gameOneDayArray = new SparseArray<>();
		gameOneDayArrayCount = new SparseIntArray();
		selectedGames = new SparseArray<>();
	}



	public void refreshSelected(int wanfaGuan) {

		SparseArray<BasketballOneGame> selectedGames1 = Control.getInstance()
				.getBasketballManager().selectedLotteryGameArray.get(wanfaGuan);
		if (selectedGames1 != null) {
			selectedGames = selectedGames1;
		} else {
			selectedGames = new SparseArray<>();
			Control.getInstance().getBasketballManager().selectedLotteryGameArray
					.put(wanfaGuan, selectedGames);
		}
	}


	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		final ChildViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.fragment_lottery_basketball_listview_sfc, parent,false);
			holder = new ChildViewHolder();
			holder.league = (TextView) convertView
					.findViewById(R.id.basket_textview_league);
			holder.gameCode = (TextView) convertView
					.findViewById(R.id.basket_textview_code);
			holder.endTime = (TextView) convertView
					.findViewById(R.id.basket_textview_time);
			holder.hostName = (TextView) convertView
					.findViewById(R.id.basket_textview_host);
			holder.visitName = (TextView) convertView
					.findViewById(R.id.basket_textview_visit);

			holder.sfcText = (TextView) convertView
					.findViewById(R.id.basket_sfc_choosed_textview);

			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}

		final BasketballOneGame item = gameOneDayArray.get(groupPosition).get(
				childPosition);
		holder.league.setText(item.leagueName[0]);
		holder.gameCode.setText(item.matchCodeGG.substring(8));
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(item.endTime);
		String date = new SimpleDateFormat("HH:mm").format(new java.util.Date(item.cp2yEndTime));
		holder.endTime.setText( date+"截止");

		holder.hostName.setText(item.hostName);
		holder.visitName.setText(item.guestName);


		final int id = item.orderIdLocal;

		StringBuilder hostStr=null;
		StringBuilder visitStr=null;
		if (selectedGames.size() > 0&&selectedGames.get(item.orderIdLocal)!=null) {
			holder.sfcText.setSelected(true);


			boolean[] isSFCHostSelected=selectedGames.get(item.orderIdLocal).isSFCHostSelected;
			boolean[] isSFCGuestSelected=selectedGames.get(item.orderIdLocal).isSFCGuestSelected;
			
			String[] sfcName=selectedGames.get(item.orderIdLocal).sfcName;
			for(int i=0;i<6;i++){
				if(isSFCGuestSelected[i]){
					if(visitStr==null){
						visitStr=new StringBuilder();
						visitStr.append("主负:").append(sfcName[i]);
					}else{
						visitStr.append("; ").append(sfcName[i]);
					}
				}
				if(isSFCHostSelected[i]){
					if(hostStr==null){
						hostStr=new StringBuilder();
						hostStr.append("主胜:").append(sfcName[i]);
					}else{
						hostStr.append("; ").append(sfcName[i]);
					}
				}
			}
			String visitChoosed="";
			String hostChoosed="";
			if(visitStr!=null){
				visitChoosed=visitStr.toString()+" ";
			}
			if(hostStr!=null){
				hostChoosed=hostStr.toString();
			}
			
			String selectedItems=visitChoosed+hostChoosed;
			holder.sfcText.setText(selectedItems);
			
		}else{
			holder.sfcText.setSelected(false);
			holder.sfcText.setText("点击展开比分投注区");

		}
		
		final int group=groupPosition;
		final int child=childPosition;
		
		holder.sfcText.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {


				if(selectedGames.size()==15&&selectedGames.indexOfKey(id)<0){
					Toast.makeText(context, "最多可选15场比赛", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent=new Intent();
				intent.setClass(context,  BasketballSFCSelector.class);
				intent.putExtra("group", group);
				intent.putExtra("child", child);
				fragment.startActivityForResult(intent, 10);
			}
			
		});
		
		
		
		return convertView;
	}


	static class ChildViewHolder {
		public TextView league;
		public TextView gameCode;
		public TextView endTime;
		public TextView hostName;
		public TextView visitName;

		public TextView sfcText;

	}
	
	

}
