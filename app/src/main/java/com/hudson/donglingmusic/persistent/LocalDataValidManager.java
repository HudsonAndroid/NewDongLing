package com.hudson.donglingmusic.persistent;

import com.hudson.donglingmusic.UI.Item.TabContentView.home.HomeHotPage;
import com.hudson.donglingmusic.entity.SheetDetail;
import com.hudson.donglingmusic.entity.SongSheet;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hudson on 2020/2/27.
 */
public class LocalDataValidManager {
    private static Map<Class,Integer> mExpireTimeMap = new HashMap<>();
    private static final long DAY_MILL = 24 * 60 * 60 * 1000;//此处必须为long类型，如果使用int类型与较大数相乘之后，将会溢出，造成出现负值情况
    private static final int DEFAULT_EXPIRE_DAYS = 360;

    static {
        mExpireTimeMap.put(HomeHotPage.class,1);
        mExpireTimeMap.put(SongSheet.class,31);
        mExpireTimeMap.put(SheetDetail.class,60);
    }

    public static long getExpireTime(Class pClass,long startTime){
        int days = DEFAULT_EXPIRE_DAYS;
        if (mExpireTimeMap.containsKey(pClass)) {
            days = mExpireTimeMap.get(pClass);
        }
        return startTime + days * DAY_MILL;//days*DAY_MILL需为long类型，避免溢出
    }
}
