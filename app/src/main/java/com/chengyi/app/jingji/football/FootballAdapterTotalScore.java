/**
 * Create on 2012-10-15
 */
package com.chengyi.app.jingji.football;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.jingji.football.FootBall.OnGameTouchCallback;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  FootballAdapterTotalScore extends FootballAdapterBase {

	/**
	 * @param mActivity
	 * @param myHandler
	 * @param
	 */
	public FootballAdapterTotalScore(Activity mActivity,
			SparseArray<JingcaizuqiuOneGame> gameList, OnGameTouchCallback myHandler) {
		super(mActivity, gameList, myHandler);

	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder holder ;
		if (convertView == null) {
			holder = new ChildViewHolder();
			convertView = buildChildView(groupPosition, parent);
			holder.loadViews(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}
		final JingcaizuqiuOneGame game = getOneGame(groupPosition,	childPosition);
		final int key = game.orderIdLocal;
		holder.fillView(game);
		for (int i = 0; i < holder.linList.size(); i++) {
			holder.linList.get(i).setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View v) {

							LinearLayout b = (LinearLayout) v;
							b.setSelected(!b.isSelected());
							boolean state = b.isSelected();
							int flag = game.getSpfFlag();
							// 改变flag的值同时判断把当前操作的比赛数据加到选择列表中
							if (state) {
								flag++;
								// 将比赛添加在比赛列表中
								if (flag == 0) {
									// 最多添加15场比赛
									if (gameList.size() == 15) {
										callback.onGameTouched(true);
										b.setSelected(false);
										flag--;
										game.setSpfFlag(flag);
										return;
									} else {
										// 把当前操作的比赛数据加到选择列表
										gameList.put(key, game);
										callback.onGameTouched(false);
									}
								}
							} else {
								flag--;
								if (flag == -1) {
									// 把当前操作的比赛数据移出选择列表
									if (gameList.indexOfKey(key)>=0)
										gameList.remove(key);
									callback.onGameTouched(false);
								}
							}
							game.setSpfFlag(flag);
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
						}
					});
			holder.linList.get(i).setSelected(game.isSelected[i]);
		}
		final LinearLayout ln = holder.layoutbottom;


		
		return convertView;
	}

	private LinearLayout buildChildView(int groupPosition, ViewGroup parent) {
		return (LinearLayout) inflater.inflate(R.layout.fragment_lottery_football_listview_totalgoals,
				parent,false);
	}

	static class ChildViewHolder {
		DecimalFormat df = new DecimalFormat("0.00");
		TextView lianSaiName;
		TextView zhuDui;
		TextView keDui;
		TextView time;
		TextView[] buts = new TextView[8];
		List<LinearLayout> linList = new ArrayList<LinearLayout>();
		LinearLayout leftlayout, layoutbottom;
		TextView txt1, txt2, txt3, txt4, txt5;

		TextView  codeText;
		public void loadViews(View convertView) {
			lianSaiName = (TextView) convertView.findViewById(R.id.liansainame);
			zhuDui = (TextView) convertView.findViewById(R.id.duiwu1);
			keDui = (TextView) convertView.findViewById(R.id.duiwu2);
			time = (TextView) convertView.findViewById(R.id.time);
			// index = (TextView) convertView.findViewById(R.id.index);
			buts[0] = (TextView) convertView.findViewById(R.id.btn0);
			buts[1] = (TextView) convertView.findViewById(R.id.btn1);
			buts[2] = (TextView) convertView.findViewById(R.id.btn2);
			buts[3] = (TextView) convertView.findViewById(R.id.btn3);
			buts[4] = (TextView) convertView.findViewById(R.id.btn4);
			buts[5] = (TextView) convertView.findViewById(R.id.btn5);
			buts[6] = (TextView) convertView.findViewById(R.id.btn6);
			buts[7] = (TextView) convertView.findViewById(R.id.btn7);

			linList.add((LinearLayout) convertView.findViewById(R.id.ln1));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln2));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln3));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln4));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln5));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln6));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln7));
			linList.add((LinearLayout) convertView.findViewById(R.id.ln8));
			leftlayout = (LinearLayout) convertView
					.findViewById(R.id.leftlayout);
			layoutbottom = (LinearLayout) convertView
					.findViewById(R.id.layoutbottom);

			txt1 = (TextView) convertView.findViewById(R.id.txt1);
			txt2 = (TextView) convertView.findViewById(R.id.txt2);
			txt3 = (TextView) convertView.findViewById(R.id.txt3);
			// txt4=(TextView) convertView.findViewById(R.id.txt4);
			// txt5=(TextView) convertView.findViewById(R.id.txt5);

			codeText=(TextView) convertView.findViewById(R.id.code);
		}

		public void fillView(JingcaizuqiuOneGame game) {
			lianSaiName.setText(game.getGameName());
			zhuDui.setText(game.getTeam1());
			keDui.setText(game.getTeam2());
			// index.setText(game.getMatchCode().substring(8));
			time.setText(game.getTime() + "截止");
			double[] pl = game.getZjqpeilv();
			buts[0].setText(df.format(pl[0]));
			buts[1].setText(df.format(pl[1]));
			buts[2].setText(df.format(pl[2]));
			buts[3].setText(df.format(pl[3]));
			buts[4].setText(df.format(pl[4]));
			buts[5].setText(df.format(pl[5]));
			buts[6].setText(df.format(pl[6]));
			buts[7].setText(df.format(pl[7]));

			if (game.getHostRank() > 0)
				txt1.setText(game.getHostRank() + "");
			if (game.getVisitRank() > 0)
				txt2.setText(game.getVisitRank() + "");

			codeText.setText(game.getMatchCode().substring(8));
		}

	}
}
