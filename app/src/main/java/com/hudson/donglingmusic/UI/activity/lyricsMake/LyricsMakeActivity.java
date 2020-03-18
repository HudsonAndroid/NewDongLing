package com.hudson.donglingmusic.UI.activity.lyricsMake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.GuideLayout.focusEntity.FocusEntity;
import com.hudson.donglingmusic.UI.View.GuideLayout.focusEntity.FocusViewEntity;
import com.hudson.donglingmusic.UI.View.GuideLayout.layout.AppGuideLayout;
import com.hudson.donglingmusic.UI.View.GuideLayout.step.lyricsMake.LyricsMakeStepFive;
import com.hudson.donglingmusic.UI.View.GuideLayout.step.lyricsMake.LyricsMakeStepFour;
import com.hudson.donglingmusic.UI.View.GuideLayout.step.lyricsMake.LyricsMakeStepOne;
import com.hudson.donglingmusic.UI.View.GuideLayout.step.lyricsMake.LyricsMakeStepSix;
import com.hudson.donglingmusic.UI.View.GuideLayout.step.lyricsMake.LyricsMakeStepThree;
import com.hudson.donglingmusic.UI.View.GuideLayout.step.lyricsMake.LyricsMakeStepTwo;
import com.hudson.donglingmusic.UI.View.PlayProgressView.IProgressView;
import com.hudson.donglingmusic.UI.View.PlayProgressView.PlayProgressManager;
import com.hudson.donglingmusic.UI.View.SpanClickTextView;
import com.hudson.donglingmusic.UI.activity.BaseNonBindActivity;
import com.hudson.donglingmusic.UI.activity.PlayPageActivity;
import com.hudson.donglingmusic.UI.activity.lyricsMake.makePage.LyricsMakeController;
import com.hudson.donglingmusic.UI.activity.lyricsMake.makePage.ReadyState;
import com.hudson.donglingmusic.UI.adapter.LyricsMakeAdapter;
import com.hudson.donglingmusic.UI.recyclerview.TopLinearLayoutManager;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.common.config.ConfigManager;
import com.hudson.donglingmusic.service.playState.IState;
import com.hudson.donglingmusic.service.playState.LoopOneState;

import java.util.Arrays;

/**
 * Created by Hudson on 2020/3/10.
 */
