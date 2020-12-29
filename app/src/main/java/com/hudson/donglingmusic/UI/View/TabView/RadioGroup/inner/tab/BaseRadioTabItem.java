package com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner.tab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.BaseRadioButton;
import com.hudson.donglingmusic.UI.View.TabView.ILoadDataView;
import com.hudson.donglingmusic.UI.View.TabView.ITab;
import com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner.tabContent.AbsRadioTabContent;
import com.hudson.donglingmusic.common.Utils.CommonUtils;


/**
 * RadioButton类型的tab view
 * Created by Hudson on 2018/12/29.
 */
public abstract class BaseRadioTabItem<T extends AbsRadioTabContent> extends BaseRadioButton<View> implements ITab {
    protected final T mContentView;//tab对应的内容(本身并不是布局)
    protected Context mActivityContext;

    public BaseRadioTabItem(Context context, int titleId, ViewGroup parent){
        super(context, titleId);
        mActivityContext = context;
        setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtils.getDimension(R.dimen.large_text_size));
        setGravity(Gravity.CENTER);
        setLayoutParams(new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
        mContentView = createContentView(parent);
        mExtraValue = mContentView.getContent();//tab内容对应的布局
    }

    @Override
    public final View getContentView(Context context) {
        return mExtraValue;
    }

    @Override
    public void loadAndRefreshView() {
        if(mContentView instanceof ILoadDataView){
            ((ILoadDataView) mContentView).loadData();
        }
    }

    /**
     * 初始化内容布局
     * @param parent 用户测量子view大小的布局。注意：子类不允许添加布局到parent
     */
    @NonNull
    protected abstract T createContentView(ViewGroup parent);

    @Override
    public void onCheck(){
        if(mContentView != null){
            mContentView.onCheck();
        }
    }

    @Override
    public void onUncheck(){
        if(mContentView != null){
            mContentView.onUncheck();
        }
    }

    @Override
    public void onDestroy(){
        if(mContentView != null){
            mContentView.onDestroy();
        }
    }
}
