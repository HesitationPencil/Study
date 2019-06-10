#include <jni.h>
#include <android/bitmap.h>
#include <malloc.h>
#include <cstring>
#include "gif_lib.h"
#include <android/log.h>

#define  LOG_TAG    "lx"
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  argb(a, r, g, b) ( ((a) & 0xff) << 24 ) | ( ((b) & 0xff) << 16 ) | ( ((g) & 0xff) << 8 ) | ((r) & 0xff)

typedef struct GifBean {
    int current_frame;
    int total_frame;
    int *dealys;
};

void drawFrame(GifFileType *gifFileType, GifBean *gifBean, AndroidBitmapInfo info, void *pixels);


extern "C"
JNIEXPORT jlong JNICALL
Java_com_lx_gifplay_GifHandler_loadPath(JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);

    int err;
    GifFileType *gifFileType = DGifOpenFileName(path, &err);

    DGifSlurp(gifFileType);

    GifBean *gifBean = (GifBean *) malloc(sizeof(GifBean));
    memset(gifBean, 0, sizeof(GifBean));
    gifFileType->UserData = gifBean;
    gifBean->dealys = (int *) malloc(sizeof(int) * gifFileType->ImageCount);
    memset(gifBean->dealys, 0, sizeof(int) * gifFileType->ImageCount);

    gifFileType->UserData = gifBean;
    gifBean->current_frame = 0;
    gifBean->total_frame = gifFileType->ImageCount;

    ExtensionBlock *ext;
    for (int i = 0; i < gifFileType->ImageCount; ++i) {
        SavedImage frame = gifFileType->SavedImages[i];
        for (int j = 0; j < frame.ExtensionBlockCount; ++j) {
            ext = &frame.ExtensionBlocks[j];
            break;
        }

        if (ext) {
            int frame_delay = 10 * (ext->Bytes[1] | (ext->Bytes[2] << 8));
            gifBean->dealys[i] = frame_delay;
        }
    }

    env->ReleaseStringUTFChars(path_, path);
    return (jlong) (gifFileType);
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_lx_gifplay_GifHandler_getWidth__J(JNIEnv *env, jobject instance, jlong gifAddr) {
    GifFileType *gifFileType = (GifFileType *) gifAddr;
    return gifFileType->SWidth;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_lx_gifplay_GifHandler_getHeight__J(JNIEnv *env, jobject instance, jlong gifAddr) {

    GifFileType *gifFileType = (GifFileType *) gifAddr;
    return gifFileType->SHeight;

}
extern "C"
JNIEXPORT jint JNICALL
Java_com_lx_gifplay_GifHandler_updateFrame__JLandroid_graphics_Bitmap_2(JNIEnv *env,
                                                                        jobject instance,
                                                                        jlong gifAddr,
                                                                        jobject bitmap) {

    GifFileType *gifFileType = (GifFileType *) gifAddr;
    GifBean *gifBean = (GifBean *) gifFileType->UserData;
    AndroidBitmapInfo info;

    AndroidBitmap_getInfo(env, bitmap, &info);

    void *pixels;
    AndroidBitmap_lockPixels(env, bitmap, &pixels);

    drawFrame(gifFileType, gifBean, info, pixels);
    gifBean->current_frame += 1;
    if (gifBean->current_frame >= gifBean->total_frame - 1) {
        gifBean->current_frame = 0;
        LOGE("重新来过  %d", gifBean->current_frame);
    }

    AndroidBitmap_unlockPixels(env, bitmap);
    return gifBean->dealys[gifBean->current_frame];
}

void drawFrame(GifFileType *gifFileType, GifBean *gifBean, AndroidBitmapInfo info, void *pixels) {
    SavedImage savedImage = gifFileType->SavedImages[gifBean->current_frame];
    int *px = (int *) pixels;
    int pointPixel;
    GifImageDesc frameInfo = savedImage.ImageDesc;
    GifByteType gifByteType;//压缩数据
    ColorMapObject *colorMapObject = frameInfo.ColorMap;
    px = (int *) ((char *) px + info.stride * frameInfo.Top);
    //    每一行的首地址
    int *line;

    for (int y = frameInfo.Top; y < frameInfo.Top + frameInfo.Height; ++y) {
        line = px;
        for (int x = frameInfo.Left; x < frameInfo.Left + frameInfo.Width; ++x) {
//            拿到每一个坐标的位置  索引    ---》  数据
            pointPixel = (y - frameInfo.Top) * frameInfo.Width + (x - frameInfo.Left);
//            索引   rgb   LZW压缩  字典   （）缓存在一个字典
//解压
            gifByteType = savedImage.RasterBits[pointPixel];
            GifColorType gifColorType = colorMapObject->Colors[gifByteType];
            line[x] = argb(255, gifColorType.Red, gifColorType.Green, gifColorType.Blue);
        }
        px = (int *) ((char *) px + info.stride);
    }
}

