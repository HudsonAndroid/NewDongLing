package com.hudson.donglingmusic.UI.activity.lyricsMake;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.InputView;
import com.hudson.donglingmusic.UI.View.LoadStateView;
import com.hudson.donglingmusic.UI.View.popup.LyricsInputPopupMore;
import com.hudson.donglingmusic.UI.activity.BaseActivity;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncException;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.CommonOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;
import com.hudson.donglingmusic.entity.LyricsResult;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.newlyricsview.lyrics.entity.Lyrics;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

/**
 * Created by Hudson on 2020/3/11.
 */
public class LyricsInputActivity extends BaseActivity implements LyricsInputPopupMore.OnMenuClickListener {
    public static final int REQUEST_CODE = 0x66;
    public static final String KEY_LYRICS = "key_lyrics";
    public static final String LYRICS_DIVIDER = "\n";
    private InputView mInput;
    private IPlayerController mPlayerController;
    private LoadStateView mLoadStateView;
    private LyricsInputPopupMore mMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //调整为状态栏为亮色模式
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else{
            //低版本的话，把状态栏设置为黑色，避免状态栏看不清楚
            StatusBarUtil.setColor(this, Color.BLACK);
        }
    }

    @Override
    protected void initView(ConstraintLayout parent) {
        LayoutInflater.from(this).inflate(R.layout.activity_lyrics_input,parent);
        mLoadStateView = (LoadStateView) parent.findViewById(R.id.lsv_load);
        mInput = (InputView) parent.findViewById(R.id.iv_input);
        parent.findViewById(R.id.iv_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMore == null){
                    mMore = new LyricsInputPopupMore(LyricsInputActivity.this,
                            LyricsInputActivity.this);
                }
                mMore.show(v);
            }
        });
        parent.findViewById(R.id.iv_complete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mInput.getInput();
                if(!TextUtils.isEmpty(input)){
                    Intent data = new Intent();
                    data.putExtra(KEY_LYRICS,input);
                    setResult(RESULT_OK, data);
                    finish();
                    IPlayerController controller = DongLingApplication.getPlayerController();
                    Log.e("hudson2","控制器已经是"+(controller == null));
                }else{
                    ToastUtils.showToast(R.string.common_lyrics_empty);
                }
            }
        });
        findViewById(R.id.v_import_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                importLyrics();
            }
        });
        mPlayerController = DongLingApplication.getPlayerController();
        TextView title = (TextView) parent.findViewById(R.id.tv_title);
        TextView artist = (TextView) parent.findViewById(R.id.tv_artist);
        if(mPlayerController != null){
            MusicEntity curMusic = mPlayerController.getCurMusic();
            if(curMusic != null){
                title.setText(curMusic.getTitle());
                artist.setText(curMusic.getArtist());
            }
        }
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        if(intent != null){
            String lyrics = intent.getStringExtra(KEY_LYRICS);
            if(!TextUtils.isEmpty(lyrics)){
                mInput.setText(lyrics);
            }
        }
    }

    private void importLyrics(){
        mLoadStateView.loading();
        new AsyncTask<String>().doInBackground(new IDoInBackground<String>() {
            @Override
            public String run() throws AsyncException {
                MusicEntity curMusic = mPlayerController.getCurMusic();
                if(curMusic != null){
                    LyricsResult result = LyricsResult.fetchLyrics(curMusic);
                    if(result != null){
                        List<Lyrics> lyrics = result.getLyrics();
                        StringBuilder sb = new StringBuilder();
                        for (Lyrics lyric : lyrics) {
                            sb.append(lyric.getLrcContent()).append(LYRICS_DIVIDER);
                        }
                        return sb.toString();
                    }
                }
                return null;
            }
        }).doOnSuccess(new IDoOnSuccess<String>() {
            @Override
            public void onSuccess(String s) {
                mLoadStateView.loadSuccess();
                if(!TextUtils.isEmpty(s)){
                    mInput.setText(s);
                }else{
                    ToastUtils.showToast(R.string.lyrics_input_failed_empty);
                }
            }
        }).doOnFail(new CommonOnFail(){
            @Override
            protected void handleOnFail(Throwable e) {
                super.handleOnFail(e);
                mLoadStateView.loadFailed();
            }
        }).acceptNullReturn().execute();
    }

    public static void start(Activity from,@Nullable String editContent){
        Intent intent = new Intent(from, LyricsInputActivity.class);
        intent.putExtra(KEY_LYRICS,editContent);
        from.startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    public void onFilterClick() {
        String input = mInput.getInput();
        if(!TextUtils.isEmpty(input)){
            StringBuilder sb = new StringBuilder();
            String[] split = input.split(LYRICS_DIVIDER);
            for (String lyrics : split) {
                if(!TextUtils.isEmpty(lyrics)){
                    sb.append(lyrics).append(LYRICS_DIVIDER);
                }
            }
            mInput.setText(sb.toString());
        }
    }
}
