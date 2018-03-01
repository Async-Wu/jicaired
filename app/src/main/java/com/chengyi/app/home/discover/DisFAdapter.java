package com.chengyi.app.home.discover;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.chengyi.app.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishangfan on 2016/10/10.
 */
public class DisFAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fs = new ArrayList<>();

    public DisFAdapter(FragmentManager fm) {
        super(fm);
        fs.add(new DisFragment());
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
