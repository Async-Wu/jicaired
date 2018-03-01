package com.chengyi.app.model.model;

import android.view.View;
import com.chengyi.app.model.wanfa.WanfaFactory;
import com.chengyi.app.util.CaipiaoConst;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.view.widget.TouzhuButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  TouzhuquerenData implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String touzhuhaoma;
	private int zhushu;
	private boolean hasHouqu;
	private int caipiaoId=0;
	public static int staticId = 0;

	private boolean delete;




	public boolean isDelete() {
		return delete;
	}



	public void setDelete(boolean delete) {
		this.delete = delete;
	}



	public boolean isHasHouqu() {
		return hasHouqu;
	}

	public boolean allNotSelected = false;//手选时，要传一个touzhuquerendata，但默认都未选中

	public int getCaipiaoId() {
		return caipiaoId;
	}

	private int wfType;

	public int getWfType() {
		return wfType;
	}



	public void setCaipiaoIdAndWanfaType(int caipiaoId, int wfType) {
		this.caipiaoId = caipiaoId;
		this.wfType = wfType;
	}

	private final int id;

	public TouzhuquerenData() {
		id = staticId++;// 唯一序列号
	}

	public int getId() {
		return id;
	}

	private String typeAndNumber;// 如单式 123456+6

	public String getTypeAndNumber() {
		return typeAndNumber;
	}

	public void setTypeAndNumber(String typeAndNumber) {
		this.typeAndNumber = typeAndNumber;
	}

	List<List<Boolean>> listTouzuList;

	public List<List<Boolean>> getListTouzuList() {
		return listTouzuList;
	}
	//boolean 值得list集合
	public void setListTouzuList(List<List<TouzhuButton>> list) {
		listTouzuList = new ArrayList<List<Boolean>>();
		for (int i = 0; i < list.size(); i++) {
			List<Boolean> oneRow = new ArrayList<Boolean>();
			for (int j = 0; j < list.get(i).size(); j++) {
				oneRow.add(list.get(i).get(j).isSelected());
			}
			listTouzuList.add(oneRow);
		}
	}
	//大小奇偶玩法的特殊处理
	public void setDXJOList(ArrayList<View> list) {
		listTouzuList = new ArrayList<List<Boolean>>();
		List<Boolean> oneRow = new ArrayList<Boolean>();
		for (int j = 0; j < list.size(); j++) {
			oneRow.add(list.get(j).isSelected());
		}
		listTouzuList.add(oneRow);
	}


	public void setListTouzuList2(List<List<Boolean>> listTouzuList) {
		this.listTouzuList = listTouzuList;
	}

	public void setHasHouqu(boolean hasHouqu) {
		this.hasHouqu = hasHouqu;
	}

	public String getName() {
		if(!name.equals("")){
			if(name.equals("普通-组3复式")||name.equals("普通-组3单式"))
				return name;
			if (zhushu > 1) {
				return name+"复式";
			}
			return name+"单式";
		}else{
			if (zhushu > 1) {
				return "复式";
			}
			return "单式";
		}
	}

	public void setName(String name) {
		this.name = name;
	}
	public  String   getOldName(){
		return name;
	}

	public String getTouzhuhaoma() {
		if (caipiaoId == CaipiaoConst.ID_LAOSHISHICAI
				&& wfType == CaipiaoConst.WF_YX_ZHIXUAN) {
			// 一星直选特殊，没有复式。客户端如果是24，为复式，则拆成2|4
			if (touzhuhaoma.length() == 1) {
				return touzhuhaoma;
			} else {
				return CaipiaoUtil.getYixingSubString(touzhuhaoma);
			}
		}
		return touzhuhaoma;
	}

	public void setTouzhuhaoma(String touzhuhaoma) {
		this.touzhuhaoma = touzhuhaoma;
	}

	public int getZhushu() {
		return zhushu;
	}

	public void setZhushu(int zhushu) {
		this.zhushu = zhushu;
	}

	public boolean isSingle() {
		return zhushu == 1;
	}

	public String getSubmitString() {
		if (caipiaoId==CaipiaoConst.ID_NEWKUAI3||caipiaoId==CaipiaoConst.ID_GUANGXIKUAI3 ||caipiaoId==CaipiaoConst.ID_ANHUIKUAI3){

			if (WanfaFactory.getSubmitType(WanfaFactory.create(wfType,caipiaoId), zhushu,name,caipiaoId).contains("twoSame")){

			}else{
				return  WanfaFactory.getSubmitType(WanfaFactory.create(wfType,caipiaoId), zhushu,name,caipiaoId)
						+ "=" + getTouzhuhaoma().replaceAll("-",",");
			}

			return  WanfaFactory.getSubmitType(WanfaFactory.create(wfType,caipiaoId), zhushu,name,caipiaoId)
					+ "=" + getTouzhuhaoma();
		}else{

		return WanfaFactory.getSubmitType(WanfaFactory.create(wfType,caipiaoId), zhushu,name,caipiaoId)
				+ "=" + getTouzhuhaoma();}
	}
	private String type;// 中文玩法名称
	private String number;// 方案

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
