package cn.ushang.plank.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import cn.ushang.plank.R;
import cn.ushang.plank.model.LevelData;
import cn.ushang.plank.model.MyData;
import cn.ushang.plank.ui.adpter.MainPagerAdapter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    TrainingLevelFragment physicalTrainingFragment;
    StatisticsDataFragment statisticsDataFragment;
    List<Fragment> fragments=new ArrayList<>();
    private MyData data;
    private ViewPager main_viewPager;
    private MainPagerAdapter adapter;
    private TextView main_level;
    private TextView main_statistics;
    private TextView page_line;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//防止5.x以后半透明影响效果，使用这种透明方式
        }

        main_viewPager=findViewById(R.id.main_viewpager);
        main_level=findViewById(R.id.title1);
        main_level.setOnClickListener(this);
        main_statistics=findViewById(R.id.title2);
        main_statistics.setOnClickListener(this);

        page_line=findViewById(R.id.page_line);


        if(physicalTrainingFragment==null){
            physicalTrainingFragment=new TrainingLevelFragment(this,getLevelData());
        }
        if(statisticsDataFragment==null){
            statisticsDataFragment=new StatisticsDataFragment();
        }
        fragments.add(physicalTrainingFragment);
        fragments.add(statisticsDataFragment);

        adapter=new MainPagerAdapter(getSupportFragmentManager(),fragments);
        main_viewPager.setAdapter(adapter);
        main_viewPager.addOnPageChangeListener(this);
        //replace(physicalTrainingFragment,R.id.content);
        //getMacAddress();
    }

    /*public void replace(Fragment fragment, int id){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }*/

    public List<LevelData> getLevelData(){

        try {
            InputStream open;
            AssetManager assetManager=getAssets();
            open=assetManager.open("data_male.json");
            data=new Gson().fromJson(new InputStreamReader(open),MyData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("shao","datas : "+data.getDataList());

        return data.getDataList();
    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//设置透明导航栏
        }
    }*/

    public void getMacAddress(){
        String macSerial = null;
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces!=null&&interfaces.hasMoreElements()) {
                NetworkInterface iF = interfaces.nextElement();
                if(iF.getName().equals("wlan0")){
                    byte[] addr = iF.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        continue;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    String mac = buf.toString();
                    Log.i("mac", "interfaceName="+iF.getName()+", mac="+mac);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //return macSerial;
    }

    @Override
    public void onClick(View v) {
        if(v==main_level){
            main_viewPager.setCurrentItem(0);
        }else if(v==main_statistics){
            main_viewPager.setCurrentItem(1);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position==0){
            page_line.animate().translationX(0);
        }else if(position==1){
            page_line.animate().translationX(page_line.getWidth());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
