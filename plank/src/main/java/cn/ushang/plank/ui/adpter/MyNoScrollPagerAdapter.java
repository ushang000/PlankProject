package cn.ushang.plank.ui.adpter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import cn.ushang.plank.model.Exercise;
import cn.ushang.plank.ui.activity.TrainingFragment;

/**
 * Created by ushang on 2018/9/3.
 */

public class MyNoScrollPagerAdapter extends FragmentPagerAdapter {

    List<Exercise> exerciseList;

    public MyNoScrollPagerAdapter(FragmentManager fm, List<Exercise> exercises) {
        super(fm);
        exerciseList=exercises;
    }

    @Override
    public Fragment getItem(int position) {
        return TrainingFragment.newInstance(position,exerciseList.get(position),position==getCount()-1);
    }

    @Override
    public int getCount() {
        return exerciseList.size();
    }
}
