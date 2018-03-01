package com.chengyi.app.jingji.bzjy;


import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.util.IP;
import com.chengyi.app.util.L;

import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BzjyAdapter extends BaseExpandableListAdapter {
    private List<DataBean> modes;
    private Context context;

    private int type = 0;

    public BzjyAdapter(List<DataBean> modes, Context context, int type) {
        this.modes = modes;
        this.context = context;
        this.type = type;
    }

    @Override
    public int getGroupCount() {
        return modes == null ? 0 : modes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return modes.get(groupPosition).getMatches() == null ? 0 : modes.get(groupPosition).getMatches().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return modes.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return modes.get(groupPosition).getMatches().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return (groupPosition + 1) * (childPosition + 1);
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        H h;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_lottery_football_listview_groupview, parent, false);
            h = new H();
            h.count= (TextView) convertView.findViewById(R.id.count);
            h.tv_title = (TextView) convertView.findViewById(R.id.datetextview);
            convertView.setTag(h);

        } else {
            h = (H) convertView.getTag();
        }
        h.count.setText(modes.get(groupPosition).getMatchCount() + "场比赛");
        h.tv_title.setText(modes.get(groupPosition).getDayKey() + modes.get(groupPosition).getDayOfWeekStr() + "   "  );

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        if (type == 0) {


            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_danch, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.tvMatcherName = (TextView) convertView.findViewById(R.id.liansainame);
                viewHolder.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
                viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                viewHolder.tvHostName = (TextView) convertView.findViewById(R.id.tv_host_name);
                viewHolder.tvHostNum = (TextView) convertView.findViewById(R.id.tv_host_num);
                viewHolder.tvPName = (TextView) convertView.findViewById(R.id.tv_p_name);
                viewHolder.tvPNum = (TextView) convertView.findViewById(R.id.tv_p_num);
                viewHolder.tvKeName = (TextView) convertView.findViewById(R.id.tv_ke_name);
                viewHolder.tvKeNum = (TextView) convertView.findViewById(R.id.tv_ke_num);
                viewHolder.ll_host = (LinearLayout) convertView.findViewById(R.id.ll_host);
                viewHolder.ll_gust = (LinearLayout) convertView.findViewById(R.id.ll_gust);
                viewHolder.ll_vs = (LinearLayout) convertView.findViewById(R.id.ll_vs);
                viewHolder.ll_rh = (LinearLayout) convertView.findViewById(R.id.ll_rh);


                viewHolder.tv_is_no = (TextView) convertView.findViewById(R.id.tv_is_no);
                viewHolder.tv_rh = (TextView) convertView.findViewById(R.id.tv_rh);
                viewHolder.tv_is_num = (TextView) convertView.findViewById(R.id.tv_is_num);


                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.tvMatcherName.setText(modes.get(groupPosition).getMatches().get(childPosition).getLeagueName());
            viewHolder.tvHostName.setText(modes.get(groupPosition).getMatches().get(childPosition).getHostName());
            viewHolder.tvHostName.setText(modes.get(groupPosition).getMatches().get(childPosition).getHostName());


            viewHolder.tvTime.setText(modes.get(groupPosition).getMatches().get(childPosition).getMatchTime() + "截至");
            viewHolder.tvPName.setText("VS");
            viewHolder.tvPNum.setText(modes.get(groupPosition).getMatches().get(childPosition).getHostName());
            viewHolder.tvKeName.setText(modes.get(groupPosition).getMatches().get(childPosition).getGuestName());

            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getRqPing().contains(IP.CANNOT_SELECT)) {
                viewHolder.tvPNum.setText(IP.CANNOT_SELECT);
            } else {
                viewHolder.tvPNum.setText("平  " + modes.get(groupPosition).getMatches().get(childPosition).getSp().getRqPing() + "");

            }


            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getRqSheng().contains(IP.CANNOT_SELECT)) {
                viewHolder.tvHostNum.setText(IP.CANNOT_SELECT);
            } else {
                viewHolder.tvHostNum.setText("胜  " + modes.get(groupPosition).getMatches().get(childPosition).getSp().getRqSheng() + "");

            }


            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getRqFu().contains(IP.CANNOT_SELECT)) {
                viewHolder.tvKeNum.setText(IP.CANNOT_SELECT);
            } else {
                viewHolder.tvKeNum.setText("负  " + modes.get(groupPosition).getMatches().get(childPosition).getSp().getRqFu() + "");

            }


            viewHolder.tvNum.setText(modes.get(groupPosition).getMatches().get(childPosition).getTeamId() + "");
