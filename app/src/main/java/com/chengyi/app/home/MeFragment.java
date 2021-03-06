package com.chengyi.app.home;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.chengyi.R;
import com.chengyi.app.base.BaseFragment;
import com.chengyi.app.home.actives.ActivesActivity;
import com.chengyi.app.listener.INet;
import com.chengyi.app.model.home.UserMsgMode;
import com.chengyi.app.net.api.HomeApi;
import com.chengyi.app.user.info.Activity_UserMessage;
import com.chengyi.app.user.jifen.Activity_JiFenShangCheng;
import com.chengyi.app.user.money.Activity_GouCaiRecord;
import com.chengyi.app.user.money.Activity_ZhangHuChongZhi;
import com.chengyi.app.user.money.Activity_ZiJinMingXi;
import com.chengyi.app.user.money.ZhuiActivity;
import com.chengyi.app.user.setting.Activity_Setting;
import com.chengyi.app.view.XuhaoExitDialog;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener, INet, XuhaoExitDialog.ICallback {

    private SwipeRefreshLayout sr;
    private TextView tvYue;
    private TextView tvCaijin, tv_name;
    private LinearLayout rlBuy;
    private LinearLayout rlGoon;
    private LinearLayout rlDetail, ll_set;
    private RelativeLayout rlAct;
    private RelativeLayout rlShop;
    private LinearLayout llCall, llAvter;
    private XuhaoExitDialog exitDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        sr = (SwipeRefreshLayout) view.findViewById(R.id.sr);
        sr.setColorSchemeResources(R.color.red);
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetch(FIRST);
            }
        });


        tvYue = (TextView) view.findViewById(R.id.tv_yue);
        tvCaijin = (TextView) view.findViewById(R.id.tv_caijin);
        rlBuy = (LinearLayout) view.findViewById(R.id.rl_buy);
        rlGoon = (LinearLayout) view.findViewById(R.id.rl_goon);
        rlDetail = (LinearLayout) view.findViewById(R.id.rl_detail);
        rlAct = (RelativeLayout) view.findViewById(R.id.rl_act);
        rlShop = (RelativeLayout) view.findViewById(R.id.rl_shop);
        llCall = (LinearLayout) view.findViewById(R.id.ll_call);

        ll_set = (LinearLayout) view.findViewById(R.id.ll_set);
        setCusTomeTitle(view, "我的账户");
        llAvter = (LinearLayout) view.findViewById(R.id.ll_avter);
        tvYue.setText(getUserBalance());
        tvCaijin.setText(getScore());
        tv_name.setText(getUserName());
        view.findViewById(R.id.ll_back).setVisibility(View.INVISIBLE);


        rlBuy.setOnClickListener(this);
        rlGoon.setOnClickListener(this);
        rlDetail.setOnClickListener(this);
        rlAct.setOnClickListener(this);
        rlShop.setOnClickListener(this);
        llCall.setOnClickListener(this);
        llAvter.setOnClickListener(this);
        ll_set.setOnClickListener(this);
        view.findViewById(R.id.ll_tik).setOnClickListener(this);
        view.findViewById(R.id.ll_conzi).setOnClickListener(this);
        exitDialog = new XuhaoExitDialog(getActivity());
        exitDialog.setCallBack(this);

    }

    @Override

    public void onResume() {
        super.onResume();
        fetch(FIRST);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.ll_set:
                startActivity(new Intent(getActivity(), Activity_Setting.class));
                break;

            case R.id.rl_buy:
                startActivity(new Intent(getActivity(), Activity_GouCaiRecord.class));
                break;
            case R.id.rl_goon:
                startActivity(new Intent(getActivity(), ZhuiActivity.class));
                break;
            case R.id.rl_detail:
                startActivity(new Intent(getActivity(), Activity_ZiJinMingXi.class));
                break;
            case R.id.rl_act:
                startActivity(new Intent(getActivity(), ActivesActivity.class));
                break;
            case R.id.rl_shop:
                startActivity(new Intent(getActivity(), Activity_JiFenShangCheng.class));
                break;
            case R.id.ll_call:
                call();
                break;
            case R.id.ll_avter:
                startActivity(new Intent(getActivity(), Activity_UserMessage.class));
                break;
            case R.id.ll_conzi:
               startActivity(new Intent(getActivity(), Activity_ZhangHuChongZhi.class));

                break;
            case R.id.ll_tik:
//                L.d(CaipiaoConst.BASE_URL + "/user/withdraw_view.htm?clientUserSession=" + getSession() + "&requestType=4"+"&appVersion="+ CaipiaoConst.APPVERSION+"&version="+CaipiaoConst.VERSION+"&requestType="+CaipiaoConst.REQUESTTYPE);
//                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("url", CaipiaoConst.BASE_URL + "/user/withdraw_view.htm?clientUserSession=" + getSession() + "&requestType=4"+"&appVersion="+ CaipiaoConst.APPVERSION+"&version="+CaipiaoConst.VERSION+"&requestType="+CaipiaoConst.REQUESTTYPE));


                exitDialog.show();
                exitDialog.getCenterTextView().setText("暂停提款");
                exitDialog.getNoBtn().setVisibility(View.GONE);
                exitDialog.getvIn().setVisibility(View.GONE);
                break;

        }

    }

    private void gotopic() {
//        if (AppUtil.getPerssion(this))
//        MultiImageSelector.create()
//                .start(this, 0);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // Get the result list of select image paths
//                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
//                L.d(path.toString());
            }
        }
    }

    /**
     * 适配6.0  以及以上
     * 权限问题
     */

    private void call() {


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 10);

//            }
        } else {
            startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:4008099182")));
        }
    }

    @Override
    public void response(int tag, Object o) {
        sr.setRefreshing(false);
        if (o == null) return;
        if (tag == FIRST) {

            tv_name.setText(getUserName());
            final UserMsgMode u = (UserMsgMode) o;
            if (u.getFlag() == 1) {
                tvYue.setText(u.getBalance());
                tvCaijin.setText(u.getCapitalHandsel());
            } else if (0 == u.getFlag()) {
                Toast.makeText(parentActivity, u.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void fetch(int tag) {
        if (tag == FIRST) {

            HomeApi.QUERY_BALANCE(getContext(), this, tag, UserMsgMode.class, null);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,                                       String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startActivity(new Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:4008099182")));

                } else {
                    Toast.makeText(parentActivity, "未获取权限", Toast.LENGTH_SHORT).show();

                }
                return;
            }


        }
    }


    @Override
    public void reBack() {
        if (exitDialog != null && exitDialog.isShowing())
            exitDialog.dismiss();
    }

    @Override
    public void close() {
        if (exitDialog != null && exitDialog.isShowing())
            exitDialog.dismiss();
    }
}


