package com.chengyi.app.home.get;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.jingji.basket.BasketBall;
import com.chengyi.app.jingji.bzjy.BzjyActivity;
import com.chengyi.app.jingji.football.FootBall;
import com.chengyi.app.jingji.guanyajun.GuanYaJun;
import com.chengyi.app.jingji.renxuan.Activity_RenXuanQiuChang;
import com.chengyi.app.model.bean.HadLotteryBean;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.num.lottery.Activity_CaipiaoTouZhu;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.Date_util;

import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class Activity_DrawNumberDetail extends BaseActivity {
    private TextView CaipaioName, issue, drawTime, zhongjiangshu, jiangxiang, jiangji, saleTotal, remainTotal;
    private TextView jiang, jiangnum, jiangmoney, goumaiText;
    private LinearLayout qiulayout, jiangchilayout, saleTotallayout, remainTotallayout, bottomlayoutbuy;

    private HadLotteryBean data;

   
    private Caipiao cp;
    private int lotteryId;
    private String issueStr;
    private RelativeLayout failedLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        issueStr = this.getIntent().getStringExtra("issue");
        lotteryId = this.getIntent().getIntExtra("loteryid", -1);
        if (lotteryId != 10065 && lotteryId != 10066&& lotteryId != 10073&& lotteryId != 10074)
            cp = CaipiaoFactory.getInstance(this).getCaipiaoById(lotteryId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_detail_drawnumber);
        initView();
        if (this.getIntent().getBooleanExtra("isfromxiangqing", false)) {
            loadData();
        } else {
            data = CaipiaoApplication.getInstance().getData();
            fillView();
        }
    }

    private void loadData() {
        setBack();
        findViewById(R.id.loaddata).setVisibility(View.VISIBLE);
        findViewById(R.id.failed).setVisibility(View.GONE);
        findViewById(R.id.mainlayout).setVisibility(View.GONE);
        RequestParams params = getRequestParams();
        params.put("lotteryId", lotteryId + "");
        params.put("issueOrder", issueStr + "");

        HttpBusinessAPI.post(URLSuffix.ISSUE_NOTIFY, params, ResponseHandler);
    }

    HttpRespHandler ResponseHandler = new HttpRespHandler() {

        @Override
        public void onSuccess(String response) {
            super.onSuccess(response);
            if (JSON.parseArray(response).size() > 0) {
                findViewById(R.id.loaddata).setVisibility(View.GONE);
                findViewById(R.id.mainlayout).setVisibility(View.VISIBLE);
                data = HadLotteryBean.createHistoryData(JSON.parseArray(response).getJSONObject(0), lotteryId);
                fillView();
            } else {
                findViewById(R.id.loaddata).setVisibility(View.GONE);
                findViewById(R.id.failed).setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onFailure(Throwable error) {
            super.onFailure(error);
            findViewById(R.id.loaddata).setVisibility(View.GONE);
            findViewById(R.id.failed).setVisibility(View.VISIBLE);
        }

    };

    private void initView() {
 
        CaipaioName = (TextView) findViewById(R.id.name);
        issue = (TextView) findViewById(R.id.issue);
        drawTime = (TextView) findViewById(R.id.date);
        zhongjiangshu = (TextView) findViewById(R.id.zhongjiangshu);
        qiulayout = (LinearLayout) findViewById(R.id.qiulayout);
        jiangchilayout = (LinearLayout) findViewById(R.id.jiangchilayout);
        saleTotallayout = (LinearLayout) findViewById(R.id.saleTotallayout);
        remainTotallayout = (LinearLayout) findViewById(R.id.remainTotallayout);
        bottomlayoutbuy = (LinearLayout) findViewById(R.id.bottomlayoutbuy);
        bottomlayoutbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaipiaoApplication.getInstance().setCurrentCaipiao(cp);
                if (CaipiaoApplication.getInstance().getCurrentCaipiao().getId() == CaipiaoConst.ID_JINGCAIZUQIU) {

                    startActivity(new Intent(getBaseContext(), FootBall.class));
                } else if (CaipiaoApplication.getInstance().getCurrentCaipiao().getId() == CaipiaoConst.ID_BASKETBALL) {
                    startActivity(new Intent(getBaseContext(), BasketBall.class));
                } else if (CaipiaoApplication.getInstance().getCurrentCaipiao().getId() == CaipiaoConst.ID_SHENGFU14CHANG) {
                    startActivity(new Intent(getBaseContext(), Activity_RenXuanQiuChang.class));
                } else if (CaipiaoApplication.getInstance().getCurrentCaipiao().getId() == CaipiaoConst.ID_GUANYAJUN) {
                    startActivity(new Intent(getBaseContext(), GuanYaJun.class));
                } else if (CaipiaoApplication.getInstance().getCurrentCaipiao().getId() == CaipiaoConst.ID_BALL) {
                    startActivity(new Intent(getBaseContext(), BzjyActivity.class));
                } else {

                    startActivity(new Intent(getBaseContext(), Activity_CaipiaoTouZhu.class));
                }

            }
        });
        jiangxiang = (TextView) findViewById(R.id.jiangxiang);
        jiangji = (TextView) findViewById(R.id.jiangji);
        saleTotal = (TextView) findViewById(R.id.saleTotal);
        remainTotal = (TextView) findViewById(R.id.remainTotal);
        goumaiText = (TextView) findViewById(R.id.goumai);
        failedLayout = (RelativeLayout) findViewById(R.id.failed);
        failedLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                loadData();
            }
        });
    }

    private void fillView() {
        setBack();
       
        if (lotteryId == 10073) {
         setCusTomeTitle("安徽快三-开奖详情");
            CaipaioName.setText("安徽快三");
            bottomlayoutbuy.setVisibility(View.GONE);
        }
        if (lotteryId == 10074) {
            setCusTomeTitle("广西快三-开奖详情");
            CaipaioName.setText("广西快三");
            bottomlayoutbuy.setVisibility(View.GONE);
        }
        if (lotteryId == 10065) {
            setCusTomeTitle("新快三-开奖详情");
            CaipaioName.setText("新快三");
            bottomlayoutbuy.setVisibility(View.GONE);
        } else if (lotteryId == 10066) {
            setCusTomeTitle("11选5-开奖详情");
            CaipaioName.setText("11选5");
            bottomlayoutbuy.setVisibility(View.GONE);
        } else {
//			if(lotteryId==10024)
//				setCusTomeTitle("排列3/5-开奖详情");
//			else
            setCusTomeTitle(cp.getName() + "-开奖详情");
            CaipaioName.setText(cp.getName());
            goumaiText.setText("购买" + cp.getName());
        }
        issue.setText("第" + data.getIssue() + "期");
        drawTime.setText(Date_util.getDateString(data.getDrawTime()));
        ///快三要特殊处理下
        if (CaipiaoUtil.isKySj(data.getLotteryId())) {
            qiulayout.removeAllViews();
            LayoutParams LP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            LP.gravity = Gravity.CENTER;
            LP.leftMargin = 2;
            String[] haoma = data.getDrawNumber().split(",");
            int sum = 0;
            for (int i = 0; i < haoma.length; i++) {
                qiulayout.addView(getImg(haoma[i]), LP);
                sum += Integer.parseInt(haoma[i]);
            }
            LP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            LP.leftMargin = 15;
            qiulayout.addView(getBtn(sum + "", -1), LP);
        } else
            addHaoma(data.getDrawNumber());
        if (lotteryId == 10065 || lotteryId == 10066  || CaipiaoUtil.isKySj(lotteryId)|| (cp != null && cp.isKuaikai())) {
            saleTotallayout.setVisibility(View.GONE);
            remainTotallayout.setVisibility(View.GONE);
            zhongjiangshu.setVisibility(View.GONE);
            jiangji.setText("每注奖金");
        } else {
            saleTotal.setText(data.getSaleTotal() + "元");
            if (TextUtils.isEmpty(data.getRemainTotal()) || data.getRemainTotal().equals("0"))
                remainTotal.setText("0元");
            else
                remainTotal.setText(data.getRemainTotal() + "元");
        }
        List<HadLotteryBean.PrizeData> prizeData = data.getItems();
        for (int i = 0; i < data.getItems().size(); i++) {
            View convertView = getLayoutInflater().inflate(R.layout.new_jiangxiang_view, null);
            jiang = (TextView) convertView.findViewById(R.id.jiang);
            jiangnum = (TextView) convertView.findViewById(R.id.jiangnum);
            jiangmoney = (TextView) convertView.findViewById(R.id.jiangmoney);
            jiang.setText(prizeData.get(i).getPrizeItem());
            if (lotteryId == 10065 || lotteryId == 10066|| CaipiaoUtil.isKySj(lotteryId)|| (cp != null && cp.isKuaikai())) {
                convertView.findViewById(R.id.numlayout).setVisibility(View.GONE);
            } else {
                jiangnum.setText(prizeData.get(i).getNumber() + "");
            }
            jiangmoney.setText(prizeData.get(i).getPrizeAmount() + "");
            jiangchilayout.addView(convertView);
        }
    }

    ///一般数字彩号码布局所用
    private void addHaoma(String number) {
        qiulayout.removeAllViews();
        int index = number.indexOf("#");
        String[] haoma;
        LayoutParams LP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LP.gravity = Gravity.CENTER;
        LP.leftMargin = 5;
        //有后驱情况
        if (index != -1) {
            haoma = number.substring(0, index).split(",");
            for (int i = 0; i < haoma.length; i++) {
                qiulayout.addView(getBtn(haoma[i], R.drawable.redqiu_2), LP);
            }
            haoma = number.substring(index + 1).split(",");
            LP.leftMargin = 10;
            for (int i = 0; i < haoma.length; i++) {
                qiulayout.addView(getBtn(haoma[i], R.drawable.blueqiu), LP);
            }
        } else {
            haoma = number.split(",");
            for (int i = 0; i < haoma.length; i++) {
                if (haoma.length==1){
                    qiulayout.addView(getBtn(haoma[i], R.drawable.redqiu), LP);
                }else{
                qiulayout.addView(getBtn(haoma[i], R.drawable.redqiu), LP);}
            }
        }
    }

    private TextView getBtn(String s, int drawable) {
        TextView btn = new TextView(this);
        btn.setGravity(Gravity.CENTER);
        btn.setTextColor(Color.WHITE);
        if (drawable != -1) {
            btn.setText(s);
            btn.setTextAppearance(this, R.style.text_style);
//            btn.setBackgroundColor();
            btn.setTextColor(Color.BLACK);
        } else {
            btn.setText("和值:" + s);
            btn.setTextColor(Color.BLACK);
            btn.setTextSize(15);
        }
        return btn;
    }

    private ImageView getImg(String s) {
        ImageView img = new ImageView(this);
        switch (Integer.parseInt(s)) {
            case 1:
                img.setBackgroundResource(R.drawable.dice_v1_small);
                break;
            case 2:
                img.setBackgroundResource(R.drawable.dice_v2_small);
                break;
            case 3:
                img.setBackgroundResource(R.drawable.dice_v3_small);
                break;
            case 4:
                img.setBackgroundResource(R.drawable.dice_v4_small);
                break;
            case 5:
                img.setBackgroundResource(R.drawable.dice_v5_small);
                break;
            case 6:
                img.setBackgroundResource(R.drawable.dice_v6_small);
                break;
        }
        return img;
    }
}
