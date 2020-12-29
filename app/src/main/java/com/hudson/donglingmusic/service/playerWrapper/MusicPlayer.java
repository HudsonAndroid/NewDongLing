package com.hudson.donglingmusic.service.playerWrapper;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.danikula.videocache.CacheListener;
import com.danikula.videocache.HttpProxyCacheServer;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncException;
import com.hudson.donglingmusic.common.Utils.asyncUtils.AsyncTask;
import com.hudson.donglingmusic.common.Utils.asyncUtils.CommonOnFail;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoInBackground;
import com.hudson.donglingmusic.common.Utils.asyncUtils.IDoOnSuccess;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.entity.NetMusicInfo;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.service.exception.PlayListEmptyException;
import com.hudson.donglingmusic.service.listener.MusicChangeNotifyHelper;
import com.hudson.donglingmusic.service.listener.OnMusicChangedListener;
import com.hudson.donglingmusic.service.playState.IState;
import com.hudson.donglingmusic.service.playState.PlayStateController;
import com.hudson.donglingplayer.AbsMediaPlayer;
import com.hudson.donglingplayer.SystemPlayer.NativePlayer;
import com.hudson.donglingplayer.listener.PlayerStateListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/3/6.
 */
public class MusicPlayer implements PlayerStateListener, CacheListener,AudioHelper.OnAudioChangeListener {
    private AbsMediaPlayer mPlayer;
    private final List<MusicEntity> mPlayList;
    private String mPlayListUniqueTag;
    private PlayStateController mStateHelper;
    private int mCurIndex = 0;
    private MusicChangeNotifyHelper mNotifyHelper;
    private AudioHelper mAudioHelper;


    public MusicPlayer() {
        mPlayer = new NativePlayer();
        mPlayer = new NativePlayer();
        mPlayer.setStateChangeListener(this);
        mPlayList = new ArrayList<>();
        mStateHelper = new PlayStateController();
        mNotifyHelper = new MusicChangeNotifyHelper();
        mAudioHelper = new AudioHelper();
        mAudioHelper.setOnAudioChangeListener(this);
    }

    public void setPlayList(@NonNull List<MusicEntity> playList,@NonNull String uniqueTag){
        if(!uniqueTag.equals(mPlayListUniqueTag)){
            mPlayList.clear();
            mPlayList.addAll(playList);
            mStateHelper.setSize(mPlayList.size());
            mCurIndex = 0;
            mPlayListUniqueTag = uniqueTag;
        }
    }

    @NonNull
    public List<MusicEntity> getPlayList(){
        return mPlayList;
    }

    public void next(){
        try {
            int nextIndex = mStateHelper.getNextIndex(mCurIndex);
            play(nextIndex);
        }catch (PlayListEmptyException e){
            e.printStackTrace();
            ToastUtils.showToast(e.getMessage());
            stop();
        }catch (RuntimeException e){
            e.printStackTrace();
            stop();
        }
    }

    public void pre(){
        try {
            int preIndex = mStateHelper.getPreIndex(mCurIndex);
            play(preIndex);
        }catch (PlayListEmptyException e){
            e.printStackTrace();
            ToastUtils.showToast(e.getMessage());
            stop();
        }catch (RuntimeException e){
            e.printStackTrace();
            stop();
        }
    }

    public void play(){
        if(mAudioHelper.canPlay()){
            mPlayer.play();
            mNotifyHelper.notifyPlayInvoke();
        }
    }

    public void pause(){
        mPlayer.pause();
        mNotifyHelper.notifyPauseInvoke();
    }

    public void stop(){
        mPlayer.stop();
        mNotifyHelper.notifyStopInvoke();
    }

    public void play(int index){
        if(mAudioHelper.canPlay()){
            int size = mPlayList.size();
            if(index >= 0&& index < size){
                MusicEntity music = mPlayList.get(index);
                mPlayer.reset();
                mCurIndex = index;
//                mNotifyHelper.notifyMusicInfoChanged();
                mStateHelper.setCurIndex(mCurIndex);
                playLocal(music);
                playNet(music);
            }
        }
    }

