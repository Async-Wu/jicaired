package com.chengyi.app.adapter;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */

/**
 * @hide
 */
@Deprecated
public class  GuidePageAdapter extends PagerAdapter {
	private ArrayList<View> pageViews; 
	public GuidePageAdapter(ArrayList<View> pv){
		this.pageViews=pv;
	}
	@Override
	public int getCount() {

		return pageViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {

		return arg0 == arg1;
	}
	@Override
	public void destroyItem(ViewGroup arg0, int arg1, Object arg2) {

		( arg0).removeView(pageViews.get(arg1));
	}
	
	@Override
	public int getItemPosition(Object object) {

		return super.getItemPosition(object);
	}
	@Override
	public Object instantiateItem(ViewGroup arg0, int arg1) {

		 ( arg0).addView(pageViews.get(arg1));
		  return pageViews.get(arg1); 
	}
	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {

		super.restoreState(state, loader);
	}
	@Override
	public Parcelable saveState() {

		return super.saveState();
	}


}
