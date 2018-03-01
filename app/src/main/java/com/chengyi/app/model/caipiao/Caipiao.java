package com.chengyi.app.model.caipiao;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.wanfa.AbsWanfa;
import com.chengyi.app.model.wanfa.Dantuo;
import com.chengyi.app.model.wanfa.NormalPailie;
import com.chengyi.app.model.wanfa.Renxuan;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.widget.TouzhuButton;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
  public class  Caipiao implements Parcelable{

private  int sort=Integer.MAX_VALUE;

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	private int  type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	private int caipiaoType;// 福彩体彩快开

	// 彩票ID
	private int id;
	// 名称
	private String name;
	// 图标URL
	private String iconUrl;
	private int iconResId = R.drawable.list_quesheng;
	// 玩法网页URL
	private String wanfaUrl;

	// 前区字符串列表，（不一定是数字，如大小单双）,如果是全排列时，择qianquList代码一行的字符串，如0-9
	private List<String> qianquList = new ArrayList<String>();
	// 后区字符串列表，（不一定是数字，如大小单双）
	private List<String> houquList = new ArrayList<String>();// 如果是全排列时，择houquList代码一行的字符串，如0-9,东方6+1为基本号码+生肖号码，因此跟前区的不一定相同
	// 前区投注号码最少个数
	private int qianquMinCount;
	// 前区投注号码最多个数
	private int qianquMaxCount;
	// 后区投注号码最少个数
	private int houquMinCount;
	// 后区投注号码最多个数
	private int houquMaxCount;
	// 单注最多不能超过多少注或＊2多少钱
	private int maxTouzhuCount = 1000000;
	// 玩法列表
	private List<AbsWanfa> wanfaList;
	private List<AbsWanfa> fushiWanfaList;
	private int  issueNum;///快开类彩票每日一共有多少钱
	private int  issueId=0;
	private double jiangChi=0;
	private int beiShu=1;
	private int clickCount=0;
	private boolean isSelected=false;
	private int  resource;    //玩法布局文件
	private String  jiangChiStr;
	private String  h5Url;
	private String  lotteryPic;
	private boolean linkH5=false;
	public boolean isLinkH5() {
		return linkH5;
	}
	public void setLinkH5(boolean linkH5) {
		this.linkH5 = linkH5;
	}
	public String getLotteryPic() {
		return lotteryPic;
	}
	public void setLotteryPic(String lotteryPic) {
		this.lotteryPic = lotteryPic;
	}
	public String getH5Url() {
		return h5Url;
	}
	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}
	public String getJiangChiStr() {
		return jiangChiStr;
	}
	public void setJiangChiStr(String jiangChiStr) {
		this.jiangChiStr = jiangChiStr;
	}
	public int getResource() {
		return resource;
	}
	public void setResource(int resource) {
		this.resource = resource;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public double getJiangChi() {
		return jiangChi;
	}
	public void setJiangChi(double jiangChi) {
		this.jiangChi = jiangChi;
	}
	public int getBeiShu() {
		return beiShu;
	}
	public void setBeiShu(int beiShu) {
		this.beiShu = beiShu;
	}
	public int getIssueId() {
		return issueId;
	}
	public void setIssueId(int issueId) {
		this.issueId = issueId;
		if(issueId!=0&&(id==10032||id==10026)&& CaipiaoApplication.getInstance().getUpdateIssue()!=null){
			CaipiaoApplication.getInstance().getUpdateIssue().sendEmptyMessage(1);
		}
	}
	public int getIssueNum() {
		return issueNum;
	}
	public void setIssueNum(int issueNum) {
		this.issueNum = issueNum;
	}

	public List<AbsWanfa> getFushiWanfaList() {
		return fushiWanfaList;
	}



	public List<AbsWanfa> getDantuoWanfaList() {
		return dantuoWanfaList;
	}



	private List<AbsWanfa> dantuoWanfaList;
	//玩法大类列表
	private List<String> wanfaKindList;
	public List<String> getWanfaKindList() {
		return wanfaKindList;
	}

	public void setWanfaKindList(List<String> wanfaKindList) {
		this.wanfaKindList = wanfaKindList;
	}
//
//	// 图表列表
//	private List<TubiaoData> tubiaoList;
//
//	private TubiaoData currentTubiao;

	private String kaijiangshijian;
	//是否今晚开奖
	private int isDay=0;
	///奖池信息
	private String message="" ;
	//event
	private String event="" ;
	//是否暂停销售
	private int  isstop=0;
	//奖期
	private String issue=null;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public int getIsstop() {
		return isstop;
	}

	public void setIsstop(int isstop) {
		this.isstop = isstop;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	//截止开奖时间
	private int remainTime=-1;
	public String getMessage() {
		if (TextUtils.isEmpty(message))
			return  "";
		return message;
	}

	public void setMessage(String message) {
		if(message.indexOf("奖池")!=-1){
			String jc=message.split(":")[1];
			jc=jc.substring(0, jc.length()-1);
			if(!jc.equals("")){
				long jiangChi=Long.parseLong(jc);
				if(jiangChi>100000000L){
					BigDecimal pool = new BigDecimal((double)jiangChi/100000000L);
//				  this.message = "奖池:"+new DecimalFormat("0.00").format((double)jiangChi/100000000L)+"亿";
					this.message = "奖池:"+pool.setScale(2,1)+"亿";
				}else if(jiangChi>10000L){
//				  this.message = "奖池:"+new DecimalFormat("0.00").format((double)jiangChi/10000L)+"万";
					BigDecimal pool = new BigDecimal((double)jiangChi/10000L);
					this.message = "奖池:"+pool.setScale(2,1)+"万";
				}else {
					this.message = message;
				}
			}
		}else
			this.message = message;
	}

	public int getIsDay() {
		return isDay;
	}

	public void setIsDay(int isDay) {
		this.isDay = isDay;
	}
//
//	public List<TubiaoData> getTubiaoList() {
//		return tubiaoList;
//	}
//
//	public void setTubiaoList(List<TubiaoData> tubiaoList) {
//		this.tubiaoList = tubiaoList;
//		this.tubiaoList.add(new TubiaoData("全部显示", "", -1));
//	}
//
//	public TubiaoData getCurrentTubiao() {
//		return currentTubiao;
//	}
//
//	public void setCurrentTubiao(TubiaoData currentTubiao) {
//		this.currentTubiao = currentTubiao;
//	}

	public String getKaijiangshijian() {
		return kaijiangshijian;
	}

	public void setKaijiangshijian(String kaijiangshijian) {
		this.kaijiangshijian = kaijiangshijian;
	}

	public Caipiao(int cpType) {
		setCaipiaoType(cpType);
		remainTime=-1;
	}

	private List<List<TouzhuButton>> listTouzuList = new ArrayList<List<TouzhuButton>>();

	public List<List<TouzhuButton>> getListTouzuList() {
		return listTouzuList;
	}

	public void setListTouzuList(List<List<TouzhuButton>> listTouzuList) {
		this.listTouzuList = listTouzuList;
	}

	// 全排列时， 数字的位数
	public int getWeishu() {
		return qianquMinCount + houquMinCount;
	}

	public int getCaipiaoType() {
		return caipiaoType;
	}

	public void setCaipiaoType(int caipiaoType) {
		this.caipiaoType = caipiaoType;
	}

	private AbsWanfa currentWanfa;

	public AbsWanfa getCurrentWanfa() {
		return currentWanfa;
	}

	public void setCurrentWanfa(AbsWanfa wanfa) {
		this.currentWanfa = wanfa;
	}

	public int getIconResId() {
		return iconResId;
	}

	public void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getWanfaUrl() {
		return wanfaUrl;
	}

	public void setWanfaUrl(String wanfaUrl) {
		this.wanfaUrl = wanfaUrl;
	}

	public List<String> getQianquList() {
		return qianquList;
	}

	public void setQianquList(List<String> qianquList) {
		this.qianquList = qianquList;
	}

	public List<String> getHouquList() {
		return houquList;
	}

	public void setHouquList(List<String> houquList) {
		this.houquList = houquList;
	}

	public int getQianquMaxCount() {
		return qianquMaxCount;
	}

	public void setQianquMaxCount(int qianquMaxCount) {
		this.qianquMaxCount = qianquMaxCount;
	}

	public int getQianquMinCount() {
		return qianquMinCount;
	}

	public void setQianquMinCount(int qianquMinCount) {
		this.qianquMinCount = qianquMinCount;
	}

	public int getHouquMinCount() {
		return houquMinCount;
	}

	public void setHouquMinCount(int houquMinCount) {
		this.houquMinCount = houquMinCount;
	}

	public int getHouquMaxCount() {
		return houquMaxCount;
	}

	public void setHouquMaxCount(int houquMaxCount) {
		this.houquMaxCount = houquMaxCount;
	}

	public int getMaxTouzhuCount() {
		return maxTouzhuCount;
	}

	public void setMaxTouzhuCount(int maxTouzhuCount) {
		this.maxTouzhuCount = maxTouzhuCount;
	}

	public List<AbsWanfa> getWanfaList() {
		return wanfaList;
	}

	public void setWanfaList(List<AbsWanfa> wanfaList) {
		this.wanfaList = wanfaList;
		for (AbsWanfa wf : wanfaList) {
			wf.setCaipiao(this);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Caipiao)) {
			return false;
		}
		Caipiao cp = (Caipiao) o;
		return this.getId() == cp.getId();// 排列3和排列5id相同，名字不同
	}

	@Override
	public int hashCode() {
		return this.getId();
	}

	public String getCurrentWanfaName() {
		if (getWanfaList().size() == 1) {
			return "";

		} else {
			return getCurrentWanfa().getName();
		}
	}

	public String getTouzhuString() {
		String s = "";
		//快三特殊处理下
		if(CaipiaoUtil.isKySj(id)){
			switch(getCurrentWanfa().getType()){
				case CaipiaoConst.WF_NOSAME_THREE:
				case CaipiaoConst.WF_NOSAME_TWO:
				case CaipiaoConst.WF_RENYI:
					for(int i=1;i<=btnList.size();i++){
						if(btnList.get(i-1).isSelected())
							s=s+i+" ";
					}
					break;
				case CaipiaoConst.WF_HEZHI:
					for(int i=3;i<=btnList.size()+2;i++){
						if(btnList.get(i-3).isSelected())
							s=s+i+" ";
					}
					break;
				case CaipiaoConst.WF_SHUNZI:
					s="123,234,345,456 ";
					break;
				case CaipiaoConst.WF_DUIZI:
					for(int i=1;i<=btnList.size();i++){
						if(btnList.get(i-1).isSelected())
							s=s+i+""+i+"*"+" ";
					}
					break;
				case CaipiaoConst.WF_BAOZI:
					for(int i=1;i<=btnList.size();i++){
						if(btnList.get(i-1).isSelected())
							s=s+i+""+i+""+i+" ";
					}
					break;
			}
			if(s.length()>1&&s.indexOf(" ")!=-1)
				return s.substring(0, s.length()-1);
			else
				return s;
		}


		else if(id==CaipiaoConst.ID_LUCKYCAR&&getCurrentWanfa().getType()==CaipiaoConst.WF_DAXIAOJQ){
			for(int i=0;i<btnList.size();i++){
				if(btnList.get(i).isSelected())
					s=s+CaipiaoConst.strArray[i]+" ";
			}
			if(s.length()>1&&s.indexOf(" ")!=-1)
				return s.substring(0, s.length()-1);
			else
				return s;
		}
		List<List<TouzhuButton>> list = getListTouzuList();
		if(null==list||list.size()==0)
			return "";
		if (getCurrentWanfa() instanceof Dantuo) {// 如果是胆拖，则list排序要为前胆-前拖-后胆-后拖
			List<List<TouzhuButton>> newList = new ArrayList<List<TouzhuButton>>();
			if (list.size() <= 3) {
				newList = list;
			} else if (list.size() == 4) {
				newList.add(list.get(0));
				newList.add(list.get(1));
				newList.add(list.get(2));
				newList.add(list.get(3));
			}
			list = newList;
		}
		String pad = list.get(0).get(0).getText().toString().length() == 2 ? CaipiaoConst.PADDING_2WEINUMBER
				: CaipiaoConst.PADDING_1WEINUMBER;
		boolean isPailie = getCurrentWanfa() instanceof NormalPailie
				|| getCurrentWanfa() instanceof Renxuan;

		for (int i = 0; i < list.size(); i++) {
			List<TouzhuButton> temp = list.get(i);
			boolean isEmptyRow = true;
			int size=temp.size();
			if(getId()==CaipiaoConst.ID_KUAILE10FEN&&getCurrentWanfa().getType()==CaipiaoConst.WF_QIAN1&&size>18){
				size=18;
			}
			for (int j = 0; j < size; j++) {
				if (temp.get(j).isSelected()) {
					s = s + temp.get(j).getText().toString() + pad;
					isEmptyRow = false;
				}
			}
			if (s.endsWith(pad)) {
				s = s.substring(0, s.length() - pad.length());
			}
			if (isEmptyRow) {
				s = s + CaipiaoConst.PADDING_EMPTY;
			}
			s = CaipiaoUtil.trimLast(s);

			if (isPailie) {
				if (list.size() > getQianquMinCount()) {// 排列有后区
					if (i == getQianquMinCount() - 1&&id!=10067) {
						s = s + CaipiaoConst.PADDING_QU;
					} else {
						if(id==10067&&i==list.size() - 1)
							s = s + "";
						else
							s = s + CaipiaoConst.PADDING_WEI;
					}
				} else {// 没后区
					if (i == list.size() - 1) {
						s = s + "";
					} else {
						s = s + CaipiaoConst.PADDING_WEI;
					}
				}
			} else if (getCurrentWanfa() instanceof Dantuo) {// 胆拖,胆码和拖码都是不能为空的
				if (list.size() == 2) {// 如22选5
					if (i == 0) {
						s = "(" + s.trim() + ")";
					}
				} else if (list.size() == 3) {// 如双色球
					if (i == 0) {
						s = "(" + s.trim() + ")";
					} else if (i == 1) {
						s = s.trim() + CaipiaoConst.PADDING_QU;
					}
				} else if (list.size() == 4) {// 如大乐透
					if (i == 0) {
						s = "(" + s.trim() + ")";
					} else if (i == 1) {
						s = s.trim() + CaipiaoConst.PADDING_QU + "(";
					} else if (i == 2) {
						s = s + ")";
					} else {// 最后将后区胆码为空的情况去除
						s = s.replace("(" + CaipiaoConst.PADDING_EMPTY + ")",
								"");// 因为胆码为空时，加了一个空行PADDING_EMPTY符号
					}
				}
			} else {
				if (i == list.size() - 1) {
					s = s + " ";
				} else {
					s = s + CaipiaoConst.PADDING_QU;
				}

			}
		}
		s = CaipiaoUtil.trimLast(s);
		if(getCurrentWanfa().getType()==CaipiaoConst.WF_ZU3_SINGLE){
			s=s.replace("-", " ");
			s=s.substring(0, 1)+s;
		}
		return s;
	}


	public boolean isKuaikai() {
		return CaipiaoUtil.isKuaikai(getId());
	}

	public AbsWanfa getWanfaByType(int type) {
		for (AbsWanfa wf : wanfaList) {
			if (wf.getType() == type) {
				return wf;
			}
		}
		return null;
	}
	public AbsWanfa getWanfaByName(String name) {
		for (AbsWanfa wf : wanfaList) {
			if (wf.getName().equals(name)) {
				return wf;
			}
		}
		return null;
	}
	///快三专用
	List<View>  btnList;
	public List<View> getBtnList() {
		return btnList;
	}
	public void setBtnList(List<View> btnList) {
		this.btnList = btnList;
	}
	List<Button> kSanLastBtnList;
	List<Button> kSanFiveBtnList;
	public List<Button> getkSanFiveBtnList() {
		return kSanFiveBtnList;
	}
	public void setkSanFiveBtnList(List<Button> kSanFiveBtnList) {
		this.kSanFiveBtnList = kSanFiveBtnList;
	}
	public List<Button> getkSanLastBtnList() {
		return kSanLastBtnList;
	}
	public void setkSanLastBtnList(List<Button> kSanLastBtnList) {
		this.kSanLastBtnList = kSanLastBtnList;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.caipiaoType);
		dest.writeInt(this.id);
		dest.writeString(this.name);
		dest.writeString(this.iconUrl);
		dest.writeInt(this.iconResId);
		dest.writeString(this.wanfaUrl);
		dest.writeStringList(this.qianquList);
		dest.writeStringList(this.houquList);
		dest.writeInt(this.qianquMinCount);
		dest.writeInt(this.qianquMaxCount);
		dest.writeInt(this.houquMinCount);
		dest.writeInt(this.houquMaxCount);
		dest.writeInt(this.maxTouzhuCount);

		dest.writeInt(this.issueNum);
		dest.writeInt(this.issueId);
		dest.writeDouble(this.jiangChi);
		dest.writeInt(this.beiShu);
		dest.writeInt(this.clickCount);
		dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
		dest.writeInt(this.resource);
		dest.writeString(this.jiangChiStr);
		dest.writeString(this.h5Url);
		dest.writeString(this.lotteryPic);
		dest.writeByte(this.linkH5 ? (byte) 1 : (byte) 0);
		dest.writeStringList(this.wanfaKindList);
		dest.writeString(this.kaijiangshijian);
		dest.writeInt(this.isDay);
		dest.writeString(this.message);
		dest.writeString(this.event);
		dest.writeInt(this.isstop);
		dest.writeString(this.issue);
		dest.writeInt(this.remainTime);
		dest.writeList(this.listTouzuList);
		dest.writeList(this.btnList);
		dest.writeList(this.kSanLastBtnList);
		dest.writeList(this.kSanFiveBtnList);
	}

	protected Caipiao(Parcel in) {
		this.caipiaoType = in.readInt();
		this.id = in.readInt();
		this.name = in.readString();
		this.iconUrl = in.readString();
		this.iconResId = in.readInt();
		this.wanfaUrl = in.readString();
		this.qianquList = in.createStringArrayList();
		this.houquList = in.createStringArrayList();
		this.qianquMinCount = in.readInt();
		this.qianquMaxCount = in.readInt();
		this.houquMinCount = in.readInt();
		this.houquMaxCount = in.readInt();
		this.maxTouzhuCount = in.readInt();

		this.issueNum = in.readInt();
		this.issueId = in.readInt();
		this.jiangChi = in.readDouble();
		this.beiShu = in.readInt();
		this.clickCount = in.readInt();
		this.isSelected = in.readByte() != 0;
		this.resource = in.readInt();
		this.jiangChiStr = in.readString();
		this.h5Url = in.readString();
		this.lotteryPic = in.readString();
		this.linkH5 = in.readByte() != 0;
		this.wanfaKindList = in.createStringArrayList();
		this.kaijiangshijian = in.readString();
		this.isDay = in.readInt();
		this.message = in.readString();
		this.event = in.readString();
		this.isstop = in.readInt();
		this.issue = in.readString();
		this.remainTime = in.readInt();
		this.listTouzuList = new ArrayList<List<TouzhuButton>>();
		this.currentWanfa = in.readParcelable(AbsWanfa.class.getClassLoader());
		this.btnList = new ArrayList<View>();
		in.readList(this.btnList, View.class.getClassLoader());
		this.kSanLastBtnList = new ArrayList<Button>();
		in.readList(this.kSanLastBtnList, Button.class.getClassLoader());
		this.kSanFiveBtnList = new ArrayList<Button>();
		in.readList(this.kSanFiveBtnList, Button.class.getClassLoader());
	}

	public static final Creator<Caipiao> CREATOR = new Creator<Caipiao>() {
		@Override
		public Caipiao createFromParcel(Parcel source) {
			return new Caipiao(source);
		}

		@Override
		public Caipiao[] newArray(int size) {
			return new Caipiao[size];
		}
	};

}
