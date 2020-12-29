package com.hudson.donglingmusic.common.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hudson on 2020/3/2.
 */
public class DateUtils {

    public static String getTodayFormatStr(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
