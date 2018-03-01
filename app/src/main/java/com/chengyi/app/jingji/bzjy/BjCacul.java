package com.chengyi.app.jingji.bzjy;

import com.chengyi.app.util.L;

import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BjCacul {

    private static int total;

    public static String cacluBzjy(List<DataBean> been) {

        total = 0;


        for (int i = 0; i < been.size(); i++) {

            for (int j = 0; j < been.get(i).getMatches().size(); j++) {
                if (been.get(i).getMatches().get(j).ishad()) {
                    total++;
                }

            }
        }

        L.i("test", String.valueOf(total));

        return String.valueOf(total);
    }
}
