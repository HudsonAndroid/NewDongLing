package com.hudson.donglingmusic.UI.activity.lyricsMake.makePage;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.service.IPlayerController;

/**
 * Created by Hudson on 2020/3/13.
 */
public class ReadyState implements ILyricsMakeState {
    private LyricsMakeController mController;

    public ReadyState(LyricsMakeController controller) {
        mController = controller;
    }

    private void showTips(){
        ToastUtils.showToast(R.string.lyrics_make_ready_start);
    }

    @Override
    public void onRestart() {
        showTips();
    }

    @Override
    public void onPlayInvoke() {
        IPlayerController controller = DongLingApplication.getPlayerController();
        controller.seekTo(0);
        controller.play();
        mController.setState(new MakingState(mController));
    }

    @Override
    public void onComplete() {
        showTips();
    }

    @Override
    public void onBackward() {
        showTips();
    }

    @Override
    public void onForward() {
        showTips();
    }

    @Override
    public void onPre() {
        showTips();
    }

    @Override
    public void onNext() {
        showTips();
    }
}
