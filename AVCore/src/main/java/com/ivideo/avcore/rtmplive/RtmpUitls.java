package com.ivideo.avcore.rtmplive;

/**
 * @author zhangyue
 * Rtmp JNI
 */
public final class RtmpUitls {
    static {
        System.loadLibrary("avcore");
    }

    static native long init(String url, int w, int h, int timeOut);

    static native int sendSpsAndPps(long cptr, byte[] sps, int spsLen, byte[] pps,
                                    int ppsLen, long timestamp);

    static native int sendVideoData(long cptr, byte[] data, int len, long timestamp);

    static native int sendAacSpec(long cptr, byte[] data, int len);

    static native int sendAacData(long cptr, byte[] data, int len, long timestamp);

    static native int stop(long cptr);

}
