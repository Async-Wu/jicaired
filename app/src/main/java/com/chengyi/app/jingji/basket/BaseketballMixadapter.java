package com.chengyi.app.jingji.basket;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.chengyi.R;
import com.chengyi.app.view.TiShiDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BaseketballMixadapter extends BaseBasketApdater {


    public BaseketballMixadapter(Context context, OnGamesTouchedCallback onGamesTouchedCallback) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.onGamesTouchedCallback = onGamesTouchedCallback;
        gameList = new ArrayList<>();
        gameOneDayArray = new SparseArray<>();
        gameOneDayArrayCount = new SparseIntArray();
        selectedGames = new SparseArray<>();
    }


    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(
                    R.layout.item_basketballmix, parent,false);
            holder = new ChildViewHolder();
            holder.shengfuzhu = (TextView) convertView.
                    findViewById(R.id.shengfuzhu);
            holder.rangfenzhu = (TextView) convertView.
                    findViewById(R.id.rangfenzhu);
            holder.zongfen = (TextView) convertView.
                    findViewById(R.id.zongfen);
            holder.dafen = (TextView) convertView.
                    findViewById(R.id.basket_textview_visit_bs);
            holder.xiaofen = (TextView) convertView.
                    findViewById(R.id.basket_textview_host_bs);
            holder.hostSp = (TextView) convertView
                    .findViewById(R.id.basket_textview_host_sp);
            holder.visitSp = (TextView) convertView
                    .findViewById(R.id.basket_textview_visit_sp);
            holder.hostrfSp = (TextView) convertView.findViewById(R.id.basket_textview_host_rf);
            holder.visitftSp = (TextView) convertView.findViewById(R.id.basket_textview_visit_rf);
            holder.league = (TextView) convertView
                    .findViewById(R.id.basket_textview_league);
            holder.gameCode = (TextView) convertView
                    .findViewById(R.id.basket_textview_code);
            holder.endTime = (TextView) convertView
                    .findViewById(R.id.basket_textview_time);
            holder.hostName = (TextView) convertView
                    .findViewById(R.id.basket_textview_host);
            holder.visitName = (TextView) convertView
                    .findViewById(R.id.basket_textview_visit);
            holder.sfcText = (TextView) convertView .findViewById(R.id.mix_right);

            holder.teamLayout = (LinearLayout) convertView.findViewById(R.id.basket_sfc_layout_team);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        final BasketballOneGame item = gameOneDayArray.get(groupPosition).get(
                childPosition);
        final int sfPassStatus = item.sfPassStatus;
        final int rfsfPassStatus = item.rfsfPassStatus;
        final int sfcPassStatus = item.sfcPassStatus;
        final int dxfPassStatus = item.dxfPassStatus;

        holder.hostSp.setText("主胜 " + item.sheng);
        holder.visitSp.setText("主负 " + item.fu);
        holder.hostrfSp.setText("主胜 " + item.rfsheng);
        holder.visitftSp.setText("主负 " + item.rffu);
        holder.dafen.setText("大分 " + item.d);
        holder.xiaofen.setText("小分 " + item.x);
        holder.rangfenzhu.setText("主" + item.rateGG);
        holder.zongfen.setText(item.basePoint + "");


        holder.league.setText(item.leagueName[0]);
        holder.gameCode.setText(item.matchCodeGG.substring(8));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(item.endTime);
        String date = new SimpleDateFormat("HH:mm").format(new java.util.Date(item.cp2yEndTime));

        holder.endTime.setText(date + "截止");
        holder.hostName.setText(item.hostName);
        holder.visitName.setText(item.guestName);

        // String num=item.id.trim();
        final int id = item.orderIdLocal;
        //胜负进行适配和点击操作；到354行
        if (selectedGames.indexOfKey(id) >= 0) {
            BasketballOneGame selectedItem = selectedGames.get(id);
            boolean[] isSelected = selectedItem.isSFSelected;
            holder.visitSp.setSelected(isSelected[1]);
            holder.hostSp.setSelected(isSelected[0]);
        } else {
            holder.visitSp.setSelected(false);
            holder.hostSp.setSelected(false);
        }
        //点击纪录胜负的状态好进行下一步适配
        holder.hostSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 存于已选列表
                if (sfPassStatus != 0) {

                    TiShiDialog tiShiDialog = new TiShiDialog(context);
                    tiShiDialog.show();
                    tiShiDialog.getTitle().setText("此比赛没有胜负投注");
                    v.setEnabled(false);
                } else {
                    if (selectedGames.size() == 15 && selectedGames.indexOfKey(id) < 0) {
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
            }

        });
        holder.visitSp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (sfPassStatus != 0) {

                    TiShiDialog tiShiDialog = new TiShiDialog(context);
                    tiShiDialog.show();
                    tiShiDialog.getTitle().setText("此比赛没有胜负投注");
                    v.setEnabled(false);
                } else {
                    if (selectedGames.size() == 15 && selectedGames.indexOfKey(id) < 0) {
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
            }

        });
        //让分胜负的操作  到450行
        if (selectedGames.indexOfKey(id) >= 0) {
            BasketballOneGame selectedItem = selectedGames.get(id);
            boolean[] isSelected = selectedItem.isRFSFSelecter;
            holder.visitftSp.setSelected(isSelected[1]);
            holder.hostrfSp.setSelected(isSelected[0]);
        } else {
            holder.visitftSp.setSelected(false);
            holder.hostrfSp.setSelected(false);
        }
        //点击纪录胜负的状态好进行下一步适配
        holder.hostrfSp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 存于已选列表
                if (rfsfPassStatus != 0) {

                    TiShiDialog tiShiDialog = new TiShiDialog(context);
                    tiShiDialog.show();
                    tiShiDialog.getTitle().setText("此比赛没有让分胜负投注");
                    v.setEnabled(false);

                } else {
                    if (selectedGames.size() == 15 && selectedGames.indexOfKey(id) < 0) {
                        Toast.makeText(context, "最多可选15场比赛", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (selectedGames.indexOfKey(id) >= 0) {
                        BasketballOneGame selectedItem = selectedGames.get(id);
                        boolean[] isSelected = selectedItem.isRFSFSelecter;
                        if (isSelected[0]) {
                            selectedItem.isRFSFSelecter[0] = false;
                            holder.hostrfSp.setSelected(false);
                            if (selectedItem.SFFlag != -1) {
                                selectedItem.SFFlag--;
                            }
                            if (!selectedItem.isRFSFSelecter[1]) {
                                selectedGames.delete(id);
                            }

                        } else {
                            selectedItem.isRFSFSelecter[0] = true;
                            if (selectedItem.SFFlag != 1) {
                                selectedItem.SFFlag++;
                            }
                            holder.hostrfSp.setSelected(true);
                        }

                    } else {
                        item.isRFSFSelecter[0] = true;
                        if (item.SFFlag != 1) {
                            item.SFFlag++;
                        }
                        selectedGames.put(id, item);
                        holder.hostrfSp.setSelected(true);

                    }
                    onGamesTouchedCallback.onTouched();
                }
            }
        });
        holder.visitftSp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (rfsfPassStatus != 0) {

                    TiShiDialog tiShiDialog = new TiShiDialog(context);
                    tiShiDialog.show();
                    tiShiDialog.getTitle().setText("此比赛没有让分胜负投注");
                    v.setEnabled(false);

                } else {
                    if (selectedGames.size() == 15 && selectedGames.indexOfKey(id) < 0) {
                        Toast.makeText(context, "最多可选15场比赛", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (selectedGames.indexOfKey(id) >= 0) {
                        BasketballOneGame selectedItem = selectedGames.get(id);
                        boolean[] isSelected = selectedItem.isRFSFSelecter;
                        if (isSelected[1]) {
                            selectedItem.isRFSFSelecter[1] = false;
                            holder.visitftSp.setSelected(false);
                            if (selectedItem.SFFlag != -1) {
                                selectedItem.SFFlag--;
                            }
                            if (!selectedItem.isRFSFSelecter[0]) {
                                selectedGames.delete(id);
                            }
                        } else {
                            selectedItem.isRFSFSelecter[1] = true;
                            if (selectedItem.SFFlag != 1) {
                                selectedItem.SFFlag++;
                            }
                            holder.visitftSp.setSelected(true);
                        }

                    } else {
                        item.isRFSFSelecter[1] = true;
                        if (item.SFFlag != 1) {
                            item.SFFlag++;
                        }
                        selectedGames.put(id, item);
                        holder.visitftSp.setSelected(true);

                    }
                    onGamesTouchedCallback.onTouched();
                }
            }
        });
        //大小分
        if (selectedGames.indexOfKey(id) >= 0) {
            BasketballOneGame selectedItem = selectedGames.get(id);
            boolean[] isSelected = selectedItem.isBigSmallSelect;
            holder.dafen.setSelected(isSelected[1]);
            holder.xiaofen.setSelected(isSelected[0]);
        } else {
            holder.dafen.setSelected(false);
            holder.xiaofen.setSelected(false);
        }
        //点击纪录胜负的状态好进行下一步适配
        holder.xiaofen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 存于已选列表
                if (dxfPassStatus != 0) {

                    TiShiDialog tiShiDialog = new TiShiDialog(context);
                    tiShiDialog.show();
                    tiShiDialog.getTitle().setText("此比赛没有大小分投注");
                    v.setEnabled(false);

                } else {
                    if (selectedGames.size() == 15 && selectedGames.indexOfKey(id) < 0) {
                        Toast.makeText(context, "最多可选15场比赛", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (selectedGames.indexOfKey(id) >= 0) {
                        BasketballOneGame selectedItem = selectedGames.get(id);
                        boolean[] isSelected = selectedItem.isBigSmallSelect;
                        if (isSelected[0]) {
                            selectedItem.isBigSmallSelect[0] = false;
                            holder.xiaofen.setSelected(false);
                            if (selectedItem.SFFlag != -1) {
                                selectedItem.SFFlag--;
                            }
                            if (!selectedItem.isBigSmallSelect[1]) {
                                selectedGames.delete(id);
                            }

                        } else {
                            selectedItem.isBigSmallSelect[0] = true;
                            if (selectedItem.SFFlag != 1) {
                                selectedItem.SFFlag++;
                            }
                            holder.xiaofen.setSelected(true);
                        }

                    } else {
                        item.isBigSmallSelect[0] = true;
                        if (item.SFFlag != 1) {
                            item.SFFlag++;
                        }
                        selectedGames.put(id, item);
                        holder.xiaofen.setSelected(true);

                    }
                    onGamesTouchedCallback.onTouched();
                }
            }
        });
        holder.dafen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (dxfPassStatus != 0) {

                    TiShiDialog tiShiDialog = new TiShiDialog(context);
                    tiShiDialog.show();
                    tiShiDialog.getTitle().setText("此比赛没有大小分投注");
                    v.setEnabled(false);

                } else {
                    if (selectedGames.size() == 15 && selectedGames.indexOfKey(id) < 0) {
                        Toast.makeText(context, "最多可选15场比赛", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (selectedGames.indexOfKey(id) >= 0) {
                        BasketballOneGame selectedItem = selectedGames.get(id);
                        boolean[] isSelected = selectedItem.isBigSmallSelect;
                        if (isSelected[1]) {
                            selectedItem.isBigSmallSelect[1] = false;
                            holder.dafen.setSelected(false);
                            if (selectedItem.SFFlag != -1) {
                                selectedItem.SFFlag--;
                            }
                            if (!selectedItem.isBigSmallSelect[0]) {
                                selectedGames.delete(id);
                            }
                        } else {
                            selectedItem.isBigSmallSelect[1] = true;
                            if (selectedItem.SFFlag != 1) {
                                selectedItem.SFFlag++;
                            }
                            holder.dafen.setSelected(true);
                        }

                    } else {
                        item.isBigSmallSelect[1] = true;
                        if (item.SFFlag != 1) {
                            item.SFFlag++;
                        }
                        selectedGames.put(id, item);
                        holder.dafen.setSelected(true);

                    }
                    onGamesTouchedCallback.onTouched();
                }
            }
        });

        //胜分差适配与点击
        StringBuilder hostStr = null;
        StringBuilder visitStr = null;
        if (selectedGames.size() > 0 && selectedGames.get(item.orderIdLocal) != null) {
            holder.teamLayout.setSelected(true);


            boolean[] isSFCHostSelected = selectedGames.get(item.orderIdLocal).isSFCHostSelected;
            boolean[] isSFCGuestSelected = selectedGames.get(item.orderIdLocal).isSFCGuestSelected;
            int changdata = 0;
            String[] sfcName = selectedGames.get(item.orderIdLocal).sfcName;
            for (int i = 0; i < 6; i++) {
                if (isSFCGuestSelected[i]) {
                    if (visitStr == null) {
                        visitStr = new StringBuilder();
                        visitStr.append("主负：").append(sfcName[i]);

                    } else {
                        visitStr.append("; ").append(sfcName[i]);

                    }
                    ++changdata;
                }
                if (isSFCHostSelected[i]) {
                    if (hostStr == null) {
                        hostStr = new StringBuilder();
                        hostStr.append("主胜：").append(sfcName[i]);

                    } else {
                        hostStr.append("; ").append(sfcName[i]);

                    }
                    ++changdata;
                }
            }


            String selectedItems = "已选\n" + changdata + "项";
            holder.sfcText.setText(selectedItems);

        } else {
            holder.teamLayout.setSelected(false);
            holder.sfcText.setText(R.string.sfc);

        }

        final int group = groupPosition;
        final int child = childPosition;

        holder.teamLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (sfcPassStatus != 0) {
                    TiShiDialog tiShiDialog = new TiShiDialog(context);
                    tiShiDialog.show();
                    tiShiDialog.getTitle().setText("此比赛没有胜分差投注");
                    v.setEnabled(false);

                } else {
                    if (selectedGames.size() == 15 && selectedGames.indexOfKey(id) < 0) {
                        Toast.makeText(context, "最多可选15场比赛", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClass(context, BasketballMixselector.class);
                    intent.putExtra("group", group);
                    intent.putExtra("child", child);
                    context.startActivity(intent);
                }
            }

        });
        return convertView;
    }


    static class ChildViewHolder {
        public TextView league;
        public TextView gameCode;
        public TextView endTime;
        public TextView hostName;
        public TextView visitName;
        public TextView hostSp;
        public TextView visitSp;
        public TextView hostrfSp;
        public TextView visitftSp;
        public TextView sfcText;

        public TextView dafen;
        public TextView xiaofen;
        public TextView shengfuzhu;
        public TextView rangfenzhu;
        public TextView zongfen;
        public LinearLayout teamLayout;
    }


}
