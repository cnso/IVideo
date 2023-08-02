package org.jash.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import org.jash.common.lifecycle.GSYLifecycleObserver
import org.jash.live.databinding.ActivityLiveBinding

@Route(path = "/live/live")
class LiveActivity : AppCompatActivity() {
    val binding:ActivityLiveBinding by lazy { DataBindingUtil.setContentView(this, R.layout.activity_live) }
    @Autowired
    @JvmField
    var title:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        binding.player.setUp("http://10.161.9.80:8066/live/livestream.flv", false, title)
        binding.player.backButton.setOnClickListener { finish() }
        lifecycle.addObserver(GSYLifecycleObserver(binding.player))
        binding.player.startPlayLogic()
    }
}