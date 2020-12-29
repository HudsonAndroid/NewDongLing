package com.hudson.donglingmusic.UI.activity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.global.ControllerInitSuccessListener;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.listener.OnMusicChangedListener;

/**
 * Created by Hudson on 2019/11/10.
 */
public abstract class BasePlayActivity extends BaseActivity implements OnMusicChangedListener, ControllerInitSuccessListener {
    protected IPlayerController mPlayerController;

    @Override
    protected final void initData() {
        super.initData();
        DongLingApplication.addPlayerControllerInitListener(this);
    }

    @Override
    protected void onDestroy() {
        if(mPlayerController != null){
            mPlayerController.removeMusicChangedListener(this);
        }
        DongLingApplication.removePlayerControllerInitListener(this);
        super.onDestroy();
    }

    private void attachPlayPauseView(@NonNull final ImageView imageView){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayState(imageView);
            }
        });
        togglePlayPauseImage();
    }

    protected void togglePlayState(@NonNull ImageView imageView){
        if(mPlayerController != null){
            if(mPlayerController.isPlaying()){
                mPlayerController.pause();
                imageView.setImageResource(getPlayResId());
            }else if(mPlayerController.isPause()){
                mPlayerController.play();
                imageView.setImageResource(getPauseResId());
            }else{
                ToastUtils.showToast(R.string.common_play_invalid);
            }
        }
    }

    @Nullable
    protected ImageView getPlayPauseImageView(){
        return null;
    }

    protected int getPlayResId(){
        return R.drawable.controller_img_play;
    }

    protected int getPauseResId(){
        return R.drawable.controller_img_pause;
    }

    protected void togglePlayPauseImage(){
        ImageView imageView = getPlayPauseImageView();
        if(imageView != null && mPlayerController != null){
            if(mPlayerController.isPlaying()){
                imageView.setImageResource(getPauseResId());
            }else if(mPlayerController.isPause()){
                imageView.setImageResource(getPlayResId());
            }
        }
    }

    protected void playNext(){
        if(mPlayerController != null){
            mPlayerController.next();
        }
    }

    protected void playPre(){
        if(mPlayerController != null){
            mPlayerController.pre();
        }
    }

    @Override
    public final void onMusicInfoChanged() {
        if(mPlayerController != null){
            refreshMusicInfo();
        }
    }

    @Override
    public void onPlayInvoke() {
        togglePlayPauseImage();
    }

    @Override
    public void onPauseInvoke() {
        togglePlayPauseImage();
    }

    @Override
    public void onStopInvoke() {
        togglePlayPauseImage();
    }

    @Override
    public void onMusicOpening() {

    }

    @Override
    public void onMusicBuffering(float percentage) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onMusicProgressChanged(long time) {

    }

    protected void refreshMusicInfo(){}

    @Override
    public void onPlayerControllerInitSuccess(IPlayerController controller) {
        mPlayerController = controller;
        if(mPlayerController != null){
            mPlayerController.addMusicChangedListener(this);
        }
        ImageView playPauseImageView = getPlayPauseImageView();
        if(playPauseImageView != null){
            attachPlayPauseView(playPauseImageView);
        }
    }
}
