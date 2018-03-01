package com.chengyi.app.jingji.football;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.chengyi.R;
import com.chengyi.app.model.model.JingcaizuqiuOneGame;
import com.chengyi.app.view.widget.OnJingcaizuqiuBtnListener;

import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  JingcaiZuqiuTZquerenAdapter extends BaseAdapter implements View.OnClickListener {
    
    Activity mActivity;
	int currentIndex;
	LayoutInflater inflater;
	List<JingcaizuqiuOneGame> list;

 
	LinearLayout view;
	OnJingcaizuqiuBtnListener listener;
	//可以选择的胆码数目
	int danNum=0;

	public boolean  deleteMode=false;
	public boolean isDeleteMode() {
		return deleteMode;
	}
	public void setDeleteMode(boolean deleteMode) {
		this.deleteMode = deleteMode;
	}
	int wfIndex=0;
	public int getWfIndex() {
		return wfIndex;
	}
	public void setWfIndex(int wfIndex) {
		this.wfIndex = wfIndex;
	}
	int danNumSelected=0;
	public int getDanNumSelected() {
		return danNumSelected;
	}
	public void setDanNumSelected(int danNumSelected) {
		this.danNumSelected = danNumSelected;
	}
	public JingcaiZuqiuTZquerenAdapter(Activity mActivity,List<JingcaizuqiuOneGame> list,OnJingcaizuqiuBtnListener listener)
	{
		this.mActivity = mActivity;
		inflater = mActivity.getLayoutInflater();
		this.list=list;
		this.listener=listener;
		
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return null;
	}
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }
	@Override
	public void onClick(View v) {

		final ImageView b=(ImageView) v;
		switch(b.getId()){

			case  R.id.iv_delete:
				//变成删除模式
				if(deleteMode){
					try {
						currentIndex=Integer.parseInt(String.valueOf(v.getTag()));
					} catch (NumberFormatException e) {

						e.printStackTrace();
						currentIndex=0;
					}
					try {
						list.get(currentIndex).reset();
						listener.qingKongBiSaiData(list.get(currentIndex));
						list.remove(currentIndex);
					} catch (Exception e) {
						e.printStackTrace();
					}
					this.notifyDataSetChanged();
					listener.buttonChangeUI(null);
					if(list.size()==0)
						deleteMode=false;
				}else{
					b.setSelected(!b.isSelected());
					int index=Integer.parseInt(v.getTag().toString());
					JingcaizuqiuOneGame item=list.get(index);
					if(b.isSelected()){
						item.setDanTuo(true);
						listener.danMaChangUI(true);

					}
					else{
						item.setDanTuo(false);
						listener.danMaChangUI(false);

					}
				}



				break;
		    //点击胆码
		    case R.id.danbtn:
//		    	//变成删除模式
//		    	if(deleteMode){
//		    		try {
//						currentIndex=Integer.parseInt(String.valueOf(v.getTag()));
//					} catch (NumberFormatException e) {
//
//						e.printStackTrace();
//						currentIndex=0;
//					}
//					try {
//						list.get(currentIndex).reset();
//						listener.qingKongBiSaiData(list.get(currentIndex));
//						list.remove(currentIndex);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//			    	this.notifyDataSetChanged();
//			    	listener.buttonChangeUI(null);
//			    	if(list.size()==0)
//			    		deleteMode=false;
//		    	}else{
//			    	b.setSelected(!b.isSelected());
//			    	int index=Integer.parseInt(v.getTag().toString());
//			    	JingcaizuqiuOneGame item=list.get(index);
//			    	if(b.isSelected()){
//			    		item.setDanTuo(true);
//				    	listener.danMaChangUI(true);
//
//			    	}
//			    	else{
//			    		item.setDanTuo(false);
//				    	listener.danMaChangUI(false);
//
//			    	}
//		    	}
		    	break;
		}
    	
	}  
	public View buildChildView(int resource) {
		return inflater .inflate(resource, null);
	}
	int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
