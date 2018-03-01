package com.chengyi.app.jingji.football;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.view.widget.OnJingcaizuqiuBtnListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  ZongjinqiuTZAdapter extends JingcaiZuqiuTZquerenAdapter {

	public ZongjinqiuTZAdapter(Activity mActivity,
			List<JingcaizuqiuOneGame> list, OnJingcaizuqiuBtnListener listener) {
		super(mActivity, list, listener);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = buildChildView(R.layout.fragment_lottery_football_cart_item_totalgoals);
			holder.loadViews(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final JingcaizuqiuOneGame game = list.get(position);
		holder.fillView(game);
//		holder.dan.setTag(position);
//		holder.dan.setOnClickListener(this);
//		holder.dan.setSelected(game.isDanTuo());

		holder.ivDelete.setTag(position);
		holder.ivDelete.setOnClickListener(this);
		holder.ivDelete.setSelected(game.isDanTuo());


		for (int i = 0; i < holder.linList.size(); i++) {
			holder.linList.get(i).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							if (true)return;
							// /如果当前所在模式是删除模式
							if (deleteMode) {
								deleteMode = false;
								ZongjinqiuTZAdapter.this.notifyDataSetChanged();
								listener.reSetData();
								return;
							}
							LinearLayout b = (LinearLayout) v;
							// 改变按钮的背景颜色
							b.setSelected(!b.isSelected());
							boolean state = b.isSelected();
							int flag = game.getSpfFlag();
							// 改变JingcaizuqiuOneGame中对应数据数据值
							switch (v.getId()) {
							case R.id.ln1:
								game.isSelected[0] = state;
								break;
							case R.id.ln2:
								game.isSelected[1] = state;
								break;
							case R.id.ln3:
								game.isSelected[2] = state;
								break;
							case R.id.ln4:
								game.isSelected[3] = state;
								break;
							case R.id.ln5:
								game.isSelected[4] = state;
								break;
							case R.id.ln6:
								game.isSelected[5] = state;
								break;
							case R.id.ln7:
								game.isSelected[6] = state;
								break;
							case R.id.ln8:
								game.isSelected[7] = state;
								break;
							}
							if (state) {
								flag++;
								game.setSpfFlag(flag);
								if (flag == 0) {
									listener.buttonChangeUI(game);
								} else {
									listener.changeZhuNumUI();
								}
							} else {
								flag--;
								game.setSpfFlag(flag);
								if (flag == -1) {
									listener.buttonChangeUI(game);
								} else {
									listener.changeZhuNumUI();
								}
							}
						}
					});
			holder.linList.get(i).setSelected(game.isSelected[i]);
		}
		return convertView;
	}

	class ViewHolder {
		DecimalFormat df = new DecimalFormat("0.00");
		TextView zhuDui;
		TextView keDui;
		TextView time;
		TextView[] buts = new TextView[8];
		Button dan;
		ImageView ivDelete;
		TextView index;
		List<LinearLayout> linList = new ArrayList<LinearLayout>();

		public void loadViews(View convertView) {
			zhuDui = (TextView) convertView.findViewById(R.id.duiwu1);
			keDui = (TextView) convertView.findViewById(R.id.duiwu2);
			buts[0] = (TextView) convertView.findViewById(R.id.btn0);
			buts[1] = (TextView) convertView.findViewById(R.id.btn1);
			buts[2] = (TextView) convertView.findViewById(R.id.btn2);
			buts[3] = (TextView) convertView.findViewById(R.id.btn3);
			buts[4] = (TextView) convertView.findViewById(R.id.btn4);
			buts[5] = (TextView) convertView.findViewById(R.id.btn5);
			buts[6] = (TextView) convertView.findViewById(R.id.btn6);
			buts[7] = (TextView) convertView.findViewById(R.id.btn7);
			dan = (Button) convertView.findViewById(R.id.danbtn);
			ivDelete= (ImageView) convertView.findViewById(R.id.iv_delete);
			linList.add((LinearLayout) convertView.findViewById(R.id.ln1));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln2));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln3));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln4));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln5));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln6));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln7));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln8));
		}

		public void fillView(JingcaizuqiuOneGame game) {
			zhuDui.setText(game.getTeam1());
			keDui.setText(game.getTeam2());
			double[] spf = game.getZjqpeilv();
			buts[0].setText(df.format((spf[0])));
			buts[1].setText(df.format(spf[1]));
			buts[2].setText(df.format(spf[2]));
			buts[3].setText(df.format(spf[3]));
			buts[4].setText(df.format(spf[4]));
			buts[5].setText(df.format(spf[5]));
			buts[6].setText(df.format(spf[6]));
			buts[7].setText(df.format(spf[7]));
			buts[0].setSelected(game.isSelected[0]);
			buts[1].setSelected(game.isSelected[1]);
			buts[2].setSelected(game.isSelected[2]);
			buts[3].setSelected(game.isSelected[3]);
			buts[4].setSelected(game.isSelected[4]);
			buts[5].setSelected(game.isSelected[5]);
			buts[6].setSelected(game.isSelected[6]);
			buts[7].setSelected(game.isSelected[7]);
			if (deleteMode) {
				ivDelete.setVisibility(View.VISIBLE);
//				dan.setVisibility(View.VISIBLE);
//				dan.setText("");
//				dan.setBackgroundResource(R.drawable.ic_delete);
			} else {
//				dan.setText("胆");
//				dan.setBackgroundResource(R.drawable.selector_football_green);
//				dan.setVisibility(game.getDanVisible());
				ivDelete.setVisibility(View.INVISIBLE);
			}
		}
	}
}
