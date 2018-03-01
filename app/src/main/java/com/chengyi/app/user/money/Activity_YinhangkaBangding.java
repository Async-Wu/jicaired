package com.chengyi.app.user.money;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.net.http.HttpRespHandler;
import com.chengyi.app.net.http.RequestParams;

import com.chengyi.app.model.param.URLSuffix;
import com.chengyi.app.util.CaipiaoUtil;
import com.chengyi.app.net.http.HttpBusinessAPI;
import com.chengyi.app.view.ClearEditText;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  Activity_YinhangkaBangding extends BaseActivity {
    
	TextView bank;


	ClearEditText kahao,usename,shenfenhaoma;
	EditText  suozaidi;
	private boolean isbangding=false;
	String province,city;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_activity_yinhangkabangding);

		setCusTomeTitle("银行卡绑定");
		 setBack();
		findViewById(R.id.linearLayout1).setOnClickListener(this);
		findViewById(R.id.linearLayout4).setOnClickListener(this);
		findViewById(R.id.yinhang).setOnClickListener(this);
		findViewById(R.id.suozaidi).setOnClickListener(this);
		bank=(TextView) findViewById(R.id.yinhang);
		kahao=(ClearEditText) findViewById(R.id.kahao);
		suozaidi=(EditText) findViewById(R.id.suozaidi);
		usename=(ClearEditText) findViewById(R.id.kaihurenxingming);
		shenfenhaoma=(ClearEditText) findViewById(R.id.zhengjianhaoma);
		isbangding=this.getIntent().getBooleanExtra("isbangding", false);
		if(isbangding){
			usename.setText(getIntent().getStringExtra("username"));
			usename.setFocusable(false);
			shenfenhaoma.setText(getIntent().getStringExtra("zhengjianhaoma"));
			shenfenhaoma.setFocusable(false);
		}
		findViewById(R.id.querenbtn).setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {

		super.onClick(v);
		switch(v.getId()){
		   case R.id.cancel:
		   case R.id.imagebg:
			if(wheelViewBankPop!=null&&wheelViewBankPop.isShowing())
				wheelViewBankPop.dismiss();
			else if(wheelViewCityPop!=null&&wheelViewCityPop.isShowing())
			    wheelViewCityPop.dismiss();
			break;

		    case R.id.linearLayout1:  //银行
		    case R.id.yinhang:
		    	closeSoftKeybord();
		    	getWheelViewBankPop().showAtLocation(this.findViewById(R.id.mainlayout), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
		    break;
		    case R.id.linearLayout4:  //所在地
		    case R.id.suozaidi:
		    	 closeSoftKeybord();
		    	getWheelViewCityPop().showAtLocation(this.findViewById(R.id.mainlayout), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); 
		    break;
			case R.id.ensure:
				if(wheelViewBankPop!=null&&wheelViewBankPop.isShowing()){
					bank.setText(bankStr[wheelView.getCurrentItem()]+"");	
					wheelViewBankPop.dismiss();
				}else if(wheelViewCityPop!=null&&wheelViewCityPop.isShowing()){
					province=provinceStr[wheelProvince.getCurrentItem()];
					city=cities[wheelProvince.getCurrentItem()][wheelCity.getCurrentItem()];
					if(province.equals(city))
					    suozaidi.setText(province);	
					else
						suozaidi.setText(province+city);	
					wheelViewCityPop.dismiss();
				}
			break;
			case R.id.querenbtn:
				bangding() ;
			break;

		}
	}

	private void bangding() {

		if (TextUtils.isEmpty(bank.getText().toString())) {
			showToast("银行不能为空");
			return;
		}
		if (TextUtils.isEmpty(kahao.getText().toString())) {
			showToast("银行卡号不能为空");
			return;
		}
		if (TextUtils.isEmpty(suozaidi.getText().toString())) {
			showToast("开户行所在地不能为空");
			return;
		}
		if (TextUtils.isEmpty(usename.getText().toString())) {
			showToast("开户人姓名不能为空");
			return;
		}
		if (TextUtils.isEmpty(shenfenhaoma.getText().toString())) {
				showToast("身份证号码不能为空");
				return;
		}
		if (shenfenhaoma.getText().toString().indexOf("已绑定")==-1&&!CaipiaoUtil.isShenfenzheng(shenfenhaoma.getText().toString())) {
				showToast("身份证格式不正确");
				return;
		}
		showLoading("正在处理...");
		RequestParams params = getRequestParams();
		params.put(URLSuffix.name, usename.getText().toString().trim());
		params.put(URLSuffix.identityNumber, shenfenhaoma.getText().toString().trim());
		params.put(URLSuffix.bankAccount, kahao.getText().toString().trim());
		params.put(URLSuffix.bank, bank.getText().toString());
		params.put(URLSuffix.province,province);
		params.put(URLSuffix.city,city);
		HttpBusinessAPI.post(URLSuffix.BIND_BANK_CARD_test, params,
				new HttpRespHandler() {
					@Override
					public void onSuccess(String response) {
						super.onSuccess(response);
						hideLoading();
						if (checkResult(response)) {
							showToast("银行卡绑定成功!");
							Intent data = new Intent();
							data.putExtra("banknum", kahao.getText().toString());
							data.putExtra("username", usename.getText().toString().trim());
							data.putExtra("identityNumber", shenfenhaoma.getText().toString().trim());
							//请求代码可以自己设置，这里设置成20
							setResult(2, data);
							//关闭掉这个Activity
							finish();
						}
					}
				});
	}
}
