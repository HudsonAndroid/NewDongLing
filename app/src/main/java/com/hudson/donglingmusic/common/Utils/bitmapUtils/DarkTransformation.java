package com.hudson.donglingmusic.common.Utils.bitmapUtils;

/**
 * 效果同于 {@link com.hudson.donglingmusic.common.Utils.bitmapUtils.DarkBitmap}
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class DarkTransformation implements Transformation<Bitmap> {
    private BitmapPool mBitmapPool;
    private boolean needDark;

    public DarkTransformation(Context context) {
        this(Glide.get(context).getBitmapPool(),true);
    }

    public DarkTransformation(Context context,boolean needDark) {
        this(Glide.get(context).getBitmapPool(),needDark);
    }

    public DarkTransformation(BitmapPool pool,boolean needDark) {
        mBitmapPool = pool;
        this.needDark = needDark;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        if(!needDark){
            return resource;
        }
        Bitmap source = resource.get();

        //注意使用传入的bitmap的宽高
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        LinearGradient gradient = new LinearGradient(0, 0, 0, height,
                new int[]{0x66000000,0x66000000},new float[]{0,1.0f},
                Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        canvas.drawBitmap(source,0,0,null);
        canvas.drawRect(new Rect(0, 0, width, height), paint);

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override public String getId() {
        return "BlurTransformation(radius=" + needDark + ")";
    }
}

