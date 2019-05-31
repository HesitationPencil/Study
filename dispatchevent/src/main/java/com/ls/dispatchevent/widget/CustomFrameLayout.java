package com.ls.dispatchevent.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Create by gagapencil
 * on 2019/4/13
 *
 * @author Administrator
 */
public class CustomFrameLayout extends FrameLayout {

    public CustomFrameLayout(Context context) {
        this(context, null);
    }

    public CustomFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("lx", "viewgroup ACTION_DOWN");
                flag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("lx", "viewgroup ACTION_MOVE");
                flag = false;
                break;
            case MotionEvent.ACTION_UP:
                Log.e("lx", "viewgroup ACTION_MOVE");
                break;
            default:
                break;
        }
        return true;
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            return false;
//        } else {
//            return true;
//        }
//    }
}
