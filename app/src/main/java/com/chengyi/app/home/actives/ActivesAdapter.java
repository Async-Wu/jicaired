package com.chengyi.app.home.actives;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.chengyi.R;

import java.util.List;

/**
 * Created by lishangfan on 2016/10/10.
 */
public class ActivesAdapter extends RecyclerView.Adapter<ActivesAdapter.ActivesHold> {
    private List<ActivesMode> mmodes;
    private Context context;

    public ActivesAdapter(List<ActivesMode> mmodes, Context context) {
        this.mmodes = mmodes;
        this.context = context;
    }

    @Override
    public ActivesHold onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ActivesHold(LayoutInflater.from(context).inflate(R.layout.item_actives, parent, false));
    }

    @Override
    public void onBindViewHolder(ActivesHold holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ActivesDetailActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mmodes == null || mmodes.isEmpty() ? 0 : mmodes.size();
    }

    class ActivesHold extends RecyclerView.ViewHolder {
        private ImageView ivActives;

        public ActivesHold(View itemView) {
            super(itemView);
            ivActives = (ImageView) itemView.findViewById(R.id.iv_actives);
        }
    }
}
