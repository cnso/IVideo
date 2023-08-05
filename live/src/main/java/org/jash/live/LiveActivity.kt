package org.jash.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import org.jash.common.lifecycle.GSYLifecycleObserver
import org.jash.common.logDebug
import org.jash.live.databinding.ActivityLiveBinding
import org.jash.live.xmpp.XMPPManager
import org.jash.network.user
import org.jivesoftware.smack.MessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smackx.muc.MultiUserChat
import org.jivesoftware.smackx.muc.ParticipantStatusListener
import org.jxmpp.jid.EntityFullJid
import kotlin.concurrent.thread

@Route(path = "/live/live")
class LiveActivity : AppCompatActivity(), MessageListener, ParticipantStatusListener {
    val binding:ActivityLiveBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_live) }
    @Autowired
    @JvmField
    var title:String = ""
    var muc: MultiUserChat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        binding.player.setUp("http://10.161.9.80:8066/live/livestream.flv", false, title)
        binding.player.backButton.setOnClickListener { finish() }
        lifecycle.addObserver(GSYLifecycleObserver(binding.player))
        binding.player.startPlayLogic()
        thread {
            user?.let {
                muc = XMPPManager.joinChatRoom(title, it.username)
                muc?.addMessageListener(this)
                muc?.addParticipantStatusListener(this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        thread {
            XMPPManager.leaveChatRoom(muc)
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