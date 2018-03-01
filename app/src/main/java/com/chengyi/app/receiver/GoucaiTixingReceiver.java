package com.chengyi.app.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.chengyi.app.CaipiaoApplication;

import com.chengyi.app.home.home.HomeActivity;
import com.chengyi.app.util.CaipiaoUtil;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  GoucaiTixingReceiver extends BroadcastReceiver {
	public static final int NOTIFICATION_ID=1; 
	private String content;
	private SharedPreferences prefence;
	Editor editor;
	@Override
	public void onReceive(Context context, Intent intent) {

		    prefence=context.getSharedPreferences("kaijiangandtixing", Context.MODE_PRIVATE);
		    content=prefence.getString("tixingcontext", "");
		    String s=prefence.getString("goucaitixingshezhi", "false,false,false,false,false,false,false,false,false,");
		    editor=prefence.edit();
		    Intent in = new Intent()  
		    .setAction(Intent.ACTION_MAIN)   
		    .setClass(context, HomeActivity.class);
			in.addCategory(Intent.CATEGORY_LAUNCHER);
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
			if(content!=null&&!TextUtils.isEmpty(content)&&content.trim().length()>0){
	            	CaipiaoUtil.showNotification(context, "彩票2元网温馨提示", in, "购彩提醒", "亲," + content + "当前期即将截止销售,快去买一注吧", NOTIFICATION_ID);
	        }
			 AlarmManager  am =  (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	         PendingIntent  pendingIntent= CaipiaoApplication.getInstance().getPendingIntent();
	         am.cancel(pendingIntent);
	         long inteneval=CaipiaoUtil.getNotificationTime(s,prefence.getInt("jizhitime", 120));
			 if(inteneval>0){
					editor.putString("tixingcontext",  CaipiaoUtil.getContext());
		            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+inteneval, pendingIntent);
		            editor.commit();
			 }
        }
}
