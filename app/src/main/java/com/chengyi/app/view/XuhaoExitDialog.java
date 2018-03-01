package com.chengyi.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.chengyi.R;


/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  XuhaoExitDialog extends Dialog implements View.OnClickListener {

	private  Button  yesBtn,noBtn;
	public Button getYesBtn() {
		return yesBtn;
	}
	public Button getNoBtn() {
		return noBtn;
	}

	private ICallback  callBack;
	private TextView  centerTextView;
	private View vIn;
	public XuhaoExitDialog(Context context) {
		super(context);
	}
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//设置对话框不显示标题
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    super.onCreate(savedInstanceState);
	    this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	    setCanceledOnTouchOutside(true);
	    setContentView(R.layout.new_xuhao_exit_dailog);
	    initView();
	}
	public void  initView(){
		yesBtn=(Button) findViewById(R.id.buttonyes);
		noBtn=(Button) findViewById(R.id.buttonno);
		yesBtn.setOnClickListener(this);
		noBtn.setOnClickListener(this);
		centerTextView=(TextView) findViewById(R.id.centerTextView);
		vIn=findViewById(R.id.v_in);
	}
	public TextView getCenterTextView() {
		return centerTextView;
	}

	public View getvIn() {
		return vIn;
	}

	@Override
	public void onClick(View v) {

		switch(v.getId()){
			case R.id.buttonyes:
				callBack.reBack();
				break;
			case R.id.buttonno:
				callBack.close();
				break;
		}
	}
	public interface ICallback {
		  void  reBack();
		  void  close();
	}
	public void setCallBack(ICallback callBack) {
		this.callBack = callBack;
	}
}
