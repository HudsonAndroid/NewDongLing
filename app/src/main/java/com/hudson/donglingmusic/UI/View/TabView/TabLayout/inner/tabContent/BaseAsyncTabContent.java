package com.hudson.donglingmusic.UI.View.TabView.TabLayout.inner.tabContent;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.LoadStateView;
import com.hudson.donglingmusic.UI.View.TabView.ILoadDataView;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncException;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.CommonOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;

/**
 * Created by Hudson on 2020/2/29.
 */
public abstract class BaseAsyncTabContent<T> extends AbsTabContent implements ILoadDataView {
    protected AsyncTask<T> mLoadTask;
    protected LoadStateView mLoadStateView;
    private SwipeRefreshLayout mRefreshView;
    protected boolean mForceServer = false;
//    private Subscription mNetWorkChange;

    public BaseAsyncTabContent(Context context) {
        super(context);
    }

    @Override
    protected final void initContentView(ViewGroup parent) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page_tab_with_load, parent);
        mLoadStateView = (LoadStateView) root.findViewById(R.id.lsv_load);
        mRefreshView = (SwipeRefreshLayout) root.findViewById(R.id.srl_refresh);
        initContent((ConstraintLayout)root.findViewById(R.id.cl_container));
        mLoadStateView.setDisable(forbidTabLoadView());
        mRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //这里获取数据的逻辑
                mLoadTask = null;
                mForceServer = true;
                loadData();
            }
        });
    }

    protected abstract void initContent(ConstraintLayout parent);

    @Override
    public void loadData() {
        if(mLoadTask == null){//通过作为成员，避免重复加载
            loadDataBefore();
            mLoadTask = new AsyncTask<T>().doInBackground(new IDoInBackground<T>() {
                @Override
                public T run() throws AsyncException {
                    return getDataBackground();
                }
            }).doOnSuccess(new IDoOnSuccess<T>() {
                @Override
                public void onSuccess(T t) {
                    mLoadStateView.loadSuccess();
                    doOnSuccess(t);
                    closeForceUpdate();
                }
            }).doOnFail(new CommonOnFail(){
                @Override
                protected void handleOnFail(Throwable e) {
                    super.handleOnFail(e);
                    doOnFailed(e);
                    closeForceUpdate();
                }
            });
//                    .doOnFail(new CommonOnFail(){
//                @Override
//                protected void handleOnFail(Throwable e) {
//                    doOnFailed(e);
//                    mLoadTask = null;
//                    mNetWorkChange = Astro.getNotifyModule().getNetworkChangedObservable().subscribe(new SimpleSubscriber<Boolean>() {
//                        @Override
//                        public void onNext(Boolean pBoolean) {
//                            if (pBoolean){
//                                loadData();
//                            }
//                        }
//                    });
//                }
//            });
            if(acceptNullResult()){
                mLoadTask.acceptNullReturn();
            }
            mLoadTask.execute();
        }
    }

    public void closeForceUpdate() {
        if (mRefreshView.isRefreshing()) {
            mRefreshView.setRefreshing(false);
            mForceServer = false;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        RxUtils.unsubscribe(mNetWorkChange);
    }

    protected abstract T getDataBackground();
    protected void loadDataBefore(){
        mLoadStateView.loading();
    }
    protected abstract void doOnSuccess(T data);
    protected void doOnFailed(Throwable e){
        mLoadStateView.loadFailed();
    }
    protected boolean acceptNullResult(){
        return false;
    }
    protected boolean forbidTabLoadView(){
        return false;
    }

}
