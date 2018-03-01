package com.chengyi.app.view.scoller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.util.HashMap;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  MyRativeLayout extends RelativeLayout {
   
	private static final int MAX_SETTLE_DURATION = 800; // ms
	private Scroller  mScroller;// 滑动控制器,用来控制滑动速度
	private float y;
	private boolean isBeginDrag=false,isOpen=false,special=false,isShow=false; 
	private LinearLayout testLayout;
	private Context context;
	private int mHeight=320;
	HashMap<String, String> map = new HashMap<String, String>();
	public int getmHeight() {
		return mHeight;
	}

	public void setmHeight(int mHeight) {
		this.mHeight = mHeight;
	}


	private boolean isSuccessfulGetKJ=true;
	public boolean isSuccessfulGetKJ() {
		return isSuccessfulGetKJ;
	}

	public void setSuccessfulGetKJ(boolean isSuccessfulGetKJ) {
		this.isSuccessfulGetKJ = isSuccessfulGetKJ;
	}

	
	PopupWindow  pressPreview;

	public MyRativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new Scroller(context); 
		this.context=context;
		// TODO Auto-generated constructor stub
	}

	public MyRativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context); 
		this.context=context;
		// TODO Auto-generated constructor stub
	}

	public MyRativeLayout(Context context) {
		super(context);
		this.context=context;
		mScroller = new Scroller(context); 
		// TODO Auto-generated constructor stub
	}
	protected void onLayout(boolean changed, int l, int t, int r, int b) {


		    final int count = getChildCount();
	        for (int i = 0; i < count; i++) {
	            final View child = getChildAt(i);
	            if (child.getVisibility() != View.GONE) {
	                final int childWidth = child.getMeasuredWidth();
	                child.layout(0, 0,  childWidth, child.getMeasuredHeight());
	            }
	        }
            testLayout=(LinearLayout) getChildAt(1);

	}
	public PopupWindow getPressPreview() {
		return pressPreview;
	}

	public void setPressPreview(PopupWindow pressPreview) {
		this.pressPreview = pressPreview;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int width = getDefaultSize(0, widthMeasureSpec);
		int height = getDefaultSize(0, heightMeasureSpec);
		setMeasuredDimension(width, height);
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
		    getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
	}
	 
		@Override
   public boolean onInterceptTouchEvent(MotionEvent ev) {

			 int action = ev.getAction();
		     switch (action){
		            case MotionEvent.ACTION_DOWN:

		                y = ev.getY();
		                break;
		            case MotionEvent.ACTION_UP:

		                break;
		            case MotionEvent.ACTION_MOVE:
//		            	determineDrag(ev);
//		            	changeSpecial(ev);
		            	break;
		     }
	   return (isBeginDrag&&!isOpen||special)&&isSuccessfulGetKJ;
   }
   private void changeSpecial(MotionEvent ev){
	   final float preY = y;
       float nowY = ev.getY();
       int deltaY =(int) (preY - nowY);
	   if(isOpen&&testLayout.getChildAt(0).getScrollY()==0&&deltaY>0)
		   special=true;
   }
		@Override
   public boolean onTouchEvent(MotionEvent ev) {
			 if(pressPreview!=null&&pressPreview.isShowing())
				 pressPreview.dismiss();
			 int action = ev.getAction();
		     switch (action){
		            case MotionEvent.ACTION_DOWN:
		                y = ev.getY();
		                break;
		            case MotionEvent.ACTION_UP:
//		            	if(pressPreview!=null&&pressPreview.isShowing())
//		   				 pressPreview.dismiss();
//		            	float  ScrollY=getChildAt(1).getScrollY();
//		            	if(ScrollY<0){
//		            		    if(!isOpen&&Math.abs(ScrollY)>mHeight/2){
//		            		    	isShow=true;
//			            		     mScroller.startScroll(0, (int)ScrollY, 0, -mHeight-(int)ScrollY, MAX_SETTLE_DURATION);
//			            		 	 invalidate();
//			            			 isOpen=true;
//		            			}else{
//		            				 mScroller.startScroll(0, (int)ScrollY, 0, -(int)ScrollY, MAX_SETTLE_DURATION);
//				            		 invalidate();
//			            			 isOpen=false;
//			            			 isBeginDrag=false;
//			            			 special=false;
//			            			 isShow=false;
//		            			}
//		            	}else if(ScrollY>0){
//		            		  mScroller.startScroll(0, (int)ScrollY, 0, -(int)ScrollY, MAX_SETTLE_DURATION);
//		            		  invalidate();
//		            		  isOpen=false;
//		            		  isBeginDrag=false;
//		            		  special=false;
//		            		  isShow=false;
//		            	}else {
//		            		isOpen=false;
//		            		special=false;
//		            		 isShow=false;
//		            	}
		               break;
		            case MotionEvent.ACTION_MOVE:
		                final float preY = y;
		                float nowY = ev.getY();
		                int deltaY = (int) (preY - nowY);//为负向下，为正向上
		                // 滚动
		                if(isCanMove(deltaY ))
		                getChildAt(1).scrollBy(0, deltaY);

		                y = nowY;
		            	break;
		     }
	   return true;
   }
   public  void  toggle(){
	   mScroller.startScroll(0, 0, 0, -mHeight, MAX_SETTLE_DURATION);
	   invalidate();
	   isShow=true;
	   map.clear();

   }
   public  void  close(){
	   mScroller.startScroll(0, -mHeight, 0, mHeight, MAX_SETTLE_DURATION);
	   invalidate();
	   isShow=false;

   }
   @Override
	public void computeScroll() {
		super.computeScroll();
		if (mScroller.computeScrollOffset()) {    
			getChildAt(1).scrollTo(0, mScroller.getCurrY());  
            postInvalidate();    
        }   
	}
   private void determineDrag(MotionEvent ev) {
	   final float preY = y;
       float nowY = ev.getY();
       int deltaY =(int) (preY - nowY);
     //当scrollview在最上方时且下滑动
       if(testLayout.getChildAt(0).getScrollY()==0&&deltaY<-5)
           isBeginDrag=true;
       else  if(testLayout.getChildAt(0).getScrollY()>=0&&deltaY>0)
    	   isBeginDrag=false;
       else  if(testLayout.getChildAt(0).getScrollY()>0&&deltaY<0)
    	   isBeginDrag=false;
	}
   private boolean  isCanMove(int deltaY ){
//	   if(deltaY<0&&Math.abs(getChildAt(1).getScrollY())>mHeight){
//		   return false;
//		}
//	   else if(deltaY>0&&getChildAt(1).getScrollY()>=0)
//	   {
//		   return false;
//		}
//	   return true;
	   return  false;
	   
   }

	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

}
