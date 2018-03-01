package com.chengyi.app.jingji.basket;

import android.content.Context;
import android.content.Intent;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.util.L;
import com.chengyi.app.view.TiShiDialog;

import java.util.ArrayList;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BasketballmixcartAdapter extends BaseAdapter {

    private SparseArray<BasketballOneGame> selectedGames;// 便于在selectedGames作添加删除操作
    private SparseArray<BasketballOneGame> selectedGamesCopy;// 保存一份拷贝，用于保持显示条目，注意isSelected要随selectedGames同时更改
    public ArrayList<Integer> danSelectedArray;// 在投注确认中，被点过胆的比赛orderIdLocal，用于统计选中场次个数

    private SparseArray<String> positionArray;
    private LayoutInflater mInflater;
    private int wanfaGuan = 200;
    private int wanfa = 0;
    private int chuan = 0;
    private Context context;
    private boolean isDeletedMode = false;

    private BasketballmixCart.OnCartTouchedCallback onCartTouchedCallback;

    public BasketballmixcartAdapter(Context context,
                                    BasketballmixCart.OnCartTouchedCallback callback) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        selectedGames = new SparseArray<>();
        selectedGamesCopy = new SparseArray<>();
        danSelectedArray = new ArrayList<>();
        positionArray = new SparseArray<>();
        selectedGames = new SparseArray<>();
        this.onCartTouchedCallback = callback;
    }

    public void setData(int wanfaGuan) {
        positionArray = Control.getInstance().getBasketballManager().lotteryGamePositionArray
                .get(wanfaGuan);
        this.wanfaGuan = wanfaGuan;
        SparseArray<BasketballOneGame> selectedGames1 = Control.getInstance()
                .getBasketballManager().selectedLotteryGameArray.get(wanfaGuan);
        if (selectedGames1 != null) {
            selectedGames = selectedGames1;
        } else {
            selectedGames = new SparseArray<>();
            Control.getInstance().getBasketballManager().selectedLotteryGameArray
                    .put(wanfaGuan, selectedGames);
        }
        wanfa = Control.getInstance().getBasketballManager()
                .getWanfa(wanfaGuan);

        for (int i = 0; i < selectedGames.size(); i++) {
            BasketballOneGame newItem = (BasketballOneGame) selectedGames
                    .valueAt(i).clone();
            selectedGamesCopy.put(selectedGames.keyAt(i), newItem);
        }
        notifyDataSetChanged();
    }

    /**
     * 外部x串x有变化，则清空胆
     *
     * @param chuan
     */
    public void resetDanData(int chuan) {
        for (int id : danSelectedArray) {
            if (selectedGames.get(id) == null) {
                danSelectedArray.remove(danSelectedArray.indexOf(id));
            } else {
                selectedGames.get(id).isDanPressed = false;
            }
        }
        danSelectedArray.clear();
        this.chuan = chuan;
        L.e("resetDanData", chuan + "");
        notifyDataSetChanged();
    }

    /**
     * 设置是否处于删除模式
     *
     * @param isDeletedMode
     */
    public void setMode(boolean isDeletedMode) {
        this.isDeletedMode = isDeletedMode;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return selectedGamesCopy.size();
    }

    @Override
    public Object getItem(int position) {

        return selectedGamesCopy.valueAt(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final ChildViewHolder holder;
        if (convertView == null) {

            convertView = mInflater.inflate(
                    R.layout.fragment_lottery_basketball_cart_mix, parent, false);
            holder = new ChildViewHolder();
            holder.rangfenzhu = (TextView) convertView.
                    findViewById(R.id.rangfenzhu);
            holder.zongfen = (TextView) convertView.
                    findViewById(R.id.zongfen);
            holder.dafen = (TextView) convertView.
                    findViewById(R.id.basket_textview_visit_bs);
            holder.xiaofen = (TextView) convertView.
                    findViewById(R.id.basket_textview_host_bs);
            holder.hostrfSp = (TextView) convertView.
                    findViewById(R.id.basket_textview_host_rf);
            holder.visitftSp = (TextView) convertView.
                    findViewById(R.id.basket_textview_visit_rf);
            holder.hostSp = (TextView) convertView.
                    findViewById(R.id.basket_textview_host_sp);
            holder.visitSp = (TextView) convertView.
                    findViewById(R.id.basket_textview_visit_sp);

            holder.hostName = (TextView) convertView
                    .findViewById(R.id.basket_textview_host);
            holder.visitName = (TextView) convertView
                    .findViewById(R.id.basket_textview_visit);
            holder.sfcText = (TextView) convertView
                    .findViewById(R.id.mix_right);
            holder.teamLayout = (LinearLayout) convertView
                    .findViewById(R.id.basket_sfc_layout_team);
            holder.danBtn = (Button) convertView
                    .findViewById(R.id.basket_danbtn);
            holder.delete = (ImageView) convertView
                    .findViewById(R.id.iv_delete);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        final BasketballOneGame itemCopy = selectedGamesCopy.valueAt(position);
        final BasketballOneGame itemSelected = selectedGames
                .get(itemCopy.orderIdLocal);

        //数据进行适配
        holder.hostSp.setText("主胜 " + itemCopy.sheng);
        holder.visitSp.setText("主负 " + itemCopy.fu);
        holder.hostrfSp.setText("主胜 " + itemCopy.rfsheng);
        holder.visitftSp.setText("主负 " + itemCopy.rffu);
        holder.dafen.setText("大分 " + itemCopy.d);
        holder.xiaofen.setText("小分 " + itemCopy.x);
        holder.rangfenzhu.setText("主" + itemCopy.rateGG);
        holder.zongfen.setText(itemCopy.basePoint + "");

        holder.hostName.setText(itemCopy.hostName);
        holder.visitName.setText(itemCopy.guestName);

        // String num=item.id.trim();
        final int id = itemCopy.orderIdLocal;

        final int sfPassStatus = itemCopy.sfPassStatus;
        final int rfsfPassStatus = itemCopy.rfsfPassStatus;
        final int sfcPassStatus = itemCopy.sfcPassStatus;
        final int dxfPassStatus = itemCopy.dxfPassStatus;
        //胜负进行适配和点击操作
        if (selectedGames.indexOfKey(id) >= 0) {
            BasketballOneGame selectedItem = selectedGames.get(id);
            boolean[] isSelected = selectedItem.isSFSelected;
            holder.visitSp.setSelected(isSelected[1]);
            holder.hostSp.setSelected(isSelected[0]);
        } else {
            holder.visitSp.setSelected(false);
            holder.hostSp.setSelected(false);
        }
        holder.hostrfSp.setEnabled(false);
        holder.hostSp.setEnabled(false);
        holder.dafen.setEnabled(false);
        holder.visitftSp.setEnabled(false);
        holder.xiaofen.setEnabled(false);
        holder.visitSp.setEnabled(false);
        holder.teamLayout.setEnabled(false);
        //点击纪录胜负的状态好进行下一步适配
        holder.hostSp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 存于已选列表、
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
                            itemCopy.isSFSelected[0] = false;
                            if (itemCopy.SFFlag != -1) {
                                itemCopy.SFFlag--;
                            }
                            if (selectedItem.SFFlag != -1) {
                                selectedItem.SFFlag--;
                            }
                            holder.hostSp.setSelected(false);
                            if (!selectedItem.isSFSelected[1]) {
                                selectedGames.delete(id);
                                itemCopy.reset();
                                int index = danSelectedArray
                                        .indexOf(id);
                                if (index >= 0) {
                                    danSelectedArray.remove(index);
                                }
                            }
                        } else {

                            selectedItem.isSFSelected[0] = true;
                            itemCopy.isSFSelected[0] = true;
                            if (itemCopy.SFFlag != 1) {
                                itemCopy.SFFlag++;
                            }
                            if (selectedItem.SFFlag != 1) {
                                selectedItem.SFFlag++;
                            }
                            holder.hostSp.setSelected(true);
                        }

                    } else {

                        itemCopy.isSFSelected[0] = true;
                        itemCopy.SFFlag++;
                        selectedGames.put(id, itemCopy);
                        holder.hostSp.setSelected(true);
                    }
                /*                onGamesTouchedCallback.onTouched();*/
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
                            itemCopy.isSFSelected[1] = false;

                            if (itemCopy.SFFlag != -1) {
                                itemCopy.SFFlag--;
                            }
                            if (selectedItem.SFFlag != -1) {
                                selectedItem.SFFlag--;
                            }
                            holder.visitSp.setSelected(false);
                            if (!selectedItem.isSFSelected[0]) {
                                selectedGames.delete(id);
                                itemCopy.reset();
                                int index = danSelectedArray
                                        .indexOf(id);
                                if (index >= 0) {
                                    danSelectedArray.remove(index);
                                }
                            }
                        } else {
                            selectedItem.isSFSelected[1] = true;
                            itemCopy.isSFSelected[1] = true;
                            if (itemCopy.SFFlag != 1) {
                                itemCopy.SFFlag++;
                            }
                            if (selectedItem.SFFlag != 1) {
                                selectedItem.SFFlag++;
                            }
                            holder.visitSp.setSelected(true);
                        }

                    } else {
                        itemCopy.isSFSelected[1] = true;
                        if (itemCopy.SFFlag != 1) {
                            itemCopy.SFFlag++;
                        }
                        selectedGames.put(id, itemCopy);
                        holder.visitSp.setSelected(true);

                    }
