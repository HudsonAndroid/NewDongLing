package com.hudson.donglingmusic.UI.View.ViewPagerWithIndicator.ViewPager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;


/**
 * auto timely change ViewPager
 * Created by Hudson on 2019/5/24.
 */
public class AutoSwitchViewPager extends CanIndicateViewPager {
    private static final long DURATION_SWITCH = 3000;//switch page duration
    private static final int MSG_SWITCH = 0x99;
    private long mSwitchDuration;
    private SwitchHandler mHandler;

    public AutoSwitchViewPager(Context context) {
        this(context,null);
    }

    public AutoSwitchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSwitchDuration = DURATION_SWITCH;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if(mHandler == null){
            mHandler = new SwitchHandler(this,mSwitchDuration);
        }
        mHandler.removeMessages(MSG_SWITCH);
        mHandler.sendEmptyMessageDelayed(MSG_SWITCH,mSwitchDuration);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mHandler.removeMessages(MSG_SWITCH);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mHandler.sendEmptyMessageDelayed(MSG_SWITCH,mSwitchDuration);
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(MSG_SWITCH);
    }

    private static class SwitchHandler extends Handler{
        private final WeakReference<ViewPager> mViewPagerRef;
        private final long mSwitchDuration;

        SwitchHandler(@NonNull ViewPager autoSwitchViewPager,long switchDuration){
            mViewPagerRef = new WeakReference<>(autoSwitchViewPager);
            mSwitchDuration = switchDuration;
        }

        @Override
        public void handleMessage(Message msg) {
            ViewPager autoSwitchViewPager = mViewPagerRef.get();
            if(autoSwitchViewPager != null){
                if(msg.what == MSG_SWITCH){
                    PagerAdapter adapter = autoSwitchViewPager.getAdapter();
                    if(adapter != null){
                        int count = adapter.getCount();
                        int currentItem = autoSwitchViewPager.getCurrentItem();
                        autoSwitchViewPager.setCurrentItem((currentItem + 1) % count,true);
                        sendEmptyMessageDelayed(MSG_SWITCH,mSwitchDuration);
                    }
                }
            }
            super.handleMessage(msg);
        }
    }

    public long getSwitchDuration() {
        return mSwitchDuration;
    }

    public void setSwitchDuration(long switchDuration) {
        mSwitchDuration = switchDuration;
    }
}