    private void playLocal(MusicEntity music){
        if(!music.isNetMusic()){
            String path = music.getPath();
            if(!TextUtils.isEmpty(path)){
                mPlayer.init(path);
                mNotifyHelper.notifyMusicBuffering(1.0f);//本地音乐，无缓存
            }else{
                ToastUtils.showToast(R.string.play_source_not_exist);
            }
        }
    }

    private void playNet(final MusicEntity music){
        if(music.isNetMusic()){
            mNotifyHelper.notifyMusicOpening();
            Log.e("hudson","正在打开中");
            new AsyncTask<NetMusicInfo>().doInBackground(new IDoInBackground<NetMusicInfo>() {
                @Override
                public NetMusicInfo run() throws AsyncException {
                    return NetMusicInfo.fetch(music.getSongId());
                }
            }).doOnSuccess(new IDoOnSuccess<NetMusicInfo>() {
                @Override
                public void onSuccess(NetMusicInfo netMusicInfo) {
                    NetMusicInfo.SongUrl songUrl = netMusicInfo.getSongUrl();
                    if(songUrl != null){
                        List<NetMusicInfo.SongUrlItem> songUrls = songUrl.getSongUrlItems();
                        if(songUrls.size() > 0){
                            Log.e("hudson","歌曲信息获取成功，准备播放");
                            final HttpProxyCacheServer proxy = DongLingApplication.getProxy();
                            NetMusicInfo.SongUrlItem songUrlItem = songUrls.get(0);
                            String musicLink = songUrlItem.getFileLink();
                            final String proxyUrl = proxy.getProxyUrl(musicLink);
                            if(proxy.isCached(musicLink)){
                                Log.e("hudson","已经缓冲过了");
                                mNotifyHelper.notifyMusicBuffering(1.0f);
                            }else{
                                proxy.registerCacheListener(MusicPlayer.this,musicLink);
                            }
                            mPlayer.init(proxyUrl);
                        }
                    }
                }
            }).doOnFail(new CommonOnFail(){
                @Override
                protected void handleOnFail(Throwable e) {
                    super.handleOnFail(e);
                }
            }).execute();
        }
    }

    @Override
    public void mediaOpening() {
        mNotifyHelper.notifyMusicOpening();
    }

    @Override
    public void mediaStartReady() {
        // 准备好了，再通知歌曲变更了
        mNotifyHelper.notifyMusicInfoChanged();
        play();
    }

    @Override
    public void mediaBuffering(float percent) {
        mNotifyHelper.notifyMusicBuffering(percent);
    }

    @Override
    public void onEnd() {
        next();
    }

    @Override
    public void onError(String msg) {
        mNotifyHelper.notifyError(msg);
    }

    public int getAudioSessionId(){
        return mPlayer.getAudioSessionId();
    }

    public void setTime(int progress){
        mPlayer.setTime(progress);
        mNotifyHelper.notifyMusicProgressChanged(progress);
    }

    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }

    public boolean isPause(){
        return mPlayer.isPause();
    }

    public MusicEntity getCurMusic(){
        if(mPlayList.size() != 0){
            return mPlayList.get(mCurIndex);
        }
        return null;
    }

    public void setPlayState(IState state){
        state.setSize(mPlayList.size());
        state.setCurPlayIndex(mCurIndex);
        mStateHelper.setPlayState(state);
    }

    public IState getPlayState(){
        return mStateHelper.getPlayState();
    }

    public int switchPlayState(){
        return mStateHelper.switchState();
    }

    public long getTime(){
        return mPlayer.getTime();
    }

    public void addMusicChangedListener(@NonNull OnMusicChangedListener listener){
        mNotifyHelper.addListener(listener);
    }

    public void removeMusicChangedListener(@NonNull OnMusicChangedListener listener){
        mNotifyHelper.removeListener(listener);
    }

    @Override
    public void onCacheAvailable(File cacheFile, String url, int percentsAvailable) {
        Log.e("hudson","请稍后，正在缓冲"+percentsAvailable);
        mNotifyHelper.notifyMusicBuffering(percentsAvailable * 1.0f / 100);
    }

    @Override
    public void resumePlay() {
        play();
    }

    @Override
    public void needPause() {
        pause();
    }
}
