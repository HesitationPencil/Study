#include <jni.h>
#include <android/native_window_jni.h>
#include <zconf.h>

extern "C" {
#include <libavformat/avformat.h>
#include <libswscale/swscale.h>
#include <libavutil/imgutils.h>
#include "libavcodec/avcodec.h"
}

extern "C"
JNIEXPORT void JNICALL
Java_com_lx_myplay_Player_native_1start(JNIEnv *env, jobject instance, jstring path_,
                                        jobject surface) {
    const char *path = env->GetStringUTFChars(path_, 0);

    avformat_network_init();
    AVFormatContext *formatContext = avformat_alloc_context();

    AVDictionary *opts = NULL;
    av_dict_set(&opts, "timeout", "3000000", 0);
    int ret = avformat_open_input(&formatContext, path, NULL, &opts);
    avformat_find_stream_info(formatContext, NULL);

    int vidio_stream_idx = -1;
    for (int i = 0; i < formatContext->nb_streams; ++i) {
        if (formatContext->streams[i]->codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            vidio_stream_idx = i;
            break;
        }
    }

    AVCodecParameters *codecpar = formatContext->streams[vidio_stream_idx]->codecpar;

    AVCodec *dec = avcodec_find_decoder(codecpar->codec_id);
    AVCodecContext *codecContext = avcodec_alloc_context3(dec);
    avcodec_parameters_to_context(codecContext, codecpar);
    avcodec_open2(codecContext, dec, NULL);
    //读取包
    AVPacket *packet = av_packet_alloc();

    SwsContext *sws_ctx = sws_getContext(
            codecContext->width, codecContext->height, codecContext->pix_fmt,
            codecContext->width, codecContext->height, AV_PIX_FMT_RGBA,
            SWS_BILINEAR, 0, 0, 0);
    ANativeWindow *nativeWindow = ANativeWindow_fromSurface(env, surface);
    ANativeWindow_Buffer outBuffer;
    int frameCount = 0;
    ANativeWindow_setBuffersGeometry(nativeWindow, codecContext->width,
                                     codecContext->height,
                                     WINDOW_FORMAT_RGBA_8888);

    while (av_read_frame(formatContext, packet) >= 0) {
        avcodec_send_packet(codecContext, packet);
        AVFrame *frame = av_frame_alloc();
        ret = avcodec_receive_frame(codecContext, frame);
        if (ret == AVERROR(EAGAIN)) {
            //需要更多数据
            continue;
        } else if (ret < 0) {
            break;
        }

        uint8_t *dst_data[4];
        int dst_linesize[4];
        av_image_alloc(dst_data, dst_linesize,
                       codecContext->width, codecContext->height, AV_PIX_FMT_RGBA, 1);
        if (packet->stream_index == vidio_stream_idx) {
            if (ret == 0) {
                ANativeWindow_lock(nativeWindow, &outBuffer, NULL);
                sws_scale(sws_ctx,
                          reinterpret_cast<const uint8_t *const *>(frame->data), frame->linesize, 0,
                          frame->height,
                          dst_data, dst_linesize);
                uint8_t *dst= (uint8_t *) outBuffer.bits;
                int destStride=outBuffer.stride*4;
                uint8_t *src_data = dst_data[0];
                int src_linesize = dst_linesize[0];
                uint8_t *firstWindown = static_cast<uint8_t *>(outBuffer.bits);
                for (int i = 0; i < outBuffer.height; ++i) {
                    memcpy(firstWindown + i * destStride, src_data + i * src_linesize, destStride);
                }
                ANativeWindow_unlockAndPost(nativeWindow);
                usleep(1000 * 16);
                av_frame_free(&frame);
            }
        }
    }

    env->ReleaseStringUTFChars(path_, path);
}