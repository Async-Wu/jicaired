package com.chengyi.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chengyi.R;
import com.chengyi.app.base.BasicAdapter;

import java.util.List;


/**
 * Created by xiaqi on 2016/12/15.
 */

public class Payadapter extends BasicAdapter<Integer> {


    public Payadapter(List<Integer> mMode, Context mCtx) {
        super(mMode, mCtx);
    }

    @Override
    protected View createView(int position, View view, ViewGroup parent, LayoutInflater inflater) {
        view= inflater.inflate(R.layout.item_shoupay,parent,false);
        ImageView payicon= (ImageView) view.findViewById(R.id.pay_icon);
        payicon.setImageResource(mMode.get(position));
        return view;
    }


}
