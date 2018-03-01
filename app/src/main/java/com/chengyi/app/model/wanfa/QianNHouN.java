package com.chengyi.app.model.wanfa;


import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  QianNHouN extends NormalPailie {

	private int n;

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	@Override
	public TouzhuquerenData randomTouzhu() {
		String name=caipiao.getCurrentWanfaName();
		List<List<Boolean>> bList = new ArrayList<List<Boolean>>();
		int qianquNum = getN();
		int qianquOneRowCount = caipiao.getQianquList().size();
		///快乐10分，前一玩法特殊处理下
		if(caipiao.getId()== CaipiaoConst.ID_KUAILE10FEN&&CaipiaoUtil.isRenOne(caipiao.getCurrentWanfa().getType()) ){
			qianquOneRowCount=20;
		}
		String s = "";
		Random random = new Random();
		List<Boolean> qList = null;
		if (getType() != CaipiaoConst.WF_DXDS) {
			for (int i = 0; i < qianquNum; i++) {
				qList = new ArrayList<Boolean>();
				for (int j = 0; j < qianquOneRowCount; j++) {
					qList.add(false);
				}
				bList.add(qList);
			}
			///快乐10分，前一玩法特殊处理下
			if(caipiao.getId()==CaipiaoConst.ID_KUAILE10FEN&&CaipiaoUtil.isRenOne(caipiao.getCurrentWanfa().getType())){
				int c = random.nextInt(qianquOneRowCount);
				if(c<18){
					s = s + caipiao.getQianquList().get(c);
					name=CaipiaoConst.QIANYISHUTOU;
				}else{
					s=String.valueOf(c+1);
					name=CaipiaoConst.QIANYIREDTOU;
				}
				bList.get(0).set(c, true);
			}else{
				for (int i = 0; i < qianquNum; i++) {
					int c = random.nextInt(qianquOneRowCount);
					if (i == qianquNum - 1) {
						s = s + caipiao.getQianquList().get(c);
					} else {
						s = s + caipiao.getQianquList().get(c)
								+ CaipiaoConst.PADDING_WEI;
					}
					bList.get(i).set(c, true);
				}
			}
		} 
		else {
			for (int i = 0; i < 2; i++) {
				qList = new ArrayList<Boolean>();
				for (int j = 0; j < 4; j++) {
					qList.add(false);
				}
				bList.add(qList);
			}
			for (int i = 0; i < 2; i++) {
				int c = random.nextInt(4);
				if (i == 1) {
					s = s + CaipiaoUtil.getDaxiaodanshuang().get(c);
				} else {
					s = s + CaipiaoUtil.getDaxiaodanshuang().get(c)
							+ CaipiaoConst.PADDING_WEI;
				}
				bList.get(i).set(c, true);
			}
		}
		s = CaipiaoUtil.trimLast(s);
		if(getType()==CaipiaoConst.WF_ZU3_SINGLE){
			s=s.replace("-", "");
			s=s.substring(0, 1)+s;
		}
		TouzhuquerenData data = getDefaultTouzhu();
		data.setHasHouqu(false);
		data.setTouzhuhaoma(s);
		data.setName(name);
		data.setListTouzuList2(bList);

		if (cannotchongfu()) {
			String[] ta = s.trim().split(CaipiaoConst.SPLIT_PADDING_WEI); 
			if (CaipiaoUtil.isChongfu(ta)) {
				return randomTouzhu();
			}
		}
		return data;
	}

}
