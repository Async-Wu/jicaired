package com.chengyi.app.jingji.fourgoal;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by xiaqi on 2016/9/12.
 */
public class FourgoalEntity implements Serializable{

    /**
     * flag : 1
     * remainTime : 0
     * issue : ["2016135"]
     * data : [{"order":0,"hostName":"切　沃","guestName":" 拉齐奥","matchTime":"2016-09-11 21:00:00","bf":"","odds_p":"","odds_f":"","odds_s":"","color":"#7E001C","in":""},{"order":1,"hostName":"AC米兰","guestName":" 乌迪内","matchTime":"2016-09-11 21:00:00","bf":"","odds_p":"","odds_f":"","odds_s":"","color":"#7E001C","in":""},{"order":2,"hostName":"佩斯卡","guestName":" 国米","matchTime":"2016-09-12 02:45:00","bf":"","odds_p":"","odds_f":"","odds_s":"","color":"#7E001C","in":""},{"order":3,"hostName":"罗　马","guestName":" 桑普多","matchTime":"2016-09-11 21:00:00","bf":"","odds_p":"","odds_f":"","odds_s":"","color":"#7E001C","in":""}]
     * issueId : [7908787]
     * curIssue : 2016135
     * sellEndTime : 09-11 20:00
     * curIssueId : 7908787
     */

    private int flag;
    private int remainTime;
    private String curIssue;
    private String sellEndTime;
    private int curIssueId;
    private List<String> issue;
    /**
     * order : 0
     * hostName : 切　沃
     * guestName :  拉齐奥
     * matchTime : 2016-09-11 21:00:00
     * bf :
     * odds_p :
     * odds_f :
     * odds_s :
     * color : #7E001C
     * in :
     */

    private List<DataEntity> data;
    private List<Integer> issueId;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public void setCurIssue(String curIssue) {
        this.curIssue = curIssue;
    }

    public void setSellEndTime(String sellEndTime) {
        this.sellEndTime = sellEndTime;
    }

    public void setCurIssueId(int curIssueId) {
        this.curIssueId = curIssueId;
    }

    public void setIssue(List<String> issue) {
        this.issue = issue;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public void setIssueId(List<Integer> issueId) {
        this.issueId = issueId;
    }

    public int getFlag() {
        return flag;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public String getCurIssue() {
        return curIssue;
    }

    public String getSellEndTime() {
        return sellEndTime;
    }

    public int getCurIssueId() {
        return curIssueId;
    }

    public List<String> getIssue() {
        return issue;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public List<Integer> getIssueId() {
        return issueId;
    }

    public static class DataEntity implements Serializable{

        private int order;
        private String hostName;
        private String guestName;
        private String matchTime;
        private String bf;
        private String odds_p;
        private String odds_f;
        private String odds_s;
        private String color;
        private String in;
        public int[] Hoststatusnew=new int[4];
        public int caculHost(){
            int total1=0;
            for (int i=0;i<Hoststatusnew.length;i++){
                total1=total1+Hoststatusnew[i];
            }
            return total1;
        }
        public void cleardata(){
            Hoststatusnew[0]=0;
            Hoststatusnew[1]=0;
            Hoststatusnew[2]=0;
            Hoststatusnew[3]=0;
            Guestatusnew[0]=0;
            Guestatusnew[1]=0;
            Guestatusnew[2]=0;
            Guestatusnew[3]=0;
        }
        public int[] Guestatusnew=new int[4];
        public int caculGuest(){
            int total2=0;
            for (int i=0;i<Guestatusnew.length;i++){
                total2=total2+Guestatusnew[i];
            }
            return total2;
        }
        public boolean judgezhu(){
            int total1=caculHost();
            int total2=caculGuest();
            if ((total1*total2)==0){
                return false;
            }else{
                return  true;
            }
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }

        public void setGuestName(String guestName) {
            this.guestName = guestName;
        }

        public void setMatchTime(String matchTime) {
            this.matchTime = matchTime;
        }

        public void setBf(String bf) {
            this.bf = bf;
        }

        public void setOdds_p(String odds_p) {
            this.odds_p = odds_p;
        }

        public void setOdds_f(String odds_f) {
            this.odds_f = odds_f;
        }

        public void setOdds_s(String odds_s) {
            this.odds_s = odds_s;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public void setIn(String in) {
            this.in = in;
        }

        public int getOrder() {
            return order;
        }

        public String getHostName() {
            return hostName;
        }

        public String getGuestName() {
            return guestName;
        }

        public String getMatchTime() {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d;
            try {
              d=  sdf.parse(matchTime);
            } catch (ParseException e) {
                e.printStackTrace();
                d=new Date();
            }


            SimpleDateFormat s=new SimpleDateFormat("HH:mm:ss");
            return  s.format(d) ;
        }

        public String getBf() {
            return bf;
        }

        public String getOdds_p() {
            return odds_p;
        }

        public String getOdds_f() {
            return odds_f;
        }

        public String getOdds_s() {
            return odds_s;
        }

        public String getColor() {
            return color;
        }

        public String getIn() {
            return in;
        }

        public String getName() {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d;
            try {
                d=  sdf.parse(matchTime);
            } catch (ParseException e) {
                e.printStackTrace();
                d=new Date();
            }
            SimpleDateFormat s=new SimpleDateFormat("MM月dd日");
            return  s.format(d) ;

        }
    }
}
