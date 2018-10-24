package cn.ushang.plank.model;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * Created by ushang on 2018/8/31.
 */

public class Exercise implements Serializable {

    private Integer duration;
    private String img_key;
    private String name_key;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getImg_key() {
        return img_key;
    }

    public void setImg_key(String img_key) {
        this.img_key = img_key;
    }

    public String getName_key() {
        return name_key;
    }

    public void setName_key(String name_key) {
        this.name_key = name_key;
    }

}


