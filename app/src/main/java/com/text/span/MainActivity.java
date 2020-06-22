package com.text.span;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
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
                                .textSize(main_tv.getTextSize() - 8)))
                .into(main_tv);


        Span.impl()
                .append(Span.builder("这事不对劲这事不对劲这事不对劲这事不对劲这事不对劲这事不对劲")
                        .textColor(Color.parseColor("#333333")))
                .append(Span.builder("·待发布")
                        .roundSpan(new RoundSpan(Color.parseColor("#50ff0000"),
                                6,
                                Paint.Style.STROKE,
                                10,
                                10,
                                8f,
                                8f)
                                .textSize(tv2.getTextSize() - 8)))
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
                .append(Span.builder("点点点点")
                        .click(new Span.OnClickSpanListener() {
                            @Override
                            public void onClick(String text, View widget) {
                                Toast.makeText(MainActivity.this, "点击了:" + text, Toast.LENGTH_SHORT).show();
                            }
                        }))
                .append(Span.builder("啊啊啊啊啊啊啊"))
                .append(Span.builder("·待发布").roundSpan(new RoundSpan(Color.parseColor("#50ff0000"), 6, Paint.Style.FILL, 10, 10, 8f, 8f)))
                .into(custom_tv);
    }
}
