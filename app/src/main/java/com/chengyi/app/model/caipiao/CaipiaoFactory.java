package com.chengyi.app.model.caipiao;

import android.content.Context;
import android.content.SharedPreferences;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengyi.R;
import com.chengyi.app.model.param.SPKey;
import com.chengyi.app.model.wanfa.AbsWanfa;
import com.chengyi.app.model.wanfa.WanfaFactory;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class CaipiaoFactory {
    SharedPreferences preferences;
    private ArrayList<String> pictureURL;
    private ArrayList<String> pictureLink;
    public int nCurrentTopImgPosition = 0;
    private String discoveryUrl;

    public ArrayList<String> getPictureURL() {
        return pictureURL;
    }


    public ArrayList<String> getPictureLink() {
        return pictureLink;
    }


    public String getDiscoveryUrl() {
        return discoveryUrl;
    }

    private static CaipiaoFactory instance;
    Context mContext;

    private CaipiaoFactory(Context mContext) {
        this.mContext = mContext;
        preferences = CaipiaoUtil.getCpSharedPreferences(mContext);
        initCaipiaoList();
    }

    public static CaipiaoFactory getInstance(Context mContext) {
        if (instance == null) {
            instance = new CaipiaoFactory(mContext);
        }
        return instance;
    }

    List<Caipiao> caipiaoList, serverCaipiaoList;

    public List<Caipiao> getCaipiaoList() {
        return caipiaoList;
    }

    public List<Caipiao> getServerCaipiaoList() {
        return serverCaipiaoList;
    }

    public void setServerCaipiaoList(List<Caipiao> serverCaipiaoList) {
        this.serverCaipiaoList = serverCaipiaoList;
    }

    public List<Caipiao> getCaipiaoListByType(int type) {
        List<Caipiao> tempList = new ArrayList<Caipiao>();
        for (int i = 0; i < caipiaoList.size(); i++) {
            if (caipiaoList.get(i).getCaipiaoType() == type) {
                tempList.add(caipiaoList.get(i));
            }
        }
        return tempList;
    }

    public Caipiao getCaipiaoById(int id) {

        if (caipiaoList != null) {
            for (Caipiao cp : caipiaoList) {
                if (cp.getId() == id) {
                    return cp;
                }
            }
        }
        return null;
    }

    public static final int FUCAI = 0;
    public static final int TICAI = 1;
    public static final int KUAIKAI = 2;
    public static final int JINGCAI = 3;


    private void initCaipiaoList() {
        int wanfaType = -1;
        preferences = CaipiaoUtil.getCpSharedPreferences(mContext);
        caipiaoList = new ArrayList<>();
        pictureURL = new ArrayList<>();
        pictureLink = new ArrayList<>();
        discoveryUrl = "";


        // 双色球
        Caipiao cp = new Caipiao(FUCAI);
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.list_cpiconssq);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.shuangseqiukaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 33));
        // 设置后区号码
        cp.setHouquList(CaipiaoUtil.getHaomaList(1, 16));
        cp.setQianquMaxCount(20);
        cp.setQianquMinCount(6);
        cp.setHouquMaxCount(16);
        cp.setHouquMinCount(1);
        cp.setId(CaipiaoConst.ID_SHUANGSEQIU);
        cp.setName(mContext.getResources().getString(R.string.shuangseqiu));
        cp.setMessage("2+1元变千万");
        cp.setResource(R.layout.pop_wanfa);
        List<AbsWanfa> wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NORMAL_LETOU,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);


        // 福彩3D
        cp = new Caipiao(FUCAI);
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.list_cpiconfc3d);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.fucai3dkaijiangshijian));
        // 设置前区号码
        cp.setMessage("简单3位赢500万");
        cp.setQianquList(CaipiaoUtil.getHaomaList(0, 9));
        cp.setQianquMaxCount(30);// 前区总共最大个数
        cp.setQianquMinCount(3);// 前区总共最小个数
        cp.setId(CaipiaoConst.ID_FUCAI3D);
        cp.setName(mContext.getResources().getString(R.string.fucai3d));
        cp.setResource(R.layout.pop_fc3dwanfa);
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NORMAL_PAILIE,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_ZU3_SINGLE,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_ZU3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_ZU6, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_ZU3,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_ZU6,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZHIX,
                cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        List<String> wanfaKindList = new ArrayList<String>();
        wanfaKindList.add(mContext.getString(R.string.fushi));
        wanfaKindList.add(mContext.getString(R.string.dantuo));
        cp.setWanfaKindList(wanfaKindList);
        caipiaoList.add(cp);

        // 七乐彩
        cp = new Caipiao(FUCAI);
        cp.setMessage("2元中500万");
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.list_cpicon7l);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.qilecaikaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 30));
        // 设置后区号码
        cp.setQianquMaxCount(18);
        cp.setQianquMinCount(7);
        cp.setId(CaipiaoConst.ID_QILECAI);
        cp.setName(mContext.getResources().getString(R.string.qilecai));
        cp.setResource(R.layout.pop_wanfa);
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NORMAL_LETOU,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);

        // 新时时彩(江西时时彩)
        cp = new Caipiao(KUAIKAI);

        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_cpiconxssc);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.xinshishicaikaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(0, 9));
        cp.setQianquMaxCount(50);
        cp.setQianquMinCount(5);
        cp.setId(CaipiaoConst.ID_XINSHISHICAI);
        cp.setName(mContext.getResources().getString(R.string.xinshishicai));
        cp.setIssueNum(84);
        cp.setMessage("单注可赢十万");
        cp.setResource(R.layout.pop_xsscwanfa);
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SX_ZHIXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DXDS, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_WX_TONGXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_WX_ZHIXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SIXING_ZHIXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SX_ZU3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SX_ZU6, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_EX_ZHIXUAN,
                cp.getId()));
        wanfaList
                .add(WanfaFactory.create(CaipiaoConst.WF_EX_ZUXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_YX_ZHIXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN1, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN2, cp.getId()));
        //胆拖
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_SX_ZHIXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_EX_ZUXUAN, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(4));// 默认3星直选
        caipiaoList.add(cp);

        // 重庆时时彩
        cp = new Caipiao(KUAIKAI);
        cp.setSort(7);
        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_cpiconlssc);
        cp.setMessage("6选3,中奖简单");
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.laoshishicaikaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(0, 9));
        cp.setQianquMaxCount(50);
        cp.setQianquMinCount(5);
        cp.setId(CaipiaoConst.ID_LAOSHISHICAI);
        cp.setName(mContext.getResources().getString(R.string.laoshishicai));
        cp.setIssueNum(120);
        cp.setResource(R.layout.pop_lsscwanfa);
        wanfaList = new ArrayList<AbsWanfa>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SX_ZHIXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DXDS, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_WX_TONGXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_WX_ZHIXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SX_ZU3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SX_ZU6, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_EX_ZHIXUAN,
                cp.getId()));
        wanfaList
                .add(WanfaFactory.create(CaipiaoConst.WF_EX_ZUXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_YX_ZHIXUAN,
                cp.getId()));
        //胆拖
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_SX_ZHIXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_EX_ZUXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_ZU3,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_ZU6,
                cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(3));// 默认3星直选
        wanfaKindList = new ArrayList<String>();
        wanfaKindList.add(mContext.getString(R.string.fushi));
        cp.setWanfaKindList(wanfaKindList);
        caipiaoList.add(cp);

        // 11运
        cp = new Caipiao(KUAIKAI);
        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_cpiconl11x5);
        cp.setKaijiangshijian(mContext.getResources().getString(R.string.lao11xuan5kaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 11));
        cp.setQianquMaxCount(11);
        cp.setQianquMinCount(5);
        cp.setId(CaipiaoConst.ID_LAO11XUAN5);
        cp.setName(mContext.getResources().getString(R.string.lao11xuan5));
        cp.setIssueNum(78);
        cp.setMessage("10分钟赚1170元");
        cp.setResource(R.layout.pop_l11x5wanfa);
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3_ZUXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN1, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN2_2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2_ZUXUAN,
                cp.getId()));


        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN3_3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN4_4, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN5_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN6_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN7_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN8_5, cp.getId()));
        // 胆拖
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN2,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN1,
                cp.getId()));

        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN3,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN4,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN5,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN6,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN7,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN8,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZHX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZHIX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZX,
                cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(6));// 默认前3组选list_cpiconx11x5
        wanfaKindList = new ArrayList<String>();
        wanfaKindList.add(mContext.getString(R.string.fushi));
        cp.setWanfaKindList(wanfaKindList);
        caipiaoList.add(cp);

        // 江西11选5
        cp = new Caipiao(KUAIKAI);
        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_cpiconx11x5);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.xin11xuan5kaijiangshijian));
        // cp.setKaijiangshijian(mContext.getResources().getString(
        // R.string.xinshishicaikaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 11));
        cp.setQianquMaxCount(11);
        cp.setQianquMinCount(5);
        cp.setMessage("78期中奖不停");
        cp.setId(CaipiaoConst.ID_XIN11XUAN5);
        cp.setName(mContext.getResources().getString(R.string.xin11xuan5));
        cp.setIssueNum(78);
        cp.setResource(R.layout.pop_l11x5wanfa);
        wanfaList = new ArrayList<AbsWanfa>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3_ZUXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN1, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN2_2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2_ZUXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN1,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN3_3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN4_4, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN5_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN6_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN7_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN8_5, cp.getId()));
        // 胆拖
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN2,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN3,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN4,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN5,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN6,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN7,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN8,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZHX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZHIX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZX,
                cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(6));// 默认前3组选
        wanfaKindList = new ArrayList<String>();
        wanfaKindList.add(mContext.getString(R.string.fushi));
        cp.setWanfaKindList(wanfaKindList);
        caipiaoList.add(cp);


        //shanghai11 xuan 5
        cp = new Caipiao(KUAIKAI);
        cp.setSort(5);

        cp.setIconResId(R.drawable.list_cpicon11x5);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.lao11xuan5kaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 11));
        cp.setQianquMaxCount(11);
        cp.setQianquMinCount(5);
        cp.setId(CaipiaoConst.ID_11XUAN5);
        cp.setName(mContext.getResources().getString(R.string.now11xuan5));
        cp.setMessage("受欢迎的高频彩");
        cp.setIssueNum(90);
        cp.setResource(R.layout.pop_l11x5wanfa);
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3_ZUXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN1, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN2_2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2_ZUXUAN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN3_3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN4_4, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN5_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN6_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN7_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN8_5, cp.getId()));
        // 胆拖
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN1,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN2,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN3,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN4,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN5,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN6,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN7,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN8,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZHX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZHIX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZX,
                cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(6));// 默认前3组选
        wanfaKindList = new ArrayList<String>();
        wanfaKindList.add(mContext.getString(R.string.fushi));
        cp.setWanfaKindList(wanfaKindList);
        caipiaoList.add(cp);
        // 快乐十分
        cp = new Caipiao(KUAIKAI);
        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_cpiconkl10f);
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 20));
        cp.setQianquMaxCount(60);
        cp.setQianquMinCount(5);
        cp.setMessage("玩到凌晨2点");
        cp.setId(CaipiaoConst.ID_KUAILE10FEN);
        cp.setName(mContext.getResources().getString(R.string.kuaile10fen));
        cp.setIssueNum(97);
        cp.setResource(R.layout.pop_k10fenwanfa);
        wanfaList = new ArrayList<AbsWanfa>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN3_3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN1, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_LIAN2ZX, cp.getId()));
        wanfaList
                .add(WanfaFactory.create(CaipiaoConst.WF_LIAN2ZHX, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3_ZUXUAN,
                cp.getId()));
        wanfaList
                .add(WanfaFactory.create(CaipiaoConst.WF_QIAN3ZHIX, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN2_2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN4_4, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN5_5, cp.getId()));
        // /胆拖
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZHX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZHIX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN2,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN3,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN4,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN5,
                cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(6));// 默认任选三
        wanfaKindList = new ArrayList<String>();
        wanfaKindList.add(mContext.getString(R.string.fushi));
        cp.setWanfaKindList(wanfaKindList);
        caipiaoList.add(cp);


        // 福建11选5
        cp = new Caipiao(KUAIKAI);
        cp.setSort(1);
        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_fujianl11x5);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.lao11xuan5kaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 11));
        cp.setQianquMaxCount(11);
        cp.setQianquMinCount(5);
        cp.setId(CaipiaoConst.ID_FUJM11XUAN5);
        cp.setName(mContext.getResources().getString(R.string.fujm11xuan5));
        cp.setIssueNum(78);
        cp.setMessage("多种幸运的玩法");
        cp.setResource(R.layout.pop_l11x5wanfa);
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3_ZUXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN1, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN2_2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2_ZUXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN3_3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN4_4, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN5_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN6_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN7_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN8_5, cp.getId()));
        // 胆拖
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN1, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN4, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN6, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN7, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN8, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZX, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZHX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZHIX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZX,
                cp.getId()));

        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(6));// 默认前3组选list_cpiconx11x5
        wanfaKindList = new ArrayList<>();
        wanfaKindList.add(mContext.getString(R.string.fushi));
        cp.setWanfaKindList(wanfaKindList);
        caipiaoList.add(cp);


        // 贵州11选5
        cp = new Caipiao(KUAIKAI);
        cp.setSort(3);
        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_gvzbl11x5);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.lao11xuan5kaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 11));
        cp.setQianquMaxCount(11);
        cp.setQianquMinCount(5);
        cp.setId(CaipiaoConst.ID_GVZB11XUAN5);
        cp.setName(mContext.getResources().getString(R.string.gv11xuan5));
        cp.setIssueNum(78);
        cp.setMessage("人气旺\t受热捧");
        cp.setResource(R.layout.pop_l11x5wanfa);
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3_ZUXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN1, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN2_2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2_ZUXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN3_3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN4_4, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN5_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN6_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN7_5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_REN8_5, cp.getId()));
        // 胆拖
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN1, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN4, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN5, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN6, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN7, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_REN8, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZX, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_LIAN2ZHX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZHIX,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZX,
                cp.getId()));

        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(6));// 默认前3组选list_cpiconx11x5
        wanfaKindList = new ArrayList<>();
        wanfaKindList.add(mContext.getString(R.string.fushi));
        cp.setWanfaKindList(wanfaKindList);
        caipiaoList.add(cp);


        // 福彩15选5
        cp = new Caipiao(FUCAI);
        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_cpicon15x5);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.fucai15xuan5kaijiangshijian));
        // 设置前区号码
        cp.setMessage("2元赢大奖");
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 15));
        // 设置后区号码
        // cp.setHouquList(CaipiaoUtil.getHaomaList(1, 12));
        cp.setQianquMaxCount(13);
        cp.setQianquMinCount(5);
        cp.setId(CaipiaoConst.ID_FUCAI15XUAN5);
        cp.setName(mContext.getResources().getString(R.string.fucai15xuan5));
        cp.setResource(R.layout.pop_wanfa);
        wanfaList = new ArrayList<AbsWanfa>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NORMAL_LETOU,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);

        // 超级大乐透
        cp = new Caipiao(TICAI);
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setMessage("3元中大奖");
        cp.setIconResId(R.drawable.list_cpicondlt);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.daletoukaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 35));
        cp.setQianquMaxCount(22);
        cp.setQianquMinCount(5);
        cp.setHouquList(CaipiaoUtil.getHaomaList(1, 12));
        cp.setHouquMaxCount(12);
        cp.setHouquMinCount(2);
        cp.setId(CaipiaoConst.ID_DALETOU);
        cp.setName(mContext.getResources().getString(R.string.chaojidaletou));
        cp.setResource(R.layout.pop_wanfa);
        wanfaList = new ArrayList<AbsWanfa>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NORMAL_LETOU,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);

        // 排列3
        cp = new Caipiao(TICAI);
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.queue3);
        // 设置前区号码
        cp.setMessage("单注赢千元");
        cp.setQianquList(CaipiaoUtil.getHaomaList(0, 9));
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.pailie3pailie5kaijiangshijian));
        cp.setQianquMaxCount(30);
        cp.setQianquMinCount(3);
        cp.setId(CaipiaoConst.ID_PAILIE3);
        cp.setName(mContext.getResources().getString(R.string.pailie3));
        cp.setResource(R.layout.pop_fc3dwanfa);
        wanfaList = new ArrayList<AbsWanfa>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NORMAL_PAILIE,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_ZU3_SINGLE,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_ZU3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_ZU6, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_ZU3,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_ZU6,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO_QIAN3ZHIX,
                cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(0));
        wanfaKindList = new ArrayList<String>();
        wanfaKindList.add(mContext.getString(R.string.fushi));
        wanfaKindList.add(mContext.getString(R.string.dantuo));
        cp.setWanfaKindList(wanfaKindList);
        caipiaoList.add(cp);

        // 排列5
        cp = new Caipiao(TICAI);
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.queue5);
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(0, 9));
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.pailie3pailie5kaijiangshijian));
        cp.setQianquMaxCount(50);
        cp.setQianquMinCount(5);
        cp.setId(CaipiaoConst.ID_PAILIE5);
        cp.setName(mContext.getResources().getString(R.string.pailie5));
        cp.setMessage("单注赢千元");
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NORMAL_PAILIE,
                cp.getId()));
        cp.setWanfaList(wanfaList);
        cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);

        // 七星彩
        cp = new Caipiao(TICAI);
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.list_cpiconqxc);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.qixingcaikaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(0, 9));
        // 设置后区号码
        cp.setMessage("奖金最高500万");
        // cp.setHouquList(CaipiaoUtil.getHaomaList(1, 12));
        cp.setQianquMaxCount(70);// 总共能选70个
        cp.setQianquMinCount(7); // 总共要选7个，每行至少一个。
        cp.setId(CaipiaoConst.ID_QIXINGCAI);
        cp.setName(mContext.getResources().getString(R.string.qixingcai));
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NORMAL_PAILIE,
                cp.getId()));
        cp.setWanfaList(wanfaList);
        cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);


        // 体彩6+1
        cp = new Caipiao(TICAI);
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.list_cpicon6j1);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.ticai6jia1kaijiangshijian));
        cp.setMessage("特等奖金等你拿");
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(0, 9));
        cp.setHouquList(CaipiaoUtil.getHaomaList(0, 9));
        cp.setQianquMaxCount(70);
        cp.setQianquMinCount(7);
        cp.setId(CaipiaoConst.ID_TICAI6JIA1);
        cp.setName(mContext.getResources().getString(R.string.ticai6jia1));
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NORMAL_PAILIE,
                cp.getId()));
        cp.setWanfaList(wanfaList);
        cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);

        // 体彩20选5
        cp = new Caipiao(TICAI);
        cp.setMessage("中奖简单无需排队");
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.list_cpicon20x5);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.ticai20xuan5kaijiangshijian));
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 20));
        cp.setQianquMaxCount(18);
        cp.setQianquMinCount(5);
        cp.setId(CaipiaoConst.ID_TICAI20XUAN5);
        cp.setName(mContext.getResources().getString(R.string.ticai20xuan5));
        cp.setResource(R.layout.pop_wanfa);
        wanfaList = new ArrayList<AbsWanfa>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NORMAL_LETOU,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DANTUO, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        // cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);
        // 内蒙快3
        cp = new Caipiao(TICAI);
        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_cpiconxk3);
        cp.setId(CaipiaoConst.ID_NEWKUAI3);
        cp.setName(mContext.getResources().getString(R.string.xinkuai3));
        // 设置玩法布局文件
        cp.setMessage("易中奖的高频彩");
        cp.setResource(R.layout.pop_k3wanfa);
        cp.setIssueNum(73);
        // cp.setCurrentWanfa(wanfaList.get(0));
        wanfaList = new ArrayList<AbsWanfa>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NOSAME_THREE,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NOSAME_TWO,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_HEZHI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_RENYI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DUIZI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SHUNZI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_BAOZI, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", 0);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);


        // 贵州快3
        cp = new Caipiao(TICAI);
        cp.setSort(4);
        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_cpiconxk3);
        cp.setId(CaipiaoConst.ID_GVZBKUAI3);
        cp.setName(mContext.getResources().getString(R.string.gvkuai3));
        // 设置玩法布局文件
        cp.setMessage("易中奖的高频彩");
        cp.setResource(R.layout.pop_k3wanfa);
        cp.setIssueNum(73);
        // cp.setCurrentWanfa(wanfaList.get(0));
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NOSAME_THREE,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NOSAME_TWO,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_HEZHI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_RENYI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DUIZI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SHUNZI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_BAOZI, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", 0);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);


        // 福建快3
        cp = new Caipiao(TICAI);
        cp.setSort(2);

        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_cpiconxk3);
        cp.setId(CaipiaoConst.ID_FUJMKUAI3);
        cp.setName(mContext.getResources().getString(R.string.fukuai3));
        // 设置玩法布局文件
        cp.setMessage("火热玩法奖金高");
        cp.setResource(R.layout.pop_k3wanfa);
        cp.setIssueNum(73);
        // cp.setCurrentWanfa(wanfaList.get(0));
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NOSAME_THREE,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NOSAME_TWO,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_HEZHI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_RENYI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DUIZI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SHUNZI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_BAOZI, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", 0);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);


        //
        //广西快3
        cp = new Caipiao(TICAI);
        cp.setIconResId(R.drawable.list_cpiconxk3);
        cp.setId(CaipiaoConst.ID_GUANGXIKUAI3);
        cp.setName(mContext.getResources().getString(R.string.guanxikuai3));
        // 设置玩法布局文件
        cp.setMessage("玩骰子轻松上手");
        cp.setResource(R.layout.pop_k3wanfa);
        cp.setIssueNum(73);
        // cp.setCurrentWanfa(wanfaList.get(0));
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NOSAME_THREE,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NOSAME_TWO,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_HEZHI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_RENYI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DUIZI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SHUNZI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_BAOZI, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", 0);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);

        //安徽快3
        cp = new Caipiao(TICAI);
        cp.setIconResId(R.drawable.list_cpiconxk3);
        cp.setId(CaipiaoConst.ID_ANHUIKUAI3);
        cp.setName(mContext.getResources().getString(R.string.anhuikuai3));
        // 设置玩法布局文件
        cp.setMessage("飞一般的好玩");
        cp.setResource(R.layout.pop_k3wanfa);
        cp.setIssueNum(73);
        // cp.setCurrentWanfa(wanfaList.get(0));
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NOSAME_THREE,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_NOSAME_TWO,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_HEZHI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_RENYI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DUIZI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_SHUNZI, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_BAOZI, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", 0);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);

        // 竞彩足球
        cp = new Caipiao(JINGCAI);
        cp.setType(CaipiaoConst.TYPE_MATCH);
        cp.setIconResId(R.drawable.list_cpiconjczq);
        cp.setId(CaipiaoConst.ID_JINGCAIZUQIU);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.jingcaizuqiu));
        cp.setName(mContext.getResources().getString(R.string.jingcaizuqiu));
        cp.setMessage("返奖率升至73%");
        wanfaList = new ArrayList<AbsWanfa>();
        wanfaList
                .add(WanfaFactory.create(CaipiaoConst.WF_ZUCAI_SPF, cp.getId()));
        wanfaList
                .add(WanfaFactory.create(CaipiaoConst.WF_ZUCAI_RQ, cp.getId()));
        wanfaList
                .add(WanfaFactory.create(CaipiaoConst.WF_ZUCAI_ZJQ, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_ZUCAI_HUNHE,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_ZUCAI_BANQUANCHANG,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_ZUCAI_BIFEN,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.MIX, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(SPKey.footballball_hiswanfa, 0);
        cp.setCurrentWanfa(wanfaList.get(wanfaType));
        caipiaoList.add(cp);

        // ball
        cp = new Caipiao(JINGCAI);
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.list_cpicondcjs);
        cp.setId(CaipiaoConst.ID_BALL);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.bjdc));
        cp.setName(mContext.getResources().getString(R.string.bjdc));
        cp.setMessage("猜中1场就有奖");
        caipiaoList.add(cp);


        // six
        cp = new Caipiao(JINGCAI);
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.list_cpicon6cb);
        cp.setResource(R.layout.wanfa_six);
        cp.setId(CaipiaoConst.ID_SIX);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.siz_chang));
        cp.setName(mContext.getResources().getString(R.string.siz_chang));
        cp.setMessage("返奖率65%");

        caipiaoList.add(cp);


        // for
        cp = new Caipiao(JINGCAI);
        cp.setType(CaipiaoConst.TYPE_NUM);
        cp.setIconResId(R.drawable.list_cpiconsf14c1);
        cp.setResource(R.layout.wanfa_six);
        cp.setId(CaipiaoConst.ID_FOURGOAL);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.for_chang));
        cp.setName(mContext.getResources().getString(R.string.for_chang));
        cp.setMessage("新玩法赢过万奖金");
        caipiaoList.add(cp);

        // 竞彩篮球
        cp = new Caipiao(JINGCAI);
        cp.setType(CaipiaoConst.TYPE_MATCH);
        cp.setIconResId(R.drawable.list_cpiconjclq);
        cp.setId(CaipiaoConst.ID_BASKETBALL);
        cp.setKaijiangshijian(mContext.getResources().getString(
                R.string.basketball));
        cp.setName(mContext.getResources().getString(R.string.basketball));
        cp.setMessage("2串1倍投收益高");
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.wf_basketball_winlose,
                cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.wf_basketball_winloserangfen,
                cp.getId()));
        wanfaList
                .add(WanfaFactory.create(CaipiaoConst.wf_basketball_shenfencha, cp.getId()));
        wanfaList
                .add(WanfaFactory.create(CaipiaoConst.wf_basketball_bigsmall, cp.getId()));
        wanfaList
                .add(WanfaFactory.create(CaipiaoConst.wf_basketball_mix, cp.getId()));


        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", 0);
        cp.setCurrentWanfa(wanfaList.get(wanfaType));
        caipiaoList.add(cp);

        // 任选9场
        cp = new Caipiao(JINGCAI);
        cp.setType(CaipiaoConst.TYPE_MATCH);
        cp.setMessage("复式中奖概率高");
        cp.setIconResId(R.drawable.list_cpiconr9c);
        cp.setId(CaipiaoConst.ID_RENXUAN9CHANG);
        cp.setQianquMinCount(9);
        cp.setName(mContext.getResources().getString(R.string.renxuan9chang));
        caipiaoList.add(cp);
        // 胜负14场
        cp = new Caipiao(JINGCAI);
        cp.setType(CaipiaoConst.TYPE_MATCH);
        cp.setIconResId(R.drawable.list_cpiconsf14c);
        cp.setId(CaipiaoConst.ID_SHENGFU14CHANG);
        cp.setQianquMinCount(14);
        cp.setName(mContext.getResources().getString(R.string.shengfu14chang));
        cp.setMessage("猜胜平负赢500万");
        caipiaoList.add(cp);


