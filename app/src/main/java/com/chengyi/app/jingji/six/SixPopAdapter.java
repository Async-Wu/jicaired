package com.chengyi.app.jingji.six;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chengyi.R;

import java.util.List;

/**
 * Created by lishangfan on 2016/9/10.
 */
public class SixPopAdapter extends RecyclerView.Adapter<SixPopAdapter.PopHoldeSix> {

    private int bg_res;
    private int txt_bg_res;
    private Context ctx;
    private List<PopMode> modes;
    private Fragment f;

    public SixPopAdapter(Context ctx, List<PopMode> modes) {
        this.ctx = ctx;
        this.modes = modes;
    }



    @Override
    public PopHoldeSix onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PopHoldeSix(LayoutInflater.from(ctx).inflate(R.layout.item_pop_six, parent, false));
    }

    @Override
    public void onBindViewHolder(PopHoldeSix holder, final int position) {

        holder.tv.setSelected(modes.get(position).isSelect());

        holder.tv.setText(modes.get(position).getDate());

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
            tv = (TextView) itemView.findViewById(R.id.btn1);
            if (bg_res != 0) {
                tv.setBackgroundResource(bg_res);
            }
            if (txt_bg_res != 0) {
                tv.setTextColor(txt_bg_res);
            }

        }
    }
    public interface IGetDate {
        void getDate(List<PopMode> pop);


    }
}
