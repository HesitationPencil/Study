package com.lx.hb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView mIvSrc;
    private ImageView mIvHb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new HbView(this));

        setContentView(R.layout.activity_main);
        mIvSrc = findViewById(R.id.iv_src);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.fan);
        mIvSrc.setImageBitmap(bitmap);
        mIvHb = findViewById(R.id.iv_hb);
        mIvHb.setImageBitmap(toHbBitmap(bitmap));
    }

    private Bitmap toHbBitmap(Bitmap bitmap) {
        int width, height;
        //获取原始图片宽高
        height = bitmap.getHeight();
        width = bitmap.getWidth();

        //生成和原图一样大小的图片
        Bitmap hbBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        //用hbBitmap生成canvas 后续的canvas操作将直接更改hbBitmap
        Canvas c = new Canvas(hbBitmap);

        //生成画笔
        Paint paint = new Paint();

        //生成画笔黑白ColorMatrixColorFilter 用于将图片转成黑白
        ColorMatrixColorFilter hbColorFilter = new ColorMatrixColorFilter(new float[]{
                0.8f, 1.6f, 0.2f, 0, -163.9f,
                0.8f, 1.6f, 0.2f, 0, -163.9f,
                0.8f, 1.6f, 0.2f, 0, -163.9f,
                0, 0, 0, 1.0f, 0});

        //设置colorFilter
        paint.setColorFilter(hbColorFilter);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //将bitmap 画入hbBitmap，因为采用了黑白颜色过滤器，最终hbBitmap呈现的是bitmap的黑白颜色
        c.drawBitmap(bitmap, 0, 0, paint);

        return hbBitmap;
    }
}
