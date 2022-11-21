package com.example.fecafootdemo.utils.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.example.fecafootdemo.R.attr;
import com.example.fecafootdemo.R.color;
import com.example.fecafootdemo.R.drawable;
import com.example.fecafootdemo.R.styleable;
/*
 * Project name = FecaFootDemo
 *  GitHub handler = https://github.com/Ora-Kool
 *  Email = etingemabian@gmail.com")
 *  File created by Etinge Mabian on 4/4/22)
 */

public class ArcView extends  View {

    private static final String TAG = ArcView.class.getSimpleName();
    private static int INVALID_PROGRESS_VALUE = -1;
    private final int mAngleOffset = -90;
    private Drawable mThumb;
    private int mMax = 100;
    private int mProgress = 0;
    private int mProgressWidth = 4;
    private int mArcWidth = 2;
    private int mStartAngle = 0;
    private int mSweepAngle = 360;
    private int mRotation = 0;
    private boolean mRoundedEdges = false;
    private boolean mTouchInside = true;
    private boolean mClockwise = true;
    private boolean mEnabled = true;
    private int mArcRadius = 0;
    private float mProgressSweep = 0.0F;
    private RectF mArcRect = new RectF();
    private Paint mArcPaint;
    private Paint mProgressPaint;
    private int mTranslateX;
    private int mTranslateY;
    private int mThumbXPos;
    private int mThumbYPos;
    private double mTouchAngle;
    private float mTouchIgnoreRadius;
    private ArcView.OnSeekArcChangeListener mOnSeekArcChangeListener;

