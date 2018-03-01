package com.chengyi.app.user.money;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by lishangfan on 2016/10/26.
 */
public class PayAdapter extends BaseAdapter {
    private List<PayModel> modelss;
    private Context context;

    public PayAdapter(List<PayModel> modelss, Context context) {
        this.modelss = modelss;
        this.context = context;
    }

    @Override
    public int getCount() {
        return modelss.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
