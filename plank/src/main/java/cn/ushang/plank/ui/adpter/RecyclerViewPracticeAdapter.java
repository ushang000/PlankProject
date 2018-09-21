package cn.ushang.plank.ui.adpter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.ushang.plank.R;
import cn.ushang.plank.model.LevelData;
import cn.ushang.plank.utils.ResourceGet;

/**
 * Created by ushang on 2018/8/27.
 */

public class RecyclerViewPracticeAdapter extends RecyclerView.Adapter<RecyclerViewPracticeAdapter.MyHolder> implements View.OnClickListener {

    OnItemClickListener itemClickListener;
    List<LevelData> mLevelDatas;
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
        resource=new ResourceGet(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_text, parent, false);
        MyHolder myHolder = new MyHolder(view);
        view.setOnClickListener(this);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.textView.setText(resource.getNameString(mLevelDatas.get(position).getDesc_key()));
        holder.textView.setTextColor(Color.parseColor(mLevelDatas.get(position).getColor_key()));
        holder.textTime.setText("计时" +resource.getDurationFormatted(mLevelDatas.get(position).getDuration()) + "分钟");
        Glide.with(mContext).load(resource.getDrawableResourceId(mLevelDatas.get(position).getImg_src())).into(holder.imageView);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mLevelDatas == null ? 0 : mLevelDatas.size();
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            int position = (Integer) v.getTag();
            itemClickListener.onItemClick(position);
        }
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView textTime;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.level_text);
            textTime = itemView.findViewById(R.id.level_time);
            imageView = itemView.findViewById(R.id.level_image);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
