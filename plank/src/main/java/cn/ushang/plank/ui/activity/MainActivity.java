package cn.ushang.plank.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

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


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    TrainingLevelFragment physicalTrainingFragment;
    StatisticsDataFragment statisticsDataFragment;
    ImteacherFragment imteacherFragment;
    List<Fragment> fragments = new ArrayList<>();
    private MyData data;
    public ViewPager main_viewPager;
    public MainPagerAdapter adapter;
    private TextView main_level;
    private TextView main_statistics;
    private TextView main_teach;
    private TextView page_line;

    private String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int OPEN_REQUEST_CODE = 15;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//防止5.x以后半透明影响效果，使用这种透明方式
        }

        UMConfigure.init(this, "5b9a0d00f29d984934000080", null, 0, null);
        main_viewPager = findViewById(R.id.main_viewpager);
        main_level = findViewById(R.id.title1);
        main_level.setOnClickListener(this);
        main_statistics = findViewById(R.id.title2);
        main_statistics.setOnClickListener(this);
        main_teach = findViewById(R.id.title_teach);
        main_teach.setOnClickListener(this);

        page_line = findViewById(R.id.page_line);


        if (physicalTrainingFragment == null) {
            physicalTrainingFragment = new TrainingLevelFragment(this, getLevelData());
        }
        if (statisticsDataFragment == null) {
            statisticsDataFragment = new StatisticsDataFragment();
        }
        if (imteacherFragment == null) {
            imteacherFragment = new ImteacherFragment();
        }
        fragments.add(physicalTrainingFragment);
        fragments.add(statisticsDataFragment);
        fragments.add(imteacherFragment);

        adapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        main_viewPager.setAdapter(adapter);
        main_viewPager.addOnPageChangeListener(this);
        //getMacAddress();
        if (checkPermissions(PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, OPEN_REQUEST_CODE);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
/*public void replace(Fragment fragment, int id){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }*/

    public void toLevelFragment() {
        main_viewPager.setCurrentItem(0);
        //adapter.notifyDataSetChanged();
    }

    public List<LevelData> getLevelData() {

        try {
            InputStream open;
            AssetManager assetManager = getAssets();
            open = assetManager.open("data_male.json");
            data = new Gson().fromJson(new InputStreamReader(open), MyData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_REQUEST_CODE) {
            if (checkPermissions(PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, OPEN_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!requestPermissionsResult(grantResults)) {
            toSetPermissions();
        }
    }

    private boolean requestPermissionsResult(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }

        return true;
    }

    private void toSetPermissions() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("开启权限");
        dialog.setMessage("请去设置界面开启相应权限，否则会影响程序功能");
        dialog.setPositiveButton("去设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, OPEN_REQUEST_CODE);
                dialog.dismiss();
            }
        }).create();
        dialog.setCancelable(false);
        dialog.show();

    }

    private boolean checkPermissions(String[] permissions) {
        for (String permission : permissions) {
            return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED;
        }
        return false;
    }

    public void getMacAddress() {
        String macSerial = null;
        Enumeration<NetworkInterface> interfaces = null;
        try {
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces != null && interfaces.hasMoreElements()) {
                NetworkInterface iF = interfaces.nextElement();
                if (iF.getName().equals("wlan0")) {
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
                    Log.i("mac", "interfaceName=" + iF.getName() + ", mac=" + mac);
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //return macSerial;
    }

    @Override
    public void onClick(View v) {
        if (v == main_level) {
            main_viewPager.setCurrentItem(0);
        } else if (v == main_statistics) {
            main_viewPager.setCurrentItem(1);
        } else if (v == main_teach) {
            main_viewPager.setCurrentItem(2);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            page_line.animate().translationX(0);
        } else if (position == 1) {
            page_line.animate().translationX(page_line.getWidth());
        } else if (position == 2) {
            page_line.animate().translationX(page_line.getWidth() * 2);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
