package com.chengyi.app.home.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.chengyi.app.home.*;
import com.chengyi.app.home.discover.DisCoverFragment;
import com.chengyi.app.home.hemai.BuyFragment;
import com.chengyi.app.home.get.GetFragment;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class  HomeVpAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fs;

    public HomeVpAdapter(FragmentManager fm) {
        super(fm);
        fs = new ArrayList<>();
        fs.add(new HomeFragment());
        fs.add(new BuyFragment());
        fs.add(new GetFragment());
        fs.add(new DisCoverFragment());
        fs.add(new MeFragment());

    }

    @Override
    public Fragment getItem(int position) {
        return fs.get(position);
    }

    @Override
    public int getCount() {
        return fs.size();
    }
}
