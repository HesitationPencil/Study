package com.lx.gifplay;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    private GifHandler mGifHandler;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
// 需要摔性能下一帧
            int mNextFrame = mGifHandler.updateFrame(bitmap);
            handler.sendEmptyMessageDelayed(1, mNextFrame);
            imageView.setImageBitmap(bitmap);
        }
    };

    public void ndkLoadGif(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "demo.gif");

        mGifHandler = new GifHandler(file.getAbsolutePath());

        int width = mGifHandler.getWidth();
        int height = mGifHandler.getHeight();
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        int nextFrame = mGifHandler.updateFrame(bitmap);
        handler.sendEmptyMessageDelayed(1, nextFrame);
    }
}
