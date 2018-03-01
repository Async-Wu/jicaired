package com.chengyi.app.model.wanfa;

import android.content.Context;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class WanfaFactory {
    public static AbsWanfa create(int type, int id) {
        AbsWanfa wanfa = new NormalLetou();
        switch (type) {

            case CaipiaoConst.WF_DANTUO_ZU3:
                wanfa = new Dantuo2();
                ((Dantuo2) wanfa).setDantuoType(Dantuo2.ZU3);
                ((Dantuo2) wanfa).setM(2);
                ((Dantuo2) wanfa).setN(0);
                wanfa.setName("胆拖-组3");
                break;
            case CaipiaoConst.WF_DANTUO_ZU6:
                wanfa = new Dantuo2();
                ((Dantuo2) wanfa).setDantuoType(Dantuo2.ZU6);
                ((Dantuo2) wanfa).setM(3);
                ((Dantuo2) wanfa).setN(0);
                wanfa.setName("胆拖-组6");
                break;
            case CaipiaoConst.WF_DANTUO:
                wanfa = new Dantuo();
                wanfa.setName(getAppContext().getString(R.string.dantuo));
                break;
            case CaipiaoConst.WF_NORMAL_PAILIE:
                wanfa = new NormalPailie();
                if (id == 10025 || id == 10024)
                    wanfa.setName("普通-直选");
                else
                    wanfa.setName(getAppContext().getString(R.string.zhixuan));
                break;
            case CaipiaoConst.WF_NORMAL_LETOU:
                wanfa = new NormalLetou();
                wanfa.setName(getAppContext().getString(R.string.putongtouzhu));
                break;
            case CaipiaoConst.WF_DALETOUSHENGXIAO:// 大乐透的生肖
                wanfa = new Shengxiao();
                ((Shengxiao) wanfa).setShengxiaoList(CaipiaoUtil
                        .getHaomaList(1, 12));
                ((Shengxiao) wanfa).setN(2);
                ((Shengxiao) wanfa).setMax(8);
                wanfa.setName(getAppContext().getString(R.string.shengxiao));
                break;
            case CaipiaoConst.WF_ZU3:
                wanfa = new Zu3();
                wanfa.setName("普通-组3复式");
                break;
            case CaipiaoConst.WF_ZU3_SINGLE:
                wanfa = new QianN();
                ((QianNHouN) wanfa).setN(2);
                wanfa.setName("普通-组3单式");
                break;
            case CaipiaoConst.WF_ZU6:
                wanfa = new Zu6();
                if (id == 10025 || id == 10024)
                    wanfa.setName("普通-组6");
                else
                    wanfa.setName(getAppContext().getString(R.string.zu6));
                break;

            case CaipiaoConst.WF_QIAN1:
                wanfa = new QianN();
                ((QianNHouN) wanfa).setN(1);
                if (id == 10064)
                    wanfa.setName("普通-" + "前一");
                else if (id == 10067)
                    wanfa.setName("前一");
                else if (CaipiaoUtil.is11xr5(id))
                    wanfa.setName("普通-任选1");
                else
                    wanfa.setName("普通-" + getQianN(1));
                break;


            case CaipiaoConst.WF_DANTUO_REN1:
                wanfa = new QianN();
                ((QianNHouN) wanfa).setN(1);
                if (id == 10064)
                    wanfa.setName("胆拖-" + "前一");
                else if (id == 10067)
                    wanfa.setName("前一");
                else if (CaipiaoUtil.is11xr5(id))
                    wanfa.setName("胆拖-任选1");
                else
                    wanfa.setName("胆拖-" + getQianN(1));
                break;











            case CaipiaoConst.WF_QIAN2:
                wanfa = new QianN();
                ((QianNHouN) wanfa).setN(2);
                if (id == 10067)
                    wanfa.setName("前二");
                else
                    wanfa.setName("普通-" + getQianN(2));
                break;
            case CaipiaoConst.WF_LIAN2ZHX:
                wanfa = new QianN();
                ((QianNHouN) wanfa).setN(2);
                wanfa.setName("普通-"
                        + getAppContext().getString(R.string.lian2zhixuan));
                break;
            case CaipiaoConst.WF_QIAN3:
                wanfa = new QianN();
                ((QianNHouN) wanfa).setN(3);
                if (id == 10067)
                    wanfa.setName("前三");
                else
                    wanfa.setName("普通-" + getQianN(3));
                break;
            // 快乐10分前三直选
            case CaipiaoConst.WF_QIAN3ZHIX:
                wanfa = new QianN();
                ((QianNHouN) wanfa).setN(3);
                wanfa.setName("普通-" + getQianN(3));
                break;
            case CaipiaoConst.WF_HOU1:
                wanfa = new HouN();
                ((QianNHouN) wanfa).setN(1);
                wanfa.setName(getHouN(1));
                break;
            case CaipiaoConst.WF_HOU2:
                wanfa = new HouN();
                ((QianNHouN) wanfa).setN(2);
                wanfa.setName(getHouN(2));
                break;
            // 时时彩
            case CaipiaoConst.WF_WX_ZHIXUAN:// 五星直选
                wanfa = new NormalPailie();
                wanfa.setName("普通-" + getAppContext().getString(R.string.wuxingzhixuan));
                break;
            case CaipiaoConst.WF_WX_TONGXUAN:// 五星通选
                wanfa = new NormalPailie();
                wanfa.setName("普通-" + getAppContext().getString(R.string.wuxingtongxuan));
                break;
            case CaipiaoConst.WF_SX_ZHIXUAN:// 三星直选，竞猜后3位
                wanfa = new HouN();
                ((QianNHouN) wanfa).setN(3);
                wanfa.setName("普通-" + getAppContext().getString(R.string.sanxingzhixuan));
                break;
            case CaipiaoConst.WF_SIXING_ZHIXUAN:// 四星直选，竞猜后4位
                wanfa = new HouN();
                ((QianNHouN) wanfa).setN(4);
                wanfa.setName("普通-" + getAppContext().getString(R.string.sixingzhixuan));
                break;
            case CaipiaoConst.WF_SX_ZU3:// 三星组3
                wanfa = new Zu3();
                wanfa.setName("普通-" + getAppContext().getString(R.string.sanxingzu3));
                break;
            case CaipiaoConst.WF_SX_ZU6:// 三星组6
                wanfa = new Zu6();
                wanfa.setName("普通-" + getAppContext().getString(R.string.sanxingzu6));
                break;
            case CaipiaoConst.WF_EX_ZHIXUAN:// 二星直选
                wanfa = new HouN();
                ((QianNHouN) wanfa).setN(2);
                wanfa.setName("普通-" + getAppContext().getString(R.string.erxingzhixuan));
                break;
            case CaipiaoConst.WF_EX_ZUXUAN:// 二星组选
                wanfa = new Zu2();
                wanfa.setName("普通-" + getAppContext().getString(R.string.erxingzuxuan));
                break;
            case CaipiaoConst.WF_YX_ZHIXUAN:// 一星直选
                wanfa = new HouN();
                ((QianNHouN) wanfa).setN(1);
                if (id == 10067)
                    wanfa.setName("位置");
                else
                    wanfa.setName("普通-" + getAppContext().getString(R.string.yixingzhixuan));
                break;
            case CaipiaoConst.WF_DXDS:// 大小单双
                wanfa = new HouN();
                ((QianNHouN) wanfa).setN(2);
                wanfa.setName("普通-" + getAppContext().getString(R.string.daxiaodanshuang));
                break;
            case CaipiaoConst.WF_QIAN2_ZUXUAN:// 前2组选，
                wanfa = new Zu3();
                wanfa.setName("普通-"
                        + getAppContext().getString(R.string.qian2zuxuan));
                if (id == 10067)
                    wanfa.setName("组2");
                break;
            // 连2组选
            case CaipiaoConst.WF_LIAN2ZX:
                wanfa = new Zu2();
                wanfa.setName("普通-"
                        + getAppContext().getString(R.string.lian2zuxuan));
                break;
            case CaipiaoConst.WF_QIAN3_ZUXUAN:// 前3组选，
                wanfa = new Zu6();
                wanfa.setName("普通-"
                        + getAppContext().getString(R.string.qian3zuxuan));
                if (id == 10067)
                    wanfa.setName("组3");
                break;
            case CaipiaoConst.WF_REN2_2:// 任2中2
                wanfa = new RenNzhongM();
                RenNzhongM temp = (RenNzhongM) wanfa;
                temp.setN(2);// 任2
                temp.setM(2);// 中2
                break;
            case CaipiaoConst.WF_REN3_3:
                wanfa = new RenNzhongM();
                temp = (RenNzhongM) wanfa;
                temp.setN(3);
                temp.setM(3);
                break;
            case CaipiaoConst.WF_REN4_4:
                wanfa = new RenNzhongM();
                temp = (RenNzhongM) wanfa;
                temp.setN(4);
                temp.setM(4);
                break;
            case CaipiaoConst.WF_REN5_5:
                wanfa = new RenNzhongM();
                temp = (RenNzhongM) wanfa;
                temp.setN(5);
                temp.setM(5);
                break;
            case CaipiaoConst.WF_REN6_5:
                wanfa = new RenNzhongM();
                temp = (RenNzhongM) wanfa;
                temp.setN(6);
                temp.setM(6);
                break;
            case CaipiaoConst.WF_REN7_5:
                wanfa = new RenNzhongM();
                temp = (RenNzhongM) wanfa;
                temp.setN(7);
                temp.setM(7);
                break;
            case CaipiaoConst.WF_REN8_5:
                wanfa = new RenNzhongM();
                temp = (RenNzhongM) wanfa;
                temp.setN(8);
                temp.setM(8);
                break;

            case CaipiaoConst.WF_REN1:
                wanfa = new Renxuan();
                ((Renxuan) wanfa).setN(1);
                String name = String.format(CaipiaoApplication.getInstance().getResources().getString(R.string.renxuann), 1);
                wanfa.setName("普通-" + name);
                break;
            case CaipiaoConst.WF_REN2:
                wanfa = new Renxuan();
                ((Renxuan) wanfa).setN(2);
                name = String.format(CaipiaoApplication.getInstance()
                        .getResources().getString(R.string.renxuann), 2);
                wanfa.setName("普通-" + name);
                break;
            case CaipiaoConst.WF_ZUCAI_SPF:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("胜平负");
                break;
            case CaipiaoConst.WF_ZUCAI_ZJQ:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("总进球");
                break;
            case CaipiaoConst.WF_ZUCAI_RQ:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("让球胜平负");
                break;
            case CaipiaoConst.WF_ZUCAI_HUNHE:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("胜负球/让球");
                break;
            case CaipiaoConst.MIX:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("混合投注");
                break;
            case CaipiaoConst.WF_ZUCAI_BANQUANCHANG:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("半全场");
                break;
            case CaipiaoConst.WF_ZUCAI_BIFEN:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("比分");
                break;
            case CaipiaoConst.WF_SHIJIE_BEI:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("冠军竞猜");
                break;
            case CaipiaoConst.WF_OUZHOU_BEI:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("欧冠杯");
                break;
            case CaipiaoConst.WF_SHIJIE_BEI_GUANYAJUN:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("冠亚军竞猜");
                break;
            //竞彩篮球
            case CaipiaoConst.wf_basketball_bigsmall:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("大小分");
                break;
            case CaipiaoConst.wf_basketball_mix:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("混合过关");
                break;
            case CaipiaoConst.wf_basketball_shenfencha:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("胜分差");
                break;
            case CaipiaoConst.wf_basketball_winlose:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("胜负");
                break;
            case CaipiaoConst.wf_basketball_winloserangfen:
                wanfa = new AbsJingcaiWanfa();
                wanfa.setName("让分胜负");
                break;

            // /快乐10分胆拖
            case CaipiaoConst.WF_DANTUO_LIAN2ZHX:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(1);
                if (id == 10064)
                    wanfa.setName("胆拖-"
                            + getAppContext().getString(R.string.lian2zhixuan));
                else
                    wanfa.setName("胆拖-" + getQianN(2));
                break;
            case CaipiaoConst.WF_DANTUO_LIAN2ZX:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(1);
                if (id == 10064)
                    wanfa.setName("胆拖-"
                            + getAppContext().getString(R.string.lian2zuxuan));
                else
                    wanfa.setName("胆拖-"
                            + getAppContext().getString(R.string.qian2zuxuan));
                break;
            case CaipiaoConst.WF_DANTUO_QIAN3ZX:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(2);
                wanfa.setName("胆拖-"
                        + getAppContext().getString(R.string.qian3zuxuan));
                break;
            case CaipiaoConst.WF_DANTUO_QIAN3ZHIX:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(2);
                if (CaipiaoUtil.is11xr5(id))
                    wanfa.setName("胆拖-" + getQianN(3));
                else
                    wanfa.setName("胆拖-直选");
                break;
            case CaipiaoConst.WF_DANTUO_REN2:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(1);
                wanfa.setName("胆拖-"
                        + String.format(CaipiaoApplication.getInstance()
                        .getResources().getString(R.string.renxuann), 2));
                break;
            case CaipiaoConst.WF_DANTUO_REN3:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(2);
                wanfa.setName("胆拖-"
                        + String.format(CaipiaoApplication.getInstance()
                        .getResources().getString(R.string.renxuann), 3));
                break;
            case CaipiaoConst.WF_DANTUO_REN4:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(3);
                wanfa.setName("胆拖-"
                        + String.format(CaipiaoApplication.getInstance()
                        .getResources().getString(R.string.renxuann), 4));
                break;
            case CaipiaoConst.WF_DANTUO_REN5:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(4);
                wanfa.setName("胆拖-"
                        + String.format(CaipiaoApplication.getInstance()
                        .getResources().getString(R.string.renxuann), 5));
                break;
            case CaipiaoConst.WF_DANTUO_REN6:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(5);
                wanfa.setName("胆拖-"
                        + String.format(CaipiaoApplication.getInstance()
                        .getResources().getString(R.string.renxuann), 6));
                break;
            case CaipiaoConst.WF_DANTUO_REN7:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(6);
                wanfa.setName("胆拖-"
                        + String.format(CaipiaoApplication.getInstance()
                        .getResources().getString(R.string.renxuann), 7));
                break;
            case CaipiaoConst.WF_DANTUO_REN8:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(7);
                wanfa.setName("胆拖-"
                        + String.format(CaipiaoApplication.getInstance()
                        .getResources().getString(R.string.renxuann), 8));
                break;
            case CaipiaoConst.WF_DANTUO_SX_ZHIXUAN:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(2);
                wanfa.setName("胆拖-三星直选");
                break;
            case CaipiaoConst.WF_DANTUO_EX_ZUXUAN:
                wanfa = new Dantuo();
                ((Dantuo) wanfa).setMinDantu(1);
                ((Dantuo) wanfa).setMaxDantu(1);
                wanfa.setName("胆拖-二星组选");
                break;
            case CaipiaoConst.WF_DAXIAOJQ:    ///幸运赛车的大小奇偶玩法
                wanfa = new DaXiaoJQAbsWanFa();
                wanfa.setName("大小奇偶");
                break;
        }
        if (CaipiaoUtil.isKySj(id)) {
            // 快三玩法
            wanfa = new KuaiSanAbsWanFa();
            switch (type) {
                case CaipiaoConst.WF_NOSAME_THREE:
                    wanfa.setName("三不同号");
                    break;
                case CaipiaoConst.WF_NOSAME_TWO:
                    wanfa.setName("二不同号");
                    break;
                case CaipiaoConst.WF_RENYI:
                    wanfa.setName("任意选号");
                    break;
                case CaipiaoConst.WF_HEZHI:
                    wanfa.setName("和值");
                    break;
                case CaipiaoConst.WF_DUIZI:
                    wanfa.setName("猜对子");
                    break;
                case CaipiaoConst.WF_SHUNZI:
                    wanfa.setName("猜顺子");
                    break;
                case CaipiaoConst.WF_BAOZI:
                    wanfa.setName("猜豹子");
                    break;
            }
        }
        wanfa.setType(type);
        return wanfa;
    }

    private static String getQianN(int n) {
        // if (n == 1) {
        // return String.format(CaipiaoApplication.getInstance()
        // .getApplicationContext().getString(R.string.qiann), n + "");
        // }
        return String.format(CaipiaoApplication.getInstance()
                .getApplicationContext().getString(R.string.qiannzhixuan), n
                + "");
    }


    private static String getHouN(int n) {
        return String.format(CaipiaoApplication.getInstance()
                .getApplicationContext().getString(R.string.houn), n + "");
    }

    private static Context getAppContext() {
        return CaipiaoApplication.getInstance().getApplicationContext();
    }

    /**
     * @param wf     玩法
     * @param zhushu 注数，区分单式还是复式
     * @return
     */
    public static String getSubmitType(AbsWanfa wf, int zhushu, String name,
                                       int id) {
        String submitType = null;
        int cpId = 0;
        if (id == 0)
            cpId = CaipiaoApplication.getInstance().getCurrentCaipiao().getId();
        else
            cpId = id;
        if (wf.getCaipiao() != null) {
            cpId = wf.getCaipiao().getId();
        }
        // /快乐10分的相关玩法做下特殊的处理
        if (cpId == CaipiaoConst.ID_KUAILE10FEN) {
            switch (wf.getId()) {
                case CaipiaoConst.WF_QIAN1:
                    if (name.equals(CaipiaoConst.QIANYIREDTOU))
                        return URLSuffix.oneRedSingle;
                    else {
                        return zhushu == 1 ? URLSuffix.oneSingle : URLSuffix.onePoly;
                    }
                case CaipiaoConst.WF_LIAN2ZX:
                    return zhushu == 1 ? URLSuffix.twoGroupSingle
                            : URLSuffix.twoGroupPoly;
                case CaipiaoConst.WF_LIAN2ZHX:
                    return zhushu == 1 ? URLSuffix.twoDirectSingle
                            : URLSuffix.twoDirect;
                case CaipiaoConst.WF_QIAN3_ZUXUAN:
                    return zhushu == 1 ? URLSuffix.threeGroupSingle
                            : URLSuffix.threeGroupPoly;
                case CaipiaoConst.WF_QIAN3ZHIX:
                    return zhushu == 1 ? URLSuffix.threeDirectSingle
                            : URLSuffix.threeDirect;
                case CaipiaoConst.WF_REN2_2:
                    return zhushu == 1 ? URLSuffix.twoSingle : URLSuffix.twoPoly;
                case CaipiaoConst.WF_REN3_3:
                    return zhushu == 1 ? URLSuffix.threeSingle : URLSuffix.threePoly;
                case CaipiaoConst.WF_REN4_4:
                    return zhushu == 1 ? URLSuffix.fourSingle : URLSuffix.fourPoly;
                case CaipiaoConst.WF_REN5_5:
                    return zhushu == 1 ? URLSuffix.fiveSingle : URLSuffix.fivePoly;
                case CaipiaoConst.WF_DANTUO_LIAN2ZX:
                    return URLSuffix.twoGroupDraw;
                case CaipiaoConst.WF_DANTUO_LIAN2ZHX:
                    return URLSuffix.twoDirectDraw;
                case CaipiaoConst.WF_DANTUO_QIAN3ZHIX:
                    return URLSuffix.threeDirectDraw;
                case CaipiaoConst.WF_DANTUO_QIAN3ZX:
                    return URLSuffix.threeGroupDraw;
                case CaipiaoConst.WF_DANTUO_REN2:
                    return URLSuffix.twoDraw;
                case CaipiaoConst.WF_DANTUO_REN3:
                    return URLSuffix.threeDraw;
                case CaipiaoConst.WF_DANTUO_REN4:
                    return URLSuffix.fourDraw;
                case CaipiaoConst.WF_DANTUO_REN5:
                    return URLSuffix.fiveDraw;
            }
            // 快三
        } else if (CaipiaoUtil.isKySj(cpId)) {
            switch (wf.getId()) {
                case CaipiaoConst.WF_NOSAME_THREE:
                    return zhushu == 1 ? URLSuffix.threeDifferentSingle
                            : URLSuffix.threeDifferentPoly;
                case CaipiaoConst.WF_NOSAME_TWO:
                    return zhushu == 0 ? URLSuffix.twoDifferentSingle
                            : URLSuffix.twoDifferentPoly;
                case CaipiaoConst.WF_HEZHI:
                    return zhushu == 1 ? URLSuffix.sumSingle : URLSuffix.sumPoly;
                case CaipiaoConst.WF_RENYI:
                    return "anyone";
                case CaipiaoConst.WF_DUIZI:
                    if (name.indexOf("二同号复选") != -1) {
                        return URLSuffix.twoSame;
                    } else if (name.indexOf("二同号单选") != -1) {
                        return URLSuffix.twoSameAlonePoly;
                    }
                case CaipiaoConst.WF_SHUNZI:
                    return "threeLinkAll";
                case CaipiaoConst.WF_BAOZI:
                    if (name.indexOf("三同号单选") != -1) {
                        return URLSuffix.threeSameAlone;
                    } else if (name.indexOf("三同号通选") != -1) {
                        return URLSuffix.threeSameAll;
                    }
            }
        }
        if (wf instanceof Dantuo) {
            if (wf instanceof Dantuo2) {// 组三或者组六胆拖
                Dantuo2 d = (Dantuo2) wf;
                return d.getDantuoType() == Dantuo2.ZU3 ? URLSuffix.group3Draw
                        : URLSuffix.group6Draw;
            } else {
                switch (wf.getType()) {
                    case CaipiaoConst.WF_DANTUO_REN2:
                        return URLSuffix.twoDraw;
                    case CaipiaoConst.WF_DANTUO_REN3:
                        return URLSuffix.threeDraw;
                    case CaipiaoConst.WF_DANTUO_REN4:
                        return URLSuffix.fourDraw;
                    case CaipiaoConst.WF_DANTUO_REN5:
                        return URLSuffix.fiveDraw;
                    case CaipiaoConst.WF_DANTUO_REN6:
                        return URLSuffix.sixDraw;
                    case CaipiaoConst.WF_DANTUO_REN7:
                        return URLSuffix.sevenDraw;
                    case CaipiaoConst.WF_DANTUO_REN8:
                        return URLSuffix.eightDraw;
                    case CaipiaoConst.WF_DANTUO_LIAN2ZX:
                        return URLSuffix.twoGroupDraw;
                    case CaipiaoConst.WF_DANTUO_LIAN2ZHX:
                        return URLSuffix.twoDirectDraw;
                    case CaipiaoConst.WF_DANTUO_QIAN3ZHIX:
                        if (cpId == CaipiaoConst.ID_FUCAI3D || cpId == CaipiaoConst.ID_PAILIE3)
                            return URLSuffix.directDraw;
                        else
                            return URLSuffix.threeDirectDraw;
                    case CaipiaoConst.WF_DANTUO_SX_ZHIXUAN:
                        if (cpId == CaipiaoConst.ID_XINSHISHICAI)
                            return "threeStarDirectDraw";
                        else
                            return "threeStarDraw";
                    case CaipiaoConst.WF_DANTUO_EX_ZUXUAN:
                        return "twoStarGroupDraw";
                    case CaipiaoConst.WF_DANTUO_QIAN3ZX:
                        return URLSuffix.threeGroupDraw;
                    default:
                        return URLSuffix.draw;
                }
            }
        }
        if (wf.getType() == CaipiaoConst.WF_DALETOUSHENGXIAO) {
            return URLSuffix.sxPoly;
        } else if (wf.getType() == CaipiaoConst.WF_ZU3_SINGLE) {
            return URLSuffix.groupSingle;
        }
        switch (cpId) {
            case CaipiaoConst.ID_QIXINGCAI:
            case CaipiaoConst.ID_TICAI6JIA1:
                return zhushu == 1 ? URLSuffix.single : URLSuffix.poly;
            case CaipiaoConst.ID_DALETOU:
                return zhushu == 1 ? URLSuffix.lottoSingle : URLSuffix.lottoPoly;
        }

        if (wf instanceof NormalLetou) {
            return zhushu == 1 ? URLSuffix.single : URLSuffix.poly;
        }

        switch (wf.getId()) {
            case CaipiaoConst.WF_NORMAL_PAILIE:
                if (cpId == CaipiaoConst.ID_PAILIE5) {
                    submitType = zhushu == 1 ? URLSuffix.pl5Single
                            : URLSuffix.pl5Poly;
                } else {
                    submitType = URLSuffix.direct;
                }
                break;
            case CaipiaoConst.WF_ZU3:
                submitType = URLSuffix.group3Poly;
                break;
            case CaipiaoConst.WF_ZU6:
                submitType = URLSuffix.group6Poly;
                break;
            case CaipiaoConst.WF_YX_ZHIXUAN:
                if (cpId == 10061)
                    submitType = "oneStar";
                else if (cpId == 10067)
                    submitType = zhushu == 1 ? URLSuffix.wzSingle
                            : URLSuffix.wzPoly;
                else
                    submitType = URLSuffix.oneStar;
                break;
            case CaipiaoConst.WF_EX_ZHIXUAN:
                if (zhushu == 1) {
                    submitType = URLSuffix.twoStarDirectSingle;
                } else {
                    submitType = URLSuffix.twoStarDirectPoly;
                }
                break;
            case CaipiaoConst.WF_EX_ZUXUAN:
                if (zhushu == 1) {
                    submitType = URLSuffix.twoStarGroupSingle;
                } else {
                    submitType = URLSuffix.twoStarGroupPoly;
                }
                break;
            case CaipiaoConst.WF_SX_ZHIXUAN:
                if (cpId == 10061) {
                    if (zhushu == 1) {
                        submitType = URLSuffix.threeStarDirectSingle;
                    } else {
                        submitType = URLSuffix.threeStarDirectPoly;
                    }
                } else
                    submitType = URLSuffix.threeStarPoly;
                break;
            case CaipiaoConst.WF_SIXING_ZHIXUAN:
                if (zhushu == 1) {
                    submitType = URLSuffix.fourStarSingle;
                } else {
                    submitType = URLSuffix.fourStarPoly;
                }
                break;
            case CaipiaoConst.WF_SX_ZU3:
                submitType = URLSuffix.threeStarGroup3Poly;
                break;
            case CaipiaoConst.WF_SX_ZU6:
                submitType = URLSuffix.threeStarGroup6Poly;
                break;
            case CaipiaoConst.WF_WX_ZHIXUAN:
                if (zhushu == 1) {
                    submitType = URLSuffix.fiveStarSingle;
                } else {
                    submitType = URLSuffix.fiveStarPoly;
                }
                break;
            case CaipiaoConst.WF_WX_TONGXUAN:
                if (zhushu == 1) {
                    submitType = URLSuffix.fiveStarAllSingle;
                } else {
                    submitType = URLSuffix.fiveStarAllPoly;
                }
                break;
            // 大小単
            case CaipiaoConst.WF_DXDS:
                submitType = URLSuffix.dxds;
                break;
            case CaipiaoConst.WF_REN1:
                submitType = URLSuffix.renOne;
                break;
            case CaipiaoConst.WF_REN2:
                submitType = URLSuffix.renTwo;
                break;

            // 老11选5
            case CaipiaoConst.WF_QIAN1:
            case CaipiaoConst.WF_DANTUO_REN1:
                if (cpId == 10067)
                    submitType = zhushu == 1 ? URLSuffix.oneDirectSingle : URLSuffix.oneDirectPoly;
                else
                    submitType = zhushu == 1 ? URLSuffix.oneSingle : URLSuffix.onePoly;
                break;
            case CaipiaoConst.WF_REN2_2:
                submitType = zhushu == 1 ? URLSuffix.twoSingle : URLSuffix.twoPoly;
                break;
            case CaipiaoConst.WF_QIAN2:
                if (cpId == 10067)
                    submitType = zhushu == 1 ? URLSuffix.twoDirectSingle : URLSuffix.twoDirect;
                else
                    submitType = URLSuffix.twoDirect;
                break;
            case CaipiaoConst.WF_QIAN2_ZUXUAN:
                submitType = zhushu == 1 ? URLSuffix.twoGroupSingle
                        : URLSuffix.twoGroupPoly;
                break;
            case CaipiaoConst.WF_REN3_3:
                submitType = zhushu == 1 ? URLSuffix.threeSingle
                        : URLSuffix.threePoly;
                break;
            case CaipiaoConst.WF_QIAN3:
                if (cpId == 10067)
                    submitType = zhushu == 1 ? URLSuffix.threeDirectSingle : URLSuffix.threeDirect;
                else
                    submitType = URLSuffix.threeDirect;
                break;
            case CaipiaoConst.WF_QIAN3_ZUXUAN:
                submitType = zhushu == 1 ? URLSuffix.threeGroupSingle
                        : URLSuffix.threeGroupPoly;
                break;
            case CaipiaoConst.WF_REN4_4:
                submitType = zhushu == 1 ? URLSuffix.fourSingle : URLSuffix.fourPoly;
                break;
            case CaipiaoConst.WF_REN5_5:
                submitType = zhushu == 1 ? URLSuffix.fiveSingle : URLSuffix.fivePoly;
                break;
            case CaipiaoConst.WF_REN6_5:
                submitType = zhushu == 1 ? URLSuffix.sixSingle : URLSuffix.sixPoly;
                break;
            case CaipiaoConst.WF_REN7_5:
                submitType = zhushu == 1 ? URLSuffix.sevenSingle
                        : URLSuffix.sevenPoly;
                break;
            case CaipiaoConst.WF_REN8_5:
                submitType = zhushu == 1 ? URLSuffix.eightSingle
                        : URLSuffix.eightPoly;
                break;
            case CaipiaoConst.WF_DAXIAOJQ:
                submitType = "dxjo";
                break;
        }
        return submitType;
    }
}
