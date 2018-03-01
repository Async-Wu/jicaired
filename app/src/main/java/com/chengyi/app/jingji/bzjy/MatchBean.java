package com.chengyi.app.jingji.bzjy;

import android.text.TextUtils;
import com.chengyi.app.model.model.GameMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  MatchBean  extends GameMode {

    /**
     * id : 30399
     * matchId : 6070314
     * matchCode : 60703_14
     * issue : 60703
     * teamId : 14
     * hostName : 柏太阳神
     * guestName : 广岛三箭
     * leagueName : J联赛
     * matchTime : 2016-7-13 17:55:00
     * rate : 0.0
     * status : 0
     * createTime : 2016-7-12 10:23:41
     * updateTime : 2016-7-13 15:54:32
     * rqSpfStatus : 0
     * zjqStatus : 0
     * sxdsStatus : 0
     * bfStatus : 0
     * bqcStatus : 0
     * isEnd : false
     * sp : {"id":24575,"bjdcMatchId":30399,"matchCode":"60703_14","issue":60703,"teamId":"14","spType":0,"f01":13.54,"f02":13.58,"f03":34.81,"f04":134.61,"f12":8.67,"f13":15.63,"f14":86.02,"f23":32.54,"f24":77.65,"fother":39.71,"p00":14.75,"p11":8.28,"p22":22.79,"p33":85.1,"pother":301.3,"s10":12.67,"s20":15.73,"s30":71.09,"s40":241.47,"s21":12.29,"s31":34.07,"s41":171.47,"s32":54.85,"s42":132.38,"sother":156.36,"ff":3.8,"fp":12.23,"fs":46.55,"pf":5.7,"pp":7.21,"ps":10.57,"sf":33.64,"sp":17.15,"ss":7.28,"rqSheng":3.88,"rqPing":3.98,"rqFu":2.03,"t0":16.05,"t1":5.63,"t2":4.24,"t3":4.43,"t4":6.86,"t5":11.5,"t6":24.39,"t7":38.9,"shangD":2.79,"shangS":5.03,"xiaD":5.74,"xiaS":3.7,"status":0,"createTime":"2016-7-12 10:24:01","updateTime":"2016-7-13 16:17:30"}
     */


    /**
     * 是否选择其中一个的
     *
     * @return
     */
    public boolean ishad() {

        return getspf() || sp.bifen() || sp.bqc() || sp.ds() || sp.zjq();
    }

    public boolean getspf() {
        return isGust() || isVs() || isHost();
    }

    public int getspfCount() {
        int m = 0;
        if (isGust()) {
            m++;
        }
        if (isVs()) {
            m++;
        }
        if (isHost()) {
            m++;
        }

        return m;
    }


    public String getSpfShow() {
        String s = "";

        if (isHost()) {

            s = s + "胜:" + sp.getRqSheng() + "|";

        }

        if (isGust()) {
            s = s + "负:" + sp.getRqFu() + "|";
        }
        if (isVs()) {
            s = s + "平:" + sp.getRqPing() + "|";
        }
        if (!TextUtils.isEmpty(s))
            s = s.substring(0, s.length() - 1);

        return s;

    }

    public String getZjqShow() {
        String s = "";
        if (sp.isT1()) {
            s = s + "0:" + sp.getT0() + "|";
        }
        if (sp.isT2()) {
            s = s + "1:" + sp.getT1() + "|";
        }
        if (sp.isT3()) {
            s = s + "2:" + sp.getT2() + "|";
        }
        if (sp.isT4()) {
            s = s + "3:" + sp.getT3() + "|";
        }
        if (sp.isT5()) {
            s = s + "4:" + sp.getT4() + "|";
        }
        if (sp.isT6()) {
            s = s + "5:" + sp.getT5() + "|";
        }
        if (sp.isT7()) {
            s = s + "6:" + sp.getT6() + "|";
        }
        if (sp.isT8()) {
            s = s + "7:" + sp.getT7() + "|";
        }
        if (!TextUtils.isEmpty(s))
            s = s.substring(0, s.length() - 1);

        return s;

    }


    public String getDsShow() {
        String s = "";

        if (sp.isShangS()) {
            s = s + "上双:" + sp.getShangS() + "|";
        }
        if (sp.isShangD()) {
            s = s + "上单:" + sp.getShangD() + "|";
        }
        if (sp.isXiaS()) {
            s = s + "下双:" + sp.getXiaS() + "|";
        }
        if (sp.isXiaD()) {
            s = s + "下单:" + sp.getXiaD() + "|";

        }
        if (!TextUtils.isEmpty(s))
            s = s.substring(0, s.length() - 1);
        return s;
    }


    public String getBifenShow() {


        String s = "";

        /**
         * isP00 ||
         isP11 ||
         isP22 ||
         isP33 ||
         isPother;
         */


        if (sp.isP00()) {
            s = s + "0:0" + "\t";
        }

        if (sp.isP11()) {
            s = s + "1:1" + "\t";
        }
        if (sp.isP22()) {
            s = s + "2:2" + "\t";
        }
        if (sp.isP33()) {
            s = s + "3:3" + "\t";
        }
        if (sp.isPother()) {
            s = s + "平其他" + "\t";
        }


        /**
         *  isF01;
         isF02;
         isF03;
         isF04;
         isF12;
         isF13;
         isF14;
         isF23;
         isF24;
         isFother;
         */


        if (sp.isF01()) {
            s = s + "0:1" + "\t";
        }
        if (sp.isF02()) {
            s = s + "0:2" + "\t";
        }
        if (sp.isF03()) {
            s = s + "0:3" + "\t";
        }
        if (sp.isF04()) {
            s = s + "0:4" + "\t";
        }
        if (sp.isF12()) {
            s = s + "1:2" + "\t";
        }
        if (sp.isF13()) {
            s = s + "1:3" + "\t";
        }
        if (sp.isF14()) {
            s = s + "1:4" + "\t";
        }
        if (sp.isF23()) {
            s = s + "2:3" + "\t";
        }
        if (sp.isF24()) {
            s = s + "2:4" + "\t";
        }
        if (sp.isFother()) {
            s = s + "负其他" + "\t";
        }


/**
 * isS10 \t\t isS10 \t\t
 iss20 \t\t
 iss30 \t\t
 iss40 \t\t
 iss21 \t\t
 iss31 \t\t
 iss41 \t\t
 iss32 \t\t
 iss42 \t\t
 issother;
 */
        if (sp.isS10()) {
            s = s + "1:0" + "\t";
        }
        if (sp.iss20()) {
            s = s + "2:0" + "\t";
        }
        if (sp.iss30()) {
            s = s + "3:0" + "\t";
        }
        if (sp.iss40()) {
            s = s + "4:0" + "\t";
        }
        if (sp.iss21()) {
            s = s + "2:1" + "\t";
        }
        if (sp.iss31()) {
            s = s + "3:1" + "\t";
        }
        if (sp.iss41()) {
            s = s + "4:1" + "\t";
        }
        if (sp.iss32()) {
            s = s + "3:2" + "\t";
        }
        if (sp.iss42()) {
            s = s + "4:2" + "\t";
        }
        if (sp.issother()) {
            s = s + "胜其他" + "\t";
        }
        if (!TextUtils.isEmpty(s))
            s = s.substring(0, s.length() - 1);

        if (!TextUtils.isEmpty(s)){

            s="        "+s+"        ";
        }


        return s;
    }

    public String getBqcShow() {

/**
 * isFf \t\t
 isFp \t\t
 isFs \t\t
 isPf \t\t
 isPp \t\t
 isPs \t\t
 isSf \t\t
 isSp \t\t
 isSs;
 */
        String s = "";

        if (sp.isFf()) {
            s = s+"负负" + sp.getFf() + "\t";
        }
        if (sp.isPs()) {
            s = s+"平胜" + sp.getPs() + "\t";
        }

        if (sp.isFp()) {
            s = s+"负平" + sp.getFp() + "\t";
        }
        if (sp.isFs()) {
            s = s+"负胜" + sp.getFs() + "\t";
        }
        if (sp.isPf()) {
            s = s+"平负" + sp.getPf() + "\t";
        }
        if (sp.isPp()) {
            s = s+"平平" + sp.getPp() + "\t";
        }
        if (sp.isSf()) {
            s =s+ "胜负" + sp.getSf() + "\t";
        }
        if (sp.isSp()) {
            s = s+"胜平" + sp.getSp() + "\t";
        }
        if (sp.isSs()) {
            s = s+"胜胜" + sp.getSs() + "\t";
        }
        if (!TextUtils.isEmpty(s))
            s = s.substring(0, s.length() - 1);
        return s;
    }


    public int getZjqCount() {

        int num = 0;

        if (sp.isT1()) {
            num++;
        }
        if (sp.isT2()) {
            num++;
        }
        if (sp.isT3()) {
            num++;
        }
        if (sp.isT4()) {
            num++;
        }
        if (sp.isT5()) {
            num++;
        }
        if (sp.isT6()) {
            num++;
        }
        if (sp.isT7()) {
            num++;
        }
        if (sp.isT8()) {
            num++;
        }

        return num;
    }


    public int getDsCount() {

        int num = 0;

        if (sp.isXiaD()) {
            num++;
        }
        if (sp.isXiaS()) {
            num++;
        }
        if (sp.isShangD()) {
            num++;
        }
        if (sp.isXiaS()) {
            num++;
        }

        return num;
    }

    /**
     * isFf =false;
     * isFp =false;
     * isFs =false;
     * isPf =false;
     * isPp =false;
     * isPs =false;
     * isSf =false;
     * isSp =false;
     * isSs=false;
     *
     * @return
     */
    public int getbqcCount() {
        int num = 0;
        if (sp.isFf()) {
            num++;
        }
        if (sp.isFp()) {
            num++;
        }
        if (sp.isFs()) {
            num++;
        }
        if (sp.isPf()) {
            num++;
        }
        if (sp.isPp()) {
            num++;
        }
        if (sp.isPs()) {
            num++;
        }
        if (sp.isSf()) {
            num++;
        }
        if (sp.isSp()) {
            num++;
        }
        if (sp.isSs()) {
            num++;
        }


        return num;
    }

    public int getBfCount() {
        int num = 0;
        /**
         *  isP00 =false;
         isP11  =false;
         isP22  =false;
         isP33  =false;
         isPother =false;
         */
        if (sp.isP00()) {
            num++;
        }

        if (sp.isP11()) {
            num++;
        }
        if (sp.isP22()) {
            num++;
        }
        if (sp.isP33()) {
            num++;
        }
        if (sp.isPother()) {
            num++;
        }


        if (sp.isF01()) {
            num++;
        }
        if (sp.isF02()) {
            num++;
        }
        if (sp.isF03()) {
            num++;
        }
        if (sp.isF04()) {
            num++;
        }
        if (sp.isF12()) {
            num++;
        }
        if (sp.isF13()) {
            num++;
        }
        if (sp.isF14()) {
            num++;
        }
        if (sp.isF23()) {
            num++;
        }
        if (sp.isF24()) {
            num++;
        }
        if (sp.isFother()) {
            num++;
        }
        if (sp.iss40()) {
            num++;
        }
        if (sp.iss41()) {
            num++;
        }
        if (sp.iss42()) {
            num++;
        }
        if (sp.iss31()) {
            num++;
        }
        if (sp.iss30()) {
            num++;
        }
        if (sp.iss32()) {
            num++;
        }
        if (sp.issother()
                ) {
            num++;
        }
        if (sp.iss20()) {
            num++;
        }
        if (sp.iss21()) {
            num++;
        }
        if (sp.isS10()) {
            num++;
        }
        return num;
    }


    public void clearselect() {
        clearSpf();
        clearZjq();
        cleards();
        clearbf();
        clearBqc();
    }

    /**
     * "hostName": "庆南FC",
     * "guestName": "江原FC",
     * "leagueName": "K联赛",
     * "matchTime": "2016-7-18 17:55:00",
     *
     * @return
     */


    public String getWeek() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = simpleDateFormat.parse(matchTime);
        } catch (ParseException e) {
            e.printStackTrace();
            d = new Date();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        return "";

    }

    public String getSpfContent() {
        if (getspf()) {
            String num = "";

            if (isHost()) {
                num = "3,";
            }

            if (isVs()) {
                num = num + "1,";
            }

            if (isGust()) {
                num = num + "0,";
            }

            num = num.substring(0, num.length() - 1);

            return getTeamId() + "`" + getTeamId() + "`" + getHostName() + "`" + getGuestName() + "`" + false + "`" + num;
        } else {
            return null;
        }
    }


    public String getZjqNum() {
        String num = "";
        if (sp.zjq()) {

            if (sp.isT1()) {
                num = num + "0,";

            }
            if (sp.isT2()) {
                num = num + "1,";

            }
            if (sp.isT3()) {
                num = num + "2,";

            }
            if (sp.isT4()) {
                num = num + "3,";

            }
            if (sp.isT5()) {
                num = num + "4,";

            }
            if (sp.isT6()) {
                num = num + "5,";

            }
            if (sp.isT7()) {
                num = num + "6,";

            }
            if (sp.isT8()) {
                num = num + "7,";

            }
            num = num.substring(0, num.length() - 1);

            return getTeamId() + "`" + getTeamId() + "`" + getHostName() + "`" + getGuestName() + "`" + false + "`" + num;

        } else {
            return null;
        }
    }

    public String getSxdNum() {
        String num = "";

        if (sp.ds()) {

            if (sp.isShangD()) {
                num = num + "0,";
            }
            if (sp.isShangS()) {
                num = num + "1,";
            }
            if (sp.isXiaD()) {
                num = num + "2,";
            }
            if (sp.isXiaS()) {
                num = num + "3,";
            }
            num = num.substring(0, num.length() - 1);

            return getTeamId() + "`" + getTeamId() + "`" + getHostName() + "`" + getGuestName() + "`" + false + "`" + num;

        } else {
            return null;
        }
    }

    public String getBf() {
        String num = "";
/**
 *                 issother;
 isF01
 */

        if (sp.isF01()) {
            num = num + "15,";
        }

        if (sp.isF02()) {
            num = num + "16,";
        }
        if (sp.isF12()) {
            num = num + "17,";
        }
        if (sp.isF03()) {
            num = num + "18,";
        }
        if (sp.isF13()) {
            num = num + "19,";
        }
        if (sp.isF23()) {
            num = num + "20,";
        }
        if (sp.isF04()) {
            num = num + "21,";
        }
        if (sp.isF14()) {
            num = num + "22,";
        }
        if (sp.isF24()) {
            num = num + "23,";
        }
        if (sp.isFother()) {
            num = num + "24,";
        }


        if (sp.isP00()) {
            num = num + "10,";
        }

        if (sp.isP11()) {
            num = num + "11,";
        }
        if (sp.isP22()) {
            num = num + "12,";
        }
        if (sp.isP33()) {
            num = num + "13,";
        }
        if (sp.isPother()) {
            num = num + "14,";
        }


        /**
         * isS10;
         iss20;
         iss30;
         iss40;
         iss21;
         iss31;
         iss41;
         iss32;
         iss42;
         issother;
         */

        if (sp.isS10()) {
            num = num + "0,";
        }

        if (sp.iss20()) {
            num = num + "1,";
        }
        if (sp.iss21()) {
            num = num + "2,";
        }

        if (sp.iss30()) {
            num = num + "3,";
        }
        if (sp.iss31()) {
            num = num + "4,";
        }
        if (sp.iss32()) {
            num = num + "5,";
        }
        if (sp.iss40()) {
            num = num + "6,";
        }
        if (sp.iss41()) {
            num = num + "7,";
        }
        if (sp.iss42()) {
            num = num + "8,";
        }

        if (sp.issother()) {
            num = num + "9,";
        }
        num = num.substring(0, num.length() - 1);
        return num;

    }

    public String getBfNum() {

        if (sp.bifen()) {
            return getTeamId() + "`" + getTeamId() + "`" + getHostName() + "`" + getGuestName() + "`" + false + "`" + getBf();
        } else {
            return null;
        }
    }

    /**
     * isFf ||
     * isFp ||
     * isFs ||
     * isPf ||
     * isPp ||
     * isPs ||
     * isSf ||
     * isSp ||
     * isSs;
     *
     * @return
     */
    public String getbqcNum() {
        String s = "";

        if (sp.bqc()) {


            if (sp.isSs()) {
                s = s + "0,";

            }
            if (sp.isSp()) {
                s = s + "1,";

            }
            if (sp.isSf()) {
                s = s + "2,";

            }
            if (sp.isPs()) {
                s = s + "3,";

            }
            if (sp.isPp()) {
                s = s + "4,";

            }
            if (sp.isFp()) {
                s = s + "5,";

            }
            if (sp.isFs()) {
                s = s + "6,";

            }
            if (sp.isFp()) {
                s = s + "7,";

            }
            if (sp.isFf()) {
                s = s + "8,";

            }

            if (!TextUtils.isEmpty(s))
                s = s.substring(0, s.length() - 1);
            return getTeamId() + "`" + getTeamId() + "`" + getHostName() + "`" + getGuestName() + "`" + false + "`" + s;
        } else {
            return null;
        }
    }


    public void clearSpf() {
        setGust(false);
        setVs(false);
        setHost(false);

    }

    public void clearZjq() {
        sp.clearZjq();
    }

    public void cleards() {
        sp.clearDs();
    }

    public void clearbf() {
        sp.clearBf();
    }

    public void clearBqc() {
        sp.clearBqc();
    }


    private int id;
    private int matchId;
    private String matchCode;
    private int issue;
    private int teamId;
    private String hostName;
    private String guestName;
    private String leagueName;
    private String matchTime;
    private double rate;
    private int status;
    private String createTime;
    private String updateTime;
    private int rqSpfStatus;
    private int zjqStatus;
    private int sxdsStatus;
    private int bfStatus;
    private int bqcStatus;
    private boolean isEnd;

    private boolean isHost;
    private boolean isVs;
    private boolean isGust;

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public boolean isVs() {
        return isVs;
    }

    public void setVs(boolean vs) {
        isVs = vs;
    }

    public boolean isGust() {
        return isGust;
    }

    public void setGust(boolean gust) {
        isGust = gust;
    }

    /**
     * id : 24575
     * bjdcMatchId : 30399
     * matchCode : 60703_14
     * issue : 60703
     * teamId : 14
     * spType : 0
     * f01 : 13.54
     * f02 : 13.58
     * f03 : 34.81
     * f04 : 134.61
     * f12 : 8.67
     * f13 : 15.63
     * f14 : 86.02
     * f23 : 32.54
     * f24 : 77.65
     * fother : 39.71
     * p00 : 14.75
     * p11 : 8.28
     * p22 : 22.79
     * p33 : 85.1
     * pother : 301.3
     * s10 : 12.67
     * s20 : 15.73
     * s30 : 71.09
     * s40 : 241.47
     * s21 : 12.29
     * s31 : 34.07
     * s41 : 171.47
     * s32 : 54.85
     * s42 : 132.38
     * sother : 156.36
     * ff : 3.8
     * fp : 12.23
     * fs : 46.55
     * pf : 5.7
     * pp : 7.21
     * ps : 10.57
     * sf : 33.64
     * sp : 17.15
     * ss : 7.28
     * rqSheng : 3.88
     * rqPing : 3.98
     * rqFu : 2.03
     * t0 : 16.05
     * t1 : 5.63
     * t2 : 4.24
     * t3 : 4.43
     * t4 : 6.86
     * t5 : 11.5
     * t6 : 24.39
     * t7 : 38.9
     * shangD : 2.79
     * shangS : 5.03
     * xiaD : 5.74
     * xiaS : 3.7
     * status : 0
     * createTime : 2016-7-12 10:24:01
     * updateTime : 2016-7-13 16:17:30
     */

    private SpBean sp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public int getIssue() {
        return issue;
    }

    public void setIssue(int issue) {
        this.issue = issue;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    /**
     * 2016-7-19 22:55:00
     *
     * @return
     */
    public String getMatchTime() {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");

        Date d = new Date();
        try {
            d = sdf.parse(matchTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);


        return calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + " ";
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String  getRate() {
       int r= (int) rate;
       if (r>0){
           return  "+"+r;
       }else {
           return String.valueOf(r);
       }



    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getRqSpfStatus() {
        return rqSpfStatus;
    }

    public void setRqSpfStatus(int rqSpfStatus) {
        this.rqSpfStatus = rqSpfStatus;
    }

    public int getZjqStatus() {
        return zjqStatus;
    }

    public void setZjqStatus(int zjqStatus) {
        this.zjqStatus = zjqStatus;
    }

    public int getSxdsStatus() {
        return sxdsStatus;
    }

    public void setSxdsStatus(int sxdsStatus) {
        this.sxdsStatus = sxdsStatus;
    }

    public int getBfStatus() {
        return bfStatus;
    }

    public void setBfStatus(int bfStatus) {
        this.bfStatus = bfStatus;
    }

    public int getBqcStatus() {
        return bqcStatus;
    }

    public void setBqcStatus(int bqcStatus) {
        this.bqcStatus = bqcStatus;
    }

    public boolean isIsEnd() {
        return isEnd;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public SpBean getSp() {
        return sp;
    }

    public void setSp(SpBean sp) {
        this.sp = sp;
    }


    public boolean isSelect() {
        return getspf()||getSp().bifen()||getSp().bqc()||getSp().ds();
    }
}
