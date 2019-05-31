package com.ls.dispatchevent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.a).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("lx", "onTouch ACTION_DOWN a");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("lx", "onTouch ACTION_MOVE a");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("lx", "onTouch ACTION_MOVE a");
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        Log.e("lx", "onTouch ACTION_CANCEL a");
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        findViewById(R.id.b).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("lx", "onTouch ACTION_DOWN b");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.e("lx", "onTouch ACTION_MOVE b");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("lx", "onTouch ACTION_MOVE b");
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        Log.e("lx", "onTouch ACTION_CANCEL b");
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
}
