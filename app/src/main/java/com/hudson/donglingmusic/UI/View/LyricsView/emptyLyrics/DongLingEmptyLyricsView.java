package com.hudson.donglingmusic.UI.View.LyricsView.emptyLyrics;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import com.hudson.donglingmusic.R;
import com.hudson.donglingmusic.UI.activity.lyricsMake.LyricsMakeActivity;
import com.hudson.donglingmusic.common.Utils.CommonUtils;
import com.hudson.newlyricsview.lyrics.view.EmptyLyricsView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hudson on 2020/3/10.
 */
public class DongLingEmptyLyricsView extends EmptyLyricsView {
    private final List<ClickSpan> mClickSpans = new ArrayList<>();
    private onSpanClickListener mOnSpanClickListener;

    public DongLingEmptyLyricsView(Context context) {
        this(context, null);
    }

    public DongLingEmptyLyricsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DongLingEmptyLyricsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setText(R.string.lyrics_empty_tips);
        divideSpan("@").startDivide();
        setOnSpanClickListener(new onSpanClickListener() {
            @Override
            public void onSpanClick(String uniqueTag, String content) {
                LyricsMakeActivity.start(CommonUtils.contextThemeWrapperToActivity(getContext()));
            }
        });
    }

    private DongLingEmptyLyricsView divideSpan(String uniqueTag){
        mClickSpans.add(new ClickSpan(uniqueTag));
        return this;
    }

    private void startDivide(){
        String source = getText().toString();
        if(TextUtils.isEmpty(source)){
            return ;
        }
        String withoutTag = source;
        for (ClickSpan clickSpan : mClickSpans) {
            withoutTag = withoutTag.replaceAll(clickSpan.mUniqueTag,"");
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(withoutTag);
        int spanStart,spanEnd;
        for (ClickSpan clickSpan : mClickSpans) {
            spanStart = source.indexOf(clickSpan.mUniqueTag);
            spanEnd = source.lastIndexOf(clickSpan.mUniqueTag);
            if(spanEnd > spanStart){
                clickSpan.setFocusText(source.substring(spanStart + 1,spanEnd));
                builder.setSpan(clickSpan,spanStart,spanEnd - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            source = source.replaceAll(clickSpan.mUniqueTag,"");
        }
        setText(builder);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    private class ClickSpan extends ClickableSpan {
        private String mUniqueTag;
        private String mFocusText;

        /**
         * @param uniqueTag 唯一标识，用于区分多个span
         */
        public ClickSpan(String uniqueTag) {
            mUniqueTag = uniqueTag;
        }

        public void setFocusText(String focusText){
            mFocusText = focusText;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(true); //增加下划线
        }

        @Override
        public void onClick(View widget) {
            if(mOnSpanClickListener != null){
                mOnSpanClickListener.onSpanClick(mUniqueTag,mFocusText);
            }
        }
    }

    public void setOnSpanClickListener(onSpanClickListener onSpanClickListener) {
        mOnSpanClickListener = onSpanClickListener;
    }

    public interface onSpanClickListener{
        /**
         * 当span被点击时回调
         * @param uniqueTag 该span的边缘标识
         * @param content 该span的内容
         */
        void onSpanClick(String uniqueTag, String content);
    }
}
