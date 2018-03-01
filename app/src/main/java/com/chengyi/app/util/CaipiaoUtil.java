package com.chengyi.app.util;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.ParseException;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.caipiao.CaipiaoFactory;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.model.wanfa.AbsWanfa;
import com.chengyi.app.model.wanfa.RenNzhongM;
import com.chengyi.app.model.wanfa.Shengxiao;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.view.widget.TouzhuButton;
import com.chengyi.app.view.widget.XuanhaoLinearLayout;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class CaipiaoUtil {
    public static final int LETOU_ONE_ROW_MAX = 9;
    public static final int PAILIE_ONE_ROW_MAX = 10;
    public static final int PAILIE_ONE_ROW_MAX2 = 8;
    public static final int ZUXUAN_ONE_ROW_MAX = 8;
    public static final int QIANNHOUN_ONE_ROW_MAX = 8;

    public static String formatNumberString(String text) {
        if (text.length() == 1) {
            return "0" + text;
        }
        return text;
    }

    public static List<String> getHaomaList(int start, int end) {
        List<String> list = new ArrayList<>();
        if (end <= 9) {// 如果都为1位数，择不需要加0
            for (int i = start; i <= end; i++) {
                list.add(i + "");
            }
        } else {
            for (int i = start; i <= end; i++) {
                list.add(formatNumberString(i + ""));
            }
        }

        return list;
    }

    private static final String[] danwei = {"第一位", "第二位", "第三位", "第四位", "第五位", "第六位", "第七位",
            "第八位", "第九位", "第十位"};

    public static String getDanwei(int index) {
        return danwei[index];
    }

//    private static final String[] danwei2 = {"个位", "十位", "百位", "千位", "万位",
//            "十万", "百万", "千万", "亿"};
    private static final String[] danwei3 = {"前一位 ", "后一位 ", "第一位", "第二位", "第三位"};
    private static final String[] danwei4 = {"胆码", "拖码 "};
    private static final String[] danwei5 = {"重号", "单号 "};

    public static String getDanwei2(int index) {
        return danwei[index];
    }

    public static String getDanwei3(int index) {
        return danwei3[index];
    }

    public static String getDanwei4(int index) {
        return danwei4[index];
    }

    public static String getDanwei5(int index) {
        return danwei5[index];
    }

    public static List<String> getDaxiaodanshuang() {
        List<String> dxdsList = new ArrayList<String>();
        dxdsList.add("大");
        dxdsList.add("小");
        dxdsList.add("单");
        dxdsList.add("双");
        return dxdsList;
    }

    public static int getRandomNumber(Caipiao cp) {
        if (cp.getCurrentWanfa() instanceof RenNzhongM) {
            return ((RenNzhongM) cp.getCurrentWanfa()).getN();
        }
        if (cp.getCurrentWanfa() instanceof Shengxiao) {
            return ((Shengxiao) cp.getCurrentWanfa()).getN();
        }
        int number = cp.getQianquMinCount();
        switch (cp.getCurrentWanfa().getType()) {
            case CaipiaoConst.WF_ZU3:
            case CaipiaoConst.WF_LIAN2ZX:
            case CaipiaoConst.WF_SX_ZU3:// 三星组三
            case CaipiaoConst.WF_QIAN2_ZUXUAN:// 前2组选，新老11选5
                number = 2;
                break;
            case CaipiaoConst.WF_ZU6:
            case CaipiaoConst.WF_SX_ZU6:// 三星组六
            case CaipiaoConst.WF_QIAN3_ZUXUAN:// 前3组选，新老11选5
                number = 3;
                break;
            case CaipiaoConst.WF_EX_ZUXUAN:// 二星组选，组2，竞猜后2位，不限顺序
                number = 2;
                break;
        }
        return number;
    }

    public static int getCombination(int total, int size) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < total; i++) {
            list.add("a");
        }
        return produceCombination(list, size);
    }

    public static List<int[]> getCombination2(int total, int size) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < total; i++) {
            list.add("a");
        }
        return produceCombination2(list, size);
    }

    public static List<int[]> produceCombination2(List<String> list, int size) {
        @SuppressWarnings("unused")
        int totalCount = 0;
        List<int[]> result = new ArrayList<int[]>();
        if (size > list.size()) {
            return result;
        }
        // 创建一个数组，前size个元素全是1
        int[] digit = new int[list.size()];
        for (int i = 0; i < size; ++i) {
            digit[i] = 1;
        }
        result.add(copyIntArray(digit));
        // 输出第一组
        // printCombination(list, digit);
        totalCount++;
        while (!end(digit, digit.length - size)) {

            for (int i = 0; i < digit.length - 1; ++i) {
                if (digit[i] == 1 && digit[i + 1] == 0) {
                    // i上是1，i + 1是0，交换
                    int temp = digit[i];
                    digit[i] = digit[i + 1];
                    digit[i + 1] = temp;

                    // 移动i前面的所有1到最左端
                    int count = countOf1(digit, i);
                    for (int j = 0; j < count; ++j) {
                        digit[j] = 1;
                    }
                    for (int j = count; j < i; ++j) {
                        digit[j] = 0;
                    }
                    // printCombination(list, digit);
                    result.add(copyIntArray(digit));
                    totalCount++;
                    break;
                }
            }
        }
        return result;
    }

    private static int[] copyIntArray(int[] array) {
        int[] result = new int[array.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = array[i];
        }
        return result;
    }

    public static int getArrangement(int total, int size) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < total; i++) {
            list.add("a");
        }
        return produceArrangement(list);
    }

    public static int produceCombination(List<String> list, int size) {
        int totalCount = 0;
        if (size <= 0 || size > list.size()) {
            return 0;
        }
        // 创建一个数组，前size个元素全是1
        int[] digit = new int[list.size()];
        for (int i = 0; i < size; ++i) {
            digit[i] = 1;
        }
        // 输出第一组
        // printCombination(list, digit);
        totalCount++;
        while (!end(digit, digit.length - size)) {
            for (int i = 0; i < digit.length - 1; ++i) {
                if (digit[i] == 1 && digit[i + 1] == 0) {
                    // i上是1，i + 1是0，交换
                    int temp = digit[i];
                    digit[i] = digit[i + 1];
                    digit[i + 1] = temp;

                    // 移动i前面的所有1到最左端
                    int count = countOf1(digit, i);
                    for (int j = 0; j < count; ++j) {
                        digit[j] = 1;
                    }
                    for (int j = count; j < i; ++j) {
                        digit[j] = 0;
                    }
                    // printCombination(list, digit);
                    totalCount++;
                    break;
                }
            }
        }
        return totalCount;
    }

    // 在下标end前1的个数
    private static int countOf1(int[] digit, int end) {
        int count = 0;
        for (int i = 0; i < end; ++i) {
            if (digit[i] == 1) {
                ++count;
            }
        }
        return count;
    }

    // 数组中为1的下标对应的字符需要输出
    // private static void printCombination(List<String> list, int[] digit) {
    // StringBuffer sb = new StringBuffer();
    // for (int i = 0; i < digit.length; ++i) {
    // if (digit[i] == 1) {
    // sb.append(list.get(i));
    // }
    // }
    // // System.out.println(sb + "   " + index++);
    // }

    // 结束条件：前 size 个元素都是0
    private static boolean end(int[] digit, int size) {
        int sum = 0;
        for (int i = 0; i < size; ++i) {
            sum += digit[i];
        }
        return sum == 0 ? true : false;
    }

    public static int produceArrangement(List<String> list) {
        int totalCount = 0;

        int[] digit = new int[list.size()];
        int base = list.size();
        while (!end(digit)) {
            ++digit[0]; // 第1个元素值加1
            // 满N进1
            for (int i = 0; i < digit.length; ++i) {
                if (digit[i] == base) {
                    digit[i] = 0;
                    ++digit[i + 1];
                } else {
                    break;
                }
            }
            if (isArrangement(digit)) {
                // printArrangement(list, digit);
                totalCount++;
            }
        }
        return totalCount;
    }

    // 数组中每个元素都不同，则是排列中的一个

    private static boolean isArrangement(int[] digit) {
        int sum = 0;
        int endSum = (0 + digit.length - 1) * digit.length / 2;
        for (int i = 0; i < digit.length; ++i) {
            sum += digit[i];
        }

        // 为了减少创建Set，所以判断一下数组中元素的和是不是结束时元素的和，如果是才再继续判断.
        if (sum != endSum) {
            return false;
        } else {
            Set<Integer> is = new HashSet<Integer>();
            for (int i : digit) {
                is.add(i);
            }
            if (is.size() != digit.length) {
                return false;
            } else {
                return true;
            }
        }
    }

    // private static void printArrangement(List<String> list, int[] digit) {
    // StringBuilder sb = new StringBuilder();
    // for (int i = 0; i < digit.length; ++i) {
    // sb.append(list.get(digit[i]));
    // }
    // System.out.println(sb);
    // }

    // 如果数组中的元素是 0, 1, 2, ..., digit.length - 1，则结束
    private static boolean end(int[] digit) {
        for (int i = 0; i < digit.length; ++i) {
            if (digit[i] != i) {
                return false;
            }
        }
        return true;
    }

    public static int[] getRandomArray(int n, int t) {
        List<Integer> numbers = new ArrayList<Integer>();
        int[] rtnumbers = new int[t];
        for (int i = 0; i < n; i++) { // 初始化数组
            numbers.add(i);
        }
        Random random = new Random();
        for (int j = 0; j < t; j++) {
            int raNum = random.nextInt(numbers.size());
            rtnumbers[j] = numbers.get(raNum);
            numbers.remove(raNum);
        }
        return rtnumbers;
    }

    public static List<String> getStringArray(List<String> list,
                                              int[] randomArray) {
        List<String> temp = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < randomArray.length; j++) {
                if (randomArray[j] == i) {
                    temp.add(list.get(i));
                    break;
                }
            }
        }
        return temp;
    }

    public static int getSelectedBtnCount(List<TouzhuButton> list) {
        int count = 0;
        if (list != null) {
            for (TouzhuButton btn : list) {
                if (btn.isSelected()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @param cp
     * @param qianquRandomNum 前区随机的个数，0为不随机保持
     * @param houquRandomNum
     * @return
     */
    public static List<List<Boolean>> getRandom(Caipiao cp,
                                                int qianquRandomNum, int houquRandomNum) {
        List<List<Boolean>> list = new ArrayList<List<Boolean>>();

        return list;
    }


    public static String trimLast(String s) {
        // s = s.trim();
        // boolean b = false;
        // if (s.endsWith(CaipiaoConst.PADDING_QU)) {
        // b = true;
        // s = s.substring(0, s.lastIndexOf(CaipiaoConst.PADDING_QU));
        // } else if (s.endsWith(CaipiaoConst.PADDING_QU.trim())) {
        // b = true;
        // s = s.substring(0, s.lastIndexOf(CaipiaoConst.PADDING_QU.trim()));
        // s = s.trim();
        // }
        // if (s.endsWith(CaipiaoConst.PADDING_WEI) && !b) {
        // s = s.substring(0, s.lastIndexOf(CaipiaoConst.PADDING_WEI));
        // }
        return s;
    }

    public static int getTotalZhushu(List<TouzhuquerenData> list) {
        int total = 0;
        if (list == null) {
            return 0;
        }
        for (TouzhuquerenData d : list) {
            total = total + d.getZhushu();
        }
        return total;
    }

    public static String[] getBeitouArray() {
        String[] s = new String[999];
        for (int i = 0; i < s.length; i++) {
            s[i] = (i + 1) + "";
        }
        return s;
    }

    public static String[] getZhuihaoArray() {
        String[] s = new String[50];
        for (int i = 0; i < s.length; i++) {
            s[i] = (i + 1) + "";
        }
        return s;
    }

    public static SharedPreferences getCpSharedPreferences(Context context) {
        return context.getSharedPreferences("jiyunkeji", Activity.MODE_PRIVATE);

    }

    public static SharedPreferences getCpSharedPreferences(Fragment context) {
        return getCpSharedPreferences(context.getContext());

    }

    /**
     * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
     * @author fy.zhang
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        String[] daojishi = CaipiaoApplication.getInstance().getDaojishi();
        if (days == 0) {
            if (hours == 0) {
                if (minutes == 0)
                    return seconds + daojishi[3];
                else
                    return minutes + daojishi[2] + seconds
                            + daojishi[3];
            } else {
                return hours + daojishi[1] + minutes + daojishi[2];
            }
        } else {
            return days + daojishi[0] + hours + daojishi[1];
        }

    }

    public static SpannableStringBuilder formatTextColorRed(String s) {
        int start = s.indexOf("奖金") + 2;
        int end = s.length() - 1;
        SpannableStringBuilder style = new SpannableStringBuilder(s);
//        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
//        style.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    /**
     * @param begin 时间段的开始
     * @param end   时间段的结束
     * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
     * @author fy.zhang
     */
    public static String formatDuring(Date begin, Date end) {
        return formatDuring(end.getTime() - begin.getTime());
    }

    // public static boolean isShuzi(String s) {
    // return s != null && s.matches(CaipiaoConst.SHUZI_R);
    // }

    public static boolean isZhengZhengshu(String s) {
        return s != null && s.matches(CaipiaoConst.SHUZI_ZSR);
    }

    public static SpannableStringBuilder getTouzhuSpan(String haoma,
                                                       int QIANQU_MAX_LENGTH) {
        haoma=haoma.replaceAll("-","|");
        Context mContext = CaipiaoApplication.getInstance()
                .getApplicationContext();
        int index = haoma.indexOf(CaipiaoConst.PADDING_QU);
        SpannableStringBuilder ss = null;
        if (index != -1) {
            if (index > QIANQU_MAX_LENGTH) {
                String newStr = haoma.substring(0, QIANQU_MAX_LENGTH)
                        + CaipiaoConst.MORE + haoma.substring(index);
                ss = new SpannableStringBuilder(newStr);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                                .getColor(R.color.red)), 0, QIANQU_MAX_LENGTH
                                + CaipiaoConst.MORE.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                                .getColor(R.color.blue)),
                        QIANQU_MAX_LENGTH + CaipiaoConst.MORE.length()
                                + CaipiaoConst.PADDING_QU.length(), newStr
                                .length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                ss = new SpannableStringBuilder(haoma);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                                .getColor(R.color.red)), 0, index
                                + CaipiaoConst.PADDING_QU.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                                .getColor(R.color.blue)), index
                                + CaipiaoConst.PADDING_QU.length(), haoma.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        } else {
            ss = new SpannableStringBuilder(haoma);
            ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                            .getColor(R.color.red)), 0, haoma.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }


    public static boolean isKuaikai(int cpid) {
        return cpid == CaipiaoConst.ID_LAOSHISHICAI
                || cpid == CaipiaoConst.ID_KUAILE10FEN
                || CaipiaoUtil.is11xr5(cpid)
                || cpid == CaipiaoConst.ID_LUCKYCAR
                || CaipiaoUtil.isKySj(cpid)
                ;
    }

    public static String[] getQianqu(String haoma) {
        int index = haoma.indexOf("#");
        String[] a = null;
        if (index != -1) {
            a = haoma.substring(0, index).split(",");
        } else {
            a = haoma.split(",");
        }
        return a;
    }

    public static String[] getHouqu(String haoma) {
        int index = haoma.indexOf("#");
        String[] a = null;
        if (index != -1) {
            a = haoma.substring(index + 1).split(",");
        }
        return a;
    }

    public static int getBallBg(int bgs, int oneRowMax) {
        int res = bgs;
//		if (oneRowMax <= 8) {
//			if (bgs == R.drawable.new_qianqu_states) {
//				// res = R.drawable.qianqu_states_big;
//			} else {
//				// res = R.drawable.houqu_states_big;
//			}
//		} else {
//			res = bgs;
//		}
        return res;
    }

    public static int getColNum(List<String> list, int oneRowMax) {
        return (list.size() % oneRowMax == 0 ? 0 : 1) + list.size() / oneRowMax;
    }

    /**
     * @param list 行的序号，从0开始
     * @return
     */
    public static List<String> getTextsByRow(final List<String> list,
                                             int colIndex, int oneRowMax) {
        List<String> temp = new ArrayList<String>(list);
        int mod = list.size() % oneRowMax;
        if (mod != 0) {
            int addNum = oneRowMax - mod;
            for (int i = 0; i < addNum; i++) {
                temp.add(null);// 每行的个数要相等，为null的按钮，设置为INVISIBLITY 这样就可以对齐
            }
        }
        int startIndex = colIndex * oneRowMax;
        if (startIndex >= temp.size()) {
            return null;
        }
        return temp.subList(startIndex, startIndex + oneRowMax);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 格式化服务器返回的
     *
     * @param haoma
     * @return
     */
    public static SpannableStringBuilder getSpanFromFuwuqi(String haoma) {
        if (haoma == null || haoma.trim().length() == 0)
            return null;
        Context mContext = CaipiaoApplication.getInstance()
                .getApplicationContext();
        SpannableStringBuilder ss = null;
        try {
            String[] q = CaipiaoUtil.getQianqu(haoma);
            String[] h = CaipiaoUtil.getHouqu(haoma);
            String s = "";
            if (q == null) {
                ss = new SpannableStringBuilder(haoma);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                                .getColor(R.color.black)), 0, haoma.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return ss;
            }
            if (h != null) {// 有后区
                for (int i = 0; i < q.length; i++) {
                    if (i != 0) {
                        s = s + CaipiaoConst.PADDING_2WEINUMBER + q[i];
                    } else {
                        s = q[i];
                    }
                }
                s = s + CaipiaoConst.PADDING_QU;
                int tempIndex = s.length();
                for (int i = 0; i < h.length; i++) {
                    if (i != 0) {
                        s = s + CaipiaoConst.PADDING_2WEINUMBER + h[i];
                    } else {
                        s = s + h[i];
                    }
                }
                ss = new SpannableStringBuilder(s);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                                .getColor(R.color.red)), 0, tempIndex,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                                .getColor(R.color.blue)), tempIndex, s.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return ss;
            } else {
                for (int i = 0; i < q.length; i++) {
                    if (i != 0) {
                        s = s + CaipiaoConst.PADDING_2WEINUMBER + q[i];
                    } else {
                        s = q[i];
                    }
                }
                ss = new SpannableStringBuilder(s);
                ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                                .getColor(R.color.red)), 0, haoma.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                return ss;
            }
        } catch (Exception e) {
            ss = new SpannableStringBuilder(haoma);
            ss.setSpan(new ForegroundColorSpan(mContext.getResources()
                            .getColor(R.color.black)), 0, haoma.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return ss;
        }

    }

    public static String getDate(String time) {
        String[] array = time.split(" ");
        if (array.length >= 2) {
            return array[0].trim();
        }
        return time;
    }

    public static String getSchemeType(int id) {
        Context context = CaipiaoApplication.getInstance()
                .getApplicationContext();
        switch (id) {
            case 101:
                return context.getString(R.string.daigou);
            case 102:
                return context.getString(R.string.daigouzhuihao);
            case 201:
                return context.getString(R.string.hemaifangan);
            case 202:
                return context.getString(R.string.hemaizhuihaofangan);
        }
        return "";
    }

    public static String getOpenName(int id) {
        Context context = CaipiaoApplication.getInstance()
                .getApplicationContext();
        switch (id) {
            case 0:
                return context.getString(R.string.weishangchuanfangan);
            case 1:
                return context.getString(R.string.gongkai);
            case 2:
                return context.getString(R.string.buyunxuchakan);
            case 3:
                return context.getString(R.string.canyuhougongkai);
            case 4:
                return context.getString(R.string.jiezhihougongkai);
        }
        return "";
    }

    public static final int DONOT_UPDATE = 0;
    public static final int MUST_UPDATE = 1;
    public static final int PLEASE_UPDATE = 2;

