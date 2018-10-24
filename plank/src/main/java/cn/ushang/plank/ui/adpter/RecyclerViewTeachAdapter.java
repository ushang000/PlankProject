package cn.ushang.plank.ui.adpter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.zip.Inflater;

import cn.ushang.plank.R;
import cn.ushang.plank.utils.ResourceGet;

public class RecyclerViewTeachAdapter extends RecyclerView.Adapter<RecyclerViewTeachAdapter.MyViewHolder>{

    Context mContext;
    ArrayList<String> mList;
    ArrayList<Integer> mTimes;
    ArrayList<String> mNames;
    IMyViewHolderClicks mClicks;
    ResourceGet resourceGet;

    public RecyclerViewTeachAdapter(Context context,ArrayList<String> motionList,ArrayList<String> nameList) {
        mList = motionList;
        mContext=context;
        mNames=nameList;
        resourceGet=new ResourceGet(mContext);
        mTimes=new ArrayList<>();
        mTimes.clear();
        for (int i=0;i<mList.size();i++){
            mTimes.add(0);
        }
    }

    public void setOnViewHolderClick(IMyViewHolderClicks clicks){
        mClicks=clicks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_teach, parent,false);
        final MyViewHolder holder = new MyViewHolder(view);
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(Integer) v.getTag();
                int time_num=mTimes.get(position);
                time_num = time_num + 5;
                //time.setTag(time_num);
                mTimes.set(position,time_num);
                holder.time.setText(mTimes.get(position)+" s");
                mClicks.onTimeChange(holder.time,mTimes,position);
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(Integer) v.getTag();
                int time_num=mTimes.get(position);
                if (time_num > 0) {
                    time_num = time_num - 5;
                }
                mTimes.set(position,time_num);
                holder.time.setText(mTimes.get(position)+" s");
                mClicks.onTimeChange(holder.time,mTimes,position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.plus.setTag(position);
        holder.minus.setTag(position);
        holder.teach_img.setImageResource(resourceGet.getDrawableResourceId(mList.get(position)));
        holder.name.setText(resourceGet.getNameString(mNames.get(position)));
        //Glide.with(mContext).load(mList.get(position)).into(holder.teach_img);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageButton minus;
        ImageButton plus;
        TextView time;
        TextView name;
        ImageView teach_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            minus = itemView.findViewById(R.id.minus);
            name=itemView.findViewById(R.id.teach_name);
            //minus.setOnClickListener(this);
            plus = itemView.findViewById(R.id.plus);
            //plus.setOnClickListener(this);
            time = itemView.findViewById(R.id.teach_time);
            teach_img = itemView.findViewById(R.id.teach_img);
        }

        @Override
        public void onClick(View v) {
            /*int position=getLayoutPosition();
            *//*switch (v.getId()){
                case R.id.minus:
                    if (time_num > 0) {
                        time_num = time_num - 5;
                    }
                    //time.setTag(time_num);
                    mTimes.set(position,time_num+" s");
                    mClicks.onTimeChange(time,position);
                    break;
                case R.id.plus:
                    time_num = time_num + 5;
                    //time.setTag(time_num);
                    mTimes.set(position,time_num+" s");
                    mClicks.onTimeChange(time,position);
                    break;
            }*//*
            int time_num=mTimes.get(position);
            if (v.getId() == R.id.minus) {
                if (time_num > 0) {
                    time_num = time_num - 5;
                }
            } else if (v.getId() == R.id.plus) {
                time_num = time_num + 5;
            }
            mTimes.set(position,time_num);
            time.setText(time_num + " s");
            Log.i("shao","the time is : "+time_num);*/
        }
    }

    public static interface IMyViewHolderClicks{

        public void onTimeChange(TextView v,ArrayList<Integer> times,int position);
    }

}
