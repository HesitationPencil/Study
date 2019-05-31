package com.ls.movelx2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Create by gagapencil
 * on 2019/5/9
 */
public class MoveView extends View implements View.OnTouchListener {

    private Paint mPaint;
    private String TAG = "MoveView";
    private float mLastX;
    private float mLastY;
    private int mMeasuredHeight;
    private int mMeasuredWidth;
    private float mDragRight;
    private float mDragBottom;
    private boolean mInDragRange;

    public MoveView(Context context) {
        this(context, null);
    }

    public MoveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOnTouchListener(this);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mInDragRange = isInDragRange(x, y);
                if(mInDragRange){
                    Toast.makeText(getContext(), "开始拖拽", Toast.LENGTH_SHORT).show();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mInDragRange) {
                    int dx = (int) (x - mLastX);
                    int dy = (int) (y - mLastY);
                    layout(getLeft() + dx, getTop() + dy, getRight() + dx, getBottom() + dy);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mInDragRange = false;
                break;
            default:

                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMeasuredHeight = getMeasuredHeight();
        mMeasuredWidth = getMeasuredWidth();
        mDragRight = mMeasuredHeight / 2.0f;
        mDragBottom = mMeasuredWidth / 2.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, mDragRight, mDragBottom, mPaint);
    }


    public boolean isInDragRange(float x, float y) {
        if (x < mDragRight && x > 0 && y < mDragBottom && y > 0) {
            return true;
        }
        return false;
    }
}
