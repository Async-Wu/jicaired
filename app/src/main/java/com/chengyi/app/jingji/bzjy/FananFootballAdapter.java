package com.chengyi.app.jingji.bzjy;

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
import com.chengyi.app.util.L;
import com.chengyi.app.view.MeGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaqi on 2016/6/24.
 */
public class FananFootballAdapter extends BaseAdapter {

    private String[] biFenStr = "1:0,2:0,2:1,3:0,3:1,3:2,4:0,4:1,4:2,5:0,5:1,5:2,胜其他,0:0,1:1,2:2,3:3,平其他,0:1,0:2,1:2,0:3,1:3,2:3,0:4,1:4,2:4,0:5,1:5,2:5,负其他"
            .split(",");
    private String[] banquanchang = "胜胜,胜平,胜负,平胜,平平,平负,负胜,负平,负负".split(",");
    Context context;
    List<MatchesResultEntity> list;
    List<SchemeJoinsEntity> listjoin;
    int flag;
    int tiaoflag;
    int open;

    public FananFootballAdapter(Context context, List<MatchesResultEntity> matches, int flag,
                                List<SchemeJoinsEntity> schemeJoins, int tiaoflag, int open) {
        this.context = context;
        this.list = matches;
        this.flag = flag;
        this.listjoin = schemeJoins;
        this.tiaoflag = tiaoflag;
        this.open = open;
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
        convertView = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_fananfootball, parent, false);
            viewholder = new Viewholder();
            viewholder.matchcode = (TextView) convertView.findViewById(R.id.item_footmatchcode);
            viewholder.hostnam = (TextView) convertView.findViewById(R.id.item_foothostname);
            viewholder.guestname = (TextView) convertView.findViewById(R.id.item_footguestname);
            viewholder.choose = (MeGridView) convertView.findViewById(R.id.item_footchoose);
            viewholder.stroke = (TextView) convertView.findViewById(R.id.stroke);
            viewholder.itemResult = (MeGridView) convertView.findViewById(R.id.item_result);


            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        viewholder.stroke.setVisibility(View.GONE);
        if (position == list.size()) {
            viewholder.stroke.setVisibility(View.VISIBLE);
            viewholder.matchcode.setText("参与者");
            viewholder.hostnam.setText("参与金额");
            viewholder.guestname.setText("参与股份");
//            viewholder.choose.setText("参与时间");
        } else if (position < list.size()) {

            if (open == 0) {
                viewholder.matchcode.setText(list.get(position).getHostName());
                viewholder.hostnam.setVisibility(View.GONE);
                viewholder.guestname.setVisibility(View.GONE);
                viewholder.choose.setVisibility(View.GONE);
            } else {

                viewholder.matchcode.setText(list.get(position).getCreateTime());
                viewholder.hostnam.setText(list.get(position).getHostName() + "");
                viewholder.guestname.setText(list.get(position).getGuestName() + "");
                List<Integer> choose = list.get(position).getChoose();
                List<String> odds = list.get(position).getOdds();
                String choosetype = "";
                List<String> chooses = new ArrayList<>();


                switch (flag) {
                    case 0:
                        for (int i = 0; i < choose.size(); i++) {
                            String content = "";
                            Integer integer = choose.get(i);
                            if (integer == 0) {
                                content = "主负";
                            } else if (integer == 3) {
                                content = "主胜";
                            } else if (integer == 1) {
                                content = "平";
                            } else if (integer == 400) {
                                content = "让球负";
                            } else if (integer == 403) {
                                content = "让球胜";
                            } else {
                                content = "让球平";
                            }
                            if (odds != null && odds.get(i) != null)
                                content = content + "(" + odds.get(i) + "),";
                            else
                                content = content + ",";


                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                    case 1:
                        for (int i = 0; i < choose.size(); i++) {
                            String content = "";
                            if (choose.get(i) == 7) {
                                content = "7球+";
                            } else {
                                content = choose.get(i) + "球";
                            }
                            if (odds != null && odds.get(i) != null)
                                content = content + "(" + odds.get(i) + " ),";
                            else
                                content = content + ",";
                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                    case 2://让球
                        for (int i = 0; i < choose.size(); i++) {
                            String content = "";
                            Integer integer = choose.get(i);
                            if (integer == 0) {
                                content = "让球负";
                            } else if (integer == 3) {
                                content = "让球胜";
                            } else {
                                content = "让球平";
                            }
                            L.d((odds != null && odds.get(i) != null) + "");
                            if (odds != null && odds.get(i) != null)
                                content = content + "(" + odds.get(i) + " ,";
                            else
                                content = content + ",";

                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                    case 4:
                        for (int i = 0; i < choose.size(); i++) {
                            String content = "";
                            Integer integer = choose.get(i);
                            content = banquanchang[integer] + "";
                            if (odds != null && odds.get(i) != null)
                                content = content + "(" + odds.get(i) + ")";
                            else
                                content += ",";
                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                    case 5:
                        for (int i = 0; i < choose.size(); i++) {
                            String content = "";
                            Integer integer = choose.get(i);
                            if (odds != null && odds.get(i) != null)
                                content = biFenStr[integer] + "";
                            if (odds != null && odds.get(i) != null)
                                content = content + "(" + odds.get(i) + "),";
                            else
                                content = content + ",";
                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                    case 6:
                        //混合投注的购彩显示
                        for (int i = 0; i < choose.size(); i++) {
                            String content = "";
                            Integer integer = choose.get(i);
                            if (integer == 0) {
                                content = "主负";
                            } else if (integer == 3) {
                                content = "主胜";
                            } else if (integer == 1) {
                                content = "平";
                            } else if (integer == 400) {
                                content = "让球负";
                            } else if (integer == 403) {
                                content = "让球胜";
                            } else if (integer == 401) {
                                content = "让球平";
                            } else if ((integer / 100) == 1) {
                                int zongtype = integer % 100;
                                if (zongtype == 7) {
                                    content = "7球+";
                                } else {
                                    content = zongtype + "球 ";
                                }


                            } else if ((integer / 100) == 3) {
                                String bantype = "";
//                                if (((integer % 100) / 10) == 3) {
//                                    bantype = "胜";
//                                } else if (((integer % 100) / 10) == 1) {
//                                    bantype = "平";
//                                } else if (((integer % 100) / 10) == 0) {
//                                    bantype = "负";
//                                }
//                                if (((integer % 100) % 10) == 3) {
//                                    bantype = bantype + "胜";
//                                } else if (((integer % 100) % 10) == 1) {
//                                    bantype = bantype + "平";
//                                } else if (((integer % 100) % 10) == 0) {
//                                    bantype = bantype + "负";
//                                }

                                int i1 = integer % 100;
                                if (i1 >= 10) {
                                    bantype = String.valueOf(i1).replace("3", "胜").replace("1", "平").replace("0", "负");
                                } else {
                                    bantype = String.valueOf("0" + i1).replace("3", "胜").replace("1", "平").replace("0", "负");
                                }


                                content = bantype;
                            } else if ((integer / 100) == 2) {
                                String bifentype = "";
                                if ((integer % 100) / 10 == 9) {
                                    if (((integer % 100) % 10) == 0) {
                                        bifentype = "胜其他";
                                    } else {
                                        bifentype = "平其他";
                                    }
                                } else if (integer == 209) {
                                    bifentype = "负其他";
                                } else {
                                    bifentype = (integer % 100) / 10 + ":" + (integer % 100) % 10 + "";
                                }
                                content = bifentype;
                            }


                            if (odds != null && odds.get(i) != null)
                                content = content + "(" + odds.get(i) + "),";
                            else
                                content = content + ",";
                            chooses.add(content);
                            choosetype = choosetype + content;
                        }
                        break;
                }


                if (choosetype.endsWith(",")) {
                    choosetype = choosetype.substring(0, choosetype.length() - 1);
                }


                List<String> result = new ArrayList<>();


                if (!TextUtils.isEmpty(list.get(position).getBingo())) {
                    if (list.get(position).getBingo().equals("-1")) {
                        list.get(position).setBingo("");
                    }

                    String[] split = list.get(position).getBingo().split("/");
                    for (String ss : split) {
                        String[] split1 = ss.split("：");
                        switch (flag) {

                            case 0:
                                try {


                                    if (ss.equals("0")) {
                                        result.add("主负");
                                    } else if (ss.equals("3")) {
                                        result.add("主胜");
                                    } else if (ss.equals("1")) {
                                        result.add("平");
                                    } else if (ss.equals("400")) {
                                        result.add("让球负");
                                    } else if (ss.equals("403")) {
                                        result.add("让球胜");
                                    } else  if (ss.equals("401")) {
                                        result.add("让球平");
                                    }else {
                                        result.add(context.getResources().getString(R.string.no_match_result));
                                    }
                                } catch (Exception e) {
                                    result.add(ss);

                                }
                                break;


                            case 1:
                                try {

                                    if (Integer.parseInt(ss.trim()) == 7) {
                                        result.add("7+球");
                                    } else if (Integer.parseInt(ss.trim()) !=-1 ){
                                        result.add(ss.trim() + "球");
                                    }else {
                                        result.add(context.getResources().getString(R.string.no_match_result));
                                    }

                                } catch (Exception e) {
                                    result.add(ss.trim());
                                }


                                break;
                            case 2:
                                try {
                                    if (ss.equals("0")) {
                                        result.add("让球负");
                                    } else if (ss.equals("3")) {
                                        result.add("让球胜");
                                    } else if (ss.equals("1"))  {
                                        result.add("让球平");
                                    } else {
                                        result.add(context.getResources().getString(R.string.no_match_result));
                                    }
                                } catch (Exception e) {
                                    result.add(ss.trim());
                                }
                                break;
                            case 3:


                                break;
                            case 4:
                                try {

                                    Integer integer1 = Integer.parseInt(ss.trim());
                                    if (integer1!=-1)
                                    result.add(banquanchang[integer1]);
                                    else
                                        result.add(context.getResources().getString(R.string.no_match_result));

                                } catch (Exception e) {
                                    result.add(ss.trim());
                                }
                                break;
                            case 5:
                                try {

                                    Integer integer = Integer.parseInt(ss.trim());
                                    if (integer!=-1)
                                    result.add(biFenStr[integer]);
                                    else
                                        result.add(context.getResources().getString(R.string.no_match_result));
                                } catch (Exception e) {
                                    result.add(ss.trim());
                                }
                                break;
                            case 6:
                                try {
                                    result.add(split1[1].trim());
                                } catch (Exception e) {
                                    result.add(ss.trim());
                                }
                                break;
                        }
                    }
                }


                if (result == null || result.size() == 0) {
                    result.add("暂无比赛结果");
                }

                List<OrderSelectMode> bgs = AppUtil.resove(context, result, result, true, true);
                OrderSelectAdapter resultAdapter = new OrderSelectAdapter(bgs, context);
                viewholder.itemResult.setAdapter(resultAdapter);
                List<OrderSelectMode> resove = AppUtil.resove(context, chooses, result, false, true);
                OrderSelectAdapter orderSelectAdapter = new OrderSelectAdapter(resove, context);
                viewholder.choose.setAdapter(orderSelectAdapter);
            }
        } else {
          SchemeJoinsEntity schemeJoinsEntity = listjoin.get(position - (list.size() + 1));
            double guo = ((int) ((schemeJoinsEntity.getProportion() * 100)));
            String baifen = guo / 100 + "";
            viewholder.hostnam.setText(schemeJoinsEntity.getMoney() + "元");
            viewholder.matchcode.setText(schemeJoinsEntity.getUserName() + "");
            viewholder.guestname.setText(baifen + "%");
//            viewholder.choose.setAdapter(schemeJoinsEntity.getDateWithoutYear() + "");
        }
        return convertView;
    }

    class Viewholder {
        TextView matchcode, hostnam, guestname, stroke;
        MeGridView choose, itemResult;

    }
}