//
            viewHolder.tvPName.setText(modes.get(groupPosition).getMatches().get(childPosition).getRate() + "");


            viewHolder.tvHostNum.setSelected(modes.get(groupPosition).getMatches().get(childPosition).isHost());
            viewHolder.tvPNum.setSelected(modes.get(groupPosition).getMatches().get(childPosition).isVs());

            viewHolder.tvKeNum.setSelected(modes.get(groupPosition).getMatches().get(childPosition).isGust());


            viewHolder.tvHostNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getRqSheng().equals(IP.CANNOT_SELECT)) {
                        Toast.makeText(context, "此比赛没有让球胜平负投注", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    viewHolder.tvHostNum.setSelected(!viewHolder.tvHostNum.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).setHost(!modes.get(groupPosition).getMatches().get(childPosition).isHost());
                    ((IMode) context).setModel(modes);
                    notifyDataSetChanged();
                }
            });


            viewHolder.tvPNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getRqPing().equals(IP.CANNOT_SELECT)) {
                        Toast.makeText(context, "此比赛没有让球胜平负投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    viewHolder.tvPNum.setSelected(!viewHolder.tvPNum.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).setVs(!modes.get(groupPosition).getMatches().get(childPosition).isVs());
                    ((IMode) context).setModel(modes);
                    notifyDataSetChanged();

                }
            });


            viewHolder.tvKeNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getRqFu().equals(IP.CANNOT_SELECT)) {
                        Toast.makeText(context, "此比赛没有让球胜平负投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    viewHolder.tvKeNum.setSelected(!viewHolder.tvKeNum.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).setGust(!modes.get(groupPosition).getMatches().get(childPosition).isGust());
                    ((IMode) context).setModel(modes);

                    notifyDataSetChanged();
                }
            });
        } else if (type == 1) {
            final HolederZongfen holederZongfen;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_zsff, parent, false);

                holederZongfen = new HolederZongfen();
                holederZongfen.tv_t1 = (TextView) convertView.findViewById(R.id.tv_t1);
                holederZongfen.tv_t2 = (TextView) convertView.findViewById(R.id.tv_t2);
                holederZongfen.tv_t3 = (TextView) convertView.findViewById(R.id.tv_t3);
                holederZongfen.tv_t4 = (TextView) convertView.findViewById(R.id.tv_t4);
                holederZongfen.tv_t5 = (TextView) convertView.findViewById(R.id.tv_t5);
                holederZongfen.tv_t6 = (TextView) convertView.findViewById(R.id.tv_t6);
                holederZongfen.tv_t7 = (TextView) convertView.findViewById(R.id.tv_t7);
                holederZongfen.tv_t8 = (TextView) convertView.findViewById(R.id.tv_t8);

                holederZongfen.ln1 = (LinearLayout) convertView.findViewById(R.id.ln1);
                holederZongfen.ln2 = (LinearLayout) convertView.findViewById(R.id.ln2);
                holederZongfen.ln3 = (LinearLayout) convertView.findViewById(R.id.ln3);
                holederZongfen.ln4 = (LinearLayout) convertView.findViewById(R.id.ln4);
                holederZongfen.ln5 = (LinearLayout) convertView.findViewById(R.id.ln5);
                holederZongfen.ln6 = (LinearLayout) convertView.findViewById(R.id.ln6);
                holederZongfen.ln7 = (LinearLayout) convertView.findViewById(R.id.ln7);
                holederZongfen.ln8 = (LinearLayout) convertView.findViewById(R.id.ln8);


                holederZongfen.tv_host = (TextView) convertView.findViewById(R.id.tv_host);
                holederZongfen.tv_gust = (TextView) convertView.findViewById(R.id.tv_gust);

                holederZongfen.tvMatcherName = (TextView) convertView.findViewById(R.id.tv_matcher_name);
                holederZongfen.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
                holederZongfen.tvTime = (TextView) convertView.findViewById(R.id.tv_time);

                convertView.setTag(holederZongfen);
            } else {
                holederZongfen = (HolederZongfen) convertView.getTag();
            }
            holederZongfen.tvNum.setText(modes.get(groupPosition).getMatches().get(childPosition).getTeamId() + "");

            holederZongfen.tvMatcherName.setText(modes.get(groupPosition).getMatches().get(childPosition).getLeagueName());

            holederZongfen.tvNum.setText(modes.get(groupPosition).getMatches().get(childPosition).getTeamId() + "");
            holederZongfen.tv_host.setText(modes.get(groupPosition).getMatches().get(childPosition).getHostName());
            holederZongfen.tv_gust.setText(modes.get(groupPosition).getMatches().get(childPosition).getGuestName());
            holederZongfen.tvTime.setText(modes.get(groupPosition).getMatches().get(childPosition).getMatchTime() + "截至");

            holederZongfen.tv_t1.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getT0() + "");
            holederZongfen.tv_t2.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getT1() + "");
            holederZongfen.tv_t3.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getT2() + "");
            holederZongfen.tv_t4.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getT3() + "");
            holederZongfen.tv_t5.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getT4() + "");
            holederZongfen.tv_t6.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getT5() + "");
            holederZongfen.tv_t7.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getT6() + "");
            holederZongfen.tv_t8.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getT7() + "");


            holederZongfen.tv_t1.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isT1());
            holederZongfen.tv_t2.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isT2());
            holederZongfen.tv_t3.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isT3());
            holederZongfen.tv_t4.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isT4());
            holederZongfen.tv_t5.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isT5());
            holederZongfen.tv_t6.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isT6());
            holederZongfen.tv_t7.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isT7());
            holederZongfen.tv_t8.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isT8());


            holederZongfen.tvMatcherName.setText(modes.get(groupPosition).getMatches().get(childPosition).getLeagueName());


            holederZongfen.ln1.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getT0().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有总进球投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    holederZongfen.ln1.setSelected(!holederZongfen.ln1.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setT1(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isT1());
                    ((IMode) context).setModel(modes);

                    notifyDataSetChanged();


                }
            });
            holederZongfen.ln2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getT1().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有总进球投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    holederZongfen.ln2.setSelected(!holederZongfen.ln2.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setT2(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isT2());
                    ((IMode) context).setModel(modes);

                    notifyDataSetChanged();

                }
            });
            holederZongfen.ln3.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getT2().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有总进球投注", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    holederZongfen.ln3.setSelected(!holederZongfen.ln3.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setT3(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isT3());
                    ((IMode) context).setModel(modes);

                    notifyDataSetChanged();

                }
            });
            holederZongfen.ln4.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {


                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getT3().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有总进球投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    holederZongfen.ln4.setSelected(!holederZongfen.ln4.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setT4(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isT4());
                    ((IMode) context).setModel(modes);

                    notifyDataSetChanged();

                }
            });
            holederZongfen.ln5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getT4().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有总进球投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    holederZongfen.ln5.setSelected(!holederZongfen.ln5.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setT5(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isT5());
                    ((IMode) context).setModel(modes);
                    notifyDataSetChanged();

                }
            });
            holederZongfen.ln6.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getT5().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有总进球投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    holederZongfen.ln6.setSelected(!holederZongfen.ln6.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setT6(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isT6());
                    ((IMode) context).setModel(modes);
                    notifyDataSetChanged();

                }
            });
            holederZongfen.ln7.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getT6().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有总进球投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    holederZongfen.ln7.setSelected(!holederZongfen.ln7.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setT7(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isT7());
                    ((IMode) context).setModel(modes);
                    notifyDataSetChanged();

                }
            });
            holederZongfen.ln8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getT7().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有总进球投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    holederZongfen.ln8.setSelected(!holederZongfen.ln8.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setT8(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isT8());
                    ((IMode) context).setModel(modes);
                    notifyDataSetChanged();
                }
            });


        } else if (type == 2) {
            final Holedersxds holederZongfen;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_sxds, parent, false);
                holederZongfen = new Holedersxds();
                holederZongfen.tv_t1 = (TextView) convertView.findViewById(R.id.tv_t1);
                holederZongfen.tv_t2 = (TextView) convertView.findViewById(R.id.tv_t2);
                holederZongfen.tv_t3 = (TextView) convertView.findViewById(R.id.tv_t3);
                holederZongfen.tv_t4 = (TextView) convertView.findViewById(R.id.tv_t4);


                holederZongfen.tv_host = (TextView) convertView.findViewById(R.id.tv_matcher_name);
                holederZongfen.tv_gust = (TextView) convertView.findViewById(R.id.tv_ke_name);

                holederZongfen.tvMatcherName = (TextView) convertView.findViewById(R.id.liansainame);
                holederZongfen.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
                holederZongfen.tvTime = (TextView) convertView.findViewById(R.id.tv_time);

                convertView.setTag(holederZongfen);
            } else {
                holederZongfen = (Holedersxds) convertView.getTag();
            }
            holederZongfen.tv_host.setText(modes.get(groupPosition).getMatches().get(childPosition).getHostName());
            holederZongfen.tv_gust.setText(modes.get(groupPosition).getMatches().get(childPosition).getGuestName());
            holederZongfen.tvMatcherName.setText(modes.get(groupPosition).getMatches().get(childPosition).getLeagueName());
            holederZongfen.tv_t1.setText("上单 " + modes.get(groupPosition).getMatches().get(childPosition).getSp().getShangD() + "");
            holederZongfen.tv_t2.setText("下单 " + modes.get(groupPosition).getMatches().get(childPosition).getSp().getShangS() + "");
            holederZongfen.tv_t3.setText("上双 " + modes.get(groupPosition).getMatches().get(childPosition).getSp().getXiaD() + "");
            holederZongfen.tv_t4.setText("下双 " + modes.get(groupPosition).getMatches().get(childPosition).getSp().getXiaS() + "");
            holederZongfen.tvNum.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getTeamId() + "");
            holederZongfen.tv_t1.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isShangD());
            holederZongfen.tv_t2.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isShangS());


            holederZongfen.tv_t3.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isXiaD());
            holederZongfen.tv_t4.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isXiaS());


            holederZongfen.tvTime.setText(modes.get(groupPosition).getMatches().get(childPosition).getMatchTime() + "截至");
            holederZongfen.tv_t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getShangD().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有上单投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    holederZongfen.tv_t1.setSelected(!holederZongfen.tv_t1.isSelected());
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setShangD(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isShangD());
                    ((IMode) context).setModel(modes);
                    notifyDataSetChanged();
                }

            });
            holederZongfen.tv_t2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getShangS().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有上双投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setShangS(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isShangS());
                    ((IMode) context).setModel(modes);
                    notifyDataSetChanged();
                }

            });
            holederZongfen.tv_t3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getXiaD().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有下单投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setXiaD(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isXiaD());
                    ((IMode) context).setModel(modes);
                    notifyDataSetChanged();
                }
            });
            holederZongfen.tv_t4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getXiaS().contains(IP.CANNOT_SELECT)) {

                        Toast.makeText(context, "此比赛没有下双投注", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    modes.get(groupPosition).getMatches().get(childPosition).getSp().setXiaS(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isXiaS());
                    ((IMode) context).setModel(modes);
                    notifyDataSetChanged();
                }
            });


        } else if (type == 3) {
            final HolederBifen bifen;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_biff, parent, false);
                bifen = new HolederBifen();
                bifen.tv_num = (TextView) convertView.findViewById(R.id.tv_num);


                bifen.tv_t1 = (TextView) convertView.findViewById(R.id.tv_t1);
                bifen.tv_t2 = (TextView) convertView.findViewById(R.id.tv_t2);
                bifen.tv_t3 = (TextView) convertView.findViewById(R.id.tv_t3);
                bifen.tv_t4 = (TextView) convertView.findViewById(R.id.tv_t4);
                bifen.chosedtext = (TextView) convertView.findViewById(R.id.chosedtext);


                bifen.tv_host = (TextView) convertView.findViewById(R.id.duiwu1);
                bifen.tv_gust = (TextView) convertView.findViewById(R.id.duiwu2);

                bifen.tvMatcherName = (TextView) convertView.findViewById(R.id.liansainame);
                bifen.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
                bifen.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(bifen);
            } else {
                bifen = (HolederBifen) convertView.getTag();
            }
            bifen.tvMatcherName.setText(modes.get(groupPosition).getMatches().get(childPosition).getLeagueName());

            bifen.tv_host.setText(modes.get(groupPosition).getMatches().get(childPosition).getHostName());
            bifen.tv_gust.setText(modes.get(groupPosition).getMatches().get(childPosition).getGuestName());

            if (!TextUtils.isEmpty(modes.get(groupPosition).getMatches().get(childPosition).getBifenShow())) {
                bifen.chosedtext.setSelected(true);
                bifen.chosedtext.setText(modes.get(groupPosition).getMatches().get(childPosition).getBifenShow());

            } else {
                bifen.chosedtext.setText("点击展开比分投注区");
                bifen.chosedtext.setSelected(false);

            }


            bifen.tv_num.setText(modes.get(groupPosition).getMatches().get(childPosition).getTeamId() + "");


            bifen.tvTime.setText(modes.get(groupPosition).getMatches().get(childPosition).getMatchTime() + "截至");
            bifen.chosedtext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Integer.parseInt(BjCacul.cacluBzjy(modes)) >= 3 && TextUtils.isEmpty(bifen.chosedtext.getText().toString())) {
                        Toast.makeText(context, "最多选择3场比赛", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    View view = LayoutInflater.from(context).inflate(R.layout.bj_biff, null);
                    final AlertDialog alertDialog = new AlertDialog.Builder(context).setView(view).show();

                    Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);





                    final LinearLayout ll_s_1 = (LinearLayout) view.findViewById(R.id.ll_s_1);
                    final LinearLayout ll_s_2 = (LinearLayout) view.findViewById(R.id.ll_s_2);
                    final LinearLayout ll_s_3 = (LinearLayout) view.findViewById(R.id.ll_s_3);
                    final LinearLayout ll_s_4 = (LinearLayout) view.findViewById(R.id.ll_s_4);
                    final LinearLayout ll_s_5 = (LinearLayout) view.findViewById(R.id.ll_s_5);
                    final LinearLayout ll_s_6 = (LinearLayout) view.findViewById(R.id.ll_s_6);
                    final LinearLayout ll_s_7 = (LinearLayout) view.findViewById(R.id.ll_s_7);
                    final LinearLayout ll_s_8 = (LinearLayout) view.findViewById(R.id.ll_s_8);
                    final LinearLayout ll_s_9 = (LinearLayout) view.findViewById(R.id.ll_s_9);
                    final LinearLayout ll_s_10 = (LinearLayout) view.findViewById(R.id.ll_s_10);


                    final LinearLayout ll_p_1 = (LinearLayout) view.findViewById(R.id.ll_p_1);
                    final LinearLayout ll_p_2 = (LinearLayout) view.findViewById(R.id.ll_p_2);
                    final LinearLayout ll_p_3 = (LinearLayout) view.findViewById(R.id.ll_p_3);
                    final LinearLayout ll_p_4 = (LinearLayout) view.findViewById(R.id.ll_p_4);
                    final LinearLayout ll_p_5 = (LinearLayout) view.findViewById(R.id.ll_p_5);


                    final LinearLayout ll_f_1 = (LinearLayout) view.findViewById(R.id.ll_f_1);
                    final LinearLayout ll_f_2 = (LinearLayout) view.findViewById(R.id.ll_f_2);
                    final LinearLayout ll_f_3 = (LinearLayout) view.findViewById(R.id.ll_f_3);
                    final LinearLayout ll_f_4 = (LinearLayout) view.findViewById(R.id.ll_f_4);
                    final LinearLayout ll_f_5 = (LinearLayout) view.findViewById(R.id.ll_f_5);
                    final LinearLayout ll_f_6 = (LinearLayout) view.findViewById(R.id.ll_f_6);
                    final LinearLayout ll_f_7 = (LinearLayout) view.findViewById(R.id.ll_f_7);
                    final LinearLayout ll_f_8 = (LinearLayout) view.findViewById(R.id.ll_f_8);
                    final LinearLayout ll_f_9 = (LinearLayout) view.findViewById(R.id.ll_f_9);
                    final LinearLayout ll_f_10 = (LinearLayout) view.findViewById(R.id.ll_f_10);



                    final  TextView zhudui= (TextView) view.findViewById(R.id.zhudui);

                    final  TextView kedui= (TextView) view.findViewById(R.id.kedui);
                    zhudui.setText(modes.get(groupPosition).getMatches().get(childPosition).getHostName());
                    kedui.setText(modes.get(groupPosition).getMatches().get(childPosition).getGuestName());






                    final TextView tv_s_1 = (TextView) view.findViewById(R.id.tv_s_1);

                    tv_s_1.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getS10() + "");
                    L.d("check", modes.get(groupPosition).getMatches().get(childPosition).getSp().isS10() + "");

