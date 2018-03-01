package com.chengyi.app.util;

import com.chengyi.app.CaipiaoApplication;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  L {
	 private static final boolean DEBUG =AppUtil.isApkDebugable(CaipiaoApplication.getInstance());
	 public static final String TAG = "stander";
	 
	 public static void v( String msg) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.v(TAG, msg); 
	        } 
	    } 
	    public static void v(String tag, String msg) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.v(tag, msg); 
	        } 
	    } 
	    public static void v(String tag, String msg, Throwable tr) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.v(tag, msg, tr); 
	        } 
	    } 
	    
	    public static void d(String msg) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.d(TAG, msg); 
	        } 
	    } 
	    public static void d(String tag, String msg) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.d(tag, msg); 
	        } 
	    } 
	    public static void d(String tag, String msg, Throwable tr) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.d(tag, msg, tr); 
	        } 
	    } 
	    
	    public static void i(String msg) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.i(TAG, msg); 
	        } 
	    } 
	    public static void i(String tag, String msg) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.i(tag, msg); 
	        } 
	    } 
	    public static void i(String tag, String msg, Throwable tr) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.i(tag, msg, tr); 
	        } 
	    } 
	    
	    public static void w(String msg) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.w(TAG, msg); 
	        } 
	    } 
	    public static void w(String tag, String msg) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.w(tag, msg); 
	        } 
	    } 
	    public static void w(String tag, String msg, Throwable tr) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.w(tag, msg, tr); 
	        } 
	    } 
	    public static void w(String tag, Throwable tr) { 
	        if(DEBUG) { 
	            android.util.Log.w(tag, tr); 
	        } 
	    } 
	    public static void e( String msg) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.e(TAG, msg); 
	        } 
	    } 
	    public static void e(String tag, String msg) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.e(tag, msg); 
	        } 
	    } 
	    public static void e(String tag, String msg, Throwable tr) { 
	        if(DEBUG&&msg!=null) { 
	            android.util.Log.e(tag, msg, tr); 
	        } 
	    } 
}
