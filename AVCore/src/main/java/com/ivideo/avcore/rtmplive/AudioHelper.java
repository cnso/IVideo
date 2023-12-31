package com.ivideo.avcore.rtmplive;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * @author zhangyue
 * 音频采集管理类
 */
public class AudioHelper {

    private static final String TAG = AudioHelper.class.getSimpleName();

    private Config mConfig;

    private AudioRecord mAudioRecord;
    private byte[] buffer;
    private Thread workThread;
    private boolean loop;
    private Callback mCallback;

    public static AudioHelper newInstance(Config config) {
        return new AudioHelper(config);

    }
    private AudioHelper(Config config){
        this.mConfig =config;
    }

    public static class Params {
        public final int sampleRate;
        public final int channelCount;

        public Params(int sampleRate, int channelCount) {
            this.sampleRate = sampleRate;
            this.channelCount = channelCount;
        }
    }

    /**
     * 初始化录音
     */
    public Params initAudioDevice() {
        int[] sampleRates = {44100, 22050, 16000, 11025,8000,4000};
        for (int sampleRate :
                sampleRates) {
            //编码制式
            int audioFormat = mConfig.audioFormat;
            // stereo 立体声，
            int channelConfig = mConfig.channelConfig;
            int buffsize = 2 * AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig,
                    audioFormat, buffsize);
            if (mAudioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                continue;
            }
            this.buffer = new byte[Math.min(4096, buffsize)];

            return new Params(sampleRate,
                    channelConfig == AudioFormat.CHANNEL_CONFIGURATION_STEREO ? 2 : 1);
        }

        return null;
    }

    /**
     * 开始录音
     */
    public void start() {
        workThread = new Thread() {
            @Override
            public void run() {
                mAudioRecord.startRecording();
                while (loop && !Thread.interrupted()) {
                    int size = mAudioRecord.read(buffer, 0, buffer.length);
                    if (size < 0) {
                        Log.i(TAG, "audio ignore ,no data to read");
                        break;
                    }
                    if (loop) {
                        byte[] audio = new byte[size];
                        System.arraycopy(buffer, 0, audio, 0, size);
                        if (mCallback != null) {
                            mCallback.audioData(audio);
                        }
                    }
                }

            }
        };

        loop = true;
        workThread.start();
    }

    public void stop() {
        loop = false;
        if(workThread != null) {
            workThread.interrupt();
        }

        if(mAudioRecord != null) {
            Log.i(TAG, "run: 调用stop");
            mAudioRecord.stop();
        }
    }

    public void release() {
        if(mAudioRecord != null) {
            mAudioRecord.release();
        }
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public interface Callback {
        void audioData(byte[] data);
    }
}
