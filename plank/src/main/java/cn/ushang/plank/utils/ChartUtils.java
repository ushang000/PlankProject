package cn.ushang.plank.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.ushang.plank.R;

/**
 * Created by ushang on 2018/9/7.
 */

public class ChartUtils {


    public static void initBarChart(BarChart barChart, List<String> myBarValues, List<String> xVaules){
        ArrayList<BarEntry> values=new ArrayList<>();
        for(int i=0;i<xVaules.size();i++){
            float temp=Float.parseFloat(xVaules.get(i));
            values.add(new BarEntry(i,temp));
        }
        BarDataSet barDataSet=new BarDataSet(values,"");
        barDataSet.setColor(0xffffffff);
        //barDataSet.setBarBorderWidth(5f);
        //barDataSet.setBarBorderColor(Color.RED);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.5f);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.setTouchEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        //barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getDescription().setEnabled(false);
        //barChart.getAxisLeft().setDrawAxisLine(false);
        barChart.getLegend().setEnabled(false);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        //barChart.getXAxis().setDrawAxisLine(false);
        //barChart.getXAxis().setGridColor(0xffff0000);
        barChart.getXAxis().setLabelCount(7);
        barChart.getXAxis().setGranularity(1);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(myBarValues));
    }
    public static void initLineChart(Context context, LineChart lineChart, List<Float> myValues, List<String> myXvalues){
        // y轴的数据
        ArrayList<Entry> values = new ArrayList<Entry>();
        for (int i = 0; i < myValues.size(); i++) {
            Entry entry = new Entry(i,myValues.get(i));

            values.add(entry);
        }

        // y轴数据集
        LineDataSet mLineDataSet = new LineDataSet(values, "");
        // 用y轴的集合来设置参数
        // 线宽
        mLineDataSet.setLineWidth(1.8f);
        // 显示的圆形大小
        //mLineDataSet.setCircleSize(5.0f);
        mLineDataSet.setDrawCircles(true);
        mLineDataSet.setDrawCircleHole(false);
        mLineDataSet.setCircleRadius(4f);
        // 填充折线上数据点、圆球里面包裹的中心空白处的颜色。
        //mLineDataSet.setCircleColorHole(0xffff7f00);
        //mLineDataSet
        //mLineDataSet.set
        // 折线的颜色
        mLineDataSet.setColor(Color.WHITE);

        // 圆球的颜色
        mLineDataSet.setCircleColor(0xffff7f00);

        // 设置mLineDataSet.setDrawHighlightIndicators(false)后，Highlight的十字交叉的纵横线将不会显示，
        //同时，mLineDataSet.setHighLightColor(Color.CYAN)失效。
        mLineDataSet.setDrawHighlightIndicators(false);

        // 按击后，十字交叉线的颜色
        //mLineDataSet.setHighLightColor(Color.RED);

        // 设置这项上显示的数据点的字体大小。
        //mLineDataSet.setValueTextSize(10.0f);
        // mLineDataSet.setDrawCircleHole(true);

        // 改变折线样式，用曲线,默认是直线


        // 填充曲线下方的区域，红色，半透明。
        //if(isFill){
            //mLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            // 曲线的平滑度，值越大越平滑。
            //mLineDataSet.setCubicIntensity(0.2f);
            mLineDataSet.setDrawFilled(true);
            //mLineDataSet.setFillAlpha(255);
           // mLineDataSet.setFillDrawable(drawable);
            //mLineDataSet.setColor(0xff2C374B);
        //}
        mLineDataSet.setFillColor(0xff2C374B);
        //mLineDataSet.setFormLineWidth(1f);
        //mLineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        //mLineDataSet.setFormSize(15f);



        mLineDataSet.setValueFormatter(new MyValueFormatter());
        ArrayList<ILineDataSet> mLineDataSets = new ArrayList<ILineDataSet>();
        mLineDataSets.add(mLineDataSet);

        LineData mLineData = new LineData(mLineDataSets);
        mLineData.setDrawValues(false);
        // 是否在折线图上添加边框
        lineChart.setDrawBorders(false);
        lineChart.setTouchEnabled(true);
        //mLineChart.setDescription("");// 数据描述

        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        //mLineChart.setNoDataTextDescription("如果传给MPAndroidChart的数据为空，那么你将看到这段文字。@Zhang Phil");

        // 是否绘制背景颜色。
        // 如果mLineChart.setDrawGridBackground(false)，
        // 那么mLineChart.setGridBackgroundColor(Color.CYAN)将失效;
        //lineChart.setDrawGridBackground(true);
        //lineChart.setGridBackgroundColor(Color.DKGRAY);

        // 触摸
//		mLineChart.setTouchEnabled(true);

        // 拖拽
//		mLineChart.setDragEnabled(true);

        // 缩放
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        //lineChart.setBottom(50);
        //lineChart.setRight(50);
        //lineChart.setPadding(50,50,50,50);
        //lineChart.parent
        //lineChart.setPaddingRelative(10,10,10,10);

        // 设置背景
        //lineChart.setBackgroundColor(0xee343E57);

        // 设置x,y轴的数据
        lineChart.setData(mLineData);

        lineChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴(true不隐藏)
        /*lineChart.getAxisRight().setGridColor(0x00ffffff);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getAxisRight().setXOffset(10f);
        lineChart.getAxisRight().setZeroLineColor(0x00000000);*/
        //mLineChart.getAxisLeft().setEnabled(false);
        XAxis xAxis=lineChart.getXAxis();
        //xAxis.setXOffset(10f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);// 让x轴在下面
        xAxis.setGridColor(0x00ffffff);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(0xff248ECC);
        YAxis yAxis=lineChart.getAxisLeft();
        yAxis.setLabelCount(5);
        //遗留问题,暂时这么解决
        int maxValue=0;
        yAxis.setAxisMaximum(5);
        for(int i=0;i<myValues.size();i++){
            int temp=myValues.get(i).intValue();
            if(temp<5){
                continue;
            }else {
                maxValue=maxValue<temp?temp:maxValue;
            }
        }
        if(maxValue>=5){
            lineChart.getAxisLeft().setLabelCount(3);
            yAxis.resetAxisMaximum();
            yAxis.setAxisMaximum(maxValue+2);
        }
        yAxis.setDrawAxisLine(false);
        yAxis.setAxisMinimum(0f);
        yAxis.setValueFormatter(new MyValueFormatter());
        yAxis.setXOffset(20f);
        yAxis.setTextColor(0xff248ECC);
        //yAxis.setYOffset(10f);
        //mLineChart.getAxisLeft().setStartAtZero(false);
        yAxis.setGridColor(0x00ffffff);
        //mLineChart.getXAxis().setEnabled(true);
        //mLineChart.getXAxis().setDrawLabels(false);
        xAxis.setLabelCount(7);
        //mLineChart.getXAxis().setValueFormatter(new MyXFormatter(myXvalues));
        //X轴显示string
        xAxis.setValueFormatter(new IndexAxisValueFormatter(myXvalues));
        xAxis.setGranularity(1f);
        //lineChart.setPadding(20,20,20,20);
        //mLineChart.getXAxis().setXOffset(-22f);
        //mLineChart.getXAxis().setCenterAxisLabels(true);
        //mLineChart.getXAxis().setAvoidFirstLastClipping(true);
        //mLineChart.getXAxis().setCenterAxisLabels(true);
        //去掉description label
        lineChart.getDescription().setEnabled(false);
        //去掉左下角的颜色块
        lineChart.getLegend().setEnabled(false);
        MyMarkerView markerView=new MyMarkerView(context, R.layout.chart_markerview);
        markerView.setChartView(lineChart);
        lineChart.setMarker(markerView);
        lineChart.invalidate();
        // 设置比例图标示，就是那个一组y的value的
//		Legend mLegend = mLineChart.getLegend();
//
//		mLegend.setPosition(LegendPosition.BELOW_CHART_CENTER);
//		mLegend.setForm(LegendForm.CIRCLE);// 样式
//		mLegend.setFormSize(15.0f);// 字体
//		mLegend.setTextColor(Color.RED);// 颜色

        // 沿x轴动画，时间2000毫秒。
        //mLineChart.animateX(100);
    }
    public static class MyValueFormatter implements IValueFormatter,IAxisValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("######## m"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            //Log.i("shao", " value : " + mFormat.format(value));
            return (int)value+" m"; // e.g. append a dollar-sign
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return (int)value+" m";
        }
    }

    static class MyMarkerView extends MarkerView{

        TextView textView;
        DecimalFormat format;

        /**
         * Constructor. Sets up the MarkerView with a custom layout resource.
         *
         * @param context
         * @param layoutResource the layout resource to use for the MarkerView
         */
        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            textView=findViewById(R.id.tv_content);
            format=new DecimalFormat("##.# m");
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {

            textView.setText(format.format(e.getY()));
            //textView.setText(""+Utils.formatNumber(e.getY(),0,true));
            super.refreshContent(e, highlight);
        }

        MPPointF mpPointF;

        @Override
        public MPPointF getOffset() {

            if(mpPointF==null){
                mpPointF= new MPPointF(-(getWidth()/2),-getHeight());
            }

            return mpPointF;
        }
    }

}
