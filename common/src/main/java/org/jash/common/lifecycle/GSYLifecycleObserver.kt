package org.jash.common.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer

class GSYLifecycleObserver(val player:GSYVideoPlayer):LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when(event) {
            Lifecycle.Event.ON_RESUME -> player.onVideoResume()
            Lifecycle.Event.ON_PAUSE -> player.onVideoPause()
            Lifecycle.Event.ON_DESTROY -> player.release()
            else -> {}
        }
    }
}