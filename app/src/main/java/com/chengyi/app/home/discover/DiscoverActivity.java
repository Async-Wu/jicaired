package com.chengyi.app.home.discover;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.chengyi.R;
import com.chengyi.app.base.BaseActivity;
import com.chengyi.app.listener.IInit;

public class DiscoverActivity extends BaseActivity implements IInit {
    private ViewPager vpDis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);
        setBack();

        setCusTomeTitle("资讯");
        init();
    }

    @Override
    public void init() {
        initView();
        loadDate();

    }

    @Override
    public void initView() {
        vpDis = (ViewPager) findViewById(R.id.vp_dis);
        vpDis.setAdapter(new DisFAdapter(getSupportFragmentManager()));


    }

    @Override
    public void loadDate() {

    }
}
