package com.example.fecafootdemo.utils.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 3/29/22)
 */
public class ScanView extends View {
    private Paint paint = new Paint();
    private int mPosY = 0;
    private boolean runAnimation = true;
    private boolean showLine = true;
    private Handler handler;
    private Runnable refreshRunnable;
    private boolean isGoingDown = true;
    private int mHeight;
    private int DELAY = 0;

    public ScanView(Context context){
        super(context);
        init();
    }

    public ScanView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    private void init(){
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(5.0f);
        handler = new Handler();
        refreshRunnable = this::refreshView;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mHeight = canvas.getHeight();
        if (showLine){
            canvas.drawLine(0, mPosY, canvas.getWidth(), mPosY, paint);
        }
        if (runAnimation){
            handler.postDelayed(refreshRunnable, DELAY);
        }
        super.onDraw(canvas);
    }

    public void startAnimation(){
        runAnimation = true;
        showLine = true;
        this.invalidate();
    }

    public void stopAnimation(){
        runAnimation = false;
        showLine = false;
        reset();
        this.invalidate();
    }

    private void reset(){
        mPosY = 0;
        isGoingDown = true;
    }

    private void refreshView(){
        if (isGoingDown){
            mPosY += 5;
            if (mPosY > mHeight){
                mPosY = mHeight;
                isGoingDown = false;
            }else{
                mPosY -= 5;
                if (mPosY < 0){
                    mPosY = 0;
                    isGoingDown = true;
                }
                this.invalidate();
            }
        }
    }
}
