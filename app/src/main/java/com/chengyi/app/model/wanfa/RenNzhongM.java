package com.chengyi.app.model.wanfa;

import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.Num2Str;
import com.chengyi.app.view.widget.TouzhuButton;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  RenNzhongM extends AbsWanfa {
	private int n;// 任N
	private int m;// 中M

	public String getName() {

		return Num2Str.num2Str("普通-"+String.format(CaipiaoApplication.getInstance().getResources()
				.getString(R.string.renxuann), n));// 其实应该为rennzhongm，但为了跟网站显示统一
//		if(this.getCaipiao().getId()==10064)
//			return "普通-"+String.format(CaipiaoApplication.getInstance().getResources()
//					.getString(R.string.renxuann), n);// 其实应该为rennzhongm，但为了跟网站显示统一
//		else
//		    return String.format(CaipiaoApplication.getInstance().getResources()
//				   .getString(R.string.renxuann), n);// 其实应该为rennzhongm，但为了跟网站显示统一
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
		List<TouzhuButton> oneRow = list.get(0);
		int afterSelected = getSelectedBtnCount(oneRow) + 1;
		if (afterSelected >= 2) {// C(afterSelected,2)
			if (CaipiaoUtil.getCombination(afterSelected, n) > caipiao
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
		return CaipiaoUtil.getCombination(selected, n);
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
		for (int i = 0; i < getCaipiao().getQianquList().size(); i++) {
			listStr.add(getCaipiao().getQianquList().get(i));
			qList.add(false);
		}

		TouzhuquerenData data = getDefaultTouzhu();

		int[] randomArray = CaipiaoUtil.getRandomArray(listStr.size(), n);
		List<String> temp = CaipiaoUtil.getStringArray(listStr, randomArray);

		for (int i = 0; i < randomArray.length; i++) {
			qList.set(randomArray[i], true);
		}
		bList.add(qList);
		String s = "";
		String pad = listStr.get(listStr.size() - 1).length() > 1 ? CaipiaoConst.PADDING_2WEINUMBER
				: CaipiaoConst.PADDING_1WEINUMBER;
		for (int i = 0; i < temp.size(); i++) {
			if (i == temp.size() - 1) {
				s = s + temp.get(i);
			} else {
				s = s + temp.get(i) + pad;
			}
		}
		s = CaipiaoUtil.trimLast(s);
		data.setHasHouqu(false);
		data.setTouzhuhaoma(s);
		data.setListTouzuList2(bList);
		return data;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

}
