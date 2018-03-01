package com.chengyi.app.jingji.bzjy;


import com.chengyi.app.util.IP;

import java.io.Serializable;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class SpBean implements Serializable {
    private int id;
    private int bjdcMatchId;
    private String matchCode;
    private int issue;
    private String teamId;
    private int spType;
    private double f01;
    private double f02;
    private double f03;
    private double f04;
    private double f12;
    private double f13;
    private double f14;
    private double f23;
    private double f24;
    private double fother;
    private double p00;
    private double p11;
    private double p22;
    private double p33;
    private double pother;
    private double s10;
    private double s20;
    private double s30;
    private double s40;
    private double s21;
    private double s31;
    private double s41;
    private double s32;
    private double s42;
    private double sother;
    private double ff;
    private double fp;
    private double fs;
    private double pf;
    private double pp;
    private double ps;
    private double sf;
    private double sp;
    private double ss;
    private double rqSheng;
    private double rqPing;
    private double rqFu;
    private double t0;
    private double t1;
    private double t2;
    private double t3;
    private double t4;
    private double t5;
    private double t6;
    private double t7;
    private double shangD;
    private double shangS;
    private double xiaD;
    private double xiaS;
    private int status;
    private String createTime;
    private String updateTime;

    private boolean isT1;
    private boolean isT2;

    private boolean isT3;

    private boolean isT4;


    private boolean isT5;

    private boolean isT6;


    private boolean isT7;
    private boolean isT8;

    public void clearZjq() {
        isT1 = false;
        isT2 = false;
        isT3 = false;
        isT4 = false;
        isT5 = false;
        isT6 = false;
        isT7 = false;
        isT8 = false;
    }

    public boolean zjq() {
        return isT1 ||
                isT2 ||
                isT3 ||
                isT4 ||
                isT5 ||
                isT6 ||
                isT7 ||
                isT8;
    }


    public boolean ds() {
        return isShangD() || isShangS() || isXiaD() || isXiaS();
    }

    public void clearDs() {
        setShangD(false);
        setShangS(false);
        setXiaD(false);
        setXiaS(false);
    }

    private boolean isShangD;
    private boolean isShangS;
    private boolean isXiaD;
    private boolean isXiaS;


    public boolean isBifenF() {

        return isF01 || isF02() || isF03 || isF04 || isF12 || isF13 || isF14() || isF23() || isF24() || isFother();

    }

    public void clearBiff() {
        isF01 = false;
        isF02 = false;
        isF03 = false;
        isF04 = false;
        isF12 = false;
        isF13 = false;
        isF14 = false;
        isF23 = false;
        isF24 = false;
        isFother = false;
    }


    private boolean isF01;
    private boolean isF02;
    private boolean isF03;
    private boolean isF04;
    private boolean isF12;
    private boolean isF13;
    private boolean isF14;
    private boolean isF23;
    private boolean isF24;
    private boolean isFother;

    public boolean isF01() {
        return isF01;
    }

    public void setF01(boolean f01) {
        isF01 = f01;
    }

    public boolean isF02() {
        return isF02;
    }

    public void setF02(boolean f02) {
        isF02 = f02;
    }

    public boolean isF03() {
        return isF03;
    }

    public void setF03(boolean f03) {
        isF03 = f03;
    }

    public boolean isF04() {
        return isF04;
    }

    public void setF04(boolean f04) {
        isF04 = f04;
    }

    public boolean isF12() {
        return isF12;
    }

    public void setF12(boolean f12) {
        isF12 = f12;
    }

    public boolean isF13() {
        return isF13;
    }

    public void setF13(boolean f13) {
        isF13 = f13;
    }

    public boolean isF14() {
        return isF14;
    }

    public void setF14(boolean f14) {
        isF14 = f14;
    }

    public boolean isF23() {
        return isF23;
    }

    public void setF23(boolean f23) {
        isF23 = f23;
    }

    public boolean isF24() {
        return isF24;
    }

    public void setF24(boolean f24) {
        isF24 = f24;
    }

    public boolean isFother() {
        return isFother;
    }

    public void setFother(boolean fother) {
        isFother = fother;
    }

    public boolean bqc() {
        return isFf ||
                isFp ||
                isFs ||
                isPf ||
                isPp ||
                isPs ||
                isSf ||
                isSp ||
                isSs;
    }

    private boolean isFf;
    private boolean isFp;
    private boolean isFs;
    private boolean isPf;
    private boolean isPp;
    private boolean isPs;
    private boolean isSf;
    private boolean isSp;
    private boolean isSs;

    public void clearBf() {
        clearBp();
        clearBiff();
        clearBifenS();
    }

    public boolean bifen() {
        return bifenP() || isBifenF() || isBifenS();
    }

    public void clearBp() {
        isP00 = false;
        isP11 = false;
        isP22 = false;
        isP33 = false;
        isPother = false;
    }

    public boolean bifenP() {
        return isP00 ||
                isP11 ||
                isP22 ||
                isP33 ||
                isPother;
    }


    private boolean isP00;
    private boolean isP11;
    private boolean isP22;
    private boolean isP33;
    private boolean isPother;

    public boolean isP00() {
        return isP00;
    }

    public void setP00(boolean p00) {
        isP00 = p00;
    }

    public boolean isP11() {
        return isP11;
    }

    public void setP11(boolean p11) {
        isP11 = p11;
    }

    public boolean isP22() {
        return isP22;
    }

    public void setP22(boolean p22) {
        isP22 = p22;
    }

    public boolean isP33() {
        return isP33;
    }

    public void setP33(boolean p33) {
        isP33 = p33;
    }

    public boolean isPother() {
        return isPother;
    }

    public void setPother(boolean pother) {
        isPother = pother;
    }


    public boolean isBifenS() {
        return isS10 || isS10 ||
                iss20 ||
                iss30 ||
                iss40 ||
                iss21 ||
                iss31 ||
                iss41 ||
                iss32 ||
                iss42 ||
                issother;
    }

    public void clearBifenS() {
        isS10 = false;
        iss20 = false;
        iss30 = false;
        iss40 = false;
        iss21 = false;
        iss31 = false;
        iss41 = false;
        iss32 = false;
        iss42 = false;
        issother = false;
    }


    private boolean isS10;
    private boolean iss20;
    private boolean iss30;
    private boolean iss40;
    private boolean iss21;
    private boolean iss31;
    private boolean iss41;
    private boolean iss32;
    private boolean iss42;
    private boolean issother;

    public boolean isS10() {
        return isS10;
    }

    public void setS10(boolean s10) {
        isS10 = s10;
    }

    public boolean iss20() {
        return iss20;
    }

    public void setIss20(boolean iss20) {
        this.iss20 = iss20;
    }

    public boolean iss30() {
        return iss30;
    }

    public void setIss30(boolean iss30) {
        this.iss30 = iss30;
    }

    public boolean iss40() {
        return iss40;
    }

    public void setIss40(boolean iss40) {
        this.iss40 = iss40;
    }

    public boolean iss21() {
        return iss21;
    }

    public void setIss21(boolean iss21) {
        this.iss21 = iss21;
    }

    public boolean iss31() {
        return iss31;
    }

    public void setIss31(boolean iss31) {
        this.iss31 = iss31;
    }

    public boolean iss41() {
        return iss41;
    }

    public void setIss41(boolean iss41) {
        this.iss41 = iss41;
    }

    public boolean iss32() {
        return iss32;
    }

    public void setIss32(boolean iss32) {
        this.iss32 = iss32;
    }

    public boolean iss42() {
        return iss42;
    }

    public void setIss42(boolean iss42) {
        this.iss42 = iss42;
    }

    public boolean issother() {
        return issother;
    }

    public void setIssother(boolean issother) {
        this.issother = issother;
    }

    public boolean isFf() {
        return isFf;
    }

    public void setFf(boolean ff) {
        isFf = ff;
    }

    public boolean isFp() {
        return isFp;
    }

    public void setFp(boolean fp) {
        isFp = fp;
    }

    public boolean isFs() {
        return isFs;
    }

    public void setFs(boolean fs) {
        isFs = fs;
    }

    public boolean isPf() {
        return isPf;
    }

    public void setPf(boolean pf) {
        isPf = pf;
    }

    public boolean isPp() {
        return isPp;
    }

    public void setPp(boolean pp) {
        isPp = pp;
    }

    public boolean isPs() {
        return isPs;
    }

    public void setPs(boolean ps) {
        isPs = ps;
    }

    public boolean isSf() {
        return isSf;
    }

    public void setSf(boolean sf) {
        isSf = sf;
    }

    public boolean isSp() {
        return isSp;
    }

    public void setSp(boolean sp) {
        isSp = sp;
    }

    public boolean isSs() {
        return isSs;
    }

    public void setSs(boolean ss) {
        isSs = ss;
    }

    public boolean isShangD() {
        return isShangD;
    }

    public void setShangD(boolean shangD) {
        isShangD = shangD;
    }

    public boolean isShangS() {
        return isShangS;
    }

    public void setShangS(boolean shangS) {
        isShangS = shangS;
    }

    public boolean isXiaD() {
        return isXiaD;
    }

    public void setXiaD(boolean xiaD) {
        isXiaD = xiaD;
    }

    public boolean isXiaS() {
        return isXiaS;
    }

    public void setXiaS(boolean xiaS) {
        isXiaS = xiaS;
    }

    public boolean isT1() {
        return isT1;
    }

    public void setT1(boolean t1) {
        isT1 = t1;
    }

    public boolean isT2() {
        return isT2;
    }

    public void setT2(boolean t2) {
        isT2 = t2;
    }

    public boolean isT3() {
        return isT3;
    }

    public void setT3(boolean t3) {
        isT3 = t3;
    }

    public boolean isT4() {
        return isT4;
    }

    public void setT4(boolean t4) {
        isT4 = t4;
    }

    public boolean isT5() {
        return isT5;
    }

    public void setT5(boolean t5) {
        isT5 = t5;
    }

    public boolean isT6() {
        return isT6;
    }

    public void setT6(boolean t6) {
        isT6 = t6;
    }

    public boolean isT7() {
        return isT7;
    }

    public void setT7(boolean t7) {
        isT7 = t7;
    }

    public boolean isT8() {
        return isT8;
    }

    public void setT8(boolean t8) {
        isT8 = t8;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBjdcMatchId() {
        return bjdcMatchId;
    }

    public void setBjdcMatchId(int bjdcMatchId) {
        this.bjdcMatchId = bjdcMatchId;
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

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public int getSpType() {
        return spType;
    }

    public void setSpType(int spType) {
        this.spType = spType;
    }

    public String getF01() {
        if (f01 == 0) {
            return IP.CANNOT_SELECT;

        }
        return f01 + "";
    }

    public void setF01(double f01) {
        this.f01 = f01;
    }

    public String getF02() {


        if (f02 == 0) {
            return IP.CANNOT_SELECT;
        }
        return "" +
                f02;
    }

    public void setF02(double f02) {
        this.f02 = f02;
    }

    public String getF03() {

        if (f03 == 0) {
            return IP.CANNOT_SELECT;
        }

        return f03 + "";
    }

    public void setF03(double f03) {
        this.f03 = f03;
    }

    public String getF04() {
        if (f04 == 0) {
            return IP.CANNOT_SELECT;
        }
        return f04 + "";
    }

    public void setF04(double f04) {

        this.f04 = f04;
    }

    public String getF12() {

        return f12 == 0 ? IP.CANNOT_SELECT : f12 + "";
    }

    public void setF12(double f12) {
        this.f12 = f12;
    }

    public String getF13() {
        if (f13 == 0)
            return IP.CANNOT_SELECT;
        return "" + f13;
    }

    public void setF13(double f13) {
        this.f13 = f13;
    }

    public String getF14() {
        if (f14 == 0)
            return IP.CANNOT_SELECT;
        return f14 + "";
    }

    public void setF14(double f14) {
        this.f14 = f14;
    }

    public String getF23() {
        if (f23 == 0) return IP.CANNOT_SELECT;

        return "" + f23;
    }

    public void setF23(double f23) {
        this.f23 = f23;
    }

    public String getF24() {
        if (f24 == 0) return IP.CANNOT_SELECT;

        return "" + f24;
    }

    public void setF24(double f24) {
        this.f24 = f24;
    }

    public String getFother() {
        if (fother == 0) return IP.CANNOT_SELECT;

        return fother + "";
    }

    public void setFother(double fother) {
        this.fother = fother;
    }

    public String getP00() {
        if (p00 == 0) return IP.CANNOT_SELECT;
        return p00 + "";
    }

    public void setP00(double p00) {
        this.p00 = p00;
    }

    public String getP11() {
        if (p11 == 0) return IP.CANNOT_SELECT;
        return p11 + "";
    }

    public void setP11(double p11) {
        this.p11 = p11;
    }

    public String getP22() {
        if (p22 == 0)
            return IP.CANNOT_SELECT;
        return p22 + "";
    }

    public void setP22(double p22) {
        this.p22 = p22;
    }

    public String getP33() {

        if (p33 == 0) {
            return IP.CANNOT_SELECT;
        }
        return p33 + "";
    }

    public void setP33(double p33) {
        this.p33 = p33;
    }

    public String getPother() {
        if (pother == 0) return IP.CANNOT_SELECT;
        return pother + "";
    }

    public void setPother(double pother) {
        this.pother = pother;
    }

    public String getS10() {

        if (s10 == 0) return IP.CANNOT_SELECT;

        return s10 + "";
    }

    public void setS10(double s10) {
        this.s10 = s10;
    }

    public String getS20() {
        if (s20 == 0) return IP.CANNOT_SELECT;

        return s20 + "";
    }

    public void setS20(double s20) {
        this.s20 = s20;
    }

    public String getS30() {

        if (s30 == 0) return IP.CANNOT_SELECT;
        return s30 + "";
    }

    public void setS30(double s30) {
        this.s30 = s30;
    }

    public String getS40() {
        if (s40 == 0) return IP.CANNOT_SELECT;

        return "" + s40;
    }

    public void setS40(double s40) {
        this.s40 = s40;
    }

    public String getS21() {
        if (s21 == 0) return IP.CANNOT_SELECT;
        return s21 + "";
    }

    public void setS21(double s21) {
        this.s21 = s21;
    }

    public String getS31() {
        if (s31 == 0) return IP.CANNOT_SELECT;


        return s31 + "";
    }

    public void setS31(double s31) {
        this.s31 = s31;
    }

    public String getS41() {
        if (s41 == 0) return IP.CANNOT_SELECT;

        return s41 + "";
    }

    public void setS41(double s41) {
        this.s41 = s41;
    }

    public String getS32() {

        if (s32 == 0) return IP.CANNOT_SELECT;
        return s32 + "";
    }

    public void setS32(double s32) {
        this.s32 = s32;
    }

    public String getS42() {
        if (s42 == 0) return IP.CANNOT_SELECT;

        return s42 + "";
    }

    public void setS42(double s42) {
        this.s42 = s42;
    }

    public String getSother() {
        if (sother == 0) return IP.CANNOT_SELECT;

        return sother + "";
    }

    public void setSother(double sother) {
        this.sother = sother;
    }

    public String getFf() {
        if (ff == 0) return IP.CANNOT_SELECT;
        return ff + "";
    }

    public void setFf(double ff) {
        this.ff = ff;
    }

    public String getFp() {

        if (fp == 0) return IP.CANNOT_SELECT;
        return fp + "";
    }

    public void setFp(double fp) {
        this.fp = fp;
    }

    public String getFs() {
        if (fs == 0) return IP.CANNOT_SELECT;
        return fs + "";
    }

    public void setFs(double fs) {
        this.fs = fs;
    }

    public String getPf() {

        if (pf == 0) return IP.CANNOT_SELECT;
        return pf + "";
    }

    public void setPf(double pf) {
        this.pf = pf;
    }

    public String getPp() {

        if (pp == 0) return IP.CANNOT_SELECT;
        return pp + "";
    }

    public void setPp(double pp) {
        this.pp = pp;
    }

    public String getPs() {

        if (ps == 0) return IP.CANNOT_SELECT;
        return ps + "";
    }

    public void setPs(double ps) {
        this.ps = ps;
    }

    public String getSf() {

        if (sf == 0) return IP.CANNOT_SELECT;

        return sf + "";
    }

    public void setSf(double sf) {
        this.sf = sf;
    }

    public String getSp() {

        if (sp == 0) return IP.CANNOT_SELECT;


        return sp + "";
    }

    public void setSp(double sp) {
        this.sp = sp;
    }

    public String getSs() {
        if (ss == 0) return IP.CANNOT_SELECT;

        return ss + "";
    }

    public void setSs(double ss) {
        this.ss = ss;
    }

    public String getRqSheng() {

        if (rqSheng == 0) return IP.CANNOT_SELECT;
        return rqSheng + "";
    }

    public void setRqSheng(double rqSheng) {
        this.rqSheng = rqSheng;
    }

    public String getRqPing() {

        if (rqPing == 0) {
            return IP.CANNOT_SELECT;
        }

        return rqPing + "";
    }

    public void setRqPing(double rqPing) {
        this.rqPing = rqPing;
    }

    public String getRqFu() {
        if (rqFu == 0) return IP.CANNOT_SELECT;

        return rqFu + "";
    }

    public void setRqFu(double rqFu) {
        this.rqFu = rqFu;
    }

    public String getT0() {

        if (t0 == 0) return IP.CANNOT_SELECT;
        return t0 + "";
    }

    public void setT0(double t0) {
        this.t0 = t0;
    }

    public String getT1() {
        if (t1 == 0) return IP.CANNOT_SELECT;
        return t1 + "";
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }

    public String getT2() {
        if (t2 == 0) return IP.CANNOT_SELECT;
        return t2 + "";
    }

    public void setT2(double t2) {
        this.t2 = t2;
    }

    public String getT3() {
        if (t3 == 0) return IP.CANNOT_SELECT;
        return t3 + "";
    }

    public void setT3(double t3) {
        this.t3 = t3;
    }

    public String getT4() {
        if (t4 == 0) return IP.CANNOT_SELECT;
        return t4 + "";
    }

    public void setT4(double t4) {
        this.t4 = t4;
    }

    public String getT5() {
        if (t5 == 0) return IP.CANNOT_SELECT;
        return t5 + "";
    }

    public void setT5(double t5) {
        this.t5 = t5;
    }

    public String getT6() {
        if (t6 == 0) return IP.CANNOT_SELECT;
        return t6 + "";
    }

    public void setT6(double t6) {
        this.t6 = t6;
    }

    public String getT7() {
        if (t7 == 0) return IP.CANNOT_SELECT;
        return t7 + "";
    }

    public void setT7(double t7) {
        this.t7 = t7;
    }

    public String getShangD() {
        if (shangD == 0) return IP.CANNOT_SELECT;
        return shangD + "";
    }

    public void setShangD(double shangD) {
        this.shangD = shangD;
    }

    public String getShangS() {
        if (shangS == 0) return IP.CANNOT_SELECT;
        return shangS + "";
    }

    public void setShangS(double shangS) {
        this.shangS = shangS;
    }

    public String getXiaD() {
        if (xiaD == 0) return IP.CANNOT_SELECT;
        return xiaD + "";
    }

    public void setXiaD(double xiaD) {
        this.xiaD = xiaD;
    }

    public String getXiaS() {
        if (xiaS == 0) return IP.CANNOT_SELECT;
        return xiaS + "";
    }

    public void setXiaS(double xiaS) {
        this.xiaS = xiaS;
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

    public void clearBqc() {
        isFf = false;
        isFp = false;
        isFs = false;
        isPf = false;
        isPp = false;
        isPs = false;
        isSf = false;
        isSp = false;
        isSs = false;

    }

    public String getBqcStr() {
        String s = "";

        if (isSs) {
            s = s + "胜胜\t";
        }

        if (isSf) {
            s = s + "胜负\t";
        }

        if (isSp) {
            s = s + "胜平\t";
        }

        if (isPf) {
            s = s + "平负\t";
        }

        if (isPp) {
            s = s + "平平\t";
        }

        if (isPs) {
            s = s + "平胜\t";
        }
        if (isFs) {
            s = s + "负胜\t";
        }
        if (isFp) {
            s = s + "负平\t";
        }
        if (isFf) {
            s = s + "负负\t";
        }


        return s;
    }
}
