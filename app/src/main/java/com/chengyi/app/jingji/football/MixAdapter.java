package com.chengyi.app.jingji.football;

import android.app.Activity;
import android.content.Intent;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.chengyi.R;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.util.CaipiaoConst;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishangfan on 2016/11/7.
 */
public class MixAdapter extends   FootballAdapterBase{
    Button btn;
    boolean isSelected;
    int flag;// /判断标志
    int wfindex;

    public int getWfindex() {
        return wfindex;
    }

    public void setWfindex(int wfindex) {
        this.wfindex = wfindex;
    }

    public MixAdapter(Activity mActivity,  SparseArray<JingcaizuqiuOneGame> gameList, FootBall.OnGameTouchCallback handler) {
        super(mActivity, gameList, handler);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();

                convertView = inflater.inflate(R.layout.football_mix, parent,false);

            convertView.setTag(holder);
            holder.loadViews(convertView);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        // 给item中的控件填充数据
        final JingcaizuqiuOneGame oneGame = getOneGame(groupPosition,    childPosition);
        final int key = oneGame.orderIdLocal;

        final String position = groupPosition + "#" + childPosition;
        // 设置监听器
        // 给胜平负按钮绑定事件监听器
        holder.fill(oneGame);
        for (int t = 0; t < holder.btnList.size(); t++) {
            holder.btnList.get(t).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btn = (Button) v;
                            if (btn.getText().toString().trim().equals("-")){
                                Toast.makeText(mActivity, "不能购买", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // 改变按钮的背景颜色
                            btn.setSelected(!btn.isSelected());
                            isSelected = btn.isSelected();
                            flag = oneGame.getSpfFlag();
                            if (isSelected) {
                                flag++;
                                if (flag == 0) {
                                    // 最多添加15场比赛
                                    if (gameList.size() == 15) {
                                        callback.onGameTouched(true);
                                        btn.setSelected(false);
                                        flag--;
                                        oneGame.setSpfFlag(flag);
                                        return;
                                    } else {
                                        // 把当前操作的比赛数据加到选择列表
                                        gameList.put(key, oneGame);
                                        callback.onGameTouched(false);
                                    }
                                }
                            } else {
                                flag--;
                                if (flag == -1) {
                                    // 把当前操作的比赛数据移出选择列表
                                    if (gameList.indexOfKey(key) >= 0) {
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
                                case R.id.btn4:
                                    oneGame.isSelected[3] = isSelected;
                                    break;
                                case R.id.btn5:
                                    oneGame.isSelected[4] = isSelected;
                                    break;
                                case R.id.btn6:
                                    oneGame.isSelected[5] = isSelected;
                                    break;
                            }

                            oneGame.setSpfFlag(flag);
                            if ((oneGame.getMixHadSelect())>0) {
                                holder.chosedtext.setText("已选\n"+oneGame.getMixHadSelect()+"场");
                                holder.chosedtext.setSelected(true);
                            } else {

                                holder. chosedtext.setText("更多\n玩法");
                                holder.chosedtext.setSelected(false);
                            }

                        }

                    });


            holder.btnList.get(t).setSelected(oneGame.isSelected[t]);
        }

            holder.chosedtext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, MixSubActivity.class);
                    intent.putExtra("gameData", oneGame);
                    intent.putExtra("wfindex", wfindex);
                    intent.putExtra("location", position);
                    mActivity.startActivityForResult(intent, CaipiaoConst.MIX);
                }
            });




        return convertView;
    }

    class ChildViewHolder {
        DecimalFormat df = new DecimalFormat("0.00");
        // 联赛名称
        TextView lianSaiName;
        // 比赛编号
        TextView matchCode;
        // 比赛时间
        TextView time;
        // 让球数
        TextView rangQiu;
        // 主队
        TextView zhuDui;
        // 客队
        TextView keDui;
        List<Button> btnList = new ArrayList<Button>();


        TextView txt1, txt2, txt3, txt6, txt7, txt8, chosedtext;

        public void loadViews(View convertView) {
            lianSaiName = (TextView) convertView.findViewById(R.id.liansainame);
            matchCode = (TextView) convertView.findViewById(R.id.code);
            time = (TextView) convertView.findViewById(R.id.time);
            zhuDui = (TextView) convertView.findViewById(R.id.duiwu1);
            keDui = (TextView) convertView.findViewById(R.id.duiwu2);



                rangQiu = (TextView) convertView.findViewById(R.id.rqtext);
                btnList.add((Button) convertView.findViewById(R.id.btn1));
                btnList.add((Button) convertView.findViewById(R.id.btn2));
                btnList.add((Button) convertView.findViewById(R.id.btn3));
                btnList.add((Button) convertView.findViewById(R.id.btn4));
                btnList.add((Button) convertView.findViewById(R.id.btn5));
                btnList.add((Button) convertView.findViewById(R.id.btn6));
                chosedtext = (TextView) convertView.findViewById(R.id.chosedtext);



            txt1 = (TextView) convertView.findViewById(R.id.txt1);
            txt2 = (TextView) convertView.findViewById(R.id.txt2);
            txt3 = (TextView) convertView.findViewById(R.id.txt3);
            txt6 = (TextView) convertView.findViewById(R.id.txt6);
            txt7 = (TextView) convertView.findViewById(R.id.txt7);
            txt8 = (TextView) convertView.findViewById(R.id.txt8);
        }

        public void fill(JingcaizuqiuOneGame game) {
            // 设置联赛名称
            lianSaiName.setText(game.getGameName());
            // 设置比赛时间
            time.setText( "截止"+game.getTime() );
            // 设置比赛队伍
            zhuDui.setText(game.getTeam1());
            keDui.setText(game.getTeam2());
            matchCode.setText(game.getMatchCode().substring(8));

                // 设置让球
                if (game.getRangNumber() <= 0)
                    rangQiu.setText(String.valueOf(game.getRangNumber()).trim());
                else
                    rangQiu.setText("+"
                            + String.valueOf(game.getRangNumber()).trim());
                double[] peilv;
                peilv = game.getSpfpeilv();


                if (type == 0) {

                    if (game.getSpfPassStatus() == 0) {
                        btnList.get(0).setText("胜 " + df.format((peilv[0])));
                        btnList.get(1).setText("平 " + df.format((peilv[1])));
                        btnList.get(2).setText("负  " + df.format((peilv[2])));
                    } else {
                        btnList.get(0).setText("- ");
                        btnList.get(1).setText("- ");
                        btnList.get(2).setText("-  ");
                    }


                    if (game.getRqspfPassStatus() == 0) {
                        peilv = game.getRqSpfPeilv();
                        btnList.get(3).setText("胜 " + df.format((peilv[0])));
                        btnList.get(4).setText("平 " + df.format((peilv[1])));
                        btnList.get(5).setText("负  " + df.format((peilv[2])));
                    } else {
                        peilv = game.getRqSpfPeilv();
                        btnList.get(3).setText("- ");
                        btnList.get(4).setText("- ");
                        btnList.get(5).setText("-  ");
                    }


                } else if (type == 1) {

                    if (game.getSpfSingleStatus() == 0) {
                        btnList.get(0).setText("胜 " + df.format((peilv[0])));
                        btnList.get(1).setText("平 " + df.format((peilv[1])));
                        btnList.get(2).setText("负  " + df.format((peilv[2])));
                    } else {
                        btnList.get(0).setText("- ");
                        btnList.get(1).setText("- ");
                        btnList.get(2).setText("-  ");
                    }


                    if (game.getRqspfSingleStatus() == 0) {
                        peilv = game.getRqSpfPeilv();
                        btnList.get(3).setText("胜 " + df.format((peilv[0])));
                        btnList.get(4).setText("平 " + df.format((peilv[1])));
                        btnList.get(5).setText("负  " + df.format((peilv[2])));
                    } else {
                        peilv = game.getRqSpfPeilv();
                        btnList.get(3).setText("- ");
                        btnList.get(4).setText("- ");
                        btnList.get(5).setText("-  ");
                    }


                }




            if ((game.getMixHadSelect())>0) {
                    chosedtext.setText("已选\n"+game.getMixHadSelect()+"场");
                    chosedtext.setSelected(true);
                } else {
                    chosedtext.setText("更多\n玩法");
                    chosedtext.setSelected(false);
                }

            if (game.getHostRank() > 0) {
                txt1.setText(game.getHostRank() + "");
            }
            if (game.getVisitRank() > 0) {
                txt2.setText(game.getVisitRank() + "");
            }

            if (game.getOdds()[0] > 0) {
                txt6.setText(game.getOdds()[0] + "");
            }
            if (game.getOdds()[1] > 0) {
                txt7.setText(game.getOdds()[1] + "");
            }
            if (game.getOdds()[2] > 0) {
                txt8.setText(game.getOdds()[2] + "");
            }
        }
    }



    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
