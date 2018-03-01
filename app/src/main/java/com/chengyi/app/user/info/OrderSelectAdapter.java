package com.chengyi.app.user.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.base.BasicAdapter;

import java.util.List;

/**
 * Created by lishangfan on 2016/12/3.
 */
public class OrderSelectAdapter extends BasicAdapter<OrderSelectMode> {
    public OrderSelectAdapter(List<OrderSelectMode> mMode, Context mCtx) {
        super(mMode, mCtx);
    }

    @Override
    public int getItemViewType(int position) {

        return position % 2;
    }


    public int getViewTypeCount() {
        return 2;
    }


    @Override
    protected View createView(int position, View convertView, ViewGroup parent, LayoutInflater inflater) {

        if (getItemViewType(position) == 0) {//left
            ViewHolde viewHolde;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_left_order, parent, false);
                viewHolde = new ViewHolde();
                viewHolde.tvOrderName = (TextView) convertView.findViewById(R.id.tv_left_name);
                convertView.setTag(viewHolde);
            } else {
                viewHolde = (ViewHolde) convertView.getTag();
            }
            viewHolde.tvOrderName.setSelected(mMode.get(position).isSelect());
            viewHolde.tvOrderName.setText(mMode.get(position).getName());

        } else if (getItemViewType(position) == 1) {//rige

            ViewHolde viewHolde;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_right_order, parent, false);
                viewHolde = new ViewHolde();
                viewHolde.tvOrderName = (TextView) convertView.findViewById(R.id.tv_right_name);
                convertView.setTag(viewHolde);
            } else {
                viewHolde = (ViewHolde) convertView.getTag();
            }
            viewHolde.tvOrderName.setSelected(mMode.get(position).isSelect());
            viewHolde.tvOrderName.setText(mMode.get(position).getName());

        }

        return convertView;
    }

    class ViewHolde {

        TextView tvOrderName;
    }

}
