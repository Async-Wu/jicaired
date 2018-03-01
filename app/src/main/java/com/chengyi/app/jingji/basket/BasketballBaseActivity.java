package com.chengyi.app.jingji.basket;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.util.FragId;

import java.util.ArrayList;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class BasketballBaseActivity extends BaseActivity {


    protected ImageView mainbound;
    boolean isActive = true;

    //+++++++++++++++++++++++++++++++++++Fragment Manage start
    private SparseArray<Fragment> mFragmentArray;
    private FragmentManager mFragMag;
    private Fragment mLeftCurrentContent;
    private Fragment mMiddleCurrentContent;
    private Fragment mRightCurrentContent;

    private ArrayList<Integer> needToBeRemovedFrage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mFragmentArray == null) {
            mFragmentArray = new SparseArray<Fragment>();
            needToBeRemovedFrage = new ArrayList<Integer>();
            mFragMag = getSupportFragmentManager();
        }
    }

//	public void changeConitainer(){
//
//	}
//
//	public void showInLeftContainer(int fragmentId){
//		Fragment to=null;
//		if(needToBeRemovedFrage.contains(fragmentId)){
//			to=recreateFragment(fragmentId);
//		}else{
//			to=getFragment(fragmentId);
//		}
//		if(to==null){
//			return;
//		}
//		switchContent(mLeftCurrentContent,to,R.id.left_container,false,false);
//
//	}
//
//
//	public void showInMiddleContainer(int fragmentId){
//		Fragment to=getFragment(fragmentId);
//		if(to==null){
//			return;
//		}
//		switchContent(mMiddleCurrentContent,to,R.id.main_container,false,false);
//
//	}
//
//	public void showInMiddleContainer(int fragmentId,boolean isDetachAfterHide,boolean isRemoveAfterHide){
//		Fragment to=getFragment(fragmentId);
//		if(to==null){
//			return;
//		}
//		if(isRemoveAfterHide){
//			needToBeRemovedFrage.add(fragmentId);
//		}
//
//		switchContent(mMiddleCurrentContent,to,R.id.main_container,isDetachAfterHide,isRemoveAfterHide);
//
//	}


//	public void showInRightContainer(int fragmentId){
//		Fragment to=getFragment(fragmentId);
//		if(to==null){
//			return;
//		}
//		switchContent(mRightCurrentContent,to,R.id.right_container,false,false);
//
//	}


    public void showFragment(int fragmentId, int containerId) {
        Fragment to = getFragment(fragmentId);
        if (to == null) {
            return;
        }
        switchContent(mRightCurrentContent, to, containerId, false, false);
    }

    private void switchContent(Fragment currentContent, Fragment to, int containerLayoutId, boolean isDetachAfterHide, boolean isRemoveAfterHide) {
        if (currentContent != null && currentContent != to) {


            FragmentTransaction transaction = mFragMag.beginTransaction();
//			transaction.setCustomAnimations(
//					R.anim.slide_in_right,
//			         R.anim.slide_out_left);

            transaction.hide(currentContent);

            currentContent = to;


            if (!to.isDetached()) {
                if (!to.isAdded()) {
                    transaction.add(containerLayoutId, to).commitAllowingStateLoss();
                } else {
                    transaction.show(to).commitAllowingStateLoss();
                }
            } else {
                transaction.attach(to).commitAllowingStateLoss();

            }
        } else if (currentContent == null) {
            currentContent = to;
            FragmentTransaction transaction = mFragMag.beginTransaction();
//			transaction.setCustomAnimations(
//					 R.anim.slide_in_right,
//			         R.anim.slide_out_left);
            transaction.replace(containerLayoutId, to).commitAllowingStateLoss();
        }
    }

    private Fragment getFragment(int fragmentId) {
//		if(fragmentId==FragId.basketball_rfsf){
//			fragmentId=FragId.basketball_sf;
//		}
        Fragment frag = mFragmentArray.get(fragmentId);
        if (frag == null) {
            frag = createFrag(fragmentId);
            if (frag == null) {
                return null;
            }
            mFragmentArray.put(fragmentId, frag);
        }
        return frag;

    }

    private Fragment createFrag(int position) {

        Fragment f = null;
        switch (position) {
            case FragId.basketball_sf:
                f = new BasketballSF();
                break;
            case FragId.basketball_rfsf:
                f = new BasketballSF();
                break;
            case FragId.basketball_sfc:
                f = new BasketballSFC();
                break;
            case FragId.basketball_bigsmall:
                f = new BasketballSF();
                break;
            case FragId.basketball_mix:
                f=new Basketballmix();
                break;
//		case FragId.login:
//			f=new Login();
//			break;
//		case FragId.discovery:
//			f=new Discovery();
//			break;
//		
            default:
                return null;
        }
        return f;
    }

    private Fragment recreateFragment(int fragId) {
        Fragment f = mFragmentArray.get(fragId);
        try {
            Fragment.SavedState savedState = mFragMag.saveFragmentInstanceState(f);

            Fragment newInstance = f.getClass().newInstance();
            newInstance.setInitialSavedState(savedState);
            mFragmentArray.put(fragId, newInstance);
            return newInstance;
        } catch (Exception e) // InstantiationException, IllegalAccessException
        {
            throw new RuntimeException("Cannot reinstantiate fragment " + f.getClass().getName(), e);
        }
    }
    //===================================Fragment Manage end

    //+++++++++++++++++++++++++++++++++++UI start
//	// 显示
//	protected Runnable mViewCome = new Runnable() {
//		@Override
//		public void run() {
//			AlphaAnimation animationClose;
//			animationClose = new AlphaAnimation(0f, 1f);
//			animationClose.setDuration(500);
//			mainbound.setAnimation(animationClose);
//			animationClose.startNow();
//			mainbound.setVisibility(View.VISIBLE);
//		}
//	};
//	// 隐藏
//	protected Runnable mViewGone = new Runnable() {
//		@Override
//		public void run() {
//			AlphaAnimation animationClose;
//			animationClose = new AlphaAnimation(1f, 0f);
//			animationClose.setDuration(500);
//			mainbound.setAnimation(animationClose);
//			animationClose.startNow();
//			mainbound.setVisibility(View.GONE);
//		}
//	};

    public void closeSoftKeybord() {
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    ProgressDialog pd;

    public void showLoading(String s) {
        try {
            if (pd == null) {
                pd = new ProgressDialog(this);
                if (s.equals(""))
                    pd.setMessage(getString(R.string.loading));
                else
                    pd.setMessage(s);
            }
            if (!pd.isShowing()) {
                pd.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideLoading() {
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestory() {
        super.onDestroy();
        if (mFragmentArray != null) {
            mFragmentArray.clear();
        }
    }

}
