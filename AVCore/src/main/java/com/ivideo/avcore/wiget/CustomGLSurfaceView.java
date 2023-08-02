package com.ivideo.avcore.wiget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.ivideo.avcore.filtercommon.CameraDrawer2;
import com.ivideo.avcore.utils.CameraDrawer;
import com.ivideo.avcore.utils.OpenGLUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/12 15:37
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/12 15:37
 * @updateRemark
 */
public class CustomGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer, SurfaceTexture.OnFrameAvailableListener {

    private int mTextureId;
    private SurfaceTexture mSurfaceTexture;
    private CameraDrawer mDrawer;

//    private CameraDrawer2 mDrawer2;

    private CameraGLSurfaceView.OnSurfaceCallback callback;

    private int mRatioWidth = 0;
    private int mRatioHeight = 0;

    public CustomGLSurfaceView(Context context) {
        super(context);
    }

    public CustomGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setEGLContextClientVersion(2);//OpenGL ES 2.0
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
//        mDrawer2=new CameraDrawer2(getResources());
    }

    @Override
    public void onFrameAvailable(SurfaceTexture surfaceTexture) {
        requestRender();
    }

    @SuppressLint("Recycle")
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        mTextureId = OpenGLUtils.getExternalOESTextureID();
        mSurfaceTexture = new SurfaceTexture(mTextureId);
        mSurfaceTexture.setOnFrameAvailableListener(this);
        mDrawer = new CameraDrawer();

//       mDrawer2.onSurfaceCreated(gl,config);
//       mDrawer2.setCameraId(0);

        if (null!=this.callback){
            this.callback.surfaceCreated();
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

//        mDrawer2.onSurfaceChanged(gl,width,height);

        GLES20.glViewport(0,0,width,height);

        if (null!=this.callback){
            this.callback.surfaceChanged(gl,width,height);
        }

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClearColor(0,0,0,0);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        mSurfaceTexture.updateTexImage();
//        mDrawer2.onDrawFrame(gl);
        mDrawer.draw(mTextureId,false);
    }

    public SurfaceTexture getSurfaceTexture() {
        return mSurfaceTexture;
    }

    public void setCallback(CameraGLSurfaceView.OnSurfaceCallback callback) {
        this.callback = callback;
    }

    public interface OnSurfaceCallback{
        void surfaceCreated();
        void surfaceChanged(GL10 gl, int width, int height);
    }

}
