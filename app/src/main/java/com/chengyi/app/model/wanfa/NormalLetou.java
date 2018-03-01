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
public class  NormalLetou extends AbsWanfa {

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
		if (list.size() == 2) {
			int qianxuTotalSelected = getSelectedBtnCount(list.get(0));
			int houquTotalSelected = getSelectedBtnCount(list.get(1));
			int afterQianquTotalSelected = (list.get(0).contains(btn) ? 1 : 0)
					+ qianxuTotalSelected;
			int afterHouquTotalSelected = (list.get(1).contains(btn) ? 1 : 0)
					+ houquTotalSelected;

			// 验证是否超过最大可选个数
			if (!letouIfSelectedCheckMaxSelectedCount(afterQianquTotalSelected,
					afterHouquTotalSelected)) {
				return;
			}
			// 验证最大可投注数量
			letouIfSelectedCheckMaxTouzhuCount(afterQianquTotalSelected,
					afterHouquTotalSelected, btn);
		} else {
			int qianxuTotalSelected = getSelectedBtnCount(list.get(0));
			int afterQianquTotalSelected = qianxuTotalSelected + 1;
			// 前区选择个数超过规定
			if (afterQianquTotalSelected > caipiao.getQianquMaxCount()) {
				String s = String
						.format(getAppContext().getString(
								R.string.zuiduozhinengxuanze),
								caipiao.getQianquMaxCount());
				if (getTouzhuListener() != null) {
					getTouzhuListener().onCheck(s);
				}
				return;
			}
			// 总注数超过规定
			int total = CaipiaoUtil.getCombination(afterQianquTotalSelected,
					caipiao.getQianquMinCount());
			if (total > caipiao.getMaxTouzhuCount()) {
				getTouzhuListener().onCheck(getChaoguoMaxCountWarning());
				return;
			}
			btn.setSelected(true);
			getTouzhuListener().onTouzhuCountChange();
		}
	}

	@Override
	public int getTouzhuCount() {
		int total = 0;
		List<List<TouzhuButton>> list = caipiao.getListTouzuList();
		if (list.size() == 2) {
			int qianxuTotalSelected = getSelectedBtnCount(list.get(0));
			int houquTotalSelected = getSelectedBtnCount(list.get(1));
			if (qianxuTotalSelected < caipiao.getQianquMinCount()
					|| houquTotalSelected < caipiao.getHouquMinCount()) {
				return 0;
			}
			total = CaipiaoUtil.getCombination(qianxuTotalSelected,
					caipiao.getQianquMinCount())
					* CaipiaoUtil.getCombination(houquTotalSelected,
							caipiao.getHouquMinCount());
		} else {
			int qianxuTotalSelected = getSelectedBtnCount(list.get(0));
			if (qianxuTotalSelected < caipiao.getQianquMinCount()) {
				return 0;
			} else {
				total = CaipiaoUtil.getCombination(qianxuTotalSelected,
						caipiao.getQianquMinCount());
			}
		}
		return total;
	}

	@Override
	public String getSubmitString() {
		return null;
	}

	@Override
	public TouzhuquerenData randomTouzhu() {
		List<List<Boolean>> bList = new ArrayList<List<Boolean>>();
		int[] randomArray = CaipiaoUtil.getRandomArray(caipiao.getQianquList()
				.size(), caipiao.getQianquMinCount());

		List<Boolean> qList = new ArrayList<Boolean>();
		for (int i = 0; i < caipiao.getQianquList().size(); i++) {
			qList.add(false);
		}
		for (int i = 0; i < randomArray.length; i++) {
			qList.set(randomArray[i], true);
		}
		bList.add(qList);
		List<String> quanquList = CaipiaoUtil.getStringArray(
				caipiao.getQianquList(), randomArray);
		List<String> houquList = null;
		boolean hasHouqu = caipiao.getHouquList() != null
				&& caipiao.getHouquList().size() > 0;
		if (hasHouqu) {
			randomArray = CaipiaoUtil.getRandomArray(caipiao.getHouquList()
					.size(), caipiao.getHouquMinCount());
			houquList = CaipiaoUtil.getStringArray(caipiao.getHouquList(),
					randomArray);

			qList = new ArrayList<Boolean>();
			for (int i = 0; i < caipiao.getHouquList().size(); i++) {
				qList.add(false);
			}
			for (int i = 0; i < randomArray.length; i++) {
				qList.set(randomArray[i], true);
			}
			bList.add(qList);
		}
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
		if (houquList != null) {
			s = s + CaipiaoConst.PADDING_QU;
			for (int i = 0; i < houquList.size(); i++) {
				if (i == houquList.size() - 1) {
					s = s + houquList.get(i);
				} else {
					s = s + houquList.get(i) + pad;
				}

			}
		}
		s = CaipiaoUtil.trimLast(s);
		TouzhuquerenData data = getDefaultTouzhu();
		data.setHasHouqu(hasHouqu);
		data.setTouzhuhaoma(s);
		data.setListTouzuList2(bList);
		return data;
	}

}
