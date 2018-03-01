package com.chengyi.app.user.info;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.CacheFactory;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.L;
import com.chengyi.app.util.YOUMENG_EVENT;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_Authentication extends BaseActivity {
    
	private Button sendAgain,ensureBtn;
	private ImageView closeImg;
	private TextView  textView;//重新发送倒计时
	private EditText phoneCode,yanZhengMa;
	private int  time=60;

	private boolean isFromSuccess=false;
	private String phone;
	boolean loginFrom;   //// 登录来源，如果是购彩时发现未登录，登录成功后返回购彩页面

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_lingqu_layout);
		isFromSuccess=this.getIntent().getBooleanExtra("isFromSuccess", false);
		loginFrom = getIntent().getBooleanExtra("isfromgoumai", false);
		phone=this.getIntent().getStringExtra("phonecode");
		textView=(TextView) findViewById(R.id.textView2);
		closeImg=(ImageView) findViewById(R.id.imageViewclose);
		closeImg.setOnClickListener(this);
		phoneCode=(EditText) findViewById(R.id.phonecode);
		yanZhengMa=(EditText) findViewById(R.id.EditText02);
		yanZhengMa.requestFocus();
		sendAgain=(Button) findViewById(R.id.button1);
		sendAgain.setOnClickListener(this);
		ensureBtn=(Button) findViewById(R.id.querenbtn);
		ensureBtn.setOnClickListener(this);


		if(isFromSuccess){
			phoneCode.setText(phone);
			findViewById(R.id.toplayout).setVisibility(View.GONE);

			setCusTomeTitle("手机绑定");
			setBack();
		}else{

			if(!TextUtils.isEmpty(CaipiaoApplication.getInstance().getPhoneNum()))
				phoneCode.setText(CaipiaoApplication.getInstance().getPhoneNum());
		}
		//启动发送验证码
		sendYanZhengMa();

	}
	Runnable run=new Runnable(){
		@Override
		public void run() {

			if(time>0){
				time--;
				textView.setText(String.format(getString(R.string.yanzhengmadaojishi), time));
				handler.postDelayed(run,1000);
			}else{
				textView.setText("重新获取验证码");
				sendAgain.setClickable(true);
				Activity_Authentication.this.getResources().getDrawable(R.drawable.sendagain);
				handler.removeCallbacks(run);
			}
			
		}
	};
	//发送手机验证码
    private void   sendYanZhengMa(){
    	textView.setText("验证码发送中...");
    	sendAgain.setClickable(false);
    	RequestParams params = getRequestParams();
		params.put(URLSuffix.mobile, phoneCode.getText().toString());
		HttpBusinessAPI.post(URLSuffix.PHONE_YANZHENG_test, params,
				new HttpRespHandler() {
					@Override
					public void onFailure(Throwable error) {

						super.onFailure(error);
						textView.setText("验证码失败!");
						sendAgain.setClickable(true);
						Activity_Authentication.this.getResources().getDrawable(R.drawable.sendagain);
						handler.removeCallbacks(run);
					}

					@Override
					public void onSuccess(String response) {

						super.onSuccess(response);
						L.d("绑定手机返回", response.toString());
						if (checkResult(response)) {
							handler.postDelayed(run, 1000);
						} else {
							textView.setText("验证码发送失败!");
							//handler.removeCallbacks(run);
						}
					}
				});
    }
    //提交验证码
    private void  sumbitvalidateCode(){
    	textView.setText("重新获取验证码");
		sendAgain.setClickable(true);
		handler.removeCallbacks(run);
    	showLoading("正在处理...");
    	RequestParams params = getRequestParams();
		params.put("validateCodeCheck", yanZhengMa.getText().toString());
		HttpBusinessAPI.post(URLSuffix.SUBMIT_VALIDATECODE, params,
				new HttpRespHandler() {
					@Override
					public void onFailure(Throwable error) {

						super.onFailure(error);
						hideLoading();
						showToast("验证码提交失败!");

					}
					@Override
					public void onSuccess(String response) {

						super.onSuccess(response);
						hideLoading();
						if(checkResult(response)) {
//

							//设置此值当切换到个人中心时，更新数据
							CacheFactory.getInstance().putTime("goucailist",0);
							closeSoftKeybord();
							if(!TextUtils.isEmpty(JSON.parseObject(response).getString("rightMessage")))
								showToast(JSON.parseObject(response).getString("rightMessage"));
							else
							    showToast("手机验证成功!");
							L.d("isfromsuccess",isFromSuccess+"");
							if(loginFrom){
								finish();
							}else {
								finish();
							}


						}
					}
		});
    }
    public  void  closeSoftKeybord(){
    	if(getCurrentFocus()!=null)  
        {  
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))  
            .hideSoftInputFromWindow(getCurrentFocus()  
                    .getWindowToken(),  
                    InputMethodManager.HIDE_NOT_ALWAYS);   
        }  
    }
	@Override
	public void onClick(View v) {

		map.clear();
		closeSoftKeybord();
		super.onClick(v);
		switch(v.getId()){
		case R.id.button1:
			String check = check();
			if (check != null) {
				showToast(check);
			} else {
				 time=60;
				//发送验证码
				 sendYanZhengMa();
			}
			map.put("操作类型","重新发送验证码");
			CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_register,map);
			break;
	    case R.id.querenbtn:
	    	if(TextUtils.isEmpty(yanZhengMa.getText().toString())){
	    		showToast("验证码不能为空!");
	    	}else{
	    		//进行验证
	    		sumbitvalidateCode();
	    	}
	    	map.put("操作类型","进行手机验证");
			CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_register,map);
		break;
	    case R.id.imageViewclose:
	    	//切换右边菜单到个人中心
	    	finish();
	    	if(loginFrom){
	    	finish();
	    	}else
			  finish();
	    break;

	   }
	}
	public  final String PHONE_RULE = "(^[0-9]{3,4}-[0-9]{3,8}$)|^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|2|3|5|6|7|8|9])\\d{8}$";
	private String check() {
		if (TextUtils.isEmpty(phoneCode.getText().toString())) {
			return getString(R.string.shoujihaomabunengweikong);
		}
		if (phoneCode.getText().toString().length()!=11) {
			return getString(R.string.shoujihaomageshicuowu);
		}
		if (!phoneCode.getText().toString().matches(PHONE_RULE)) {
			return getString(R.string.shoujihaomageshicuowu);
		}
		return null;
	}
}
