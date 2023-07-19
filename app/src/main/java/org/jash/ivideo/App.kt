package org.jash.ivideo

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.android.arouter.utils.ClassUtils

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
//            val set =
//                ClassUtils.getFileNameByPackageName(this, "com.alibaba.android.arouter.routes")
//            println(set)
        }
    }
}