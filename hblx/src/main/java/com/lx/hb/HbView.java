package com.lx.hb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Create by gagapencil
 * on 2019/5/8
 */
public class HbView extends View {

    private float displayMetricsWidth;
    private float displayMetricsHeight;
    private Paint mPaint;
    private Bitmap mBitmap;
    public float colormatrix_heibai[] = {
            0.8f, 1.6f, 0.2f, 0, -163.9f,
            0.8f, 1.6f, 0.2f, 0, -163.9f,
            0.8f, 1.6f, 0.2f, 0, -163.9f,
            0, 0, 0, 1.0f, 0};
    private float scale = 1;
    private ColorMatrixColorFilter mColorMatrixColorFilter;

    public HbView(Context context) {
        this(context, null);
    }

    public HbView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HbView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);


    }


    private void init(AttributeSet attrs) {
        //在这里得到设备的真实值
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int systemBarHeight = getSystemBarHeight(getContext());
        //判断横屏还竖屏
        if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
            displayMetricsWidth = (float) (displayMetrics.heightPixels);
            displayMetricsHeight = (float) (displayMetrics.widthPixels - systemBarHeight);
        } else {
            this.displayMetricsWidth = (float) (displayMetrics.widthPixels);
            this.displayMetricsHeight = (float) (displayMetrics.heightPixels - systemBarHeight);
        }

        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.fan);
        mColorMatrixColorFilter = new ColorMatrixColorFilter(colormatrix_heibai);
        if (mBitmap.getHeight() > displayMetricsHeight || mBitmap.getWidth() > displayMetricsWidth) {
            float scaleW = displayMetricsWidth / mBitmap.getWidth();
            float scaleH = displayMetricsHeight / mBitmap.getHeight();
            scale = Math.min(scaleH, scaleW);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(scale, scale);

        mPaint.reset();
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        mPaint.setColorFilter(mColorMatrixColorFilter);
        canvas.drawBitmap(mBitmap, 0, mBitmap.getHeight(), mPaint);
    }

    /**
     * 用于得到状态框的高度
     */
    public int getSystemBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int height = context.getResources().getDimensionPixelSize(resourceId);
        if (height != -1) {
            return height;
        }
        return getValue(context, "com.android.internal.R$dimen", "system_bar_height", 48);
    }

    private int getValue(Context context, String dimeClass, String system_bar_height, int defaultValue) {

        try {
            Class<?> clz = Class.forName(dimeClass);
            Object object = clz.newInstance();
            Field field = clz.getField(system_bar_height);
            int id = Integer.parseInt(field.get(object).toString());
            return context.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
