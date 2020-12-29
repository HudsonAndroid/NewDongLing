package com.hudson.donglingmusic.UI.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Hudson on 2020/3/26.
 */
public class MenuToggleView extends AppCompatImageView implements DrawerLayout.DrawerListener{
    private DrawerLayout mDrawerLayout;

    public MenuToggleView(Context context) {
        this(context, null);
    }

    public MenuToggleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuToggleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDrawerLayout != null){
                    if(mDrawerLayout.isDrawerOpen(8388611)){
                        mDrawerLayout.closeDrawer(8388611);
                    }else{
                        mDrawerLayout.openDrawer(8388611);
                    }
                }
            }
        });
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
    }

//    public void syncState() {
//        if (this.mDrawerLayout.isDrawerOpen(8388611)) {
//            this.setPosition(1.0F);
//        } else {
//            this.setPosition(0.0F);
//        }
//
//        if (this.mDrawerIndicatorEnabled) {
//            this.setActionBarUpIndicator(this.mSlider, this.mDrawerLayout.isDrawerOpen(8388611) ? this.mCloseDrawerContentDescRes : this.mOpenDrawerContentDescRes);
//        }
//
//    }

    @Override
    public void onDrawerSlide(@NonNull View view, float v) {

    }

    @Override
    public void onDrawerOpened(@NonNull View view) {

    }

    @Override
    public void onDrawerClosed(@NonNull View view) {

    }

    @Override
    public void onDrawerStateChanged(int i) {

    }
}
