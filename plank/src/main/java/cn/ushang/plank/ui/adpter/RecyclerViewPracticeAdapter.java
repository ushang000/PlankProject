package cn.ushang.plank.ui.adpter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.ushang.plank.R;

/**
 * Created by shao on 2018/8/27.
 */

public class RecyclerViewPracticeAdapter extends RecyclerView.Adapter<RecyclerViewPracticeAdapter.MyHolder> implements View.OnClickListener{

    OnItemClickListener itemClickListener;
    ArrayList<String> mDataSet;
    ArrayList<Drawable> mDataImages;
    ArrayList<String> mDataTimes;

    public RecyclerViewPracticeAdapter(ArrayList<String> dataSet,ArrayList<Drawable> dataImages,ArrayList<String> dataTimes) {
        mDataSet = dataSet;
        mDataImages=dataImages;
        mDataTimes=dataTimes;
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
        holder.textView.setText(mDataSet.get(position));
        holder.textTime.setText(mDataTimes.get(position));
        holder.imageView.setImageDrawable(mDataImages.get(position));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    @Override
    public void onClick(View v) {
        if(itemClickListener!=null){
            itemClickListener.onItemClick((Integer) v.getTag());
        }
    }


    class MyHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView textTime;
        ImageView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.level_text);
            textTime=itemView.findViewById(R.id.level_time);
            imageView=itemView.findViewById(R.id.level_image);
        }
    }



    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
