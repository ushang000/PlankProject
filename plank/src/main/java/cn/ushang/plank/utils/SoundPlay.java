package cn.ushang.plank.utils;

import java.util.Arrays;
import java.util.List;

public class SoundPlay {

    static List<Integer> lists = Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9,10,15,20,25,30,40,50});

    public static int middleSound(int i){
        if (i <= 10) {
            return 0;
        }
        int i2 = Integer.MAX_VALUE;
        for (int c4238a : lists) {
            if (Math.abs(c4238a - i) < Math.abs(i2)) {
                i2 = c4238a - i;
            }
        }
        return getByNumber(lists,i2 + i);
    }
    private static int getByNumber(List<Integer> lists,int i) {
        for (int c4238a : lists) {
            if (c4238a == i) {
                return c4238a;
            }
        }
        return 0;
    }

}
