package org.jash.live.model

import com.alibaba.android.arouter.launcher.ARouter

data class LiveRoom (
    val name:String,
    val logo:String,
    val info:String
) {
    fun look() {
        ARouter.getInstance().build("/live/live")
            .withString("title", name)
            .navigation()
    }
}