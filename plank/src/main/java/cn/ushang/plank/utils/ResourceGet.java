package cn.ushang.plank.utils;

import android.content.Context;

/**
 * Created by ushang on 2018/9/6.
 */

public class ResourceGet {

    private Context mContext;

    public ResourceGet(Context context) {
        mContext = context;
    }

    public String getDurationFormatted(long time) {
        long intValue = time / 1000;
        long i = intValue / 60;
        intValue %= 60;
        return i + ":" + (intValue < 10 ? "0" + intValue : intValue);
    }

    public String getNameString(String str) {
        int identifier = mContext.getResources().getIdentifier(str, "string", mContext.getApplicationInfo().packageName);
        return identifier == 0 ? null : mContext.getString(identifier);
    }

    public Integer getDrawableResourceId(String str) {
        return mContext.getResources().getIdentifier(str, "mipmap", mContext.getApplicationInfo().packageName);
    }

    public Integer getSoundResourceId(String str){
        int identifier=mContext.getResources().getIdentifier(str,"raw",mContext.getApplicationInfo().packageName);
        return identifier == 0 ? null : Integer.valueOf(identifier);
    }

}
