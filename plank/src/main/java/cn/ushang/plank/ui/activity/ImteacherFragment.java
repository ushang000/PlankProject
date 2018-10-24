package cn.ushang.plank.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import cn.ushang.plank.R;
import cn.ushang.plank.model.Exercise;
import cn.ushang.plank.model.LevelData;
import cn.ushang.plank.ui.adpter.RecyclerViewTeachAdapter;
import cn.ushang.plank.utils.ResourceGet;
import cn.ushang.plank.utils.VarUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ImteacherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ImteacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImteacherFragment extends LazyLoadFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerViewTeachAdapter adapter;
    private MainActivity mParentActivity;
    private ArrayList<String> nameLists = new ArrayList<String>() {
        {
            add("full_plank");
            add("side_plank_left");
            add("side_plank_right");
            add("elbow_plank");
            add("reverse_plank");
            add("kickbacks_of_hands_plank_right");
            add("kickbacks_of_hands_plank_left");
            add("raised_plank_left");
            add("raised_plank_right");
            add("side_plank_raised_leg_right");
            add("side_plank_raised_leg_left");
            add("raised_hand_plank_left");
            add("raised_hand_plank_right");
            add("reverse_plank_legs_raised_left");
            add("reverse_plank_legs_raised_right");
            add("right_hand_left_leg_raise_plank");
            add("left_hand_right_leg_raise_plank");


        }
    };
    private ArrayList<String> motionList = new ArrayList<String>() {
        {
            add("male_full_plank");
            add("male_side_plank_left");
            add("male_side_plank_right");
            add("male_elbow_plank");
            add("male_reverse_plank");
            add("male_kick_backs_hand_right");
            add("male_kick_backs_hand_left");
            add("male_full_plank_raised_leg_left");
            add("male_full_plank_raised_leg_right");
            add("male_side_plank_leg_raised_right");
            add("male_side_plank_leg_raised_left");
            add("male_full_plank_raised_hand_left");
            add("male_full_plank_raised_hand_right");
            add("male_reverse_leg_raised_plank_left");
            add("male_reverse_leg_raised_plank_right");
            add("male_full_plank_leg_hand_raised_left");
            add("male_full_plank_leg_hand_raised_right");
        }
    };
    private ArrayList<Integer> mTimeList = new ArrayList<>();
    private SharedPreferences plankTeachTable;
    private int sumTimes=0;

    private OnFragmentInteractionListener mListener;

    public ImteacherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImteacherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ImteacherFragment newInstance(String param1, String param2) {
        ImteacherFragment fragment = new ImteacherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_imteacher, container, false);
    }*/

    @Override
    protected int setContentView() {
        return R.layout.fragment_imteacher;
    }

    @Override
    protected void lazyLoad() {
        RecyclerView recyclerView = findViewById(R.id.recycle_teach);
        final TextView teachTimes = findViewById(R.id.teach_times);
        ImageView teachLock = findViewById(R.id.teach_locked);
        Button addTeach = findViewById(R.id.add_teach);
        SharedPreferences plankDatas = mParentActivity.getSharedPreferences("StatisticsDatas", MODE_PRIVATE);
        plankTeachTable = mParentActivity.getSharedPreferences("TeachData", MODE_PRIVATE);
        VarUtils.isLocked = plankDatas.getBoolean("isLocked", true);
        mTimeList.clear();
        if (VarUtils.isLocked) {
            teachLock.setImageResource(R.mipmap.abs_lock_white);
            //Glide.with(this).load(R.mipmap.abs_lock_white).into(teachLock);
        } else {
            teachLock.setImageResource(R.mipmap.abs_unlock_white);
            //Glide.with(this).load(R.mipmap.abs_unlock_white).into(teachLock);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mParentActivity));
        adapter = new RecyclerViewTeachAdapter(mParentActivity, motionList, nameLists);
        adapter.setOnViewHolderClick(new RecyclerViewTeachAdapter.IMyViewHolderClicks() {
            @Override
            public void onTimeChange(TextView v, ArrayList<Integer> times, int position) {
                //adapter.notifyDataSetChanged();
                //timeLists=times;
                //mTimeList.clear();
                mTimeList = times;
                v.setText(mTimeList.get(position) + " s");
                sumTimes = 0;
                for (int s : mTimeList) {
                    sumTimes = sumTimes + s;
                }

                teachTimes.setText(new ResourceGet(mParentActivity).getDurationFormatted(sumTimes * 1000) + " m");

            }
        });
        addTeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int teachNum = plankTeachTable.getInt("teachNum", 0);
                if (teachNum < 3&&!VarUtils.isLocked&&sumTimes>0) {
                    List<Exercise> exercises = new ArrayList<>();
                    for (int i = 0; i < mTimeList.size(); i++) {
                        if (mTimeList.get(i) > 0) {
                            Exercise exercise = new Exercise();
                            exercise.setName_key(nameLists.get(i));
                            exercise.setImg_key(motionList.get(i));
                            exercise.setDuration(mTimeList.get(i) * 1000);
                            exercises.add(exercise);
                        /*plankTeachTable.edit().putFloat("duration",mTimeList.get(i)*1000)
                        .putString("img_key",motionList.get(i))
                        .putString("name_key",nameLists.get(i)).apply();*/
                        }
                    }
                    Gson gson = new Gson();
                    LevelData levelData = new LevelData();
                    levelData.setColor_key("#FFCA435B");
                    levelData.setDesc_key("level_teach");
                    levelData.setImg_key("ic_level_5");
                    levelData.setExercises(exercises);
                    String json = gson.toJson(levelData);
                    teachNum=teachNum+1;
                    VarUtils.isAdd=false;
                    plankTeachTable.edit().putString("teach"+teachNum, json)
                            .putInt("teachNum",teachNum).apply();
                    Toast.makeText(mParentActivity, "添加成功", Toast.LENGTH_SHORT).show();
                    mParentActivity.toLevelFragment();
                }else {
                    Toast.makeText(mParentActivity, "sorry!请解锁且只能添加3条", Toast.LENGTH_SHORT).show();
                }

            }
        });
        /*int sumTimes=0;
        for (int s:timeLists){
            sumTimes=sumTimes+s;
        }
        teachTimes.setText(new ResourceGet(mParentActivity).getDurationFormatted(sumTimes*100)+" m");
        *///adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            mParentActivity = (MainActivity) context;
        }
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
