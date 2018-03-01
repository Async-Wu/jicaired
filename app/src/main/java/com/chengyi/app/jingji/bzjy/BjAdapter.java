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
public class BjAdapter extends BaseAdapter {

    private List<MatchBean> modes;
    private Context context;
    private int type;

    public BjAdapter(List<MatchBean> modes, Context context, int type) {
        this.modes = modes;
        this.context = context;
        this.type = type;
    }

    @Override
    public int getCount() {
        return modes.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 6;
    }

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (type == 0) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_bz_spf, parent, false);
                viewHolder = new ViewHolder();

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

                viewHolder.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
                viewHolder.tv_is_no = (TextView) convertView.findViewById(R.id.tv_is_no);
                viewHolder.tv_rh = (TextView) convertView.findViewById(R.id.tv_rh);
                viewHolder.tv_is_num = (TextView) convertView.findViewById(R.id.tv_is_num);


                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (isDelete()) {
                viewHolder.ivDelete.setVisibility(View.VISIBLE);
            } else {
                viewHolder.ivDelete.setVisibility(View.INVISIBLE);
            }

            viewHolder.tvHostName.setText(modes.get(position).getHostName());
            viewHolder.tvHostName.setText(modes.get(position).getHostName());
            viewHolder.tvPName.setText(modes.get(position).getRate() + "");


            viewHolder.tvPNum.setText(modes.get(position).getHostName());
            viewHolder.tvKeName.setText(modes.get(position).getGuestName());

            if (modes.get(position).getSp().getRqPing().contains(IP.CANNOT_SELECT)) {
                viewHolder.tvPNum.setText(IP.CANNOT_SELECT);
            } else {
                viewHolder.tvPNum.setText("平  " + modes.get(position).getSp().getRqPing() + "");

            }


            if (modes.get(position).getSp().getRqSheng().contains(IP.CANNOT_SELECT)) {
                viewHolder.tvHostNum.setText(IP.CANNOT_SELECT);
            } else {
                viewHolder.tvHostNum.setText("胜  " + modes.get(position).getSp().getRqSheng() + "");

            }


            if (modes.get(position).getSp().getRqFu().contains(IP.CANNOT_SELECT)) {
                viewHolder.tvKeNum.setText(IP.CANNOT_SELECT);
            } else {
                viewHolder.tvKeNum.setText("负  " + modes.get(position).getSp().getRqFu() + "");

            }

            if (modes.get(position).isHost()) {
                viewHolder.ll_host.setSelected(true);
            } else {
                viewHolder.ll_host.setSelected(false);
            }


            if (modes.get(position).isVs()) {
                viewHolder.ll_vs.setSelected(true);
            } else {
                viewHolder.ll_vs.setSelected(false);
            }

            if (modes.get(position).isGust()) {
                viewHolder.ll_gust.setSelected(true);
            } else {
                viewHolder.ll_gust.setSelected(false);
            }


            viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modes.size() < 3) {
                        Toast.makeText(context, "不能删除了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int j = modes.get(position).getId();
                    modes.remove(position);
                    ((IDelete) context).delete(modes, j);
                    notifyDataSetChanged();
                }

            });


        } else if (type == 1)

        {
            HolederZongfen holederZongfen;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_bz_cart_zsff, parent, false);

                holederZongfen = new HolederZongfen();
                holederZongfen.tv_t1 = (TextView) convertView.findViewById(R.id.tv_t1);
                holederZongfen.tv_t2 = (TextView) convertView.findViewById(R.id.tv_t2);
                holederZongfen.tv_t3 = (TextView) convertView.findViewById(R.id.tv_t3);
                holederZongfen.tv_t4 = (TextView) convertView.findViewById(R.id.tv_t4);
                holederZongfen.tv_t5 = (TextView) convertView.findViewById(R.id.tv_t5);
                holederZongfen.tv_t6 = (TextView) convertView.findViewById(R.id.tv_t6);
                holederZongfen.tv_t7 = (TextView) convertView.findViewById(R.id.tv_t7);
                holederZongfen.tv_t8 = (TextView) convertView.findViewById(R.id.tv_t8);
                holederZongfen.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);

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


            if (isDelete()) {
                holederZongfen.ivDelete.setVisibility(View.VISIBLE);
            } else {
                holederZongfen.ivDelete.setVisibility(View.INVISIBLE);
            }


            holederZongfen.tvNum.setText(modes.get(position).getTeamId() + "");

            holederZongfen.tvMatcherName.setText(modes.get(position).getLeagueName());

            holederZongfen.tvNum.setText(modes.get(position).getTeamId() + "");
            holederZongfen.tv_host.setText(modes.get(position).getHostName());
            holederZongfen.tv_gust.setText(modes.get(position).getGuestName());
            holederZongfen.tvTime.setText(modes.get(position).getMatchTime() + "截至");

            holederZongfen.tv_t1.setText(modes.get(position).getSp().getT0() + "");
            holederZongfen.tv_t2.setText(modes.get(position).getSp().getT1() + "");
            holederZongfen.tv_t3.setText(modes.get(position).getSp().getT2() + "");
            holederZongfen.tv_t4.setText(modes.get(position).getSp().getT3() + "");
            holederZongfen.tv_t5.setText(modes.get(position).getSp().getT4() + "");
            holederZongfen.tv_t6.setText(modes.get(position).getSp().getT5() + "");
            holederZongfen.tv_t7.setText(modes.get(position).getSp().getT6() + "");
            holederZongfen.tv_t8.setText(modes.get(position).getSp().getT7() + "");


            holederZongfen.ln1.setSelected(modes.get(position).getSp().isT1());
            holederZongfen.ln2.setSelected(modes.get(position).getSp().isT2());
            holederZongfen.ln3.setSelected(modes.get(position).getSp().isT3());
            holederZongfen.ln4.setSelected(modes.get(position).getSp().isT4());
            holederZongfen.ln5.setSelected(modes.get(position).getSp().isT5());
            holederZongfen.ln6.setSelected(modes.get(position).getSp().isT6());
            holederZongfen.ln7.setSelected(modes.get(position).getSp().isT7());
            holederZongfen.ln8.setSelected(modes.get(position).getSp().isT8());


            holederZongfen.tvMatcherName.setText(modes.get(position).getLeagueName());

            holederZongfen.tvMatcherName.setText(modes.get(position).getLeagueName());
            holederZongfen.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modes.size() < 3) {
                        Toast.makeText(context, "不能删除了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int j = modes.get(position).getId();
                    modes.remove(position);
                    ((IDelete) context).delete(modes, j);
                    notifyDataSetChanged();
                }

            });


        } else if (type == 2)

        {
            Holedersxds holederZongfen;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_foot_cart_sxds, parent, false);
                holederZongfen = new Holedersxds();
                holederZongfen.tv_t1 = (TextView) convertView.findViewById(R.id.tv_t1);
                holederZongfen.tv_t2 = (TextView) convertView.findViewById(R.id.tv_t2);
                holederZongfen.tv_t3 = (TextView) convertView.findViewById(R.id.tv_t3);
                holederZongfen.tv_t4 = (TextView) convertView.findViewById(R.id.tv_t4);


                holederZongfen.tv_host = (TextView) convertView.findViewById(R.id.tv_matcher_name);
                holederZongfen.tv_gust = (TextView) convertView.findViewById(R.id.tv_ke_name);

                holederZongfen.tvMatcherName = (TextView) convertView.findViewById(R.id.tv_matcher_name);
                holederZongfen.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
                holederZongfen.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                holederZongfen.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
                convertView.setTag(holederZongfen);
            } else {
                holederZongfen = (Holedersxds) convertView.getTag();
            }
            holederZongfen.tv_host.setText(modes.get(position).getHostName());
            holederZongfen.tv_gust.setText(modes.get(position).getGuestName());
            holederZongfen.tvMatcherName.setText(modes.get(position).getLeagueName());
            holederZongfen.tv_t1.setText("上单 " + modes.get(position).getSp().getShangD() + "");
            holederZongfen.tv_t2.setText("下单 " + modes.get(position).getSp().getShangS() + "");
            holederZongfen.tv_t3.setText("上双 " + modes.get(position).getSp().getXiaD() + "");
            holederZongfen.tv_t4.setText("下双 " + modes.get(position).getSp().getXiaS() + "");
            holederZongfen.tvNum.setText(modes.get(position).getSp().getTeamId() + "");
            if (isDelete()) {
                holederZongfen.ivDelete.setVisibility(View.VISIBLE);
            } else {
                holederZongfen.ivDelete.setVisibility(View.INVISIBLE);
            }
            if (modes.get(position).getSp().isShangD()) {
                holederZongfen.tv_t1.setSelected(true);
            } else {
                holederZongfen.tv_t1.setSelected(false);
            }


            if (modes.get(position).getSp().isShangS()) {
                holederZongfen.tv_t2.setSelected(true);
            } else {
                holederZongfen.tv_t2.setSelected(false);
            }

            if (modes.get(position).getSp().isXiaD()) {
                holederZongfen.tv_t3.setSelected(true);
            } else {
                holederZongfen.tv_t3.setSelected(false);
            }
            if (modes.get(position).getSp().isXiaS()) {
                holederZongfen.tv_t4.setSelected(true);
            } else {
                holederZongfen.tv_t4.setSelected(false);
            }

            holederZongfen.tvTime.setText(modes.get(position).getMatchTime() + "截至");
            holederZongfen.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modes.size() < 3) {
                        Toast.makeText(context, "不能删除了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int j = modes.get(position).getId();
                    modes.remove(position);
                    ((IDelete) context).delete(modes, j);
                    notifyDataSetChanged();
                }

            });


        } else if (type == 3)

        {
            final HolederBifen bifen;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_bz_cart_biff, parent, false);
                bifen = new HolederBifen();

                bifen.chosedtext = (TextView) convertView.findViewById(R.id.chosedtext);
                bifen.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);


                bifen.tv_host = (TextView) convertView.findViewById(R.id.duiwu1);
                bifen.tv_gust = (TextView) convertView.findViewById(R.id.duiwu2);

                bifen.tvMatcherName = (TextView) convertView.findViewById(R.id.liansainame);

                convertView.setTag(bifen);
            } else {
                bifen = (HolederBifen) convertView.getTag();
            }
            bifen.tvMatcherName.setText(modes.get(position).getLeagueName());

            bifen.tv_host.setText(modes.get(position).getHostName());
            bifen.tv_gust.setText(modes.get(position).getGuestName());

            if (!TextUtils.isEmpty(modes.get(position).getBifenShow())) {
                bifen.chosedtext.setSelected(true);
                bifen.chosedtext.setText(modes.get(position).getBifenShow());

            } else {
                bifen.chosedtext.setText("点击展开比分投注区");
                bifen.chosedtext.setSelected(false);
            }


            if (isDelete()) {
                bifen.ivDelete.setVisibility(View.VISIBLE);
            } else {
                bifen.ivDelete.setVisibility(View.INVISIBLE);
            }
            bifen.tvMatcherName.setText(modes.get(position).getLeagueName());

            bifen.tv_host.setText(modes.get(position).getHostName());
            bifen.tv_gust.setText(modes.get(position).getGuestName());


            bifen.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (modes.size() < 3) {
                        Toast.makeText(context, "不能删除了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int j = modes.get(position).getId();
                    modes.remove(position);
                    ((IDelete) context).delete(modes, j);
                    notifyDataSetChanged();
                }
            });


            bifen.chosedtext.setText(modes.get(position).getBifenShow());

            bifen.chosedtext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


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


                    final TextView zhudui = (TextView) view.findViewById(R.id.zhudui);

                    final TextView kedui = (TextView) view.findViewById(R.id.kedui);
                    zhudui.setText(modes.get(position).getHostName());
                    kedui.setText(modes.get(position).getGuestName());

                    final TextView tv_s_1 = (TextView) view.findViewById(R.id.tv_s_1);

                    tv_s_1.setText(modes.get(position).getSp().getS10() + "");

                    ll_s_1.setSelected(modes.get(position).getSp().isS10());


                    final TextView tv_s_2 = (TextView) view.findViewById(R.id.tv_s_2);
                    tv_s_2.setText(modes.get(position).getSp().getS20() + "");

                    ll_s_2.setSelected(modes.get(position).getSp().iss20());


                    final TextView tv_s_3 = (TextView) view.findViewById(R.id.tv_s_3);
                    tv_s_3.setText(modes.get(position).getSp().getS21() + "");
                    ll_s_3.setSelected(modes.get(position).getSp().iss21());


                    final TextView tv_s_4 = (TextView) view.findViewById(R.id.tv_s_4);
                    tv_s_4.setText(modes.get(position).getSp().getS30() + "");
                    ll_s_4.setSelected(modes.get(position).getSp().iss30());


                    final TextView tv_s_5 = (TextView) view.findViewById(R.id.tv_s_5);
                    tv_s_5.setText(modes.get(position).getSp().getS31() + "");
                    L.d("test", modes.get(position).getSp().getS31() + "");
                    ll_s_5.setSelected(modes.get(position).getSp().iss31());

                    final TextView tv_s_6 = (TextView) view.findViewById(R.id.tv_s_6);
                    tv_s_6.setText(modes.get(position).getSp().getS32() + "");

                    ll_s_6.setSelected(modes.get(position).getSp().iss32());


                    final TextView tv_s_7 = (TextView) view.findViewById(R.id.tv_s_7);
                    tv_s_7.setText(modes.get(position).getSp().getS40() + "");

                    ll_s_7.setSelected(modes.get(position).getSp().iss40());

                    final TextView tv_s_8 = (TextView) view.findViewById(R.id.tv_s_8);
                    tv_s_8.setText(modes.get(position).getSp().getS41() + "");

                    ll_s_8.setSelected(modes.get(position).getSp().iss41());


                    final TextView tv_s_9 = (TextView) view.findViewById(R.id.tv_s_9);
                    tv_s_9.setText(modes.get(position).getSp().getS42() + "");

                    ll_s_9.setSelected(modes.get(position).getSp().iss42());


                    final TextView tv_s_10 = (TextView) view.findViewById(R.id.tv_s_10);
                    tv_s_10.setText(modes.get(position).getSp().getSother() + "");

                    ll_s_10.setSelected(modes.get(position).getSp().issother());


                    final TextView tv_p_1 = (TextView) view.findViewById(R.id.tv_p_1);
                    tv_p_1.setText(modes.get(position).getSp().getP00() + "");
                    ll_p_1.setSelected(modes.get(position).getSp().isP00());


                    final TextView tv_p_2 = (TextView) view.findViewById(R.id.tv_p_2);
                    tv_p_2.setText(modes.get(position).getSp().getP11() + "");

                    ll_p_2.setSelected(modes.get(position).getSp().isP11());


                    final TextView tv_p_3 = (TextView) view.findViewById(R.id.tv_p_3);
                    tv_p_3.setText(modes.get(position).getSp().getP22() + "");

                    ll_p_3.setSelected(modes.get(position).getSp().isP22());


                    final TextView tv_p_4 = (TextView) view.findViewById(R.id.tv_p_4);
                    tv_p_4.setText(modes.get(position).getSp().getP33() + "");
                    ll_p_4.setSelected(modes.get(position).getSp().isP33());


                    final TextView tv_p_5 = (TextView) view.findViewById(R.id.tv_p_5);
                    tv_p_5.setText(modes.get(position).getSp().getPother() + "");

                    ll_p_5.setSelected(modes.get(position).getSp().isPother());


                    final TextView tv_f_1 = (TextView) view.findViewById(R.id.tv_f_1);
                    tv_f_1.setText(modes.get(position).getSp().getF01() + "");
                    ll_f_1.setSelected(modes.get(position).getSp().isF01());


                    final TextView tv_f_2 = (TextView) view.findViewById(R.id.tv_f_2);
                    tv_f_2.setText(modes.get(position).getSp().getF02() + "");

                    ll_f_2.setSelected(modes.get(position).getSp().isF02());


                    final TextView tv_f_3 = (TextView) view.findViewById(R.id.tv_f_3);
                    tv_f_3.setText(modes.get(position).getSp().getF12() + "");

                    ll_f_3.setSelected(modes.get(position).getSp().isF12());


                    final TextView tv_f_4 = (TextView) view.findViewById(R.id.tv_f_4);
                    tv_f_4.setText(modes.get(position).getSp().getF03() + "");
                    ll_f_4.setSelected(modes.get(position).getSp().isF03());


                    final TextView tv_f_5 = (TextView) view.findViewById(R.id.tv_f_5);
                    tv_f_5.setText(modes.get(position).getSp().getF13() + "");

                    ll_f_5.setSelected(modes.get(position).getSp().isF13());

                    final TextView tv_f_6 = (TextView) view.findViewById(R.id.tv_f_6);
                    tv_f_6.setText(modes.get(position).getSp().getF23() + "");
                    ll_f_6.setSelected(modes.get(position).getSp().isF23());


                    final TextView tv_f_7 = (TextView) view.findViewById(R.id.tv_f_7);
                    tv_f_7.setText(modes.get(position).getSp().getF04() + "");

                    ll_f_7.setSelected(modes.get(position).getSp().isF04());


                    final TextView tv_f_8 = (TextView) view.findViewById(R.id.tv_f_8);
                    tv_f_8.setText(modes.get(position).getSp().getF14() + "");
                    ll_f_8.setSelected(modes.get(position).getSp().isF14());


                    final TextView tv_f_9 = (TextView) view.findViewById(R.id.tv_f_9);
                    tv_f_9.setText(modes.get(position).getSp().getF24() + "");
                    ll_f_9.setSelected(modes.get(position).getSp().isF24());


                    final TextView tv_f_10 = (TextView) view.findViewById(R.id.tv_f_10);
                    tv_f_10.setText(modes.get(position).getSp().getFother() + "");
                    ll_f_10.setSelected(modes.get(position).getSp().isFother());


                    btn_cancle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alertDialog.dismiss();

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


        } else if (type == 4)

        {

            /***
             *      "ff": 0,
             "fp": 3,
             "fs": 0,
             "pf": 0,
             "pp": 3,
             "ps": 0,
             "sf": 0,
             "sp": 3,
             "ss": 0,
             */
            HolederBq holederZongfen;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_bz_cart_bqc, parent, false);
                holederZongfen = new HolederBq();
                holederZongfen.chosedtext = (TextView) convertView.findViewById(R.id.chosedtext);

                holederZongfen.tv_host = (TextView) convertView.findViewById(R.id.tv_host);
                holederZongfen.tv_gust = (TextView) convertView.findViewById(R.id.tv_gust);

                holederZongfen.tvMatcherName = (TextView) convertView.findViewById(R.id.liansainame);
                holederZongfen.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
                holederZongfen.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
                holederZongfen.ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);

                convertView.setTag(holederZongfen);


            } else {
                holederZongfen = (HolederBq) convertView.getTag();
            }
            if (isDelete()) {
                holederZongfen.ivDelete.setVisibility(View.VISIBLE);
            } else {
                holederZongfen.ivDelete.setVisibility(View.INVISIBLE);
            }


            holederZongfen.tvNum.setText(modes.get(position).getTeamId() + "");

            holederZongfen.tvTime.setText(modes.get(position).getMatchTime() + "截至");
            holederZongfen.tvMatcherName.setText(modes.get(position).getLeagueName());
            holederZongfen.tv_host.setText(modes.get(position).getHostName());
            holederZongfen.tv_gust.setText(modes.get(position).getGuestName());
            holederZongfen.chosedtext.setSelected(modes.get(position).getSp().bqc());
            if (modes.get(position).getSp().bqc()) {
                holederZongfen.chosedtext.setText(modes.get(position).getSp().getBqcStr());
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

                    zhudui.setText(modes.get(position).getHostName());
                    kedui.setText(modes.get(position).getGuestName());

                    tv_s_1.setText(modes.get(position).getSp().getSs());
                    tv_s_2.setText(modes.get(position).getSp().getSp());
                    tv_s_3.setText(modes.get(position).getSp().getSf());
                    tv_s_4.setText(modes.get(position).getSp().getPs());
                    tv_s_5.setText(modes.get(position).getSp().getPp());
                    tv_s_6.setText(modes.get(position).getSp().getPf());
                    tv_s_7.setText(modes.get(position).getSp().getFs());
                    tv_s_8.setText(modes.get(position).getSp().getFp());
                    tv_s_9.setText(modes.get(position).getSp().getFf());


                    ll_s_1.setSelected(modes.get(position).getSp().isSs());
                    ll_s_2.setSelected(modes.get(position).getSp().isSp());
                    ll_s_3.setSelected(modes.get(position).getSp().isSf());
                    ll_s_4.setSelected(modes.get(position).getSp().isPs());
                    ll_s_5.setSelected(modes.get(position).getSp().isPp());
                    ll_s_6.setSelected(modes.get(position).getSp().isPf());
                    ll_s_7.setSelected(modes.get(position).getSp().isFs());
                    ll_s_8.setSelected(modes.get(position).getSp().isFp());
                    ll_s_9.setSelected(modes.get(position).getSp().isFf());}
                });
            
            
            
            
            
            
           
            holederZongfen.ivDelete.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick (View v){
                    if (modes.size() < 3) {
                        Toast.makeText(context, "不能删除了", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int j = modes.get(position).getId();
                    modes.remove(position);
                    ((IDelete) context).delete(modes, j);
                    notifyDataSetChanged();
                }

                });

            }

            return convertView;
        }


        /**
         * if (modes.size()<3){
         * Toast.makeText(context, "不能删除了", Toast.LENGTH_SHORT).show();
         * return;
         * }
         * int j=modes.get(position).getId();
         * modes.remove(position);
         * ((IDelete) context).delete(modes,j);
         * notifyDataSetChanged();
         */

        private boolean delete;

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }


    class BaseHolde {
        ImageView ivDelete;

    }


    class ViewHolder extends BaseHolde {

        TextView tvHostName;
        TextView tvHostNum;
        TextView tvPName;
        TextView tvPNum;
        TextView tvKeName;
        TextView tvKeNum;
        LinearLayout ll_host, ll_vs, ll_gust, ll_rh;

        TextView tv_is_no, tv_rh, tv_is_num;


    }

    class HolederZongfen extends BaseHolde {
        TextView tv_t1, tv_t2, tv_t3, tv_t4, tv_t5, tv_t6, tv_t7, tv_t8, tv_host, tv_gust;
        TextView tvMatcherName;
        TextView tvNum;
        TextView tvTime;
        LinearLayout ln1, ln2, ln3, ln4, ln5, ln6, ln7, ln8;
    }

    class Holedersxds extends BaseHolde {
        TextView tv_t1, tv_t2, tv_t3, tv_t4, tv_host, tv_gust;
        TextView tvMatcherName;
        TextView tvNum;
        TextView tvTime;
    }


    class HolederBifen extends BaseHolde {
        TextView tv_host, tv_gust;
        TextView tvMatcherName;
        TextView chosedtext;
    }

    class HolederBq extends BaseHolde {

        TextView tvMatcherName;
        TextView tvNum;
        TextView tvTime;
        TextView chosedtext;
        public TextView tv_host;
        public TextView tv_gust;
    }

    interface IDelete {

        void delete(List<MatchBean> modes, int delete);

    }
}
  
