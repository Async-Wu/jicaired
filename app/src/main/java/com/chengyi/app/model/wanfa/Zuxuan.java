package com.chengyi.app.model.wanfa;

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
public abstract class Zuxuan extends AbsWanfa {

	public abstract int getMinNum();

	@Override
	public void check(TouzhuButton btn) {
	}

	@Override
	public int getTouzhuCount() {
		return 0;
	}

	@Override
	public String getSubmitString() {
		return null;
	}

	@Override
	public TouzhuquerenData randomTouzhu() {
		List<String> listStr = new ArrayList<String>();
		List<List<Boolean>> bList = new ArrayList<List<Boolean>>();
		List<Boolean> qList = new ArrayList<Boolean>();
		for (int i = 0; i < caipiao.getQianquList().size(); i++) {
			listStr.add(caipiao.getQianquList().get(i));
			qList.add(false);
		}
		int c = 2;

		TouzhuquerenData data = getDefaultTouzhu();

		if (this instanceof Zu3 && getType() != CaipiaoConst.WF_QIAN2_ZUXUAN) {// 老11选5，前2组选，无关顺序
			data.setZhushu(2);
		}
		c = CaipiaoUtil.getRandomNumber(caipiao);
		int[] randomArray = CaipiaoUtil.getRandomArray(listStr.size(), c);
		List<String> temp = CaipiaoUtil.getStringArray(listStr, randomArray);

		for (int i = 0; i < randomArray.length; i++) {
			qList.set(randomArray[i], true);
		}
		bList.add(qList);
		String s = "";
		String pad = caipiao.getQianquList().get(0).length() > 1 ? CaipiaoConst.PADDING_2WEINUMBER
				: CaipiaoConst.PADDING_1WEINUMBER;
		for (int i = 0; i < temp.size(); i++) {
			if (i == temp.size() - 1) {
				s = s + temp.get(i);
			} else {
				s = s + temp.get(i) + pad;
			}
		}
		s = s.trim();
		data.setHasHouqu(false);
		data.setTouzhuhaoma(s);
		data.setListTouzuList2(bList);
		return data;
	}

}
