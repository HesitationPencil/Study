package com.lx.gifplay;

import android.graphics.Bitmap;

/**
 * Create by gagapencil
 * on 2019/5/31
 */
public class GifHandler {
    static {
        System.loadLibrary("gif-lib");
    }

    private long gifAddr;

    public GifHandler(String path) {
        this.gifAddr = loadPath(path);
    }


    public int getWidth() {
        return getWidth(gifAddr);
    }


    public int getHeight() {
        return getHeight(gifAddr);
    }

    public int updateFrame(Bitmap bitmap) {
        return updateFrame(gifAddr, bitmap);
    }

    private native long loadPath(String path);

    private native int getWidth(long gifAddr);

    private native int getHeight(long gifAddr);

    private native int updateFrame(long gifAddr, Bitmap bitmap);

}
