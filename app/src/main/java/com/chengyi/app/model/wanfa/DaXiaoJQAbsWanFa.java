package com.chengyi.app.model.wanfa;

import android.view.View;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.widget.TouzhuButton;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  DaXiaoJQAbsWanFa extends AbsWanfa {
    

	@Override
	public TouzhuquerenData randomTouzhu() {

		TouzhuquerenData data = getDefaultTouzhu();
		int[] randomArray = CaipiaoUtil.getRandomArray(4, 1);
		data.setHasHouqu(false);
		data.setTouzhuhaoma(CaipiaoConst.strArray[randomArray[0]]);
		return data;
	}

	@Override
	public void check(TouzhuButton btn) {


	}

	@Override
	public int getTouzhuCount() {

		int count = 0;
		if (caipiao.getBtnList() != null) {
			for (View btn : caipiao.getBtnList()) {
				if (btn.isSelected()) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public String getSubmitString() {

		return null;
	}

	public void checkView(View btn) {

		btn.setSelected(!btn.isSelected());
		if (getTouzhuListener() != null) {
			getTouzhuListener().onTouzhuCountChange();
		}
	}
}
