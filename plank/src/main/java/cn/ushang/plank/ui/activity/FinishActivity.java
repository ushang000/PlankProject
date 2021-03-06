package cn.ushang.plank.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.ushang.plank.R;
import cn.ushang.plank.utils.ResourceGet;
import cn.ushang.plank.utils.VarUtils;

public class FinishActivity extends AppCompatActivity {

    private TextView finishLevelName;
    private TextView finishLevelTime;
    private ResourceGet resource;
    private SharedPreferences plankDatas;
    private Calendar calendar;
    private ImageView crunches;
    private int duration=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//防止5.x以后半透明影响效果，使用这种透明方式
        }
        plankDatas = getSharedPreferences("StatisticsDatas", MODE_PRIVATE);
        calendar = Calendar.getInstance();
        String dataKey=new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
        //String dataKey="20180910";
        Intent intent = getIntent();
        duration=intent.getIntExtra("duration", 0);
        if(duration>=375000){
            VarUtils.isLocked=false;
            plankDatas.edit().putBoolean("isLocked",VarUtils.isLocked).apply();
        }
        resource = new ResourceGet(this);
        finishLevelName = findViewById(R.id.finish_level_name);
        finishLevelTime = findViewById(R.id.finish_level_time);
        crunches=findViewById(R.id.crunches);
        finishLevelName.setText(resource.getNameString(intent.getStringExtra("desc_name")));
        finishLevelName.setTextColor(Color.parseColor(intent.getStringExtra("color_key")));
        finishLevelTime.setText(resource.getDurationFormatted(duration) + " 分钟");
        Glide.with(this).load(R.mipmap.abs_male_recomend).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(crunches);
        //plankDatas.edit().clear().apply();
        float beforeDatas=plankDatas.getFloat(dataKey,0);
        plankDatas.edit()
                .putFloat(dataKey, (float) duration/(1000*60)+beforeDatas)
                .apply();
    }

    public void finished(View view) {
        finish();
    }

}