/*                onGamesTouchedCallback.onTouched();*/
                }
            }

        });

        //让分胜负
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
                            itemCopy.isRFSFSelecter[0] = false;
                            if (itemCopy.SFFlag != -1) {
                                itemCopy.SFFlag--;
                            }
                            if (selectedItem.SFFlag != -1) {
                                selectedItem.SFFlag--;
                            }
                            holder.hostrfSp.setSelected(false);
                            if (!selectedItem.isRFSFSelecter[1]) {
                                selectedGames.delete(id);
                                itemCopy.reset();
                                int index = danSelectedArray
                                        .indexOf(id);
                                if (index >= 0) {
                                    danSelectedArray.remove(index);
                                }
                            }

                        } else {

                            selectedItem.isRFSFSelecter[0] = true;
                            itemCopy.isRFSFSelecter[0] = true;
                            if (itemCopy.SFFlag != 1) {
                                itemCopy.SFFlag++;
                            }
                            if (selectedItem.SFFlag != 1) {
                                selectedItem.SFFlag++;
                            }
                            holder.hostrfSp.setSelected(true);
                        }

                    } else {

                        itemCopy.isRFSFSelecter[0] = true;
                        itemCopy.SFFlag++;
                        selectedGames.put(id, itemCopy);
                        holder.hostrfSp.setSelected(true);
                    }
                /*                onGamesTouchedCallback.onTouched();*/
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
                            itemCopy.isRFSFSelecter[1] = false;

                            if (itemCopy.SFFlag != -1) {
                                itemCopy.SFFlag--;
                            }
                            if (selectedItem.SFFlag != -1) {
                                selectedItem.SFFlag--;
                            }
                            holder.visitftSp.setSelected(false);
                            if (!selectedItem.isRFSFSelecter[0]) {
                                selectedGames.delete(id);
                                itemCopy.reset();
                                int index = danSelectedArray
                                        .indexOf(id);
                                if (index >= 0) {
                                    danSelectedArray.remove(index);
                                }
                            }
                        } else {
                            selectedItem.isRFSFSelecter[1] = true;
                            itemCopy.isRFSFSelecter[1] = true;
                            if (itemCopy.SFFlag != 1) {
                                itemCopy.SFFlag++;
                            }
                            if (selectedItem.SFFlag != 1) {
                                selectedItem.SFFlag++;
                            }
                            holder.visitftSp.setSelected(true);
                        }

                    } else {
                        itemCopy.isRFSFSelecter[1] = true;
                        if (itemCopy.SFFlag != 1) {
                            itemCopy.SFFlag++;
                        }
                        selectedGames.put(id, itemCopy);
                        holder.visitftSp.setSelected(true);

                    }
