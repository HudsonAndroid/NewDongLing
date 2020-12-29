package com.hudson.donglingmusic.UI.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.hudson.donglingmusic.R;

/**
 * Created by Hudson on 2020/3/11.
 */
public class InputView extends ConstraintLayout implements TextWatcher {
    private static final int DEFAULT_MAX_COUNT = 4000;
    private int mMaxCount = DEFAULT_MAX_COUNT;
    private Animation mShakeAnimation;
    private boolean mIsInvalid = false;
    private TextView mIndicator;
    private EditText mInput;

    public InputView(Context context) {
        this(context, null);
    }

    public InputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.custom_view_round_edit_text,this);
        mIndicator = (TextView) this.findViewById(R.id.tv_indicator);
        mInput = (EditText) this.findViewById(R.id.et_input);
        mInput.addTextChangedListener(this);
        if(attrs!=null){//避免由于第一个构造方法造成异常
            TypedArray ta = context.obtainStyledAttributes(attrs,
                    R.styleable.InputView);
            mMaxCount = ta.getResourceId(R.styleable.InputView_maxCount,DEFAULT_MAX_COUNT);
            Log.e("hudson","获取到最大可填"+mMaxCount);
            ta.recycle();
        }
        mIndicator.setText(indicatorShowStyle(0,mMaxCount));
    }

    public void setText(String text){
        mInput.setText(text);
    }

    public String getInput(){
        return mInput.getText().toString();
    }

    public int getMaxCount() {
        return mMaxCount;
    }

    public void setMaxCount(int maxCount) {
        mMaxCount = maxCount;
        int current = 0;
        Editable text = mInput.getText();
        if(text != null){
            current = text.length();
        }
        mIndicator.setText(indicatorShowStyle(current,mMaxCount));
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        String input = trimStart(s.toString());
        int lenOffset = s.length() - input.length();
        if(input.length() > mMaxCount){
            s.delete(mMaxCount + lenOffset,s.length());
            input = input.substring(0,mMaxCount);
            startShakeInvalidStatus();
        }
        if(mIsInvalid){
            int normalColor = Color.DKGRAY;
            mIndicator.setTextColor(normalColor);
            mIsInvalid = false;
        }
        mIndicator.setText(indicatorShowStyle(input.length(),mMaxCount));
    }


    private static String trimStart(String resource){
        int len = resource.length();
        int st = 0;

        while ((st < len) && (resource.charAt(st) <= ' ')) {
            st++;
        }
        return ((st > 0)) ? resource.substring(st, len) : resource;
    }

    private void startShakeInvalidStatus() {
        if(mShakeAnimation == null){
            mShakeAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_say_no);
        }
        startAnimation(mShakeAnimation);
    }

    /**
     * 设置数据非法提示
     * @param resId 提示的资源id
     */
    public void setInvalidTips(int resId){
        mIndicator.setTextColor(getResources().getColor(R.color.common_error));
        mIndicator.setText(resId);
        startShakeInvalidStatus();
        mIsInvalid = true;
    }

    /**
     * 显示当前字数的方式
     * @param curCount 当前字数
     * @param maxCount 最多可输入字数
     * @return 返回组合的字符串
     */
    protected String indicatorShowStyle(int curCount,int maxCount){
        return curCount + "/" + maxCount;
    }
}
