package com.chengyi.app.jingji.football;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
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
public class HunHeJingCaiEnsureAdapter extends JingcaiZuqiuTZquerenAdapter {

    private int wfIndex = 3;

    public int getWfIndex() {
        return wfIndex;
    }

    public void setWfIndex(int wfIndex) {
        this.wfIndex = wfIndex;
    }

    public HunHeJingCaiEnsureAdapter(Activity mActivity,
                                     List<JingcaizuqiuOneGame> list, OnJingcaizuqiuBtnListener listener) {
        super(mActivity, list, listener);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            if (wfIndex == 3)
                convertView = buildChildView(R.layout.fragment_lottery_football_cart_item_mix);
            else
                convertView = buildChildView(R.layout.fragment_lottery_football_cart_item_bifen);
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
        holder.ivDelete.setOnClickListener(this);
        holder.ivDelete.setTag(position);
        holder.ivDelete.setSelected(game.isDanTuo());

        return convertView;
    }

    class ViewHolder {
        DecimalFormat df = new DecimalFormat("0.00");
        TextView zhuDui;
        TextView keDui;
        TextView rangQiu;
        Button[] buts = new Button[6];
        Button dan;
        ImageView ivDelete;
        TextView chosedtext;

        public void loadViews(View convertView) {
            zhuDui = (TextView) convertView.findViewById(R.id.duiwu1);
            keDui = (TextView) convertView.findViewById(R.id.duiwu2);
            ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
            if (wfIndex == 3) {
                rangQiu = (TextView) convertView.findViewById(R.id.rqtext);
                buts[0] = (Button) convertView.findViewById(R.id.btn1);
                buts[1] = (Button) convertView.findViewById(R.id.btn2);
                buts[2] = (Button) convertView.findViewById(R.id.btn3);
                buts[3] = (Button) convertView.findViewById(R.id.btn4);
                buts[4] = (Button) convertView.findViewById(R.id.btn5);
                buts[5] = (Button) convertView.findViewById(R.id.btn6);
            } else {
                chosedtext = (TextView) convertView.findViewById(R.id.chosedtext);
            }
            dan = (Button) convertView.findViewById(R.id.danbtn);
        }

        public void fillView(final JingcaizuqiuOneGame game) {
            zhuDui.setText(game.getTeam1());
            keDui.setText(game.getTeam2());
            if (wfIndex == 3) {
                if (game.getRangNumber() <= 0)
                    rangQiu.setText(String.valueOf(game.getRangNumber()).trim());
                else
                    rangQiu.setText("+" + String.valueOf(game.getRangNumber()).trim());
                double[] peilv;
                peilv = game.getSpfpeilv();
//					buts[0].setText("胜 " + df.format((peilv[0])));
//					buts[1].setText("平 " + df.format((peilv[1])));
//					buts[2].setText("负  " + df.format((peilv[2])));
//					peilv = game.getRqSpfPeilv();
//					buts[3].setText("胜 " + df.format((peilv[0])));
//					buts[4].setText("平 " + df.format((peilv[1])));
//					buts[5].setText("负  " + df.format((peilv[2])));


                if (type == 0) {

                    if (game.getSpfPassStatus() == 0) {
                        buts[0].setText("胜 " + df.format((peilv[0])));
                        buts[1].setText("平 " + df.format((peilv[1])));
                        buts[2].setText("负  " + df.format((peilv[2])));
                    } else {
                        buts[0].setText("- ");
                        buts[1].setText("- ");
                        buts[2].setText("-  ");
                    }


                    if (game.getRqspfPassStatus() == 0) {
                        peilv = game.getRqSpfPeilv();
                        buts[3].setText("胜 " + df.format((peilv[0])));
                        buts[4].setText("平 " + df.format((peilv[1])));
                        buts[5].setText("负  " + df.format((peilv[2])));
                    } else {
                        peilv = game.getRqSpfPeilv();
                        buts[3].setText("- ");
                        buts[4].setText("- ");
                        buts[5].setText("-  ");
                    }


                } else if (type == 1) {

                    if (game.getSpfSingleStatus() == 0) {
                        buts[0].setText("胜 " + df.format((peilv[0])));
                        buts[1].setText("平 " + df.format((peilv[1])));
                        buts[2].setText("负  " + df.format((peilv[2])));
                    } else {
                        buts[0].setText("- ");
                        buts[1].setText("- ");
                        buts[2].setText("-  ");
                    }


                    if (game.getRqspfSingleStatus() == 0) {
                        peilv = game.getRqSpfPeilv();
                        buts[3].setText("胜 " + df.format((peilv[0])));
                        buts[4].setText("平 " + df.format((peilv[1])));
                        buts[5].setText("负  " + df.format((peilv[2])));
                    } else {
                        peilv = game.getRqSpfPeilv();
                        buts[3].setText("- ");
                        buts[4].setText("- ");
                        buts[5].setText("-  ");
                    }


                }


                buts[0].setSelected(game.isSelected[0]);
                buts[1].setSelected(game.isSelected[1]);
                buts[2].setSelected(game.isSelected[2]);
                buts[3].setSelected(game.isSelected[3]);
                buts[4].setSelected(game.isSelected[4]);
                buts[5].setSelected(game.isSelected[5]);
            } else {
                chosedtext.setText(game.getSelContentStr().replace(",", "  "));
                chosedtext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mActivity, FootBallAdapter_mix_halfwhole_bifen_Selector.class);
                        intent.putExtra("gameData", game);
                        intent.putExtra("wfindex", wfIndex);
                        intent.putExtra("isclick", false);
                        mActivity.startActivityForResult(intent, 1);


                    }
                });
            }
            if (deleteMode) {
                ivDelete.setVisibility(View.VISIBLE);
//				dan.setVisibility(View.VISIBLE);
//				dan.setText("");
//				dan.setBackgroundResource(R.drawable.ic_delete);
//			} else {
//				ivDelete.setVisibility(View.GONE);
//				dan.setText("胆");
//				dan.setBackgroundResource(R.drawable.selector_football_green);
//				if(wfIndex!=3&&game.getDanVisible()==View.INVISIBLE&&danNumSelected==0)
//					game.setDanVisible(View.GONE);
//			}
            }else {
                ivDelete.setVisibility(View.INVISIBLE);
            }
        }
    }


}
