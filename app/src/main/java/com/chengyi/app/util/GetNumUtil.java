package com.chengyi.app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  GetNumUtil {

    private static double minValue = 0;
    private static double maxValue = 0;
    private static double minTemp = 0;
    private static double maxTemp = 0;

    // 计算n的阶乘  n>19return
    public static long getJCNumber(int n) {
        if ((n < 0) || (n > 19)) {
            return -1;
        }
        if (n == 1)
            return 1;
        return n * getJCNumber(n - 1);
    }

    // 根据过关方式得到可以选择的胆码个数
    public static int getDanNumFromGuan(String kind) {
        int[] temp = guoGuantypeMap.get(kind);
        return temp[0];
    }

//	// 清空注数缓冲列表
//	public static void clearZhuMap() {
//		zhuMap.clear();
//	}

    // 根据所选择的过关方式来计算注数
    public static long getZhuNum(final List<GetNumModel> list,
                                 String kind) {
        minValue = 0;
        maxValue = 0;
        String[] kindStr = kind.split("串");
        if (Integer.parseInt(kindStr[1]) == 1) {
            // m串1过关
            return getZhuNumByKind(list, Integer.parseInt(kindStr[0]));
        } else {
            int[] temp = guoGuantypeMap.get(kind);
            return getZhuNumMoreKind(list, Integer.parseInt(kindStr[0]), temp);
        }
    }

    // m串n过关方式的算法
    public static long getZhuNumMoreKind(List<GetNumModel> list,
                                         int chuan, int[] temp) {
        List<Integer> danList = new ArrayList<>();
        List<Integer> feiDanList = new ArrayList<>();
        for (int s = 0; s < list.size(); s++) {
            if (list.get(s).isDan())
                danList.add(s );
            else
                feiDanList.add(s);
        }
        List<List<Integer>> gameList = getGameList(feiDanList, chuan
                - danList.size(), 0);
        long sum = 0;
        double mix = 1000000000, max = 0;
        for (int i = 0; i < gameList.size(); i++) {
            List<GetNumModel> listTemp = new ArrayList<>();
            for (int l = 0; l < danList.size(); l++) {
                listTemp.add(list.get(danList.get(l)));
            }
            for (int t = 0; t < gameList.get(i).size(); t++) {
                listTemp.add(list.get(gameList.get(i).get(t)));
            }
            long num = 0;
            double mixOne = 1000000000, maxOne = 0;
            for (int j = 0; j < temp.length; j++) {
                num += getZhuNumByKind(listTemp, temp[j]);
                maxOne += maxValue;
                mixOne = mixOne > minValue ? minValue : mixOne;
            }
            mix = mix > mixOne ? mixOne : mix;
            max += maxOne;
            sum += num;
        }
        minValue = mix;
        maxValue = max;
        gameList.clear();
        gameList=null;
        return sum;
    }

    /****
     * 利用递归进行排列组合算法，所选择的比赛按照排列组合模式来拆分
     *
     * @param selectedChuan
     *            chuan-投胆场次数，是剩余需要串在一起的场次数（从list中要获取的比赛场次），最高不会超过8-1=7
     * @param begin
     *            起始位置
     */
    public static List<List<Integer>> getGameList(List<Integer> feiDanOrderList, int selectedChuan,
                                                  int begin) {
        List<List<Integer>> gameList = new ArrayList<>();
        for (int i = begin; i <= feiDanOrderList.size() - selectedChuan; i++) {
            if (selectedChuan == 1) {
                List<Integer> listTemp = new ArrayList<>();
                listTemp.add(feiDanOrderList.get(i));
                gameList.add(listTemp);
            } else {
                List<List<Integer>> gameListTemp = getGameList(feiDanOrderList, selectedChuan - 1,
                        i + 1);
                for (int j = 0; j < gameListTemp.size(); j++) {
                    List<Integer> listTemp = new ArrayList<>();
                    listTemp.add(feiDanOrderList.get(i));
                    listTemp.addAll(gameListTemp.get(j));
                    gameList.add(listTemp);
                }
            }
        }
        return gameList;
    }




    public static List<List<Integer>> getGameList0(List<Integer> feiDanOrderList, int selectedChuan,
                                                   int begin) {
        List<List<Integer>> gameList = new ArrayList<>();
        if(selectedChuan==0){
            return gameList;
        }
        int chuan=selectedChuan;
        int startPosition=begin;

        int recyleCount=0;

        while(chuan>1){

            chuan-=1;
            startPosition+=1;
            recyleCount+=1;

        }
        int feiDanOrderListSize=feiDanOrderList.size();


        for (int i = startPosition; i <= feiDanOrderListSize - chuan; i++) {

            List<Integer> listTemp = new ArrayList<>();
            listTemp.add(feiDanOrderList.get(i));
            gameList.add(listTemp);

        }


        for (int i=0;i<recyleCount;i++){
            int size=gameList.size();
            for (int j = 0; j < size; j++) {
                List<Integer> listTemp = new ArrayList<>();
                listTemp.add(feiDanOrderList.get(startPosition-i));
                listTemp.addAll(gameList.get(j));
                gameList.add(listTemp);
            }
        }

        return gameList;
    }





    // 根据m串1来计算注数
    public static long getZhuNumByKind(List<GetNumModel> list, int kind) {
        // m场比赛m串1过关方式
        if (kind == list.size()) {
            return getNum(list);
        } else {
            List<GetNumModel> listDan = new ArrayList<>();
            List<GetNumModel> listTuo = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                GetNumModel game = list.get(i);
                if (game.isDan()) {
                    listDan.add(game);
                } else {
                    listTuo.add(game);
                }
            }
            long num = 0;
            num = combine(listTuo, listTuo.size(), kind - listDan.size());
            long sum = getNum(listDan) * num;
            minValue = minValue * getMinTemp();
            maxValue = maxValue * getMaxTemp();
            return sum;
        }
    }


    public static long combine(List<GetNumModel> list, int n, int m) {
        long sum = 0;
        minTemp = 1000000000;
        maxTemp = 0;
        for (int i = n; i >= m; i--) // 注意这里的循环范围
        {
            double minBak = getMinTemp();
            double maxBak = getMaxTemp();
            double mix, max;
            try {
                GetNumModel game = list.get(i - 1);
                mix = 1;
                max = 1;
                if (m > 1) {
                    sum += (game.getCount() ) * combine(list, i - 1, m - 1);
                    mix = game.getMixSP() * getMinTemp();
                    max = game.getMaxSP() * getMaxTemp();
                } else {
                    sum += (game.getCount() );
                    mix = game.getMixSP();
                    max = game.getMaxSP();
                }
                minBak = minBak > mix ? mix : minBak;
                maxBak += max;
                minTemp = minBak;
                maxTemp = maxBak;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return sum;
    }

    public static double getMinTemp() {
        return minTemp;
    }

    public static void setMinTemp(double minTemp) {
        GetNumUtil.minTemp = minTemp;
    }

    public static double getMaxTemp() {
        return maxTemp;
    }

    public static void setMaxTemp(double maxTemp) {
        GetNumUtil.maxTemp = maxTemp;
    }

    public static long getNum(List<GetNumModel> list) {
        long sum = 1;
        minValue = 1;
        maxValue = 1;
        for (int i = 0; i < list.size(); i++) {
            GetNumModel game = list.get(i);
            sum *= (game.getCount());
            minValue *= game.getMixSP();
            maxValue *= game.getMaxSP();
        }
        return sum;
    }

    // 存储各种过关方式对应的最终过关方式，比如4串5等同于3串1加4串1;
    private final static Map<String, int[]> guoGuantypeMap = GuoGuanType.getGuoGuanType();


}
