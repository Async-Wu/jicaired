package com.chengyi.app.user.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.CaipiaoApplication;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.util.YOUMENG_EVENT;
import com.chengyi.app.view.ClearEditText;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Register_VerifyCode extends BaseActivity {

    private boolean loginFrom; // // 登录来源，如果是购彩时发现未登录，登录成功后返回购彩页面
    private ImageView closeImg;
    private Button querenbtn;
    private TextView countdownTextView,resendSMSTextView,phoneNumTextView;// 重新发送倒计时
    private LinearLayout countDownLayout;
    private ClearEditText et_verfiyCode;

    private String phone;
    private int time=60;

    private Runnable run = new Runnable() {
        public void run() {
            if (time > 0) {
                time--;
               countdownTextView.setText(time+"");
                handler.postDelayed(run, 1000);
            } else {
            	countDownLayout.setVisibility(View.INVISIBLE);
            	resendSMSTextView.setVisibility(View.VISIBLE);
                Register_VerifyCode.this.getResources().getDrawable(R.drawable.sendagain);
                countdownTextView.setText(60+"");
                handler.removeCallbacks(run);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CaipiaoApplication.getInstance().isOnRegisterProcess=true;



        
        setContentView(R.layout.fragment_register_verifycode);
        setupUI(findViewById(R.id.register_verifycode_main_layout));
        loginFrom = getIntent().getBooleanExtra("isfromgoumai", false);

        closeImg = (ImageView) findViewById(R.id.imageViewclose);
        closeImg.setOnClickListener(this);
        querenbtn = (Button) findViewById(R.id.querenbtn);
        querenbtn.setOnClickListener(this);
        phoneNumTextView=(TextView) findViewById(R.id.register_verifycode_phone_num);
        phoneNumTextView.setText(formatPhoneNum(phone));
        
        countDownLayout=(LinearLayout)findViewById(R.id.register_verifycode_layout_countdown);
        countdownTextView = (TextView) findViewById(R.id.register_verify_code_textview_countdown);
        resendSMSTextView=(TextView)findViewById(R.id.register_verify_code_textview_resent_sms);
        resendSMSTextView.setOnClickListener(this);
        et_verfiyCode = (ClearEditText) findViewById(R.id.et_verifyCode);
        et_verfiyCode.setText("");
        countDownLayout.setVisibility(View.VISIBLE);
    	resendSMSTextView.setVisibility(View.GONE);
       
    }
    
    private String formatPhoneNum(String phoneNum){
    	String phonenum=phoneNum+"";
    	if(phonenum.length()==11){
    		String firstpart=phonenum.substring(0, 3);
    		String secondpart=phonenum.substring(3, 7);
    		String thirdpart=phonenum.substring(7, 11);
    		phonenum=new StringBuilder().append(firstpart).append(" ").append(secondpart).append(" ").append(thirdpart).toString();
    	}
    			
    	return 	phonenum;	
    }

    @Override
    protected void onResume() {
        super.onResume();

//        phone = userInfo.getPhotoNumber();
//        et_verfiyCode.setText("");
//        time=60;
//        handler.postDelayed(run, 100);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	time=0;
    	 handler.removeCallbacks(run);
    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();

        CaipiaoApplication.getInstance().isOnRegisterProcess=false;
    }

    @Override
    public void onClick(View v) {
        map.clear();
        closeSoftKeybord();
        super.onClick(v);
        switch (v.getId()) {
            case R.id.imageViewclose:

                CaipiaoApplication.getInstance().isOnRegisterProcess=false;
                if (loginFrom){
                   finish();
                }else{
                   finish();
                }
                map.put("操作类型", "退出注册");
                CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_register, map);
                break;
            case R.id.register_verify_code_textview_resent_sms:
                String checkPhone = checkPhone();
                if (checkPhone != null) {
                    showToast(checkPhone);
                } else {
                    // 获取验证码
                    sendYanZhengMa();
                }
                map.put("操作类型", "重新发送验证码");
                CaipiaoUtil.youmengEvent(this, YOUMENG_EVENT.new_register, map);
                break;
            case R.id.querenbtn:
                showLoading("正在发送请求......");
                verifyCode();
                hideLoading();
                break;
        }
    }

    private String checkPhone() {
        if (TextUtils.isEmpty(phone)) {
            return getString(R.string.shoujihaomabunengweikong);
        }
        if (phone.length() != 11) {
            return getString(R.string.shoujihaomageshicuowu);
        }
        return null;
    }

    // 发送手机验证码
    private void sendYanZhengMa() {
    	countDownLayout.setVisibility(View.VISIBLE);
    	resendSMSTextView.setVisibility(View.GONE);
        RequestParams params = getRequestParams();
        params.put(URLSuffix.mobile, phone);
        HttpBusinessAPI.post(URLSuffix.REGISTER_SEND_MOBILE, params,
                new HttpRespHandler() {
                    @Override
                    public void onFailure(Throwable error) {
                        super.onFailure(error);
                        countDownLayout.setVisibility(View.INVISIBLE);
                        resendSMSTextView.setVisibility(View.VISIBLE);
                        showToast("抱歉 验证码获取失败");
                        handler.removeCallbacks(run);
                    }

                    @Override
                    public void onSuccess(String response) {
                        super.onSuccess(response);
                        //System.out.println("response");
                        if (checkResult(response)) {
                            time = 60;
                            countDownLayout.setVisibility(View.VISIBLE);
                            resendSMSTextView.setVisibility(View.GONE);
                            handler.postDelayed(run, 100);
                        } else {
                            countDownLayout.setVisibility(View.INVISIBLE);
                            resendSMSTextView.setVisibility(View.VISIBLE);
                            showToast("抱歉 验证码获取失败");
                        }
                    }
                }
        );
    }

    private void verifyCode() {
        String verifyCode;
        Editable e = et_verfiyCode.getEditableText();
        if (e == null) {
            showToast("验证码不能为空！");
            return;
        }
        verifyCode = e.toString();

        RequestParams params = getRequestParams();
        params.put(URLSuffix.mobile, phone);
        params.put("verifyCode", verifyCode);
//        userInfo.setVerifyCode(verifyCode);
        HttpBusinessAPI.post(URLSuffix.CHECK_MOBILE_VERFIY_CODE, params,
                new HttpRespHandler() {
                    @Override
                    public void onFailure(Throwable error) {
                        super.onFailure(error);
                        showToast("请求失败！请检查您的网络是否正常！");
                    }

                    @Override
                    public void onSuccess(String response) {
                        super.onSuccess(response);
                        if (checkResult(response)) {
                            if (loginFrom) {
//                               startActivity(new Intent(getBaseContext(),Register_SetPassword.class));
                            } else {
//                                startActivity(new Intent(getBaseContext(),Register_SetPassword.class));

                            }
                        }
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