//
//                    tv_s_1.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isS10());
                    ll_s_1.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isS10());


                    ll_s_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getS10().contains(IP.CANNOT_SELECT)) {
                                ll_s_1.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();

                                return;
                            }

                            ll_s_1.setSelected(!ll_s_1.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setS10(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isS10());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_s_2 = (TextView) view.findViewById(R.id.tv_s_2);
                    tv_s_2.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getS20() + "");

                    ll_s_2.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().iss20());
                    ll_s_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getS20().contains(IP.CANNOT_SELECT)) {
                                ll_s_2.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_2.setSelected(!ll_s_2.isSelected());

                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setIss20(!modes.get(groupPosition).getMatches().get(childPosition).getSp().iss20());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_s_3 = (TextView) view.findViewById(R.id.tv_s_3);
                    tv_s_3.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getS21() + "");
                    ll_s_3.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().iss21());

                    ll_s_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getS21().contains(IP.CANNOT_SELECT)) {
                                ll_s_3.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_3.setSelected(!ll_s_3.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setIss21(!modes.get(groupPosition).getMatches().get(childPosition).getSp().iss21());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_s_4 = (TextView) view.findViewById(R.id.tv_s_4);
                    tv_s_4.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getS30() + "");
                    ll_s_4.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().iss30());

                    ll_s_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getS30().contains(IP.CANNOT_SELECT)) {
                                ll_s_4.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_4.setSelected(!ll_s_4.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setIss30(!modes.get(groupPosition).getMatches().get(childPosition).getSp().iss30());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_s_5 = (TextView) view.findViewById(R.id.tv_s_5);
                    tv_s_5.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getS31() + "");
                    L.d("test", modes.get(groupPosition).getMatches().get(childPosition).getSp().getS31() + "");
                    ll_s_5.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().iss31());
                    ll_s_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getS31().contains(IP.CANNOT_SELECT)) {
                                ll_s_5.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_5.setSelected(!ll_s_5.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setIss31(!modes.get(groupPosition).getMatches().get(childPosition).getSp().iss31());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_s_6 = (TextView) view.findViewById(R.id.tv_s_6);
                    tv_s_6.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getS32() + "");

                    ll_s_6.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().iss32());
                    ll_s_6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getS32().contains(IP.CANNOT_SELECT)) {
                                ll_s_6.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_6.setSelected(!ll_s_6.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setIss32(!modes.get(groupPosition).getMatches().get(childPosition).getSp().iss32());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_s_7 = (TextView) view.findViewById(R.id.tv_s_7);
                    tv_s_7.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getS40() + "");

                    ll_s_7.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().iss40());
                    ll_s_7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getS40().contains(IP.CANNOT_SELECT)) {
                                ll_s_7.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_7.setSelected(!ll_s_7.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setIss40(!modes.get(groupPosition).getMatches().get(childPosition).getSp().iss40());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_s_8 = (TextView) view.findViewById(R.id.tv_s_8);
                    tv_s_8.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getS41() + "");

                    ll_s_8.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().iss41());
                    ll_s_8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getS41().contains(IP.CANNOT_SELECT)) {
                                ll_s_8.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_8.setSelected(!ll_s_8.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setIss41(!modes.get(groupPosition).getMatches().get(childPosition).getSp().iss41());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_s_9 = (TextView) view.findViewById(R.id.tv_s_9);
                    tv_s_9.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getS42() + "");

                    ll_s_9.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().iss42());
                    ll_s_9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getS42().contains(IP.CANNOT_SELECT)) {
                                ll_s_9.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_9.setSelected(!ll_s_9.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setIss42(!modes.get(groupPosition).getMatches().get(childPosition).getSp().iss42());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_s_10 = (TextView) view.findViewById(R.id.tv_s_10);
                    tv_s_10.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getSother() + "");

                    ll_s_10.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().issother());
                    ll_s_10.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getSother().contains(IP.CANNOT_SELECT)) {
                                ll_s_10.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_10.setSelected(!ll_s_10.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setIssother(!modes.get(groupPosition).getMatches().get(childPosition).getSp().issother());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_p_1 = (TextView) view.findViewById(R.id.tv_p_1);
                    tv_p_1.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getP00() + "");
                    ll_p_1.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isP00());

                    ll_p_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getP00().contains(IP.CANNOT_SELECT)) {
                                ll_p_1.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_p_1.setSelected(!tv_p_1.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setP00(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isP00());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_p_2 = (TextView) view.findViewById(R.id.tv_p_2);
                    tv_p_2.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getP11() + "");

                    ll_p_2.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isP11());

                    ll_p_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getP11().contains(IP.CANNOT_SELECT)) {
                                ll_p_2.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_p_2.setSelected(!ll_p_2.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setP11(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isP11());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_p_3 = (TextView) view.findViewById(R.id.tv_p_3);
                    tv_p_3.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getP22() + "");

                    ll_p_3.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isP22());

                    ll_p_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getP22().contains(IP.CANNOT_SELECT)) {
                                ll_p_3.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_p_3.setSelected(!tv_p_3.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setP22(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isP22());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_p_4 = (TextView) view.findViewById(R.id.tv_p_4);
                    tv_p_4.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getP33() + "");
                    ll_p_4.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isP33());


                    ll_p_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getP33().contains(IP.CANNOT_SELECT)) {
                                ll_p_4.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_p_4.setSelected(!ll_p_4.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setP33(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isP33());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_p_5 = (TextView) view.findViewById(R.id.tv_p_5);
                    tv_p_5.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getPother() + "");

                    ll_p_5.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isPother());

                    ll_p_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getPother().contains(IP.CANNOT_SELECT)) {
                                ll_p_5.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_p_5.setSelected(!ll_p_5.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setPother(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isPother());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_f_1 = (TextView) view.findViewById(R.id.tv_f_1);
                    tv_f_1.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getF01() + "");
                    ll_f_1.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isF01());


                    ll_f_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getF01().contains(IP.CANNOT_SELECT)) {
                                ll_f_1.setSelected(false);
                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_f_1.setSelected(!tv_f_1.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setF01(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isF01());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_f_2 = (TextView) view.findViewById(R.id.tv_f_2);
                    tv_f_2.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getF02() + "");

                    ll_f_2.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isF02());
                    ll_f_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getF02().contains(IP.CANNOT_SELECT)) {
                                ll_f_2.setSelected(false);

                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_f_2.setSelected(!ll_f_2.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setF02(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isF02());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_f_3 = (TextView) view.findViewById(R.id.tv_f_3);
                    tv_f_3.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getF12() + "");

                    ll_f_3.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isF12());
                    ll_f_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getF12().contains(IP.CANNOT_SELECT)) {
                                ll_f_3.setSelected(false);

                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_f_3.setSelected(!ll_f_3.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setF12(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isF12());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    final TextView tv_f_4 = (TextView) view.findViewById(R.id.tv_f_4);
                    tv_f_4.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getF03() + "");
                    ll_f_4.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isF03());

                    ll_f_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getF03().contains(IP.CANNOT_SELECT)) {
                                ll_f_4.setSelected(false);

                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }


                            ll_f_4.setSelected(!ll_f_4.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setF03(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isF03());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_f_5 = (TextView) view.findViewById(R.id.tv_f_5);
                    tv_f_5.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getF13() + "");

                    ll_f_5.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isF13());
                    ll_f_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getF13().contains(IP.CANNOT_SELECT)) {
                                ll_f_5.setSelected(false);

                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_f_5.setSelected(!ll_f_5.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setF13(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isF13());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_f_6 = (TextView) view.findViewById(R.id.tv_f_6);
                    tv_f_6.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getF23() + "");
                    ll_f_6.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isF23());

                    ll_f_6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getF23().contains(IP.CANNOT_SELECT)) {
                                ll_f_6.setSelected(false);

                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_f_6.setSelected(!ll_f_6.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setF23(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isF23());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_f_7 = (TextView) view.findViewById(R.id.tv_f_7);
                    tv_f_7.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getF04() + "");

                    ll_f_7.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isF04());
                    ll_f_7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getF04().contains(IP.CANNOT_SELECT)) {
                                ll_f_7.setSelected(false);

                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_f_7.setSelected(!ll_f_7.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setF04(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isF04());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_f_8 = (TextView) view.findViewById(R.id.tv_f_8);
                    tv_f_8.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getF14() + "");
                    ll_f_8.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isF14());

                    ll_f_8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getF14().contains(IP.CANNOT_SELECT)) {
                                ll_f_8.setSelected(false);

                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_f_8.setSelected(!ll_f_8.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setF14(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isF14());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_f_9 = (TextView) view.findViewById(R.id.tv_f_9);
                    tv_f_9.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getF24() + "");
                    ll_f_9.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isF24());

                    ll_f_9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getF24().contains(IP.CANNOT_SELECT)) {
                                ll_f_9.setSelected(false);

                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_f_9.setSelected(!ll_f_9.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setF24(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isF24());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    final TextView tv_f_10 = (TextView) view.findViewById(R.id.tv_f_10);
                    tv_f_10.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getFother() + "");
                    ll_f_10.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isFother());

                    ll_f_10.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getFother().contains(IP.CANNOT_SELECT)) {
                                ll_f_10.setSelected(false);

                                Toast.makeText(context, "此比赛没有比分投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_f_10.setSelected(!ll_f_10.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setFother(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isFother());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });

                    btn_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ll_f_10.setSelected(false);
                            ll_f_9.setSelected(false);
                            ll_f_8.setSelected(false);
                            ll_f_7.setSelected(false);
                            ll_f_6.setSelected(false);
                            ll_f_5.setSelected(false);
                            ll_f_4.setSelected(false);
                            ll_f_7.setSelected(false);
                            ll_f_3.setSelected(false);
                            ll_f_2.setSelected(false);
                            ll_f_1.setSelected(false);

                            ll_p_5.setSelected(false);
                            ll_p_4.setSelected(false);
                            ll_p_3.setSelected(false);
                            ll_p_2.setSelected(false);

                            ll_p_1.setSelected(false);

                            ll_s_10.setSelected(false);
                            ll_s_9.setSelected(false);
                            ll_s_8.setSelected(false);
                            ll_s_7.setSelected(false);
                            ll_s_6.setSelected(false);
                            ll_s_5.setSelected(false);
                            ll_s_4.setSelected(false);
                            ll_s_3.setSelected(false);
                            ll_s_2.setSelected(false);
                            ll_s_1.setSelected(false);


                            modes.get(groupPosition).getMatches().get(childPosition).clearbf();
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();

                        }
                    });


                    Button btnOk = (Button) view.findViewById(R.id.btn_ok);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                }
            });


        } else if (type == 4) {


            HolederBq holederZongfen;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_bqc, parent, false);
                holederZongfen = new HolederBq();

                holederZongfen.chosedtext = (TextView) convertView.findViewById(R.id.chosedtext);

                holederZongfen.tv_host = (TextView) convertView.findViewById(R.id.tv_host);
                holederZongfen.tv_gust = (TextView) convertView.findViewById(R.id.tv_gust);

                holederZongfen.tvMatcherName = (TextView) convertView.findViewById(R.id.liansainame);
                holederZongfen.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
                holederZongfen.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
