package com.chengyi.app.home.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chengyi.R;

/**
 * Created by lishangfan on 2016/10/11.
 */
public class PageAdapter extends RecyclerView.Adapter<PageAdapter.PageHolde> {

    private Context ctx;
    private PageMode mmodes;
    private IPageSelect pageSelect;

    public PageAdapter(Context ctx, PageMode mmodes, IPageSelect pageSelect) {
        this.ctx = ctx;
        this.mmodes = mmodes;
        this.pageSelect = pageSelect;
    }

    @Override
    public PageHolde onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PageHolde(LayoutInflater.from(ctx).inflate(R.layout.item_page, parent, false));
    }

    @Override
    public void onBindViewHolder(PageHolde holder, final int position) {
        holder.tvPage.setText("第" + (position + 1 )+ "页");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pageSelect != null) {
                    pageSelect.getPageselect(position + 1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mmodes == null ? 0 : mmodes.getCount();
    }

    class PageHolde extends RecyclerView.ViewHolder {
        private TextView tvPage;

        public PageHolde(View itemView) {
            super(itemView);
            tvPage = (TextView) itemView.findViewById(R.id.tv_page);
        }
    }

    interface IPageSelect {
        void getPageselect(int index);
    }
}
