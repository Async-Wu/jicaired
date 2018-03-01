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
public class  NormalPailie extends AbsWanfa {
	// 是否能重复，如老11选5的前3直选，前3位，不能有重复的如112就不行
	public boolean cannotchongfu() {
		return getId() == CaipiaoConst.WF_QIAN2
				|| getId() == CaipiaoConst.WF_QIAN3
						||getId() == CaipiaoConst.WF_ZU3_SINGLE;
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

		
		if (getId() == CaipiaoConst.WF_DXDS||getId() == CaipiaoConst.WF_ZU3_SINGLE) {// 大小单双,组三单式不能复式
			List<TouzhuButton> temp = list.get(thisBtn);
			for (TouzhuButton t : temp) {
				t.setSelected(false);
			}
		}

		if (cannotchongfu()) {
			String thisBtnText = btn.getText().toString();
			for (int i = 0; i < list.size(); i++) {
				if (i != thisBtn) {
					for (TouzhuButton tb : list.get(i)) {
						if (tb.isSelected()
								&& tb.getText().toString().equals(thisBtnText)) {
							//组三单式不能重新重号
							if(getId() == CaipiaoConst.WF_ZU3_SINGLE){
								tb.setSelected(false);
							}else if (getTouzhuListener() != null) {
								getTouzhuListener().onCheck(getBunengchongfu());
								return;
							}
						}
					}
				}
			}
		}
		int totalCount = 1;
		if(getType()==CaipiaoConst.WF_QIAN3ZHIX||getType()==CaipiaoConst.WF_LIAN2ZHX){
			totalCount = totalCount *getTouzhuCountBySpecial();
		}else{
			for (int i = 0; i < selectedCount.length; i++) {
				totalCount = totalCount * selectedCount[i];
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
		int totalCount = 1;
		if(getType()==CaipiaoConst.WF_QIAN3ZHIX||getType()==CaipiaoConst.WF_LIAN2ZHX){
			totalCount = totalCount *getTouzhuCountBySpecial();
		}else{
			for (int i = 0; i < selectedCount.length; i++) {
				totalCount = totalCount * selectedCount[i];
			}
		}
		return totalCount;
	}
	///快乐10分的连二直选和前三直选
    public int getTouzhuCountBySpecial(){
    	int totalCount=0;
    	List<List<TouzhuButton>> list = caipiao.getListTouzuList();
    	///连二直选算法
    	if(list.size()==2){
	    	for(int i=0;i<list.get(0).size();i++){
	    		TouzhuButton btn=list.get(0).get(i);
	    		if(btn.isSelected()){
	    			totalCount=totalCount+getNumFromList(list.get(1),btn);
	    		}
	    	}
    	}else if(list.size()==3){
    		for(int i=0;i<list.get(0).size();i++){
	    		TouzhuButton btn=list.get(0).get(i);
	    		if(btn.isSelected()){
		    		for(int j=0;j<list.get(1).size();j++){
		    			TouzhuButton btnTwo=list.get(1).get(j);
		    			if(btnTwo.isSelected()&&!btnTwo.getText().equals(btn.getText())){
		    				totalCount=totalCount+getNumFromListTwoBtn(list.get(2),btn,btnTwo);
		    			}
		    		}
	    		}
	    	}
    	}
    	return totalCount;
    }
  ///从list中获取不存在某2个btn的个数
    public int getNumFromListTwoBtn(List<TouzhuButton> list,TouzhuButton btn,TouzhuButton btnTwo){
    	int count=0;
    	for (TouzhuButton b : list) {
			if(b.isSelected()&&!b.getText().equals(btn.getText())&&!b.getText().equals(btnTwo.getText())){
				count++;
			}
		}
    	return count;
    }
    ///从list中获取不存在某个btn的个数
    public int getNumFromList(List<TouzhuButton> list,TouzhuButton btn){
    	int count=0;
    	for (TouzhuButton b : list) {
			if(b.isSelected()&&!b.getText().equals(btn.getText())){
				count++;
			}
		}
    	return count;
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
		int houquNum = caipiao.getHouquMinCount();
		int houquOneRowCount = caipiao.getHouquList().size();
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
		for (int i = 0; i < houquNum; i++) {
			qList = new ArrayList<Boolean>();
			for (int j = 0; j < houquOneRowCount; j++) {
				qList.add(false);
			}
			bList.add(qList);
		}
		String temp = null;
		for (int i = 0; i < qianquNum; i++) {
			int c = random.nextInt(qianquOneRowCount);
			temp = (i == qianquNum - 1) ? "" : CaipiaoConst.PADDING_WEI;
			s = s + caipiao.getQianquList().get(c) + temp;
			bList.get(i).set(c, true);
		}

		s = CaipiaoUtil.trimLast(s);

		if (houquNum > 0) {
			s = s + CaipiaoConst.PADDING_QU;
			qList = new ArrayList<Boolean>();
		}
		for (int i = 0; i < houquNum; i++) {
			int c = random.nextInt(houquOneRowCount);
			temp = (i == houquNum - 1) ? "" : CaipiaoConst.PADDING_WEI;
			s = s + caipiao.getHouquList().get(c) + CaipiaoConst.PADDING_WEI;
			bList.get(qianquNum + i).set(c, true);
		}
		s = CaipiaoUtil.trimLast(s);
		TouzhuquerenData data = getDefaultTouzhu();
		data.setHasHouqu(houquNum > 0);
		data.setTouzhuhaoma(s);
		data.setListTouzuList2(bList);
		return data;
	}

}
