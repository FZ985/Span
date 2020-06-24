package com.text.span;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import jfz.span.Span;
import jfz.span.span.RoundSpan;

public class MainActivity extends AppCompatActivity {
    TextView main_tv;
    TextView custom_tv;
    TextView tv2;
    ImageView main_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_tv = findViewById(R.id.main_tv);
        custom_tv = findViewById(R.id.custom_tv);
        tv2 = findViewById(R.id.tv2);
        main_image = findViewById(R.id.main_image);

        int[] colors = new int[]{
                Color.argb(100, 255, 0, 0),
                Color.TRANSPARENT,
                Color.argb(100, 0, 0, 255)
        };

        Glide.with(this)
                .asBitmap()
                .load("http://d.hiphotos.baidu.com/zhidao/pic/item/6a63f6246b600c334c3e91cb1e4c510fd9f9a16a.jpg")
//                .transform(new ColorsFilterTransformation(colors, ColorsFilterTransformation.Type.LB_RT), new FitCenter(), new RoundedCorners(30))
//                .transform(new ColorsFilterTransformation(colors, ColorsFilterTransformation.Type.LT_RB), new FitCenter(), new RoundedCorners(30))
                .transform(new ColorsFilterTransformation(colors, ColorsFilterTransformation.Type.L_R), new FitCenter(), new RoundedCorners(30))
//                .transform(new ColorsFilterTransformation(colors), new FitCenter(), new RoundedCorners(30))
//                .transform(new ColorsFilterTransformation(colors, new ColorsFilterTransformation.CustomXYValues() {
//                    @Override
//                    public Shader getShader(int width, int height) {
//                        final int[] GRADIENT_COLORS = new int[]{
//                                Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN, Color.WHITE, Color.RED};
//                        final float[] GRADIENT_POSITONS = new float[]{
//                                0.0f, 0.5f, 0.55f, 0.6f, 0.65f, 1.0f};
//                        SweepGradient shader = new SweepGradient(width / 2, height / 2, GRADIENT_COLORS, null);
//                        return shader;
//                    }
//                }), new FitCenter(), new RoundedCorners(30))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        main_image.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        init();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void init() {
        Span.impl()
                .append(Span.builder("这事不对劲这事不对劲这事不对劲这事不对劲这事不对劲这事不对劲")
                        .textColor(Color.parseColor("#333333")))
                .append(Span.builder("·待发布")
                        .roundSpan(new RoundSpan(Color.parseColor("#50ff0000"),
                                6,
                                Paint.Style.FILL,
                                10,
                                10,
                                8f,
                                8f)
                                .textSize(dip2px(MainActivity.this, 12))))
                .into(main_tv);

        Span.impl()
                .append(Span.builder("这事不对劲这事不对劲这事不对劲这事不对劲这事不对劲这事不对劲")
                        .textColor(Color.parseColor("#333333")))
                .append(Span.builder("·待发布")
                        .roundSpan(new RoundSpan(Color.parseColor("#008577"),
                                6,
                                Paint.Style.STROKE,
                                10,
                                10,
                                8f,
                                8f)
                                .textSize(tv2.getTextSize() - 8)
                                .textColor(Color.parseColor("#008577"))))
                .into(tv2);


        Span.impl()
                .append(Span.builder("哈哈哈"))
                .append(Span.builder("嘿嘿嘿嘿嘿")
                        .backgroundColor(Color.GREEN))
                .append(Span.builder("呵呵呵呵呵呵呵呵")
                        .textColor(Color.RED))
                .append(Span.builder("嘻嘻嘻嘻嘻嘻嘻嘻")
                        .textSize(30))
                .append(Span.builder("哼哼哼哼哼")
                        .textColor(Color.YELLOW)
                        .underLine())
                .append(Span.builder("吼吼吼吼吼吼")
                        .deleteLine())
                .append(Span.builder("加粗加粗加粗")
                        .textStyle(Typeface.BOLD))
                .append(Span.builder("倾斜倾斜倾斜")
                        .textStyle(Typeface.ITALIC))
                .append(Span.builder("加粗倾斜加粗倾斜加粗倾斜")
                        .textStyle(Typeface.BOLD_ITALIC))
                .append(Span.builder("点击点击点击")
                        .textColor(Color.BLUE)
                        .underLine()
                        .click(new Span.OnClickSpanListener() {
                            @Override
                            public void onClick(String text, View widget) {
                                Toast.makeText(MainActivity.this, "子节点点击了:" + text, Toast.LENGTH_SHORT).show();
                            }
                        }))
                .append(Span.builder(" ").addSpans(new ImageSpan(this, R.mipmap.ic_launcher_round, ImageSpan.ALIGN_BASELINE), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE))
                .append(Span.builder("啊啊啊啊啊啊啊"))
                .append(Span.builder("·待发布").roundSpan(new RoundSpan(Color.parseColor("#50ff0000"), 6, Paint.Style.FILL, 10, 10, 8f, 8f)))
                .totalClickListener(new Span.OnClickSpanListener() {
                    @Override
                    public void onClick(String text, View widget) {
                        Toast.makeText(MainActivity.this, "总体点击了:" + text, Toast.LENGTH_SHORT).show();
                    }
                })
                .into(custom_tv);

//        custom_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
