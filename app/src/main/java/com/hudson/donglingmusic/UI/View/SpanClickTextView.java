package com.hudson.donglingmusic.UI.View;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 文本部分内容需要响应用户点击的textView
 *
 * 使用说明：
 * 本类只是对原生textView的一层包裹，方便
 * 简化使用。因此，制定一些规则，以规范逻辑：
 * 需要响应点击的内容应该以特殊字符标识
 * 边缘；如果没有按照上面要求处理，那么该
 * span将无效；如果包含有多个可响应点击的
 * 内容，应当保证各个标识是唯一的。
 * Created by hudson on 2019/8/19.
 */
public class SpanClickTextView extends TextView {
    private final List<ClickSpan> mClickSpans = new ArrayList<>();
    private onSpanClickListener mOnSpanClickListener;

    public SpanClickTextView(Context context) {
        this(context, null);
    }

    public SpanClickTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpanClickTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Finally,call method {@link #startDivide()} to analyse the text.
     *
     * 注意：避免使用正则表达式中的特殊字符，例如$ ^等，因为String replaceAll方法
     * 会自动检测是否是正则表达式格式.
     * @param uniqueTag 唯一标识
     * @return self
     */
    public SpanClickTextView divideSpan(String uniqueTag){
        mClickSpans.add(new ClickSpan(uniqueTag));
        return this;
    }

    /**
     * Make sure you have called method {@link #divideSpan(String)} to
     * add rules and method {@link #setText(CharSequence)} or {@link #setText(int)}
     * to set the text before you invoke this.
     */
    public void startDivide(){
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

    public class ClickSpan extends ClickableSpan{
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
            ds.setUnderlineText(true);
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
