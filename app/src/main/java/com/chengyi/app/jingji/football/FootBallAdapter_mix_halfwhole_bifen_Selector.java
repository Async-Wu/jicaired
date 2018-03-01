package com.chengyi.app.jingji.football;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.model.GameMode;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class FootBallAdapter_mix_halfwhole_bifen_Selector extends BaseActivity {
    private boolean isclick = true;
private  View v1,v2;
    private Button cancelBtn, ensureBtn;
    private TextView zhuDuiText, keDuiText, titleOne, titleTwo, titleThree;
    private GameMode gameData;
    private int wfindex;
    private LinearLayout layoutFirst, layoutSecond, layoutThree;
    LayoutParams layoutParams;
    LayoutInflater mInflater;
    private int count = 0;//按钮的个数
    private String[] banQuanChangStr = CaipiaoConst.FBallBQCSTR.split(",");
    ArrayList<String> peilvList;
    ArrayList<Integer> btnSelectedList;
    private String selectedStr;    //半全场，比分投注选择的拼接成的串
    private String selContentStr;    //半全场，比分投注具体选择的投注按钮文字拼接成的串
    private String location;//比赛数据所在的位置。

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_lottery_football_cart_zone);
        gameData = (GameMode) getIntent().getSerializableExtra("gameData");

        wfindex = getIntent().getIntExtra("wfindex", 4);
        location = getIntent().getStringExtra("location");
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        peilvList = gameData.getBqcList();
        btnSelectedList = new ArrayList<>();
        isclick = getIntent().getBooleanExtra("isclick", true);
        if (!TextUtils.isEmpty(gameData.getSelectedStr())) {
            String[] selectedStr = gameData.getSelectedStr().split(",");
            for (String s : selectedStr) {
                btnSelectedList.add(Integer.parseInt(s));
            }
        }
        if (wfindex != 4)
            banQuanChangStr = gameData.getBiFenStr();
        initView();
        fillView();

    }

    private void initView() {
        v1=findViewById(R.id.v1);
        v2=findViewById(R.id.v2);
        cancelBtn = (Button) findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(this);
        ensureBtn = (Button) findViewById(R.id.yesbtn);
        ensureBtn.setOnClickListener(this);
        zhuDuiText = (TextView) findViewById(R.id.zhudui);
        keDuiText = (TextView) findViewById(R.id.kedui);
        titleOne = (TextView) findViewById(R.id.title1);
        titleTwo = (TextView) findViewById(R.id.title2);
        titleThree = (TextView) findViewById(R.id.title3);
        layoutFirst = (LinearLayout) findViewById(R.id.layout1);
        layoutSecond = (LinearLayout) findViewById(R.id.layout2);
        layoutThree = (LinearLayout) findViewById(R.id.layout3);
    }

    private void fillView() {
        zhuDuiText.setText(gameData.getTeam1());
        keDuiText.setText(gameData.getTeam2());
        if (wfindex == 4) {
            titleOne.setVisibility(View.GONE);
//            titleTwo.setVisibility(View.GONE);
            titleThree.setVisibility(View.GONE);
//            layoutSecond.setVisibility(View.GONE);
            layoutFirst.setVisibility(View.GONE);
            layoutThree.setVisibility(View.GONE);

            findViewById(R.id.ll1).setVisibility(View.GONE);
            findViewById(R.id.ll3).setVisibility(View.GONE);
            v1.setVisibility(View.GONE);
            v2.setVisibility(View.GONE);
            titleTwo.setText("半\n全\n场");
            addView(layoutSecond, 3, 3);
        } else {
            findViewById(R.id.ll1).setVisibility(View.VISIBLE);
            findViewById(R.id.ll3).setVisibility(View.VISIBLE);
            v1.setVisibility(View.VISIBLE);
            v2.setVisibility(View.VISIBLE);

            addView(layoutFirst, 7, 2);
            addView(layoutSecond, 5, 1);
            addView(layoutThree, 7, 2);
        }
    }

    /**
     * @param layout 给layout容器动态添加view
     * @param num    每行要添加的个数
     * @param group  列数
     */
    private void addView(LinearLayout layout, int num, int group) {
        layout.removeAllViews();
        for (int i = 0; i < group; i++) {
            LinearLayout tempLayout = new LinearLayout(this);

            if (group == 2 && i == 1) {
                num = num - 1;
            }

            for (int t = 0; t < num; t++) {

                LayoutParams temp;


                if (group == 2 && i == 1) {
                    if (t < num - 1) {
                        temp = new LayoutParams(
                                0, LayoutParams.WRAP_CONTENT, 1);
                    } else {
                        temp = new LayoutParams(
                                0, LayoutParams.WRAP_CONTENT, 2);
                    }


                } else {

                    temp = new LayoutParams(
                            0, LayoutParams.WRAP_CONTENT, 1);

                    if (num == 5 && group == 1 && t == num - 1) {
                        temp = new LayoutParams(
                                0, LayoutParams.WRAP_CONTENT, 3);
                    }
                }


                final View tempView = mInflater.inflate(R.layout.new_jingcai_bifen_itemview, null, false);
                if (0 != i) {
                    if (0 != t) {
//			    		tempView.setBackgroundResource(R.drawable.new_jingcai_bifen_btn_four);
                    } else {
//			    		tempView.setBackgroundResource(R.drawable.new_jingcai_bifen_btn_three);
                    }
                } else {
                    if (0 != t) {
//			    		tempView.setBackgroundResource(R.drawable.new_jingcai_bifen_btn_two);
                    } else {
//			    		tempView.setBackgroundResource(R.drawable.new_jingcai_bifen_btn_one);
                    }
                }
                TextView title = (TextView) tempView.findViewById(R.id.numtext);
                TextView sp = (TextView) tempView.findViewById(R.id.peilv);
                if (wfindex == 4) {
                    try {
                        title.setText(banQuanChangStr[count]);
                        sp.setText(peilvList.get(count));
                        tempView.setTag(count);
                        if (btnSelectedList.contains(count))
                            tempView.setSelected(true);
                        count++;
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                } else {
                    if (i == 2 && t > 2) {
                        tempView.setVisibility(View.INVISIBLE);
                    } else {
                        try {
                            if (peilvList.size() > 9) {
                                title.setText(banQuanChangStr[count]);
                                sp.setText(peilvList.get(count + 9));
                                tempView.setTag(count);
                                if (btnSelectedList.contains(count))
                                    tempView.setSelected(true);
                                count++;
                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                    }
                }
                tempView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (isclick) {

                            tempView.setSelected(!tempView.isSelected());
                            if (tempView.isSelected())
                                btnSelectedList.add(Integer.parseInt(tempView.getTag() + ""));
                            else {
                                int index = btnSelectedList.indexOf(Integer.parseInt(tempView.getTag() + ""));
                                if (index != -1)
                                    btnSelectedList.remove(index);
                            }
                        }
//}
                    }
                });
                tempLayout.addView(tempView, temp);

            }
            layout.addView(tempLayout, layoutParams);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.cancel:
                finish();
                break;
            case R.id.yesbtn:
                Intent in = new Intent();
                in.putExtra("location", location);
                getSelectedStr();
                in.putExtra("selectedStr", selectedStr);
                in.putExtra("selContentStr", selContentStr);
                L.d(location+":"+":"+selectedStr);
                setResult(RESULT_OK, in);
                finish();
                break;
        }
    }

    private void getSelectedStr() {
        selectedStr = "";
        selContentStr = "";
        //排个序
        Collections.sort(btnSelectedList, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {

                return lhs - rhs;
            }
        });
        for (Integer s : btnSelectedList) {
            selectedStr += "," + s;
            selContentStr += "," + banQuanChangStr[s];
        }
        if (selectedStr.length() > 1) {
            selectedStr = selectedStr.substring(1);
            selContentStr = selContentStr.substring(1);
        }

    }
}
