package cn.ushang.plank.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.ushang.plank.R;
import cn.ushang.plank.model.Exercise;
import cn.ushang.plank.utils.ResourceGet;

public class TrainingFragment extends LazyLoadFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    //private Exercise mParam1;
    private boolean isLast=false;
    private boolean isFragmentVisible=false;
    //private TextView text_time;
    private PhysicalTrainingActivity mParentActivity;
    private CountDownTimer timer;
    private CountDownTimer restTimer;
    private ResourceGet resource;
    private int currentPage=0;
    private int offSet=999;//500是抵消发送消息的时间延迟，每发次消息大概消耗1-2毫秒
    private long mTime;
    private long mCurrentTime;
    public TrainingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TrainingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrainingFragment newInstance(int page,Exercise param1, boolean param2) {
        TrainingFragment fragment = new TrainingFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        if(param2){
            args.putBoolean(ARG_PARAM2, param2);
        }
        args.putInt("page",page);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event

    private void startRestTime(final TextView text,long time){

        restTimer=new CountDownTimer(time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished<1000){
                    text.setText("GO !");
                }else {
                    text.setText("Ready ："+millisUntilFinished/1000);
                }

            }

            @Override
            public void onFinish() {
                startDownTime(text);
            }
        }.start();
    }

    public void startDownTime(final TextView text){
          timer=  new CountDownTimer(mCurrentTime,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    text.setText(resource.getDurationFormatted(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    //翻页
                    mParentActivity.startNextPage();
                    gotoFinish();
                }
            }.start();

    }

    private void gotoFinish(){
        if(isLast&&isFragmentVisible){
            mParentActivity.goFinish();
        }
    }


    @Override
    protected int setContentView() {
        return R.layout.content_physical_training;
    }


    //懒加载
    @Override
    protected void lazyLoad() {
        Exercise exercise= (Exercise) getArguments().get(ARG_PARAM1);
        isLast=getArguments().containsKey(ARG_PARAM2);
        mTime=(long)exercise.getDuration().intValue()+offSet;
        mCurrentTime=mTime;
        currentPage=getArguments().getInt("page");
        resource=new ResourceGet(mParentActivity);
        ImageView imageView=findViewById(R.id.training_img);
        TextView text_time=findViewById(R.id.training_time);
        //text_time.setText(mExercise.getDurationFormatted());
        TextView text_name=findViewById(R.id.train_name);
        text_name.setText(resource.getNameString(exercise.getName_key()));
        Glide.with(mParentActivity).load(resource.getDrawableResourceId(exercise.getImg_key())).into(imageView);
        if(currentPage==0){
            startRestTime(text_time,3000+offSet);
        }else {
            startRestTime(text_time,10000+offSet);
        }
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        isFragmentVisible=menuVisible;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mParentActivity = (PhysicalTrainingActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onPause() {
        super.onPause();
        if(timer!=null){
            timer.cancel();
        }
        if (restTimer != null) {
            restTimer.cancel();
        }
    }

}
