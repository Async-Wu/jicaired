
package com.chengyi.app.jingji.basket;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.net.control.Control;
import com.chengyi.app.util.*;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BasketBall extends BasketballBaseActivity implements
        View.OnClickListener {
    private ImageView ivAnim;

    View dan, guoguan;
    ImageView rightbtnmenu;
    private TextView

            noGameTip;

    boolean isFromTouZhuQueRen = false;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery_basketball);
        findViewById(R.id.footballffootballtopbarLayout).setOnClickListener(this);
        isFromTouZhuQueRen = this.getIntent().getBooleanExtra(
                "isfromtouzhuqueren", false);
        // /如果从投注确认页面跳转过来
        ivAnim = (ImageView) findViewById(R.id.iv_anim);
        initView();
        wanfaIndex = Control.getInstance().getBasketballManager().getCurrentWanfa();
        if (wanfaIndex == BasketballManager.sf) {
            showFragment(FragId.basketball_sf, R.id.basketball_mainlayout);
            Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.sf + BasketballManager.guoguan);
        } else if (wanfaIndex == BasketballManager.rfsf) {
            showFragment(FragId.basketball_rfsf, R.id.basketball_mainlayout);
            Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.rfsf + BasketballManager.guoguan);
        } else if (wanfaIndex == BasketballManager.sfc) {
            Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.sfc + BasketballManager.guoguan);
            showFragment(FragId.basketball_sfc, R.id.basketball_mainlayout);
        } else if (wanfaIndex == BasketballManager.bigsmall) {
            Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.bigsmall + BasketballManager.guoguan);
            showFragment(FragId.basketball_bigsmall, R.id.basketball_mainlayout);
        } else if (wanfaIndex == BasketballManager.mix) {
            Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.mix + BasketballManager.guoguan);
            showFragment(FragId.basketball_mix, R.id.basketball_mainlayout);
        }
    }

    private void initView() {



        setCusTomeTitle(getCurrentCaipiao().getName()
                + "-" + Control.getInstance().getBasketballManager().getCurrentWanfaGuanName());
        setBack();
        rightbtnmenu = (ImageView) findViewById(R.id.iv_wanfa);
        rightbtnmenu.setOnClickListener(this);
        findViewById(R.id.iv_select).setVisibility(View.GONE);

        noGameTip = (TextView) findViewById(R.id.bascketball_layout_no_game);

    }

    @Override
    public void onClick(View v) {


        map.clear();
        switch (v.getId()) {
            case R.id.wanfa:
                Control.getInstance().getBasketballManager().setDanorguo(0);
                dan.setVisibility(View.GONE);
                popMixBtn.setVisibility(View.VISIBLE);
                guoguan.setVisibility(View.VISIBLE);
                ((TextView) vPopupWindow.findViewById(R.id.wanfa)).setTextColor(getResources().getColor(R.color.red));
                ((TextView) vPopupWindow.findViewById(R.id.football_pop_news_btn)).setTextColor(getResources().getColor(R.color.football_grey));


                Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.sf + BasketballManager.guoguan);
                wanfaIndex = (Integer) popSfBtn.getTag(R.id.tag);
                popSfBtn.setSelected(false);
                popRfsfBtn.setSelected(false);
                popSfcBtn.setSelected(false);
                popBigSmallBtn.setSelected(false);
                popMixBtn.setSelected(false);
                popSfBtn.setSelected(true);

                setCusTomeTitle(getCurrentCaipiao().getName()    + "-" + "胜负");
                Control.getInstance().getBasketballManager().setCurrentWanfa(0);
                showFragment(FragId.basketball_sf, R.id.basketball_mainlayout);
                noGameTip.setVisibility(View.GONE);


                break;
            case R.id.football_pop_news_btn:
                Control.getInstance().getBasketballManager().setDanorguo(1);
                popMixBtn.setVisibility(View.GONE);
                guoguan.setVisibility(View.GONE);
                dan.setVisibility(View.VISIBLE);
                ((TextView) vPopupWindow.findViewById(R.id.football_pop_news_btn)).setTextColor(getResources().getColor(R.color.red));
                ((TextView) vPopupWindow.findViewById(R.id.wanfa)).setTextColor(getResources().getColor(R.color.football_grey));

                Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.sf + BasketballManager.guoguan);
                wanfaIndex = (Integer) popSfBtn.getTag(R.id.tag);
                popSfBtn.setSelected(false);
                popRfsfBtn.setSelected(false);
                popSfcBtn.setSelected(false);
                popBigSmallBtn.setSelected(false);
                popMixBtn.setSelected(false);
                popSfBtn.setSelected(true);

                setCusTomeTitle(getCurrentCaipiao().getName()    + "-" + "胜负");
                Control.getInstance().getBasketballManager().setCurrentWanfa(0);
                showFragment(FragId.basketball_sf, R.id.basketball_mainlayout);
                noGameTip.setVisibility(View.GONE);



                break;
            case R.id.iv_wanfa:

                Toast.makeText(this, "暂无玩法介绍", Toast.LENGTH_SHORT).show();

                break;

            case R.id.wanfabtn: // /玩法
                moveToWanFa();


                break;
            case  R.id.footballffootballtopbarLayout:
            case R.id.tv_title:
                if (wanfaChangePop != null && wanfaChangePop.isShowing()) {
                    dismiss();
                } else {
                    AppUtil.startRotateAnim(ivAnim);
                    getSelectPop().showAsDropDown(findViewById(R.id.footballffootballtopbarLayout));
                }
                break;
            case R.id.pop_basket_buttonsf: // 胜负
                L.d("shengfu", "shengfushengfu");
                Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.sf + BasketballManager.guoguan);
                wanfaIndex = (Integer) v.getTag(R.id.tag);
                v.setSelected(true);
                dismiss();
                setCusTomeTitle(getCurrentCaipiao().getName()
                        + "-" + "胜负");
                Control.getInstance().getBasketballManager().setCurrentWanfa(0);
                showFragment(FragId.basketball_sf, R.id.basketball_mainlayout);
                noGameTip.setVisibility(View.GONE);


                break;
            case R.id.pop_basket_buttonrfsf: // 让分胜负
                Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.rfsf + BasketballManager.guoguan);
                wanfaIndex = (Integer) v.getTag(R.id.tag);
                v.setSelected(true);
                dismiss();
                setCusTomeTitle(getCurrentCaipiao().getName()
                        + "-" + "让分胜负");
                Control.getInstance().getBasketballManager().setCurrentWanfa(1);
                showFragment(FragId.basketball_rfsf, R.id.basketball_mainlayout);
                noGameTip.setVisibility(View.GONE);
                map.put("玩法类型", "让分胜负");
                CaipiaoUtil.youmengEvent(this,
                        YOUMENG_EVENT.new_basketball, map);
                break;

            case R.id.pop_basket_btnshengfencha: // 胜分差
                Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.sfc + BasketballManager.guoguan);
                wanfaIndex = (Integer) v.getTag(R.id.tag);
                v.setSelected(true);
                dismiss();
                setCusTomeTitle(getCurrentCaipiao().getName()
                        + "-" + "胜分差");
                Control.getInstance().getBasketballManager().setCurrentWanfa(2);
                try {
                    showFragment(FragId.basketball_sfc, R.id.basketball_mainlayout);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                noGameTip.setVisibility(View.GONE);
                map.put("玩法类型", "让分胜负");
                CaipiaoUtil.youmengEvent(this,
                        YOUMENG_EVENT.new_basketball, map);
                break;
            case R.id.pop_basket_btnmix:// 混合投注
                Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.mix + BasketballManager.guoguan);
                wanfaIndex = (Integer) v.getTag(R.id.tag);
                v.setSelected(true);
                dismiss();
                setCusTomeTitle(getCurrentCaipiao().getName()
                        + "-" + "混合投注");
                Control.getInstance().getBasketballManager().setCurrentWanfa(4);
                showFragment(FragId.basketball_mix, R.id.basketball_mainlayout);
                noGameTip.setVisibility(View.GONE);
                map.put("玩法类型", "混合投注");
                CaipiaoUtil.youmengEvent(BasketBall.this,
                        YOUMENG_EVENT.new_basketball, map);
                break;
            case R.id.pop_basket_btnbigsmall://大小
                Control.getInstance().getBasketballManager().requestLotteryData(BasketballManager.bigsmall + BasketballManager.guoguan);
                wanfaIndex = (Integer) v.getTag(R.id.tag);
                v.setSelected(true);
                dismiss();
                setCusTomeTitle(getCurrentCaipiao().getName()
                        + "-" + "大小分");
                Control.getInstance().getBasketballManager().setCurrentWanfa(3);
                showFragment(FragId.basketball_bigsmall, R.id.basketball_mainlayout);
                noGameTip.setVisibility(View.GONE);
                map.put("玩法类型", "大小分");
                CaipiaoUtil.youmengEvent(BasketBall.this,
                        YOUMENG_EVENT.new_basketball, map);
                break;


        }
    }


    public void showNoGameTip() {
        noGameTip.setVisibility(View.VISIBLE);
    }

    // ================================================请求网络数据 end

    // ++++++++++++++++++++++++++++++++++++++++++++++++顶部彩种切换 start
    View vPopupWindow;
    PopupWindow wanfaChangePop; // 区域选择性弹窗


    TextView   popSfBtn, popRfsfBtn, popSfcBtn, popBigSmallBtn,
            popMixBtn;
    public int wanfaIndex = 0;

    private PopupWindow getSelectPop() {
        if (vPopupWindow == null) {

            vPopupWindow = getLayoutInflater()
                    .inflate(R.layout.pop_basketball,
                            null, false);
            wanfaChangePop = new PopupWindow(vPopupWindow,
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            wanfaChangePop.setFocusable(true);
            wanfaChangePop.setOutsideTouchable(true);

            vPopupWindow.findViewById(R.id.wanfa).setOnClickListener(this);
            vPopupWindow.findViewById(R.id.football_pop_news_btn).setOnClickListener(this);
            dan = vPopupWindow.findViewById(R.id.danguan_bootm);
            guoguan = vPopupWindow.findViewById(R.id.guoguan_bootm);
            dan.setVisibility(View.GONE);
            guoguan.setVisibility(View.VISIBLE);

            popSfBtn = (TextView) vPopupWindow
                    .findViewById(R.id.pop_basket_buttonsf);
            popSfBtn.setOnClickListener(this);
            popSfBtn.setTag(R.id.tag, 0);

            popRfsfBtn = (TextView) vPopupWindow
                    .findViewById(R.id.pop_basket_buttonrfsf);
            popRfsfBtn.setOnClickListener(this);
            popRfsfBtn.setTag(R.id.tag, 1);

            popSfcBtn = (TextView) vPopupWindow
                    .findViewById(R.id.pop_basket_btnshengfencha);
            popSfcBtn.setOnClickListener(this);
            popSfcBtn.setTag(R.id.tag, 2);

            popBigSmallBtn = (TextView) vPopupWindow
                    .findViewById(R.id.pop_basket_btnbigsmall);
            popBigSmallBtn.setOnClickListener(this);
            popBigSmallBtn.setTag(R.id.tag, 3);

            popMixBtn = (TextView) vPopupWindow
                    .findViewById(R.id.pop_basket_btnmix);
            popMixBtn.setOnClickListener(this);
            popMixBtn.setTag(R.id.tag, 4);


            vPopupWindow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (wanfaChangePop != null && wanfaChangePop.isShowing()) {
                        dismiss();
                    }
                }
            });
        }

        wanfaChangePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
          AppUtil.startResRotateAnim(ivAnim);
            }
        });



        popSfBtn.setSelected(false);
        popRfsfBtn.setSelected(false);
        popSfcBtn.setSelected(false);
        popBigSmallBtn.setSelected(false);
        popMixBtn.setSelected(false);

        if (wanfaIndex == 0) {
            popSfBtn.setSelected(true);

        } else if (wanfaIndex == 1) {
            popRfsfBtn.setSelected(true);

        } else if (wanfaIndex == 2) {
            popSfcBtn.setSelected(true);

        } else if (wanfaIndex == 3) {
            popBigSmallBtn.setSelected(true);

        } else if (wanfaIndex == 4) {
            popMixBtn.setSelected(true);

        }
        return wanfaChangePop;
    }

    private void dismiss() {
        AppUtil.startResRotateAnim(ivAnim);
        wanfaChangePop.dismiss();

    }
}