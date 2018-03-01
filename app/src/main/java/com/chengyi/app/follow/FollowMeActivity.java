package com.chengyi.app.follow;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.listener.IInit;
import com.chengyi.app.listener.INet;
import com.chengyi.app.net.api.HomeApi;
import com.chengyi.app.view.XuhaoExitDialog;

import java.util.HashMap;
import java.util.Map;

public class FollowMeActivity extends BaseActivity implements IInit, INet ,XuhaoExitDialog.ICallback{
    private FollowMeModel followMeModel;
    private Button btn_sub;
    private TextView title_back_tv, tv_bili, tv_jnee, tv_bili_gfdj, tv_user_name, tv__lottery_name, tv_follow_jnee;
    private int type = 1;
    private LinearLayout ll_bili;
    private EditText et_bili, et_jnee;
private XuhaoExitDialog exitDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_me);
        followMeModel = getIntent().getParcelableExtra("follow");
        init();

    }

    @Override
    public void response(int tag, Object o) {
        if (o == null) return;
        if (tag == FIRST) {
            Follow_me_mode mode= (Follow_me_mode) o;


            if (mode.getFlag()!=1){
                Toast.makeText(this, mode.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }else {
                exitDialog.show();
                exitDialog.getvIn().setVisibility(View.GONE);
                exitDialog.getNoBtn().setVisibility(View.GONE);
                exitDialog.getCenterTextView().setText(mode.getErrorMessage());

            }
        }

    }

    @Override
    public void fetch(int tag) {
        if (tag == FIRST) {
            /**
             * type 为 1 时，是“按金额跟单” 此时 money 和 maxMoney 都为 “跟单金额”
             type 为 2 时，是“按比例跟单” 此时 money 为 “跟单金额百分比”  maxMoney 都为 “最大跟单金额”
             跟单用户 ID 为“objectUserID”
             跟单彩种ID 为 “lotteryID”
             每期认购上限 为 “buyCount”   “不限制”  buyCount 为 “0”

             */


            Map<String, String> m = new HashMap<>();
            if (type == 2) {
                if (!TextUtils.isEmpty(et_bili.getText().toString()))
                    m.put("money", et_bili.getText().toString());
                else
                    m.put("money", "0");


            } else {

                if (!TextUtils.isEmpty(et_jnee.getText().toString()))
                    m.put("money", et_jnee.getText().toString());
                else
                    m.put("money", "0");
            }
            m.put("maxMoney", et_jnee.getText().toString());
            m.put("type", String.valueOf(type));
            m.put("userId", followMeModel.getCur_user_id());
            m.put("objectUserId", followMeModel.getUser_id());
            m.put("lotteryId", followMeModel.getLottery_id());
            m.put("buyCount", "0");

            HomeApi.FOLLOW_ME(this, this, tag, Follow_me_mode.class, m);
        }
    }

    @Override
    public void init() {
        initView();
        loadDate();

    }

    @Override
    public void initView() {
        exitDialog=new XuhaoExitDialog(this);
        exitDialog.setCallBack(this);
        et_bili = (EditText) findViewById(R.id.et_bili);
        title_back_tv = (TextView) findViewById(R.id.tv_title);
        setCusTomeTitle("自动跟单");
        tv_jnee = (TextView) findViewById(R.id.tv_jnee);
        tv_jnee.setOnClickListener(this);
        tv_bili_gfdj = (TextView) findViewById(R.id.tv_bili_gfdj);
        tv_bili_gfdj.setOnClickListener(this);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_user_name.setText(followMeModel.getUser_name());
        tv__lottery_name = (TextView) findViewById(R.id.tv__lottery_name);
        tv__lottery_name.setText(followMeModel.getLottery_name());
        btn_sub = (Button) findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(this);
        tv_follow_jnee = (TextView) findViewById(R.id.tv_follow_jnee);

        et_jnee = (EditText) findViewById(R.id.et_jnee);
        tv_bili = (TextView) findViewById(R.id.tv_bili);
        ll_bili = (LinearLayout) findViewById(R.id.ll_bili);
        init_bili();
        tv_jnee.setSelected(true);
        setBack();

    }

    private void init_bili() {
        if (type == 1) {
            ll_bili.setVisibility(View.GONE);
            tv_bili.setVisibility(View.GONE);
            tv_follow_jnee.setText("跟单金额");
        } else if (type == 2) {
            tv_bili.setVisibility(View.VISIBLE);
            ll_bili.setVisibility(View.VISIBLE);
            et_bili.requestFocus();


            tv_follow_jnee.setText("最大跟单金额");
        }
    }

    @Override
    public void loadDate() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_sub:

                if (type == 1) {
                    if (TextUtils.isEmpty(et_jnee.getText().toString()) || Integer.parseInt(et_jnee.getText().toString()) <= 0) {
                        Toast.makeText(this, "请输入合法金额", Toast.LENGTH_SHORT).show();
                        break;
                    }

                } else {

                    if (TextUtils.isEmpty(et_jnee.getText().toString()) || Integer.parseInt(et_jnee.getText().toString()) <= 0) {
                        Toast.makeText(this, "请输入合法金额", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    if (TextUtils.isEmpty(et_bili.getText().toString()) || Integer.parseInt(et_bili.getText().toString()) <= 0) {
                        Toast.makeText(this, "请输入合法比例", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }


                fetch(FIRST);
                break;
            case R.id.tv_jnee:
                tv_jnee.setSelected(true);
                tv_bili_gfdj.setSelected(false);
                type = 1;
                init_bili();
                break;
            case R.id.tv_bili_gfdj:
                tv_jnee.setSelected(false);
                tv_bili_gfdj.setSelected(true);
                type = 2;
                init_bili();
                break;
        }

    }

    @Override
    public void reBack() {
        if (exitDialog!=null&&exitDialog.isShowing()){
            exitDialog.dismiss();
        }
    }

    @Override
    public void close() {
        if (exitDialog!=null&&exitDialog.isShowing()){
            exitDialog.dismiss();
        }
    }
}
