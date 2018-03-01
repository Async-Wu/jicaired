/**
 * Create on 2012-10-12
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

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  FootballAdapterShengpingfu extends FootballAdapterBase {

	public static final String PAD = "   ";
	private int wfIndex = -1;

	public int getWfIndex() {
		return wfIndex;
	}

	public void setWfIndex(int wfIndex) {
		this.wfIndex = wfIndex;
	}

	/**
	 * @param mActivity
	 * @param
	 */
	public FootballAdapterShengpingfu(Activity mActivity,
			SparseArray<JingcaizuqiuOneGame> gameList, OnGameTouchCallback callback) {
		super(mActivity, gameList, callback);
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		ChildViewHolder holder = null;
		if (convertView == null) {
			holder = new ChildViewHolder();
			convertView = buildChildView(groupPosition, parent);
			convertView.setTag(holder);
			holder.loadViews(convertView);
		} else {
			holder = (ChildViewHolder) convertView.getTag();
		}
		// 给item中的控件填充数据
		final JingcaizuqiuOneGame oneGame = getOneGame(groupPosition,
				childPosition);
		final int key = oneGame.orderIdLocal;
		holder.fill(groupPosition, childPosition, oneGame);
		// 给胜平负按钮绑定事件监听器
		for (int t = 0; t < 3; t++) {
			holder.resultLayout[t].setSelected(oneGame.isSelected[t]);
			holder.resultLayout[t]
					.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							TextView b = (TextView) v;
							// 改变按钮的背景颜色
							b.setSelected(!b.isSelected());
							boolean isSelected = b.isSelected();
							int flag = oneGame.getSpfFlag();

							// 改变flag的值同时判断把当前操作的比赛数据加到选择列表中
							if (isSelected) {

								flag++;

								if (flag == 0) {

									// 最多添加15场比赛
									if (gameList.size() == 15) {

										callback.onGameTouched(true);
										b.setSelected(false);
										flag--;
										oneGame.setSpfFlag(flag);
										return;
									} else {
										// 把当前操作的比赛数据加到选择列表
										gameList.put(oneGame.orderIdLocal, oneGame);
										callback.onGameTouched(false);

									}
								}
							} else {

								flag--;

								if (flag == -1) {
									// 把当前操作的比赛数据移出选择列表
									if (gameList.indexOfKey(key)>=0){
										gameList.remove(key);
									}
									callback.onGameTouched(false);

								}
							}
							// 改变JingcaizuqiuOneGame中对应数据数据值
							switch (v.getId()) {
							case R.id.btn1:
								oneGame.isSelected[0] = isSelected;
								break;
							case R.id.btn2:
								oneGame.isSelected[1] = isSelected;
								break;
							case R.id.btn3:
								oneGame.isSelected[2] = isSelected;
								break;
							}
							oneGame.setSpfFlag(flag);
						}
					});
			
		}

		return convertView;
	}

	private LinearLayout buildChildView(int groupPosition, ViewGroup parent) {
		return (LinearLayout) inflater.inflate(R.layout.fragment_lottery_football_listview_spf, parent,false);
	}

	class ChildViewHolder {
		DecimalFormat df = new DecimalFormat("0.00");
		// 联赛名称
		TextView lianSaiName;
		// 比赛时间
		TextView time;
		// 让球数
		TextView rangQiu;
		// 主队
		TextView zhuDui;
		// 客队
		TextView keDui;
		TextView[] resultLayout = new TextView[3];
		// 赔率
		TextView[] spValue = new TextView[3];
		LinearLayout leftlayout, layoutbottom;
		TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10,
				txt11;

		TextView codeText;

		public void loadViews(View convertView) {
			lianSaiName = (TextView) convertView.findViewById(R.id.liansainame);
			time = (TextView) convertView.findViewById(R.id.time);
			rangQiu = (TextView) convertView.findViewById(R.id.rangqiu);
			zhuDui = (TextView) convertView.findViewById(R.id.duiwu1);
			keDui = (TextView) convertView.findViewById(R.id.duiwu2);
			spValue[0] = (TextView) convertView.findViewById(R.id.btn1);
			spValue[1] = (TextView) convertView.findViewById(R.id.btn2);
			spValue[2] = (TextView) convertView.findViewById(R.id.btn3);
			resultLayout[0] = (TextView) convertView
					.findViewById(R.id.btn1);
			resultLayout[1] = (TextView) convertView
					.findViewById(R.id.btn2);
			resultLayout[2] = (TextView) convertView
					.findViewById(R.id.btn3);

			leftlayout = (LinearLayout) convertView
					.findViewById(R.id.leftlayout);
			layoutbottom = (LinearLayout) convertView
					.findViewById(R.id.layoutbottom);
			txt1 = (TextView) convertView.findViewById(R.id.txt1);
			txt2 = (TextView) convertView.findViewById(R.id.txt2);
			txt3 = (TextView) convertView.findViewById(R.id.txt3);
			// txt4=(TextView) convertView.findViewById(R.id.txt4);
			// txt5=(TextView) convertView.findViewById(R.id.txt5);
			txt6 = (TextView) convertView.findViewById(R.id.txt6);
			txt7 = (TextView) convertView.findViewById(R.id.txt7);
			txt8 = (TextView) convertView.findViewById(R.id.txt8);
			txt9 = (TextView) convertView.findViewById(R.id.txt9);
			txt10 = (TextView) convertView.findViewById(R.id.txt10);
			txt11 = (TextView) convertView.findViewById(R.id.txt11);

			codeText = (TextView) convertView.findViewById(R.id.code);
		}

		public void fill(final int groupPosition, final int childPosition,
				final JingcaizuqiuOneGame game) {

			// 设置联赛名称
			lianSaiName.setText(game.getGameName());
			// 设置比赛时间
			time.setText(game.getTime() + "截止");
			// 设置比赛队伍
			if(wfIndex == 0){
			zhuDui.setText(game.getTeam1());
			}else{
				if (game.getRangNumber() <= 0){
					zhuDui.setText(game.getTeam1()+" "+String.valueOf(game.getRangNumber()));
				}else{
					zhuDui.setText(game.getTeam1()+" +" + String.valueOf(game.getRangNumber()));
				}
				
			}
			keDui.setText(game.getTeam2());
			codeText.setText(game.getMatchCode().substring(8));
			double[] peilv;
			// 设置让球
			if (wfIndex == 0) {
				rangQiu.setText("VS");
				peilv = game.getSpfpeilv();
			} else {
				peilv = game.getRqSpfPeilv();
				rangQiu.setText("VS");
//				if (game.getRangNumber() <= 0){
//					rangQiu.setText(String.valueOf(game.getRangNumber()));
//				}else{
//					rangQiu.setText("+" + String.valueOf(game.getRangNumber()));
//				}
			}
			// 胜平负按钮的值
			spValue[0].setText("主胜 " + df.format((peilv[0])));
			spValue[1].setText("平 " + df.format(peilv[1]));
			spValue[2].setText("主负 " + df.format(peilv[2]));
			if (game.getHostRank() > 0)
				txt1.setText(game.getHostRank() + "");
			if (game.getVisitRank() > 0)
				txt2.setText(game.getVisitRank() + "");
			if (game.getOdds()[0] > 0)
				txt6.setText(game.getOdds()[0] + "");
			if (game.getOdds()[1] > 0)
				txt7.setText(game.getOdds()[1] + "");
			if (game.getOdds()[2] > 0)
				txt8.setText(game.getOdds()[2] + "");
		}
	}

}
