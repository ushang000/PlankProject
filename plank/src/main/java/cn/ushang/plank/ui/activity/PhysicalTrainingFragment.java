package cn.ushang.plank.ui.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import cn.ushang.plank.R;
import cn.ushang.plank.ui.adpter.RecyclerViewPracticeAdapter;

/**
 * Created by shao on 2018/8/27.
 */

@SuppressLint("ValidFragment")
public class PhysicalTrainingFragment extends Fragment {

    RecyclerViewPracticeAdapter adapter;
    Context mContext;
    ArrayList<String> mDataSet;
    ArrayList<Drawable> mDataImages;
    ArrayList<String> mDataTimes;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PhysicalTrainingFragment(final Context mContext) {
        this.mContext = mContext;
        mDataSet= new ArrayList<String>(){
            {
                add(mContext.getString(R.string.level_1));
                add(mContext.getString(R.string.level_2));
                add(mContext.getString(R.string.level_3));
                add(mContext.getString(R.string.level_4));
                add(mContext.getString(R.string.level_5));
                add(mContext.getString(R.string.level_6));
                add(mContext.getString(R.string.level_7));
                add(mContext.getString(R.string.level_8));
            }
        };
        mDataTimes =new ArrayList<String>(){
            {
                add(mContext.getString(R.string.level_time1));
                add(mContext.getString(R.string.level_time2));
                add(mContext.getString(R.string.level_time3));
                add(mContext.getString(R.string.level_time4));
                add(mContext.getString(R.string.level_time5));
                add(mContext.getString(R.string.level_time6));
                add(mContext.getString(R.string.level_time7));
                add(mContext.getString(R.string.level_time8));
            }
        };
        mDataImages=new ArrayList<Drawable>(){
            {
                add(mContext.getDrawable(R.mipmap.plank_cu));
                add(mContext.getDrawable(R.mipmap.plank_slv));
                add(mContext.getDrawable(R.mipmap.plank_gold));
                add(mContext.getDrawable(R.mipmap.plank_pt));
                add(mContext.getDrawable(R.mipmap.plank_diamond));
                add(mContext.getDrawable(R.mipmap.plank_gold));
                add(mContext.getDrawable(R.mipmap.plank_gold));
                add(mContext.getDrawable(R.mipmap.plank_gold));
            }
        };
    }

    public PhysicalTrainingFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.layout_practices,container,false);
        RecyclerView recyclerView=view.findViewById(R.id.recycle_level);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter=new RecyclerViewPracticeAdapter(mDataSet,mDataImages,mDataTimes);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewPracticeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(mContext,"你选择了 "+mDataSet.get(position)+" 级别难度",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
