package com.chengyi.app.jingji.football;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.view.widget.OnJingcaizuqiuBtnListener;

import java.util.List;

/**
 * Created by lishangfan on 2016/11/8.
 */
public class MixCarAdapter extends JingcaiZuqiuTZquerenAdapter {
    public MixCarAdapter(Activity mActivity, List<JingcaizuqiuOneGame> list, OnJingcaizuqiuBtnListener listener) {
        super(mActivity, list, listener);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new MixCarAdapter.ViewHolder();
            convertView = buildChildView(R.layout.football_car_mix);
            holder.loadViews(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final JingcaizuqiuOneGame game = list.get(position);
        holder.fillView(game);
        holder.dan.setTag(position);
        holder.dan.setOnClickListener(this);
        holder.dan.setSelected(game.isDanTuo());
        holder.btn1.setSelected(true);

        String content = "";


        if (game.isSelected[0] || game.isSelected[1] || game.isSelected[2]) {
            content += "胜平负:";
            if (game.isSelected[0]) {
                content += "主胜,";
            }
            if (game.isSelected[1]) {
                content += "主平,";
            }
            if (game.isSelected[2]) {
                content += "主负,";
            }
            if (content.endsWith(",")){
                content=content.substring(0,content.length()-1);
            }
            content += "|";

        }


        if (game.isSelected[3] || game.isSelected[4] || game.isSelected[5]) {
            content += "让球胜平负:";
            if (game.isSelected[3]) {
                content += "主胜,";
            }
            if (game.isSelected[4]) {
                content += "主平,";
            }
            if (game.isSelected[5]) {
                content += "主负,";
            }

            if (content.endsWith(",")){
                content=content.substring(0,content.length()-1);
            }

            content += "|";

        }


        boolean biffflag = false;

        for (int j = 0; j <= 30; j++) {

            if (game.getSelects().size() > 0 && game.getSelects().get(j))
                biffflag = true;


        }

        if (biffflag) {
            content += "比分:";
        }


        for (int j = 0; j <= 30; j++) {

            if (game.getSelects().size() > 0 && game.getSelects().get(j)) {
                content += game.getBiFenStr()[j] + ",";
            }
        }


        if (content.endsWith(",")){
            content=content.substring(0,content.length()-1);
        }
        content=content+"|";



        boolean zjqffflag = false;

        for (int j = 31; j <= 38; j++) {
            if (game.getSelects().size() > 0 && game.getSelects().get(j))
                zjqffflag = true;
        }

        if (zjqffflag) {
            content += "总进球:";
        }


        for (int j = 31; j <= 38; j++) {

            if (game.getSelects().size() > 0 && game.getSelects().get(j)) {
                if (j - 31 == 7) {
                    content += j - 31 + "+球,";
                } else {
                    content += j - 31 + "球,";
                }
            }
        }
        if (content.endsWith(",")){
            content=content.substring(0,content.length()-1);
        }


        content = content+ "|";


        boolean bqcffflag = false;

        for (int j = 39; j <= game.getSelects().size()-1; j++) {
            if (game.getSelects().size() > 0 && game.getSelects().get(j))
                bqcffflag = true;
        }

        if (bqcffflag) {
            content += "半全场:";
        }




        for (int j = 39; j <= game.getSelects().size() - 1; j++) {

            if (game.getSelects().size() > 0 && game.getSelects().get(j)) {
                content += game.bqcArr[j - 39] + ",";

            }
        }

        if (content.endsWith(",")){
            content=content.substring(0,content.length()-1);
        }


        content = content+ "|";

        if (content.trim().endsWith("|")) {
            content = content.trim().substring(0, content.length() - 1);
        }
        if (content.endsWith(",")) {
            content = content.substring(0, content.length() - 1);
        }

       content= deleteLastLine(content);
        content= deleteFirstLine(content);

        holder.btn1.setText(content);


        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, MixSubActivity.class);
                intent.putExtra("gameData", game);
                intent.putExtra("isClick", false);
                mActivity.startActivityForResult(intent, CaipiaoConst.MIX);
            }
        });


        holder.ivDelete.setTag(position);
        holder.ivDelete.setOnClickListener(this);
        holder.ivDelete.setSelected(game.isDanTuo());
        return convertView;
    }

    /**
     * 递归  去除|
     * @param content
     * @return
     */
    private String deleteLastLine(String content) {

        if (content.endsWith("|")){
            content=content.substring(0,content.length()-1);
            return  deleteLastLine(content);
        }else {
            return  content;
        }



    }


    private String deleteFirstLine(String content) {

        if (content.startsWith("|")){
            content=content.substring(1,content.length());
            return  deleteFirstLine(content);
        }else {
            return  content;
        }



    }

    class ViewHolder {

        TextView zhuDui;
        TextView keDui;
        TextView rangQiu;

        TextView btn1;

        Button dan;
        ImageView ivDelete;

        public void loadViews(View convertView) {
            zhuDui = (TextView) convertView.findViewById(R.id.duiwu1);
            keDui = (TextView) convertView.findViewById(R.id.duiwu2);
            rangQiu = (TextView) convertView.findViewById(R.id.rangqiu);
            btn1 = (TextView) convertView.findViewById(R.id.btn1);

            dan = (Button) convertView.findViewById(R.id.danbtn);
            ivDelete = (ImageView) convertView.findViewById(R.id.iv_delete);
        }

        public void fillView(JingcaizuqiuOneGame game) {

            zhuDui.setText(game.getTeam1());
            keDui.setText(game.getTeam2());


            if (deleteMode) {
                ivDelete.setVisibility(View.VISIBLE);

            } else {
                ivDelete.setVisibility(View.INVISIBLE);
            }
        }
    }

}
