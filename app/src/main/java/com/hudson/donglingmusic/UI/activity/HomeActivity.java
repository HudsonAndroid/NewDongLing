package com.hudson.donglingmusic.UI.activity;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Group;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.View.TabView.TabLayout.HomeFirstPage;
import com.hudson.donglingmusic.common.Utils.CommonUtils;
import com.hudson.donglingmusic.common.Utils.ToastUtils;
import com.hudson.donglingmusic.common.Utils.bitmapUtils.BitmapUtils;
import com.hudson.donglingmusic.common.config.ConfigManager;
import com.hudson.donglingmusic.entity.MusicEntity;
import com.hudson.donglingmusic.global.DongLingApplication;
import com.hudson.donglingmusic.module.skin.manager.OnSkinLoadCompleteListener;
import com.hudson.donglingmusic.module.skin.manager.SkinManager;
import com.hudson.donglingmusic.service.IPlayerController;
import com.hudson.donglingmusic.service.listener.OnMusicChangedListener;
import com.jaeger.library.StatusBarUtil;

public class HomeActivity extends BasePlayActivity
        implements OnMusicChangedListener, OnSkinLoadCompleteListener, View.OnClickListener {
    private TextView mTitle,mDesc;
    private ImageView mImg;
    private HomeFirstPage mHomeFirstPage;
    private Group mBottomGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //调整为状态栏为亮色模式
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }else{
            //低版本的话，把状态栏设置为黑色，避免状态栏看不清楚
            StatusBarUtil.setColor(this, Color.BLACK);
        }
        SkinManager.getInstance().addSkinLoadCompleteListener(this);
    }

    @Override
    protected void initView(ConstraintLayout parent) {
        LayoutInflater.from(this).inflate(R.layout.activity_home, parent);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        CommonUtils.requestPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
        LinearLayout container = findViewById(R.id.ll_container);
        mHomeFirstPage = new HomeFirstPage(this, container);

        View menu = container.findViewById(R.id.iv_menu);
        menu.setVisibility(View.VISIBLE);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(GravityCompat.START)){
                    drawer.closeDrawer(GravityCompat.START);
                }else{
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        mTitle = (TextView) findViewById(R.id.tv_title);
        mDesc = (TextView) findViewById(R.id.tv_desc);
        mImg = (ImageView) findViewById(R.id.iv_img);
        findViewById(R.id.iv_play_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayerController != null){
                    if(mPlayerController.getPlayList().size() > 0){
                        PlayQueueActivity.start(HomeActivity.this);
                    }else{
                        ToastUtils.showToast(R.string.common_play_list_empty);
                    }
                }
            }
        });
        findViewById(R.id.v_controller_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayPageActivity.start(HomeActivity.this);
            }
        });
        mBottomGroup = findViewById(R.id.g_bottom_controller);
        attachMenu(((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0));
    }

    private void attachMenu(View parent){
        parent.findViewById(R.id.tv_skin).setOnClickListener(this);
        parent.findViewById(R.id.tv_display_setting).setOnClickListener(this);
        parent.findViewById(R.id.tv_lyrics_setting).setOnClickListener(this);
        parent.findViewById(R.id.tv_exit_setting).setOnClickListener(this);
    }

    public void onClick(View v){
        int id = v.getId();
        if(id == R.id.tv_skin){
            SkinManager.getInstance().loadSkin(Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+"donglingMusic/"+"buleskin.skin");
        }else if(id == R.id.tv_display_setting){

        }else if(id == R.id.tv_lyrics_setting){
            ConfigManager instance = ConfigManager.getInstance();
            int style = instance.getInt("LyricsStyle", 0);
            if(style == 0){
                instance.putInt("LyricsStyle",1);
            }else{
                instance.putInt("LyricsStyle",0);
            }
        }else if(id == R.id.tv_exit_setting){
            exit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(false);
        }
    }

    @Override
    public void refreshMusicInfo() {
        MusicEntity curMusic = mPlayerController.getCurMusic();
        if(curMusic != null){
            if(mBottomGroup.getVisibility() == View.GONE){
                mBottomGroup.setVisibility(View.VISIBLE);
            }
            mTitle.setText(curMusic.getTitle());
            mDesc.setText(curMusic.getAlbumTitle());
            BitmapUtils.loadMusicPic(curMusic,mImg);
        }
    }

    @Override
    public void onPlayerControllerInitSuccess(IPlayerController controller) {
        super.onPlayerControllerInitSuccess(controller);
        if(controller.getCurMusic() != null){
            mBottomGroup.setVisibility(View.VISIBLE);
        }else{
            mBottomGroup.setVisibility(View.GONE);
        }
        togglePlayPauseImage();
        onMusicInfoChanged();
    }

    @Nullable
    @Override
    protected ImageView getPlayPauseImageView() {
        return (ImageView) findViewById(R.id.iv_play_pause);
    }

    private void exit(){
        if(mPlayerController != null){
            mPlayerController.stop();
        }
        finish();
        DongLingApplication.exit();
    }

    @Override
    protected void onDestroy() {
        mHomeFirstPage.onDestroy();
        super.onDestroy();
    }

    protected int getPlayResId(){
        return R.drawable.icon_notification_play;
    }

    protected int getPauseResId(){
        return R.drawable.icon_notification_pause;
    }

    @Override
    public void onSkinLoadComplete() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //调整为状态栏为亮色模式
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_VISIBLE);
        }else{
            //低版本的话，把状态栏设置为黑色，避免状态栏看不清楚
            StatusBarUtil.setColor(this, Color.WHITE);
        }
    }
}
