package com.slut.simrec.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<String> title;
    private List<Fragment> fragmentList;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MainPagerAdapter(FragmentManager fm, List<String> title, List<Fragment> fragmentList) {
        super(fm);
        this.title = title;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentList != null && !fragmentList.isEmpty() && position < fragmentList.size()) {
            return fragmentList.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (fragmentList != null && !fragmentList.isEmpty()) {
            return fragmentList.size();
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (title != null && !title.isEmpty() && position < title.size()) {
            return title.get(position);
        }
        return super.getPageTitle(position);
    }
}
