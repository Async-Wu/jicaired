package com.chengyi.app.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  GuoGuanType  {



    public  static  Map<String, int[]>  getGuoGuanType(){
        Map<String, int[]> guoGuantypeMap=new HashMap<>();

        guoGuantypeMap.put("1串1", new int[] { 1 });
        guoGuantypeMap.put("2串1", new int[] { 2 });
        guoGuantypeMap.put("3串1", new int[] { 3 });
        guoGuantypeMap.put("3串3", new int[] { 2 });
        guoGuantypeMap.put("3串4", new int[] { 2, 3 });
        guoGuantypeMap.put("4串1", new int[] { 4 });
        guoGuantypeMap.put("4串4", new int[] { 3 });
        guoGuantypeMap.put("4串5", new int[] { 3, 4 });
        guoGuantypeMap.put("4串6", new int[] { 2 });
        guoGuantypeMap.put("4串11", new int[] { 2, 3, 4 });
        guoGuantypeMap.put("5串1", new int[] { 5 });
        guoGuantypeMap.put("5串5", new int[] { 4 });
        guoGuantypeMap.put("5串6", new int[] { 4, 5 });
        guoGuantypeMap.put("5串10", new int[] { 2 });
        guoGuantypeMap.put("5串16", new int[] { 3, 4, 5 });
        guoGuantypeMap.put("5串20", new int[] { 2, 3 });
        guoGuantypeMap.put("5串26", new int[] { 2, 3, 4, 5 });
        guoGuantypeMap.put("6串1", new int[] { 6 });
        guoGuantypeMap.put("6串6", new int[] { 5 });
        guoGuantypeMap.put("6串7", new int[] { 5, 6 });
        guoGuantypeMap.put("6串15", new int[] { 2 });
        guoGuantypeMap.put("6串20", new int[] { 3 });
        guoGuantypeMap.put("6串22", new int[] { 4, 5, 6 });
        guoGuantypeMap.put("6串35", new int[] { 2, 3 });
        guoGuantypeMap.put("6串42", new int[] { 3, 4, 5, 6 });
        guoGuantypeMap.put("6串50", new int[] { 2, 3, 4 });
        guoGuantypeMap.put("6串57", new int[] { 2, 3, 4, 5, 6 });
        guoGuantypeMap.put("7串1", new int[] { 7 });
        guoGuantypeMap.put("7串7", new int[] { 6 });
        guoGuantypeMap.put("7串8", new int[] { 6, 7 });
        guoGuantypeMap.put("7串21", new int[] { 5 });
        guoGuantypeMap.put("7串35", new int[] { 4 });
        guoGuantypeMap.put("7串120", new int[] { 2, 3, 4, 5, 6, 7 });
        guoGuantypeMap.put("8串1", new int[] { 8 });
        guoGuantypeMap.put("8串8", new int[] { 7 });
        guoGuantypeMap.put("8串9", new int[] { 7, 8 });
        guoGuantypeMap.put("8串28", new int[] { 6 });
        guoGuantypeMap.put("8串56", new int[] { 5 });
        guoGuantypeMap.put("8串70", new int[] { 4 });
        guoGuantypeMap.put("8串247", new int[] { 2, 3, 4, 5, 6, 7, 8 });

        return  guoGuantypeMap;

    }




}
