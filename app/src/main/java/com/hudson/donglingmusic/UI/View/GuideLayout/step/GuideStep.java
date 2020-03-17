package com.hudson.donglingmusic.UI.View.GuideLayout.step;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudson.donglingmusic.UI.View.GuideLayout.focusEntity.FocusEntity;
import com.hudson.donglingmusic.UI.View.GuideLayout.layout.GuideLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 步骤
 * Created by hpz on 2019/3/1.
 */
public class GuideStep implements IStep{
    private int mGuideLayoutId;
    private int mNextButtonId;
    protected List<FocusEntity> mFocusEntities;
    protected final List<Integer> mSelfFocusIds = new ArrayList<>();

    /**
     * 引导步骤
     * @param guideLayoutId 该步骤的布局id
     * @param nextButtonId 该步骤跳转到下一步的按钮的id
     */
    public GuideStep(int guideLayoutId, int nextButtonId) {
        mGuideLayoutId = guideLayoutId;
        mNextButtonId = nextButtonId;
    }

    public GuideStep addFocusEntity(FocusEntity entity){
        if(mFocusEntities == null){
            mFocusEntities = new ArrayList<>();
        }
        mFocusEntities.add(entity);
        return this;
    }

    @Override
    public void showStep(final GuideLayout parent) {
        if(mGuideLayoutId <= 0 || mNextButtonId <= 0){
            Log.w("GuideStep","the step resource id is invalid!");
            return ;
        }
        Context context = parent.getContext();
        LayoutInflater.from(context).inflate(mGuideLayoutId,parent);
        View nextBtn = parent.findViewById(mNextButtonId);
        if(nextBtn != null){
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parent.showNextStep();
                }
            });
        }
        decodeSelfFocus(parent);
        if(mFocusEntities != null){
            parent.addFocusItems(mFocusEntities);
        }
    }

    /**
     * 解析自身布局文件中设置的高亢布局
     * @param parent
     */
    private void decodeSelfFocus(ViewGroup parent){
        if(mSelfFocusIds.size() > 0){
            for (Integer focusId : mSelfFocusIds) {
                View focus = parent.findViewById(focusId);
                if(focus != null){
                    mFocusEntities.add(new FocusEntity(focus));
                }
            }
        }
    }

    @Override
    public void prepare() {
        if(mFocusEntities != null){
            for (FocusEntity focusEntity : mFocusEntities) {
                focusEntity.prepare();
            }
        }
    }
}
