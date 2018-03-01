package com.chengyi.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.chengyi.R;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  TiShiDialog extends Dialog implements View.OnClickListener {
    
	private TextView  text1,text2,title;
	public TextView getTitle() {
		return title;
	}
	public TextView getText1() {
		return text1;
	}
	public TextView getText2() {
		return text2;
	}
	ImageView close;
	public TiShiDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//设置对话框不显示标题
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    super.onCreate(savedInstanceState);
	    this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	    setCanceledOnTouchOutside(true);
	    setContentView(R.layout.new_tishi_dailog);
	    text1=(TextView) findViewById(R.id.textView2);
	    text2=(TextView) findViewById(R.id.textView3);
	    close=(ImageView) findViewById(R.id.imageView1);
	    title=(TextView) findViewById(R.id.textView1);
	    close.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {

		switch(v.getId()){
			case R.id.imageView1:
	            if(this.isShowing())
	            	this.dismiss();
			break;
	    }
	}
}
