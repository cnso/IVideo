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