//	public static int getVersionUpdateInfo( result) {
//		String versionContent = result.getString(URLSuffix.version_content);
//		try {
//			BaseJSONObject json = new BaseJSONObject(versionContent);
//			String serverCurrent = json.getString(URLSuffix.current_version)
//					.trim();
//			String minVersion = json.getString(URLSuffix.min_version).trim();
//			if (getVersionCompare(CaipiaoConst.VERSION, serverCurrent) >= 0) {
//				return DONOT_UPDATE;
//			} else {// 客户端版本比较小，
//				if (getVersionCompare(CaipiaoConst.VERSION, minVersion) >= 0) {
//					return PLEASE_UPDATE;
//				} else {
//					return MUST_UPDATE;
//				}
//			}
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return DONOT_UPDATE;
//		}
//	}

//    private static int getVersionCompare(String v1, String v2) {
//        if (TextUtils.isEmpty(v1) && !TextUtils.isEmpty(v2)) {
//            return -1;
//        }
//        if (TextUtils.isEmpty(v1) && TextUtils.isEmpty(v2)) {
//            return 0;
//        }
//        if (!TextUtils.isEmpty(v1) && TextUtils.isEmpty(v2)) {
//            return 1;
//        }
//        String[] v1Array = v1.split("\\.");
//        String[] v2Array = v2.split("\\.");
//        if (v1Array.length == v2Array.length) {
//            try {
//                for (int i = 0; i < v1Array.length; i++) {
//                    if (Integer.valueOf(v1Array[i]) < Integer
//                            .valueOf(v2Array[i])) {
//                        return -1;
//                    } else if (Integer.valueOf(v1Array[i]) > Integer
//                            .valueOf(v2Array[i])) {
//                        return 1;
//                    }
//                }
//                return 0;
//            } catch (Exception e) {
//                return 0;
//            }
//        } else {
//            return 0;
//        }
//
//    }

    private static final int YIYUAN = 100000000;

