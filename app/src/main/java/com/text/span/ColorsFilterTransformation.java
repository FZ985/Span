package com.text.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * Create by JFZ
 * date: 2020-06-23 18:17
 **/
public class ColorsFilterTransformation implements Transformation<Bitmap> {

    private static final int VERSION = 1;
    private static final String ID = "jp.wasabeef.glide.transformations.ColorsFilterTransformation." + VERSION;

    private int[] colors;

    public enum Type {
        T_B, LT_RB, L_R, LB_RT
    }

    private Type type = Type.T_B;

    public ColorsFilterTransformation(int[] colors) {
        this(colors, Type.T_B);
    }

    public ColorsFilterTransformation(int[] colors, Type type) {
        this.colors = colors;
        this.type = type;
    }

    @NonNull
    @Override
    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
        if (!Util.isValidDimensions(outWidth, outHeight)) {
            throw new IllegalArgumentException(
                    "Cannot apply transformation on width: " + outWidth + " or height: " + outHeight
                            + " less than or equal to zero and not Target.SIZE_ORIGINAL");
        }
        BitmapPool bitmapPool = Glide.get(context).getBitmapPool();
        Bitmap toTransform = resource.get();
        int targetWidth = outWidth == Target.SIZE_ORIGINAL ? toTransform.getWidth() : outWidth;
        int targetHeight = outHeight == Target.SIZE_ORIGINAL ? toTransform.getHeight() : outHeight;
        System.out.println("transform_checkWidth:" + (outWidth == Target.SIZE_ORIGINAL));
        System.out.println("transform_checkHeight:" + (outHeight == Target.SIZE_ORIGINAL));
        System.out.println("transform_transformWidth:" + toTransform.getWidth());
        System.out.println("transform_transformHeight:" + toTransform.getHeight());
        System.out.println("transform_outWidth:" + outWidth);
        System.out.println("transform_outHeight:" + outHeight);
        Bitmap transformed = transform(context.getApplicationContext(), bitmapPool, toTransform, targetWidth, targetHeight);
        final Resource<Bitmap> result;
        if (toTransform.equals(transformed)) {
            result = resource;
        } else {
            result = BitmapResource.obtain(transformed, bitmapPool);
        }
        return result;
    }

    protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                               @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();

        Bitmap.Config config =
                toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = pool.get(width, height, config);

        bitmap.setDensity(toTransform.getDensity());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));

        canvas.drawBitmap(toTransform, 0, 0, paint);

        LinearGradient shader = null;
        switch (type) {
            default:
            case T_B:
                shader = new LinearGradient(width / 2, 0, width / 2, height, colors, null, Shader.TileMode.CLAMP);
                break;
            case L_R:
                shader = new LinearGradient(0, height / 2, width, height / 2, colors, null, Shader.TileMode.CLAMP);
                break;
            case LB_RT:
                shader = new LinearGradient(0, height, width, 0, colors, null, Shader.TileMode.CLAMP);
                break;
            case LT_RB:
                shader = new LinearGradient(0, 0, width, height, colors, null, Shader.TileMode.CLAMP);
                break;
        }
        paint.setShader(shader);
        canvas.drawRect(0, 0, width, height, paint);
        return bitmap;
    }

    @Override
    public String toString() {
        return "ColorsFilterTransformation(color=" + colors + ")";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ColorsFilterTransformation && ((ColorsFilterTransformation) o).colors == colors;
    }

//    @Override
//    public int hashCode() {
//        return ID.hashCode() + colors[0] * 10;
//    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID + colors).getBytes(CHARSET));
    }
}
