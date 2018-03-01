package com.chengyi.app.model.wanfa;

import android.view.View;
import android.widget.Button;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.L;
import com.chengyi.app.view.widget.TouzhuButton;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class KuaiSanAbsWanFa extends AbsWanfa {

    private int getSecondRandom(int first) {
        int second = 0;
        second = CaipiaoUtil.getRandomArray(
                caipiao.getkSanLastBtnList().size(), 1)[0];
        if (second == first)
            return getSecondRandom(first);
        else
            return second;
    }

    private String getRandomStr(int[] randomArray) {
        String s = "";
        switch (getType()) {
            case CaipiaoConst.WF_NOSAME_THREE:
            case CaipiaoConst.WF_NOSAME_TWO:
            case CaipiaoConst.WF_RENYI:
                for (int i = 0; i < randomArray.length; i++) {
                    s = s + (randomArray[i] + 1) + " ";
                }
                break;
            case CaipiaoConst.WF_HEZHI:
                for (int i = 0; i < randomArray.length; i++) {
                    s = s + (randomArray[i] + 3) + " ";
                }
                break;
            case CaipiaoConst.WF_SHUNZI:
                s = "123,234,345,456 ";
                break;
            case CaipiaoConst.WF_DUIZI:
                for (int i = 0; i < randomArray.length; i++) {
                    s = s + (randomArray[i] + 1) + "" + (randomArray[i] + 1) + "*"
                            + " ";
                }
                break;
            case CaipiaoConst.WF_BAOZI:
                for (int i = 0; i < randomArray.length; i++) {
                    s = s + (randomArray[i] + 1) + "" + (randomArray[i] + 1) + ""
                            + (randomArray[i] + 1) + " ";
                }
                break;
        }
        if (s.length() > 1 && s.indexOf(" ") != -1)
            return s.substring(0, s.length() - 1);
        else
            return s;
    }

    @Override
    public TouzhuquerenData randomTouzhu() {

        TouzhuquerenData data = getDefaultTouzhu();
        int n = 0, m = 0;
        String s = "";
        m = caipiao.getBtnList().size();
        switch (getType()) {
            case CaipiaoConst.WF_NOSAME_THREE:
            case CaipiaoConst.WF_RENYI:
                n = 3;
                break;
            case CaipiaoConst.WF_NOSAME_TWO:
                n = 2;
                break;
            case CaipiaoConst.WF_HEZHI:
            case CaipiaoConst.WF_SHUNZI:
                n = 1;
                break;
            case CaipiaoConst.WF_BAOZI:
                data.setName("三同号单选");
                n = 1;
                m = m + 1;
                break;
            case CaipiaoConst.WF_DUIZI:
                data.setName("二同号复选");
                n = 1;
                m = 2 * m;
                break;
        }
        int[] randomArray = CaipiaoUtil.getRandomArray(m, n);
        if (getType() == CaipiaoConst.WF_BAOZI && randomArray[0] > 5) {
            s = "111,222,333,444,555,666";
            data.setName("三同号通选");
        } else if (getType() == CaipiaoConst.WF_DUIZI && randomArray[0] > 5) {
            int first = CaipiaoUtil.getRandomArray(caipiao.getkSanFiveBtnList()
                    .size(), 1)[0];
            s = s + (first + 1) + "" + (first + 1) + " ";
            int second = getSecondRandom(first);
            s = s.substring(0, s.length() - 1) + "-";
            s = s + (second + 1) + " ";
            data.setName("二同号单选");
        } else {
            if (randomArray.length > 1)
                Arrays.sort(randomArray);  //进行排序
            s = getRandomStr(randomArray);
        }
        data.setHasHouqu(false);
        data.setTouzhuhaoma(s);
        // data.setListTouzuList2(bList);
        return data;
    }

    @Override
    public void check(TouzhuButton btn) {

    }

    @Override
    public int getTouzhuCount() {

        if (getType() <= 37) {
            int selected = getSelectCount(caipiao.getBtnList());
            switch (getType()) {
                case CaipiaoConst.WF_NOSAME_THREE:
                    return CaipiaoUtil.getCombination(selected, 3);
                case CaipiaoConst.WF_NOSAME_TWO:
                    return CaipiaoUtil.getCombination(selected, 2);
                case CaipiaoConst.WF_RENYI:
                    if (selected == 1)
                        return 21;
                    else if (selected == 2)
                        return 6;
                    else if (selected == 3)
                        return 1;
            }
        } else if (getType() == CaipiaoConst.WF_SHUNZI
                || getType() == CaipiaoConst.WF_HEZHI) {
            return getSelectCount(caipiao.getBtnList());
        } else if (getType() == CaipiaoConst.WF_BAOZI) {
            return getSelectCount(caipiao.getBtnList())
                    + getSelectBtnCount(caipiao.getkSanLastBtnList());
        } else {
            return getSelectCount(caipiao.getBtnList())
                    + getSelectBtnCount(caipiao.getkSanLastBtnList())
                    * getSelectBtnCount(caipiao.getkSanFiveBtnList());
        }
        return 0;
    }

    @Override
    public String getSubmitString() {

        return null;
    }

    // 每次点击按钮都检查下
    public void checkView(View btn) {

        if (btn.isSelected()) {
            btn.setSelected(false);

            if (getType() == CaipiaoConst.WF_HEZHI) {
                if (btn.getTag() != null) {
                    for (int i = 0; i < caipiao.getBtnList().size(); i++) {
                        caipiao.getBtnList().get(i).setSelected(false);

                    }
                    preChangeBtnList();
                } else
                    changeHouQuBtnState();
            }
            if (getTouzhuListener() != null) {
                getTouzhuListener().onTouzhuCountChange();
            }


            return;
        }
        if (getType() == CaipiaoConst.WF_RENYI && getSelectCount(caipiao.getBtnList()) >= 3) {
            getTouzhuListener().onCheck("最多可选择3个号码");
            return;
            // 豹子玩法中，二同号单选需要检查两列不能同选
        } else if (getType() == CaipiaoConst.WF_DUIZI && btn.getTag() != null) {
            // 点击的是第五个容器的，那么第六个容器的同一位置的设置非选中
            if (String.valueOf(btn.getTag()).contains("f")) {
                caipiao.getkSanLastBtnList()
                        .get(Integer.parseInt(String.valueOf(btn.getTag())
                                .substring(1)) - 1).setSelected(false);

            } else {
                caipiao.getkSanFiveBtnList().get(Integer.parseInt(String.valueOf(btn.getTag())
                        .substring(1)) - 1).setSelected(false);

            }
        }
        btn.setSelected(true);
        if (getType() == CaipiaoConst.WF_HEZHI) {
            // /点击的是大小奇偶全
            if (btn.getTag() != null) {
                if (String.valueOf(btn.getTag()).equals("大")) {
                    caipiao.getkSanLastBtnList().get(1).setSelected(false);
                    caipiao.getkSanLastBtnList().get(4).setSelected(false);

                } else if (String.valueOf(btn.getTag()).equals("小")) {
                    caipiao.getkSanLastBtnList().get(0).setSelected(false);
                    caipiao.getkSanLastBtnList().get(4).setSelected(false);

                } else if (String.valueOf(btn.getTag()).equals("奇")) {
                    caipiao.getkSanLastBtnList().get(3).setSelected(false);
                    caipiao.getkSanLastBtnList().get(4).setSelected(false);

                } else if (String.valueOf(btn.getTag()).equals("偶")) {
                    caipiao.getkSanLastBtnList().get(2).setSelected(false);
                    caipiao.getkSanLastBtnList().get(4).setSelected(false);

                } else {
                    for (int i = 0; i < 4; i++) {
                        caipiao.getkSanLastBtnList().get(i).setSelected(false);

                    }

                }
                preChangeBtnList();
                // 点击的是投注按钮
            } else {
                changeHouQuBtnState();
            }
        }
        if (getTouzhuListener() != null) {
            getTouzhuListener().onTouzhuCountChange();
        }



//        setChildren(btn,btn.isSelected());



        L.d(btn.isSelected() + "-------------------");


    }

    // 点击投注按钮后底部按钮状态改变
    public void changeHouQuBtnState() {
        if (getSelectCount(caipiao.getBtnList()) == 16)
            changKSanLastBtnState(4, -1);
        else {
            String s = "";
            for (int i = 0; i < caipiao.getBtnList().size(); i++) {
                if (caipiao.getBtnList().get(i).isSelected()) {
                    s += i;
                }
            }
            // 大
            if (s.equals("89101112131415")) {
                changKSanLastBtnState(0, -1);
                // 小
            } else if (s.equals("01234567")) {
                changKSanLastBtnState(1, -1);
                // 奇
            } else if (s.equals("02468101214")) {
                changKSanLastBtnState(2, -1);
                // 偶
            } else if (s.equals("13579111315")) {
                changKSanLastBtnState(3, -1);
                // 大奇
            } else if (s.equals("8101214")) {
                changKSanLastBtnState(0, 2);
                // 大偶
            } else if (s.equals("9111315")) {
                changKSanLastBtnState(0, 3);
                // 小奇
            } else if (s.equals("0246")) {
                changKSanLastBtnState(1, 2);
                // 小偶
            } else if (s.equals("1357")) {
                changKSanLastBtnState(1, 3);
            } else {
                changKSanLastBtnState(-1, -1);
            }
        }
    }

    private void changKSanLastBtnState(int t, int n) {
        for (int i = 0; i < caipiao.getkSanLastBtnList().size(); i++) {
            if (i == t || i == n) {
                caipiao.getkSanLastBtnList().get(i).setSelected(true);

            } else {
                caipiao.getkSanLastBtnList().get(i).setSelected(false);

            }
        }
    }

    private void preChangeBtnList() {
        boolean isDaXiaoSelected = false; // 判断大小选项是否已选择
        if (caipiao.getkSanLastBtnList().get(0).isSelected()) {
            isDaXiaoSelected = true;
            for (int i = 0; i < caipiao.getBtnList().size(); i++)
                if (i > 7) {
                    caipiao.getBtnList().get(i).setSelected(true);

                } else {
                    caipiao.getBtnList().get(i).setSelected(false);

                }
        }
        if (caipiao.getkSanLastBtnList().get(1).isSelected()) {
            isDaXiaoSelected = true;
            for (int i = 0; i < caipiao.getBtnList().size(); i++)
                if (i > 7) {
                    caipiao.getBtnList().get(i).setSelected(false);

                } else {
                    caipiao.getBtnList().get(i).setSelected(true);

                }

        }
        if (caipiao.getkSanLastBtnList().get(2).isSelected()) {
            if (isDaXiaoSelected) {
                for (int i = 0; i < caipiao.getBtnList().size(); i++) {
                    if (caipiao.getBtnList().get(i).isSelected() && i % 2 == 1) {
                        caipiao.getBtnList().get(i).setSelected(false);

                    }
                }
            } else {
                for (int i = 0; i < caipiao.getBtnList().size(); i++)
                    if (i % 2 == 0) {
                        caipiao.getBtnList().get(i).setSelected(true);

                    } else {
                        caipiao.getBtnList().get(i).setSelected(false);

                    }
            }

        }
        if (caipiao.getkSanLastBtnList().get(3).isSelected()) {
            if (isDaXiaoSelected) {
                for (int i = 0; i < caipiao.getBtnList().size(); i++) {
                    if (caipiao.getBtnList().get(i).isSelected() && i % 2 == 0) {
                        caipiao.getBtnList().get(i).setSelected(false);

                    }
                }
            } else {
                for (int i = 0; i < caipiao.getBtnList().size(); i++)
                    if (i % 2 == 0) {
                        caipiao.getBtnList().get(i).setSelected(false);

                    } else {
                        caipiao.getBtnList().get(i).setSelected(true);

                    }
            }
        }
        if (caipiao.getkSanLastBtnList().get(4).isSelected()) {
            for (int i = 0; i < caipiao.getBtnList().size(); i++) {
                caipiao.getBtnList().get(i).setSelected(true);

            }
        }

    }

    private int getSelectCount(List<View> list) {
        int count = 0;
        if (list != null) {
            for (View btn : list) {
                if (btn.isSelected()) {
                    count++;
                }
            }
        }
        return count;
    }

    private int getSelectBtnCount(List<Button> list) {
        int count = 0;
        if (list != null) {
            for (Button btn : list) {
                if (btn.isSelected()) {
                    count++;
                }
            }
        }
        return count;
    }



}
