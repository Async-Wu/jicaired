package com.chengyi.app.model.wanfa;

import android.content.Context;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.model.caipiao.Caipiao;
import com.chengyi.app.model.model.TouzhuquerenData;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.Num2Str;
import com.chengyi.app.view.widget.TouzhuButton;
import com.chengyi.app.view.widget.TouzhuListener;

import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public abstract class AbsWanfa {
	TouzhuListener touzhuListener;

	public TouzhuListener getTouzhuListener() {
		return touzhuListener;
	}

	public abstract TouzhuquerenData randomTouzhu();

	public void setTouzhuListener(TouzhuListener touzhuListener) {
		this.touzhuListener = touzhuListener;
	}

	public static int getSelectedBtnCount(List<TouzhuButton> list) {
		return CaipiaoUtil.getSelectedBtnCount(list);
	}

	protected boolean letouIfSelectedCheckMaxSelectedCount(
			int afterQianquTotalSelected, int afterHouquTotalSelected) {
		if (afterQianquTotalSelected > caipiao.getQianquMaxCount()) {
			String s = String.format(
					getAppContext().getString(R.string.zuiduozhinengxuanze),
					caipiao.getQianquMaxCount());
			if (getTouzhuListener() != null) {
				getTouzhuListener().onCheck(s);
			}
			return false;
		}
		if (afterHouquTotalSelected > caipiao.getHouquMaxCount()) {
			String s = String.format(
					getAppContext().getString(R.string.zuiduozhinengxuanze),
					caipiao.getHouquMaxCount());
			if (getTouzhuListener() != null) {
				getTouzhuListener().onCheck(s);
			}
			return false;
		}
		return true;
	}

	protected String getChaoguoMaxCountWarning() {
		return String.format(getAppContext().getString(R.string.bunengchaoguo),
				caipiao.getMaxTouzhuCount() * CaipiaoConst.PRICE);
	}

	protected String getBunengchongfu() {
		return  getAppContext().getString(R.string.bunengyouchongfudehaoma) ;
	}
	
	protected void letouIfSelectedCheckMaxTouzhuCount(
			int afterQianquTotalSelected, int afterHouquTotalSelected,
			TouzhuButton btn) {
		if (afterQianquTotalSelected >= caipiao.getQianquMinCount()&& afterHouquTotalSelected >= caipiao.getHouquMinCount()) {
			int total = CaipiaoUtil.getCombination(afterQianquTotalSelected,
					caipiao.getQianquMinCount())
					* CaipiaoUtil.getCombination(afterHouquTotalSelected,
							caipiao.getHouquMinCount());

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
		} else {
			btn.setSelected(true);
			if (getTouzhuListener() != null) {
				getTouzhuListener().onTouzhuCountChange();
			}
		}
	}

	//type是一类玩法，可以相同
	private int type;

	// 玩法ID，唯一性
	private int id;
	// 玩法名称
	private String name;

	// 保存该玩法当前对应的彩票
	protected Caipiao caipiao;

	// 全排列时，几位数
	private int weishu;

	// 验证是否合法，如超过总注数等,点击btn时，先进行检测，然后决定该按钮是否要选中
	public abstract void check(TouzhuButton btn);

	// 当前总投注数
	public abstract int getTouzhuCount();

	// 得到提交服务器的参数字符串
	public abstract String getSubmitString();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
		setId(type);
	}

	public int getWeishu() {
		return weishu;
	}

	public void setWeishu(int weishu) {
		this.weishu = weishu;
	}

	public String getName() {
		return Num2Str.num2Str(name) ;
	}

	public void setName(String name) {

		this.name = Num2Str.num2Str(name);
	}

	public Caipiao getCaipiao() {
		return caipiao;
	}

	public void setCaipiao(Caipiao caipiao) {
		this.caipiao = caipiao;
	}

	protected Context getAppContext() {
		return CaipiaoApplication.getInstance().getApplicationContext();
	}

	protected TouzhuquerenData getDefaultTouzhu() {
		TouzhuquerenData data = new TouzhuquerenData();
		data.setName(caipiao.getCurrentWanfaName());
		data.setCaipiaoIdAndWanfaType(caipiao.getId(), getType());
		data.setZhushu(1);
		return data;
	}
}
