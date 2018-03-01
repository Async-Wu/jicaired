package com.chengyi.app.jingji.football;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.view.widget.OnJingcaizuqiuBtnListener;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class ShengpingfuTZAdapter extends JingcaiZuqiuTZquerenAdapter {

    public ShengpingfuTZAdapter(Activity mActivity,
                                List<JingcaizuqiuOneGame> list, OnJingcaizuqiuBtnListener listener) {
        super(mActivity, list, listener);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder ;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = buildChildView(R.layout.fragment_lottery_football_cart_item_spf);
            holder.loadViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final JingcaizuqiuOneGame game = list.get(position);
        holder.fillView(game);
        holder.dan.setTag(position);
        holder.dan.setOnClickListener(this);
        holder.dan.setSelected(game.isDanTuo());

        holder.ivDelete.setTag(position);
        holder.ivDelete.setOnClickListener(this);
        holder.ivDelete.setSelected(game.isDanTuo());


//        // 给胜平负按钮绑定事件监听器
//        for (int t = 0; t < 3; t++) {
//            int id = t;
////            holder.layout[t].setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    // /如果当前所在模式是删除模式
////                    if (true) return;
////                    if (deleteMode) {
////                        deleteMode = false;
////                        ShengpingfuTZAdapter.this.notifyDataSetChanged();
////                        listener.reSetData();
////                        return;
////                    }
////                    LinearLayout b = (LinearLayout) v;
////                    // 改变按钮的背景颜色
////                    b.setSelected(!b.isSelected());
////                    boolean isSelected = b.isSelected();
////                    int flag = game.getSpfFlag();
////                    // 改变JingcaizuqiuOneGame中对应数据数据值
////                    switch (v.getId()) {
////                        case R.id.listview_spf_layout_win:
////                            game.isSelected[0] = isSelected;
////                            break;
////                        case R.id.listview_spf_layout_tie:
////                            game.isSelected[1] = isSelected;
////                            break;
////                        case R.id.listview_spf_layout_lose:
////                            game.isSelected[2] = isSelected;
////                            break;
////                    }
////                    if (isSelected) {
////                        flag++;
////                        game.setSpfFlag(flag);
////                        if (flag == 0) {
////
////                            listener.buttonChangeUI(game);
////                        } else {
////
////                            listener.changeZhuNumUI();
////                        }
////                    } else {
////                        flag--;
////                        game.setSpfFlag(flag);
////                        if (flag == -1) {
////
////                            listener.buttonChangeUI(game);
////                        } else {
////
////                            listener.changeZhuNumUI();
////                        }
////                    }
////                }
////            });
//            holder.layout[t].setSelected(game.isSelected[t]);
//        }
        return convertView;
    }

    class ViewHolder {
        DecimalFormat df = new DecimalFormat("0.00");
        TextView zhuDui;
        TextView keDui;
        TextView rangQiu;
        TextView[] layout = new TextView[3];
        TextView[] spValue = new TextView[3];
        Button dan;
        ImageView ivDelete;


        public void loadViews(View convertView) {
            zhuDui = (TextView) convertView.findViewById(R.id.duiwu1);
            keDui = (TextView) convertView.findViewById(R.id.duiwu2);
            rangQiu = (TextView) convertView.findViewById(R.id.rangqiu);

            spValue[0] = (TextView) convertView.findViewById(R.id.btn1);
            spValue[1] = (TextView) convertView.findViewById(R.id.btn2);
            spValue[2] = (TextView) convertView.findViewById(R.id.btn3);

            layout[0] = (TextView) convertView
                    .findViewById(R.id.btn1);
            layout[1] = (TextView) convertView
                    .findViewById(R.id.btn2);
            layout[2] = (TextView) convertView
                    .findViewById(R.id.btn3);
            dan = (Button) convertView.findViewById(R.id.danbtn);
            ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
        }

        public void fillView(JingcaizuqiuOneGame game) {
            double[] spf;
            zhuDui.setText(game.getTeam1());
            keDui.setText(game.getTeam2());

            if (wfIndex == 0) {
                rangQiu.setText("VS");
                spf = game.getSpfpeilv();
                spValue[0].setText("主胜 " + df.format(spf[0]));


            } else {
                if (game.getRangNumber() > 0) {
                    spf = game.getRqSpfPeilv();
                    spValue[0].setText("主胜 " + df.format(spf[0]) + " +" + String.valueOf(game.getRangNumber()));

//					rangQiu.setText();
                } else {
//					rangQiu.setText();
                    spf = game.getRqSpfPeilv();
                    spValue[0].setText("主胜 " + df.format(spf[0]) + " " + String.valueOf(game.getRangNumber()));
                }

            }

            spValue[1].setText("平 " + df.format(spf[1]));
            spValue[2].setText("主负 " + df.format(spf[2]));
            layout[0].setSelected(game.isSelected[0]);
            layout[1].setSelected(game.isSelected[1]);
            layout[2].setSelected(game.isSelected[2]);
            LayoutParams params = dan.getLayoutParams();
            if (deleteMode) {
                ivDelete.setVisibility(View.VISIBLE);


//
//				params.width = LayoutParams.WRAP_CONTENT;
//				dan.setLayoutParams(params);
//				dan.setVisibility(View.VISIBLE);
//				dan.setText("");
//				dan.setBackgroundResource(R.drawable.ic_delete);
//			} else {
//				// dan.setVisibility(game.getDanVisible());
//				if (game.getDanVisible() == View.INVISIBLE) {
//					if (danNumSelected == 0) {
//						params.width = 0;
//						dan.setLayoutParams(params);
//					} else {
//						params.width = LayoutParams.WRAP_CONTENT;
//						dan.setLayoutParams(params);
//						dan.setText("胆");
//						dan.setBackgroundResource(R.drawable.selector_football_green);
//					}
//				} else {
//					params.width = LayoutParams.WRAP_CONTENT;
//					dan.setLayoutParams(params);
//					dan.setText("胆");
//					dan.setBackgroundResource(R.drawable.selector_football_green);
//				}
//				dan.setVisibility(game.getDanVisible());
//			}
            }else {
                ivDelete.setVisibility(View.INVISIBLE);
            }
        }
    }
}
