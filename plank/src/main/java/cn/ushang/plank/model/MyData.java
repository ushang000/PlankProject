package cn.ushang.plank.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shao on 2018/9/4.
 */

public class MyData implements Serializable {

    private List<LevelData> data=new ArrayList<>();

    public List<LevelData> getDataList() {
        return data;
    }

    public void setDataList(List<LevelData> dataList) {
        this.data = dataList;
    }
}
