package com.chengyi.app.view.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.chengyi.R;

/**
 * Created by ChengYi
 * Copyright 2014-2016 丞易软件 All rights reserved.
 */
public class RoundProgressBarWidthNumber extends
        HorizontalProgressBarWithNumber {
    /**
     * mRadius of view
     */
    private int mRadius = dp2px(30);
    private int mMaxPaintWidth;
    private TextPaint txtPaint;

    public RoundProgressBarWidthNumber(Context context) {
        this(context, null);
    }

    public RoundProgressBarWidthNumber(Context context, AttributeSet attrs) {
        super(context, attrs);

        mReachedProgressBarHeight = (int) (mUnReachedProgressBarHeight * 1f);
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBarWidthNumber);
        mRadius = (int) ta.getDimension(   R.styleable.RoundProgressBarWidthNumber_radius, mRadius);
        ta.recycle();
        txtPaint = new TextPaint();
        txtPaint.setColor(Color.RED);
        mPaint.setStyle(Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Cap.ROUND);

    }

    /**
     * 这里默认在布局中padding值要么不设置，要么全部设置
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {

        mMaxPaintWidth = Math.max(mReachedProgressBarHeight,
                mUnReachedProgressBarHeight);
        int expect = mRadius * 2 + mMaxPaintWidth + getPaddingLeft()
                + getPaddingRight();
        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);
        int realWidth = Math.min(width, height);

        mRadius = (realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2;

        setMeasuredDimension(realWidth, realWidth);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {

        String text = getProgress() + "%";
        float textWidth = mPaint.measureText(text);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop()  + mMaxPaintWidth / 2);
        mPaint.setStyle(Style.STROKE);
        // draw unreaded bar
        mPaint.setColor(mUnReachedBarColor);
        mPaint.setStrokeWidth(mUnReachedProgressBarHeight);
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        // draw reached bar
        mPaint.setColor(mReachedBarColor);
        mPaint.setStrokeWidth(mReachedProgressBarHeight);
        float sweepAngle = getProgress() * 1.0f / getMax() * 360;
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), -90,
                sweepAngle, false, mPaint);
        // draw text
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(Color.RED);

        if (!TextUtils.isEmpty(getSecondTxt())) {
            canvas.drawText(text, mRadius - textWidth / 2, mRadius + textHeight,
                    mPaint);
        } else {
            canvas.drawText(text, mRadius - textWidth / 2, mRadius-textHeight, mPaint);
        }
        if (!TextUtils.isEmpty(getSecondTxt())) {
            mPaint.setColor(Color.GREEN);
        } else {
            mPaint.setColor(Color.TRANSPARENT);
        }

        if (!TextUtils.isEmpty(getSecondTxt())) {
            textWidth = mPaint.measureText(getSecondTxt());

            canvas.drawText(getSecondTxt(), mRadius - textWidth / 2, mRadius - textHeight * 2,
                    mPaint);
        } else {
            textWidth = mPaint.measureText("");
            canvas.drawText("", mRadius - textWidth / 2, mRadius - textHeight * 2,
                    mPaint);
        }
        canvas.restore();

    }


    private String secondTxt;

    public String getSecondTxt() {
        if (TextUtils.isEmpty(secondTxt))
            return  "";
        return secondTxt;
    }

    public void setSecondTxt(String secondTxt) {
        this.secondTxt = secondTxt;
        invalidate();
    }
}
