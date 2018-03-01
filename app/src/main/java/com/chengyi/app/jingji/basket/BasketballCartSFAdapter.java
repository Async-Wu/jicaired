package com.chengyi.app.jingji.basket;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.net.control.Control;

import java.util.ArrayList;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BasketballCartSFAdapter extends BaseAdapter {

    private SparseArray<BasketballOneGame> selectedGames;// 便于在selectedGames作添加删除操作
    private SparseArray<BasketballOneGame> selectedGamesCopy;// 保存一份拷贝，用于保持显示条目，注意isSelected要随selectedGames同时更改
    public ArrayList<Integer> danSelectedArray;// 在投注确认中，被点过胆的比赛orderIdLocal，用于统计选中场次个数

    private LayoutInflater mInflater;
    private int wanfaGuan = 200;
    private int wanfa = 0;
    private int chuan = 0;
    private BasketballCartSF.OnCartTouchedCallback onCartTouchedCallback;

    private boolean isDeletedMode = false;

    public BasketballCartSFAdapter(Context context,
                                   BasketballCartSF.OnCartTouchedCallback onCartTouchedCallback) {
        mInflater = LayoutInflater.from(context);

        selectedGames = new SparseArray<BasketballOneGame>();
        selectedGamesCopy = new SparseArray<BasketballOneGame>();
        danSelectedArray = new ArrayList<Integer>();
        this.onCartTouchedCallback = onCartTouchedCallback;
    }


    public void setData(int wanfaGuan) {

        this.wanfaGuan = wanfaGuan;
        SparseArray<BasketballOneGame> selectedGames1 = Control.getInstance()
                .getBasketballManager().selectedLotteryGameArray.get(wanfaGuan);
        if (selectedGames1 != null) {
            selectedGames = selectedGames1;
        } else {
            selectedGames = new SparseArray<BasketballOneGame>();
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
                    R.layout.fragment_lottery_basketball_cart_sf, parent,false);
            holder = new ChildViewHolder();
//            holder.bigsmall_total = (TextView) convertView
//                    .findViewById(R.id.bigsmall_total);
            holder.hostName = (TextView) convertView
                    .findViewById(R.id.basket_textview_host);
            holder.hostSp = (TextView) convertView
                    .findViewById(R.id.basket_textview_host_sp);
            holder.hostRF = (TextView) convertView
                    .findViewById(R.id.bigsmall_total);
            holder.visitName = (TextView) convertView
                    .findViewById(R.id.basket_textview_visit);
            holder.visitSp = (TextView) convertView
                    .findViewById(R.id.basket_textview_visit_sp);
          
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

        holder.hostName.setText(itemCopy.hostName);
        holder.visitName.setText(itemCopy.guestName);
        if (wanfa == BasketballManager.rfsf) {
            holder.hostRF.setVisibility(View.VISIBLE);
            holder.hostRF.setText(itemCopy.rateGG + "");
            holder.hostSp.setText("主胜 " + itemCopy.rfsheng);
            holder.visitSp.setText("主负 " + itemCopy.rffu);
        } else {
            holder.hostRF.setVisibility(View.GONE);
            holder.hostSp.setText("主胜 " + itemCopy.sheng);
            holder.visitSp.setText("主负 " + itemCopy.fu);
        }
        if (wanfa == BasketballManager.bigsmall) {
          try {
              holder.hostRF.setVisibility(View.VISIBLE);
              holder.hostRF.setText(itemCopy.basePoint + "");

          }catch (Exception e){
              e.printStackTrace();
          }
            holder.hostSp.setText("小分 " + itemCopy.x);
            holder.visitSp.setText("大分 " + itemCopy.d);
        }

        holder.danBtn.setVisibility(View.GONE);
        holder.delete.setVisibility(View.INVISIBLE);
        if (!isDeletedMode
                && itemSelected != null
                && (itemSelected.isSFSelected[0] || itemSelected.isSFSelected[1])) {// 只有有场次被选中才能显示胆按钮
            if (wanfaGuan - BasketballManager.guoguan >= 0) {// 确定是过关
                if (chuan > 1 && selectedGames.size() > 2 && selectedGames.size() > chuan) {// 3关以上，比赛数大于串数，才能胆拖
//                    holder.danBtn.setVisibility(View.VISIBLE);
                    holder.danBtn.setSelected(false);
                    if (itemSelected.isDanPressed) {
                        holder.danBtn.setSelected(true);
                    } else if (!itemSelected.isDanPressed
                            && danSelectedArray.size() == chuan - 1) {// 如果未被选中，且已定胆数达到最大
//                        holder.danBtn.setVisibility(View.INVISIBLE);
                    }

                    holder.danBtn.setOnClickListener(new OnClickListener() {

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
            holder.delete.setOnClickListener(new OnClickListener() {

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

                    Control.getInstance()
                            .getBasketballManager().selectedLotteryGameArray.get(wanfaGuan);
                    notifyDataSetChanged();
                }

            });
        }

        // String num=item.id.trim();
        final int id = itemCopy.orderIdLocal;
        if (selectedGames.indexOfKey(id) >= 0) {
            BasketballOneGame selectedItem = selectedGames.get(id);
            boolean[] isSelected = selectedItem.isSFSelected;
            holder.visitSp.setSelected(isSelected[1]);
            holder.hostSp.setSelected(isSelected[0]);

        } else {
            holder.visitSp.setSelected(false);
            holder.hostSp.setSelected(false);
        }
        holder.visitSp.setEnabled(false);
        holder.hostSp.setEnabled(false);
        holder.hostSp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                // 存于已选列表

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
                            Control.getInstance()
                                    .getBasketballManager().selectedLotteryGameArray.get(wanfaGuan).delete(id);


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
                onCartTouchedCallback.onGamePressed();
            }

        });

        holder.visitSp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

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
                    selectedGames.put(id, itemCopy);
                    if (itemCopy.SFFlag != 1) {
                        itemCopy.SFFlag++;
                    }
                    holder.visitSp.setSelected(true);

                }
                onCartTouchedCallback.onGamePressed();
            }

        });

        return convertView;
    }

    static class ChildViewHolder {


        public TextView hostName;
        public TextView hostSp;
        public TextView hostRF;
        public TextView visitName;
        public TextView visitSp;
//        public LinearLayout visitSp;
//        public LinearLayout hostSp;
        public Button danBtn;
        public ImageView delete;

    }

}