public class LyricsMakeActivity extends BaseNonBindActivity {
    private static final String KEY_GUIDE_MAKE_LYRICS = "guide_lyrics_make";
    private IProgressView mProgressView;
    private SpanClickTextView mCreateLyrics;
    private ImageView mPlay;
    private LyricsMakeController mLyricsMaker;
    private LyricsMakeAdapter mAdapter;
    private IState mPrePlayState;
    private View mEidt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(ConstraintLayout parent) {
        LayoutInflater.from(this).inflate(R.layout.activity_lyrics_make,parent);
        mProgressView = (IProgressView) parent.findViewById(R.id.ppsb_seek);
        mEidt = parent.findViewById(R.id.iv_edit);
        mEidt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LyricsInputActivity.start(LyricsMakeActivity.this,mAdapter.getAllLyrics());
            }
        });
        PlayProgressManager.getInstance().addProgressView(mProgressView);
        final RecyclerView recyclerView = (RecyclerView) parent.findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new TopLinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                mAdapter = new LyricsMakeAdapter(recyclerView.getHeight() / 2);
                recyclerView.setAdapter(mAdapter);
            }
        });
        mLyricsMaker = new LyricsMakeController(recyclerView);
        mCreateLyrics = (SpanClickTextView) parent.findViewById(R.id.tv_filter_lyrics);
        mCreateLyrics.setText(R.string.lyrics_make_go_to_create);
        mCreateLyrics.divideSpan("@").startDivide();
        mCreateLyrics.setOnSpanClickListener(new SpanClickTextView.onSpanClickListener() {
            @Override
            public void onSpanClick(String uniqueTag, String content) {
                LyricsInputActivity.start(LyricsMakeActivity.this,null);
            }
        });
        mPlay = (ImageView) parent.findViewById(R.id.iv_play);
        findViewById(R.id.iv_backward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLyricsMaker.onBackward();
            }
        });
        findViewById(R.id.iv_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLyricsMaker.onForward();
            }
        });
        findViewById(R.id.iv_restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLyricsMaker.onRestart();
            }
        });
        findViewById(R.id.iv_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLyricsMaker.onComplete();
            }
        });
        findViewById(R.id.iv_pre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLyricsMaker.onPre();
            }
        });
        findViewById(R.id.iv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLyricsMaker.onNext();
            }
        });
    }

    @Override
    protected void prepare() {
        super.prepare();
        if(mPlayerController == null || mPlayerController.getCurMusic() == null){
            ToastUtils.showToast(R.string.common_music_invalid);
            finish();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLyricsMaker.onPlayInvoke();
            }
        });
        if(!ConfigManager.getInstance().getBoolean(KEY_GUIDE_MAKE_LYRICS,false)){
            mCreateLyrics.post(new Runnable() {
                @Override
                public void run() {
                    new AppGuideLayout(LyricsMakeActivity.this)
                            .addStep(new LyricsMakeStepOne()
                                    .addFocusEntity(new FocusEntity(mCreateLyrics)))
                            .addStep(new LyricsMakeStepTwo()
                                    .addFocusEntity(new FocusViewEntity(mPlay)))
                            .addStep(new LyricsMakeStepThree()
                                    .addFocusEntity(new FocusEntity(findViewById(R.id.iv_pre)))
                                    .addFocusEntity(new FocusEntity(findViewById(R.id.iv_next))))
                            .addStep(new LyricsMakeStepFour()
                                    .addFocusEntity(new FocusEntity(findViewById(R.id.iv_backward)))
                                    .addFocusEntity(new FocusEntity(findViewById(R.id.iv_forward))))
                            .addStep(new LyricsMakeStepFive()
                                    .addFocusEntity(new FocusViewEntity(findViewById(R.id.iv_restart))))
                            .addStep(new LyricsMakeStepSix()
                                    .addFocusEntity(new FocusViewEntity(findViewById(R.id.iv_complete))))
                            .start();
                    ConfigManager.getInstance().putBoolean(KEY_GUIDE_MAKE_LYRICS,true);
                }
            });
        }
        mPrePlayState = mPlayerController.getPlayState();
        mPlayerController.setPlayState(new LoopOneState());
        mPlayerController.pause();
    }

    @Nullable
    @Override
    protected ImageView getPlayPauseImageView() {
        return mPlay;
    }

    @Override
    protected int getPauseResId() {
        return R.drawable.lyrics_make_pause;
    }

    @Override
    protected int getPlayResId() {
        return R.drawable.lyrics_make_play;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == LyricsInputActivity.REQUEST_CODE && data != null){
            String lyrics = data.getStringExtra(LyricsInputActivity.KEY_LYRICS);
            if(!TextUtils.isEmpty(lyrics)){
                String[] split = lyrics.split("\n");
                if(split.length > 0){
                    mCreateLyrics.setVisibility(View.GONE);
                    mAdapter.refreshList(Arrays.asList(split));
                    if(!mLyricsMaker.isMaking()){
                        mLyricsMaker.setState(new ReadyState(mLyricsMaker));
                    }else{
                        mLyricsMaker.applyLyricsModify();
                    }
                    mEidt.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public static void start(Activity from){
        from.startActivityForResult(new Intent(from,LyricsMakeActivity.class),
                PlayPageActivity.REQUEST_MAKE_LYRICS_CODE);
    }

    @Override
    protected void onDestroy() {
        mPlayerController.setPlayState(mPrePlayState);
        super.onDestroy();
        PlayProgressManager.getInstance().removeProgressView(mProgressView);
    }
}
