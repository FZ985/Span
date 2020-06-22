package com.text.span;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * create by : jfz
 * 使用：
 * |   TextSpannable.create()
 * |     .append(new TextSpannable.Builder()
 * |     //文本
 * |     .text("哈哈哈")
 * |     //文字大小
 * |     .textSize(30)
 * |     //本段文字背景色
 * |     .backgroundColor(Color.YELLOW)
 * |     //字体颜色
 * |     .textColor(Color.RED)
 * |     //下划线
 * |     .underLine()
 * |     //删除线
 * |     .deleteLine()
 * |     //字体样式（默认、加粗、斜体、斜粗）
 * |     .typeFace(Typeface.BOLD)
 * |     //本段文字点击事件
 * |     .click(new TextSpannable.OnClickSpanListener() {
 * |
 * |      @Override public void onClick(String text, View widget) {
 * |
 * |     }
 * |     }))
 * |    .append(new TextSpannable.Builder().xxxx.xx)
 * |    .append(new TextSpannable.Builder().xxxx.xx)
 * |    .append(new TextSpannable.Builder().xxxx.xx)
 * |    //是否设置字体 setFont(bool,fontName)
 * |    .setFont(false)
 * |    //高亮颜色
 * |    .hightLightColor(Color.parseColor("#626262"))
 * |    //设置容器
 * |    .into(tv);
 * |
 */

public class TextSpannable {
    private List<Builder> list;
    private int hightLightColor = Color.TRANSPARENT;
    private boolean font;
//    public static final String TEXT_FONT = "Impact.ttf";
    private String fontName;

    public static TextSpannable create() {
        return new TextSpannable();
    }

    public TextSpannable append(Builder builder) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(builder);
        return this;
    }

    public TextSpannable hightLightColor(int color) {
        this.hightLightColor = color;
        return this;
    }

//    public TextSpannable setFont(boolean font) {
//        this.font = font;
//        return this;
//    }
//
//    public TextSpannable setFont(boolean font, String fontName) {
//        this.font = font;
//        this.fontName = fontName;
//        return this;
//    }

    public void into(TextView tv) {
        if (tv != null && list != null && list.size() > 0) {
//            if (font) {
//                if (!TextUtils.isEmpty(fontName)) {
//                    FontUtils.setTypeface(tv, fontName);
//                } else FontUtils.setTypeface(tv);
//            }
            SpannableStringBuilder string = new SpannableStringBuilder("");
            for (int i = 0; i < list.size(); i++) {
                Builder builder = list.get(i);
                if (!TextUtils.isEmpty(builder.getText())) {
                    SpannableString span = new SpannableString(builder.getText());

                    if (builder.getBackgroundColor() != -1) {
                        //背景色
                        span.setSpan(new BackgroundColorSpan(builder.getBackgroundColor()),
                                0,
                                builder.getText().length(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    if (builder.getTextColor() != -1) {
                        //文字颜色
                        span.setSpan(new ForegroundColorSpan(builder.getTextColor()),
                                0,
                                builder.getText().length(),
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    if (builder.getTextSize() != -1) {
                        //文字大小,绝对大小，
                        span.setSpan(new AbsoluteSizeSpan(builder.getTextSize(), true),
                                0,
                                builder.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    if (builder.isUnderLine()) {
                        //下划线
                        span.setSpan(new UnderlineSpan(), 0, builder.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    if (builder.isDeleteLine()) {
                        //删除线
                        span.setSpan(new StrikethroughSpan(), 0, builder.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    if (builder.getTypeFace() != -1) {
                        //字体样式(斜体、粗体、粗斜体)
                        span.setSpan(new StyleSpan(builder.getTypeFace()), 0, builder.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                    if (builder.getClickSpanListener() != null) {
                        //文本设置点击
                        span.setSpan(new TextClickSpannable(builder.getText(), builder.isUnderLine(), builder.getClickSpanListener()), 0, builder.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    }
                    string.append(span);
                }
            }
            tv.setText(string);
//            tv.setMovementMethod(LinkMovementMethod.getInstance());
            tv.setMovementMethod(LinkMovementMethodImpl.getInstance());
            tv.setHighlightColor(hightLightColor);
        }
    }

    private static class LinkMovementMethodImpl extends LinkMovementMethod {
        private static LinkMovementMethodImpl instance;

        public LinkMovementMethodImpl() {
        }

        public static LinkMovementMethodImpl getInstance() {
            if (instance == null) {
                instance = new LinkMovementMethodImpl();
            }
            return instance;
        }

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            boolean b = super.onTouchEvent(widget, buffer, event);
            int action = event.getAction();
            if (action == MotionEvent.ACTION_UP) {
                if (!b) {
                    ViewParent parent = widget.getParent();
                    if (parent instanceof ViewGroup) {
                        // 获取被点击控件的父容器，让父容器执行点击；
                        ((ViewGroup) parent).performClick();
                    }
                }
            }
            return b;
        }
    }

    private static class TextClickSpannable extends ClickableSpan {
        private OnClickSpanListener onClickSpanListener;
        private String text;
        private boolean isUnderLine;

        public TextClickSpannable(String text, boolean isUnderLine, OnClickSpanListener onClickSpanListener) {
            this.onClickSpanListener = onClickSpanListener;
            this.text = text;
            this.isUnderLine = isUnderLine;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(isUnderLine);
        }

        @Override
        public void onClick(View widget) {
            if (onClickSpanListener != null) {
                onClickSpanListener.onClick(text, widget);
            }
        }
    }

    public static class Builder {
        private String text = "";
        private int textColor = -1;
        private int textSize = -1;
        private int backgroundColor = -1;
        private boolean isUnderLine;
        private boolean isDeleteLine;
        private int typeFace = Typeface.NORMAL;
        private OnClickSpanListener clickSpanListener;

        private String getText() {
            return text;
        }

        public Builder text(String text) {
            this.text = text+"";
            return this;
        }

        private int getTextColor() {
            return textColor;
        }

        public Builder textColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        private int getTextSize() {
            return textSize;
        }

        public Builder textSize(int textSize) {
            this.textSize = textSize;
            return this;
        }

        private int getBackgroundColor() {
            return backgroundColor;
        }

        public Builder backgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        private boolean isUnderLine() {
            return isUnderLine;
        }

        public Builder underLine() {
            isUnderLine = true;
            return this;
        }

        private boolean isDeleteLine() {
            return isDeleteLine;
        }

        public Builder deleteLine() {
            isDeleteLine = true;
            return this;
        }

        private int getTypeFace() {
            return typeFace;
        }

        public Builder typeFace(int typeFace) {
            this.typeFace = typeFace;
            return this;
        }

        public Builder click(OnClickSpanListener clickSpanListener) {
            this.clickSpanListener = clickSpanListener;
            return this;
        }

        private OnClickSpanListener getClickSpanListener() {
            return clickSpanListener;
        }
    }

    public interface OnClickSpanListener {
        void onClick(String text, View widget);
    }
}
