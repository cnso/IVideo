package org.jash.live

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.ActivityResultRegistryOwner
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.ivideo.avcore.rtmplive.Config
import com.ivideo.avcore.rtmplive.MediaPublisher
import com.ivideo.avcore.wiget.CameraGLSurfaceView
import com.ivideo.avcore.wiget.CameraGLSurfaceView.OnSurfaceCallback
import org.jash.live.databinding.ActivityPublishBinding
import javax.microedition.khronos.opengles.GL10

@Route(path = "/live/publish")
class PublishActivity : AppCompatActivity() {
    lateinit var binding: ActivityPublishBinding
    lateinit var mediaPublisher: MediaPublisher
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_publish)

    }

    override fun onResume() {
        super.onResume()
        mediaPublisher = MediaPublisher.newInstance(
            Config.Builder()
                .setFps(30)
                .setMaxWidth(720)
                .setMinWidth(360)
                .setUrl(BuildConfig.RTMP_URL)
                .build()
        )
        mediaPublisher.init()
        binding.surface.setCallback(object :OnSurfaceCallback{
            override fun surfaceCreated() {
                mediaPublisher.initVideoGatherer(this@PublishActivity, binding.surface.surfaceTexture)
            }

            override fun surfaceChanged(gl: GL10?, width: Int, height: Int) {
                mediaPublisher.initVideoGatherer(this@PublishActivity, binding.surface.surfaceTexture)
            }
        })
        binding.checkbox.setOnCheckedChangeListener { _, b ->
            if (b) {
                start()
            } else {
                stop()
            }
        }
    }
    fun start() {
        mediaPublisher.initAudioGatherer()
        mediaPublisher.initEncoders()
        mediaPublisher.startGather()
        mediaPublisher.startEncoder()
        mediaPublisher.starPublish()

    }
    fun stop() {
        mediaPublisher.stopPublish()
        mediaPublisher.stopEncoder()
        mediaPublisher.stopGather()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPublisher.release()
    }
}