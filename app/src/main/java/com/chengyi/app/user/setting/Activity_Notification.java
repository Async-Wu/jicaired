package com.chengyi.app.user.setting;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.view.widget.WheelView;
import com.chengyi.app.view.widget.adapters.ArrayWheelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */

public class  Activity_Notification extends BaseActivity {

	private SharedPreferences prefence;
	private String [] tixingStr,timeStr;
	private List<CheckBox> list=new ArrayList<CheckBox>();
	private String tixing;
	int  index=3;
	TextView tiXingText;
	Editor editor;
	AlarmManager  am;
	PendingIntent  pendingIntent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_notification);
		timeStr=new String[]{"15分钟","30分钟","1小时","2小时","3小时"};
		prefence=getSharedPreferences("kaijiangandtixing", MODE_PRIVATE);
		//获取购彩提醒设置的数组
		tixing=prefence.getString("goucaitixingshezhi", "false,false,false,false,false,false,false,false,false,");
		tixingStr=tixing.split(",");
		index=prefence.getInt("index", 3);
		editor=prefence.edit();
		initView();
	}
    private void initView(){
		setCusTomeTitle("购彩时间提醒");
		 setBack();
		list.add((CheckBox) findViewById(R.id.checkbox1));
		list.add((CheckBox) findViewById(R.id.checkbox2));
		list.add((CheckBox) findViewById(R.id.checkbox3));
		list.add((CheckBox) findViewById(R.id.checkbox4));
		list.add((CheckBox) findViewById(R.id.checkbox5));
		list.add((CheckBox) findViewById(R.id.checkbox6));
		list.add((CheckBox) findViewById(R.id.checkbox7));
		list.add((CheckBox) findViewById(R.id.checkbox8));
		list.add((CheckBox) findViewById(R.id.checkbox9));
		for(int i=0;i<list.size();i++)
			list.get(i).setChecked(Boolean.parseBoolean(tixingStr[i]));
		findViewById(R.id.shezhitime).setOnClickListener(this);
		tiXingText=(TextView) findViewById(R.id.tixing);
		tiXingText.setText(timeStr[index]+"提醒");
    }
	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.shezhitime:
			getWheelViewTimePop().showAtLocation(findViewById(R.id.mainlayout), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
		break;
		case R.id.cancel:
			if(wheelViewBankPop.isShowing())
				wheelViewBankPop.dismiss();
		break;
		case R.id.ensure:
			if(wheelViewBankPop.isShowing()){
				editor.putInt("index", wheelView.getCurrentItem());
				switch(wheelView.getCurrentItem()){
					case 0:
						editor.putInt("jizhitime", 15);
					break;
					case 1:
						editor.putInt("jizhitime", 30);
					break;
					case 2:
						editor.putInt("jizhitime", 60);
					break;
					case 3:
						editor.putInt("jizhitime", 120);
					break;
					case 4:
						editor.putInt("jizhitime", 180);
					break;
				}
				editor.commit();
				tiXingText.setText(timeStr[wheelView.getCurrentItem()]+"提醒");
				wheelViewBankPop.dismiss();
			}
		break;
		}
	}
	@Override
	protected void onDestroy() {

		super.onDestroy();
		String s="";
		boolean isModify=false;
		for(int i=0;i<list.size();i++){
			s+=list.get(i).isChecked()+",";
			if(list.get(i).isChecked())
				isModify=true;
		}
		boolean b=index!=prefence.getInt("index", 3);
		if(!s.equals(tixing)||b){
			if(!s.equals(tixing))
			editor.putString("goucaitixingshezhi", s);
			//开启定时器,条件:设置被修改了且有选项被打开的
			am =  (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
//	        pendingIntent= CaipiaoApplication.getInstance().getPendingIntent();
//	        am.cancel(pendingIntent);
//			if(isModify||b){
//				long inteneval= CaipiaoUtil.getNotificationTime(s, prefence.getInt("jizhitime", 120));
//				if(inteneval>0){
//					editor.putString("tixingcontext", CaipiaoUtil.getContext());
//		            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+inteneval, pendingIntent);
//				}
//			}else{
//		        am.cancel(pendingIntent);
//			}
			editor.commit();
		}
	}
	private  PopupWindow getWheelViewTimePop(){
			if(vPopupWindowBank==null){
				vPopupWindowBank = getLayoutInflater().inflate(R.layout.pop_wheelview, null, false);
				wheelViewBankPop = new PopupWindow(vPopupWindowBank,LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				wheelViewBankPop.setBackgroundDrawable(new BitmapDrawable());
				wheelViewBankPop.setFocusable(true);
				wheelViewBankPop.setOutsideTouchable(true);
				try {
					wheelViewBankPop.setAnimationStyle(R.style.PopWindowAnimation);
				} catch (Exception e) {
					e.printStackTrace();
				}
				cancel=(Button) vPopupWindowBank.findViewById(R.id.cancel);
				cancel.setOnClickListener(this);
				ensure=(Button) vPopupWindowBank.findViewById(R.id.ensure);
				ensure.setOnClickListener(this);
				vPopupWindowBank.findViewById(R.id.wheelviewtwo).setVisibility(View.GONE);
				wheelView= (WheelView) vPopupWindowBank.findViewById(R.id.wheelviewone);
				wheelView.setVisibleItems(5);
				wheelView.setViewAdapter(new ArrayWheelAdapter<String>(this,timeStr));
				wheelView.setCurrentItem(index);
				vPopupWindowBank.findViewById(R.id.imagebg).setOnClickListener(this);
			}
			return wheelViewBankPop;
		}
}
