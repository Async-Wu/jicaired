package com.chengyi.app.jingji.basket;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.jingji.MatchesResultEntity;
import com.chengyi.app.model.caipiao.SchemeJoinsEntity;
import com.chengyi.app.user.info.OrderSelectAdapter;
import com.chengyi.app.user.info.OrderSelectMode;
import com.chengyi.app.util.AppUtil;
import com.chengyi.app.view.MeGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaqi on 2016/7/22.
 */

public class FananBasketball extends BaseAdapter {
    public String[] sfcName = {"1-5", "6-10", "11-15", "16-20", "21-25", "26+"};
    Context context;
    List<MatchesResultEntity> basketballmathes;
    List<SchemeJoinsEntity> listjoin;
    int xiaflag;
    int tiaoflag;
    int open;

    /**
     * flag=100 表示从合买中心跳转过来 显示参与合买信息  和参加合买按钮
     */
    public FananBasketball(Context context, List<MatchesResultEntity> basketballmathes, int xiaflag,
                           List<SchemeJoinsEntity> schemeJoins, int tiaoflag, int open) {
        this.xiaflag = xiaflag;
        this.basketballmathes = basketballmathes;
        this.context = context;
        this.listjoin = schemeJoins;
        this.tiaoflag = tiaoflag;
        this.open = open;
    }

    @Override
    public int getCount() {
        if (tiaoflag != 100) {
            return basketballmathes.size();
        }
        return basketballmathes.size() + listjoin.size() + 1;
    }

