package org.jash.common

import android.app.Application

open class BaseApplication:Application() {
    companion object {
//        @JvmStatic
        lateinit var appContext:BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }
}