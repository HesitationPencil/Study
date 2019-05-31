#include <jni.h>
#include <string>
#include <malloc.h>
#include <cstring>
#include <android/log.h>
#include <android/bitmap.h>

extern "C"
JNIEXPORT jlong JNICALL
Java_com_lx_gifplay_GifHandler_loadPath(JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);

    // TODO

    env->ReleaseStringUTFChars(path_, path);
}extern "C"
JNIEXPORT jint JNICALL
Java_com_lx_gifplay_GifHandler_getWidth__J(JNIEnv *env, jobject instance, jlong gifAddr) {




}extern "C"
JNIEXPORT jint JNICALL
Java_com_lx_gifplay_GifHandler_getHeight__J(JNIEnv *env, jobject instance, jlong gifAddr) {

    // TODO

}extern "C"
JNIEXPORT jint JNICALL
Java_com_lx_gifplay_GifHandler_updateFrame__JLandroid_graphics_Bitmap_2(JNIEnv *env,
                                                                        jobject instance,
                                                                        jlong gifAddr,
                                                                        jobject bitmap) {

    // TODO

}