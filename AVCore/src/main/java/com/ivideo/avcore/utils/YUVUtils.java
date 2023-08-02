package com.ivideo.avcore.utils;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/6/15 8:48
 * @description
 * @updateUser hahajing
 * @updateDate 2022/6/15 8:48
 * @updateRemark
 */
public class YUVUtils {
    static {
        System.loadLibrary("avcore");
    }

    public native int[] yuv2Rgb(byte[] buf,int width,int height);
}
