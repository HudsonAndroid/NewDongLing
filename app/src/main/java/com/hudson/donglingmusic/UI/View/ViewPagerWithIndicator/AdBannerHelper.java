package com.hudson.donglingmusic.UI.View.ViewPagerWithIndicator;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.hudson.donglingmusic.UI.View.ViewPagerWithIndicator.ViewPager.AutoSwitchViewPager;
import com.hudson.donglingmusic.UI.View.ViewPagerWithIndicator.indicator.IPagerIndicator;

/**
 * 广告轮播图帮助类
 * 外界可以自行设定轮播控件的位置和对应的指示器位置，
 * 而不受限于固定位置。
 * Created by Hudson on 2019/5/24.
 */
public class AdBannerHelper {

    /**
     * 公共控件库中虽然有nd-banner-layout控件，但是经过实际使用，发现
     * 体验效果不佳（用户触摸情况下仍然自动滑动，很显然对用户感受不好），
     * 同时内部使用的逻辑不太利于后期拓展。因此决定自行自定义控件。
     * @param viewPager 自动切换的ViewPager
     * @param indicator 对应的指示器
     * @param closeView 广告的关闭按钮
     */
    public static void combineIntoBanner(@NonNull AutoSwitchViewPager viewPager,
                                         @NonNull IPagerIndicator indicator,
                                         @Nullable View closeView){
        viewPager.attachIndicator(indicator);
        if(closeView != null){
            closeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
