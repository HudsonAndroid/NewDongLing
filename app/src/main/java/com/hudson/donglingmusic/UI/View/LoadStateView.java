package com.hudson.donglingmusic.UI.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hudson.donglingmusic.R;


/**
 * 加载提示控件，提示加载中、加载失败
 * Created by Hudson on 2018/11/6.
 */
public class LoadStateView extends RelativeLayout {
    private View mPbView;
    private TextView mTips;
    private ImageView mTipImg;
    private boolean mDisable = false;

    public LoadStateView(Context context) {
        this(context, null);
    }

    public LoadStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        LayoutInflater.from(context).inflate(R.layout.custom_view_load_state,this,true);
        mPbView = findViewById(R.id.pb_loading);
        mTips = (TextView) findViewById(R.id.tv_tips);
        mTipImg = (ImageView) this.findViewById(R.id.iv_tips);
    }

    public void loading(){
        if(!checkDisable()){
            mPbView.setVisibility(VISIBLE);
            mTipImg.setVisibility(GONE);
            mTips.setText(R.string.common_loading);
            setVisibility(VISIBLE);
        }
    }

    public boolean checkDisable(){
        if(mDisable){
            setVisibility(GONE);
            return true;
        }else{
            setVisibility(VISIBLE);
            return false;
        }
    }

    public boolean isDisable() {
        return mDisable;
    }

    public void setDisable(boolean disable) {
        mDisable = disable;
    }

    public void loadSuccess(){
        setVisibility(GONE);
    }

    public void loadFailed(){
        loadFailed(R.string.common_load_failed);
    }

    public void loadFailed(int resId){
        if(!checkDisable()){
            mPbView.setVisibility(GONE);
            mTipImg.setVisibility(VISIBLE);
            setVisibility(VISIBLE);
            mTips.setText(resId);
        }
    }

    public void showTips(int resId){
        if(!checkDisable()){
            mPbView.setVisibility(GONE);
            mTipImg.setVisibility(GONE);
            setVisibility(VISIBLE);
            mTips.setVisibility(VISIBLE);
            mTips.setText(resId);
        }
    }
}
