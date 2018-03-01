package com.chengyi.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.chengyi.R;

import java.io.InputStream;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class CaipiaoConst {


    public final static int TYPE_NUM = 2;
    public final static int TYPE_MATCH = 1;
    public final static int TYPE_QUICK = 0;

    public static final int PopWindowStyle = R.style.PopWindowAnimation;
    public static final String AUTO_DIALOG_PRE = "auto_dialog_pre";// 友盟配置的某个页面的弹窗口
    public static final String LISTTYPE = "0";
    public static final String APPVERSION = "1";
    public static final String REQUESTTYPE = "4";

    public static final int ID_FOURGOAL = 10042;
    public static final int FOURREQUEST = 100;
    public static final int ID_FUJM11XUAN5 = 10084;
    public static final int FUJM_WF_REN1 = 10000;
    public static final int ID_GVZBKUAI3 = 10087;
    public static final int ID_GVZB11XUAN5 = 10086;
    public static final int ID_FUJMKUAI3 = 10085;

    // 商户包名

    public static String PHONE = "400-666-7575";
//    public static final String VERSION = "1.2.9";
//    public static final int VERSION_ID = 33;// 版本id

    public static final String VERSION = "1.0.0";
    public static final int VERSION_ID = 2;// 版本id

    public static String BASE_URL = IP.IP;


    public static String SHUZI_ZSR = "^\\+?[1-9][0-9]*$";// 数字正则表达式，正整数

    public static String FBallBQCSTR = "胜胜,胜平,胜负,平胜,平平,平负,负胜,负平,负负";
    public static final int WF_NORMAL_LETOU = 0;// 普通乐透型玩法，如双色球

    public static final int WF_NORMAL_PAILIE = 1;// 普通全排列玩法

    public static final int WF_ZU3 = 2;// 组3

    public static final int WF_ZU6 = 3;// 组6

    public static final int WF_QIAN2 = 4;// 前2

    public static final int WF_QIAN1 = 5;// 前1

    public static final int WF_HOU2 = 6;// 后2

    public static final int WF_HOU1 = 7;// 后1

    public static final int WF_SX_ZU3 = 8;// 三星组三

    public static final int WF_SX_ZU6 = 9;// 三星组六

    public static final int WF_SX_ZHIXUAN = 10;// 三星直选,竞猜后3位

    public static final int WF_WX_ZHIXUAN = 11;// 五星直选

    public static final int WF_WX_TONGXUAN = 12;// 五星通选

    public static final int WF_YX_ZHIXUAN = 13;// 竞猜后1位

    public static final int WF_EX_ZHIXUAN = 14;// 竞猜后2位

    public static final int WF_EX_ZUXUAN = 15;// 竞猜后2位，顺序不限

    public static final int WF_DXDS = 16;// 大小单双

    public static final int WF_QIAN2_ZUXUAN = 17;// 前2组选，顺序不限,跟组3一样

    public static final int WF_QIAN3_ZUXUAN = 18;// 前3组选，顺序不限，跟组6一样

    public static final int WF_REN2_2 = 19;// 任2中2

    public static final int WF_REN3_3 = 20;// 任3中3

    public static final int WF_REN4_4 = 21;// 任4中4

    public static final int WF_REN5_5 = 22;// 任5中5

    public static final int WF_REN6_5 = 23;// 任6中5

    public static final int WF_REN7_5 = 24;// 任7中5

    public static final int WF_REN8_5 = 25;// 任8中5

    public static final int WF_REN1 = 26;// 任1(与开奖号码的任意一位按位相符)

    public static final int WF_REN2 = 27;// 任2(与开奖号码的任意2位按位相符)

    public static final int WF_SIXING_ZHIXUAN = 28;// 四星直选

    public static final int WF_QIAN3 = 29;// 前3

    public static final int WF_DANTUO = 30;// 胆拖

    public static final int WF_DANTUO_ZU3 = 31;// 胆拖组3

    public static final int WF_DANTUO_ZU6 = 32;// 胆拖组6
    public static final int WF_ZU3_SINGLE = 34;// 组三单式
    //新快三玩法
    public static final int WF_NOSAME_THREE = 35;// 三不同号
    public static final int WF_NOSAME_TWO = 36;// 二不同号
    public static final int WF_HEZHI = 38;// 和值
    public static final int WF_RENYI = 37;// /任意
    public static final int WF_DUIZI = 39;// 对子
    public static final int WF_SHUNZI = 40;// 顺子
    public static final int WF_BAOZI = 41;// 豹子
    ///快乐十分玩法列表
    public static final int WF_DANTUO_LIAN2ZX = 102;// 胆拖连2组选
    public static final int WF_DANTUO_LIAN2ZHX = 103;// 胆拖连2直选
    public static final int WF_DANTUO_QIAN3ZX = 104;// 胆拖前3
    public static final int WF_DANTUO_QIAN3ZHIX = 105;// 胆拖前三直选
    public static final int WF_DANTUO_REN2 = 106;// 胆拖任选2
    public static final int WF_DANTUO_REN3 = 107;// 胆拖任选3
    public static final int WF_DANTUO_REN4 = 108;// 胆拖任选4
    public static final int WF_DANTUO_REN5 = 109;// 胆拖任选5
    public static final int WF_DANTUO_REN6 = 110;// 胆拖任选6
    public static final int WF_DANTUO_REN7 = 111;// 胆拖任选7
    public static final int WF_DANTUO_REN8 = 112;// 胆拖任选8

    public static final int WF_DANTUO_REN1 = -1;// 胆拖任选1  与普通任1  同


    public static final int WF_DANTUO_SX_ZHIXUAN = 115;// 胆拖三星直选
    public static final int WF_DANTUO_EX_ZUXUAN = 116;// 胆拖二星组选
    public static final int WF_LIAN2ZX = 42;// 复式连2
    public static final int WF_LIAN2ZHX = 43;// 复式连2直选
    //	public static final int WF_QIAN3ZX = 44;// 复式前三
    public static final int WF_QIAN3ZHIX = 45;// 复式前三直选
    public static final int WF_REN3 = 46;// 复式任选3
    public static final int WF_REN4 = 47;// 复式任选4
    public static final int WF_REN5 = 48;// 复式任选5
    public static final int WF_DAXIAOJQ = 50;// 大小奇偶

    public static final int WF_DALETOUSHENGXIAO = 33;// 大乐透生肖

    public static final int WF_ZUCAI_RQ = 99;// 足彩让球胜平负

    public static final int WF_ZUCAI_SPF = 100;// 足彩胜平负

    public static final int WF_ZUCAI_ZJQ = 101;// 足彩总进球

    public static final int WF_ZUCAI_HUNHE = 98;// 足彩混合投注
    public static final int WF_ZUCAI_BANQUANCHANG = 97;// 足彩半全场
    public static final int WF_ZUCAI_BIFEN = 96;// 足彩半全场
    public static final int MIX = 95;// 足彩半全场

    public static final int WF_SHIJIE_BEI = 113;// 世界杯竞猜

    public static final int WF_OUZHOU_BEI = 114;// 欧洲杯竞猜

    public static final int WF_SHIJIE_BEI_GUANYAJUN = 999;

    public static final int wf_basketball_winlose = 200;
    public static final int wf_basketball_winloserangfen = 201;
    public static final int wf_basketball_shenfencha = 202;
    public static final int wf_basketball_bigsmall = 203;
    public static final int wf_basketball_mix = 204;






    public static final int PRICE = 2;// 每注2元


    public static final String PADDING_2WEINUMBER = " ";// 2位数，同一位的数字
    public static final String PADDING_1WEINUMBER = "";// 1位数，同一位的数字
    public static final String PADDING_WEI = "-";// 位数split分割时注意要用\\|
    public static final String SPLIT_PADDING_WEI = "-";
    public static final String PADDING_QU = " + ";// 前后区,split分割时注意要用\\+
    public static final String PADDING_EMPTY = "*";// 空的
    public static final String SPLIT_PADDING_EMPTY = "\\*";// 空的
    public static final String MORE = "...";

    public static final int ID_SHUANGSEQIU = 10032;//双色球
    public static final int ID_FUCAI3D = 10025;//福彩3D
    public static final int ID_QILECAI = 10033;//七彩乐
    public static final int ID_15XUAN5 = 10035;
    public static final int ID_XINSHISHICAI = 10061; // 新时时彩(江西时时彩)
    public static final int ID_LAOSHISHICAI = 10038;// 老时时彩
    public static final int ID_LAO11XUAN5 = 10046;// 老11选5
    public static final int ID_XIN11XUAN5 = 10060;// 新11选5
    public static final int ID_11XUAN5 = 10066;//上海11选5
    public static final int ID_KUAILE10FEN = 10064;
    public static final int ID_LUCKYCAR = 10067;//幸运赛车
    public static final int ID_FUCAI15XUAN5 = 10035;

    public static final int ID_SIX = 10041;//6
    public static final int ID_DALETOU = 10026;
    public static final int ID_PAILIE3 = 10024;
    public static final int ID_PAILIE5 = ID_PAILIE3 * 10;// 排列五和排列3是一样的
    public static final int ID_QIXINGCAI = 10030;
    public static final int ID_QUANGUO22XUAN5 = 10029;
    public static final int ID_TICAI6JIA1 = 10027;
    public static final int ID_TICAI20XUAN5 = 10028;
    public static final int ID_NEWKUAI3 = 10065;//NEIMENG KUAI3
    public static final int ID_GUANGXIKUAI3 = 10074;//广西快3
    public static final int ID_ANHUIKUAI3 = 10073;//安徽快3
    public static final int ID_JINGCAIZUQIU = 10059;//竞彩足球
    public static final int ID_BASKETBALL = 10058;//竞彩篮球
    public static final int ID_BALL = 10057;//
    public static final int ID_RENXUAN9CHANG = 10040;//任选9场
    public static final int ID_SHENGFU14CHANG = 10039;//胜负14场
    public static final int ID_GUANYAJUN = 20000;//冠亚军竞猜

    public static final String TEXT_EMPTY = "--";
    public static final String QIANYIREDTOU = "前一红投";
    public static final String QIANYISHUTOU = "前一数投";
    public static final String KEY_INTENT_CAIPIAOID = "intent_cpid";
    public static final String KEY_INTENT_WF_TYPE = "intent_wftype";

    public static final int GENGDUO_MAX_NUM = 10;// 购买确认单等最多显示10条投注号码

    public static final int DELAY = 0;

    public static final String[] FUCAI3D_YL = {
            IP.YILOU + "/call/client_miss.jsp?lid=10001&type=1&location=1",// 直选
            // 百
            IP.YILOU + "/call/client_miss.jsp?lid=10001&type=1&location=2",// 直选
            // 十
            IP.YILOU + "/call/client_miss.jsp?lid=10001&type=1&location=3",// 直选
            // 个
            IP.YILOU + "/call/client_miss.jsp?lid=10001",// 组三, 组六
    };
    public static final String[] PAILIE2_YL = {
            IP.YILOU + "/call/client_miss.jsp?lid=10003&type=1&location=1",// 直选
            // 百
            IP.YILOU + "/call/client_miss.jsp?lid=10003&type=1&location=2",// 直选
            // 十
            IP.YILOU + "/call/client_miss.jsp?lid=10003&type=1&location=3",// 直选
            // 个
            IP.YILOU + "/call/client_miss.jsp?lid=10003"// 组三,组六
    };
    public static final String[] PAILIE5_YL = {
            IP.YILOU + "/call/client_miss.jsp?lid=10004&type=1&location=1",// 直选
            // 百
            IP.YILOU + "/call/client_miss.jsp?lid=10004&type=1&location=2",// 直选
            // 十
            IP.YILOU + "/call/client_miss.jsp?lid=10004&type=1&location=3",// 直选
            // 个
            IP.YILOU + "/call/client_miss.jsp?lid=10004&type=1&location=4",
            IP.YILOU + "/call/client_miss.jsp?lid=10004&type=1&location=5"// 组三,组六
    };
    public static final String[] LAOSHISHICAI_YL = {
            IP.YILOU + "/call/client_miss.jsp?lid=10089&type=1&location=1",
            IP.YILOU + "/call/client_miss.jsp?lid=10089&type=1&location=2",
            IP.YILOU + "/call/client_miss.jsp?lid=10089&type=1&location=3",
            IP.YILOU + "/call/client_miss.jsp?lid=10089&type=1&location=4",
            IP.YILOU + "/call/client_miss.jsp?lid=10089&type=1&location=5",//五星
            IP.YILOU + "/call/client_miss.jsp?lid=10093&type=1&location=1",
            IP.YILOU + "/call/client_miss.jsp?lid=10093&type=1&location=2",
            IP.YILOU + "/call/client_miss.jsp?lid=10093&type=1&location=3",//三星直选
            IP.YILOU + "/call/client_miss.jsp?lid=10093",//三星组选
            IP.YILOU + "/call/client_miss.jsp?lid=10093&type=1&location=2",
            IP.YILOU + "/call/client_miss.jsp?lid=10093&type=1&location=3",//二星直选
            IP.YILOU + "/call/client_miss.jsp?lid=10095&type=3",//二星组选
            IP.YILOU + "/call/client_miss.jsp?lid=10089&type=1&location=5"//一星
    };
    public static final String[] XINSHISHICAI_YL = {
            IP.YILOU + "/call/client_miss.jsp?lid=10134&type=1&location=1",// 五星通选/五星/任选一/任选二
            // 万
            IP.YILOU + "/call/client_miss.jsp?lid=10134&type=1&location=2",// 五星通选/五星/任选一/任选二
            // 千
            IP.YILOU + "/call/client_miss.jsp?lid=10134&type=1&location=3",// 五星通选/五星/任选一/任选二
            // 百
            IP.YILOU + "/call/client_miss.jsp?lid=10134&type=1&location=4",// 五星通选/五星/任选一/任选二
            // 十
            IP.YILOU + "/call/client_miss.jsp?lid=10134&type=1&location=5",// 五星通选/五星/任选一/任选二
            // 个

            IP.YILOU + "/call/client_miss.jsp?lid=10135&type=1&location=1",// 四星
            // 千
            IP.YILOU + "/call/client_miss.jsp?lid=10135&type=1&location=2",// 四星
            // 百
            IP.YILOU + "/call/client_miss.jsp?lid=10135&type=1&location=3",// 四星
            // 十
            IP.YILOU + "/call/client_miss.jsp?lid=10135&type=1&location=4",// 四星
            // 个

            IP.YILOU + "/call/client_miss.jsp?lid=10136&type=1&location=1",// 三星直选
            // 百
            IP.YILOU + "/call/client_miss.jsp?lid=10136&type=1&location=2",// 三星直选
            // 十
            IP.YILOU + "/call/client_miss.jsp?lid=10136&type=1&location=3",// 三星直选
            // 个

            IP.YILOU + "/call/client_miss.jsp?lid=10136",// 三星组三/三星组六

            IP.YILOU + "/call/client_miss.jsp?lid=10136&type=1&location=2",// 二星直选
            // 十
            IP.YILOU + "/call/client_miss.jsp?lid=10136&type=1&location=3",// 二星直选
            // 个

            IP.YILOU + "/call/client_miss.jsp?lid=10137",// 二星组选

            IP.YILOU + "/call/client_miss.jsp?lid=10134&type=1&location=5",// 一星
    };

    public static final String[] LAO11XUAN5_YL = {
            IP.YILOU + "/call/client_miss.jsp?lid=10105&type=1&location=1",// 前一
            IP.YILOU + "/call/client_miss.jsp?lid=10103",// 任选二,三,四,五,六,七,八

            IP.YILOU + "/call/client_miss.jsp?lid=10106&type=1&location=1",// 前二直选
            // 万
            IP.YILOU + "/call/client_miss.jsp?lid=10106&type=1&location=2",// 前二直选
            // 千

            IP.YILOU + "/call/client_miss.jsp?lid=10105&type=1&location=1",// 前三直选
            // 万
            IP.YILOU + "/call/client_miss.jsp?lid=10105&type=1&location=2",// 前三直选
            // 千
            IP.YILOU + "/call/client_miss.jsp?lid=10105&type=1&location=3",// 前三直选
            // 十

            IP.YILOU + "/call/client_miss.jsp?lid=10106",// 前二组选

            IP.YILOU + "/call/client_miss.jsp?lid=10107",// 前三组选
    };

    public static final String[] XIN11XUAN5_YL = {
            IP.YILOU + "/call/client_miss.jsp?lid=10130&type=1&location=1",// 前一
            IP.YILOU + "/call/client_miss.jsp?lid=10129",// 任选二,三,四,五,六,七,八

            IP.YILOU + "/call/client_miss.jsp?lid=10131&type=1&location=1",// 前二直选
            // 万
            IP.YILOU + "/call/client_miss.jsp?lid=10131&type=1&location=2",// 前二直选
            // 千

            IP.YILOU + "/call/client_miss.jsp?lid=10130&type=1&location=1",// 前三直选
            // 万
            IP.YILOU + "/call/client_miss.jsp?lid=10130&type=1&location=2",// 前三直选
            // 千
            IP.YILOU + "/call/client_miss.jsp?lid=10130&type=1&location=3",// 前三直选
            //
            IP.YILOU + "/call/client_miss.jsp?lid=10131",// 前二组选

            IP.YILOU + "/call/client_miss.jsp?lid=10130",// 前三组选
    };
    public static final String[] LuckyCar_YL = {
            IP.YILOU + "/call/client_miss.jsp?lid=10155&type=1&location=1",// 前一
            IP.YILOU + "/call/client_miss.jsp?lid=10155&type=1&location=2",// 前二
            IP.YILOU + "/call/client_miss.jsp?lid=10155&type=1&location=3",// 前三

            IP.YILOU + "/call/client_miss.jsp?lid=10157",//位置，组三

            IP.YILOU + "/call/client_miss.jsp?lid=10158",// 组二

    };
    public static final String[] KUAILE10FEN_YL = {
            IP.YILOU + "/call/client_miss.jsp?lid=10149&type=1&location=1",//前一数投
            IP.YILOU + "/call/client_miss.jsp?lid=10147",//连2组选
            IP.YILOU + "/call/client_miss.jsp?lid=10150",//连3组选
    };
    public static String CAPITAL_DETAIL_test = "user/capital_detail.htm";


    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static final String[] kuanSanStrUtils = {
            "奖金240元", "奖金80元", "奖金40元", "奖金25元",
            "奖金16元", "奖金12元", "奖金10元", "奖金9元",
            "奖金9元", "奖金10元", "奖金12元", "奖金16元",
            "奖金25元", "奖金40元", "奖金80元", "奖金240元"
    };
    public static final String[] strArray = new String[]{"小奇", "小偶", "大奇", "大偶"};
    public static final String[] kuanSanHeZhiUtils = {
            "大", "小", "奇", "偶", "全"
    };

    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inSampleSize = computeSampleSize(opt, -1, 128 * 128);  //计算出图片使用的inSampleSize
        opt.inJustDecodeBounds = false;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static boolean judgeyear(int year) {
        boolean leadyear;
        if (year % 4 == 0 && year % 100 != 0) {
            leadyear = true;
        } else if (year % 400 == 0) {
            leadyear = true;
        } else
            leadyear = false;
        return leadyear;
    }

    public static final String JOINHEMAI = BASE_URL + "lottery/scheme_join_detail.htm?clientUserSession=%s&schemeId=%d";

    public static boolean isSort(Context context) {

        return getFromPre(context,"sort");
    }

    public static boolean isHoDs(Context context) {

        return getFromPre(context,"isActivty");

    }
    public static String[] isShowLottery(Context context) {
        String ids=        AppUtil.getFromConfig(context, "showlotteryIds");

        return  ids.split(" ");

    }


    public static boolean isZixp(Context context) {

            return getFromPre(context,"isZixun");

    }


    private  static  boolean getFromPre(Context context,String key){

        String fromConfig = AppUtil.getFromConfig(context, key);
        if (fromConfig.equals("true")) {
            return true;
        } else {

            return false;
        }
    }

}