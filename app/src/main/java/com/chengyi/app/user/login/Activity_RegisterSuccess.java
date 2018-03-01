package com.chengyi.app.user.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.user.info.Activity_Authentication;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_RegisterSuccess extends BaseActivity implements View.OnClickListener {
    
	private Button lingqubtn;
	private ImageView closeImg;
	private EditText  phoneCode;
	String fromFlag="";

	boolean loginFrom;     //// 登录来源，如果是购彩时发现未登录，登录成功后返回购彩页面
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_register_success);
		loginFrom = getIntent().getBooleanExtra("isfromgoumai", false);
		lingqubtn=(Button) findViewById(R.id.lingqubtn);
		lingqubtn.setOnClickListener(this);
		closeImg=(ImageView) findViewById(R.id.imageViewclose);
		closeImg.setOnClickListener(this);
		phoneCode=(EditText) findViewById(R.id.shoujihaoma);
		phoneCode.setText("");
		fromFlag=this.getIntent().getStringExtra("fromflag");
		if(fromFlag!=null&&fromFlag.trim().length()>0){
			findViewById(R.id.successlayout).setVisibility(View.GONE);
			phoneCode.setText("");
			findViewById(R.id.toplayout).setVisibility(View.GONE);

			setCusTomeTitle("手机绑定");
			setBack();
			//重新绑定
			if(getIntent().getBooleanExtra("isChongBang", false)){
				findViewById(R.id.textView3).setVisibility(View.GONE);
				setCusTomeTitle("手机重绑");
				lingqubtn.setText("立即重绑");
			}
		}else{

			if(!TextUtils.isEmpty(CaipiaoApplication.getInstance().getPhoneNum()))
			phoneCode.setText(CaipiaoApplication.getInstance().getPhoneNum());
		}
		phoneCode.setSelection(phoneCode.getText().length());
	}
	@Override
	public void onClick(View v) {

		map.clear();
		closeSoftKeybord();
		super.onClick(v);
		switch(v.getId()){
		    case R.id.lingqubtn:
		    	String check = check();
				if (check != null) {
					showToast(check);
				} else {
					if(fromFlag!=null&&fromFlag.trim().length()>0){
						Intent intent=new Intent(this,Activity_Authentication.class);
						intent.putExtra("isFromSuccess", true);
						intent.putExtra("phonecode", phoneCode.getText().toString());
						startActivity(intent);
						pullUpActivityAnim();
						finish();
					}else{
						if(!TextUtils.isEmpty(getSession())) {
							CaipiaoApplication.getInstance().setPhoneNum(phoneCode.getText().toString());
							//切换到领取页面
							startActivity(new Intent(getBaseContext(), Activity_Authentication.class));
						}else {
							startActivity(new Intent(getBaseContext(), LoginActivity.class));
						}
					}

				}
			break;
		    case R.id.imageViewclose:

		    	finish();

		    break;

		}
	}
	@Nullable
	private String check() {
		if (TextUtils.isEmpty(phoneCode.getText().toString())) {
			return getString(R.string.shoujihaomabunengweikong);
		}
		if (phoneCode.getText().toString().length()!=11||!phoneCode.getText().toString().substring(0, 1).equals("1")) {
			return getString(R.string.shoujihaomageshicuowu);
		}
		return null;
	}
}
