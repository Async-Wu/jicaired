package com.chengyi.app.receiver;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.util.CaipiaoUtil;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  BootReceiver extends BroadcastReceiver {
    
	private SharedPreferences prefence;
	Editor editor;
	@Override
	public void onReceive(Context context, Intent intent) {

		    ///开启开奖公告信息推送服务		        
			//定时提醒服务开启
		 prefence=context.getSharedPreferences("kaijiangandtixing", Context.MODE_PRIVATE);
		 String s=prefence.getString("goucaitixingshezhi", "false,false,false,false,false,false,false,false,false,");
		 editor=prefence.edit();
         AlarmManager  am =  (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
         PendingIntent  pendingIntent= CaipiaoApplication.getInstance().getPendingIntent();
         am.cancel(pendingIntent);
         long inteneval= CaipiaoUtil.getNotificationTime(s, prefence.getInt("jizhitime", 120));
		 if(inteneval>0){
				editor.putString("tixingcontext",  CaipiaoUtil.getContext());
	            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+inteneval, pendingIntent);
	            editor.commit();
		 }
          
	}
}
