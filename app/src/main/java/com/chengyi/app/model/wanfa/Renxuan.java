package com.chengyi.app.model.wanfa;

import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.widget.TouzhuButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Renxuan extends AbsWanfa {
	private int N;

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	@Override
	public void check(TouzhuButton btn) {
		if (btn.isSelected()) {
			// btn.setSelected(false);一定要放在onTouzhuCountChange前
			btn.setSelected(false);
			if (getTouzhuListener() != null) {
				getTouzhuListener().onTouzhuCountChange();
			}
			return;
		}
		List<List<TouzhuButton>> list = caipiao.getListTouzuList();

		int[] selectedCount = new int[list.size()];
		int thisBtn = 0;
		for (int i = 0; i < selectedCount.length; i++) {
			selectedCount[i] = getSelectedBtnCount(list.get(i));
			if (list.get(i).contains(btn)) {
				thisBtn = i;
			}
		}
		// 如果btn被选中后
		selectedCount[thisBtn] = selectedCount[thisBtn] + 1;
		List<Integer> temp = new ArrayList<Integer>();// 有被选中的行的选中个数
		for (int i = 0; i < selectedCount.length; i++) {
			if (selectedCount[i] > 0) {
				temp.add(selectedCount[i]);
			}
		}
		int totalCount = 0;
		if (temp.size() >= N) {
			List<int[]> result = CaipiaoUtil.getCombination2(temp.size(), N);
			for (int i = 0; i < result.size(); i++) {
				int tempCount = 1;
				int[] tempRow = result.get(i);
				for (int j = 0; j < tempRow.length; j++) {
					if (tempRow[j] == 1) {
						tempCount = tempCount * temp.get(j);
					}
				}
				totalCount = totalCount + tempCount;
			}
		}

		if (totalCount > caipiao.getMaxTouzhuCount()) {
			if (getTouzhuListener() != null) {
				getTouzhuListener().onCheck(getChaoguoMaxCountWarning());
			}
		} else {
			btn.setSelected(true);
			if (getTouzhuListener() != null) {
				getTouzhuListener().onTouzhuCountChange();
			}
		}
	}

	@Override
	public int getTouzhuCount() {
		List<List<TouzhuButton>> list = caipiao.getListTouzuList();

		int[] selectedCount = new int[list.size()];
		for (int i = 0; i < selectedCount.length; i++) {
			selectedCount[i] = getSelectedBtnCount(list.get(i));
		}
		List<Integer> temp = new ArrayList<Integer>();// 有被选中的行的选中个数
		for (int i = 0; i < selectedCount.length; i++) {
			if (selectedCount[i] > 0) {
				temp.add(selectedCount[i]);
			}
		}
		int totalCount = 0;
		if (temp.size() >= N) {
			List<int[]> result = CaipiaoUtil.getCombination2(temp.size(), N);
			for (int i = 0; i < result.size(); i++) {
				int tempCount = 1;
				int[] tempRow = result.get(i);
				for (int j = 0; j < tempRow.length; j++) {
					if (tempRow[j] == 1) {
						tempCount = tempCount * temp.get(j);
					}
				}
				totalCount = totalCount + tempCount;
			}
		}
		return totalCount;
	}

	@Override
	public String getSubmitString() {
		return null;
	}

	@Override
	public TouzhuquerenData randomTouzhu() {
		List<List<Boolean>> bList = new ArrayList<List<Boolean>>();
		int qianquNum = caipiao.getQianquMinCount();
		int qianquOneRowCount = caipiao.getQianquList().size();
		String s = "";
		Random random = new Random();
		List<Boolean> qList = null;
		for (int i = 0; i < qianquNum; i++) {
			qList = new ArrayList<Boolean>();
			for (int j = 0; j < qianquOneRowCount; j++) {
				qList.add(false);
			}
			bList.add(qList);
		}

		int[] array = CaipiaoUtil
				.getRandomArray(caipiao.getQianquMinCount(), N);
		for (int i = 0; i < qianquNum; i++) {
			boolean isReady = false;
			for (int j = 0; j < array.length; j++) {
				if (i == array[j]) {
					isReady = true;
					break;
				}
			}
			if (isReady) {
				int c = random.nextInt(qianquOneRowCount);
				s = s + caipiao.getQianquList().get(c)
						+ CaipiaoConst.PADDING_WEI;
				bList.get(i).set(c, true);
			} else {
				s = s + CaipiaoConst.PADDING_EMPTY;
				s = s + CaipiaoConst.PADDING_WEI;
			}
		}
		s = CaipiaoUtil.trimLast(s);
		if (s.startsWith(CaipiaoConst.PADDING_WEI)) {
			s = s.replaceFirst(CaipiaoConst.PADDING_WEI, "");
		} else if (s.endsWith(CaipiaoConst.PADDING_WEI)) {
			s = s.substring(0, s.length() - 1);
		}

		TouzhuquerenData data = getDefaultTouzhu();
		data.setHasHouqu(false);
		data.setTouzhuhaoma(s);
		data.setListTouzuList2(bList);
		return data;
	}
}
