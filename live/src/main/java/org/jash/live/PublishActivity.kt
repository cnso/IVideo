package org.jash.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.ivideo.avcore.rtmplive.Config
import com.ivideo.avcore.rtmplive.MediaPublisher
import com.ivideo.avcore.wiget.CameraGLSurfaceView.OnSurfaceCallback
import org.jash.common.logDebug
import org.jash.live.databinding.ActivityPublishBinding
import org.jash.live.xmpp.XMPPManager
import org.jash.network.user
import org.jivesoftware.smack.MessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smackx.muc.MultiUserChat
import org.jivesoftware.smackx.muc.ParticipantStatusListener
import org.jxmpp.jid.EntityFullJid
import javax.microedition.khronos.opengles.GL10
import kotlin.concurrent.thread

@Route(path = "/live/publish")
class PublishActivity : AppCompatActivity(), MessageListener, ParticipantStatusListener{
    lateinit var binding: ActivityPublishBinding
    lateinit var mediaPublisher: MediaPublisher
    var muc:MultiUserChat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_publish)
        thread {
            user?.let {
                muc = XMPPManager.createChatRoom("${it.username}的房间", it.username)
                muc?.addMessageListener(this)
                muc?.addParticipantStatusListener(this)
            }
        }
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
        thread {
            XMPPManager.destroyChatRoom(muc)
        }
    }

    override fun processMessage(message: Message?) {
        message?.takeIf { it.from.hasResource() }?.let {
            logDebug( "${it.from.resourceOrEmpty}: ${it.body}")
        }
    }

    override fun joined(participant: EntityFullJid?) {
        participant?.let {
            logDebug("${it.resourceOrEmpty} 加入直播间")
        }
    }

    override fun left(participant: EntityFullJid?) {
        participant?.let {
            logDebug("${it.resourceOrEmpty} 离开直播间")
        }
    }
}