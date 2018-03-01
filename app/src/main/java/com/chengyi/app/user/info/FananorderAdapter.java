package com.chengyi.app.user.info;

import android.content.Context;
import android.text.SpannableString;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.caipiao.SchemeJoinsEntity;
import com.chengyi.app.model.footOrder.SchemeContentEntity;
import com.chengyi.app.model.footOrder.SchemeDetailEntity;
import com.chengyi.app.util.AppUtil;

import java.util.List;

/**
 * Created by xiaqi on 2016/5/30.
 */
public class FananorderAdapter extends BaseAdapter {
    List<SchemeJoinsEntity> listjoin;
    private Context context;
    private List<SchemeContentEntity> list;
    private int tiaoflag;
    private int open;
    List<SchemeDetailEntity> schemeDetail;

    public FananorderAdapter(Context context, List<SchemeContentEntity> list, List<SchemeJoinsEntity> listjoin, int tiaoflag, int open, List<SchemeDetailEntity> schemeDetail) {
        this.context = context;
        this.list = list;
        this.listjoin = listjoin;
        this.tiaoflag = tiaoflag;
        this.open = open;
        this.schemeDetail = schemeDetail;
    }

    @Override
    public int getCount() {
        if (tiaoflag != 100) {
            return list.size();
        }
        return list.size() + listjoin.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position < list.size()) {
            return list.get(position);
        } else if (position == list.size()) {
            return listjoin.get(0);
        } else {
            return listjoin.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_title, parent, false);
            viewholder = new Viewholder();
            viewholder.matchcode = (TextView) convertView.findViewById(R.id.item_footmatchcode);
            viewholder.hostnam = (TextView) convertView.findViewById(R.id.item_foothostname);
            viewholder.guestname = (TextView) convertView.findViewById(R.id.item_footguestname);
            viewholder.choose = (TextView) convertView.findViewById(R.id.item_footchoose);
            viewholder.stroke = (TextView) convertView.findViewById(R.id.stroke);
            viewholder.result = (TextView) convertView.findViewById(R.id.item_result);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        if (position < list.size()) {

            viewholder.guestname.setVisibility(View.GONE);
            viewholder.choose.setVisibility(View.GONE);
            viewholder.stroke.setVisibility(View.GONE);
            if (position == 0) {
                viewholder.matchcode.setVisibility(View.VISIBLE);
                viewholder.matchcode.setText("投注号码:");
            } else {
                viewholder.matchcode.setText("投注号码:");
                viewholder.matchcode.setVisibility(View.INVISIBLE);
            }
            viewholder.result.setVisibility(View.GONE);




            viewholder.hostnam.setGravity(Gravity.LEFT);
            if (null != schemeDetail && null != schemeDetail.get(0) && null != schemeDetail.get(0).getDrawNumber()) {
                viewholder.result.setText(schemeDetail.get(0).getDrawNumber());
            } else {
                viewholder.result.setText("暂无比赛结果");
            }

            SpannableString spannableString = AppUtil.initRed(list.get(position).getNumber(), viewholder.result.getText().toString());
            viewholder.hostnam.setText(spannableString);


            viewholder.result.setTextColor(context.getResources().getColor(R.color.red));
        } else if (position == list.size()) {
            viewholder.stroke.setVisibility(View.VISIBLE);
            viewholder.matchcode.setText("参与者");
            viewholder.hostnam.setText("参与金额");
            viewholder.guestname.setText("参与股份");
            viewholder.choose.setText("参与时间");
        } else {
            if (listjoin.size() == 0) {
                viewholder.hostnam.setVisibility(View.GONE);
                viewholder.guestname.setVisibility(View.GONE);
                viewholder.choose.setVisibility(View.GONE);
                viewholder.stroke.setVisibility(View.GONE);
                viewholder.matchcode.setText("该方案目前没有合买参与者");
            } else {
                viewholder.stroke.setVisibility(View.GONE);
             SchemeJoinsEntity schemeJoinsEntity = listjoin.get(position - (list.size() + 1));
                double guo = ((int) ((schemeJoinsEntity.getProportion() * 100)));
                String baifen = guo / 100 + "";
                viewholder.hostnam.setText(schemeJoinsEntity.getMoney() + "元");
                viewholder.matchcode.setText(schemeJoinsEntity.getUserName() + "");
                viewholder.guestname.setText(baifen + "%");
                viewholder.choose.setText(schemeJoinsEntity.getDateWithoutYear() + "");
                viewholder.result.setText(schemeJoinsEntity.getDrawNumber());
                viewholder.result.setTextColor(context.getResources().getColor(R.color.red));


//                }

            }

        }
        return convertView;
    }

    class Viewholder {
        TextView matchcode, hostnam, guestname, choose, stroke, result;
    }
}
