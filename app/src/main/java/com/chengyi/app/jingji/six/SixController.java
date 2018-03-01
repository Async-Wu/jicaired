package com.chengyi.app.jingji.six;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishangfan on 2016/9/9.
 */
public class SixController {
    private static List<Integer> sixdelet = new ArrayList<>();


    public static List<Integer> getSixdelet() {
        if (sixdelet == null) sixdelet = new ArrayList<>();
        return sixdelet;
    }

    public static void clearDelete() {
        if (sixdelet != null)
            sixdelet.clear();
    }

    public static void setSixdelet(int j) {
        if (sixdelet != null&&!sixdelet.contains(j)) {
            sixdelet.add(j);
        }
    }
    private  static  boolean isClear;

    public static boolean isClear() {
        return isClear;
    }

    public static void setIsClear(boolean isClear) {
        SixController.isClear = isClear;
    }
}
