package com.chengyi.app.view.widget;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Button;

import com.chengyi.R;
import com.chengyi.app.num.lottery.Activity_CaipiaoTouZhu;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  TouzhuButton extends Button {
	Activity mActivity;

	public TouzhuButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public TouzhuButton(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		mActivity = (Activity) context;
		if(mActivity instanceof Activity_CaipiaoTouZhu){
			xuanhaoLinearLayout=((Activity_CaipiaoTouZhu) mActivity).getXuanhaoView();
		}else{
			xuanhaoLinearLayout = (XuanhaoLinearLayout) mActivity
					.findViewById(R.id.xuanhaoView);
		}
	}

	public static void setMoniTouchUp(final float x, final float y) {
		new Thread() {

			@Override
			public void run() {
				super.run();
				Instrumentation inst = new Instrumentation();

				inst.sendPointerSync(MotionEvent.obtain(
						SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
						MotionEvent.ACTION_UP, x, y, 0));
			}
		}.start();

	}

	public static void setMoniTouchDown(final float x, final float y) {
		new Thread() {

			@Override
			public void run() {
				super.run();
				Instrumentation inst = new Instrumentation();

				inst.sendPointerSync(MotionEvent.obtain(
						SystemClock.uptimeMillis(), SystemClock.uptimeMillis(),
						MotionEvent.ACTION_DOWN, x, y, 0));
			}

		}.start();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
//		if (showKeyPress) {
//			switch (event.getAction()) {
//			case MotionEvent.ACTION_DOWN:
//				//showKeyPress();
//				break;
//			case MotionEvent.ACTION_UP:
//				dismissKeyPress();
//				System.out.println("btn---->ACTION_UP");
//				break;
//			case MotionEvent.ACTION_MOVE:
//				if (changedText(event.getRawX(), event.getRawY())) {
//					if (isShowingPop()) {
//						xuanhaoLinearLayout.getPressPreviewPop().dismiss();
//						// asetMoniTouchUp(event.getRawX(), event.getRawY());
//						// sendUp = true;
//						// setMoniTouchDown(event.getRawX(), event.getRawY());
//					}
//				}
//				xuanhaoLinearLayout.ensureDismissKeyPress(1000);
//				break;
//			}
//		}
		return super.onTouchEvent(event);
	}

	int[] location = new int[2];

	XuanhaoLinearLayout xuanhaoLinearLayout;

	public static Rect rect = new Rect();

	Handler handler = new Handler();
	Runnable showRun = new Runnable() {

		@Override
		public void run() {
			try {
				if (xuanhaoLinearLayout == null) {
					return;
				}
				xuanhaoLinearLayout.getPressPreviewPop().dismiss();
				int previewWidth = (int) (xuanhaoLinearLayout
						.getPreviewTextView().getPaint()
						.measureText(getText() + "")
						+ getPaddingLeft() + getPaddingRight())
						+ getWidth();
				int previewHeight = previewWidth;
				// if (previewWidth > 100) {// 如果按键比较宽，则预览宽度不要太大
				// previewWidth = getWidth();
				// }

				getLocationOnScreen(location);
				int showX = location[0] - (previewWidth - getWidth()) / 2;
				int showY = location[1] - previewHeight * 4 / 3;

				rect.left = location[0];
				rect.top = location[1];
				rect.right = location[0] + getWidth();
				rect.bottom = location[1] + getHeight();
				xuanhaoLinearLayout.getPreviewBg().setBackgroundResource(
						previewBg);
				xuanhaoLinearLayout.getPreviewTextView().setText(getText());
				xuanhaoLinearLayout.getPressPreviewPop().showAtLocation(
						mActivity.getWindow().getDecorView(),
						Gravity.NO_GRAVITY, showX, showY);// 显示到按钮上方

				xuanhaoLinearLayout.getPressPreviewPop().update(showX, showY,
						previewWidth, previewHeight);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	};

	private int previewBg = 0;

	public void setPreviewBg(int bg) {
		previewBg = bg;
	}
}