/*                onGamesTouchedCallback.onTouched();*/
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
                            itemCopy.isBigSmallSelect[0] = false;
                            if (itemCopy.SFFlag != -1) {
                                itemCopy.SFFlag--;
                            }
                            if (selectedItem.SFFlag != -1) {
                                selectedItem.SFFlag--;
                            }
                            holder.xiaofen.setSelected(false);
                            if (!selectedItem.isBigSmallSelect[1]) {
                                selectedGames.delete(id);
                                itemCopy.reset();
                                int index = danSelectedArray
                                        .indexOf(id);
                                if (index >= 0) {
                                    danSelectedArray.remove(index);
                                }
                            }

                        } else {
                            selectedItem.isBigSmallSelect[0] = true;
                            itemCopy.isBigSmallSelect[0] = true;
                            if (itemCopy.SFFlag != 1) {
                                itemCopy.SFFlag++;
                            }
                            if (selectedItem.SFFlag != 1) {
                                selectedItem.SFFlag++;
                            }
                            holder.xiaofen.setSelected(true);
                        }

                    } else {

                        itemCopy.isBigSmallSelect[0] = true;
                        itemCopy.SFFlag++;
                        selectedGames.put(id, itemCopy);
                        holder.xiaofen.setSelected(true);
                    }
                /*                onGamesTouchedCallback.onTouched();*/
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
                            itemCopy.isBigSmallSelect[1] = false;

                            if (itemCopy.SFFlag != -1) {
                                itemCopy.SFFlag--;
                            }
                            if (selectedItem.SFFlag != -1) {
                                selectedItem.SFFlag--;
                            }
                            holder.dafen.setSelected(false);
                            if (!selectedItem.isBigSmallSelect[0]) {
                                selectedGames.delete(id);
                                itemCopy.reset();
                                int index = danSelectedArray
                                        .indexOf(id);
                                if (index >= 0) {
                                    danSelectedArray.remove(index);
                                }
                            }
                        } else {
                            selectedItem.isBigSmallSelect[1] = true;
                            itemCopy.isBigSmallSelect[1] = true;
                            if (itemCopy.SFFlag != 1) {
                                itemCopy.SFFlag++;
                            }
                            if (selectedItem.SFFlag != 1) {
                                selectedItem.SFFlag++;
                            }
                            holder.dafen.setSelected(true);
                        }

                    } else {
                        itemCopy.isBigSmallSelect[1] = true;
                        if (itemCopy.SFFlag != 1) {
                            itemCopy.SFFlag++;
                        }
                        selectedGames.put(id, itemCopy);
                        holder.dafen.setSelected(true);

                    }
