package com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.TabView.ILoadDataView;
import com.hudson.donglingmusic.UI.View.TabView.ITab;
import com.hudson.donglingmusic.Utils.CommonUtils;

/**
 * RadioButton类型的tab view
 * Created by hpz on 2018/12/29.
 */
public abstract class BaseRadioTabItem extends BaseRadioButton<View> implements ITab {
    protected Context mActivityContext;

    public BaseRadioTabItem(Context context, int titleId){
        super(context, titleId);
        mActivityContext = context;
        setTextSize(TypedValue.COMPLEX_UNIT_PX, CommonUtils.getDimension(R.dimen.normal_header_height));
        setGravity(Gravity.CENTER);
        setLayoutParams(new RadioGroup.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
        mExtraValue = createContentView(context);
    }

    @Override
    public View getContentView(Context context) {
        return mExtraValue;
    }

    @Override
    public void loadAndRefreshView() {
        if(mExtraValue instanceof ILoadDataView){
            ((ILoadDataView) mExtraValue).loadData();
        }
    }

    protected abstract View createContentView(Context context);

    /**
     * 获取顶部头布局的背景图id
     * @return id
     */
    public abstract int getTabViewHeaderBgId();
}
