package com.chengyi.app.home.get;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.bean.HadLotteryBean;
import com.chengyi.app.util.CaipiaoUtil;

import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  HistoryDrawNumberAdapter extends BaseAdapter {
	
	private List<HadLotteryBean> list;
	protected LayoutInflater mInflater;
	public List<HadLotteryBean> getList() {
		return list;
	}
	public void setList(List<HadLotteryBean> list) {
		this.list = list;
	}
	public HistoryDrawNumberAdapter(Context mContext){
		mInflater = LayoutInflater.from(mContext);
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
			convertView=mInflater.inflate(R.layout.new_item_historydrawnumber, null);
			holder=new ViewHolder();
			holder.create(convertView);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.fillView(position);
		return convertView;
	}
   class  ViewHolder{
	   TextView drawnumber,issue,date,henumber;
	   LinearLayout  qiulayout;
	   public void create(View convertView){
		   drawnumber=(TextView) convertView.findViewById(R.id.drawnumber);
		   issue=(TextView) convertView.findViewById(R.id.issue);
		   date=(TextView) convertView.findViewById(R.id.date);
		   qiulayout=(LinearLayout) convertView.findViewById(R.id.qiulayout);
		   henumber=(TextView) convertView.findViewById(R.id.henumber);
	   }
	   public void  fillView(int location){
		   HadLotteryBean data=list.get(location);
		   issue.setText(data.getIssue()+"期");
		   date.setText(data.getDrawTime());
		   drawnumber.setText(CaipiaoUtil.getSpanFromFuwuqi(data.getDrawNumber()));
		   if(CaipiaoUtil.isKySj(data.getLotteryId())){
			   henumber.setVisibility(View.VISIBLE);
			   henumber.setText("和值:"+getHezhi(data.getDrawNumber()));
		   }
	   }
	  private String getHezhi(String s){
		  String [] str=s.split(",");
		  int sum=0;
		  for(int i=0;i<str.length;i++)
		  {
			  sum+=Integer.parseInt(str[i]);
		  }
		  return sum+"";
	  }
   }
}
