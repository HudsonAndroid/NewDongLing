package com.hudson.donglingmusic.module.language;

import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.Keep;
import android.util.Log;

import java.util.Locale;

@Keep
public class LanguageModuleImpl implements LanguageModule {
    private static final String TAG = "LanguageModuleImpl";
    private boolean mIsTraditional;

    public LanguageModuleImpl() {
        String country = Locale.getDefault().getCountry();
        mIsTraditional = !country.equals("CN");
        Log.d(TAG, "LanguageModuleImpl: init " + mIsTraditional);
    }

    /**
     * 查看系统语言是否是中文
     * @return
     */
    public boolean isChinese(){
        Locale locale;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){//7.0以上获取系统设置中的语言
            locale = Resources.getSystem().getConfiguration().getLocales().get(0);
        }else{
            locale = Resources.getSystem().getConfiguration().locale;
        }
        return locale.getLanguage().equals("zh");
    }

    @Override
    public boolean isTraditional() {
        return mIsTraditional;
    }

    @Override
    public String getLanguageString() {
        if (mIsTraditional) {
            return "big5";
        } else {
            return "gb2312";
        }
    }
}
