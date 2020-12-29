package com.hudson.donglingmusic.UI.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.Window;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.module.skin.factory.SkinFactory;
import com.hudson.donglingmusic.module.skin.skinAttr.BackgroundColorAttr;
import com.hudson.donglingmusic.module.skin.skinAttr.BackgroundDrawbleAttr;
import com.hudson.donglingmusic.module.skin.skinAttr.TextColorAttr;

/**
 * Created by Hudson on 2019/11/10.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected SkinFactory mFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        AppCompatDelegate delegate = getDelegate();
        mFactory = new SkinFactory(delegate);
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(this), mFactory);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        prepare();
        initView((ConstraintLayout) findViewById(R.id.cl_root));
        initData();
    }

    protected void prepare(){
        mFactory.addSkinAttr(new TextColorAttr("common_text_color"))
                .addSkinAttr(new TextColorAttr("common_light_gray"))
                .addSkinAttr(new BackgroundColorAttr("common_light_white"))
                .addSkinAttr(new BackgroundDrawbleAttr("default_bg"))
                .addSkinAttr(new BackgroundDrawbleAttr("selector_hot_item_bg"))
                .addSkinAttr(new BackgroundDrawbleAttr("shape_round_bg"))
                .addSkinAttr(new BackgroundDrawbleAttr("shape_round_top_bg"));
    }

    protected abstract void initView(ConstraintLayout parent);

    protected void initData(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFactory.onDestroy();
    }
}
