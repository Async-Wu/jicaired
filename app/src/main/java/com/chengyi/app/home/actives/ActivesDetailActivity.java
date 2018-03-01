package com.chengyi.app.home.actives;

import android.os.Bundle;
import android.widget.TextView;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.listener.IInit;
import com.chengyi.app.listener.INet;

public class ActivesDetailActivity extends BaseActivity implements INet, IInit {
    private TextView tv_content_act, tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actives_detail);
        setBack();

        setCusTomeTitle("活动详情");
        init();
    }

    @Override
    public void init() {
        initView();
        loadDate();
    }

    @Override
    public void initView() {
        tv_content_act = (TextView) findViewById(R.id.tv_content_act);
        tv_time = (TextView) findViewById(R.id.tv_time);

    }

    @Override
    public void loadDate() {
        fetch(FIRST);

    }

    @Override
    public void response(int tag, Object o) {

    }

    @Override
    public void fetch(int tag) {

    }
}
