package com.hudson.donglingmusic.UI.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.Utils.CommonUtils;

/**
 * Created by Hudson on 2020/3/3.
 */
public class BackView extends AppCompatImageView {

    public BackView(Context context) {
        this(context, null);
    }

    public BackView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BackView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.contextThemeWrapperToActivity(getContext()).finish();
            }
        });
    }

    private void init(Context context, AttributeSet attrs) {
        int imageResId = R.drawable.selector_back_view;
        if(attrs!=null){//避免由于第一个构造方法造成异常
            TypedArray ta = context.obtainStyledAttributes(attrs,
                    R.styleable.BackView);
            imageResId = ta.getResourceId(R.styleable.BackView_src,imageResId);
            ta.recycle();
        }
        setImageResource(imageResId);
    }
}
