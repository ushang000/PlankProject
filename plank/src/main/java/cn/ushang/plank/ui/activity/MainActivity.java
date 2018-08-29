package cn.ushang.plank.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.ArrayList;

import cn.ushang.plank.R;


public class MainActivity extends AppCompatActivity {

    PhysicalTrainingFragment physicalTrainingFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(physicalTrainingFragment==null){
            physicalTrainingFragment=new PhysicalTrainingFragment(this);
        }
        replace(physicalTrainingFragment,R.id.content);
    }
    public void replace(Fragment fragment, int id){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }
}
