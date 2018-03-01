package com.chengyi.app.pop;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.jingji.six.PopMode;

import java.util.List;

/**
 * Created by lishangfan on 2016/9/20.
 */
public class Title_pop extends RecyclerView.Adapter<Title_pop.PopHoldeSix> {


    private Context ctx;
    private List<PopMode> modes;
    private Fragment f;
    public Title_pop(Fragment f, List<PopMode> pop_list) {
        this.ctx = f.getActivity();
        this.f = f;
        this.modes = pop_list;
    }

    @Override
    public PopHoldeSix onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PopHoldeSix(LayoutInflater.from(ctx).inflate(R.layout.item_red_pop, parent, false));
    }

    @Override
    public void onBindViewHolder(PopHoldeSix holder, final int position) {

        holder.tv.setSelected(modes.get(position).isSelect());

        holder.tv.setText(modes.get(position).getDate().replaceAll("期",""));

        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < modes.size(); i++) {
                    modes.get(i).setSelect(false);
                }
                modes.get(position).setSelect(true);

                notifyDataSetChanged();
                if (f != null) {
                    ((IGetDate) f).getDate(modes);
                } else {
                    ((IGetDate) ctx).getDate(modes);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return modes == null ? 0 : modes.size();
    }

    class PopHoldeSix extends RecyclerView.ViewHolder {
        TextView tv;

        public PopHoldeSix(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_red_pop);
        }
    }

    public interface IGetDate {
        void getDate(List<PopMode> pop);


    }
}