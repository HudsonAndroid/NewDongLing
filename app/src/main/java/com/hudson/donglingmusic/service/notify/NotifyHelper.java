package com.hudson.donglingmusic.service.notify;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.RemoteViews;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.activity.PlayPageActivity;
import com.hudson.donglingmusic.common.Utils.CommonUtils;
import com.hudson.donglingmusic.common.Utils.StringUtils;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.common.Utils.bitmapUtils.BitmapUtils;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.listener.OnMusicChangedListener;

/**
 * Created by Hudson on 2020/3/18.
 */
public class NotifyHelper implements OnMusicChangedListener {
    private static final String BROADCAST_PLAY_PAUSE = "dongLing_music_play_pause";
    private static final String BROADCAST_NEXT = "dongLing_music_next";
    private static final int NOTIFICATION_ID = 0x99;
    private static final int TITLE_MAX_LENGTH = 18;
    private static final int ARTIST_MAX_LENGTH = 25;
    private static final int NOTIFICATION_IMG_DIMEN = 50;//dp
    private String mChannelId;
    private NotificationManager mNotificationManager;
    private Service mService;
    private Notification mNotification;
    private RemoteReceiver mReceiver;
    private final int mNotificationImgDimen;

    public NotifyHelper(Service context) {
        mService = context;
        mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Resources resources = context.getResources();
            mChannelId = resources.getString(R.string.notify_id_music_play);
            NotificationChannel channel = new NotificationChannel(
                    mChannelId,
                    resources.getString(R.string.notify_name_music_play),
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }
        mNotificationImgDimen = CommonUtils.dp2px(NOTIFICATION_IMG_DIMEN);
    }

    private void registerBroadCast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BROADCAST_NEXT);
        filter.addAction(BROADCAST_PLAY_PAUSE);
        mReceiver = new RemoteReceiver();
        mService.registerReceiver(mReceiver, filter);
    }

    public void startForeground() {
        mNotification = createNotification();
        mService.startForeground(NOTIFICATION_ID, mNotification);
        registerBroadCast();
    }

    private Notification createNotification() {
        Notification.Builder builder = new Notification.Builder(mService)
                .setSmallIcon(R.drawable.icon_player)
                .setContentIntent(PendingIntent.getActivity(mService,
                        0, new Intent(mService, PlayPageActivity.class),
                        PendingIntent.FLAG_UPDATE_CURRENT));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(mChannelId);
        }
        RemoteViews contentView = new RemoteViews(mService.getPackageName(), R.layout.notification_music_play);
        contentView.setTextViewText(R.id.tv_title, mService.getText(R.string.app_name));
        contentView.setTextViewText(R.id.tv_artist, mService.getText(R.string.notify_no_music_playing));
        Intent playPauseIntent = new Intent();
        playPauseIntent.setAction(BROADCAST_PLAY_PAUSE);
        contentView.setOnClickPendingIntent(R.id.iv_play_pause,
                PendingIntent.getBroadcast(mService, 0,
                        playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        Intent nextIntent = new Intent();
        nextIntent.setAction(BROADCAST_NEXT);
        contentView.setOnClickPendingIntent(R.id.iv_next,
                PendingIntent.getBroadcast(mService, 0,
                        nextIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        builder.setContent(contentView);
        builder.setWhen(System.currentTimeMillis());
        return builder.build();
    }

    @Override
    public void onMusicInfoChanged() {
        changeNewMusicInfo();
    }

    private void changeNewMusicInfo() {
        final IPlayerController controller = DongLingApplication.getPlayerController();

        if (controller != null && controller.getCurMusic() != null) {
            MusicEntity curMusic = controller.getCurMusic();
            BitmapUtils.loadMusicPicBitmapType(curMusic, mService, mNotificationImgDimen, mNotificationImgDimen,
                    new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            updateNotification(resource);
                        }

                        //需要重写该方法，因为加载失败的情况下，SimpleTarget是不会有任何操作的，因为她不知道怎么处理
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            updateNotification(null);
                        }
                    });
        }
    }

    private void updateNotification(Bitmap bitmap) {
        IPlayerController controller = DongLingApplication.getPlayerController();
        if (controller != null && controller.getCurMusic() != null) {
            MusicEntity curMusic = controller.getCurMusic();
            mNotification.contentView.setTextViewText(R.id.tv_title, StringUtils.cutStr(curMusic.getTitle(), TITLE_MAX_LENGTH));
            mNotification.contentView.setTextViewText(R.id.tv_artist, StringUtils.cutStr(curMusic.getArtist(), ARTIST_MAX_LENGTH));
            if (bitmap == null) {
                mNotification.contentView.setImageViewResource(R.id.iv_music_pic, R.drawable.icon_player);
            } else {
                mNotification.contentView.setImageViewBitmap(R.id.iv_music_pic, bitmap);
            }
            mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        }
    }

    private void updatePlayPause() {
        IPlayerController controller = DongLingApplication.getPlayerController();
        if (controller != null) {
            if (controller.isPlaying()) {
                mNotification.contentView.setImageViewResource(R.id.iv_play_pause, R.drawable.icon_notification_pause);
            } else {
                mNotification.contentView.setImageViewResource(R.id.iv_play_pause, R.drawable.icon_notification_play);
            }
            mNotificationManager.notify(NOTIFICATION_ID, mNotification);
        }
    }

    @Override
    public void onPlayInvoke() {
        updatePlayPause();
    }

    @Override
    public void onPauseInvoke() {
        updatePlayPause();
    }

    @Override
    public void onStopInvoke() {

    }

    @Override
    public void onMusicOpening() {

    }

    @Override
    public void onMusicBuffering(float percentage) {

    }

    @Override
    public void onMusicProgressChanged(long time) {

    }

    @Override
    public void onError(String msg) {

    }

    public void onDestroy() {
        mNotificationManager.cancel(NOTIFICATION_ID);
        mService.unregisterReceiver(mReceiver);
    }

    private static class RemoteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BROADCAST_NEXT.equals(action)) {
                IPlayerController controller = DongLingApplication.getPlayerController();
                if (controller != null && (controller.isPlaying() || controller.isPause())) {
                    controller.next();
                } else {
                    ToastUtils.showToast(R.string.common_play_invalid);
                }
            } else if (BROADCAST_PLAY_PAUSE.equals(action)) {
                IPlayerController controller = DongLingApplication.getPlayerController();
                if (controller != null) {
                    if (controller.isPause()) {
                        controller.play();
                    } else if (controller.isPlaying()) {
                        controller.pause();
                    } else {
                        ToastUtils.showToast(R.string.common_play_invalid);
                    }
                }
            }
        }
    }
}
