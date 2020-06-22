package com.text.span;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import jfz.span.Span;
import jfz.span.span.RoundSpan;

public class MainActivity extends AppCompatActivity {
    TextView main_tv;
    TextView custom_tv;
    TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_tv = findViewById(R.id.main_tv);
        custom_tv = findViewById(R.id.custom_tv);
        tv2 = findViewById(R.id.tv2);
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
