package cn.ushang.plank.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.ushang.plank.R;
import cn.ushang.plank.utils.ChartUtils;

import static android.content.Context.MODE_PRIVATE;

public class StatisticsDataFragment extends LazyLoadFragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MainActivity mParentActivity;
    private Calendar calendar;
    private Float beforeData;
    private TextView mLabelTitle;
    private ImageButton prevWeek;
    private ImageButton nextWeek;
    private int firstDayOfWeek=0;
    private int dayNum=0;
    private SharedPreferences plankData;
    private List<String> xValues=new ArrayList<String>(){
        {
            add("星期日");
            add("星期一");
            add("星期二");
            add("星期三");
            add("星期四");
            add("星期五");
            add("星期六");
        }
    };
    private List<Float> dataValues=new ArrayList<Float>(){
        {
            add(0f);
            add(0f);
            add(0f);
            add(0f);
            add(0f);
            add(0f);
            add(0f);
        }
    };

    private LineChart mChart;


    public StatisticsDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsDataFragment newInstance(String param1, String param2) {
        StatisticsDataFragment fragment = new StatisticsDataFragment();
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
        calendar=Calendar.getInstance();
        firstDayOfWeek=calendar.getFirstDayOfWeek();
        dayNum=dayNum-calendar.get(Calendar.DAY_OF_WEEK)+1;
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_statistics, container, false);

        //beforeData=plankData.getFloat(dataKey,0);
        //Log.i("shao"," beforeData"+beforeData+"  time : "+dataKey);
        //dataValues.set(week-1,beforeData);

        return view;
    }*/

    @Override
    protected int setContentView() {
        return R.layout.fragment_statistics;
    }

    @Override
    protected void lazyLoad() {
        mChart=findViewById(R.id.lineChart);
        mLabelTitle=findViewById(R.id.mLabelTitle);
        prevWeek=findViewById(R.id.prevWeek);
        prevWeek.setOnClickListener(this);
        nextWeek=findViewById(R.id.nextWeek);
        nextWeek.setOnClickListener(this);
        if(plankData==null){
            plankData = mParentActivity.getSharedPreferences("StatisticsDatas", MODE_PRIVATE);
        }

        getWeekFirsLastDay(dayNum);
        getWeekDay(dayNum);
    }


    private void getWeekFirsLastDay(int firstDayOfWeek){
        String firstDay=getWeekFirstDay(firstDayOfWeek);
        String lastDay=getWeekLastDay(firstDayOfWeek);
        Log.i("shao","date : "+firstDay+" - "+lastDay);
        mLabelTitle.setText(firstDay+" - "+lastDay);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            mParentActivity=(MainActivity)context;
        }
    }


    private void getWeekDay(int firstDayOfWeek) {
        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, firstDayOfWeek + i);
            String day = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
            float data=plankData.getFloat(day,0);
            Log.i("shao"," data : "+day+" : "+data);
            dataValues.set(i,data);
        }
        ChartUtils.initLineChart(mParentActivity,mChart,dataValues,xValues);

    }

    private String getWeekFirstDay(int preFirstDayOfWeek){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,preFirstDayOfWeek);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        return month+"月"+day;

    }

    private String getWeekLastDay(int preFirstDayOfWeek){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,preFirstDayOfWeek+6);
        int month=calendar.get(Calendar.MONTH)+1;
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        return month+"月"+day;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if(v==prevWeek){
            dayNum=dayNum-7;
            getWeekFirsLastDay(dayNum);
            getWeekDay(dayNum);

        }else if(v==nextWeek){
            dayNum=dayNum+7;
            getWeekFirsLastDay(dayNum);
            getWeekDay(dayNum);
        }
    }
}
