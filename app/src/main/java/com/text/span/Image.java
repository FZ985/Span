package com.text.span;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Create by JFZ
 * date: 2020-06-23 17:12
 **/
public class Image {

    public static Bitmap drawBitmapShadow(Bitmap bitmap, int color) {
        Bitmap outB = bitmap.copy(Bitmap.Config.RGB_565, true);
        Canvas canvas = new Canvas(outB);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawColor(color);
        return outB;
    }
}
