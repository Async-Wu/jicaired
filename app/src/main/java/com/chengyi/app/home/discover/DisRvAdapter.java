package com.chengyi.app.home.discover;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.web.WebViewActivity;

import java.util.List;

/**
 * Created by lishangfan on 2016/10/10.
 */
public class DisRvAdapter extends RecyclerView.Adapter<DisRvAdapter.DisHold> {
    private List<DisMode> mModes;
    private Context context;

    public DisRvAdapter(List<DisMode> mModes, Context context) {
        this.mModes = mModes;
        this.context = context;
    }

    @Override
    public DisHold onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DisHold(LayoutInflater.from(context).inflate(R.layout.item_dis_list, parent, false));
    }

    @Override
    public void onBindViewHolder(DisHold holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, WebViewActivity.class).putExtra("data","").putExtra("flag",1));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mModes == null  ? 0 : mModes.size();
    }

    class DisHold extends RecyclerView.ViewHolder {
        private TextView tvDis;

        public DisHold(View itemView) {
            super(itemView);
            tvDis = (TextView) itemView.findViewById(R.id.tv_dis);
        }
    }
}