/*                onGamesTouchedCallback.onTouched();*/
                }
            }

        });


        //胜负差
        StringBuilder hostStr = null;
        StringBuilder visitStr = null;
        if (selectedGames.size() > 0 && itemSelected != null) {

            holder.sfcText.setVisibility(View.VISIBLE);
            boolean[] isSFCHostSelected = selectedGames
                    .get(itemCopy.orderIdLocal).isSFCHostSelected;
            boolean[] isSFCGuestSelected = selectedGames
                    .get(itemCopy.orderIdLocal).isSFCGuestSelected;

            String[] sfcName = itemSelected.sfcName;
            int chagedata = 0;
            for (int i = 0; i < 6; i++) {
                if (isSFCGuestSelected[i]) {
                    holder.teamLayout.setSelected(true);
                    if (visitStr == null) {
                        visitStr = new StringBuilder();
                        visitStr.append("主负：").append(sfcName[i]);
                    } else {
                        visitStr.append("; ").append(sfcName[i]);
                    }
                    chagedata++;
                }
                if (isSFCHostSelected[i]) {
                    holder.teamLayout.setSelected(true);
                    if (hostStr == null) {
                        hostStr = new StringBuilder();
                        hostStr.append("主胜：").append(sfcName[i]);
                    } else {
                        hostStr.append("; ").append(sfcName[i]);
                    }
                    chagedata++;
                }

            }
            String visitChoosed = "";
            String hostChoosed = "";
            if (visitStr != null) {
                visitChoosed = visitStr.toString() + " ";
            }
            if (hostStr != null) {
                hostChoosed = hostStr.toString();
            }

            String selectedItems = "已选\n" + chagedata + "项";
            if (chagedata == 0) {
                holder.sfcText.setVisibility(View.VISIBLE);

            }
            holder.sfcText.setText(selectedItems);

        } else {
            holder.teamLayout.setSelected(false);

        }


        holder.danBtn.setVisibility(View.GONE);
        holder.delete.setVisibility(View.INVISIBLE);
        if (!isDeletedMode && itemSelected != null && itemSelected.SFFlag > -1) {
            if (wanfaGuan - BasketballManager.guoguan >= 0) {// 确定是过关
                if (chuan > 1 && selectedGames.size() > 2
                        && selectedGames.size() > chuan) {// 3关以上，比赛数大于串数，才能胆拖

                    holder.danBtn.setVisibility(View.VISIBLE);
                    holder.danBtn.setSelected(false);
                    if (itemSelected.isDanPressed) {
                        holder.danBtn.setSelected(true);
                    } else if (!itemSelected.isDanPressed
                            && danSelectedArray.size() == chuan - 1) {// 如果未被选中，且已定胆数达到最大
                        holder.danBtn.setVisibility(View.INVISIBLE);
                    }

                    holder.danBtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            boolean isSelected = itemSelected.isDanPressed;
                            if (isSelected) {
                                itemSelected.isDanPressed = false;
                            } else {
                                itemSelected.isDanPressed = true;
                            }

                            holder.danBtn
                                    .setSelected(itemSelected.isDanPressed);
                            if (itemSelected.isDanPressed) {
                                danSelectedArray.add(itemSelected.orderIdLocal);
                            } else {
                                int index = danSelectedArray
                                        .indexOf(itemSelected.orderIdLocal);
                                if (index >= 0) {
                                    danSelectedArray.remove(index);
                                }
                            }
                            onCartTouchedCallback.onDanPressed();
                            notifyDataSetChanged();
                        }

                    });

                }

            }

        }

        if (isDeletedMode) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (selectedGames.get(itemCopy.orderIdLocal) != null) {
                        selectedGames.get(itemCopy.orderIdLocal).reset();
                    }

                    selectedGames.remove(itemCopy.orderIdLocal);
                    selectedGamesCopy.remove(itemCopy.orderIdLocal);
                    int index = danSelectedArray.indexOf(itemCopy.orderIdLocal);
                    if (index >= 0) {
                        danSelectedArray.remove(index);
                    }
                    onCartTouchedCallback.onGameDeleted();
                    notifyDataSetChanged();
                }

            });
        }


        holder.sfcText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                L.d("22222222222222222222222222222222222222");
                Intent intent = new Intent();
                intent.setClass(context, BasketballMixselector.class);
                String[] position = positionArray.get(id).split(",");
                intent.putExtra("isClick", false);
                int group = Integer.valueOf(position[0]);
                int child = Integer.valueOf(position[1]);
                intent.putExtra("group", group);
                intent.putExtra("child", child);
                context.startActivity(intent);

            }

        });


        return convertView;
    }

    static class ChildViewHolder {
        public TextView zongfen;
        public TextView rangfenzhu;
        public TextView dafen;
        public TextView xiaofen;
        public TextView hostrfSp;
        public TextView visitftSp;
        public TextView hostSp;
        public TextView visitSp;

        public TextView hostName;
        public TextView visitName;
        public ImageView divider;
        public TextView sfcText;
        public LinearLayout teamLayout;
        public Button danBtn;
        public ImageView delete;
    }

}
