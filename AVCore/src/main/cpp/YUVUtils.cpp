//
// Created by zhangyue on 2022/6/15.
//
#include <jni.h>
#include <string>
#include <android/log.h>

extern "C" {
#include "include/libavcodec/avcodec.h"
}
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,"ivideo",__VA_ARGS__)


extern "C"
JNIEXPORT jintArray JNICALL
Java_com_ivideo_avcore_utils_YUVUtils_yuv2Rgb(JNIEnv *env, jobject thiz, jbyteArray buf, jint width,
                                              jint height) {
    jbyte *yuv420spSRC = (env)->GetByteArrayElements(buf, 0);
    jbyte *yuv420sp = (jbyte *)malloc(env->GetArrayLength(buf));

    //旋转图像（因为传过来的图像是横着的）
    int index = 0;
    //旋转Y分量，放入dst数组
    for (int y = 0; y < width; y++)
        for (int x = 0; x < height; x++) {
            int oldY = (height - 1) - x;
            int oldX = y;
            int oldIndex = oldY * width + oldX;
            yuv420sp[index++] = yuv420spSRC[oldIndex];
        }
    //每四个点采集一组VU分量，共享右上角像素的VU分量
    //根据Y分量，找到对应的VU分量，放入dst数组
    for (int y = 0; y < width; y += 2)
        for (int x = 0; x < height; x += 2) {
            int oldY = (height - 1) - (x + 1);
            int oldX = y;
            int vuY = height + oldY / 2; //根据Y分量计算VU分量所在行
            int vuX = oldX;
            int vuIndex = vuY * width + vuX;
            yuv420sp[index++] = yuv420spSRC[vuIndex];
            yuv420sp[index++] = yuv420spSRC[vuIndex + 1];
        }

    //将YUV转为RGB
    int frameSize = width * height;
    jint rgb[frameSize];
    int i = 0, j = 0, yp = 0;
    int uvp = 0, u = 0, v = 0;
    for (j = 0, yp = 0; j < height; j++) {
        uvp = frameSize + (j >> 1) * width;
        u = 0;
        v = 0;
        for (i = 0; i < width; i++, yp++) {
            int y = (0xff & ((int) yuv420sp[yp])) - 16;
            if (y < 0)
                y = 0;
            if ((i & 1) == 0) {
                v = (0xff & yuv420sp[uvp++]) - 128;
                u = (0xff & yuv420sp[uvp++]) - 128;
            }

            int y1192 = 1192 * y;
            int r = (y1192 + 1634 * v);
            int g = (y1192 - 833 * v - 400 * u);
            int b = (y1192 + 2066 * u);

            if (r < 0) r = 0; else if (r > 262143) r = 262143;
            if (g < 0) g = 0; else if (g > 262143) g = 262143;
            if (b < 0) b = 0; else if (b > 262143) b = 262143;

            rgb[yp] = 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
        }
    }
    jintArray result = (env)->NewIntArray(frameSize);
    (env)->SetIntArrayRegion(result, 0, frameSize, rgb);
    free(yuv420sp);
    (env)->ReleaseByteArrayElements(buf, yuv420spSRC, 0);
    return result;
}