//    public static String amountToString(String money) {
//        if (money == null || money.equals("0")) {
//            return "0";
//        }
//
//        String strReturn = money;
//        try {
//            int m = Integer.valueOf(money);
//            if (m > YIYUAN) {
//                strReturn = m / YIYUAN + ".";
//                if ((m / 1000000) % 100 < 10) {
//                    strReturn = strReturn + "0";
//                }
//                strReturn = strReturn + (m / 1000000) % 100 + "亿";
//            } else if (m > 100000) {
//                strReturn = m / 10000 + "万";
//            } else {
//                strReturn = m + "";
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return money;
//        }
//        return strReturn;
//    }

    public static String getYixingSubString(String s) {
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                result = s.charAt(0) + "";
            } else {
                result = result + "|" + s.charAt(i);
            }
        }
        return result;
    }

    public static Caipiao getCaipiaoByIdAndSetWf(int cpId, int wfType) {
        Caipiao cp = CaipiaoFactory.getInstance(
                CaipiaoApplication.getInstance().getApplicationContext())
                .getCaipiaoById(cpId);
        if (cp == null) {
            return null;
        }
        List<AbsWanfa> wflist = cp.getWanfaList();
        for (AbsWanfa wf : wflist) {
            if (wf.getType() == wfType) {
                cp.setCurrentWanfa(wf);
                return cp;
            }
        }
        return cp;
    }

    private static DecimalFormat df = new DecimalFormat();

    public static String formatPrice(double p) {
        try {
            if (df == null) {
                df = new DecimalFormat();
            }
            String style = "0.00";// 定义要显示的数字的格式
            df.applyPattern(style);// 将格式应用于格式化器
            return df.format(p);
        } catch (Exception e) {
            e.printStackTrace();
            return p + "";
        }
    }

    public static String formatZhengshuPrice(double p) {
        try {
            if (df == null) {
                df = new DecimalFormat();
            }
            String style = "0";// 定义要显示的数字的格式
            df.applyPattern(style);// 将格式应用于格式化器
            return df.format(p);
        } catch (Exception e) {
            return p + "";
        }
    }

    public static void setNoHintWhenFocus(EditText et) {
        try {
            et.setOnFocusChangeListener(new OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText _v = (EditText) v;
                    if (!hasFocus) {// 失去焦点
                        _v.setHint(_v.getTag().toString());
                    } else {
                        String hint = _v.getHint().toString();
                        _v.setTag(hint);
                        _v.setHint("");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//	public static void saveSharkData(boolean dakai, Activity activity) {
//		SharedPreferences preferences = CaipiaoUtil
//				.getCpSharedPreferences(activity);
//		SharedPreferences.Editor editor = preferences.edit();
//		editor.putBoolean("dakaiyaodongxuanhao", dakai);
//		editor.commit();
//	}
//
//	public static void saveYilouData(boolean dakai, Activity activity) {
//		SharedPreferences preferences = CaipiaoUtil
//				.getCpSharedPreferences(activity);
//		SharedPreferences.Editor editor = preferences.edit();
//	    editor.putBoolean(CaipiaoConst.DAKAI_YILOU, dakai);
//		editor.commit();
//
//	}

    public static void youmengEvent(Context context, String key,
                                    HashMap<String, String> map) {

    }

    public static void youmengEvent(Context context, String key) {

    }


    public static boolean isFileApk(String url) {
        return url.toLowerCase().endsWith("apk")
                || url.toLowerCase().endsWith("rar")
                || url.toLowerCase().endsWith("zip");
    }

    /**
     * 检测网络连接是否可用
     *
     * @param ctx
     * @return true 可用; false 不可用
     */
    public static boolean isNetworkAvailable(Context ctx) {
        try {
            ConnectivityManager cm = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (isWifiEnable(ctx)) {
                return true;
            }
            if (cm == null) {
                return false;
            }
            // 取当前默认的连接信息
            NetworkInfo netinfo = cm.getActiveNetworkInfo();
            if (netinfo != null && netinfo.isConnected()) {
                return true;
            }
            State mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                    .getState();

            if (mobile == State.CONNECTED) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }//http://m.jicai500.com/zoushi/10038#bt=10038
    }

    private static boolean isWifiEnable(Context ctx) {
        WifiManager wifi = (WifiManager) ctx
                .getSystemService(Context.WIFI_SERVICE);
        return wifi.isWifiEnabled();
    }

    public static String getCpId(int id) {// 由于排列3和排列5 真实的id相同，而对客户端来说
        // 这个是两种彩票，要不同
        if (id == CaipiaoConst.ID_PAILIE5) {
            return CaipiaoConst.ID_PAILIE3 + "";
        } else if (id == CaipiaoConst.ID_GUANYAJUN)
            return CaipiaoConst.ID_JINGCAIZUQIU + "";
        return id + "";
    }

    // array中是否有重复
    public static boolean isChongfu(String[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (array[i].equals(array[j]) && i != j) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param tvs
     * @param cpId         彩票ID
     * @param wfType       玩法类型
     * @param indexOfGroup 第几组
     * @param startIndex   在组中的索引 从0开始
     */
    public static void setYilouData(final TextView[] tvs, final int cpId,
                                    final int wfType, final int indexOfGroup, final int startIndex) {

        final String url = getLyUrl(cpId, wfType, indexOfGroup);
        final SharedPreferences preferences = CaipiaoUtil
                .getCpSharedPreferences(CaipiaoApplication.getInstance()
                        .getApplicationContext());
        if (url != null) {
            try {
                String defaultData = preferences.getString(url, "");
                if (!TextUtils.isEmpty(defaultData)) {// 先设置上次的数据 同时请求
                    String[] a = defaultData.split("&");
                    setYilou(tvs, a[1], Integer.valueOf(a[2]), startIndex);
                    // currentState!=null时，需要强制更新遗漏
                    if (XuanhaoLinearLayout.currentState == null) {
                        long lastTime = Long.valueOf(a[0]);
                        if (!CaipiaoUtil.isKuaikai(cpId)) {
                            if (System.currentTimeMillis() - lastTime < 60000 * 10) {
                                return;
                            }
                        } else {
                            if (System.currentTimeMillis() - lastTime < 30000) {
                                return;
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
            RequestParams params = new RequestParams();
            HttpBusinessAPI.post(url, params, new HttpRespHandler() {
                @Override
                public void onSuccess(String result) {
//					String s = JSONObject.parseObject(result).getString(URLSuffix.miss);
//					int max = 10;
//					try {
//						max = Integer.valueOf( JSONObject.parseObject(result).getString((URLSuffix.max)));
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//					if (!TextUtils.isEmpty(s)) {
//						setYilou(tvs, s, max, startIndex);
//						SharedPreferences.Editor editor = preferences.edit();
//						editor.putString(url, System.currentTimeMillis() + "&"
//								+ s + "&" + max);
//						editor.commit();
//					}
                }
            });
        }
    }

    private static void setYilou(final TextView[] tvs, String s, int max,
                                 int startIndex) {
        String[] array = s.split(",");
        if (tvs.length <= array.length) {
            for (int i = 0; i < tvs.length; i++) {
                if (tvs[i].getVisibility() == View.INVISIBLE) {
                    //碰到占位的即停止
                    break;
                }
                tvs[i].setText(array[startIndex + i]);
                try {
                    if (Integer.valueOf(array[startIndex + i]) >= max) {
                        tvs[i].setTextColor(Color.RED);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    ///获取遗漏的url
    private static String getLyUrl(int cpId, int wfType, int indexOfGroup) {
        switch (cpId) {
            case CaipiaoConst.ID_FUCAI3D:
                if (wfType == CaipiaoConst.WF_NORMAL_PAILIE) {
                    return CaipiaoConst.FUCAI3D_YL[indexOfGroup];
                } else {
                    return CaipiaoConst.FUCAI3D_YL[3];
                }
            case CaipiaoConst.ID_PAILIE3:
                if (wfType == CaipiaoConst.WF_NORMAL_PAILIE) {
                    return CaipiaoConst.PAILIE2_YL[indexOfGroup];
                } else {
                    return CaipiaoConst.PAILIE2_YL[3];
                }
            case CaipiaoConst.ID_PAILIE5:
                if (wfType == CaipiaoConst.WF_NORMAL_PAILIE) {
                    return CaipiaoConst.PAILIE5_YL[indexOfGroup];
                }
                //老时时彩
            case CaipiaoConst.ID_LAOSHISHICAI:
                if (wfType == CaipiaoConst.WF_WX_TONGXUAN
                        || wfType == CaipiaoConst.WF_WX_ZHIXUAN
                        ) {
                    return CaipiaoConst.LAOSHISHICAI_YL[indexOfGroup];
                } else if (wfType == CaipiaoConst.WF_SX_ZHIXUAN) {
                    return CaipiaoConst.LAOSHISHICAI_YL[indexOfGroup + 5];
                } else if (wfType == CaipiaoConst.WF_SX_ZU3
                        || wfType == CaipiaoConst.WF_SX_ZU6) {
                    return CaipiaoConst.LAOSHISHICAI_YL[8];
                } else if (wfType == CaipiaoConst.WF_EX_ZHIXUAN) {
                    return CaipiaoConst.LAOSHISHICAI_YL[indexOfGroup + 9];
                } else if (wfType == CaipiaoConst.WF_EX_ZUXUAN) {
                    return CaipiaoConst.LAOSHISHICAI_YL[11];
                } else if (wfType == CaipiaoConst.WF_YX_ZHIXUAN) {
                    return CaipiaoConst.LAOSHISHICAI_YL[12];
                }
                //新时时彩
            case CaipiaoConst.ID_XINSHISHICAI:
                if (wfType == CaipiaoConst.WF_WX_TONGXUAN
                        || wfType == CaipiaoConst.WF_WX_ZHIXUAN
                        || wfType == CaipiaoConst.WF_REN1
                        || wfType == CaipiaoConst.WF_REN2
                        ) {
                    return CaipiaoConst.XINSHISHICAI_YL[indexOfGroup];
                } else if (wfType == CaipiaoConst.WF_SIXING_ZHIXUAN) {
                    return CaipiaoConst.XINSHISHICAI_YL[indexOfGroup + 5];
                } else if (wfType == CaipiaoConst.WF_SX_ZHIXUAN) {
                    return CaipiaoConst.XINSHISHICAI_YL[indexOfGroup + 9];
                } else if (wfType == CaipiaoConst.WF_SX_ZU3
                        || wfType == CaipiaoConst.WF_SX_ZU6) {
                    return CaipiaoConst.XINSHISHICAI_YL[12];
                } else if (wfType == CaipiaoConst.WF_EX_ZHIXUAN) {
                    return CaipiaoConst.XINSHISHICAI_YL[indexOfGroup + 13];
                } else if (wfType == CaipiaoConst.WF_EX_ZUXUAN) {
                    return CaipiaoConst.XINSHISHICAI_YL[15];
                } else if (wfType == CaipiaoConst.WF_YX_ZHIXUAN) {
                    return CaipiaoConst.XINSHISHICAI_YL[16];
                }
                //快乐10分
            case CaipiaoConst.ID_KUAILE10FEN:
                if (CaipiaoUtil.isRenOne(wfType)) {
                    return CaipiaoConst.KUAILE10FEN_YL[0];
                } else if (wfType == CaipiaoConst.WF_QIAN3_ZUXUAN) {
                    return CaipiaoConst.KUAILE10FEN_YL[2];
                } else {
                    return CaipiaoConst.KUAILE10FEN_YL[1];
                }
            case CaipiaoConst.ID_LAO11XUAN5:
            case CaipiaoConst.ID_XIN11XUAN5:
            case  CaipiaoConst.ID_FUJM11XUAN5:
            case CaipiaoConst.ID_11XUAN5:
                if (CaipiaoUtil.isRenOne(wfType)) {
                    return CaipiaoConst.LAO11XUAN5_YL[0];
                } else if (wfType == CaipiaoConst.WF_QIAN2) {
                    return CaipiaoConst.LAO11XUAN5_YL[indexOfGroup + 2];
                } else if (wfType == CaipiaoConst.WF_QIAN3) {
                    return CaipiaoConst.LAO11XUAN5_YL[indexOfGroup + 4];
                } else if (wfType == CaipiaoConst.WF_QIAN2_ZUXUAN) {
                    return CaipiaoConst.LAO11XUAN5_YL[7];
                } else if (wfType == CaipiaoConst.WF_QIAN3_ZUXUAN) {
                    return CaipiaoConst.LAO11XUAN5_YL[8];
                } else {
                    return CaipiaoConst.LAO11XUAN5_YL[1];
                }




            case CaipiaoConst.ID_LUCKYCAR:
                if (CaipiaoUtil.isRenOne(wfType)) {
                    return CaipiaoConst.LuckyCar_YL[0];
                } else if (wfType == CaipiaoConst.WF_QIAN2) {
                    return CaipiaoConst.LuckyCar_YL[indexOfGroup];
                } else if (wfType == CaipiaoConst.WF_QIAN3) {
                    return CaipiaoConst.LuckyCar_YL[indexOfGroup];
                } else if (wfType == CaipiaoConst.WF_YX_ZHIXUAN) {
                    return CaipiaoConst.LuckyCar_YL[3];
                } else if (wfType == CaipiaoConst.WF_QIAN2_ZUXUAN) {
                    return CaipiaoConst.LuckyCar_YL[4];
                } else if (wfType == CaipiaoConst.WF_QIAN3_ZUXUAN) {
                    return CaipiaoConst.LuckyCar_YL[3];
                }
        }

        return null;
    }

    public static int getCaipiaoIdByName(String cpName) {
        List<Caipiao> list = CaipiaoFactory.getInstance(
                CaipiaoApplication.getInstance().getApplicationContext())
                .getCaipiaoList();
        for (Caipiao cp : list) {
            if (cpName.contains(cp.getName())) {
                return cp.getId();
            }
        }
        return -1;
    }

    /**
     * 判断某个彩票id在本地是否存在
     */
    public static boolean HasThisCaipiaoById(int id) {
        List<Caipiao> list = CaipiaoFactory.getInstance(
                CaipiaoApplication.getInstance().getApplicationContext())
                .getCaipiaoList();
        for (Caipiao cp : list) {
            if (cp.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static int getWanfaTypeByName(int cpId, String wfName) {
        // Caipiao cp = CaipiaoFactory.getInstance(
        // CaipiaoApplication.getInstance().getApplicationContext())
        // .getCaipiaoById(cpId);
        // List<AbsWanfa> list = cp.getWanfaList();
        if (wfName.equals("单式") || wfName.equals("复式")) {
            return CaipiaoConst.WF_NORMAL_LETOU;
        }
        return -1;
    }

    public static List<Caipiao> getTopAdapterList(List<Caipiao> list,
                                                  boolean isTubiao) {
        if (isTubiao) {
            return getCaipiaoListWithTubiao(list);
        } else {
            return list;
        }
    }

    public static List<Caipiao> getCaipiaoListWithTubiao(
            List<Caipiao> caipiaoList) {
        List<Caipiao> list = new ArrayList<Caipiao>();
        for (Caipiao cp : caipiaoList) {
            if (hasTubiao(cp.getId())) {
                list.add(cp);
            }
        }
        return list;
    }

    public static boolean hasTubiao(int cpId) {
        return cpId == CaipiaoConst.ID_SHUANGSEQIU
                || cpId == CaipiaoConst.ID_XINSHISHICAI
                || cpId == CaipiaoConst.ID_XIN11XUAN5
                || cpId == CaipiaoConst.ID_11XUAN5
                || cpId == CaipiaoConst.ID_LAO11XUAN5
                || cpId == CaipiaoConst.ID_FUCAI3D
                || cpId == CaipiaoConst.ID_DALETOU
                || cpId == CaipiaoConst.ID_LAOSHISHICAI
                || cpId == CaipiaoConst.ID_QILECAI
                || cpId == CaipiaoConst.ID_FUCAI15XUAN5
                || cpId == CaipiaoConst.ID_PAILIE5
                || cpId == CaipiaoConst.ID_QIXINGCAI
                || cpId == CaipiaoConst.ID_QUANGUO22XUAN5
                || cpId == CaipiaoConst.ID_TICAI6JIA1
                || cpId == CaipiaoConst.ID_TICAI20XUAN5
                || cpId == CaipiaoConst.ID_PAILIE3;
    }

    // 是否身份证
    public static boolean isShenfenzheng(String sfz) {
        if (sfz.length() >= 15) {
            if (sfz.toLowerCase().endsWith("x") || sfz.endsWith("***")
                    || sfz.toLowerCase().endsWith("y"))
                return true;
        }
        if (sfz.length() == 18) {
            return isZhengZhengshu(sfz.substring(0, 17));
        } else if (sfz.length() == 15) {
            return isZhengZhengshu(sfz.substring(0, 14));
        }
        return false;
    }

    public static boolean hasShowAutoDialog(Context context, String id) {
        SharedPreferences preferences = CaipiaoUtil
                .getCpSharedPreferences(context);
        String s = preferences.getString(CaipiaoConst.AUTO_DIALOG_PRE, "");
        return s.contains(id);
    }

    public static void addShowAutoDialog(Context context, String id) {
        SharedPreferences preferences = CaipiaoUtil
                .getCpSharedPreferences(context);
        String s = preferences.getString(CaipiaoConst.AUTO_DIALOG_PRE, "");
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CaipiaoConst.AUTO_DIALOG_PRE, s + "," + id);
        editor.commit();
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        return sdf;
    }

    /**
     * 自定义通知界面
     **/
    public static void showNotification(Context context, String title, Intent in, String titleshow, String content, int NOTIFICATION_ID) {
        final NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int icon = R.drawable.lauch;
        CharSequence tickerText = title;
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);
        notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;//发出默认声音
        notification.flags |= Notification.FLAG_AUTO_CANCEL;///在通知栏上点击此通知后自动清除此通知
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.contentIntent = contentIntent;
        //自定义界面
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notificationstyle);
        contentView.setImageViewResource(R.id.image, R.drawable.lauch);
        contentView.setTextViewText(R.id.title, "购彩提醒");
        contentView.setTextViewText(R.id.text, CaipiaoConst.ToDBC(content));
        notification.contentView = contentView;
        nm.notify(NOTIFICATION_ID, notification);
    }

    //显示遗漏
    public static boolean showYilou(Context mContext, int cpId, AbsWanfa wanfa) {
//		return hasYilou(cpId)
//				&& CaipiaoUtil.getCpSharedPreferences(mContext).getBoolean("dakaiyilou", true)
//				&& wanfa.getType() != CaipiaoConst.WF_DXDS// 大小单双不做遗漏
//				&& wanfa.getType() != CaipiaoConst.WF_LIAN2ZHX//连2直选
//				&& wanfa.getType() != CaipiaoConst.WF_QIAN3ZHIX// 大小单双不做遗漏
//				&& wanfa.getType() != CaipiaoConst.WF_ZU3_SINGLE;// 组三单式没有遗漏
        return false;
    }

    ///某种玩法是否存在遗漏
    public static boolean hasYilouFromWF(Context mContext, int cpId, AbsWanfa wanfa) {
        return hasYilou(cpId)
                && wanfa.getType() != CaipiaoConst.WF_DXDS// 大小单双不做遗漏
                && wanfa.getType() != CaipiaoConst.WF_LIAN2ZHX//连2直选
                && wanfa.getType() != CaipiaoConst.WF_QIAN3ZHIX;// 大小单双不做遗漏
    }

    public static boolean hasYilou(int cpId) {
        return false;
//		return (cpId == CaipiaoConst.ID_FUCAI3D
//				|| cpId == CaipiaoConst.ID_LAOSHISHICAI
//				|| cpId == CaipiaoConst.ID_PAILIE3
//				||cpId == CaipiaoConst.ID_PAILIE5
//				|| cpId == CaipiaoConst.ID_LAO11XUAN5
//				|| cpId == CaipiaoConst.ID_XIN11XUAN5
//				||cpId==CaipiaoConst.ID_XINSHISHICAI
//				||cpId == CaipiaoConst.ID_KUAILE10FEN
//				||cpId == CaipiaoConst.ID_LUCKYCAR
//		);
    }

    public static String getDanwei(Caipiao caipiao, int indexOfGroup) {
        int type = caipiao.getCurrentWanfa().getType();
        String s = null;
        switch (type) {
            case CaipiaoConst.WF_NORMAL_PAILIE:
                if (caipiao.getQianquMinCount() > 5) {
                    s = CaipiaoUtil.getDanwei(indexOfGroup);
                } else {
                    s = CaipiaoUtil.getDanwei2(/*caipiao.getQianquMinCount()
                            -*/ indexOfGroup /*- 1*/);
                }
                if (caipiao.getHouquMinCount() > 0
                        && indexOfGroup == caipiao.getQianquMinCount()) {
                    s = "特";// 特別号码
                }
                if (caipiao.getId() == CaipiaoConst.ID_TICAI6JIA1
                        && indexOfGroup == 6) {
                    s = "特";// 特別号码
                }
                break;
            case CaipiaoConst.WF_QIAN1:
            case CaipiaoConst.WF_DANTUO_REN1:
            case CaipiaoConst.WF_QIAN2:
            case CaipiaoConst.WF_QIAN3:
            case CaipiaoConst.WF_WX_TONGXUAN:
            case CaipiaoConst.WF_WX_ZHIXUAN:
            case CaipiaoConst.WF_REN1:
            case CaipiaoConst.WF_REN2:
                s = CaipiaoUtil.getDanwei2(/*caipiao.getQianquMinCount()
                        -*/ indexOfGroup/* - 1*/);
                break;
            case CaipiaoConst.WF_HOU1:
                s = CaipiaoUtil.getDanwei2(0);
                break;
            case CaipiaoConst.WF_HOU2:
            case CaipiaoConst.WF_EX_ZHIXUAN:
            case CaipiaoConst.WF_DXDS:// 大小单双，后2位


                    s = CaipiaoUtil.getDanwei2(indexOfGroup);

                break;
            case CaipiaoConst.WF_SX_ZHIXUAN:

            case CaipiaoConst.WF_SIXING_ZHIXUAN:
//                if (indexOfGroup == 0) {
                    s = CaipiaoUtil.getDanwei2(indexOfGroup);
//                } else if (indexOfGroup == 1) {
//                    s = CaipiaoUtil.getDanwei2(2);
//                } else if (indexOfGroup == 2) {
//                    s = CaipiaoUtil.getDanwei2(1);
//                } else {
//                    s = CaipiaoUtil.getDanwei2(0);
//                }
                break;
            case CaipiaoConst.WF_YX_ZHIXUAN:
                s = CaipiaoUtil.getDanwei2(0);
                break;
        }
        return s;
    }

    public static LinearLayout getOneBtn(String text, int bgs, int colors,
                                         int previewBg, List<TouzhuButton> btnList, LayoutInflater mInflater, final AbsWanfa wanfa,
                                         Context mContext, Caipiao caipiao, String textstr) {

        int btnLayout = colors == R.color.qianqubtnselector ? R.layout.new_touzhubutton
                : R.layout.new_touzhubutton2;
        // colors在代码里设置没用，要在布局里写，
        LinearLayout linearlayout = (LinearLayout) mInflater.inflate(btnLayout,
                null);
        TouzhuButton btn = (TouzhuButton) linearlayout.findViewById(R.id.btn);
        if (text == null) {
            btn.setText(" ");
            btn.setVisibility(View.INVISIBLE);
        } else {
            btn.setText(text);
            btn.setPreviewBg(previewBg);
        }

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View btn) {
                wanfa.check((TouzhuButton) btn);
            }
        });
        btn.setGravity(Gravity.CENTER);
        if (text != null && textstr.indexOf(text) != -1)
            btn.setSelected(true);
        else
            btn.setSelected(false);

        btn.setBackgroundResource(CaipiaoUtil.getBallBg(bgs, getOneRowMax(mContext, caipiao)));
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        if (text != null) {// text == null时，几个按钮是为了占位
            btnList.add(btn);
        }
        return linearlayout;
    }

    public static int getOneRowMax(Context mContext, Caipiao caipiao) {
        // 显示遗漏时，一排显示10个。
        if (showYilou(mContext, caipiao.getId(), caipiao.getCurrentWanfa())) {
            if (caipiao.getId() == CaipiaoConst.ID_LAO11XUAN5
                    || caipiao.getId() == CaipiaoConst.ID_XIN11XUAN5 || caipiao.getId() == CaipiaoConst.ID_11XUAN5) {
                return 8;
            }
            return PAILIE_ONE_ROW_MAX;
        }

        int type = caipiao.getCurrentWanfa().getType();
        int n = 0;
        switch (type) {
            case CaipiaoConst.WF_NORMAL_PAILIE:
            case CaipiaoConst.WF_WX_TONGXUAN:
            case CaipiaoConst.WF_WX_ZHIXUAN:
            case CaipiaoConst.WF_REN1:
            case CaipiaoConst.WF_REN2:
                if (caipiao.getQianquMinCount()
                        + caipiao.getHouquMinCount() >= 5) {
                    n = PAILIE_ONE_ROW_MAX;
                    // n = PAILIE_ONE_ROW_MAX2;
                } else {
                    n = PAILIE_ONE_ROW_MAX2;
                }
                break;
            case CaipiaoConst.WF_QIAN2_ZUXUAN:
            case CaipiaoConst.WF_QIAN3_ZUXUAN:
            case CaipiaoConst.WF_ZU3:
            case CaipiaoConst.WF_ZU6:
            case CaipiaoConst.WF_SX_ZU3:// 三星组3
            case CaipiaoConst.WF_SX_ZU6:// 三星组6
            case CaipiaoConst.WF_EX_ZUXUAN:// 二星组选
                n = ZUXUAN_ONE_ROW_MAX;
                break;
            case CaipiaoConst.WF_HOU1:
            case CaipiaoConst.WF_HOU2:
            case CaipiaoConst.WF_QIAN1:
            case CaipiaoConst.WF_DANTUO_REN1:
            case CaipiaoConst.WF_QIAN2:
            case CaipiaoConst.WF_QIAN3:
                n = QIANNHOUN_ONE_ROW_MAX;
                break;
            case CaipiaoConst.WF_DANTUO:
            case CaipiaoConst.WF_DALETOUSHENGXIAO:
            case CaipiaoConst.WF_NORMAL_LETOU:
                n = LETOU_ONE_ROW_MAX;
                break;
            default:
                if (getDanwei(caipiao, 0) == null) {
                    n = PAILIE_ONE_ROW_MAX2;
                } else {
                    n = PAILIE_ONE_ROW_MAX2;
                }
                break;
        }
        return n;
    }

    public static String listToString(ArrayList<String> list) {
        String str = "";
        //System.out.println("listsize:"+list.size());
        for (int i = 0; i < list.size(); i++) {
            //System.out.println(list.get(i));
            if (i != list.size() - 1)
                str += list.get(i) + ",";
            else
                str += list.get(i);
        }
        //System.out.println(str);
        return str;
    }

    public static ArrayList<String> getListFromStr(String[] str) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < str.length; i++) {
            list.add(str[i]);
        }
        //System.out.println("init:"+list.size());
        return list;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    //计算下一日的日期
    public static String getSpecifiedDayAfter(String specifiedDay, int num) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("MMdd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + num);

        String dayAfter = new SimpleDateFormat("MMdd").format(c.getTime());
        return dayAfter;
    }

    //计算每个彩种距离当前期的剩余时间
    public static long getCaiPiaoNextTime(int id, int j) {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int week = c.get(Calendar.DAY_OF_WEEK);
        long time = 0;
        int iHour = 0;
        int iMinute = 0;
        String name = "";
        switch (id) {
            case 0:      ///双色球2,4,7   19:45
                name = "双色球";
                iHour = (19 * 60 + 45 - j) / 60;
                iMinute = (19 * 60 + 45 - j) % 60;
                switch (week) {
                    case 1:
                    case 3:
                        if (hour < iHour)
                            time = iMinute * 60 * 1000 + (iHour - hour) * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        else if (hour == iHour && minute < iMinute)
                            time = (iMinute - minute) * 60 * 1000 - second * 1000;
                        else
                            time = (24 + iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 2:
                    case 4:
                    case 7:
                        time = (24 + iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 5:
                        if (hour < iHour)
                            time = iMinute * 60 * 1000 + (iHour - hour) * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        else if (hour == iHour && minute < iMinute)
                            time = (iMinute - minute) * 60 * 1000 - second * 1000;
                        else
                            time = (24 * 2 + iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 6:
                        time = (24 * 2 + iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                }
                break;
            case 1:      ///福彩3D
            case 5:      ///排列3/5                 每天19:50
                if (id == 1)
                    name = "福彩3D";
                else
                    name = "排列3/5";
                iHour = (19 * 60 + 50 - j) / 60;
                iMinute = (19 * 60 + 50 - j) % 60;
                if (hour < iHour)
                    time = iMinute * 60 * 1000 + (iHour - hour) * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                else if (hour == iHour && minute < iMinute)
                    time = (iMinute - minute) * 60 * 1000 - second * 1000;
                else
                    time = (iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                break;
            case 2:      ///七乐彩        1,3,5  19:45
                name = "七乐彩";
                iHour = (19 * 60 + 45 - j) / 60;
                iMinute = (19 * 60 + 45 - j) % 60;
                switch (week) {
                    case 2:
                    case 4:
                        if (hour < iHour)
                            time = iMinute * 60 * 1000 + (iHour - hour) * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        else if (hour == iHour && minute < iMinute)
                            time = (iMinute - minute) * 60 * 1000 - second * 1000;
                        else
                            time = (24 + iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 6:
                        if (hour < iHour)
                            time = iMinute * 60 * 1000 + (iHour - hour) * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        else if (hour == iHour && minute < iMinute)
                            time = (iMinute - minute) * 60 * 1000 - second * 1000;
                        else
                            time = (24 * 2 + iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 1:
                    case 3:
                    case 5:
                        time = (24 + iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;

                    case 7:
                        time = (24 * 2 + iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                }
                break;
            case 3:      ///15选5        每天18:00
                name = "15选5";
                iHour = (18 * 60 - j) / 60;
                iMinute = (18 * 60 - j) % 60;
                if (hour < iHour)
                    time = (iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                else if (hour == iHour && minute < iMinute)
                    time = (iMinute - minute) * 60 * 1000 - second * 1000;
                else
                    time = (iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                break;
            case 4:      ///大乐透       1,3,6   19:45
                name = "大乐透";
                iHour = (19 * 60 + 45 - j) / 60;
                iMinute = (19 * 60 + 45 - j) % 60;
                switch (week) {
                    case 2:
                    case 7:
                        if (hour < iHour)
                            time = iMinute * 60 * 1000 + (iHour - hour) * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        else if (hour == iHour && minute < iMinute)
                            time = (iMinute - minute) * 60 * 1000 - second * 1000;
                        else
                            time = (24 + iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 4:
                        if (hour < iHour)
                            time = iMinute * 60 * 1000 + (iHour - hour) * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        else if (hour == iHour && minute < iMinute)
                            time = (iMinute - minute) * 60 * 1000 - second * 1000;
                        else
                            time = (24 * 2 + iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 1:
                    case 3:
                    case 6:
                        time = (24 + iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;

                    case 5:
                        time = (24 * 2 + iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                }
                break;

            case 6:      ///七星彩      2,5,7    19:45
                name = "七星彩 ";
                iHour = (19 * 60 + 45 - j) / 60;
                iMinute = (19 * 60 + 45 - j) % 60;
                switch (week) {
                    case 1:
                    case 6:
                        if (hour < iHour)
                            time = iMinute * 60 * 1000 + (iHour - hour) * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        else if (hour == iHour && minute < iMinute)
                            time = (iMinute - minute) * 60 * 1000 - second * 1000;
                        else
                            time = (24 + iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 3:
                        if (hour < iHour)
                            time = iMinute * 60 * 1000 + (iHour - hour) * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        else if (hour == iHour && minute < iMinute)
                            time = (iMinute - minute) * 60 * 1000 - second * 1000;
                        else
                            time = (24 * 2 + iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 2:
                    case 5:
                    case 7:
                        time = (24 + iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;

                    case 4:
                        time = (24 * 2 + iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                }
                break;
            case 7:      ///体彩6+1   2,5,7 18:00
                name = "体彩6+1";
                iHour = (18 * 60 - j) / 60;
                iMinute = (18 * 60 - j) % 60;
                switch (week) {
                    case 1:
                    case 6:
                        if (hour < iHour)
                            time = (iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        else if (hour == iHour && minute < iMinute)
                            time = (iMinute - minute) * 60 * 1000 - second * 1000;
                        else
                            time = (24 + iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 3:
                        if (hour < iHour)
                            time = (iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        else if (hour == iHour && minute < iMinute)
                            time = (iMinute - minute) * 60 * 1000 - second * 1000;
                        else
                            time = (24 * 2 + iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                    case 2:
                    case 5:
                    case 7:
                        time = (24 + iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;

                    case 4:
                        time = (24 * 2 + iHour - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                        break;
                }
                break;
            case 8:      ///体彩20选5     每天18:30
                name = "体彩20选5";
                iHour = (18 * 60 + 30 - j) / 60;
                iMinute = (18 * 60 + 30 - j) % 60;
                if (hour < iHour)
                    time = iMinute * 60 * 1000 + (iHour - hour) * 60 * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                else if (hour == iHour && minute < iMinute)
                    time = (iMinute - minute) * 60 * 1000 - second * 1000;
                else
                    time = (iHour + 24 - hour) * 60 * 60 * 1000 + iMinute * 60 * 1000 - minute * 60 * 1000 - second * 1000;
                break;
        }
        caiPiaoNextTimeMap.put(name, time);
        return time;
    }

    public static HashMap<String, Long> caiPiaoNextTimeMap = new HashMap<String, Long>();
    public static String context = "";

    public static String getContext() {
        return context.substring(0, context.length() - 1);
    }

    @SuppressWarnings("rawtypes")
    public static long getNotificationTime(String s, int jiezhi) {
        long internal = 0;
        long temp = 0;
        String[] str = s.split(",");
        caiPiaoNextTimeMap.clear();
        for (int i = 0; i < str.length; i++) {
            if (Boolean.parseBoolean(str[i])) {
                temp = getCaiPiaoNextTime(i, jiezhi);
                if (internal == 0)
                    internal = temp;
                else if (internal > temp)
                    internal = temp;
            }
        }
        Iterator<Entry<String, Long>> iter = caiPiaoNextTimeMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next();
            if ((Long) entry.getValue() == internal)
                context += entry.getKey() + ",";
        }
        return internal;
    }

    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int getBgStates(boolean isQianqu, int oneRowMax) {

        if (isQianqu) {

            return R.drawable.new_qianqu_states;
        } else {

            return R.drawable.new_houqu_states;
        }
    }

    //获取奖期的数字字符串
    public static String getStrjiangqi(Caipiao cp, String str) {
        if (cp.isKuaikai() && cp.getId() != CaipiaoConst.ID_LAOSHISHICAI) {
            return str.substring(str.length() - 2);
        } else
            return str.substring(str.length() - 3);
    }

    /**
     * 创建桌面快捷方式
     **/
    public static void createShortCut(Activity act, int iconResId,
                                      int appnameResId) {


    }

    /**
     * 快捷方式删除的action
     */
    private final static String SHORTCUT_DEL_ACTION = "com.android.launcher.action.UNINSTALL_SHORTCUT";
    /**
     * 读取数据库需要的权限
     */
    private final static String READ_SETTINGS_PERMISSION = "com.android.launcher.permission.READ_SETTINGS";

    /**
     * 判断桌面上是否有快捷方式，调用此方法需要添加权限
     * <uses-permission android:name="com.android.launcher.permission.READ_SETTINGS" />
     *
     * @param context
     * @return
     * @throws NameNotFoundException
     */
    public static boolean hasShortcut(Context context) {
        String AUTHORITY = getAuthorityFromPermission(context, READ_SETTINGS_PERMISSION);

        if (AUTHORITY == null) {
            return false;
        }
        Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
        String appName = null;
        try {
            appName = obtatinAppName(context);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Cursor c = context.getContentResolver().query(CONTENT_URI, new String[]{"title"}, "title=?", new String[]{appName}, null);
            if (c != null && c.getCount() > 0) {
                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
            return true;
        }
        return false;
    }

    /**
     * 获取应用的名称
     *
     * @param context
     * @return
     * @throws NameNotFoundException
     */
    private static String obtatinAppName(Context context) throws NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getApplicationLabel(packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)).toString();
    }

    /**
     * android系统桌面的基本信息由一个launcher.db的Sqlite数据库管理，里面有三张表
     * 其中一张表就是favorites。这个db文件一般放在data/data/com.android.launcher(launcher2)文件的databases下
     * 但是对于不同的rom会放在不同的地方
     * 例如MIUI放在data/data/com.miui.home/databases下面
     * htc放在data/data/com.htc.launcher/databases下面
     *
     * @param context
     * @param permission 读取设置的权限  READ_SETTINGS_PERMISSION
     * @return
     */
    private static String getAuthorityFromPermission(Context context, String permission) {
        if (TextUtils.isEmpty(permission)) {
            return null;
        }
        List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
        if (packs == null) {
            return null;
        }
        for (PackageInfo pack : packs) {
            ProviderInfo[] providers = pack.providers;
            if (providers != null) {
                for (ProviderInfo provider : providers) {
                    if (permission.equals(provider.readPermission) || permission.equals(provider.writePermission)) {
                        return provider.authority;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 删除桌面上的快捷方式，需要添加权限
     * <uses-permission android:name="com.android.launcher.permission.UNINSTALL_SHORTCUT" />
     *
     * @param context
     */
    public static void delShortcut(Context context, Activity activity) {
        Intent shortcut = new Intent(SHORTCUT_DEL_ACTION);
        // 获取当前应用名称
        String appName = null;
        try {
            appName = obtatinAppName(context);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        // 快捷方式名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_LAUNCHER).setClassName(context.getPackageName(), context.getClass().getName()));
        context.sendBroadcast(shortcut);
    }

    @NonNull
    public static String format_money(double v) {

        BigDecimal b = new BigDecimal(v);
        double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return String.valueOf(f1);
    }

    public static boolean isKySj(int id) {
        return id == CaipiaoConst.ID_GUANGXIKUAI3 || id == CaipiaoConst.ID_ANHUIKUAI3 || id == CaipiaoConst.ID_NEWKUAI3 || id == CaipiaoConst.ID_GVZBKUAI3 || id == CaipiaoConst.ID_FUJMKUAI3;
    }

    public static boolean is11xr5(int id) {
        return id == CaipiaoConst.ID_FUJM11XUAN5 || id == CaipiaoConst.ID_11XUAN5 || id == CaipiaoConst.ID_LAO11XUAN5 || id == CaipiaoConst.ID_XIN11XUAN5 || id == CaipiaoConst.ID_GVZB11XUAN5;
    }

    public  static  boolean isRenOne(int wanfaType){
        return  wanfaType==CaipiaoConst.WF_DANTUO_REN1||wanfaType==CaipiaoConst.WF_QIAN1;

    }


}
