package com.chengyi.app.model.wanfa;

import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.widget.TouzhuButton;

import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Zu2 extends Zuxuan {
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
		List<TouzhuButton> oneRow = list.get(0);
		int afterSelected = getSelectedBtnCount(oneRow) + 1;
		if (afterSelected >= 2) {// C(afterSelected,2)
			if (CaipiaoUtil.getCombination(afterSelected, 2) > caipiao
					.getMaxTouzhuCount()) {
				getTouzhuListener().onCheck(getChaoguoMaxCountWarning());
			} else {
				btn.setSelected(true);
				getTouzhuListener().onTouzhuCountChange();
			}
		} else {
			btn.setSelected(true);
			getTouzhuListener().onTouzhuCountChange();
		}
	}

	@Override
	public int getTouzhuCount() {
		List<List<TouzhuButton>> list = caipiao.getListTouzuList();
		List<TouzhuButton> oneRow = list.get(0);
		int selected = getSelectedBtnCount(oneRow);
		return CaipiaoUtil.getCombination(selected, 2);
	}

	@Override
	public String getSubmitString() {
		return null;
	}

	@Override
	public int getMinNum() {
		return 2;
	}
}
