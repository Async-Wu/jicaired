package com.chengyi.app.model.wanfa;

import com.chengyi.R;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.widget.TouzhuButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Shengxiao extends AbsWanfa {
	private List<String> shengxiaoList;
	private int N;
	private int Max;

	// 总共M个球,N个球为一注

	public int getMax() {
		return Max;
	}

	public void setMax(int max) {
		Max = max;
	}

	public List<String> getShengxiaoList() {
		return shengxiaoList;
	}

	public void setShengxiaoList(List<String> shengxiaoList) {
		this.shengxiaoList = shengxiaoList;
	}

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
		int totalSelected = getSelectedBtnCount(list.get(0));
		int afterQianquTotalSelected = totalSelected + 1;
		if (afterQianquTotalSelected > getMax()) {
			String s = String.format(
					getAppContext().getString(R.string.zuiduozhinengxuanze),
					getMax());
			if (getTouzhuListener() != null) {
				getTouzhuListener().onCheck(s);
			}
			return;
		}
		int total = CaipiaoUtil
				.getCombination(afterQianquTotalSelected, getN());
		if (total > caipiao.getMaxTouzhuCount()) {
			if (getTouzhuListener() != null) {
				getTouzhuListener().onCheck(getChaoguoMaxCountWarning());
			}
		} else {
			if (getTouzhuListener() != null) {
				// btn.setSelected(true);一定要放在onTouzhuCountChange前
				btn.setSelected(true);
				getTouzhuListener().onTouzhuCountChange();
			}
		}
	}

	@Override
	public int getTouzhuCount() {
		int totalSelected = getSelectedBtnCount(caipiao.getListTouzuList().get(
				0));
		return CaipiaoUtil.getCombination(totalSelected, getN());
	}

	@Override
	public String getSubmitString() {
		return null;
	}

	@Override
	public TouzhuquerenData randomTouzhu() {
		List<List<Boolean>> bList = new ArrayList<List<Boolean>>();
		int[] randomArray = CaipiaoUtil.getRandomArray(getShengxiaoList()
				.size(), getN());

		List<Boolean> qList = new ArrayList<Boolean>();
		for (int i = 0; i < getShengxiaoList().size(); i++) {
			qList.add(false);
		}
		for (int i = 0; i < randomArray.length; i++) {
			qList.set(randomArray[i], true);
		}
		bList.add(qList);
		List<String> quanquList = CaipiaoUtil.getStringArray(
				getShengxiaoList(), randomArray);

		String s = "";
		String pad = quanquList.get(0).length() == 2 ? CaipiaoConst.PADDING_2WEINUMBER
				: CaipiaoConst.PADDING_1WEINUMBER;
		for (int i = 0; i < quanquList.size(); i++) {
			if (i == quanquList.size() - 1) {
				s = s + quanquList.get(i);
			} else {
				s = s + quanquList.get(i) + pad;
			}
		}
		s = CaipiaoUtil.trimLast(s);

		TouzhuquerenData data = getDefaultTouzhu();
		data.setHasHouqu(false);
		data.setTouzhuhaoma(s);
		data.setListTouzuList2(bList);
		return data;
	}
}
