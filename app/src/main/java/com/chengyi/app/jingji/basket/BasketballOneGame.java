package com.chengyi.app.jingji.basket;

import android.widget.TextView;
import com.chengyi.app.util.L;

import java.io.Serializable;
import java.text.DecimalFormat;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BasketballOneGame implements Cloneable, Serializable {

	public int id;// 比赛ID
	public int issue;// 奖期
	public int issueId;
	public String matchCode;

	public String matchId;

	public String adminId;
	public String basePoint;// 预设分
	public String cls;
	public String color;// 联赛颜色

	public String guestName;// 客队名
	public String hostName;// 主队名

	public String[] leagueName = new String[2];// 联赛名称;0缩写 1全称
	public long matchTime;// 比赛时间
	public long cp2yEndTime;// 网站截止时间
	public boolean end;// 比赛是否结束
	public long endTime;// 结束时间

	public String oneScore;// 第一节比分
	public String fourScore;// 第四节比分
	public String lastScore;// 最后一节比分

	public int jcwId;

	public int order;
	public String personLock;

	public String dxfggResult;// 大小分过关结果
	public int dxfPassStatus;// 大小分过关状态
	public String dxfResult;// 大小分单关结果
	public int dxfSingleStatus;// 大小分单关状态
	public String dxfSp;// 大小分赔率

	public double rate;// 让分

	public String rfsfggResult;// 让分过关结果
	public int rfsfPassStatus;// 让分胜负过关状态
	public String rfsfResult;// 让分胜负结果
	public int rfsfSingleStatus;// 让分胜负单关状态
	public String rfsfSp;// 让分胜负赔率

	public String sfcggResult;// 胜分差过关结果
	public int sfcPassStatus;// 胜分差过关状态
	public String sfcResult;// 胜分差结果
	public int sfcSingleStatus;
	public String sfcSp;// 胜分差赔率

	public String sfggResult;// 胜负过关结果
	public int sfPassStatus;// 胜负过关状态
	public String sfResult;
	public int sfSingleStatus;
	public String sfSp;// 胜负赔率

	// 过关
	public int idGG;
	public int issueGG;

	public String matchCodeGG;
	public double rateGG;
	public String basketBallMatchId;

	public String createTime;

	public double sheng;// 胜赔率
	public double fu;// 负赔率

	public double[] shenfuMinSp = new double[2];//0:最小赔率；1:0胜赔率小，1负赔率小

	public double rffu;// 让分负赔率
	public double rfsheng;// 让分胜赔率

	public double[] rfshenfuMinSp = new double[2];

	public double d;// 大小分大赔率
	public double x;// 大小分小赔率

	public double[] sfcGuestWinSp = new double[6];
	public double[] sfcHostWinSp = new double[6];

	public String[] sfcName = {"1-5", "6-10", "11-15", "16-20", "21-25", "26+"};

	public String[] sfcGuestRequestId = {"0", "1", "2", "3", "4", "5"};
	public String[] sfcHostRequestId = {"6", "7", "8", "9", "10", "11"};

	public String[] mixHostRequestId={"1", "2", "3", "4", "5","6"};
	// public double g1115;// 胜分差客胜11-15赔率
	// public double g15;// 胜分差客胜1-5赔率
	// public double g1620;// 胜分差客胜16-20赔率
	// public double g2125;
	// public double g26;
	// public double g610;
	// public double h1115;// 胜分差主11-15赔率
	// public double h15;
	// public double h1620;
	// public double h2125;
	// public double h26;
	// public double h610;

	public int spType;// 赔率类型
	public int status;// 平台赛事状态
	public String teamId;// 队伍ID
	public String updateTime;
	public int weekDay;

	public int SFFlag = -1; // /投注列表中被选中的按钮的个数，主要用来计算注数所用

	public int SFCHostFlag = 0; // /投注列表中被选中的按钮的个数，主要用来计算注数所用
	public int SFCGuestFlag = 0;


	public boolean[] isSFSelected = new boolean[2];//胜负布尔值纪录
	public boolean[] isRFSFSelecter = new boolean[2];//让分胜平负布尔值纪录
	public boolean[] isBigSmallSelect = new boolean[2];//大小分纪录
	public boolean isDanPressed = false;

	public boolean[] isSFCHostSelected = new boolean[6];
	public boolean[] isSFCGuestSelected = new boolean[6];

	public int orderIdLocal = 0;// 用于记录这场比赛在整个记录中的位置，方便投注确认页面正确排序
	public double minSP = 1000000000;
	public double maxSP = 0;
	public String dayOfWeek;

	public int groupPosition = 0;

	public void reset() {
		SFFlag = -1;
		isSFSelected = new boolean[2];
		isRFSFSelecter=new boolean[2];
		isBigSmallSelect=new boolean[2];
		isDanPressed = false;
		isSFCHostSelected = new boolean[6];
		isSFCGuestSelected = new boolean[6];
		SFCHostFlag = 0;
		SFCGuestFlag = 0;
	}

	// public int getSpfFlag() {
	// return spfFlag;
	// }
	//
	// public void setSpfFlag(int spfFlag) {
	// this.spfFlag = spfFlag;
	// }

	public Object clone() {
		BasketballOneGame o = null;
		try {
			o = (BasketballOneGame) super.clone();
		} catch (CloneNotSupportedException e) {

		}
		o.isSFCGuestSelected = isSFCGuestSelected.clone();
		o.isSFCHostSelected = isSFCHostSelected.clone();
//		for(int i=0;i<6;i++){
//			o.isSFCGuestSelected[i]=isSFCHostSelected[i];
//
//		}

		return o;
	}

	public BasketballOneGame() {
		adminId = "";
		basePoint = "0";
		cls = "";
		color = "";
		cp2yEndTime = 0;
		dxfggResult = "";
		dxfPassStatus = 0;
		dxfResult = "";
		dxfSingleStatus = 0;
		dxfSp = "";
		end = false;
		endTime = 0;
		fourScore = "";
		guestName = "";
		hostName = "";
		id = 0;
		issue = 0;
		jcwId = 0;
		lastScore = "";
		matchCode = "";
		matchId = "";
		matchTime = 0;
		oneScore = "";
		order = 0;
		personLock = "";
		rate = 0;
		rfsfggResult = "";
		rfsfPassStatus = 0;
		rfsfResult = "";
		rfsfSingleStatus = 0;
		rfsfSp = "";
		sfcggResult = "";
		sfcPassStatus = 0;
		sfcResult = "";
		sfcSingleStatus = 0;
		sfcSp = "";
		sfggResult = "";
		sfPassStatus = 0;
		sfResult = "";
		sfSingleStatus = 0;
		sfSp = "";
		basePoint = "0";
		basketBallMatchId = "";
		createTime = "";
		d = 0;
		fu = 0;
		// g1115 = 0;
		// g15 = 0;
		// g1620 = 0;
		// g2125 = 0;
		// g26 = 0;
		// g610 = 0;
		// h1115 = 0;
		// h15 = 0;
		// h1620 = 0;
		// h2125 = 0;
		// h26 = 0;
		// h610 = 0;
		idGG = 0;
		issueGG = 0;
		matchCodeGG = "";
		rateGG = 0;
		rffu = 0;
		rfsheng = 0;
		sheng = 0;
		spType = 0;
		status = 0;
		teamId = "";
		updateTime = "";
		weekDay = 0;
		x = 0;

		leagueName[0] = "";
		leagueName[1] = "";
		isSFSelected[0] = false;
		isSFSelected[1] = false;

		dayOfWeek = "";
		issueId = 0;

		shenfuMinSp[0] = 0;
		shenfuMinSp[1] = 0;

		rfshenfuMinSp[0] = 0;
		rfshenfuMinSp[1] = 0;

	}

	// /填充投注确认和方案详情页面中的view
	public void fillXiangQingView(int wf, TextView dw, TextView tz) {
		DecimalFormat dft = new DecimalFormat("0.00");

		String rqStr = "";
		if (wf == 1) {

			rqStr = "(" + String.valueOf(rateGG) + ")";
		}
		String dwStr = this.matchCodeGG.substring(8) + "\n" + guestName
				+ "\nVS\n" + hostName + rqStr;

		String tzStr = "";
		String isDan = "";
		if (isDanPressed)
			isDan = "（胆)";
		int count = 0;
		switch (wf) {
			case 0: // /胜平负
				if (isSFSelected[1]) {

					tzStr += "\n主负 " + dft.format(fu) + isDan + "\n";

					count++;
				}

				if (isSFSelected[0]) {
					if (count == 0) {
						tzStr += "\n\n\n主胜 " + dft.format(sheng) + isDan;
					} else {
						tzStr += "\n主胜 " + dft.format(sheng) + isDan;
					}
					count++;
				} else {
					tzStr += "\n";
				}
				break;
			case 1: // 让球胜平负

				if (isSFSelected[1]) {

					tzStr += "\n让分主负 " + dft.format(rffu) + isDan + "\n";
					count++;
				}

				if (isSFSelected[0]) {
					if (count == 0) {
						tzStr += "\n\n\n让分主胜 " + dft.format(rfsheng) + isDan;
					} else {
						tzStr += "\n让分主胜 " + dft.format(rfsheng) + isDan;
					}

					count++;
				} else {
					tzStr += "\n";
				}
				break;

			case 2:
				String[] sfcName = this.sfcName;
				StringBuilder visitStr = null;
				StringBuilder hostStr = null;
				for (int i = 0; i < 6; i++) {
					if (isSFCGuestSelected[i]) {
						if (visitStr == null) {
							visitStr = new StringBuilder();
							visitStr.append("主负：").append(sfcName[i]);
						} else {
							visitStr.append(";\n          ").append(sfcName[i]);
						}
					}
					if (isSFCHostSelected[i]) {
						if (hostStr == null) {
							hostStr = new StringBuilder();
							hostStr.append("主胜：").append(sfcName[i]);
						} else {
							hostStr.append(";\n          ").append(sfcName[i]);
						}
					}
				}

				String visitChoosed = "";
				String hostChoosed = "";
				if (visitStr != null) {
					visitChoosed = visitStr.toString() + " ";
				}
				if (hostStr != null) {
					hostChoosed = hostStr.toString();
				}

				String selectedItems = visitChoosed + "\n" + hostChoosed;
				tzStr = selectedItems;
				break;
			case 6:
				if (isSFSelected[0]) {
					tzStr += "\n小分 " + dft.format(x) + isDan + "\n";
					count++;
				}
				if (isSFSelected[1]) {
					if (count == 0) {
						tzStr += "\n\n\n大分 " + dft.format(d) + isDan;
					} else {
						tzStr += "\n大分 " + dft.format(d) + isDan;
					}
					count++;
				} else {
					tzStr += "\n";
				}
				L.d("touzhuneirong",tzStr);
				break;
			case 8:
				if (isSFSelected[1]) {

					tzStr += "\n主负 " + dft.format(fu) + isDan;
					count++;
				}
				if (isSFSelected[0]) {
					if (count == 0) {
						tzStr += "\n主胜 " + dft.format(sheng) + isDan;
					} else {
						tzStr += "\n主胜 " + dft.format(sheng) + isDan;
					}
					count++;
				} else {

				}
				if (isRFSFSelecter[1]) {

					tzStr += "\n让分主负 " + dft.format(rffu) + isDan ;

					count++;
				}
				if (isRFSFSelecter[0]) {
					if (count == 0) {
						tzStr += "\n让分主胜 " + dft.format(rfsheng) + isDan;
					} else {
						tzStr += "\n让分主胜 " + dft.format(rfsheng) + isDan;
					}
					count++;
				} else {

				}
				if (isBigSmallSelect[0]) {
					tzStr += "\n小分 " + dft.format(x) + isDan;
					count++;
				}
				if (isBigSmallSelect[1]) {
					if (count == 0) {
						tzStr += "\n大分 " + dft.format(d) + isDan;
					} else {
						tzStr += "\n大分 " + dft.format(d) + isDan;
					}
					count++;
				} else {

				}
				String[] sfcName1 = this.sfcName;
				StringBuilder visitStr1 = null;
				StringBuilder hostStr1 = null;
				for (int i = 0; i < 6; i++) {
					if (isSFCGuestSelected[i]) {
						if (visitStr1 == null) {
							visitStr1 = new StringBuilder();
							visitStr1.append("\n主负：").append(sfcName1[i]);
						} else {
							visitStr1.append(";\n          ").append(sfcName1[i]);
						}
					}
					if (isSFCHostSelected[i]) {
						if (hostStr1 == null) {
							hostStr1 = new StringBuilder();
							hostStr1.append("\n主胜：").append(sfcName1[i]);
						} else {
							hostStr1.append(";\n          ").append(sfcName1[i]);
						}
					}
				}

				String visitChoosed1 = "";
				String hostChoosed1 = "";
				if (visitStr1 != null) {
					visitChoosed1 = visitStr1.toString() + " ";
				}
				if (hostStr1 != null) {
					hostChoosed1 = hostStr1.toString();
				}

				String selectedItems1 = tzStr+visitChoosed1 + "\n" + hostChoosed1;
				tzStr = selectedItems1;
				break;


		}

		// if(count==1){
		// tzStr="\n"+tzStr+"\n";
		// }else if(count==2){
		// tzStr="\n"+tzStr;
		// }
		dw.setText(dwStr);
		tz.setText(tzStr);

	}


}
