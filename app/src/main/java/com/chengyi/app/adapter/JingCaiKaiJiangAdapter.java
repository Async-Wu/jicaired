package com.chengyi.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.model.JingCaiFootBallKaiJiangData;
import com.chengyi.app.model.model.JingCaiFootBallOneGameResult;

import java.util.List;



/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
@Deprecated
public class  JingCaiKaiJiangAdapter extends BaseExpandableListAdapter {
    
	LayoutInflater inflater;
	private Context mContext;
    private List<JingCaiFootBallKaiJiangData> list;
	public List<JingCaiFootBallKaiJiangData> getList() {
		return list;
	}
	public void setList(List<JingCaiFootBallKaiJiangData> list) {
		this.list = list;
	}
    public JingCaiKaiJiangAdapter(Context mContext){
    	inflater = LayoutInflater.from(mContext);
    	this.mContext=mContext;
    }
	@Override
	public Object getChild(int groupPosition, int childPosition) {

		return list.get(groupPosition).getGames().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ChildViewHolder holder = null;
		if (convertView == null) {
			holder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.new_item_jingcaizuqiu_kaijiang, null);
			convertView.setTag(holder);
			holder.loadViews(convertView);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}
		final JingCaiFootBallOneGameResult game = list.get(groupPosition).getGames().get(childPosition);
		holder.fillView(game);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return list.get(groupPosition).getCount();
	}

	@Override
	public Object getGroup(int groupPosition) {

		return list.get(groupPosition);
	}

	@Override
	public int getGroupCount() {

		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition) {

		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		GroupViewHolder holder;
		if (convertView == null) {
			convertView =  inflater.inflate(R.layout.fragment_lottery_football_listview_groupview, null);
			holder = new GroupViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.datetextview);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder) convertView.getTag();
		}
		if(holder==null){
			holder = new GroupViewHolder();
			holder.date = (TextView) convertView.findViewById(R.id.datetextview);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			convertView.setTag(holder);
		}
		holder.date.setText(list.get(groupPosition).getDate() );
		holder.count.setText(list.get(groupPosition).getCount() + "场比赛");
		return convertView;
	}

	@Override
	public boolean hasStableIds() {

		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {

		return false;
	}
	static class GroupViewHolder {
		public TextView date;
		public TextView count;
	}
	class ChildViewHolder {
		TextView textView1,textView2,zhudui,kedui,bifen,textView3,textView4,textView5,textView6,textView7,textView8;
		int color=Color.GRAY;
		public void loadViews(View convertView) {
			textView1=(TextView) convertView.findViewById(R.id.textView1);
			textView2=(TextView) convertView.findViewById(R.id.textView2);
			zhudui=(TextView) convertView.findViewById(R.id.zhudui);
			kedui=(TextView) convertView.findViewById(R.id.kedui);
			bifen=(TextView) convertView.findViewById(R.id.bifen);
			textView3=(TextView) convertView.findViewById(R.id.textView3);
			textView4=(TextView) convertView.findViewById(R.id.textView4);
			textView5=(TextView) convertView.findViewById(R.id.textView5);
			textView6=(TextView) convertView.findViewById(R.id.textView6);
			textView7=(TextView) convertView.findViewById(R.id.textView7);
			textView8=(TextView) convertView.findViewById(R.id.textView8);
			
		}
		public  void fillView(JingCaiFootBallOneGameResult game){
			textView1.setText(game.getLeagueName());
			//textView1.setText("欧冠");
			textView2.setText(game.getMatchTime().split(" ")[1]+"开赛");
			zhudui.setText(game.getHostName());
			kedui.setText(game.getGuestName());
			bifen.setText(game.getLastScore());
			setTextView(textView3,"让球胜平负("+game.getRate()+"):"+getSaiGuo(game.getRqSpfResult()));
			color=mContext.getResources().getColor(R.color.jcred);
			setTextView(textView4,"过关奖金:"+game.getRqSpfJiangJin());
			setTextView(textView5,"胜平负:"+getSaiGuo(game.getSpfResult()));
			color=mContext.getResources().getColor(R.color.jcred);
			setTextView(textView6,"过关奖金:"+game.getSpfJiangJin());
			color=Color.BLACK;
			setTextView(textView7,"总进球:"+game.getZjqResult());
			color=mContext.getResources().getColor(R.color.jcred);
			setTextView(textView8,"过关奖金:"+game.getZjqJiangJin());
		}
		private void  setTextView(TextView tv,String  strs){
			int index=strs.indexOf(":");
			SpannableStringBuilder style=new SpannableStringBuilder(strs);   
			style.setSpan(new ForegroundColorSpan(color),index,strs.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
			tv.setText(style);
		}
		private  String  getSaiGuo(String s){
			if(s.equals("3")){
				color=mContext.getResources().getColor(R.color.jcred);
				return "胜";
			}else if(s.equals("1")){
				color=mContext.getResources().getColor(R.color.myblue);
				return "平";
			}else {
				color=mContext.getResources().getColor(R.color.green);
				return "负";	
			}
		}
	}
	
}
