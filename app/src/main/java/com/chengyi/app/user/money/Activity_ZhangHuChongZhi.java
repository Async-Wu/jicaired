package com.chengyi.app.user.money;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.adapter.Payadapter;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.net.http.OkHttpUtil;
import com.chengyi.app.util.IP;
import com.chengyi.app.util.L;
import com.google.gson.Gson;
import com.jiyunxueyuanandroid.Initpay;
import com.jiyunxueyuanandroid.PayEntity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_ZhangHuChongZhi extends BaseActivity implements View.OnClickListener {

    private ListView lvPay;
    private Payadapter payadapter;
    private List<Integer> mlist = new ArrayList<Integer>();
    private EditText mmoney;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_zhanghuchongzhi);
        back= (ImageView) findViewById(R.id.title_back_left);
        lvPay = (ListView) findViewById(R.id.lv_pay);
        mmoney= (EditText) findViewById(R.id.first_money);
        mmoney.setText("10");
        mlist.add(R.drawable.shoumilogo);
        payadapter=new Payadapter(mlist,Activity_ZhangHuChongZhi.this);
        lvPay.setAdapter(payadapter);
        if (mmoney.getText().toString()==null){
        }else{
            mmoney.setSelection(mmoney.getText().length());
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mmoney.getText().toString()==null){
                }else{
                    mmoney.setSelection(mmoney.getText().length());
                    mmoney.setText("");
                }
            }
        });
        lvPay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    if (mmoney.getText().length()==0) {
                            Toast.makeText(Activity_ZhangHuChongZhi.this,"请输入正确金额",Toast.LENGTH_LONG).show();
                    }else{
                        if (Integer.parseInt(mmoney.getText().toString())<2){
                            Toast.makeText(Activity_ZhangHuChongZhi.this,"至少充值2元",Toast.LENGTH_LONG).show();
                        }else{
                            pay();
                        }
                    }
                }
            }
        });
        mmoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }


    @Override
    public void onClick(View view) {

    }

    private void pay() {
        String paymoney;
            Map<String,String> map=new HashMap<String, String>();
        if (mmoney.getText().length()==0){
            paymoney="2";
        }else{
            paymoney=mmoney.getText().toString();
        }
            map.put("P_Amount",paymoney);
            map.put("P_RequestType","3");
            map.put("P_SDKVersion","1.0.0");
            OkHttpUtil.postSubmitForm(IP.IP+"/user/shoumi_app.htm", map, new OkHttpUtil.OnDownDataListener() {
                @Override
                public void onResponse(String url, String json) {
                    L.d("result",json);
                    if (json!=null){
                        try {
                            JSONObject jsonobject=new JSONObject(json);
                            Gson gson=new Gson();
                            PayEntity payEntity1 = gson.fromJson(json, PayEntity.class);
                            Initpay initpay=new Initpay(Activity_ZhangHuChongZhi.this,payEntity1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onFailure(String url, String error) {
                }
            });
        }
    }

