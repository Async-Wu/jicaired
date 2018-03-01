package com.chengyi.app.model.wanfa;

import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.widget.TouzhuButton;

import java.util.List;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Dantuo2 extends Dantuo {

	public static final int ZU3 = 0;
	public static final int ZU6 = 1;
	private int dantuoType = ZU3;

	public void setDantuoType(int dantuotype) {
		this.dantuoType = dantuotype;
	}

	public int getDantuoType() {
		return dantuoType;
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
		TouzhuButton temp = null;// 需要从选中变为不选中的球。

		int danmaTotalSelected = getSelectedBtnCount(list.get(0));
		int tuomaTotalSelected = getSelectedBtnCount(list.get(1));
		int afterDanmaTotalSelected = danmaTotalSelected;
		int afterTuomaTotalSelected = tuomaTotalSelected;
		if (list.get(0).contains(btn)) {// 该按钮是胆码,检查拖码中是否有该球，如果胆码选中此球，则拖码要变为未选中
			afterDanmaTotalSelected++;
			temp = getSelectedBtnByString(list.get(1), btn.getText().toString());
			if (temp != null) {
				afterTuomaTotalSelected--;
			}
		} else {// 该球是拖码，
			afterTuomaTotalSelected++;
			temp = getSelectedBtnByString(list.get(0), btn.getText().toString());
			if (temp != null) {
				afterDanmaTotalSelected--;
			}
		}
		if (afterDanmaTotalSelected >= getM()) {
			if (getTouzhuListener() != null) {
				getTouzhuListener().onCheck("胆码最多" + (getM() - 1) + "个");
			}
			return;
		}
		// 总注数
		int total = 0;
		if (afterDanmaTotalSelected > 0) {// 胆码拖码必须都有选中的
			total = getTotal(afterDanmaTotalSelected, afterTuomaTotalSelected);
		}

		if (total > caipiao.getMaxTouzhuCount()) {
			getTouzhuListener().onCheck(getChaoguoMaxCountWarning());
			return;
		}
		btn.setSelected(true);
		if (temp != null) {
			temp.setSelected(false);// 需要清除选中状态的球,如胆码选中了01，则拖码中的01要变为未选中
		}
		getTouzhuListener().onTouzhuCountChange();
	}

	private int getTotal(int danma, int tuoma) {
		int total = 0;
		int min = dantuoType == ZU3 ? 3 : 4;
		if (danma == 0 || (danma + tuoma) < min) {
			return 0;
		}

		if (dantuoType == ZU3) {
			total = CaipiaoUtil.getCombination(tuoma, (getM() - danma)) * 2;
		} else if (dantuoType == ZU6) {
			total = CaipiaoUtil.getCombination(tuoma, (getM() - danma));
		}
		return total;
	}

	@Override
	public int getTouzhuCount() {
		List<List<TouzhuButton>> list = caipiao.getListTouzuList();
		int danmaTotalSelected = getSelectedBtnCount(list.get(0));
		int tuomaTotalSelected = getSelectedBtnCount(list.get(1));
		return getTotal(danmaTotalSelected, tuomaTotalSelected);
	}
}
