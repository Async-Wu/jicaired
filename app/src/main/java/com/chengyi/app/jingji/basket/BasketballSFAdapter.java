package com.chengyi.app.jingji.basket;

import android.content.Context;
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
public class  BasketballSFAdapter extends BaseBasketApdater {
	
	public BasketballSFAdapter(Context context, 	OnGamesTouchedCallback onGamesTouchedCallback) {
		mInflater = LayoutInflater.from(context);
		this.context=context;
		gameList = new ArrayList<>();
		gameOneDayArray = new SparseArray<>();
		gameOneDayArrayCount = new SparseIntArray();
		selectedGames = new SparseArray<>();
		 this.onGamesTouchedCallback = onGamesTouchedCallback;
	}

//	public void setData(ArrayList<BasketballOneDay> gameList, int wanfaGuan) {
//		if (gameList == null) {
//			return;
//		}
//		this.gameList = gameList;
//		groupCount = gameList.size();
//		for (int i = 0; i < groupCount; i++) {
//			gameOneDayArray.put(i, gameList.get(i).gameListOneDay);
//			gameOneDayArrayCount.put(i, gameList.get(i).gameListOneDay.size());
//
//		}
//		this.wanfaGuan = wanfaGuan;
//		wanfa = Control.getInstance().getBasketballManager()
//				.getWanfa(wanfaGuan);
//		SparseArray<BasketballOneGame> selectedGames1 = Control.getInstance()
//				.getBasketballManager().selectedLotteryGameArray.get(wanfaGuan);
//		if (selectedGames1 != null) {
//			selectedGames = selectedGames1;
//		} else {
//			selectedGames = new SparseArray<>();
//			Control.getInstance().getBasketballManager().selectedLotteryGameArray
//					.put(wanfaGuan, selectedGames);
//		}
//	}
	
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
					R.layout.fragment_lottery_basketball_listview_sf, parent,false);
			holder = new ChildViewHolder();
			holder.vPing=convertView.findViewById(R.id.v_ping);
			holder.league = (TextView) convertView
					.findViewById(R.id.basket_textview_league);
			holder.gameCode = (TextView) convertView
					.findViewById(R.id.basket_textview_code);
			holder.bigsmall_total= (TextView) convertView
					.findViewById(R.id.bigsmall_total);
			holder.endTime = (TextView) convertView
					.findViewById(R.id.basket_textview_time);
			holder.hostName = (TextView) convertView
					.findViewById(R.id.basket_textview_host);
			holder.hostSp = (TextView) convertView
					.findViewById(R.id.basket_textview_host_sp);
			holder.visitSp = (TextView) convertView
					.findViewById(R.id.basket_textview_visit_sp);
