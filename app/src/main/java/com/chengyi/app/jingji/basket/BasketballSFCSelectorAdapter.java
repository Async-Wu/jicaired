package com.chengyi.app.jingji.basket;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BasketballSFCSelectorAdapter extends BaseAdapter{

	
	private LayoutInflater mInflater;
	private BasketballOneGame item;
	private boolean isHost;
	private Context context;
	
	
	public BasketballSFCSelectorAdapter(Context context,boolean isHost, BasketballOneGame item1){
		mInflater = LayoutInflater.from(context);
		this.context=context;
		this.isHost=isHost;
		BasketballOneGame i=item1;
		this.item=(BasketballOneGame) i.clone();
		
	}
	
	public BasketballOneGame getBasketballOneGameData(){
		return item;
	}
	
	public void setClick(int position){
		boolean isSelected=false;
		if(isHost){
			isSelected=item.isSFCHostSelected[position];	
			item.isSFCHostSelected[position]=!isSelected;
			if(item.isSFCHostSelected[position]){
				if(item.SFCHostFlag<6){
				item.SFCHostFlag++;
				}
			}else{
				if(item.SFCHostFlag>0){
					item.SFCHostFlag--;
				}
			}
			
		}else{
			isSelected=item.isSFCGuestSelected[position];	
			item.isSFCGuestSelected[position]=!isSelected;
			if(item.isSFCGuestSelected[position]){
				if(item.SFCGuestFlag<6){
				item.SFCGuestFlag++;
				}
			}else{
				if(item.SFCGuestFlag>0){
					item.SFCGuestFlag--;
				}
			}
		}
	}
	
	@Override
	public int getCount() {

		return 6;
	}

	@Override
	public Object getItem(int position) {

		return position;
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.fragment_lottery_basketball_sfc_selector_adapter, parent,false);
		}

		TextView scoreDistance=(TextView) convertView.findViewById(R.id.basket_selector_item_score_distance);
		TextView spTextview=(TextView) convertView.findViewById(R.id.basket_selector_item_sp);
		LinearLayout layout=(LinearLayout)convertView.findViewById(R.id.basket_selector_item_layout);
		double[] sp=null;
		boolean[] isSelected=null;
		if(isHost){
			sp=item.sfcHostWinSp;
			isSelected=item.isSFCHostSelected;
			
		}else{
			sp=item.sfcGuestWinSp;
			isSelected=item.isSFCGuestSelected;
		}
		
		scoreDistance.setText(item.sfcName[position]);
		spTextview.setText(""+sp[position]);
		
		
		
		if(isSelected[position]){
			spTextview.setTextColor(Color.WHITE);
			scoreDistance.setTextColor(Color.WHITE);
			layout.setBackgroundResource(R.color.red);
		}else{
			layout.setBackgroundResource(R.color.white);
			scoreDistance.setTextColor(context.getResources().getColor(R.color.football_normal));
			spTextview.setTextColor(context.getResources().getColor(R.color.football_grey));
		}
		
		return convertView;
	}

}
