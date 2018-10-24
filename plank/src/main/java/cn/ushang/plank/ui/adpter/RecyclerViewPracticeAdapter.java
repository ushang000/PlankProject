package cn.ushang.plank.ui.adpter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ushang.plank.R;
import cn.ushang.plank.model.LevelData;
import cn.ushang.plank.ui.view.InclineTextView;
import cn.ushang.plank.ui.view.SwipeItemLayout;
import cn.ushang.plank.utils.ResourceGet;
import cn.ushang.plank.utils.VarUtils;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ushang on 2018/8/27.
 */

public class RecyclerViewPracticeAdapter extends RecyclerView.Adapter<RecyclerViewPracticeAdapter.MyHolder> {

    OnItemClickListener itemClickListener;
    List<LevelData> mLevelDatas;
    Map<String, String> levelType;
    SharedPreferences plankDatas;
    private Context mContext;
    private ResourceGet resource;
    ArrayList<Integer> mDataColors = new ArrayList<Integer>() {
        {
            add(0xFFC5A6AB);
            add(0xFF84869D);
            add(0xFFB9925B);
            add(0xFF778FAB);
            add(0xFF9D88AF);
            add(0xFFBF905A);
            add(0xFF7A3C25);
            add(0xFFBC1717);
        }
    };

    public RecyclerViewPracticeAdapter(Context context, List<LevelData> levelData) {
        mContext = context;
        mLevelDatas = levelData;
        levelType = new HashMap<>();
        plankDatas = context.getSharedPreferences("StatisticsDatas", MODE_PRIVATE);
        resource = new ResourceGet(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_text, parent, false);
        MyHolder myHolder = new MyHolder(view);
        //view.setOnClickListener(this);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        String name = mLevelDatas.get(position).getDesc_key();
        holder.slantText.setVisibility(View.INVISIBLE);
        holder.swipe.setTag(6);
        if (name.equals("level_teach")) {
            holder.slantText.setVisibility(View.VISIBLE);
            holder.swipe.setTag(5);
        }
        holder.textView.setText(resource.getNameString(name));
        holder.textView.setTextColor(Color.parseColor(mLevelDatas.get(position).getColor_key()));
        holder.textTime.setText("计时" + resource.getDurationFormatted(mLevelDatas.get(position).getDuration()) + "分钟");
        holder.imageView.setImageResource(resource.getDrawableResourceId(mLevelDatas.get(position).getImg_src()));
        //holder.itemView.setTag(position);
        //holder.itemView.setTag(position,"official");
    }

    @Override
    public int getItemCount() {
        return mLevelDatas == null ? 0 : mLevelDatas.size();
    }

    public void deleteData(int position) {
        SharedPreferences preferences = mContext.getSharedPreferences("TeachData", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Map<String, ?> values = preferences.getAll();
        int size = values.size() - 1;
        if (size >= 1) {
            editor.remove("teach" + (size - position)).putInt("teachNum", size - 1).apply();
            if (position == 1) {
                String json = preferences.getString("teach" + size, "nothing");
                editor.remove("teach" + size).putString("teach" + (size - 1), json).apply();
            /*if (size == 2) {
                String json = preferences.getString("teach2", "nothing");
                editor.remove("teach2").putString("teach1", json).apply();
            }
            if (size == 3) {
                String json = preferences.getString("teach3", "nothing");
                editor.remove("teach3").putString("teach2", json).apply();
            }*/
            } else if (position == 2) {
                String json1 = preferences.getString("teach2", "nothing");
                editor.putString("teach1", json1).remove("teach2").apply();
                String json2 = preferences.getString("teach3", "nothing");
                editor.putString("teach2", json2).remove("teach3").apply();
            }
        }

    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textView;
        TextView textTime;
        ImageView imageView;
        Button deleteButton;
        View mainLayout;
        InclineTextView slantText;
        SwipeItemLayout swipe;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.level_text);
            textTime = itemView.findViewById(R.id.level_time);
            imageView = itemView.findViewById(R.id.level_image);
            deleteButton = itemView.findViewById(R.id.delete);
            mainLayout = itemView.findViewById(R.id.main);
            slantText = itemView.findViewById(R.id.slant_text);
            swipe = itemView.findViewById(R.id.swipe);
            deleteButton.setOnClickListener(this);
            mainLayout.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete:
                    int pos = getAdapterPosition();
                    mLevelDatas.remove(pos);
                    notifyItemRemoved(pos);
                    deleteData(pos);
                    break;
                case R.id.main:
                    if (itemClickListener != null) {
                        int position = getAdapterPosition();
                        itemClickListener.onItemClick(v, position);
                    }
                    break;
            }

        }
    }


    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }
}