//			holder.hostRF = (TextView) convertView
//					.findViewById(R.id.basket_textview_host_rf);
			holder.visitName = (TextView) convertView
					.findViewById(R.id.basket_textview_visit);
		 
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}

		final BasketballOneGame item = gameOneDayArray.get(groupPosition).get(
				childPosition);
		holder.league.setText(item.leagueName[0]);
		holder.gameCode.setText(item.matchCodeGG.substring(8));

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(item.cp2yEndTime);
		String date = new SimpleDateFormat("HH:mm").format(new java.util.Date(item.cp2yEndTime));
		holder.endTime.setText( date+"截止");

		holder.hostName.setText(item.hostName);
		holder.visitName.setText(item.guestName);

		if (wanfa == BasketballManager.rfsf||wanfa== BasketballManager.mix) {
			holder.bigsmall_total.setVisibility(View.VISIBLE);
			holder.vPing.setVisibility(View.VISIBLE);
			if(item.rateGG<=0){
			holder.bigsmall_total.setText(item.rateGG + "");
			}else{
				holder.bigsmall_total.setText("+"+item.rateGG);
			}
			holder.hostSp.setText("主胜 " + item.rfsheng);
			holder.visitSp.setText("主负 " + item.rffu);
		} else {
			holder.bigsmall_total.setVisibility(View.GONE);
			holder.vPing.setVisibility(View.GONE);
			holder.hostSp.setText("主胜 " + item.sheng);
			holder.visitSp.setText("主负 " + item.fu);
		}
		if (wanfa==BasketballManager.bigsmall){
			holder.vPing.setVisibility(View.VISIBLE);
			holder.bigsmall_total.setVisibility(View.VISIBLE);
			holder.bigsmall_total.setText(item.basePoint + "");
			holder.visitSp.setText("大分 "+item.d);
			holder.hostSp.setText("小分 "+item.x);
		}
		// String num=item.id.trim();
		final int id = item.orderIdLocal;
		if (selectedGames.indexOfKey(id) >= 0) {
			BasketballOneGame selectedItem = selectedGames.get(id);
			boolean[] isSelected = selectedItem.isSFSelected;
			holder.visitSp.setSelected(isSelected[1]);
			holder.hostSp.setSelected(isSelected[0]);

		}else{
			holder.visitSp.setSelected(false);
			holder.hostSp.setSelected(false);
		}

		holder.hostSp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// 存于已选列表
				if(selectedGames.size()==15&&selectedGames.indexOfKey(id)<0){
					Toast.makeText(context, "最多可选15场比赛", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if (selectedGames.indexOfKey(id) >= 0) {
					BasketballOneGame selectedItem = selectedGames.get(id);
					boolean[] isSelected = selectedItem.isSFSelected;
					if (isSelected[0]) {
						selectedItem.isSFSelected[0] = false;
						holder.hostSp.setSelected(false);
						if (selectedItem.SFFlag != -1) {
							selectedItem.SFFlag--;
						}
						if (!selectedItem.isSFSelected[1]) {
							selectedGames.delete(id);
						}

					} else {
						selectedItem.isSFSelected[0] = true;
						if (selectedItem.SFFlag != 1) {
							selectedItem.SFFlag++;
						}
						holder.hostSp.setSelected(true);
					}

				} else {
					item.isSFSelected[0] = true;
					if (item.SFFlag != 1) {
						item.SFFlag++;
					}
					selectedGames.put(id, item);
					holder.hostSp.setSelected(true);

				}
				onGamesTouchedCallback.onTouched();
			}

		});

		holder.visitSp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(selectedGames.size()==15&&selectedGames.indexOfKey(id)<0){
					Toast.makeText(context, "最多可选15场比赛", Toast.LENGTH_SHORT).show();
					return;
				}
				if (selectedGames.indexOfKey(id) >= 0) {
					BasketballOneGame selectedItem = selectedGames.get(id);
					boolean[] isSelected = selectedItem.isSFSelected;
					if (isSelected[1]) {
						selectedItem.isSFSelected[1] = false;
						holder.visitSp.setSelected(false);
						if (selectedItem.SFFlag != -1) {
							selectedItem.SFFlag--;
						}
						if (!selectedItem.isSFSelected[0]) {
							selectedGames.delete(id);
						}
					} else {
						selectedItem.isSFSelected[1] = true;
						if (selectedItem.SFFlag != 1) {
							selectedItem.SFFlag++;
						}
						holder.visitSp.setSelected(true);
					}

				} else {
					item.isSFSelected[1] = true;
					if (item.SFFlag != 1) {
						item.SFFlag++;
					}
					selectedGames.put(id, item);
					holder.visitSp.setSelected(true);

				}
				onGamesTouchedCallback.onTouched();
			}

		});

		return convertView;
	}


	static class ChildViewHolder {
		public TextView league;
		public TextView gameCode;
		public TextView endTime;
		public TextView hostName;
		public TextView hostSp;
		public TextView visitSp;
//		public TextView hostRF;
		public TextView visitName;
		public TextView bigsmall_total;
//		public LinearLayout visitSp;
//		public LinearLayout hostSp;
		View vPing;
	}

}
