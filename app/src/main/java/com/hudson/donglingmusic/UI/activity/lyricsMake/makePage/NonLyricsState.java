package com.hudson.donglingmusic.UI.activity.lyricsMake.makePage;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.Utils.ToastUtils;

/**
 * Created by Hudson on 2020/3/13.
 */
public class NonLyricsState implements ILyricsMakeState {

    private void showTips(){
        ToastUtils.showToast(R.string.lyrics_make_non_lyrics_tips);
    }

    @Override
    public void onRestart() {
        showTips();
    }

    @Override
    public void onPlayInvoke() {
        showTips();
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
