package com.chengyi.app.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 方案格式转换工具类. Date: 11-12-8 下午2:51
 * 
 * @author dongxf2006@gmail.com since 2.0
 */
/**  * Created by ChengYi  * Copyright 2014-2016 丞易软件 All rights reserved.  */ public class  SchemeFormatUtil {

	public static String SCHEME_SPLIT = "&"; // 每注号码分隔符

	public static final int ID_SHUANGSEQIU = 10032;
	public static final int ID_FUCAI3D = 10025;
	public static final int ID_QILECAI = 10033;
	public static final int ID_15XUAN5 = 10035;
	public static final int ID_XINSHISHICAI = 10061;

	public static final int ID_LAO11XUAN5 = 10046;
	public static final int ID_XIN11XUAN5 = 10060;
	public static final int ID_11XUAN5 = 10066;
	public static final int ID_FUCAI15XUAN5 = 10035;
	public static final int ID_DALETOU = 10026;
	public static final int ID_PAILIE3 = 10024;
	public static final int ID_PAILIE5 = 10024;
	public static final int ID_QIXINGCAI = 10030;
	public static final int ID_QUANGUO22XUAN5 = 10029;
	public static final int ID_TICAI6JIA1 = 10027;
	public static final int ID_TICAI20XUAN5 = 10028;

	public static final String single = "single";// 单式
	public static final String poly = "poly";// 复式
	public static final String groupSingle = "groupSingle";// 组选单式
	public static final String group3Poly = "group3Poly";// 组3复式
	public static final String group6Poly = "group6Poly";// 组6复式
	public static final String directDraw = "directDraw";// 直选胆拖
	public static final String group3Draw = "group3Draw";// 组三胆拖
	public static final String group6Draw = "group6Draw";// 组6胆拖
	public static final String directSum = "directSum";// 直选和值
	public static final String group3Sum = "group3Sum";// 组三和值
	public static final String group6Sum = "group6Sum";// 组六和值
	public static final String kdDirect = "kdDirect";// 直选跨度
	public static final String kdGroup3 = "kdGroup3";// 组三跨度
	public static final String kdGroup6 = "kdGroup6";// 组六跨度

	public static final String dxds = "dxds";// 大小单双
	public static final String fiveStarAllSingle = "fiveStarAllSingle";// 五星通选单式
	public static final String fiveStarAllPoly = "fiveStarAllPoly";// 五星通选复式
	public static final String fiveStarSingle = "fiveStarSingle";// 五星单式
	public static final String fiveStarPoly = "fiveStarPoly";// 五星复式
	public static final String fourStarSingle = "fourStarSingle";// 四星单式
	public static final String fourStarPoly = "fourStarPoly";// 四星复式
	public static final String threeStarDirectSingle = "threeStarDirectSingle";// 三星直选单式
	public static final String threeStarDirectPoly = "threeStarDirectPoly";// 三星直选复式
	public static final String threeStarDirectDraw = "threeStarDirectDraw";// 三星直选胆拖
	public static final String threeStarDirectSum = "threeStarDirectSum";// 三星直选和值

	public static final String threeStarGroupSingle = "threeStarGroupSingle";// 三星组选单式
	public static final String threeStarGroup3Poly = "threeStarGroup3Poly";// 三星组3复式
	public static final String threeStarGroup6Poly = "threeStarGroup6Poly";// 三星组6复式
	public static final String threeStarGroupSum = "threeStarGroupSum";// 三星组选和值

	public static final String twoStarDirectSingle = "twoStarDirectSingle";// 二星直选单式
	public static final String twoStarDirectPoly = "twoStarDirectPoly";// 二星直选复式
	public static final String twoStarDirectSum = "twoStarDirectSum";// 二星直选和值

	public static final String twoStarGroupSingle = "twoStarGroupSingle";// 二星组选单式
	public static final String twoStarGroupPoly = "twoStarGroupPoly";// 二星组选复式
	public static final String twoStarGroupDraw = "twoStarGroupDraw";// 二星组选胆拖
	public static final String twoStarGroupSum = "twoStarGroupSum";// 二星组选和值

	public static final String renOne = "renOne";// 任选1
	public static final String renTwo = "renTwo";// 任选2
	public static final String direct = "direct";// 单选定位
	public static final String directSingle = "directSingle";// 单选单式
	public static final String directPoly = "directPoly";// 单选复式

	public static final String oneStar = "oneStar";// 一星

	public static final String draw = "draw";// 胆拖

	public static final String sxPoly = "sxPoly";// 大乐透 生肖

	public static final String oneSingle = "oneSingle";// 任选前一单式
	public static final String onePoly = "onePoly";// 任选前一复式
	public static final String twoSingle = "twoSingle";// 任选二单式
	public static final String twoPoly = "twoPoly";// 任选二复式
	public static final String twoDirectSingle = "twoDirectSingle";// 前二直选单式
	public static final String twoDirectPoly = "twoDirectPoly";// 前二直选复式
	public static final String twoDirect = "twoDirect";// 前二直选定位
	public static final String twoGroupSingle = "twoGroupSingle";// 前二组选单式
	public static final String twoGroupPoly = "twoGroupPoly";// 前二组选复式

	public static final String threeSingle = "threeSingle";// 任选三单式
	public static final String threePoly = "threePoly";// 任选三复式
	public static final String threeDirectSingle = "threeDirectSingle";// 前三直选单式
	public static final String threeDirectPoly = "threeDirectPoly";// 前三直选复式
	public static final String threeGroupSingle = "threeGroupSingle";// 前三组选单式
	public static final String threeGroupPoly = "threeGroupPoly";// 前三组选复式
	public static final String threeDirect = "threeDirect";// 前三组选定位
	public static final String fourSingle = "fourSingle";// 任选四单式
	public static final String fourPoly = "fourPoly";// 任选四复式
	public static final String fiveSingle = "fiveSingle";// 任选五单式
	public static final String fivePoly = "fivePoly";// 任选五复式
	public static final String sixSingle = "sixSingle";// 任选六单式
	public static final String sixPoly = "sixPoly";// 任选六复式
	public static final String sevenSingle = "sevenSingle";// 任选七单式
	public static final String sevenPoly = "sevenPoly";// 任选七复式
	public static final String eightSingle = "eightSingle";// 任选八单式
	public static final String eightPoly = "eightPoly";// 任选八复式

	public static final String twoDraw = "twoDraw";// 任选前二胆拖

	public static final String lottoSingle = "lottoSingle";// 大乐透单式
	public static final String lottoPoly = "lottoPoly";// 大乐透复式

	public static final String pl5Single = "pl5Single";// 排列5单式
	public static final String pl5Poly = "pl5Poly";// 排列5复式

	public static final String PADDING_2WEINUMBER = " ";// 2位数，同一位的数字
	public static final String PADDING_1WEINUMBER = "";// 1位数，同一位的数字
	// public static final String PADDING_1WEINUMBER =
	// PADDING_1WEINUMBER;// 1位数，同一位的数字
	// public static final String PADDING_2WEINUMBER =
	// PADDING_2WEINUMBER;// 2位数，同一位的数字
	public static final String PADDING_WEI = "-";// 位数split分割时注意要用\\|
	// public static final String PADDING_WEI = "-";
	public static final String PADDING_QU = " + ";// 前后区,split分割时注意要用\\+
	// public static final String PADDING_QU = " \\+ ";//
	// 前后区,split分割时注意要用\\+
	public static final String PADDING_EMPTY = "*";// 空的

	// public static final String PADDING_EMPTY = "\\*";// 空的

	/**
	 * 页面显示格式转换成方案保存格式
	 * 
	 * @param lotteryId
	 *            :
	 * @param schemeNumbers
	 *            :
	 * @return
	 */
	public static String converFormat(int lotteryId, String schemeNumbers) {
		String result = null;
		switch (lotteryId) {
		case ID_SHUANGSEQIU:
			result = getShuangseqiu(schemeNumbers);
			break;
		case ID_FUCAI3D:
			result = getFucai3D(schemeNumbers);
			break;
		case ID_QILECAI:
			result = getQilecai(schemeNumbers);
			break;
		case ID_XINSHISHICAI:
			result = getXinshishicai(schemeNumbers);
			break;
		case ID_LAO11XUAN5:
			result = getLao11xuan5(schemeNumbers);
			break;
		case ID_XIN11XUAN5:// 新老时时彩一样
			result = getLao11xuan5(schemeNumbers);
			break;
		case ID_FUCAI15XUAN5:
			result = getFucai15xuan5(schemeNumbers);
			break;
		case ID_QIXINGCAI:
			result = getQixingcai(schemeNumbers);
			break;
		case ID_DALETOU:
			result = getDaletou(schemeNumbers);
			break;
		case ID_PAILIE3:// 排列5和排列3一样
			result = getPailie3pailie5(schemeNumbers);
			break;
		case ID_QUANGUO22XUAN5:
			result = getQuanguo22xuan5(schemeNumbers);
			break;
		case ID_TICAI6JIA1:
			result = getTicai6jia1(schemeNumbers);
			break;
		case ID_TICAI20XUAN5:
			result = getTicai20xuan5(schemeNumbers);
			break;
		}
		return result;
	}

	private static String getTicai20xuan5(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer singleResult = createStringBuffer(single, list);
		StringBuffer polyResult = createStringBuffer(poly, list);
		StringBuffer drawResult = createStringBuffer(draw, list);
		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(single)) {
				if (!isEmpty(singleResult)) {
					singleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				singleResult.append(value);
			} else if (key.equals(poly)) {
				if (!isEmpty(polyResult)) {
					polyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				polyResult.append(value.trim());
			} else if (key.equals(draw)) {
				if (!isEmpty(drawResult)) {
					drawResult.append("|");
				}
				value = value.replace(")", "#");
				value = value.replace("(", "");
				value = value.replace(PADDING_2WEINUMBER, ",");
				drawResult.append(value);
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	private static String getTicai6jia1(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer singleResult = createStringBuffer(single, list);
		StringBuffer polyResult = createStringBuffer(poly, list);
		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(single)) {
				if (!isEmpty(singleResult)) {
					singleResult.append(",");
				}
				value = myReplace(value, PADDING_WEI, "");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				singleResult.append(value);
			} else if (key.equals(poly)) {
				if (!isEmpty(polyResult)) {
					polyResult.append("|");
				}
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				polyResult.append(value.trim());
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	private static String getQuanguo22xuan5(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer singleResult = createStringBuffer(single, list);
		StringBuffer polyResult = createStringBuffer(poly, list);
		StringBuffer drawResult = createStringBuffer(draw, list);
		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(single)) {
				if (!isEmpty(singleResult)) {
					singleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				singleResult.append(value);
			} else if (key.equals(poly)) {
				if (!isEmpty(polyResult)) {
					polyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				polyResult.append(value);
			} else if (key.equals(draw)) {
				if (!isEmpty(drawResult)) {
					drawResult.append("|");
				}
				value = value.replace(")", "#");
				value = value.replace("(", "");
				value = value.replace(PADDING_2WEINUMBER, ",");
				drawResult.append(value);
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	@SuppressWarnings("unused")
	private static String getPailie3pailie5(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer directResult = createStringBuffer(direct, list);
		StringBuffer directSingleResult = createStringBuffer(directSingle, list);
		StringBuffer directPolyResult = createStringBuffer(directPoly, list);
		StringBuffer groupSingleResult = createStringBuffer(groupSingle, list);
		StringBuffer group3PolyResult = createStringBuffer(group3Poly, list);

		StringBuffer group3DrawResult = createStringBuffer(group3Draw, list);
		StringBuffer group6DrawResult = createStringBuffer(group6Draw, list);

		StringBuffer group6PolyResult = createStringBuffer(group6Poly, list);
		StringBuffer pl5SingleResult = createStringBuffer(pl5Single, list);
		StringBuffer pl5PolyResult = createStringBuffer(pl5Poly, list);
		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(direct)) {
				if (!isEmpty(directResult)) {
					directResult.append("|");
				}
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				directResult.append(value);
			} else if (key.equals(directSingle)) {// 直选单式
				// nop
			} else if (key.equals(directPoly)) {// 直选复式
				// nop
			} else if (key.equals(groupSingle)) {// 组选单式
				// nop
			} else if (key.equals(group3Poly)) {// 组3复式
				if (!isEmpty(group3PolyResult)) {
					group3PolyResult.append("|");
				}
				value = myReplace(value, PADDING_1WEINUMBER, "");
				group3PolyResult.append(value);
			} else if (key.equals(group6Poly)) {// 组6复式
				if (!isEmpty(group6PolyResult)) {
					group6PolyResult.append("|");
				}
				value = myReplace(value, PADDING_1WEINUMBER, "");
				group6PolyResult.append(value);
			} else if (key.equals(pl5Single)) {// 排列5单式(直选定位)
				if (!isEmpty(pl5SingleResult)) {
					pl5SingleResult.append(",");
				}
				value = myReplace(value, PADDING_WEI, "");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				pl5SingleResult.append(value);
			} else if (key.equals(pl5Poly)) {// 排列5复式(直选定位)
				if (!isEmpty(pl5PolyResult)) {
					pl5PolyResult.append("|");
				}
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				pl5PolyResult.append(value);
			} else if (key.equals(group3Draw)) {// 组3胆拖
				if (!isEmpty(group3DrawResult)) {
					group3DrawResult.append("|");
				}
				value = value.replace(")", ",");
				value = value.replace("(", "");
				group3DrawResult.append(value);
			} else if (key.equals(group6Draw)) {// 组6胆拖
				if (!isEmpty(group6DrawResult)) {
					group6DrawResult.append("|");
				}
				value = value.replace(")", ",");
				value = value.replace("(", "");
				group6DrawResult.append(value);
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	private static String getDaletou(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer lottoSingleResult = createStringBuffer(lottoSingle, list);
		StringBuffer lottoPolyResult = createStringBuffer(lottoPoly, list);
		StringBuffer drawResult = createStringBuffer(draw, list);
		StringBuffer sxPolyResult = createStringBuffer(sxPoly, list);

		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(lottoSingle)) {
				if (!isEmpty(lottoSingleResult)) {
					lottoSingleResult.append(",");
				}
				value = myReplace(value, PADDING_QU, " ");
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				lottoSingleResult.append(value);
			} else if (key.equals(lottoPoly) || key.equals(poly)) { // iphone的写错了
																	// 兼容lottoPoly
				if (!isEmpty(lottoPolyResult)) {
					lottoPolyResult.append("|");
				}
				lottoPolyResult.append(myReplace(value, PADDING_QU, "#")
						.replace(PADDING_2WEINUMBER, " "));
			} else if (key.equals(draw)) {// 胆拖
				if (!isEmpty(drawResult)) {
					drawResult.append("|");
				}
				if (value.contains(PADDING_QU + "(")) {// 说明后区胆码是有的
					value = value.replace(")", "#");
					value = value.replace("(", "");
					value = value.replace(PADDING_QU, "#");
					value.replace(PADDING_2WEINUMBER, " ");
				} else {// 后区胆码没有
					value = value.replace(")", "#");
					value = value.replace("(", "");
					value.replace(PADDING_2WEINUMBER, " ");
					value = value.replace(PADDING_QU, "# #");
				}
				drawResult.append(value);
			} else if (key.equals(sxPoly)) {// 生肖
				if (!isEmpty(sxPolyResult)) {
					sxPolyResult.append("|");
				}
				value = value.replace(PADDING_2WEINUMBER, " ");
				sxPolyResult.append(value);
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	/**
	 * 七星彩
	 * */
	private static String getQixingcai(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer singleResult = createStringBuffer(single, list);
		StringBuffer polyResult = createStringBuffer(poly, list);
		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(single)) {
				if (!isEmpty(singleResult)) {
					singleResult.append(",");
				}
				value = myReplace(value, PADDING_WEI, "");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				singleResult.append(value);
			} else if (key.equals(poly)) {
				if (!isEmpty(polyResult)) {
					polyResult.append("|");
				}
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				polyResult.append(value.trim());
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	/**
	 * 福彩15选5
	 */
	private static String getFucai15xuan5(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer singleResult = createStringBuffer(single, list);
		StringBuffer polyResult = createStringBuffer(poly, list);
		StringBuffer drawResult = createStringBuffer(draw, list);
		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(single)) {
				if (!isEmpty(singleResult)) {
					singleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				singleResult.append(value);
			} else if (key.equals(poly)) {
				if (!isEmpty(polyResult)) {
					polyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				polyResult.append(value);
			} else if (key.equals(draw)) {
				if (!isEmpty(drawResult)) {
					drawResult.append("|");
				}
				value = value.replace(")", "#");
				value = value.replace("(", "");
				value = value.replace(PADDING_2WEINUMBER, ",");
				drawResult.append(value);
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	/**
	 * 老11选5
	 */
	@SuppressWarnings("unused")
	private static String getLao11xuan5(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer oneSingleResult = createStringBuffer(oneSingle, list);
		StringBuffer onePolyResult = createStringBuffer(onePoly, list);
		StringBuffer twoSingleResult = createStringBuffer(twoSingle, list);
		StringBuffer twoPolyResult = createStringBuffer(twoPoly, list);
		StringBuffer twoDirectSingleResult = createStringBuffer(
				twoDirectSingle, list);
		StringBuffer twoDirectPolyResult = createStringBuffer(twoDirectPoly,
				list);
		StringBuffer twoDirectResult = createStringBuffer(twoDirect, list);
		StringBuffer twoGroupSingleResult = createStringBuffer(twoGroupSingle,
				list);
		StringBuffer twoGroupPolyResult = createStringBuffer(twoGroupPoly, list);
		StringBuffer threeSingleResult = createStringBuffer(threeSingle, list);
		StringBuffer threePolyResult = createStringBuffer(threePoly, list);
		StringBuffer threeDirectSingleResult = createStringBuffer(
				threeDirectSingle, list);
		StringBuffer threeDirectPolyResult = createStringBuffer(
				threeDirectPoly, list);
		StringBuffer threeDirectResult = createStringBuffer(threeDirect, list);

		StringBuffer threeGroupSingleResult = createStringBuffer(
				threeGroupSingle, list);
		StringBuffer threeGroupPolyResult = createStringBuffer(threeGroupPoly,
				list);
		StringBuffer fourSingleResult = createStringBuffer(fourSingle, list);
		StringBuffer fourPolyResult = createStringBuffer(fourPoly, list);
		StringBuffer fiveSingleResult = createStringBuffer(fiveSingle, list);
		StringBuffer fivePolyResult = createStringBuffer(fivePoly, list);
		StringBuffer sixSingleResult = createStringBuffer(sixSingle, list);
		StringBuffer sixPolyResult = createStringBuffer(sixPoly, list);
		StringBuffer sevenSingleResult = createStringBuffer(sevenSingle, list);
		StringBuffer sevenPolyResult = createStringBuffer(sevenPoly, list);
		StringBuffer eightSingleResult = createStringBuffer(eightSingle, list);
		StringBuffer eightPolyResult = createStringBuffer(eightPoly, list);
		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(oneSingle)) {
				if (!isEmpty(oneSingleResult)) {
					oneSingleResult.append(",");
				}
				oneSingleResult.append(value);
			} else if (key.equals(onePoly)) {
				if (!isEmpty(onePolyResult)) {
					onePolyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				onePolyResult.append(value);
			} else if (key.equals(twoSingle)) {
				if (!isEmpty(twoSingleResult)) {
					twoSingleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				twoSingleResult.append(value);
			} else if (key.equals(twoPoly)) {
				if (!isEmpty(twoPolyResult)) {
					twoPolyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				twoPolyResult.append(value);
			} else if (key.equals(twoDraw)) {
				// NOP
			} else if (key.equals(twoDirectSingle)) {// 前二直选单式
				// nop
			} else if (key.equals(twoDirectPoly)) {// 前二直选复式
				// nop
			} else if (key.equals(twoDirect)) {// 前二直选定位
				if (!isEmpty(twoDirectResult)) {
					twoDirectResult.append("|");
				}
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				twoDirectResult.append(value);
			} else if (key.equals(twoGroupSingle)) {
				if (!isEmpty(twoGroupSingleResult)) {
					twoGroupSingleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				twoGroupSingleResult.append(value);
			} else if (key.equals(twoGroupPoly)) {
				if (!isEmpty(twoGroupPolyResult)) {
					twoGroupPolyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				twoGroupPolyResult.append(value);
			} else if (key.equals(threeSingle)) {
				if (!isEmpty(threeSingleResult)) {
					threeSingleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				threeSingleResult.append(value);
			} else if (key.equals(threePoly)) {
				if (!isEmpty(threePolyResult)) {
					threePolyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				threePolyResult.append(value);
			} else if (key.equals(threeDirectSingle)) {// 前三直选单式
				// nop
			} else if (key.equals(threeDirectPoly)) {// 前三直选复式
				// nop
			} else if (key.equals(threeDirect)) {// 前三直选定位
				if (!isEmpty(threeDirectResult)) {
					threeDirectResult.append("|");
				}
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				threeDirectResult.append(value);
			} else if (key.equals(threeGroupSingle)) {
				if (!isEmpty(threeGroupSingleResult)) {
					threeGroupSingleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				threeGroupSingleResult.append(value);
			} else if (key.equals(threeGroupPoly)) {
				if (!isEmpty(threeGroupPolyResult)) {
					threeGroupPolyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				threeGroupPolyResult.append(value);
			} else if (key.equals(fourSingle)) {
				if (!isEmpty(fourSingleResult)) {
					fourSingleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				fourSingleResult.append(value);
			} else if (key.equals(fourPoly)) {
				if (!isEmpty(fourPolyResult)) {
					fourPolyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				fourPolyResult.append(value);
			} else if (key.equals(fiveSingle)) {
				if (!isEmpty(fiveSingleResult)) {
					fiveSingleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				fiveSingleResult.append(value);
			} else if (key.equals(fivePoly)) {
				if (!isEmpty(fivePolyResult)) {
					fivePolyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				fivePolyResult.append(value);
			} else if (key.equals(sixSingle)) {
				if (!isEmpty(sixSingleResult)) {
					sixSingleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				sixSingleResult.append(value);
			} else if (key.equals(sixPoly)) {
				if (!isEmpty(sixPolyResult)) {
					sixPolyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				sixPolyResult.append(value);
			} else if (key.equals(sevenSingle)) {
				if (!isEmpty(sevenSingleResult)) {
					sevenSingleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				sevenSingleResult.append(value);
			} else if (key.equals(sevenPoly)) {
				if (!isEmpty(sevenPolyResult)) {
					sevenPolyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				sevenPolyResult.append(value);
			} else if (key.equals(eightSingle)) {
				if (!isEmpty(eightSingleResult)) {
					eightSingleResult.append(",");
				}
				value = myReplace(value, PADDING_2WEINUMBER, " ");
				eightSingleResult.append(value);
			} else if (key.equals(eightPoly)) {
				if (!isEmpty(eightPolyResult)) {
					eightPolyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				eightPolyResult.append(value);
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	/**
	 * 新时时彩
	 */
	@SuppressWarnings("unused")
	private static String getXinshishicai(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer dxdsResult = createStringBuffer(dxds, list);
		StringBuffer fiveStarAllSingleResult = createStringBuffer(
				fiveStarAllSingle, list);
		StringBuffer fiveStarAllPolyResult = createStringBuffer(
				fiveStarAllPoly, list);
		StringBuffer fiveStarSingleResult = createStringBuffer(fiveStarSingle,
				list);
		StringBuffer fiveStarPolyResult = createStringBuffer(fiveStarPoly, list);

		StringBuffer fourStarSingleResult = createStringBuffer(fourStarSingle,
				list);
		StringBuffer fourStarPolyResult = createStringBuffer(fourStarPoly, list);
		StringBuffer threeStarDirectSingleResult = createStringBuffer(
				threeStarDirectSingle, list);
		StringBuffer threeStarDirectPolyResult = createStringBuffer(
				threeStarDirectPoly, list);
		StringBuffer threeStarDirectDrawResult = createStringBuffer(
				threeStarDirectDraw, list);

		StringBuffer threeStarDirectSumResult = createStringBuffer(
				threeStarDirectSum, list);
		StringBuffer threeStarGroupSingleSumResult = createStringBuffer(
				threeStarGroupSingle, list);
		StringBuffer threeStarGroup3PolyResult = createStringBuffer(
				threeStarGroup3Poly, list);
		StringBuffer threeStarGroup6PolyResult = createStringBuffer(
				threeStarGroup6Poly, list);
		StringBuffer threeStarGroupSumResult = createStringBuffer(
				threeStarGroupSum, list);

		StringBuffer twoStarDirectSingleResult = createStringBuffer(
				twoStarDirectSingle, list);
		StringBuffer twoStarDirectPolyResult = createStringBuffer(
				twoStarDirectPoly, list);
		StringBuffer twoStarDirectSumResult = createStringBuffer(
				twoStarDirectSum, list);
		StringBuffer twoStarGroupSingleResult = createStringBuffer(
				twoStarGroupSingle, list);
		StringBuffer twoStarGroupPolyResult = createStringBuffer(
				twoStarGroupPoly, list);

		StringBuffer twoStarGroupDrawResult = createStringBuffer(
				twoStarGroupDraw, list);
		StringBuffer twoStarGroupSumResult = createStringBuffer(
				twoStarGroupSum, list);
		StringBuffer oneStarResult = createStringBuffer(oneStar, list);
		StringBuffer renOneResult = createStringBuffer(renOne, list);
		StringBuffer renTwoResult = createStringBuffer(renTwo, list);

		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(dxds)) {// 大小单双，客户端为:大 双|小
				value = myReplace(value, PADDING_WEI, "");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				value = myReplace(value, "大", "A");
				value = myReplace(value, "小", "B");
				value = myReplace(value, "单", "C");
				value = myReplace(value, "双", "D");
				if (!isEmpty(dxdsResult)) {
					dxdsResult.append("|");
				}
				dxdsResult.append(value);
			} else if (key.equals(fiveStarAllSingle)) {// 五星通选单式
				value = myReplace(value, PADDING_WEI, "");
				if (!isEmpty(fiveStarAllSingleResult)) {
					fiveStarAllSingleResult.append(",");
				}
				fiveStarAllSingleResult.append(value);
			} else if (key.equals(fiveStarAllPoly)) {// 五星通选复式
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(fiveStarAllPolyResult)) {
					fiveStarAllPolyResult.append("|");
				}
				fiveStarAllPolyResult.append(value);
			} else if (key.equals(fiveStarSingle)) {// 五星单式
				value = myReplace(value, PADDING_WEI, "");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(fiveStarSingleResult)) {
					fiveStarSingleResult.append(",");
				}
				fiveStarSingleResult.append(value);
			} else if (key.equals(fiveStarPoly)) {// 五星复式
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(fiveStarPolyResult)) {
					fiveStarPolyResult.append("|");
				}
				fiveStarPolyResult.append(value);
			} else if (key.equals(fourStarSingle)) {// 四星单式
				value = myReplace(value, PADDING_WEI, "");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(fourStarSingleResult)) {
					fourStarSingleResult.append(",");
				}
				fourStarSingleResult.append(value);
			} else if (key.equals(fourStarPoly)) {// 四星复式
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(fourStarPolyResult)) {
					fourStarPolyResult.append("|");
				}
				fourStarPolyResult.append(value);
			} else if (key.equals(threeStarDirectSingle)) {// 三星直选单式
				value = myReplace(value, PADDING_WEI, "");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(threeStarDirectSingleResult)) {
					threeStarDirectSingleResult.append(",");
				}
				threeStarDirectSingleResult.append(value);
			} else if (key.equals(threeStarDirectPoly)) {// 三星直选复式
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(threeStarDirectPolyResult)) {
					threeStarDirectPolyResult.append("|");
				}
				threeStarDirectPolyResult.append(value);
			} else if (key.equals(threeStarDirectDraw)) {// 三星直选胆拖
				// nop
			} else if (key.equals(threeStarDirectSum)) {// 三星直选和值
				// nop
			} else if (key.equals(threeStarGroupSingle)) {// 三星组选单式
				// nop
			} else if (key.equals(threeStarGroup3Poly)) {// 组三复式
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(threeStarGroup3PolyResult)) {
					threeStarGroup3PolyResult.append("|");
				}
				threeStarGroup3PolyResult.append(value);
			} else if (key.equals(threeStarGroup6Poly)) {// 组六复式
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(threeStarGroup6PolyResult)) {
					threeStarGroup6PolyResult.append("|");
				}
				threeStarGroup6PolyResult.append(value);
			} else if (key.equals(threeStarGroupSum)) {// 三星组选和值
				// nop
			} else if (key.equals(twoStarDirectSingle)) {// 二星直选单式
				value = myReplace(value, PADDING_WEI, "");
				if (!isEmpty(twoStarDirectSingleResult)) {
					twoStarDirectSingleResult.append(",");
				}
				twoStarDirectSingleResult.append(value);
			} else if (key.equals(twoStarDirectPoly)) {// 二星直选复式
				value = myReplace(value, PADDING_WEI, "-");
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(twoStarDirectPolyResult)) {
					twoStarDirectPolyResult.append("|");
				}
				twoStarDirectPolyResult.append(value);
			} else if (key.equals(twoStarDirectSum)) {// 二星直选和值
				// nop
			} else if (key.equals(twoStarGroupSingle)) {// 二星组选单式
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(twoStarGroupSingleResult)) {
					twoStarGroupSingleResult.append(",");
				}
				twoStarGroupSingleResult.append(value);
			} else if (key.equals(twoStarGroupPoly)) {// 二星组选复式
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(twoStarGroupPolyResult)) {
					twoStarGroupPolyResult.append("|");
				}
				twoStarGroupPolyResult.append(value);
			} else if (key.equals(twoStarGroupDraw)) {// 二星组选胆拖
				// nop
			} else if (key.equals(twoStarGroupSum)) {// 二星组选和值
				// nop
			} else if (key.equals(oneStar)) {// 一星
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(oneStarResult)) {
					oneStarResult.append("|");
				}
				oneStarResult.append(value);
			} else if (key.equals(renOne)) {// 任选1
				value = myReplace(value, PADDING_WEI, ",");
				value = myReplace(value, PADDING_EMPTY, "-");

				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(renOneResult)) {
					renOneResult.append("|");
				}
				renOneResult.append(value);
			} else if (key.equals(renTwo)) {// 任选2
				value = myReplace(value, PADDING_WEI, ",");
				value = myReplace(value, PADDING_EMPTY, "-");

				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(renTwoResult)) {
					renTwoResult.append("|");
				}
				renTwoResult.append(value);
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	/**
	 * 七乐彩
	 */
	private static String getQilecai(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer singleResult = createStringBuffer(single, list);
		StringBuffer polyResult = createStringBuffer(poly, list);
		StringBuffer drawResult = createStringBuffer(draw, list);
		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			value = myReplace(value, PADDING_2WEINUMBER, " ");
			if (key.equals(single)) {// 单式 客户端为: 03 05 09 10 20 24 26
				if (!isEmpty(singleResult)) {
					singleResult.append(",");
				}
				singleResult.append(value);
			} else if (key.equals(poly)) {// 复式 客户端为: 03 05 09 10 20 24 26 29
				if (!isEmpty(polyResult)) {
					polyResult.append("|");
				}
				value = myReplace(value, PADDING_2WEINUMBER, ",");
				polyResult.append(value);
			} else if (key.equals(draw)) {// 胆拖
				if (!isEmpty(drawResult)) {
					drawResult.append("|");
				}
				value = value.replace(")", "#");
				value = value.replace("(", "");
				value = value.replace(PADDING_2WEINUMBER, ",");
				drawResult.append(value);
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	private static String[] getAllHaoma(String allHaoma) {
		return allHaoma.split(";");
	}

	private static String[] getKeyValue(String oneHaoma) {
		return oneHaoma.split("=");
	}

	private static String myReplace(String value, String oldStr, String newStr) {
		if (oldStr == null || oldStr.length() == 0) {
			return value;
		} else {
			return value.trim().replace(oldStr, newStr);
		}
	}

	/**
	 * 福彩3D
	 */
	@SuppressWarnings("unused")
	private static String getFucai3D(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer directResult = createStringBuffer(direct, list);
		StringBuffer directSingleResult = createStringBuffer(directSingle, list);
		StringBuffer directPolyResult = createStringBuffer(directPoly, list);
		StringBuffer groupSingleResult = createStringBuffer(groupSingle, list);
		StringBuffer group3PolyResult = createStringBuffer(group3Poly, list);
		StringBuffer group6PolyResult = createStringBuffer(group6Poly, list);
		StringBuffer directDrawResult = createStringBuffer(directDraw, list);
		StringBuffer group3DrawResult = createStringBuffer(group3Draw, list);
		StringBuffer group6DrawResult = createStringBuffer(group6Draw, list);
		StringBuffer directSumResult = createStringBuffer(directSum, list);
		StringBuffer group3SumResult = createStringBuffer(group3Sum, list);
		StringBuffer group6SumResult = createStringBuffer(group6Sum, list);
		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(direct)) {// 单选定位，7|3 5 6|8
				value = myReplace(value, PADDING_WEI, "-");//
				value = myReplace(value, PADDING_1WEINUMBER, "");// 空格去掉
				if (!isEmpty(directResult)) {
					directResult.append("|");
				}
				directResult.append(value);
			} else if (key.equals(directSingle)) {// 单选单式
				// nop
			} else if (key.equals(directPoly)) {// 单选复式
				// nop
			} else if (key.equals(groupSingle)) {// 组选单式
				// nop
			} else if (key.equals(group3Poly)) {// 组3复式
				value = myReplace(value, "", "");
				if (!isEmpty(group3PolyResult)) {
					group3PolyResult.append("|");
				}
				group3PolyResult.append(value);
			} else if (key.equals(group6Poly)) {// 组6复式
				value = myReplace(value, PADDING_1WEINUMBER, "");
				if (!isEmpty(group6PolyResult)) {
					group6PolyResult.append("|");
				}
				group6PolyResult.append(value);
			} else if (key.equals(directDraw)) {// 直选胆拖
				// nop
			} else if (key.equals(group3Draw)) {// 组3胆拖
				if (!isEmpty(group3DrawResult)) {
					group3DrawResult.append("|");
				}
				value = value.replace(")", ",");
				value = value.replace("(", "");
				group3DrawResult.append(value);
			} else if (key.equals(group6Draw)) {// 组6胆拖
				if (!isEmpty(group6DrawResult)) {
					group6DrawResult.append("|");
				}
				value = value.replace(")", ",");
				value = value.replace("(", "");
				group6DrawResult.append(value);
			} else if (key.equals(directSum)) {// 直选和值
				// nop
			} else if (key.equals(group3Sum)) {// 组三和值
				// nop
			} else if (key.equals(group6Sum)) {// 组6和值
				// nop
			} else if (key.equals(kdDirect)) {// 直选跨度
				// nop
			} else if (key.equals(kdGroup3)) {// 组三跨度
				// nop
			} else if (key.equals(kdGroup3)) {// 组六跨度
				// nop
			}

		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}

		return result;
	}

	/**
	 * 双色球
	 */
	private static String getShuangseqiu(String allHaoma) {
		String result = null;
		List<StringBuffer> list = new ArrayList<StringBuffer>();
		StringBuffer singleResult = createStringBuffer(single, list);
		StringBuffer polyResult = createStringBuffer(poly, list);
		StringBuffer drawResult = createStringBuffer(draw, list);
		String qianquString = null;
		String houquString = null;
		int plusIndex = 0;// +号位置，用来分割前后区
		String[] all = getAllHaoma(allHaoma);
		for (int i = 0; i < all.length; i++) {
			String[] keyValue = getKeyValue(all[i]);
			String key = keyValue[0];
			String value = keyValue[1];
			if (key.equals(single)) {// 单式
				// 加号变为空格

				value = myReplace(value, PADDING_QU, " ");
				if (!isEmpty(singleResult)) {
					singleResult.append(",");
				}
				singleResult.append(value);
			} else if (key.equals(poly)) {// 复式
				plusIndex = value.indexOf(PADDING_QU);
				qianquString = value.substring(0, plusIndex);
				houquString = value.substring(plusIndex + PADDING_QU.length());
				if (!isEmpty(polyResult)) {
					polyResult.append("|");
				}
				polyResult.append(qianquString);
				polyResult.append("#");
				polyResult.append(houquString);
			} else if (key.equals(draw)) {// 胆拖
				if (!isEmpty(drawResult)) {// 客户端的格式为(10 02 03)07 08 15 12 + 05
					drawResult.append("|");
				}
				value = value.replace(")", "#");
				value = value.replace("(", "");
				value = value.replace(PADDING_QU, "#");
				value = value.trim().replace(PADDING_2WEINUMBER, ",");
				drawResult.append(value);
			}
		}
		for (StringBuffer sb : list) {
			result = addOneType(result, sb);
		}
		return result;
	}

	/**
	 * 生产一个StringBuffer，同时注册到list中，list是为了后面拼接整体字符串方便
	 */
	private static StringBuffer createStringBuffer(String qianzhui,
			List<StringBuffer> list) {
		StringBuffer sb = new StringBuffer(qianzhui + "=");
		list.add(sb);
		return sb;
	}

	private static boolean isEmpty(StringBuffer sb) {
		// 说明还没加值，只有前者如single=
		return sb.toString().endsWith("=");
	}

	/**
	 * 加一种类型的字符串，如当前result=single=01 02 03 04 05 06 07 ,07 06 05 04 03 02 01 sb
	 * = poly=09 08 09 04 03 04 03#12 23
	 * 
	 * @param result
	 * @param sb
	 */
	private static String addOneType(String result, StringBuffer sb) {
		if (isEmpty(sb)) {// 如果此类型为空，则直接返回
			return result;
		}
		if (result != null) {
			result = result + "&";
		} else {
			result = "";
		}
		result = result + sb.toString();
		return result;
	}

}
