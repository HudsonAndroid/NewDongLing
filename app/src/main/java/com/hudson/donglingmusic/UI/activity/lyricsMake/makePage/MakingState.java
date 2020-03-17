package com.hudson.donglingmusic.UI.activity.lyricsMake.makePage;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.activity.lyricsMake.LyricsMakeActivity;
import com.hudson.donglingmusic.UI.activity.lyricsMake.maker.ILyricsMaker;
import com.hudson.donglingmusic.UI.activity.lyricsMake.maker.InvalidTimeLyricsException;
import com.hudson.donglingmusic.UI.activity.lyricsMake.maker.LyricsMaker;
import com.hudson.donglingmusic.UI.adapter.LyricsMakeAdapter;
import com.hudson.donglingmusic.common.Utils.CommonUtils;
import com.hudson.donglingmusic.common.Utils.MusicUtils.LocalMusicUtils;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncException;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;
import com.hudson.donglingmusic.customClass.ConfirmDialog;
import com.hudson.donglingmusic.customClass.LoadingDialog;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.musicController.MusicController;

/**
 * Created by Hudson on 2020/3/13.
 */
public class MakingState implements ILyricsMakeState {
    private static final int ADJUST_PROGRESS = 1000;
    private IPlayerController mPlayerController;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private LyricsMakeAdapter mAdapter;
    private int mCurPosition = -1;
    private int mLastHalfHeight = -1;
    private ILyricsMaker mLyricsMaker;

    public MakingState(LyricsMakeController controller) {
        mRecyclerView = controller.getRecyclerView();
        mLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        mAdapter = (LyricsMakeAdapter) mRecyclerView.getAdapter();
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mCurPosition += mAdapter.getHeaderOffset();
            }
        });
        mPlayerController = MusicController.getController();
        mLyricsMaker = new LyricsMaker();
    }

    public void applyLyricsModify(){
        int offset = mAdapter.getHeaderOffset();
        for (int i = offset; i <= mCurPosition; i++) {
            mLyricsMaker.modifyLyrics(i - offset,mAdapter.getLyrics(i));
        }
    }

    @Override
    public void onRestart() {
        mPlayerController.pause();
        mPlayerController.seekTo(0);
        mCurPosition = mAdapter.getHeaderOffset() - 1;
        mAdapter.updateCompletePosition(mCurPosition);
        mRecyclerView.scrollTo(0,0);
        mLyricsMaker.onReset();
    }

    @Override
    public void onPlayInvoke() {
        if(mPlayerController.isPlaying()){
            mPlayerController.pause();
        }else{
            mPlayerController.play();
        }
    }

    @Override
    public void onComplete() {
        mCurPosition ++;
        saveLyrics();
    }

    @Override
    public void onBackward() {
        int progress = mPlayerController.getCurTime() - ADJUST_PROGRESS;
        mPlayerController.seekTo(Math.max(progress, 0));
    }

    @Override
    public void onForward() {
        int progress = mPlayerController.getCurTime() + ADJUST_PROGRESS;
        MusicEntity curMusic = mPlayerController.getCurMusic();
        if(curMusic!= null){
            mPlayerController.seekTo((int) Math.min(progress,curMusic.getDuration()));
        }
    }

    @Override
    public void onPre() {
        if(mCurPosition > mAdapter.getHeaderOffset()-1){
            mCurPosition --;
            View targetView = mLayoutManager.findViewByPosition(mCurPosition);
            if(targetView != null){
                if(mLastHalfHeight == -1){
                    mRecyclerView.smoothScrollBy(0,-targetView.getHeight());
                }else{
                    mRecyclerView.smoothScrollBy(0,-targetView.getHeight()/2 - mLastHalfHeight);
                    mLastHalfHeight = targetView.getHeight()/2;
                }
                mAdapter.updateCompletePosition(mCurPosition);
                mLyricsMaker.onLast();
            }
        }
    }

    @Override
    public void onNext() {
        mCurPosition ++;
        View targetView = mLayoutManager.findViewByPosition(mCurPosition);
        if(targetView != null && !mAdapter.isEnd(mCurPosition)){
            try{
                mLyricsMaker.onNext(mPlayerController.getCurTime(),mAdapter.getLyrics(mCurPosition));
                if(mLastHalfHeight == -1){
                    mRecyclerView.smoothScrollBy(0,targetView.getHeight());
                }else{
                    mRecyclerView.smoothScrollBy(0,targetView.getHeight()/2 + mLastHalfHeight);
                    mLastHalfHeight = targetView.getHeight()/2;
                }
                mAdapter.updateCompletePosition(mCurPosition);
            }catch (InvalidTimeLyricsException e){
                mCurPosition --;
                ToastUtils.showToast(e.getMessage());
            }
        }else{
            saveLyrics();
        }
    }

    private void saveLyrics(){
        if(mAdapter.isEnd(mCurPosition)){
            save();
        }else{
            final ConfirmDialog dialog = new ConfirmDialog(mRecyclerView.getContext());
            dialog.setTips(R.string.lyrics_make_save_not_end);
            dialog.setOkClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    save();//强制保存
                }
            });
            dialog.setCancelClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    mCurPosition --;
                }
            });
            dialog.show();
        }
    }

    private void save() {
        final MusicEntity curMusic = mPlayerController.getCurMusic();
        if(curMusic != null){
            final LoadingDialog loading = new LoadingDialog(mRecyclerView.getContext());
            loading.show();
            new AsyncTask<Boolean>().doInBackground(new IDoInBackground<Boolean>() {
                @Override
                public Boolean run() throws AsyncException {
                    return mLyricsMaker.save(LocalMusicUtils.getMusicLocalLyricsFilePath(curMusic));
                }
            }).doOnSuccess(new IDoOnSuccess<Boolean>() {
                @Override
                public void onSuccess(Boolean success) {
                    loading.dismiss();
                    if(success){
                        ToastUtils.showToast(R.string.common_save_success);
                        Activity activity = CommonUtils.contextThemeWrapperToActivity(mRecyclerView.getContext());
                        if(activity instanceof LyricsMakeActivity){
                            activity.setResult(Activity.RESULT_OK);
                            activity.finish();
                        }
                    }else{
                        ToastUtils.showToast(R.string.common_save_failed);
                    }
                    mCurPosition --;
                }
            }).doOnFail(new IDoOnFail() {
                @Override
                public void onFail(Throwable e) {
                    e.printStackTrace();
                    loading.dismiss();
                    ToastUtils.showToast(R.string.common_save_failed);
                    mCurPosition --;
                }
            }).execute();
        }
    }
}
