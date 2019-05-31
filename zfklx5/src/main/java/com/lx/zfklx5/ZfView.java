package com.lx.zfklx5;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.InputFilter;
import android.util.AttributeSet;

/**
 * Create by gagapencil
 * on 2019/5/11
 */
public class ZfView extends android.support.v7.widget.AppCompatEditText {
    /**
     * 边线的颜色
     */
    private int mBorderColor = Color.BLACK;
    /**
     * 边线的宽度
     */
    private int mBorderStroke = dp2px(getContext(), 0.5f);
    /**
     * 边线的画笔
     */
    private Paint mBorderPaint;


    /**
     * 分割线的颜色
     */
    private int mDivideLineColor = Color.BLACK;


    /**
     * 分割线的宽度
     */
    private int mDivideLineStroke = dp2px(getContext(), 0.5f);

    /**
     * 分割线的画笔
     */
    private Paint mDividePaint;

    /**
     * 圆的颜色   默认BLACK
     */
    private int mCircleColor = Color.BLACK;

    /**
     * 实心圆的半径
     */
    private int mCircleRadius = dp2px(getContext(), 5);

    /**
     * 圆的画笔
     */
    private Paint mCirclePaint;

    /**
     * 最大输入位数
     */
    private int mMaxCount = 6;

    /**
     * 分割线距离
     */
    private RectF mRectF;

    /**
     * 分割线路径
     */
    private Path mDividerPath;

    /**
     * 密码点路径
     */
    private Path mCirclePath;

    /**
     * 圆或者分割线的距离
     */
    private float mIntervalLength;

    private ZfListener mZfListener;


    public ZfView(Context context) {
        super(context);
        init(context, null);
    }


    public ZfView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ZfView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZfView);
            mBorderColor = typedArray.getColor(R.styleable.ZfView_borderColor, mBorderColor);
            mBorderStroke = typedArray.getDimensionPixelOffset(R.styleable.ZfView_borderStroke, mBorderStroke);
            mDivideLineColor = typedArray.getColor(R.styleable.ZfView_divideLineColor, mDivideLineColor);
            mDivideLineStroke = typedArray.getDimensionPixelOffset(R.styleable.ZfView_divideLineStroke, mDivideLineStroke);
            mCircleColor = typedArray.getColor(R.styleable.ZfView_circleColor, mCircleColor);
            mCircleRadius = typedArray.getDimensionPixelOffset(R.styleable.ZfView_circleRadius, mCircleRadius);
            mMaxCount = typedArray.getInt(R.styleable.ZfView_maxCount, mMaxCount);
            typedArray.recycle();
        }

        mBorderPaint = new Paint();
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStrokeWidth(mBorderStroke);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);

        mDividePaint = new Paint();
        mDividePaint.setColor(mDivideLineColor);
        mDividePaint.setStrokeWidth(mDivideLineStroke);
        mDividePaint.setStyle(Paint.Style.STROKE);
        mDividePaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);

        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mMaxCount)});
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mIntervalLength = (w - getPaddingStart() - getPaddingEnd()) * 1f / mMaxCount;

        if (mRectF == null) {
            mRectF = new RectF();
        }
        mRectF.left = getPaddingStart() + mBorderStroke * 1.0f / 2;
        mRectF.right = w - getPaddingEnd() - mBorderStroke * 1.0f / 2;
        mRectF.top = getPaddingTop() + mBorderStroke * 1.0f / 2;
        mRectF.bottom = h - getPaddingBottom() - mBorderStroke * 1.0f / 2;

        //生成分割线路径
        if (mDividerPath == null) {
            mDividerPath = new Path();
        }

        mDividerPath.reset();
        for (int i = 1; i < mMaxCount; i++) {
            mDividerPath.moveTo(mRectF.left + i * mIntervalLength, mRectF.top);
            mDividerPath.lineTo(mRectF.left + i * mIntervalLength, mRectF.bottom * 30);
        }

        //生成圆路径，用于PathMeasure中
        if (mCirclePath == null) {
            mCirclePath = new Path();
        }
        mCirclePath.reset();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画背景
        if (getBackground() != null) {
            getBackground().draw(canvas);
        }

        //画边框
        canvas.drawRect(mRectF, mBorderPaint);

        //画分割线
        canvas.drawPath(mDividerPath, mDividePaint);

        //画圆
        canvas.drawPath(mCirclePath, mCirclePaint);
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        int currentCount = text.toString().length();

        if (mZfListener != null) {
            mZfListener.onInput(getText().toString().trim());
        }

        if (currentCount == mMaxCount && mZfListener != null) {
            mZfListener.onCompeleted(getText().toString().trim());
        }

        if (mCirclePath != null) {
            mCirclePath.reset();
            for (int i = 0; i < currentCount; i++) {
                float x = mRectF.left + mIntervalLength / 2 + i * mIntervalLength;
                float y = (mRectF.bottom - mRectF.top) / 2;
                mCirclePath.addCircle(x, y, mCircleRadius, Path.Direction.CCW);
            }
            invalidate();
        }
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //保证光标始终在最后
        if (selStart == selEnd && getText() != null) {
            setSelection(getText().length());
        }
    }

    /**
     * dp转px  自定义事件注意使用dp为单位
     *
     * @param context
     * @param valve
     * @return
     */
    public static int dp2px(Context context, float valve) {
        float var2 = context.getResources().getDisplayMetrics().density;
        return (int) (valve * var2 + 0.5F);
    }

    public interface ZfListener {
        void onInput(String str);

        void onCompeleted(String str);
    }

    public int getBorderColor() {
        return mBorderColor;
    }

    public void setBorderColor(int borderColor) {
        mBorderColor = borderColor;
    }

    public int getBorderStroke() {
        return mBorderStroke;
    }

    public void setBorderStroke(int borderStroke) {
        mBorderStroke = borderStroke;
    }

    public int getDivideLineColor() {
        return mDivideLineColor;
    }

    public void setDivideLineColor(int divideLineColor) {
        mDivideLineColor = divideLineColor;
    }

    public int getDivideLineStroke() {
        return mDivideLineStroke;
    }

    public void setDivideLineStroke(int divideLineStroke) {
        mDivideLineStroke = divideLineStroke;
    }

    public int getCircleColor() {
        return mCircleColor;
    }

    public void setCircleColor(int circleColor) {
        mCircleColor = circleColor;
    }

    public int getCircleRadius() {
        return mCircleRadius;
    }

    public void setCircleRadius(int circleRadius) {
        mCircleRadius = circleRadius;
    }

    public int getMaxCount() {
        return mMaxCount;
    }

    public void setMaxCount(int maxCount) {
        mMaxCount = maxCount;
    }

    public ZfListener getZfListener() {
        return mZfListener;
    }

    public void setZfListener(ZfListener zfListener) {
        mZfListener = zfListener;
    }
}
