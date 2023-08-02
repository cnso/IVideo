cmake_minimum_required(VERSION 3.10.2)

project("avcore")

add_subdirectory(librtmp)

add_library(
        avcore
        SHARED
        publish_jni.cpp
        Rtmp.cpp
        avcore.cpp
        FFmpeg.cpp
        YUVUtils.cpp
)

set(FF ${CMAKE_CURRENT_SOURCE_DIR}/../../../libs)
add_library(avcodec SHARED IMPORTED)
add_library(avformat SHARED IMPORTED)
add_library(avutil SHARED IMPORTED)
add_library(swscale SHARED IMPORTED)
add_library(swresample SHARED IMPORTED)
add_library(avfilter SHARED IMPORTED)
add_library(postproc SHARED IMPORTED)
set_target_properties(avcodec PROPERTIES IMPORTED_LOCATION ${FF}/${ANDROID_ABI}/libavcodec.so)
set_target_properties(avformat PROPERTIES IMPORTED_LOCATION ${FF}/${ANDROID_ABI}/libavformat.so)
set_target_properties(avutil PROPERTIES IMPORTED_LOCATION ${FF}/${ANDROID_ABI}/libavutil.so)
set_target_properties(swscale PROPERTIES IMPORTED_LOCATION ${FF}/${ANDROID_ABI}/libswscale.so)
set_target_properties(swresample PROPERTIES IMPORTED_LOCATION ${FF}/${ANDROID_ABI}/libswresample.so)
set_target_properties(avfilter PROPERTIES IMPORTED_LOCATION ${FF}/${ANDROID_ABI}/libavfilter.so)
set_target_properties(postproc PROPERTIES IMPORTED_LOCATION ${FF}/${ANDROID_ABI}/libpostproc.so)

target_include_directories(avcore PRIVATE
        src/main/cpp/include)
#添加头文件目录
include_directories(${rtmp_dir}/include/librtmp)


find_library(
        log-lib
        log)

target_link_libraries(
        avcore
        rtmp

        avcodec
        avfilter
        avformat
        avutil
        swresample
        swscale
        postproc

        ${log-lib})


#set(rtmp_dir ${CMAKE_SOURCE_DIR}/../../../../rtmp)
#set(rtmp_lib_dir ${rtmp_dir}/lib)
#
#
#add_library( publish
#        SHARED
#        publish_jni.cpp
#        Rtmp.cpp
#        )
#
#
#find_library(
#        log-lib
#        log )
#
##librtmp
#
#add_library(rtmp SHARED IMPORTED)
#set_target_properties(rtmp PROPERTIES IMPORTED_LOCATION
#        ${rtmp_lib_dir}/${ANDROID_ABI}/librtmp.so)
#
#target_link_libraries( publish
#        ${log-lib}
#        rtmp
#        )