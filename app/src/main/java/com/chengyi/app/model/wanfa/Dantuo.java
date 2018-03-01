package com.chengyi.app.model.wanfa;


import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.widget.TouzhuButton;

import java.util.List;



/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Dantuo extends AbsWanfa {

	@Override
	public TouzhuquerenData randomTouzhu() {
		return null;
	}

	// 胆码和拖码不能相同，判断list组中是否有text为str的并选中的球,有则返回
	protected TouzhuButton getSelectedBtnByString(List<TouzhuButton> btnList,
												  String str) {
		for (TouzhuButton btn : btnList) {
			if (btn.isSelected() && btn.getText().toString().equals(str)) {
				return btn;
			}
		}
		return null;
	}

	private int M = -1;
	private int N = -1;
    private int minDantu=1;
    private int maxDantu=1;
    private  int num=0;
	public int getMinDantu() {
		return minDantu;
	}

	public void setMinDantu(int minDantu) {
		this.minDantu = minDantu;
	}

	public int getMaxDantu() {
		return maxDantu;
	}

	public void setMaxDantu(int maxDantu) {
		this.maxDantu = maxDantu;
	}

	public int getM() {// 如福彩3D的胆拖，需要设置M和N
		if (M < 0) {
			return caipiao.getQianquMinCount();
		}
		return M;
	}

	public void setM(int m) {
		M = m;
	}

	public int getN() {
		if (N < 0) {// 组三胆拖时，为0
			return caipiao.getHouquMinCount();
		}
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
		TouzhuButton temp = null;// 需要从选中变为不选中的球。
		if (getN() == 0) {// 如22选5
			int danmaTotalSelected = getSelectedBtnCount(list.get(0));
			int tuomaTotalSelected = getSelectedBtnCount(list.get(1));
			int afterDanmaTotalSelected = danmaTotalSelected;
			int afterTuomaTotalSelected = tuomaTotalSelected;
			if (list.get(0).contains(btn)) {// 该按钮是胆码,检查拖码中是否有该球，如果胆码选中此球，则拖码要变为未选中
				afterDanmaTotalSelected++;
				temp = getSelectedBtnByString(list.get(1), btn.getText()
						.toString());
				if (temp != null) {
					afterTuomaTotalSelected--;
				}
			} else {// 该球是拖码，
				afterTuomaTotalSelected++;
				temp = getSelectedBtnByString(list.get(0), btn.getText()
						.toString());
				if (temp != null) {
					afterDanmaTotalSelected--;
				}
			}
			num=getM();
			///快乐10分的一系列胆拖玩法
			if(getType()>101){
				num=getMaxDantu()+1;
				if (afterDanmaTotalSelected >=getMaxDantu()+1) {
					if (getTouzhuListener() != null) {
						getTouzhuListener().onCheck("胆码最多" + getMaxDantu() + "个");
					}
					return;
				}
			}else{
				if (afterDanmaTotalSelected >= getM()) {
					if (getTouzhuListener() != null) {
						getTouzhuListener().onCheck("胆码最多" + (getM() - 1) + "个");
					}
					return;
				}
			}
			// 总注数
			int total = 0;
			if (afterDanmaTotalSelected > 0) {// 胆码拖码必须都有选中的
				///福彩3d胆拖直选特殊处理下
				if((getCaipiao().getId()==10024||getCaipiao().getId()==10025)&&(afterDanmaTotalSelected+afterTuomaTotalSelected<4)&&getType()==105){
					total = 0;
					btn.setSelected(true);
					if (temp != null) {
						temp.setSelected(false);// 需要清除选中状态的球,如胆码选中了01，则拖码中的01要变为未选中
					}
					return;
				}else
					total = CaipiaoUtil.getCombination(afterTuomaTotalSelected,
							(num - afterDanmaTotalSelected));
				if(getType()==103)
					total=total*2;
				else if(getType()==105)
					total=total*6;
			}
			if (total > caipiao.getMaxTouzhuCount()) {
				getTouzhuListener().onCheck(getChaoguoMaxCountWarning());
				return;
			}
			btn.setSelected(true);
			if (temp != null) {
				temp.setSelected(false);// 需要清除选中状态的球,如胆码选中了01，则拖码中的01要变为未选中
			}
		} else if (getN() == 1) {// 后区没有胆码的.如双色球
			int danmaTotalSelected = getSelectedBtnCount(list.get(0));
			int tuomaTotalSelected = getSelectedBtnCount(list.get(1));
			int lanqiuSelected = getSelectedBtnCount(list.get(2));

			int afterDanmaTotalSelected = danmaTotalSelected;
			int afterTuomaTotalSelected = tuomaTotalSelected;
			int afterLanqiuSelected = lanqiuSelected;
			if (list.get(0).contains(btn)) {// 胆码区
				afterDanmaTotalSelected++;
				temp = getSelectedBtnByString(list.get(1), btn.getText()
						.toString());
				if (temp != null) {
					afterTuomaTotalSelected--;
				}
			} else if (list.get(1).contains(btn)) {// 拖码区
				afterTuomaTotalSelected++;
				temp = getSelectedBtnByString(list.get(0), btn.getText()
						.toString());
				if (temp != null) {
					afterDanmaTotalSelected--;
				}
			} else {// 蓝球区
				afterLanqiuSelected++;
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
				total = CaipiaoUtil.getCombination(afterTuomaTotalSelected,
						(getM() - afterDanmaTotalSelected))
						* CaipiaoUtil.getCombination(afterLanqiuSelected,
								getN());
			}

			if (total > caipiao.getMaxTouzhuCount()) {
				getTouzhuListener().onCheck(getChaoguoMaxCountWarning());
				return;
			}
			btn.setSelected(true);
			if (temp != null) {
				temp.setSelected(false);// 需要清除选中状态的球,如胆码选中了01，则拖码中的01要变为未选中
			}
		} else {// 如大乐透
			int qqDanmaTotalSelected = getSelectedBtnCount(list.get(0));
			int hqDanmaTotalSelected = getSelectedBtnCount(list.get(2));
			int qqTuomaTotalSelected = getSelectedBtnCount(list.get(1));
			int hqTuomaTotalSelected = getSelectedBtnCount(list.get(3));

			int afterQqDanmaTotalSelected = qqDanmaTotalSelected;
			int afterHqDanmaTotalSelected = hqDanmaTotalSelected;
			int afterQqTuomaTotalSelected = qqTuomaTotalSelected;
			int afterHqTuomaTotalSelected = hqTuomaTotalSelected;
			if (list.get(0).contains(btn)) {// 前区胆码区
				afterQqDanmaTotalSelected++;
				temp = getSelectedBtnByString(list.get(1), btn.getText()
						.toString());
				if (temp != null) {
					afterQqTuomaTotalSelected--;
				}
			} else if (list.get(2).contains(btn)) {// 后区胆码
				afterHqDanmaTotalSelected = hqDanmaTotalSelected + 1;
				temp = getSelectedBtnByString(list.get(3), btn.getText()
						.toString());
				if (temp != null) {
					afterHqTuomaTotalSelected--;
				}

			} else if (list.get(1).contains(btn)) {// 前区拖码
				afterQqTuomaTotalSelected++;
				temp = getSelectedBtnByString(list.get(0), btn.getText()
						.toString());
				if (temp != null) {
					afterQqDanmaTotalSelected--;
				}
			} else {// 后区拖码
				afterHqTuomaTotalSelected++;
				temp = getSelectedBtnByString(list.get(2), btn.getText()
						.toString());
				if (temp != null) {
					afterHqDanmaTotalSelected--;
				}
			}

			if (afterQqDanmaTotalSelected >= getM()) {
				if (getTouzhuListener() != null) {
					getTouzhuListener().onCheck("前区胆码最多" + (getM() - 1) + "个");
				}
				return;
			} else if (afterHqDanmaTotalSelected >= getN()) {
				if (getTouzhuListener() != null) {
					getTouzhuListener().onCheck("后区胆码最多" + (getN() - 1) + "个");
				}
				return;
			}
			// 总注数
			int total = 0;
			if (afterQqDanmaTotalSelected > 0) {// 胆码拖码必须都有选中的
				total = CaipiaoUtil.getCombination(afterQqTuomaTotalSelected,
						(getM() - afterQqDanmaTotalSelected))
						* CaipiaoUtil.getCombination(afterHqTuomaTotalSelected,
								(getN() - afterHqDanmaTotalSelected));
			}
			if (total > caipiao.getMaxTouzhuCount()) {
				getTouzhuListener().onCheck(getChaoguoMaxCountWarning());
				return;
			}
			btn.setSelected(true);
			if (temp != null) {
				temp.setSelected(false);// 需要清除选中状态的球,如胆码选中了01，则拖码中的01要变为未选中
			}
		}
		getTouzhuListener().onTouzhuCountChange();
	}

	@Override
	public int getTouzhuCount() {
		
		int total = 0;
		List<List<TouzhuButton>> list = caipiao.getListTouzuList();
		if (getN() == 0) {// 如22选5
			int danmaTotalSelected = getSelectedBtnCount(list.get(0));
			int tuomaTotalSelected = getSelectedBtnCount(list.get(1));
			if(getType()>101){
				num=getMaxDantu()+1;
			}else{
				num=getM();
			}
			if (danmaTotalSelected > 0) {
				if((getCaipiao().getId()==10024||getCaipiao().getId()==10025||getCaipiao().getId()==10061||getCaipiao().getId()==10038)
						&&(danmaTotalSelected+tuomaTotalSelected<4)&&(getType()==105||getType()==115)){
					total = 0;
				}else{
					total = CaipiaoUtil.getCombination(tuomaTotalSelected,(num - danmaTotalSelected));
				}
				if(getType()==103)
					total=total*2;
				else if(getType()==105||getType()==115)
					total=total*6;
			}
		} else if (getN() == 1) {// 后区没有胆码的.如双色球
			int danmaTotalSelected = getSelectedBtnCount(list.get(0));
			int tuomaTotalSelected = getSelectedBtnCount(list.get(1));
			int lanqiuSelected = getSelectedBtnCount(list.get(2));
			if (danmaTotalSelected > 0) {
				total = CaipiaoUtil.getCombination(tuomaTotalSelected,
						(getM() - danmaTotalSelected))
						* CaipiaoUtil.getCombination(lanqiuSelected, getN());
			}
		} else {// 如大乐透
			int qqDanmaTotalSelected = getSelectedBtnCount(list.get(0));//前胆
			int hqDanmaTotalSelected = getSelectedBtnCount(list.get(2));//后胆
			int qqTuomaTotalSelected = getSelectedBtnCount(list.get(1));//前拖
			int hqTuomaTotalSelected = getSelectedBtnCount(list.get(3));//后拖
			if (qqDanmaTotalSelected > 0) {
				total = CaipiaoUtil.getCombination(qqTuomaTotalSelected,
						(getM() - qqDanmaTotalSelected))
						* CaipiaoUtil.getCombination(hqTuomaTotalSelected,
								(getN() - hqDanmaTotalSelected));
			}
		}
		return total;
	}

	@Override
	public String getSubmitString() {
		return null;
	}

	 
}
