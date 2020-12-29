package com.hudson.donglingmusic.common.Utils;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.global.DongLingApplication;

/**
 * Created by Hudson on 2020/3/2.
 */
public class StringUtils {

    public static String getHumanNum(int number){
        Resources resources = DongLingApplication.getGlobalContext().getResources();
        String tenThousand = resources.getString(R.string.common_ten_thousand);
        if(number>10000){
            int wan = number/10000;
            if(number<1000000){
                int qian = number%10000/1000;
                return wan+"."+qian+ tenThousand;
            }else{
                return wan+tenThousand;
            }

        }else{
            return String.valueOf(number);
        }
    }

    public static String cutStr(@NonNull String source, int limit){
        if(!TextUtils.isEmpty(source) && limit > 0 && limit < source.length()){
            return source.substring(0,limit) + "...";
        }
        return source;
    }
}