//                holederZongfen.tv_t5 = (TextView) convertView.findViewById(R.id.tv_t5);
//                holederZongfen.tv_t6 = (TextView) convertView.findViewById(R.id.tv_t6);
//                holederZongfen.tv_t7 = (TextView) convertView.findViewById(R.id.tv_t7);
//                holederZongfen.tv_t8 = (TextView) convertView.findViewById(R.id.tv_t8);

                convertView.setTag(holederZongfen);


            } else {
                holederZongfen = (HolederBq) convertView.getTag();
            }
            holederZongfen.tvNum.setText(modes.get(groupPosition).getMatches().get(childPosition).getTeamId() + "");

            holederZongfen.tvTime.setText(modes.get(groupPosition).getMatches().get(childPosition).getMatchTime() + "截至");
            holederZongfen.tvMatcherName.setText(modes.get(groupPosition).getMatches().get(childPosition).getLeagueName());
            holederZongfen.tv_host.setText(modes.get(groupPosition).getMatches().get(childPosition).getHostName());
            holederZongfen.tv_gust.setText(modes.get(groupPosition).getMatches().get(childPosition).getGuestName());
            holederZongfen.chosedtext.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().bqc());
            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().bqc()) {
                holederZongfen.chosedtext.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getBqcStr());
            } else {
                holederZongfen.chosedtext.setText("点击展开比分投注区");
            }


            holederZongfen.chosedtext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View view = LayoutInflater.from(context).inflate(R.layout.bj_bqc, null);
                    final AlertDialog alertDialog = new AlertDialog.Builder(context).setView(view).show();

                    Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
                    btn_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });
                    Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });


                    final LinearLayout ll_s_1 = (LinearLayout) view.findViewById(R.id.ll_s_1);
                    final LinearLayout ll_s_2 = (LinearLayout) view.findViewById(R.id.ll_s_2);
                    final LinearLayout ll_s_3 = (LinearLayout) view.findViewById(R.id.ll_s_3);
                    final LinearLayout ll_s_4 = (LinearLayout) view.findViewById(R.id.ll_s_4);
                    final LinearLayout ll_s_5 = (LinearLayout) view.findViewById(R.id.ll_s_5);
                    final LinearLayout ll_s_6 = (LinearLayout) view.findViewById(R.id.ll_s_6);
                    final LinearLayout ll_s_7 = (LinearLayout) view.findViewById(R.id.ll_s_7);
                    final LinearLayout ll_s_8 = (LinearLayout) view.findViewById(R.id.ll_s_8);
                    final LinearLayout ll_s_9 = (LinearLayout) view.findViewById(R.id.ll_s_9);


                    final TextView tv_s_1 = (TextView) view.findViewById(R.id.tv_s_1);
                    final TextView tv_s_2 = (TextView) view.findViewById(R.id.tv_s_2);
                    final TextView tv_s_3 = (TextView) view.findViewById(R.id.tv_s_3);
                    final TextView tv_s_4 = (TextView) view.findViewById(R.id.tv_s_4);
                    final TextView tv_s_5 = (TextView) view.findViewById(R.id.tv_s_5);
                    final TextView tv_s_6 = (TextView) view.findViewById(R.id.tv_s_6);
                    final TextView tv_s_7 = (TextView) view.findViewById(R.id.tv_s_7);
                    final TextView tv_s_8 = (TextView) view.findViewById(R.id.tv_s_8);
                    final TextView tv_s_9 = (TextView) view.findViewById(R.id.tv_s_9);

                    final TextView zhudui = (TextView) view.findViewById(R.id.zhudui);
                    final TextView kedui = (TextView) view.findViewById(R.id.kedui);

                    zhudui.setText(modes.get(groupPosition).getMatches().get(childPosition).getHostName());
                    kedui.setText(modes.get(groupPosition).getMatches().get(childPosition).getGuestName());

                    tv_s_1.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getSs());
                    tv_s_2.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getSp());
                    tv_s_3.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getSf());
                    tv_s_4.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getPs());
                    tv_s_5.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getPp());
                    tv_s_6.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getPf());
                    tv_s_7.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getFs());
                    tv_s_8.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getFp());
                    tv_s_9.setText(modes.get(groupPosition).getMatches().get(childPosition).getSp().getFf());


                    ll_s_1.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isSs());
                    ll_s_2.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isSp());
                    ll_s_3.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isSf());
                    ll_s_4.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isPs());
                    ll_s_5.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isPp());
                    ll_s_6.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isPf());
                    ll_s_7.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isFs());
                    ll_s_8.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isFp());
                    ll_s_9.setSelected(modes.get(groupPosition).getMatches().get(childPosition).getSp().isFf());

                    ll_s_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getSp().contains(IP.CANNOT_SELECT)) {

                                Toast.makeText(context, "此比赛没有半全场投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_1.setSelected(!ll_s_1.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setSs(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isSs());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                    ll_s_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getSp().contains(IP.CANNOT_SELECT)) {

                                Toast.makeText(context, "此比赛没有半全场投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_2.setSelected(!ll_s_2.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setSp(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isSp());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });
                    ll_s_3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getSf().contains(IP.CANNOT_SELECT)) {

                                Toast.makeText(context, "此比赛没有半全场投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_3.setSelected(!ll_s_3.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setSf(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isSf());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });
                    ll_s_4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getPs().contains(IP.CANNOT_SELECT)) {

                                Toast.makeText(context, "此比赛没有半全场投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_4.setSelected(!ll_s_4.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setPs(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isPs());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });
                    ll_s_5.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getPp().contains(IP.CANNOT_SELECT)) {

                                Toast.makeText(context, "此比赛没有半全场投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_5.setSelected(!ll_s_5.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setPp(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isPp());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });
                    ll_s_6.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getPf().contains(IP.CANNOT_SELECT)) {

                                Toast.makeText(context, "此比赛没有半全场投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_6.setSelected(!ll_s_6.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setPf(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isPf());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });
                    ll_s_7.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getFs().contains(IP.CANNOT_SELECT)) {

                                Toast.makeText(context, "此比赛没有半全场投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_7.setSelected(!ll_s_7.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setFs(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isFs());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });
                    ll_s_8.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getFp().contains(IP.CANNOT_SELECT)) {

                                Toast.makeText(context, "此比赛没有半全场投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_8.setSelected(!ll_s_8.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setFp(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isFp());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });
                    ll_s_9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (modes.get(groupPosition).getMatches().get(childPosition).getSp().getFf().contains(IP.CANNOT_SELECT)) {

                                Toast.makeText(context, "此比赛没有半全场投注", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ll_s_9.setSelected(!ll_s_9.isSelected());
                            modes.get(groupPosition).getMatches().get(childPosition).getSp().setFf(!modes.get(groupPosition).getMatches().get(childPosition).getSp().isFf());
                            ((IMode) context).setModel(modes);
                            notifyDataSetChanged();
                        }
                    });


                }
            });


        }


        return convertView;
    }


    @Override
    public int getChildType(int groupPosition, int childPosition) {

        return type;
    }

    @Override
    public int getChildTypeCount() {
        return 6;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }


    class ViewHolder {
        TextView tvMatcherName;
        TextView tvNum;
        TextView tvTime;
        TextView tvHostName;
        TextView tvHostNum;
        TextView tvPName;
        TextView tvPNum;
        TextView tvKeName;
        TextView tvKeNum;
        LinearLayout ll_host, ll_vs, ll_gust, ll_rh;

        TextView tv_is_no, tv_rh, tv_is_num;


    }

    class H {
        TextView tv_title;
        TextView count;
    }

    public interface IMode {
        void setModel(List<DataBean> been);
    }

    public void clearhadget() {

        if (modes == null) return;
        for (int i = 0; i < modes.size(); i++) {
            modes.get(i).clearselect();
        }

        ((IMode) context).setModel(modes);

        notifyDataSetChanged();
    }


    class HolederZongfen {
        TextView tv_t1, tv_t2, tv_t3, tv_t4, tv_t5, tv_t6, tv_t7, tv_t8, tv_host, tv_gust;
        TextView tvMatcherName;
        TextView tvNum;
        TextView tvTime;
        LinearLayout ln1, ln2, ln3, ln4, ln5, ln6, ln7, ln8;
    }

    class Holedersxds {
        TextView tv_t1, tv_t2, tv_t3, tv_t4, tv_host, tv_gust;
        TextView tvMatcherName;
        TextView tvNum;
        TextView tvTime;
    }


    class HolederBifen {
        TextView tv_t1, tv_t2, tv_t3, tv_t4, tv_t5, tv_t6, tv_t7, tv_t8, tv_host, tv_gust;
        TextView tvMatcherName;
        TextView tvNum;
        TextView chosedtext;


        TextView tvTime;


        TextView tv_num;
    }

    class HolederBq {
        TextView /*tv_t0, tv_t1, tv_t2, tv_t3, tv_t4, tv_t5, tv_t6, tv_t7, tv_t8, */tv_host, tv_gust, chosedtext;
        TextView tvMatcherName;
        TextView tvNum;
        TextView tvTime;
    }

}
