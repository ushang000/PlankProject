package cn.ushang.plank.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.ushang.plank.R;
import cn.ushang.plank.model.Exercise;
import cn.ushang.plank.model.LevelData;
import cn.ushang.plank.ui.adpter.RecyclerViewPracticeAdapter;
import cn.ushang.plank.ui.view.SwipeItemLayout;
import cn.ushang.plank.utils.VarUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ushang on 2018/8/27.
 */

@SuppressLint("ValidFragment")
public class TrainingLevelFragment extends LazyLoadFragment {

    RecyclerViewPracticeAdapter adapter;
    Context mContext;
    ArrayList<String> mDataSet;
    ArrayList<Drawable> mDataImages;
    ArrayList<String> mDataTimes;
    List<LevelData> mLevelDatas;
    private MainActivity mainActivity;
    private List<Exercise> exercises=new ArrayList<>();
    LevelData levelData;
    SharedPreferences plankTeachTable;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TrainingLevelFragment(final Context mContext,List<LevelData> levelData) {
        this.mContext = mContext;
        mLevelDatas=levelData;
    }

    public TrainingLevelFragment(){}

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.layout_practices,container,false);


        return view;
    }*/

    @Override
    protected int setContentView() {
        return R.layout.layout_practices;
    }

    @Override
    protected void lazyLoad() {
        RecyclerView recyclerView=findViewById(R.id.recycle_level);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        plankTeachTable=mContext.getSharedPreferences("TeachData",MODE_PRIVATE);
        int teachNum=plankTeachTable.getInt("teachNum",0);
        Log.i("shao","teachNum : "+teachNum);
        if(mLevelDatas.size()-8<teachNum){
            VarUtils.isAdd=false;
        }
        if (teachNum>0){
            for (int i=mLevelDatas.size()-7;i<teachNum+1;i++){
                String temp=plankTeachTable.getString("teach"+i,"nothing");
                if (!VarUtils.isAdd){
                    LevelData levelData=new Gson().fromJson(temp,LevelData.class);
                    //mLevelDatas.add(levelData);
                    mLevelDatas.add(0,levelData);
                }
            }
            VarUtils.isAdd=true;
        }

        adapter=new RecyclerViewPracticeAdapter(mContext,mLevelDatas);
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecyclerViewPracticeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v,int position) {
                Intent intent=new Intent(mContext,PhysicalTrainingActivity.class);
                intent.putExtra("level",mLevelDatas.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            mainActivity=(MainActivity)context;
        }
    }
}
