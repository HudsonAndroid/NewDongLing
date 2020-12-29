package com.hudson.donglingmusic.UI.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.LoadStateView;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncException;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.CommonOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;

/**
 * Created by Hudson on 2020/3/3.
 */
public abstract class BaseFetchActivity<T> extends BaseActivity {
    protected T mData;
    private LoadStateView mLoadView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(ConstraintLayout parent) {
        LayoutInflater.from(this).inflate(R.layout.activity_base_fetch,parent);
        mLoadView = (LoadStateView) this.findViewById(R.id.lsv_load);
        initContentView((ConstraintLayout)findViewById(R.id.cl_content));
    }

    protected abstract void initContentView(ConstraintLayout parent);

    protected void fetchData(){
        if(mData == null){
            loadDataBefore();
            new AsyncTask<T>().doInBackground(new IDoInBackground<T>() {
                @Override
                public T run() throws AsyncException {
                    return requestData();
                }
            }).doOnSuccess(new IDoOnSuccess<T>() {
                @Override
                public void onSuccess(T t) {
                    mLoadView.loadSuccess();
                    mData = t;
                    onDataFetchSuccess(t);
                }
            }).doOnFail(new CommonOnFail(){
                @Override
                protected void handleOnFail(Throwable e) {
                    super.handleOnFail(e);
                    mLoadView.loadFailed();
                    onDataFetchFailed(e);
                }
            }).execute();
        }
    }

    protected void disableLoadShow(){
        mLoadView.setDisable(true);
    }

    protected void loadDataBefore(){
        mLoadView.loading();
    }
    protected abstract T requestData();
    protected abstract void onDataFetchSuccess(T data);
    protected void onDataFetchFailed(Throwable e){}
}
