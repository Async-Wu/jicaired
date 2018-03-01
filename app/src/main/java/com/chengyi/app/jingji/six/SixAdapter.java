package com.chengyi.app.jingji.six;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.chengyi.R;
import com.chengyi.app.util.L;

import java.util.List;

/**
 * Created by lishangfan on 2016/9/9.
 */
public class SixAdapter extends RecyclerView.Adapter<SixAdapter.SixHolde> {
    private Activity ctx;
    private List<SixMode> mode;
    private int type = 0;
    private int delete;

    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
        notifyDataSetChanged();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SixAdapter(Activity ctx, List<SixMode> mode) {
        this.ctx = ctx;
        this.mode = mode;
    }

    @Override
    public SixHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.item_six, parent, false);
        return new SixHolde(view);
    }

    @Override
    public void onBindViewHolder(final SixHolde holder, final int position) {

        holder.tv_t1.setText("半主胜");
        holder.tv_t2.setText("半主平");
        holder.tv_t3.setText("半主负");
        holder.tv_t5.setText("全主胜");
        holder.tv_t6.setText("全主平");
        holder.tv_t7.setText("全主负");

        holder.tv_matcher_name.setText(String.valueOf(position + 1));
        holder.tv_time.setText(mode.get(position).getData());
        holder.tv_num.setText(mode.get(position).getTime());
        holder.tv_host.setText(mode.get(position).getHostName());
        holder.tv_gust.setText(mode.get(position).getGuestName());


        holder.tv_t1.setSelected(mode.get(position).isBzusg());
        holder.tv_t2.setSelected(mode.get(position).isBzupy());
        holder.tv_t3.setSelected(mode.get(position).isBzufu());
        holder.tv_t5.setSelected(mode.get(position).isQzusg());
        holder.tv_t6.setSelected(mode.get(position).isQzupy());
        holder.tv_t7.setSelected(mode.get(position).isQzufu());
        if (delete == 1) {
            holder.iv_delet_b.setVisibility(View.VISIBLE);
            holder.iv_delet_q.setVisibility(View.VISIBLE);
        } else  {
            holder.iv_delet_b.setVisibility(View.INVISIBLE);
            holder.iv_delet_q.setVisibility(View.INVISIBLE);
        }

        if (type==1){
            holder.ll_left.setVisibility(View.GONE);
            holder.v.setVisibility(View.GONE);
            holder.ll_left_1.setVisibility(View.GONE);

        }else {
            holder.ll_left.setVisibility(View.VISIBLE);
            holder.ll_left_1.setVisibility(View.VISIBLE);
            holder.v.setVisibility(View.VISIBLE);
            holder.iv_delet_b.setVisibility(View.GONE);
            holder.iv_delet_q.setVisibility(View.GONE);
        }




        holder.iv_delet_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode.get(position).clearB();
                ((ISixGet) ctx).notifyDate(mode, position, false);
                notifyDataSetChanged();
            }
        });

        holder.iv_delet_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mode.get(position).clearQ();
                ((ISixGet) ctx).notifyDate(mode, position, true);
                notifyDataSetChanged();
            }
        });


        holder.tv_t1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (type == 1) return;
                if (type == 2) {
                    Toast.makeText(ctx, "比赛已截至", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tv_t1.setSelected(!holder.tv_t1.isSelected());
                L.d(mode.toString());
                mode.get(position).setBzusg(holder.tv_t1.isSelected());
                ((ISixGet) ctx).notifyDate(mode, position, false);
                notifyDataSetChanged();


            }
        });

        holder.tv_t2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (type == 1) return;
                if (type == 2) {
                    Toast.makeText(ctx, "比赛已截至", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tv_t2.setSelected(!holder.tv_t2.isSelected());

                L.d(mode.toString());
                mode.get(position).setBzupy(holder.tv_t2.isSelected());
                ((ISixGet) ctx).notifyDate(mode, position, false);
                notifyDataSetChanged();

            }
        });
        holder.tv_t3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (type == 1) return;
                if (type == 2) {
                    Toast.makeText(ctx, "比赛已截至", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tv_t3.setSelected(!holder.tv_t3.isSelected());
                L.d(mode.toString());
                mode.get(position).setBzufu(holder.tv_t3.isSelected());
                ((ISixGet) ctx).notifyDate(mode, position, false);
                notifyDataSetChanged();
            }
        });
        holder.tv_t5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (type == 1) return;
                if (type == 2) {
                    Toast.makeText(ctx, "比赛已截至", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tv_t5.setSelected(!holder.tv_t5.isSelected());
                L.d(mode.toString());
                mode.get(position).setQzusg(holder.tv_t5.isSelected());
                ((ISixGet) ctx).notifyDate(mode, position, false);
                notifyDataSetChanged();

            }
        });
        holder.tv_t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) return;
                if (type == 2) {
                    Toast.makeText(ctx, "比赛已截至", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tv_t6.setSelected(!holder.tv_t6.isSelected());
                L.d(mode.toString());
                mode.get(position).setQzupy(holder.tv_t6.isSelected());
                ((ISixGet) ctx).notifyDate(mode, position, false);
                notifyDataSetChanged();

            }
        });
        holder.tv_t7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) return;
                if (type == 2) {
                    Toast.makeText(ctx, "比赛已截至", Toast.LENGTH_SHORT).show();
                    return;
                }
                holder.tv_t7.setSelected(!holder.tv_t7.isSelected());
                L.d(mode.toString());
                mode.get(position).setQzufu(holder.tv_t7.isSelected());
                ((ISixGet) ctx).notifyDate(mode, position, false);
                notifyDataSetChanged();

            }
        });


    }

    @Override
    public int getItemCount() {
        return mode != null ? mode.size() : 0;
//        return 6;
    }

    class SixHolde extends RecyclerView.ViewHolder {
        TextView tv_t1, tv_t2, tv_t3, tv_t5, tv_t6, tv_t7;
        ImageView iv_delet_b, iv_delet_q;
        TextView tv_matcher_name, tv_time, tv_host, tv_gust, tv_num;
LinearLayout ll_left_1,ll_left;
View v;
        public SixHolde(View itemView) {
            super(itemView);
            v=itemView.findViewById(R.id.v);
            tv_t1 = (TextView) itemView.findViewById(R.id.tv_t1);
            tv_t2 = (TextView) itemView.findViewById(R.id.tv_t2);
            tv_t3 = (TextView) itemView.findViewById(R.id.tv_t3);
            tv_t5 = (TextView) itemView.findViewById(R.id.tv_t5);
            tv_t6 = (TextView) itemView.findViewById(R.id.tv_t6);
            tv_t7 = (TextView) itemView.findViewById(R.id.tv_t7);
            iv_delet_b = (ImageView) itemView.findViewById(R.id.iv_delet_b);
            iv_delet_q = (ImageView) itemView.findViewById(R.id.iv_delet_q);
            ll_left_1= (LinearLayout) itemView.findViewById(R.id.ll_left_1);
            ll_left= (LinearLayout) itemView.findViewById(R.id.ll_left);


            tv_matcher_name = (TextView) itemView.findViewById(R.id.tv_matcher_name);

            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_host = (TextView) itemView.findViewById(R.id.tv_host);
            tv_gust = (TextView) itemView.findViewById(R.id.tv_gust);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
        }
    }


    interface ISixGet {
        void notifyDate(List<SixMode> sixModes, int pos, boolean flag);
    }


}
