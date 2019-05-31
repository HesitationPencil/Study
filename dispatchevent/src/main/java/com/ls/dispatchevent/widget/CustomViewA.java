package com.ls.dispatchevent.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Create by gagapencil
 * on 2019/4/13
 */
public class CustomViewA extends TextView {


    private String detail;

    public CustomViewA(Context context) {
        this(context, null);
    }

    public CustomViewA(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        detail = getText().toString();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag = true;
                Log.e("lx", "view ACTION_DOWN:" + detail);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("lx", "view ACTION_MOVE:" + detail);
                flag = false;
                break;
            case MotionEvent.ACTION_UP:
                Log.e("lx", "view ACTION_MOVE:" + detail);
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.e("lx", "view ACTION_CANCEL:" + detail);
                break;
            default:
                break;
        }
        return false;
    }
}
