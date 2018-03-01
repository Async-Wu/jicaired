package com.chengyi.app.num.lottery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.wanfa.AbsWanfa;

import java.util.List;

/**
 * Created by lishangfan on 2016/11/24.
 */
public class LinearPopAdapter extends RecyclerView.Adapter<LinearPopAdapter.LinearPopHolder> {

    private Context ctx;
    private List<AbsWanfa> mMode;

    public LinearPopAdapter(Context ctx, List<AbsWanfa> mMode) {
        this.ctx = ctx;
        this.mMode = mMode;
    }

    @Override
    public LinearPopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinearPopHolder(LayoutInflater.from(ctx).inflate(R.layout.item_pop_linear, parent, false));
    }

    @Override
    public void onBindViewHolder(LinearPopHolder holder, int position) {
        holder.textView.setText(mMode.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mMode == null ? 0 : mMode.size();
    }

    class LinearPopHolder extends RecyclerView.ViewHolder {

        TextView textView;


        public LinearPopHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.wanfa_name);
        }
    }
}
