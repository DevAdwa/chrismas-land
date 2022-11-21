package com.example.fecafootdemo.utils.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.example.fecafootdemo.R;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
public class CustomProgressbar extends ProgressBar {
    private Paint textPaint;
    private String text;
    public CustomProgressbar(Context context) {
        super(context);
        text = "0%";
        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
    }

    public CustomProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray attrValues = context.obtainStyledAttributes(attrs, R.styleable.CustomProgressbar);
        textPaint = new TextPaint();
        int color = attrValues.getColor(R.styleable.CustomProgressbar_android_color, getResources().getColor(android.R.color.black));
        float size = attrValues.getDimension(R.styleable.CustomProgressbar_textSize, 18);
        text = attrValues.getString(R.styleable.CustomProgressbar_innerText);
        textPaint.setTextSize(size);
        textPaint.setColor(color);
    }


    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation")
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        int x = getWidth() / 2 - bounds.centerX();
        int y = getHeight() / 2 - bounds.centerY();
        canvas.drawText(text, x, y, textPaint);
    }

    public synchronized void setText(String text){
        this.text = text.concat("%");
        refreshDrawableState();
    }
}
