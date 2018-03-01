package com.chengyi.app.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.user.info.OrderSelectMode;
import com.igexin.sdk.PushManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class AppUtil {
    public static boolean isAppOnForeground(Activity activity) {

        ActivityManager activityManager = (ActivityManager) activity.getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = activity.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    public static String[][] getCity(Fragment fragment) {
        return getCity(fragment.getContext());
    }

    public static String[][] getCity(Context context) {
        return new String[][]{
                context.getResources().getStringArray(R.array.beijin_province_item),
                context.getResources().getStringArray(R.array.tianjin_province_item),
                context.getResources().getStringArray(R.array.heibei_province_item),
                context.getResources().getStringArray(R.array.shanxi1_province_item),
                context.getResources().getStringArray(R.array.neimenggu_province_item),
                context.getResources().getStringArray(R.array.liaoning_province_item),
                context.getResources().getStringArray(R.array.jilin_province_item),
                context.getResources().getStringArray(R.array.heilongjiang_province_item),
                context.getResources().getStringArray(R.array.shanghai_province_item),
                context.getResources().getStringArray(R.array.jiangsu_province_item),
                context.getResources().getStringArray(R.array.zhejiang_province_item),
                context.getResources().getStringArray(R.array.anhui_province_item),
                context.getResources().getStringArray(R.array.fujian_province_item),
                context.getResources().getStringArray(R.array.jiangxi_province_item),
                context.getResources().getStringArray(R.array.shandong_province_item),
                context.getResources().getStringArray(R.array.henan_province_item),
                context.getResources().getStringArray(R.array.hubei_province_item),
                context.getResources().getStringArray(R.array.hunan_province_item),
                context.getResources().getStringArray(R.array.guangdong_province_item),
                context.getResources().getStringArray(R.array.guangxi_province_item),
                context.getResources().getStringArray(R.array.hainan_province_item),
                context.getResources().getStringArray(R.array.chongqing_province_item),
                context.getResources().getStringArray(R.array.sichuan_province_item),
                context.getResources().getStringArray(R.array.guizhou_province_item),
                context.getResources().getStringArray(R.array.yunnan_province_item),
                context.getResources().getStringArray(R.array.xizang_province_item),
                context.getResources().getStringArray(R.array.shanxi2_province_item),
                context.getResources().getStringArray(R.array.gansu_province_item),
                context.getResources().getStringArray(R.array.qinghai_province_item),
                context.getResources().getStringArray(R.array.linxia_province_item),
                context.getResources().getStringArray(R.array.xinjiang_province_item),
                context.getResources().getStringArray(R.array.hongkong_province_item),
                context.getResources().getStringArray(R.array.aomen_province_item),
                context.getResources().getStringArray(R.array.taiwan_province_item),};
    }

    @NonNull
    public static Map<String, String> addParam(Map<String, String> params) {
        if (params == null) params = new HashMap<>();
        if (!params.containsKey("appVersion")) {
            params.put("appVersion", CaipiaoConst.APPVERSION);
        }
        if (!params.containsKey("version")) {
            params.put("version", CaipiaoConst.VERSION);
        }
        if (!params.containsKey("requestType")) {
            params.put("requestType", CaipiaoConst.REQUESTTYPE);
        }
        if (!params.containsKey("listType")) {
            params.put("listType", CaipiaoConst.LISTTYPE);
        }

        if (!params.containsKey("clientUserSession")) {
            params.put("clientUserSession", CaipiaoApplication.getInstance().getSession());
        }
        if (!params.containsKey("clientId")) {
            params.put("clientId", getCidByPush(CaipiaoApplication.getInstance()));
        }
        return params;
    }

    public static boolean getPerssion(Fragment f, String per) {
        return getPerssion(f.getActivity(), per);
    }

    public static boolean getPerssion(Activity context, String per) {
        if (ContextCompat.checkSelfPermission(context, per)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    per)) {
            } else {
                ActivityCompat.requestPermissions(context, new String[]{per}, 10);

            }
            return false;

        } else {
            return true;
        }
    }


    public static void startRotateAnim(ImageView imageView) {
        RotateAnimation animation = new RotateAnimation(-0, 180, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        animation.setDuration(500);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);

    }


    public static void startResRotateAnim(ImageView ivAnim) {
        RotateAnimation animation = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        animation.setDuration(500);
        animation.setFillBefore(true);
        animation.setRepeatMode(Animation.REVERSE);
        ivAnim.startAnimation(animation);
    }


    /**
     * 得到config 配置
     */
    public static String getFromConfig(Context context, String key) {

        try {
            Properties p = new Properties();
            InputStream open = context.getAssets().open("config.properties");
            p.load(open);
            return p.get(key).toString();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    public static boolean isHideWanfa(Caipiao currentCaipiao) {
        return currentCaipiao != null && (currentCaipiao.getId() == CaipiaoConst.ID_FUCAI15XUAN5 || currentCaipiao.getId() == CaipiaoConst.ID_XINSHISHICAI
                || currentCaipiao.getId() == CaipiaoConst.ID_ANHUIKUAI3
                || currentCaipiao.getId() == CaipiaoConst.ID_GUANGXIKUAI3
                || currentCaipiao.getId() == CaipiaoConst.ID_LUCKYCAR
                || currentCaipiao.getId() == CaipiaoConst.ID_FUJMKUAI3
                || currentCaipiao.getId() == CaipiaoConst.ID_GVZB11XUAN5
                || currentCaipiao.getId() == CaipiaoConst.ID_GUANGXIKUAI3
                || currentCaipiao.getId() == CaipiaoConst.ID_XINSHISHICAI
                || currentCaipiao.getId() == CaipiaoConst.ID_KUAILE10FEN
        );
    }


    public static boolean isHideZs(Caipiao currentCaipiao) {
        return true;
/*        return currentCaipiao != null && (
                currentCaipiao.getId() == CaipiaoConst.ID_FUCAI15XUAN5
                        || currentCaipiao.getId() == CaipiaoConst.ID_LUCKYCAR ||
                        currentCaipiao.getId() == CaipiaoConst.ID_XINSHISHICAI
                        || currentCaipiao.getId() == CaipiaoConst.ID_ANHUIKUAI3
                        || currentCaipiao.getId() == CaipiaoConst.ID_GUANGXIKUAI3
                        || currentCaipiao.getId() == CaipiaoConst.ID_FUJMKUAI3
                        || currentCaipiao.getId() == CaipiaoConst.ID_GVZB11XUAN5
                        || currentCaipiao.getId() == CaipiaoConst.ID_GUANGXIKUAI3
                        || currentCaipiao.getId() == CaipiaoConst.ID_XINSHISHICAI
                        || currentCaipiao.getId() == CaipiaoConst.ID_15XUAN5
                        || currentCaipiao.getId() == CaipiaoConst.ID_QIXINGCAI
                        || currentCaipiao.getId() == CaipiaoConst.ID_TICAI6JIA1
                        || currentCaipiao.getId() == CaipiaoConst.ID_TICAI20XUAN5
                        || currentCaipiao.getId() == CaipiaoConst.ID_QILECAI
                        || currentCaipiao.getId() == CaipiaoConst.ID_TICAI20XUAN5


        );*/
    }


    public static List<OrderSelectMode> resove(Context ctx, List<String> chooses, List<String> result, boolean f) {


        return resove(ctx, chooses, result, f, false);
    }

    /**
     * 转换为富文本
     */
    public static List<OrderSelectMode> resove(Context ctx, List<String> chooses, List<String> result, boolean f, boolean football) {


        List<OrderSelectMode> modes = new ArrayList<>();


        for (String s : chooses) {

            OrderSelectMode m = new OrderSelectMode();

            if (TextUtils.isEmpty(s)) {
                s = ctx.getString(R.string.no_match_result);
            }
            s = s.replaceAll(",", "");
            String tempS = "";
            if (s.indexOf("(") != -1) {
                tempS = s.substring(0, s.indexOf("("));
            } else {
                tempS = s;
            }
            if (f) {
                if (result.contains(s)) {
                    m.setSelect(true);
                }
            } else {
                if (result.contains(tempS.trim())) {
                    m.setSelect(true);
                }
            }


            if (ctx.getString(R.string.no_match_result).equals(s)) {
                m.setSelect(true);
            }
            if ("-1".equals(s)) {
                m.setSelect(true);
                s = ctx.getString(R.string.no_match_result);
            }

            m.setName(s);

            modes.add(m);


        }


        return modes;
    }


    public static SpannableString initRed(String number, String text) {
        number = number.replaceAll(",", "\t");


        List<Point> ps = new ArrayList<>();


        String[] split = number.split("\t");
        for (int i = 0; i < split.length; i++) {
            if (text.contains(split[i])) {

                Point p = new Point(number.indexOf(split[i]), number.indexOf(split[i]) + split[i].length());
                ps.add(p);

            }

        }


        SpannableString ss = new SpannableString(number);

        for (Point pp : ps) {


            ss.setSpan(new ForegroundColorSpan(CaipiaoApplication.getInstance().getResources().getColor(R.color.red)), pp.x, pp.y, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }


        return ss;
    }

    public static String resoveFootball(String trim) {


        return getFoot().get(Integer.parseInt(trim));
    }

    private static Map<Integer, String> getFoot() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "主负");
        map.put(1, "主平");
        map.put(3, "主胜");

        return map;
    }

    public static boolean isApkDebugable(Context context) {
//        try {
//            ApplicationInfo info= context.getApplicationInfo();
//            return (info.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        }
//        return false;
        return true;
    }

    public static String getCidByPush(Context context) {
        if (TextUtils.isEmpty(PushManager.getInstance().getClientid(context))) {
            return "";
        } else {
            return PushManager.getInstance().getClientid(context);
        }
    }


}
