package cn.ushang.plank.ui.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ushang on 2018/9/6.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;

    public MainPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        fragmentList=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
