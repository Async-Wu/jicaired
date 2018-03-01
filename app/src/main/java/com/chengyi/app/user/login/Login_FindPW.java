package com.chengyi.app.user.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.alibaba.fastjson.JSON;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.view.ClearEditText;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Login_FindPW extends BaseActivity {
    

	
	private Button  querenbtn;
	private ClearEditText userName,phoneCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_login_find_pw);
		 setBack();

		setCusTomeTitle("找回密码");

		userName=(ClearEditText) findViewById(R.id.zhanghao);
		phoneCode=(ClearEditText) findViewById(R.id.EditText02);
		querenbtn=(Button) findViewById(R.id.querenbtn);
		querenbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				closeSoftKeybord();
				zhaohui() ;
			}
		});



	}
	public  final String PHONE_RULE = "(^[0-9]{3,4}-[0-9]{3,8}$)|^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|2|3|5|6|7|8|9])\\d{8}$";

	private void zhaohui() {
		String yhm = userName.getText().toString().trim();
		String sjh = phoneCode.getText().toString().trim();
		if (TextUtils.isEmpty(yhm)) {
			showToast("请输入用户名");
			return;
		}
		if (TextUtils.isEmpty(sjh)) {
			showToast("请输入手机号码");
			return;
		}
		if (!sjh.matches(PHONE_RULE)) {
			showToast("手机号码格式不正确");
			return;
		}
		showLoading("正在处理...");
		RequestParams params = getRequestParams();
		params.put(URLSuffix.username, yhm);
		params.put(URLSuffix.mobile, sjh);
		HttpBusinessAPI.post(URLSuffix.GET_FORGET_PASSWORD_test, params, new HttpRespHandler() {
					@Override
					public void onFailure(Throwable error) {

						super.onFailure(error);
						hideLoading();
						showToast("数据提交失败,请重试!");
					}

					@Override
					public void onSuccess(String result) {
						hideLoading();
						if (JSON.parseObject(result).getIntValue(URLSuffix.flag) == 0) {
							String errorMsg = JSON.parseObject(result).getString(URLSuffix.errorMessage);
							showToast(errorMsg);
						} else {
							showToast("您的手机将会收到新密码短信");
						}
					}
				});
	}
}
