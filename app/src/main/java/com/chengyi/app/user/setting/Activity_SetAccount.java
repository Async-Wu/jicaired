package com.chengyi.app.user.setting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.view.ClearEditText;

import java.io.UnsupportedEncodingException;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_SetAccount extends BaseActivity {

    private static final String TAG = Activity_SetAccount.class.getSimpleName();
    private ClearEditText accountEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_setaccount);
        setupUI(findViewById(R.id.mainlayout));

        setCusTomeTitle("用户名设置");
        accountEditText = (ClearEditText) findViewById(R.id.accountEditText);
        setBack();

        findViewById(R.id.querenbtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String account = accountEditText.getText().toString().trim();
                        if (TextUtils.isEmpty(account)) {
                            showToast(R.string.usernamecannotblank);
                            return;
                        }
                        try {
                            int length = account.getBytes("gb18030").length;
                            if (length < 3 || length > 30) {
                                showToast(R.string.zhanghaorule);
                            }
                        } catch (UnsupportedEncodingException e) {
                            showToast(R.string.zhanghaorule);
                            return;
                        }

                        // 不能是纯数字
                        boolean isMatch = account.matches("^[0-9]*$");
                        if (isMatch) {
                            showToast("用户名不能为纯数字");
                            return;
                        }
                        // 用户名只能是字母数字中文和下划线
                        isMatch = account.matches("^[a-zA-Z0-9_\\u4e00-\\u9fa5]{3,16}$");
                        if (!isMatch) {
                            showToast(R.string.zhanghaorule);
                            return;
                        }

                        showLoading("提交中...");
                        RequestParams params = getRequestParams();
                        params.put(URLSuffix.username, account);
                        HttpBusinessAPI.post(URLSuffix.USER_SET_ACCOUNT,
                                params, new HttpRespHandler() {
                                    @Override
                                    public void onSuccess(String result) {

                                        hideLoading();
                                        if (checkResult(result)) {
                                            showToast("设置账号成功");
                                            Intent data = new Intent();
                                            data.putExtra("username", accountEditText.getText().toString().trim());
                                            //请求代码可以自己设置，这里设置成20
                                            setResult(3, data);
                                            //关闭掉这个Activity
                                            finish();
                                        }
                                    }
                                }
                        );
                    }
                }
        );
    }
    
    public void setupUI(View view) {
		// Set up touch listener for non-text box views to hide keyboard.
		if (!(view instanceof EditText)) {
			view.setOnTouchListener(new OnTouchListener() {
				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboard();
					return false;
				}

			});
		}
		// If a layout container, iterate over children and seed recursion.
		if (view instanceof ViewGroup) {
			for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
				View innerView = ((ViewGroup) view).getChildAt(i);
				setupUI(innerView);
			}
		}
	}
	
	public void hideSoftKeyboard() {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
		if (getCurrentFocus() != null) {
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
		}
	}
}