//        // 冠亚军竞猜
//        cp = new Caipiao(JINGCAI);
//        cp.setType(CaipiaoConst.TYPE_MATCH);
//        cp.setIconResId(R.drawable.list_cpicongjb);
//        cp.setId(CaipiaoConst.ID_GUANYAJUN);
//        cp.setName(mContext.getResources().getString(R.string.guanyajun));
//        caipiaoList.add(cp);
//        wanfaList = new ArrayList<>();
//        wanfaList
//                .add(WanfaFactory.create(CaipiaoConst.WF_SHIJIE_BEI, cp.getId()));
//        wanfaList
//                .add(WanfaFactory.create(CaipiaoConst.WF_SHIJIE_BEI_GUANYAJUN, cp.getId()));
//        cp.setWanfaList(wanfaList);
//        wanfaType = preferences.getInt(cp.getId() + "WfType", 0);
//        cp.setCurrentWanfa(wanfaList.get(wanfaType));
//        caipiaoList.add(cp);
//


        // 幸运赛车
        cp = new Caipiao(KUAIKAI);
        cp.setType(CaipiaoConst.TYPE_QUICK);
        cp.setIconResId(R.drawable.list_cpiconxysc);
        // 设置前区号码
        cp.setQianquList(CaipiaoUtil.getHaomaList(1, 12));
        cp.setQianquMaxCount(12);
        cp.setQianquMinCount(1);
        cp.setIssueNum(84);
        cp.setId(CaipiaoConst.ID_LUCKYCAR);
        cp.setName(mContext.getResources().getString(R.string.xinyunsaiche));
        cp.setMessage("火热高频游戏");
        cp.setResource(R.layout.pop_xyscwanfa);
        wanfaList = new ArrayList<>();
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN1, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_YX_ZHIXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN2_ZUXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_QIAN3_ZUXUAN, cp.getId()));
        wanfaList.add(WanfaFactory.create(CaipiaoConst.WF_DAXIAOJQ, cp.getId()));
        cp.setWanfaList(wanfaList);
        wanfaType = preferences.getInt(cp.getId() + "WfType", -1);
        if (wanfaType != -1 && cp.getWanfaByType(wanfaType) != null) {
            cp.setCurrentWanfa(cp.getWanfaByType(wanfaType));
        } else
            cp.setCurrentWanfa(wanfaList.get(0));
        caipiaoList.add(cp);
    }


    public Caipiao getCaipiaobyName(String name) {
        for (int i = 0; i < caipiaoList.size(); i++) {
            if (caipiaoList.get(i).getName().equals(name))
                return caipiaoList.get(i);
        }
        return null;
    }

    Caipiao cp;

    @Deprecated
    public void create(String result, boolean isRefresh) {
        if (preferences == null)
            preferences = CaipiaoUtil.getCpSharedPreferences(mContext);
        if (serverCaipiaoList != null)
            serverCaipiaoList.clear();
        else
            serverCaipiaoList = new ArrayList<Caipiao>();
        // 解析json数据
        JSONArray data = JSON.parseObject(result).getJSONArray("list");

        for (int i = 0; i < data.size(); i++) {
            JSONObject obj = data.getJSONObject(i);
            cp = getCaipiaoById(obj.getIntValue("lotteryId"));
            if (cp != null) {
                cp.setMessage(obj.getString("message"));
                cp.setJiangChiStr(obj.getString("message"));
                // 网络请求数据有延时，故减去5秒
                cp.setRemainTime(obj.getIntValue("remainTime") - 7000);
                cp.setIsstop(obj.getIntValue("isStop"));
                cp.setIssue(obj.getString("issue"));
                cp.setIssueId(obj.getIntValue("issueId"));
                cp.setEvent(obj.getString("event"));
                cp.setClickCount(preferences.getInt(cp.getName() + "_bak", 0));
                cp.setLotteryPic(obj.getString("lotteryPic"));
                cp.setH5Url(obj.getString("h5Url"));
                cp.setLinkH5(obj.getBooleanValue("linkH5"));
                serverCaipiaoList.add(cp);
                if (cp.getId() == CaipiaoConst.ID_PAILIE3) {
                    Caipiao cp5 = getCaipiaoById(CaipiaoConst.ID_PAILIE5);
                    cp5.setMessage(cp.getMessage());
                    cp5.setRemainTime(cp.getRemainTime());
                    cp5.setIsstop(cp.getIsstop());
                    cp5.setIssue(cp.getIssue());
                    cp5.setIssueId(cp.getIssueId());
                    cp5.setClickCount(preferences.getInt(
                            cp5.getName() + "_bak", 0));
                    serverCaipiaoList.add(cp5);
                }
            } else {
                // 跳到html5的
                cp = new Caipiao(-1);
                cp.setName(obj.getString("lotteryName"));
                cp.setId(obj.getIntValue("lotteryId"));
                cp.setMessage(obj.getString("message"));
                // 网络请求数据有延时，故减去5秒
                cp.setRemainTime(obj.getIntValue("remainTime") - 7000);
                cp.setIssue(obj.getString("issue"));
                cp.setIssueId(obj.getIntValue("issueId"));
                cp.setClickCount(preferences.getInt(cp.getName() + "_bak", 0));
                cp.setLotteryPic(obj.getString("lotteryPic"));
                cp.setH5Url(obj.getString("h5Url"));
                cp.setLinkH5(obj.getBooleanValue("linkH5"));

                serverCaipiaoList.add(cp);
            }
        }
        // 彩种list排序
        //	sortServerList(isRefresh);
        if (!isRefresh) {
            JSONArray json = JSON.parseObject(result).getJSONArray("urlAddress");
            for (int i = 0; i < json.size(); i++) {
                JSONObject obj = json.getJSONObject(i);
                String s = obj.getString("pictureUrl");
                String[] str = s.split("\\|");
                pictureURL.add(str[0]);
                pictureLink.add(str[1]);
//			setPictureURL(str[0]);
//			setPictureLink(str[1]);
            }
        }

        discoveryUrl = JSON.parseObject(result).getString("found");

    }

}
