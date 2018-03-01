package com.chengyi.app.jingji.basket;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BasketballZhuCalculator {

	private static double minValue = 0;
	private static double maxValue = 0;
	private static double minTemp = 0;
	private static double maxTemp = 0;

	public static double getMinValue() {
		return minValue;
	}

	public static void setMinValue(double minValue) {
		BasketballZhuCalculator.minValue = minValue;
	}

	public static double getMaxValue() {
		return maxValue;
	}

	public static void setMaxValue(double maxValue) {
		BasketballZhuCalculator.maxValue = maxValue;
	}

	// 注数的缓冲列表
//	public static HashMap<Integer, Long> zhuMap = new HashMap<Integer, Long>();

	// 从n个不同的数中取m个数字的组合公式： n!/((n-m)!m!)
	public static long getZHNumber(int n, int m) {
		if (n < m || n <= 0 || m <= 0)
			return 0;
		else if (n == m) {
			return 1;
		} else {
			return getJCNumber(n) / (getJCNumber(n - m) * getJCNumber(m));
		}
	}

	// 计算n的阶乘
	public static long getJCNumber(int n) {
		if ((n < 0) || (n > 19)) {
			return -1;
		}
		if (n == 1)
			return 1;
		return n * getJCNumber(n - 1);
	}

	// 根据过关方式得到可以选择的胆码个数
	public static int getDanNumFromGuan(String kind) {
		int[] temp = guoGuantypeMap.get(kind);
		return temp[0];
	}

//	// 清空注数缓冲列表
//	public static void clearZhuMap() {
//		zhuMap.clear();
//	}

	// 根据所选择的过关方式来计算注数
	public static long getZhuNum(final SparseArray<BasketballOneGame> list,
			String kind) {
		minValue = 0;
		maxValue = 0;
		String[] kindStr = kind.split("串");

		if (Integer.parseInt(kindStr[1]) == 1) {
			// m串1过关
			return getZhuNumByKind(list, Integer.parseInt(kindStr[0]));
		} else {
			int[] temp = guoGuantypeMap.get(kind);
			return getZhuNumMoreKind(list, Integer.parseInt(kindStr[0]), temp);
		}
	}

	// m串n过关方式的算法
	public static long getZhuNumMoreKind(SparseArray<BasketballOneGame> list,
			int selectSize, int[] temp) {
		List<String> danList = new ArrayList<String>();
		List<String> feiDanList = new ArrayList<String>();
		for (int s = 0; s < list.size(); s++) {
			if (list.valueAt(s).isDanPressed)
				danList.add(s + "");
			else
				feiDanList.add(s + "");
		}
		List<List<String>> gameList = getGameList(feiDanList, selectSize
				- danList.size(), 0);
		long sum = 0;
		double mix = 1000000000, max = 0;
		for (int i = 0; i < gameList.size(); i++) {
			List<BasketballOneGame> listTemp = new ArrayList<BasketballOneGame>();
			for (int l = 0; l < danList.size(); l++) {
				listTemp.add(list.valueAt(Integer.parseInt(danList.get(l))));
			}
			for (int t = 0; t < gameList.get(i).size(); t++) {
				listTemp.add(list.valueAt(Integer.parseInt(gameList.get(i).get(t))));
			}
			long num = 0;
			double mixOne = 1000000000, maxOne = 0;
			for (int j = 0; j < temp.length; j++) {
				num += getZhuNumByKind(listTemp, temp[j]);
				maxOne += maxValue;
				mixOne = mixOne > minValue ? minValue : mixOne;
			}
			mix = mix > mixOne ? mixOne : mix;
			max += maxOne;
			sum += num;
		}
		minValue = mix;
		maxValue = max;
		return sum;
	}
	
	

	/****
	 * 利用递归进行排列组合算法，所选择的比赛按照排列组合模式来拆分
	 * 
	 * @param m
	 *            从list中要获取的比赛场次
	 * @param begin
	 *            起始位置
	 */
	public static List<List<String>> getGameList(List<String> list, int m,
			int begin) {
		List<List<String>> gameList = new ArrayList<List<String>>();
		for (int i = begin; i <= list.size() - m; i++) {
			if (m == 1) {
				List<String> listTemp = new ArrayList<String>();
				listTemp.add(list.get(i));
				gameList.add(listTemp);
			} else {
				List<List<String>> gameListTemp = getGameList(list, m - 1,
						i + 1);
				for (int j = 0; j < gameListTemp.size(); j++) {
					List<String> listTemp = new ArrayList<String>();
					listTemp.add(list.get(i));
					listTemp.addAll(gameListTemp.get(j));
					gameList.add(listTemp);
				}
			}
		}
		return gameList;
	}
	
	

	// 根据m串1来计算注数
	public static long getZhuNumByKind(SparseArray<BasketballOneGame> list, int kind) {
		// m场比赛m串1过关方式
		if (kind == list.size()) {
			return getNum(list);
		} else {
			List<BasketballOneGame> listDan = new ArrayList<BasketballOneGame>();
			List<BasketballOneGame> listTuo = new ArrayList<BasketballOneGame>();
			for (int i = 0; i < list.size(); i++) {
				BasketballOneGame game = list.valueAt(i);
				if (game.isDanPressed) {
					listDan.add(game);
				} else {
					listTuo.add(game);
				}
			}
			long num = 0;
			num = combine(listTuo, listTuo.size(), kind - listDan.size());
			long sum = getNum(listDan) * num;
			minValue = minValue * getMinTemp();
			maxValue = maxValue * getMaxTemp();
			return sum;
		}
	}
	
	// 根据m串1来计算注数
		public static long getZhuNumByKind(List<BasketballOneGame> list, int kind) {
			// m场比赛m串1过关方式
			if (kind == list.size()) {
				return getNum(list);
			} else {
				List<BasketballOneGame> listDan = new ArrayList<BasketballOneGame>();
				List<BasketballOneGame> listTuo = new ArrayList<BasketballOneGame>();
				for (int i = 0; i < list.size(); i++) {
					BasketballOneGame game = list.get(i);
					if (game.isDanPressed) {
						listDan.add(game);
					} else {
						listTuo.add(game);
					}
				}
				long num = 0;
				num = combine(listTuo, listTuo.size(), kind - listDan.size());
				long sum = getNum(listDan) * num;
				minValue = minValue * getMinTemp();
				maxValue = maxValue * getMaxTemp();
				return sum;
			}
		}

	// 从n中选m个数
	// a. 首先从n个数中选取编号最大的数，然后在剩下的n-1个数里面选取m-1个数，直到从n-(m-1)个数中选取1个数为止。
	// b. 从n个数中选取编号次小的一个数，继续执行1步，直到当前可选编号最大的数为m。
	public static long combine(List<BasketballOneGame> list, int n, int m) {
		long sum = 0;
		minTemp = 1000000000;
		maxTemp = 0;
		for (int i = n; i >= m; i--) // 注意这里的循环范围
		{
			double minBak = getMinTemp();
			double maxBak = getMaxTemp();
			double mix, max;
			try {
				BasketballOneGame game = list.get(i - 1);
				mix = 1;
				max = 1;
				if (m > 1) {
					sum += (game.SFFlag+ 1) * combine(list, i - 1, m - 1);
					mix = game.minSP * getMinTemp();
					max = game.maxSP * getMaxTemp();
				} else {
					sum += (game.SFFlag + 1);
					mix = game.minSP;
					max = game.maxSP;
				}
				minBak = minBak > mix ? mix : minBak;
				maxBak += max;
				minTemp = minBak;
				maxTemp = maxBak;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return sum;
	}

	public static double getMinTemp() {
		return minTemp;
	}

	public static void setMinTemp(double minTemp) {
		BasketballZhuCalculator.minTemp = minTemp;
	}

	public static double getMaxTemp() {
		return maxTemp;
	}

	public static void setMaxTemp(double maxTemp) {
		BasketballZhuCalculator.maxTemp = maxTemp;
	}

	public static long getNum(SparseArray<BasketballOneGame> list) {
		long sum = 1;
		minValue = 1;
		maxValue = 1;
		for (int i = 0; i < list.size(); i++) {
			BasketballOneGame game = list.valueAt(i);
			sum *= (game.SFFlag + 1);
			minValue *= game.minSP;
			maxValue *= game.maxSP;
		}
		return sum;
	}
	
	public static long getNum(List<BasketballOneGame> list) {
		long sum = 1;
		minValue = 1;
		maxValue = 1;
		for (int i = 0; i < list.size(); i++) {
			BasketballOneGame game = list.get(i);
			sum *= (game.SFFlag + 1);
			minValue *= game.minSP;
			maxValue *= game.maxSP;
		}
		return sum;
	}

	// 存储各种过关方式对应的最终过关方式，比如4串5等同于3串1加4串1;
	private final static HashMap<String, int[]> guoGuantypeMap = new HashMap<>();
	static {
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
	}
}
