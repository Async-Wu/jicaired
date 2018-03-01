package com.chengyi.app.jingji.basket;

import android.content.Context;
import android.content.Intent;
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
import com.chengyi.app.util.L;

import java.util.ArrayList;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BasketballCartSFCAdapter extends BaseAdapter {

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

    private BasketballCartSFC.OnCartTouchedCallback onCartTouchedCallback;

    public BasketballCartSFCAdapter(Context context,
                                    BasketballCartSFC.OnCartTouchedCallback callback) {
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
                    R.layout.fragment_lottery_basketball_cart_sfc, parent, false);
            holder = new ChildViewHolder();

            holder.hostName = (TextView) convertView
                    .findViewById(R.id.basket_textview_host);
            holder.visitName = (TextView) convertView
                    .findViewById(R.id.basket_textview_visit);

            holder.sfcText = (TextView) convertView
                    .findViewById(R.id.basket_sfc_choosed_textview);

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


        // String num=item.id.trim();
        final int id = itemCopy.orderIdLocal;


        holder.sfcText.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, BasketballSFCSelector.class);
                intent.putExtra("isClick", false);
                String[] position = positionArray.get(id).split(",");
                int group = Integer.valueOf(position[0]);
                int child = Integer.valueOf(position[1]);
                intent.putExtra("group", group);
                intent.putExtra("child", child);
                context.startActivity(intent);
            }

        });


        StringBuilder hostStr = null;
        StringBuilder visitStr = null;
        if (selectedGames.size() > 0 && itemSelected != null) {
            holder.sfcText.setSelected(true);
            boolean[] isSFCHostSelected = selectedGames
                    .get(itemCopy.orderIdLocal).isSFCHostSelected;
            boolean[] isSFCGuestSelected = selectedGames
                    .get(itemCopy.orderIdLocal).isSFCGuestSelected;

            String[] sfcName = itemSelected.sfcName;
            for (int i = 0; i < 6; i++) {
                if (isSFCGuestSelected[i]) {
                    if (visitStr == null) {
                        visitStr = new StringBuilder();
                        visitStr.append("主负：").append(sfcName[i]);
                    } else {
                        visitStr.append("; ").append(sfcName[i]);
                    }
                }
                if (isSFCHostSelected[i]) {
                    if (hostStr == null) {
                        hostStr = new StringBuilder();
                        hostStr.append("主胜：").append(sfcName[i]);
                    } else {
                        hostStr.append("; ").append(sfcName[i]);
                    }
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

            String selectedItems = visitChoosed + hostChoosed;
            holder.sfcText.setText(selectedItems);

        } else {
            holder.sfcText.setSelected(false);


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
                    notifyDataSetChanged();
                }

            });
        }

        return convertView;
    }

    static class ChildViewHolder {

        public TextView hostName;
        public TextView visitName;

        public TextView sfcText;

        public Button danBtn;
        public ImageView delete;
    }

}
