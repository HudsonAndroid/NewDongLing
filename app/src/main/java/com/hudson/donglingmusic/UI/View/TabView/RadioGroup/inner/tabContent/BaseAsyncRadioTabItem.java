package com.hudson.donglingmusic.UI.View.TabView.RadioGroup.inner.tabContent;

import android.view.ViewGroup;

import com.hudson.donglingmusic.UI.View.TabView.ILoadDataView;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.CommonOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;

/**
 * Created by Hudson on 2019/4/29.
 */
public abstract class BaseAsyncRadioTabItem<T> extends AbsRadioTabContent implements ILoadDataView {
    protected AsyncTask<T> mLoadTask;
//    private Subscription mNetWorkChange;

    public BaseAsyncRadioTabItem(ViewGroup parent) {
        super(parent);
    }

    @Override
    public void loadData() {
        if(mLoadTask == null){//通过作为成员，避免重复加载
            loadDataBefore();
            mLoadTask = new AsyncTask<T>().doInBackground(new IDoInBackground<T>() {
                @Override
                public T run() {
                    return getDataBackground();
                }
            }).doOnSuccess(new IDoOnSuccess<T>() {
                @Override
                public void onSuccess(T t) {
                    doOnSuccess(t);
                }
            }).doOnFail(new CommonOnFail(){
                @Override
                protected void handleOnFail(Throwable e) {
                    doOnFailed(e);
                    mLoadTask = null;
//                    mNetWorkChange = Astro.getNotifyModule().getNetworkChangedObservable().subscribe(new SimpleSubscriber<Boolean>() {
//                        @Override
//                        public void onNext(Boolean pBoolean) {
//                            if (pBoolean){
//                                loadData();
//                            }
//                        }
//                    });
                }
            });
            mLoadTask.execute();
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        RxUtils.unsubscribe(mNetWorkChange);
//    }

    protected abstract T getDataBackground();
    protected void loadDataBefore(){}
    protected abstract void doOnSuccess(T data);
    protected void doOnFailed(Throwable e){}
}
