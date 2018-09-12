package cn.ushang.plank.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ushang.plank.R;
import cn.ushang.plank.model.LevelData;
import cn.ushang.plank.ui.adpter.MyNoScrollPagerAdapter;
import cn.ushang.plank.ui.view.NoScrollViewPager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PhysicalTrainingActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private TextView train_current;
    private int size=0;
    private NoScrollViewPager viewPager;
    private TextView train_title;
    private LevelData mLeveldata;
    private MyNoScrollPagerAdapter adapter;
    private List<TrainingFragment> trainingFragments = new ArrayList<>();
    /*private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    */

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     *//*
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_physical_training);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().setStatusBarColor(Color.TRANSPARENT);//防止5.x以后半透明影响效果，使用这种透明方式
        }
        viewPager = findViewById(R.id.train_viewpager);
        train_title = findViewById(R.id.train_title);
        train_current=findViewById(R.id.train_current);
        Intent intent = getIntent();
        mLeveldata = (LevelData) intent.getExtras().getSerializable("level");
        size=mLeveldata.getExercises().size();
        train_current.setText("第 1/"+size+" 回合");
        train_title.setText(getTitleString(mLeveldata.getDesc_key()));
        train_title.setTextColor(Color.parseColor(mLeveldata.getColor_key()));
        adapter = new MyNoScrollPagerAdapter(getSupportFragmentManager(), mLeveldata.getExercises());
        viewPager.setAdapter(adapter);
    }

    private String getTitleString(String str) {
        int identifier = getResources().getIdentifier(str, "string", getPackageName());
        return identifier == 0 ? null : getString(identifier);
    }

    public void startNextPage() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        train_current.setText("第 "+(viewPager.getCurrentItem()+1)+"/"+size+" 回合");
    }

    public void goFinish() {
        Intent intent = new Intent(this, FinishActivity.class);
        intent.putExtra("desc_name",mLeveldata.getDesc_key());
        intent.putExtra("color_key",mLeveldata.getColor_key());
        intent.putExtra("duration",mLeveldata.getDuration());
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
