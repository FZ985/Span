package com.text.span;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;

/**
 * Create by JFZ
 * date: 2020-06-23 17:12
 **/
public class Image {

    /**
     * 给bitmap 添加一层颜色蒙版
     *
     * @param bitmap
     * @param color
     * @return
     */
    public static Bitmap drawBitmapShadow(Bitmap bitmap, int color) {
        Bitmap outB = bitmap.copy(Bitmap.Config.RGB_565, true);
        Canvas canvas = new Canvas(outB);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawColor(color, PorterDuff.Mode.DST_OUT);
        return outB;
    }

    /**
     * 给drawable 添加一层颜色蒙版
     *
     * @param drawable
     * @param color
     * @return
     */
    public static Drawable drawDrawableShadow(Drawable drawable, int color) {
        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        return drawable;
    }
}
