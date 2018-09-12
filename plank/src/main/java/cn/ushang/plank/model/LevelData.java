package cn.ushang.plank.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shao on 2018/9/4.
 */

public class LevelData implements Serializable {

    private String desc_key;
    private String img_src;
    private String color_key;
    private List<Exercise> exercises = new ArrayList<>();

    public String getDesc_key() {
        return desc_key;
    }

    public void setDesc_key(String desc_key) {
        this.desc_key = desc_key;
    }

    public int getDuration() {
        int i = 0;
        for (Exercise duration : this.exercises) {
            i = duration.getDuration().intValue() + i;
        }
        return i;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_key(String img_src) {
        this.img_src = img_src;
    }

    public String getColor_key() {
        return color_key;
    }

    public void setColor_key(String color_key) {
        this.color_key = color_key;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }
}
