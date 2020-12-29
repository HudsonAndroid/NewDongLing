package com.hudson.donglingmusic.common.Utils.asyncUtils;

import android.support.annotation.NonNull;

public class MergableTask {

    private AsyncTask mPreTask;
    private AsyncTask mAnother;
    private IDoFinally mDoFinally;

    MergableTask(@NonNull AsyncTask pTask) {
        mPreTask = pTask;
        mPreTask.doOnSuccess(new IDoOnSuccess() {
            @Override
            public void onSuccess(Object pO) {
                if (!mAnother.mIsRunning) {
                    mDoFinally.doFinally();
                }
            }
        }).doOnFail(new IDoOnFail() {
            @Override
            public void onFail(Throwable e) {
                if (!mAnother.mIsRunning) {
                    mDoFinally.doFinally();
                }
            }
        });
    }

    public void execute() {
        mPreTask.execute();
        mAnother.execute();
    }

    MergableTask merge(@NonNull AsyncTask pAnother){
        mAnother = pAnother;
        mAnother.doOnSuccess(new IDoOnSuccess() {
            @Override
            public void onSuccess(Object pO) {
                if (!mPreTask.mIsRunning) {
                    mDoFinally.doFinally();
                }
            }
        }).doOnFail(new IDoOnFail() {
            @Override
            public void onFail(Throwable e) {
                if (!mPreTask.mIsRunning) {
                    mDoFinally.doFinally();
                }
            }
        });
        return this;
    }

    public MergableTask doFinally(IDoFinally pDoFinally){
        mDoFinally = pDoFinally;
        return this;
    }
}