    @Override
    public Object getItem(int position) {

        if (position < basketballmathes.size()) {
            return basketballmathes.get(position);
        } else if (position == basketballmathes.size()) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fananfootball, parent, false);
            viewholder = new Viewholder();
            viewholder.matchcode = (TextView) convertView.findViewById(R.id.item_footmatchcode);
            viewholder.hostnam = (TextView) convertView.findViewById(R.id.item_foothostname);
            viewholder.guestname = (TextView) convertView.findViewById(R.id.item_footguestname);
            viewholder.choose = (MeGridView) convertView.findViewById(R.id.item_footchoose);
            viewholder.stroke = (TextView) convertView.findViewById(R.id.stroke);
            viewholder.result = (MeGridView) convertView.findViewById(R.id.item_result);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        viewholder.stroke.setVisibility(View.GONE);
        if (position < basketballmathes.size()) {
            if (open == 0) {
                viewholder.matchcode.setText(basketballmathes.get(position).getHostName());
                viewholder.hostnam.setVisibility(View.GONE);
                viewholder.guestname.setVisibility(View.GONE);
                viewholder.choose.setVisibility(View.GONE);
            } else {

                viewholder.matchcode.setText(basketballmathes.get(position).getCreateTime());
                viewholder.hostnam.setText(basketballmathes.get(position).getHostName() + "");
                viewholder.guestname.setText(basketballmathes.get(position).getGuestName() + "");
                List<Integer> choose = basketballmathes.get(position).getChoose();
                List<String> odds = basketballmathes.get(position).getOdds();
                String choosetype = "";

                List<String> chooses = new ArrayList<>();

                switch (xiaflag) {
                    case 10:
                        //胜负
                        for (int i = 0; i < choose.size(); i++) {
                            String content = "";
                            Integer integer = choose.get(i);
                            if (integer == 0) {
                                content = context.getString(R.string.zufu) + odds.get(i) + "),";
                            } else if (integer == 3) {
                                content = context.getString(R.string.zuseng) + odds.get(i) + "),";
                            }

                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                    case 11:
                        //让分胜负
                        for (int i = 0; i < choose.size(); i++) {
                            String content = "";
                            if (choose.get(i) == 3) {
                                content = context.getString(R.string.rangffzusg) + odds.get(i) + "),";
                            } else if (choose.get(i) == 0) {
                                content = context.getString(R.string.rangffzufu) + odds.get(i) + "),";
                            }
                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                    case 12:
                        //胜分差
                        for (int i = 0; i < choose.size(); i++) {
                            String content = "";
                            Integer integer = choose.get(i);
                            if (integer > 5) {
                                //主胜
                                choosetype = choosetype + context.getString(R.string.zusg_);
                                int i1 = integer - 6;
                                content =context.getString(R.string.zusg__)+ sfcName[i1] + "(" + odds.get(i) + "),";
                            } else {
                                choosetype = choosetype + "主负:";
                                content ="主负" +sfcName[integer] + "(" + odds.get(i) + "),";
                            }
                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                    case 13:
                        //大小分

                        for (int i = 0; i < choose.size(); i++) {
                            if (odds.size() != choose.size()) {
                                odds.add("-");
                            }
                            String content = "";
                            Integer integer = choose.get(i);
                            if (integer == 1) {
                                content = "小分(" + odds.get(i) + "),";
                            } else if (integer == 0) {
                                content = "大分(" + odds.get(i) + "),";
                            }
                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                    case 14:
                        //混合投注
                        for (int i = 0; i < choose.size(); i++) {
                            String content = "";
                            Integer integer = choose.get(i);
                            if (integer == 0) {
                                content = "主负(" + odds.get(i) + "),";
                            }
                            if (integer == 3) {
                                content = "主胜(" + odds.get(i) + "),";
                            }
                            if (choose.get(i) == 103) {
                                content = "让分主胜(" + odds.get(i) + "),";
                            }
                            if (choose.get(i) == 100) {
                                content = "让分主负(" + odds.get(i) + "),";
                            }
                            if (integer == 202) {
                                content = "小(" + odds.get(i) + "),";
                            }
                            if (integer == 201) {
                                content = "大(" + odds.get(i) + "),";
                            }
                            if (integer > 300) {
                                if ((integer - 300) > 10) {
                                    //客胜
                                    choosetype = choosetype + "主负:";
                                    content ="主负"+ sfcName[(integer - 300) - 11] + "(" + odds.get(i) + "),";
                                } else {
                                    choosetype = choosetype + "主胜:";
                                    content = "主胜"+sfcName[integer - 301] + "(" + odds.get(i) + "),";
                                }
                            }
                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                }
                if (choosetype.endsWith(",")) {
                    choosetype = choosetype.substring(0, choosetype.length() - 1);
                }



                List<String> result = new ArrayList<>();
                if (!TextUtils.isEmpty(basketballmathes.get(position).getBingo())) {
                    String[] split = basketballmathes.get(position).getBingo().split("/");
                    for (String ss : split) {
                        String[] split1 = ss.split("：");

                        switch (xiaflag) {

                            case 10:
                            case 11:
                            case 12:
                            case 13:

                                result.add(ss.trim());
                                break;
                            case 14:
                                result.add(split1[1].trim());
                                break;

                        }
                    }


                }
                if (result == null || result.size() == 0) {
                    result.add(context.getString(R.string.no_match_result));
                }
                List<OrderSelectMode> bgs = AppUtil.resove(context,result, result,true);

                OrderSelectAdapter resultAdapter = new OrderSelectAdapter(bgs, context);
                viewholder.result.setAdapter(resultAdapter);
                List<OrderSelectMode> resove = AppUtil.resove(context,chooses, result,false);
                OrderSelectAdapter orderSelectAdapter = new OrderSelectAdapter(resove, context);
                viewholder.choose.setAdapter(orderSelectAdapter);


            }
        } else if (position == basketballmathes.size()) {
            viewholder.stroke.setVisibility(View.VISIBLE);
            viewholder.matchcode.setText(context.getString(R.string.joiner));

            viewholder.hostnam.setText(context.getString(R.string.jointotle));
            viewholder.guestname.setText(context.getString(R.string.join_guf));
//            viewholder.choose.setText("参与时间");
        } else {

            if (listjoin.size() == 0) {
                viewholder.hostnam.setVisibility(View.GONE);
                viewholder.guestname.setVisibility(View.GONE);
                viewholder.choose.setVisibility(View.GONE);
                viewholder.stroke.setVisibility(View.GONE);
                viewholder.matchcode.setText(context.getString(R.string.order_no_join));
            } else {
               SchemeJoinsEntity schemeJoinsEntity = listjoin.get(position - (basketballmathes.size() + 1));
                double guo = ((int) ((schemeJoinsEntity.getProportion() * 100)));
                String baifen = guo / 100 + "";
                viewholder.hostnam.setText(schemeJoinsEntity.getMoney() +context.getString(R.string.yuan));
                viewholder.matchcode.setText(schemeJoinsEntity.getUserName() + "");
                viewholder.guestname.setText(baifen + "%");
//                viewholder.choose.setText(schemeJoinsEntity.getDateWithoutYear() + "");
            }
        }
        return convertView;
    }

    class Viewholder {
        TextView matchcode, hostnam, guestname, stroke;
        MeGridView choose, result;
    }
}
