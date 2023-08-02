package com.ivideo.avcore.rtmplive;

/**
 * @author zhangyue
 * Rtmp推流
 */
public class RtmpPublisher {

    private long cPtr;
    private long timeOffset;

    public static RtmpPublisher newInstance() {

        return new RtmpPublisher();
    }
    private RtmpPublisher(){}

    public int init(String url, int w, int h, int timeOut) {
        cPtr = RtmpUitls.init(url, w, h, timeOut);
        if (cPtr != 0) {
            return 0;
        }
        return -1;
    }

    public int sendSpsAndPps(byte[] sps, int spsLen, byte[] pps, int ppsLen, long timeOffset) {
        this.timeOffset = timeOffset;
        return RtmpUitls.sendSpsAndPps(cPtr, sps, spsLen, pps, ppsLen, 0);
    }

    public int sendVideoData(byte[] data, int len, long timestamp) {
        if(timestamp-timeOffset<=0){return -1;}
        return RtmpUitls.sendVideoData(cPtr, data, len, timestamp - timeOffset);
    }

    public int sendAacSpec(byte[] data, int len) {
        return RtmpUitls.sendAacSpec(cPtr, data, len);
    }

    public int sendAacData(byte[] data, int len, long timestamp) {
        if(timestamp-timestamp<0){return -1;}
        return RtmpUitls.sendAacData(cPtr, data, len, timestamp - timeOffset);
    }

    public int stop() {
        try {
            return RtmpUitls.stop(cPtr);
        }finally {
            cPtr=0;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(cPtr!=0){
            stop();
        }
    }
}
