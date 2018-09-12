package cn.ushang.plank.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.ushang.plank.R;
import cn.ushang.plank.model.LevelData;
import cn.ushang.plank.ui.adpter.RecyclerViewPracticeAdapter;

/**
 * Created by shao on 2018/8/27.
 */

@SuppressLint("ValidFragment")
public class TrainingLevelFragment extends Fragment {

    RecyclerViewPracticeAdapter adapter;
    Context mContext;
    ArrayList<String> mDataSet;
    ArrayList<Drawable> mDataImages;
    ArrayList<String> mDataTimes;
    List<LevelData> mLevelDatas;
    private MainActivity mainActivity;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TrainingLevelFragment(final Context mContext,List<LevelData> levelData) {
        this.mContext = mContext;
        mLevelDatas=levelData;
    }

    public TrainingLevelFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.layout_practices,container,false);
        RecyclerView recyclerView=view.findViewById(R.id.recycle_level);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter=new RecyclerViewPracticeAdapter(mContext,mLevelDatas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewPracticeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(mContext,PhysicalTrainingActivity.class);
                intent.putExtra("level",mLevelDatas.get(position));
                startActivity(intent);
            }
        });

        return view;
    }

    /*@Override
    protected int setContentView() {
        return R.layout.layout_practices;
    }

    @Override
    protected void lazyLoad() {

    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            mainActivity=(MainActivity)context;
        }
    }
}
