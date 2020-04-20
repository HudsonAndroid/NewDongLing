package com.hudson.donglingmusic.common.Utils.bitmapUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * Created by Hudson on 2020/4/19.
 * 效果同于 {@link DarkTransformation}
 */
public class DarkBitmap extends BitmapTransformation {
    private boolean needDark;

    public DarkBitmap(Context context) {
        this(context,true);
    }

    public DarkBitmap(BitmapPool bitmapPool) {
        super(bitmapPool);
        needDark = true;
    }

    public DarkBitmap(Context context,boolean needDark){
        super(context);
        this.needDark = needDark;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        if(!needDark){
            return toTransform;
        }
        //注意使用传入的bitmap的宽高
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        final Bitmap toReuse = pool.get(width, height, Bitmap.Config.ARGB_8888);
        final Bitmap result;
        if (toReuse != null) {
            result = toReuse;
        } else {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        LinearGradient gradient = new LinearGradient(0, 0, 0, outHeight,
                new int[]{0x66000000,0x66000000},new float[]{0,1.0f},
                Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        canvas.drawBitmap(toTransform,0,0,null);
        canvas.drawRect(new Rect(0, 0, width, height), paint);
        return result;
    }

    @Override
    public String getId() {
        return "DarkBitmap"+needDark;
    }
}