    public ArcView(Context context) {
        super(context);
        this.init(context, (AttributeSet)null, 0);
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, attr.arcStyle);
    }

    public ArcView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        Log.d(TAG, "Initialising ArcView");
        Resources res = this.getResources();
        float density = context.getResources().getDisplayMetrics().density;
        int arcColor = res.getColor(color.progress_gray);
        int progressColor = res.getColor(color.default_blue_light);
        //int thumbHalfheight = false;
        //int thumbHalfWidth = false;
        this.mThumb = res.getDrawable(drawable.seek_arc_control_selector);
        this.mProgressWidth = (int)((float)this.mProgressWidth * density);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, styleable.ArcView, defStyle, 0);
            Drawable thumb = a.getDrawable(styleable.SeekArc_thumb);
            if (thumb != null) {
                this.mThumb = thumb;
            }

            int thumbHalfheight = this.mThumb.getIntrinsicHeight() / 2;
            int thumbHalfWidth = this.mThumb.getIntrinsicWidth() / 2;
            this.mThumb.setBounds(-thumbHalfWidth, -thumbHalfheight, thumbHalfWidth, thumbHalfheight);
            this.mMax = a.getInteger(styleable.SeekArc_max, this.mMax);
            this.mProgress = a.getInteger(styleable.SeekArc_progress, this.mProgress);
            this.mProgressWidth = (int)a.getDimension(styleable.SeekArc_progressWidth, (float)this.mProgressWidth);
            this.mArcWidth = (int)a.getDimension(styleable.SeekArc_arcWidth, (float)this.mArcWidth);
            this.mStartAngle = a.getInt(styleable.SeekArc_startAngle, this.mStartAngle);
            this.mSweepAngle = a.getInt(styleable.SeekArc_sweepAngle, this.mSweepAngle);
            this.mRotation = a.getInt(styleable.SeekArc_rotation, this.mRotation);
            this.mRoundedEdges = a.getBoolean(styleable.SeekArc_roundEdges, this.mRoundedEdges);
            this.mTouchInside = a.getBoolean(styleable.SeekArc_touchInside, this.mTouchInside);
            this.mClockwise = a.getBoolean(styleable.SeekArc_clockwise, this.mClockwise);
            this.mEnabled = a.getBoolean(styleable.SeekArc_enabled, this.mEnabled);
            arcColor = a.getColor(styleable.SeekArc_arcColor, arcColor);
            progressColor = a.getColor(styleable.SeekArc_progressColor, progressColor);
            a.recycle();
        }

        this.mProgress = this.mProgress > this.mMax ? this.mMax : this.mProgress;
        this.mProgress = this.mProgress < 0 ? 0 : this.mProgress;
        this.mSweepAngle = this.mSweepAngle > 360 ? 360 : this.mSweepAngle;
        this.mSweepAngle = this.mSweepAngle < 0 ? 0 : this.mSweepAngle;
        this.mProgressSweep = (float)this.mProgress / (float)this.mMax * (float)this.mSweepAngle;
        this.mStartAngle = this.mStartAngle > 360 ? 0 : this.mStartAngle;
        this.mStartAngle = this.mStartAngle < 0 ? 0 : this.mStartAngle;
        this.mArcPaint = new Paint();
        this.mArcPaint.setColor(arcColor);
        this.mArcPaint.setAntiAlias(true);
        this.mArcPaint.setStyle(Paint.Style.STROKE);
        this.mArcPaint.setStrokeWidth((float)this.mArcWidth);
        this.mProgressPaint = new Paint();
        this.mProgressPaint.setColor(progressColor);
        this.mProgressPaint.setAntiAlias(true);
        this.mProgressPaint.setStyle(Paint.Style.STROKE);
        this.mProgressPaint.setStrokeWidth((float)this.mProgressWidth);
        if (this.mRoundedEdges) {
            this.mArcPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        }

    }

    protected void onDraw(Canvas canvas) {
        if (!this.mClockwise) {
            canvas.scale(-1.0F, 1.0F, this.mArcRect.centerX(), this.mArcRect.centerY());
        }

        int arcStart = this.mStartAngle + -90 + this.mRotation;
        int arcSweep = this.mSweepAngle;
        canvas.drawArc(this.mArcRect, (float)arcStart, (float)arcSweep, false, this.mArcPaint);
        canvas.drawArc(this.mArcRect, (float)arcStart, this.mProgressSweep, false, this.mProgressPaint);
        if (this.mEnabled) {
            canvas.translate((float)(this.mTranslateX - this.mThumbXPos), (float)(this.mTranslateY - this.mThumbYPos));
            this.mThumb.draw(canvas);
        }

    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = getDefaultSize(this.getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(this.getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(width, height);
        float top = 0.0F;
        float left = 0.0F;
        //int arcDiameter = false;
        this.mTranslateX = (int)((float)width * 0.5F);
        this.mTranslateY = (int)((float)height * 0.5F);
        int arcDiameter = min - this.getPaddingLeft();
        this.mArcRadius = arcDiameter / 2;
        top = (float)(height / 2 - arcDiameter / 2);
        left = (float)(width / 2 - arcDiameter / 2);
        this.mArcRect.set(left, top, left + (float)arcDiameter, top + (float)arcDiameter);
        int arcStart = (int)this.mProgressSweep + this.mStartAngle + this.mRotation + 90;
        this.mThumbXPos = (int)((double)this.mArcRadius * Math.cos(Math.toRadians((double)arcStart)));
        this.mThumbYPos = (int)((double)this.mArcRadius * Math.sin(Math.toRadians((double)arcStart)));
        this.setTouchInSide(this.mTouchInside);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mEnabled) {
            this.getParent().requestDisallowInterceptTouchEvent(true);
            switch(event.getAction()) {
                case 0:
                    this.onStartTrackingTouch();
                    this.updateOnTouch(event);
                    break;
                case 1:
                    this.onStopTrackingTouch();
                    this.setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                case 2:
                    this.updateOnTouch(event);
                    break;
                case 3:
                    this.onStopTrackingTouch();
                    this.setPressed(false);
                    this.getParent().requestDisallowInterceptTouchEvent(false);
            }

            return true;
        } else {
            return false;
        }
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mThumb != null && this.mThumb.isStateful()) {
            int[] state = this.getDrawableState();
            this.mThumb.setState(state);
        }

        this.invalidate();
    }

    private void onStartTrackingTouch() {
        if (this.mOnSeekArcChangeListener != null) {
            this.mOnSeekArcChangeListener.onStartTrackingTouch(this);
        }

    }

    private void onStopTrackingTouch() {
        if (this.mOnSeekArcChangeListener != null) {
            this.mOnSeekArcChangeListener.onStopTrackingTouch(this);
        }

    }

    private void updateOnTouch(MotionEvent event) {
        boolean ignoreTouch = this.ignoreTouch(event.getX(), event.getY());
        if (!ignoreTouch) {
            this.setPressed(true);
            this.mTouchAngle = this.getTouchDegrees(event.getX(), event.getY());
            int progress = this.getProgressForAngle(this.mTouchAngle);
            this.onProgressRefresh(progress, true);
        }
    }

    private boolean ignoreTouch(float xPos, float yPos) {
        boolean ignore = false;
        float x = xPos - (float)this.mTranslateX;
        float y = yPos - (float)this.mTranslateY;
        float touchRadius = (float)Math.sqrt((double)(x * x + y * y));
        if (touchRadius < this.mTouchIgnoreRadius) {
            ignore = true;
        }

        return ignore;
    }

    private double getTouchDegrees(float xPos, float yPos) {
        float x = xPos - (float)this.mTranslateX;
        float y = yPos - (float)this.mTranslateY;
        x = this.mClockwise ? x : -x;
        double angle = Math.toDegrees(Math.atan2((double)y, (double)x) + 1.5707963267948966D - Math.toRadians((double)this.mRotation));
        if (angle < 0.0D) {
            angle += 360.0D;
        }

        angle -= (double)this.mStartAngle;
        return angle;
    }

    private int getProgressForAngle(double angle) {
        int touchProgress = (int)Math.round((double)this.valuePerDegree() * angle);
        touchProgress = touchProgress < 0 ? INVALID_PROGRESS_VALUE : touchProgress;
        touchProgress = touchProgress > this.mMax ? INVALID_PROGRESS_VALUE : touchProgress;
        return touchProgress;
    }

    private float valuePerDegree() {
        return (float)this.mMax / (float)this.mSweepAngle;
    }

    private void onProgressRefresh(int progress, boolean fromUser) {
        this.updateProgress(progress, fromUser);
    }

    private void updateThumbPosition() {
        int thumbAngle = (int)((float)this.mStartAngle + this.mProgressSweep + (float)this.mRotation + 90.0F);
        this.mThumbXPos = (int)((double)this.mArcRadius * Math.cos(Math.toRadians((double)thumbAngle)));
        this.mThumbYPos = (int)((double)this.mArcRadius * Math.sin(Math.toRadians((double)thumbAngle)));
    }

    private void updateProgress(int progress, boolean fromUser) {
        if (progress != INVALID_PROGRESS_VALUE) {
            if (this.mOnSeekArcChangeListener != null) {
                this.mOnSeekArcChangeListener.onProgressChanged(this, progress, fromUser);
            }

            progress = progress > this.mMax ? this.mMax : progress;
            progress = progress < 0 ? 0 : progress;
            this.mProgress = progress;
            this.mProgressSweep = (float)progress / (float)this.mMax * (float)this.mSweepAngle;
            this.updateThumbPosition();
            this.invalidate();
        }
    }

    public void setOnSeekArcChangeListener(ArcView.OnSeekArcChangeListener l) {
        this.mOnSeekArcChangeListener = l;
    }

    public void setProgress(int progress) {
        this.updateProgress(progress, false);
    }

    public int getProgress() {
        return this.mProgress;
    }

    public int getProgressWidth() {
        return this.mProgressWidth;
    }

    public void setProgressWidth(int mProgressWidth) {
        this.mProgressWidth = mProgressWidth;
        this.mProgressPaint.setStrokeWidth((float)mProgressWidth);
    }

    public int getArcWidth() {
        return this.mArcWidth;
    }

    public void setArcWidth(int mArcWidth) {
        this.mArcWidth = mArcWidth;
        this.mArcPaint.setStrokeWidth((float)mArcWidth);
    }

    public int getArcRotation() {
        return this.mRotation;
    }

    public void setArcRotation(int mRotation) {
        this.mRotation = mRotation;
        this.updateThumbPosition();
    }

    public int getStartAngle() {
        return this.mStartAngle;
    }

    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        this.updateThumbPosition();
    }

    public int getSweepAngle() {
        return this.mSweepAngle;
    }

    public void setSweepAngle(int mSweepAngle) {
        this.mSweepAngle = mSweepAngle;
        this.updateThumbPosition();
    }

    public void setRoundedEdges(boolean isEnabled) {
        this.mRoundedEdges = isEnabled;
        if (this.mRoundedEdges) {
            this.mArcPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        } else {
            this.mArcPaint.setStrokeCap(Paint.Cap.SQUARE);
            this.mProgressPaint.setStrokeCap(Paint.Cap.SQUARE);
        }

    }

    public void setTouchInSide(boolean isEnabled) {
        int thumbHalfheight = this.mThumb.getIntrinsicHeight() / 2;
        int thumbHalfWidth = this.mThumb.getIntrinsicWidth() / 2;
        this.mTouchInside = isEnabled;
        if (this.mTouchInside) {
            this.mTouchIgnoreRadius = (float)this.mArcRadius / 4.0F;
        } else {
            this.mTouchIgnoreRadius = (float)(this.mArcRadius - Math.min(thumbHalfWidth, thumbHalfheight));
        }

    }

    public void setClockwise(boolean isClockwise) {
        this.mClockwise = isClockwise;
    }

    public boolean isClockwise() {
        return this.mClockwise;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
    }

    public int getProgressColor() {
        return this.mProgressPaint.getColor();
    }

    public void setProgressColor(int color) {
        this.mProgressPaint.setColor(color);
        this.invalidate();
    }

    public void setMax(int max){
        this.mMax  = max;
    }

    public int getArcColor() {
        return this.mArcPaint.getColor();
    }



    public void setArcColor(int color) {
        this.mArcPaint.setColor(color);
        this.invalidate();
    }

    public interface OnSeekArcChangeListener {
        void onProgressChanged(ArcView var1, int var2, boolean var3);

        void onStartTrackingTouch(ArcView var1);

        void onStopTrackingTouch(ArcView var1);
    }
}
